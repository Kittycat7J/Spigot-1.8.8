package net.minecraft.server.v1_8_R3;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChunkRegionLoader implements IChunkLoader, IAsyncChunkSaver
{
    private static final Logger a = LogManager.getLogger();
    private Map<ChunkCoordIntPair, NBTTagCompound> b = new ConcurrentHashMap();
    private Set<ChunkCoordIntPair> c = Collections.newSetFromMap(new ConcurrentHashMap());
    private final File d;
    private boolean e = false;

    public ChunkRegionLoader(File p_i190_1_)
    {
        this.d = p_i190_1_;
    }

    public boolean chunkExists(World p_chunkExists_1_, int p_chunkExists_2_, int p_chunkExists_3_)
    {
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(p_chunkExists_2_, p_chunkExists_3_);
        return this.c.contains(chunkcoordintpair) && this.b.containsKey(chunkcoordintpair) ? true : RegionFileCache.a(this.d, p_chunkExists_2_, p_chunkExists_3_).chunkExists(p_chunkExists_2_ & 31, p_chunkExists_3_ & 31);
    }

    public Chunk a(World p_a_1_, int p_a_2_, int p_a_3_) throws IOException
    {
        p_a_1_.timings.syncChunkLoadDataTimer.startTiming();
        Object[] aobject = this.loadChunk(p_a_1_, p_a_2_, p_a_3_);
        p_a_1_.timings.syncChunkLoadDataTimer.stopTiming();

        if (aobject != null)
        {
            Chunk chunk = (Chunk)aobject[0];
            NBTTagCompound nbttagcompound = (NBTTagCompound)aobject[1];
            this.loadEntities(chunk, nbttagcompound.getCompound("Level"), p_a_1_);
            return chunk;
        }
        else
        {
            return null;
        }
    }

    public Object[] loadChunk(World p_loadChunk_1_, int p_loadChunk_2_, int p_loadChunk_3_) throws IOException
    {
        ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(p_loadChunk_2_, p_loadChunk_3_);
        NBTTagCompound nbttagcompound = (NBTTagCompound)this.b.get(chunkcoordintpair);

        if (nbttagcompound == null)
        {
            DataInputStream datainputstream = RegionFileCache.c(this.d, p_loadChunk_2_, p_loadChunk_3_);

            if (datainputstream == null)
            {
                return null;
            }

            nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
        }

        return this.a(p_loadChunk_1_, p_loadChunk_2_, p_loadChunk_3_, nbttagcompound);
    }

    protected Object[] a(World p_a_1_, int p_a_2_, int p_a_3_, NBTTagCompound p_a_4_)
    {
        if (!p_a_4_.hasKeyOfType("Level", 10))
        {
            a.error("Chunk file at " + p_a_2_ + "," + p_a_3_ + " is missing level data, skipping");
            return null;
        }
        else
        {
            NBTTagCompound nbttagcompound = p_a_4_.getCompound("Level");

            if (!nbttagcompound.hasKeyOfType("Sections", 9))
            {
                a.error("Chunk file at " + p_a_2_ + "," + p_a_3_ + " is missing block data, skipping");
                return null;
            }
            else
            {
                Chunk chunk = this.a(p_a_1_, nbttagcompound);

                if (!chunk.a(p_a_2_, p_a_3_))
                {
                    a.error("Chunk file at " + p_a_2_ + "," + p_a_3_ + " is in the wrong location; relocating. (Expected " + p_a_2_ + ", " + p_a_3_ + ", got " + chunk.locX + ", " + chunk.locZ + ")");
                    nbttagcompound.setInt("xPos", p_a_2_);
                    nbttagcompound.setInt("zPos", p_a_3_);
                    NBTTagList nbttaglist = p_a_4_.getCompound("Level").getList("TileEntities", 10);

                    if (nbttaglist != null)
                    {
                        for (int i = 0; i < nbttaglist.size(); ++i)
                        {
                            NBTTagCompound nbttagcompound1 = nbttaglist.get(i);
                            int j = nbttagcompound1.getInt("x") - chunk.locX * 16;
                            int k = nbttagcompound1.getInt("z") - chunk.locZ * 16;
                            nbttagcompound1.setInt("x", p_a_2_ * 16 + j);
                            nbttagcompound1.setInt("z", p_a_3_ * 16 + k);
                        }
                    }

                    chunk = this.a(p_a_1_, nbttagcompound);
                }

                Object[] aobject = new Object[] {chunk, p_a_4_};
                return aobject;
            }
        }
    }

    public void a(World p_a_1_, Chunk p_a_2_) throws IOException, ExceptionWorldConflict
    {
        p_a_1_.checkSession();

        try
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound.set("Level", nbttagcompound1);
            this.a(p_a_2_, p_a_1_, nbttagcompound1);
            this.a(p_a_2_.j(), nbttagcompound);
        }
        catch (Exception exception)
        {
            a.error((String)"Failed to save chunk", (Throwable)exception);
        }
    }

    protected void a(ChunkCoordIntPair p_a_1_, NBTTagCompound p_a_2_)
    {
        if (!this.c.contains(p_a_1_))
        {
            this.b.put(p_a_1_, p_a_2_);
        }

        FileIOThread.a().a(this);
    }

    public boolean c()
    {
        if (this.b.isEmpty())
        {
            if (this.e)
            {
                a.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", new Object[] {this.d.getName()});
            }

            return false;
        }
        else
        {
            ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)this.b.keySet().iterator().next();
            boolean flag;

            try
            {
                this.c.add(chunkcoordintpair);
                NBTTagCompound nbttagcompound = (NBTTagCompound)this.b.remove(chunkcoordintpair);

                if (nbttagcompound != null)
                {
                    try
                    {
                        this.b(chunkcoordintpair, nbttagcompound);
                    }
                    catch (Exception exception)
                    {
                        a.error((String)"Failed to save chunk", (Throwable)exception);
                    }
                }

                flag = true;
            }
            finally
            {
                this.c.remove(chunkcoordintpair);
            }

            return flag;
        }
    }

    private void b(ChunkCoordIntPair p_b_1_, NBTTagCompound p_b_2_) throws IOException
    {
        DataOutputStream dataoutputstream = RegionFileCache.d(this.d, p_b_1_.x, p_b_1_.z);
        NBTCompressedStreamTools.a((NBTTagCompound)p_b_2_, (DataOutput)dataoutputstream);
        dataoutputstream.close();
    }

    public void b(World p_b_1_, Chunk p_b_2_) throws IOException
    {
    }

    public void a()
    {
    }

    public void b()
    {
        try
        {
            this.e = true;

            while (true)
            {
                if (this.c())
                {
                    ;
                }
            }
        }
        finally
        {
            this.e = false;
        }
    }

    private void a(Chunk p_a_1_, World p_a_2_, NBTTagCompound p_a_3_)
    {
        p_a_3_.setByte("V", (byte)1);
        p_a_3_.setInt("xPos", p_a_1_.locX);
        p_a_3_.setInt("zPos", p_a_1_.locZ);
        p_a_3_.setLong("LastUpdate", p_a_2_.getTime());
        p_a_3_.setIntArray("HeightMap", p_a_1_.q());
        p_a_3_.setBoolean("TerrainPopulated", p_a_1_.isDone());
        p_a_3_.setBoolean("LightPopulated", p_a_1_.u());
        p_a_3_.setLong("InhabitedTime", p_a_1_.w());
        ChunkSection[] achunksection = p_a_1_.getSections();
        NBTTagList nbttaglist = new NBTTagList();
        boolean flag = !p_a_2_.worldProvider.o();

        for (ChunkSection chunksection : achunksection)
        {
            if (chunksection != null)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Y", (byte)(chunksection.getYPosition() >> 4 & 255));
                byte[] abyte = new byte[chunksection.getIdArray().length];
                NibbleArray nibblearray = new NibbleArray();
                NibbleArray nibblearray1 = null;

                for (int i = 0; i < chunksection.getIdArray().length; ++i)
                {
                    char c0 = chunksection.getIdArray()[i];
                    int j = i & 15;
                    int k = i >> 8 & 15;
                    int l = i >> 4 & 15;

                    if (c0 >> 12 != 0)
                    {
                        if (nibblearray1 == null)
                        {
                            nibblearray1 = new NibbleArray();
                        }

                        nibblearray1.a(j, k, l, c0 >> 12);
                    }

                    abyte[i] = (byte)(c0 >> 4 & 255);
                    nibblearray.a(j, k, l, c0 & 15);
                }

                nbttagcompound.setByteArray("Blocks", abyte);
                nbttagcompound.setByteArray("Data", nibblearray.a());

                if (nibblearray1 != null)
                {
                    nbttagcompound.setByteArray("Add", nibblearray1.a());
                }

                nbttagcompound.setByteArray("BlockLight", chunksection.getEmittedLightArray().a());

                if (flag)
                {
                    nbttagcompound.setByteArray("SkyLight", chunksection.getSkyLightArray().a());
                }
                else
                {
                    nbttagcompound.setByteArray("SkyLight", new byte[chunksection.getEmittedLightArray().a().length]);
                }

                nbttaglist.add(nbttagcompound);
            }
        }

        p_a_3_.set("Sections", nbttaglist);
        p_a_3_.setByteArray("Biomes", p_a_1_.getBiomeIndex());
        p_a_1_.g(false);
        NBTTagList nbttaglist1 = new NBTTagList();

        for (int j11 = 0; j11 < p_a_1_.getEntitySlices().length; ++j11)
        {
            for (Entity entity : p_a_1_.getEntitySlices()[j11])
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();

                if (entity.d(nbttagcompound1))
                {
                    p_a_1_.g(true);
                    nbttaglist1.add(nbttagcompound1);
                }
            }
        }

        p_a_3_.set("Entities", nbttaglist1);
        NBTTagList nbttaglist2 = new NBTTagList();

        for (TileEntity tileentity : p_a_1_.getTileEntities().values())
        {
            NBTTagCompound nbttagcompound2 = new NBTTagCompound();
            tileentity.b(nbttagcompound2);
            nbttaglist2.add(nbttagcompound2);
        }

        p_a_3_.set("TileEntities", nbttaglist2);
        List list = p_a_2_.a(p_a_1_, false);

        if (list != null)
        {
            long i1 = p_a_2_.getTime();
            NBTTagList nbttaglist3 = new NBTTagList();

            for (NextTickListEntry nextticklistentry : list)
            {
                NBTTagCompound nbttagcompound3 = new NBTTagCompound();
                MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(nextticklistentry.a());
                nbttagcompound3.setString("i", minecraftkey == null ? "" : minecraftkey.toString());
                nbttagcompound3.setInt("x", nextticklistentry.a.getX());
                nbttagcompound3.setInt("y", nextticklistentry.a.getY());
                nbttagcompound3.setInt("z", nextticklistentry.a.getZ());
                nbttagcompound3.setInt("t", (int)(nextticklistentry.b - i1));
                nbttagcompound3.setInt("p", nextticklistentry.c);
                nbttaglist3.add(nbttagcompound3);
            }

            p_a_3_.set("TileTicks", nbttaglist3);
        }
    }

    private Chunk a(World p_a_1_, NBTTagCompound p_a_2_)
    {
        int i = p_a_2_.getInt("xPos");
        int j = p_a_2_.getInt("zPos");
        Chunk chunk = new Chunk(p_a_1_, i, j);
        chunk.a(p_a_2_.getIntArray("HeightMap"));
        chunk.d(p_a_2_.getBoolean("TerrainPopulated"));
        chunk.e(p_a_2_.getBoolean("LightPopulated"));
        chunk.c(p_a_2_.getLong("InhabitedTime"));
        NBTTagList nbttaglist = p_a_2_.getList("Sections", 10);
        byte b0 = 16;
        ChunkSection[] achunksection = new ChunkSection[b0];
        boolean flag = !p_a_1_.worldProvider.o();

        for (int k = 0; k < nbttaglist.size(); ++k)
        {
            NBTTagCompound nbttagcompound = nbttaglist.get(k);
            byte b1 = nbttagcompound.getByte("Y");
            ChunkSection chunksection = new ChunkSection(b1 << 4, flag);
            byte[] abyte = nbttagcompound.getByteArray("Blocks");
            NibbleArray nibblearray = new NibbleArray(nbttagcompound.getByteArray("Data"));
            NibbleArray nibblearray1 = nbttagcompound.hasKeyOfType("Add", 7) ? new NibbleArray(nbttagcompound.getByteArray("Add")) : null;
            char[] achar = new char[abyte.length];

            for (int l = 0; l < achar.length; ++l)
            {
                int i1 = l & 15;
                int j1 = l >> 8 & 15;
                int k1 = l >> 4 & 15;
                int l1 = nibblearray1 != null ? nibblearray1.a(i1, j1, k1) : 0;
                int i2 = abyte[l] & 255;
                int j2 = nibblearray.a(i1, j1, k1);
                int k2 = l1 << 12 | i2 << 4 | j2;

                if (Block.d.a(k2) == null)
                {
                    Block block = Block.getById(l1 << 8 | i2);

                    if (block != null)
                    {
                        try
                        {
                            j2 = block.toLegacyData(block.fromLegacyData(j2));
                        }
                        catch (Exception var28)
                        {
                            j2 = block.toLegacyData(block.getBlockData());
                        }

                        k2 = l1 << 12 | i2 << 4 | j2;
                    }
                }

                achar[l] = (char)k2;
            }

            chunksection.a(achar);
            chunksection.a(new NibbleArray(nbttagcompound.getByteArray("BlockLight")));

            if (flag)
            {
                chunksection.b(new NibbleArray(nbttagcompound.getByteArray("SkyLight")));
            }

            chunksection.recalcBlockCounts();
            achunksection[b1] = chunksection;
        }

        chunk.a(achunksection);

        if (p_a_2_.hasKeyOfType("Biomes", 7))
        {
            chunk.a(p_a_2_.getByteArray("Biomes"));
        }

        return chunk;
    }

    public void loadEntities(Chunk p_loadEntities_1_, NBTTagCompound p_loadEntities_2_, World p_loadEntities_3_)
    {
        p_loadEntities_3_.timings.syncChunkLoadEntitiesTimer.startTiming();
        NBTTagList nbttaglist = p_loadEntities_2_.getList("Entities", 10);

        if (nbttaglist != null)
        {
            for (int i = 0; i < nbttaglist.size(); ++i)
            {
                NBTTagCompound nbttagcompound = nbttaglist.get(i);
                Entity entity = EntityTypes.a(nbttagcompound, p_loadEntities_3_);
                p_loadEntities_1_.g(true);

                if (entity != null)
                {
                    p_loadEntities_1_.a(entity);
                    Entity entity1 = entity;

                    for (NBTTagCompound nbttagcompound1 = nbttagcompound; nbttagcompound1.hasKeyOfType("Riding", 10); nbttagcompound1 = nbttagcompound1.getCompound("Riding"))
                    {
                        Entity entity2 = EntityTypes.a(nbttagcompound1.getCompound("Riding"), p_loadEntities_3_);

                        if (entity2 != null)
                        {
                            p_loadEntities_1_.a(entity2);
                            entity1.mount(entity2);
                        }

                        entity1 = entity2;
                    }
                }
            }
        }

        p_loadEntities_3_.timings.syncChunkLoadEntitiesTimer.stopTiming();
        p_loadEntities_3_.timings.syncChunkLoadTileEntitiesTimer.startTiming();
        NBTTagList nbttaglist1 = p_loadEntities_2_.getList("TileEntities", 10);

        if (nbttaglist1 != null)
        {
            for (int j = 0; j < nbttaglist1.size(); ++j)
            {
                NBTTagCompound nbttagcompound2 = nbttaglist1.get(j);
                TileEntity tileentity = TileEntity.c(nbttagcompound2);

                if (tileentity != null)
                {
                    p_loadEntities_1_.a(tileentity);
                }
            }
        }

        p_loadEntities_3_.timings.syncChunkLoadTileEntitiesTimer.stopTiming();
        p_loadEntities_3_.timings.syncChunkLoadTileTicksTimer.startTiming();

        if (p_loadEntities_2_.hasKeyOfType("TileTicks", 9))
        {
            NBTTagList nbttaglist2 = p_loadEntities_2_.getList("TileTicks", 10);

            if (nbttaglist2 != null)
            {
                for (int k = 0; k < nbttaglist2.size(); ++k)
                {
                    NBTTagCompound nbttagcompound3 = nbttaglist2.get(k);
                    Block block;

                    if (nbttagcompound3.hasKeyOfType("i", 8))
                    {
                        block = Block.getByName(nbttagcompound3.getString("i"));
                    }
                    else
                    {
                        block = Block.getById(nbttagcompound3.getInt("i"));
                    }

                    p_loadEntities_3_.b(new BlockPosition(nbttagcompound3.getInt("x"), nbttagcompound3.getInt("y"), nbttagcompound3.getInt("z")), block, nbttagcompound3.getInt("t"), nbttagcompound3.getInt("p"));
                }
            }
        }

        p_loadEntities_3_.timings.syncChunkLoadTileTicksTimer.stopTiming();
    }
}
