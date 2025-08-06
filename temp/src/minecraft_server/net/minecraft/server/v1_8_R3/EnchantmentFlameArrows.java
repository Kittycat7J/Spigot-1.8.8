package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;
import net.minecraft.server.v1_8_R3.MinecraftKey;

public class EnchantmentFlameArrows extends Enchantment {
   public EnchantmentFlameArrows(int p_i523_1_, MinecraftKey p_i523_2_, int p_i523_3_) {
      super(p_i523_1_, p_i523_2_, p_i523_3_, EnchantmentSlotType.BOW);
      this.c("arrowFire");
   }

   public int a(int p_a_1_) {
      return 20;
   }

   public int b(int p_b_1_) {
      return 50;
   }

   public int getMaxLevel() {
      return 1;
   }
}
