package survivalblock.atmosphere.registrar.shared;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.registries.BuiltInRegistries;
//? if >=26.2
import net.minecraft.references.BlockItemId;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import survivalblock.atmosphere.registrar.Registrant;
import survivalblock.atmosphere.registrar.annotation.ConstructItem;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Function;

public interface IBlockItemRegistrant {
    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    <T extends Item, S extends Item.Properties> T register(Block block, Function<S, T> itemFunction, S settings);

    //? if >=26.2
    <T extends Item, S extends Item.Properties> T register(BlockItemId id, Block block, Function<S, T> itemFunction, S settings);

    /**
     * Uses reflection to add {@link Item}s from {@linkplain Block}s (<26.2) or {@linkplain BlockItemId}s (>=26.2).
     * Note that this method requires the Blocks to all be registered.
     * @param clazz the class containing the {@linkplain Block}s (<26.2) or {@linkplain BlockItemId}s (>=26.2)
     * @param tryAllByDefault true to attempt registration, even if no annotation is present
     * @return all successfully registered items
     */
    default Map<? extends Block, ? extends Item> registerFromAnnotations(Class<?> clazz, boolean tryAllByDefault) {
        ImmutableMap.Builder<Block, Item> builder = ImmutableMap.builder();
        for (Field field : clazz.getFields()) {
            try {
                Class<? extends Item> blockItemClass;
                boolean useBlockTranslation;
                //? if >=26.2
                boolean suppressIdWarnings;
                if (field.isAnnotationPresent(ConstructItem.class)) {
                    ConstructItem construct = field.getAnnotation(ConstructItem.class);
                    if (construct.exclude()) {
                        continue;
                    }
                    blockItemClass = construct.constructor();
                    useBlockTranslation = construct.useBlockTranslation();
                    //? if >=26.2
                    suppressIdWarnings = construct.suppressIdWarnings();
                } else if (tryAllByDefault) {
                    blockItemClass = BlockItem.class;
                    useBlockTranslation = true;
                    //? if >=26.2
                    suppressIdWarnings = false;
                } else {
                    continue;
                }

                Object obj = field.get(null);
                Block block;
                //? if >=26.2 {
                BlockItemId id;
                if (obj instanceof BlockItemId) {
                    id = (BlockItemId) obj;
                    block = BuiltInRegistries.BLOCK.getValue(id.block());

                    if (block == null) {
                        continue;
                    }
                } else if (obj instanceof Block) {
                    id = null;
                    block = (Block) obj;
                    if (!suppressIdWarnings) {
                        Registrant.LOGGER.warn("Item {} from block {} in class {} is being registered reflectively without a BlockItemId! This is a deprecated action and will likely not be possible in future versions of Minecraft.", blockItemClass.getName(), block, clazz.getName());
                    }
                } else {
                    continue;
                }
                //?} else {
                /*if (!(obj instanceof Block)) {
                    continue;
                }
                block = (Block) obj;
                *///?}

                Item.Properties settings = new Item.Properties();
                if (useBlockTranslation) {
                    settings.useBlockDescriptionPrefix();
                }

                Constructor<? extends Item> constructor = blockItemClass.getConstructor(Block.class, Item.Properties.class);

                Function<Item.Properties, Item> creator = properties -> {
                    try {
                        return constructor.newInstance(block, properties);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                };

                //? if >=26.2 {
                Item item;
                if (id == null) {
                    item = this.register(block, creator, settings);
                } else {
                    item = this.register(id, block, creator, settings);
                }
                //?} else {
                /*item = this.register(block, creator, settings);
                 *///?}

                builder.put(block, item);
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return builder.build();
    }
}
