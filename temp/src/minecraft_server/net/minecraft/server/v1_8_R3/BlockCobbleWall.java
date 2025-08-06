package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockFenceGate;
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
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class BlockCobbleWall extends Block {
   public static final BlockStateBoolean UP = BlockStateBoolean.of("up");
   public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
   public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
   public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
   public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");
   public static final BlockStateEnum<BlockCobbleWall.EnumCobbleVariant> VARIANT = BlockStateEnum.<BlockCobbleWall.EnumCobbleVariant>of("variant", BlockCobbleWall.EnumCobbleVariant.class);

   public BlockCobbleWall(Block p_i656_1_) {
      super(p_i656_1_.material);
      this.j(this.blockStateList.getBlockData().set(UP, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)).set(VARIANT, BlockCobbleWall.EnumCobbleVariant.NORMAL));
      this.c(p_i656_1_.strength);
      this.b(p_i656_1_.durability / 3.0F);
      this.a(p_i656_1_.stepSound);
      this.a(CreativeModeTab.b);
   }

   public String getName() {
      return LocaleI18n.get(this.a() + "." + BlockCobbleWall.EnumCobbleVariant.NORMAL.c() + ".name");
   }

   public boolean d() {
      return false;
   }

   public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_) {
      return false;
   }

   public boolean c() {
      return false;
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      boolean flag = this.e(p_updateShape_1_, p_updateShape_2_.north());
      boolean flag1 = this.e(p_updateShape_1_, p_updateShape_2_.south());
      boolean flag2 = this.e(p_updateShape_1_, p_updateShape_2_.west());
      boolean flag3 = this.e(p_updateShape_1_, p_updateShape_2_.east());
      float f = 0.25F;
      float f1 = 0.75F;
      float f2 = 0.25F;
      float f3 = 0.75F;
      float f4 = 1.0F;
      if(flag) {
         f2 = 0.0F;
      }

      if(flag1) {
         f3 = 1.0F;
      }

      if(flag2) {
         f = 0.0F;
      }

      if(flag3) {
         f1 = 1.0F;
      }

      if(flag && flag1 && !flag2 && !flag3) {
         f4 = 0.8125F;
         f = 0.3125F;
         f1 = 0.6875F;
      } else if(!flag && !flag1 && flag2 && flag3) {
         f4 = 0.8125F;
         f2 = 0.3125F;
         f3 = 0.6875F;
      }

      this.a(f, 0.0F, f2, f1, f4, f3);
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      this.updateShape(p_a_1_, p_a_2_);
      this.maxY = 1.5D;
      return super.a(p_a_1_, p_a_2_, p_a_3_);
   }

   public boolean e(IBlockAccess p_e_1_, BlockPosition p_e_2_) {
      Block block = p_e_1_.getType(p_e_2_).getBlock();
      return block == Blocks.BARRIER?false:(block != this && !(block instanceof BlockFenceGate)?(block.material.k() && block.d()?block.material != Material.PUMPKIN:false):true);
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockCobbleWall.EnumCobbleVariant)p_getDropData_1_.get(VARIANT)).a();
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(VARIANT, BlockCobbleWall.EnumCobbleVariant.a(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockCobbleWall.EnumCobbleVariant)p_toLegacyData_1_.get(VARIANT)).a();
   }

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      return p_updateState_1_.set(UP, Boolean.valueOf(!p_updateState_2_.isEmpty(p_updateState_3_.up()))).set(NORTH, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.north()))).set(EAST, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.east()))).set(SOUTH, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.south()))).set(WEST, Boolean.valueOf(this.e(p_updateState_2_, p_updateState_3_.west())));
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{UP, NORTH, EAST, WEST, SOUTH, VARIANT});
   }

   public static enum EnumCobbleVariant implements INamable {
      NORMAL(0, "cobblestone", "normal"),
      MOSSY(1, "mossy_cobblestone", "mossy");

      private static final BlockCobbleWall.EnumCobbleVariant[] c = new BlockCobbleWall.EnumCobbleVariant[values().length];
      private final int d;
      private final String e;
      private String f;

      private EnumCobbleVariant(int p_i655_3_, String p_i655_4_, String p_i655_5_) {
         this.d = p_i655_3_;
         this.e = p_i655_4_;
         this.f = p_i655_5_;
      }

      public int a() {
         return this.d;
      }

      public String toString() {
         return this.e;
      }

      public static BlockCobbleWall.EnumCobbleVariant a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= c.length) {
            p_a_0_ = 0;
         }

         return c[p_a_0_];
      }

      public String getName() {
         return this.e;
      }

      public String c() {
         return this.f;
      }

      static {
         for(BlockCobbleWall.EnumCobbleVariant blockcobblewall$enumcobblevariant : values()) {
            c[blockcobblewall$enumcobblevariant.a()] = blockcobblewall$enumcobblevariant;
         }

      }
   }
}
