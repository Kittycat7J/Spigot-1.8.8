package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockBeacon;
import net.minecraft.server.v1_8_R3.BlockHalfTransparent;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.World;

public class BlockStainedGlass extends BlockHalfTransparent {
   public static final BlockStateEnum<EnumColor> COLOR = BlockStateEnum.<EnumColor>of("color", EnumColor.class);

   public BlockStainedGlass(Material p_i643_1_) {
      super(p_i643_1_, false);
      this.j(this.blockStateList.getBlockData().set(COLOR, EnumColor.WHITE));
      this.a(CreativeModeTab.b);
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((EnumColor)p_getDropData_1_.get(COLOR)).getColorIndex();
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return ((EnumColor)p_g_1_.get(COLOR)).e();
   }

   public int a(Random p_a_1_) {
      return 0;
   }

   protected boolean I() {
      return true;
   }

   public boolean d() {
      return false;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(COLOR, EnumColor.fromColorIndex(p_fromLegacyData_1_));
   }

   public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_) {
      if(!p_onPlace_1_.isClientSide) {
         BlockBeacon.f(p_onPlace_1_, p_onPlace_2_);
      }

   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      if(!p_remove_1_.isClientSide) {
         BlockBeacon.f(p_remove_1_, p_remove_2_);
      }

   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((EnumColor)p_toLegacyData_1_.get(COLOR)).getColorIndex();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{COLOR});
   }
}
