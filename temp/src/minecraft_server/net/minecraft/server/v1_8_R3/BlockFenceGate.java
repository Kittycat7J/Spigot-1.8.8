package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDirectional;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class BlockFenceGate extends BlockDirectional {
   public static final BlockStateBoolean OPEN = BlockStateBoolean.of("open");
   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
   public static final BlockStateBoolean IN_WALL = BlockStateBoolean.of("in_wall");

   public BlockFenceGate(BlockWood.EnumLogVariant p_i613_1_) {
      super(Material.WOOD, p_i613_1_.c());
      this.j(this.blockStateList.getBlockData().set(OPEN, Boolean.valueOf(false)).set(POWERED, Boolean.valueOf(false)).set(IN_WALL, Boolean.valueOf(false)));
      this.a(CreativeModeTab.d);
   }

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      EnumDirection.EnumAxis enumdirection$enumaxis = ((EnumDirection)p_updateState_1_.get(FACING)).k();
      if(enumdirection$enumaxis == EnumDirection.EnumAxis.Z && (p_updateState_2_.getType(p_updateState_3_.west()).getBlock() == Blocks.COBBLESTONE_WALL || p_updateState_2_.getType(p_updateState_3_.east()).getBlock() == Blocks.COBBLESTONE_WALL) || enumdirection$enumaxis == EnumDirection.EnumAxis.X && (p_updateState_2_.getType(p_updateState_3_.north()).getBlock() == Blocks.COBBLESTONE_WALL || p_updateState_2_.getType(p_updateState_3_.south()).getBlock() == Blocks.COBBLESTONE_WALL)) {
         p_updateState_1_ = p_updateState_1_.set(IN_WALL, Boolean.valueOf(true));
      }

      return p_updateState_1_;
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      return p_canPlace_1_.getType(p_canPlace_2_.down()).getBlock().getMaterial().isBuildable()?super.canPlace(p_canPlace_1_, p_canPlace_2_):false;
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      if(((Boolean)p_a_3_.get(OPEN)).booleanValue()) {
         return null;
      } else {
         EnumDirection.EnumAxis enumdirection$enumaxis = ((EnumDirection)p_a_3_.get(FACING)).k();
         return enumdirection$enumaxis == EnumDirection.EnumAxis.Z?new AxisAlignedBB((double)p_a_2_.getX(), (double)p_a_2_.getY(), (double)((float)p_a_2_.getZ() + 0.375F), (double)(p_a_2_.getX() + 1), (double)((float)p_a_2_.getY() + 1.5F), (double)((float)p_a_2_.getZ() + 0.625F)):new AxisAlignedBB((double)((float)p_a_2_.getX() + 0.375F), (double)p_a_2_.getY(), (double)p_a_2_.getZ(), (double)((float)p_a_2_.getX() + 0.625F), (double)((float)p_a_2_.getY() + 1.5F), (double)(p_a_2_.getZ() + 1));
      }
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      EnumDirection.EnumAxis enumdirection$enumaxis = ((EnumDirection)p_updateShape_1_.getType(p_updateShape_2_).get(FACING)).k();
      if(enumdirection$enumaxis == EnumDirection.EnumAxis.Z) {
         this.a(0.0F, 0.0F, 0.375F, 1.0F, 1.0F, 0.625F);
      } else {
         this.a(0.375F, 0.0F, 0.0F, 0.625F, 1.0F, 1.0F);
      }

   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_) {
      return ((Boolean)p_b_1_.getType(p_b_2_).get(OPEN)).booleanValue();
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      return this.getBlockData().set(FACING, p_getPlacedState_8_.getDirection()).set(OPEN, Boolean.valueOf(false)).set(POWERED, Boolean.valueOf(false)).set(IN_WALL, Boolean.valueOf(false));
   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(((Boolean)p_interact_3_.get(OPEN)).booleanValue()) {
         p_interact_3_ = p_interact_3_.set(OPEN, Boolean.valueOf(false));
         p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_, 2);
      } else {
         EnumDirection enumdirection = EnumDirection.fromAngle((double)p_interact_4_.yaw);
         if(p_interact_3_.get(FACING) == enumdirection.opposite()) {
            p_interact_3_ = p_interact_3_.set(FACING, enumdirection);
         }

         p_interact_3_ = p_interact_3_.set(OPEN, Boolean.valueOf(true));
         p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_, 2);
      }

      p_interact_1_.a(p_interact_4_, ((Boolean)p_interact_3_.get(OPEN)).booleanValue()?1003:1006, p_interact_2_, 0);
      return true;
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(!p_doPhysics_1_.isClientSide) {
         boolean flag = p_doPhysics_1_.isBlockIndirectlyPowered(p_doPhysics_2_);
         if(flag || p_doPhysics_4_.isPowerSource()) {
            if(flag && !((Boolean)p_doPhysics_3_.get(OPEN)).booleanValue() && !((Boolean)p_doPhysics_3_.get(POWERED)).booleanValue()) {
               p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, p_doPhysics_3_.set(OPEN, Boolean.valueOf(true)).set(POWERED, Boolean.valueOf(true)), 2);
               p_doPhysics_1_.a((EntityHuman)null, 1003, p_doPhysics_2_, 0);
            } else if(!flag && ((Boolean)p_doPhysics_3_.get(OPEN)).booleanValue() && ((Boolean)p_doPhysics_3_.get(POWERED)).booleanValue()) {
               p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, p_doPhysics_3_.set(OPEN, Boolean.valueOf(false)).set(POWERED, Boolean.valueOf(false)), 2);
               p_doPhysics_1_.a((EntityHuman)null, 1006, p_doPhysics_2_, 0);
            } else if(flag != ((Boolean)p_doPhysics_3_.get(POWERED)).booleanValue()) {
               p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, p_doPhysics_3_.set(POWERED, Boolean.valueOf(flag)), 2);
            }
         }

      }
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(FACING, EnumDirection.fromType2(p_fromLegacyData_1_)).set(OPEN, Boolean.valueOf((p_fromLegacyData_1_ & 4) != 0)).set(POWERED, Boolean.valueOf((p_fromLegacyData_1_ & 8) != 0));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i = 0;
      i = i | ((EnumDirection)p_toLegacyData_1_.get(FACING)).b();
      if(((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue()) {
         i |= 8;
      }

      if(((Boolean)p_toLegacyData_1_.get(OPEN)).booleanValue()) {
         i |= 4;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{FACING, OPEN, POWERED, IN_WALL});
   }
}
