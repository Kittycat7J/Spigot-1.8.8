package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

public class ItemSeeds extends Item {
   private Block a;
   private Block b;

   public ItemSeeds(Block p_i514_1_, Block p_i514_2_) {
      this.a = p_i514_1_;
      this.b = p_i514_2_;
      this.a(CreativeModeTab.l);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      if(p_interactWith_5_ != EnumDirection.UP) {
         return false;
      } else if(!p_interactWith_2_.a(p_interactWith_4_.shift(p_interactWith_5_), p_interactWith_5_, p_interactWith_1_)) {
         return false;
      } else if(p_interactWith_3_.getType(p_interactWith_4_).getBlock() == this.b && p_interactWith_3_.isEmpty(p_interactWith_4_.up())) {
         p_interactWith_3_.setTypeUpdate(p_interactWith_4_.up(), this.a.getBlockData());
         --p_interactWith_1_.count;
         return true;
      } else {
         return false;
      }
   }
}
