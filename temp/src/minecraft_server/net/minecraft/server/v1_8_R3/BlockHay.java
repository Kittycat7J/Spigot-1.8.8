package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockRotatable;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.World;

public class BlockHay extends BlockRotatable {
   public BlockHay() {
      super(Material.GRASS, MaterialMapColor.t);
      this.j(this.blockStateList.getBlockData().set(AXIS, EnumDirection.EnumAxis.Y));
      this.a(CreativeModeTab.b);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      EnumDirection.EnumAxis enumdirection$enumaxis = EnumDirection.EnumAxis.Y;
      int i = p_fromLegacyData_1_ & 12;
      if(i == 4) {
         enumdirection$enumaxis = EnumDirection.EnumAxis.X;
      } else if(i == 8) {
         enumdirection$enumaxis = EnumDirection.EnumAxis.Z;
      }

      return this.getBlockData().set(AXIS, enumdirection$enumaxis);
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i = 0;
      EnumDirection.EnumAxis enumdirection$enumaxis = (EnumDirection.EnumAxis)p_toLegacyData_1_.get(AXIS);
      if(enumdirection$enumaxis == EnumDirection.EnumAxis.X) {
         i |= 4;
      } else if(enumdirection$enumaxis == EnumDirection.EnumAxis.Z) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{AXIS});
   }

   protected ItemStack i(IBlockData p_i_1_) {
      return new ItemStack(Item.getItemOf(this), 1, 0);
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      return super.getPlacedState(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_3_, p_getPlacedState_4_, p_getPlacedState_5_, p_getPlacedState_6_, p_getPlacedState_7_, p_getPlacedState_8_).set(AXIS, p_getPlacedState_3_.k());
   }
}
