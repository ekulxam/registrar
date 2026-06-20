/*
 * MIT License
 *
 * Copyright (c) 2025-present ekulxam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package survivalblock.atmosphere.registrar.delayed;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedSoundEventRegistrant extends DelayedRegistrant<SoundEvent> {
    protected DelayedSoundEventRegistrant(String modId, Registry<SoundEvent> registry) {
        super(modId, registry);
    }

    protected DelayedSoundEventRegistrant(Function<String, Identifier> idFunction, Registry<SoundEvent> registry) {
        super(idFunction, registry);
    }

    public DelayedSoundEventRegistrant(String modId) {
        this(modId, BuiltInRegistries.SOUND_EVENT);
    }

    public DelayedSoundEventRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.SOUND_EVENT);
    }

    public SoundEvent register(String name) {
        return this.register(name, (Function<Identifier, SoundEvent>) SoundEvent::createVariableRangeEvent);
    }

    public SoundEvent register(String name, Function<Identifier, SoundEvent> soundEventFunction) {
        return this.register(name, soundEventFunction.apply(this.idFunction.apply(name)));
    }

    public Holder.Reference<SoundEvent> registerReference(String name) {
        return this.registerReference(name, SoundEvent::createVariableRangeEvent);
    }

    public Holder.Reference<SoundEvent> registerReference(String name, Function<Identifier, SoundEvent> soundEventFunction) {
        return this.registerReference(name, soundEventFunction.apply(this.idFunction.apply(name)));
    }

    public Holder.Reference<SoundEvent> registerReference(String name, SoundEvent soundEvent) {
        return Registry.registerForHolder(this.registry, this.idFunction.apply(name), soundEvent);
    }
}
