package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockLeaves;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockRotatable;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public abstract class BlockLogAbstract extends BlockRotatable {
   public static final BlockStateEnum<BlockLogAbstract.EnumLogRotation> AXIS = BlockStateEnum.<BlockLogAbstract.EnumLogRotation>of("axis", BlockLogAbstract.EnumLogRotation.class);

   public BlockLogAbstract() {
      super(Material.WOOD);
      this.a(CreativeModeTab.b);
      this.c(2.0F);
      this.a(f);
   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      byte b0 = 4;
      int i = b0 + 1;
      if(p_remove_1_.areChunksLoadedBetween(p_remove_2_.a(-i, -i, -i), p_remove_2_.a(i, i, i))) {
         for(BlockPosition blockposition : BlockPosition.a(p_remove_2_.a(-b0, -b0, -b0), p_remove_2_.a(b0, b0, b0))) {
            IBlockData iblockdata = p_remove_1_.getType(blockposition);
            if(iblockdata.getBlock().getMaterial() == Material.LEAVES && !((Boolean)iblockdata.get(BlockLeaves.CHECK_DECAY)).booleanValue()) {
               p_remove_1_.setTypeAndData(blockposition, iblockdata.set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(true)), 4);
            }
         }

      }
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      return super.getPlacedState(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_3_, p_getPlacedState_4_, p_getPlacedState_5_, p_getPlacedState_6_, p_getPlacedState_7_, p_getPlacedState_8_).set(AXIS, BlockLogAbstract.EnumLogRotation.a(p_getPlacedState_3_.k()));
   }

   public static enum EnumLogRotation implements INamable {
      X("x"),
      Y("y"),
      Z("z"),
      NONE("none");

      private final String e;

      private EnumLogRotation(String p_i629_3_) {
         this.e = p_i629_3_;
      }

      public String toString() {
         return this.e;
      }

      public static BlockLogAbstract.EnumLogRotation a(EnumDirection.EnumAxis p_a_0_) {
         switch(p_a_0_) {
         case X:
            return X;
         case Y:
            return Y;
         case Z:
            return Z;
         default:
            return NONE;
         }
      }

      public String getName() {
         return this.e;
      }
   }
}
