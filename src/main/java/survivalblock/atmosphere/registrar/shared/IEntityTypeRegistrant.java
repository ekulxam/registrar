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

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public interface IEntityTypeRegistrant extends IRegistrant<EntityType<?>> {
    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    default <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        return this.register(this.createKey(name), builder);
    }

    default <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        return this.register(key, builder.build(/*? >=1.21.2 {*/key/*?}*/));
    }

    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    default <T extends Entity> EntityType<T> register(String name, EntityType<T> entityType) {
        return IRegistrant.super.register(name, entityType);
    }

    default <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType<T> entityType) {
        return IRegistrant.super.register(key, entityType);
    }
}
