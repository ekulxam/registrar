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
