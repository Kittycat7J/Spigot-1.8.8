package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;
import net.minecraft.server.v1_8_R3.MinecraftKey;

public class EnchantmentDepthStrider extends Enchantment {
   public EnchantmentDepthStrider(int p_i538_1_, MinecraftKey p_i538_2_, int p_i538_3_) {
      super(p_i538_1_, p_i538_2_, p_i538_3_, EnchantmentSlotType.ARMOR_FEET);
      this.c("waterWalker");
   }

   public int a(int p_a_1_) {
      return p_a_1_ * 10;
   }

   public int b(int p_b_1_) {
      return this.a(p_b_1_) + 15;
   }

   public int getMaxLevel() {
      return 3;
   }
}
