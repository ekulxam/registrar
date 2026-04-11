/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.alchemy.Potion;

import java.util.function.Function;

@SuppressWarnings("unused")
public class PotionRegistrant extends Registrant<Potion> {
    protected PotionRegistrant(String modId, Registry<Potion> registry) {
        super(modId, registry);
    }

    protected PotionRegistrant(Function<String, ResourceLocation> idFunction, Registry<Potion> registry) {
        super(idFunction, registry);
    }

    public PotionRegistrant(String modId) {
        this(modId, BuiltInRegistries.POTION);
    }

    public PotionRegistrant(Function<String, ResourceLocation> idFunction) {
        this(idFunction, BuiltInRegistries.POTION);
    }

    public Holder.Reference<Potion> registerReference(String name, Potion potion) {
        return Registry.registerForHolder(this.registry, this.idFunction.apply(name), potion);
    }
}
