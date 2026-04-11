/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar.dynamic;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.damagesource.DamageType;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DamageTypeRegistrant extends DynamicRegistrant<DamageType> {
    protected DamageTypeRegistrant(String modId, ResourceKey<? extends Registry<DamageType>> registry) {
        super(modId, registry);
    }

    protected DamageTypeRegistrant(Function<String, Identifier> idFunction, ResourceKey<? extends Registry<DamageType>> registry) {
        super(idFunction, registry);
    }

    public DamageTypeRegistrant(String modId) {
        this(modId, Registries.DAMAGE_TYPE);
    }

    public DamageTypeRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, Registries.DAMAGE_TYPE);
    }

    public ResourceKey<DamageType> register(String path, String message) {
        return this.register(path, message, 0.1F);
    }

    public ResourceKey<DamageType> register(String path, String message, float exhaustion) {
        return this.register(path, new DamageType(message, exhaustion));
    }
}
