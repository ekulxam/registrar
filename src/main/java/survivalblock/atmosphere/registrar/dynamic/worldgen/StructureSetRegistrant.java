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
package survivalblock.atmosphere.registrar.dynamic.worldgen;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.Nullable;
import survivalblock.atmosphere.registrar.annotation.AllowsForChaining;
import survivalblock.atmosphere.registrar.dynamic.DynamicRegistrant;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

@ApiStatus.Experimental
@SuppressWarnings("unused")
public class StructureSetRegistrant extends DynamicRegistrant<StructureSet> {
    protected StructureSetRegistrant(String modId, ResourceKey<? extends Registry<StructureSet>> registry) {
        super(modId, registry);
    }

    protected StructureSetRegistrant(Function<String, Identifier> idFunction, ResourceKey<? extends Registry<StructureSet>> registry) {
        super(idFunction, registry);
    }

    public StructureSetRegistrant(String modId) {
        this(modId, Registries.STRUCTURE_SET);
    }

    @SuppressWarnings("unused")
    public StructureSetRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, Registries.STRUCTURE_SET);
    }

    public ResourceKey<StructureSet> register(String path, Consumer<StructureSetCreator> consumer) {
        ResourceKey<StructureSet> key = this.register(path);
        this.maybeAdd(
                key,
                registerable -> {
                    StructureSetCreatorImpl creator = new StructureSetCreatorImpl(registerable);
                    consumer.accept(creator);
                    return creator.build(key);
                }
        );
        return key;
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public interface StructureSetCreator extends SingleObjectCreator<StructureSet> {
        default HolderGetter<Structure> structureLookup() {
            return this.lookup(Registries.STRUCTURE);
        }

        @AllowsForChaining
        StructureSetCreator entry(StructureSet.StructureSelectionEntry entry);

        @AllowsForChaining
        StructureSetCreator entry(ResourceKey<Structure> structure);

        @AllowsForChaining
        StructureSetCreator placement(StructurePlacement placement);

        @AllowsForChaining
        StructureSetCreator randomPlacement(int spacing, int separation, int salt);

        @ApiStatus.Experimental
        @Override
        void define(StructureSet structureSet);
    }

    public class StructureSetCreatorImpl extends SingleObjectCreatorImpl implements StructureSetCreator {
        protected final ImmutableList.Builder<StructureSet.StructureSelectionEntry> entryBuilder = ImmutableList.builder();
        @Nullable
        protected StructurePlacement placement = null;

        public StructureSetCreatorImpl(BootstrapContext<StructureSet> registerable) {
            super(registerable);
        }

        @Override
        public StructureSet build(ResourceKey<StructureSet> key) {
            if (this.obj != null) {
                return this.obj;
            }

            List<StructureSet.StructureSelectionEntry> entries = entryBuilder.build();
            if (entries.isEmpty()) {
                throw new IllegalStateException("Structure Set must have at least 1 entry!");
            }
            return new StructureSet(entries, Objects.requireNonNull(this.placement));
        }

        @Override
        public StructureSetCreator entry(StructureSet.StructureSelectionEntry entry) {
            this.entryBuilder.add(entry);
            return this;
        }

        @Override
        public StructureSetCreator entry(ResourceKey<Structure> structure) {
            this.entryBuilder.add(new StructureSet.StructureSelectionEntry(this.structureLookup().getOrThrow(structure), 1));
            return this;
        }

        @Override
        public StructureSetCreator placement(StructurePlacement placement) {
            if (this.placement != null) {
                throw new IllegalStateException("Structure Placement was already defined!");
            }
            this.placement = placement;
            return this;
        }

        @Override
        public StructureSetCreator randomPlacement(int spacing, int separation, int salt) {
            return this.placement(new RandomSpreadStructurePlacement(spacing, separation, RandomSpreadType.LINEAR, salt));
        }

        @ApiStatus.Experimental
        @Override
        public void define(StructureSet obj) {
            super.define(obj);
        }
    }
}