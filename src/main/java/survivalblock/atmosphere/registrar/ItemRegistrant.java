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
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//? if >=1.21.2
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

@SuppressWarnings("unused")
public class ItemRegistrant extends Registrant<Item> {
    protected ItemRegistrant(String modId, Registry<Item> registry) {
        super(modId, registry);
    }

    protected ItemRegistrant(Function<String, Identifier> idFunction, Registry<Item> registry) {
        super(idFunction, registry);
    }

    public ItemRegistrant(String modId) {
        this(modId, BuiltInRegistries.ITEM);
    }

    public ItemRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.ITEM);
    }

    public <T extends Item, S extends Item.Properties> T register(String name, Function<S, T> itemFunction, S settings) {
        T item = itemFunction.apply(/*? >=1.21.2 {*/(S)/*?}*/ settings /*? >=1.21.2 {*/.setId(ResourceKey.create(this.registry.key(), this.idFunction.apply(name))) /*?}*/);
        return this.register(name, item);
    }

    public BlockItem register(Block block) {
        return register(block, new Item.Properties());
    }

    public <S extends Item.Properties> BlockItem register(Block block, S settings) {
        return register(block, settings1 -> new BlockItem(block, settings1), settings);
    }

    public <T extends Item, S extends Item.Properties> T register(Block block, Function<S, T> itemFunction, S settings) {
        T item = this.register(block.builtInRegistryHolder().key()./*? <1.21.11 {*/ /*location() *//*?} else {*/ identifier() /*?}*/.getPath(), itemFunction, settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.registerBlocks(Item.BY_BLOCK, blockItem);
        }
        return item;
    }
}
