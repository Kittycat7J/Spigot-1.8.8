package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.IAnimal;
import net.minecraft.server.v1_8_R3.World;

public abstract class EntityAmbient extends EntityInsentient implements IAnimal {
   public EntityAmbient(World p_i1209_1_) {
      super(p_i1209_1_);
   }

   public boolean cb() {
      return false;
   }

   protected boolean a(EntityHuman p_a_1_) {
      return false;
   }
}
