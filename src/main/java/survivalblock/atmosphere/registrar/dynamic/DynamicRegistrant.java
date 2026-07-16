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

import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * A special {@link survivalblock.atmosphere.registrar.Registrant} for dynamic registries.
 * {@link DynamicRegistrant#bootstrap(BootstrapContext)} can be used as a {@link RegistrySetBuilder.RegistryBootstrap}
 * @see net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint#buildRegistry(RegistrySetBuilder) 
 */
public class DynamicRegistrant<T> {
    protected final Function<String, Identifier> idFunction;
    protected final ResourceKey<? extends Registry<T>> registry;

    protected final Map<ResourceKey<T>, Function<BootstrapContext<T>, T>> toRegister = new HashMap<>();

    public DynamicRegistrant(String modId, ResourceKey<? extends Registry<T>> registry) {
        this(path -> Identifier.fromNamespaceAndPath(modId, path), registry);
    }

    public DynamicRegistrant(Function<String, Identifier> idFunction, ResourceKey<? extends Registry<T>> registry) {
        this.idFunction = idFunction;
        this.registry = registry;
    }

    public ResourceKey<T> register(String path) {
        return ResourceKey.create(this.registry, this.idFunction.apply(path));
    }

    public ResourceKey<T> register(String path, T obj) {
        return this.register(path, registerable -> obj);
    }

    protected ResourceKey<T> register(String path, Function<BootstrapContext<T>, T> objCreator) {
        ResourceKey<T> key = this.register(path);
        this.maybeAdd(key, objCreator);
        return key;
    }

    protected void maybeAdd(ResourceKey<T> key, Function<BootstrapContext<T>, T> objCreator) {
        if (this.runningDatagen()) {
            this.toRegister.put(key, objCreator);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    protected boolean runningDatagen() {
        return FabricDataGenHelper.ENABLED;
    }

    @SuppressWarnings("unused")
    public final void bootstrap(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(this.registry, this::bootstrap);
    }

    public void bootstrap(BootstrapContext<T> registerable) {
        for (Map.Entry<ResourceKey<T>, Function<BootstrapContext<T>, T>> entry : this.toRegister.entrySet()) {
            registerable.register(entry.getKey(), entry.getValue().apply(registerable));
        }
        this.toRegister.clear();
    }

    public interface Creator<T> {
        BootstrapContext<T> registerable();

        default <U> HolderGetter<U> lookup(ResourceKey<? extends Registry<U>> key) {
            return this.registerable().lookup(key);
        }
    }

    @SuppressWarnings("unused")
    public interface SingleObjectCreator<T> extends Creator<T> {
        void define(T obj);
    }

    public abstract class CreatorImpl implements Creator<T> {
        protected final BootstrapContext<T> registerable;
        protected final Map<ResourceKey<? extends Registry<?>>, HolderGetter<?>> lookupMap = new HashMap<>();

        public CreatorImpl(BootstrapContext<T> registerable) {
            this.registerable = registerable;
        }

        @Override
        public BootstrapContext<T> registerable() {
            return this.registerable;
        }

        @Override
        public <U> HolderGetter<U> lookup(ResourceKey<? extends Registry<U>> key) {
            if (!this.lookupMap.containsKey(key)) {
                // trust me, it's better this way
                HolderGetter<U> lookup = Creator.super.lookup(key);
                this.lookupMap.put(key, lookup);
                return lookup;
            }
            //noinspection unchecked
            return (HolderGetter<U>) this.lookupMap.get(key);
        }

        public abstract T build(ResourceKey<T> key);
    }

    @SuppressWarnings("unused")
    public class SingleObjectCreatorImpl extends CreatorImpl implements SingleObjectCreator<T> {
        @Nullable
        protected T obj;

        public SingleObjectCreatorImpl(BootstrapContext<T> registerable) {
            super(registerable);
        }

        @Override
        public T build(ResourceKey<T> key) {
            return Objects.requireNonNull(this.obj);
        }

        @Override
        public void define(T obj) {
            if (this.obj != null) {
                throw new IllegalStateException("Object " + this.obj + " has already been defined!");
            }
            this.obj = obj;
        }
    }
}
