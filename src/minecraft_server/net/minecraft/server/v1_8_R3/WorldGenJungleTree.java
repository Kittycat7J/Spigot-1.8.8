package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenJungleTree extends WorldGenMegaTreeAbstract
{
    public WorldGenJungleTree(boolean p_i702_1_, int p_i702_2_, int p_i702_3_, IBlockData p_i702_4_, IBlockData p_i702_5_)
    {
        super(p_i702_1_, p_i702_2_, p_i702_3_, p_i702_4_, p_i702_5_);
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        int i = this.a(p_generate_2_);

        if (!this.a(p_generate_1_, p_generate_2_, p_generate_3_, i))
        {
            return false;
        }
        else
        {
            this.c(p_generate_1_, p_generate_3_.up(i), 2);

            for (int j = p_generate_3_.getY() + i - 2 - p_generate_2_.nextInt(4); j > p_generate_3_.getY() + i / 2; j -= 2 + p_generate_2_.nextInt(4))
            {
                float f = p_generate_2_.nextFloat() * (float)Math.PI * 2.0F;
                int k = p_generate_3_.getX() + (int)(0.5F + MathHelper.cos(f) * 4.0F);
                int l = p_generate_3_.getZ() + (int)(0.5F + MathHelper.sin(f) * 4.0F);

                for (int i1 = 0; i1 < 5; ++i1)
                {
                    k = p_generate_3_.getX() + (int)(1.5F + MathHelper.cos(f) * (float)i1);
                    l = p_generate_3_.getZ() + (int)(1.5F + MathHelper.sin(f) * (float)i1);
                    this.a(p_generate_1_, new BlockPosition(k, j - 3 + i1 / 2, l), this.b);
                }

                int j2 = 1 + p_generate_2_.nextInt(2);
                int j1 = j;

                for (int k1 = j - j2; k1 <= j1; ++k1)
                {
                    int l1 = k1 - j1;
                    this.b(p_generate_1_, new BlockPosition(k, k1, l), 1 - l1);
                }
            }

            for (int i2 = 0; i2 < i; ++i2)
            {
                BlockPosition blockposition = p_generate_3_.up(i2);

                if (this.a(p_generate_1_.getType(blockposition).getBlock()))
                {
                    this.a(p_generate_1_, blockposition, this.b);

                    if (i2 > 0)
                    {
                        this.a(p_generate_1_, p_generate_2_, blockposition.west(), BlockVine.EAST);
                        this.a(p_generate_1_, p_generate_2_, blockposition.north(), BlockVine.SOUTH);
                    }
                }

                if (i2 < i - 1)
                {
                    BlockPosition blockposition1 = blockposition.east();

                    if (this.a(p_generate_1_.getType(blockposition1).getBlock()))
                    {
                        this.a(p_generate_1_, blockposition1, this.b);

                        if (i2 > 0)
                        {
                            this.a(p_generate_1_, p_generate_2_, blockposition1.east(), BlockVine.WEST);
                            this.a(p_generate_1_, p_generate_2_, blockposition1.north(), BlockVine.SOUTH);
                        }
                    }

                    BlockPosition blockposition2 = blockposition.south().east();

                    if (this.a(p_generate_1_.getType(blockposition2).getBlock()))
                    {
                        this.a(p_generate_1_, blockposition2, this.b);

                        if (i2 > 0)
                        {
                            this.a(p_generate_1_, p_generate_2_, blockposition2.east(), BlockVine.WEST);
                            this.a(p_generate_1_, p_generate_2_, blockposition2.south(), BlockVine.NORTH);
                        }
                    }

                    BlockPosition blockposition3 = blockposition.south();

                    if (this.a(p_generate_1_.getType(blockposition3).getBlock()))
                    {
                        this.a(p_generate_1_, blockposition3, this.b);

                        if (i2 > 0)
                        {
                            this.a(p_generate_1_, p_generate_2_, blockposition3.west(), BlockVine.EAST);
                            this.a(p_generate_1_, p_generate_2_, blockposition3.south(), BlockVine.NORTH);
                        }
                    }
                }
            }

            return true;
        }
    }

    private void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_, BlockStateBoolean p_a_4_)
    {
        if (p_a_2_.nextInt(3) > 0 && p_a_1_.isEmpty(p_a_3_))
        {
            this.a(p_a_1_, p_a_3_, Blocks.VINE.getBlockData().set(p_a_4_, Boolean.valueOf(true)));
        }
    }

    private void c(World p_c_1_, BlockPosition p_c_2_, int p_c_3_)
    {
        byte b0 = 2;

        for (int i = -b0; i <= 0; ++i)
        {
            this.a(p_c_1_, p_c_2_.up(i), p_c_3_ + 1 - i);
        }
    }
}
