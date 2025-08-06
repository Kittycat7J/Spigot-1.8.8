package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockCrops;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.World;

public class BlockPotatoes extends BlockCrops {
   protected Item l() {
      return Items.POTATO;
   }

   protected Item n() {
      return Items.POTATO;
   }

   public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_) {
      super.dropNaturally(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, p_dropNaturally_4_, p_dropNaturally_5_);
      if(!p_dropNaturally_1_.isClientSide) {
         if(((Integer)p_dropNaturally_3_.get(AGE)).intValue() >= 7 && p_dropNaturally_1_.random.nextInt(50) == 0) {
            a(p_dropNaturally_1_, p_dropNaturally_2_, new ItemStack(Items.POISONOUS_POTATO));
         }

      }
   }
}
