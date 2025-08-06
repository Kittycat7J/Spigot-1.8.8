package net.minecraft.server.v1_8_R3;

public class GenLayerPlains extends GenLayer
{
    public GenLayerPlains(long p_i822_1_, GenLayer p_i822_3_)
    {
        super(p_i822_1_);
        this.a = p_i822_3_;
    }

    public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        int[] aint = this.a.a(p_a_1_ - 1, p_a_2_ - 1, p_a_3_ + 2, p_a_4_ + 2);
        int[] aint1 = IntCache.a(p_a_3_ * p_a_4_);

        for (int i = 0; i < p_a_4_; ++i)
        {
            for (int j = 0; j < p_a_3_; ++j)
            {
                this.a((long)(j + p_a_1_), (long)(i + p_a_2_));
                int k = aint[j + 1 + (i + 1) * (p_a_3_ + 2)];

                if (this.a(57) == 0)
                {
                    if (k == BiomeBase.PLAINS.id)
                    {
                        aint1[j + i * p_a_3_] = BiomeBase.PLAINS.id + 128;
                    }
                    else
                    {
                        aint1[j + i * p_a_3_] = k;
                    }
                }
                else
                {
                    aint1[j + i * p_a_3_] = k;
                }
            }
        }

        return aint1;
    }
}
