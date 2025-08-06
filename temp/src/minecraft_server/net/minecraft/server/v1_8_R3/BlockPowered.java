package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class BlockPowered extends Block {
   public BlockPowered(Material p_i633_1_, MaterialMapColor p_i633_2_) {
      super(p_i633_1_, p_i633_2_);
   }

   public boolean isPowerSource() {
      return true;
   }

   public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_) {
      return 15;
   }
}
