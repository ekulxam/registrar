package survivalblock.atmosphere.registrar.shared;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface IReferenceRegistrant<T> extends IRegistrant<T> {
    default <U extends T> Holder.Reference<T> registerReference(String name, U obj) {
        return Registry.registerForHolder(this.getRegistry(), this.id(name), obj);
    }

    default <U extends T> Holder.Reference<T> registerReference(ResourceKey<T> key, U obj) {
        return Registry.registerForHolder(this.getRegistry(), key, obj);
    }
}
