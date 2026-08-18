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

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//? if >=26.2
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import survivalblock.atmosphere.registrar.ItemRegistrant;

import java.util.Map;
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

    /**
     * Uses reflection to add {@link Item}s from {@linkplain Block}s (<26.2) or {@linkplain BlockItemId}s (>=26.2).
     * Note that this method requires the Blocks to all be registered.
     * @param clazz the class containing the {@linkplain Block}s (<26.2) or {@linkplain BlockItemId}s (>=26.2)
     * @param tryAllByDefault true to attempt registration, even if no annotation is present
     * @return all successfully registered items
     */
    public Map<? extends Block, ? extends Item> registerFromAnnotations(Class<?> clazz, boolean tryAllByDefault) {
        return ItemRegistrant.registerFromAnnotations(clazz, tryAllByDefault, this::register/*? >=26.2 {*/, this::register/*?}*/);
    }
}
