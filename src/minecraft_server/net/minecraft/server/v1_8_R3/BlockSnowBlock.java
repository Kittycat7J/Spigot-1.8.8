package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockSnowBlock extends Block
{
    protected BlockSnowBlock()
    {
        super(Material.SNOW_BLOCK);
        this.a(true);
        this.a(CreativeModeTab.b);
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.SNOWBALL;
    }

    public int a(Random p_a_1_)
    {
        return 4;
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (p_b_1_.b(EnumSkyBlock.BLOCK, p_b_2_) > 11)
        {
            this.b(p_b_1_, p_b_2_, p_b_1_.getType(p_b_2_), 0);
            p_b_1_.setAir(p_b_2_);
        }
    }
}
