/*
 * All Rights Reserved
 *
 * Copyright (c) 2024-present ekulxam
 */
package survivalblock.atmosphere.registrar.dynamic;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.Nullable;
import survivalblock.atmosphere.atmospheric_api.not_mixin.funny.AllowsForChaining;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unused")
public class EnchantmentRegistrant extends DynamicRegistrant<Enchantment> {
    protected EnchantmentRegistrant(String modId, ResourceKey<? extends Registry<Enchantment>> registry) {
        super(modId, registry);
    }

    protected EnchantmentRegistrant(Function<String, ResourceLocation> idFunction, ResourceKey<? extends Registry<Enchantment>> registry) {
        super(idFunction, registry);
    }

    public EnchantmentRegistrant(String modId) {
        this(modId, Registries.ENCHANTMENT);
    }

    public EnchantmentRegistrant(Function<String, ResourceLocation> idFunction) {
        this(idFunction, Registries.ENCHANTMENT);
    }

    public ResourceKey<Enchantment> register(String path, Consumer<EnchantmentCreator> consumer) {
        ResourceKey<Enchantment> key = this.register(path);
        this.maybeAdd(
                key,
                registerable -> {
                    EnchantmentCreatorImpl creator = new EnchantmentCreatorImpl(registerable);
                    consumer.accept(creator);
                    return creator.build(key);
                }
        );
        return key;
    }

    @SuppressWarnings("UnusedReturnValue")
    public interface EnchantmentCreator extends Creator<Enchantment> {

        default HolderGetter<Item> itemLookup() {
            return this.lookup(Registries.ITEM);
        }

        default HolderGetter<Enchantment> enchantmentLookup() {
            return this.lookup(Registries.ENCHANTMENT);
        }

        @AllowsForChaining
        EnchantmentCreator define(Enchantment.EnchantmentDefinition definition);

        default HolderSet<Item> supportedItems(TagKey<Item> tag) {
            return this.itemLookup().getOrThrow(tag);
        }

        @AllowsForChaining
        default EnchantmentCreator exclusiveSet(HolderSet<Enchantment> exclusiveSet) {
            return this.modify(builder -> builder.exclusiveWith(exclusiveSet));
        }

        @AllowsForChaining
        default EnchantmentCreator exclusiveSet(TagKey<Enchantment> tag) {
            return this.exclusiveSet(this.enchantmentLookup().getOrThrow(tag));
        }

        @AllowsForChaining
        EnchantmentCreator modify(UnaryOperator<Enchantment.Builder> unaryOperator);
    }

    public class EnchantmentCreatorImpl extends CreatorImpl implements EnchantmentCreator {
        @Nullable
        private Enchantment.Builder builder = null;

        public EnchantmentCreatorImpl(BootstrapContext<Enchantment> registerable) {
            super(registerable);
        }

        @Override
        public EnchantmentCreator define(Enchantment.EnchantmentDefinition definition) {
            if (this.builder != null) {
                throw new IllegalStateException("Builder is already defined!");
            }
            this.builder = Enchantment.enchantment(definition);
            return this;
        }
        
        @Override
        public EnchantmentCreator modify(UnaryOperator<Enchantment.Builder> unaryOperator) {
            unaryOperator.apply(Objects.requireNonNull(this.builder));
            return this;
        }

        @Override
        public Enchantment build(ResourceKey<Enchantment> key) {
            return Objects.requireNonNull(this.builder).build(key.location());
        }
    }
}
