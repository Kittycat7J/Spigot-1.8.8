package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockCactus extends Block {
   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 15);

   protected BlockCactus() {
      super(Material.CACTUS);
      this.j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)));
      this.a(true);
      this.a(CreativeModeTab.c);
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      BlockPosition blockposition = p_b_2_.up();
      if(p_b_1_.isEmpty(blockposition)) {
         int i;
         for(i = 1; p_b_1_.getType(p_b_2_.down(i)).getBlock() == this; ++i) {
            ;
         }

         if(i < 3) {
            int j = ((Integer)p_b_3_.get(AGE)).intValue();
            if(j >= (byte)((int)range(3.0F, p_b_1_.growthOdds / (float)p_b_1_.spigotConfig.cactusModifier * 15.0F + 0.5F, 15.0F))) {
               IBlockData iblockdata = p_b_3_.set(AGE, Integer.valueOf(0));
               CraftEventFactory.handleBlockGrowEvent(p_b_1_, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this, 0);
               p_b_1_.setTypeAndData(p_b_2_, iblockdata, 4);
               this.doPhysics(p_b_1_, blockposition, iblockdata, this);
            } else {
               p_b_1_.setTypeAndData(p_b_2_, p_b_3_.set(AGE, Integer.valueOf(j + 1)), 4);
            }
         }
      }

   }

   public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_) {
      float f = 0.0625F;
      return new AxisAlignedBB((double)((float)p_a_2_.getX() + f), (double)p_a_2_.getY(), (double)((float)p_a_2_.getZ() + f), (double)((float)(p_a_2_.getX() + 1) - f), (double)((float)(p_a_2_.getY() + 1) - f), (double)((float)(p_a_2_.getZ() + 1) - f));
   }

   public boolean d() {
      return false;
   }

   public boolean c() {
      return false;
   }

   public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_) {
      return super.canPlace(p_canPlace_1_, p_canPlace_2_)?this.e(p_canPlace_1_, p_canPlace_2_):false;
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      if(!this.e(p_doPhysics_1_, p_doPhysics_2_)) {
         p_doPhysics_1_.setAir(p_doPhysics_2_, true);
      }

   }

   public boolean e(World p_e_1_, BlockPosition p_e_2_) {
      for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
         if(p_e_1_.getType(p_e_2_.shift(enumdirection)).getBlock().getMaterial().isBuildable()) {
            return false;
         }
      }

      Block block = p_e_1_.getType(p_e_2_.down()).getBlock();
      return block == Blocks.CACTUS || block == Blocks.SAND;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_) {
      CraftEventFactory.blockDamage = p_a_1_.getWorld().getBlockAt(p_a_2_.getX(), p_a_2_.getY(), p_a_2_.getZ());
      p_a_4_.damageEntity(DamageSource.CACTUS, 1.0F);
      CraftEventFactory.blockDamage = null;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(AGE, Integer.valueOf(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((Integer)p_toLegacyData_1_.get(AGE)).intValue();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{AGE});
   }
}
