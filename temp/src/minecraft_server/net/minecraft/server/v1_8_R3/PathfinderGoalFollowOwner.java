package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityTameableAnimal;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.World;

public class PathfinderGoalFollowOwner extends PathfinderGoal {
   private EntityTameableAnimal d;
   private EntityLiving e;
   World a;
   private double f;
   private NavigationAbstract g;
   private int h;
   float b;
   float c;
   private boolean i;

   public PathfinderGoalFollowOwner(EntityTameableAnimal p_i1166_1_, double p_i1166_2_, float p_i1166_4_, float p_i1166_5_) {
      this.d = p_i1166_1_;
      this.a = p_i1166_1_.world;
      this.f = p_i1166_2_;
      this.g = p_i1166_1_.getNavigation();
      this.c = p_i1166_4_;
      this.b = p_i1166_5_;
      this.a(3);
      if(!(p_i1166_1_.getNavigation() instanceof Navigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   public boolean a() {
      EntityLiving entityliving = this.d.getOwner();
      if(entityliving == null) {
         return false;
      } else if(entityliving instanceof EntityHuman && ((EntityHuman)entityliving).isSpectator()) {
         return false;
      } else if(this.d.isSitting()) {
         return false;
      } else if(this.d.h(entityliving) < (double)(this.c * this.c)) {
         return false;
      } else {
         this.e = entityliving;
         return true;
      }
   }

   public boolean b() {
      return !this.g.m() && this.d.h(this.e) > (double)(this.b * this.b) && !this.d.isSitting();
   }

   public void c() {
      this.h = 0;
      this.i = ((Navigation)this.d.getNavigation()).e();
      ((Navigation)this.d.getNavigation()).a(false);
   }

   public void d() {
      this.e = null;
      this.g.n();
      ((Navigation)this.d.getNavigation()).a(true);
   }

   private boolean a(BlockPosition p_a_1_) {
      IBlockData iblockdata = this.a.getType(p_a_1_);
      Block block = iblockdata.getBlock();
      return block == Blocks.AIR?true:!block.d();
   }

   public void e() {
      this.d.getControllerLook().a(this.e, 10.0F, (float)this.d.bQ());
      if(!this.d.isSitting()) {
         if(--this.h <= 0) {
            this.h = 10;
            if(!this.g.a((Entity)this.e, this.f)) {
               if(!this.d.cc()) {
                  if(this.d.h(this.e) >= 144.0D) {
                     int ix = MathHelper.floor(this.e.locX) - 2;
                     int j = MathHelper.floor(this.e.locZ) - 2;
                     int k = MathHelper.floor(this.e.getBoundingBox().b);

                     for(int l = 0; l <= 4; ++l) {
                        for(int i1 = 0; i1 <= 4; ++i1) {
                           if((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.a((IBlockAccess)this.a, (BlockPosition)(new BlockPosition(ix + l, k - 1, j + i1))) && this.a(new BlockPosition(ix + l, k, j + i1)) && this.a(new BlockPosition(ix + l, k + 1, j + i1))) {
                              this.d.setPositionRotation((double)((float)(ix + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.d.yaw, this.d.pitch);
                              this.g.n();
                              return;
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
