package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockMobSpawner extends BlockContainer
{
    protected BlockMobSpawner()
    {
        super(Material.STONE);
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityMobSpawner();
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return null;
    }

    public int a(Random p_a_1_)
    {
        return 0;
    }

    public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_)
    {
        super.dropNaturally(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, p_dropNaturally_4_, p_dropNaturally_5_);
    }

    public int getExpDrop(World p_getExpDrop_1_, IBlockData p_getExpDrop_2_, int p_getExpDrop_3_)
    {
        int i = 15 + p_getExpDrop_1_.random.nextInt(15) + p_getExpDrop_1_.random.nextInt(15);
        return i;
    }

    public boolean c()
    {
        return false;
    }

    public int b()
    {
        return 3;
    }
}
