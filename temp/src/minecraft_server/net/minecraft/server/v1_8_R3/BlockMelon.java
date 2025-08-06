package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class BlockMelon extends Block {
   protected BlockMelon() {
      super(Material.PUMPKIN, MaterialMapColor.u);
      this.a(CreativeModeTab.b);
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Items.MELON;
   }

   public int a(Random p_a_1_) {
      return 3 + p_a_1_.nextInt(5);
   }

   public int getDropCount(int p_getDropCount_1_, Random p_getDropCount_2_) {
      return Math.min(9, this.a(p_getDropCount_2_) + p_getDropCount_2_.nextInt(1 + p_getDropCount_1_));
   }
}
