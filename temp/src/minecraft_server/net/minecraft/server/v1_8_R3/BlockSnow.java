package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumSkyBlock;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockSnow extends Block {
   public static final BlockStateInteger LAYERS = BlockStateInteger.of("layers", 1, 8);

   protected BlockSnow() {
      super(Material.PACKED_ICE);
      this.j(this.blockStateList.getBlockData().set(LAYERS, Integer.valueOf(1)));
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.a(true);
      this.a(CreativeModeTab.c);
      this.j();
   }

   public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_) {
      return ((Integer)p_b_1_.getType(p_b_2_).get(LAYERS)).intValue() < 5;
   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      int i = ((Integer)p_a_3_.get(LAYERS)).intValue() - 1;
      float f = 0.125F;
      return new AxisAlignedBB((double)p_a_2_.getX() + this.minX, (double)p_a_2_.getY() + this.minY, (double)p_a_2_.getZ() + this.minZ, (double)p_a_2_.getX() + this.maxX, (double)((float)p_a_2_.getY() + (float)i * f), (double)p_a_2_.getZ() + this.maxZ);
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public void j() {
      this.b(0);
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      IBlockData iblockdata = p_updateShape_1_.getType(p_updateShape_2_);
      this.b(((Integer)iblockdata.get(LAYERS)).intValue());
   }

   protected void b(int p_b_1_) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, (float)p_b_1_ / 8.0F, 1.0F);
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      IBlockData iblockdata = p_canPlace_1_.getType(p_canPlace_2_.down());
      Block block = iblockdata.getBlock();
      return block != Blocks.ICE && block != Blocks.PACKED_ICE?(block.getMaterial() == Material.LEAVES?true:(block == this && ((Integer)iblockdata.get(LAYERS)).intValue() >= 7?true:block.c() && block.material.isSolid())):false;
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
   }

   private boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_) {
      if(!this.canPlace(p_e_1_, p_e_2_)) {
         this.b(p_e_1_, p_e_2_, p_e_3_, 0);
         p_e_1_.setAir(p_e_2_);
         return false;
      } else {
         return true;
      }
   }

   public void a(World p_a_1_, EntityHuman p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, TileEntity p_a_5_) {
      a(p_a_1_, p_a_3_, new ItemStack(Items.SNOWBALL, ((Integer)p_a_4_.get(LAYERS)).intValue() + 1, 0));
      p_a_1_.setAir(p_a_3_);
      p_a_2_.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Items.SNOWBALL;
   }

   public int a(Random p_a_1_) {
      return 0;
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(p_b_1_.b(EnumSkyBlock.BLOCK, p_b_2_) > 11) {
         if(CraftEventFactory.callBlockFadeEvent(p_b_1_.getWorld().getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()), Blocks.AIR).isCancelled()) {
            return;
         }

         this.b(p_b_1_, p_b_2_, p_b_1_.getType(p_b_2_), 0);
         p_b_1_.setAir(p_b_2_);
      }

   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(LAYERS, Integer.valueOf((p_fromLegacyData_1_ & 7) + 1));
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_) {
      return ((Integer)p_a_1_.getType(p_a_2_).get(LAYERS)).intValue() == 1;
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((Integer)p_toLegacyData_1_.get(LAYERS)).intValue() - 1;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{LAYERS});
   }
}
