package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockSnow;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemBlock;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

public class ItemReed extends Item {
   private Block a;

   public ItemReed(Block p_i1257_1_) {
      this.a = p_i1257_1_;
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
      Block block = iblockdata.getBlock();
      if(block == Blocks.SNOW_LAYER && ((Integer)iblockdata.get(BlockSnow.LAYERS)).intValue() < 1) {
         p_interactWith_5_ = EnumDirection.UP;
      } else if(!block.a(p_interactWith_3_, p_interactWith_4_)) {
         p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);
      }

      if(!p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_)) {
         return false;
      } else if(p_interactWith_1_.count == 0) {
         return false;
      } else {
         if(p_interactWith_3_.a(this.a, p_interactWith_4_, false, p_interactWith_5_, (Entity)null, p_interactWith_1_)) {
            IBlockData iblockdata1 = this.a.getPlacedState(p_interactWith_3_, p_interactWith_4_, p_interactWith_5_, p_interactWith_6_, p_interactWith_7_, p_interactWith_8_, 0, p_interactWith_2_);
            if(p_interactWith_3_.setTypeAndData(p_interactWith_4_, iblockdata1, 3)) {
               iblockdata1 = p_interactWith_3_.getType(p_interactWith_4_);
               if(iblockdata1.getBlock() == this.a) {
                  ItemBlock.a(p_interactWith_3_, p_interactWith_2_, p_interactWith_4_, p_interactWith_1_);
                  iblockdata1.getBlock().postPlace(p_interactWith_3_, p_interactWith_4_, iblockdata1, p_interactWith_2_, p_interactWith_1_);
               }

               p_interactWith_3_.makeSound((double)((float)p_interactWith_4_.getX() + 0.5F), (double)((float)p_interactWith_4_.getY() + 0.5F), (double)((float)p_interactWith_4_.getZ() + 0.5F), this.a.stepSound.getPlaceSound(), (this.a.stepSound.getVolume1() + 1.0F) / 2.0F, this.a.stepSound.getVolume2() * 0.8F);
               --p_interactWith_1_.count;
               return true;
            }
         }

         return false;
      }
   }
}
