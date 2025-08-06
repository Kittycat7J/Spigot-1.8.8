package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStairs;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateDirection;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockStepAbstract;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockTrapdoor extends Block {
   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
   public static final BlockStateBoolean OPEN = BlockStateBoolean.of("open");
   public static final BlockStateEnum<BlockTrapdoor.EnumTrapdoorHalf> HALF = BlockStateEnum.<BlockTrapdoor.EnumTrapdoorHalf>of("half", BlockTrapdoor.EnumTrapdoorHalf.class);

   protected BlockTrapdoor(Material p_i2_1_) {
      super(p_i2_1_);
      this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(OPEN, Boolean.valueOf(false)).set(HALF, BlockTrapdoor.EnumTrapdoorHalf.BOTTOM));
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      this.a(CreativeModeTab.d);
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_) {
      return !((Boolean)p_b_1_.getType(p_b_2_).get(OPEN)).booleanValue();
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      this.updateShape(p_a_1_, p_a_2_);
      return super.a(p_a_1_, p_a_2_, p_a_3_);
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      this.d(p_updateShape_1_.getType(p_updateShape_2_));
   }

   public void j() {
      this.a(0.0F, 0.40625F, 0.0F, 1.0F, 0.59375F, 1.0F);
   }

   public void d(IBlockData p_d_1_) {
      if(p_d_1_.getBlock() == this) {
         boolean flag = p_d_1_.get(HALF) == BlockTrapdoor.EnumTrapdoorHalf.TOP;
         Boolean obool = (Boolean)p_d_1_.get(OPEN);
         EnumDirection enumdirection = (EnumDirection)p_d_1_.get(FACING);
         if(flag) {
            this.a(0.0F, 0.8125F, 0.0F, 1.0F, 1.0F, 1.0F);
         } else {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.1875F, 1.0F);
         }

         if(obool.booleanValue()) {
            if(enumdirection == EnumDirection.NORTH) {
               this.a(0.0F, 0.0F, 0.8125F, 1.0F, 1.0F, 1.0F);
            }

            if(enumdirection == EnumDirection.SOUTH) {
               this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1875F);
            }

            if(enumdirection == EnumDirection.WEST) {
               this.a(0.8125F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if(enumdirection == EnumDirection.EAST) {
               this.a(0.0F, 0.0F, 0.0F, 0.1875F, 1.0F, 1.0F);
            }
         }
      }

   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(this.material == Material.ORE) {
         return true;
      } else {
         p_interact_3_ = p_interact_3_.a(OPEN);
         p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_, 2);
         p_interact_1_.a(p_interact_4_, ((Boolean)p_interact_3_.get(OPEN)).booleanValue()?1003:1006, p_interact_2_, 0);
         return true;
      }
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(!p_doPhysics_1_.isClientSide) {
         BlockPosition blockposition = p_doPhysics_2_.shift(((EnumDirection)p_doPhysics_3_.get(FACING)).opposite());
         if(!c(p_doPhysics_1_.getType(blockposition).getBlock())) {
            p_doPhysics_1_.setAir(p_doPhysics_2_);
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
         } else {
            boolean flag = p_doPhysics_1_.isBlockIndirectlyPowered(p_doPhysics_2_);
            if(flag || p_doPhysics_4_.isPowerSource()) {
               org.bukkit.World world = p_doPhysics_1_.getWorld();
               org.bukkit.block.Block block = world.getBlockAt(p_doPhysics_2_.getX(), p_doPhysics_2_.getY(), p_doPhysics_2_.getZ());
               int i = block.getBlockPower();
               int j = ((Boolean)p_doPhysics_3_.get(OPEN)).booleanValue()?15:0;
               if(j == 0 ^ i == 0 || p_doPhysics_4_.isPowerSource()) {
                  BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, j, i);
                  p_doPhysics_1_.getServer().getPluginManager().callEvent(blockredstoneevent);
                  flag = blockredstoneevent.getNewCurrent() > 0;
               }

               boolean flag1 = ((Boolean)p_doPhysics_3_.get(OPEN)).booleanValue();
               if(flag1 != flag) {
                  p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, p_doPhysics_3_.set(OPEN, Boolean.valueOf(flag)), 2);
                  p_doPhysics_1_.a((EntityHuman)null, flag?1003:1006, p_doPhysics_2_, 0);
               }
            }
         }
      }

   }

   public MovingObjectPosition a(World p_a_1_, BlockPosition p_a_2_, Vec3D p_a_3_, Vec3D p_a_4_) {
      this.updateShape(p_a_1_, p_a_2_);
      return super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      IBlockData iblockdata = this.getBlockData();
      if(p_getPlacedState_3_.k().c()) {
         iblockdata = iblockdata.set(FACING, p_getPlacedState_3_).set(OPEN, Boolean.valueOf(false));
         iblockdata = iblockdata.set(HALF, p_getPlacedState_5_ > 0.5F?BlockTrapdoor.EnumTrapdoorHalf.TOP:BlockTrapdoor.EnumTrapdoorHalf.BOTTOM);
      }

      return iblockdata;
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_, EnumDirection p_canPlace_3_) {
      return !p_canPlace_3_.k().b() && c(p_canPlace_1_.getType(p_canPlace_2_.shift(p_canPlace_3_.opposite())).getBlock());
   }

   protected static EnumDirection b(int p_b_0_) {
      switch(p_b_0_ & 3) {
      case 0:
         return EnumDirection.NORTH;
      case 1:
         return EnumDirection.SOUTH;
      case 2:
         return EnumDirection.WEST;
      case 3:
      default:
         return EnumDirection.EAST;
      }
   }

   protected static int a(EnumDirection p_a_0_) {
      switch(BlockTrapdoor.SyntheticClass_1.a[p_a_0_.ordinal()]) {
      case 1:
         return 0;
      case 2:
         return 1;
      case 3:
         return 2;
      case 4:
      default:
         return 3;
      }
   }

   private static boolean c(Block p_c_0_) {
      return p_c_0_.material.k() && p_c_0_.d() || p_c_0_ == Blocks.GLOWSTONE || p_c_0_ instanceof BlockStepAbstract || p_c_0_ instanceof BlockStairs;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(FACING, b(p_fromLegacyData_1_)).set(OPEN, Boolean.valueOf((p_fromLegacyData_1_ & 4) != 0)).set(HALF, (p_fromLegacyData_1_ & 8) == 0?BlockTrapdoor.EnumTrapdoorHalf.BOTTOM:BlockTrapdoor.EnumTrapdoorHalf.TOP);
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      byte b0 = 0;
      int i = b0 | a((EnumDirection)p_toLegacyData_1_.get(FACING));
      if(((Boolean)p_toLegacyData_1_.get(OPEN)).booleanValue()) {
         i |= 4;
      }

      if(p_toLegacyData_1_.get(HALF) == BlockTrapdoor.EnumTrapdoorHalf.TOP) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{FACING, OPEN, HALF});
   }

   public static enum EnumTrapdoorHalf implements INamable {
      TOP("top"),
      BOTTOM("bottom");

      private final String c;

      private EnumTrapdoorHalf(String p_i98_3_) {
         this.c = p_i98_3_;
      }

      public String toString() {
         return this.c;
      }

      public String getName() {
         return this.c;
      }
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[EnumDirection.values().length];

      static {
         try {
            a[EnumDirection.NORTH.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[EnumDirection.SOUTH.ordinal()] = 2;
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
