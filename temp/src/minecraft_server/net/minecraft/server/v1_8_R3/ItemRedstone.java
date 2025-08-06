package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

public class ItemRedstone extends Item {
   public ItemRedstone() {
      this.a(CreativeModeTab.d);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      boolean flag = p_interactWith_3_.getType(p_interactWith_4_).getBlock().a(p_interactWith_3_, p_interactWith_4_);
      BlockPosition blockposition = flag?p_interactWith_4_:p_interactWith_4_.shift(p_interactWith_5_);
      if(!p_interactWith_2_.a(blockposition, p_interactWith_5_, p_interactWith_1_)) {
         return false;
      } else {
         Block block = p_interactWith_3_.getType(blockposition).getBlock();
         if(!p_interactWith_3_.a(block, blockposition, false, p_interactWith_5_, (Entity)null, p_interactWith_1_)) {
            return false;
         } else if(Blocks.REDSTONE_WIRE.canPlace(p_interactWith_3_, blockposition)) {
            --p_interactWith_1_.count;
            p_interactWith_3_.setTypeUpdate(blockposition, Blocks.REDSTONE_WIRE.getBlockData());
            return true;
         } else {
            return false;
         }
      }
   }
}
