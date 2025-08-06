package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ISourceBlock;
import net.minecraft.server.v1_8_R3.ItemStack;

public interface IDispenseBehavior {
   IDispenseBehavior NONE = new IDispenseBehavior() {
      public ItemStack a(ISourceBlock p_a_1_, ItemStack p_a_2_) {
         return p_a_2_;
      }
   };

   ItemStack a(ISourceBlock var1, ItemStack var2);
}
