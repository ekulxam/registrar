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
import net.minecraft.world.effect.MobEffect;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedStatusEffectRegistrant extends DelayedRegistrant<MobEffect> {
    protected DelayedStatusEffectRegistrant(String modId, Registry<MobEffect> registry) {
        super(modId, registry);
    }

    protected DelayedStatusEffectRegistrant(Function<String, Identifier> idFunction, Registry<MobEffect> registry) {
        super(idFunction, registry);
    }

    public DelayedStatusEffectRegistrant(String modId) {
        this(modId, BuiltInRegistries.MOB_EFFECT);
    }

    public DelayedStatusEffectRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.MOB_EFFECT);
    }

    public Holder.Reference<MobEffect> registerReference(String name, MobEffect effect) {
        return Registry.registerForHolder(this.registry, this.idFunction.apply(name), effect);
    }
}
