/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class Registrant<T> {
    protected final Function<String, ResourceLocation> idFunction;
    protected final Registry<T> registry;

    public Registrant(String modId, Registry<T> registry) {
        this(path -> ResourceLocation.fromNamespaceAndPath(modId, path), registry);
    }

    public Registrant(Function<String, ResourceLocation> idFunction, Registry<T> registry) {
        this.idFunction = idFunction;
        this.registry = registry;
    }

    public <U extends T> U register(String name, U obj) {
        return Registry.register(this.registry, this.idFunction.apply(name), obj);
    }
}
