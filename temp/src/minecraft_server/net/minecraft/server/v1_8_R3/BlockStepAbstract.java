package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public abstract class BlockStepAbstract extends Block {
   public static final BlockStateEnum<BlockStepAbstract.EnumSlabHalf> HALF = BlockStateEnum.<BlockStepAbstract.EnumSlabHalf>of("half", BlockStepAbstract.EnumSlabHalf.class);

   public BlockStepAbstract(Material p_i623_1_) {
      super(p_i623_1_);
      if(this.l()) {
         this.r = true;
      } else {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

      this.e(255);
   }

   protected boolean I() {
      return false;
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      if(this.l()) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         IBlockData iblockdata = p_updateShape_1_.getType(p_updateShape_2_);
         if(iblockdata.getBlock() == this) {
            if(iblockdata.get(HALF) == BlockStepAbstract.EnumSlabHalf.TOP) {
               this.a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
            } else {
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
            }
         }

      }
   }

   public void j() {
      if(this.l()) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_) {
      this.updateShape(p_a_1_, p_a_2_);
      super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
   }

   public boolean c() {
      return this.l();
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      IBlockData iblockdata = super.getPlacedState(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_3_, p_getPlacedState_4_, p_getPlacedState_5_, p_getPlacedState_6_, p_getPlacedState_7_, p_getPlacedState_8_).set(HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
      return this.l()?iblockdata:(p_getPlacedState_3_ != EnumDirection.DOWN && (p_getPlacedState_3_ == EnumDirection.UP || (double)p_getPlacedState_5_ <= 0.5D)?iblockdata:iblockdata.set(HALF, BlockStepAbstract.EnumSlabHalf.TOP));
   }

   public int a(Random p_a_1_) {
      return this.l()?2:1;
   }

   public boolean d() {
      return this.l();
   }

   public abstract String b(int var1);

   public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_) {
      return super.getDropData(p_getDropData_1_, p_getDropData_2_) & 7;
   }

   public abstract boolean l();

   public abstract IBlockState<?> n();

   public abstract Object a(ItemStack var1);

   public static enum EnumSlabHalf implements INamable {
      TOP("top"),
      BOTTOM("bottom");

      private final String c;

      private EnumSlabHalf(String p_i622_3_) {
         this.c = p_i622_3_;
      }

      public String toString() {
         return this.c;
      }

      public String getName() {
         return this.c;
      }
   }
}
