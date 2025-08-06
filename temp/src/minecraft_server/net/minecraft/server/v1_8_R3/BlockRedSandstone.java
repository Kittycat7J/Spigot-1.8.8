package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockSand;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Material;

public class BlockRedSandstone extends Block {
   public static final BlockStateEnum<BlockRedSandstone.EnumRedSandstoneVariant> TYPE = BlockStateEnum.<BlockRedSandstone.EnumRedSandstoneVariant>of("type", BlockRedSandstone.EnumRedSandstoneVariant.class);

   public BlockRedSandstone() {
      super(Material.STONE, BlockSand.EnumSandVariant.RED_SAND.c());
      this.j(this.blockStateList.getBlockData().set(TYPE, BlockRedSandstone.EnumRedSandstoneVariant.DEFAULT));
      this.a(CreativeModeTab.b);
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockRedSandstone.EnumRedSandstoneVariant)p_getDropData_1_.get(TYPE)).a();
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(TYPE, BlockRedSandstone.EnumRedSandstoneVariant.a(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockRedSandstone.EnumRedSandstoneVariant)p_toLegacyData_1_.get(TYPE)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{TYPE});
   }

   public static enum EnumRedSandstoneVariant implements INamable {
      DEFAULT(0, "red_sandstone", "default"),
      CHISELED(1, "chiseled_red_sandstone", "chiseled"),
      SMOOTH(2, "smooth_red_sandstone", "smooth");

      private static final BlockRedSandstone.EnumRedSandstoneVariant[] d = new BlockRedSandstone.EnumRedSandstoneVariant[values().length];
      private final int e;
      private final String f;
      private final String g;

      private EnumRedSandstoneVariant(int p_i636_3_, String p_i636_4_, String p_i636_5_) {
         this.e = p_i636_3_;
         this.f = p_i636_4_;
         this.g = p_i636_5_;
      }

      public int a() {
         return this.e;
      }

      public String toString() {
         return this.f;
      }

      public static BlockRedSandstone.EnumRedSandstoneVariant a(int p_a_0_) {
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
         for(BlockRedSandstone.EnumRedSandstoneVariant blockredsandstone$enumredsandstonevariant : values()) {
            d[blockredsandstone$enumredsandstonevariant.a()] = blockredsandstone$enumredsandstonevariant;
         }

      }
   }
}
