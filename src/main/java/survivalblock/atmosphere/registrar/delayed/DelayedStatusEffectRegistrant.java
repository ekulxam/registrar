/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar.delayed;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedStatusEffectRegistrant extends DelayedRegistrant<MobEffect> {
    protected DelayedStatusEffectRegistrant(String modId, Registry<MobEffect> registry) {
        super(modId, registry);
    }

    protected DelayedStatusEffectRegistrant(Function<String, ResourceLocation> idFunction, Registry<MobEffect> registry) {
        super(idFunction, registry);
    }

    public DelayedStatusEffectRegistrant(String modId) {
        this(modId, BuiltInRegistries.MOB_EFFECT);
    }

    public DelayedStatusEffectRegistrant(Function<String, ResourceLocation> idFunction) {
        this(idFunction, BuiltInRegistries.MOB_EFFECT);
    }

    public Holder.Reference<MobEffect> registerReference(String name, MobEffect effect) {
        return Registry.registerForHolder(this.registry, this.idFunction.apply(name), effect);
    }
}
