package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.RandomPositionGenerator;
import net.minecraft.server.v1_8_R3.Vec3D;

public class PathfinderGoalRandomStroll extends PathfinderGoal {
   private EntityCreature a;
   private double b;
   private double c;
   private double d;
   private double e;
   private int f;
   private boolean g;

   public PathfinderGoalRandomStroll(EntityCreature p_i1187_1_, double p_i1187_2_) {
      this(p_i1187_1_, p_i1187_2_, 120);
   }

   public PathfinderGoalRandomStroll(EntityCreature p_i1188_1_, double p_i1188_2_, int p_i1188_4_) {
      this.a = p_i1188_1_;
      this.e = p_i1188_2_;
      this.f = p_i1188_4_;
      this.a(1);
   }

   public boolean a() {
      if(!this.g) {
         if(this.a.bh() >= 100) {
            return false;
         }

         if(this.a.bc().nextInt(this.f) != 0) {
            return false;
         }
      }

      Vec3D vec3d = RandomPositionGenerator.a(this.a, 10, 7);
      if(vec3d == null) {
         return false;
      } else {
         this.b = vec3d.a;
         this.c = vec3d.b;
         this.d = vec3d.c;
         this.g = false;
         return true;
      }
   }

   public boolean b() {
      return !this.a.getNavigation().m();
   }

   public void c() {
      this.a.getNavigation().a(this.b, this.c, this.d, this.e);
   }

   public void f() {
      this.g = true;
   }

   public void setTimeBetweenMovement(int p_setTimeBetweenMovement_1_) {
      this.f = p_setTimeBetweenMovement_1_;
   }
}
