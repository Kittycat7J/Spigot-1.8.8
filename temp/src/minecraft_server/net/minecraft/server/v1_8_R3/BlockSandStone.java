package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class BlockSandStone extends Block {
   public static final BlockStateEnum<BlockSandStone.EnumSandstoneVariant> TYPE = BlockStateEnum.<BlockSandStone.EnumSandstoneVariant>of("type", BlockSandStone.EnumSandstoneVariant.class);

   public BlockSandStone() {
      super(Material.STONE);
      this.j(this.blockStateList.getBlockData().set(TYPE, BlockSandStone.EnumSandstoneVariant.DEFAULT));
      this.a(CreativeModeTab.b);
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockSandStone.EnumSandstoneVariant)p_getDropData_1_.get(TYPE)).a();
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return MaterialMapColor.d;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(TYPE, BlockSandStone.EnumSandstoneVariant.a(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockSandStone.EnumSandstoneVariant)p_toLegacyData_1_.get(TYPE)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{TYPE});
   }

   public static enum EnumSandstoneVariant implements INamable {
      DEFAULT(0, "sandstone", "default"),
      CHISELED(1, "chiseled_sandstone", "chiseled"),
      SMOOTH(2, "smooth_sandstone", "smooth");

      private static final BlockSandStone.EnumSandstoneVariant[] d = new BlockSandStone.EnumSandstoneVariant[values().length];
      private final int e;
      private final String f;
      private final String g;

      private EnumSandstoneVariant(int p_i641_3_, String p_i641_4_, String p_i641_5_) {
         this.e = p_i641_3_;
         this.f = p_i641_4_;
         this.g = p_i641_5_;
      }

      public int a() {
         return this.e;
      }

      public String toString() {
         return this.f;
      }

      public static BlockSandStone.EnumSandstoneVariant a(int p_a_0_) {
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
         for(BlockSandStone.EnumSandstoneVariant blocksandstone$enumsandstonevariant : values()) {
            d[blocksandstone$enumsandstonevariant.a()] = blocksandstone$enumsandstonevariant;
         }

      }
   }
}
