package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import gnu.trove.iterator.TLongShortIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WeatherType;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_8_R3.CraftTravelAgent;
import org.bukkit.craftbukkit.v1_8_R3.generator.CustomChunkGenerator;
import org.bukkit.craftbukkit.v1_8_R3.generator.InternalChunkGenerator;
import org.bukkit.craftbukkit.v1_8_R3.generator.NetherChunkGenerator;
import org.bukkit.craftbukkit.v1_8_R3.generator.NormalChunkGenerator;
import org.bukkit.craftbukkit.v1_8_R3.generator.SkyLandsChunkGenerator;
import org.bukkit.craftbukkit.v1_8_R3.util.HashTreeSet;
import org.bukkit.entity.LightningStrike;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.generator.ChunkGenerator;

public class WorldServer extends World implements IAsyncTaskHandler
{
    private static final Logger a = LogManager.getLogger();
    private final MinecraftServer server;
    public EntityTracker tracker;
    private final PlayerChunkMap manager;
    private final HashTreeSet<NextTickListEntry> M = new HashTreeSet();
    private final Map<UUID, Entity> entitiesByUUID = Maps.<UUID, Entity>newHashMap();
    public ChunkProviderServer chunkProviderServer;
    public boolean savingDisabled;
    private boolean O;
    private int emptyTime;
    private final PortalTravelAgent Q;
    private final SpawnerCreature R = new SpawnerCreature();
    protected final VillageSiege siegeManager = new VillageSiege(this);
    private WorldServer.BlockActionDataList[] S = new WorldServer.BlockActionDataList[] {new WorldServer.BlockActionDataList((Object)null), new WorldServer.BlockActionDataList((Object)null)};
    private int T;
    private static final List<StructurePieceTreasure> U = Lists.newArrayList(new StructurePieceTreasure[] {new StructurePieceTreasure(Items.STICK, 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.PLANKS), 0, 1, 3, 10), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG), 0, 1, 3, 10), new StructurePieceTreasure(Items.STONE_AXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOODEN_AXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.STONE_PICKAXE, 0, 1, 1, 3), new StructurePieceTreasure(Items.WOODEN_PICKAXE, 0, 1, 1, 5), new StructurePieceTreasure(Items.APPLE, 0, 2, 3, 5), new StructurePieceTreasure(Items.BREAD, 0, 2, 3, 3), new StructurePieceTreasure(Item.getItemOf(Blocks.LOG2), 0, 1, 3, 10)});
    private List<NextTickListEntry> V = Lists.<NextTickListEntry>newArrayList();
    public final int dimension;

    public WorldServer(MinecraftServer p_i180_1_, IDataManager p_i180_2_, WorldData p_i180_3_, int p_i180_4_, MethodProfiler p_i180_5_, Environment p_i180_6_, ChunkGenerator p_i180_7_)
    {
        super(p_i180_2_, p_i180_3_, WorldProvider.byDimension(p_i180_6_.getId()), p_i180_5_, false, p_i180_7_, p_i180_6_);
        this.dimension = p_i180_4_;
        this.pvpMode = p_i180_1_.getPVP();
        p_i180_3_.world = this;
        this.server = p_i180_1_;
        this.tracker = new EntityTracker(this);
        this.manager = new PlayerChunkMap(this, this.spigotConfig.viewDistance);
        this.worldProvider.a(this);
        this.chunkProvider = this.k();
        this.Q = new CraftTravelAgent(this);
        this.B();
        this.C();
        this.getWorldBorder().a(p_i180_1_.aI());
    }

    public World b()
    {
        this.worldMaps = new PersistentCollection(this.dataManager);
        String s = PersistentVillage.a(this.worldProvider);
        PersistentVillage persistentvillage = (PersistentVillage)this.worldMaps.get(PersistentVillage.class, s);

        if (persistentvillage == null)
        {
            this.villages = new PersistentVillage(this);
            this.worldMaps.a(s, this.villages);
        }
        else
        {
            this.villages = persistentvillage;
            this.villages.a((World)this);
        }

        if (this.getServer().getScoreboardManager() == null)
        {
            this.scoreboard = new ScoreboardServer(this.server);
            PersistentScoreboard persistentscoreboard = (PersistentScoreboard)this.worldMaps.get(PersistentScoreboard.class, "scoreboard");

            if (persistentscoreboard == null)
            {
                persistentscoreboard = new PersistentScoreboard();
                this.worldMaps.a("scoreboard", persistentscoreboard);
            }

            persistentscoreboard.a(this.scoreboard);
            ((ScoreboardServer)this.scoreboard).a(persistentscoreboard);
        }
        else
        {
            this.scoreboard = this.getServer().getScoreboardManager().getMainScoreboard().getHandle();
        }

        this.getWorldBorder().setCenter(this.worldData.C(), this.worldData.D());
        this.getWorldBorder().setDamageAmount(this.worldData.I());
        this.getWorldBorder().setDamageBuffer(this.worldData.H());
        this.getWorldBorder().setWarningDistance(this.worldData.J());
        this.getWorldBorder().setWarningTime(this.worldData.K());

        if (this.worldData.F() > 0L)
        {
            this.getWorldBorder().transitionSizeBetween(this.worldData.E(), this.worldData.G(), this.worldData.F());
        }
        else
        {
            this.getWorldBorder().setSize(this.worldData.E());
        }

        if (this.generator != null)
        {
            this.getWorld().getPopulators().addAll(this.generator.getDefaultPopulators(this.getWorld()));
        }

        return this;
    }

    public TileEntity getTileEntity(BlockPosition p_getTileEntity_1_)
    {
        TileEntity tileentity = super.getTileEntity(p_getTileEntity_1_);
        Block block = this.getType(p_getTileEntity_1_).getBlock();

        if (block != Blocks.CHEST && block != Blocks.TRAPPED_CHEST)
        {
            if (block == Blocks.FURNACE)
            {
                if (!(tileentity instanceof TileEntityFurnace))
                {
                    tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                }
            }
            else if (block == Blocks.DROPPER)
            {
                if (!(tileentity instanceof TileEntityDropper))
                {
                    tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                }
            }
            else if (block == Blocks.DISPENSER)
            {
                if (!(tileentity instanceof TileEntityDispenser))
                {
                    tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                }
            }
            else if (block == Blocks.JUKEBOX)
            {
                if (!(tileentity instanceof BlockJukeBox.TileEntityRecordPlayer))
                {
                    tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                }
            }
            else if (block == Blocks.NOTEBLOCK)
            {
                if (!(tileentity instanceof TileEntityNote))
                {
                    tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                }
            }
            else if (block == Blocks.MOB_SPAWNER)
            {
                if (!(tileentity instanceof TileEntityMobSpawner))
                {
                    tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                }
            }
            else if (block != Blocks.STANDING_SIGN && block != Blocks.WALL_SIGN)
            {
                if (block == Blocks.ENDER_CHEST)
                {
                    if (!(tileentity instanceof TileEntityEnderChest))
                    {
                        tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                    }
                }
                else if (block == Blocks.BREWING_STAND)
                {
                    if (!(tileentity instanceof TileEntityBrewingStand))
                    {
                        tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                    }
                }
                else if (block == Blocks.BEACON)
                {
                    if (!(tileentity instanceof TileEntityBeacon))
                    {
                        tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                    }
                }
                else if (block == Blocks.HOPPER && !(tileentity instanceof TileEntityHopper))
                {
                    tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
                }
            }
            else if (!(tileentity instanceof TileEntitySign))
            {
                tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
            }
        }
        else if (!(tileentity instanceof TileEntityChest))
        {
            tileentity = this.fixTileEntity(p_getTileEntity_1_, block, tileentity);
        }

        return tileentity;
    }

    private TileEntity fixTileEntity(BlockPosition p_fixTileEntity_1_, Block p_fixTileEntity_2_, TileEntity p_fixTileEntity_3_)
    {
        this.getServer().getLogger().log(Level.SEVERE, "Block at {0},{1},{2} is {3} but has {4}. Bukkit will attempt to fix this, but there may be additional damage that we cannot recover.", new Object[] {Integer.valueOf(p_fixTileEntity_1_.getX()), Integer.valueOf(p_fixTileEntity_1_.getY()), Integer.valueOf(p_fixTileEntity_1_.getZ()), org.bukkit.Material.getMaterial(Block.getId(p_fixTileEntity_2_)).toString(), p_fixTileEntity_3_});

        if (p_fixTileEntity_2_ instanceof IContainer)
        {
            TileEntity tileentity = ((IContainer)p_fixTileEntity_2_).a(this, p_fixTileEntity_2_.toLegacyData(this.getType(p_fixTileEntity_1_)));
            tileentity.world = this;
            this.setTileEntity(p_fixTileEntity_1_, tileentity);
            return tileentity;
        }
        else
        {
            this.getServer().getLogger().severe("Don\'t know how to fix for this type... Can\'t do anything! :(");
            return p_fixTileEntity_3_;
        }
    }

    private boolean canSpawn(int p_canSpawn_1_, int p_canSpawn_2_)
    {
        return this.generator != null ? this.generator.canSpawn(this.getWorld(), p_canSpawn_1_, p_canSpawn_2_) : this.worldProvider.canSpawn(p_canSpawn_1_, p_canSpawn_2_);
    }

    public void doTick()
    {
        super.doTick();

        if (this.getWorldData().isHardcore() && this.getDifficulty() != EnumDifficulty.HARD)
        {
            this.getWorldData().setDifficulty(EnumDifficulty.HARD);
        }

        this.worldProvider.m().b();

        if (this.everyoneDeeplySleeping())
        {
            if (this.getGameRules().getBoolean("doDaylightCycle"))
            {
                long i = this.worldData.getDayTime() + 24000L;
                this.worldData.setDayTime(i - i % 24000L);
            }

            this.e();
        }

        long k = this.worldData.getTime();

        if (this.getGameRules().getBoolean("doMobSpawning") && this.worldData.getType() != WorldType.DEBUG_ALL_BLOCK_STATES && (this.allowMonsters || this.allowAnimals) && this instanceof WorldServer && this.players.size() > 0)
        {
            this.timings.mobSpawn.startTiming();
            this.R.a(this, this.allowMonsters && this.ticksPerMonsterSpawns != 0L && k % this.ticksPerMonsterSpawns == 0L, this.allowAnimals && this.ticksPerAnimalSpawns != 0L && k % this.ticksPerAnimalSpawns == 0L, this.worldData.getTime() % 400L == 0L);
            this.timings.mobSpawn.stopTiming();
        }

        this.timings.doChunkUnload.startTiming();
        this.methodProfiler.c("chunkSource");
        this.chunkProvider.unloadChunks();
        int j = this.a(1.0F);

        if (j != this.ab())
        {
            this.c(j);
        }

        this.worldData.setTime(this.worldData.getTime() + 1L);

        if (this.getGameRules().getBoolean("doDaylightCycle"))
        {
            this.worldData.setDayTime(this.worldData.getDayTime() + 1L);
        }

        this.timings.doChunkUnload.stopTiming();
        this.methodProfiler.c("tickPending");
        this.timings.doTickPending.startTiming();
        this.a(false);
        this.timings.doTickPending.stopTiming();
        this.methodProfiler.c("tickBlocks");
        this.timings.doTickTiles.startTiming();
        this.h();
        this.timings.doTickTiles.stopTiming();
        this.methodProfiler.c("chunkMap");
        this.timings.doChunkMap.startTiming();
        this.manager.flush();
        this.timings.doChunkMap.stopTiming();
        this.methodProfiler.c("village");
        this.timings.doVillages.startTiming();
        this.villages.tick();
        this.siegeManager.a();
        this.timings.doVillages.stopTiming();
        this.methodProfiler.c("portalForcer");
        this.timings.doPortalForcer.startTiming();
        this.Q.a(this.getTime());
        this.timings.doPortalForcer.stopTiming();
        this.methodProfiler.b();
        this.timings.doSounds.startTiming();
        this.ak();
        this.getWorld().processChunkGC();
        this.timings.doChunkGC.stopTiming();
    }

    public BiomeBase.BiomeMeta a(EnumCreatureType p_a_1_, BlockPosition p_a_2_)
    {
        List list = this.N().getMobsFor(p_a_1_, p_a_2_);
        return list != null && !list.isEmpty() ? (BiomeBase.BiomeMeta)WeightedRandom.a(this.random, list) : null;
    }

    public boolean a(EnumCreatureType p_a_1_, BiomeBase.BiomeMeta p_a_2_, BlockPosition p_a_3_)
    {
        List list = this.N().getMobsFor(p_a_1_, p_a_3_);
        return list != null && !list.isEmpty() ? list.contains(p_a_2_) : false;
    }

    public void everyoneSleeping()
    {
        this.O = false;

        if (!this.players.isEmpty())
        {
            int i = 0;
            int j = 0;

            for (EntityHuman entityhuman : this.players)
            {
                if (entityhuman.isSpectator())
                {
                    ++i;
                }
                else if (entityhuman.isSleeping() || entityhuman.fauxSleeping)
                {
                    ++j;
                }
            }

            this.O = j > 0 && j >= this.players.size() - i;
        }
    }

    protected void e()
    {
        this.O = false;

        for (EntityHuman entityhuman : this.players)
        {
            if (entityhuman.isSleeping())
            {
                entityhuman.a(false, false, true);
            }
        }

        this.ag();
    }

    private void ag()
    {
        this.worldData.setStorm(false);

        if (!this.worldData.hasStorm())
        {
            this.worldData.setWeatherDuration(0);
        }

        this.worldData.setThundering(false);

        if (!this.worldData.isThundering())
        {
            this.worldData.setThunderDuration(0);
        }
    }

    public boolean everyoneDeeplySleeping()
    {
        if (this.O && !this.isClientSide)
        {
            Iterator iterator = this.players.iterator();
            boolean flag = false;

            while (iterator.hasNext())
            {
                EntityHuman entityhuman = (EntityHuman)iterator.next();

                if (entityhuman.isDeeplySleeping())
                {
                    flag = true;
                }

                if (entityhuman.isSpectator() && !entityhuman.isDeeplySleeping() && !entityhuman.fauxSleeping)
                {
                    return false;
                }
            }

            return flag;
        }
        else
        {
            return false;
        }
    }

    protected void h()
    {
        super.h();

        if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES)
        {
            TLongShortIterator tlongshortiterator = this.chunkTickList.iterator();

            while (tlongshortiterator.hasNext())
            {
                tlongshortiterator.advance();
                long i = tlongshortiterator.key();
                this.getChunkAt(World.keyToX(i), World.keyToZ(i)).b(false);
            }
        }
        else
        {
            TLongShortIterator tlongshortiterator1 = this.chunkTickList.iterator();

            while (tlongshortiterator1.hasNext())
            {
                tlongshortiterator1.advance();
                long l2 = tlongshortiterator1.key();
                int j = World.keyToX(l2);
                int k = World.keyToZ(l2);

                if (this.chunkProvider.isChunkLoaded(j, k) && !this.chunkProviderServer.unloadQueue.contains(j, k))
                {
                    int l = j * 16;
                    int i1 = k * 16;
                    this.methodProfiler.a("getChunk");
                    Chunk chunk = this.getChunkAt(j, k);
                    this.a(l, i1, chunk);
                    this.methodProfiler.c("tickChunk");
                    chunk.b(false);
                    this.methodProfiler.c("thunder");

                    if (this.random.nextInt(100000) == 0 && this.S() && this.R())
                    {
                        this.m = this.m * 3 + 1013904223;
                        int j1 = this.m >> 2;
                        BlockPosition blockposition = this.a(new BlockPosition(l + (j1 & 15), 0, i1 + (j1 >> 8 & 15)));

                        if (this.isRainingAt(blockposition))
                        {
                            this.strikeLightning(new EntityLightning(this, (double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ()));
                        }
                    }

                    this.methodProfiler.c("iceandsnow");

                    if (this.random.nextInt(16) == 0)
                    {
                        this.m = this.m * 3 + 1013904223;
                        int i3 = this.m >> 2;
                        BlockPosition blockposition2 = this.q(new BlockPosition(l + (i3 & 15), 0, i1 + (i3 >> 8 & 15)));
                        BlockPosition blockposition1 = blockposition2.down();

                        if (this.w(blockposition1))
                        {
                            org.bukkit.block.BlockState blockstate = this.getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()).getState();
                            blockstate.setTypeId(Block.getId(Blocks.ICE));
                            BlockFormEvent blockformevent = new BlockFormEvent(blockstate.getBlock(), blockstate);
                            this.getServer().getPluginManager().callEvent(blockformevent);

                            if (!blockformevent.isCancelled())
                            {
                                blockstate.update(true);
                            }
                        }

                        if (this.S() && this.f(blockposition2, true))
                        {
                            org.bukkit.block.BlockState blockstate1 = this.getWorld().getBlockAt(blockposition2.getX(), blockposition2.getY(), blockposition2.getZ()).getState();
                            blockstate1.setTypeId(Block.getId(Blocks.SNOW_LAYER));
                            BlockFormEvent blockformevent1 = new BlockFormEvent(blockstate1.getBlock(), blockstate1);
                            this.getServer().getPluginManager().callEvent(blockformevent1);

                            if (!blockformevent1.isCancelled())
                            {
                                blockstate1.update(true);
                            }
                        }

                        if (this.S() && this.getBiome(blockposition1).e())
                        {
                            this.getType(blockposition1).getBlock().k(this, blockposition1);
                        }
                    }

                    this.methodProfiler.c("tickBlocks");
                    int j3 = this.getGameRules().c("randomTickSpeed");

                    if (j3 > 0)
                    {
                        for (ChunkSection chunksection : chunk.getSections())
                        {
                            if (chunksection != null && chunksection.shouldTick())
                            {
                                for (int k1 = 0; k1 < j3; ++k1)
                                {
                                    this.m = this.m * 3 + 1013904223;
                                    int l1 = this.m >> 2;
                                    int i2 = l1 & 15;
                                    int j2 = l1 >> 8 & 15;
                                    int k2 = l1 >> 16 & 15;
                                    IBlockData iblockdata = chunksection.getType(i2, k2, j2);
                                    Block block = iblockdata.getBlock();

                                    if (block.isTicking())
                                    {
                                        block.a((World)this, new BlockPosition(i2 + l, k2 + chunksection.getYPosition(), j2 + i1), (IBlockData)iblockdata, (Random)this.random);
                                    }
                                }
                            }
                        }
                    }
                }
                else
                {
                    tlongshortiterator1.remove();
                }
            }
        }

        if (this.spigotConfig.clearChunksOnTick)
        {
            this.chunkTickList.clear();
        }
    }

    protected BlockPosition a(BlockPosition p_a_1_)
    {
        BlockPosition blockposition = this.q(p_a_1_);
        AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockposition, new BlockPosition(blockposition.getX(), this.getHeight(), blockposition.getZ()))).grow(3.0D, 3.0D, 3.0D);
        List list = this.a(EntityLiving.class, axisalignedbb, new Predicate()
        {
            public boolean a(EntityLiving p_a_1_)
            {
                return p_a_1_ != null && p_a_1_.isAlive() && WorldServer.this.i(p_a_1_.getChunkCoordinates());
            }
            public boolean apply(Object p_apply_1_)
            {
                return this.a((EntityLiving)p_apply_1_);
            }
        });
        return !list.isEmpty() ? ((EntityLiving)list.get(this.random.nextInt(list.size()))).getChunkCoordinates() : blockposition;
    }

    public boolean a(BlockPosition p_a_1_, Block p_a_2_)
    {
        NextTickListEntry nextticklistentry = new NextTickListEntry(p_a_1_, p_a_2_);
        return this.V.contains(nextticklistentry);
    }

    public void a(BlockPosition p_a_1_, Block p_a_2_, int p_a_3_)
    {
        this.a(p_a_1_, p_a_2_, p_a_3_, 0);
    }

    public void a(BlockPosition p_a_1_, Block p_a_2_, int p_a_3_, int p_a_4_)
    {
        NextTickListEntry nextticklistentry = new NextTickListEntry(p_a_1_, p_a_2_);
        byte b0 = 0;

        if (this.e && p_a_2_.getMaterial() != Material.AIR)
        {
            if (p_a_2_.N())
            {
                b0 = 8;

                if (this.areChunksLoadedBetween(nextticklistentry.a.a(-b0, -b0, -b0), nextticklistentry.a.a(b0, b0, b0)))
                {
                    IBlockData iblockdata = this.getType(nextticklistentry.a);

                    if (iblockdata.getBlock().getMaterial() != Material.AIR && iblockdata.getBlock() == nextticklistentry.a())
                    {
                        iblockdata.getBlock().b((World)this, nextticklistentry.a, iblockdata, (Random)this.random);
                    }
                }

                return;
            }

            p_a_3_ = 1;
        }

        if (this.areChunksLoadedBetween(p_a_1_.a(-b0, -b0, -b0), p_a_1_.a(b0, b0, b0)))
        {
            if (p_a_2_.getMaterial() != Material.AIR)
            {
                nextticklistentry.a((long)p_a_3_ + this.worldData.getTime());
                nextticklistentry.a(p_a_4_);
            }

            if (!this.M.contains(nextticklistentry))
            {
                this.M.add(nextticklistentry);
            }
        }
    }

    public void b(BlockPosition p_b_1_, Block p_b_2_, int p_b_3_, int p_b_4_)
    {
        NextTickListEntry nextticklistentry = new NextTickListEntry(p_b_1_, p_b_2_);
        nextticklistentry.a(p_b_4_);

        if (p_b_2_.getMaterial() != Material.AIR)
        {
            nextticklistentry.a((long)p_b_3_ + this.worldData.getTime());
        }

        if (!this.M.contains(nextticklistentry))
        {
            this.M.add(nextticklistentry);
        }
    }

    public void tickEntities()
    {
        this.j();
        super.tickEntities();
        this.spigotConfig.currentPrimedTnt = 0;
    }

    public void j()
    {
        this.emptyTime = 0;
    }

    public boolean a(boolean p_a_1_)
    {
        if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES)
        {
            return false;
        }
        else
        {
            int i = this.M.size();

            if (i > 1000)
            {
                if (i > 20000)
                {
                    i /= 20;
                }
                else
                {
                    i = 1000;
                }
            }

            this.methodProfiler.a("cleaning");

            for (int j = 0; j < i; ++j)
            {
                NextTickListEntry nextticklistentry = (NextTickListEntry)this.M.first();

                if (!p_a_1_ && nextticklistentry.b > this.worldData.getTime())
                {
                    break;
                }

                this.M.remove(nextticklistentry);
                this.V.add(nextticklistentry);
            }

            this.methodProfiler.b();
            this.methodProfiler.a("ticking");
            Iterator iterator = this.V.iterator();

            while (iterator.hasNext())
            {
                NextTickListEntry nextticklistentry1 = (NextTickListEntry)iterator.next();
                iterator.remove();
                byte b0 = 0;

                if (this.areChunksLoadedBetween(nextticklistentry1.a.a(-b0, -b0, -b0), nextticklistentry1.a.a(b0, b0, b0)))
                {
                    IBlockData iblockdata = this.getType(nextticklistentry1.a);

                    if (iblockdata.getBlock().getMaterial() != Material.AIR && Block.a(iblockdata.getBlock(), nextticklistentry1.a()))
                    {
                        try
                        {
                            iblockdata.getBlock().b((World)this, nextticklistentry1.a, iblockdata, (Random)this.random);
                        }
                        catch (Throwable throwable)
                        {
                            CrashReport crashreport = CrashReport.a(throwable, "Exception while ticking a block");
                            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being ticked");
                            CrashReportSystemDetails.a(crashreportsystemdetails, nextticklistentry1.a, iblockdata);
                            throw new ReportedException(crashreport);
                        }
                    }
                }
                else
                {
                    this.a(nextticklistentry1.a, nextticklistentry1.a(), 0);
                }
            }

            this.methodProfiler.b();
            this.V.clear();
            return !this.M.isEmpty();
        }
    }

    public List<NextTickListEntry> a(Chunk p_a_1_, boolean p_a_2_)
    {
        ChunkCoordIntPair chunkcoordintpair = p_a_1_.j();
        int i = (chunkcoordintpair.x << 4) - 2;
        int j = i + 16 + 2;
        int k = (chunkcoordintpair.z << 4) - 2;
        int l = k + 16 + 2;
        return this.a(new StructureBoundingBox(i, 0, k, j, 256, l), p_a_2_);
    }

    public List<NextTickListEntry> a(StructureBoundingBox p_a_1_, boolean p_a_2_)
    {
        ArrayList arraylist = null;

        for (int i = 0; i < 2; ++i)
        {
            Iterator iterator;

            if (i == 0)
            {
                iterator = this.M.iterator();
            }
            else
            {
                iterator = this.V.iterator();
            }

            while (iterator.hasNext())
            {
                NextTickListEntry nextticklistentry = (NextTickListEntry)iterator.next();
                BlockPosition blockposition = nextticklistentry.a;

                if (blockposition.getX() >= p_a_1_.a && blockposition.getX() < p_a_1_.d && blockposition.getZ() >= p_a_1_.c && blockposition.getZ() < p_a_1_.f)
                {
                    if (p_a_2_)
                    {
                        iterator.remove();
                    }

                    if (arraylist == null)
                    {
                        arraylist = Lists.newArrayList();
                    }

                    arraylist.add(nextticklistentry);
                }
            }
        }

        return arraylist;
    }

    private boolean getSpawnNPCs()
    {
        return this.server.getSpawnNPCs();
    }

    private boolean getSpawnAnimals()
    {
        return this.server.getSpawnAnimals();
    }

    protected IChunkProvider k()
    {
        IChunkLoader ichunkloader = this.dataManager.createChunkLoader(this.worldProvider);
        InternalChunkGenerator internalchunkgenerator;

        if (this.generator != null)
        {
            internalchunkgenerator = new CustomChunkGenerator(this, this.getSeed(), this.generator);
        }
        else if (this.worldProvider instanceof WorldProviderHell)
        {
            internalchunkgenerator = new NetherChunkGenerator(this, this.getSeed());
        }
        else if (this.worldProvider instanceof WorldProviderTheEnd)
        {
            internalchunkgenerator = new SkyLandsChunkGenerator(this, this.getSeed());
        }
        else
        {
            internalchunkgenerator = new NormalChunkGenerator(this, this.getSeed());
        }

        this.chunkProviderServer = new ChunkProviderServer(this, ichunkloader, internalchunkgenerator);
        return this.chunkProviderServer;
    }

    public List<TileEntity> getTileEntities(int p_getTileEntities_1_, int p_getTileEntities_2_, int p_getTileEntities_3_, int p_getTileEntities_4_, int p_getTileEntities_5_, int p_getTileEntities_6_)
    {
        ArrayList arraylist = Lists.newArrayList();

        for (int i = p_getTileEntities_1_ >> 4; i <= p_getTileEntities_4_ - 1 >> 4; ++i)
        {
            for (int j = p_getTileEntities_3_ >> 4; j <= p_getTileEntities_6_ - 1 >> 4; ++j)
            {
                Chunk chunk = this.getChunkAt(i, j);

                if (chunk != null)
                {
                    for (Object object : chunk.tileEntities.values())
                    {
                        TileEntity tileentity = (TileEntity)object;

                        if (tileentity.position.getX() >= p_getTileEntities_1_ && tileentity.position.getY() >= p_getTileEntities_2_ && tileentity.position.getZ() >= p_getTileEntities_3_ && tileentity.position.getX() < p_getTileEntities_4_ && tileentity.position.getY() < p_getTileEntities_5_ && tileentity.position.getZ() < p_getTileEntities_6_)
                        {
                            arraylist.add(tileentity);
                        }
                    }
                }
            }
        }

        return arraylist;
    }

    public boolean a(EntityHuman p_a_1_, BlockPosition p_a_2_)
    {
        return !this.server.a(this, p_a_2_, p_a_1_) && this.getWorldBorder().a(p_a_2_);
    }

    public void a(WorldSettings p_a_1_)
    {
        if (!this.worldData.w())
        {
            try
            {
                this.b(p_a_1_);

                if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES)
                {
                    this.aj();
                }

                super.a(p_a_1_);
            }
            catch (Throwable throwable)
            {
                CrashReport crashreport = CrashReport.a(throwable, "Exception initializing level");

                try
                {
                    this.a((CrashReport)crashreport);
                }
                catch (Throwable var4)
                {
                    ;
                }

                throw new ReportedException(crashreport);
            }

            this.worldData.d(true);
        }
    }

    private void aj()
    {
        this.worldData.f(false);
        this.worldData.c(true);
        this.worldData.setStorm(false);
        this.worldData.setThundering(false);
        this.worldData.i(1000000000);
        this.worldData.setDayTime(6000L);
        this.worldData.setGameType(WorldSettings.EnumGamemode.SPECTATOR);
        this.worldData.g(false);
        this.worldData.setDifficulty(EnumDifficulty.PEACEFUL);
        this.worldData.e(true);
        this.getGameRules().set("doDaylightCycle", "false");
    }

    private void b(WorldSettings p_b_1_)
    {
        if (!this.worldProvider.e())
        {
            this.worldData.setSpawn(BlockPosition.ZERO.up(this.worldProvider.getSeaLevel()));
        }
        else if (this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES)
        {
            this.worldData.setSpawn(BlockPosition.ZERO.up());
        }
        else
        {
            this.isLoading = true;
            WorldChunkManager worldchunkmanager = this.worldProvider.m();
            List list = worldchunkmanager.a();
            Random random = new Random(this.getSeed());
            BlockPosition blockposition = worldchunkmanager.a(0, 0, 256, list, random);
            int i = 0;
            int j = this.worldProvider.getSeaLevel();
            int k = 0;

            if (this.generator != null)
            {
                Random random1 = new Random(this.getSeed());
                Location location = this.generator.getFixedSpawnLocation(this.getWorld(), random1);

                if (location != null)
                {
                    if (location.getWorld() != this.getWorld())
                    {
                        throw new IllegalStateException("Cannot set spawn point for " + this.worldData.getName() + " to be in another world (" + location.getWorld().getName() + ")");
                    }

                    this.worldData.setSpawn(new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
                    this.isLoading = false;
                    return;
                }
            }

            if (blockposition != null)
            {
                i = blockposition.getX();
                k = blockposition.getZ();
            }
            else
            {
                a.warn("Unable to find spawn biome");
            }

            int l = 0;

            while (!this.canSpawn(i, k))
            {
                i += random.nextInt(64) - random.nextInt(64);
                k += random.nextInt(64) - random.nextInt(64);
                ++l;

                if (l == 1000)
                {
                    break;
                }
            }

            this.worldData.setSpawn(new BlockPosition(i, j, k));
            this.isLoading = false;

            if (p_b_1_.c())
            {
                this.l();
            }
        }
    }

    protected void l()
    {
        WorldGenBonusChest worldgenbonuschest = new WorldGenBonusChest(U, 10);

        for (int i = 0; i < 10; ++i)
        {
            int j = this.worldData.c() + this.random.nextInt(6) - this.random.nextInt(6);
            int k = this.worldData.e() + this.random.nextInt(6) - this.random.nextInt(6);
            BlockPosition blockposition = this.r(new BlockPosition(j, 0, k)).up();

            if (worldgenbonuschest.generate(this, this.random, blockposition))
            {
                break;
            }
        }
    }

    public BlockPosition getDimensionSpawn()
    {
        return this.worldProvider.h();
    }

    public void save(boolean p_save_1_, IProgressUpdate p_save_2_) throws ExceptionWorldConflict
    {
        if (this.chunkProvider.canSave())
        {
            Bukkit.getPluginManager().callEvent(new WorldSaveEvent(this.getWorld()));

            if (p_save_2_ != null)
            {
                p_save_2_.a("Saving level");
            }

            this.a();

            if (p_save_2_ != null)
            {
                p_save_2_.c("Saving chunks");
            }

            this.chunkProvider.saveChunks(p_save_1_, p_save_2_);

            for (Chunk chunk : this.chunkProviderServer.a())
            {
                if (chunk != null && !this.manager.a(chunk.locX, chunk.locZ))
                {
                    this.chunkProviderServer.queueUnload(chunk.locX, chunk.locZ);
                }
            }
        }
    }

    public void flushSave()
    {
        if (this.chunkProvider.canSave())
        {
            this.chunkProvider.c();
        }
    }

    protected void a() throws ExceptionWorldConflict
    {
        this.checkSession();
        this.worldData.a(this.getWorldBorder().getSize());
        this.worldData.d(this.getWorldBorder().getCenterX());
        this.worldData.c(this.getWorldBorder().getCenterZ());
        this.worldData.e(this.getWorldBorder().getDamageBuffer());
        this.worldData.f(this.getWorldBorder().getDamageAmount());
        this.worldData.j(this.getWorldBorder().getWarningDistance());
        this.worldData.k(this.getWorldBorder().getWarningTime());
        this.worldData.b(this.getWorldBorder().j());
        this.worldData.e(this.getWorldBorder().i());

        if (!(this instanceof SecondaryWorldServer))
        {
            this.worldMaps.a();
        }

        this.dataManager.saveWorldData(this.worldData, this.server.getPlayerList().t());
    }

    protected void a(Entity p_a_1_)
    {
        super.a(p_a_1_);
        this.entitiesById.a(p_a_1_.getId(), p_a_1_);
        this.entitiesByUUID.put(p_a_1_.getUniqueID(), p_a_1_);
        Entity[] aentity = p_a_1_.aB();

        if (aentity != null)
        {
            for (int i = 0; i < aentity.length; ++i)
            {
                this.entitiesById.a(aentity[i].getId(), aentity[i]);
            }
        }
    }

    protected void b(Entity p_b_1_)
    {
        super.b(p_b_1_);
        this.entitiesById.d(p_b_1_.getId());
        this.entitiesByUUID.remove(p_b_1_.getUniqueID());
        Entity[] aentity = p_b_1_.aB();

        if (aentity != null)
        {
            for (int i = 0; i < aentity.length; ++i)
            {
                this.entitiesById.d(aentity[i].getId());
            }
        }
    }

    public boolean strikeLightning(Entity p_strikeLightning_1_)
    {
        LightningStrikeEvent lightningstrikeevent = new LightningStrikeEvent(this.getWorld(), (LightningStrike)p_strikeLightning_1_.getBukkitEntity());
        this.getServer().getPluginManager().callEvent(lightningstrikeevent);

        if (lightningstrikeevent.isCancelled())
        {
            return false;
        }
        else if (super.strikeLightning(p_strikeLightning_1_))
        {
            this.server.getPlayerList().sendPacketNearby(p_strikeLightning_1_.locX, p_strikeLightning_1_.locY, p_strikeLightning_1_.locZ, 512.0D, this.dimension, new PacketPlayOutSpawnEntityWeather(p_strikeLightning_1_));
            return true;
        }
        else
        {
            return false;
        }
    }

    public void broadcastEntityEffect(Entity p_broadcastEntityEffect_1_, byte p_broadcastEntityEffect_2_)
    {
        this.getTracker().sendPacketToEntity(p_broadcastEntityEffect_1_, new PacketPlayOutEntityStatus(p_broadcastEntityEffect_1_, p_broadcastEntityEffect_2_));
    }

    public Explosion createExplosion(Entity p_createExplosion_1_, double p_createExplosion_2_, double p_createExplosion_4_, double p_createExplosion_6_, float p_createExplosion_8_, boolean p_createExplosion_9_, boolean p_createExplosion_10_)
    {
        Explosion explosion = super.createExplosion(p_createExplosion_1_, p_createExplosion_2_, p_createExplosion_4_, p_createExplosion_6_, p_createExplosion_8_, p_createExplosion_9_, p_createExplosion_10_);

        if (explosion.wasCanceled)
        {
            return explosion;
        }
        else
        {
            if (!p_createExplosion_10_)
            {
                explosion.clearBlocks();
            }

            for (EntityHuman entityhuman : this.players)
            {
                if (entityhuman.e(p_createExplosion_2_, p_createExplosion_4_, p_createExplosion_6_) < 4096.0D)
                {
                    ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutExplosion(p_createExplosion_2_, p_createExplosion_4_, p_createExplosion_6_, p_createExplosion_8_, explosion.getBlocks(), (Vec3D)explosion.b().get(entityhuman)));
                }
            }

            return explosion;
        }
    }

    public void playBlockAction(BlockPosition p_playBlockAction_1_, Block p_playBlockAction_2_, int p_playBlockAction_3_, int p_playBlockAction_4_)
    {
        BlockActionData blockactiondata = new BlockActionData(p_playBlockAction_1_, p_playBlockAction_2_, p_playBlockAction_3_, p_playBlockAction_4_);

        for (BlockActionData blockactiondata1 : this.S[this.T])
        {
            if (blockactiondata1.equals(blockactiondata))
            {
                return;
            }
        }

        this.S[this.T].add(blockactiondata);
    }

    private void ak()
    {
        while (!this.S[this.T].isEmpty())
        {
            int i = this.T;
            this.T ^= 1;

            for (BlockActionData blockactiondata : this.S[i])
            {
                if (this.a(blockactiondata))
                {
                    this.server.getPlayerList().sendPacketNearby((double)blockactiondata.a().getX(), (double)blockactiondata.a().getY(), (double)blockactiondata.a().getZ(), 64.0D, this.dimension, new PacketPlayOutBlockAction(blockactiondata.a(), blockactiondata.d(), blockactiondata.b(), blockactiondata.c()));
                }
            }

            this.S[i].clear();
        }
    }

    private boolean a(BlockActionData p_a_1_)
    {
        IBlockData iblockdata = this.getType(p_a_1_.a());
        return iblockdata.getBlock() == p_a_1_.d() ? iblockdata.getBlock().a(this, p_a_1_.a(), iblockdata, p_a_1_.b(), p_a_1_.c()) : false;
    }

    public void saveLevel()
    {
        this.dataManager.a();
    }

    protected void p()
    {
        boolean flag = this.S();
        super.p();

        if (flag != this.S())
        {
            for (int i = 0; i < this.players.size(); ++i)
            {
                if (((EntityPlayer)this.players.get(i)).world == this)
                {
                    ((EntityPlayer)this.players.get(i)).setPlayerWeather(!flag ? WeatherType.DOWNFALL : WeatherType.CLEAR, false);
                }
            }
        }

        for (int j = 0; j < this.players.size(); ++j)
        {
            if (((EntityPlayer)this.players.get(j)).world == this)
            {
                ((EntityPlayer)this.players.get(j)).updateWeather(this.o, this.p, this.q, this.r);
            }
        }
    }

    protected int q()
    {
        return this.server.getPlayerList().s();
    }

    public MinecraftServer getMinecraftServer()
    {
        return this.server;
    }

    public EntityTracker getTracker()
    {
        return this.tracker;
    }

    public PlayerChunkMap getPlayerChunkMap()
    {
        return this.manager;
    }

    public PortalTravelAgent getTravelAgent()
    {
        return this.Q;
    }

    public void a(EnumParticle p_a_1_, double p_a_2_, double p_a_4_, double p_a_6_, int p_a_8_, double p_a_9_, double p_a_11_, double p_a_13_, double p_a_15_, int... p_a_17_)
    {
        this.a(p_a_1_, false, p_a_2_, p_a_4_, p_a_6_, p_a_8_, p_a_9_, p_a_11_, p_a_13_, p_a_15_, p_a_17_);
    }

    public void a(EnumParticle p_a_1_, boolean p_a_2_, double p_a_3_, double p_a_5_, double p_a_7_, int p_a_9_, double p_a_10_, double p_a_12_, double p_a_14_, double p_a_16_, int... p_a_18_)
    {
        this.sendParticles((EntityPlayer)null, p_a_1_, p_a_2_, p_a_3_, p_a_5_, p_a_7_, p_a_9_, p_a_10_, p_a_12_, p_a_14_, p_a_16_, p_a_18_);
    }

    public void sendParticles(EntityPlayer p_sendParticles_1_, EnumParticle p_sendParticles_2_, boolean p_sendParticles_3_, double p_sendParticles_4_, double p_sendParticles_6_, double p_sendParticles_8_, int p_sendParticles_10_, double p_sendParticles_11_, double p_sendParticles_13_, double p_sendParticles_15_, double p_sendParticles_17_, int... p_sendParticles_19_)
    {
        PacketPlayOutWorldParticles packetplayoutworldparticles = new PacketPlayOutWorldParticles(p_sendParticles_2_, p_sendParticles_3_, (float)p_sendParticles_4_, (float)p_sendParticles_6_, (float)p_sendParticles_8_, (float)p_sendParticles_11_, (float)p_sendParticles_13_, (float)p_sendParticles_15_, (float)p_sendParticles_17_, p_sendParticles_10_, p_sendParticles_19_);

        for (int i = 0; i < this.players.size(); ++i)
        {
            EntityPlayer entityplayer = (EntityPlayer)this.players.get(i);

            if (p_sendParticles_1_ == null || entityplayer.getBukkitEntity().canSee(p_sendParticles_1_.getBukkitEntity()))
            {
                BlockPosition blockposition = entityplayer.getChunkCoordinates();
                double d0 = blockposition.c(p_sendParticles_4_, p_sendParticles_6_, p_sendParticles_8_);

                if (d0 <= 256.0D || p_sendParticles_3_ && d0 <= 65536.0D)
                {
                    entityplayer.playerConnection.sendPacket(packetplayoutworldparticles);
                }
            }
        }
    }

    public Entity getEntity(UUID p_getEntity_1_)
    {
        return (Entity)this.entitiesByUUID.get(p_getEntity_1_);
    }

    public ListenableFuture<Object> postToMainThread(Runnable p_postToMainThread_1_)
    {
        return this.server.postToMainThread(p_postToMainThread_1_);
    }

    public boolean isMainThread()
    {
        return this.server.isMainThread();
    }

    static class BlockActionDataList extends ArrayList<BlockActionData>
    {
        private BlockActionDataList()
        {
        }

        BlockActionDataList(Object p_i152_1_)
        {
            this();
        }
    }
}
