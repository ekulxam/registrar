package survivalblock.atmosphere.registrar.wrapper;

//? if >=26.2
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
//? if >=26.2
import org.jspecify.annotations.Nullable;

@SuppressWarnings("ClassCanBeRecord")
public class BlockPresenter<T extends Block> implements ItemLike {
    protected final T block;
    protected final ResourceKey<Block> resourceKey;
    //? if >=26.2 {
    @Nullable
    protected final BlockItemId blockItemId;
    //?}

    public BlockPresenter(T block, ResourceKey<Block> resourceKey /*? >=26.2 {*/, @org.jspecify.annotations.Nullable BlockItemId blockItemId/*?}*/) {
        this.block = block;
        this.resourceKey = resourceKey;
        //? if >=26.2
        this.blockItemId = blockItemId;
    }

    public T getBlock() {
        return this.block;
    }

    public ResourceKey<Block> getKey() {
        return this.resourceKey;
    }

    //? if >=26.2 {
    @Nullable
    public BlockItemId getBlockItemId() {
        return this.blockItemId;
    }
    //?}

    @Override
    public Item asItem() {
        return this.block.asItem();
    }
}
