/*
 * MIT License
 *
 * Copyright (c) 2025-present ekulxam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package survivalblock.atmosphere.registrar.delayed;

//? if >=26.2
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//? if >=26.2
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
//? if >=26.2
import survivalblock.atmosphere.registrar.annotation.ConstructItem;

//? if >=26.2 {
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Map;
//?}
import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedItemRegistrant extends DelayedRegistrant<Item> {
    protected DelayedItemRegistrant(String modId, Registry<Item> registry) {
        super(modId, registry);
    }

    protected DelayedItemRegistrant(Function<String, Identifier> idFunction, Registry<Item> registry) {
        super(idFunction, registry);
    }

    public DelayedItemRegistrant(String modId) {
        this(modId, BuiltInRegistries.ITEM);
    }

    public DelayedItemRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.ITEM);
    }

    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    public <T extends Item, S extends Item.Properties> T register(String name, Function<S, T> itemFunction, S settings) {
        return this.register(this.createKey(name), itemFunction, settings);
    }

    public <T extends Item, S extends Item.Properties> T register(ResourceKey<Item> key, Function<S, T> itemFunction, S settings) {
        T item = itemFunction.apply(/*? >=1.21.2 {*/(S)/*?}*/ settings /*? >=1.21.2 {*/.setId(key) /*?}*/);
        return this.register(key, item);
    }

    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    public BlockItem register(Block block) {
        return register(block, new Item.Properties());
    }

    //? if >=26.2 {
    public BlockItem register(BlockItemId id, Block block) {
        return register(id, block, new Item.Properties());
    }
    //?}

    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    public <S extends Item.Properties> BlockItem register(Block block, S settings) {
        return register(block, settings1 -> new BlockItem(block, settings1), settings);
    }

    //? if >=26.2 {
    public <S extends Item.Properties> BlockItem register(BlockItemId id, Block block, S settings) {
        return register(id, block, settings1 -> new BlockItem(block, settings1), settings);
    }
    //?}

    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    public <T extends Item, S extends Item.Properties> T register(Block block, Function<S, T> itemFunction, S settings) {
        T item = this.register(block.builtInRegistryHolder().key()./*? <1.21.11 {*/ /*location() *//*?} else {*/ identifier() /*?}*/.getPath(), itemFunction, settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, blockItem);
        }
        return item;
    }

    //? if >=26.2 {
    public <T extends Item, S extends Item.Properties> T register(BlockItemId id, Block block, Function<S, T> itemFunction, S settings) {
        T item = this.register(id.item(), itemFunction, settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, blockItem);
        }
        return item;
    }
    //?}

    //? if >=26.2 {
    /**
     * Uses reflection to add {@link Item}s from {@link BlockItemId}s.
     * Note that this method requires the Blocks to all be registered.
     * @param clazz the class containing the {@link BlockItemId}s
     * @param tryAllByDefault true to attempt registration, even if no annotation is present
     * @return all successfully registered items
     */
    public Map<? extends Block, ? extends Item> registerFromAnnotations(Class<?> clazz, boolean tryAllByDefault) {
        ImmutableMap.Builder<Block, Item> builder = ImmutableMap.builder();
        for (Field field : clazz.getFields()) {
            try {
                Class<? extends Item> blockItemClass;
                boolean useBlockTranslation;
                if (field.isAnnotationPresent(ConstructItem.class)) {
                    ConstructItem construct = field.getAnnotation(ConstructItem.class);
                    if (construct.exclude()) {
                        continue;
                    }
                    blockItemClass = construct.constructor();
                    useBlockTranslation = construct.useBlockTranslation();
                } else if (tryAllByDefault) {
                    blockItemClass = BlockItem.class;
                    useBlockTranslation = true;
                } else {
                    continue;
                }

                Object obj = field.get(null);
                if (!(obj instanceof BlockItemId id)) {
                    continue;
                }

                Block block = BuiltInRegistries.BLOCK.getValue(id.block());

                if (block == null) {
                    continue;
                }

                Item.Properties settings = new Item.Properties();
                if (useBlockTranslation) {
                    settings.useBlockDescriptionPrefix();
                }

                Constructor<? extends Item> constructor = blockItemClass.getConstructor(Block.class, Item.Properties.class);

                Item item = this.register(id, block, properties -> {
                    try {
                        return constructor.newInstance(block, properties);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException(e);
                    }
                }, settings);

                builder.put(block, item);
            } catch (ReflectiveOperationException ignored) {
            }
        }
        return builder.build();
    }
    //?}
}
