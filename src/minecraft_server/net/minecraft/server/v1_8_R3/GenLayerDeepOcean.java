package net.minecraft.server.v1_8_R3;

public class GenLayerDeepOcean extends GenLayer
{
    public GenLayerDeepOcean(long p_i809_1_, GenLayer p_i809_3_)
    {
        super(p_i809_1_);
        this.a = p_i809_3_;
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
                int l2 = 0;

                if (k1 == 0)
                {
                    ++l2;
                }

                if (l1 == 0)
                {
                    ++l2;
                }

                if (i2 == 0)
                {
                    ++l2;
                }

                if (j2 == 0)
                {
                    ++l2;
                }

                if (k2 == 0 && l2 > 3)
                {
                    aint1[j1 + i1 * p_a_3_] = BiomeBase.DEEP_OCEAN.id;
                }
                else
                {
                    aint1[j1 + i1 * p_a_3_] = k2;
                }
            }
        }

        return aint1;
    }
}
