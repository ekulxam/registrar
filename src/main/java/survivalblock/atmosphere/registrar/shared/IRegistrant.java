package survivalblock.atmosphere.registrar.shared;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public interface IRegistrant<T> {
    default Registry<T> getRegistry() {
        throw new UnsupportedOperationException();
    }

    default Identifier id(String name) {
        throw new UnsupportedOperationException();
    }

    default ResourceKey<T> createKey(String name) {
        return ResourceKey.create(this.getRegistry().key(), this.id(name));
    }

    default <U extends T> U register(String name, U obj) {
        return this.register(this.createKey(name), obj);
    }

    default <U extends T> U register(ResourceKey<T> key, U obj) {
        return Registry.register(this.getRegistry(), key, obj);
    }
}
