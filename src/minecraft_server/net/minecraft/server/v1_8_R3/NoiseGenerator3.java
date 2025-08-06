package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class NoiseGenerator3 extends NoiseGenerator
{
    private NoiseGenerator3Handler[] a;
    private int b;

    public NoiseGenerator3(Random p_i800_1_, int p_i800_2_)
    {
        this.b = p_i800_2_;
        this.a = new NoiseGenerator3Handler[p_i800_2_];

        for (int i = 0; i < p_i800_2_; ++i)
        {
            this.a[i] = new NoiseGenerator3Handler(p_i800_1_);
        }
    }

    public double a(double p_a_1_, double p_a_3_)
    {
        double d0 = 0.0D;
        double d1 = 1.0D;

        for (int i = 0; i < this.b; ++i)
        {
            d0 += this.a[i].a(p_a_1_ * d1, p_a_3_ * d1) / d1;
            d1 /= 2.0D;
        }

        return d0;
    }

    public double[] a(double[] p_a_1_, double p_a_2_, double p_a_4_, int p_a_6_, int p_a_7_, double p_a_8_, double p_a_10_, double p_a_12_)
    {
        return this.a(p_a_1_, p_a_2_, p_a_4_, p_a_6_, p_a_7_, p_a_8_, p_a_10_, p_a_12_, 0.5D);
    }

    public double[] a(double[] p_a_1_, double p_a_2_, double p_a_4_, int p_a_6_, int p_a_7_, double p_a_8_, double p_a_10_, double p_a_12_, double p_a_14_)
    {
        if (p_a_1_ != null && p_a_1_.length >= p_a_6_ * p_a_7_)
        {
            for (int i = 0; i < p_a_1_.length; ++i)
            {
                p_a_1_[i] = 0.0D;
            }
        }
        else
        {
            p_a_1_ = new double[p_a_6_ * p_a_7_];
        }

        double d0 = 1.0D;
        double d1 = 1.0D;

        for (int j = 0; j < this.b; ++j)
        {
            this.a[j].a(p_a_1_, p_a_2_, p_a_4_, p_a_6_, p_a_7_, p_a_8_ * d1 * d0, p_a_10_ * d1 * d0, 0.55D / d0);
            d1 *= p_a_12_;
            d0 *= p_a_14_;
        }

        return p_a_1_;
    }
}
