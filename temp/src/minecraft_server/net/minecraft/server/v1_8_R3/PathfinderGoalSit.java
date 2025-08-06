package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityTameableAnimal;
import net.minecraft.server.v1_8_R3.PathfinderGoal;

public class PathfinderGoalSit extends PathfinderGoal {
   private EntityTameableAnimal entity;
   private boolean willSit;

   public PathfinderGoalSit(EntityTameableAnimal p_i22_1_) {
      this.entity = p_i22_1_;
      this.a(5);
   }

   public boolean a() {
      if(!this.entity.isTamed()) {
         return this.willSit && this.entity.getGoalTarget() == null;
      } else if(this.entity.V()) {
         return false;
      } else if(!this.entity.onGround) {
         return false;
      } else {
         EntityLiving entityliving = this.entity.getOwner();
         return entityliving == null?true:(this.entity.h(entityliving) < 144.0D && entityliving.getLastDamager() != null?false:this.willSit);
      }
   }

   public void c() {
      this.entity.getNavigation().n();
      this.entity.setSitting(true);
   }

   public void d() {
      this.entity.setSitting(false);
   }

   public void setSitting(boolean p_setSitting_1_) {
      this.willSit = p_setSitting_1_;
   }
}
