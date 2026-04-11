/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

@SuppressWarnings("unused")
public class ParticleTypeRegistrant extends Registrant<ParticleType<?>> {
    protected ParticleTypeRegistrant(String modId, Registry<ParticleType<?>> registry) {
        super(modId, registry);
    }

    protected ParticleTypeRegistrant(Function<String, Identifier> idFunction, Registry<ParticleType<?>> registry) {
        super(idFunction, registry);
    }

    public ParticleTypeRegistrant(String modId) {
        this(modId, BuiltInRegistries.PARTICLE_TYPE);
    }

    public ParticleTypeRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.PARTICLE_TYPE);
    }
}
