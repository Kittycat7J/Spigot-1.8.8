package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockFalling;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class BlockSand extends BlockFalling {
   public static final BlockStateEnum<BlockSand.EnumSandVariant> VARIANT = BlockStateEnum.<BlockSand.EnumSandVariant>of("variant", BlockSand.EnumSandVariant.class);

   public BlockSand() {
      this.j(this.blockStateList.getBlockData().set(VARIANT, BlockSand.EnumSandVariant.SAND));
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockSand.EnumSandVariant)p_getDropData_1_.get(VARIANT)).a();
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return ((BlockSand.EnumSandVariant)p_g_1_.get(VARIANT)).c();
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(VARIANT, BlockSand.EnumSandVariant.a(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockSand.EnumSandVariant)p_toLegacyData_1_.get(VARIANT)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{VARIANT});
   }

   public static enum EnumSandVariant implements INamable {
      SAND(0, "sand", "default", MaterialMapColor.d),
      RED_SAND(1, "red_sand", "red", MaterialMapColor.q);

      private static final BlockSand.EnumSandVariant[] c = new BlockSand.EnumSandVariant[values().length];
      private final int d;
      private final String e;
      private final MaterialMapColor f;
      private final String g;

      private EnumSandVariant(int p_i640_3_, String p_i640_4_, String p_i640_5_, MaterialMapColor p_i640_6_) {
         this.d = p_i640_3_;
         this.e = p_i640_4_;
         this.f = p_i640_6_;
         this.g = p_i640_5_;
      }

      public int a() {
         return this.d;
      }

      public String toString() {
         return this.e;
      }

      public MaterialMapColor c() {
         return this.f;
      }

      public static BlockSand.EnumSandVariant a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= c.length) {
            p_a_0_ = 0;
         }

         return c[p_a_0_];
      }

      public String getName() {
         return this.e;
      }

      public String d() {
         return this.g;
      }

      static {
         for(BlockSand.EnumSandVariant blocksand$enumsandvariant : values()) {
            c[blocksand$enumsandvariant.a()] = blocksand$enumsandvariant;
         }

      }
   }
}
