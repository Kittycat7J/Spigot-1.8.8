package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenBase
{
    protected int a = 8;
    protected Random b = new Random();
    protected World c;

    public void a(IChunkProvider p_a_1_, World p_a_2_, int p_a_3_, int p_a_4_, ChunkSnapshot p_a_5_)
    {
        int i = this.a;
        this.c = p_a_2_;
        this.b.setSeed(p_a_2_.getSeed());
        long j = this.b.nextLong();
        long k = this.b.nextLong();

        for (int l = p_a_3_ - i; l <= p_a_3_ + i; ++l)
        {
            for (int i1 = p_a_4_ - i; i1 <= p_a_4_ + i; ++i1)
            {
                long j1 = (long)l * j;
                long k1 = (long)i1 * k;
                this.b.setSeed(j1 ^ k1 ^ p_a_2_.getSeed());
                this.a(p_a_2_, l, i1, p_a_3_, p_a_4_, p_a_5_);
            }
        }
    }

    protected void a(World p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, ChunkSnapshot p_a_6_)
    {
    }
}
