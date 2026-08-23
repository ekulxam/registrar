package survivalblock.atmosphere.registar_testmod;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;
import survivalblock.atmosphere.registar_testmod.init.TestStructures;

public class RegistrarTestmod implements ModInitializer {
    public static final String MOD_ID = "registrar_testmod";

    @Override
    public void onInitialize() {
        TestStructures.init();
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
