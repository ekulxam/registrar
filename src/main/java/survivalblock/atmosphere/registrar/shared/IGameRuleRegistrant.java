package survivalblock.atmosphere.registrar.shared;

//? if >=1.21.11 {
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
//?} else {
/*import net.fabricmc.fabric.api.gamerule.v1.CustomGameRuleCategory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.fabricmc.fabric.api.gamerule.v1.rule.DoubleRule;
import net.fabricmc.fabric.api.gamerule.v1.rule.EnumRule;
*///?}
import net.minecraft.core.Registry;
//? if >=1.21.11 {
import net.minecraft.core.registries.BuiltInRegistries;
//?} else {
/*import net.minecraft.resources.ResourceKey;
 *///?}
import net.minecraft.resources.Identifier;
//? if <1.21.11 {
/*import net.minecraft.world.level.GameRules;
 *///?} else {
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
//?}

//~ if >=1.21.11 'GameRules.Key<?>' -> 'GameRule<?>'
public interface IGameRuleRegistrant extends IRegistrant<GameRule<?>> {
    @SuppressWarnings("unused")
    //~ if >=1.21.11 'GameRules.Key<GameRules.BooleanValue>' -> 'GameRule<Boolean>'
    default GameRule<Boolean> registerBoolean(String name, boolean defaultValue) {
        //~ if >=1.21.11 'GameRuleFactory.createBooleanRule(' -> 'GameRuleBuilder.forBoolean('
        return this.register(name, GameRuleBuilder.forBoolean(defaultValue));
    }

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
    public ResourceKey<GameRules.Key<?>> createKey(String name) {
        throw new UnsupportedOperationException("Not allowed before 1.21.11!");
    }

    @Override
    public <U extends GameRules.Key<?>> U register(String name, U obj) {
        throw new UnsupportedOperationException("Not allowed before 1.21.11!");
    }

    @Override
    public <U extends GameRules.Key<?>> U register(ResourceKey<GameRules.Key<?>> key, U obj) {
        throw new UnsupportedOperationException("Not allowed before 1.21.11!");
    }

    public GameRules.Key<GameRules.BooleanValue> registerBoolean(String name, GameRules.Category category, boolean defaultValue) {
        return this.register(name, category, GameRuleFactory.createBooleanRule(defaultValue));
    }

    public GameRules.Key<GameRules.BooleanValue> registerBoolean(String name, CustomGameRuleCategory category, boolean defaultValue) {
        return this.register(name, category, GameRuleFactory.createBooleanRule(defaultValue));
    }

    public <T extends GameRules.Value<T>> GameRules.Key<T> register(String name, GameRules.Category category, GameRules.Type<T> type) {
        return GameRuleRegistry.register(this.id(name).toString(), category, type);
    }

    public <T extends GameRules.Value<T>> GameRules.Key<T> register(String name, CustomGameRuleCategory category, GameRules.Type<T> type) {
        return GameRuleRegistry.register(this.id(name).toString(), category, type);
    }
    *///?}

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
        //~ if >=1.21.11 'getRule(enumRule).get() -> 'get(enumRule)'
        return gameRules.get(enumRule);
    }
    //~}
}
