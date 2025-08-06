package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.chunkio.ChunkIOExecutor;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHashSet;
import org.bukkit.craftbukkit.v1_8_R3.util.LongObjectHashMap;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.generator.BlockPopulator;

public class ChunkProviderServer implements IChunkProvider
{
    private static final Logger b = LogManager.getLogger();
    public LongHashSet unloadQueue = new LongHashSet();
    public Chunk emptyChunk;
    public IChunkProvider chunkProvider;
    private IChunkLoader chunkLoader;
    public boolean forceChunkLoad = false;
    public LongObjectHashMap<Chunk> chunks = new LongObjectHashMap();
    public WorldServer world;

    public ChunkProviderServer(WorldServer p_i306_1_, IChunkLoader p_i306_2_, IChunkProvider p_i306_3_)
    {
        this.emptyChunk = new EmptyChunk(p_i306_1_, 0, 0);
        this.world = p_i306_1_;
        this.chunkLoader = p_i306_2_;
        this.chunkProvider = p_i306_3_;
    }

    public boolean isChunkLoaded(int p_isChunkLoaded_1_, int p_isChunkLoaded_2_)
    {
        return this.chunks.containsKey(LongHash.toLong(p_isChunkLoaded_1_, p_isChunkLoaded_2_));
    }

    public Collection a()
    {
        return this.chunks.values();
    }

    public void queueUnload(int p_queueUnload_1_, int p_queueUnload_2_)
    {
        if (this.world.worldProvider.e())
        {
            if (!this.world.c(p_queueUnload_1_, p_queueUnload_2_))
            {
                this.unloadQueue.add(p_queueUnload_1_, p_queueUnload_2_);
                Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(p_queueUnload_1_, p_queueUnload_2_));

                if (chunk != null)
                {
                    chunk.mustSave = true;
                }
            }
        }
        else
        {
            this.unloadQueue.add(p_queueUnload_1_, p_queueUnload_2_);
            Chunk chunk1 = (Chunk)this.chunks.get(LongHash.toLong(p_queueUnload_1_, p_queueUnload_2_));

            if (chunk1 != null)
            {
                chunk1.mustSave = true;
            }
        }
    }

    public void b()
    {
        for (Chunk chunk : this.chunks.values())
        {
            this.queueUnload(chunk.locX, chunk.locZ);
        }
    }

    public Chunk getChunkIfLoaded(int p_getChunkIfLoaded_1_, int p_getChunkIfLoaded_2_)
    {
        return (Chunk)this.chunks.get(LongHash.toLong(p_getChunkIfLoaded_1_, p_getChunkIfLoaded_2_));
    }

    public Chunk getChunkAt(int p_getChunkAt_1_, int p_getChunkAt_2_)
    {
        return this.getChunkAt(p_getChunkAt_1_, p_getChunkAt_2_, (Runnable)null);
    }

    public Chunk getChunkAt(int p_getChunkAt_1_, int p_getChunkAt_2_, Runnable p_getChunkAt_3_)
    {
        this.unloadQueue.remove(p_getChunkAt_1_, p_getChunkAt_2_);
        Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(p_getChunkAt_1_, p_getChunkAt_2_));
        ChunkRegionLoader chunkregionloader = null;

        if (this.chunkLoader instanceof ChunkRegionLoader)
        {
            chunkregionloader = (ChunkRegionLoader)this.chunkLoader;
        }

        if (chunk == null && chunkregionloader != null && chunkregionloader.chunkExists(this.world, p_getChunkAt_1_, p_getChunkAt_2_))
        {
            if (p_getChunkAt_3_ != null)
            {
                ChunkIOExecutor.queueChunkLoad(this.world, chunkregionloader, this, p_getChunkAt_1_, p_getChunkAt_2_, p_getChunkAt_3_);
                return null;
            }

            chunk = ChunkIOExecutor.syncChunkLoad(this.world, chunkregionloader, this, p_getChunkAt_1_, p_getChunkAt_2_);
        }
        else if (chunk == null)
        {
            chunk = this.originalGetChunkAt(p_getChunkAt_1_, p_getChunkAt_2_);
        }

        if (p_getChunkAt_3_ != null)
        {
            p_getChunkAt_3_.run();
        }

        return chunk;
    }

    public Chunk originalGetChunkAt(int p_originalGetChunkAt_1_, int p_originalGetChunkAt_2_)
    {
        this.unloadQueue.remove(p_originalGetChunkAt_1_, p_originalGetChunkAt_2_);
        Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(p_originalGetChunkAt_1_, p_originalGetChunkAt_2_));
        boolean flag = false;

        if (chunk == null)
        {
            this.world.timings.syncChunkLoadTimer.startTiming();
            chunk = this.loadChunk(p_originalGetChunkAt_1_, p_originalGetChunkAt_2_);

            if (chunk == null)
            {
                if (this.chunkProvider == null)
                {
                    chunk = this.emptyChunk;
                }
                else
                {
                    try
                    {
                        chunk = this.chunkProvider.getOrCreateChunk(p_originalGetChunkAt_1_, p_originalGetChunkAt_2_);
                    }
                    catch (Throwable throwable1)
                    {
                        CrashReport crashreport = CrashReport.a(throwable1, "Exception generating new chunk");
                        CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Chunk to be generated");
                        crashreportsystemdetails.a((String)"Location", String.format("%d,%d", new Object[] {Integer.valueOf(p_originalGetChunkAt_1_), Integer.valueOf(p_originalGetChunkAt_2_)}));
                        crashreportsystemdetails.a((String)"Position hash", Long.valueOf(LongHash.toLong(p_originalGetChunkAt_1_, p_originalGetChunkAt_2_)));
                        crashreportsystemdetails.a((String)"Generator", this.chunkProvider.getName());
                        throw new ReportedException(crashreport);
                    }
                }

                flag = true;
            }

            this.chunks.put(LongHash.toLong(p_originalGetChunkAt_1_, p_originalGetChunkAt_2_), chunk);
            chunk.addEntities();
            Server server = this.world.getServer();

            if (server != null)
            {
                server.getPluginManager().callEvent(new ChunkLoadEvent(chunk.bukkitChunk, flag));
            }

            for (int i = -2; i < 3; ++i)
            {
                for (int j = -2; j < 3; ++j)
                {
                    if (i != 0 || j != 0)
                    {
                        Chunk chunk1 = this.getChunkIfLoaded(chunk.locX + i, chunk.locZ + j);

                        if (chunk1 != null)
                        {
                            chunk1.setNeighborLoaded(-i, -j);
                            chunk.setNeighborLoaded(i, j);
                        }
                    }
                }
            }

            chunk.loadNearby(this, this, p_originalGetChunkAt_1_, p_originalGetChunkAt_2_);
            this.world.timings.syncChunkLoadTimer.stopTiming();
        }

        return chunk;
    }

    public Chunk getOrCreateChunk(int p_getOrCreateChunk_1_, int p_getOrCreateChunk_2_)
    {
        Chunk chunk = (Chunk)this.chunks.get(LongHash.toLong(p_getOrCreateChunk_1_, p_getOrCreateChunk_2_));
        chunk = chunk == null ? (!this.world.ad() && !this.forceChunkLoad ? this.emptyChunk : this.getChunkAt(p_getOrCreateChunk_1_, p_getOrCreateChunk_2_)) : chunk;

        if (chunk == this.emptyChunk)
        {
            return chunk;
        }
        else
        {
            if (p_getOrCreateChunk_1_ != chunk.locX || p_getOrCreateChunk_2_ != chunk.locZ)
            {
                b.error("Chunk (" + chunk.locX + ", " + chunk.locZ + ") stored at  (" + p_getOrCreateChunk_1_ + ", " + p_getOrCreateChunk_2_ + ") in world \'" + this.world.getWorld().getName() + "\'");
                b.error(chunk.getClass().getName());
                Throwable throwable = new Throwable();
                throwable.fillInStackTrace();
                throwable.printStackTrace();
            }

            return chunk;
        }
    }

    public Chunk loadChunk(int p_loadChunk_1_, int p_loadChunk_2_)
    {
        if (this.chunkLoader == null)
        {
            return null;
        }
        else
        {
            try
            {
                Chunk chunk = this.chunkLoader.a(this.world, p_loadChunk_1_, p_loadChunk_2_);

                if (chunk != null)
                {
                    chunk.setLastSaved(this.world.getTime());

                    if (this.chunkProvider != null)
                    {
                        this.world.timings.syncChunkLoadStructuresTimer.startTiming();
                        this.chunkProvider.recreateStructures(chunk, p_loadChunk_1_, p_loadChunk_2_);
                        this.world.timings.syncChunkLoadStructuresTimer.stopTiming();
                    }
                }

                return chunk;
            }
            catch (Exception exception)
            {
                b.error((String)"Couldn\'t load chunk", (Throwable)exception);
                return null;
            }
        }
    }

    public void saveChunkNOP(Chunk p_saveChunkNOP_1_)
    {
        if (this.chunkLoader != null)
        {
            try
            {
                this.chunkLoader.b(this.world, p_saveChunkNOP_1_);
            }
            catch (Exception exception)
            {
                b.error((String)"Couldn\'t save entities", (Throwable)exception);
            }
        }
    }

    public void saveChunk(Chunk p_saveChunk_1_)
    {
        if (this.chunkLoader != null)
        {
            try
            {
                p_saveChunk_1_.setLastSaved(this.world.getTime());
                this.chunkLoader.a(this.world, p_saveChunk_1_);
            }
            catch (IOException ioexception)
            {
                b.error((String)"Couldn\'t save chunk", (Throwable)ioexception);
            }
            catch (ExceptionWorldConflict exceptionworldconflict)
            {
                b.error((String)"Couldn\'t save chunk; already in use by another instance of Minecraft?", (Throwable)exceptionworldconflict);
            }
        }
    }

    public void getChunkAt(IChunkProvider p_getChunkAt_1_, int p_getChunkAt_2_, int p_getChunkAt_3_)
    {
        Chunk chunk = this.getOrCreateChunk(p_getChunkAt_2_, p_getChunkAt_3_);

        if (!chunk.isDone())
        {
            chunk.n();

            if (this.chunkProvider != null)
            {
                this.chunkProvider.getChunkAt(p_getChunkAt_1_, p_getChunkAt_2_, p_getChunkAt_3_);
                BlockSand.instaFall = true;
                Random random = new Random();
                random.setSeed(this.world.getSeed());
                long i = random.nextLong() / 2L * 2L + 1L;
                long j = random.nextLong() / 2L * 2L + 1L;
                random.setSeed((long)p_getChunkAt_2_ * i + (long)p_getChunkAt_3_ * j ^ this.world.getSeed());
                org.bukkit.World world = this.world.getWorld();

                if (world != null)
                {
                    this.world.populating = true;

                    try
                    {
                        for (BlockPopulator blockpopulator : world.getPopulators())
                        {
                            blockpopulator.populate(world, random, chunk.bukkitChunk);
                        }
                    }
                    finally
                    {
                        this.world.populating = false;
                    }
                }

                BlockSand.instaFall = false;
                this.world.getServer().getPluginManager().callEvent(new ChunkPopulateEvent(chunk.bukkitChunk));
                chunk.e();
            }
        }
    }

    public boolean a(IChunkProvider p_a_1_, Chunk p_a_2_, int p_a_3_, int p_a_4_)
    {
        if (this.chunkProvider != null && this.chunkProvider.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_))
        {
            Chunk chunk = this.getOrCreateChunk(p_a_3_, p_a_4_);
            chunk.e();
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean saveChunks(boolean p_saveChunks_1_, IProgressUpdate p_saveChunks_2_)
    {
        int i = 0;

        for (Chunk chunk : this.chunks.values())
        {
            if (p_saveChunks_1_)
            {
                this.saveChunkNOP(chunk);
            }

            if (chunk.a(p_saveChunks_1_))
            {
                this.saveChunk(chunk);
                chunk.f(false);
                ++i;

                if (i == 24)
                {
                    ;
                }
            }
        }

        return true;
    }

    public void c()
    {
        if (this.chunkLoader != null)
        {
            this.chunkLoader.b();
        }
    }

    public boolean unloadChunks()
    {
        if (!this.world.savingDisabled)
        {
            Server server = this.world.getServer();

            for (int i = 0; i < 100 && !this.unloadQueue.isEmpty(); ++i)
            {
                long j = this.unloadQueue.popFirst();
                Chunk chunk = (Chunk)this.chunks.get(j);

                if (chunk != null)
                {
                    ChunkUnloadEvent chunkunloadevent = new ChunkUnloadEvent(chunk.bukkitChunk);
                    server.getPluginManager().callEvent(chunkunloadevent);

                    if (!chunkunloadevent.isCancelled())
                    {
                        if (chunk != null)
                        {
                            chunk.removeEntities();
                            this.saveChunk(chunk);
                            this.saveChunkNOP(chunk);
                            this.chunks.remove(j);
                        }

                        for (int k = -2; k < 3; ++k)
                        {
                            for (int l = -2; l < 3; ++l)
                            {
                                if (k != 0 || l != 0)
                                {
                                    Chunk chunk1 = this.getChunkIfLoaded(chunk.locX + k, chunk.locZ + l);

                                    if (chunk1 != null)
                                    {
                                        chunk1.setNeighborUnloaded(-k, -l);
                                        chunk.setNeighborUnloaded(k, l);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (this.chunkLoader != null)
            {
                this.chunkLoader.a();
            }
        }

        return this.chunkProvider.unloadChunks();
    }

    public boolean canSave()
    {
        return !this.world.savingDisabled;
    }

    public String getName()
    {
        return "ServerChunkCache: " + this.chunks.size() + " Drop: " + this.unloadQueue.size();
    }

    public List<BiomeBase.BiomeMeta> getMobsFor(EnumCreatureType p_getMobsFor_1_, BlockPosition p_getMobsFor_2_)
    {
        return this.chunkProvider.getMobsFor(p_getMobsFor_1_, p_getMobsFor_2_);
    }

    public BlockPosition findNearestMapFeature(World p_findNearestMapFeature_1_, String p_findNearestMapFeature_2_, BlockPosition p_findNearestMapFeature_3_)
    {
        return this.chunkProvider.findNearestMapFeature(p_findNearestMapFeature_1_, p_findNearestMapFeature_2_, p_findNearestMapFeature_3_);
    }

    public int getLoadedChunks()
    {
        return this.chunks.size();
    }

    public void recreateStructures(Chunk p_recreateStructures_1_, int p_recreateStructures_2_, int p_recreateStructures_3_)
    {
    }

    public Chunk getChunkAt(BlockPosition p_getChunkAt_1_)
    {
        return this.getOrCreateChunk(p_getChunkAt_1_.getX() >> 4, p_getChunkAt_1_.getZ() >> 4);
    }
}
