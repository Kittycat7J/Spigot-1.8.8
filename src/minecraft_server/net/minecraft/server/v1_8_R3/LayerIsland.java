package net.minecraft.server.v1_8_R3;

public class LayerIsland extends GenLayer
{
    public LayerIsland(long p_i818_1_)
    {
        super(p_i818_1_);
    }

    public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        int[] aint = IntCache.a(p_a_3_ * p_a_4_);

        for (int i = 0; i < p_a_4_; ++i)
        {
            for (int j = 0; j < p_a_3_; ++j)
            {
                this.a((long)(p_a_1_ + j), (long)(p_a_2_ + i));
                aint[j + i * p_a_3_] = this.a(10) == 0 ? 1 : 0;
            }
        }

        if (p_a_1_ > -p_a_3_ && p_a_1_ <= 0 && p_a_2_ > -p_a_4_ && p_a_2_ <= 0)
        {
            aint[-p_a_1_ + -p_a_2_ * p_a_3_] = 1;
        }

        return aint;
    }
}
