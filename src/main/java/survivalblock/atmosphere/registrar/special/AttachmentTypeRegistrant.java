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
package survivalblock.atmosphere.registrar.special;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unused")
public class AttachmentTypeRegistrant extends SpecialRegistrant {
    public AttachmentTypeRegistrant(String modId) {
        super(modId);
    }

    public AttachmentTypeRegistrant(Function<String, Identifier> idFunction) {
        super(idFunction);
    }

    public <A> AttachmentType<A> register(String name, AttachmentRegistry.Builder<A> builder) {
        return builder.buildAndRegister(this.idFunction.apply(name));
    }

    public <A> AttachmentType<A> register(String name, Consumer<AttachmentRegistry.Builder<A>> consumer) {
        return AttachmentRegistry.create(this.idFunction.apply(name), consumer);
    }

    // defaulted
    public <A> AttachmentType<A> register(String name, A defaultValue) {
        return this.register(name, defaultValue, null);
    }

    public <A> AttachmentType<A> register(String name, A defaultValue, boolean copyOnDeath) {
        return this.register(name, defaultValue, null, copyOnDeath);
    }

    // defaulted with codec only
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable Codec<A> codec) {
        return this.register(name, defaultValue, codec, false);
    }

    // defaulted with codec and death persistence
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable Codec<A> codec, boolean copyOnDeath) {
        return this.register(name, defaultValue, codec, null, null, copyOnDeath);
    }

    // defaulted with packet codec only
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, A> packetCodec, @Nullable AttachmentSyncPredicate syncPredicate) {
        return this.register(name, defaultValue, null, packetCodec, syncPredicate, null, false);
    }

    // defaulted with packet codec and max sync size
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, A> packetCodec, @Nullable AttachmentSyncPredicate syncPredicate, @Nullable Integer maxSyncSize) {
        return this.register(name, defaultValue, null, packetCodec, syncPredicate, maxSyncSize, false);
    }

    // defaulted with packet codec and death persistence
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, A> packetCodec, @Nullable AttachmentSyncPredicate syncPredicate, boolean copyOnDeath) {
        return this.register(name, defaultValue, null, packetCodec, syncPredicate, null, copyOnDeath);
    }

    // defaulted with packet codec, max sync size, and death persistence
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, A> packetCodec, @Nullable AttachmentSyncPredicate syncPredicate, @Nullable Integer maxSyncSize, boolean copyOnDeath) {
        return this.register(name, defaultValue, null, packetCodec, syncPredicate, null, copyOnDeath);
    }

    // defaulted with codec and packet codec
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable Codec<A> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, A> packetCodec, @Nullable AttachmentSyncPredicate syncPredicate) {
        return this.register(name, defaultValue, codec, packetCodec, syncPredicate, null, false);
    }

    // defaulted with codec, packet codec, and max sync size
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable Codec<A> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, A> packetCodec, @Nullable AttachmentSyncPredicate syncPredicate, @Nullable Integer maxSyncSize) {
        return this.register(name, defaultValue, codec, packetCodec, syncPredicate, maxSyncSize, false);
    }

    // defaulted with codec, packet codec, and death persistence
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable Codec<A> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, A> packetCodec, @Nullable AttachmentSyncPredicate syncPredicate, boolean copyOnDeath) {
        return this.register(name, defaultValue, codec, packetCodec, syncPredicate, null, copyOnDeath);
    }

    // all
    public <A> AttachmentType<A> register(String name, A defaultValue, @Nullable Codec<A> codec, @Nullable StreamCodec<? super RegistryFriendlyByteBuf, A> packetCodec, @Nullable AttachmentSyncPredicate syncPredicate, @Nullable Integer maxSyncSize, boolean copyOnDeath) {
        return this.register(name, builder -> {
            builder.initializer(() -> defaultValue);
            if (codec != null) {
                builder.persistent(codec);
            }
            if (packetCodec != null) {
                if (maxSyncSize != null) {
                    builder.syncWith(packetCodec, Objects.requireNonNull(syncPredicate), maxSyncSize);
                } else {
                    builder.syncWith(packetCodec, Objects.requireNonNull(syncPredicate));
                }
            }
            if (copyOnDeath) {
                builder.copyOnDeath();
            }
        });
    }
}
