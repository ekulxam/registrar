/*
 * All Rights Reserved
 *
 * Copyright (c) 2025-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

public class Registrant<T> {
    protected final Function<String, Identifier> idFunction;
    protected final Registry<T> registry;

    public Registrant(String modId, Registry<T> registry) {
        this(path -> Identifier.fromNamespaceAndPath(modId, path), registry);
    }

    public Registrant(Function<String, Identifier> idFunction, Registry<T> registry) {
        this.idFunction = idFunction;
        this.registry = registry;
    }

    public <U extends T> U register(String name, U obj) {
        return Registry.register(this.registry, this.idFunction.apply(name), obj);
    }
}
