/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar.delayed;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedParticleTypeRegistrant extends DelayedRegistrant<ParticleType<?>> {
    protected DelayedParticleTypeRegistrant(String modId, Registry<ParticleType<?>> registry) {
        super(modId, registry);
    }

    protected DelayedParticleTypeRegistrant(Function<String, ResourceLocation> idFunction, Registry<ParticleType<?>> registry) {
        super(idFunction, registry);
    }

    public DelayedParticleTypeRegistrant(String modId) {
        this(modId, BuiltInRegistries.PARTICLE_TYPE);
    }

    public DelayedParticleTypeRegistrant(Function<String, ResourceLocation> idFunction) {
        this(idFunction, BuiltInRegistries.PARTICLE_TYPE);
    }
}
