//? if >=1.21.11 {
package survivalblock.atmosphere.registrar.delayed;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRules;
import survivalblock.atmosphere.registrar.GameRuleRegistrant;

import java.util.function.Function;

@SuppressWarnings("unused")
public class DelayedGameRuleRegistrant extends DelayedRegistrant<GameRule<?>> {
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

    public GameRule<Boolean> registerBoolean(String name, boolean defaultValue) {
        return this.register(name, GameRuleBuilder.forBoolean(defaultValue));
    }

    public <T> GameRule<T> register(String name, GameRuleBuilder<T> builder) {
        return this.register(name, builder.build());
    }

    public static boolean getBoolean(GameRules gameRules, GameRule<Boolean> booleanRule) {
        return GameRuleRegistrant.getBoolean(gameRules, booleanRule);
    }

    public static int getInteger(GameRules gameRules, GameRule<Integer> integerRule) {
        return GameRuleRegistrant.getInteger(gameRules, integerRule);
    }

    public static double getDouble(GameRules gameRules, GameRule<Double> doubleRule) {
        return GameRuleRegistrant.getDouble(gameRules, doubleRule);
    }
    
    public static <E extends Enum<E>> E getEnum(GameRules gameRules, GameRule<E> enumRule) {
        return GameRuleRegistrant.getEnum(gameRules, enumRule);
    }
}
//?}