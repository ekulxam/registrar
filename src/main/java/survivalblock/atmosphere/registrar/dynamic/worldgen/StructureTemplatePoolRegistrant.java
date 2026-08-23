package survivalblock.atmosphere.registrar.dynamic.worldgen;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.ApiStatus;
import survivalblock.atmosphere.registrar.dynamic.DynamicRegistrant;
import survivalblock.atmosphere.registrar.mixin.SinglePoolElementAccessor;

import java.util.Optional;
import java.util.function.Function;

@ApiStatus.Experimental
@SuppressWarnings("unused")
public class StructureTemplatePoolRegistrant extends DynamicRegistrant<StructureTemplatePool> {
    protected StructureTemplatePoolRegistrant(String modId, ResourceKey<? extends Registry<StructureTemplatePool>> registry) {
        super(modId, registry);
    }

    protected StructureTemplatePoolRegistrant(Function<String, Identifier> idFunction, ResourceKey<? extends Registry<StructureTemplatePool>> registry) {
        super(idFunction, registry);
    }

    public StructureTemplatePoolRegistrant(String modId) {
        this(modId, Registries.TEMPLATE_POOL);
    }

    @SuppressWarnings("unused")
    public StructureTemplatePoolRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, Registries.TEMPLATE_POOL);
    }

    public ResourceKey<StructureTemplatePool> register(String path, Function<TemplatePoolCreator, StructureTemplatePool> function) {
        ResourceKey<StructureTemplatePool> key = this.register(path);
        this.maybeAdd(
                key,
                registerable -> {
                    TemplatePoolCreatorImpl creator = new TemplatePoolCreatorImpl(registerable);
                    return function.apply(creator);
                }
        );
        return key;
    }

    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public interface TemplatePoolCreator extends Creator<StructureTemplatePool> {
        default Holder<StructureTemplatePool> emptyFallback() {
            return this.fallback(ResourceKey.create(Registries.TEMPLATE_POOL, Identifier.withDefaultNamespace("empty")));
        }

        default Holder<StructureProcessorList> emptyProcessor() {
            return this.lookup(Registries.PROCESSOR_LIST).getOrThrow(ResourceKey.create(Registries.PROCESSOR_LIST, Identifier.withDefaultNamespace("empty")));
        }

        default Holder<StructureTemplatePool> fallback(ResourceKey<StructureTemplatePool> resourceKey) {
            return this.lookup(Registries.TEMPLATE_POOL).getOrThrow(resourceKey);
        }

        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        default SinglePoolElement singlePoolElement(
                final Either<Identifier, StructureTemplate> template,
                final Holder<StructureProcessorList> processors,
                final StructureTemplatePool.Projection projection,
                final Optional<LiquidSettings> overrideLiquidSettings
        ) {
            return SinglePoolElementAccessor.registrar$invokeInit(template, processors, projection, overrideLiquidSettings);
        }
    }

    public class TemplatePoolCreatorImpl extends CreatorImpl implements TemplatePoolCreator {
        public TemplatePoolCreatorImpl(BootstrapContext<StructureTemplatePool> registerable) {
            super(registerable);
        }
    }
}