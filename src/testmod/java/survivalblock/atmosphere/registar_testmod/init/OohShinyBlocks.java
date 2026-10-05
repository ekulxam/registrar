package survivalblock.atmosphere.registar_testmod.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.ApiStatus;
import survivalblock.atmosphere.registar_testmod.RegistrarTestmod;
import survivalblock.atmosphere.registrar.annotation.ConstructBlock;
import survivalblock.atmosphere.registrar.annotation.ConstructItem;
import survivalblock.atmosphere.registrar.delayed.DelayedBlockRegistrant;
import survivalblock.atmosphere.registrar.wrapper.BlockPresenter;

import java.util.Objects;

@ApiStatus.NonExtendable
@ConstructBlock
public interface OohShinyBlocks {
    DelayedBlockRegistrant DELAYED_BLOCK_REGISTRANT = new DelayedBlockRegistrant(RegistrarTestmod.MOD_ID);

    Block ITEMLESS = DELAYED_BLOCK_REGISTRANT.register(Block::new, BlockBehaviour.Properties.of());
    BlockPresenter<Block> ITEMLESS_TWO = DELAYED_BLOCK_REGISTRANT.registerAndPresent(Block::new, BlockBehaviour.Properties.ofFullCopy(ITEMLESS));
    @ConstructItem(exclude = true)
    BlockPresenter<Block> ITEMLESS_THREE = DELAYED_BLOCK_REGISTRANT.registerAndPresent(Block::new, BlockBehaviour.Properties.ofFullCopy(ITEMLESS));
    @ConstructItem
    BlockPresenter<Block> SPECIFIED_NAME = DELAYED_BLOCK_REGISTRANT.registerAndPresent("another_one", Block::new, BlockBehaviour.Properties.of());
    @ConstructItem(suppressIdWarnings = true)
    BlockPresenter<Block> LAZY = DELAYED_BLOCK_REGISTRANT.registerAndPresent(Block::new, BlockBehaviour.Properties.ofFullCopy(SPECIFIED_NAME.getBlock()));

    static void init() {
        DELAYED_BLOCK_REGISTRANT.consumeAll();

        if (ITEMLESS.asItem() != Items.AIR) {
            throw new RuntimeException("ITEMLESS wasn't air");
        }

        if (ITEMLESS_TWO.asItem() != Items.AIR || ITEMLESS_TWO.getBlockItemId() != null) {
            throw new RuntimeException("ITEMLESS_TWO has an item?");
        }

        if (ITEMLESS_THREE.asItem() != Items.AIR || ITEMLESS_THREE.getBlockItemId() != null) {
            throw new RuntimeException("ITEMLESS_THREE has an item?");
        }

        if (SPECIFIED_NAME.asItem() == Items.AIR || SPECIFIED_NAME.getBlockItemId() == null) {
            throw new RuntimeException("SPECIFIED_NAME didn't get an item?");
        }

        if (!Objects.equals("another_one", BuiltInRegistries.BLOCK.getKey(SPECIFIED_NAME.getBlock()).getPath())) {
            throw new RuntimeException("SPECIFIED_NAME's id wasn't \"another_one\"");
        }

        if (LAZY.asItem() == Items.AIR || LAZY.getBlockItemId() == null) {
            throw new RuntimeException("LAZY didn't get an item?");
        }
    }
}
