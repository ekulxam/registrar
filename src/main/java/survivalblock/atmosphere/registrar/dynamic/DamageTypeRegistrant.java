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
package survivalblock.atmosphere.registrar.dynamic;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageType;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DamageTypeRegistrant extends DynamicRegistrant<DamageType> {
    protected DamageTypeRegistrant(String modId, ResourceKey<? extends Registry<DamageType>> registry) {
        super(modId, registry);
    }

    protected DamageTypeRegistrant(Function<String, Identifier> idFunction, ResourceKey<? extends Registry<DamageType>> registry) {
        super(idFunction, registry);
    }

    public DamageTypeRegistrant(String modId) {
        this(modId, Registries.DAMAGE_TYPE);
    }

    public DamageTypeRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, Registries.DAMAGE_TYPE);
    }

    public ResourceKey<DamageType> register(String path, String message) {
        return this.register(path, message, 0.1F);
    }

    public ResourceKey<DamageType> register(String path, String message, float exhaustion) {
        return this.register(path, new DamageType(message, exhaustion));
    }
}
