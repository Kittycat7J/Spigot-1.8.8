package net.minecraft.server.v1_8_R3;

public class BlockSlowSand extends Block
{
    public BlockSlowSand()
    {
        super(Material.SAND, MaterialMapColor.B);
        this.a(CreativeModeTab.b);
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        float f = 0.125F;
        return new AxisAlignedBB((double)p_a_2_.getX(), (double)p_a_2_.getY(), (double)p_a_2_.getZ(), (double)(p_a_2_.getX() + 1), (double)((float)(p_a_2_.getY() + 1) - f), (double)(p_a_2_.getZ() + 1));
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_)
    {
        p_a_4_.motX *= 0.4D;
        p_a_4_.motZ *= 0.4D;
    }
}
