package survivalblock.atmosphere.registrar.special;

import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.function.Function;

@ApiStatus.Experimental
@SuppressWarnings("unused")
public abstract class SpecialRegistrant {
    protected final Function<String, Identifier> idFunction;

    public SpecialRegistrant(String modId) {
        this(path -> Identifier.fromNamespaceAndPath(modId, path));
    }

    public SpecialRegistrant(Function<String, Identifier> idFunction) {
        this.idFunction = idFunction;
    }
}
