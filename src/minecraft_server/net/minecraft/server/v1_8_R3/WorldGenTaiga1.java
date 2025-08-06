package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenTaiga1 extends WorldGenTreeAbstract
{
    private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.SPRUCE);
    private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.SPRUCE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    public WorldGenTaiga1()
    {
        super(false);
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        int i = p_generate_2_.nextInt(5) + 7;
        int j = i - p_generate_2_.nextInt(2) - 3;
        int k = i - j;
        int l = 1 + p_generate_2_.nextInt(k + 1);
        boolean flag = true;

        if (p_generate_3_.getY() >= 1 && p_generate_3_.getY() + i + 1 <= 256)
        {
            for (int i1 = p_generate_3_.getY(); i1 <= p_generate_3_.getY() + 1 + i && flag; ++i1)
            {
                int j1 = 1;

                if (i1 - p_generate_3_.getY() < j)
                {
                    j1 = 0;
                }
                else
                {
                    j1 = l;
                }

                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for (int k1 = p_generate_3_.getX() - j1; k1 <= p_generate_3_.getX() + j1 && flag; ++k1)
                {
                    for (int l1 = p_generate_3_.getZ() - j1; l1 <= p_generate_3_.getZ() + j1 && flag; ++l1)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            if (!this.a(p_generate_1_.getType(blockposition$mutableblockposition.c(k1, i1, l1)).getBlock()))
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
                Block block = p_generate_1_.getType(p_generate_3_.down()).getBlock();

                if ((block == Blocks.GRASS || block == Blocks.DIRT) && p_generate_3_.getY() < 256 - i - 1)
                {
                    this.a(p_generate_1_, p_generate_3_.down());
                    int k2 = 0;

                    for (int l2 = p_generate_3_.getY() + i; l2 >= p_generate_3_.getY() + j; --l2)
                    {
                        for (int j3 = p_generate_3_.getX() - k2; j3 <= p_generate_3_.getX() + k2; ++j3)
                        {
                            int k3 = j3 - p_generate_3_.getX();

                            for (int i2 = p_generate_3_.getZ() - k2; i2 <= p_generate_3_.getZ() + k2; ++i2)
                            {
                                int j2 = i2 - p_generate_3_.getZ();

                                if (Math.abs(k3) != k2 || Math.abs(j2) != k2 || k2 <= 0)
                                {
                                    BlockPosition blockposition = new BlockPosition(j3, l2, i2);

                                    if (!p_generate_1_.getType(blockposition).getBlock().o())
                                    {
                                        this.a(p_generate_1_, blockposition, b);
                                    }
                                }
                            }
                        }

                        if (k2 >= 1 && l2 == p_generate_3_.getY() + j + 1)
                        {
                            --k2;
                        }
                        else if (k2 < l)
                        {
                            ++k2;
                        }
                    }

                    for (int i3 = 0; i3 < i - 1; ++i3)
                    {
                        Block block1 = p_generate_1_.getType(p_generate_3_.up(i3)).getBlock();

                        if (block1.getMaterial() == Material.AIR || block1.getMaterial() == Material.LEAVES)
                        {
                            this.a(p_generate_1_, p_generate_3_.up(i3), a);
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
