package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.ItemBlock;

public class ItemPiston extends ItemBlock {
   public ItemPiston(Block p_i511_1_) {
      super(p_i511_1_);
   }

   public int filterData(int p_filterData_1_) {
      return 7;
   }
}
