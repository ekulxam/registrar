/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar.delayed;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import survivalblock.atmosphere.registrar.Registrant;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DelayedRegistrant<T> extends Registrant<T> {
    protected final Map<String, T> delayed = new HashMap<>();

    public DelayedRegistrant(String modId, Registry<T> registry) {
        super(modId, registry);
    }

    public DelayedRegistrant(Function<String, Identifier> idFunction, Registry<T> registry) {
        super(idFunction, registry);
    }

    @Override
    public <U extends T> U register(String name, U obj) {
        this.delayed.put(name, obj);
        return obj;
    }

    protected <U extends T> void actuallyRegister(String name, U obj) {
        super.register(name, obj);
    }

    protected void registerAll() {
        this.delayed.forEach(this::actuallyRegister);
    }

    @SuppressWarnings("unused")
    public void consumeAll() {
        this.registerAll();
        this.delayed.clear();
    }
}
