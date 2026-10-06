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
//? if >=1.21.11 {
package survivalblock.atmosphere.registrar.delayed;

//? if <26
//import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRules;
import org.jspecify.annotations.Nullable;
import survivalblock.atmosphere.registrar.shared.IGameRuleRegistrant;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedGameRuleRegistrant extends DelayedRegistrant<GameRule<?>> implements IGameRuleRegistrant {
    protected @Nullable GameRuleCategory defaultCategory;
    //? if <26
    //protected @Nullable CustomGameRuleCategory defaultCustomCategory;

    protected DelayedGameRuleRegistrant(String modId, Registry<GameRule<?>> registry) {
        super(modId, registry);
    }

    protected DelayedGameRuleRegistrant(Function<String, Identifier> idFunction, Registry<GameRule<?>> registry) {
        super(idFunction, registry);
    }

    public DelayedGameRuleRegistrant(String modId) {
        this(modId, BuiltInRegistries.GAME_RULE);
    }

    public DelayedGameRuleRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.GAME_RULE);
    }

    @Override
    public @Nullable GameRuleCategory getDefaultCategory() {
        return this.defaultCategory;
    }

    @Override
    public DelayedGameRuleRegistrant specifyDefaultCategory(@Nullable GameRuleCategory category) {
        IGameRuleRegistrant.super.specifyDefaultCategory(category);
        this.defaultCategory = category;
        return this;
    }

    //? if <26 {
    /*@Override
    public @Nullable CustomGameRuleCategory getDefaultCustomCategory() {
        return this.defaultCustomCategory;
    }

    @Override
    public DelayedGameRuleRegistrant specifyDefaultCategory(@Nullable CustomGameRuleCategory category) {
        IGameRuleRegistrant.super.specifyDefaultCategory(category);
        this.defaultCustomCategory = category;
        return this;
    }
    *///?}

    public static boolean getBoolean(GameRules gameRules, GameRule<Boolean> booleanRule) {
        return IGameRuleRegistrant.getBoolean(gameRules, booleanRule);
    }

    public static int getInteger(GameRules gameRules, GameRule<Integer> integerRule) {
        return IGameRuleRegistrant.getInteger(gameRules, integerRule);
    }

    public static double getDouble(GameRules gameRules, GameRule<Double> doubleRule) {
        return IGameRuleRegistrant.getDouble(gameRules, doubleRule);
    }
    
    public static <E extends Enum<E>> E getEnum(GameRules gameRules, GameRule<E> enumRule) {
        return IGameRuleRegistrant.getEnum(gameRules, enumRule);
    }

    public static <T> T getValue(GameRules gameRules, GameRule<T> rule) {
        return IGameRuleRegistrant.getValue(gameRules, rule);
    }
}
//?}