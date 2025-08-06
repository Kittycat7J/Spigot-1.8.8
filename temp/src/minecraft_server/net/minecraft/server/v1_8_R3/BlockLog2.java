package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.BlockLogAbstract;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class BlockLog2 extends BlockLogAbstract {
   public static final BlockStateEnum<BlockWood.EnumLogVariant> VARIANT = BlockStateEnum.a("variant", BlockWood.EnumLogVariant.class, new Predicate<BlockWood.EnumLogVariant>() {
      public boolean a(BlockWood.EnumLogVariant p_a_1_) {
         return p_a_1_.a() >= 4;
      }
   });

   public BlockLog2() {
      this.j(this.blockStateList.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.ACACIA).set(AXIS, BlockLogAbstract.EnumLogRotation.Y));
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      BlockWood.EnumLogVariant blockwood$enumlogvariant = (BlockWood.EnumLogVariant)p_g_1_.get(VARIANT);
      switch((BlockLogAbstract.EnumLogRotation)p_g_1_.get(AXIS)) {
      case X:
      case Z:
      case NONE:
      default:
         switch(blockwood$enumlogvariant) {
         case ACACIA:
         default:
            return MaterialMapColor.m;
         case DARK_OAK:
            return BlockWood.EnumLogVariant.DARK_OAK.c();
         }
      case Y:
         return blockwood$enumlogvariant.c();
      }
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      IBlockData iblockdata = this.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.a((p_fromLegacyData_1_ & 3) + 4));
      switch(p_fromLegacyData_1_ & 12) {
      case 0:
         iblockdata = iblockdata.set(AXIS, BlockLogAbstract.EnumLogRotation.Y);
         break;
      case 4:
         iblockdata = iblockdata.set(AXIS, BlockLogAbstract.EnumLogRotation.X);
         break;
      case 8:
         iblockdata = iblockdata.set(AXIS, BlockLogAbstract.EnumLogRotation.Z);
         break;
      default:
         iblockdata = iblockdata.set(AXIS, BlockLogAbstract.EnumLogRotation.NONE);
      }

      return iblockdata;
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i = 0;
      i = i | ((BlockWood.EnumLogVariant)p_toLegacyData_1_.get(VARIANT)).a() - 4;
      switch((BlockLogAbstract.EnumLogRotation)p_toLegacyData_1_.get(AXIS)) {
      case X:
         i |= 4;
         break;
      case Z:
         i |= 8;
         break;
      case NONE:
         i |= 12;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{VARIANT, AXIS});
   }

   protected ItemStack i(IBlockData p_i_1_) {
      return new ItemStack(Item.getItemOf(this), 1, ((BlockWood.EnumLogVariant)p_i_1_.get(VARIANT)).a() - 4);
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockWood.EnumLogVariant)p_getDropData_1_.get(VARIANT)).a() - 4;
   }
}
