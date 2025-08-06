package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;
import net.minecraft.server.v1_8_R3.MinecraftKey;

public class EnchantmentFire extends Enchantment {
   protected EnchantmentFire(int p_i531_1_, MinecraftKey p_i531_2_, int p_i531_3_) {
      super(p_i531_1_, p_i531_2_, p_i531_3_, EnchantmentSlotType.WEAPON);
      this.c("fire");
   }

   public int a(int p_a_1_) {
      return 10 + 20 * (p_a_1_ - 1);
   }

   public int b(int p_b_1_) {
      return super.a(p_b_1_) + 50;
   }

   public int getMaxLevel() {
      return 2;
   }
}
