package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class NoiseGeneratorOctaves extends NoiseGenerator
{
    private NoiseGeneratorPerlin[] a;
    private int b;

    public NoiseGeneratorOctaves(Random p_i799_1_, int p_i799_2_)
    {
        this.b = p_i799_2_;
        this.a = new NoiseGeneratorPerlin[p_i799_2_];

        for (int i = 0; i < p_i799_2_; ++i)
        {
            this.a[i] = new NoiseGeneratorPerlin(p_i799_1_);
        }
    }

    public double[] a(double[] p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, double p_a_8_, double p_a_10_, double p_a_12_)
    {
        if (p_a_1_ == null)
        {
            p_a_1_ = new double[p_a_5_ * p_a_6_ * p_a_7_];
        }
        else
        {
            for (int i = 0; i < p_a_1_.length; ++i)
            {
                p_a_1_[i] = 0.0D;
            }
        }

        double d0 = 1.0D;

        for (int j = 0; j < this.b; ++j)
        {
            double d1 = (double)p_a_2_ * d0 * p_a_8_;
            double d2 = (double)p_a_3_ * d0 * p_a_10_;
            double d3 = (double)p_a_4_ * d0 * p_a_12_;
            long k = MathHelper.d(d1);
            long l = MathHelper.d(d3);
            d1 = d1 - (double)k;
            d3 = d3 - (double)l;
            k = k % 16777216L;
            l = l % 16777216L;
            d1 = d1 + (double)k;
            d3 = d3 + (double)l;
            this.a[j].a(p_a_1_, d1, d2, d3, p_a_5_, p_a_6_, p_a_7_, p_a_8_ * d0, p_a_10_ * d0, p_a_12_ * d0, d0);
            d0 /= 2.0D;
        }

        return p_a_1_;
    }

    public double[] a(double[] p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, double p_a_6_, double p_a_8_, double p_a_10_)
    {
        return this.a(p_a_1_, p_a_2_, 10, p_a_3_, p_a_4_, 1, p_a_5_, p_a_6_, 1.0D, p_a_8_);
    }
}
