package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;

public class BiomeCache
{
    private final WorldChunkManager a;
    private long b;
    private LongHashMap<BiomeCache.BiomeCacheBlock> c = new LongHashMap();
    private List<BiomeCache.BiomeCacheBlock> d = Lists.<BiomeCache.BiomeCacheBlock>newArrayList();

    public BiomeCache(WorldChunkManager p_i568_1_)
    {
        this.a = p_i568_1_;
    }

    public BiomeCache.BiomeCacheBlock a(int p_a_1_, int p_a_2_)
    {
        p_a_1_ = p_a_1_ >> 4;
        p_a_2_ = p_a_2_ >> 4;
        long i = (long)p_a_1_ & 4294967295L | ((long)p_a_2_ & 4294967295L) << 32;
        BiomeCache.BiomeCacheBlock biomecache$biomecacheblock = (BiomeCache.BiomeCacheBlock)this.c.getEntry(i);

        if (biomecache$biomecacheblock == null)
        {
            biomecache$biomecacheblock = new BiomeCache.BiomeCacheBlock(p_a_1_, p_a_2_);
            this.c.put(i, biomecache$biomecacheblock);
            this.d.add(biomecache$biomecacheblock);
        }

        biomecache$biomecacheblock.e = MinecraftServer.az();
        return biomecache$biomecacheblock;
    }

    public BiomeBase a(int p_a_1_, int p_a_2_, BiomeBase p_a_3_)
    {
        BiomeBase biomebase = this.a(p_a_1_, p_a_2_).a(p_a_1_, p_a_2_);
        return biomebase == null ? p_a_3_ : biomebase;
    }

    public void a()
    {
        long i = MinecraftServer.az();
        long j = i - this.b;

        if (j > 7500L || j < 0L)
        {
            this.b = i;

            for (int k = 0; k < this.d.size(); ++k)
            {
                BiomeCache.BiomeCacheBlock biomecache$biomecacheblock = (BiomeCache.BiomeCacheBlock)this.d.get(k);
                long l = i - biomecache$biomecacheblock.e;

                if (l > 30000L || l < 0L)
                {
                    this.d.remove(k--);
                    long i1 = (long)biomecache$biomecacheblock.c & 4294967295L | ((long)biomecache$biomecacheblock.d & 4294967295L) << 32;
                    this.c.remove(i1);
                }
            }
        }
    }

    public BiomeBase[] c(int p_c_1_, int p_c_2_)
    {
        return this.a(p_c_1_, p_c_2_).b;
    }

    public class BiomeCacheBlock
    {
        public float[] a = new float[256];
        public BiomeBase[] b = new BiomeBase[256];
        public int c;
        public int d;
        public long e;

        public BiomeCacheBlock(int p_i567_2_, int p_i567_3_)
        {
            this.c = p_i567_2_;
            this.d = p_i567_3_;
            BiomeCache.this.a.getWetness(this.a, p_i567_2_ << 4, p_i567_3_ << 4, 16, 16);
            BiomeCache.this.a.a(this.b, p_i567_2_ << 4, p_i567_3_ << 4, 16, 16, false);
        }

        public BiomeBase a(int p_a_1_, int p_a_2_)
        {
            return this.b[p_a_1_ & 15 | (p_a_2_ & 15) << 4];
        }
    }
}
