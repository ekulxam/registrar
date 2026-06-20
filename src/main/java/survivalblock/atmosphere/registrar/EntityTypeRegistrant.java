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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Function;

@SuppressWarnings("unused")
public class EntityTypeRegistrant extends Registrant<EntityType<?>> {
    protected EntityTypeRegistrant(String modId, Registry<EntityType<?>> registry) {
        super(modId, registry);
    }

    protected EntityTypeRegistrant(Function<String, Identifier> idFunction, Registry<EntityType<?>> registry) {
        super(idFunction, registry);
    }

    public EntityTypeRegistrant(String modId) {
        this(modId, BuiltInRegistries.ENTITY_TYPE);
    }

    public EntityTypeRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.ENTITY_TYPE);
    }

    public <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        return this.register(name, builder.build(/*? >=1.21.2 {*/ResourceKey.create(this.registry.key(), this.idFunction.apply(name))/*?}*/));
    }

    public <T extends Entity> EntityType<T> register(String name, EntityType<T> entityType) {
        return super.register(name, entityType);
    }
}
