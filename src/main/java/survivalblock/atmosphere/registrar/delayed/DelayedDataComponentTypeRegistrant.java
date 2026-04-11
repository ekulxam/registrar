/*
 * All Rights Reserved
 *
 * Copyright (c) 2025-present ekulxam
 */
package survivalblock.atmosphere.registrar.delayed;

import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedDataComponentTypeRegistrant extends DelayedRegistrant<DataComponentType<?>> {
    protected DelayedDataComponentTypeRegistrant(String modId, Registry<DataComponentType<?>> registry) {
        super(modId, registry);
    }

    protected DelayedDataComponentTypeRegistrant(Function<String, Identifier> idFunction, Registry<DataComponentType<?>> registry) {
        super(idFunction, registry);
    }

    public DelayedDataComponentTypeRegistrant(String modId) {
        this(modId, BuiltInRegistries.DATA_COMPONENT_TYPE);
    }

    public DelayedDataComponentTypeRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.DATA_COMPONENT_TYPE);
    }

    public <T> DataComponentType<T> register(String name, DataComponentType.Builder<T> builder) {
        return this.register(name, builder.build());
    }

    public <T> DataComponentType<T> register(String name, DataComponentType<T> componentType) {
        return super.register(name, componentType);
    }
}
