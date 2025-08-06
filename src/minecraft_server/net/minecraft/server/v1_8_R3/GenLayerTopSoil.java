package net.minecraft.server.v1_8_R3;

public class GenLayerTopSoil extends GenLayer
{
    public GenLayerTopSoil(long p_i814_1_, GenLayer p_i814_3_)
    {
        super(p_i814_1_);
        this.a = p_i814_3_;
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
                int k1 = aint[j1 + 1 + (i1 + 1) * k];
                this.a((long)(j1 + p_a_1_), (long)(i1 + p_a_2_));

                if (k1 == 0)
                {
                    aint1[j1 + i1 * p_a_3_] = 0;
                }
                else
                {
                    int l1 = this.a(6);

                    if (l1 == 0)
                    {
                        l1 = 4;
                    }
                    else if (l1 <= 1)
                    {
                        l1 = 3;
                    }
                    else
                    {
                        l1 = 1;
                    }

                    aint1[j1 + i1 * p_a_3_] = l1;
                }
            }
        }

        return aint1;
    }
}
