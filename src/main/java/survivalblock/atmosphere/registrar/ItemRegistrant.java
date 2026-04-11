/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//? if >=1.21.2
/*import net.minecraft.resources.ResourceKey;*/
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

@SuppressWarnings("unused")
public class ItemRegistrant extends Registrant<Item> {
    protected ItemRegistrant(String modId, Registry<Item> registry) {
        super(modId, registry);
    }

    protected ItemRegistrant(Function<String, ResourceLocation> idFunction, Registry<Item> registry) {
        super(idFunction, registry);
    }

    public ItemRegistrant(String modId) {
        this(modId, BuiltInRegistries.ITEM);
    }

    public ItemRegistrant(Function<String, ResourceLocation> idFunction) {
        this(idFunction, BuiltInRegistries.ITEM);
    }

    public <T extends Item, S extends Item.Properties> T register(String name, Function<S, T> itemFunction, S settings) {
        T item = itemFunction.apply(/*? >=1.21.2 {*//*(S)*//*?}*/ settings /*? >=1.21.2 {*//*.setId(ResourceKey.create(this.registry.key(), this.idFunction.apply(name))) *//*?}*/);
        return this.register(name, item);
    }

    public BlockItem register(Block block) {
        return register(block, new Item.Properties());
    }

    public <S extends Item.Properties> BlockItem register(Block block, S settings) {
        return register(block, settings1 -> new BlockItem(block, settings1), settings);
    }

    public <T extends Item, S extends Item.Properties> T register(Block block, Function<S, T> itemFunction, S settings) {
        T item = this.register(block.builtInRegistryHolder().key().location().getPath(), itemFunction, settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, blockItem);
        }
        return item;
    }
}
