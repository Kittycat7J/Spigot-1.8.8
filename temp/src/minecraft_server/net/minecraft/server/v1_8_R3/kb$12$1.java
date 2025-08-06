package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.DispenseBehaviorProjectile;
import net.minecraft.server.v1_8_R3.EntityPotion;
import net.minecraft.server.v1_8_R3.IPosition;
import net.minecraft.server.v1_8_R3.IProjectile;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

class kb$12$1 extends DispenseBehaviorProjectile {
   kb$12$1(Object p_i1057_1_, ItemStack p_i1057_2_) {
      this.c = p_i1057_1_;
      this.b = p_i1057_2_;
   }

   protected IProjectile a(World p_a_1_, IPosition p_a_2_) {
      return new EntityPotion(p_a_1_, p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ(), this.b.cloneItemStack());
   }

   protected float a() {
      return super.a() * 0.5F;
   }

   protected float getPower() {
      return super.getPower() * 1.25F;
   }
}
