package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Sets;
import java.util.Set;

public class ItemSpade extends ItemTool
{
    private static final Set<Block> c = Sets.newHashSet(new Block[] {Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.SOUL_SAND});

    public ItemSpade(Item.EnumToolMaterial p_i515_1_)
    {
        super(1.0F, p_i515_1_, c);
    }

    public boolean canDestroySpecialBlock(Block p_canDestroySpecialBlock_1_)
    {
        return p_canDestroySpecialBlock_1_ == Blocks.SNOW_LAYER ? true : p_canDestroySpecialBlock_1_ == Blocks.SNOW;
    }
}
