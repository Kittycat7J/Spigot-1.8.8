package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;

public final class IEntitySelector {
   public static final Predicate<Entity> a = new Predicate<Entity>() {
      public boolean a(Entity p_a_1_) {
         return p_a_1_.isAlive();
      }
   };
   public static final Predicate<Entity> b = new Predicate<Entity>() {
      public boolean a(Entity p_a_1_) {
         return p_a_1_.isAlive() && p_a_1_.passenger == null && p_a_1_.vehicle == null;
      }
   };
   public static final Predicate<Entity> c = new Predicate<Entity>() {
      public boolean a(Entity p_a_1_) {
         return p_a_1_ instanceof IInventory && p_a_1_.isAlive();
      }
   };
   public static final Predicate<Entity> d = new Predicate<Entity>() {
      public boolean a(Entity p_a_1_) {
         return !(p_a_1_ instanceof EntityHuman) || !((EntityHuman)p_a_1_).isSpectator();
      }
   };

   public static class EntitySelectorEquipable implements Predicate<Entity> {
      private final ItemStack a;

      public EntitySelectorEquipable(ItemStack p_i1145_1_) {
         this.a = p_i1145_1_;
      }

      public boolean a(Entity p_a_1_) {
         if(!p_a_1_.isAlive()) {
            return false;
         } else if(!(p_a_1_ instanceof EntityLiving)) {
            return false;
         } else {
            EntityLiving entityliving = (EntityLiving)p_a_1_;
            return entityliving.getEquipment(EntityInsentient.c(this.a)) != null?false:(entityliving instanceof EntityInsentient?((EntityInsentient)entityliving).bY():(entityliving instanceof EntityArmorStand?true:entityliving instanceof EntityHuman));
         }
      }
   }
}
