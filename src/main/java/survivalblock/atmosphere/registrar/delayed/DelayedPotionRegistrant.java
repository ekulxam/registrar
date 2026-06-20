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

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.alchemy.Potion;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedPotionRegistrant extends DelayedRegistrant<Potion> {
    protected DelayedPotionRegistrant(String modId, Registry<Potion> registry) {
        super(modId, registry);
    }

    protected DelayedPotionRegistrant(Function<String, Identifier> idFunction, Registry<Potion> registry) {
        super(idFunction, registry);
    }

    public DelayedPotionRegistrant(String modId) {
        this(modId, BuiltInRegistries.POTION);
    }

    public DelayedPotionRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.POTION);
    }

    public Holder.Reference<Potion> registerReference(String name, Potion potion) {
        return Registry.registerForHolder(this.registry, this.idFunction.apply(name), potion);
    }
}
