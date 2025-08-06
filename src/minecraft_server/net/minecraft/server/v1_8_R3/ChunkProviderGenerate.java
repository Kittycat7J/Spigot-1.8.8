package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;

public class ChunkProviderGenerate implements IChunkProvider
{
    private Random h;
    private NoiseGeneratorOctaves i;
    private NoiseGeneratorOctaves j;
    private NoiseGeneratorOctaves k;
    private NoiseGenerator3 l;
    public NoiseGeneratorOctaves a;
    public NoiseGeneratorOctaves b;
    public NoiseGeneratorOctaves c;
    private World m;
    private final boolean n;
    private WorldType o;
    private final double[] p;
    private final float[] q;
    private CustomWorldSettingsFinal r;
    private Block s = Blocks.WATER;
    private double[] t = new double[256];
    private WorldGenBase u = new WorldGenCaves();
    private WorldGenStronghold v = new WorldGenStronghold();
    private WorldGenVillage w = new WorldGenVillage();
    private WorldGenMineshaft x = new WorldGenMineshaft();
    private WorldGenLargeFeature y = new WorldGenLargeFeature();
    private WorldGenBase z = new WorldGenCanyon();
    private WorldGenMonument A = new WorldGenMonument();
    private BiomeBase[] B;
    double[] d;
    double[] e;
    double[] f;
    double[] g;

    public ChunkProviderGenerate(World p_i686_1_, long p_i686_2_, boolean p_i686_4_, String p_i686_5_)
    {
        this.m = p_i686_1_;
        this.n = p_i686_4_;
        this.o = p_i686_1_.getWorldData().getType();
        this.h = new Random(p_i686_2_);
        this.i = new NoiseGeneratorOctaves(this.h, 16);
        this.j = new NoiseGeneratorOctaves(this.h, 16);
        this.k = new NoiseGeneratorOctaves(this.h, 8);
        this.l = new NoiseGenerator3(this.h, 4);
        this.a = new NoiseGeneratorOctaves(this.h, 10);
        this.b = new NoiseGeneratorOctaves(this.h, 16);
        this.c = new NoiseGeneratorOctaves(this.h, 8);
        this.p = new double[825];
        this.q = new float[25];

        for (int ix = -2; ix <= 2; ++ix)
        {
            for (int jx = -2; jx <= 2; ++jx)
            {
                float fx = 10.0F / MathHelper.c((float)(ix * ix + jx * jx) + 0.2F);
                this.q[ix + 2 + (jx + 2) * 5] = fx;
            }
        }

        if (p_i686_5_ != null)
        {
            this.r = CustomWorldSettingsFinal.CustomWorldSettings.a(p_i686_5_).b();
            this.s = this.r.E ? Blocks.LAVA : Blocks.WATER;
            p_i686_1_.b(this.r.q);
        }
    }

    public void a(int p_a_1_, int p_a_2_, ChunkSnapshot p_a_3_)
    {
        this.B = this.m.getWorldChunkManager().getBiomes(this.B, p_a_1_ * 4 - 2, p_a_2_ * 4 - 2, 10, 10);
        this.a(p_a_1_ * 4, 0, p_a_2_ * 4);

        for (int ix = 0; ix < 4; ++ix)
        {
            int jx = ix * 5;
            int kx = (ix + 1) * 5;

            for (int lx = 0; lx < 4; ++lx)
            {
                int i1 = (jx + lx) * 33;
                int j1 = (jx + lx + 1) * 33;
                int k1 = (kx + lx) * 33;
                int l1 = (kx + lx + 1) * 33;

                for (int i2 = 0; i2 < 32; ++i2)
                {
                    double d0 = 0.125D;
                    double d1 = this.p[i1 + i2];
                    double d2 = this.p[j1 + i2];
                    double d3 = this.p[k1 + i2];
                    double d4 = this.p[l1 + i2];
                    double d5 = (this.p[i1 + i2 + 1] - d1) * d0;
                    double d6 = (this.p[j1 + i2 + 1] - d2) * d0;
                    double d7 = (this.p[k1 + i2 + 1] - d3) * d0;
                    double d8 = (this.p[l1 + i2 + 1] - d4) * d0;

                    for (int j2 = 0; j2 < 8; ++j2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int k2 = 0; k2 < 4; ++k2)
                        {
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int l2 = 0; l2 < 4; ++l2)
                            {
                                if ((d15 += d16) > 0.0D)
                                {
                                    p_a_3_.a(ix * 4 + k2, i2 * 8 + j2, lx * 4 + l2, Blocks.STONE.getBlockData());
                                }
                                else if (i2 * 8 + j2 < this.r.q)
                                {
                                    p_a_3_.a(ix * 4 + k2, i2 * 8 + j2, lx * 4 + l2, this.s.getBlockData());
                                }
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void a(int p_a_1_, int p_a_2_, ChunkSnapshot p_a_3_, BiomeBase[] p_a_4_)
    {
        double d0 = 0.03125D;
        this.t = this.l.a(this.t, (double)(p_a_1_ * 16), (double)(p_a_2_ * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int ix = 0; ix < 16; ++ix)
        {
            for (int jx = 0; jx < 16; ++jx)
            {
                BiomeBase biomebase = p_a_4_[jx + ix * 16];
                biomebase.a(this.m, this.h, p_a_3_, p_a_1_ * 16 + ix, p_a_2_ * 16 + jx, this.t[jx + ix * 16]);
            }
        }
    }

    public Chunk getOrCreateChunk(int p_getOrCreateChunk_1_, int p_getOrCreateChunk_2_)
    {
        this.h.setSeed((long)p_getOrCreateChunk_1_ * 341873128712L + (long)p_getOrCreateChunk_2_ * 132897987541L);
        ChunkSnapshot chunksnapshot = new ChunkSnapshot();
        this.a(p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        this.B = this.m.getWorldChunkManager().getBiomeBlock(this.B, p_getOrCreateChunk_1_ * 16, p_getOrCreateChunk_2_ * 16, 16, 16);
        this.a(p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot, this.B);

        if (this.r.r)
        {
            this.u.a(this, this.m, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        }

        if (this.r.z)
        {
            this.z.a(this, this.m, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        }

        if (this.r.w && this.n)
        {
            this.x.a(this, this.m, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        }

        if (this.r.v && this.n)
        {
            this.w.a(this, this.m, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        }

        if (this.r.u && this.n)
        {
            this.v.a(this, this.m, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        }

        if (this.r.x && this.n)
        {
            this.y.a(this, this.m, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        }

        if (this.r.y && this.n)
        {
            this.A.a(this, this.m, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        }

        Chunk chunk = new Chunk(this.m, chunksnapshot, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_);
        byte[] abyte = chunk.getBiomeIndex();

        for (int ix = 0; ix < abyte.length; ++ix)
        {
            abyte[ix] = (byte)this.B[ix].id;
        }

        chunk.initLighting();
        return chunk;
    }

    private void a(int p_a_1_, int p_a_2_, int p_a_3_)
    {
        this.g = this.b.a(this.g, p_a_1_, p_a_3_, 5, 5, (double)this.r.e, (double)this.r.f, (double)this.r.g);
        float fx = this.r.a;
        float f1 = this.r.b;
        this.d = this.k.a(this.d, p_a_1_, p_a_2_, p_a_3_, 5, 33, 5, (double)(fx / this.r.h), (double)(f1 / this.r.i), (double)(fx / this.r.j));
        this.e = this.i.a(this.e, p_a_1_, p_a_2_, p_a_3_, 5, 33, 5, (double)fx, (double)f1, (double)fx);
        this.f = this.j.a(this.f, p_a_1_, p_a_2_, p_a_3_, 5, 33, 5, (double)fx, (double)f1, (double)fx);
        p_a_3_ = 0;
        p_a_1_ = 0;
        int ix = 0;
        int jx = 0;

        for (int kx = 0; kx < 5; ++kx)
        {
            for (int lx = 0; lx < 5; ++lx)
            {
                float f2 = 0.0F;
                float f3 = 0.0F;
                float f4 = 0.0F;
                byte b0 = 2;
                BiomeBase biomebase = this.B[kx + 2 + (lx + 2) * 10];

                for (int i1 = -b0; i1 <= b0; ++i1)
                {
                    for (int j1 = -b0; j1 <= b0; ++j1)
                    {
                        BiomeBase biomebase1 = this.B[kx + i1 + 2 + (lx + j1 + 2) * 10];
                        float f5 = this.r.n + biomebase1.an * this.r.m;
                        float f6 = this.r.p + biomebase1.ao * this.r.o;

                        if (this.o == WorldType.AMPLIFIED && f5 > 0.0F)
                        {
                            f5 = 1.0F + f5 * 2.0F;
                            f6 = 1.0F + f6 * 4.0F;
                        }

                        float f7 = this.q[i1 + 2 + (j1 + 2) * 5] / (f5 + 2.0F);

                        if (biomebase1.an > biomebase.an)
                        {
                            f7 /= 2.0F;
                        }

                        f2 += f6 * f7;
                        f3 += f5 * f7;
                        f4 += f7;
                    }
                }

                f2 = f2 / f4;
                f3 = f3 / f4;
                f2 = f2 * 0.9F + 0.1F;
                f3 = (f3 * 4.0F - 1.0F) / 8.0F;
                double d0 = this.g[jx] / 8000.0D;

                if (d0 < 0.0D)
                {
                    d0 = -d0 * 0.3D;
                }

                d0 = d0 * 3.0D - 2.0D;

                if (d0 < 0.0D)
                {
                    d0 = d0 / 2.0D;

                    if (d0 < -1.0D)
                    {
                        d0 = -1.0D;
                    }

                    d0 = d0 / 1.4D;
                    d0 = d0 / 2.0D;
                }
                else
                {
                    if (d0 > 1.0D)
                    {
                        d0 = 1.0D;
                    }

                    d0 = d0 / 8.0D;
                }

                ++jx;
                double d1 = (double)f3;
                double d2 = (double)f2;
                d1 = d1 + d0 * 0.2D;
                d1 = d1 * (double)this.r.k / 8.0D;
                double d3 = (double)this.r.k + d1 * 4.0D;

                for (int k1 = 0; k1 < 33; ++k1)
                {
                    double d4 = ((double)k1 - d3) * (double)this.r.l * 128.0D / 256.0D / d2;

                    if (d4 < 0.0D)
                    {
                        d4 *= 4.0D;
                    }

                    double d5 = this.e[ix] / (double)this.r.d;
                    double d6 = this.f[ix] / (double)this.r.c;
                    double d7 = (this.d[ix] / 10.0D + 1.0D) / 2.0D;
                    double d8 = MathHelper.b(d5, d6, d7) - d4;

                    if (k1 > 29)
                    {
                        double d9 = (double)((float)(k1 - 29) / 3.0F);
                        d8 = d8 * (1.0D - d9) + -10.0D * d9;
                    }

                    this.p[ix] = d8;
                    ++ix;
                }
            }
        }
    }

    public boolean isChunkLoaded(int p_isChunkLoaded_1_, int p_isChunkLoaded_2_)
    {
        return true;
    }

    public void getChunkAt(IChunkProvider p_getChunkAt_1_, int p_getChunkAt_2_, int p_getChunkAt_3_)
    {
        BlockFalling.instaFall = true;
        int ix = p_getChunkAt_2_ * 16;
        int jx = p_getChunkAt_3_ * 16;
        BlockPosition blockposition = new BlockPosition(ix, 0, jx);
        BiomeBase biomebase = this.m.getBiome(blockposition.a(16, 0, 16));
        this.h.setSeed(this.m.getSeed());
        long kx = this.h.nextLong() / 2L * 2L + 1L;
        long lx = this.h.nextLong() / 2L * 2L + 1L;
        this.h.setSeed((long)p_getChunkAt_2_ * kx + (long)p_getChunkAt_3_ * lx ^ this.m.getSeed());
        boolean flag = false;
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(p_getChunkAt_2_, p_getChunkAt_3_);

        if (this.r.w && this.n)
        {
            this.x.a(this.m, this.h, chunkcoordintpair);
        }

        if (this.r.v && this.n)
        {
            flag = this.w.a(this.m, this.h, chunkcoordintpair);
        }

        if (this.r.u && this.n)
        {
            this.v.a(this.m, this.h, chunkcoordintpair);
        }

        if (this.r.x && this.n)
        {
            this.y.a(this.m, this.h, chunkcoordintpair);
        }

        if (this.r.y && this.n)
        {
            this.A.a(this.m, this.h, chunkcoordintpair);
        }

        if (biomebase != BiomeBase.DESERT && biomebase != BiomeBase.DESERT_HILLS && this.r.A && !flag && this.h.nextInt(this.r.B) == 0)
        {
            int i1 = this.h.nextInt(16) + 8;
            int j1 = this.h.nextInt(256);
            int k1 = this.h.nextInt(16) + 8;
            (new WorldGenLakes(Blocks.WATER)).generate(this.m, this.h, blockposition.a(i1, j1, k1));
        }

        if (!flag && this.h.nextInt(this.r.D / 10) == 0 && this.r.C)
        {
            int i2 = this.h.nextInt(16) + 8;
            int l2 = this.h.nextInt(this.h.nextInt(248) + 8);
            int k3 = this.h.nextInt(16) + 8;

            if (l2 < this.m.F() || this.h.nextInt(this.r.D / 8) == 0)
            {
                (new WorldGenLakes(Blocks.LAVA)).generate(this.m, this.h, blockposition.a(i2, l2, k3));
            }
        }

        if (this.r.s)
        {
            for (int j2 = 0; j2 < this.r.t; ++j2)
            {
                int i3 = this.h.nextInt(16) + 8;
                int l3 = this.h.nextInt(256);
                int l1 = this.h.nextInt(16) + 8;
                (new WorldGenDungeons()).generate(this.m, this.h, blockposition.a(i3, l3, l1));
            }
        }

        biomebase.a(this.m, this.h, new BlockPosition(ix, 0, jx));
        SpawnerCreature.a(this.m, biomebase, ix + 8, jx + 8, 16, 16, this.h);
        blockposition = blockposition.a(8, 0, 8);

        for (int k2 = 0; k2 < 16; ++k2)
        {
            for (int j3 = 0; j3 < 16; ++j3)
            {
                BlockPosition blockposition1 = this.m.q(blockposition.a(k2, 0, j3));
                BlockPosition blockposition2 = blockposition1.down();

                if (this.m.v(blockposition2))
                {
                    this.m.setTypeAndData(blockposition2, Blocks.ICE.getBlockData(), 2);
                }

                if (this.m.f(blockposition1, true))
                {
                    this.m.setTypeAndData(blockposition1, Blocks.SNOW_LAYER.getBlockData(), 2);
                }
            }
        }

        BlockFalling.instaFall = false;
    }

    public boolean a(IChunkProvider p_a_1_, Chunk p_a_2_, int p_a_3_, int p_a_4_)
    {
        boolean flag = false;

        if (this.r.y && this.n && p_a_2_.w() < 3600L)
        {
            flag |= this.A.a(this.m, this.h, new ChunkCoordIntPair(p_a_3_, p_a_4_));
        }

        return flag;
    }

    public boolean saveChunks(boolean p_saveChunks_1_, IProgressUpdate p_saveChunks_2_)
    {
        return true;
    }

    public void c()
    {
    }

    public boolean unloadChunks()
    {
        return false;
    }

    public boolean canSave()
    {
        return true;
    }

    public String getName()
    {
        return "RandomLevelSource";
    }

    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType p_getMobsFor_1_, BlockPosition p_getMobsFor_2_)
    {
        BiomeBase biomebase = this.m.getBiome(p_getMobsFor_2_);

        if (this.n)
        {
            if (p_getMobsFor_1_ == EnumCreatureType.MONSTER && this.y.a(p_getMobsFor_2_))
            {
                return this.y.b();
            }

            if (p_getMobsFor_1_ == EnumCreatureType.MONSTER && this.r.y && this.A.a(this.m, p_getMobsFor_2_))
            {
                return this.A.b();
            }
        }

        return biomebase.getMobs(p_getMobsFor_1_);
    }

    public BlockPosition findNearestMapFeature(World p_findNearestMapFeature_1_, String p_findNearestMapFeature_2_, BlockPosition p_findNearestMapFeature_3_)
    {
        return "Stronghold".equals(p_findNearestMapFeature_2_) && this.v != null ? this.v.getNearestGeneratedFeature(p_findNearestMapFeature_1_, p_findNearestMapFeature_3_) : null;
    }

    public int getLoadedChunks()
    {
        return 0;
    }

    public void recreateStructures(Chunk p_recreateStructures_1_, int p_recreateStructures_2_, int p_recreateStructures_3_)
    {
        if (this.r.w && this.n)
        {
            this.x.a(this, this.m, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkSnapshot)null);
        }

        if (this.r.v && this.n)
        {
            this.w.a(this, this.m, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkSnapshot)null);
        }

        if (this.r.u && this.n)
        {
            this.v.a(this, this.m, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkSnapshot)null);
        }

        if (this.r.x && this.n)
        {
            this.y.a(this, this.m, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkSnapshot)null);
        }

        if (this.r.y && this.n)
        {
            this.A.a(this, this.m, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkSnapshot)null);
        }
    }

    public Chunk getChunkAt(BlockPosition p_getChunkAt_1_)
    {
        return this.getOrCreateChunk(p_getChunkAt_1_.getX() >> 4, p_getChunkAt_1_.getZ() >> 4);
    }
}
