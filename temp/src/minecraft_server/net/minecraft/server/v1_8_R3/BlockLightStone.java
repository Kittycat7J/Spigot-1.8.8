package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.MathHelper;

public class BlockLightStone extends Block {
   public BlockLightStone(Material p_i621_1_) {
      super(p_i621_1_);
      this.a(CreativeModeTab.b);
   }

   public int getDropCount(int p_getDropCount_1_, Random p_getDropCount_2_) {
      return MathHelper.clamp(this.a(p_getDropCount_2_) + p_getDropCount_2_.nextInt(p_getDropCount_1_ + 1), 1, 4);
   }

   public int a(Random p_a_1_) {
      return 2 + p_a_1_.nextInt(3);
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Items.GLOWSTONE_DUST;
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return MaterialMapColor.d;
   }
}
