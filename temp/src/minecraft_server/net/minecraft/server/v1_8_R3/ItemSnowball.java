package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntitySnowball;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;

public class ItemSnowball extends Item {
   public ItemSnowball() {
      this.maxStackSize = 16;
      this.a(CreativeModeTab.f);
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      if(!p_a_3_.abilities.canInstantlyBuild) {
         --p_a_1_.count;
      }

      p_a_2_.makeSound(p_a_3_, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
      if(!p_a_2_.isClientSide) {
         p_a_2_.addEntity(new EntitySnowball(p_a_2_, p_a_3_));
      }

      p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
      return p_a_1_;
   }
}
