package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenSwampTree extends WorldGenTreeAbstract
{
    private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.OAK);
    private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.OAK).set(BlockLeaves1.CHECK_DECAY, Boolean.valueOf(false));

    public WorldGenSwampTree()
    {
        super(false);
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        int i;

        for (i = p_generate_2_.nextInt(4) + 5; p_generate_1_.getType(p_generate_3_.down()).getBlock().getMaterial() == Material.WATER; p_generate_3_ = p_generate_3_.down())
        {
            ;
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
                    b0 = 3;
                }

                BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                for (int k = p_generate_3_.getX() - b0; k <= p_generate_3_.getX() + b0 && flag; ++k)
                {
                    for (int l = p_generate_3_.getZ() - b0; l <= p_generate_3_.getZ() + b0 && flag; ++l)
                    {
                        if (j >= 0 && j < 256)
                        {
                            Block block = p_generate_1_.getType(blockposition$mutableblockposition.c(k, j, l)).getBlock();

                            if (block.getMaterial() != Material.AIR && block.getMaterial() != Material.LEAVES)
                            {
                                if (block != Blocks.WATER && block != Blocks.FLOWING_WATER)
                                {
                                    flag = false;
                                }
                                else if (j > p_generate_3_.getY())
                                {
                                    flag = false;
                                }
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

                if ((block1 == Blocks.GRASS || block1 == Blocks.DIRT) && p_generate_3_.getY() < 256 - i - 1)
                {
                    this.a(p_generate_1_, p_generate_3_.down());

                    for (int k1 = p_generate_3_.getY() - 3 + i; k1 <= p_generate_3_.getY() + i; ++k1)
                    {
                        int j2 = k1 - (p_generate_3_.getY() + i);
                        int l2 = 2 - j2 / 2;

                        for (int j3 = p_generate_3_.getX() - l2; j3 <= p_generate_3_.getX() + l2; ++j3)
                        {
                            int k3 = j3 - p_generate_3_.getX();

                            for (int i1 = p_generate_3_.getZ() - l2; i1 <= p_generate_3_.getZ() + l2; ++i1)
                            {
                                int j1 = i1 - p_generate_3_.getZ();

                                if (Math.abs(k3) != l2 || Math.abs(j1) != l2 || p_generate_2_.nextInt(2) != 0 && j2 != 0)
                                {
                                    BlockPosition blockposition = new BlockPosition(j3, k1, i1);

                                    if (!p_generate_1_.getType(blockposition).getBlock().o())
                                    {
                                        this.a(p_generate_1_, blockposition, b);
                                    }
                                }
                            }
                        }
                    }

                    for (int l1 = 0; l1 < i; ++l1)
                    {
                        Block block2 = p_generate_1_.getType(p_generate_3_.up(l1)).getBlock();

                        if (block2.getMaterial() == Material.AIR || block2.getMaterial() == Material.LEAVES || block2 == Blocks.FLOWING_WATER || block2 == Blocks.WATER)
                        {
                            this.a(p_generate_1_, p_generate_3_.up(l1), a);
                        }
                    }

                    for (int i2 = p_generate_3_.getY() - 3 + i; i2 <= p_generate_3_.getY() + i; ++i2)
                    {
                        int k2 = i2 - (p_generate_3_.getY() + i);
                        int i3 = 2 - k2 / 2;
                        BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 = new BlockPosition.MutableBlockPosition();

                        for (int l3 = p_generate_3_.getX() - i3; l3 <= p_generate_3_.getX() + i3; ++l3)
                        {
                            for (int i4 = p_generate_3_.getZ() - i3; i4 <= p_generate_3_.getZ() + i3; ++i4)
                            {
                                blockposition$mutableblockposition1.c(l3, i2, i4);

                                if (p_generate_1_.getType(blockposition$mutableblockposition1).getBlock().getMaterial() == Material.LEAVES)
                                {
                                    BlockPosition blockposition3 = blockposition$mutableblockposition1.west();
                                    BlockPosition blockposition4 = blockposition$mutableblockposition1.east();
                                    BlockPosition blockposition1 = blockposition$mutableblockposition1.north();
                                    BlockPosition blockposition2 = blockposition$mutableblockposition1.south();

                                    if (p_generate_2_.nextInt(4) == 0 && p_generate_1_.getType(blockposition3).getBlock().getMaterial() == Material.AIR)
                                    {
                                        this.a(p_generate_1_, blockposition3, BlockVine.EAST);
                                    }

                                    if (p_generate_2_.nextInt(4) == 0 && p_generate_1_.getType(blockposition4).getBlock().getMaterial() == Material.AIR)
                                    {
                                        this.a(p_generate_1_, blockposition4, BlockVine.WEST);
                                    }

                                    if (p_generate_2_.nextInt(4) == 0 && p_generate_1_.getType(blockposition1).getBlock().getMaterial() == Material.AIR)
                                    {
                                        this.a(p_generate_1_, blockposition1, BlockVine.SOUTH);
                                    }

                                    if (p_generate_2_.nextInt(4) == 0 && p_generate_1_.getType(blockposition2).getBlock().getMaterial() == Material.AIR)
                                    {
                                        this.a(p_generate_1_, blockposition2, BlockVine.NORTH);
                                    }
                                }
                            }
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

    private void a(World p_a_1_, BlockPosition p_a_2_, BlockStateBoolean p_a_3_)
    {
        IBlockData iblockdata = Blocks.VINE.getBlockData().set(p_a_3_, Boolean.valueOf(true));
        this.a(p_a_1_, p_a_2_, iblockdata);
        int i = 4;

        for (p_a_2_ = p_a_2_.down(); p_a_1_.getType(p_a_2_).getBlock().getMaterial() == Material.AIR && i > 0; --i)
        {
            this.a(p_a_1_, p_a_2_, iblockdata);
            p_a_2_ = p_a_2_.down();
        }
    }
}
