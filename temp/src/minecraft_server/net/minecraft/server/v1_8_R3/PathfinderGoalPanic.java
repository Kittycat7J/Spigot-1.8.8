package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.PathfinderGoal;
import net.minecraft.server.v1_8_R3.RandomPositionGenerator;
import net.minecraft.server.v1_8_R3.Vec3D;

public class PathfinderGoalPanic extends PathfinderGoal {
   private EntityCreature b;
   protected double a;
   private double c;
   private double d;
   private double e;

   public PathfinderGoalPanic(EntityCreature p_i361_1_, double p_i361_2_) {
      this.b = p_i361_1_;
      this.a = p_i361_2_;
      this.a(1);
   }

   public boolean a() {
      if(this.b.getLastDamager() == null && !this.b.isBurning()) {
         return false;
      } else {
         Vec3D vec3d = RandomPositionGenerator.a(this.b, 5, 4);
         if(vec3d == null) {
            return false;
         } else {
            this.c = vec3d.a;
            this.d = vec3d.b;
            this.e = vec3d.c;
            return true;
         }
      }
   }

   public void c() {
      this.b.getNavigation().a(this.c, this.d, this.e, this.a);
   }

   public boolean b() {
      if(this.b.ticksLived - this.b.hurtTimestamp > 100) {
         this.b.b((EntityLiving)null);
         return false;
      } else {
         return !this.b.getNavigation().m();
      }
   }
}
