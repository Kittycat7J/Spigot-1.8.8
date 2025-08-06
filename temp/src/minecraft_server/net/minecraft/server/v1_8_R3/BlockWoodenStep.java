package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockStepAbstract;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public abstract class BlockWoodenStep extends BlockStepAbstract {
   public static final BlockStateEnum<BlockWood.EnumLogVariant> VARIANT = BlockStateEnum.<BlockWood.EnumLogVariant>of("variant", BlockWood.EnumLogVariant.class);

   public BlockWoodenStep() {
      super(Material.WOOD);
      IBlockData iblockdata = this.blockStateList.getBlockData();
      if(!this.l()) {
         iblockdata = iblockdata.set(HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
      }

      this.j(iblockdata.set(VARIANT, BlockWood.EnumLogVariant.OAK));
      this.a(CreativeModeTab.b);
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return ((BlockWood.EnumLogVariant)p_g_1_.get(VARIANT)).c();
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Item.getItemOf(Blocks.WOODEN_SLAB);
   }

   public String b(int p_b_1_) {
      return super.a() + "." + BlockWood.EnumLogVariant.a(p_b_1_).d();
   }

   public IBlockState<?> n() {
      return VARIANT;
   }

   public Object a(ItemStack p_a_1_) {
      return BlockWood.EnumLogVariant.a(p_a_1_.getData() & 7);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      IBlockData iblockdata = this.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.a(p_fromLegacyData_1_ & 7));
      if(!this.l()) {
         iblockdata = iblockdata.set(HALF, (p_fromLegacyData_1_ & 8) == 0?BlockStepAbstract.EnumSlabHalf.BOTTOM:BlockStepAbstract.EnumSlabHalf.TOP);
      }

      return iblockdata;
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i = 0;
      i = i | ((BlockWood.EnumLogVariant)p_toLegacyData_1_.get(VARIANT)).a();
      if(!this.l() && p_toLegacyData_1_.get(HALF) == BlockStepAbstract.EnumSlabHalf.TOP) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return this.l()?new BlockStateList(this, new IBlockState[]{VARIANT}):new BlockStateList(this, new IBlockState[]{HALF, VARIANT});
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockWood.EnumLogVariant)p_getDropData_1_.get(VARIANT)).a();
   }
}
