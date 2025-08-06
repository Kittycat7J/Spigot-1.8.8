package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDirectional;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockFragilePlantElement;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockCocoa extends BlockDirectional implements IBlockFragilePlantElement {
   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 2);

   public BlockCocoa() {
      super(Material.PLANT);
      this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(AGE, Integer.valueOf(0)));
      this.a(true);
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(!this.e(p_b_1_, p_b_2_, p_b_3_)) {
         this.f(p_b_1_, p_b_2_, p_b_3_);
      } else if(p_b_1_.random.nextInt(5) == 0) {
         int i = ((Integer)p_b_3_.get(AGE)).intValue();
         if(i < 2) {
            IBlockData iblockdata = p_b_3_.set(AGE, Integer.valueOf(i + 1));
            CraftEventFactory.handleBlockGrowEvent(p_b_1_, p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ(), this, this.toLegacyData(iblockdata));
         }
      }

   }

   public boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_) {
      p_e_2_ = p_e_2_.shift((EnumDirection)p_e_3_.get(FACING));
      IBlockData iblockdata = p_e_1_.getType(p_e_2_);
      return iblockdata.getBlock() == Blocks.LOG && iblockdata.get(BlockWood.VARIANT) == BlockWood.EnumLogVariant.JUNGLE;
   }

   public boolean d() {
      return false;
   }

   public boolean c() {
      return false;
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      this.updateShape(p_a_1_, p_a_2_);
      return super.a(p_a_1_, p_a_2_, p_a_3_);
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      IBlockData iblockdata = p_updateShape_1_.getType(p_updateShape_2_);
      EnumDirection enumdirection = (EnumDirection)iblockdata.get(FACING);
      int i = ((Integer)iblockdata.get(AGE)).intValue();
      int j = 4 + i * 2;
      int k = 5 + i * 2;
      float f = (float)j / 2.0F;
      switch(BlockCocoa.SyntheticClass_1.a[enumdirection.ordinal()]) {
      case 1:
         this.a((8.0F - f) / 16.0F, (12.0F - (float)k) / 16.0F, (15.0F - (float)j) / 16.0F, (8.0F + f) / 16.0F, 0.75F, 0.9375F);
         break;
      case 2:
         this.a((8.0F - f) / 16.0F, (12.0F - (float)k) / 16.0F, 0.0625F, (8.0F + f) / 16.0F, 0.75F, (1.0F + (float)j) / 16.0F);
         break;
      case 3:
         this.a(0.0625F, (12.0F - (float)k) / 16.0F, (8.0F - f) / 16.0F, (1.0F + (float)j) / 16.0F, 0.75F, (8.0F + f) / 16.0F);
         break;
      case 4:
         this.a((15.0F - (float)j) / 16.0F, (12.0F - (float)k) / 16.0F, (8.0F - f) / 16.0F, 0.9375F, 0.75F, (8.0F + f) / 16.0F);
      }

   }

   public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_) {
      EnumDirection enumdirection = EnumDirection.fromAngle((double)p_postPlace_4_.yaw);
      p_postPlace_1_.setTypeAndData(p_postPlace_2_, p_postPlace_3_.set(FACING, enumdirection), 2);
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      if(!p_getPlacedState_3_.k().c()) {
         p_getPlacedState_3_ = EnumDirection.NORTH;
      }

      return this.getBlockData().set(FACING, p_getPlacedState_3_.opposite()).set(AGE, Integer.valueOf(0));
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(!this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_)) {
         this.f(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
      }

   }

   private void f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_) {
      p_f_1_.setTypeAndData(p_f_2_, Blocks.AIR.getBlockData(), 3);
      this.b(p_f_1_, p_f_2_, p_f_3_, 0);
   }

   public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_) {
      int i = ((Integer)p_dropNaturally_3_.get(AGE)).intValue();
      byte b0 = 1;
      if(i >= 2) {
         b0 = 3;
      }

      for(int j = 0; j < b0; ++j) {
         a(p_dropNaturally_1_, p_dropNaturally_2_, new ItemStack(Items.DYE, 1, EnumColor.BROWN.getInvColorIndex()));
      }

   }

   public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_) {
      return EnumColor.BROWN.getInvColorIndex();
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_) {
      return ((Integer)p_a_3_.get(AGE)).intValue() < 2;
   }

   public boolean a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_) {
      return true;
   }

   public void b(World p_b_1_, Random p_b_2_, BlockPosition p_b_3_, IBlockData p_b_4_) {
      IBlockData iblockdata = p_b_4_.set(AGE, Integer.valueOf(((Integer)p_b_4_.get(AGE)).intValue() + 1));
      CraftEventFactory.handleBlockGrowEvent(p_b_1_, p_b_3_.getX(), p_b_3_.getY(), p_b_3_.getZ(), this, this.toLegacyData(iblockdata));
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(FACING, EnumDirection.fromType2(p_fromLegacyData_1_)).set(AGE, Integer.valueOf((p_fromLegacyData_1_ & 15) >> 2));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      byte b0 = 0;
      int i = b0 | ((EnumDirection)p_toLegacyData_1_.get(FACING)).b();
      i = i | ((Integer)p_toLegacyData_1_.get(AGE)).intValue() << 2;
      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{FACING, AGE});
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[EnumDirection.values().length];

      static {
         try {
            a[EnumDirection.SOUTH.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[EnumDirection.NORTH.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[EnumDirection.WEST.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[EnumDirection.EAST.ordinal()] = 4;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}
