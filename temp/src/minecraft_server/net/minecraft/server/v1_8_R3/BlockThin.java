package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class BlockThin extends Block {
   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
   private final boolean a;

   protected BlockThin(Material p_i653_1_, boolean p_i653_2_) {
      super(p_i653_1_);
      this.j(this.blockStateList.getBlockData().set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)));
      this.a = p_i653_2_;
      this.a(CreativeModeTab.c);
   }

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      return p_updateState_1_.set(NORTH, Boolean.valueOf(this.c(p_updateState_2_.getType(p_updateState_3_.north()).getBlock()))).set(SOUTH, Boolean.valueOf(this.c(p_updateState_2_.getType(p_updateState_3_.south()).getBlock()))).set(WEST, Boolean.valueOf(this.c(p_updateState_2_.getType(p_updateState_3_.west()).getBlock()))).set(EAST, Boolean.valueOf(this.c(p_updateState_2_.getType(p_updateState_3_.east()).getBlock())));
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return !this.a?null:super.getDropType(p_getDropType_1_, p_getDropType_2_, p_getDropType_3_);
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_) {
      boolean flag = this.c(p_a_1_.getType(p_a_2_.north()).getBlock());
      boolean flag1 = this.c(p_a_1_.getType(p_a_2_.south()).getBlock());
      boolean flag2 = this.c(p_a_1_.getType(p_a_2_.west()).getBlock());
      boolean flag3 = this.c(p_a_1_.getType(p_a_2_.east()).getBlock());
      if((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
         if(flag2) {
            this.a(0.0F, 0.0F, 0.4375F, 0.5F, 1.0F, 0.5625F);
            super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
         } else if(flag3) {
            this.a(0.5F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
            super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
         }
      } else {
         this.a(0.0F, 0.0F, 0.4375F, 1.0F, 1.0F, 0.5625F);
         super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      }

      if((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
         if(flag) {
            this.a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 0.5F);
            super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
         } else if(flag1) {
            this.a(0.4375F, 0.0F, 0.5F, 0.5625F, 1.0F, 1.0F);
            super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
         }
      } else {
         this.a(0.4375F, 0.0F, 0.0F, 0.5625F, 1.0F, 1.0F);
         super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      }

   }

   public void j() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      float f = 0.4375F;
      float f1 = 0.5625F;
      float f2 = 0.4375F;
      float f3 = 0.5625F;
      boolean flag = this.c(p_updateShape_1_.getType(p_updateShape_2_.north()).getBlock());
      boolean flag1 = this.c(p_updateShape_1_.getType(p_updateShape_2_.south()).getBlock());
      boolean flag2 = this.c(p_updateShape_1_.getType(p_updateShape_2_.west()).getBlock());
      boolean flag3 = this.c(p_updateShape_1_.getType(p_updateShape_2_.east()).getBlock());
      if((!flag2 || !flag3) && (flag2 || flag3 || flag || flag1)) {
         if(flag2) {
            f = 0.0F;
         } else if(flag3) {
            f1 = 1.0F;
         }
      } else {
         f = 0.0F;
         f1 = 1.0F;
      }

      if((!flag || !flag1) && (flag2 || flag3 || flag || flag1)) {
         if(flag) {
            f2 = 0.0F;
         } else if(flag1) {
            f3 = 1.0F;
         }
      } else {
         f2 = 0.0F;
         f3 = 1.0F;
      }

      this.a(f, 0.0F, f2, f1, 1.0F, f3);
   }

   public final boolean c(Block p_c_1_) {
      return p_c_1_.o() || p_c_1_ == this || p_c_1_ == Blocks.GLASS || p_c_1_ == Blocks.STAINED_GLASS || p_c_1_ == Blocks.STAINED_GLASS_PANE || p_c_1_ instanceof BlockThin;
   }

   protected boolean I() {
      return true;
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return 0;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{NORTH, EAST, WEST, SOUTH});
   }
}
