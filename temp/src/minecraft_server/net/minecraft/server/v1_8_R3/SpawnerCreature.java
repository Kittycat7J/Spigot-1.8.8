package net.minecraft.server.v1_8_R3;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Chunk;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityPositionTypes;
import net.minecraft.server.v1_8_R3.EnumCreatureType;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.WeightedRandom;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHashSet;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public final class SpawnerCreature {
   private static final int a = (int)Math.pow(17.0D, 2.0D);
   private final LongHashSet b = new LongHashSet();

   private int getEntityCount(WorldServer p_getEntityCount_1_, Class p_getEntityCount_2_) {
      int i = 0;

      for(Long olong : this.b) {
         int j = LongHash.msw(olong.longValue());
         int k = LongHash.lsw(olong.longValue());
         if(!p_getEntityCount_1_.chunkProviderServer.unloadQueue.contains(olong.longValue()) && p_getEntityCount_1_.isChunkLoaded(j, k, true)) {
            i += p_getEntityCount_1_.getChunkAt(j, k).entityCount.get(p_getEntityCount_2_);
         }
      }

      return i;
   }

   public int a(WorldServer p_a_1_, boolean p_a_2_, boolean p_a_3_, boolean p_a_4_) {
      if(!p_a_2_ && !p_a_3_) {
         return 0;
      } else {
         this.b.clear();
         int i = 0;

         for(EntityHuman entityhuman : p_a_1_.players) {
            if(!entityhuman.isSpectator()) {
               int j = MathHelper.floor(entityhuman.locX / 16.0D);
               int k = MathHelper.floor(entityhuman.locZ / 16.0D);
               byte b0 = 8;
               b0 = p_a_1_.spigotConfig.mobSpawnRange;
               b0 = b0 > p_a_1_.spigotConfig.viewDistance?(byte)p_a_1_.spigotConfig.viewDistance:b0;
               b0 = b0 > 8?8:b0;

               for(int l = -b0; l <= b0; ++l) {
                  for(int i1 = -b0; i1 <= b0; ++i1) {
                     boolean flag = l == -b0 || l == b0 || i1 == -b0 || i1 == b0;
                     long j1 = LongHash.toLong(l + j, i1 + k);
                     if(!this.b.contains(j1)) {
                        ++i;
                        if(!flag && p_a_1_.getWorldBorder().isInBounds(l + j, i1 + k)) {
                           this.b.add(j1);
                        }
                     }
                  }
               }
            }
         }

         int l4 = 0;
         BlockPosition blockposition2 = p_a_1_.getSpawn();

         for(EnumCreatureType enumcreaturetype : EnumCreatureType.values()) {
            int k1 = enumcreaturetype.b();
            switch($SWITCH_TABLE$net$minecraft$server$EnumCreatureType()[enumcreaturetype.ordinal()]) {
            case 1:
               k1 = p_a_1_.getWorld().getMonsterSpawnLimit();
               break;
            case 2:
               k1 = p_a_1_.getWorld().getAnimalSpawnLimit();
               break;
            case 3:
               k1 = p_a_1_.getWorld().getAmbientSpawnLimit();
               break;
            case 4:
               k1 = p_a_1_.getWorld().getWaterAnimalSpawnLimit();
            }

            if(k1 != 0) {
               int l1 = 0;
               if((!enumcreaturetype.d() || p_a_3_) && (enumcreaturetype.d() || p_a_2_) && (!enumcreaturetype.e() || p_a_4_)) {
                  p_a_1_.a((Class)enumcreaturetype.a());
                  int i5 = k1 * i / a;
                  if((l1 = this.getEntityCount(p_a_1_, enumcreaturetype.a())) <= k1 * i / 256) {
                     Iterator iterator = this.b.iterator();
                     int i2 = k1 * i / 256 - l1 + 1;

                     label531:
                     while(iterator.hasNext() && i2 > 0) {
                        long j2 = ((Long)iterator.next()).longValue();
                        BlockPosition blockposition = getRandomPosition(p_a_1_, LongHash.msw(j2), LongHash.lsw(j2));
                        int k2 = blockposition.getX();
                        int l2 = blockposition.getY();
                        int i3 = blockposition.getZ();
                        Block block = p_a_1_.getType(blockposition).getBlock();
                        if(!block.isOccluding()) {
                           int j3 = 0;

                           for(int k3 = 0; k3 < 3; ++k3) {
                              int l3 = k2;
                              int i4 = l2;
                              int j4 = i3;
                              byte b1 = 6;
                              BiomeBase.BiomeMeta biomebase$biomemeta = null;
                              GroupDataEntity groupdataentity = null;

                              for(int k4 = 0; k4 < 4; ++k4) {
                                 l3 += p_a_1_.random.nextInt(b1) - p_a_1_.random.nextInt(b1);
                                 i4 += p_a_1_.random.nextInt(1) - p_a_1_.random.nextInt(1);
                                 j4 += p_a_1_.random.nextInt(b1) - p_a_1_.random.nextInt(b1);
                                 BlockPosition blockposition1 = new BlockPosition(l3, i4, j4);
                                 float f = (float)l3 + 0.5F;
                                 float f1 = (float)j4 + 0.5F;
                                 if(!p_a_1_.isPlayerNearby((double)f, (double)i4, (double)f1, 24.0D) && blockposition2.c((double)f, (double)i4, (double)f1) >= 576.0D) {
                                    if(biomebase$biomemeta == null) {
                                       biomebase$biomemeta = p_a_1_.a(enumcreaturetype, blockposition1);
                                       if(biomebase$biomemeta == null) {
                                          break;
                                       }
                                    }

                                    if(p_a_1_.a(enumcreaturetype, biomebase$biomemeta, blockposition1) && a(EntityPositionTypes.a(biomebase$biomemeta.b), p_a_1_, blockposition1)) {
                                       EntityInsentient entityinsentient;
                                       try {
                                          entityinsentient = (EntityInsentient)biomebase$biomemeta.b.getConstructor(new Class[]{World.class}).newInstance(new Object[]{p_a_1_});
                                       } catch (Exception exception) {
                                          exception.printStackTrace();
                                          return l4;
                                       }

                                       entityinsentient.setPositionRotation((double)f, (double)i4, (double)f1, p_a_1_.random.nextFloat() * 360.0F, 0.0F);
                                       if(entityinsentient.bR() && entityinsentient.canSpawn()) {
                                          groupdataentity = entityinsentient.prepare(p_a_1_.E(new BlockPosition(entityinsentient)), groupdataentity);
                                          if(entityinsentient.canSpawn()) {
                                             ++j3;
                                             p_a_1_.addEntity(entityinsentient, SpawnReason.NATURAL);
                                          }

                                          --i2;
                                          if(i2 <= 0 || j3 >= entityinsentient.bV()) {
                                             continue label531;
                                          }
                                       }

                                       l4 += j3;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return l4;
      }
   }

   protected static BlockPosition getRandomPosition(World p_getRandomPosition_0_, int p_getRandomPosition_1_, int p_getRandomPosition_2_) {
      Chunk chunk = p_getRandomPosition_0_.getChunkAt(p_getRandomPosition_1_, p_getRandomPosition_2_);
      int i = p_getRandomPosition_1_ * 16 + p_getRandomPosition_0_.random.nextInt(16);
      int j = p_getRandomPosition_2_ * 16 + p_getRandomPosition_0_.random.nextInt(16);
      int k = MathHelper.c(chunk.f(new BlockPosition(i, 0, j)) + 1, 16);
      int l = p_getRandomPosition_0_.random.nextInt(k > 0?k:chunk.g() + 16 - 1);
      return new BlockPosition(i, l, j);
   }

   public static boolean a(EntityInsentient.EnumEntityPositionType p_a_0_, World p_a_1_, BlockPosition p_a_2_) {
      if(!p_a_1_.getWorldBorder().a(p_a_2_)) {
         return false;
      } else {
         Block block = p_a_1_.getType(p_a_2_).getBlock();
         if(p_a_0_ == EntityInsentient.EnumEntityPositionType.IN_WATER) {
            return block.getMaterial().isLiquid() && p_a_1_.getType(p_a_2_.down()).getBlock().getMaterial().isLiquid() && !p_a_1_.getType(p_a_2_.up()).getBlock().isOccluding();
         } else {
            BlockPosition blockposition = p_a_2_.down();
            if(!World.a((IBlockAccess)p_a_1_, (BlockPosition)blockposition)) {
               return false;
            } else {
               Block block1 = p_a_1_.getType(blockposition).getBlock();
               boolean flag = block1 != Blocks.BEDROCK && block1 != Blocks.BARRIER;
               return flag && !block.isOccluding() && !block.getMaterial().isLiquid() && !p_a_1_.getType(p_a_2_.up()).getBlock().isOccluding();
            }
         }
      }
   }

   public static void a(World p_a_0_, BiomeBase p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, Random p_a_6_) {
      List list = p_a_1_.getMobs(EnumCreatureType.CREATURE);
      if(!list.isEmpty()) {
         while(p_a_6_.nextFloat() < p_a_1_.g()) {
            BiomeBase.BiomeMeta biomebase$biomemeta = (BiomeBase.BiomeMeta)WeightedRandom.a(p_a_0_.random, list);
            int i = biomebase$biomemeta.c + p_a_6_.nextInt(1 + biomebase$biomemeta.d - biomebase$biomemeta.c);
            GroupDataEntity groupdataentity = null;
            int j = p_a_2_ + p_a_6_.nextInt(p_a_4_);
            int k = p_a_3_ + p_a_6_.nextInt(p_a_5_);
            int l = j;
            int i1 = k;

            for(int j1 = 0; j1 < i; ++j1) {
               boolean flag = false;

               for(int k1 = 0; !flag && k1 < 4; ++k1) {
                  BlockPosition blockposition = p_a_0_.r(new BlockPosition(j, 0, k));
                  if(a(EntityInsentient.EnumEntityPositionType.ON_GROUND, p_a_0_, blockposition)) {
                     EntityInsentient entityinsentient;
                     try {
                        entityinsentient = (EntityInsentient)biomebase$biomemeta.b.getConstructor(new Class[]{World.class}).newInstance(new Object[]{p_a_0_});
                     } catch (Exception exception) {
                        exception.printStackTrace();
                        continue;
                     }

                     entityinsentient.setPositionRotation((double)((float)j + 0.5F), (double)blockposition.getY(), (double)((float)k + 0.5F), p_a_6_.nextFloat() * 360.0F, 0.0F);
                     groupdataentity = entityinsentient.prepare(p_a_0_.E(new BlockPosition(entityinsentient)), groupdataentity);
                     p_a_0_.addEntity(entityinsentient, SpawnReason.CHUNK_GEN);
                     flag = true;
                  }

                  j += p_a_6_.nextInt(5) - p_a_6_.nextInt(5);

                  for(k += p_a_6_.nextInt(5) - p_a_6_.nextInt(5); j < p_a_2_ || j >= p_a_2_ + p_a_4_ || k < p_a_3_ || k >= p_a_3_ + p_a_4_; k = i1 + p_a_6_.nextInt(5) - p_a_6_.nextInt(5)) {
                     j = l + p_a_6_.nextInt(5) - p_a_6_.nextInt(5);
                  }
               }
            }
         }
      }

   }
}
