package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.LongHashMap;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockChange;
import net.minecraft.server.v1_8_R3.PacketPlayOutMapChunk;
import net.minecraft.server.v1_8_R3.PacketPlayOutMultiBlockChange;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.WorldProvider;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.v1_8_R3.chunkio.ChunkIOExecutor;

public class PlayerChunkMap {
   private static final Logger a = LogManager.getLogger();
   private final WorldServer world;
   private final List<EntityPlayer> managedPlayers = Lists.<EntityPlayer>newArrayList();
   private final LongHashMap<PlayerChunkMap.PlayerChunk> d = new LongHashMap();
   private final Queue<PlayerChunkMap.PlayerChunk> e = new ConcurrentLinkedQueue();
   private final Queue<PlayerChunkMap.PlayerChunk> f = new ConcurrentLinkedQueue();
   private int g;
   private long h;
   private final int[][] i = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
   private boolean wasNotEmpty;

   public PlayerChunkMap(WorldServer p_i408_1_, int p_i408_2_) {
      this.world = p_i408_1_;
      this.a(p_i408_2_);
   }

   public WorldServer a() {
      return this.world;
   }

   public void flush() {
      long i = this.world.getTime();
      if(i - this.h > 8000L) {
         this.h = i;

         for(PlayerChunkMap.PlayerChunk playerchunkmap$playerchunk : this.f) {
            playerchunkmap$playerchunk.b();
            playerchunkmap$playerchunk.a();
         }
      } else {
         Iterator iterator = this.e.iterator();

         while(iterator.hasNext()) {
            PlayerChunkMap.PlayerChunk playerchunkmap$playerchunk1 = (PlayerChunkMap.PlayerChunk)iterator.next();
            playerchunkmap$playerchunk1.b();
            iterator.remove();
         }
      }

      if(this.managedPlayers.isEmpty()) {
         if(!this.wasNotEmpty) {
            return;
         }

         WorldProvider worldprovider = this.world.worldProvider;
         if(!worldprovider.e()) {
            this.world.chunkProviderServer.b();
         }

         this.wasNotEmpty = false;
      } else {
         this.wasNotEmpty = true;
      }

   }

   public boolean a(int p_a_1_, int p_a_2_) {
      long i = (long)p_a_1_ + 2147483647L | (long)p_a_2_ + 2147483647L << 32;
      return this.d.getEntry(i) != null;
   }

   private PlayerChunkMap.PlayerChunk a(int p_a_1_, int p_a_2_, boolean p_a_3_) {
      long i = (long)p_a_1_ + 2147483647L | (long)p_a_2_ + 2147483647L << 32;
      PlayerChunkMap.PlayerChunk playerchunkmap$playerchunk = (PlayerChunkMap.PlayerChunk)this.d.getEntry(i);
      if(playerchunkmap$playerchunk == null && p_a_3_) {
         playerchunkmap$playerchunk = new PlayerChunkMap.PlayerChunk(p_a_1_, p_a_2_);
         this.d.put(i, playerchunkmap$playerchunk);
         this.f.add(playerchunkmap$playerchunk);
      }

      return playerchunkmap$playerchunk;
   }

   public final boolean isChunkInUse(int p_isChunkInUse_1_, int p_isChunkInUse_2_) {
      PlayerChunkMap.PlayerChunk playerchunkmap$playerchunk = this.a(p_isChunkInUse_1_, p_isChunkInUse_2_, false);
      return playerchunkmap$playerchunk != null?playerchunkmap$playerchunk.b.size() > 0:false;
   }

   public void flagDirty(BlockPosition p_flagDirty_1_) {
      int i = p_flagDirty_1_.getX() >> 4;
      int j = p_flagDirty_1_.getZ() >> 4;
      PlayerChunkMap.PlayerChunk playerchunkmap$playerchunk = this.a(i, j, false);
      if(playerchunkmap$playerchunk != null) {
         playerchunkmap$playerchunk.a(p_flagDirty_1_.getX() & 15, p_flagDirty_1_.getY(), p_flagDirty_1_.getZ() & 15);
      }

   }

   public void addPlayer(EntityPlayer p_addPlayer_1_) {
      int i = (int)p_addPlayer_1_.locX >> 4;
      int j = (int)p_addPlayer_1_.locZ >> 4;
      p_addPlayer_1_.d = p_addPlayer_1_.locX;
      p_addPlayer_1_.e = p_addPlayer_1_.locZ;
      List<ChunkCoordIntPair> list = new LinkedList();

      for(int k = i - this.g; k <= i + this.g; ++k) {
         for(int l = j - this.g; l <= j + this.g; ++l) {
            list.add(new ChunkCoordIntPair(k, l));
         }
      }

      Collections.sort(list, new PlayerChunkMap.ChunkCoordComparator(p_addPlayer_1_));

      for(ChunkCoordIntPair chunkcoordintpair : list) {
         this.a(chunkcoordintpair.x, chunkcoordintpair.z, true).a(p_addPlayer_1_);
      }

      this.managedPlayers.add(p_addPlayer_1_);
      this.b(p_addPlayer_1_);
   }

   public void b(EntityPlayer p_b_1_) {
      ArrayList arraylist = Lists.newArrayList(p_b_1_.chunkCoordIntPairQueue);
      int i = 0;
      int j = this.g;
      int k = (int)p_b_1_.locX >> 4;
      int l = (int)p_b_1_.locZ >> 4;
      int i1 = 0;
      int j1 = 0;
      ChunkCoordIntPair chunkcoordintpair = this.a(k, l, true).location;
      p_b_1_.chunkCoordIntPairQueue.clear();
      if(arraylist.contains(chunkcoordintpair)) {
         p_b_1_.chunkCoordIntPairQueue.add(chunkcoordintpair);
      }

      for(int k1 = 1; k1 <= j * 2; ++k1) {
         for(int l1 = 0; l1 < 2; ++l1) {
            int[] aint = this.i[i++ % 4];

            for(int i2 = 0; i2 < k1; ++i2) {
               i1 += aint[0];
               j1 += aint[1];
               chunkcoordintpair = this.a(k + i1, l + j1, true).location;
               if(arraylist.contains(chunkcoordintpair)) {
                  p_b_1_.chunkCoordIntPairQueue.add(chunkcoordintpair);
               }
            }
         }
      }

      i = i % 4;

      for(int j2 = 0; j2 < j * 2; ++j2) {
         i1 += this.i[i][0];
         j1 += this.i[i][1];
         chunkcoordintpair = this.a(k + i1, l + j1, true).location;
         if(arraylist.contains(chunkcoordintpair)) {
            p_b_1_.chunkCoordIntPairQueue.add(chunkcoordintpair);
         }
      }

   }

   public void removePlayer(EntityPlayer p_removePlayer_1_) {
      int i = (int)p_removePlayer_1_.d >> 4;
      int j = (int)p_removePlayer_1_.e >> 4;

      for(int k = i - this.g; k <= i + this.g; ++k) {
         for(int l = j - this.g; l <= j + this.g; ++l) {
            PlayerChunkMap.PlayerChunk playerchunkmap$playerchunk = this.a(k, l, false);
            if(playerchunkmap$playerchunk != null) {
               playerchunkmap$playerchunk.b(p_removePlayer_1_);
            }
         }
      }

      this.managedPlayers.remove(p_removePlayer_1_);
   }

   private boolean a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_) {
      int i = p_a_1_ - p_a_3_;
      int j = p_a_2_ - p_a_4_;
      return i >= -p_a_5_ && i <= p_a_5_?j >= -p_a_5_ && j <= p_a_5_:false;
   }

   public void movePlayer(EntityPlayer p_movePlayer_1_) {
      int i = (int)p_movePlayer_1_.locX >> 4;
      int j = (int)p_movePlayer_1_.locZ >> 4;
      double d0 = p_movePlayer_1_.d - p_movePlayer_1_.locX;
      double d1 = p_movePlayer_1_.e - p_movePlayer_1_.locZ;
      double d2 = d0 * d0 + d1 * d1;
      if(d2 >= 64.0D) {
         int k = (int)p_movePlayer_1_.d >> 4;
         int l = (int)p_movePlayer_1_.e >> 4;
         int i1 = this.g;
         int j1 = i - k;
         int k1 = j - l;
         List<ChunkCoordIntPair> list = new LinkedList();
         if(j1 != 0 || k1 != 0) {
            for(int l1 = i - i1; l1 <= i + i1; ++l1) {
               for(int i2 = j - i1; i2 <= j + i1; ++i2) {
                  if(!this.a(l1, i2, k, l, i1)) {
                     list.add(new ChunkCoordIntPair(l1, i2));
                  }

                  if(!this.a(l1 - j1, i2 - k1, i, j, i1)) {
                     PlayerChunkMap.PlayerChunk playerchunkmap$playerchunk = this.a(l1 - j1, i2 - k1, false);
                     if(playerchunkmap$playerchunk != null) {
                        playerchunkmap$playerchunk.b(p_movePlayer_1_);
                     }
                  }
               }
            }

            this.b(p_movePlayer_1_);
            p_movePlayer_1_.d = p_movePlayer_1_.locX;
            p_movePlayer_1_.e = p_movePlayer_1_.locZ;
            Collections.sort(list, new PlayerChunkMap.ChunkCoordComparator(p_movePlayer_1_));

            for(ChunkCoordIntPair chunkcoordintpair : list) {
               this.a(chunkcoordintpair.x, chunkcoordintpair.z, true).a(p_movePlayer_1_);
            }

            if(j1 > 1 || j1 < -1 || k1 > 1 || k1 < -1) {
               Collections.sort(p_movePlayer_1_.chunkCoordIntPairQueue, new PlayerChunkMap.ChunkCoordComparator(p_movePlayer_1_));
            }
         }
      }

   }

   public boolean a(EntityPlayer p_a_1_, int p_a_2_, int p_a_3_) {
      PlayerChunkMap.PlayerChunk playerchunkmap$playerchunk = this.a(p_a_2_, p_a_3_, false);
      return playerchunkmap$playerchunk != null && playerchunkmap$playerchunk.b.contains(p_a_1_) && !p_a_1_.chunkCoordIntPairQueue.contains(playerchunkmap$playerchunk.location);
   }

   public void a(int p_a_1_) {
      p_a_1_ = MathHelper.clamp(p_a_1_, 3, 32);
      if(p_a_1_ != this.g) {
         int i = p_a_1_ - this.g;

         for(EntityPlayer entityplayer : Lists.newArrayList(this.managedPlayers)) {
            int j = (int)entityplayer.locX >> 4;
            int k = (int)entityplayer.locZ >> 4;
            if(i > 0) {
               for(int j1 = j - p_a_1_; j1 <= j + p_a_1_; ++j1) {
                  for(int k1 = k - p_a_1_; k1 <= k + p_a_1_; ++k1) {
                     PlayerChunkMap.PlayerChunk playerchunkmap$playerchunk = this.a(j1, k1, true);
                     if(!playerchunkmap$playerchunk.b.contains(entityplayer)) {
                        playerchunkmap$playerchunk.a(entityplayer);
                     }
                  }
               }
            } else {
               for(int l = j - this.g; l <= j + this.g; ++l) {
                  for(int i1 = k - this.g; i1 <= k + this.g; ++i1) {
                     if(!this.a(l, i1, j, k, p_a_1_)) {
                        this.a(l, i1, true).b(entityplayer);
                     }
                  }
               }
            }
         }

         this.g = p_a_1_;
      }

   }

   public static int getFurthestViewableBlock(int p_getFurthestViewableBlock_0_) {
      return p_getFurthestViewableBlock_0_ * 16 - 16;
   }

   private static class ChunkCoordComparator implements Comparator<ChunkCoordIntPair> {
      private int x;
      private int z;

      public ChunkCoordComparator(EntityPlayer p_i349_1_) {
         this.x = (int)p_i349_1_.locX >> 4;
         this.z = (int)p_i349_1_.locZ >> 4;
      }

      public int compare(ChunkCoordIntPair p_compare_1_, ChunkCoordIntPair p_compare_2_) {
         if(p_compare_1_.equals(p_compare_2_)) {
            return 0;
         } else {
            int i = p_compare_1_.x - this.x;
            int j = p_compare_1_.z - this.z;
            int k = p_compare_2_.x - this.x;
            int l = p_compare_2_.z - this.z;
            int i1 = (i - k) * (i + k) + (j - l) * (j + l);
            return i1 != 0?i1:(i < 0?(k < 0?l - j:-1):(k < 0?1:j - l));
         }
      }
   }

   class PlayerChunk {
      private final List<EntityPlayer> b = Lists.<EntityPlayer>newArrayList();
      private final ChunkCoordIntPair location;
      private short[] dirtyBlocks = new short[64];
      private int dirtyCount;
      private int f;
      private long g;
      private final HashMap<EntityPlayer, Runnable> players = new HashMap();
      private boolean loaded = false;
      private Runnable loadedRunnable = new Runnable() {
         public void run() {
            PlayerChunk.this.loaded = true;
         }
      };

      public PlayerChunk(int p_i416_2_, int p_i416_3_) {
         this.location = new ChunkCoordIntPair(p_i416_2_, p_i416_3_);
         PlayerChunkMap.this.a().chunkProviderServer.getChunkAt(p_i416_2_, p_i416_3_, this.loadedRunnable);
      }

      public void a(final EntityPlayer p_a_1_) {
         if(this.b.contains(p_a_1_)) {
            PlayerChunkMap.a.debug("Failed to add player. {} already is in chunk {}, {}", new Object[]{p_a_1_, Integer.valueOf(this.location.x), Integer.valueOf(this.location.z)});
         } else {
            if(this.b.isEmpty()) {
               this.g = PlayerChunkMap.this.world.getTime();
            }

            this.b.add(p_a_1_);
            Runnable runnable;
            if(this.loaded) {
               runnable = null;
               p_a_1_.chunkCoordIntPairQueue.add(this.location);
            } else {
               runnable = new Runnable() {
                  public void run() {
                     p_a_1_.chunkCoordIntPairQueue.add(PlayerChunk.this.location);
                  }
               };
               PlayerChunkMap.this.a().chunkProviderServer.getChunkAt(this.location.x, this.location.z, runnable);
            }

            this.players.put(p_a_1_, runnable);
         }

      }

      public void b(EntityPlayer p_b_1_) {
         if(this.b.contains(p_b_1_)) {
            if(!this.loaded) {
               ChunkIOExecutor.dropQueuedChunkLoad(PlayerChunkMap.this.a(), this.location.x, this.location.z, (Runnable)this.players.get(p_b_1_));
               this.b.remove(p_b_1_);
               this.players.remove(p_b_1_);
               if(this.b.isEmpty()) {
                  ChunkIOExecutor.dropQueuedChunkLoad(PlayerChunkMap.this.a(), this.location.x, this.location.z, this.loadedRunnable);
                  long i = (long)this.location.x + 2147483647L | (long)this.location.z + 2147483647L << 32;
                  PlayerChunkMap.this.d.remove(i);
                  PlayerChunkMap.this.f.remove(this);
               }

               return;
            }

            Chunk chunk = PlayerChunkMap.this.world.getChunkAt(this.location.x, this.location.z);
            if(chunk.isReady()) {
               p_b_1_.playerConnection.sendPacket(new PacketPlayOutMapChunk(chunk, true, 0));
            }

            this.players.remove(p_b_1_);
            this.b.remove(p_b_1_);
            p_b_1_.chunkCoordIntPairQueue.remove(this.location);
            if(this.b.isEmpty()) {
               long j = (long)this.location.x + 2147483647L | (long)this.location.z + 2147483647L << 32;
               this.a(chunk);
               PlayerChunkMap.this.d.remove(j);
               PlayerChunkMap.this.f.remove(this);
               if(this.dirtyCount > 0) {
                  PlayerChunkMap.this.e.remove(this);
               }

               PlayerChunkMap.this.a().chunkProviderServer.queueUnload(this.location.x, this.location.z);
            }
         }

      }

      public void a() {
         this.a(PlayerChunkMap.this.world.getChunkAt(this.location.x, this.location.z));
      }

      private void a(Chunk p_a_1_) {
         p_a_1_.c(p_a_1_.w() + PlayerChunkMap.this.world.getTime() - this.g);
         this.g = PlayerChunkMap.this.world.getTime();
      }

      public void a(int p_a_1_, int p_a_2_, int p_a_3_) {
         if(this.dirtyCount == 0) {
            PlayerChunkMap.this.e.add(this);
         }

         this.f |= 1 << (p_a_2_ >> 4);
         if(this.dirtyCount < 64) {
            short short1 = (short)(p_a_1_ << 12 | p_a_3_ << 8 | p_a_2_);

            for(int i = 0; i < this.dirtyCount; ++i) {
               if(this.dirtyBlocks[i] == short1) {
                  return;
               }
            }

            this.dirtyBlocks[this.dirtyCount++] = short1;
         }

      }

      public void a(Packet p_a_1_) {
         for(int i = 0; i < this.b.size(); ++i) {
            EntityPlayer entityplayer = (EntityPlayer)this.b.get(i);
            if(!entityplayer.chunkCoordIntPairQueue.contains(this.location)) {
               entityplayer.playerConnection.sendPacket(p_a_1_);
            }
         }

      }

      public void b() {
         if(this.dirtyCount != 0) {
            if(this.dirtyCount == 1) {
               int k1 = (this.dirtyBlocks[0] >> 12 & 15) + this.location.x * 16;
               int i2 = this.dirtyBlocks[0] & 255;
               int k2 = (this.dirtyBlocks[0] >> 8 & 15) + this.location.z * 16;
               BlockPosition blockposition = new BlockPosition(k1, i2, k2);
               this.a((Packet)(new PacketPlayOutBlockChange(PlayerChunkMap.this.world, blockposition)));
               if(PlayerChunkMap.this.world.getType(blockposition).getBlock().isTileEntity()) {
                  this.a(PlayerChunkMap.this.world.getTileEntity(blockposition));
               }
            } else if(this.dirtyCount != 64) {
               this.a((Packet)(new PacketPlayOutMultiBlockChange(this.dirtyCount, this.dirtyBlocks, PlayerChunkMap.this.world.getChunkAt(this.location.x, this.location.z))));

               for(int j1 = 0; j1 < this.dirtyCount; ++j1) {
                  int l1 = (this.dirtyBlocks[j1] >> 12 & 15) + this.location.x * 16;
                  int j2 = this.dirtyBlocks[j1] & 255;
                  int l2 = (this.dirtyBlocks[j1] >> 8 & 15) + this.location.z * 16;
                  BlockPosition blockposition1 = new BlockPosition(l1, j2, l2);
                  if(PlayerChunkMap.this.world.getType(blockposition1).getBlock().isTileEntity()) {
                     this.a(PlayerChunkMap.this.world.getTileEntity(blockposition1));
                  }
               }
            } else {
               int i = this.location.x * 16;
               int j = this.location.z * 16;
               this.a((Packet)(new PacketPlayOutMapChunk(PlayerChunkMap.this.world.getChunkAt(this.location.x, this.location.z), false, this.f)));

               for(int k = 0; k < 16; ++k) {
                  if((this.f & 1 << k) != 0) {
                     int l = k << 4;
                     List list = PlayerChunkMap.this.world.getTileEntities(i, l, j, i + 16, l + 16, j + 16);

                     for(int i1 = 0; i1 < list.size(); ++i1) {
                        this.a((TileEntity)list.get(i1));
                     }
                  }
               }
            }

            this.dirtyCount = 0;
            this.f = 0;
         }

      }

      private void a(TileEntity p_a_1_) {
         if(p_a_1_ != null) {
            Packet packet = p_a_1_.getUpdatePacket();
            if(packet != null) {
               this.a(packet);
            }
         }

      }
   }
}
