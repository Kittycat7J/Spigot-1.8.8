package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockSeaLantern extends Block
{
    public BlockSeaLantern(Material p_i642_1_)
    {
        super(p_i642_1_);
        this.a(CreativeModeTab.b);
    }

    public int a(Random p_a_1_)
    {
        return 2 + p_a_1_.nextInt(2);
    }

    public int getDropCount(int p_getDropCount_1_, Random p_getDropCount_2_)
    {
        return MathHelper.clamp(this.a(p_getDropCount_2_) + p_getDropCount_2_.nextInt(p_getDropCount_1_ + 1), 1, 5);
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.PRISMARINE_CRYSTALS;
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return MaterialMapColor.p;
    }

    protected boolean I()
    {
        return true;
    }
}
