package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class BlockCloth extends Block {
   public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.<EnumColor>of("color", EnumColor.class);

   public BlockCloth(Material p_i598_1_) {
      super(p_i598_1_);
      this.j(this.blockStateList.getBlockData().set(COLOR, EnumColor.WHITE));
      this.a(CreativeModeTab.b);
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((EnumColor)p_getDropData_1_.get(COLOR)).getColorIndex();
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return ((EnumColor)p_g_1_.get(COLOR)).e();
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(COLOR, EnumColor.fromColorIndex(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((EnumColor)p_toLegacyData_1_.get(COLOR)).getColorIndex();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{COLOR});
   }
}
