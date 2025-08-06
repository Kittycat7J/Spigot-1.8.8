package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPlant;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockNetherWart extends BlockPlant {
   public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 3);

   protected BlockNetherWart() {
      super(Material.PLANT, MaterialMapColor.D);
      this.j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)));
      this.a(true);
      float f = 0.5F;
      this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
      this.a((CreativeModeTab)null);
   }

   protected boolean c(Block p_c_1_) {
      return p_c_1_ == Blocks.SOUL_SAND;
   }

   public boolean f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_) {
      return this.c(p_f_1_.getType(p_f_2_.down()).getBlock());
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      int i = ((Integer)p_b_3_.get(AGE)).intValue();
      if(i < 3 && p_b_4_.nextInt(Math.max(1, (int)p_b_1_.growthOdds / p_b_1_.spigotConfig.wartModifier * 10)) == 0) {
         p_b_3_ = p_b_3_.set(AGE, Integer.valueOf(i + 1));
         CraftEventFactory.handleBlockGrowEvent(p_b_1_, p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ(), this, this.toLegacyData(p_b_3_));
      }

      super.b(p_b_1_, p_b_2_, p_b_3_, p_b_4_);
   }

   public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_) {
      if(!p_dropNaturally_1_.isClientSide) {
         int i = 1;
         if(((Integer)p_dropNaturally_3_.get(AGE)).intValue() >= 3) {
            i = 2 + p_dropNaturally_1_.random.nextInt(3);
            if(p_dropNaturally_5_ > 0) {
               i += p_dropNaturally_1_.random.nextInt(p_dropNaturally_5_ + 1);
            }
         }

         for(int j = 0; j < i; ++j) {
            a(p_dropNaturally_1_, p_dropNaturally_2_, new ItemStack(Items.NETHER_WART));
         }
      }

   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return null;
   }

   public int a(Random p_a_1_) {
      return 0;
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
