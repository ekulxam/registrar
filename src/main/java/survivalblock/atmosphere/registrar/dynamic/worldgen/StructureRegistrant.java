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

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.jetbrains.annotations.ApiStatus;
import survivalblock.atmosphere.registrar.dynamic.DynamicRegistrant;

import java.util.function.Consumer;
import java.util.function.Function;

@ApiStatus.Experimental
@SuppressWarnings("unused")
public class StructureRegistrant extends DynamicRegistrant<Structure> {
    protected StructureRegistrant(String modId, ResourceKey<? extends Registry<Structure>> registry) {
        super(modId, registry);
    }

    protected StructureRegistrant(Function<String, Identifier> idFunction, ResourceKey<? extends Registry<Structure>> registry) {
        super(idFunction, registry);
    }

    public StructureRegistrant(String modId) {
        this(modId, Registries.STRUCTURE);
    }

    @SuppressWarnings("unused")
    public StructureRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, Registries.STRUCTURE);
    }

    public ResourceKey<Structure> register(String path, Function<StructureCreator, Structure> function) {
        ResourceKey<Structure> key = this.register(path);
        this.maybeAdd(
                key,
                registerable -> {
                    StructureCreatorImpl creator = new StructureCreatorImpl(registerable);
                    return function.apply(creator);
                }
        );
        return key;
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public interface StructureCreator extends Creator<Structure> {
        default Holder<StructureTemplatePool> templatePool(ResourceKey<StructureTemplatePool> resourceKey) {
            return this.lookup(Registries.TEMPLATE_POOL).getOrThrow(resourceKey);
        }

        default HolderSet<Biome> biomeTag(TagKey<Biome> tagKey) {
            return this.lookup(Registries.BIOME).getOrThrow(tagKey);
        }

        default ConstantHeight absoluteHeight(int y) {
            return ConstantHeight.of(VerticalAnchor.absolute(y));
        }

        default JigsawStructure.MaxDistance maxDistance(int distance) {
            return new JigsawStructure.MaxDistance(distance);
        }
    }

    public class StructureCreatorImpl extends SingleObjectCreatorImpl implements StructureCreator {
        public StructureCreatorImpl(BootstrapContext<Structure> registerable) {
            super(registerable);
        }
    }
}