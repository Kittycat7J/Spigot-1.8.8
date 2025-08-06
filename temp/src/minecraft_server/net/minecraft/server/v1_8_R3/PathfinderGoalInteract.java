package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;

public class PathfinderGoalInteract extends PathfinderGoalLookAtPlayer {
   public PathfinderGoalInteract(EntityInsentient p_i1169_1_, Class<? extends Entity> p_i1169_2_, float p_i1169_3_, float p_i1169_4_) {
      super(p_i1169_1_, p_i1169_2_, p_i1169_3_, p_i1169_4_);
      this.a(3);
   }
}
