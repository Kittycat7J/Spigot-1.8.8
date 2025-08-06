package net.minecraft.server.v1_8_R3;

public class BlockBarrier extends Block
{
    protected BlockBarrier()
    {
        super(Material.BANNER);
        this.x();
        this.b(6000001.0F);
        this.K();
        this.t = true;
    }

    public int b()
    {
        return -1;
    }

    public boolean c()
    {
        return false;
    }

    public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_)
    {
    }
}
