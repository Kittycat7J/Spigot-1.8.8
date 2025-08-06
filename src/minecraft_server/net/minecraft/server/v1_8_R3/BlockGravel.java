package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockGravel extends BlockFalling
{
    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        if (p_getDropType_3_ > 3)
        {
            p_getDropType_3_ = 3;
        }

        return p_getDropType_2_.nextInt(10 - p_getDropType_3_ * 3) == 0 ? Items.FLINT : Item.getItemOf(this);
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return MaterialMapColor.m;
    }
}
