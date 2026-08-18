package survivalblock.atmosphere.registrar.shared;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Function;

public interface ISoundEventRegistrant extends IReferenceRegistrant<SoundEvent> {
    default SoundEvent register(String name) {
        return this.register(name, (Function<Identifier, SoundEvent>) SoundEvent::createVariableRangeEvent);
    }

    default SoundEvent register(String name, Function<Identifier, SoundEvent> soundEventFunction) {
        return this.register(name, soundEventFunction.apply(this.id(name)));
    }

    @SuppressWarnings("unused")
    default Holder.Reference<SoundEvent> registerReference(String name) {
        return this.registerReference(name, (Function<Identifier, SoundEvent>) SoundEvent::createVariableRangeEvent);
    }

    default Holder.Reference<SoundEvent> registerReference(String name, Function<Identifier, SoundEvent> soundEventFunction) {
        return this.registerReference(name, soundEventFunction.apply(this.id(name)));
    }
}
