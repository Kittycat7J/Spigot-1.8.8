package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockObsidian extends Block
{
    public BlockObsidian()
    {
        super(Material.STONE);
        this.a(CreativeModeTab.b);
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Item.getItemOf(Blocks.OBSIDIAN);
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return MaterialMapColor.E;
    }
}
