package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPlant;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockTallPlant;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockFragilePlantElement;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class BlockLongGrass extends BlockPlant implements IBlockFragilePlantElement {
   public static final BlockStateEnum<BlockLongGrass.EnumTallGrassType> TYPE = BlockStateEnum.<BlockLongGrass.EnumTallGrassType>of("type", BlockLongGrass.EnumTallGrassType.class);

   protected BlockLongGrass() {
      super(Material.REPLACEABLE_PLANT);
      this.j(this.blockStateList.getBlockData().set(TYPE, BlockLongGrass.EnumTallGrassType.DEAD_BUSH));
      float f = 0.4F;
      this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
   }

   public boolean f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_) {
      return this.c(p_f_1_.getType(p_f_2_.down()).getBlock());
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_) {
      return true;
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return p_getDropType_2_.nextInt(8) == 0?Items.WHEAT_SEEDS:null;
   }

   public int getDropCount(int p_getDropCount_1_, Random p_getDropCount_2_) {
      return 1 + p_getDropCount_2_.nextInt(p_getDropCount_1_ * 2 + 1);
   }

   public void a(World p_a_1_, EntityHuman p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, TileEntity p_a_5_) {
      if(!p_a_1_.isClientSide && p_a_2_.bZ() != null && p_a_2_.bZ().getItem() == Items.SHEARS) {
         p_a_2_.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
         a(p_a_1_, p_a_3_, new ItemStack(Blocks.TALLGRASS, 1, ((BlockLongGrass.EnumTallGrassType)p_a_4_.get(TYPE)).a()));
      } else {
         super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);
      }

   }

   public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_) {
      IBlockData iblockdata = p_getDropData_1_.getType(p_getDropData_2_);
      return iblockdata.getBlock().toLegacyData(iblockdata);
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_) {
      return p_a_3_.get(TYPE) != BlockLongGrass.EnumTallGrassType.DEAD_BUSH;
   }

   public boolean a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_) {
      return true;
   }

   public void b(World p_b_1_, Random p_b_2_, BlockPosition p_b_3_, IBlockData p_b_4_) {
      BlockTallPlant.EnumTallFlowerVariants blocktallplant$enumtallflowervariants = BlockTallPlant.EnumTallFlowerVariants.GRASS;
      if(p_b_4_.get(TYPE) == BlockLongGrass.EnumTallGrassType.FERN) {
         blocktallplant$enumtallflowervariants = BlockTallPlant.EnumTallFlowerVariants.FERN;
      }

      if(Blocks.DOUBLE_PLANT.canPlace(p_b_1_, p_b_3_)) {
         Blocks.DOUBLE_PLANT.a(p_b_1_, p_b_3_, blocktallplant$enumtallflowervariants, 2);
      }

   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(TYPE, BlockLongGrass.EnumTallGrassType.a(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockLongGrass.EnumTallGrassType)p_toLegacyData_1_.get(TYPE)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{TYPE});
   }

   public static enum EnumTallGrassType implements INamable {
      DEAD_BUSH(0, "dead_bush"),
      GRASS(1, "tall_grass"),
      FERN(2, "fern");

      private static final BlockLongGrass.EnumTallGrassType[] d = new BlockLongGrass.EnumTallGrassType[values().length];
      private final int e;
      private final String f;

      private EnumTallGrassType(int p_i652_3_, String p_i652_4_) {
         this.e = p_i652_3_;
         this.f = p_i652_4_;
      }

      public int a() {
         return this.e;
      }

      public String toString() {
         return this.f;
      }

      public static BlockLongGrass.EnumTallGrassType a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= d.length) {
            p_a_0_ = 0;
         }

         return d[p_a_0_];
      }

      public String getName() {
         return this.f;
      }

      static {
         for(BlockLongGrass.EnumTallGrassType blocklonggrass$enumtallgrasstype : values()) {
            d[blocklonggrass$enumtallgrasstype.a()] = blocklonggrass$enumtallgrasstype;
         }

      }
   }
}
