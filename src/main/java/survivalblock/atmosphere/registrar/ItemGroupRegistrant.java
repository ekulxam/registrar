/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;

import java.util.function.Function;

@SuppressWarnings("unused")
public class ItemGroupRegistrant extends Registrant<CreativeModeTab> {
    protected ItemGroupRegistrant(String modId, Registry<CreativeModeTab> registry) {
        super(modId, registry);
    }

    protected ItemGroupRegistrant(Function<String, Identifier> idFunction, Registry<CreativeModeTab> registry) {
        super(idFunction, registry);
    }

    public ItemGroupRegistrant(String modId) {
        this(modId, BuiltInRegistries.CREATIVE_MODE_TAB);
    }

    public ItemGroupRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.CREATIVE_MODE_TAB);
    }

    public CreativeModeTab register(String name, CreativeModeTab.Builder builder) {
        return this.register(name, builder.build());
    }
}
