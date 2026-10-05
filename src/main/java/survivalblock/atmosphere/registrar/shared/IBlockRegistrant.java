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
package survivalblock.atmosphere.registrar.shared;

//? if >=26.2
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jspecify.annotations.Nullable;
import survivalblock.atmosphere.registrar.Registrant;
import survivalblock.atmosphere.registrar.annotation.ConstructBlock;
import survivalblock.atmosphere.registrar.annotation.ConstructItem;
import survivalblock.atmosphere.registrar.wrapper.BlockPresenter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;
import java.util.function.Function;

public interface IBlockRegistrant extends IRegistrant<Block> {
    //? if >=26.2
    @Deprecated(since = "Minecraft 26.2")
    default <T extends Block, S extends BlockBehaviour.Properties> T register(String name, Function<S, T> blockFunction, S settings) {
        return this.register(this.createKey(name), blockFunction, settings);
    }

    //? if >=26.2 {
    default <T extends Block, S extends BlockBehaviour.Properties> T register(BlockItemId blockItemId, Function<S, T> blockFunction, S settings) {
        return this.register(blockItemId.block(), blockFunction, settings);
    }
    //?}

    default <T extends Block, S extends BlockBehaviour.Properties> T register(ResourceKey<Block> key, Function<S, T> blockFunction, S settings) {
        T block = blockFunction.apply(/*? >=1.21.2 {*/(S)/*?}*/ settings /*? >=1.21.2 {*/.setId(key) /*?}*/);
        return this.register(key, block);
    }

    //? if >=26.2 {
    @SuppressWarnings("unused")
    default BlockItemId createId(String name) {
        Identifier id = this.id(name);
        return BlockItemId.create(id, id);
    }

    @SuppressWarnings("unused")
    default BlockItemId createId(String block, String item) {
        return BlockItemId.create(this.id(block), this.id(item));
    }
    //?}

    @SuppressWarnings("unused")
    @Nullable
    IItemRegistrant getItemRegistrant();

    IItemRegistrant getOrCreateItemRegistrant();

    default <T extends Block, S extends BlockBehaviour.Properties> T register(Function<S, T> blockFunction, S settings) {
        //~ if >=26.2 'registerAndGrabKey' -> 'registerAndGrabIds'
        return this.registerAndPresent(blockFunction, settings, Registrant.STACK_WALKER.getCallerClass()).getBlock();
    }

    default <T extends Block, S extends BlockBehaviour.Properties> BlockPresenter<T> registerAndPresent(Function<S, T> blockFunction, S settings) {
        return this.registerAndPresent(blockFunction, settings, Registrant.STACK_WALKER.getCallerClass());
    }

    @SuppressWarnings("unused")
    default <T extends Block, S extends BlockBehaviour.Properties> BlockPresenter<T> registerAndPresent(String name, Function<S, T> blockFunction, S settings) {
        return this.registerAndPresent(name, blockFunction, settings, tryGrabInitializingBlock(Registrant.STACK_WALKER.getCallerClass()));
    }

    default <T extends Block, S extends BlockBehaviour.Properties> BlockPresenter<T> registerAndPresent(String name, Function<S, T> blockFunction, S settings, Field field) {
        ConstructItem constructItem = field.getAnnotation(ConstructItem.class);
        boolean item = constructItem != null && !constructItem.exclude();
        //? if >=26.2
        BlockItemId ids = item ? this.createId(name) : null;
        ResourceKey<Block> key = this.createKey(name);
        T block;
        //? if >=26.2 {
        if (ids == null) {
            block = this.register(key, blockFunction, settings);
        } else {
            block = this.register(ids, blockFunction, settings);
        }
        //?} else {
        /*block = this.register(key, blockFunction, settings);
         *///?}
        if (item) {
            try {
                this.getOrCreateItemRegistrant().constructItem(/*? >1.21.1 {*/constructItem.useBlockTranslation(),/*?}*/ constructItem.constructor(), block/*? >=26.2 {*/, ids/*?}*/);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        return new BlockPresenter<>(block, key /*? >=26.2 {*/, ids/*?}*/);
    }

    default <T extends Block, S extends BlockBehaviour.Properties> BlockPresenter<T> registerAndPresent(Function<S, T> blockFunction, S settings, Class<?> callingClass) {
        Field field = tryGrabInitializingBlock(callingClass);
        String name = field.getName().toLowerCase(Locale.ROOT);
        return this.registerAndPresent(name, blockFunction, settings, field);
    }

    static Field tryGrabInitializingBlock(Class<?> clazz) {
        Field target = null;
        boolean allowByDefault = clazz.isAnnotationPresent(ConstructBlock.class);
        try {
            for (Field field : clazz.getDeclaredFields()) {
                if (allowByDefault) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        continue;
                    }
                } else if (!field.isAnnotationPresent(ConstructBlock.class)) {
                    continue;
                }
                Class<?> type = field.getType();
                if (!Block.class.isAssignableFrom(type) && !BlockPresenter.class.isAssignableFrom(type)) {
                    continue;
                }
                field.setAccessible(true);
                if (field.get(null) != null) {
                    continue;
                }
                target = field;
                break;
            }
            if (target == null) {
                throw new NoSuchFieldException("Field annotated with ConstructBlock was not found in class " + clazz.getName());
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
        return target;
    }
}
