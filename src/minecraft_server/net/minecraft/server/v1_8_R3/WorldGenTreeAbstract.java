package net.minecraft.server.v1_8_R3;

import java.util.Random;

public abstract class WorldGenTreeAbstract extends WorldGenerator
{
    public WorldGenTreeAbstract(boolean p_i688_1_)
    {
        super(p_i688_1_);
    }

    protected boolean a(Block p_a_1_)
    {
        Material material = p_a_1_.getMaterial();
        return material == Material.AIR || material == Material.LEAVES || p_a_1_ == Blocks.GRASS || p_a_1_ == Blocks.DIRT || p_a_1_ == Blocks.LOG || p_a_1_ == Blocks.LOG2 || p_a_1_ == Blocks.SAPLING || p_a_1_ == Blocks.VINE;
    }

    public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_)
    {
    }

    protected void a(World p_a_1_, BlockPosition p_a_2_)
    {
        if (p_a_1_.getType(p_a_2_).getBlock() != Blocks.DIRT)
        {
            this.a(p_a_1_, p_a_2_, Blocks.DIRT.getBlockData());
        }
    }
}
