package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenCanyon extends WorldGenBase
{
    private float[] d = new float[1024];

    protected void a(long p_a_1_, int p_a_3_, int p_a_4_, ChunkSnapshot p_a_5_, double p_a_6_, double p_a_8_, double p_a_10_, float p_a_12_, float p_a_13_, float p_a_14_, int p_a_15_, int p_a_16_, double p_a_17_)
    {
        Random random = new Random(p_a_1_);
        double d0 = (double)(p_a_3_ * 16 + 8);
        double d1 = (double)(p_a_4_ * 16 + 8);
        float f = 0.0F;
        float f1 = 0.0F;

        if (p_a_16_ <= 0)
        {
            int i = this.a * 16 - 16;
            p_a_16_ = i - random.nextInt(i / 4);
        }

        boolean flag2 = false;

        if (p_a_15_ == -1)
        {
            p_a_15_ = p_a_16_ / 2;
            flag2 = true;
        }

        float f2 = 1.0F;

        for (int j = 0; j < 256; ++j)
        {
            if (j == 0 || random.nextInt(3) == 0)
            {
                f2 = 1.0F + random.nextFloat() * random.nextFloat() * 1.0F;
            }

            this.d[j] = f2 * f2;
        }

        for (; p_a_15_ < p_a_16_; ++p_a_15_)
        {
            double d2 = 1.5D + (double)(MathHelper.sin((float)p_a_15_ * (float)Math.PI / (float)p_a_16_) * p_a_12_ * 1.0F);
            double d3 = d2 * p_a_17_;
            d2 = d2 * ((double)random.nextFloat() * 0.25D + 0.75D);
            d3 = d3 * ((double)random.nextFloat() * 0.25D + 0.75D);
            float f3 = MathHelper.cos(p_a_14_);
            float f4 = MathHelper.sin(p_a_14_);
            p_a_6_ += (double)(MathHelper.cos(p_a_13_) * f3);
            p_a_8_ += (double)f4;
            p_a_10_ += (double)(MathHelper.sin(p_a_13_) * f3);
            p_a_14_ = p_a_14_ * 0.7F;
            p_a_14_ = p_a_14_ + f1 * 0.05F;
            p_a_13_ += f * 0.05F;
            f1 = f1 * 0.8F;
            f = f * 0.5F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if (flag2 || random.nextInt(4) != 0)
            {
                double d4 = p_a_6_ - d0;
                double d5 = p_a_10_ - d1;
                double d6 = (double)(p_a_16_ - p_a_15_);
                double d7 = (double)(p_a_12_ + 2.0F + 16.0F);

                if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7)
                {
                    return;
                }

                if (p_a_6_ >= d0 - 16.0D - d2 * 2.0D && p_a_10_ >= d1 - 16.0D - d2 * 2.0D && p_a_6_ <= d0 + 16.0D + d2 * 2.0D && p_a_10_ <= d1 + 16.0D + d2 * 2.0D)
                {
                    int k = MathHelper.floor(p_a_6_ - d2) - p_a_3_ * 16 - 1;
                    int l = MathHelper.floor(p_a_6_ + d2) - p_a_3_ * 16 + 1;
                    int i1 = MathHelper.floor(p_a_8_ - d3) - 1;
                    int j1 = MathHelper.floor(p_a_8_ + d3) + 1;
                    int k1 = MathHelper.floor(p_a_10_ - d2) - p_a_4_ * 16 - 1;
                    int l1 = MathHelper.floor(p_a_10_ + d2) - p_a_4_ * 16 + 1;

                    if (k < 0)
                    {
                        k = 0;
                    }

                    if (l > 16)
                    {
                        l = 16;
                    }

                    if (i1 < 1)
                    {
                        i1 = 1;
                    }

                    if (j1 > 248)
                    {
                        j1 = 248;
                    }

                    if (k1 < 0)
                    {
                        k1 = 0;
                    }

                    if (l1 > 16)
                    {
                        l1 = 16;
                    }

                    boolean flag = false;

                    for (int i2 = k; !flag && i2 < l; ++i2)
                    {
                        for (int j2 = k1; !flag && j2 < l1; ++j2)
                        {
                            for (int k2 = j1 + 1; !flag && k2 >= i1 - 1; --k2)
                            {
                                if (k2 >= 0 && k2 < 256)
                                {
                                    IBlockData iblockdata = p_a_5_.a(i2, k2, j2);

                                    if (iblockdata.getBlock() == Blocks.FLOWING_WATER || iblockdata.getBlock() == Blocks.WATER)
                                    {
                                        flag = true;
                                    }

                                    if (k2 != i1 - 1 && i2 != k && i2 != l - 1 && j2 != k1 && j2 != l1 - 1)
                                    {
                                        k2 = i1;
                                    }
                                }
                            }
                        }
                    }

                    if (!flag)
                    {
                        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                        for (int j3 = k; j3 < l; ++j3)
                        {
                            double d8 = ((double)(j3 + p_a_3_ * 16) + 0.5D - p_a_6_) / d2;

                            for (int l2 = k1; l2 < l1; ++l2)
                            {
                                double d9 = ((double)(l2 + p_a_4_ * 16) + 0.5D - p_a_10_) / d2;
                                boolean flag1 = false;

                                if (d8 * d8 + d9 * d9 < 1.0D)
                                {
                                    for (int i3 = j1; i3 > i1; --i3)
                                    {
                                        double d10 = ((double)(i3 - 1) + 0.5D - p_a_8_) / d3;

                                        if ((d8 * d8 + d9 * d9) * (double)this.d[i3 - 1] + d10 * d10 / 6.0D < 1.0D)
                                        {
                                            IBlockData iblockdata1 = p_a_5_.a(j3, i3, l2);

                                            if (iblockdata1.getBlock() == Blocks.GRASS)
                                            {
                                                flag1 = true;
                                            }

                                            if (iblockdata1.getBlock() == Blocks.STONE || iblockdata1.getBlock() == Blocks.DIRT || iblockdata1.getBlock() == Blocks.GRASS)
                                            {
                                                if (i3 - 1 < 10)
                                                {
                                                    p_a_5_.a(j3, i3, l2, Blocks.FLOWING_LAVA.getBlockData());
                                                }
                                                else
                                                {
                                                    p_a_5_.a(j3, i3, l2, Blocks.AIR.getBlockData());

                                                    if (flag1 && p_a_5_.a(j3, i3 - 1, l2).getBlock() == Blocks.DIRT)
                                                    {
                                                        blockposition$mutableblockposition.c(j3 + p_a_3_ * 16, 0, l2 + p_a_4_ * 16);
                                                        p_a_5_.a(j3, i3 - 1, l2, this.c.getBiome(blockposition$mutableblockposition).ak);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (flag2)
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    protected void a(World p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, ChunkSnapshot p_a_6_)
    {
        if (this.b.nextInt(50) == 0)
        {
            double d0 = (double)(p_a_2_ * 16 + this.b.nextInt(16));
            double d1 = (double)(this.b.nextInt(this.b.nextInt(40) + 8) + 20);
            double d2 = (double)(p_a_3_ * 16 + this.b.nextInt(16));
            byte b0 = 1;

            for (int i = 0; i < b0; ++i)
            {
                float f = this.b.nextFloat() * (float)Math.PI * 2.0F;
                float f1 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = (this.b.nextFloat() * 2.0F + this.b.nextFloat()) * 2.0F;
                this.a(this.b.nextLong(), p_a_4_, p_a_5_, p_a_6_, d0, d1, d2, f2, f, f1, 0, 0, 3.0D);
            }
        }
    }
}
