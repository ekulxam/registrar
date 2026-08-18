package survivalblock.atmosphere.registrar.shared;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

public interface IEntityTypeRegistrant extends IRegistrant<EntityType<?>> {
    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    default <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        return this.register(this.createKey(name), builder);
    }

    default <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType.Builder<T> builder) {
        return this.register(key, builder.build(/*? >=1.21.2 {*/key/*?}*/));
    }

    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    default <T extends Entity> EntityType<T> register(String name, EntityType<T> entityType) {
        return IRegistrant.super.register(name, entityType);
    }

    default <T extends Entity> EntityType<T> register(ResourceKey<EntityType<?>> key, EntityType<T> entityType) {
        return IRegistrant.super.register(key, entityType);
    }
}
