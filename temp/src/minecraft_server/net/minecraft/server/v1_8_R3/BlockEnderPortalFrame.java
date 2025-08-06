package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateDirection;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.World;

public class BlockEnderPortalFrame extends Block {
   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
   public static final BlockStateBoolean EYE = BlockStateBoolean.of("eye");

   public BlockEnderPortalFrame() {
      super(Material.STONE, MaterialMapColor.C);
      this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(EYE, Boolean.valueOf(false)));
   }

   public boolean c() {
      return false;
   }

   public void j() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
      super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      if(((Boolean)p_a_1_.getType(p_a_2_).get(EYE)).booleanValue()) {
         this.a(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
         super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      }

      this.j();
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return null;
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      return this.getBlockData().set(FACING, p_getPlacedState_8_.getDirection().opposite()).set(EYE, Boolean.valueOf(false));
   }

   public boolean isComplexRedstone() {
      return true;
   }

   public int l(World p_l_1_, BlockPosition p_l_2_) {
      return ((Boolean)p_l_1_.getType(p_l_2_).get(EYE)).booleanValue()?15:0;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(EYE, Boolean.valueOf((p_fromLegacyData_1_ & 4) != 0)).set(FACING, EnumDirection.fromType2(p_fromLegacyData_1_ & 3));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i = 0;
      i = i | ((EnumDirection)p_toLegacyData_1_.get(FACING)).b();
      if(((Boolean)p_toLegacyData_1_.get(EYE)).booleanValue()) {
         i |= 4;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{FACING, EYE});
   }
}
