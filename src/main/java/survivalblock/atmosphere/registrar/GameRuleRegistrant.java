package survivalblock.atmosphere.registrar;

import net.minecraft.core.Registry;
//? if >=1.21.11
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
//? if <1.21.11 {
/*import net.minecraft.world.level.GameRules;
*///?} else {
import net.minecraft.world.level.gamerules.GameRule;
//?}
import net.minecraft.world.level.gamerules.GameRules;
import survivalblock.atmosphere.registrar.shared.IGameRuleRegistrant;

import java.util.function.Function;

/**
 * A {@link Registrant} for game rules. Note that before 1.21.11, the registry is null.
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
    //~}
}
