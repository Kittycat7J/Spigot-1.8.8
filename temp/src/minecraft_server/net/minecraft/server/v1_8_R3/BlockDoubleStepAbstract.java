package net.minecraft.server.v1_8_R3;

import java.util.Random;
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
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public abstract class BlockDoubleStepAbstract extends BlockStepAbstract {
   public static final BlockStateBoolean SEAMLESS = BlockStateBoolean.of("seamless");
   public static final BlockStateEnum<BlockDoubleStepAbstract.EnumStoneSlabVariant> VARIANT = BlockStateEnum.<BlockDoubleStepAbstract.EnumStoneSlabVariant>of("variant", BlockDoubleStepAbstract.EnumStoneSlabVariant.class);

   public BlockDoubleStepAbstract() {
      super(Material.STONE);
      IBlockData iblockdata = this.blockStateList.getBlockData();
      if(this.l()) {
         iblockdata = iblockdata.set(SEAMLESS, Boolean.valueOf(false));
      } else {
         iblockdata = iblockdata.set(HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
      }

      this.j(iblockdata.set(VARIANT, BlockDoubleStepAbstract.EnumStoneSlabVariant.STONE));
      this.a(CreativeModeTab.b);
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Item.getItemOf(Blocks.STONE_SLAB);
   }

   public String b(int p_b_1_) {
      return super.a() + "." + BlockDoubleStepAbstract.EnumStoneSlabVariant.a(p_b_1_).d();
   }

   public IBlockState<?> n() {
      return VARIANT;
   }

   public Object a(ItemStack p_a_1_) {
      return BlockDoubleStepAbstract.EnumStoneSlabVariant.a(p_a_1_.getData() & 7);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      IBlockData iblockdata = this.getBlockData().set(VARIANT, BlockDoubleStepAbstract.EnumStoneSlabVariant.a(p_fromLegacyData_1_ & 7));
      if(this.l()) {
         iblockdata = iblockdata.set(SEAMLESS, Boolean.valueOf((p_fromLegacyData_1_ & 8) != 0));
      } else {
         iblockdata = iblockdata.set(HALF, (p_fromLegacyData_1_ & 8) == 0?BlockStepAbstract.EnumSlabHalf.BOTTOM:BlockStepAbstract.EnumSlabHalf.TOP);
      }

      return iblockdata;
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i = 0;
      i = i | ((BlockDoubleStepAbstract.EnumStoneSlabVariant)p_toLegacyData_1_.get(VARIANT)).a();
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

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockDoubleStepAbstract.EnumStoneSlabVariant)p_getDropData_1_.get(VARIANT)).a();
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return ((BlockDoubleStepAbstract.EnumStoneSlabVariant)p_g_1_.get(VARIANT)).c();
   }

   public static enum EnumStoneSlabVariant implements INamable {
      STONE(0, MaterialMapColor.m, "stone"),
      SAND(1, MaterialMapColor.d, "sandstone", "sand"),
      WOOD(2, MaterialMapColor.o, "wood_old", "wood"),
      COBBLESTONE(3, MaterialMapColor.m, "cobblestone", "cobble"),
      BRICK(4, MaterialMapColor.D, "brick"),
      SMOOTHBRICK(5, MaterialMapColor.m, "stone_brick", "smoothStoneBrick"),
      NETHERBRICK(6, MaterialMapColor.K, "nether_brick", "netherBrick"),
      QUARTZ(7, MaterialMapColor.p, "quartz");

      private static final BlockDoubleStepAbstract.EnumStoneSlabVariant[] i = new BlockDoubleStepAbstract.EnumStoneSlabVariant[values().length];
      private final int j;
      private final MaterialMapColor k;
      private final String l;
      private final String m;

      private EnumStoneSlabVariant(int p_i650_3_, MaterialMapColor p_i650_4_, String p_i650_5_) {
         this(p_i650_3_, p_i650_4_, p_i650_5_, p_i650_5_);
      }

      private EnumStoneSlabVariant(int p_i651_3_, MaterialMapColor p_i651_4_, String p_i651_5_, String p_i651_6_) {
         this.j = p_i651_3_;
         this.k = p_i651_4_;
         this.l = p_i651_5_;
         this.m = p_i651_6_;
      }

      public int a() {
         return this.j;
      }

      public MaterialMapColor c() {
         return this.k;
      }

      public String toString() {
         return this.l;
      }

      public static BlockDoubleStepAbstract.EnumStoneSlabVariant a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= i.length) {
            p_a_0_ = 0;
         }

         return i[p_a_0_];
      }

      public String getName() {
         return this.l;
      }

      public String d() {
         return this.m;
      }

      static {
         for(BlockDoubleStepAbstract.EnumStoneSlabVariant blockdoublestepabstract$enumstoneslabvariant : values()) {
            i[blockdoublestepabstract$enumstoneslabvariant.a()] = blockdoublestepabstract$enumstoneslabvariant;
         }

      }
   }
}
