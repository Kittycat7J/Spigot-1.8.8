package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;

public class ItemFlintAndSteel extends Item {
   public ItemFlintAndSteel() {
      this.maxStackSize = 1;
      this.setMaxDurability(64);
      this.a(CreativeModeTab.i);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      BlockPosition blockposition = p_interactWith_4_;
      p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);
      if(!p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_)) {
         return false;
      } else {
         if(p_interactWith_3_.getType(p_interactWith_4_).getBlock().getMaterial() == Material.AIR) {
            if(CraftEventFactory.callBlockIgniteEvent(p_interactWith_3_, p_interactWith_4_.getX(), p_interactWith_4_.getY(), p_interactWith_4_.getZ(), IgniteCause.FLINT_AND_STEEL, p_interactWith_2_).isCancelled()) {
               p_interactWith_1_.damage(1, p_interactWith_2_);
               return false;
            }

            CraftBlockState craftblockstate = CraftBlockState.getBlockState(p_interactWith_3_, p_interactWith_4_.getX(), p_interactWith_4_.getY(), p_interactWith_4_.getZ());
            p_interactWith_3_.makeSound((double)p_interactWith_4_.getX() + 0.5D, (double)p_interactWith_4_.getY() + 0.5D, (double)p_interactWith_4_.getZ() + 0.5D, "fire.ignite", 1.0F, g.nextFloat() * 0.4F + 0.8F);
            p_interactWith_3_.setTypeUpdate(p_interactWith_4_, Blocks.FIRE.getBlockData());
            BlockPlaceEvent blockplaceevent = CraftEventFactory.callBlockPlaceEvent(p_interactWith_3_, p_interactWith_2_, craftblockstate, blockposition.getX(), blockposition.getY(), blockposition.getZ());
            if(blockplaceevent.isCancelled() || !blockplaceevent.canBuild()) {
               blockplaceevent.getBlockPlaced().setTypeIdAndData(0, (byte)0, false);
               return false;
            }
         }

         p_interactWith_1_.damage(1, p_interactWith_2_);
         return true;
      }
   }
}
