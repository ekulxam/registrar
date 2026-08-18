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
package survivalblock.atmosphere.registrar;

//? if <1.21.11 {
/*import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
*///?}
import net.minecraft.core.Registry;
//? if >=1.21.11
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
//? if <1.21.11 {
/*import net.minecraft.world.level.GameRules;
*///?} else {
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
//?}
import survivalblock.atmosphere.registrar.shared.IGameRuleRegistrant;

import java.util.function.Function;

/**
 * A {@link Registrant} for game rules. Note that before 1.21.11, the {@link #registry} is null because it doesn't exist.
 */
@SuppressWarnings("unused")
//~ if >=1.21.11 'GameRules.Key<?>' -> 'GameRule<?>' {
public class GameRuleRegistrant extends Registrant<GameRule<?>> implements IGameRuleRegistrant {
    protected GameRuleRegistrant(String modId, Registry<GameRule<?>> registry) {
        super(modId, registry);
    }

    protected GameRuleRegistrant(Function<String, Identifier> idFunction, Registry<GameRule<?>> registry) {
        super(idFunction, registry);
    }
//~}

    //~ if >=1.21.11 ', null' -> ', BuiltInRegistries.GAME_RULE' {
    public GameRuleRegistrant(String modId) {
        this(modId, BuiltInRegistries.GAME_RULE);
    }

    public GameRuleRegistrant(Function<String, Identifier> idFunction) {
        this(idFunction, BuiltInRegistries.GAME_RULE);
    }
    //~}

    //~ if >=1.21.11 'GameRules.Key<' -> 'GameRule<' {
    //~ if >=1.21.11 'GameRules.BooleanValue>' -> 'Boolean>' {
    public static boolean getBoolean(GameRules gameRules, GameRule<Boolean> booleanRule) {
        return IGameRuleRegistrant.getBoolean(gameRules, booleanRule);
    }
    //~}

    //~ if >=1.21.11 'GameRules.IntegerValue>' -> 'Integer>' {
    public static int getInteger(GameRules gameRules, GameRule<Integer> integerRule) {
        return IGameRuleRegistrant.getInteger(gameRules, integerRule);
    }
    //~}

    //~ if >=1.21.11 'DoubleRule>' -> 'Double>' {
    public static double getDouble(GameRules gameRules, GameRule<Double> doubleRule) {
        return IGameRuleRegistrant.getDouble(gameRules, doubleRule);
    }
    //~}

    public static <E extends Enum<E>> E getEnum(GameRules gameRules, GameRule</*? <1.21.11 {*/ /*EnumRule<E> *//*?} else {*/ E /*?}*/> enumRule) {
        return IGameRuleRegistrant.getEnum(gameRules, enumRule);
    }

    static <T /*? <1.21.11 {*/ /*extends GameRules.Value<T> *//*?}*/> T getValue(GameRules gameRules, GameRule<T> rule) {
        return IGameRuleRegistrant.getValue(gameRules, rule);
    }
    //~}
}
