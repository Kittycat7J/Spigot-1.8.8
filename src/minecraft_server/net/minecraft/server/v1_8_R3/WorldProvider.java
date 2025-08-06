package net.minecraft.server.v1_8_R3;

public abstract class WorldProvider
{
    public static final float[] a = new float[] {1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F};
    protected World b;
    private WorldType type;
    private String i;
    protected WorldChunkManager c;
    protected boolean d;
    protected boolean e;
    protected final float[] f = new float[16];
    protected int dimension;
    private final float[] j = new float[4];

    public final void a(World p_a_1_)
    {
        this.b = p_a_1_;
        this.type = p_a_1_.getWorldData().getType();
        this.i = p_a_1_.getWorldData().getGeneratorOptions();
        this.b();
        this.a();
    }

    protected void a()
    {
        float fx = 0.0F;

        for (int ix = 0; ix <= 15; ++ix)
        {
            float f1 = 1.0F - (float)ix / 15.0F;
            this.f[ix] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - fx) + fx;
        }
    }

    protected void b()
    {
        WorldType worldtype = this.b.getWorldData().getType();

        if (worldtype == WorldType.FLAT)
        {
            WorldGenFlatInfo worldgenflatinfo = WorldGenFlatInfo.a(this.b.getWorldData().getGeneratorOptions());
            this.c = new WorldChunkManagerHell(BiomeBase.getBiome(worldgenflatinfo.a(), BiomeBase.ad), 0.5F);
        }
        else if (worldtype == WorldType.DEBUG_ALL_BLOCK_STATES)
        {
            this.c = new WorldChunkManagerHell(BiomeBase.PLAINS, 0.0F);
        }
        else
        {
            this.c = new WorldChunkManager(this.b);
        }
    }

    public IChunkProvider getChunkProvider()
    {
        return (IChunkProvider)(this.type == WorldType.FLAT ? new ChunkProviderFlat(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures(), this.i) : (this.type == WorldType.DEBUG_ALL_BLOCK_STATES ? new ChunkProviderDebug(this.b) : (this.type == WorldType.CUSTOMIZED ? new ChunkProviderGenerate(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures(), this.i) : new ChunkProviderGenerate(this.b, this.b.getSeed(), this.b.getWorldData().shouldGenerateMapFeatures(), this.i))));
    }

    public boolean canSpawn(int p_canSpawn_1_, int p_canSpawn_2_)
    {
        return this.b.c(new BlockPosition(p_canSpawn_1_, 0, p_canSpawn_2_)) == Blocks.GRASS;
    }

    public float a(long p_a_1_, float p_a_3_)
    {
        int ix = (int)(p_a_1_ % 24000L);
        float fx = ((float)ix + p_a_3_) / 24000.0F - 0.25F;

        if (fx < 0.0F)
        {
            ++fx;
        }

        if (fx > 1.0F)
        {
            --fx;
        }

        fx = 1.0F - (float)((Math.cos((double)fx * Math.PI) + 1.0D) / 2.0D);
        fx = fx + (fx - fx) / 3.0F;
        return fx;
    }

    public int a(long p_a_1_)
    {
        return (int)(p_a_1_ / 24000L % 8L + 8L) % 8;
    }

    public boolean d()
    {
        return true;
    }

    public boolean e()
    {
        return true;
    }

    public static WorldProvider byDimension(int p_byDimension_0_)
    {
        return (WorldProvider)(p_byDimension_0_ == -1 ? new WorldProviderHell() : (p_byDimension_0_ == 0 ? new WorldProviderNormal() : (p_byDimension_0_ == 1 ? new WorldProviderTheEnd() : null)));
    }

    public BlockPosition h()
    {
        return null;
    }

    public int getSeaLevel()
    {
        return this.type == WorldType.FLAT ? 4 : this.b.F() + 1;
    }

    public abstract String getName();

    public abstract String getSuffix();

    public WorldChunkManager m()
    {
        return this.c;
    }

    public boolean n()
    {
        return this.d;
    }

    public boolean o()
    {
        return this.e;
    }

    public float[] p()
    {
        return this.f;
    }

    public int getDimension()
    {
        return this.dimension;
    }

    public WorldBorder getWorldBorder()
    {
        return new WorldBorder();
    }
}
