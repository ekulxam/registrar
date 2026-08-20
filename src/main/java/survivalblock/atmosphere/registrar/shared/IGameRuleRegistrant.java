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

//? if >=1.21.11
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
//? if <26
//import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
//? if <1.21.11 {
/*import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.GameRules;
*///?} else {
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import net.minecraft.world.level.gamerules.GameRules;
//?}

@SuppressWarnings("unused")
//~ if >=1.21.11 'GameRules.Key<?>' -> 'GameRule<?>'
public interface IGameRuleRegistrant extends IRegistrant<GameRule<?>> {
    //~ if >=1.21.11 'GameRules.Key<T>' -> 'GameRule<T>' {
    //~ if >=1.21.11 'GameRules.Type<T> type' -> 'GameRuleBuilder<T> builder' {
    default <T /*? <1.21.11 {*/ /*extends GameRules.Value<T> *//*?}*/> GameRule<T> register(String name, GameRuleBuilder<T> builder) {
    //~}
    //~}
        //? <1.21.11
        //return this.register(name, GameRules.Category.MISC, type);
        //? >=1.21.11
        return this.register(name, builder.build());
    }

    //? if <1.21.11 {
    /*@Override
    default ResourceKey<GameRules.Key<?>> createKey(String name) {
        throw new UnsupportedOperationException("Not allowed before 1.21.11!");
    }

    @Override
    default <U extends GameRules.Key<?>> U register(String name, U obj) {
        throw new UnsupportedOperationException("Not allowed before 1.21.11!");
    }

    @Override
    default <U extends GameRules.Key<?>> U register(ResourceKey<GameRules.Key<?>> key, U obj) {
        throw new UnsupportedOperationException("Not allowed before 1.21.11!");
    }

    default GameRules.Key<GameRules.BooleanValue> registerBoolean(String name, boolean defaultValue) {
        return this.register(name, GameRuleFactory.createBooleanRule(defaultValue));
    }

    default GameRules.Key<GameRules.BooleanValue> registerBoolean(String name, GameRules.Category category, boolean defaultValue) {
        return this.register(name, category, GameRuleFactory.createBooleanRule(defaultValue));
    }

    default GameRules.Key<GameRules.BooleanValue> registerBoolean(String name, CustomGameRuleCategory category, boolean defaultValue) {
        return this.register(name, category, GameRuleFactory.createBooleanRule(defaultValue));
    }

    default GameRules.Key<DoubleRule> registerDouble(String name, double defaultValue) {
        return this.register(name, GameRuleFactory.createDoubleRule(defaultValue));
    }

    default GameRules.Key<DoubleRule> registerDouble(String name, GameRules.Category category, double defaultValue) {
        return this.register(name, category, GameRuleFactory.createDoubleRule(defaultValue));
    }

    default GameRules.Key<DoubleRule> registerDouble(String name, CustomGameRuleCategory category, double defaultValue) {
        return this.register(name, category, GameRuleFactory.createDoubleRule(defaultValue));
    }

    default GameRules.Key<DoubleRule> registerDouble(String name, double defaultValue, double min, double max) {
        return this.register(name, GameRuleFactory.createDoubleRule(defaultValue, min, max));
    }

    default GameRules.Key<DoubleRule> registerDouble(String name, GameRules.Category category, double defaultValue, double min, double max) {
        return this.register(name, category, GameRuleFactory.createDoubleRule(defaultValue, min, max));
    }

    default GameRules.Key<DoubleRule> registerDouble(String name, CustomGameRuleCategory category, double defaultValue, double min, double max) {
        return this.register(name, category, GameRuleFactory.createDoubleRule(defaultValue, min, max));
    }

    default <E extends Enum<E>> GameRules.Key<EnumRule<E>> registerEnum(String name, E defaultValue) {
        return this.register(name, GameRuleFactory.createEnumRule(defaultValue));
    }

    default <E extends Enum<E>> GameRules.Key<EnumRule<E>> registerEnum(String name, CustomGameRuleCategory category, E defaultValue) {
        return this.register(name, category, GameRuleFactory.createEnumRule(defaultValue));
    }

    default <E extends Enum<E>> GameRules.Key<EnumRule<E>> registerEnum(String name, GameRules.Category category, E defaultValue) {
        return this.register(name, category, GameRuleFactory.createEnumRule(defaultValue));
    }

    @SuppressWarnings("unchecked")
    default <E extends Enum<E>> GameRules.Key<EnumRule<E>> registerEnum(String name, E defaultValue, E... supportedValues) {
        return this.register(name, GameRuleFactory.createEnumRule(defaultValue));
    }

    @SuppressWarnings("unchecked")
    default <E extends Enum<E>> GameRules.Key<EnumRule<E>> registerEnum(String name, CustomGameRuleCategory category, E defaultValue, E... supportedValues) {
        return this.register(name, category, GameRuleFactory.createEnumRule(defaultValue, supportedValues));
    }

    @SuppressWarnings("unchecked")
    default <E extends Enum<E>> GameRules.Key<EnumRule<E>> registerEnum(String name, GameRules.Category category, E defaultValue, E... supportedValues) {
        return this.register(name, category, GameRuleFactory.createEnumRule(defaultValue, supportedValues));
    }

    default <T extends GameRules.Value<T>> GameRules.Key<T> register(String name, GameRules.Category category, GameRules.Type<T> type) {
        return GameRuleRegistry.register(this.id(name).toString(), category, type);
    }

    default <T extends GameRules.Value<T>> GameRules.Key<T> register(String name, CustomGameRuleCategory category, GameRules.Type<T> type) {
        return GameRuleRegistry.register(this.id(name).toString(), category, type);
    }
    *///?} else {
    default GameRule<Boolean> registerBoolean(String name, boolean defaultValue) {
        return this.register(name, GameRuleBuilder.forBoolean(defaultValue));
    }

    default GameRule<Double> registerDouble(String name, double defaultValue) {
        return this.register(name, GameRuleBuilder.forDouble(defaultValue));
    }

    default GameRule<Double> registerDouble(String name, double defaultValue, double min, double max) {
        return this.register(name, GameRuleBuilder.forDouble(defaultValue).range(min, max));
    }

    default <E extends Enum<E>> GameRule<E> registerEnum(String name, E defaultValue) {
        return this.register(name, GameRuleBuilder.forEnum(defaultValue));
    }

    @SuppressWarnings("unchecked")
    default <E extends Enum<E>> GameRule<E> registerEnum(String name, E defaultValue, E... supportedValues) {
        return this.register(name, GameRuleBuilder.forEnum(defaultValue).supportedValues(supportedValues));
    }

    //? if <26 {
    /*default GameRule<Boolean> registerBoolean(String name, CustomGameRuleCategory category, boolean defaultValue) {
        return this.register(name, GameRuleBuilder.forBoolean(defaultValue).category(category));
    }

    default GameRule<Double> registerDouble(String name, CustomGameRuleCategory category, double defaultValue) {
        return this.register(name, GameRuleBuilder.forDouble(defaultValue).category(category));
    }

    default GameRule<Double> registerDouble(String name, CustomGameRuleCategory category, double defaultValue, double min, double max) {
        return this.register(name, GameRuleBuilder.forDouble(defaultValue).range(min, max).category(category));
    }

    default <E extends Enum<E>> GameRule<E> registerEnum(String name, CustomGameRuleCategory category, E defaultValue) {
        return this.register(name, GameRuleBuilder.forEnum(defaultValue).category(category));
    }

    @SuppressWarnings("unchecked")
    default <E extends Enum<E>> GameRule<E> registerEnum(String name, CustomGameRuleCategory category, E defaultValue, E... supportedValues) {
        return this.register(name, GameRuleBuilder.forEnum(defaultValue).supportedValues(supportedValues).category(category));
    }
    *///?}

    default GameRule<Boolean> registerBoolean(String name, GameRuleCategory category, boolean defaultValue) {
        return this.register(name, GameRuleBuilder.forBoolean(defaultValue).category(category));
    }

    default GameRule<Double> registerDouble(String name, GameRuleCategory category, double defaultValue) {
        return this.register(name, GameRuleBuilder.forDouble(defaultValue).category(category));
    }

    default GameRule<Double> registerDouble(String name, GameRuleCategory category, double defaultValue, double min, double max) {
        return this.register(name, GameRuleBuilder.forDouble(defaultValue).range(min, max).category(category));
    }

    default <E extends Enum<E>> GameRule<E> registerEnum(String name, GameRuleCategory category, E defaultValue) {
        return this.register(name, GameRuleBuilder.forEnum(defaultValue).category(category));
    }

    @SuppressWarnings("unchecked")
    default <E extends Enum<E>> GameRule<E> registerEnum(String name, GameRuleCategory category, E defaultValue, E... supportedValues) {
        return this.register(name, GameRuleBuilder.forEnum(defaultValue).supportedValues(supportedValues).category(category));
    }
    //?}

    //~ if >=1.21.11 'GameRules.Key<' -> 'GameRule<' {
    //~ if >=1.21.11 'GameRules.BooleanValue>' -> 'Boolean>' {
    static Boolean getBoolean(GameRules gameRules, GameRule<Boolean> booleanRule) {
        //~ if >=1.21.11 'getBoolean(booleanRule)' -> 'get(booleanRule)'
        return gameRules.get(booleanRule);
    }
    //~}

    //~ if >=1.21.11 'GameRules.IntegerValue>' -> 'Integer>' {
    static Integer getInteger(GameRules gameRules, GameRule<Integer> integerRule) {
        //~ if >=1.21.11 'getInt(integerRule)' -> 'get(integerRule)'
        return gameRules.get(integerRule);
    }
    //~}

    //~ if >=1.21.11 'DoubleRule>' -> 'Double>' {
    static Double getDouble(GameRules gameRules, GameRule<Double> doubleRule) {
        //~ if >=1.21.11 'getRule(doubleRule).get()' -> 'get(doubleRule)'
        return gameRules.get(doubleRule);
    }
    //~}

    static <E extends Enum<E>> E getEnum(GameRules gameRules, GameRule</*? <1.21.11 {*/ /*EnumRule<E> *//*?} else {*/ E /*?}*/> enumRule) {
        //~ if >=1.21.11 'getRule(enumRule).get()' -> 'get(enumRule)'
        return gameRules.get(enumRule);
    }

    static <T /*? <1.21.11 {*/ /*extends GameRules.Value<T> *//*?}*/> T getValue(GameRules gameRules, GameRule<T> rule) {
        //~ if >=1.21.11 'getRule(rule)' -> 'get(rule)'
        return gameRules.get(rule);
    }
    //~}
}
