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

public class BlockWood extends Block {
   public static final BlockStateEnum<BlockWood.EnumLogVariant> VARIANT = BlockStateEnum.<BlockWood.EnumLogVariant>of("variant", BlockWood.EnumLogVariant.class);

   public BlockWood() {
      super(Material.WOOD);
      this.j(this.blockStateList.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.OAK));
      this.a(CreativeModeTab.b);
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockWood.EnumLogVariant)p_getDropData_1_.get(VARIANT)).a();
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.a(p_fromLegacyData_1_));
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return ((BlockWood.EnumLogVariant)p_g_1_.get(VARIANT)).c();
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockWood.EnumLogVariant)p_toLegacyData_1_.get(VARIANT)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{VARIANT});
   }

   public static enum EnumLogVariant implements INamable {
      OAK(0, "oak", MaterialMapColor.o),
      SPRUCE(1, "spruce", MaterialMapColor.J),
      BIRCH(2, "birch", MaterialMapColor.d),
      JUNGLE(3, "jungle", MaterialMapColor.l),
      ACACIA(4, "acacia", MaterialMapColor.q),
      DARK_OAK(5, "dark_oak", "big_oak", MaterialMapColor.B);

      private static final BlockWood.EnumLogVariant[] g = new BlockWood.EnumLogVariant[values().length];
      private final int h;
      private final String i;
      private final String j;
      private final MaterialMapColor k;

      private EnumLogVariant(int p_i631_3_, String p_i631_4_, MaterialMapColor p_i631_5_) {
         this(p_i631_3_, p_i631_4_, p_i631_4_, p_i631_5_);
      }

      private EnumLogVariant(int p_i632_3_, String p_i632_4_, String p_i632_5_, MaterialMapColor p_i632_6_) {
         this.h = p_i632_3_;
         this.i = p_i632_4_;
         this.j = p_i632_5_;
         this.k = p_i632_6_;
      }

      public int a() {
         return this.h;
      }

      public MaterialMapColor c() {
         return this.k;
      }

      public String toString() {
         return this.i;
      }

      public static BlockWood.EnumLogVariant a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= g.length) {
            p_a_0_ = 0;
         }

         return g[p_a_0_];
      }

      public String getName() {
         return this.i;
      }

      public String d() {
         return this.j;
      }

      static {
         for(BlockWood.EnumLogVariant blockwood$enumlogvariant : values()) {
            g[blockwood$enumlogvariant.a()] = blockwood$enumlogvariant;
         }

      }
   }
}
