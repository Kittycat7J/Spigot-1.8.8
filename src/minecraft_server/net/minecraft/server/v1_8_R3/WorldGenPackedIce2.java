package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenPackedIce2 extends WorldGenerator
{
    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        while (p_generate_1_.isEmpty(p_generate_3_) && p_generate_3_.getY() > 2)
        {
            p_generate_3_ = p_generate_3_.down();
        }

        if (p_generate_1_.getType(p_generate_3_).getBlock() != Blocks.SNOW)
        {
            return false;
        }
        else
        {
            p_generate_3_ = p_generate_3_.up(p_generate_2_.nextInt(4));
            int i = p_generate_2_.nextInt(4) + 7;
            int j = i / 4 + p_generate_2_.nextInt(2);

            if (j > 1 && p_generate_2_.nextInt(60) == 0)
            {
                p_generate_3_ = p_generate_3_.up(10 + p_generate_2_.nextInt(30));
            }

            for (int k = 0; k < i; ++k)
            {
                float f = (1.0F - (float)k / (float)i) * (float)j;
                int l = MathHelper.f(f);

                for (int i1 = -l; i1 <= l; ++i1)
                {
                    float f1 = (float)MathHelper.a(i1) - 0.25F;

                    for (int j1 = -l; j1 <= l; ++j1)
                    {
                        float f2 = (float)MathHelper.a(j1) - 0.25F;

                        if ((i1 == 0 && j1 == 0 || f1 * f1 + f2 * f2 <= f * f) && (i1 != -l && i1 != l && j1 != -l && j1 != l || p_generate_2_.nextFloat() <= 0.75F))
                        {
                            Block block = p_generate_1_.getType(p_generate_3_.a(i1, k, j1)).getBlock();

                            if (block.getMaterial() == Material.AIR || block == Blocks.DIRT || block == Blocks.SNOW || block == Blocks.ICE)
                            {
                                p_generate_1_.setTypeUpdate(p_generate_3_.a(i1, k, j1), Blocks.PACKED_ICE.getBlockData());
                            }

                            if (k != 0 && l > 1)
                            {
                                block = p_generate_1_.getType(p_generate_3_.a(i1, -k, j1)).getBlock();

                                if (block.getMaterial() == Material.AIR || block == Blocks.DIRT || block == Blocks.SNOW || block == Blocks.ICE)
                                {
                                    p_generate_1_.setTypeUpdate(p_generate_3_.a(i1, -k, j1), Blocks.PACKED_ICE.getBlockData());
                                }
                            }
                        }
                    }
                }
            }

            int k1 = j - 1;

            if (k1 < 0)
            {
                k1 = 0;
            }
            else if (k1 > 1)
            {
                k1 = 1;
            }

            for (int l1 = -k1; l1 <= k1; ++l1)
            {
                for (int i2 = -k1; i2 <= k1; ++i2)
                {
                    BlockPosition blockposition = p_generate_3_.a(l1, -1, i2);
                    int j2 = 50;

                    if (Math.abs(l1) == 1 && Math.abs(i2) == 1)
                    {
                        j2 = p_generate_2_.nextInt(5);
                    }

                    while (blockposition.getY() > 50)
                    {
                        Block block1 = p_generate_1_.getType(blockposition).getBlock();

                        if (block1.getMaterial() != Material.AIR && block1 != Blocks.DIRT && block1 != Blocks.SNOW && block1 != Blocks.ICE && block1 != Blocks.PACKED_ICE)
                        {
                            break;
                        }

                        p_generate_1_.setTypeUpdate(blockposition, Blocks.PACKED_ICE.getBlockData());
                        blockposition = blockposition.down();
                        --j2;

                        if (j2 <= 0)
                        {
                            blockposition = blockposition.down(p_generate_2_.nextInt(5) + 1);
                            j2 = p_generate_2_.nextInt(5);
                        }
                    }
                }
            }

            return true;
        }
    }
}
