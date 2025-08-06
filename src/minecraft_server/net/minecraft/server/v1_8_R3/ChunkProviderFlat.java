package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChunkProviderFlat implements IChunkProvider
{
    private World a;
    private Random b;
    private final IBlockData[] c = new IBlockData[256];
    private final WorldGenFlatInfo d;
    private final List<StructureGenerator> e = Lists.<StructureGenerator>newArrayList();
    private final boolean f;
    private final boolean g;
    private WorldGenLakes h;
    private WorldGenLakes i;

    public ChunkProviderFlat(World p_i684_1_, long p_i684_2_, boolean p_i684_4_, String p_i684_5_)
    {
        this.a = p_i684_1_;
        this.b = new Random(p_i684_2_);
        this.d = WorldGenFlatInfo.a(p_i684_5_);

        if (p_i684_4_)
        {
            Map map = this.d.b();

            if (map.containsKey("village"))
            {
                Map map1 = (Map)map.get("village");

                if (!map1.containsKey("size"))
                {
                    map1.put("size", "1");
                }

                this.e.add(new WorldGenVillage(map1));
            }

            if (map.containsKey("biome_1"))
            {
                this.e.add(new WorldGenLargeFeature((Map)map.get("biome_1")));
            }

            if (map.containsKey("mineshaft"))
            {
                this.e.add(new WorldGenMineshaft((Map)map.get("mineshaft")));
            }

            if (map.containsKey("stronghold"))
            {
                this.e.add(new WorldGenStronghold((Map)map.get("stronghold")));
            }

            if (map.containsKey("oceanmonument"))
            {
                this.e.add(new WorldGenMonument((Map)map.get("oceanmonument")));
            }
        }

        if (this.d.b().containsKey("lake"))
        {
            this.h = new WorldGenLakes(Blocks.WATER);
        }

        if (this.d.b().containsKey("lava_lake"))
        {
            this.i = new WorldGenLakes(Blocks.LAVA);
        }

        this.g = this.d.b().containsKey("dungeon");
        int j = 0;
        int k = 0;
        boolean flag = true;

        for (WorldGenFlatLayerInfo worldgenflatlayerinfo : this.d.c())
        {
            for (int ix = worldgenflatlayerinfo.d(); ix < worldgenflatlayerinfo.d() + worldgenflatlayerinfo.b(); ++ix)
            {
                IBlockData iblockdata = worldgenflatlayerinfo.c();

                if (iblockdata.getBlock() != Blocks.AIR)
                {
                    flag = false;
                    this.c[ix] = iblockdata;
                }
            }

            if (worldgenflatlayerinfo.c().getBlock() == Blocks.AIR)
            {
                k += worldgenflatlayerinfo.b();
            }
            else
            {
                j += worldgenflatlayerinfo.b() + k;
                k = 0;
            }
        }

        p_i684_1_.b(j);
        this.f = flag ? false : this.d.b().containsKey("decoration");
    }

    public Chunk getOrCreateChunk(int p_getOrCreateChunk_1_, int p_getOrCreateChunk_2_)
    {
        ChunkSnapshot chunksnapshot = new ChunkSnapshot();

        for (int ix = 0; ix < this.c.length; ++ix)
        {
            IBlockData iblockdata = this.c[ix];

            if (iblockdata != null)
            {
                for (int j = 0; j < 16; ++j)
                {
                    for (int k = 0; k < 16; ++k)
                    {
                        chunksnapshot.a(j, ix, k, iblockdata);
                    }
                }
            }
        }

        for (WorldGenBase worldgenbase : this.e)
        {
            worldgenbase.a(this, this.a, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_, chunksnapshot);
        }

        Chunk chunk = new Chunk(this.a, chunksnapshot, p_getOrCreateChunk_1_, p_getOrCreateChunk_2_);
        BiomeBase[] abiomebase = this.a.getWorldChunkManager().getBiomeBlock((BiomeBase[])null, p_getOrCreateChunk_1_ * 16, p_getOrCreateChunk_2_ * 16, 16, 16);
        byte[] abyte = chunk.getBiomeIndex();

        for (int l = 0; l < abyte.length; ++l)
        {
            abyte[l] = (byte)abiomebase[l].id;
        }

        chunk.initLighting();
        return chunk;
    }

    public boolean isChunkLoaded(int p_isChunkLoaded_1_, int p_isChunkLoaded_2_)
    {
        return true;
    }

    public void getChunkAt(IChunkProvider p_getChunkAt_1_, int p_getChunkAt_2_, int p_getChunkAt_3_)
    {
        int ix = p_getChunkAt_2_ * 16;
        int j = p_getChunkAt_3_ * 16;
        BlockPosition blockposition = new BlockPosition(ix, 0, j);
        BiomeBase biomebase = this.a.getBiome(new BlockPosition(ix + 16, 0, j + 16));
        boolean flag = false;
        this.b.setSeed(this.a.getSeed());
        long k = this.b.nextLong() / 2L * 2L + 1L;
        long l = this.b.nextLong() / 2L * 2L + 1L;
        this.b.setSeed((long)p_getChunkAt_2_ * k + (long)p_getChunkAt_3_ * l ^ this.a.getSeed());
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(p_getChunkAt_2_, p_getChunkAt_3_);

        for (StructureGenerator structuregenerator : this.e)
        {
            boolean flag1 = structuregenerator.a(this.a, this.b, chunkcoordintpair);

            if (structuregenerator instanceof WorldGenVillage)
            {
                flag |= flag1;
            }
        }

        if (this.h != null && !flag && this.b.nextInt(4) == 0)
        {
            this.h.generate(this.a, this.b, blockposition.a(this.b.nextInt(16) + 8, this.b.nextInt(256), this.b.nextInt(16) + 8));
        }

        if (this.i != null && !flag && this.b.nextInt(8) == 0)
        {
            BlockPosition blockposition1 = blockposition.a(this.b.nextInt(16) + 8, this.b.nextInt(this.b.nextInt(248) + 8), this.b.nextInt(16) + 8);

            if (blockposition1.getY() < this.a.F() || this.b.nextInt(10) == 0)
            {
                this.i.generate(this.a, this.b, blockposition1);
            }
        }

        if (this.g)
        {
            for (int i1 = 0; i1 < 8; ++i1)
            {
                (new WorldGenDungeons()).generate(this.a, this.b, blockposition.a(this.b.nextInt(16) + 8, this.b.nextInt(256), this.b.nextInt(16) + 8));
            }
        }

        if (this.f)
        {
            biomebase.a(this.a, this.b, blockposition);
        }
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
        return "FlatLevelSource";
    }

    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType p_getMobsFor_1_, BlockPosition p_getMobsFor_2_)
    {
        BiomeBase biomebase = this.a.getBiome(p_getMobsFor_2_);
        return biomebase.getMobs(p_getMobsFor_1_);
    }

    public BlockPosition findNearestMapFeature(World p_findNearestMapFeature_1_, String p_findNearestMapFeature_2_, BlockPosition p_findNearestMapFeature_3_)
    {
        if ("Stronghold".equals(p_findNearestMapFeature_2_))
        {
            for (StructureGenerator structuregenerator : this.e)
            {
                if (structuregenerator instanceof WorldGenStronghold)
                {
                    return structuregenerator.getNearestGeneratedFeature(p_findNearestMapFeature_1_, p_findNearestMapFeature_3_);
                }
            }
        }

        return null;
    }

    public int getLoadedChunks()
    {
        return 0;
    }

    public void recreateStructures(Chunk p_recreateStructures_1_, int p_recreateStructures_2_, int p_recreateStructures_3_)
    {
        for (StructureGenerator structuregenerator : this.e)
        {
            structuregenerator.a(this, this.a, p_recreateStructures_2_, p_recreateStructures_3_, (ChunkSnapshot)null);
        }
    }

    public Chunk getChunkAt(BlockPosition p_getChunkAt_1_)
    {
        return this.getOrCreateChunk(p_getChunkAt_1_.getX() >> 4, p_getChunkAt_1_.getZ() >> 4);
    }
}
