package net.minecraft.server.v1_8_R3;

import java.util.Random;
import java.util.UUID;

public class MathHelper
{
    public static final float a = c(2.0F);
    private static final float[] b = new float[65536];
    private static final int[] c;
    private static final double d;
    private static final double[] e;
    private static final double[] f;

    public static float sin(float p_sin_0_)
    {
        return b[(int)(p_sin_0_ * 10430.378F) & 65535];
    }

    public static float cos(float p_cos_0_)
    {
        return b[(int)(p_cos_0_ * 10430.378F + 16384.0F) & 65535];
    }

    public static float c(float p_c_0_)
    {
        return (float)Math.sqrt((double)p_c_0_);
    }

    public static float sqrt(double p_sqrt_0_)
    {
        return (float)Math.sqrt(p_sqrt_0_);
    }

    public static int d(float p_d_0_)
    {
        int i = (int)p_d_0_;
        return p_d_0_ < (float)i ? i - 1 : i;
    }

    public static int floor(double p_floor_0_)
    {
        int i = (int)p_floor_0_;
        return p_floor_0_ < (double)i ? i - 1 : i;
    }

    public static long d(double p_d_0_)
    {
        long i = (long)p_d_0_;
        return p_d_0_ < (double)i ? i - 1L : i;
    }

    public static float e(float p_e_0_)
    {
        return p_e_0_ >= 0.0F ? p_e_0_ : -p_e_0_;
    }

    public static int a(int p_a_0_)
    {
        return p_a_0_ >= 0 ? p_a_0_ : -p_a_0_;
    }

    public static int f(float p_f_0_)
    {
        int i = (int)p_f_0_;
        return p_f_0_ > (float)i ? i + 1 : i;
    }

    public static int f(double p_f_0_)
    {
        int i = (int)p_f_0_;
        return p_f_0_ > (double)i ? i + 1 : i;
    }

    public static int clamp(int p_clamp_0_, int p_clamp_1_, int p_clamp_2_)
    {
        return p_clamp_0_ < p_clamp_1_ ? p_clamp_1_ : (p_clamp_0_ > p_clamp_2_ ? p_clamp_2_ : p_clamp_0_);
    }

    public static float a(float p_a_0_, float p_a_1_, float p_a_2_)
    {
        return p_a_0_ < p_a_1_ ? p_a_1_ : (p_a_0_ > p_a_2_ ? p_a_2_ : p_a_0_);
    }

    public static double a(double p_a_0_, double p_a_2_, double p_a_4_)
    {
        return p_a_0_ < p_a_2_ ? p_a_2_ : (p_a_0_ > p_a_4_ ? p_a_4_ : p_a_0_);
    }

    public static double b(double p_b_0_, double p_b_2_, double p_b_4_)
    {
        return p_b_4_ < 0.0D ? p_b_0_ : (p_b_4_ > 1.0D ? p_b_2_ : p_b_0_ + (p_b_2_ - p_b_0_) * p_b_4_);
    }

    public static double a(double p_a_0_, double p_a_2_)
    {
        if (p_a_0_ < 0.0D)
        {
            p_a_0_ = -p_a_0_;
        }

        if (p_a_2_ < 0.0D)
        {
            p_a_2_ = -p_a_2_;
        }

        return p_a_0_ > p_a_2_ ? p_a_0_ : p_a_2_;
    }

    public static int nextInt(Random p_nextInt_0_, int p_nextInt_1_, int p_nextInt_2_)
    {
        return p_nextInt_1_ >= p_nextInt_2_ ? p_nextInt_1_ : p_nextInt_0_.nextInt(p_nextInt_2_ - p_nextInt_1_ + 1) + p_nextInt_1_;
    }

    public static float a(Random p_a_0_, float p_a_1_, float p_a_2_)
    {
        return p_a_1_ >= p_a_2_ ? p_a_1_ : p_a_0_.nextFloat() * (p_a_2_ - p_a_1_) + p_a_1_;
    }

    public static double a(Random p_a_0_, double p_a_1_, double p_a_3_)
    {
        return p_a_1_ >= p_a_3_ ? p_a_1_ : p_a_0_.nextDouble() * (p_a_3_ - p_a_1_) + p_a_1_;
    }

    public static double a(long[] p_a_0_)
    {
        long i = 0L;

        for (long j : p_a_0_)
        {
            i += j;
        }

        return (double)i / (double)p_a_0_.length;
    }

    public static float g(float p_g_0_)
    {
        p_g_0_ = p_g_0_ % 360.0F;

        if (p_g_0_ >= 180.0F)
        {
            p_g_0_ -= 360.0F;
        }

        if (p_g_0_ < -180.0F)
        {
            p_g_0_ += 360.0F;
        }

        return p_g_0_;
    }

    public static double g(double p_g_0_)
    {
        p_g_0_ = p_g_0_ % 360.0D;

        if (p_g_0_ >= 180.0D)
        {
            p_g_0_ -= 360.0D;
        }

        if (p_g_0_ < -180.0D)
        {
            p_g_0_ += 360.0D;
        }

        return p_g_0_;
    }

    public static int a(String p_a_0_, int p_a_1_)
    {
        try
        {
            return Integer.parseInt(p_a_0_);
        }
        catch (Throwable var3)
        {
            return p_a_1_;
        }
    }

    public static int a(String p_a_0_, int p_a_1_, int p_a_2_)
    {
        return Math.max(p_a_2_, a(p_a_0_, p_a_1_));
    }

    public static double a(String p_a_0_, double p_a_1_)
    {
        try
        {
            return Double.parseDouble(p_a_0_);
        }
        catch (Throwable var4)
        {
            return p_a_1_;
        }
    }

    public static double a(String p_a_0_, double p_a_1_, double p_a_3_)
    {
        return Math.max(p_a_3_, a(p_a_0_, p_a_1_));
    }

    public static int b(int p_b_0_)
    {
        int i = p_b_0_ - 1;
        i = i | i >> 1;
        i = i | i >> 2;
        i = i | i >> 4;
        i = i | i >> 8;
        i = i | i >> 16;
        return i + 1;
    }

    private static boolean d(int p_d_0_)
    {
        return p_d_0_ != 0 && (p_d_0_ & p_d_0_ - 1) == 0;
    }

    private static int e(int p_e_0_)
    {
        p_e_0_ = d(p_e_0_) ? p_e_0_ : b(p_e_0_);
        return c[(int)((long)p_e_0_ * 125613361L >> 27) & 31];
    }

    public static int c(int p_c_0_)
    {
        return e(p_c_0_) - (d(p_c_0_) ? 0 : 1);
    }

    public static int c(int p_c_0_, int p_c_1_)
    {
        if (p_c_1_ == 0)
        {
            return 0;
        }
        else if (p_c_0_ == 0)
        {
            return p_c_1_;
        }
        else
        {
            if (p_c_0_ < 0)
            {
                p_c_1_ *= -1;
            }

            int i = p_c_0_ % p_c_1_;
            return i == 0 ? p_c_0_ : p_c_0_ + p_c_1_ - i;
        }
    }

    public static UUID a(Random p_a_0_)
    {
        long i = p_a_0_.nextLong() & -61441L | 16384L;
        long j = p_a_0_.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
        return new UUID(i, j);
    }

    public static double c(double p_c_0_, double p_c_2_, double p_c_4_)
    {
        return (p_c_0_ - p_c_2_) / (p_c_4_ - p_c_2_);
    }

    public static double b(double p_b_0_, double p_b_2_)
    {
        double d0 = p_b_2_ * p_b_2_ + p_b_0_ * p_b_0_;

        if (Double.isNaN(d0))
        {
            return Double.NaN;
        }
        else
        {
            boolean flag = p_b_0_ < 0.0D;

            if (flag)
            {
                p_b_0_ = -p_b_0_;
            }

            boolean flag1 = p_b_2_ < 0.0D;

            if (flag1)
            {
                p_b_2_ = -p_b_2_;
            }

            boolean flag2 = p_b_0_ > p_b_2_;

            if (flag2)
            {
                double d1 = p_b_2_;
                p_b_2_ = p_b_0_;
                p_b_0_ = d1;
            }

            double d9 = i(d0);
            p_b_2_ = p_b_2_ * d9;
            p_b_0_ = p_b_0_ * d9;
            double d2 = d + p_b_0_;
            int i = (int)Double.doubleToRawLongBits(d2);
            double d3 = e[i];
            double d4 = f[i];
            double d5 = d2 - d;
            double d6 = p_b_0_ * d4 - p_b_2_ * d5;
            double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
            double d8 = d3 + d7;

            if (flag2)
            {
                d8 = (Math.PI / 2D) - d8;
            }

            if (flag1)
            {
                d8 = Math.PI - d8;
            }

            if (flag)
            {
                d8 = -d8;
            }

            return d8;
        }
    }

    public static double i(double p_i_0_)
    {
        double d0 = 0.5D * p_i_0_;
        long i = Double.doubleToRawLongBits(p_i_0_);
        i = 6910469410427058090L - (i >> 1);
        p_i_0_ = Double.longBitsToDouble(i);
        p_i_0_ = p_i_0_ * (1.5D - d0 * p_i_0_ * p_i_0_);
        return p_i_0_;
    }

    static
    {
        for (int i = 0; i < 65536; ++i)
        {
            b[i] = (float)Math.sin((double)i * Math.PI * 2.0D / 65536.0D);
        }

        c = new int[] {0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
        d = Double.longBitsToDouble(4805340802404319232L);
        e = new double[257];
        f = new double[257];

        for (int j = 0; j < 257; ++j)
        {
            double d0 = (double)j / 256.0D;
            double d1 = Math.asin(d0);
            f[j] = Math.cos(d1);
            e[j] = d1;
        }
    }
}
