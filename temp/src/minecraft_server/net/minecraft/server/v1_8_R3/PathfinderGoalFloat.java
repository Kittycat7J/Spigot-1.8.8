package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathfinderGoal;

public class PathfinderGoalFloat extends PathfinderGoal {
   private EntityInsentient a;

   public PathfinderGoalFloat(EntityInsentient p_i1165_1_) {
      this.a = p_i1165_1_;
      this.a(4);
      ((Navigation)p_i1165_1_.getNavigation()).d(true);
   }

   public boolean a() {
      return this.a.V() || this.a.ab();
   }

   public void e() {
      if(this.a.bc().nextFloat() < 0.8F) {
         this.a.getControllerJump().a();
      }

   }
}
