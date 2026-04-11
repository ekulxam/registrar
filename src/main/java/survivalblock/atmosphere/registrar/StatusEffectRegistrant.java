/*
 * All Rights Reserved
 *
 * Copyright (c) 2025-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Function;

@SuppressWarnings("unused")
public class StatusEffectRegistrant extends Registrant<MobEffect> {
    protected StatusEffectRegistrant(String modId, Registry<MobEffect> registry) {
        super(modId, registry);
    }

    protected StatusEffectRegistrant(Function<String, Identifier> idFunction, Registry<MobEffect> registry) {
        super(idFunction, registry);
    }

    public StatusEffectRegistrant(String modId) {
        this(modId, BuiltInRegistries.MOB_EFFECT);
    }

    public StatusEffectRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.MOB_EFFECT);
    }

    public Holder.Reference<MobEffect> registerReference(String name, MobEffect effect) {
        return Registry.registerForHolder(this.registry, this.idFunction.apply(name), effect);
    }
}
