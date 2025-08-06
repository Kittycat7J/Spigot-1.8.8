package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ItemFood;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.World;

public class ItemSoup extends ItemFood {
   public ItemSoup(int p_i1258_1_) {
      super(p_i1258_1_, false);
      this.c(1);
   }

   public ItemStack b(ItemStack p_b_1_, World p_b_2_, EntityHuman p_b_3_) {
      super.b(p_b_1_, p_b_2_, p_b_3_);
      return new ItemStack(Items.BOWL);
   }
}
