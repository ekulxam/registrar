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
package survivalblock.atmosphere.registar_testmod.init;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.jetbrains.annotations.ApiStatus;
import survivalblock.atmosphere.registar_testmod.RegistrarTestmod;
import survivalblock.atmosphere.registrar.dynamic.worldgen.StructureRegistrant;
import survivalblock.atmosphere.registrar.dynamic.worldgen.StructureSetRegistrant;
import survivalblock.atmosphere.registrar.dynamic.worldgen.StructureTemplatePoolRegistrant;

import java.util.List;
import java.util.Optional;

@ApiStatus.NonExtendable
@SuppressWarnings("unused")
public interface TestStructures {
    StructureTemplatePoolRegistrant TEMPLATE_POOL_REGISTRANT = new StructureTemplatePoolRegistrant(RegistrarTestmod.MOD_ID);
    StructureRegistrant STRUCTURE_REGISTRANT = new StructureRegistrant(RegistrarTestmod.MOD_ID);
    StructureSetRegistrant STRUCTURE_SET_REGISTRANT = new StructureSetRegistrant(RegistrarTestmod.MOD_ID);

    ResourceKey<StructureTemplatePool> TEST_TEMPLATE_POOL = TEMPLATE_POOL_REGISTRANT.register(
            "test",
            creator -> new StructureTemplatePool(
                    creator.emptyFallback(),
                    List.of(Pair.of(
                            creator.singlePoolElement(
                                    Either.left(RegistrarTestmod.id("test")),
                                    creator.emptyProcessor(),
                                    StructureTemplatePool.Projection.RIGID,
                                    Optional.empty()
                            ),
                            1
                    ))
            )
    );

    ResourceKey<Structure> TEST_STRUCTURE = STRUCTURE_REGISTRANT.register(
            "test",
            creator -> new JigsawStructure(
                    new Structure.StructureSettings(
                            creator.biomeTag(BiomeTags.MINESHAFT_BLOCKING)
                    ),
                    creator.templatePool(TEST_TEMPLATE_POOL),
                    Optional.empty(),
                    1,
                    creator.absoluteHeight(0),
                    false,
                    Optional.empty(),
                    creator.maxDistance(1),
                    List.of(),
                    JigsawStructure.DEFAULT_DIMENSION_PADDING,
                    JigsawStructure.DEFAULT_LIQUID_SETTINGS
            )
    );

    ResourceKey<StructureSet> TEST_STRUCTURE_SET = STRUCTURE_SET_REGISTRANT.register(
            "test",
            creator -> creator.entry(TEST_STRUCTURE)
                    .randomPlacement(1, 1, 1)
    );

    static void init() {
        // NO-OP
    }
}
