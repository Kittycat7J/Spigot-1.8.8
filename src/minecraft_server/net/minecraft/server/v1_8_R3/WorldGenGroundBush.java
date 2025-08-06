package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenGroundBush extends WorldGenTrees
{
    private final IBlockData a;
    private final IBlockData b;

    public WorldGenGroundBush(IBlockData p_i68_1_, IBlockData p_i68_2_)
    {
        super(false);
        this.b = p_i68_1_;
        this.a = p_i68_2_;
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        Block block;

        while (((block = p_generate_1_.getType(p_generate_3_).getBlock()).getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES) && p_generate_3_.getY() > 0)
        {
            p_generate_3_ = p_generate_3_.down();
        }

        Block block1 = p_generate_1_.getType(p_generate_3_).getBlock();

        if (block1 != Blocks.DIRT && block1 != Blocks.GRASS)
        {
            return false;
        }
        else
        {
            p_generate_3_ = p_generate_3_.up();
            this.a(p_generate_1_, p_generate_3_, this.b);

            for (int i = p_generate_3_.getY(); i <= p_generate_3_.getY() + 2; ++i)
            {
                int j = i - p_generate_3_.getY();
                int k = 2 - j;

                for (int l = p_generate_3_.getX() - k; l <= p_generate_3_.getX() + k; ++l)
                {
                    int i1 = l - p_generate_3_.getX();

                    for (int j1 = p_generate_3_.getZ() - k; j1 <= p_generate_3_.getZ() + k; ++j1)
                    {
                        int k1 = j1 - p_generate_3_.getZ();

                        if (Math.abs(i1) != k || Math.abs(k1) != k || p_generate_2_.nextInt(2) != 0)
                        {
                            BlockPosition blockposition = new BlockPosition(l, i, j1);

                            if (!p_generate_1_.getType(blockposition).getBlock().o())
                            {
                                this.a(p_generate_1_, blockposition, this.a);
                            }
                        }
                    }
                }
            }

            return true;
        }
    }
}
