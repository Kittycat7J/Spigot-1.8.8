package net.minecraft.server.v1_8_R3;

public class GenLayerIcePlains extends GenLayer
{
    public GenLayerIcePlains(long p_i824_1_, GenLayer p_i824_3_)
    {
        super(p_i824_1_);
        this.a = p_i824_3_;
    }

    public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        int i = p_a_1_ - 1;
        int j = p_a_2_ - 1;
        int k = p_a_3_ + 2;
        int l = p_a_4_ + 2;
        int[] aint = this.a.a(i, j, k, l);
        int[] aint1 = IntCache.a(p_a_3_ * p_a_4_);

        for (int i1 = 0; i1 < p_a_4_; ++i1)
        {
            for (int j1 = 0; j1 < p_a_3_; ++j1)
            {
                int k1 = aint[j1 + 1 + (i1 + 1 - 1) * (p_a_3_ + 2)];
                int l1 = aint[j1 + 1 + 1 + (i1 + 1) * (p_a_3_ + 2)];
                int i2 = aint[j1 + 1 - 1 + (i1 + 1) * (p_a_3_ + 2)];
                int j2 = aint[j1 + 1 + (i1 + 1 + 1) * (p_a_3_ + 2)];
                int k2 = aint[j1 + 1 + (i1 + 1) * k];
                aint1[j1 + i1 * p_a_3_] = k2;
                this.a((long)(j1 + p_a_1_), (long)(i1 + p_a_2_));

                if (k2 == 0 && k1 == 0 && l1 == 0 && i2 == 0 && j2 == 0 && this.a(2) == 0)
                {
                    aint1[j1 + i1 * p_a_3_] = 1;
                }
            }
        }

        return aint1;
    }
}
