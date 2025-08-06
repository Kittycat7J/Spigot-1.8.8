package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoalDoorInteract;

public class PathfinderGoalOpenDoor extends PathfinderGoalDoorInteract {
   boolean g;
   int h;

   public PathfinderGoalOpenDoor(EntityInsentient p_i1184_1_, boolean p_i1184_2_) {
      super(p_i1184_1_);
      this.a = p_i1184_1_;
      this.g = p_i1184_2_;
   }

   public boolean b() {
      return this.g && this.h > 0 && super.b();
   }

   public void c() {
      this.h = 20;
      this.c.setDoor(this.a.world, this.b, true);
   }

   public void d() {
      if(this.g) {
         this.c.setDoor(this.a.world, this.b, false);
      }

   }

   public void e() {
      --this.h;
      super.e();
   }
}
