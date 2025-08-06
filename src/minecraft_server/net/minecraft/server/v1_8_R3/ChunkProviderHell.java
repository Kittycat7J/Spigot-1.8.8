package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;

public class ChunkProviderHell implements IChunkProvider
{
    private final World h;
    private final boolean i;
    private final Random j;
    private double[] k = new double[256];
    private double[] l = new double[256];
    private double[] m = new double[256];
    private double[] n;
    private final NoiseGeneratorOctaves o;
    private final NoiseGeneratorOctaves p;
    private final NoiseGeneratorOctaves q;
    private final NoiseGeneratorOctaves r;
    private final NoiseGeneratorOctaves s;
    public final NoiseGeneratorOctaves a;
    public final NoiseGeneratorOctaves b;
    private final WorldGenFire t = new WorldGenFire();
    private final WorldGenLightStone1 u = new WorldGenLightStone1();
    private final WorldGenLightStone2 v = new WorldGenLightStone2();
    private final WorldGenerator w = new WorldGenMinable(Blocks.QUARTZ_ORE.getBlockData(), 14, BlockPredicate.a(Blocks.NETHERRACK));
    private final WorldGenHellLava x = new WorldGenHellLava(Blocks.FLOWING_LAVA, true);
    private final WorldGenHellLava y = new WorldGenHellLava(Blocks.FLOWING_LAVA, false);
    private final WorldGenMushrooms z = new WorldGenMushrooms(Blocks.BROWN_MUSHROOM);
    private final WorldGenMushrooms A = new WorldGenMushrooms(Blocks.RED_MUSHROOM);
    private final WorldGenNether B = new WorldGenNether();
    private final WorldGenBase C = new WorldGenCavesHell();
    double[] c;
    double[] d;
    double[] e;
    double[] f;
    double[] g;

    public ChunkProviderHell(World p_i685_1_, boolean p_i685_2_, long p_i685_3_)
    {
        this.h = p_i685_1_;
        this.i = p_i685_2_;
        this.j = new Random(p_i685_3_);
        this.o = new NoiseGeneratorOctaves(this.j, 16);
        this.p = new NoiseGeneratorOctaves(this.j, 16);
        this.q = new NoiseGeneratorOctaves(this.j, 8);
        this.r = new NoiseGeneratorOctaves(this.j, 4);
        this.s = new NoiseGeneratorOctaves(this.j, 4);
        this.a = new NoiseGeneratorOctaves(this.j, 10);
        this.b = new NoiseGeneratorOctaves(this.j, 16);
        p_i685_1_.b(63);
    }

    public void a(int p_a_1_, int p_a_2_, ChunkSnapshot p_a_3_)
    {
        byte b0 = 4;
        int ix = this.h.F() / 2 + 1;
        int jx = b0 + 1;
        byte b1 = 17;
        int kx = b0 + 1;
        this.n = this.a(this.n, p_a_1_ * b0, 0, p_a_2_ * b0, jx, b1, kx);

        for (int lx = 0; lx < b0; ++lx)
        {
            for (int i1 = 0; i1 < b0; ++i1)
            {
                for (int j1 = 0; j1 < 16; ++j1)
                {
                    double d0 = 0.125D;
                    double d1 = this.n[((lx + 0) * kx + i1 + 0) * b1 + j1 + 0];
                    double d2 = this.n[((lx + 0) * kx + i1 + 1) * b1 + j1 + 0];
                    double d3 = this.n[((lx + 1) * kx + i1 + 0) * b1 + j1 + 0];
                    double d4 = this.n[((lx + 1) * kx + i1 + 1) * b1 + j1 + 0];
                    double d5 = (this.n[((lx + 0) * kx + i1 + 0) * b1 + j1 + 1] - d1) * d0;
                    double d6 = (this.n[((lx + 0) * kx + i1 + 1) * b1 + j1 + 1] - d2) * d0;
                    double d7 = (this.n[((lx + 1) * kx + i1 + 0) * b1 + j1 + 1] - d3) * d0;
                    double d8 = (this.n[((lx + 1) * kx + i1 + 1) * b1 + j1 + 1] - d4) * d0;

                    for (int k1 = 0; k1 < 8; ++k1)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int l1 = 0; l1 < 4; ++l1)
                        {
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * d14;

                            for (int i2 = 0; i2 < 4; ++i2)
                            {
                                IBlockData iblockdata = null;

                                if (j1 * 8 + k1 < ix)
                                {
                                    iblockdata = Blocks.LAVA.getBlockData();
                                }

                                if (d15 > 0.0D)
                                {
                                    iblockdata = Blocks.NETHERRACK.getBlockData();
                                }

                                int j2 = l1 + lx * 4;
                                int k2 = k1 + j1 * 8;
                                int l2 = i2 + i1 * 4;
                                p_a_3_.a(j2, k2, l2, iblockdata);
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

    public void b(int p_b_1_, int p_b_2_, ChunkSnapshot p_b_3_)
    {
        int ix = this.h.F() + 1;
        double d0 = 0.03125D;
        this.k = this.r.a(this.k, p_b_1_ * 16, p_b_2_ * 16, 0, 16, 16, 1, d0, d0, 1.0D);
        this.l = this.r.a(this.l, p_b_1_ * 16, 109, p_b_2_ * 16, 16, 1, 16, d0, 1.0D, d0);
        this.m = this.s.a(this.m, p_b_1_ * 16, p_b_2_ * 16, 0, 16, 16, 1, d0 * 2.0D, d0 * 2.0D, d0 * 2.0D);

        for (int jx = 0; jx < 16; ++jx)
        {
            for (int kx = 0; kx < 16; ++kx)
            {
                boolean flag = this.k[jx + kx * 16] + this.j.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.l[jx + kx * 16] + this.j.nextDouble() * 0.2D > 0.0D;
                int lx = (int)(this.m[jx + kx * 16] / 3.0D + 3.0D + this.j.nextDouble() * 0.25D);
                int i1 = -1;
                IBlockData iblockdata = Blocks.NETHERRACK.getBlockData();
                IBlockData iblockdata1 = Blocks.NETHERRACK.getBlockData();

                for (int j1 = 127; j1 >= 0; --j1)
                {
                    if (j1 < 127 - this.j.nextInt(5) && j1 > this.j.nextInt(5))
                    {
                        IBlockData iblockdata2 = p_b_3_.a(kx, j1, jx);

                        if (iblockdata2.getBlock() != null && iblockdata2.getBlock().getMaterial() != Material.AIR)
                        {
                            if (iblockdata2.getBlock() == Blocks.NETHERRACK)
                            {
                                if (i1 == -1)
                                {
                                    if (lx <= 0)
                                    {
                                        iblockdata = null;
                                        iblockdata1 = Blocks.NETHERRACK.getBlockData();
                                    }
                                    else if (j1 >= ix - 4 && j1 <= ix + 1)
                                    {
                                        iblockdata = Blocks.NETHERRACK.getBlockData();
                                        iblockdata1 = Blocks.NETHERRACK.getBlockData();

                                        if (flag1)
                                        {
                                            iblockdata = Blocks.GRAVEL.getBlockData();
                                            iblockdata1 = Blocks.NETHERRACK.getBlockData();
                                        }

                                        if (flag)
                                        {
                                            iblockdata = Blocks.SOUL_SAND.getBlockData();
                                            iblockdata1 = Blocks.SOUL_SAND.getBlockData();
                                        }
                                    }

                                    if (j1 < ix && (iblockdata == null || iblockdata.getBlock().getMaterial() == Material.AIR))
                                    {
                                        iblockdata = Blocks.LAVA.getBlockData();
                                    }

                                    i1 = lx;

                                    if (j1 >= ix - 1)
                                    {
                                        p_b_3_.a(kx, j1, jx, iblockdata);
                                    }
                                    else
                                    {
                                        p_b_3_.a(kx, j1, jx, iblockdata1);
                                    }
                                }
                                else if (i1 > 0)
                                {
                                    --i1;
                                    p_b_3_.a(kx, j1, jx, iblockdata1);
                                }
                            }
                        }
                        else
                        {
                            i1 = -1;
                        }
                    }
                    else
                    {
                        p_b_3_.a(kx, j1, jx, Blocks.BEDROCK.getBlockData());
                    }
                }
            }
        }
    }

    public Chunk getOrCreateChunk(int p_getOrCreateChunk_1_, int p_getOrCreateChunk_2_)
    {
        this.j.setSeed((long)p_getOrCreateChunk_1_ * 341873128712L + (long)p_getOrCreateChunk_2_ * 132897987541L);
        ChunkSnapshot chunksnapshot = new ChunkSnapshot();
        this.a(p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        this.b(p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        this.C.a(this, this.h, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);

        if (this.i)
        {
            this.B.a(this, this.h, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        }

        Chunk chunk = new Chunk(this.h, chunksnapshot, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_);
        BiomeBase[] abiomebase = this.h.getWorldChunkManager().getBiomeBlock((BiomeBase[])null, p_getOrCreateChunk_1_ * 16, p_getOrCreateChunk_2_ * 16, 16, 16);
        byte[] abyte = chunk.getBiomeIndex();

        for (int ix = 0; ix < abyte.length; ++ix)
        {
            abyte[ix] = (byte)abiomebase[ix].id;
        }

        chunk.l();
        return chunk;
    }

    private double[] a(double[] p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_)
    {
        if (p_a_1_ == null)
        {
            p_a_1_ = new double[p_a_5_ * p_a_6_ * p_a_7_];
        }

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.f = this.a.a(this.f, p_a_2_, p_a_3_, p_a_4_, p_a_5_, 1, p_a_7_, 1.0D, 0.0D, 1.0D);
        this.g = this.b.a(this.g, p_a_2_, p_a_3_, p_a_4_, p_a_5_, 1, p_a_7_, 100.0D, 0.0D, 100.0D);
        this.c = this.q.a(this.c, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, d0 / 80.0D, d1 / 60.0D, d0 / 80.0D);
        this.d = this.o.a(this.d, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, d0, d1, d0);
        this.e = this.p.a(this.e, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_, p_a_7_, d0, d1, d0);
        int ix = 0;
        double[] adouble = new double[p_a_6_];

        for (int jx = 0; jx < p_a_6_; ++jx)
        {
            adouble[jx] = Math.cos((double)jx * Math.PI * 6.0D / (double)p_a_6_) * 2.0D;
            double d2 = (double)jx;

            if (jx > p_a_6_ / 2)
            {
                d2 = (double)(p_a_6_ - 1 - jx);
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble[jx] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (int i1 = 0; i1 < p_a_5_; ++i1)
        {
            for (int kx = 0; kx < p_a_7_; ++kx)
            {
                double d3 = 0.0D;

                for (int lx = 0; lx < p_a_6_; ++lx)
                {
                    double d4 = 0.0D;
                    double d5 = adouble[lx];
                    double d6 = this.d[ix] / 512.0D;
                    double d7 = this.e[ix] / 512.0D;
                    double d8 = (this.c[ix] / 10.0D + 1.0D) / 2.0D;

                    if (d8 < 0.0D)
                    {
                        d4 = d6;
                    }
                    else if (d8 > 1.0D)
                    {
                        d4 = d7;
                    }
                    else
                    {
                        d4 = d6 + (d7 - d6) * d8;
                    }

                    d4 = d4 - d5;

                    if (lx > p_a_6_ - 4)
                    {
                        double d9 = (double)((float)(lx - (p_a_6_ - 4)) / 3.0F);
                        d4 = d4 * (1.0D - d9) + -10.0D * d9;
                    }

                    if ((double)lx < d3)
                    {
                        double d10 = (d3 - (double)lx) / 4.0D;
                        d10 = MathHelper.a(d10, 0.0D, 1.0D);
                        d4 = d4 * (1.0D - d10) + -10.0D * d10;
                    }

                    p_a_1_[ix] = d4;
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
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(p_getChunkAt_2_, p_getChunkAt_3_);
        this.B.a(this.h, this.j, chunkcoordintpair);

        for (int ix = 0; ix < 8; ++ix)
        {
            this.y.generate(this.h, this.j, blockposition.a(this.j.nextInt(16) + 8, this.j.nextInt(120) + 4, this.j.nextInt(16) + 8));
        }

        for (int jx = 0; jx < this.j.nextInt(this.j.nextInt(10) + 1) + 1; ++jx)
        {
            this.t.generate(this.h, this.j, blockposition.a(this.j.nextInt(16) + 8, this.j.nextInt(120) + 4, this.j.nextInt(16) + 8));
        }

        for (int kx = 0; kx < this.j.nextInt(this.j.nextInt(10) + 1); ++kx)
        {
            this.u.generate(this.h, this.j, blockposition.a(this.j.nextInt(16) + 8, this.j.nextInt(120) + 4, this.j.nextInt(16) + 8));
        }

        for (int lx = 0; lx < 10; ++lx)
        {
            this.v.generate(this.h, this.j, blockposition.a(this.j.nextInt(16) + 8, this.j.nextInt(128), this.j.nextInt(16) + 8));
        }

        if (this.j.nextBoolean())
        {
            this.z.generate(this.h, this.j, blockposition.a(this.j.nextInt(16) + 8, this.j.nextInt(128), this.j.nextInt(16) + 8));
        }

        if (this.j.nextBoolean())
        {
            this.A.generate(this.h, this.j, blockposition.a(this.j.nextInt(16) + 8, this.j.nextInt(128), this.j.nextInt(16) + 8));
        }

        for (int i1 = 0; i1 < 16; ++i1)
        {
            this.w.generate(this.h, this.j, blockposition.a(this.j.nextInt(16), this.j.nextInt(108) + 10, this.j.nextInt(16)));
        }

        for (int j1 = 0; j1 < 16; ++j1)
        {
            this.x.generate(this.h, this.j, blockposition.a(this.j.nextInt(16), this.j.nextInt(108) + 10, this.j.nextInt(16)));
        }

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
        return "HellRandomLevelSource";
    }

    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType p_getMobsFor_1_, BlockPosition p_getMobsFor_2_)
    {
        if (p_getMobsFor_1_ == EnumCreatureType.MONSTER)
        {
            if (this.B.b(p_getMobsFor_2_))
            {
                return this.B.b();
            }

            if (this.B.a(this.h, p_getMobsFor_2_) && this.h.getType(p_getMobsFor_2_.down()).getBlock() == Blocks.NETHER_BRICK)
            {
                return this.B.b();
            }
        }

        BiomeBase biomebase = this.h.getBiome(p_getMobsFor_2_);
        return biomebase.getMobs(p_getMobsFor_1_);
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
        this.B.a(this, this.h, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkSnapshot)null);
    }

    public Chunk getChunkAt(BlockPosition p_getChunkAt_1_)
    {
        return this.getOrCreateChunk(p_getChunkAt_1_.getX() >> 4, p_getChunkAt_1_.getZ() >> 4);
    }
}
