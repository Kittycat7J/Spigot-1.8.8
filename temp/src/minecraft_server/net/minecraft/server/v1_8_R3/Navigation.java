package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityChicken;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityZombie;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.PathPoint;
import net.minecraft.server.v1_8_R3.Pathfinder;
import net.minecraft.server.v1_8_R3.PathfinderNormal;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

public class Navigation extends NavigationAbstract {
   protected PathfinderNormal a;
   private boolean f;

   public Navigation(EntityInsentient p_i1200_1_, World p_i1200_2_) {
      super(p_i1200_1_, p_i1200_2_);
   }

   protected Pathfinder a() {
      this.a = new PathfinderNormal();
      this.a.a(true);
      return new Pathfinder(this.a);
   }

   protected boolean b() {
      return this.b.onGround || this.h() && this.o() || this.b.au() && this.b instanceof EntityZombie && this.b.vehicle instanceof EntityChicken;
   }

   protected Vec3D c() {
      return new Vec3D(this.b.locX, (double)this.p(), this.b.locZ);
   }

   private int p() {
      if(this.b.V() && this.h()) {
         int i = (int)this.b.getBoundingBox().b;
         Block block = this.c.getType(new BlockPosition(MathHelper.floor(this.b.locX), i, MathHelper.floor(this.b.locZ))).getBlock();
         int j = 0;

         while(block == Blocks.FLOWING_WATER || block == Blocks.WATER) {
            ++i;
            block = this.c.getType(new BlockPosition(MathHelper.floor(this.b.locX), i, MathHelper.floor(this.b.locZ))).getBlock();
            ++j;
            if(j > 16) {
               return (int)this.b.getBoundingBox().b;
            }
         }

         return i;
      } else {
         return (int)(this.b.getBoundingBox().b + 0.5D);
      }
   }

   protected void d() {
      super.d();
      if(this.f) {
         if(this.c.i(new BlockPosition(MathHelper.floor(this.b.locX), (int)(this.b.getBoundingBox().b + 0.5D), MathHelper.floor(this.b.locZ)))) {
            return;
         }

         for(int i = 0; i < this.d.d(); ++i) {
            PathPoint pathpoint = this.d.a(i);
            if(this.c.i(new BlockPosition(pathpoint.a, pathpoint.b, pathpoint.c))) {
               this.d.b(i - 1);
               return;
            }
         }
      }

   }

   protected boolean a(Vec3D p_a_1_, Vec3D p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_) {
      int i = MathHelper.floor(p_a_1_.a);
      int j = MathHelper.floor(p_a_1_.c);
      double d0 = p_a_2_.a - p_a_1_.a;
      double d1 = p_a_2_.c - p_a_1_.c;
      double d2 = d0 * d0 + d1 * d1;
      if(d2 < 1.0E-8D) {
         return false;
      } else {
         double d3 = 1.0D / Math.sqrt(d2);
         d0 = d0 * d3;
         d1 = d1 * d3;
         p_a_3_ = p_a_3_ + 2;
         p_a_5_ = p_a_5_ + 2;
         if(!this.a(i, (int)p_a_1_.b, j, p_a_3_, p_a_4_, p_a_5_, p_a_1_, d0, d1)) {
            return false;
         } else {
            p_a_3_ = p_a_3_ - 2;
            p_a_5_ = p_a_5_ - 2;
            double d4 = 1.0D / Math.abs(d0);
            double d5 = 1.0D / Math.abs(d1);
            double d6 = (double)(i * 1) - p_a_1_.a;
            double d7 = (double)(j * 1) - p_a_1_.c;
            if(d0 >= 0.0D) {
               ++d6;
            }

            if(d1 >= 0.0D) {
               ++d7;
            }

            d6 = d6 / d0;
            d7 = d7 / d1;
            int k = d0 < 0.0D?-1:1;
            int l = d1 < 0.0D?-1:1;
            int i1 = MathHelper.floor(p_a_2_.a);
            int j1 = MathHelper.floor(p_a_2_.c);
            int k1 = i1 - i;
            int l1 = j1 - j;

            while(k1 * k > 0 || l1 * l > 0) {
               if(d6 < d7) {
                  d6 += d4;
                  i += k;
                  k1 = i1 - i;
               } else {
                  d7 += d5;
                  j += l;
                  l1 = j1 - j;
               }

               if(!this.a(i, (int)p_a_1_.b, j, p_a_3_, p_a_4_, p_a_5_, p_a_1_, d0, d1)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, Vec3D p_a_7_, double p_a_8_, double p_a_10_) {
      int i = p_a_1_ - p_a_4_ / 2;
      int j = p_a_3_ - p_a_6_ / 2;
      if(!this.b(i, p_a_2_, j, p_a_4_, p_a_5_, p_a_6_, p_a_7_, p_a_8_, p_a_10_)) {
         return false;
      } else {
         for(int k = i; k < i + p_a_4_; ++k) {
            for(int l = j; l < j + p_a_6_; ++l) {
               double d0 = (double)k + 0.5D - p_a_7_.a;
               double d1 = (double)l + 0.5D - p_a_7_.c;
               if(d0 * p_a_8_ + d1 * p_a_10_ >= 0.0D) {
                  Block block = this.c.getType(new BlockPosition(k, p_a_2_ - 1, l)).getBlock();
                  Material material = block.getMaterial();
                  if(material == Material.AIR) {
                     return false;
                  }

                  if(material == Material.WATER && !this.b.V()) {
                     return false;
                  }

                  if(material == Material.LAVA) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean b(int p_b_1_, int p_b_2_, int p_b_3_, int p_b_4_, int p_b_5_, int p_b_6_, Vec3D p_b_7_, double p_b_8_, double p_b_10_) {
      for(BlockPosition blockposition : BlockPosition.a(new BlockPosition(p_b_1_, p_b_2_, p_b_3_), new BlockPosition(p_b_1_ + p_b_4_ - 1, p_b_2_ + p_b_5_ - 1, p_b_3_ + p_b_6_ - 1))) {
         double d0 = (double)blockposition.getX() + 0.5D - p_b_7_.a;
         double d1 = (double)blockposition.getZ() + 0.5D - p_b_7_.c;
         if(d0 * p_b_8_ + d1 * p_b_10_ >= 0.0D) {
            Block block = this.c.getType(blockposition).getBlock();
            if(!block.b(this.c, blockposition)) {
               return false;
            }
         }
      }

      return true;
   }

   public void a(boolean p_a_1_) {
      this.a.c(p_a_1_);
   }

   public boolean e() {
      return this.a.e();
   }

   public void b(boolean p_b_1_) {
      this.a.b(p_b_1_);
   }

   public void c(boolean p_c_1_) {
      this.a.a(p_c_1_);
   }

   public boolean g() {
      return this.a.b();
   }

   public void d(boolean p_d_1_) {
      this.a.d(p_d_1_);
   }

   public boolean h() {
      return this.a.d();
   }

   public void e(boolean p_e_1_) {
      this.f = p_e_1_;
   }
}
