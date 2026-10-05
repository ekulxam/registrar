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
package survivalblock.atmosphere.registar_testmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import survivalblock.atmosphere.registar_testmod.init.*;
import survivalblock.atmosphere.registrar.wrapper.BlockPresenter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class RegistrarTestmod implements ModInitializer {
    public static final String MOD_ID = "registrar_testmod";
    public static final Logger LOGGER = LoggerFactory.getLogger("Registrar Testmod");

    @Override
    public void onInitialize() {
        TestStructures.init();
        TestBlocks.init();

        logAllOrSomething(TestBlocks.class);
        logAllOrSomething(ConstructBlockClassTest.class);
        logAllOrSomething(OohShinyBlocks.class);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void logAllOrSomething(Class<?> clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            try {
                if (!Modifier.isStatic(field.getModifiers())) {
                    continue;
                }
                field.setAccessible(true);
                Object obj = field.get(null);
                if (obj instanceof Block block) {
                    LOGGER.info("Registered Block \"{}\" from field {} in class {}", BuiltInRegistries.BLOCK.getKey(block), field.getName(), clazz.getName());
                } else if (obj instanceof BlockPresenter<?> blockPresenter) {
                    Block block = blockPresenter.getBlock();
                    LOGGER.info("Registered BlockPresenter \"{}\" from field {} in class {}", BuiltInRegistries.BLOCK.getKey(block), field.getName(), clazz.getName());
                    Item possibility = blockPresenter.asItem();
                    if (possibility != Items.AIR) {
                        LOGGER.info("Registered BlockPresented Item \"{}\" from field {} in class {}", BuiltInRegistries.ITEM.getKey(possibility), field.getName(), clazz.getName());
                    }
                } else if (obj instanceof Item item) {
                    LOGGER.info("Registered Item \"{}\" from field {} in class {}", BuiltInRegistries.ITEM.getKey(item), field.getName(), clazz.getName());
                }
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
