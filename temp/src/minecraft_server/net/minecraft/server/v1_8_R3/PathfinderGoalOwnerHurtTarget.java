package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityTameableAnimal;
import net.minecraft.server.v1_8_R3.PathfinderGoalTarget;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class PathfinderGoalOwnerHurtTarget extends PathfinderGoalTarget {
   EntityTameableAnimal a;
   EntityLiving b;
   private int c;

   public PathfinderGoalOwnerHurtTarget(EntityTameableAnimal p_i495_1_) {
      super(p_i495_1_, false);
      this.a = p_i495_1_;
      this.a(1);
   }

   public boolean a() {
      if(!this.a.isTamed()) {
         return false;
      } else {
         EntityLiving entityliving = this.a.getOwner();
         if(entityliving == null) {
            return false;
         } else {
            this.b = entityliving.bf();
            int i = entityliving.bg();
            return i != this.c && this.a(this.b, false) && this.a.a(this.b, entityliving);
         }
      }
   }

   public void c() {
      this.e.setGoalTarget(this.b, TargetReason.OWNER_ATTACKED_TARGET, true);
      EntityLiving entityliving = this.a.getOwner();
      if(entityliving != null) {
         this.c = entityliving.bg();
      }

      super.c();
   }
}
