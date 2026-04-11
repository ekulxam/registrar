/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//? if >=1.21.2
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

@SuppressWarnings("unused")
public class BlockRegistrant extends Registrant<Block> {
    protected BlockRegistrant(String modId, Registry<Block> registry) {
        super(modId, registry);
    }

    protected BlockRegistrant(Function<String, Identifier> idFunction, Registry<Block> registry) {
        super(idFunction, registry);
    }

    public BlockRegistrant(String modId) {
        this(modId, BuiltInRegistries.BLOCK);
    }

    public BlockRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.BLOCK);
    }

    public <T extends Block, S extends BlockBehaviour.Properties> T register(String name, Function<S, T> blockFunction, S settings) {
        T block = blockFunction.apply(/*? >=1.21.2 {*/(S)/*?}*/ settings /*? >=1.21.2 {*/.setId(ResourceKey.create(this.registry.key(), this.idFunction.apply(name))) /*?}*/);
        return this.register(name, block);
    }
}
