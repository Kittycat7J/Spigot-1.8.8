package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.WeightedRandom;

public class WeightedRandomEnchant extends WeightedRandom.WeightedRandomChoice {
   public final Enchantment enchantment;
   public final int level;

   public WeightedRandomEnchant(Enchantment p_i530_1_, int p_i530_2_) {
      super(p_i530_1_.getRandomWeight());
      this.enchantment = p_i530_1_;
      this.level = p_i530_2_;
   }
}
