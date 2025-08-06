package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPortal;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.LongHashMap;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.ShapeDetector;
import net.minecraft.server.v1_8_R3.WorldServer;
import org.bukkit.Location;
import org.bukkit.World.Environment;
import org.bukkit.event.entity.EntityPortalExitEvent;
import org.bukkit.util.Vector;

public class PortalTravelAgent {
   private final WorldServer a;
   private final Random b;
   private final LongHashMap<PortalTravelAgent.ChunkCoordinatesPortal> c = new LongHashMap();
   private final List<Long> d = Lists.<Long>newArrayList();

   public PortalTravelAgent(WorldServer p_i465_1_) {
      this.a = p_i465_1_;
      this.b = new Random(p_i465_1_.getSeed());
   }

   public void a(Entity p_a_1_, float p_a_2_) {
      if(this.a.worldProvider.getDimension() != 1) {
         if(!this.b(p_a_1_, p_a_2_)) {
            this.a(p_a_1_);
            this.b(p_a_1_, p_a_2_);
         }
      } else {
         MathHelper.floor(p_a_1_.locX);
         MathHelper.floor(p_a_1_.locY);
         MathHelper.floor(p_a_1_.locZ);
         BlockPosition blockposition = this.createEndPortal(p_a_1_.locX, p_a_1_.locY, p_a_1_.locZ);
         p_a_1_.setPositionRotation((double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ(), p_a_1_.yaw, 0.0F);
         p_a_1_.motX = p_a_1_.motY = p_a_1_.motZ = 0.0D;
      }

   }

   private BlockPosition createEndPortal(double p_createEndPortal_1_, double p_createEndPortal_3_, double p_createEndPortal_5_) {
      int i = MathHelper.floor(p_createEndPortal_1_);
      int j = MathHelper.floor(p_createEndPortal_3_) - 1;
      int k = MathHelper.floor(p_createEndPortal_5_);
      byte b0 = 1;
      byte b1 = 0;

      for(int l = -2; l <= 2; ++l) {
         for(int i1 = -2; i1 <= 2; ++i1) {
            for(int j1 = -1; j1 < 3; ++j1) {
               int k1 = i + i1 * b0 + l * b1;
               int l1 = j + j1;
               int i2 = k + i1 * b1 - l * b0;
               boolean flag = j1 < 0;
               this.a.setTypeUpdate(new BlockPosition(k1, l1, i2), flag?Blocks.OBSIDIAN.getBlockData():Blocks.AIR.getBlockData());
            }
         }
      }

      return new BlockPosition(i, k, k);
   }

   private BlockPosition findEndPortal(BlockPosition p_findEndPortal_1_) {
      int i = p_findEndPortal_1_.getX();
      int j = p_findEndPortal_1_.getY() - 1;
      int k = p_findEndPortal_1_.getZ();
      byte b0 = 1;
      byte b1 = 0;

      for(int l = -2; l <= 2; ++l) {
         for(int i1 = -2; i1 <= 2; ++i1) {
            for(int j1 = -1; j1 < 3; ++j1) {
               int k1 = i + i1 * b0 + l * b1;
               int l1 = j + j1;
               int i2 = k + i1 * b1 - l * b0;
               boolean flag = j1 < 0;
               if(this.a.getType(new BlockPosition(k1, l1, i2)).getBlock() != (flag?Blocks.OBSIDIAN:Blocks.AIR)) {
                  return null;
               }
            }
         }
      }

      return new BlockPosition(i, j, k);
   }

   public boolean b(Entity p_b_1_, float p_b_2_) {
      BlockPosition blockposition = this.findPortal(p_b_1_.locX, p_b_1_.locY, p_b_1_.locZ, 128);
      if(blockposition == null) {
         return false;
      } else {
         Location location = new Location(this.a.getWorld(), (double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ(), p_b_2_, p_b_1_.pitch);
         Vector vector = p_b_1_.getBukkitEntity().getVelocity();
         this.adjustExit(p_b_1_, location, vector);
         p_b_1_.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
         if(p_b_1_.motX != vector.getX() || p_b_1_.motY != vector.getY() || p_b_1_.motZ != vector.getZ()) {
            p_b_1_.getBukkitEntity().setVelocity(vector);
         }

         return true;
      }
   }

   public BlockPosition findPortal(double p_findPortal_1_, double p_findPortal_3_, double p_findPortal_5_, int p_findPortal_7_) {
      if(this.a.getWorld().getEnvironment() == Environment.THE_END) {
         return this.findEndPortal(this.a.worldProvider.h());
      } else {
         double d0 = -1.0D;
         int i = MathHelper.floor(p_findPortal_1_);
         int j = MathHelper.floor(p_findPortal_5_);
         boolean flag = true;
         Object object = BlockPosition.ZERO;
         long k = ChunkCoordIntPair.a(i, j);
         if(this.c.contains(k)) {
            PortalTravelAgent.ChunkCoordinatesPortal portaltravelagent$chunkcoordinatesportal = (PortalTravelAgent.ChunkCoordinatesPortal)this.c.getEntry(k);
            d0 = 0.0D;
            object = portaltravelagent$chunkcoordinatesportal;
            portaltravelagent$chunkcoordinatesportal.c = this.a.getTime();
            flag = false;
         } else {
            BlockPosition blockposition2 = new BlockPosition(p_findPortal_1_, p_findPortal_3_, p_findPortal_5_);

            for(int l = -128; l <= 128; ++l) {
               BlockPosition blockposition1;
               for(int i1 = -128; i1 <= 128; ++i1) {
                  for(BlockPosition blockposition = blockposition2.a(l, this.a.V() - 1 - blockposition2.getY(), i1); blockposition.getY() >= 0; blockposition = blockposition1) {
                     blockposition1 = blockposition.down();
                     if(this.a.getType(blockposition).getBlock() == Blocks.PORTAL) {
                        while(this.a.getType(blockposition1 = blockposition.down()).getBlock() == Blocks.PORTAL) {
                           blockposition = blockposition1;
                        }

                        double d1 = blockposition.i(blockposition2);
                        if(d0 < 0.0D || d1 < d0) {
                           d0 = d1;
                           object = blockposition;
                        }
                     }
                  }
               }
            }
         }

         if(d0 >= 0.0D) {
            if(flag) {
               this.c.put(k, new PortalTravelAgent.ChunkCoordinatesPortal((BlockPosition)object, this.a.getTime()));
               this.d.add(Long.valueOf(k));
            }

            return (BlockPosition)object;
         } else {
            return null;
         }
      }
   }

   public void adjustExit(Entity p_adjustExit_1_, Location p_adjustExit_2_, Vector p_adjustExit_3_) {
      Location location = p_adjustExit_2_.clone();
      Vector vector = p_adjustExit_3_.clone();
      BlockPosition blockposition = new BlockPosition(p_adjustExit_2_.getBlockX(), p_adjustExit_2_.getBlockY(), p_adjustExit_2_.getBlockZ());
      float f = p_adjustExit_2_.getYaw();
      if(this.a.getWorld().getEnvironment() != Environment.THE_END && p_adjustExit_1_.getBukkitEntity().getWorld().getEnvironment() != Environment.THE_END && p_adjustExit_1_.aG() != null) {
         double d0 = (double)blockposition.getX() + 0.5D;
         double d1 = (double)blockposition.getY() + 0.5D;
         double d2 = (double)blockposition.getZ() + 0.5D;
         ShapeDetector.ShapeDetectorCollection shapedetector$shapedetectorcollection = Blocks.PORTAL.f(this.a, blockposition);
         boolean flag = shapedetector$shapedetectorcollection.b().e().c() == EnumDirection.EnumAxisDirection.NEGATIVE;
         double d3 = shapedetector$shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X?(double)shapedetector$shapedetectorcollection.a().getZ():(double)shapedetector$shapedetectorcollection.a().getX();
         d1 = (double)(shapedetector$shapedetectorcollection.a().getY() + 1) - p_adjustExit_1_.aG().b * (double)shapedetector$shapedetectorcollection.e();
         if(flag) {
            ++d3;
         }

         if(shapedetector$shapedetectorcollection.b().k() == EnumDirection.EnumAxis.X) {
            d2 = d3 + (1.0D - p_adjustExit_1_.aG().a) * (double)shapedetector$shapedetectorcollection.d() * (double)shapedetector$shapedetectorcollection.b().e().c().a();
         } else {
            d0 = d3 + (1.0D - p_adjustExit_1_.aG().a) * (double)shapedetector$shapedetectorcollection.d() * (double)shapedetector$shapedetectorcollection.b().e().c().a();
         }

         float f1 = 0.0F;
         float f2 = 0.0F;
         float f3 = 0.0F;
         float f4 = 0.0F;
         if(shapedetector$shapedetectorcollection.b().opposite() == p_adjustExit_1_.aH()) {
            f1 = 1.0F;
            f2 = 1.0F;
         } else if(shapedetector$shapedetectorcollection.b().opposite() == p_adjustExit_1_.aH().opposite()) {
            f1 = -1.0F;
            f2 = -1.0F;
         } else if(shapedetector$shapedetectorcollection.b().opposite() == p_adjustExit_1_.aH().e()) {
            f3 = 1.0F;
            f4 = -1.0F;
         } else {
            f3 = -1.0F;
            f4 = 1.0F;
         }

         double d4 = p_adjustExit_3_.getX();
         double d5 = p_adjustExit_3_.getZ();
         p_adjustExit_3_.setX(d4 * (double)f1 + d5 * (double)f4);
         p_adjustExit_3_.setZ(d4 * (double)f3 + d5 * (double)f2);
         f = f - (float)(p_adjustExit_1_.aH().opposite().b() * 90) + (float)(shapedetector$shapedetectorcollection.b().b() * 90);
         p_adjustExit_2_.setX(d0);
         p_adjustExit_2_.setY(d1);
         p_adjustExit_2_.setZ(d2);
         p_adjustExit_2_.setYaw(f);
      } else {
         p_adjustExit_2_.setPitch(0.0F);
         p_adjustExit_3_.setX(0);
         p_adjustExit_3_.setY(0);
         p_adjustExit_3_.setZ(0);
      }

      EntityPortalExitEvent entityportalexitevent = new EntityPortalExitEvent(p_adjustExit_1_.getBukkitEntity(), location, p_adjustExit_2_, vector, p_adjustExit_3_);
      this.a.getServer().getPluginManager().callEvent(entityportalexitevent);
      Location location1 = entityportalexitevent.getTo();
      if(!entityportalexitevent.isCancelled() && location1 != null && p_adjustExit_1_.isAlive()) {
         p_adjustExit_2_.setX(location1.getX());
         p_adjustExit_2_.setY(location1.getY());
         p_adjustExit_2_.setZ(location1.getZ());
         p_adjustExit_2_.setYaw(location1.getYaw());
         p_adjustExit_2_.setPitch(location1.getPitch());
         p_adjustExit_3_.copy(entityportalexitevent.getAfter());
      } else {
         p_adjustExit_2_.setX(location.getX());
         p_adjustExit_2_.setY(location.getY());
         p_adjustExit_2_.setZ(location.getZ());
         p_adjustExit_2_.setYaw(location.getYaw());
         p_adjustExit_2_.setPitch(location.getPitch());
         p_adjustExit_3_.copy(vector);
      }

   }

   public boolean a(Entity p_a_1_) {
      return this.createPortal(p_a_1_.locX, p_a_1_.locY, p_a_1_.locZ, 16);
   }

   public boolean createPortal(double p_createPortal_1_, double p_createPortal_3_, double p_createPortal_5_, int p_createPortal_7_) {
      if(this.a.getWorld().getEnvironment() == Environment.THE_END) {
         this.createEndPortal(p_createPortal_1_, p_createPortal_3_, p_createPortal_5_);
         return true;
      } else {
         double d0 = -1.0D;
         int i = MathHelper.floor(p_createPortal_1_);
         int j = MathHelper.floor(p_createPortal_3_);
         int k = MathHelper.floor(p_createPortal_5_);
         int l = i;
         int i1 = j;
         int j1 = k;
         int k1 = 0;
         int l1 = this.b.nextInt(4);
         BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int i2 = i - p_createPortal_7_; i2 <= i + p_createPortal_7_; ++i2) {
            double d1 = (double)i2 + 0.5D - p_createPortal_1_;

            for(int j2 = k - p_createPortal_7_; j2 <= k + p_createPortal_7_; ++j2) {
               double d2 = (double)j2 + 0.5D - p_createPortal_5_;

               label142:
               for(int k2 = this.a.V() - 1; k2 >= 0; --k2) {
                  if(this.a.isEmpty(blockposition$mutableblockposition.c(i2, k2, j2))) {
                     while(k2 > 0 && this.a.isEmpty(blockposition$mutableblockposition.c(i2, k2 - 1, j2))) {
                        --k2;
                     }

                     for(int l2 = l1; l2 < l1 + 4; ++l2) {
                        int i3 = l2 % 2;
                        int j3 = 1 - i3;
                        if(l2 % 4 >= 2) {
                           i3 = -i3;
                           j3 = -j3;
                        }

                        for(int k3 = 0; k3 < 3; ++k3) {
                           for(int l3 = 0; l3 < 4; ++l3) {
                              for(int i4 = -1; i4 < 4; ++i4) {
                                 int j4 = i2 + (l3 - 1) * i3 + k3 * j3;
                                 int k4 = k2 + i4;
                                 int l4 = j2 + (l3 - 1) * j3 - k3 * i3;
                                 blockposition$mutableblockposition.c(j4, k4, l4);
                                 if(i4 < 0 && !this.a.getType(blockposition$mutableblockposition).getBlock().getMaterial().isBuildable() || i4 >= 0 && !this.a.isEmpty(blockposition$mutableblockposition)) {
                                    continue label142;
                                 }
                              }
                           }
                        }

                        double d3 = (double)k2 + 0.5D - p_createPortal_3_;
                        double d4 = d1 * d1 + d3 * d3 + d2 * d2;
                        if(d0 < 0.0D || d4 < d0) {
                           d0 = d4;
                           l = i2;
                           i1 = k2;
                           j1 = j2;
                           k1 = l2 % 4;
                        }
                     }
                  }
               }
            }
         }

         if(d0 < 0.0D) {
            for(int l51 = i - p_createPortal_7_; l51 <= i + p_createPortal_7_; ++l51) {
               double d5 = (double)l51 + 0.5D - p_createPortal_1_;

               for(int i6 = k - p_createPortal_7_; i6 <= k + p_createPortal_7_; ++i6) {
                  double d6 = (double)i6 + 0.5D - p_createPortal_5_;

                  label559:
                  for(int k6 = this.a.V() - 1; k6 >= 0; --k6) {
                     if(this.a.isEmpty(blockposition$mutableblockposition.c(l51, k6, i6))) {
                        while(k6 > 0 && this.a.isEmpty(blockposition$mutableblockposition.c(l51, k6 - 1, i6))) {
                           --k6;
                        }

                        for(int i7 = l1; i7 < l1 + 2; ++i7) {
                           int l7 = i7 % 2;
                           int l8 = 1 - l7;

                           for(int l9 = 0; l9 < 4; ++l9) {
                              for(int l10 = -1; l10 < 4; ++l10) {
                                 int l11 = l51 + (l9 - 1) * l7;
                                 int k12 = k6 + l10;
                                 int l12 = i6 + (l9 - 1) * l8;
                                 blockposition$mutableblockposition.c(l11, k12, l12);
                                 if(l10 < 0 && !this.a.getType(blockposition$mutableblockposition).getBlock().getMaterial().isBuildable() || l10 >= 0 && !this.a.isEmpty(blockposition$mutableblockposition)) {
                                    continue label559;
                                 }
                              }
                           }

                           double d7 = (double)k6 + 0.5D - p_createPortal_3_;
                           double d8 = d5 * d5 + d7 * d7 + d6 * d6;
                           if(d0 < 0.0D || d8 < d0) {
                              d0 = d8;
                              l = l51;
                              i1 = k6;
                              j1 = i6;
                              k1 = i7 % 2;
                           }
                        }
                     }
                  }
               }
            }
         }

         int i13 = l;
         int i5 = i1;
         int j6 = j1;
         int j5 = k1 % 2;
         int k5 = 1 - j5;
         if(k1 % 4 >= 2) {
            j5 = -j5;
            k5 = -k5;
         }

         if(d0 < 0.0D) {
            i1 = MathHelper.clamp(i1, 70, this.a.V() - 10);
            i5 = i1;

            for(int l6 = -1; l6 <= 1; ++l6) {
               for(int j7 = 1; j7 < 3; ++j7) {
                  for(int i8 = -1; i8 < 3; ++i8) {
                     int i9 = i13 + (j7 - 1) * j5 + l6 * k5;
                     int i10 = i5 + i8;
                     int i11 = j6 + (j7 - 1) * k5 - l6 * j5;
                     boolean flag = i8 < 0;
                     this.a.setTypeUpdate(new BlockPosition(i9, i10, i11), flag?Blocks.OBSIDIAN.getBlockData():Blocks.AIR.getBlockData());
                  }
               }
            }
         }

         IBlockData iblockdata = Blocks.PORTAL.getBlockData().set(BlockPortal.AXIS, j5 != 0?EnumDirection.EnumAxis.X:EnumDirection.EnumAxis.Z);

         for(int k7 = 0; k7 < 4; ++k7) {
            for(int j8 = 0; j8 < 4; ++j8) {
               for(int j9 = -1; j9 < 4; ++j9) {
                  int j10 = i13 + (j8 - 1) * j5;
                  int j11 = i5 + j9;
                  int i12 = j6 + (j8 - 1) * k5;
                  boolean flag1 = j8 == 0 || j8 == 3 || j9 == -1 || j9 == 3;
                  this.a.setTypeAndData(new BlockPosition(j10, j11, i12), flag1?Blocks.OBSIDIAN.getBlockData():iblockdata, 2);
               }
            }

            for(int k8 = 0; k8 < 4; ++k8) {
               for(int k9 = -1; k9 < 4; ++k9) {
                  int k10 = i13 + (k8 - 1) * j5;
                  int k11 = i5 + k9;
                  int j12 = j6 + (k8 - 1) * k5;
                  BlockPosition blockposition = new BlockPosition(k10, k11, j12);
                  this.a.applyPhysics(blockposition, this.a.getType(blockposition).getBlock());
               }
            }
         }

         return true;
      }
   }

   public void a(long p_a_1_) {
      if(p_a_1_ % 100L == 0L) {
         Iterator iterator = this.d.iterator();
         long i = p_a_1_ - 300L;

         while(iterator.hasNext()) {
            Long olong = (Long)iterator.next();
            PortalTravelAgent.ChunkCoordinatesPortal portaltravelagent$chunkcoordinatesportal = (PortalTravelAgent.ChunkCoordinatesPortal)this.c.getEntry(olong.longValue());
            if(portaltravelagent$chunkcoordinatesportal == null || portaltravelagent$chunkcoordinatesportal.c < i) {
               iterator.remove();
               this.c.remove(olong.longValue());
            }
         }
      }

   }

   public class ChunkCoordinatesPortal extends BlockPosition {
      public long c;

      public ChunkCoordinatesPortal(BlockPosition p_i319_2_, long p_i319_3_) {
         super(p_i319_2_.getX(), p_i319_2_.getY(), p_i319_2_.getZ());
         this.c = p_i319_3_;
      }
   }
}
