package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;

public class ChunkProviderTheEnd implements IChunkProvider
{
    private Random h;
    private NoiseGeneratorOctaves i;
    private NoiseGeneratorOctaves j;
    private NoiseGeneratorOctaves k;
    public NoiseGeneratorOctaves a;
    public NoiseGeneratorOctaves b;
    private World l;
    private double[] m;
    private BiomeBase[] n;
    double[] c;
    double[] d;
    double[] e;
    double[] f;
    double[] g;

    public ChunkProviderTheEnd(World p_i687_1_, long p_i687_2_)
    {
        this.l = p_i687_1_;
        this.h = new Random(p_i687_2_);
        this.i = new NoiseGeneratorOctaves(this.h, 16);
        this.j = new NoiseGeneratorOctaves(this.h, 16);
        this.k = new NoiseGeneratorOctaves(this.h, 8);
        this.a = new NoiseGeneratorOctaves(this.h, 10);
        this.b = new NoiseGeneratorOctaves(this.h, 16);
    }

    public void a(int p_a_1_, int p_a_2_, ChunkSnapshot p_a_3_)
    {
        byte b0 = 2;
        int ix = b0 + 1;
        byte b1 = 33;
        int jx = b0 + 1;
        this.m = this.a(this.m, p_a_1_ * b0, 0, p_a_2_ * b0, ix, b1, jx);

        for (int kx = 0; kx < b0; ++kx)
        {
            for (int lx = 0; lx < b0; ++lx)
            {
                for (int i1 = 0; i1 < 32; ++i1)
                {
                    double d0 = 0.25D;
                    double d1 = this.m[((kx + 0) * jx + lx + 0) * b1 + i1 + 0];
                    double d2 = this.m[((kx + 0) * jx + lx + 1) * b1 + i1 + 0];
                    double d3 = this.m[((kx + 1) * jx + lx + 0) * b1 + i1 + 0];
                    double d4 = this.m[((kx + 1) * jx + lx + 1) * b1 + i1 + 0];
                    double d5 = (this.m[((kx + 0) * jx + lx + 0) * b1 + i1 + 1] - d1) * d0;
                    double d6 = (this.m[((kx + 0) * jx + lx + 1) * b1 + i1 + 1] - d2) * d0;
                    double d7 = (this.m[((kx + 1) * jx + lx + 0) * b1 + i1 + 1] - d3) * d0;
                    double d8 = (this.m[((kx + 1) * jx + lx + 1) * b1 + i1 + 1] - d4) * d0;

                    for (int j1 = 0; j1 < 4; ++j1)
                    {
                        double d9 = 0.125D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int k1 = 0; k1 < 8; ++k1)
                        {
                            double d14 = 0.125D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int l1 = 0; l1 < 8; ++l1)
                            {
                                IBlockData iblockdata = null;

                                if (d15 > 0.0D)
                                {
                                    iblockdata = Blocks.END_STONE.getBlockData();
                                }

                                int i2 = k1 + kx * 8;
                                int j2 = j1 + i1 * 4;
                                int k2 = l1 + lx * 8;
                                p_a_3_.a(i2, j2, k2, iblockdata);
                                d15 += d16;
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

    public void a(ChunkSnapshot p_a_1_)
    {
        for (int ix = 0; ix < 16; ++ix)
        {
            for (int jx = 0; jx < 16; ++jx)
            {
                byte b0 = 1;
                int kx = -1;
                IBlockData iblockdata = Blocks.END_STONE.getBlockData();
                IBlockData iblockdata1 = Blocks.END_STONE.getBlockData();

                for (int lx = 127; lx >= 0; --lx)
                {
                    IBlockData iblockdata2 = p_a_1_.a(ix, lx, jx);

                    if (iblockdata2.getBlock().getMaterial() == Material.AIR)
                    {
                        kx = -1;
                    }
                    else if (iblockdata2.getBlock() == Blocks.STONE)
                    {
                        if (kx == -1)
                        {
                            if (b0 <= 0)
                            {
                                iblockdata = Blocks.AIR.getBlockData();
                                iblockdata1 = Blocks.END_STONE.getBlockData();
                            }

                            kx = b0;

                            if (lx >= 0)
                            {
                                p_a_1_.a(ix, lx, jx, iblockdata);
                            }
                            else
                            {
                                p_a_1_.a(ix, lx, jx, iblockdata1);
                            }
                        }
                        else if (kx > 0)
                        {
                            --kx;
                            p_a_1_.a(ix, lx, jx, iblockdata1);
                        }
                    }
                }
            }
        }
    }

    public Chunk getOrCreateChunk(int p_getOrCreateChunk_1_, int p_getOrCreateChunk_2_)
    {
        this.h.setSeed((long)p_getOrCreateChunk_1_ * 341873128712L + (long)p_getOrCreateChunk_2_ * 132897987541L);
        ChunkSnapshot chunksnapshot = new ChunkSnapshot();
        this.n = this.l.getWorldChunkManager().getBiomeBlock(this.n, p_getOrCreateChunk_1_ * 16, p_getOrCreateChunk_2_ * 16, 16, 16);
        this.a(p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        this.a(chunksnapshot);
        Chunk chunk = new Chunk(this.l, chunksnapshot, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_);
        byte[] abyte = chunk.getBiomeIndex();

        for (int ix = 0; ix < abyte.length; ++ix)
        {
            abyte[ix] = (byte)this.n[ix].id;
        }

        chunk.initLighting();
        return chunk;
    }

    private double[] a(double[] p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_)
    {
        if (p_a_1_ == null)
        {
            p_a_1_ = new double[p_a_5_ * p_a_6_ * p_a_7_];
        }

        double d0 = 684.412D;
        double d1 = 684.412D;
        this.f = this.a.a(this.f, p_a_2_, p_a_4_, p_a_5_, p_a_7_, 1.121D, 1.121D, 0.5D);
        this.g = this.b.a(this.g, p_a_2_, p_a_4_, p_a_5_, p_a_7_, 200.0D, 200.0D, 0.5D);
        d0 = d0 * 2.0D;
        this.c = this.k.a(this.c, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, d0 / 80.0D, d1 / 160.0D, d0 / 80.0D);
        this.d = this.i.a(this.d, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, d0, d1, d0);
        this.e = this.j.a(this.e, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, d0, d1, d0);
        int ix = 0;

        for (int jx = 0; jx < p_a_5_; ++jx)
        {
            for (int kx = 0; kx < p_a_7_; ++kx)
            {
                float fx = (float)(jx + p_a_2_) / 1.0F;
                float f1 = (float)(kx + p_a_4_) / 1.0F;
                float f2 = 100.0F - MathHelper.c(fx * fx + f1 * f1) * 8.0F;

                if (f2 > 80.0F)
                {
                    f2 = 80.0F;
                }

                if (f2 < -100.0F)
                {
                    f2 = -100.0F;
                }

                for (int lx = 0; lx < p_a_6_; ++lx)
                {
                    double d2 = 0.0D;
                    double d3 = this.d[ix] / 512.0D;
                    double d4 = this.e[ix] / 512.0D;
                    double d5 = (this.c[ix] / 10.0D + 1.0D) / 2.0D;

                    if (d5 < 0.0D)
                    {
                        d2 = d3;
                    }
                    else if (d5 > 1.0D)
                    {
                        d2 = d4;
                    }
                    else
                    {
                        d2 = d3 + (d4 - d3) * d5;
                    }

                    d2 = d2 - 8.0D;
                    d2 = d2 + (double)f2;
                    byte b0 = 2;

                    if (lx > p_a_6_ / 2 - b0)
                    {
                        double d6 = (double)((float)(lx - (p_a_6_ / 2 - b0)) / 64.0F);
                        d6 = MathHelper.a(d6, 0.0D, 1.0D);
                        d2 = d2 * (1.0D - d6) + -3000.0D * d6;
                    }

                    b0 = 8;

                    if (lx < b0)
                    {
                        double d7 = (double)((float)(b0 - lx) / ((float)b0 - 1.0F));
                        d2 = d2 * (1.0D - d7) + -30.0D * d7;
                    }

                    p_a_1_[ix] = d2;
                    ++ix;
                }
            }
        }

        return p_a_1_;
    }

    public boolean isChunkLoaded(int p_isChunkLoaded_1_, int p_isChunkLoaded_2_)
    {
        return true;
    }

    public void getChunkAt(IChunkProvider p_getChunkAt_1_, int p_getChunkAt_2_, int p_getChunkAt_3_)
    {
        BlockFalling.instaFall = true;
        BlockPosition blockposition = new BlockPosition(p_getChunkAt_2_ * 16, 0, p_getChunkAt_3_ * 16);
        this.l.getBiome(blockposition.a(16, 0, 16)).a(this.l, this.l.random, blockposition);
        BlockFalling.instaFall = false;
    }

    public boolean a(IChunkProvider p_a_1_, Chunk p_a_2_, int p_a_3_, int p_a_4_)
    {
        return false;
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
        return this.l.getBiome(p_getMobsFor_2_).getMobs(p_getMobsFor_1_);
    }

    public BlockPosition findNearestMapFeature(World p_findNearestMapFeature_1_, String p_findNearestMapFeature_2_, BlockPosition p_findNearestMapFeature_3_)
    {
        return null;
    }

    public int getLoadedChunks()
    {
        return 0;
    }

    public void recreateStructures(Chunk p_recreateStructures_1_, int p_recreateStructures_2_, int p_recreateStructures_3_)
    {
    }

    public Chunk getChunkAt(BlockPosition p_getChunkAt_1_)
    {
        return this.getOrCreateChunk(p_getChunkAt_1_.getX() >> 4, p_getChunkAt_1_.getZ() >> 4);
    }
}
