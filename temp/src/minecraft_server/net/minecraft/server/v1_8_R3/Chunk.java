package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockContainer;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.ChunkProviderDebug;
import net.minecraft.server.v1_8_R3.ChunkSection;
import net.minecraft.server.v1_8_R3.ChunkSnapshot;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.EmptyChunk;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumCreatureType;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumSkyBlock;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IChunkProvider;
import net.minecraft.server.v1_8_R3.IContainer;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldChunkManager;
import net.minecraft.server.v1_8_R3.WorldType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import org.bukkit.entity.HumanEntity;

public class Chunk {
   private static final Logger c = LogManager.getLogger();
   private final ChunkSection[] sections;
   private final byte[] e;
   private final int[] f;
   private final boolean[] g;
   private boolean h;
   public final World world;
   public final int[] heightMap;
   public final int locX;
   public final int locZ;
   private boolean k;
   public final Map<BlockPosition, TileEntity> tileEntities;
   public final List<Entity>[] entitySlices;
   private boolean done;
   private boolean lit;
   private boolean p;
   private boolean q;
   private boolean r;
   private long lastSaved;
   private int t;
   private long u;
   private int v;
   private ConcurrentLinkedQueue<BlockPosition> w;
   protected TObjectIntHashMap<Class> entityCount;
   private int neighbors;
   public org.bukkit.Chunk bukkitChunk;
   public boolean mustSave;

   public boolean areNeighborsLoaded(int p_areNeighborsLoaded_1_) {
      switch(p_areNeighborsLoaded_1_) {
      case 1:
         if((this.neighbors & 473536) == 473536) {
            return true;
         }

         return false;
      case 2:
         if(this.neighbors == 33554431) {
            return true;
         }

         return false;
      default:
         throw new UnsupportedOperationException(String.valueOf(p_areNeighborsLoaded_1_));
      }
   }

   public void setNeighborLoaded(int p_setNeighborLoaded_1_, int p_setNeighborLoaded_2_) {
      this.neighbors |= 1 << p_setNeighborLoaded_1_ * 5 + 12 + p_setNeighborLoaded_2_;
   }

   public void setNeighborUnloaded(int p_setNeighborUnloaded_1_, int p_setNeighborUnloaded_2_) {
      this.neighbors &= ~(1 << p_setNeighborUnloaded_1_ * 5 + 12 + p_setNeighborUnloaded_2_);
   }

   public Chunk(World p_i481_1_, int p_i481_2_, int p_i481_3_) {
      this.entityCount = new TObjectIntHashMap();
      this.neighbors = 4096;
      this.sections = new ChunkSection[16];
      this.e = new byte[256];
      this.f = new int[256];
      this.g = new boolean[256];
      this.tileEntities = Maps.<BlockPosition, TileEntity>newHashMap();
      this.v = 4096;
      this.w = Queues.<BlockPosition>newConcurrentLinkedQueue();
      this.entitySlices = new List[16];
      this.world = p_i481_1_;
      this.locX = p_i481_2_;
      this.locZ = p_i481_3_;
      this.heightMap = new int[256];

      for(int i = 0; i < this.entitySlices.length; ++i) {
         this.entitySlices[i] = new UnsafeList();
      }

      Arrays.fill(this.f, -999);
      Arrays.fill(this.e, (byte)-1);
      if(!(this instanceof EmptyChunk)) {
         this.bukkitChunk = new CraftChunk(this);
      }

   }

   public Chunk(World p_i482_1_, ChunkSnapshot p_i482_2_, int p_i482_3_, int p_i482_4_) {
      this(p_i482_1_, p_i482_3_, p_i482_4_);
      short short1 = 256;
      boolean flag = !p_i482_1_.worldProvider.o();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            for(int k = 0; k < short1; ++k) {
               int l = i * short1 * 16 | j * short1 | k;
               IBlockData iblockdata = p_i482_2_.a(l);
               if(iblockdata.getBlock().getMaterial() != Material.AIR) {
                  int i1 = k >> 4;
                  if(this.sections[i1] == null) {
                     this.sections[i1] = new ChunkSection(i1 << 4, flag);
                  }

                  this.sections[i1].setType(i, k & 15, j, iblockdata);
               }
            }
         }
      }

   }

   public boolean a(int p_a_1_, int p_a_2_) {
      return p_a_1_ == this.locX && p_a_2_ == this.locZ;
   }

   public int f(BlockPosition p_f_1_) {
      return this.b(p_f_1_.getX() & 15, p_f_1_.getZ() & 15);
   }

   public int b(int p_b_1_, int p_b_2_) {
      return this.heightMap[p_b_2_ << 4 | p_b_1_];
   }

   public int g() {
      for(int i = this.sections.length - 1; i >= 0; --i) {
         if(this.sections[i] != null) {
            return this.sections[i].getYPosition();
         }
      }

      return 0;
   }

   public ChunkSection[] getSections() {
      return this.sections;
   }

   public void initLighting() {
      int i = this.g();
      this.t = Integer.MAX_VALUE;

      for(int j = 0; j < 16; ++j) {
         for(int k = 0; k < 16; ++k) {
            this.f[j + (k << 4)] = -999;

            for(int l = i + 16; l > 0; --l) {
               if(this.e(j, l - 1, k) != 0) {
                  this.heightMap[k << 4 | j] = l;
                  if(l < this.t) {
                     this.t = l;
                  }
                  break;
               }
            }

            if(!this.world.worldProvider.o()) {
               int k1 = 15;
               int i1 = i + 16 - 1;

               while(true) {
                  int j1 = this.e(j, i1, k);
                  if(j1 == 0 && k1 != 15) {
                     j1 = 1;
                  }

                  k1 -= j1;
                  if(k1 > 0) {
                     ChunkSection chunksection = this.sections[i1 >> 4];
                     if(chunksection != null) {
                        chunksection.a(j, i1 & 15, k, k1);
                        this.world.n(new BlockPosition((this.locX << 4) + j, i1, (this.locZ << 4) + k));
                     }
                  }

                  --i1;
                  if(i1 <= 0 || k1 <= 0) {
                     break;
                  }
               }
            }
         }
      }

      this.q = true;
   }

   private void d(int p_d_1_, int p_d_2_) {
      this.g[p_d_1_ + p_d_2_ * 16] = true;
      this.k = true;
   }

   private void h(boolean p_h_1_) {
      this.world.methodProfiler.a("recheckGaps");
      if(this.world.areChunksLoaded(new BlockPosition(this.locX * 16 + 8, 0, this.locZ * 16 + 8), 16)) {
         for(int i = 0; i < 16; ++i) {
            for(int j = 0; j < 16; ++j) {
               if(this.g[i + j * 16]) {
                  this.g[i + j * 16] = false;
                  int k = this.b(i, j);
                  int l = this.locX * 16 + i;
                  int i1 = this.locZ * 16 + j;
                  int j1 = Integer.MAX_VALUE;

                  for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                     j1 = Math.min(j1, this.world.b(l + enumdirection.getAdjacentX(), i1 + enumdirection.getAdjacentZ()));
                  }

                  this.c(l, i1, j1);

                  for(EnumDirection enumdirection1 : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                     this.c(l + enumdirection1.getAdjacentX(), i1 + enumdirection1.getAdjacentZ(), k);
                  }

                  if(p_h_1_) {
                     this.world.methodProfiler.b();
                     return;
                  }
               }
            }
         }

         this.k = false;
      }

      this.world.methodProfiler.b();
   }

   private void c(int p_c_1_, int p_c_2_, int p_c_3_) {
      int i = this.world.getHighestBlockYAt(new BlockPosition(p_c_1_, 0, p_c_2_)).getY();
      if(i > p_c_3_) {
         this.a(p_c_1_, p_c_2_, p_c_3_, i + 1);
      } else if(i < p_c_3_) {
         this.a(p_c_1_, p_c_2_, i, p_c_3_ + 1);
      }

   }

   private void a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      if(p_a_4_ > p_a_3_ && this.world.areChunksLoaded(new BlockPosition(p_a_1_, 0, p_a_2_), 16)) {
         for(int i = p_a_3_; i < p_a_4_; ++i) {
            this.world.c(EnumSkyBlock.SKY, new BlockPosition(p_a_1_, i, p_a_2_));
         }

         this.q = true;
      }

   }

   private void d(int p_d_1_, int p_d_2_, int p_d_3_) {
      int i = this.heightMap[p_d_3_ << 4 | p_d_1_] & 255;
      int j = i;
      if(p_d_2_ > i) {
         j = p_d_2_;
      }

      while(j > 0 && this.e(p_d_1_, j - 1, p_d_3_) == 0) {
         --j;
      }

      if(j != i) {
         this.world.a(p_d_1_ + this.locX * 16, p_d_3_ + this.locZ * 16, j, i);
         this.heightMap[p_d_3_ << 4 | p_d_1_] = j;
         int k = this.locX * 16 + p_d_1_;
         int l = this.locZ * 16 + p_d_3_;
         if(!this.world.worldProvider.o()) {
            if(j < i) {
               for(int i1 = j; i1 < i; ++i1) {
                  ChunkSection chunksection = this.sections[i1 >> 4];
                  if(chunksection != null) {
                     chunksection.a(p_d_1_, i1 & 15, p_d_3_, 15);
                     this.world.n(new BlockPosition((this.locX << 4) + p_d_1_, i1, (this.locZ << 4) + p_d_3_));
                  }
               }
            } else {
               for(int k1 = i; k1 < j; ++k1) {
                  ChunkSection chunksection2 = this.sections[k1 >> 4];
                  if(chunksection2 != null) {
                     chunksection2.a(p_d_1_, k1 & 15, p_d_3_, 0);
                     this.world.n(new BlockPosition((this.locX << 4) + p_d_1_, k1, (this.locZ << 4) + p_d_3_));
                  }
               }
            }

            int l1 = 15;

            while(j > 0 && l1 > 0) {
               --j;
               int j1 = this.e(p_d_1_, j, p_d_3_);
               if(j1 == 0) {
                  j1 = 1;
               }

               l1 -= j1;
               if(l1 < 0) {
                  l1 = 0;
               }

               ChunkSection chunksection1 = this.sections[j >> 4];
               if(chunksection1 != null) {
                  chunksection1.a(p_d_1_, j & 15, p_d_3_, l1);
               }
            }
         }

         int i2 = this.heightMap[p_d_3_ << 4 | p_d_1_];
         int k2 = i;
         int j2 = i2;
         if(i2 < i) {
            k2 = i2;
            j2 = i;
         }

         if(i2 < this.t) {
            this.t = i2;
         }

         if(!this.world.worldProvider.o()) {
            for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
               this.a(k + enumdirection.getAdjacentX(), l + enumdirection.getAdjacentZ(), k2, j2);
            }

            this.a(k, l, k2, j2);
         }

         this.q = true;
      }

   }

   public int b(BlockPosition p_b_1_) {
      return this.getType(p_b_1_).p();
   }

   private int e(int p_e_1_, int p_e_2_, int p_e_3_) {
      return this.getType(p_e_1_, p_e_2_, p_e_3_).p();
   }

   private Block getType(int p_getType_1_, int p_getType_2_, int p_getType_3_) {
      Block block = Blocks.AIR;
      if(p_getType_2_ >= 0 && p_getType_2_ >> 4 < this.sections.length) {
         ChunkSection chunksection = this.sections[p_getType_2_ >> 4];
         if(chunksection != null) {
            try {
               block = chunksection.b(p_getType_1_, p_getType_2_ & 15, p_getType_3_);
            } catch (Throwable throwable) {
               CrashReport crashreport = CrashReport.a(throwable, "Getting block");
               throw new ReportedException(crashreport);
            }
         }
      }

      return block;
   }

   public Block getTypeAbs(final int p_getTypeAbs_1_, final int p_getTypeAbs_2_, final int p_getTypeAbs_3_) {
      try {
         return this.getType(p_getTypeAbs_1_ & 15, p_getTypeAbs_2_, p_getTypeAbs_3_ & 15);
      } catch (ReportedException reportedexception) {
         CrashReportSystemDetails crashreportsystemdetails = reportedexception.a().a("Block being got");
         crashreportsystemdetails.a("Location", new Callable() {
            public String a() throws Exception {
               return CrashReportSystemDetails.a(new BlockPosition(Chunk.this.locX * 16 + p_getTypeAbs_1_, p_getTypeAbs_2_, Chunk.this.locZ * 16 + p_getTypeAbs_3_));
            }

            public Object call() throws Exception {
               return this.a();
            }
         });
         throw reportedexception;
      }
   }

   public Block getType(final BlockPosition p_getType_1_) {
      try {
         return this.getType(p_getType_1_.getX() & 15, p_getType_1_.getY(), p_getType_1_.getZ() & 15);
      } catch (ReportedException reportedexception) {
         CrashReportSystemDetails crashreportsystemdetails = reportedexception.a().a("Block being got");
         crashreportsystemdetails.a("Location", new Callable() {
            public String a() throws Exception {
               return CrashReportSystemDetails.a(p_getType_1_);
            }

            public Object call() throws Exception {
               return this.a();
            }
         });
         throw reportedexception;
      }
   }

   public IBlockData getBlockData(final BlockPosition p_getBlockData_1_) {
      if(this.world.G() == WorldType.DEBUG_ALL_BLOCK_STATES) {
         IBlockData iblockdata = null;
         if(p_getBlockData_1_.getY() == 60) {
            iblockdata = Blocks.BARRIER.getBlockData();
         }

         if(p_getBlockData_1_.getY() == 70) {
            iblockdata = ChunkProviderDebug.b(p_getBlockData_1_.getX(), p_getBlockData_1_.getZ());
         }

         return iblockdata == null?Blocks.AIR.getBlockData():iblockdata;
      } else {
         try {
            if(p_getBlockData_1_.getY() >= 0 && p_getBlockData_1_.getY() >> 4 < this.sections.length) {
               ChunkSection chunksection = this.sections[p_getBlockData_1_.getY() >> 4];
               if(chunksection != null) {
                  int j = p_getBlockData_1_.getX() & 15;
                  int k = p_getBlockData_1_.getY() & 15;
                  int i = p_getBlockData_1_.getZ() & 15;
                  return chunksection.getType(j, k, i);
               }
            }

            return Blocks.AIR.getBlockData();
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Getting block state");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Block being got");
            crashreportsystemdetails.a("Location", new Callable() {
               public String a() throws Exception {
                  return CrashReportSystemDetails.a(p_getBlockData_1_);
               }

               public Object call() throws Exception {
                  return this.a();
               }
            });
            throw new ReportedException(crashreport);
         }
      }
   }

   private int g(int p_g_1_, int p_g_2_, int p_g_3_) {
      if(p_g_2_ >> 4 >= this.sections.length) {
         return 0;
      } else {
         ChunkSection chunksection = this.sections[p_g_2_ >> 4];
         return chunksection != null?chunksection.c(p_g_1_, p_g_2_ & 15, p_g_3_):0;
      }
   }

   public int c(BlockPosition p_c_1_) {
      return this.g(p_c_1_.getX() & 15, p_c_1_.getY(), p_c_1_.getZ() & 15);
   }

   public IBlockData a(BlockPosition p_a_1_, IBlockData p_a_2_) {
      int i = p_a_1_.getX() & 15;
      int j = p_a_1_.getY();
      int k = p_a_1_.getZ() & 15;
      int l = k << 4 | i;
      if(j >= this.f[l] - 1) {
         this.f[l] = -999;
      }

      int i1 = this.heightMap[l];
      IBlockData iblockdata = this.getBlockData(p_a_1_);
      if(iblockdata == p_a_2_) {
         return null;
      } else {
         Block block = p_a_2_.getBlock();
         Block block1 = iblockdata.getBlock();
         ChunkSection chunksection = this.sections[j >> 4];
         boolean flag = false;
         if(chunksection == null) {
            if(block == Blocks.AIR) {
               return null;
            }

            chunksection = this.sections[j >> 4] = new ChunkSection(j >> 4 << 4, !this.world.worldProvider.o());
            flag = j >= i1;
         }

         chunksection.setType(i, j & 15, k, p_a_2_);
         if(block1 != block) {
            if(!this.world.isClientSide) {
               block1.remove(this.world, p_a_1_, iblockdata);
            } else if(block1 instanceof IContainer) {
               this.world.t(p_a_1_);
            }
         }

         if(chunksection.b(i, j & 15, k) != block) {
            return null;
         } else {
            if(flag) {
               this.initLighting();
            } else {
               int j1 = block.p();
               int k1 = block1.p();
               if(j1 > 0) {
                  if(j >= i1) {
                     this.d(i, j + 1, k);
                  }
               } else if(j == i1 - 1) {
                  this.d(i, j, k);
               }

               if(j1 != k1 && (j1 < k1 || this.getBrightness(EnumSkyBlock.SKY, p_a_1_) > 0 || this.getBrightness(EnumSkyBlock.BLOCK, p_a_1_) > 0)) {
                  this.d(i, k);
               }
            }

            if(block1 instanceof IContainer) {
               TileEntity tileentity = this.a(p_a_1_, Chunk.EnumTileEntityState.CHECK);
               if(tileentity != null) {
                  tileentity.E();
               }
            }

            if(!this.world.isClientSide && block1 != block && (!this.world.captureBlockStates || block instanceof BlockContainer)) {
               block.onPlace(this.world, p_a_1_, p_a_2_);
            }

            if(block instanceof IContainer) {
               TileEntity tileentity1 = this.a(p_a_1_, Chunk.EnumTileEntityState.CHECK);
               if(tileentity1 == null) {
                  tileentity1 = ((IContainer)block).a(this.world, block.toLegacyData(p_a_2_));
                  this.world.setTileEntity(p_a_1_, tileentity1);
               }

               if(tileentity1 != null) {
                  tileentity1.E();
               }
            }

            this.q = true;
            return iblockdata;
         }
      }
   }

   public int getBrightness(EnumSkyBlock p_getBrightness_1_, BlockPosition p_getBrightness_2_) {
      int i = p_getBrightness_2_.getX() & 15;
      int j = p_getBrightness_2_.getY();
      int k = p_getBrightness_2_.getZ() & 15;
      ChunkSection chunksection = this.sections[j >> 4];
      return chunksection == null?(this.d(p_getBrightness_2_)?p_getBrightness_1_.c:0):(p_getBrightness_1_ == EnumSkyBlock.SKY?(this.world.worldProvider.o()?0:chunksection.d(i, j & 15, k)):(p_getBrightness_1_ == EnumSkyBlock.BLOCK?chunksection.e(i, j & 15, k):p_getBrightness_1_.c));
   }

   public void a(EnumSkyBlock p_a_1_, BlockPosition p_a_2_, int p_a_3_) {
      int i = p_a_2_.getX() & 15;
      int j = p_a_2_.getY();
      int k = p_a_2_.getZ() & 15;
      ChunkSection chunksection = this.sections[j >> 4];
      if(chunksection == null) {
         chunksection = this.sections[j >> 4] = new ChunkSection(j >> 4 << 4, !this.world.worldProvider.o());
         this.initLighting();
      }

      this.q = true;
      if(p_a_1_ == EnumSkyBlock.SKY) {
         if(!this.world.worldProvider.o()) {
            chunksection.a(i, j & 15, k, p_a_3_);
         }
      } else if(p_a_1_ == EnumSkyBlock.BLOCK) {
         chunksection.b(i, j & 15, k, p_a_3_);
      }

   }

   public int a(BlockPosition p_a_1_, int p_a_2_) {
      int i = p_a_1_.getX() & 15;
      int j = p_a_1_.getY();
      int k = p_a_1_.getZ() & 15;
      ChunkSection chunksection = this.sections[j >> 4];
      if(chunksection != null) {
         int l = this.world.worldProvider.o()?0:chunksection.d(i, j & 15, k);
         l = l - p_a_2_;
         int i1 = chunksection.e(i, j & 15, k);
         if(i1 > l) {
            l = i1;
         }

         return l;
      } else {
         return !this.world.worldProvider.o() && p_a_2_ < EnumSkyBlock.SKY.c?EnumSkyBlock.SKY.c - p_a_2_:0;
      }
   }

   public void a(Entity p_a_1_) {
      this.r = true;
      int i = MathHelper.floor(p_a_1_.locX / 16.0D);
      int j = MathHelper.floor(p_a_1_.locZ / 16.0D);
      if(i != this.locX || j != this.locZ) {
         Bukkit.getLogger().warning("Wrong location for " + p_a_1_ + " in world \'" + this.world.getWorld().getName() + "\'!");
         Bukkit.getLogger().warning("Entity is at " + p_a_1_.locX + "," + p_a_1_.locZ + " (chunk " + i + "," + j + ") but was stored in chunk " + this.locX + "," + this.locZ);
         p_a_1_.die();
      }

      int k = MathHelper.floor(p_a_1_.locY / 16.0D);
      if(k < 0) {
         k = 0;
      }

      if(k >= this.entitySlices.length) {
         k = this.entitySlices.length - 1;
      }

      p_a_1_.ad = true;
      p_a_1_.ae = this.locX;
      p_a_1_.af = k;
      p_a_1_.ag = this.locZ;
      this.entitySlices[k].add(p_a_1_);
      if(p_a_1_ instanceof EntityInsentient) {
         EntityInsentient entityinsentient = (EntityInsentient)p_a_1_;
         if(entityinsentient.isTypeNotPersistent() && entityinsentient.isPersistent()) {
            return;
         }
      }

      EnumCreatureType[] aenumcreaturetype;
      for(EnumCreatureType enumcreaturetype : aenumcreaturetype = EnumCreatureType.values()) {
         if(enumcreaturetype.a().isAssignableFrom(p_a_1_.getClass())) {
            this.entityCount.adjustOrPutValue(enumcreaturetype.a(), 1, 1);
         }
      }

   }

   public void b(Entity p_b_1_) {
      this.a(p_b_1_, p_b_1_.af);
   }

   public void a(Entity p_a_1_, int p_a_2_) {
      if(p_a_2_ < 0) {
         p_a_2_ = 0;
      }

      if(p_a_2_ >= this.entitySlices.length) {
         p_a_2_ = this.entitySlices.length - 1;
      }

      this.entitySlices[p_a_2_].remove(p_a_1_);
      if(p_a_1_ instanceof EntityInsentient) {
         EntityInsentient entityinsentient = (EntityInsentient)p_a_1_;
         if(entityinsentient.isTypeNotPersistent() && entityinsentient.isPersistent()) {
            return;
         }
      }

      EnumCreatureType[] aenumcreaturetype;
      for(EnumCreatureType enumcreaturetype : aenumcreaturetype = EnumCreatureType.values()) {
         if(enumcreaturetype.a().isAssignableFrom(p_a_1_.getClass())) {
            this.entityCount.adjustValue(enumcreaturetype.a(), -1);
         }
      }

   }

   public boolean d(BlockPosition p_d_1_) {
      int i = p_d_1_.getX() & 15;
      int j = p_d_1_.getY();
      int k = p_d_1_.getZ() & 15;
      return j >= this.heightMap[k << 4 | i];
   }

   private TileEntity i(BlockPosition p_i_1_) {
      Block block = this.getType(p_i_1_);
      return !block.isTileEntity()?null:((IContainer)block).a(this.world, this.c(p_i_1_));
   }

   public TileEntity a(BlockPosition p_a_1_, Chunk.EnumTileEntityState p_a_2_) {
      TileEntity tileentity = null;
      if(this.world.captureBlockStates) {
         tileentity = (TileEntity)this.world.capturedTileEntities.get(p_a_1_);
      }

      if(tileentity == null) {
         tileentity = (TileEntity)this.tileEntities.get(p_a_1_);
      }

      if(tileentity == null) {
         if(p_a_2_ == Chunk.EnumTileEntityState.IMMEDIATE) {
            tileentity = this.i(p_a_1_);
            this.world.setTileEntity(p_a_1_, tileentity);
         } else if(p_a_2_ == Chunk.EnumTileEntityState.QUEUED) {
            this.w.add(p_a_1_);
         }
      } else if(tileentity.x()) {
         this.tileEntities.remove(p_a_1_);
         return null;
      }

      return tileentity;
   }

   public void a(TileEntity p_a_1_) {
      this.a(p_a_1_.getPosition(), p_a_1_);
      if(this.h) {
         this.world.a(p_a_1_);
      }

   }

   public void a(BlockPosition p_a_1_, TileEntity p_a_2_) {
      p_a_2_.a(this.world);
      p_a_2_.a(p_a_1_);
      if(this.getType(p_a_1_) instanceof IContainer) {
         if(this.tileEntities.containsKey(p_a_1_)) {
            ((TileEntity)this.tileEntities.get(p_a_1_)).y();
         }

         p_a_2_.D();
         this.tileEntities.put(p_a_1_, p_a_2_);
      } else {
         System.out.println("Attempted to place a tile entity (" + p_a_2_ + ") at " + p_a_2_.position.getX() + "," + p_a_2_.position.getY() + "," + p_a_2_.position.getZ() + " (" + CraftMagicNumbers.getMaterial(this.getType(p_a_1_)) + ") where there was no entity tile!");
         System.out.println("Chunk coordinates: " + this.locX * 16 + "," + this.locZ * 16);
         (new Exception()).printStackTrace();
      }

   }

   public void e(BlockPosition p_e_1_) {
      if(this.h) {
         TileEntity tileentity = (TileEntity)this.tileEntities.remove(p_e_1_);
         if(tileentity != null) {
            tileentity.y();
         }
      }

   }

   public void addEntities() {
      this.h = true;
      this.world.a(this.tileEntities.values());

      for(int i = 0; i < this.entitySlices.length; ++i) {
         for(Entity entity : this.entitySlices[i]) {
            entity.ah();
         }

         this.world.b((Collection)this.entitySlices[i]);
      }

   }

   public void removeEntities() {
      this.h = false;

      for(TileEntity tileentity : this.tileEntities.values()) {
         if(tileentity instanceof IInventory) {
            for(HumanEntity humanentity : Lists.newArrayList(((IInventory)tileentity).getViewers())) {
               if(humanentity instanceof CraftHumanEntity) {
                  ((CraftHumanEntity)humanentity).getHandle().closeInventory();
               }
            }
         }

         this.world.b(tileentity);
      }

      for(int i = 0; i < this.entitySlices.length; ++i) {
         List<Entity> list = Lists.newArrayList(this.entitySlices[i]);
         Iterator<Entity> iterator = list.iterator();

         while(iterator.hasNext()) {
            Entity entity = (Entity)iterator.next();
            if(entity instanceof IInventory) {
               for(HumanEntity humanentity1 : Lists.newArrayList(((IInventory)entity).getViewers())) {
                  if(humanentity1 instanceof CraftHumanEntity) {
                     ((CraftHumanEntity)humanentity1).getHandle().closeInventory();
                  }
               }
            }

            if(entity instanceof EntityPlayer) {
               iterator.remove();
            }
         }

         this.world.c((Collection)list);
      }

   }

   public void e() {
      this.q = true;
   }

   public void a(Entity p_a_1_, AxisAlignedBB p_a_2_, List<Entity> p_a_3_, Predicate<? super Entity> p_a_4_) {
      int i = MathHelper.floor((p_a_2_.b - 2.0D) / 16.0D);
      int j = MathHelper.floor((p_a_2_.e + 2.0D) / 16.0D);
      i = MathHelper.clamp(i, 0, this.entitySlices.length - 1);
      j = MathHelper.clamp(j, 0, this.entitySlices.length - 1);

      for(int k = i; k <= j; ++k) {
         if(!this.entitySlices[k].isEmpty()) {
            for(Entity entity : this.entitySlices[k]) {
               if(entity.getBoundingBox().b(p_a_2_) && entity != p_a_1_) {
                  if(p_a_4_ == null || p_a_4_.apply(entity)) {
                     p_a_3_.add(entity);
                  }

                  Entity[] aentity = entity.aB();
                  if(aentity != null) {
                     for(int l = 0; l < aentity.length; ++l) {
                        entity = aentity[l];
                        if(entity != p_a_1_ && entity.getBoundingBox().b(p_a_2_) && (p_a_4_ == null || p_a_4_.apply(entity))) {
                           p_a_3_.add(entity);
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public <T extends Entity> void a(Class<? extends T> p_a_1_, AxisAlignedBB p_a_2_, List<T> p_a_3_, Predicate<? super T> p_a_4_) {
      int i = MathHelper.floor((p_a_2_.b - 2.0D) / 16.0D);
      int j = MathHelper.floor((p_a_2_.e + 2.0D) / 16.0D);
      i = MathHelper.clamp(i, 0, this.entitySlices.length - 1);
      j = MathHelper.clamp(j, 0, this.entitySlices.length - 1);

      for(int k = i; k <= j; ++k) {
         for(Entity entity : this.entitySlices[k]) {
            if(p_a_1_.isInstance(entity) && entity.getBoundingBox().b(p_a_2_) && (p_a_4_ == null || p_a_4_.apply(entity))) {
               p_a_3_.add(entity);
            }
         }
      }

   }

   public boolean a(boolean p_a_1_) {
      if(p_a_1_) {
         if(this.r && this.world.getTime() != this.lastSaved || this.q) {
            return true;
         }
      } else if(this.r && this.world.getTime() >= this.lastSaved + (long)(MinecraftServer.getServer().autosavePeriod * 4)) {
         return true;
      }

      return this.q;
   }

   public Random a(long p_a_1_) {
      return new Random(this.world.getSeed() + (long)(this.locX * this.locX * 4987142) + (long)(this.locX * 5947611) + (long)(this.locZ * this.locZ) * 4392871L + (long)(this.locZ * 389711) ^ p_a_1_);
   }

   public boolean isEmpty() {
      return false;
   }

   public void loadNearby(IChunkProvider p_loadNearby_1_, IChunkProvider p_loadNearby_2_, int p_loadNearby_3_, int p_loadNearby_4_) {
      this.world.timings.syncChunkLoadPostTimer.startTiming();
      boolean flag = p_loadNearby_1_.isChunkLoaded(p_loadNearby_3_, p_loadNearby_4_ - 1);
      boolean flag1 = p_loadNearby_1_.isChunkLoaded(p_loadNearby_3_ + 1, p_loadNearby_4_);
      boolean flag2 = p_loadNearby_1_.isChunkLoaded(p_loadNearby_3_, p_loadNearby_4_ + 1);
      boolean flag3 = p_loadNearby_1_.isChunkLoaded(p_loadNearby_3_ - 1, p_loadNearby_4_);
      boolean flag4 = p_loadNearby_1_.isChunkLoaded(p_loadNearby_3_ - 1, p_loadNearby_4_ - 1);
      boolean flag5 = p_loadNearby_1_.isChunkLoaded(p_loadNearby_3_ + 1, p_loadNearby_4_ + 1);
      boolean flag6 = p_loadNearby_1_.isChunkLoaded(p_loadNearby_3_ - 1, p_loadNearby_4_ + 1);
      boolean flag7 = p_loadNearby_1_.isChunkLoaded(p_loadNearby_3_ + 1, p_loadNearby_4_ - 1);
      if(flag1 && flag2 && flag5) {
         if(!this.done) {
            p_loadNearby_1_.getChunkAt(p_loadNearby_2_, p_loadNearby_3_, p_loadNearby_4_);
         } else {
            p_loadNearby_1_.a(p_loadNearby_2_, this, p_loadNearby_3_, p_loadNearby_4_);
         }
      }

      if(flag3 && flag2 && flag6) {
         Chunk chunk = p_loadNearby_1_.getOrCreateChunk(p_loadNearby_3_ - 1, p_loadNearby_4_);
         if(!chunk.done) {
            p_loadNearby_1_.getChunkAt(p_loadNearby_2_, p_loadNearby_3_ - 1, p_loadNearby_4_);
         } else {
            p_loadNearby_1_.a(p_loadNearby_2_, chunk, p_loadNearby_3_ - 1, p_loadNearby_4_);
         }
      }

      if(flag && flag1 && flag7) {
         Chunk chunk1 = p_loadNearby_1_.getOrCreateChunk(p_loadNearby_3_, p_loadNearby_4_ - 1);
         if(!chunk1.done) {
            p_loadNearby_1_.getChunkAt(p_loadNearby_2_, p_loadNearby_3_, p_loadNearby_4_ - 1);
         } else {
            p_loadNearby_1_.a(p_loadNearby_2_, chunk1, p_loadNearby_3_, p_loadNearby_4_ - 1);
         }
      }

      if(flag4 && flag && flag3) {
         Chunk chunk2 = p_loadNearby_1_.getOrCreateChunk(p_loadNearby_3_ - 1, p_loadNearby_4_ - 1);
         if(!chunk2.done) {
            p_loadNearby_1_.getChunkAt(p_loadNearby_2_, p_loadNearby_3_ - 1, p_loadNearby_4_ - 1);
         } else {
            p_loadNearby_1_.a(p_loadNearby_2_, chunk2, p_loadNearby_3_ - 1, p_loadNearby_4_ - 1);
         }
      }

      this.world.timings.syncChunkLoadPostTimer.stopTiming();
   }

   public BlockPosition h(BlockPosition p_h_1_) {
      int i = p_h_1_.getX() & 15;
      int j = p_h_1_.getZ() & 15;
      int k = i | j << 4;
      BlockPosition blockposition = new BlockPosition(p_h_1_.getX(), this.f[k], p_h_1_.getZ());
      if(blockposition.getY() == -999) {
         int l = this.g() + 15;
         blockposition = new BlockPosition(p_h_1_.getX(), l, p_h_1_.getZ());
         int i1 = -1;

         while(blockposition.getY() > 0 && i1 == -1) {
            Block block = this.getType(blockposition);
            Material material = block.getMaterial();
            if(!material.isSolid() && !material.isLiquid()) {
               blockposition = blockposition.down();
            } else {
               i1 = blockposition.getY() + 1;
            }
         }

         this.f[k] = i1;
      }

      return new BlockPosition(p_h_1_.getX(), this.f[k], p_h_1_.getZ());
   }

   public void b(boolean p_b_1_) {
      if(this.k && !this.world.worldProvider.o() && !p_b_1_) {
         this.h(this.world.isClientSide);
      }

      this.p = true;
      if(!this.lit && this.done && this.world.spigotConfig.randomLightUpdates) {
         this.n();
      }

      while(!this.w.isEmpty()) {
         BlockPosition blockposition = (BlockPosition)this.w.poll();
         if(this.a(blockposition, Chunk.EnumTileEntityState.CHECK) == null && this.getType(blockposition).isTileEntity()) {
            TileEntity tileentity = this.i(blockposition);
            this.world.setTileEntity(blockposition, tileentity);
            this.world.b(blockposition, blockposition);
         }
      }

   }

   public boolean isReady() {
      return true;
   }

   public ChunkCoordIntPair j() {
      return new ChunkCoordIntPair(this.locX, this.locZ);
   }

   public boolean c(int p_c_1_, int p_c_2_) {
      if(p_c_1_ < 0) {
         p_c_1_ = 0;
      }

      if(p_c_2_ >= 256) {
         p_c_2_ = 255;
      }

      for(int i = p_c_1_; i <= p_c_2_; i += 16) {
         ChunkSection chunksection = this.sections[i >> 4];
         if(chunksection != null && !chunksection.a()) {
            return false;
         }
      }

      return true;
   }

   public void a(ChunkSection[] p_a_1_) {
      if(this.sections.length != p_a_1_.length) {
         c.warn("Could not set level chunk sections, array length is " + p_a_1_.length + " instead of " + this.sections.length);
      } else {
         for(int i = 0; i < this.sections.length; ++i) {
            this.sections[i] = p_a_1_[i];
         }
      }

   }

   public BiomeBase getBiome(BlockPosition p_getBiome_1_, WorldChunkManager p_getBiome_2_) {
      int i = p_getBiome_1_.getX() & 15;
      int j = p_getBiome_1_.getZ() & 15;
      int k = this.e[j << 4 | i] & 255;
      if(k == 255) {
         BiomeBase biomebase = p_getBiome_2_.getBiome(p_getBiome_1_, BiomeBase.PLAINS);
         k = biomebase.id;
         this.e[j << 4 | i] = (byte)(k & 255);
      }

      BiomeBase biomebase1 = BiomeBase.getBiome(k);
      return biomebase1 == null?BiomeBase.PLAINS:biomebase1;
   }

   public byte[] getBiomeIndex() {
      return this.e;
   }

   public void a(byte[] p_a_1_) {
      if(this.e.length != p_a_1_.length) {
         c.warn("Could not set level chunk biomes, array length is " + p_a_1_.length + " instead of " + this.e.length);
      } else {
         for(int i = 0; i < this.e.length; ++i) {
            this.e[i] = p_a_1_[i];
         }
      }

   }

   public void l() {
      this.v = 0;
   }

   public void m() {
      BlockPosition blockposition = new BlockPosition(this.locX << 4, 0, this.locZ << 4);

      for(int i = 0; i < 8; ++i) {
         if(this.v >= 4096) {
            return;
         }

         int j = this.v % 16;
         int k = this.v / 16 % 16;
         int l = this.v / 256;
         ++this.v;

         for(int i1 = 0; i1 < 16; ++i1) {
            BlockPosition blockposition1 = blockposition.a(k, (j << 4) + i1, l);
            boolean flag = i1 == 0 || i1 == 15 || k == 0 || k == 15 || l == 0 || l == 15;
            if(this.sections[j] == null && flag || this.sections[j] != null && this.sections[j].b(k, i1, l).getMaterial() == Material.AIR) {
               for(EnumDirection enumdirection : EnumDirection.values()) {
                  BlockPosition blockposition2 = blockposition1.shift(enumdirection);
                  if(this.world.getType(blockposition2).getBlock().r() > 0) {
                     this.world.x(blockposition2);
                  }
               }

               this.world.x(blockposition1);
            }
         }
      }

   }

   public void n() {
      this.done = true;
      this.lit = true;
      BlockPosition blockposition = new BlockPosition(this.locX << 4, 0, this.locZ << 4);
      if(!this.world.worldProvider.o()) {
         if(this.world.areChunksLoadedBetween(blockposition.a(-1, 0, -1), blockposition.a(16, this.world.F(), 16))) {
            label86:
            for(int i = 0; i < 16; ++i) {
               for(int j = 0; j < 16; ++j) {
                  if(!this.e(i, j)) {
                     this.lit = false;
                     break label86;
                  }
               }
            }

            if(this.lit) {
               for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                  int k = enumdirection.c() == EnumDirection.EnumAxisDirection.POSITIVE?16:1;
                  this.world.getChunkAtWorldCoords(blockposition.shift(enumdirection, k)).a(enumdirection.opposite());
               }

               this.y();
            }
         } else {
            this.lit = false;
         }
      }

   }

   private void y() {
      for(int i = 0; i < this.g.length; ++i) {
         this.g[i] = true;
      }

      this.h(false);
   }

   private void a(EnumDirection p_a_1_) {
      if(this.done) {
         if(p_a_1_ == EnumDirection.EAST) {
            for(int i = 0; i < 16; ++i) {
               this.e(15, i);
            }
         } else if(p_a_1_ == EnumDirection.WEST) {
            for(int j = 0; j < 16; ++j) {
               this.e(0, j);
            }
         } else if(p_a_1_ == EnumDirection.SOUTH) {
            for(int k = 0; k < 16; ++k) {
               this.e(k, 15);
            }
         } else if(p_a_1_ == EnumDirection.NORTH) {
            for(int l = 0; l < 16; ++l) {
               this.e(l, 0);
            }
         }
      }

   }

   private boolean e(int p_e_1_, int p_e_2_) {
      int i = this.g();
      boolean flag = false;
      boolean flag1 = false;
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition((this.locX << 4) + p_e_1_, 0, (this.locZ << 4) + p_e_2_);

      for(int j = i + 16 - 1; j > this.world.F() || j > 0 && !flag1; --j) {
         blockposition$mutableblockposition.c(blockposition$mutableblockposition.getX(), j, blockposition$mutableblockposition.getZ());
         int k = this.b((BlockPosition)blockposition$mutableblockposition);
         if(k == 255 && blockposition$mutableblockposition.getY() < this.world.F()) {
            flag1 = true;
         }

         if(!flag && k > 0) {
            flag = true;
         } else if(flag && k == 0 && !this.world.x(blockposition$mutableblockposition)) {
            return false;
         }
      }

      for(int l1 = blockposition$mutableblockposition.getY(); l1 > 0; --l1) {
         blockposition$mutableblockposition.c(blockposition$mutableblockposition.getX(), l1, blockposition$mutableblockposition.getZ());
         if(this.getType(blockposition$mutableblockposition).r() > 0) {
            this.world.x(blockposition$mutableblockposition);
         }
      }

      return true;
   }

   public boolean o() {
      return this.h;
   }

   public World getWorld() {
      return this.world;
   }

   public int[] q() {
      return this.heightMap;
   }

   public void a(int[] p_a_1_) {
      if(this.heightMap.length != p_a_1_.length) {
         c.warn("Could not set level chunk heightmap, array length is " + p_a_1_.length + " instead of " + this.heightMap.length);
      } else {
         for(int i = 0; i < this.heightMap.length; ++i) {
            this.heightMap[i] = p_a_1_[i];
         }
      }

   }

   public Map<BlockPosition, TileEntity> getTileEntities() {
      return this.tileEntities;
   }

   public List<Entity>[] getEntitySlices() {
      return this.entitySlices;
   }

   public boolean isDone() {
      return this.done;
   }

   public void d(boolean p_d_1_) {
      this.done = p_d_1_;
   }

   public boolean u() {
      return this.lit;
   }

   public void e(boolean p_e_1_) {
      this.lit = p_e_1_;
   }

   public void f(boolean p_f_1_) {
      this.q = p_f_1_;
   }

   public void g(boolean p_g_1_) {
      this.r = p_g_1_;
   }

   public void setLastSaved(long p_setLastSaved_1_) {
      this.lastSaved = p_setLastSaved_1_;
   }

   public int v() {
      return this.t;
   }

   public long w() {
      return this.u;
   }

   public void c(long p_c_1_) {
      this.u = p_c_1_;
   }

   public static enum EnumTileEntityState {
      IMMEDIATE,
      QUEUED,
      CHECK;
   }
}
