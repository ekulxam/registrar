package survivalblock.atmosphere.registrar.shared;

//? if <=1.21.1
//import com.mojang.datafixers.types.Type;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public interface IBlockEntityRegistrant extends IRegistrant<BlockEntityType<?>> {
    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    default <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> blockEntityType) {
        return IRegistrant.super.register(name, blockEntityType);
    }

    default <T extends BlockEntity> BlockEntityType<T> register(ResourceKey<BlockEntityType<?>> key, BlockEntityType<T> blockEntityType) {
        return IRegistrant.super.register(key, blockEntityType);
    }

    //? if <=1.21.1 {
    /*default <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.Builder<T> builder) {
        return this.register(this.createKey(name), builder.build(null));
    }

    default <T extends BlockEntity> BlockEntityType<T> register(ResourceKey<BlockEntityType<?>> key, BlockEntityType.Builder<T> builder) {
        return this.register(key, builder.build(null));
    }

    default <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType.Builder<T> builder, Type<?> type) {
        return this.register(this.createKey(name), builder.build(type));
    }

    default <T extends BlockEntity> BlockEntityType<T> register(ResourceKey<BlockEntityType<?>> key, BlockEntityType.Builder<T> builder, Type<?> type) {
        return this.register(key, builder.build(type));
    }
    *///?}

    //? if <=1.21.1
    //@SuppressWarnings("deprecation")
    default <T extends BlockEntity> BlockEntityType<T> register(ResourceKey<BlockEntityType<?>> key, FabricBlockEntityTypeBuilder<T> builder) {
        return this.register(key, builder.build());
    }

    //~ if >1.21.1 'BlockEntityType.BlockEntitySupplier' -> 'FabricBlockEntityTypeBuilder.Factory' {
    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    default <T extends BlockEntity> BlockEntityType<T> register(String name, FabricBlockEntityTypeBuilder.Factory<? extends T> blockEntitySupplier, Block... blocks) {
        return this.register(this.createKey(name), blockEntitySupplier, blocks);
    }

    default <T extends BlockEntity> BlockEntityType<T> register(ResourceKey<BlockEntityType<?>> key, FabricBlockEntityTypeBuilder.Factory<? extends T> blockEntitySupplier, Block... blocks) {
        //~ if >1.21.1 'BlockEntityType.Builder.of(' -> 'FabricBlockEntityTypeBuilder.create('
        return this.register(key, FabricBlockEntityTypeBuilder.create(blockEntitySupplier, blocks));
    }
    //~}
}
