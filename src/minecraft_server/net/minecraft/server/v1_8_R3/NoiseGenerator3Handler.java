package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class NoiseGenerator3Handler
{
    private static int[][] e = new int[][] {{1, 1, 0}, { -1, 1, 0}, {1, -1, 0}, { -1, -1, 0}, {1, 0, 1}, { -1, 0, 1}, {1, 0, -1}, { -1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}};
    public static final double a = Math.sqrt(3.0D);
    private int[] f;
    public double b;
    public double c;
    public double d;
    private static final double g = 0.5D * (a - 1.0D);
    private static final double h = (3.0D - a) / 6.0D;

    public NoiseGenerator3Handler()
    {
        this(new Random());
    }

    public NoiseGenerator3Handler(Random p_i801_1_)
    {
        this.f = new int[512];
        this.b = p_i801_1_.nextDouble() * 256.0D;
        this.c = p_i801_1_.nextDouble() * 256.0D;
        this.d = p_i801_1_.nextDouble() * 256.0D;

        for (int i = 0; i < 256; this.f[i] = i++)
        {
            ;
        }

        for (int l = 0; l < 256; ++l)
        {
            int j = p_i801_1_.nextInt(256 - l) + l;
            int k = this.f[l];
            this.f[l] = this.f[j];
            this.f[j] = k;
            this.f[l + 256] = this.f[l];
        }
    }

    private static int a(double p_a_0_)
    {
        return p_a_0_ > 0.0D ? (int)p_a_0_ : (int)p_a_0_ - 1;
    }

    private static double a(int[] p_a_0_, double p_a_1_, double p_a_3_)
    {
        return (double)p_a_0_[0] * p_a_1_ + (double)p_a_0_[1] * p_a_3_;
    }

    public double a(double p_a_1_, double p_a_3_)
    {
        double d0 = 0.5D * (a - 1.0D);
        double d1 = (p_a_1_ + p_a_3_) * d0;
        int i = a(p_a_1_ + d1);
        int j = a(p_a_3_ + d1);
        double d2 = (3.0D - a) / 6.0D;
        double d3 = (double)(i + j) * d2;
        double d4 = (double)i - d3;
        double d5 = (double)j - d3;
        double d6 = p_a_1_ - d4;
        double d7 = p_a_3_ - d5;
        byte b0;
        byte b1;

        if (d6 > d7)
        {
            b0 = 1;
            b1 = 0;
        }
        else
        {
            b0 = 0;
            b1 = 1;
        }

        double d8 = d6 - (double)b0 + d2;
        double d9 = d7 - (double)b1 + d2;
        double d10 = d6 - 1.0D + 2.0D * d2;
        double d11 = d7 - 1.0D + 2.0D * d2;
        int k = i & 255;
        int l = j & 255;
        int i1 = this.f[k + this.f[l]] % 12;
        int j1 = this.f[k + b0 + this.f[l + b1]] % 12;
        int k1 = this.f[k + 1 + this.f[l + 1]] % 12;
        double d12 = 0.5D - d6 * d6 - d7 * d7;
        double d13;

        if (d12 < 0.0D)
        {
            d13 = 0.0D;
        }
        else
        {
            d12 = d12 * d12;
            d13 = d12 * d12 * a(e[i1], d6, d7);
        }

        double d14 = 0.5D - d8 * d8 - d9 * d9;
        double d15;

        if (d14 < 0.0D)
        {
            d15 = 0.0D;
        }
        else
        {
            d14 = d14 * d14;
            d15 = d14 * d14 * a(e[j1], d8, d9);
        }

        double d16 = 0.5D - d10 * d10 - d11 * d11;
        double d17;

        if (d16 < 0.0D)
        {
            d17 = 0.0D;
        }
        else
        {
            d16 = d16 * d16;
            d17 = d16 * d16 * a(e[k1], d10, d11);
        }

        return 70.0D * (d13 + d15 + d17);
    }

    public void a(double[] p_a_1_, double p_a_2_, double p_a_4_, int p_a_6_, int p_a_7_, double p_a_8_, double p_a_10_, double p_a_12_)
    {
        int i = 0;

        for (int j = 0; j < p_a_7_; ++j)
        {
            double d0 = (p_a_4_ + (double)j) * p_a_10_ + this.c;

            for (int k = 0; k < p_a_6_; ++k)
            {
                double d1 = (p_a_2_ + (double)k) * p_a_8_ + this.b;
                double d2 = (d1 + d0) * g;
                int l = a(d1 + d2);
                int i1 = a(d0 + d2);
                double d3 = (double)(l + i1) * h;
                double d4 = (double)l - d3;
                double d5 = (double)i1 - d3;
                double d6 = d1 - d4;
                double d7 = d0 - d5;
                byte b0;
                byte b1;

                if (d6 > d7)
                {
                    b0 = 1;
                    b1 = 0;
                }
                else
                {
                    b0 = 0;
                    b1 = 1;
                }

                double d8 = d6 - (double)b0 + h;
                double d9 = d7 - (double)b1 + h;
                double d10 = d6 - 1.0D + 2.0D * h;
                double d11 = d7 - 1.0D + 2.0D * h;
                int j1 = l & 255;
                int k1 = i1 & 255;
                int l1 = this.f[j1 + this.f[k1]] % 12;
                int i2 = this.f[j1 + b0 + this.f[k1 + b1]] % 12;
                int j2 = this.f[j1 + 1 + this.f[k1 + 1]] % 12;
                double d12 = 0.5D - d6 * d6 - d7 * d7;
                double d13;

                if (d12 < 0.0D)
                {
                    d13 = 0.0D;
                }
                else
                {
                    d12 = d12 * d12;
                    d13 = d12 * d12 * a(e[l1], d6, d7);
                }

                double d14 = 0.5D - d8 * d8 - d9 * d9;
                double d15;

                if (d14 < 0.0D)
                {
                    d15 = 0.0D;
                }
                else
                {
                    d14 = d14 * d14;
                    d15 = d14 * d14 * a(e[i2], d8, d9);
                }

                double d16 = 0.5D - d10 * d10 - d11 * d11;
                double d17;

                if (d16 < 0.0D)
                {
                    d17 = 0.0D;
                }
                else
                {
                    d16 = d16 * d16;
                    d17 = d16 * d16 * a(e[j2], d10, d11);
                }

                int k2 = i++;
                p_a_1_[k2] += 70.0D * (d13 + d15 + d17) * p_a_12_;
            }
        }
    }
}
