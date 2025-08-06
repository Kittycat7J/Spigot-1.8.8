package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.World;

public class BlockDirt extends Block {
   public static final BlockStateEnum<BlockDirt.EnumDirtVariant> VARIANT = BlockStateEnum.<BlockDirt.EnumDirtVariant>of("variant", BlockDirt.EnumDirtVariant.class);
   public static final BlockStateBoolean SNOWY = BlockStateBoolean.of("snowy");

   protected BlockDirt() {
      super(Material.EARTH);
      this.j(this.blockStateList.getBlockData().set(VARIANT, BlockDirt.EnumDirtVariant.DIRT).set(SNOWY, Boolean.valueOf(false)));
      this.a(CreativeModeTab.b);
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return ((BlockDirt.EnumDirtVariant)p_g_1_.get(VARIANT)).d();
   }

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      if(p_updateState_1_.get(VARIANT) == BlockDirt.EnumDirtVariant.PODZOL) {
         Block block = p_updateState_2_.getType(p_updateState_3_.up()).getBlock();
         p_updateState_1_ = p_updateState_1_.set(SNOWY, Boolean.valueOf(block == Blocks.SNOW || block == Blocks.SNOW_LAYER));
      }

      return p_updateState_1_;
   }

   public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_) {
      IBlockData iblockdata = p_getDropData_1_.getType(p_getDropData_2_);
      return iblockdata.getBlock() != this?0:((BlockDirt.EnumDirtVariant)iblockdata.get(VARIANT)).a();
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(VARIANT, BlockDirt.EnumDirtVariant.a(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockDirt.EnumDirtVariant)p_toLegacyData_1_.get(VARIANT)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{VARIANT, SNOWY});
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      BlockDirt.EnumDirtVariant blockdirt$enumdirtvariant = (BlockDirt.EnumDirtVariant)p_getDropData_1_.get(VARIANT);
      if(blockdirt$enumdirtvariant == BlockDirt.EnumDirtVariant.PODZOL) {
         blockdirt$enumdirtvariant = BlockDirt.EnumDirtVariant.DIRT;
      }

      return blockdirt$enumdirtvariant.a();
   }

   public static enum EnumDirtVariant implements INamable {
      DIRT(0, "dirt", "default", MaterialMapColor.l),
      COARSE_DIRT(1, "coarse_dirt", "coarse", MaterialMapColor.l),
      PODZOL(2, "podzol", MaterialMapColor.J);

      private static final BlockDirt.EnumDirtVariant[] d = new BlockDirt.EnumDirtVariant[values().length];
      private final int e;
      private final String f;
      private final String g;
      private final MaterialMapColor h;

      private EnumDirtVariant(int p_i605_3_, String p_i605_4_, MaterialMapColor p_i605_5_) {
         this(p_i605_3_, p_i605_4_, p_i605_4_, p_i605_5_);
      }

      private EnumDirtVariant(int p_i606_3_, String p_i606_4_, String p_i606_5_, MaterialMapColor p_i606_6_) {
         this.e = p_i606_3_;
         this.f = p_i606_4_;
         this.g = p_i606_5_;
         this.h = p_i606_6_;
      }

      public int a() {
         return this.e;
      }

      public String c() {
         return this.g;
      }

      public MaterialMapColor d() {
         return this.h;
      }

      public String toString() {
         return this.f;
      }

      public static BlockDirt.EnumDirtVariant a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= d.length) {
            p_a_0_ = 0;
         }

         return d[p_a_0_];
      }

      public String getName() {
         return this.f;
      }

      static {
         for(BlockDirt.EnumDirtVariant blockdirt$enumdirtvariant : values()) {
            d[blockdirt$enumdirtvariant.a()] = blockdirt$enumdirtvariant;
         }

      }
   }
}
