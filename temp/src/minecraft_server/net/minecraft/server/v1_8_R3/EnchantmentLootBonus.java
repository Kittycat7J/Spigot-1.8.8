package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;
import net.minecraft.server.v1_8_R3.MinecraftKey;

public class EnchantmentLootBonus extends Enchantment {
   protected EnchantmentLootBonus(int p_i534_1_, MinecraftKey p_i534_2_, int p_i534_3_, EnchantmentSlotType p_i534_4_) {
      super(p_i534_1_, p_i534_2_, p_i534_3_, p_i534_4_);
      if(p_i534_4_ == EnchantmentSlotType.DIGGER) {
         this.c("lootBonusDigger");
      } else if(p_i534_4_ == EnchantmentSlotType.FISHING_ROD) {
         this.c("lootBonusFishing");
      } else {
         this.c("lootBonus");
      }

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

   public boolean a(Enchantment p_a_1_) {
      return super.a(p_a_1_) && p_a_1_.id != SILK_TOUCH.id;
   }
}
