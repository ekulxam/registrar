package survivalblock.atmosphere.registrar.shared;

import net.minecraft.core.component.DataComponentType;

public interface IDataComponentTypeRegistrant extends IRegistrant<DataComponentType<?>> {
    default <T> DataComponentType<T> register(String name, DataComponentType.Builder<T> builder) {
        return this.register(name, builder.build());
    }
}
