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
package survivalblock.atmosphere.registrar.wrapper;

//? if >=26.2
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
//? if >=26.2
import org.jspecify.annotations.Nullable;

//? if >=26.2
import java.util.Objects;
import java.util.function.Supplier;

@SuppressWarnings("ClassCanBeRecord")
public class BlockPresenter<T extends Block> implements ItemLike, Supplier<T> {
    protected final T block;
    protected final ResourceKey<Block> resourceKey;
    //? if >=26.2 {
    @Nullable
    protected final BlockItemId blockItemId;
    //?}

    public BlockPresenter(T block, ResourceKey<Block> resourceKey /*? >=26.2 {*/, @org.jspecify.annotations.Nullable BlockItemId blockItemId/*?}*/) {
        this.block = block;
        this.resourceKey = resourceKey;
        //? if >=26.2
        this.blockItemId = blockItemId;
    }

    public T getBlock() {
        return this.block;
    }

    public ResourceKey<Block> getKey() {
        return this.resourceKey;
    }

    //? if >=26.2 {
    @Nullable
    public BlockItemId getBlockItemId() {
        return this.blockItemId;
    }

    @SuppressWarnings("unused")
    public BlockItemId getBlockItemIdOrThrow() {
        return Objects.requireNonNull(this.blockItemId);
    }
    //?}

    @Override
    public Item asItem() {
        return this.block.asItem();
    }

    @Override
    public T get() {
        return this.block;
    }
}
