/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Function;

@SuppressWarnings("unused")
public class SoundEventRegistrant extends Registrant<SoundEvent> {
    protected SoundEventRegistrant(String modId, Registry<SoundEvent> registry) {
        super(modId, registry);
    }

    protected SoundEventRegistrant(Function<String, Identifier> idFunction, Registry<SoundEvent> registry) {
        super(idFunction, registry);
    }

    public SoundEventRegistrant(String modId) {
        this(modId, BuiltInRegistries.SOUND_EVENT);
    }

    public SoundEventRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.SOUND_EVENT);
    }

    public SoundEvent register(String name) {
        return this.register(name, (Function<Identifier, SoundEvent>) SoundEvent::createVariableRangeEvent);
    }

    public SoundEvent register(String name, Function<Identifier, SoundEvent> soundEventFunction) {
        return this.register(name, soundEventFunction.apply(this.idFunction.apply(name)));
    }

    public Holder.Reference<SoundEvent> registerReference(String name) {
        return this.registerReference(name, SoundEvent::createVariableRangeEvent);
    }

    public Holder.Reference<SoundEvent> registerReference(String name, Function<Identifier, SoundEvent> soundEventFunction) {
        return this.registerReference(name, soundEventFunction.apply(this.idFunction.apply(name)));
    }

    public Holder.Reference<SoundEvent> registerReference(String name, SoundEvent soundEvent) {
        return Registry.registerForHolder(this.registry, this.idFunction.apply(name), soundEvent);
    }
}
