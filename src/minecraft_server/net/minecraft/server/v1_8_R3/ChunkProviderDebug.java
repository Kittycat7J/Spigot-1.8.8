package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;

public class ChunkProviderDebug implements IChunkProvider
{
    private static final List<IBlockData> a = Lists.<IBlockData>newArrayList();
    private static final int b;
    private static final int c;
    private final World d;

    public ChunkProviderDebug(World p_i683_1_)
    {
        this.d = p_i683_1_;
    }

    public Chunk getOrCreateChunk(int p_getOrCreateChunk_1_, int p_getOrCreateChunk_2_)
    {
        ChunkSnapshot chunksnapshot = new ChunkSnapshot();

        for (int i = 0; i < 16; ++i)
        {
            for (int j = 0; j < 16; ++j)
            {
                int k = p_getOrCreateChunk_1_ * 16 + i;
                int l = p_getOrCreateChunk_2_ * 16 + j;
                chunksnapshot.a(i, 60, j, Blocks.BARRIER.getBlockData());
                IBlockData iblockdata = b(k, l);

                if (iblockdata != null)
                {
                    chunksnapshot.a(i, 70, j, iblockdata);
                }
            }
        }

        Chunk chunk = new Chunk(this.d, chunksnapshot, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_);
        chunk.initLighting();
        BiomeBase[] abiomebase = this.d.getWorldChunkManager().getBiomeBlock((BiomeBase[])null, p_getOrCreateChunk_1_ * 16, p_getOrCreateChunk_2_ * 16, 16, 16);
        byte[] abyte = chunk.getBiomeIndex();

        for (int i1 = 0; i1 < abyte.length; ++i1)
        {
            abyte[i1] = (byte)abiomebase[i1].id;
        }

        chunk.initLighting();
        return chunk;
    }

    public static IBlockData b(int p_b_0_, int p_b_1_)
    {
        IBlockData iblockdata = null;

        if (p_b_0_ > 0 && p_b_1_ > 0 && p_b_0_ % 2 != 0 && p_b_1_ % 2 != 0)
        {
            p_b_0_ = p_b_0_ / 2;
            p_b_1_ = p_b_1_ / 2;

            if (p_b_0_ <= b && p_b_1_ <= c)
            {
                int i = MathHelper.a(p_b_0_ * b + p_b_1_);

                if (i < a.size())
                {
                    iblockdata = (IBlockData)a.get(i);
                }
            }
        }

        return iblockdata;
    }

    public boolean isChunkLoaded(int p_isChunkLoaded_1_, int p_isChunkLoaded_2_)
    {
        return true;
    }

    public void getChunkAt(IChunkProvider p_getChunkAt_1_, int p_getChunkAt_2_, int p_getChunkAt_3_)
    {
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
        return "DebugLevelSource";
    }

    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType p_getMobsFor_1_, BlockPosition p_getMobsFor_2_)
    {
        BiomeBase biomebase = this.d.getBiome(p_getMobsFor_2_);
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
    }

    public Chunk getChunkAt(BlockPosition p_getChunkAt_1_)
    {
        return this.getOrCreateChunk(p_getChunkAt_1_.getX() >> 4, p_getChunkAt_1_.getZ() >> 4);
    }

    static
    {
        for (Block block : Block.REGISTRY)
        {
            a.addAll(block.P().a());
        }

        b = MathHelper.f(MathHelper.c((float)a.size()));
        c = MathHelper.f((float)a.size() / (float)b);
    }
}
