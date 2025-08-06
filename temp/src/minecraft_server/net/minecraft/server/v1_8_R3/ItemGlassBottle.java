package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;

public class ItemGlassBottle extends Item {
   public ItemGlassBottle() {
      this.a(CreativeModeTab.k);
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      MovingObjectPosition movingobjectposition = this.a(p_a_2_, p_a_3_, true);
      if(movingobjectposition == null) {
         return p_a_1_;
      } else {
         if(movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
            BlockPosition blockposition = movingobjectposition.a();
            if(!p_a_2_.a(p_a_3_, blockposition)) {
               return p_a_1_;
            }

            if(!p_a_3_.a(blockposition.shift(movingobjectposition.direction), movingobjectposition.direction, p_a_1_)) {
               return p_a_1_;
            }

            if(p_a_2_.getType(blockposition).getBlock().getMaterial() == Material.WATER) {
               --p_a_1_.count;
               p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
               if(p_a_1_.count <= 0) {
                  return new ItemStack(Items.POTION);
               }

               if(!p_a_3_.inventory.pickup(new ItemStack(Items.POTION))) {
                  p_a_3_.drop(new ItemStack(Items.POTION, 1, 0), false);
               }
            }
         }

         return p_a_1_;
      }
   }
}
