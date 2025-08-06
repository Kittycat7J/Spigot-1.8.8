package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenForest extends WorldGenTreeAbstract
{
    private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.BIRCH);
    private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.BIRCH).set(BlockLeaves1.CHECK_DECAY, Boolean.valueOf(false));
    private boolean c;

    public WorldGenForest(boolean p_i691_1_, boolean p_i691_2_)
    {
        super(p_i691_1_);
        this.c = p_i691_2_;
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        int i = p_generate_2_.nextInt(3) + 5;

        if (this.c)
        {
            i += p_generate_2_.nextInt(7);
        }

        boolean flag = true;

        if (p_generate_3_.getY() >= 1 && p_generate_3_.getY() + i + 1 <= 256)
        {
            for (int j = p_generate_3_.getY(); j <= p_generate_3_.getY() + 1 + i; ++j)
            {
                byte b0 = 1;

                if (j == p_generate_3_.getY())
                {
                    b0 = 0;
                }

                if (j >= p_generate_3_.getY() + 1 + i - 2)
                {
                    b0 = 2;
                }

                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for (int k = p_generate_3_.getX() - b0; k <= p_generate_3_.getX() + b0 && flag; ++k)
                {
                    for (int l = p_generate_3_.getZ() - b0; l <= p_generate_3_.getZ() + b0 && flag; ++l)
                    {
                        if (j >= 0 && j < 256)
                        {
                            if (!this.a(p_generate_1_.getType(blockposition$mutableblockposition.c(k, j, l)).getBlock()))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                Block block1 = p_generate_1_.getType(p_generate_3_.down()).getBlock();

                if ((block1 == Blocks.GRASS || block1 == Blocks.DIRT || block1 == Blocks.FARMLAND) && p_generate_3_.getY() < 256 - i - 1)
                {
                    this.a(p_generate_1_, p_generate_3_.down());

                    for (int l1 = p_generate_3_.getY() - 3 + i; l1 <= p_generate_3_.getY() + i; ++l1)
                    {
                        int j2 = l1 - (p_generate_3_.getY() + i);
                        int k2 = 1 - j2 / 2;

                        for (int l2 = p_generate_3_.getX() - k2; l2 <= p_generate_3_.getX() + k2; ++l2)
                        {
                            int i1 = l2 - p_generate_3_.getX();

                            for (int j1 = p_generate_3_.getZ() - k2; j1 <= p_generate_3_.getZ() + k2; ++j1)
                            {
                                int k1 = j1 - p_generate_3_.getZ();

                                if (Math.abs(i1) != k2 || Math.abs(k1) != k2 || p_generate_2_.nextInt(2) != 0 && j2 != 0)
                                {
                                    BlockPosition blockposition = new BlockPosition(l2, l1, j1);
                                    Block block = p_generate_1_.getType(blockposition).getBlock();

                                    if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES)
                                    {
                                        this.a(p_generate_1_, blockposition, b);
                                    }
                                }
                            }
                        }
                    }

                    for (int i2 = 0; i2 < i; ++i2)
                    {
                        Block block2 = p_generate_1_.getType(p_generate_3_.up(i2)).getBlock();

                        if (block2.getMaterial() == Material.AIR || block2.getMaterial() == Material.LEAVES)
                        {
                            this.a(p_generate_1_, p_generate_3_.up(i2), a);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
