package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockButtonAbstract;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockLever extends Block {
   public static final BlockStateEnum<BlockLever.EnumLeverPosition> FACING = BlockStateEnum.<BlockLever.EnumLeverPosition>of("facing", BlockLever.EnumLeverPosition.class);
   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");

   protected BlockLever() {
      super(Material.ORIENTABLE);
      this.j(this.blockStateList.getBlockData().set(FACING, BlockLever.EnumLeverPosition.NORTH).set(POWERED, Boolean.valueOf(false)));
      this.a(CreativeModeTab.d);
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      return null;
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_, EnumDirection p_canPlace_3_) {
      return a(p_canPlace_1_, p_canPlace_2_, p_canPlace_3_.opposite());
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      for(EnumDirection enumdirection : EnumDirection.values()) {
         if(a(p_canPlace_1_, p_canPlace_2_, enumdirection)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean a(World p_a_0_, BlockPosition p_a_1_, EnumDirection p_a_2_) {
      return BlockButtonAbstract.a(p_a_0_, p_a_1_, p_a_2_);
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      IBlockData iblockdata = this.getBlockData().set(POWERED, Boolean.valueOf(false));
      if(a(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_3_.opposite())) {
         return iblockdata.set(FACING, BlockLever.EnumLeverPosition.a(p_getPlacedState_3_, p_getPlacedState_8_.getDirection()));
      } else {
         for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            if(enumdirection != p_getPlacedState_3_ && a(p_getPlacedState_1_, p_getPlacedState_2_, enumdirection.opposite())) {
               return iblockdata.set(FACING, BlockLever.EnumLeverPosition.a(enumdirection, p_getPlacedState_8_.getDirection()));
            }
         }

         if(World.a((IBlockAccess)p_getPlacedState_1_, (BlockPosition)p_getPlacedState_2_.down())) {
            return iblockdata.set(FACING, BlockLever.EnumLeverPosition.a(EnumDirection.UP, p_getPlacedState_8_.getDirection()));
         } else {
            return iblockdata;
         }
      }
   }

   public static int a(EnumDirection p_a_0_) {
      switch(BlockLever.SyntheticClass_1.a[p_a_0_.ordinal()]) {
      case 1:
         return 0;
      case 2:
         return 5;
      case 3:
         return 4;
      case 4:
         return 3;
      case 5:
         return 2;
      case 6:
         return 1;
      default:
         return -1;
      }
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_) && !a(p_doPhysics_1_, p_doPhysics_2_, ((BlockLever.EnumLeverPosition)p_doPhysics_3_.get(FACING)).c().opposite())) {
         this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
         p_doPhysics_1_.setAir(p_doPhysics_2_);
      }

   }

   private boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_) {
      if(this.canPlace(p_e_1_, p_e_2_)) {
         return true;
      } else {
         this.b(p_e_1_, p_e_2_, p_e_3_, 0);
         p_e_1_.setAir(p_e_2_);
         return false;
      }
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      float f = 0.1875F;
      switch(BlockLever.SyntheticClass_1.b[((BlockLever.EnumLeverPosition)p_updateShape_1_.getType(p_updateShape_2_).get(FACING)).ordinal()]) {
      case 1:
         this.a(0.0F, 0.2F, 0.5F - f, f * 2.0F, 0.8F, 0.5F + f);
         break;
      case 2:
         this.a(1.0F - f * 2.0F, 0.2F, 0.5F - f, 1.0F, 0.8F, 0.5F + f);
         break;
      case 3:
         this.a(0.5F - f, 0.2F, 0.0F, 0.5F + f, 0.8F, f * 2.0F);
         break;
      case 4:
         this.a(0.5F - f, 0.2F, 1.0F - f * 2.0F, 0.5F + f, 0.8F, 1.0F);
         break;
      case 5:
      case 6:
         f = 0.25F;
         this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.6F, 0.5F + f);
         break;
      case 7:
      case 8:
         f = 0.25F;
         this.a(0.5F - f, 0.4F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
      }

   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(p_interact_1_.isClientSide) {
         return true;
      } else {
         boolean flag = ((Boolean)p_interact_3_.get(POWERED)).booleanValue();
         org.bukkit.block.Block block = p_interact_1_.getWorld().getBlockAt(p_interact_2_.getX(), p_interact_2_.getY(), p_interact_2_.getZ());
         int i = flag?15:0;
         int j = !flag?15:0;
         BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, i, j);
         p_interact_1_.getServer().getPluginManager().callEvent(blockredstoneevent);
         if(blockredstoneevent.getNewCurrent() > 0 != !flag) {
            return true;
         } else {
            p_interact_3_ = p_interact_3_.a(POWERED);
            p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_, 3);
            p_interact_1_.makeSound((double)p_interact_2_.getX() + 0.5D, (double)p_interact_2_.getY() + 0.5D, (double)p_interact_2_.getZ() + 0.5D, "random.click", 0.3F, ((Boolean)p_interact_3_.get(POWERED)).booleanValue()?0.6F:0.5F);
            p_interact_1_.applyPhysics(p_interact_2_, this);
            EnumDirection enumdirection = ((BlockLever.EnumLeverPosition)p_interact_3_.get(FACING)).c();
            p_interact_1_.applyPhysics(p_interact_2_.shift(enumdirection.opposite()), this);
            return true;
         }
      }
   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      if(((Boolean)p_remove_3_.get(POWERED)).booleanValue()) {
         p_remove_1_.applyPhysics(p_remove_2_, this);
         EnumDirection enumdirection = ((BlockLever.EnumLeverPosition)p_remove_3_.get(FACING)).c();
         p_remove_1_.applyPhysics(p_remove_2_.shift(enumdirection.opposite()), this);
      }

      super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
   }

   public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_) {
      return ((Boolean)p_a_3_.get(POWERED)).booleanValue()?15:0;
   }

   public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_) {
      return !((Boolean)p_b_3_.get(POWERED)).booleanValue()?0:(((BlockLever.EnumLeverPosition)p_b_3_.get(FACING)).c() == p_b_4_?15:0);
   }

   public boolean isPowerSource() {
      return true;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(FACING, BlockLever.EnumLeverPosition.a(p_fromLegacyData_1_ & 7)).set(POWERED, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      byte b0 = 0;
      int i = b0 | ((BlockLever.EnumLeverPosition)p_toLegacyData_1_.get(FACING)).a();
      if(((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{FACING, POWERED});
   }

   public static enum EnumLeverPosition implements INamable {
      DOWN_X(0, "down_x", EnumDirection.DOWN),
      EAST(1, "east", EnumDirection.EAST),
      WEST(2, "west", EnumDirection.WEST),
      SOUTH(3, "south", EnumDirection.SOUTH),
      NORTH(4, "north", EnumDirection.NORTH),
      UP_Z(5, "up_z", EnumDirection.UP),
      UP_X(6, "up_x", EnumDirection.UP),
      DOWN_Z(7, "down_z", EnumDirection.DOWN);

      private static final BlockLever.EnumLeverPosition[] i = new BlockLever.EnumLeverPosition[values().length];
      private final int j;
      private final String k;
      private final EnumDirection l;

      static {
         for(BlockLever.EnumLeverPosition blocklever$enumleverposition : values()) {
            i[blocklever$enumleverposition.a()] = blocklever$enumleverposition;
         }

      }

      private EnumLeverPosition(int p_i413_3_, String p_i413_4_, EnumDirection p_i413_5_) {
         this.j = p_i413_3_;
         this.k = p_i413_4_;
         this.l = p_i413_5_;
      }

      public int a() {
         return this.j;
      }

      public EnumDirection c() {
         return this.l;
      }

      public String toString() {
         return this.k;
      }

      public static BlockLever.EnumLeverPosition a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= i.length) {
            p_a_0_ = 0;
         }

         return i[p_a_0_];
      }

      public static BlockLever.EnumLeverPosition a(EnumDirection p_a_0_, EnumDirection p_a_1_) {
         switch(BlockLever.SyntheticClass_1.a[p_a_0_.ordinal()]) {
         case 1:
            switch(BlockLever.SyntheticClass_1.c[p_a_1_.k().ordinal()]) {
            case 1:
               return DOWN_X;
            case 2:
               return DOWN_Z;
            default:
               throw new IllegalArgumentException("Invalid entityFacing " + p_a_1_ + " for facing " + p_a_0_);
            }
         case 2:
            switch(BlockLever.SyntheticClass_1.c[p_a_1_.k().ordinal()]) {
            case 1:
               return UP_X;
            case 2:
               return UP_Z;
            default:
               throw new IllegalArgumentException("Invalid entityFacing " + p_a_1_ + " for facing " + p_a_0_);
            }
         case 3:
            return NORTH;
         case 4:
            return SOUTH;
         case 5:
            return WEST;
         case 6:
            return EAST;
         default:
            throw new IllegalArgumentException("Invalid facing: " + p_a_0_);
         }
      }

      public String getName() {
         return this.k;
      }
   }

   static class SyntheticClass_1 {
      static final int[] a;
      static final int[] b;
      static final int[] c = new int[EnumDirection.EnumAxis.values().length];

      static {
         try {
            c[EnumDirection.EnumAxis.X.ordinal()] = 1;
         } catch (NoSuchFieldError var15) {
            ;
         }

         try {
            c[EnumDirection.EnumAxis.Z.ordinal()] = 2;
         } catch (NoSuchFieldError var14) {
            ;
         }

         b = new int[BlockLever.EnumLeverPosition.values().length];

         try {
            b[BlockLever.EnumLeverPosition.EAST.ordinal()] = 1;
         } catch (NoSuchFieldError var13) {
            ;
         }

         try {
            b[BlockLever.EnumLeverPosition.WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var12) {
            ;
         }

         try {
            b[BlockLever.EnumLeverPosition.SOUTH.ordinal()] = 3;
         } catch (NoSuchFieldError var11) {
            ;
         }

         try {
            b[BlockLever.EnumLeverPosition.NORTH.ordinal()] = 4;
         } catch (NoSuchFieldError var10) {
            ;
         }

         try {
            b[BlockLever.EnumLeverPosition.UP_Z.ordinal()] = 5;
         } catch (NoSuchFieldError var9) {
            ;
         }

         try {
            b[BlockLever.EnumLeverPosition.UP_X.ordinal()] = 6;
         } catch (NoSuchFieldError var8) {
            ;
         }

         try {
            b[BlockLever.EnumLeverPosition.DOWN_X.ordinal()] = 7;
         } catch (NoSuchFieldError var7) {
            ;
         }

         try {
            b[BlockLever.EnumLeverPosition.DOWN_Z.ordinal()] = 8;
         } catch (NoSuchFieldError var6) {
            ;
         }

         a = new int[EnumDirection.values().length];

         try {
            a[EnumDirection.DOWN.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            a[EnumDirection.UP.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            a[EnumDirection.NORTH.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[EnumDirection.SOUTH.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[EnumDirection.WEST.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[EnumDirection.EAST.ordinal()] = 6;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}
