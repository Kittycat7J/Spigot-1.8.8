package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockWeb extends Block
{
    public BlockWeb()
    {
        super(Material.WEB);
        this.a(CreativeModeTab.c);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_)
    {
        p_a_4_.aA();
    }

    public boolean c()
    {
        return false;
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public boolean d()
    {
        return false;
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.STRING;
    }

    protected boolean I()
    {
        return true;
    }
}
