//? if >=1.21.11 {
package survivalblock.atmosphere.registrar.delayed;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import survivalblock.atmosphere.registrar.shared.IGameRuleRegistrant;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedGameRuleRegistrant extends DelayedRegistrant<GameRule<?>> implements IGameRuleRegistrant {
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
}
//?}