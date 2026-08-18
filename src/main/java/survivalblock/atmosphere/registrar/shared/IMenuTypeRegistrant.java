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

import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public interface IMenuTypeRegistrant extends IRegistrant<MenuType<?>> {
    @SuppressWarnings("unused")
    default <T extends AbstractContainerMenu> MenuType<T> registerSimple(String name, MenuType.MenuSupplier<T> factory) {
        return this.register(name, factory, FeatureFlags.VANILLA_SET);
    }

    default <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> factory, FeatureFlag... flags) {
        return this.register(name, factory, FeatureFlags.REGISTRY.subset(flags));
    }

    default <T extends AbstractContainerMenu> MenuType<T> register(String name, MenuType.MenuSupplier<T> factory, FeatureFlagSet set) {
        return this.register(name, new MenuType<>(factory, set));
    }
}
