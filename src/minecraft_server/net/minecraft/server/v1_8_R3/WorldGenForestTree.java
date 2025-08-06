package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenForestTree extends WorldGenTreeAbstract
{
    private static final IBlockData a = Blocks.LOG2.getBlockData().set(BlockLog2.VARIANT, BlockWood.EnumLogVariant.DARK_OAK);
    private static final IBlockData b = Blocks.LEAVES2.getBlockData().set(BlockLeaves2.VARIANT, BlockWood.EnumLogVariant.DARK_OAK).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    public WorldGenForestTree(boolean p_i293_1_)
    {
        super(p_i293_1_);
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        int i = p_generate_2_.nextInt(3) + p_generate_2_.nextInt(2) + 6;
        int j = p_generate_3_.getX();
        int k = p_generate_3_.getY();
        int l = p_generate_3_.getZ();

        if (k >= 1 && k + i + 1 < 256)
        {
            BlockPosition blockposition = p_generate_3_.down();
            Block block = p_generate_1_.getType(blockposition).getBlock();

            if (block != Blocks.GRASS && block != Blocks.DIRT)
            {
                return false;
            }
            else if (!this.a(p_generate_1_, p_generate_3_, i))
            {
                return false;
            }
            else
            {
                this.a(p_generate_1_, blockposition);
                this.a(p_generate_1_, blockposition.east());
                this.a(p_generate_1_, blockposition.south());
                this.a(p_generate_1_, blockposition.south().east());
                EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_generate_2_);
                int i1 = i - p_generate_2_.nextInt(4);
                int j1 = 2 - p_generate_2_.nextInt(3);
                int k1 = j;
                int l1 = l;
                int i2 = k + i - 1;

                for (int j2 = 0; j2 < i; ++j2)
                {
                    if (j2 >= i1 && j1 > 0)
                    {
                        k1 += enumdirection.getAdjacentX();
                        l1 += enumdirection.getAdjacentZ();
                        --j1;
                    }

                    int k2 = k + j2;
                    BlockPosition blockposition1 = new BlockPosition(k1, k2, l1);
                    Material material = p_generate_1_.getType(blockposition1).getBlock().getMaterial();

                    if (material == Material.AIR || material == Material.LEAVES)
                    {
                        this.b(p_generate_1_, blockposition1);
                        this.b(p_generate_1_, blockposition1.east());
                        this.b(p_generate_1_, blockposition1.south());
                        this.b(p_generate_1_, blockposition1.east().south());
                    }
                }

                for (int i3 = -2; i3 <= 0; ++i3)
                {
                    for (int l3 = -2; l3 <= 0; ++l3)
                    {
                        byte b0 = -1;
                        this.a(p_generate_1_, k1 + i3, i2 + b0, l1 + l3);
                        this.a(p_generate_1_, 1 + k1 - i3, i2 + b0, l1 + l3);
                        this.a(p_generate_1_, k1 + i3, i2 + b0, 1 + l1 - l3);
                        this.a(p_generate_1_, 1 + k1 - i3, i2 + b0, 1 + l1 - l3);

                        if ((i3 > -2 || l3 > -1) && (i3 != -1 || l3 != -2))
                        {
                            byte b1 = 1;
                            this.a(p_generate_1_, k1 + i3, i2 + b1, l1 + l3);
                            this.a(p_generate_1_, 1 + k1 - i3, i2 + b1, l1 + l3);
                            this.a(p_generate_1_, k1 + i3, i2 + b1, 1 + l1 - l3);
                            this.a(p_generate_1_, 1 + k1 - i3, i2 + b1, 1 + l1 - l3);
                        }
                    }
                }

                if (p_generate_2_.nextBoolean())
                {
                    this.a(p_generate_1_, k1, i2 + 2, l1);
                    this.a(p_generate_1_, k1 + 1, i2 + 2, l1);
                    this.a(p_generate_1_, k1 + 1, i2 + 2, l1 + 1);
                    this.a(p_generate_1_, k1, i2 + 2, l1 + 1);
                }

                for (int j31 = -3; j31 <= 4; ++j31)
                {
                    for (int i4 = -3; i4 <= 4; ++i4)
                    {
                        if ((j31 != -3 || i4 != -3) && (j31 != -3 || i4 != 4) && (j31 != 4 || i4 != -3) && (j31 != 4 || i4 != 4) && (Math.abs(j31) < 3 || Math.abs(i4) < 3))
                        {
                            this.a(p_generate_1_, k1 + j31, i2, l1 + i4);
                        }
                    }
                }

                for (int k3 = -1; k3 <= 2; ++k3)
                {
                    for (int j4 = -1; j4 <= 2; ++j4)
                    {
                        if ((k3 < 0 || k3 > 1 || j4 < 0 || j4 > 1) && p_generate_2_.nextInt(3) <= 0)
                        {
                            int k4 = p_generate_2_.nextInt(3) + 2;

                            for (int l4 = 0; l4 < k4; ++l4)
                            {
                                this.b(p_generate_1_, new BlockPosition(j + k3, i2 - l4 - 1, l + j4));
                            }

                            for (int i5 = -1; i5 <= 1; ++i5)
                            {
                                for (int l2 = -1; l2 <= 1; ++l2)
                                {
                                    this.a(p_generate_1_, k1 + k3 + i5, i2, l1 + j4 + l2);
                                }
                            }

                            for (int j5 = -2; j5 <= 2; ++j5)
                            {
                                for (int k5 = -2; k5 <= 2; ++k5)
                                {
                                    if (Math.abs(j5) != 2 || Math.abs(k5) != 2)
                                    {
                                        this.a(p_generate_1_, k1 + k3 + j5, i2 - 1, l1 + j4 + k5);
                                    }
                                }
                            }
                        }
                    }
                }

                return true;
            }
        }
        else
        {
            return false;
        }
    }

    private boolean a(World p_a_1_, BlockPosition p_a_2_, int p_a_3_)
    {
        int i = p_a_2_.getX();
        int j = p_a_2_.getY();
        int k = p_a_2_.getZ();
        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

        for (int l = 0; l <= p_a_3_ + 1; ++l)
        {
            byte b0 = 1;

            if (l == 0)
            {
                b0 = 0;
            }

            if (l >= p_a_3_ - 1)
            {
                b0 = 2;
            }

            for (int i1 = -b0; i1 <= b0; ++i1)
            {
                for (int j1 = -b0; j1 <= b0; ++j1)
                {
                    if (!this.a(p_a_1_.getType(blockposition$mutableblockposition.c(i + i1, j + l, k + j1)).getBlock()))
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void b(World p_b_1_, BlockPosition p_b_2_)
    {
        if (this.a(p_b_1_.getType(p_b_2_).getBlock()))
        {
            this.a(p_b_1_, p_b_2_, a);
        }
    }

    private void a(World p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        BlockPosition blockposition = new BlockPosition(p_a_2_, p_a_3_, p_a_4_);
        Block block = p_a_1_.getType(blockposition).getBlock();

        if (block.getMaterial() == Material.AIR)
        {
            this.a(p_a_1_, blockposition, b);
        }
    }
}
