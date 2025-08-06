package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gnu.trove.map.hash.TLongShortHashMap;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Callable;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockFluids;
import net.minecraft.server.v1_8_R3.BlockHopper;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockSnow;
import net.minecraft.server.v1_8_R3.BlockStairs;
import net.minecraft.server.v1_8_R3.BlockStepAbstract;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.ChunkProviderServer;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityAnimal;
import net.minecraft.server.v1_8_R3.EntityExperienceOrb;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityGolem;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.EntityWaterAnimal;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.EnumSkyBlock;
import net.minecraft.server.v1_8_R3.ExceptionWorldConflict;
import net.minecraft.server.v1_8_R3.Explosion;
import net.minecraft.server.v1_8_R3.GameRules;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IChunkProvider;
import net.minecraft.server.v1_8_R3.IDataManager;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.IWorldAccess;
import net.minecraft.server.v1_8_R3.IWorldBorderListener;
import net.minecraft.server.v1_8_R3.IntHashMap;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MethodProfiler;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.NextTickListEntry;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_8_R3.PersistentBase;
import net.minecraft.server.v1_8_R3.PersistentCollection;
import net.minecraft.server.v1_8_R3.PersistentVillage;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.StructureBoundingBox;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.WorldBorder;
import net.minecraft.server.v1_8_R3.WorldChunkManager;
import net.minecraft.server.v1_8_R3.WorldData;
import net.minecraft.server.v1_8_R3.WorldMap;
import net.minecraft.server.v1_8_R3.WorldProvider;
import net.minecraft.server.v1_8_R3.WorldServer;
import net.minecraft.server.v1_8_R3.WorldSettings;
import net.minecraft.server.v1_8_R3.WorldType;
import org.bukkit.Bukkit;
import org.bukkit.World.Environment;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings;
import org.bukkit.craftbukkit.v1_8_R3.SpigotTimings.WorldTimingsHandler;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.generator.ChunkGenerator;
import org.spigotmc.ActivationRange;
import org.spigotmc.AsyncCatcher;
import org.spigotmc.SpigotWorldConfig;
import org.spigotmc.TickLimiter;

public abstract class World implements IBlockAccess {
   private int a = 63;
   protected boolean e;
   public final List<Entity> entityList = new ArrayList<Entity>() {
      public Entity remove(int p_remove_1_) {
         this.guard();
         return (Entity)super.remove(p_remove_1_);
      }

      public boolean remove(Object p_remove_1_) {
         this.guard();
         return super.remove(p_remove_1_);
      }

      private void guard() {
         if(World.this.guardEntityList) {
            throw new ConcurrentModificationException();
         }
      }
   };
   protected final List<Entity> g = Lists.<Entity>newArrayList();
   public final List<TileEntity> h = Lists.<TileEntity>newArrayList();
   public final List<TileEntity> tileEntityList = Lists.<TileEntity>newArrayList();
   private final List<TileEntity> b = Lists.<TileEntity>newArrayList();
   private final List<TileEntity> c = Lists.<TileEntity>newArrayList();
   public final List<EntityHuman> players = Lists.<EntityHuman>newArrayList();
   public final List<Entity> k = Lists.<Entity>newArrayList();
   protected final IntHashMap<Entity> entitiesById = new IntHashMap();
   private long d = 16777215L;
   private int I;
   protected int m = (new Random()).nextInt();
   protected final int n = 1013904223;
   protected float o;
   protected float p;
   protected float q;
   protected float r;
   private int J;
   public final Random random = new Random();
   public WorldProvider worldProvider;
   protected List<IWorldAccess> u = Lists.<IWorldAccess>newArrayList();
   protected IChunkProvider chunkProvider;
   protected final IDataManager dataManager;
   public WorldData worldData;
   protected boolean isLoading;
   public PersistentCollection worldMaps;
   protected PersistentVillage villages;
   public final MethodProfiler methodProfiler;
   private final Calendar K = Calendar.getInstance();
   public Scoreboard scoreboard = new Scoreboard();
   public final boolean isClientSide;
   private int L;
   public boolean allowMonsters;
   public boolean allowAnimals;
   private boolean M;
   private final WorldBorder N;
   int[] H;
   private final CraftWorld world;
   public boolean pvpMode;
   public boolean keepSpawnInMemory = true;
   public ChunkGenerator generator;
   public boolean captureBlockStates = false;
   public boolean captureTreeGeneration = false;
   public ArrayList<org.bukkit.block.BlockState> capturedBlockStates = new ArrayList<org.bukkit.block.BlockState>() {
      public boolean add(org.bukkit.block.BlockState p_add_1_) {
         for(org.bukkit.block.BlockState blockstate : this) {
            if(blockstate.getLocation().equals(p_add_1_.getLocation())) {
               return false;
            }
         }

         return super.add(p_add_1_);
      }
   };
   public long ticksPerAnimalSpawns;
   public long ticksPerMonsterSpawns;
   public boolean populating;
   private int tickPosition;
   private boolean guardEntityList;
   protected final TLongShortHashMap chunkTickList;
   protected float growthOdds = 100.0F;
   protected float modifiedOdds = 100.0F;
   private final byte chunkTickRadius;
   public static boolean haveWeSilencedAPhysicsCrash;
   public static String blockLocation;
   private TickLimiter entityLimiter;
   private TickLimiter tileLimiter;
   private int tileTickPosition;
   public final SpigotWorldConfig spigotConfig;
   public final WorldTimingsHandler timings;
   public Map<BlockPosition, TileEntity> capturedTileEntities = Maps.<BlockPosition, TileEntity>newHashMap();

   public static long chunkToKey(int p_chunkToKey_0_, int p_chunkToKey_1_) {
      long i = ((long)p_chunkToKey_0_ & 4294901760L) << 16 | ((long)p_chunkToKey_0_ & 65535L) << 0;
      i = i | ((long)p_chunkToKey_1_ & 4294901760L) << 32 | ((long)p_chunkToKey_1_ & 65535L) << 16;
      return i;
   }

   public static int keyToX(long p_keyToX_0_) {
      return (int)(p_keyToX_0_ >> 16 & -65536L | p_keyToX_0_ & 65535L);
   }

   public static int keyToZ(long p_keyToZ_0_) {
      return (int)(p_keyToZ_0_ >> 32 & 4294901760L | p_keyToZ_0_ >> 16 & 65535L);
   }

   public CraftWorld getWorld() {
      return this.world;
   }

   public CraftServer getServer() {
      return (CraftServer)Bukkit.getServer();
   }

   public Chunk getChunkIfLoaded(int p_getChunkIfLoaded_1_, int p_getChunkIfLoaded_2_) {
      return ((ChunkProviderServer)this.chunkProvider).getChunkIfLoaded(p_getChunkIfLoaded_1_, p_getChunkIfLoaded_2_);
   }

   protected World(IDataManager p_i119_1_, WorldData p_i119_2_, WorldProvider p_i119_3_, MethodProfiler p_i119_4_, boolean p_i119_5_, ChunkGenerator p_i119_6_, Environment p_i119_7_) {
      this.spigotConfig = new SpigotWorldConfig(p_i119_2_.getName());
      this.generator = p_i119_6_;
      this.world = new CraftWorld((WorldServer)this, p_i119_6_, p_i119_7_);
      this.ticksPerAnimalSpawns = (long)this.getServer().getTicksPerAnimalSpawns();
      this.ticksPerMonsterSpawns = (long)this.getServer().getTicksPerMonsterSpawns();
      this.chunkTickRadius = (byte)(this.getServer().getViewDistance() < 7?this.getServer().getViewDistance():7);
      this.chunkTickList = new TLongShortHashMap(this.spigotConfig.chunksPerTick * 5, 0.7F, Long.MIN_VALUE, (short)-32768);
      this.chunkTickList.setAutoCompactionFactor(0.0F);
      this.L = this.random.nextInt(12000);
      this.allowMonsters = true;
      this.allowAnimals = true;
      this.H = new int['\u8000'];
      this.dataManager = p_i119_1_;
      this.methodProfiler = p_i119_4_;
      this.worldData = p_i119_2_;
      this.worldProvider = p_i119_3_;
      this.isClientSide = p_i119_5_;
      this.N = p_i119_3_.getWorldBorder();
      this.N.a(new IWorldBorderListener() {
         public void a(WorldBorder p_a_1_, double p_a_2_) {
            World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(p_a_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_SIZE), World.this);
         }

         public void a(WorldBorder p_a_1_, double p_a_2_, double p_a_4_, long p_a_6_) {
            World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(p_a_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.LERP_SIZE), World.this);
         }

         public void a(WorldBorder p_a_1_, double p_a_2_, double p_a_4_) {
            World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(p_a_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_CENTER), World.this);
         }

         public void a(WorldBorder p_a_1_, int p_a_2_) {
            World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(p_a_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_TIME), World.this);
         }

         public void b(WorldBorder p_b_1_, int p_b_2_) {
            World.this.getServer().getHandle().sendAll(new PacketPlayOutWorldBorder(p_b_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction.SET_WARNING_BLOCKS), World.this);
         }

         public void b(WorldBorder p_b_1_, double p_b_2_) {
         }

         public void c(WorldBorder p_c_1_, double p_c_2_) {
         }
      });
      this.getServer().addWorld(this.world);
      this.timings = new WorldTimingsHandler(this);
      this.entityLimiter = new TickLimiter(this.spigotConfig.entityMaxTickTime);
      this.tileLimiter = new TickLimiter(this.spigotConfig.tileMaxTickTime);
   }

   public World b() {
      return this;
   }

   public BiomeBase getBiome(final BlockPosition p_getBiome_1_) {
      if(this.isLoaded(p_getBiome_1_)) {
         Chunk chunk = this.getChunkAtWorldCoords(p_getBiome_1_);

         try {
            return chunk.getBiome(p_getBiome_1_, this.worldProvider.m());
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Getting biome");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Coordinates of biome request");
            crashreportsystemdetails.a("Location", new Callable() {
               public String a() throws Exception {
                  return CrashReportSystemDetails.a(p_getBiome_1_);
               }

               public Object call() throws Exception {
                  return this.a();
               }
            });
            throw new ReportedException(crashreport);
         }
      } else {
         return this.worldProvider.m().getBiome(p_getBiome_1_, BiomeBase.PLAINS);
      }
   }

   public WorldChunkManager getWorldChunkManager() {
      return this.worldProvider.m();
   }

   protected abstract IChunkProvider k();

   public void a(WorldSettings p_a_1_) {
      this.worldData.d(true);
   }

   public Block c(BlockPosition p_c_1_) {
      BlockPosition blockposition;
      for(blockposition = new BlockPosition(p_c_1_.getX(), this.F(), p_c_1_.getZ()); !this.isEmpty(blockposition.up()); blockposition = blockposition.up()) {
         ;
      }

      return this.getType(blockposition).getBlock();
   }

   private boolean isValidLocation(BlockPosition p_isValidLocation_1_) {
      return p_isValidLocation_1_.getX() >= -30000000 && p_isValidLocation_1_.getZ() >= -30000000 && p_isValidLocation_1_.getX() < 30000000 && p_isValidLocation_1_.getZ() < 30000000 && p_isValidLocation_1_.getY() >= 0 && p_isValidLocation_1_.getY() < 256;
   }

   public boolean isEmpty(BlockPosition p_isEmpty_1_) {
      return this.getType(p_isEmpty_1_).getBlock().getMaterial() == Material.AIR;
   }

   public boolean isLoaded(BlockPosition p_isLoaded_1_) {
      return this.a(p_isLoaded_1_, true);
   }

   public boolean a(BlockPosition p_a_1_, boolean p_a_2_) {
      return !this.isValidLocation(p_a_1_)?false:this.isChunkLoaded(p_a_1_.getX() >> 4, p_a_1_.getZ() >> 4, p_a_2_);
   }

   public boolean areChunksLoaded(BlockPosition p_areChunksLoaded_1_, int p_areChunksLoaded_2_) {
      return this.areChunksLoaded(p_areChunksLoaded_1_, p_areChunksLoaded_2_, true);
   }

   public boolean areChunksLoaded(BlockPosition p_areChunksLoaded_1_, int p_areChunksLoaded_2_, boolean p_areChunksLoaded_3_) {
      return this.isAreaLoaded(p_areChunksLoaded_1_.getX() - p_areChunksLoaded_2_, p_areChunksLoaded_1_.getY() - p_areChunksLoaded_2_, p_areChunksLoaded_1_.getZ() - p_areChunksLoaded_2_, p_areChunksLoaded_1_.getX() + p_areChunksLoaded_2_, p_areChunksLoaded_1_.getY() + p_areChunksLoaded_2_, p_areChunksLoaded_1_.getZ() + p_areChunksLoaded_2_, p_areChunksLoaded_3_);
   }

   public boolean areChunksLoadedBetween(BlockPosition p_areChunksLoadedBetween_1_, BlockPosition p_areChunksLoadedBetween_2_) {
      return this.areChunksLoadedBetween(p_areChunksLoadedBetween_1_, p_areChunksLoadedBetween_2_, true);
   }

   public boolean areChunksLoadedBetween(BlockPosition p_areChunksLoadedBetween_1_, BlockPosition p_areChunksLoadedBetween_2_, boolean p_areChunksLoadedBetween_3_) {
      return this.isAreaLoaded(p_areChunksLoadedBetween_1_.getX(), p_areChunksLoadedBetween_1_.getY(), p_areChunksLoadedBetween_1_.getZ(), p_areChunksLoadedBetween_2_.getX(), p_areChunksLoadedBetween_2_.getY(), p_areChunksLoadedBetween_2_.getZ(), p_areChunksLoadedBetween_3_);
   }

   public boolean a(StructureBoundingBox p_a_1_) {
      return this.b(p_a_1_, true);
   }

   public boolean b(StructureBoundingBox p_b_1_, boolean p_b_2_) {
      return this.isAreaLoaded(p_b_1_.a, p_b_1_.b, p_b_1_.c, p_b_1_.d, p_b_1_.e, p_b_1_.f, p_b_2_);
   }

   private boolean isAreaLoaded(int p_isAreaLoaded_1_, int p_isAreaLoaded_2_, int p_isAreaLoaded_3_, int p_isAreaLoaded_4_, int p_isAreaLoaded_5_, int p_isAreaLoaded_6_, boolean p_isAreaLoaded_7_) {
      if(p_isAreaLoaded_5_ >= 0 && p_isAreaLoaded_2_ < 256) {
         p_isAreaLoaded_1_ = p_isAreaLoaded_1_ >> 4;
         p_isAreaLoaded_3_ = p_isAreaLoaded_3_ >> 4;
         p_isAreaLoaded_4_ = p_isAreaLoaded_4_ >> 4;
         p_isAreaLoaded_6_ = p_isAreaLoaded_6_ >> 4;

         for(int i = p_isAreaLoaded_1_; i <= p_isAreaLoaded_4_; ++i) {
            for(int j = p_isAreaLoaded_3_; j <= p_isAreaLoaded_6_; ++j) {
               if(!this.isChunkLoaded(i, j, p_isAreaLoaded_7_)) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean isChunkLoaded(int p_isChunkLoaded_1_, int p_isChunkLoaded_2_, boolean p_isChunkLoaded_3_) {
      return this.chunkProvider.isChunkLoaded(p_isChunkLoaded_1_, p_isChunkLoaded_2_) && (p_isChunkLoaded_3_ || !this.chunkProvider.getOrCreateChunk(p_isChunkLoaded_1_, p_isChunkLoaded_2_).isEmpty());
   }

   public Chunk getChunkAtWorldCoords(BlockPosition p_getChunkAtWorldCoords_1_) {
      return this.getChunkAt(p_getChunkAtWorldCoords_1_.getX() >> 4, p_getChunkAtWorldCoords_1_.getZ() >> 4);
   }

   public Chunk getChunkAt(int p_getChunkAt_1_, int p_getChunkAt_2_) {
      return this.chunkProvider.getOrCreateChunk(p_getChunkAt_1_, p_getChunkAt_2_);
   }

   public boolean setTypeAndData(BlockPosition p_setTypeAndData_1_, IBlockData p_setTypeAndData_2_, int p_setTypeAndData_3_) {
      if(this.captureTreeGeneration) {
         org.bukkit.block.BlockState blockstate1 = null;
         Iterator<org.bukkit.block.BlockState> iterator = this.capturedBlockStates.iterator();

         while(iterator.hasNext()) {
            org.bukkit.block.BlockState blockstate2 = (org.bukkit.block.BlockState)iterator.next();
            if(blockstate2.getX() == p_setTypeAndData_1_.getX() && blockstate2.getY() == p_setTypeAndData_1_.getY() && blockstate2.getZ() == p_setTypeAndData_1_.getZ()) {
               blockstate1 = blockstate2;
               iterator.remove();
               break;
            }
         }

         if(blockstate1 == null) {
            blockstate1 = CraftBlockState.getBlockState(this, p_setTypeAndData_1_.getX(), p_setTypeAndData_1_.getY(), p_setTypeAndData_1_.getZ(), p_setTypeAndData_3_);
         }

         blockstate1.setTypeId(CraftMagicNumbers.getId(p_setTypeAndData_2_.getBlock()));
         blockstate1.setRawData((byte)p_setTypeAndData_2_.getBlock().toLegacyData(p_setTypeAndData_2_));
         this.capturedBlockStates.add(blockstate1);
         return true;
      } else if(!this.isValidLocation(p_setTypeAndData_1_)) {
         return false;
      } else if(!this.isClientSide && this.worldData.getType() == WorldType.DEBUG_ALL_BLOCK_STATES) {
         return false;
      } else {
         Chunk chunk = this.getChunkAtWorldCoords(p_setTypeAndData_1_);
         Block block = p_setTypeAndData_2_.getBlock();
         org.bukkit.block.BlockState blockstate = null;
         if(this.captureBlockStates) {
            blockstate = CraftBlockState.getBlockState(this, p_setTypeAndData_1_.getX(), p_setTypeAndData_1_.getY(), p_setTypeAndData_1_.getZ(), p_setTypeAndData_3_);
            this.capturedBlockStates.add(blockstate);
         }

         IBlockData iblockdata = chunk.a(p_setTypeAndData_1_, p_setTypeAndData_2_);
         if(iblockdata == null) {
            if(this.captureBlockStates) {
               this.capturedBlockStates.remove(blockstate);
            }

            return false;
         } else {
            Block block1 = iblockdata.getBlock();
            if(block.p() != block1.p() || block.r() != block1.r()) {
               this.methodProfiler.a("checkLight");
               this.x(p_setTypeAndData_1_);
               this.methodProfiler.b();
            }

            if(!this.captureBlockStates) {
               this.notifyAndUpdatePhysics(p_setTypeAndData_1_, chunk, block1, block, p_setTypeAndData_3_);
            }

            return true;
         }
      }
   }

   public void notifyAndUpdatePhysics(BlockPosition p_notifyAndUpdatePhysics_1_, Chunk p_notifyAndUpdatePhysics_2_, Block p_notifyAndUpdatePhysics_3_, Block p_notifyAndUpdatePhysics_4_, int p_notifyAndUpdatePhysics_5_) {
      if((p_notifyAndUpdatePhysics_5_ & 2) != 0 && (p_notifyAndUpdatePhysics_2_ == null || p_notifyAndUpdatePhysics_2_.isReady())) {
         this.notify(p_notifyAndUpdatePhysics_1_);
      }

      if(!this.isClientSide && (p_notifyAndUpdatePhysics_5_ & 1) != 0) {
         this.update(p_notifyAndUpdatePhysics_1_, p_notifyAndUpdatePhysics_3_);
         if(p_notifyAndUpdatePhysics_4_.isComplexRedstone()) {
            this.updateAdjacentComparators(p_notifyAndUpdatePhysics_1_, p_notifyAndUpdatePhysics_4_);
         }
      }

   }

   public boolean setAir(BlockPosition p_setAir_1_) {
      return this.setTypeAndData(p_setAir_1_, Blocks.AIR.getBlockData(), 3);
   }

   public boolean setAir(BlockPosition p_setAir_1_, boolean p_setAir_2_) {
      IBlockData iblockdata = this.getType(p_setAir_1_);
      Block block = iblockdata.getBlock();
      if(block.getMaterial() == Material.AIR) {
         return false;
      } else {
         this.triggerEffect(2001, p_setAir_1_, Block.getCombinedId(iblockdata));
         if(p_setAir_2_) {
            block.b(this, p_setAir_1_, iblockdata, 0);
         }

         return this.setTypeAndData(p_setAir_1_, Blocks.AIR.getBlockData(), 3);
      }
   }

   public boolean setTypeUpdate(BlockPosition p_setTypeUpdate_1_, IBlockData p_setTypeUpdate_2_) {
      return this.setTypeAndData(p_setTypeUpdate_1_, p_setTypeUpdate_2_, 3);
   }

   public void notify(BlockPosition p_notify_1_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).a(p_notify_1_);
      }

   }

   public void update(BlockPosition p_update_1_, Block p_update_2_) {
      if(this.worldData.getType() != WorldType.DEBUG_ALL_BLOCK_STATES) {
         if(this.populating) {
            return;
         }

         this.applyPhysics(p_update_1_, p_update_2_);
      }

   }

   public void a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      if(p_a_3_ > p_a_4_) {
         int i = p_a_4_;
         p_a_4_ = p_a_3_;
         p_a_3_ = i;
      }

      if(!this.worldProvider.o()) {
         for(int j = p_a_3_; j <= p_a_4_; ++j) {
            this.c(EnumSkyBlock.SKY, new BlockPosition(p_a_1_, j, p_a_2_));
         }
      }

      this.b(p_a_1_, p_a_3_, p_a_2_, p_a_1_, p_a_4_, p_a_2_);
   }

   public void b(BlockPosition p_b_1_, BlockPosition p_b_2_) {
      this.b(p_b_1_.getX(), p_b_1_.getY(), p_b_1_.getZ(), p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ());
   }

   public void b(int p_b_1_, int p_b_2_, int p_b_3_, int p_b_4_, int p_b_5_, int p_b_6_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).a(p_b_1_, p_b_2_, p_b_3_, p_b_4_, p_b_5_, p_b_6_);
      }

   }

   public void applyPhysics(BlockPosition p_applyPhysics_1_, Block p_applyPhysics_2_) {
      this.d(p_applyPhysics_1_.west(), p_applyPhysics_2_);
      this.d(p_applyPhysics_1_.east(), p_applyPhysics_2_);
      this.d(p_applyPhysics_1_.down(), p_applyPhysics_2_);
      this.d(p_applyPhysics_1_.up(), p_applyPhysics_2_);
      this.d(p_applyPhysics_1_.north(), p_applyPhysics_2_);
      this.d(p_applyPhysics_1_.south(), p_applyPhysics_2_);
      this.spigotConfig.antiXrayInstance.updateNearbyBlocks(this, p_applyPhysics_1_);
   }

   public void a(BlockPosition p_a_1_, Block p_a_2_, EnumDirection p_a_3_) {
      if(p_a_3_ != EnumDirection.WEST) {
         this.d(p_a_1_.west(), p_a_2_);
      }

      if(p_a_3_ != EnumDirection.EAST) {
         this.d(p_a_1_.east(), p_a_2_);
      }

      if(p_a_3_ != EnumDirection.DOWN) {
         this.d(p_a_1_.down(), p_a_2_);
      }

      if(p_a_3_ != EnumDirection.UP) {
         this.d(p_a_1_.up(), p_a_2_);
      }

      if(p_a_3_ != EnumDirection.NORTH) {
         this.d(p_a_1_.north(), p_a_2_);
      }

      if(p_a_3_ != EnumDirection.SOUTH) {
         this.d(p_a_1_.south(), p_a_2_);
      }

   }

   public void d(BlockPosition p_d_1_, final Block p_d_2_) {
      if(!this.isClientSide) {
         IBlockData iblockdata = this.getType(p_d_1_);

         try {
            CraftWorld craftworld = ((WorldServer)this).getWorld();
            if(craftworld != null) {
               BlockPhysicsEvent blockphysicsevent = new BlockPhysicsEvent(craftworld.getBlockAt(p_d_1_.getX(), p_d_1_.getY(), p_d_1_.getZ()), CraftMagicNumbers.getId(p_d_2_));
               this.getServer().getPluginManager().callEvent(blockphysicsevent);
               if(blockphysicsevent.isCancelled()) {
                  return;
               }
            }

            iblockdata.getBlock().doPhysics(this, p_d_1_, iblockdata, p_d_2_);
         } catch (StackOverflowError var7) {
            haveWeSilencedAPhysicsCrash = true;
            blockLocation = p_d_1_.getX() + ", " + p_d_1_.getY() + ", " + p_d_1_.getZ();
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Exception while updating neighbours");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being updated");
            crashreportsystemdetails.a("Source block type", new Callable() {
               public String a() throws Exception {
                  try {
                     return String.format("ID #%d (%s // %s)", new Object[]{Integer.valueOf(Block.getId(p_d_2_)), p_d_2_.a(), p_d_2_.getClass().getCanonicalName()});
                  } catch (Throwable var1) {
                     return "ID #" + Block.getId(p_d_2_);
                  }
               }

               public Object call() throws Exception {
                  return this.a();
               }
            });
            CrashReportSystemDetails.a(crashreportsystemdetails, p_d_1_, iblockdata);
            throw new ReportedException(crashreport);
         }
      }

   }

   public boolean a(BlockPosition p_a_1_, Block p_a_2_) {
      return false;
   }

   public boolean i(BlockPosition p_i_1_) {
      return this.getChunkAtWorldCoords(p_i_1_).d(p_i_1_);
   }

   public boolean j(BlockPosition p_j_1_) {
      if(p_j_1_.getY() >= this.F()) {
         return this.i(p_j_1_);
      } else {
         BlockPosition blockposition = new BlockPosition(p_j_1_.getX(), this.F(), p_j_1_.getZ());
         if(!this.i(blockposition)) {
            return false;
         } else {
            for(blockposition = blockposition.down(); blockposition.getY() > p_j_1_.getY(); blockposition = blockposition.down()) {
               Block block = this.getType(blockposition).getBlock();
               if(block.p() > 0 && !block.getMaterial().isLiquid()) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int k(BlockPosition p_k_1_) {
      if(p_k_1_.getY() < 0) {
         return 0;
      } else {
         if(p_k_1_.getY() >= 256) {
            p_k_1_ = new BlockPosition(p_k_1_.getX(), 255, p_k_1_.getZ());
         }

         return this.getChunkAtWorldCoords(p_k_1_).a((BlockPosition)p_k_1_, 0);
      }
   }

   public int getLightLevel(BlockPosition p_getLightLevel_1_) {
      return this.c(p_getLightLevel_1_, true);
   }

   public int c(BlockPosition p_c_1_, boolean p_c_2_) {
      if(p_c_1_.getX() >= -30000000 && p_c_1_.getZ() >= -30000000 && p_c_1_.getX() < 30000000 && p_c_1_.getZ() < 30000000) {
         if(p_c_2_ && this.getType(p_c_1_).getBlock().s()) {
            int i1 = this.c(p_c_1_.up(), false);
            int i = this.c(p_c_1_.east(), false);
            int j = this.c(p_c_1_.west(), false);
            int k = this.c(p_c_1_.south(), false);
            int l = this.c(p_c_1_.north(), false);
            if(i > i1) {
               i1 = i;
            }

            if(j > i1) {
               i1 = j;
            }

            if(k > i1) {
               i1 = k;
            }

            if(l > i1) {
               i1 = l;
            }

            return i1;
         } else if(p_c_1_.getY() < 0) {
            return 0;
         } else {
            if(p_c_1_.getY() >= 256) {
               p_c_1_ = new BlockPosition(p_c_1_.getX(), 255, p_c_1_.getZ());
            }

            Chunk chunk = this.getChunkAtWorldCoords(p_c_1_);
            return chunk.a(p_c_1_, this.I);
         }
      } else {
         return 15;
      }
   }

   public BlockPosition getHighestBlockYAt(BlockPosition p_getHighestBlockYAt_1_) {
      int i;
      if(p_getHighestBlockYAt_1_.getX() >= -30000000 && p_getHighestBlockYAt_1_.getZ() >= -30000000 && p_getHighestBlockYAt_1_.getX() < 30000000 && p_getHighestBlockYAt_1_.getZ() < 30000000) {
         if(this.isChunkLoaded(p_getHighestBlockYAt_1_.getX() >> 4, p_getHighestBlockYAt_1_.getZ() >> 4, true)) {
            i = this.getChunkAt(p_getHighestBlockYAt_1_.getX() >> 4, p_getHighestBlockYAt_1_.getZ() >> 4).b(p_getHighestBlockYAt_1_.getX() & 15, p_getHighestBlockYAt_1_.getZ() & 15);
         } else {
            i = 0;
         }
      } else {
         i = this.F() + 1;
      }

      return new BlockPosition(p_getHighestBlockYAt_1_.getX(), i, p_getHighestBlockYAt_1_.getZ());
   }

   public int b(int p_b_1_, int p_b_2_) {
      if(p_b_1_ >= -30000000 && p_b_2_ >= -30000000 && p_b_1_ < 30000000 && p_b_2_ < 30000000) {
         if(!this.isChunkLoaded(p_b_1_ >> 4, p_b_2_ >> 4, true)) {
            return 0;
         } else {
            Chunk chunk = this.getChunkAt(p_b_1_ >> 4, p_b_2_ >> 4);
            return chunk.v();
         }
      } else {
         return this.F() + 1;
      }
   }

   public int b(EnumSkyBlock p_b_1_, BlockPosition p_b_2_) {
      if(p_b_2_.getY() < 0) {
         p_b_2_ = new BlockPosition(p_b_2_.getX(), 0, p_b_2_.getZ());
      }

      if(!this.isValidLocation(p_b_2_)) {
         return p_b_1_.c;
      } else if(!this.isLoaded(p_b_2_)) {
         return p_b_1_.c;
      } else {
         Chunk chunk = this.getChunkAtWorldCoords(p_b_2_);
         return chunk.getBrightness(p_b_1_, p_b_2_);
      }
   }

   public void a(EnumSkyBlock p_a_1_, BlockPosition p_a_2_, int p_a_3_) {
      if(this.isValidLocation(p_a_2_) && this.isLoaded(p_a_2_)) {
         Chunk chunk = this.getChunkAtWorldCoords(p_a_2_);
         chunk.a(p_a_1_, p_a_2_, p_a_3_);
         this.n(p_a_2_);
      }

   }

   public void n(BlockPosition p_n_1_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).b(p_n_1_);
      }

   }

   public float o(BlockPosition p_o_1_) {
      return this.worldProvider.p()[this.getLightLevel(p_o_1_)];
   }

   public IBlockData getType(BlockPosition p_getType_1_) {
      return this.getType(p_getType_1_, true);
   }

   public IBlockData getType(BlockPosition p_getType_1_, boolean p_getType_2_) {
      if(this.captureTreeGeneration && p_getType_2_) {
         for(org.bukkit.block.BlockState blockstate : this.capturedBlockStates) {
            if(blockstate.getX() == p_getType_1_.getX() && blockstate.getY() == p_getType_1_.getY() && blockstate.getZ() == p_getType_1_.getZ()) {
               return CraftMagicNumbers.getBlock(blockstate.getTypeId()).fromLegacyData(blockstate.getRawData());
            }
         }
      }

      if(!this.isValidLocation(p_getType_1_)) {
         return Blocks.AIR.getBlockData();
      } else {
         Chunk chunk = this.getChunkAtWorldCoords(p_getType_1_);
         return chunk.getBlockData(p_getType_1_);
      }
   }

   public boolean w() {
      return this.I < 4;
   }

   public MovingObjectPosition rayTrace(Vec3D p_rayTrace_1_, Vec3D p_rayTrace_2_) {
      return this.rayTrace(p_rayTrace_1_, p_rayTrace_2_, false, false, false);
   }

   public MovingObjectPosition rayTrace(Vec3D p_rayTrace_1_, Vec3D p_rayTrace_2_, boolean p_rayTrace_3_) {
      return this.rayTrace(p_rayTrace_1_, p_rayTrace_2_, p_rayTrace_3_, false, false);
   }

   public MovingObjectPosition rayTrace(Vec3D p_rayTrace_1_, Vec3D p_rayTrace_2_, boolean p_rayTrace_3_, boolean p_rayTrace_4_, boolean p_rayTrace_5_) {
      if(!Double.isNaN(p_rayTrace_1_.a) && !Double.isNaN(p_rayTrace_1_.b) && !Double.isNaN(p_rayTrace_1_.c)) {
         if(!Double.isNaN(p_rayTrace_2_.a) && !Double.isNaN(p_rayTrace_2_.b) && !Double.isNaN(p_rayTrace_2_.c)) {
            int i = MathHelper.floor(p_rayTrace_2_.a);
            int j = MathHelper.floor(p_rayTrace_2_.b);
            int k = MathHelper.floor(p_rayTrace_2_.c);
            int l = MathHelper.floor(p_rayTrace_1_.a);
            int i1 = MathHelper.floor(p_rayTrace_1_.b);
            int j1 = MathHelper.floor(p_rayTrace_1_.c);
            BlockPosition blockposition = new BlockPosition(l, i1, j1);
            IBlockData iblockdata = this.getType(blockposition);
            Block block = iblockdata.getBlock();
            if((!p_rayTrace_4_ || block.a(this, blockposition, iblockdata) != null) && block.a(iblockdata, p_rayTrace_3_)) {
               MovingObjectPosition movingobjectposition = block.a(this, blockposition, p_rayTrace_1_, p_rayTrace_2_);
               if(movingobjectposition != null) {
                  return movingobjectposition;
               }
            }

            MovingObjectPosition movingobjectposition2 = null;
            int k1 = 200;

            while(k1-- >= 0) {
               if(Double.isNaN(p_rayTrace_1_.a) || Double.isNaN(p_rayTrace_1_.b) || Double.isNaN(p_rayTrace_1_.c)) {
                  return null;
               }

               if(l == i && i1 == j && j1 == k) {
                  return p_rayTrace_5_?movingobjectposition2:null;
               }

               boolean flag = true;
               boolean flag1 = true;
               boolean flag2 = true;
               double d0 = 999.0D;
               double d1 = 999.0D;
               double d2 = 999.0D;
               if(i > l) {
                  d0 = (double)l + 1.0D;
               } else if(i < l) {
                  d0 = (double)l + 0.0D;
               } else {
                  flag = false;
               }

               if(j > i1) {
                  d1 = (double)i1 + 1.0D;
               } else if(j < i1) {
                  d1 = (double)i1 + 0.0D;
               } else {
                  flag1 = false;
               }

               if(k > j1) {
                  d2 = (double)j1 + 1.0D;
               } else if(k < j1) {
                  d2 = (double)j1 + 0.0D;
               } else {
                  flag2 = false;
               }

               double d3 = 999.0D;
               double d4 = 999.0D;
               double d5 = 999.0D;
               double d6 = p_rayTrace_2_.a - p_rayTrace_1_.a;
               double d7 = p_rayTrace_2_.b - p_rayTrace_1_.b;
               double d8 = p_rayTrace_2_.c - p_rayTrace_1_.c;
               if(flag) {
                  d3 = (d0 - p_rayTrace_1_.a) / d6;
               }

               if(flag1) {
                  d4 = (d1 - p_rayTrace_1_.b) / d7;
               }

               if(flag2) {
                  d5 = (d2 - p_rayTrace_1_.c) / d8;
               }

               if(d3 == -0.0D) {
                  d3 = -1.0E-4D;
               }

               if(d4 == -0.0D) {
                  d4 = -1.0E-4D;
               }

               if(d5 == -0.0D) {
                  d5 = -1.0E-4D;
               }

               EnumDirection enumdirection;
               if(d3 < d4 && d3 < d5) {
                  enumdirection = i > l?EnumDirection.WEST:EnumDirection.EAST;
                  p_rayTrace_1_ = new Vec3D(d0, p_rayTrace_1_.b + d7 * d3, p_rayTrace_1_.c + d8 * d3);
               } else if(d4 < d5) {
                  enumdirection = j > i1?EnumDirection.DOWN:EnumDirection.UP;
                  p_rayTrace_1_ = new Vec3D(p_rayTrace_1_.a + d6 * d4, d1, p_rayTrace_1_.c + d8 * d4);
               } else {
                  enumdirection = k > j1?EnumDirection.NORTH:EnumDirection.SOUTH;
                  p_rayTrace_1_ = new Vec3D(p_rayTrace_1_.a + d6 * d5, p_rayTrace_1_.b + d7 * d5, d2);
               }

               l = MathHelper.floor(p_rayTrace_1_.a) - (enumdirection == EnumDirection.EAST?1:0);
               i1 = MathHelper.floor(p_rayTrace_1_.b) - (enumdirection == EnumDirection.UP?1:0);
               j1 = MathHelper.floor(p_rayTrace_1_.c) - (enumdirection == EnumDirection.SOUTH?1:0);
               blockposition = new BlockPosition(l, i1, j1);
               IBlockData iblockdata1 = this.getType(blockposition);
               Block block1 = iblockdata1.getBlock();
               if(!p_rayTrace_4_ || block1.a(this, blockposition, iblockdata1) != null) {
                  if(block1.a(iblockdata1, p_rayTrace_3_)) {
                     MovingObjectPosition movingobjectposition1 = block1.a(this, blockposition, p_rayTrace_1_, p_rayTrace_2_);
                     if(movingobjectposition1 != null) {
                        return movingobjectposition1;
                     }
                  } else {
                     movingobjectposition2 = new MovingObjectPosition(MovingObjectPosition.EnumMovingObjectType.MISS, p_rayTrace_1_, enumdirection, blockposition);
                  }
               }
            }

            return p_rayTrace_5_?movingobjectposition2:null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public void makeSound(Entity p_makeSound_1_, String p_makeSound_2_, float p_makeSound_3_, float p_makeSound_4_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).a(p_makeSound_2_, p_makeSound_1_.locX, p_makeSound_1_.locY, p_makeSound_1_.locZ, p_makeSound_3_, p_makeSound_4_);
      }

   }

   public void a(EntityHuman p_a_1_, String p_a_2_, float p_a_3_, float p_a_4_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).a(p_a_1_, p_a_2_, p_a_1_.locX, p_a_1_.locY, p_a_1_.locZ, p_a_3_, p_a_4_);
      }

   }

   public void makeSound(double p_makeSound_1_, double p_makeSound_3_, double p_makeSound_5_, String p_makeSound_7_, float p_makeSound_8_, float p_makeSound_9_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).a(p_makeSound_7_, p_makeSound_1_, p_makeSound_3_, p_makeSound_5_, p_makeSound_8_, p_makeSound_9_);
      }

   }

   public void a(double p_a_1_, double p_a_3_, double p_a_5_, String p_a_7_, float p_a_8_, float p_a_9_, boolean p_a_10_) {
   }

   public void a(BlockPosition p_a_1_, String p_a_2_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).a(p_a_2_, p_a_1_);
      }

   }

   public void addParticle(EnumParticle p_addParticle_1_, double p_addParticle_2_, double p_addParticle_4_, double p_addParticle_6_, double p_addParticle_8_, double p_addParticle_10_, double p_addParticle_12_, int... p_addParticle_14_) {
      this.a(p_addParticle_1_.c(), p_addParticle_1_.e(), p_addParticle_2_, p_addParticle_4_, p_addParticle_6_, p_addParticle_8_, p_addParticle_10_, p_addParticle_12_, p_addParticle_14_);
   }

   private void a(int p_a_1_, boolean p_a_2_, double p_a_3_, double p_a_5_, double p_a_7_, double p_a_9_, double p_a_11_, double p_a_13_, int... p_a_15_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).a(p_a_1_, p_a_2_, p_a_3_, p_a_5_, p_a_7_, p_a_9_, p_a_11_, p_a_13_, p_a_15_);
      }

   }

   public boolean strikeLightning(Entity p_strikeLightning_1_) {
      this.k.add(p_strikeLightning_1_);
      return true;
   }

   public boolean addEntity(Entity p_addEntity_1_) {
      return this.addEntity(p_addEntity_1_, SpawnReason.DEFAULT);
   }

   public boolean addEntity(Entity p_addEntity_1_, SpawnReason p_addEntity_2_) {
      AsyncCatcher.catchOp("entity add");
      if(p_addEntity_1_ == null) {
         return false;
      } else {
         int i = MathHelper.floor(p_addEntity_1_.locX / 16.0D);
         int j = MathHelper.floor(p_addEntity_1_.locZ / 16.0D);
         boolean flag = p_addEntity_1_.attachedToPlayer;
         if(p_addEntity_1_ instanceof EntityHuman) {
            flag = true;
         }

         Cancellable cancellable = null;
         if(p_addEntity_1_ instanceof EntityLiving && !(p_addEntity_1_ instanceof EntityPlayer)) {
            boolean flag2 = p_addEntity_1_ instanceof EntityAnimal || p_addEntity_1_ instanceof EntityWaterAnimal || p_addEntity_1_ instanceof EntityGolem;
            boolean flag1 = p_addEntity_1_ instanceof EntityMonster || p_addEntity_1_ instanceof EntityGhast || p_addEntity_1_ instanceof EntitySlime;
            if(p_addEntity_2_ != SpawnReason.CUSTOM && (flag2 && !this.allowAnimals || flag1 && !this.allowMonsters)) {
               p_addEntity_1_.dead = true;
               return false;
            }

            cancellable = CraftEventFactory.callCreatureSpawnEvent((EntityLiving)p_addEntity_1_, p_addEntity_2_);
         } else if(p_addEntity_1_ instanceof EntityItem) {
            cancellable = CraftEventFactory.callItemSpawnEvent((EntityItem)p_addEntity_1_);
         } else if(p_addEntity_1_.getBukkitEntity() instanceof Projectile) {
            cancellable = CraftEventFactory.callProjectileLaunchEvent(p_addEntity_1_);
         } else if(p_addEntity_1_ instanceof EntityExperienceOrb) {
            EntityExperienceOrb entityexperienceorb = (EntityExperienceOrb)p_addEntity_1_;
            double d0 = this.spigotConfig.expMerge;
            if(d0 > 0.0D) {
               for(Entity entity : this.getEntities(p_addEntity_1_, p_addEntity_1_.getBoundingBox().grow(d0, d0, d0))) {
                  if(entity instanceof EntityExperienceOrb) {
                     EntityExperienceOrb entityexperienceorb1 = (EntityExperienceOrb)entity;
                     if(!entityexperienceorb1.dead) {
                        entityexperienceorb.value += entityexperienceorb1.value;
                        entityexperienceorb1.die();
                     }
                  }
               }
            }
         }

         if(cancellable == null || !cancellable.isCancelled() && !p_addEntity_1_.dead) {
            if(!flag && !this.isChunkLoaded(i, j, true)) {
               p_addEntity_1_.dead = true;
               return false;
            } else {
               if(p_addEntity_1_ instanceof EntityHuman) {
                  EntityHuman entityhuman = (EntityHuman)p_addEntity_1_;
                  this.players.add(entityhuman);
                  this.everyoneSleeping();
               }

               this.getChunkAt(i, j).a(p_addEntity_1_);
               this.entityList.add(p_addEntity_1_);
               this.a(p_addEntity_1_);
               return true;
            }
         } else {
            p_addEntity_1_.dead = true;
            return false;
         }
      }
   }

   protected void a(Entity p_a_1_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).a(p_a_1_);
      }

      p_a_1_.valid = true;
   }

   protected void b(Entity p_b_1_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).b(p_b_1_);
      }

      p_b_1_.valid = false;
   }

   public void kill(Entity p_kill_1_) {
      if(p_kill_1_.passenger != null) {
         p_kill_1_.passenger.mount((Entity)null);
      }

      if(p_kill_1_.vehicle != null) {
         p_kill_1_.mount((Entity)null);
      }

      p_kill_1_.die();
      if(p_kill_1_ instanceof EntityHuman) {
         this.players.remove(p_kill_1_);

         for(Object object : this.worldMaps.c) {
            if(object instanceof WorldMap) {
               WorldMap worldmap = (WorldMap)object;
               worldmap.i.remove(p_kill_1_);
               Iterator<WorldMap.WorldMapHumanTracker> iterator = worldmap.g.iterator();

               while(iterator.hasNext()) {
                  if(((WorldMap.WorldMapHumanTracker)iterator.next()).trackee == p_kill_1_) {
                     iterator.remove();
                  }
               }
            }
         }

         this.everyoneSleeping();
         this.b(p_kill_1_);
      }

   }

   public void removeEntity(Entity p_removeEntity_1_) {
      AsyncCatcher.catchOp("entity remove");
      p_removeEntity_1_.die();
      if(p_removeEntity_1_ instanceof EntityHuman) {
         this.players.remove(p_removeEntity_1_);
         this.everyoneSleeping();
      }

      if(!this.guardEntityList) {
         int i = p_removeEntity_1_.ae;
         int j = p_removeEntity_1_.ag;
         if(p_removeEntity_1_.ad && this.isChunkLoaded(i, j, true)) {
            this.getChunkAt(i, j).b(p_removeEntity_1_);
         }

         int k = this.entityList.indexOf(p_removeEntity_1_);
         if(k != -1) {
            if(k <= this.tickPosition) {
               --this.tickPosition;
            }

            this.entityList.remove(k);
         }
      }

      this.b(p_removeEntity_1_);
   }

   public void addIWorldAccess(IWorldAccess p_addIWorldAccess_1_) {
      this.u.add(p_addIWorldAccess_1_);
   }

   public List<AxisAlignedBB> getCubes(Entity p_getCubes_1_, AxisAlignedBB p_getCubes_2_) {
      ArrayList arraylist = Lists.newArrayList();
      int i = MathHelper.floor(p_getCubes_2_.a);
      int j = MathHelper.floor(p_getCubes_2_.d + 1.0D);
      int k = MathHelper.floor(p_getCubes_2_.b);
      int l = MathHelper.floor(p_getCubes_2_.e + 1.0D);
      int i1 = MathHelper.floor(p_getCubes_2_.c);
      int j1 = MathHelper.floor(p_getCubes_2_.f + 1.0D);
      WorldBorder worldborder = this.getWorldBorder();
      boolean flag = p_getCubes_1_.aT();
      boolean flag1 = this.a(worldborder, p_getCubes_1_);
      Blocks.STONE.getBlockData();
      new BlockPosition.MutableBlockPosition();
      int k1 = k - 1 < 0?0:k - 1;

      for(int l1 = i >> 4; l1 <= j - 1 >> 4; ++l1) {
         int i2 = l1 << 4;

         for(int j2 = i1 >> 4; j2 <= j1 - 1 >> 4; ++j2) {
            if(this.isChunkLoaded(l1, j2, true)) {
               int k2 = j2 << 4;
               Chunk chunk = this.getChunkAt(l1, j2);
               int l2 = i < i2?i2:i;
               int i3 = j < i2 + 16?j:i2 + 16;
               int j3 = i1 < k2?k2:i1;
               int k3 = j1 < k2 + 16?j1:k2 + 16;

               for(int l3 = l2; l3 < i3; ++l3) {
                  for(int i4 = j3; i4 < k3; ++i4) {
                     for(int j4 = k1; j4 < l; ++j4) {
                        BlockPosition blockposition = new BlockPosition(l3, j4, i4);
                        if(flag && flag1) {
                           p_getCubes_1_.h(false);
                        } else if(!flag && !flag1) {
                           p_getCubes_1_.h(true);
                        }

                        IBlockData iblockdata;
                        if(!this.getWorldBorder().a(blockposition) && flag1) {
                           iblockdata = Blocks.STONE.getBlockData();
                        } else {
                           iblockdata = chunk.getBlockData(blockposition);
                        }

                        if(iblockdata != null) {
                           iblockdata.getBlock().a(this, blockposition, iblockdata, p_getCubes_2_, arraylist, p_getCubes_1_);
                        }
                     }
                  }
               }
            }
         }
      }

      double d0 = 0.25D;
      List list = this.getEntities(p_getCubes_1_, p_getCubes_2_.grow(d0, d0, d0));

      for(int k4 = 0; k4 < list.size(); ++k4) {
         if(p_getCubes_1_.passenger != list && p_getCubes_1_.vehicle != list) {
            AxisAlignedBB axisalignedbb = ((Entity)list.get(k4)).S();
            if(axisalignedbb != null && axisalignedbb.b(p_getCubes_2_)) {
               arraylist.add(axisalignedbb);
            }

            axisalignedbb = p_getCubes_1_.j((Entity)list.get(k4));
            if(axisalignedbb != null && axisalignedbb.b(p_getCubes_2_)) {
               arraylist.add(axisalignedbb);
            }
         }
      }

      return arraylist;
   }

   public boolean a(WorldBorder p_a_1_, Entity p_a_2_) {
      double d0 = p_a_1_.b();
      double d1 = p_a_1_.c();
      double d2 = p_a_1_.d();
      double d3 = p_a_1_.e();
      if(p_a_2_.aT()) {
         ++d0;
         ++d1;
         --d2;
         --d3;
      } else {
         --d0;
         --d1;
         ++d2;
         ++d3;
      }

      return p_a_2_.locX > d0 && p_a_2_.locX < d2 && p_a_2_.locZ > d1 && p_a_2_.locZ < d3;
   }

   public List<AxisAlignedBB> a(AxisAlignedBB p_a_1_) {
      ArrayList arraylist = Lists.newArrayList();
      int i = MathHelper.floor(p_a_1_.a);
      int j = MathHelper.floor(p_a_1_.d + 1.0D);
      int k = MathHelper.floor(p_a_1_.b);
      int l = MathHelper.floor(p_a_1_.e + 1.0D);
      int i1 = MathHelper.floor(p_a_1_.c);
      int j1 = MathHelper.floor(p_a_1_.f + 1.0D);
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = i1; l1 < j1; ++l1) {
            if(this.isLoaded(blockposition$mutableblockposition.c(k1, 64, l1))) {
               for(int i2 = k - 1; i2 < l; ++i2) {
                  blockposition$mutableblockposition.c(k1, i2, l1);
                  IBlockData iblockdata;
                  if(k1 >= -30000000 && k1 < 30000000 && l1 >= -30000000 && l1 < 30000000) {
                     iblockdata = this.getType(blockposition$mutableblockposition);
                  } else {
                     iblockdata = Blocks.BEDROCK.getBlockData();
                  }

                  iblockdata.getBlock().a(this, blockposition$mutableblockposition, iblockdata, p_a_1_, arraylist, (Entity)null);
               }
            }
         }
      }

      return arraylist;
   }

   public int a(float p_a_1_) {
      float f = this.c(p_a_1_);
      float f1 = 1.0F - (MathHelper.cos(f * 3.1415927F * 2.0F) * 2.0F + 0.5F);
      f1 = MathHelper.a(f1, 0.0F, 1.0F);
      f1 = 1.0F - f1;
      f1 = (float)((double)f1 * (1.0D - (double)(this.j(p_a_1_) * 5.0F) / 16.0D));
      f1 = (float)((double)f1 * (1.0D - (double)(this.h(p_a_1_) * 5.0F) / 16.0D));
      f1 = 1.0F - f1;
      return (int)(f1 * 11.0F);
   }

   public float c(float p_c_1_) {
      return this.worldProvider.a(this.worldData.getDayTime(), p_c_1_);
   }

   public float y() {
      return WorldProvider.a[this.worldProvider.a(this.worldData.getDayTime())];
   }

   public float d(float p_d_1_) {
      float f = this.c(p_d_1_);
      return f * 3.1415927F * 2.0F;
   }

   public BlockPosition q(BlockPosition p_q_1_) {
      return this.getChunkAtWorldCoords(p_q_1_).h(p_q_1_);
   }

   public BlockPosition r(BlockPosition p_r_1_) {
      Chunk chunk = this.getChunkAtWorldCoords(p_r_1_);

      BlockPosition blockposition;
      BlockPosition blockposition1;
      for(blockposition = new BlockPosition(p_r_1_.getX(), chunk.g() + 16, p_r_1_.getZ()); blockposition.getY() >= 0; blockposition = blockposition1) {
         blockposition1 = blockposition.down();
         Material material = chunk.getType(blockposition1).getMaterial();
         if(material.isSolid() && material != Material.LEAVES) {
            break;
         }
      }

      return blockposition;
   }

   public void a(BlockPosition p_a_1_, Block p_a_2_, int p_a_3_) {
   }

   public void a(BlockPosition p_a_1_, Block p_a_2_, int p_a_3_, int p_a_4_) {
   }

   public void b(BlockPosition p_b_1_, Block p_b_2_, int p_b_3_, int p_b_4_) {
   }

   public void tickEntities() {
      this.methodProfiler.a("entities");
      this.methodProfiler.a("global");

      for(int i = 0; i < this.k.size(); ++i) {
         Entity entity = (Entity)this.k.get(i);
         if(entity != null) {
            try {
               ++entity.ticksLived;
               entity.t_();
            } catch (Throwable throwable2) {
               CrashReport crashreport = CrashReport.a(throwable2, "Ticking entity");
               CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Entity being ticked");
               if(entity == null) {
                  crashreportsystemdetails.a((String)"Entity", "~~NULL~~");
               } else {
                  entity.appendEntityCrashDetails(crashreportsystemdetails);
               }

               throw new ReportedException(crashreport);
            }

            if(entity.dead) {
               this.k.remove(i--);
            }
         }
      }

      this.methodProfiler.c("remove");
      this.entityList.removeAll(this.g);

      for(int j1 = 0; j1 < this.g.size(); ++j1) {
         Entity entity1 = (Entity)this.g.get(j1);
         int j = entity1.ae;
         int k = entity1.ag;
         if(entity1.ad && this.isChunkLoaded(j, k, true)) {
            this.getChunkAt(j, k).b(entity1);
         }
      }

      for(int k1 = 0; k1 < this.g.size(); ++k1) {
         this.b((Entity)this.g.get(k1));
      }

      this.g.clear();
      this.methodProfiler.c("regular");
      ActivationRange.activateEntities(this);
      this.timings.entityTick.startTiming();
      this.guardEntityList = true;
      int l = 0;
      if(this.tickPosition < 0) {
         this.tickPosition = 0;
      }

      this.entityLimiter.initTick();

      while(l < this.entityList.size() && (l % 10 != 0 || this.entityLimiter.shouldContinue())) {
         label379: {
            this.tickPosition = this.tickPosition < this.entityList.size()?this.tickPosition:0;
            Entity entity2 = (Entity)this.entityList.get(this.tickPosition);
            if(entity2.vehicle != null) {
               if(!entity2.vehicle.dead && entity2.vehicle.passenger == entity2) {
                  break label379;
               }

               entity2.vehicle.passenger = null;
               entity2.vehicle = null;
            }

            this.methodProfiler.a("tick");
            if(!entity2.dead) {
               try {
                  SpigotTimings.tickEntityTimer.startTiming();
                  this.g(entity2);
                  SpigotTimings.tickEntityTimer.stopTiming();
               } catch (Throwable throwable11) {
                  CrashReport crashreport2 = CrashReport.a(throwable11, "Ticking entity");
                  CrashReportSystemDetails crashreportsystemdetails2 = crashreport2.a("Entity being ticked");
                  entity2.appendEntityCrashDetails(crashreportsystemdetails2);
                  throw new ReportedException(crashreport2);
               }
            }

            this.methodProfiler.b();
            this.methodProfiler.a("remove");
            if(entity2.dead) {
               int l1 = entity2.ae;
               int i2 = entity2.ag;
               if(entity2.ad && this.isChunkLoaded(l1, i2, true)) {
                  this.getChunkAt(l1, i2).b(entity2);
               }

               this.guardEntityList = false;
               this.entityList.remove(this.tickPosition--);
               this.guardEntityList = true;
               this.b(entity2);
            }

            this.methodProfiler.b();
         }

         ++this.tickPosition;
         ++l;
      }

      this.guardEntityList = false;
      this.timings.entityTick.stopTiming();
      this.methodProfiler.c("blockEntities");
      this.timings.tileEntityTick.startTiming();
      this.M = true;
      if(!this.c.isEmpty()) {
         this.tileEntityList.removeAll(this.c);
         this.h.removeAll(this.c);
         this.c.clear();
      }

      int i1 = 0;
      this.tileLimiter.initTick();

      while(i1 < this.tileEntityList.size() && (i1 % 10 != 0 || this.tileLimiter.shouldContinue())) {
         this.tileTickPosition = this.tileTickPosition < this.tileEntityList.size()?this.tileTickPosition:0;
         TileEntity tileentity = (TileEntity)this.tileEntityList.get(this.tileTickPosition);
         if(tileentity == null) {
            this.getServer().getLogger().severe("Spigot has detected a null entity and has removed it, preventing a crash");
            --i1;
            this.tileEntityList.remove(this.tileTickPosition--);
         } else {
            if(!tileentity.x() && tileentity.t()) {
               BlockPosition blockposition = tileentity.getPosition();
               if(this.isLoaded(blockposition) && this.N.a(blockposition)) {
                  try {
                     tileentity.tickTimer.startTiming();
                     ((IUpdatePlayerListBox)tileentity).c();
                  } catch (Throwable throwable1) {
                     CrashReport crashreport1 = CrashReport.a(throwable1, "Ticking block entity");
                     CrashReportSystemDetails crashreportsystemdetails1 = crashreport1.a("Block entity being ticked");
                     tileentity.a(crashreportsystemdetails1);
                     throw new ReportedException(crashreport1);
                  } finally {
                     tileentity.tickTimer.stopTiming();
                  }
               }
            }

            if(tileentity.x()) {
               --i1;
               this.tileEntityList.remove(this.tileTickPosition--);
               this.h.remove(tileentity);
               if(this.isLoaded(tileentity.getPosition())) {
                  this.getChunkAtWorldCoords(tileentity.getPosition()).e(tileentity.getPosition());
               }
            }
         }

         ++this.tileTickPosition;
         ++i1;
      }

      this.timings.tileEntityTick.stopTiming();
      this.timings.tileEntityPending.startTiming();
      this.M = false;
      this.methodProfiler.c("pendingBlockEntities");
      if(!this.b.isEmpty()) {
         for(int j2 = 0; j2 < this.b.size(); ++j2) {
            TileEntity tileentity1 = (TileEntity)this.b.get(j2);
            if(!tileentity1.x()) {
               if(this.isLoaded(tileentity1.getPosition())) {
                  this.getChunkAtWorldCoords(tileentity1.getPosition()).a(tileentity1.getPosition(), tileentity1);
               }

               this.notify(tileentity1.getPosition());
            }
         }

         this.b.clear();
      }

      this.timings.tileEntityPending.stopTiming();
      this.methodProfiler.b();
      this.methodProfiler.b();
   }

   public boolean a(TileEntity p_a_1_) {
      boolean flag = this.h.add(p_a_1_);
      if(flag && p_a_1_ instanceof IUpdatePlayerListBox) {
         this.tileEntityList.add(p_a_1_);
      }

      return flag;
   }

   public void a(Collection<TileEntity> p_a_1_) {
      if(this.M) {
         this.b.addAll(p_a_1_);
      } else {
         for(TileEntity tileentity : p_a_1_) {
            this.h.add(tileentity);
            if(tileentity instanceof IUpdatePlayerListBox) {
               this.tileEntityList.add(tileentity);
            }
         }
      }

   }

   public void g(Entity p_g_1_) {
      this.entityJoinedWorld(p_g_1_, true);
   }

   public void entityJoinedWorld(Entity p_entityJoinedWorld_1_, boolean p_entityJoinedWorld_2_) {
      MathHelper.floor(p_entityJoinedWorld_1_.locX);
      MathHelper.floor(p_entityJoinedWorld_1_.locZ);
      if(!ActivationRange.checkIfActive(p_entityJoinedWorld_1_)) {
         ++p_entityJoinedWorld_1_.ticksLived;
         p_entityJoinedWorld_1_.inactiveTick();
      } else {
         p_entityJoinedWorld_1_.tickTimer.startTiming();
         p_entityJoinedWorld_1_.P = p_entityJoinedWorld_1_.locX;
         p_entityJoinedWorld_1_.Q = p_entityJoinedWorld_1_.locY;
         p_entityJoinedWorld_1_.R = p_entityJoinedWorld_1_.locZ;
         p_entityJoinedWorld_1_.lastYaw = p_entityJoinedWorld_1_.yaw;
         p_entityJoinedWorld_1_.lastPitch = p_entityJoinedWorld_1_.pitch;
         if(p_entityJoinedWorld_2_ && p_entityJoinedWorld_1_.ad) {
            ++p_entityJoinedWorld_1_.ticksLived;
            if(p_entityJoinedWorld_1_.vehicle != null) {
               p_entityJoinedWorld_1_.ak();
            } else {
               p_entityJoinedWorld_1_.t_();
            }
         }

         this.methodProfiler.a("chunkCheck");
         if(Double.isNaN(p_entityJoinedWorld_1_.locX) || Double.isInfinite(p_entityJoinedWorld_1_.locX)) {
            p_entityJoinedWorld_1_.locX = p_entityJoinedWorld_1_.P;
         }

         if(Double.isNaN(p_entityJoinedWorld_1_.locY) || Double.isInfinite(p_entityJoinedWorld_1_.locY)) {
            p_entityJoinedWorld_1_.locY = p_entityJoinedWorld_1_.Q;
         }

         if(Double.isNaN(p_entityJoinedWorld_1_.locZ) || Double.isInfinite(p_entityJoinedWorld_1_.locZ)) {
            p_entityJoinedWorld_1_.locZ = p_entityJoinedWorld_1_.R;
         }

         if(Double.isNaN((double)p_entityJoinedWorld_1_.pitch) || Double.isInfinite((double)p_entityJoinedWorld_1_.pitch)) {
            p_entityJoinedWorld_1_.pitch = p_entityJoinedWorld_1_.lastPitch;
         }

         if(Double.isNaN((double)p_entityJoinedWorld_1_.yaw) || Double.isInfinite((double)p_entityJoinedWorld_1_.yaw)) {
            p_entityJoinedWorld_1_.yaw = p_entityJoinedWorld_1_.lastYaw;
         }

         int i = MathHelper.floor(p_entityJoinedWorld_1_.locX / 16.0D);
         int j = MathHelper.floor(p_entityJoinedWorld_1_.locY / 16.0D);
         int k = MathHelper.floor(p_entityJoinedWorld_1_.locZ / 16.0D);
         if(!p_entityJoinedWorld_1_.ad || p_entityJoinedWorld_1_.ae != i || p_entityJoinedWorld_1_.af != j || p_entityJoinedWorld_1_.ag != k) {
            if(p_entityJoinedWorld_1_.ad && this.isChunkLoaded(p_entityJoinedWorld_1_.ae, p_entityJoinedWorld_1_.ag, true)) {
               this.getChunkAt(p_entityJoinedWorld_1_.ae, p_entityJoinedWorld_1_.ag).a(p_entityJoinedWorld_1_, p_entityJoinedWorld_1_.af);
            }

            if(this.isChunkLoaded(i, k, true)) {
               p_entityJoinedWorld_1_.ad = true;
               this.getChunkAt(i, k).a(p_entityJoinedWorld_1_);
            } else {
               p_entityJoinedWorld_1_.ad = false;
            }
         }

         this.methodProfiler.b();
         if(p_entityJoinedWorld_2_ && p_entityJoinedWorld_1_.ad && p_entityJoinedWorld_1_.passenger != null) {
            if(!p_entityJoinedWorld_1_.passenger.dead && p_entityJoinedWorld_1_.passenger.vehicle == p_entityJoinedWorld_1_) {
               this.g(p_entityJoinedWorld_1_.passenger);
            } else {
               p_entityJoinedWorld_1_.passenger.vehicle = null;
               p_entityJoinedWorld_1_.passenger = null;
            }
         }

         p_entityJoinedWorld_1_.tickTimer.stopTiming();
      }

   }

   public boolean b(AxisAlignedBB p_b_1_) {
      return this.a((AxisAlignedBB)p_b_1_, (Entity)null);
   }

   public boolean a(AxisAlignedBB p_a_1_, Entity p_a_2_) {
      List list = this.getEntities((Entity)null, p_a_1_);

      for(int i = 0; i < list.size(); ++i) {
         Entity entity = (Entity)list.get(i);
         if(!entity.dead && entity.k && entity != p_a_2_ && (p_a_2_ == null || p_a_2_.vehicle != entity && p_a_2_.passenger != entity)) {
            return false;
         }
      }

      return true;
   }

   public boolean c(AxisAlignedBB p_c_1_) {
      int i = MathHelper.floor(p_c_1_.a);
      int j = MathHelper.floor(p_c_1_.d);
      int k = MathHelper.floor(p_c_1_.b);
      int l = MathHelper.floor(p_c_1_.e);
      int i1 = MathHelper.floor(p_c_1_.c);
      int j1 = MathHelper.floor(p_c_1_.f);
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int k1 = i; k1 <= j; ++k1) {
         for(int l1 = k; l1 <= l; ++l1) {
            for(int i2 = i1; i2 <= j1; ++i2) {
               Block block = this.getType(blockposition$mutableblockposition.c(k1, l1, i2)).getBlock();
               if(block.getMaterial() != Material.AIR) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean containsLiquid(AxisAlignedBB p_containsLiquid_1_) {
      int i = MathHelper.floor(p_containsLiquid_1_.a);
      int j = MathHelper.floor(p_containsLiquid_1_.d);
      int k = MathHelper.floor(p_containsLiquid_1_.b);
      int l = MathHelper.floor(p_containsLiquid_1_.e);
      int i1 = MathHelper.floor(p_containsLiquid_1_.c);
      int j1 = MathHelper.floor(p_containsLiquid_1_.f);
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int k1 = i; k1 <= j; ++k1) {
         for(int l1 = k; l1 <= l; ++l1) {
            for(int i2 = i1; i2 <= j1; ++i2) {
               Block block = this.getType(blockposition$mutableblockposition.c(k1, l1, i2)).getBlock();
               if(block.getMaterial().isLiquid()) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean e(AxisAlignedBB p_e_1_) {
      int i = MathHelper.floor(p_e_1_.a);
      int j = MathHelper.floor(p_e_1_.d + 1.0D);
      int k = MathHelper.floor(p_e_1_.b);
      int l = MathHelper.floor(p_e_1_.e + 1.0D);
      int i1 = MathHelper.floor(p_e_1_.c);
      int j1 = MathHelper.floor(p_e_1_.f + 1.0D);
      if(this.isAreaLoaded(i, k, i1, j, l, j1, true)) {
         BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
               for(int i2 = i1; i2 < j1; ++i2) {
                  Block block = this.getType(blockposition$mutableblockposition.c(k1, l1, i2)).getBlock();
                  if(block == Blocks.FIRE || block == Blocks.FLOWING_LAVA || block == Blocks.LAVA) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public boolean a(AxisAlignedBB p_a_1_, Material p_a_2_, Entity p_a_3_) {
      int i = MathHelper.floor(p_a_1_.a);
      int j = MathHelper.floor(p_a_1_.d + 1.0D);
      int k = MathHelper.floor(p_a_1_.b);
      int l = MathHelper.floor(p_a_1_.e + 1.0D);
      int i1 = MathHelper.floor(p_a_1_.c);
      int j1 = MathHelper.floor(p_a_1_.f + 1.0D);
      if(!this.isAreaLoaded(i, k, i1, j, l, j1, true)) {
         return false;
      } else {
         boolean flag = false;
         Vec3D vec3d = new Vec3D(0.0D, 0.0D, 0.0D);
         BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int k1 = i; k1 < j; ++k1) {
            for(int l1 = k; l1 < l; ++l1) {
               for(int i2 = i1; i2 < j1; ++i2) {
                  blockposition$mutableblockposition.c(k1, l1, i2);
                  IBlockData iblockdata = this.getType(blockposition$mutableblockposition);
                  Block block = iblockdata.getBlock();
                  if(block.getMaterial() == p_a_2_) {
                     double d0 = (double)((float)(l1 + 1) - BlockFluids.b(((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue()));
                     if((double)l >= d0) {
                        flag = true;
                        vec3d = block.a((World)this, blockposition$mutableblockposition, (Entity)p_a_3_, (Vec3D)vec3d);
                     }
                  }
               }
            }
         }

         if(vec3d.b() > 0.0D && p_a_3_.aL()) {
            vec3d = vec3d.a();
            double d1 = 0.014D;
            p_a_3_.motX += vec3d.a * d1;
            p_a_3_.motY += vec3d.b * d1;
            p_a_3_.motZ += vec3d.c * d1;
         }

         return flag;
      }
   }

   public boolean a(AxisAlignedBB p_a_1_, Material p_a_2_) {
      int i = MathHelper.floor(p_a_1_.a);
      int j = MathHelper.floor(p_a_1_.d + 1.0D);
      int k = MathHelper.floor(p_a_1_.b);
      int l = MathHelper.floor(p_a_1_.e + 1.0D);
      int i1 = MathHelper.floor(p_a_1_.c);
      int j1 = MathHelper.floor(p_a_1_.f + 1.0D);
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               if(this.getType(blockposition$mutableblockposition.c(k1, l1, i2)).getBlock().getMaterial() == p_a_2_) {
                  return true;
               }
            }
         }
      }

      return false;
   }

   public boolean b(AxisAlignedBB p_b_1_, Material p_b_2_) {
      int i = MathHelper.floor(p_b_1_.a);
      int j = MathHelper.floor(p_b_1_.d + 1.0D);
      int k = MathHelper.floor(p_b_1_.b);
      int l = MathHelper.floor(p_b_1_.e + 1.0D);
      int i1 = MathHelper.floor(p_b_1_.c);
      int j1 = MathHelper.floor(p_b_1_.f + 1.0D);
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int k1 = i; k1 < j; ++k1) {
         for(int l1 = k; l1 < l; ++l1) {
            for(int i2 = i1; i2 < j1; ++i2) {
               IBlockData iblockdata = this.getType(blockposition$mutableblockposition.c(k1, l1, i2));
               Block block = iblockdata.getBlock();
               if(block.getMaterial() == p_b_2_) {
                  int j2 = ((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue();
                  double d0 = (double)(l1 + 1);
                  if(j2 < 8) {
                     d0 = (double)(l1 + 1) - (double)j2 / 8.0D;
                  }

                  if(d0 >= p_b_1_.b) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public Explosion explode(Entity p_explode_1_, double p_explode_2_, double p_explode_4_, double p_explode_6_, float p_explode_8_, boolean p_explode_9_) {
      return this.createExplosion(p_explode_1_, p_explode_2_, p_explode_4_, p_explode_6_, p_explode_8_, false, p_explode_9_);
   }

   public Explosion createExplosion(Entity p_createExplosion_1_, double p_createExplosion_2_, double p_createExplosion_4_, double p_createExplosion_6_, float p_createExplosion_8_, boolean p_createExplosion_9_, boolean p_createExplosion_10_) {
      Explosion explosion = new Explosion(this, p_createExplosion_1_, p_createExplosion_2_, p_createExplosion_4_, p_createExplosion_6_, p_createExplosion_8_, p_createExplosion_9_, p_createExplosion_10_);
      explosion.a();
      explosion.a(true);
      return explosion;
   }

   public float a(Vec3D p_a_1_, AxisAlignedBB p_a_2_) {
      double d0 = 1.0D / ((p_a_2_.d - p_a_2_.a) * 2.0D + 1.0D);
      double d1 = 1.0D / ((p_a_2_.e - p_a_2_.b) * 2.0D + 1.0D);
      double d2 = 1.0D / ((p_a_2_.f - p_a_2_.c) * 2.0D + 1.0D);
      double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
      double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
      if(d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
         int i = 0;
         int j = 0;

         for(float f = 0.0F; f <= 1.0F; f = (float)((double)f + d0)) {
            for(float f1 = 0.0F; f1 <= 1.0F; f1 = (float)((double)f1 + d1)) {
               for(float f2 = 0.0F; f2 <= 1.0F; f2 = (float)((double)f2 + d2)) {
                  double d5 = p_a_2_.a + (p_a_2_.d - p_a_2_.a) * (double)f;
                  double d6 = p_a_2_.b + (p_a_2_.e - p_a_2_.b) * (double)f1;
                  double d7 = p_a_2_.c + (p_a_2_.f - p_a_2_.c) * (double)f2;
                  if(this.rayTrace(new Vec3D(d5 + d3, d6, d7 + d4), p_a_1_) == null) {
                     ++i;
                  }

                  ++j;
               }
            }
         }

         return (float)i / (float)j;
      } else {
         return 0.0F;
      }
   }

   public boolean douseFire(EntityHuman p_douseFire_1_, BlockPosition p_douseFire_2_, EnumDirection p_douseFire_3_) {
      p_douseFire_2_ = p_douseFire_2_.shift(p_douseFire_3_);
      if(this.getType(p_douseFire_2_).getBlock() == Blocks.FIRE) {
         this.a(p_douseFire_1_, 1004, p_douseFire_2_, 0);
         this.setAir(p_douseFire_2_);
         return true;
      } else {
         return false;
      }
   }

   public TileEntity getTileEntity(BlockPosition p_getTileEntity_1_) {
      if(!this.isValidLocation(p_getTileEntity_1_)) {
         return null;
      } else if(this.capturedTileEntities.containsKey(p_getTileEntity_1_)) {
         return (TileEntity)this.capturedTileEntities.get(p_getTileEntity_1_);
      } else {
         TileEntity tileentity = null;
         if(this.M) {
            for(int i = 0; i < this.b.size(); ++i) {
               TileEntity tileentity1 = (TileEntity)this.b.get(i);
               if(!tileentity1.x() && tileentity1.getPosition().equals(p_getTileEntity_1_)) {
                  tileentity = tileentity1;
                  break;
               }
            }
         }

         if(tileentity == null) {
            tileentity = this.getChunkAtWorldCoords(p_getTileEntity_1_).a(p_getTileEntity_1_, Chunk.EnumTileEntityState.IMMEDIATE);
         }

         if(tileentity == null) {
            for(int j = 0; j < this.b.size(); ++j) {
               TileEntity tileentity2 = (TileEntity)this.b.get(j);
               if(!tileentity2.x() && tileentity2.getPosition().equals(p_getTileEntity_1_)) {
                  tileentity = tileentity2;
                  break;
               }
            }
         }

         return tileentity;
      }
   }

   public void setTileEntity(BlockPosition p_setTileEntity_1_, TileEntity p_setTileEntity_2_) {
      if(p_setTileEntity_2_ != null && !p_setTileEntity_2_.x()) {
         if(this.captureBlockStates) {
            p_setTileEntity_2_.a(this);
            p_setTileEntity_2_.a(p_setTileEntity_1_);
            this.capturedTileEntities.put(p_setTileEntity_1_, p_setTileEntity_2_);
            return;
         }

         if(this.M) {
            p_setTileEntity_2_.a(p_setTileEntity_1_);
            Iterator iterator = this.b.iterator();

            while(iterator.hasNext()) {
               TileEntity tileentity = (TileEntity)iterator.next();
               if(tileentity.getPosition().equals(p_setTileEntity_1_)) {
                  tileentity.y();
                  iterator.remove();
               }
            }

            p_setTileEntity_2_.a(this);
            this.b.add(p_setTileEntity_2_);
         } else {
            this.a(p_setTileEntity_2_);
            this.getChunkAtWorldCoords(p_setTileEntity_1_).a(p_setTileEntity_1_, p_setTileEntity_2_);
         }
      }

   }

   public void t(BlockPosition p_t_1_) {
      TileEntity tileentity = this.getTileEntity(p_t_1_);
      if(tileentity != null && this.M) {
         tileentity.y();
         this.b.remove(tileentity);
      } else {
         if(tileentity != null) {
            this.b.remove(tileentity);
            this.h.remove(tileentity);
            this.tileEntityList.remove(tileentity);
         }

         this.getChunkAtWorldCoords(p_t_1_).e(p_t_1_);
      }

   }

   public void b(TileEntity p_b_1_) {
      this.c.add(p_b_1_);
   }

   public boolean u(BlockPosition p_u_1_) {
      IBlockData iblockdata = this.getType(p_u_1_);
      AxisAlignedBB axisalignedbb = iblockdata.getBlock().a(this, p_u_1_, iblockdata);
      return axisalignedbb != null && axisalignedbb.a() >= 1.0D;
   }

   public static boolean a(IBlockAccess p_a_0_, BlockPosition p_a_1_) {
      IBlockData iblockdata = p_a_0_.getType(p_a_1_);
      Block block = iblockdata.getBlock();
      return block.getMaterial().k() && block.d()?true:(block instanceof BlockStairs?iblockdata.get(BlockStairs.HALF) == BlockStairs.EnumHalf.TOP:(block instanceof BlockStepAbstract?iblockdata.get(BlockStepAbstract.HALF) == BlockStepAbstract.EnumSlabHalf.TOP:(block instanceof BlockHopper?true:(block instanceof BlockSnow?((Integer)iblockdata.get(BlockSnow.LAYERS)).intValue() == 7:false))));
   }

   public boolean d(BlockPosition p_d_1_, boolean p_d_2_) {
      if(!this.isValidLocation(p_d_1_)) {
         return p_d_2_;
      } else {
         Chunk chunk = this.chunkProvider.getChunkAt(p_d_1_);
         if(chunk.isEmpty()) {
            return p_d_2_;
         } else {
            Block block = this.getType(p_d_1_).getBlock();
            return block.getMaterial().k() && block.d();
         }
      }
   }

   public void B() {
      int i = this.a(1.0F);
      if(i != this.I) {
         this.I = i;
      }

   }

   public void setSpawnFlags(boolean p_setSpawnFlags_1_, boolean p_setSpawnFlags_2_) {
      this.allowMonsters = p_setSpawnFlags_1_;
      this.allowAnimals = p_setSpawnFlags_2_;
   }

   public void doTick() {
      this.p();
   }

   protected void C() {
      if(this.worldData.hasStorm()) {
         this.p = 1.0F;
         if(this.worldData.isThundering()) {
            this.r = 1.0F;
         }
      }

   }

   protected void p() {
      if(!this.worldProvider.o() && !this.isClientSide) {
         int i = this.worldData.A();
         if(i > 0) {
            --i;
            this.worldData.i(i);
            this.worldData.setThunderDuration(this.worldData.isThundering()?1:2);
            this.worldData.setWeatherDuration(this.worldData.hasStorm()?1:2);
         }

         int j = this.worldData.getThunderDuration();
         if(j <= 0) {
            if(this.worldData.isThundering()) {
               this.worldData.setThunderDuration(this.random.nextInt(12000) + 3600);
            } else {
               this.worldData.setThunderDuration(this.random.nextInt(168000) + 12000);
            }
         } else {
            --j;
            this.worldData.setThunderDuration(j);
            if(j <= 0) {
               this.worldData.setThundering(!this.worldData.isThundering());
            }
         }

         this.q = this.r;
         if(this.worldData.isThundering()) {
            this.r = (float)((double)this.r + 0.01D);
         } else {
            this.r = (float)((double)this.r - 0.01D);
         }

         this.r = MathHelper.a(this.r, 0.0F, 1.0F);
         int k = this.worldData.getWeatherDuration();
         if(k <= 0) {
            if(this.worldData.hasStorm()) {
               this.worldData.setWeatherDuration(this.random.nextInt(12000) + 12000);
            } else {
               this.worldData.setWeatherDuration(this.random.nextInt(168000) + 12000);
            }
         } else {
            --k;
            this.worldData.setWeatherDuration(k);
            if(k <= 0) {
               this.worldData.setStorm(!this.worldData.hasStorm());
            }
         }

         this.o = this.p;
         if(this.worldData.hasStorm()) {
            this.p = (float)((double)this.p + 0.01D);
         } else {
            this.p = (float)((double)this.p - 0.01D);
         }

         this.p = MathHelper.a(this.p, 0.0F, 1.0F);

         for(int l = 0; l < this.players.size(); ++l) {
            if(((EntityPlayer)this.players.get(l)).world == this) {
               ((EntityPlayer)this.players.get(l)).tickWeather();
            }
         }
      }

   }

   protected void D() {
      this.methodProfiler.a("buildList");
      int i = this.spigotConfig.chunksPerTick;
      if(i > 0) {
         int j = Math.min(200, Math.max(1, (int)((double)(i - this.players.size()) / (double)this.players.size() + 0.5D)));
         int k = 3 + j / 30;
         k = k > this.chunkTickRadius?this.chunkTickRadius:k;
         this.growthOdds = this.modifiedOdds = Math.max(35.0F, Math.min(100.0F, (float)(j + 1) * 100.0F / 15.0F));

         for(int l = 0; l < this.players.size(); ++l) {
            EntityHuman entityhuman = (EntityHuman)this.players.get(l);
            int i1 = MathHelper.floor(entityhuman.locX / 16.0D);
            int j1 = MathHelper.floor(entityhuman.locZ / 16.0D);
            int k1 = this.q();
            long l1 = chunkToKey(i1, j1);
            int i2 = Math.max(0, this.chunkTickList.get(l1));
            this.chunkTickList.put(l1, (short)(i2 + 1));

            for(int j2 = 0; j2 < j; ++j2) {
               int k2 = (this.random.nextBoolean()?1:-1) * this.random.nextInt(k);
               int l2 = (this.random.nextBoolean()?1:-1) * this.random.nextInt(k);
               long i3 = chunkToKey(k2 + i1, l2 + j1);
               if(!this.chunkTickList.contains(i3) && this.chunkProvider.isChunkLoaded(k2 + i1, l2 + j1)) {
                  this.chunkTickList.put(i3, (short)-1);
               }
            }
         }
      }

      this.methodProfiler.b();
      if(this.L > 0) {
         --this.L;
      }

      this.methodProfiler.a("playerCheckLight");
      if(this.spigotConfig.randomLightUpdates && !this.players.isEmpty()) {
         int j3 = this.random.nextInt(this.players.size());
         EntityHuman entityhuman1 = (EntityHuman)this.players.get(j3);
         int k3 = MathHelper.floor(entityhuman1.locX) + this.random.nextInt(11) - 5;
         int l3 = MathHelper.floor(entityhuman1.locY) + this.random.nextInt(11) - 5;
         int i4 = MathHelper.floor(entityhuman1.locZ) + this.random.nextInt(11) - 5;
         this.x(new BlockPosition(k3, l3, i4));
      }

      this.methodProfiler.b();
   }

   protected abstract int q();

   protected void a(int p_a_1_, int p_a_2_, Chunk p_a_3_) {
      this.methodProfiler.c("moodSound");
      if(this.L == 0 && !this.isClientSide) {
         this.m = this.m * 3 + 1013904223;
         int i = this.m >> 2;
         int j = i & 15;
         int k = i >> 8 & 15;
         int l = i >> 16 & 255;
         BlockPosition blockposition = new BlockPosition(j, l, k);
         Block block = p_a_3_.getType(blockposition);
         j = j + p_a_1_;
         k = k + p_a_2_;
         if(block.getMaterial() == Material.AIR && this.k(blockposition) <= this.random.nextInt(8) && this.b(EnumSkyBlock.SKY, blockposition) <= 0) {
            EntityHuman entityhuman = this.findNearbyPlayer((double)j + 0.5D, (double)l + 0.5D, (double)k + 0.5D, 8.0D);
            if(entityhuman != null && entityhuman.e((double)j + 0.5D, (double)l + 0.5D, (double)k + 0.5D) > 4.0D) {
               this.makeSound((double)j + 0.5D, (double)l + 0.5D, (double)k + 0.5D, "ambient.cave.cave", 0.7F, 0.8F + this.random.nextFloat() * 0.2F);
               this.L = this.random.nextInt(12000) + 6000;
            }
         }
      }

      this.methodProfiler.c("checkLight");
      p_a_3_.m();
   }

   protected void h() {
      this.D();
   }

   public void a(Block p_a_1_, BlockPosition p_a_2_, Random p_a_3_) {
      this.e = true;
      p_a_1_.b(this, p_a_2_, this.getType(p_a_2_), p_a_3_);
      this.e = false;
   }

   public boolean v(BlockPosition p_v_1_) {
      return this.e(p_v_1_, false);
   }

   public boolean w(BlockPosition p_w_1_) {
      return this.e(p_w_1_, true);
   }

   public boolean e(BlockPosition p_e_1_, boolean p_e_2_) {
      BiomeBase biomebase = this.getBiome(p_e_1_);
      float f = biomebase.a(p_e_1_);
      if(f > 0.15F) {
         return false;
      } else {
         if(p_e_1_.getY() >= 0 && p_e_1_.getY() < 256 && this.b(EnumSkyBlock.BLOCK, p_e_1_) < 10) {
            IBlockData iblockdata = this.getType(p_e_1_);
            Block block = iblockdata.getBlock();
            if((block == Blocks.WATER || block == Blocks.FLOWING_WATER) && ((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0) {
               if(!p_e_2_) {
                  return true;
               }

               boolean flag = this.F(p_e_1_.west()) && this.F(p_e_1_.east()) && this.F(p_e_1_.north()) && this.F(p_e_1_.south());
               if(!flag) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean F(BlockPosition p_F_1_) {
      return this.getType(p_F_1_).getBlock().getMaterial() == Material.WATER;
   }

   public boolean f(BlockPosition p_f_1_, boolean p_f_2_) {
      BiomeBase biomebase = this.getBiome(p_f_1_);
      float f = biomebase.a(p_f_1_);
      if(f > 0.15F) {
         return false;
      } else if(!p_f_2_) {
         return true;
      } else {
         if(p_f_1_.getY() >= 0 && p_f_1_.getY() < 256 && this.b(EnumSkyBlock.BLOCK, p_f_1_) < 10) {
            Block block = this.getType(p_f_1_).getBlock();
            if(block.getMaterial() == Material.AIR && Blocks.SNOW_LAYER.canPlace(this, p_f_1_)) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean x(BlockPosition p_x_1_) {
      boolean flag = false;
      if(!this.worldProvider.o()) {
         flag |= this.c(EnumSkyBlock.SKY, p_x_1_);
      }

      flag = flag | this.c(EnumSkyBlock.BLOCK, p_x_1_);
      return flag;
   }

   private int a(BlockPosition p_a_1_, EnumSkyBlock p_a_2_) {
      if(p_a_2_ == EnumSkyBlock.SKY && this.i(p_a_1_)) {
         return 15;
      } else {
         Block block = this.getType(p_a_1_).getBlock();
         int i = p_a_2_ == EnumSkyBlock.SKY?0:block.r();
         int j = block.p();
         if(j >= 15 && block.r() > 0) {
            j = 1;
         }

         if(j < 1) {
            j = 1;
         }

         if(j >= 15) {
            return 0;
         } else if(i >= 14) {
            return i;
         } else {
            for(EnumDirection enumdirection : EnumDirection.values()) {
               BlockPosition blockposition = p_a_1_.shift(enumdirection);
               int k = this.b(p_a_2_, blockposition) - j;
               if(k > i) {
                  i = k;
               }

               if(i >= 14) {
                  return i;
               }
            }

            return i;
         }
      }
   }

   public boolean c(EnumSkyBlock p_c_1_, BlockPosition p_c_2_) {
      Chunk chunk = this.getChunkIfLoaded(p_c_2_.getX() >> 4, p_c_2_.getZ() >> 4);
      if(chunk != null && chunk.areNeighborsLoaded(1)) {
         int i = 0;
         int j = 0;
         this.methodProfiler.a("getBrightness");
         int k = this.b(p_c_1_, p_c_2_);
         int l = this.a(p_c_2_, p_c_1_);
         int i1 = p_c_2_.getX();
         int j1 = p_c_2_.getY();
         int k1 = p_c_2_.getZ();
         if(l > k) {
            this.H[j++] = 133152;
         } else if(l < k) {
            this.H[j++] = 133152 | k << 18;

            while(i < j) {
               int l1 = this.H[i++];
               int i2 = (l1 & 63) - 32 + i1;
               int j2 = (l1 >> 6 & 63) - 32 + j1;
               int k2 = (l1 >> 12 & 63) - 32 + k1;
               int l2 = l1 >> 18 & 15;
               BlockPosition blockposition = new BlockPosition(i2, j2, k2);
               int i3 = this.b(p_c_1_, blockposition);
               if(i3 == l2) {
                  this.a((EnumSkyBlock)p_c_1_, (BlockPosition)blockposition, 0);
                  if(l2 > 0) {
                     int j3 = MathHelper.a(i2 - i1);
                     int k3 = MathHelper.a(j2 - j1);
                     int l3 = MathHelper.a(k2 - k1);
                     if(j3 + k3 + l3 < 17) {
                        BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

                        for(EnumDirection enumdirection : EnumDirection.values()) {
                           int i4 = i2 + enumdirection.getAdjacentX();
                           int j4 = j2 + enumdirection.getAdjacentY();
                           int k4 = k2 + enumdirection.getAdjacentZ();
                           blockposition$mutableblockposition.c(i4, j4, k4);
                           int l4 = Math.max(1, this.getType(blockposition$mutableblockposition).getBlock().p());
                           i3 = this.b((EnumSkyBlock)p_c_1_, (BlockPosition)blockposition$mutableblockposition);
                           if(i3 == l2 - l4 && j < this.H.length) {
                              this.H[j++] = i4 - i1 + 32 | j4 - j1 + 32 << 6 | k4 - k1 + 32 << 12 | l2 - l4 << 18;
                           }
                        }
                     }
                  }
               }
            }

            i = 0;
         }

         this.methodProfiler.b();
         this.methodProfiler.a("checkedPosition < toCheckCount");

         while(i < j) {
            int i5 = this.H[i++];
            int j5 = (i5 & 63) - 32 + i1;
            int k5 = (i5 >> 6 & 63) - 32 + j1;
            int l5 = (i5 >> 12 & 63) - 32 + k1;
            BlockPosition blockposition1 = new BlockPosition(j5, k5, l5);
            int i6 = this.b(p_c_1_, blockposition1);
            int j6 = this.a(blockposition1, p_c_1_);
            if(j6 != i6) {
               this.a(p_c_1_, blockposition1, j6);
               if(j6 > i6) {
                  int k6 = Math.abs(j5 - i1);
                  int l6 = Math.abs(k5 - j1);
                  int i7 = Math.abs(l5 - k1);
                  boolean flag = j < this.H.length - 6;
                  if(k6 + l6 + i7 < 17 && flag) {
                     if(this.b(p_c_1_, blockposition1.west()) < j6) {
                        this.H[j++] = j5 - 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                     }

                     if(this.b(p_c_1_, blockposition1.east()) < j6) {
                        this.H[j++] = j5 + 1 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                     }

                     if(this.b(p_c_1_, blockposition1.down()) < j6) {
                        this.H[j++] = j5 - i1 + 32 + (k5 - 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                     }

                     if(this.b(p_c_1_, blockposition1.up()) < j6) {
                        this.H[j++] = j5 - i1 + 32 + (k5 + 1 - j1 + 32 << 6) + (l5 - k1 + 32 << 12);
                     }

                     if(this.b(p_c_1_, blockposition1.north()) < j6) {
                        this.H[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 - 1 - k1 + 32 << 12);
                     }

                     if(this.b(p_c_1_, blockposition1.south()) < j6) {
                        this.H[j++] = j5 - i1 + 32 + (k5 - j1 + 32 << 6) + (l5 + 1 - k1 + 32 << 12);
                     }
                  }
               }
            }
         }

         this.methodProfiler.b();
         return true;
      } else {
         return false;
      }
   }

   public boolean a(boolean p_a_1_) {
      return false;
   }

   public List<NextTickListEntry> a(Chunk p_a_1_, boolean p_a_2_) {
      return null;
   }

   public List<NextTickListEntry> a(StructureBoundingBox p_a_1_, boolean p_a_2_) {
      return null;
   }

   public List<Entity> getEntities(Entity p_getEntities_1_, AxisAlignedBB p_getEntities_2_) {
      return this.a(p_getEntities_1_, p_getEntities_2_, IEntitySelector.d);
   }

   public List<Entity> a(Entity p_a_1_, AxisAlignedBB p_a_2_, Predicate<? super Entity> p_a_3_) {
      ArrayList arraylist = Lists.newArrayList();
      int i = MathHelper.floor((p_a_2_.a - 2.0D) / 16.0D);
      int j = MathHelper.floor((p_a_2_.d + 2.0D) / 16.0D);
      int k = MathHelper.floor((p_a_2_.c - 2.0D) / 16.0D);
      int l = MathHelper.floor((p_a_2_.f + 2.0D) / 16.0D);

      for(int i1 = i; i1 <= j; ++i1) {
         for(int j1 = k; j1 <= l; ++j1) {
            if(this.isChunkLoaded(i1, j1, true)) {
               this.getChunkAt(i1, j1).a(p_a_1_, p_a_2_, arraylist, p_a_3_);
            }
         }
      }

      return arraylist;
   }

   public <T extends Entity> List<T> a(Class<? extends T> p_a_1_, Predicate<? super T> p_a_2_) {
      ArrayList arraylist = Lists.newArrayList();

      for(Entity entity : this.entityList) {
         if(p_a_1_.isAssignableFrom(entity.getClass()) && p_a_2_.apply(entity)) {
            arraylist.add(entity);
         }
      }

      return arraylist;
   }

   public <T extends Entity> List<T> b(Class<? extends T> p_b_1_, Predicate<? super T> p_b_2_) {
      ArrayList arraylist = Lists.newArrayList();

      for(Entity entity : this.players) {
         if(p_b_1_.isAssignableFrom(entity.getClass()) && p_b_2_.apply(entity)) {
            arraylist.add(entity);
         }
      }

      return arraylist;
   }

   public <T extends Entity> List<T> a(Class<? extends T> p_a_1_, AxisAlignedBB p_a_2_) {
      return this.a(p_a_1_, p_a_2_, IEntitySelector.d);
   }

   public <T extends Entity> List<T> a(Class<? extends T> p_a_1_, AxisAlignedBB p_a_2_, Predicate<? super T> p_a_3_) {
      int i = MathHelper.floor((p_a_2_.a - 2.0D) / 16.0D);
      int j = MathHelper.floor((p_a_2_.d + 2.0D) / 16.0D);
      int k = MathHelper.floor((p_a_2_.c - 2.0D) / 16.0D);
      int l = MathHelper.floor((p_a_2_.f + 2.0D) / 16.0D);
      ArrayList arraylist = Lists.newArrayList();

      for(int i1 = i; i1 <= j; ++i1) {
         for(int j1 = k; j1 <= l; ++j1) {
            if(this.isChunkLoaded(i1, j1, true)) {
               this.getChunkAt(i1, j1).a((Class)p_a_1_, p_a_2_, arraylist, p_a_3_);
            }
         }
      }

      return arraylist;
   }

   public <T extends Entity> T a(Class<? extends T> p_a_1_, AxisAlignedBB p_a_2_, T p_a_3_) {
      List list = this.a(p_a_1_, p_a_2_);
      Entity entity = null;
      double d0 = Double.MAX_VALUE;

      for(int i = 0; i < list.size(); ++i) {
         Entity entity1 = (Entity)list.get(i);
         if(entity1 != p_a_3_ && IEntitySelector.d.apply(entity1)) {
            double d1 = p_a_3_.h(entity1);
            if(d1 <= d0) {
               entity = entity1;
               d0 = d1;
            }
         }
      }

      return (T)entity;
   }

   public Entity a(int p_a_1_) {
      return (Entity)this.entitiesById.get(p_a_1_);
   }

   public void b(BlockPosition p_b_1_, TileEntity p_b_2_) {
      if(this.isLoaded(p_b_1_)) {
         this.getChunkAtWorldCoords(p_b_1_).e();
      }

   }

   public int a(Class<?> p_a_1_) {
      int i = 0;
      Iterator iterator = this.entityList.iterator();

      while(true) {
         Entity entity;
         while(true) {
            if(!iterator.hasNext()) {
               return i;
            }

            entity = (Entity)iterator.next();
            if(!(entity instanceof EntityInsentient)) {
               break;
            }

            EntityInsentient entityinsentient = (EntityInsentient)entity;
            if(!entityinsentient.isTypeNotPersistent() || !entityinsentient.isPersistent()) {
               break;
            }
         }

         if(p_a_1_.isAssignableFrom(entity.getClass())) {
            ++i;
         }
      }
   }

   public void b(Collection<Entity> p_b_1_) {
      AsyncCatcher.catchOp("entity world add");

      for(Entity entity : p_b_1_) {
         if(entity != null) {
            this.entityList.add(entity);
            this.a(entity);
         }
      }

   }

   public void c(Collection<Entity> p_c_1_) {
      this.g.addAll(p_c_1_);
   }

   public boolean a(Block p_a_1_, BlockPosition p_a_2_, boolean p_a_3_, EnumDirection p_a_4_, Entity p_a_5_, ItemStack p_a_6_) {
      Block block = this.getType(p_a_2_).getBlock();
      AxisAlignedBB axisalignedbb = p_a_3_?null:p_a_1_.a(this, p_a_2_, p_a_1_.getBlockData());
      boolean flag = axisalignedbb != null && !this.a(axisalignedbb, p_a_5_)?false:(block.getMaterial() == Material.ORIENTABLE && p_a_1_ == Blocks.ANVIL?true:block.getMaterial().isReplaceable() && p_a_1_.canPlace(this, p_a_2_, p_a_4_, p_a_6_));
      BlockCanBuildEvent blockcanbuildevent = new BlockCanBuildEvent(this.getWorld().getBlockAt(p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ()), CraftMagicNumbers.getId(p_a_1_), flag);
      this.getServer().getPluginManager().callEvent(blockcanbuildevent);
      return blockcanbuildevent.isBuildable();
   }

   public int F() {
      return this.a;
   }

   public void b(int p_b_1_) {
      this.a = p_b_1_;
   }

   public int getBlockPower(BlockPosition p_getBlockPower_1_, EnumDirection p_getBlockPower_2_) {
      IBlockData iblockdata = this.getType(p_getBlockPower_1_);
      return iblockdata.getBlock().b((IBlockAccess)this, p_getBlockPower_1_, iblockdata, (EnumDirection)p_getBlockPower_2_);
   }

   public WorldType G() {
      return this.worldData.getType();
   }

   public int getBlockPower(BlockPosition p_getBlockPower_1_) {
      byte b0 = 0;
      int i = Math.max(b0, this.getBlockPower(p_getBlockPower_1_.down(), EnumDirection.DOWN));
      if(i >= 15) {
         return i;
      } else {
         i = Math.max(i, this.getBlockPower(p_getBlockPower_1_.up(), EnumDirection.UP));
         if(i >= 15) {
            return i;
         } else {
            i = Math.max(i, this.getBlockPower(p_getBlockPower_1_.north(), EnumDirection.NORTH));
            if(i >= 15) {
               return i;
            } else {
               i = Math.max(i, this.getBlockPower(p_getBlockPower_1_.south(), EnumDirection.SOUTH));
               if(i >= 15) {
                  return i;
               } else {
                  i = Math.max(i, this.getBlockPower(p_getBlockPower_1_.west(), EnumDirection.WEST));
                  if(i >= 15) {
                     return i;
                  } else {
                     i = Math.max(i, this.getBlockPower(p_getBlockPower_1_.east(), EnumDirection.EAST));
                     return i >= 15?i:i;
                  }
               }
            }
         }
      }
   }

   public boolean isBlockFacePowered(BlockPosition p_isBlockFacePowered_1_, EnumDirection p_isBlockFacePowered_2_) {
      return this.getBlockFacePower(p_isBlockFacePowered_1_, p_isBlockFacePowered_2_) > 0;
   }

   public int getBlockFacePower(BlockPosition p_getBlockFacePower_1_, EnumDirection p_getBlockFacePower_2_) {
      IBlockData iblockdata = this.getType(p_getBlockFacePower_1_);
      Block block = iblockdata.getBlock();
      return block.isOccluding()?this.getBlockPower(p_getBlockFacePower_1_):block.a((IBlockAccess)this, p_getBlockFacePower_1_, (IBlockData)iblockdata, (EnumDirection)p_getBlockFacePower_2_);
   }

   public boolean isBlockIndirectlyPowered(BlockPosition p_isBlockIndirectlyPowered_1_) {
      return this.getBlockFacePower(p_isBlockIndirectlyPowered_1_.down(), EnumDirection.DOWN) > 0?true:(this.getBlockFacePower(p_isBlockIndirectlyPowered_1_.up(), EnumDirection.UP) > 0?true:(this.getBlockFacePower(p_isBlockIndirectlyPowered_1_.north(), EnumDirection.NORTH) > 0?true:(this.getBlockFacePower(p_isBlockIndirectlyPowered_1_.south(), EnumDirection.SOUTH) > 0?true:(this.getBlockFacePower(p_isBlockIndirectlyPowered_1_.west(), EnumDirection.WEST) > 0?true:this.getBlockFacePower(p_isBlockIndirectlyPowered_1_.east(), EnumDirection.EAST) > 0))));
   }

   public int A(BlockPosition p_A_1_) {
      int i = 0;

      for(EnumDirection enumdirection : EnumDirection.values()) {
         int j = this.getBlockFacePower(p_A_1_.shift(enumdirection), enumdirection);
         if(j >= 15) {
            return 15;
         }

         if(j > i) {
            i = j;
         }
      }

      return i;
   }

   public EntityHuman findNearbyPlayer(Entity p_findNearbyPlayer_1_, double p_findNearbyPlayer_2_) {
      return this.findNearbyPlayer(p_findNearbyPlayer_1_.locX, p_findNearbyPlayer_1_.locY, p_findNearbyPlayer_1_.locZ, p_findNearbyPlayer_2_);
   }

   public EntityHuman findNearbyPlayer(double p_findNearbyPlayer_1_, double p_findNearbyPlayer_3_, double p_findNearbyPlayer_5_, double p_findNearbyPlayer_7_) {
      double d0 = -1.0D;
      EntityHuman entityhuman = null;

      for(int i = 0; i < this.players.size(); ++i) {
         EntityHuman entityhuman1 = (EntityHuman)this.players.get(i);
         if(entityhuman1 != null && !entityhuman1.dead && IEntitySelector.d.apply(entityhuman1)) {
            double d1 = entityhuman1.e(p_findNearbyPlayer_1_, p_findNearbyPlayer_3_, p_findNearbyPlayer_5_);
            if((p_findNearbyPlayer_7_ < 0.0D || d1 < p_findNearbyPlayer_7_ * p_findNearbyPlayer_7_) && (d0 == -1.0D || d1 < d0)) {
               d0 = d1;
               entityhuman = entityhuman1;
            }
         }
      }

      return entityhuman;
   }

   public boolean isPlayerNearby(double p_isPlayerNearby_1_, double p_isPlayerNearby_3_, double p_isPlayerNearby_5_, double p_isPlayerNearby_7_) {
      for(int i = 0; i < this.players.size(); ++i) {
         EntityHuman entityhuman = (EntityHuman)this.players.get(i);
         if(IEntitySelector.d.apply(entityhuman)) {
            double d0 = entityhuman.e(p_isPlayerNearby_1_, p_isPlayerNearby_3_, p_isPlayerNearby_5_);
            if(p_isPlayerNearby_7_ < 0.0D || d0 < p_isPlayerNearby_7_ * p_isPlayerNearby_7_) {
               return true;
            }
         }
      }

      return false;
   }

   public EntityHuman a(String p_a_1_) {
      for(int i = 0; i < this.players.size(); ++i) {
         EntityHuman entityhuman = (EntityHuman)this.players.get(i);
         if(p_a_1_.equals(entityhuman.getName())) {
            return entityhuman;
         }
      }

      return null;
   }

   public EntityHuman b(UUID p_b_1_) {
      for(int i = 0; i < this.players.size(); ++i) {
         EntityHuman entityhuman = (EntityHuman)this.players.get(i);
         if(p_b_1_.equals(entityhuman.getUniqueID())) {
            return entityhuman;
         }
      }

      return null;
   }

   public void checkSession() throws ExceptionWorldConflict {
      this.dataManager.checkSession();
   }

   public long getSeed() {
      return this.worldData.getSeed();
   }

   public long getTime() {
      return this.worldData.getTime();
   }

   public long getDayTime() {
      return this.worldData.getDayTime();
   }

   public void setDayTime(long p_setDayTime_1_) {
      this.worldData.setDayTime(p_setDayTime_1_);
   }

   public BlockPosition getSpawn() {
      BlockPosition blockposition = new BlockPosition(this.worldData.c(), this.worldData.d(), this.worldData.e());
      if(!this.getWorldBorder().a(blockposition)) {
         blockposition = this.getHighestBlockYAt(new BlockPosition(this.getWorldBorder().getCenterX(), 0.0D, this.getWorldBorder().getCenterZ()));
      }

      return blockposition;
   }

   public void B(BlockPosition p_B_1_) {
      this.worldData.setSpawn(p_B_1_);
   }

   public boolean a(EntityHuman p_a_1_, BlockPosition p_a_2_) {
      return true;
   }

   public void broadcastEntityEffect(Entity p_broadcastEntityEffect_1_, byte p_broadcastEntityEffect_2_) {
   }

   public IChunkProvider N() {
      return this.chunkProvider;
   }

   public void playBlockAction(BlockPosition p_playBlockAction_1_, Block p_playBlockAction_2_, int p_playBlockAction_3_, int p_playBlockAction_4_) {
      p_playBlockAction_2_.a(this, p_playBlockAction_1_, this.getType(p_playBlockAction_1_), p_playBlockAction_3_, p_playBlockAction_4_);
   }

   public IDataManager getDataManager() {
      return this.dataManager;
   }

   public WorldData getWorldData() {
      return this.worldData;
   }

   public GameRules getGameRules() {
      return this.worldData.x();
   }

   public void everyoneSleeping() {
   }

   public void checkSleepStatus() {
      if(!this.isClientSide) {
         this.everyoneSleeping();
      }

   }

   public float h(float p_h_1_) {
      return (this.q + (this.r - this.q) * p_h_1_) * this.j(p_h_1_);
   }

   public float j(float p_j_1_) {
      return this.o + (this.p - this.o) * p_j_1_;
   }

   public boolean R() {
      return (double)this.h(1.0F) > 0.9D;
   }

   public boolean S() {
      return (double)this.j(1.0F) > 0.2D;
   }

   public boolean isRainingAt(BlockPosition p_isRainingAt_1_) {
      if(!this.S()) {
         return false;
      } else if(!this.i(p_isRainingAt_1_)) {
         return false;
      } else if(this.q(p_isRainingAt_1_).getY() > p_isRainingAt_1_.getY()) {
         return false;
      } else {
         BiomeBase biomebase = this.getBiome(p_isRainingAt_1_);
         return biomebase.d()?false:(this.f(p_isRainingAt_1_, false)?false:biomebase.e());
      }
   }

   public boolean D(BlockPosition p_D_1_) {
      BiomeBase biomebase = this.getBiome(p_D_1_);
      return biomebase.f();
   }

   public PersistentCollection T() {
      return this.worldMaps;
   }

   public void a(String p_a_1_, PersistentBase p_a_2_) {
      this.worldMaps.a(p_a_1_, p_a_2_);
   }

   public PersistentBase a(Class<? extends PersistentBase> p_a_1_, String p_a_2_) {
      return this.worldMaps.get(p_a_1_, p_a_2_);
   }

   public int b(String p_b_1_) {
      return this.worldMaps.a(p_b_1_);
   }

   public void a(int p_a_1_, BlockPosition p_a_2_, int p_a_3_) {
      for(int i = 0; i < this.u.size(); ++i) {
         ((IWorldAccess)this.u.get(i)).a(p_a_1_, p_a_2_, p_a_3_);
      }

   }

   public void triggerEffect(int p_triggerEffect_1_, BlockPosition p_triggerEffect_2_, int p_triggerEffect_3_) {
      this.a((EntityHuman)null, p_triggerEffect_1_, p_triggerEffect_2_, p_triggerEffect_3_);
   }

   public void a(EntityHuman p_a_1_, int p_a_2_, BlockPosition p_a_3_, int p_a_4_) {
      try {
         for(int i = 0; i < this.u.size(); ++i) {
            ((IWorldAccess)this.u.get(i)).a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
         }

      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.a(throwable, "Playing level event");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Level event being played");
         crashreportsystemdetails.a((String)"Block coordinates", CrashReportSystemDetails.a(p_a_3_));
         crashreportsystemdetails.a((String)"Event source", p_a_1_);
         crashreportsystemdetails.a((String)"Event type", Integer.valueOf(p_a_2_));
         crashreportsystemdetails.a((String)"Event data", Integer.valueOf(p_a_4_));
         throw new ReportedException(crashreport);
      }
   }

   public int getHeight() {
      return 256;
   }

   public int V() {
      return this.worldProvider.o()?128:256;
   }

   public Random a(int p_a_1_, int p_a_2_, int p_a_3_) {
      long i = (long)p_a_1_ * 341873128712L + (long)p_a_2_ * 132897987541L + this.getWorldData().getSeed() + (long)p_a_3_;
      this.random.setSeed(i);
      return this.random;
   }

   public BlockPosition a(String p_a_1_, BlockPosition p_a_2_) {
      return this.N().findNearestMapFeature(this, p_a_1_, p_a_2_);
   }

   public CrashReportSystemDetails a(CrashReport p_a_1_) {
      CrashReportSystemDetails crashreportsystemdetails = p_a_1_.a("Affected level", 1);
      crashreportsystemdetails.a((String)"Level name", this.worldData == null?"????":this.worldData.getName());
      crashreportsystemdetails.a("All players", new Callable() {
         public String a() {
            return World.this.players.size() + " total; " + World.this.players.toString();
         }

         public Object call() throws Exception {
            return this.a();
         }
      });
      crashreportsystemdetails.a("Chunk stats", new Callable() {
         public String a() {
            return World.this.chunkProvider.getName();
         }

         public Object call() throws Exception {
            return this.a();
         }
      });

      try {
         this.worldData.a(crashreportsystemdetails);
      } catch (Throwable throwable) {
         crashreportsystemdetails.a("Level Data Unobtainable", throwable);
      }

      return crashreportsystemdetails;
   }

   public void c(int p_c_1_, BlockPosition p_c_2_, int p_c_3_) {
      for(int i = 0; i < this.u.size(); ++i) {
         IWorldAccess iworldaccess = (IWorldAccess)this.u.get(i);
         iworldaccess.b(p_c_1_, p_c_2_, p_c_3_);
      }

   }

   public Calendar Y() {
      if(this.getTime() % 600L == 0L) {
         this.K.setTimeInMillis(MinecraftServer.az());
      }

      return this.K;
   }

   public Scoreboard getScoreboard() {
      return this.scoreboard;
   }

   public void updateAdjacentComparators(BlockPosition p_updateAdjacentComparators_1_, Block p_updateAdjacentComparators_2_) {
      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
         BlockPosition blockposition = p_updateAdjacentComparators_1_.shift(enumdirection);
         if(this.isLoaded(blockposition)) {
            IBlockData iblockdata = this.getType(blockposition);
            if(Blocks.UNPOWERED_COMPARATOR.e(iblockdata.getBlock())) {
               iblockdata.getBlock().doPhysics(this, blockposition, iblockdata, p_updateAdjacentComparators_2_);
            } else if(iblockdata.getBlock().isOccluding()) {
               blockposition = blockposition.shift(enumdirection);
               iblockdata = this.getType(blockposition);
               if(Blocks.UNPOWERED_COMPARATOR.e(iblockdata.getBlock())) {
                  iblockdata.getBlock().doPhysics(this, blockposition, iblockdata, p_updateAdjacentComparators_2_);
               }
            }
         }
      }

   }

   public DifficultyDamageScaler E(BlockPosition p_E_1_) {
      long i = 0L;
      float f = 0.0F;
      if(this.isLoaded(p_E_1_)) {
         f = this.y();
         i = this.getChunkAtWorldCoords(p_E_1_).w();
      }

      return new DifficultyDamageScaler(this.getDifficulty(), this.getDayTime(), i, f);
   }

   public EnumDifficulty getDifficulty() {
      return this.getWorldData().getDifficulty();
   }

   public int ab() {
      return this.I;
   }

   public void c(int p_c_1_) {
      this.I = p_c_1_;
   }

   public void d(int p_d_1_) {
      this.J = p_d_1_;
   }

   public boolean ad() {
      return this.isLoading;
   }

   public PersistentVillage ae() {
      return this.villages;
   }

   public WorldBorder getWorldBorder() {
      return this.N;
   }

   public boolean c(int p_c_1_, int p_c_2_) {
      BlockPosition blockposition = this.getSpawn();
      int i = p_c_1_ * 16 + 8 - blockposition.getX();
      int j = p_c_2_ * 16 + 8 - blockposition.getZ();
      short short1 = 128;
      return i >= -short1 && i <= short1 && j >= -short1 && j <= short1 && this.keepSpawnInMemory;
   }
}
