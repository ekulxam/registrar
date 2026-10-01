package survivalblock.atmosphere.registar_testmod.init;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.ApiStatus;
import survivalblock.atmosphere.registar_testmod.RegistrarTestmod;
import survivalblock.atmosphere.registrar.BlockRegistrant;

@ApiStatus.NonExtendable
public interface TestBlocks {
    BlockRegistrant BLOCK_REGISTRANT = new BlockRegistrant(RegistrarTestmod.MOD_ID);

    Block TEST_BLOCK = BLOCK_REGISTRANT.register(Block::new, BlockBehaviour.Properties.of());

    static void init() {
        // NO-OP
    }
}
