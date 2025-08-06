package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockFluids;
import net.minecraft.server.v1_8_R3.BlockPlant;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityBoat;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class BlockWaterLily extends BlockPlant {
   protected BlockWaterLily() {
      float f = 0.5F;
      float f1 = 0.015625F;
      this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
      this.a(CreativeModeTab.c);
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_) {
      if(p_a_6_ == null || !(p_a_6_ instanceof EntityBoat)) {
         super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      }

   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      return new AxisAlignedBB((double)p_a_2_.getX() + this.minX, (double)p_a_2_.getY() + this.minY, (double)p_a_2_.getZ() + this.minZ, (double)p_a_2_.getX() + this.maxX, (double)p_a_2_.getY() + this.maxY, (double)p_a_2_.getZ() + this.maxZ);
   }

   protected boolean c(Block p_c_1_) {
      return p_c_1_ == Blocks.WATER;
   }

   public boolean f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_) {
      if(p_f_2_.getY() >= 0 && p_f_2_.getY() < 256) {
         IBlockData iblockdata = p_f_1_.getType(p_f_2_.down());
         return iblockdata.getBlock().getMaterial() == Material.WATER && ((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0;
      } else {
         return false;
      }
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return 0;
   }
}
