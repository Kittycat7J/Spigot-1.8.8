package net.minecraft.server.v1_8_R3;

public class GenLayerSpecial extends GenLayer
{
    private final GenLayerSpecial.EnumGenLayerSpecial c;

    public GenLayerSpecial(long p_i811_1_, GenLayer p_i811_3_, GenLayerSpecial.EnumGenLayerSpecial p_i811_4_)
    {
        super(p_i811_1_);
        this.a = p_i811_3_;
        this.c = p_i811_4_;
    }

    public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        switch (this.c)
        {
            case COOL_WARM:
            default:
                return this.c(p_a_1_, p_a_2_, p_a_3_, p_a_4_);

            case HEAT_ICE:
                return this.d(p_a_1_, p_a_2_, p_a_3_, p_a_4_);

            case SPECIAL:
                return this.e(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
        }
    }

    private int[] c(int p_c_1_, int p_c_2_, int p_c_3_, int p_c_4_)
    {
        int i = p_c_1_ - 1;
        int j = p_c_2_ - 1;
        int k = 1 + p_c_3_ + 1;
        int l = 1 + p_c_4_ + 1;
        int[] aint = this.a.a(i, j, k, l);
        int[] aint1 = IntCache.a(p_c_3_ * p_c_4_);

        for (int i1 = 0; i1 < p_c_4_; ++i1)
        {
            for (int j1 = 0; j1 < p_c_3_; ++j1)
            {
                this.a((long)(j1 + p_c_1_), (long)(i1 + p_c_2_));
                int k1 = aint[j1 + 1 + (i1 + 1) * k];

                if (k1 == 1)
                {
                    int l1 = aint[j1 + 1 + (i1 + 1 - 1) * k];
                    int i2 = aint[j1 + 1 + 1 + (i1 + 1) * k];
                    int j2 = aint[j1 + 1 - 1 + (i1 + 1) * k];
                    int k2 = aint[j1 + 1 + (i1 + 1 + 1) * k];
                    boolean flag = l1 == 3 || i2 == 3 || j2 == 3 || k2 == 3;
                    boolean flag1 = l1 == 4 || i2 == 4 || j2 == 4 || k2 == 4;

                    if (flag || flag1)
                    {
                        k1 = 2;
                    }
                }

                aint1[j1 + i1 * p_c_3_] = k1;
            }
        }

        return aint1;
    }

    private int[] d(int p_d_1_, int p_d_2_, int p_d_3_, int p_d_4_)
    {
        int i = p_d_1_ - 1;
        int j = p_d_2_ - 1;
        int k = 1 + p_d_3_ + 1;
        int l = 1 + p_d_4_ + 1;
        int[] aint = this.a.a(i, j, k, l);
        int[] aint1 = IntCache.a(p_d_3_ * p_d_4_);

        for (int i1 = 0; i1 < p_d_4_; ++i1)
        {
            for (int j1 = 0; j1 < p_d_3_; ++j1)
            {
                int k1 = aint[j1 + 1 + (i1 + 1) * k];

                if (k1 == 4)
                {
                    int l1 = aint[j1 + 1 + (i1 + 1 - 1) * k];
                    int i2 = aint[j1 + 1 + 1 + (i1 + 1) * k];
                    int j2 = aint[j1 + 1 - 1 + (i1 + 1) * k];
                    int k2 = aint[j1 + 1 + (i1 + 1 + 1) * k];
                    boolean flag = l1 == 2 || i2 == 2 || j2 == 2 || k2 == 2;
                    boolean flag1 = l1 == 1 || i2 == 1 || j2 == 1 || k2 == 1;

                    if (flag1 || flag)
                    {
                        k1 = 3;
                    }
                }

                aint1[j1 + i1 * p_d_3_] = k1;
            }
        }

        return aint1;
    }

    private int[] e(int p_e_1_, int p_e_2_, int p_e_3_, int p_e_4_)
    {
        int[] aint = this.a.a(p_e_1_, p_e_2_, p_e_3_, p_e_4_);
        int[] aint1 = IntCache.a(p_e_3_ * p_e_4_);

        for (int i = 0; i < p_e_4_; ++i)
        {
            for (int j = 0; j < p_e_3_; ++j)
            {
                this.a((long)(j + p_e_1_), (long)(i + p_e_2_));
                int k = aint[j + i * p_e_3_];

                if (k != 0 && this.a(13) == 0)
                {
                    k |= 1 + this.a(15) << 8 & 3840;
                }

                aint1[j + i * p_e_3_] = k;
            }
        }

        return aint1;
    }

    public static enum EnumGenLayerSpecial
    {
        COOL_WARM,
        HEAT_ICE,
        SPECIAL;
    }
}
