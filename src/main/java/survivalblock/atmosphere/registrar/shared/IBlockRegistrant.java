package survivalblock.atmosphere.registrar.shared;

//? if >=26.2
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public interface IBlockRegistrant extends IRegistrant<Block> {
    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    default <T extends Block, S extends BlockBehaviour.Properties> T register(String name, Function<S, T> blockFunction, S settings) {
        return this.register(this.createKey(name), blockFunction, settings);
    }

    //? if >=26.2 {
    default <T extends Block, S extends BlockBehaviour.Properties> T register(BlockItemId blockItemId, Function<S, T> blockFunction, S settings) {
        return this.register(blockItemId.block(), blockFunction, settings);
    }
    //?}

    default <T extends Block, S extends BlockBehaviour.Properties> T register(ResourceKey<Block> key, Function<S, T> blockFunction, S settings) {
        T block = blockFunction.apply(/*? >=1.21.2 {*/(S)/*?}*/ settings /*? >=1.21.2 {*/.setId(key) /*?}*/);
        return this.register(key, block);
    }

    //? if >=26.2 {
    @SuppressWarnings("unused")
    default BlockItemId createId(String name) {
        Identifier id = this.id(name);
        return BlockItemId.create(id, id);
    }

    @SuppressWarnings("unused")
    default BlockItemId createId(String block, String item) {
        return BlockItemId.create(this.id(block), this.id(item));
    }
    //?}
}
