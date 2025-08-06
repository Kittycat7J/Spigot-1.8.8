package net.minecraft.server.v1_8_R3;

import com.google.common.base.Objects;
import java.util.Random;

public class WorldGenCaves extends WorldGenBase
{
    protected void a(long p_a_1_, int p_a_3_, int p_a_4_, ChunkSnapshot p_a_5_, double p_a_6_, double p_a_8_, double p_a_10_)
    {
        this.a(p_a_1_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_8_, p_a_10_, 1.0F + this.b.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
    }

    protected void a(long p_a_1_, int p_a_3_, int p_a_4_, ChunkSnapshot p_a_5_, double p_a_6_, double p_a_8_, double p_a_10_, float p_a_12_, float p_a_13_, float p_a_14_, int p_a_15_, int p_a_16_, double p_a_17_)
    {
        double d0 = (double)(p_a_3_ * 16 + 8);
        double d1 = (double)(p_a_4_ * 16 + 8);
        float f = 0.0F;
        float f1 = 0.0F;
        Random random = new Random(p_a_1_);

        if (p_a_16_ <= 0)
        {
            int i = this.a * 16 - 16;
            p_a_16_ = i - random.nextInt(i / 4);
        }

        boolean flag3 = false;

        if (p_a_15_ == -1)
        {
            p_a_15_ = p_a_16_ / 2;
            flag3 = true;
        }

        int j = random.nextInt(p_a_16_ / 2) + p_a_16_ / 4;

        for (boolean flag = random.nextInt(6) == 0; p_a_15_ < p_a_16_; ++p_a_15_)
        {
            double d2 = 1.5D + (double)(MathHelper.sin((float)p_a_15_ * (float)Math.PI / (float)p_a_16_) * p_a_12_ * 1.0F);
            double d3 = d2 * p_a_17_;
            float f2 = MathHelper.cos(p_a_14_);
            float f3 = MathHelper.sin(p_a_14_);
            p_a_6_ += (double)(MathHelper.cos(p_a_13_) * f2);
            p_a_8_ += (double)f3;
            p_a_10_ += (double)(MathHelper.sin(p_a_13_) * f2);

            if (flag)
            {
                p_a_14_ = p_a_14_ * 0.92F;
            }
            else
            {
                p_a_14_ = p_a_14_ * 0.7F;
            }

            p_a_14_ = p_a_14_ + f1 * 0.1F;
            p_a_13_ += f * 0.1F;
            f1 = f1 * 0.9F;
            f = f * 0.75F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if (!flag3 && p_a_15_ == j && p_a_12_ > 1.0F && p_a_16_ > 0)
            {
                this.a(random.nextLong(), p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_8_, p_a_10_, random.nextFloat() * 0.5F + 0.5F, p_a_13_ - ((float)Math.PI / 2F), p_a_14_ / 3.0F, p_a_15_, p_a_16_, 1.0D);
                this.a(random.nextLong(), p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_8_, p_a_10_, random.nextFloat() * 0.5F + 0.5F, p_a_13_ + ((float)Math.PI / 2F), p_a_14_ / 3.0F, p_a_15_, p_a_16_, 1.0D);
                return;
            }

            if (flag3 || random.nextInt(4) != 0)
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

                    boolean flag1 = false;

                    for (int i2 = k; !flag1 && i2 < l; ++i2)
                    {
                        for (int j2 = k1; !flag1 && j2 < l1; ++j2)
                        {
                            for (int k2 = j1 + 1; !flag1 && k2 >= i1 - 1; --k2)
                            {
                                if (k2 >= 0 && k2 < 256)
                                {
                                    IBlockData iblockdata = p_a_5_.a(i2, k2, j2);

                                    if (iblockdata.getBlock() == Blocks.FLOWING_WATER || iblockdata.getBlock() == Blocks.WATER)
                                    {
                                        flag1 = true;
                                    }

                                    if (k2 != i1 - 1 && i2 != k && i2 != l - 1 && j2 != k1 && j2 != l1 - 1)
                                    {
                                        k2 = i1;
                                    }
                                }
                            }
                        }
                    }

                    if (!flag1)
                    {
                        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                        for (int j3 = k; j3 < l; ++j3)
                        {
                            double d8 = ((double)(j3 + p_a_3_ * 16) + 0.5D - p_a_6_) / d2;

                            for (int l2 = k1; l2 < l1; ++l2)
                            {
                                double d9 = ((double)(l2 + p_a_4_ * 16) + 0.5D - p_a_10_) / d2;
                                boolean flag2 = false;

                                if (d8 * d8 + d9 * d9 < 1.0D)
                                {
                                    for (int i3 = j1; i3 > i1; --i3)
                                    {
                                        double d10 = ((double)(i3 - 1) + 0.5D - p_a_8_) / d3;

                                        if (d10 > -0.7D && d8 * d8 + d10 * d10 + d9 * d9 < 1.0D)
                                        {
                                            IBlockData iblockdata1 = p_a_5_.a(j3, i3, l2);
                                            IBlockData iblockdata2 = (IBlockData)Objects.firstNonNull(p_a_5_.a(j3, i3 + 1, l2), Blocks.AIR.getBlockData());

                                            if (iblockdata1.getBlock() == Blocks.GRASS || iblockdata1.getBlock() == Blocks.MYCELIUM)
                                            {
                                                flag2 = true;
                                            }

                                            if (this.a(iblockdata1, iblockdata2))
                                            {
                                                if (i3 - 1 < 10)
                                                {
                                                    p_a_5_.a(j3, i3, l2, Blocks.LAVA.getBlockData());
                                                }
                                                else
                                                {
                                                    p_a_5_.a(j3, i3, l2, Blocks.AIR.getBlockData());

                                                    if (iblockdata2.getBlock() == Blocks.SAND)
                                                    {
                                                        p_a_5_.a(j3, i3 + 1, l2, iblockdata2.get(BlockSand.VARIANT) == BlockSand.EnumSandVariant.RED_SAND ? Blocks.RED_SANDSTONE.getBlockData() : Blocks.SANDSTONE.getBlockData());
                                                    }

                                                    if (flag2 && p_a_5_.a(j3, i3 - 1, l2).getBlock() == Blocks.DIRT)
                                                    {
                                                        blockposition$mutableblockposition.c(j3 + p_a_3_ * 16, 0, l2 + p_a_4_ * 16);
                                                        p_a_5_.a(j3, i3 - 1, l2, this.c.getBiome(blockposition$mutableblockposition).ak.getBlock().getBlockData());
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (flag3)
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    protected boolean a(IBlockData p_a_1_, IBlockData p_a_2_)
    {
        return p_a_1_.getBlock() == Blocks.STONE ? true : (p_a_1_.getBlock() == Blocks.DIRT ? true : (p_a_1_.getBlock() == Blocks.GRASS ? true : (p_a_1_.getBlock() == Blocks.HARDENED_CLAY ? true : (p_a_1_.getBlock() == Blocks.STAINED_HARDENED_CLAY ? true : (p_a_1_.getBlock() == Blocks.SANDSTONE ? true : (p_a_1_.getBlock() == Blocks.RED_SANDSTONE ? true : (p_a_1_.getBlock() == Blocks.MYCELIUM ? true : (p_a_1_.getBlock() == Blocks.SNOW_LAYER ? true : (p_a_1_.getBlock() == Blocks.SAND || p_a_1_.getBlock() == Blocks.GRAVEL) && p_a_2_.getBlock().getMaterial() != Material.WATER))))))));
    }

    protected void a(World p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, ChunkSnapshot p_a_6_)
    {
        int i = this.b.nextInt(this.b.nextInt(this.b.nextInt(15) + 1) + 1);

        if (this.b.nextInt(7) != 0)
        {
            i = 0;
        }

        for (int j = 0; j < i; ++j)
        {
            double d0 = (double)(p_a_2_ * 16 + this.b.nextInt(16));
            double d1 = (double)this.b.nextInt(this.b.nextInt(120) + 8);
            double d2 = (double)(p_a_3_ * 16 + this.b.nextInt(16));
            int k = 1;

            if (this.b.nextInt(4) == 0)
            {
                this.a(this.b.nextLong(), p_a_4_, p_a_5_, p_a_6_, d0, d1, d2);
                k += this.b.nextInt(4);
            }

            for (int l = 0; l < k; ++l)
            {
                float f = this.b.nextFloat() * (float)Math.PI * 2.0F;
                float f1 = (this.b.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = this.b.nextFloat() * 2.0F + this.b.nextFloat();

                if (this.b.nextInt(10) == 0)
                {
                    f2 *= this.b.nextFloat() * this.b.nextFloat() * 3.0F + 1.0F;
                }

                this.a(this.b.nextLong(), p_a_4_, p_a_5_, p_a_6_, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
            }
        }
    }
}
