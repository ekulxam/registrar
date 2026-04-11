/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar.delayed;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.alchemy.Potion;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedPotionRegistrant extends DelayedRegistrant<Potion> {
    protected DelayedPotionRegistrant(String modId, Registry<Potion> registry) {
        super(modId, registry);
    }

    protected DelayedPotionRegistrant(Function<String, Identifier> idFunction, Registry<Potion> registry) {
        super(idFunction, registry);
    }

    public DelayedPotionRegistrant(String modId) {
        this(modId, BuiltInRegistries.POTION);
    }

    public DelayedPotionRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.POTION);
    }

    public Holder.Reference<Potion> registerReference(String name, Potion potion) {
        return Registry.registerForHolder(this.registry, this.idFunction.apply(name), potion);
    }
}
