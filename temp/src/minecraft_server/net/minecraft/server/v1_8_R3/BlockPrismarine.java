package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class BlockPrismarine extends Block {
   public static final BlockStateEnum<BlockPrismarine.EnumPrismarineVariant> VARIANT = BlockStateEnum.<BlockPrismarine.EnumPrismarineVariant>of("variant", BlockPrismarine.EnumPrismarineVariant.class);
   public static final int b = BlockPrismarine.EnumPrismarineVariant.ROUGH.a();
   public static final int N = BlockPrismarine.EnumPrismarineVariant.BRICKS.a();
   public static final int O = BlockPrismarine.EnumPrismarineVariant.DARK.a();

   public BlockPrismarine() {
      super(Material.STONE);
      this.j(this.blockStateList.getBlockData().set(VARIANT, BlockPrismarine.EnumPrismarineVariant.ROUGH));
      this.a(CreativeModeTab.b);
   }

   public String getName() {
      return LocaleI18n.get(this.a() + "." + BlockPrismarine.EnumPrismarineVariant.ROUGH.c() + ".name");
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return p_g_1_.get(VARIANT) == BlockPrismarine.EnumPrismarineVariant.ROUGH?MaterialMapColor.y:MaterialMapColor.G;
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockPrismarine.EnumPrismarineVariant)p_getDropData_1_.get(VARIANT)).a();
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockPrismarine.EnumPrismarineVariant)p_toLegacyData_1_.get(VARIANT)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{VARIANT});
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(VARIANT, BlockPrismarine.EnumPrismarineVariant.a(p_fromLegacyData_1_));
   }

   public static enum EnumPrismarineVariant implements INamable {
      ROUGH(0, "prismarine", "rough"),
      BRICKS(1, "prismarine_bricks", "bricks"),
      DARK(2, "dark_prismarine", "dark");

      private static final BlockPrismarine.EnumPrismarineVariant[] d = new BlockPrismarine.EnumPrismarineVariant[values().length];
      private final int e;
      private final String f;
      private final String g;

      private EnumPrismarineVariant(int p_i634_3_, String p_i634_4_, String p_i634_5_) {
         this.e = p_i634_3_;
         this.f = p_i634_4_;
         this.g = p_i634_5_;
      }

      public int a() {
         return this.e;
      }

      public String toString() {
         return this.f;
      }

      public static BlockPrismarine.EnumPrismarineVariant a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= d.length) {
            p_a_0_ = 0;
         }

         return d[p_a_0_];
      }

      public String getName() {
         return this.f;
      }

      public String c() {
         return this.g;
      }

      static {
         for(BlockPrismarine.EnumPrismarineVariant blockprismarine$enumprismarinevariant : values()) {
            d[blockprismarine$enumprismarinevariant.a()] = blockprismarine$enumprismarinevariant;
         }

      }
   }
}
