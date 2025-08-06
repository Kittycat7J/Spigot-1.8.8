package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathEntity;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.World;

public class PathfinderGoalMeleeAttack extends PathfinderGoal {
   World a;
   protected EntityCreature b;
   int c;
   double d;
   boolean e;
   PathEntity f;
   Class<? extends Entity> g;
   private int h;
   private double i;
   private double j;
   private double k;

   public PathfinderGoalMeleeAttack(EntityCreature p_i1174_1_, Class<? extends Entity> p_i1174_2_, double p_i1174_3_, boolean p_i1174_5_) {
      this(p_i1174_1_, p_i1174_3_, p_i1174_5_);
      this.g = p_i1174_2_;
   }

   public PathfinderGoalMeleeAttack(EntityCreature p_i1175_1_, double p_i1175_2_, boolean p_i1175_4_) {
      this.b = p_i1175_1_;
      this.a = p_i1175_1_.world;
      this.d = p_i1175_2_;
      this.e = p_i1175_4_;
      this.a(3);
   }

   public boolean a() {
      EntityLiving entityliving = this.b.getGoalTarget();
      if(entityliving == null) {
         return false;
      } else if(!entityliving.isAlive()) {
         return false;
      } else if(this.g != null && !this.g.isAssignableFrom(entityliving.getClass())) {
         return false;
      } else {
         this.f = this.b.getNavigation().a((Entity)entityliving);
         return this.f != null;
      }
   }

   public boolean b() {
      EntityLiving entityliving = this.b.getGoalTarget();
      return entityliving == null?false:(!entityliving.isAlive()?false:(!this.e?!this.b.getNavigation().m():this.b.e(new BlockPosition(entityliving))));
   }

   public void c() {
      this.b.getNavigation().a(this.f, this.d);
      this.h = 0;
   }

   public void d() {
      this.b.getNavigation().n();
   }

   public void e() {
      EntityLiving entityliving = this.b.getGoalTarget();
      this.b.getControllerLook().a(entityliving, 30.0F, 30.0F);
      double d0 = this.b.e(entityliving.locX, entityliving.getBoundingBox().b, entityliving.locZ);
      double d1 = this.a(entityliving);
      --this.h;
      if((this.e || this.b.getEntitySenses().a(entityliving)) && this.h <= 0 && (this.i == 0.0D && this.j == 0.0D && this.k == 0.0D || entityliving.e(this.i, this.j, this.k) >= 1.0D || this.b.bc().nextFloat() < 0.05F)) {
         this.i = entityliving.locX;
         this.j = entityliving.getBoundingBox().b;
         this.k = entityliving.locZ;
         this.h = 4 + this.b.bc().nextInt(7);
         if(d0 > 1024.0D) {
            this.h += 10;
         } else if(d0 > 256.0D) {
            this.h += 5;
         }

         if(!this.b.getNavigation().a((Entity)entityliving, this.d)) {
            this.h += 15;
         }
      }

      this.c = Math.max(this.c - 1, 0);
      if(d0 <= d1 && this.c <= 0) {
         this.c = 20;
         if(this.b.bA() != null) {
            this.b.bw();
         }

         this.b.r(entityliving);
      }

   }

   protected double a(EntityLiving p_a_1_) {
      return (double)(this.b.width * 2.0F * this.b.width * 2.0F + p_a_1_.width);
   }
}
