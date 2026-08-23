package survivalblock.atmosphere.registrar.dynamic;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;

public interface ContextExposing<T> {
    @SuppressWarnings("unchecked")
    default ResourceKey<T> register(String path, Function<BootstrapContext<T>, T> objCreator) {
        ResourceKey<T> key = ((DynamicRegistrant<T>) this).register(path);
        ((DynamicRegistrant<T>) this).maybeAdd(key, objCreator);
        return key;
    }
}
