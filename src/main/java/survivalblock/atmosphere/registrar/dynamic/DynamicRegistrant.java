/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar.dynamic;

import net.fabricmc.fabric.impl.datagen.FabricDataGenHelper;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;

import java.util.HashMap;
import java.util.Map;
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
}
