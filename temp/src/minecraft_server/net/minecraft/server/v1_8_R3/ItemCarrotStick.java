package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPig;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;

public class ItemCarrotStick extends Item {
   public ItemCarrotStick() {
      this.a(CreativeModeTab.e);
      this.c(1);
      this.setMaxDurability(25);
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      if(p_a_3_.au() && p_a_3_.vehicle instanceof EntityPig) {
         EntityPig entitypig = (EntityPig)p_a_3_.vehicle;
         if(entitypig.cm().h() && p_a_1_.j() - p_a_1_.getData() >= 7) {
            entitypig.cm().g();
            p_a_1_.damage(7, p_a_3_);
            if(p_a_1_.count == 0) {
               ItemStack itemstack = new ItemStack(Items.FISHING_ROD);
               itemstack.setTag(p_a_1_.getTag());
               return itemstack;
            }
         }
      }

      p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
      return p_a_1_;
   }
}
