package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockFenceGate;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.ItemLeash;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.World;

public class BlockFence extends Block {
   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");

   public BlockFence(Material p_i611_1_) {
      this(p_i611_1_, p_i611_1_.r());
   }

   public BlockFence(Material p_i612_1_, MaterialMapColor p_i612_2_) {
      super(p_i612_1_, p_i612_2_);
      this.j(this.blockStateList.getBlockData().set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)));
      this.a(CreativeModeTab.c);
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_) {
      boolean flag = this.e(p_a_1_, p_a_2_.north());
      boolean flag1 = this.e(p_a_1_, p_a_2_.south());
      boolean flag2 = this.e(p_a_1_, p_a_2_.west());
      boolean flag3 = this.e(p_a_1_, p_a_2_.east());
      float f = 0.375F;
      float f1 = 0.625F;
      float f2 = 0.375F;
      float f3 = 0.625F;
      if(flag) {
         f2 = 0.0F;
      }

      if(flag1) {
         f3 = 1.0F;
      }

      if(flag || flag1) {
         this.a(f, 0.0F, f2, f1, 1.5F, f3);
         super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      }

      f2 = 0.375F;
      f3 = 0.625F;
      if(flag2) {
         f = 0.0F;
      }

      if(flag3) {
         f1 = 1.0F;
      }

      if(flag2 || flag3 || !flag && !flag1) {
         this.a(f, 0.0F, f2, f1, 1.5F, f3);
         super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      }

      if(flag) {
         f2 = 0.0F;
      }

      if(flag1) {
         f3 = 1.0F;
      }

      this.a(f, 0.0F, f2, f1, 1.0F, f3);
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      boolean flag = this.e(p_updateShape_1_, p_updateShape_2_.north());
      boolean flag1 = this.e(p_updateShape_1_, p_updateShape_2_.south());
      boolean flag2 = this.e(p_updateShape_1_, p_updateShape_2_.west());
      boolean flag3 = this.e(p_updateShape_1_, p_updateShape_2_.east());
      float f = 0.375F;
      float f1 = 0.625F;
      float f2 = 0.375F;
      float f3 = 0.625F;
      if(flag) {
         f2 = 0.0F;
      }

      if(flag1) {
         f3 = 1.0F;
      }

      if(flag2) {
         f = 0.0F;
      }

      if(flag3) {
         f1 = 1.0F;
      }

      this.a(f, 0.0F, f2, f1, 1.0F, f3);
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_) {
      return false;
   }

   public boolean e(IBlockAccess p_e_1_, BlockPosition p_e_2_) {
      Block block = p_e_1_.getType(p_e_2_).getBlock();
      return block == Blocks.BARRIER?false:((!(block instanceof BlockFence) || block.material != this.material) && !(block instanceof BlockFenceGate)?(block.material.k() && block.d()?block.material != Material.PUMPKIN:false):true);
   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      return p_interact_1_.isClientSide?true:ItemLeash.a(p_interact_4_, p_interact_1_, p_interact_2_);
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return 0;
   }

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      return p_updateState_1_.set(NORTH, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.north()))).set(EAST, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.east()))).set(SOUTH, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.south()))).set(WEST, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.west())));
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{NORTH, EAST, WEST, SOUTH});
   }
}
