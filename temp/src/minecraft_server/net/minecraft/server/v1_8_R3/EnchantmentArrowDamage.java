package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;
import net.minecraft.server.v1_8_R3.MinecraftKey;

public class EnchantmentArrowDamage extends Enchantment {
   public EnchantmentArrowDamage(int p_i522_1_, MinecraftKey p_i522_2_, int p_i522_3_) {
      super(p_i522_1_, p_i522_2_, p_i522_3_, EnchantmentSlotType.BOW);
      this.c("arrowDamage");
   }

   public int a(int p_a_1_) {
      return 1 + (p_a_1_ - 1) * 10;
   }

   public int b(int p_b_1_) {
      return this.a(p_b_1_) + 15;
   }

   public int getMaxLevel() {
      return 5;
   }
}
