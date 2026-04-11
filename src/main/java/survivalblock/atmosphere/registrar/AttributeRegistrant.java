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
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Function;

@SuppressWarnings("unused")
public class AttributeRegistrant extends Registrant<Attribute> {
    protected AttributeRegistrant(String modId, Registry<Attribute> registry) {
        super(modId, registry);
    }

    protected AttributeRegistrant(Function<String, Identifier> idFunction, Registry<Attribute> registry) {
        super(idFunction, registry);
    }

    public AttributeRegistrant(String modId) {
        this(modId, BuiltInRegistries.ATTRIBUTE);
    }

    public AttributeRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.ATTRIBUTE);
    }

    public Holder.Reference<Attribute> registerReference(String name, Attribute attribute) {
        return Registry.registerForHolder(this.registry, this.idFunction.apply(name), attribute);
    }
}
