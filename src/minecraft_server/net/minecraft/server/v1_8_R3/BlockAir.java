package net.minecraft.server.v1_8_R3;

public class BlockAir extends Block
{
    protected BlockAir()
    {
        super(Material.AIR);
    }

    public int b()
    {
        return -1;
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public boolean c()
    {
        return false;
    }

    public boolean a(IBlockData p_a_1_, boolean p_a_2_)
    {
        return false;
    }

    public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_)
    {
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_)
    {
        return true;
    }
}
