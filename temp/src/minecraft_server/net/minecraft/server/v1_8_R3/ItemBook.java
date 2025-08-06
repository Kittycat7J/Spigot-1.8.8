package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;

public class ItemBook extends Item {
   public boolean f_(ItemStack p_f__1_) {
      return p_f__1_.count == 1;
   }

   public int b() {
      return 1;
   }
}
