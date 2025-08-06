package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockSand;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockStepAbstract;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public abstract class BlockDoubleStoneStepAbstract extends BlockStepAbstract {
   public static final BlockStateBoolean SEAMLESS = BlockStateBoolean.of("seamless");
   public static final BlockStateEnum<BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant> VARIANT = BlockStateEnum.<BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant>of("variant", BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant.class);

   public BlockDoubleStoneStepAbstract() {
      super(Material.STONE);
      IBlockData iblockdata = this.blockStateList.getBlockData();
      if(this.l()) {
         iblockdata = iblockdata.set(SEAMLESS, Boolean.valueOf(false));
      } else {
         iblockdata = iblockdata.set(HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
      }

      this.j(iblockdata.set(VARIANT, BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant.RED_SANDSTONE));
      this.a(CreativeModeTab.b);
   }

   public String getName() {
      return LocaleI18n.get(this.a() + ".red_sandstone.name");
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Item.getItemOf(Blocks.STONE_SLAB2);
   }

   public String b(int p_b_1_) {
      return super.a() + "." + BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant.a(p_b_1_).d();
   }

   public IBlockState<?> n() {
      return VARIANT;
   }

   public Object a(ItemStack p_a_1_) {
      return BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant.a(p_a_1_.getData() & 7);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      IBlockData iblockdata = this.getBlockData().set(VARIANT, BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant.a(p_fromLegacyData_1_ & 7));
      if(this.l()) {
         iblockdata = iblockdata.set(SEAMLESS, Boolean.valueOf((p_fromLegacyData_1_ & 8) != 0));
      } else {
         iblockdata = iblockdata.set(HALF, (p_fromLegacyData_1_ & 8) == 0?BlockStepAbstract.EnumSlabHalf.BOTTOM:BlockStepAbstract.EnumSlabHalf.TOP);
      }

      return iblockdata;
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i = 0;
      i = i | ((BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant)p_toLegacyData_1_.get(VARIANT)).a();
      if(this.l()) {
         if(((Boolean)p_toLegacyData_1_.get(SEAMLESS)).booleanValue()) {
            i |= 8;
         }
      } else if(p_toLegacyData_1_.get(HALF) == BlockStepAbstract.EnumSlabHalf.TOP) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return this.l()?new BlockStateList(this, new IBlockState[]{SEAMLESS, VARIANT}):new BlockStateList(this, new IBlockState[]{HALF, VARIANT});
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return ((BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant)p_g_1_.get(VARIANT)).c();
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant)p_getDropData_1_.get(VARIANT)).a();
   }

   public static enum EnumStoneSlab2Variant implements INamable {
      RED_SANDSTONE(0, "red_sandstone", BlockSand.EnumSandVariant.RED_SAND.c());

      private static final BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant[] b = new BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant[values().length];
      private final int c;
      private final String d;
      private final MaterialMapColor e;

      private EnumStoneSlab2Variant(int p_i630_3_, String p_i630_4_, MaterialMapColor p_i630_5_) {
         this.c = p_i630_3_;
         this.d = p_i630_4_;
         this.e = p_i630_5_;
      }

      public int a() {
         return this.c;
      }

      public MaterialMapColor c() {
         return this.e;
      }

      public String toString() {
         return this.d;
      }

      public static BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= b.length) {
            p_a_0_ = 0;
         }

         return b[p_a_0_];
      }

      public String getName() {
         return this.d;
      }

      public String d() {
         return this.d;
      }

      static {
         for(BlockDoubleStoneStepAbstract.EnumStoneSlab2Variant blockdoublestonestepabstract$enumstoneslab2variant : values()) {
            b[blockdoublestonestepabstract$enumstoneslab2variant.a()] = blockdoublestonestepabstract$enumstoneslab2variant;
         }

      }
   }
}
