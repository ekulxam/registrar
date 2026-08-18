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
package survivalblock.atmosphere.registrar.annotation;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotated on {@link net.minecraft.world.level.block.Block}s (<26.2) and
 * {@link net.minecraft.references.BlockItemId}s (>=26.2) for registration via reflection.
 * @see survivalblock.atmosphere.registrar.ItemRegistrant#registerFromAnnotations(Class, boolean)
 * @see survivalblock.atmosphere.registrar.delayed.DelayedItemRegistrant#registerFromAnnotations(Class, boolean)
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConstructItem {
    //? if >1.21.1 {
    /**
     * @see net.minecraft.world.item.Item.Properties#useBlockDescriptionPrefix()
     */
    boolean useBlockTranslation() default true;
    //?}

    /**
     *
     * @return a class with constructor that takes a {@link net.minecraft.world.level.block.Block} and a {@link net.minecraft.world.item.Item.Properties}
     */
    Class<? extends Item> constructor() default BlockItem.class;

    /**
     * @return whether this block should be excluded from item registration
     */
    boolean exclude() default false;

    //? if >=26.2 {
    @Deprecated
    boolean suppressIdWarnings() default false;
    //?}
}
