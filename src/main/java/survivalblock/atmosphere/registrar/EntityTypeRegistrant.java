/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
//? if >=1.21.2
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

import java.util.function.Function;

@SuppressWarnings("unused")
public class EntityTypeRegistrant extends Registrant<EntityType<?>> {
    protected EntityTypeRegistrant(String modId, Registry<EntityType<?>> registry) {
        super(modId, registry);
    }

    protected EntityTypeRegistrant(Function<String, Identifier> idFunction, Registry<EntityType<?>> registry) {
        super(idFunction, registry);
    }

    public EntityTypeRegistrant(String modId) {
        this(modId, BuiltInRegistries.ENTITY_TYPE);
    }

    public EntityTypeRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.ENTITY_TYPE);
    }

    public <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        return this.register(name, builder.build(/*? >=1.21.2 {*/ResourceKey.create(this.registry.key(), this.idFunction.apply(name))/*?}*/));
    }

    public <T extends Entity> EntityType<T> register(String name, EntityType<T> entityType) {
        return super.register(name, entityType);
    }
}
