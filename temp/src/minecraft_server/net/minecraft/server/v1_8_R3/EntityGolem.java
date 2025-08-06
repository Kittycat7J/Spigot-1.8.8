package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityCreature;
import net.minecraft.server.v1_8_R3.IAnimal;
import net.minecraft.server.v1_8_R3.World;

public abstract class EntityGolem extends EntityCreature implements IAnimal {
   public EntityGolem(World p_i1211_1_) {
      super(p_i1211_1_);
   }

   public void e(float p_e_1_, float p_e_2_) {
   }

   protected String z() {
      return "none";
   }

   protected String bo() {
      return "none";
   }

   protected String bp() {
      return "none";
   }

   public int w() {
      return 120;
   }

   protected boolean isTypeNotPersistent() {
      return false;
   }
}
