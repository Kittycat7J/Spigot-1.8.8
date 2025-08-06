package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.IAnimal;
import net.minecraft.server.v1_8_R3.World;

public abstract class EntityWaterAnimal extends EntityInsentient implements IAnimal {
   public EntityWaterAnimal(World p_i1212_1_) {
      super(p_i1212_1_);
   }

   public boolean aY() {
      return true;
   }

   public boolean bR() {
      return true;
   }

   public boolean canSpawn() {
      return this.world.a((AxisAlignedBB)this.getBoundingBox(), (Entity)this);
   }

   public int w() {
      return 120;
   }

   protected boolean isTypeNotPersistent() {
      return true;
   }

   protected int getExpValue(EntityHuman p_getExpValue_1_) {
      return 1 + this.world.random.nextInt(3);
   }

   public void K() {
      int i = this.getAirTicks();
      super.K();
      if(this.isAlive() && !this.V()) {
         --i;
         this.setAirTicks(i);
         if(this.getAirTicks() == -20) {
            this.setAirTicks(0);
            this.damageEntity(DamageSource.DROWN, 2.0F);
         }
      } else {
         this.setAirTicks(300);
      }

   }

   public boolean aL() {
      return false;
   }
}
