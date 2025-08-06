package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;
import net.minecraft.server.v1_8_R3.MinecraftKey;

public class EnchantmentLure extends Enchantment {
   protected EnchantmentLure(int p_i532_1_, MinecraftKey p_i532_2_, int p_i532_3_, EnchantmentSlotType p_i532_4_) {
      super(p_i532_1_, p_i532_2_, p_i532_3_, p_i532_4_);
      this.c("fishingSpeed");
   }

   public int a(int p_a_1_) {
      return 15 + (p_a_1_ - 1) * 9;
   }

   public int b(int p_b_1_) {
      return super.a(p_b_1_) + 50;
   }

   public int getMaxLevel() {
      return 3;
   }
}
