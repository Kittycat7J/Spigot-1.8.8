package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.WeightedRandom;

public class PossibleFishingResult extends WeightedRandom.WeightedRandomChoice {
   private final ItemStack b;
   private float c;
   private boolean d;

   public PossibleFishingResult(ItemStack p_i1215_1_, int p_i1215_2_) {
      super(p_i1215_2_);
      this.b = p_i1215_1_;
   }

   public ItemStack a(Random p_a_1_) {
      ItemStack itemstack = this.b.cloneItemStack();
      if(this.c > 0.0F) {
         int i = (int)(this.c * (float)this.b.j());
         int j = itemstack.j() - p_a_1_.nextInt(p_a_1_.nextInt(i) + 1);
         if(j > i) {
            j = i;
         }

         if(j < 1) {
            j = 1;
         }

         itemstack.setData(j);
      }

      if(this.d) {
         EnchantmentManager.a(p_a_1_, itemstack, 30);
      }

      return itemstack;
   }

   public PossibleFishingResult a(float p_a_1_) {
      this.c = p_a_1_;
      return this;
   }

   public PossibleFishingResult a() {
      this.d = true;
      return this;
   }
}
