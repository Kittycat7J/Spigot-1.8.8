package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemBow;
import net.minecraft.server.v1_8_R3.ItemFishingRod;
import net.minecraft.server.v1_8_R3.ItemSword;
import net.minecraft.server.v1_8_R3.ItemTool;

public enum EnchantmentSlotType {
   ALL,
   ARMOR,
   ARMOR_FEET,
   ARMOR_LEGS,
   ARMOR_TORSO,
   ARMOR_HEAD,
   WEAPON,
   DIGGER,
   FISHING_ROD,
   BREAKABLE,
   BOW;

   public boolean canEnchant(Item p_canEnchant_1_) {
      if(this == ALL) {
         return true;
      } else if(this == BREAKABLE && p_canEnchant_1_.usesDurability()) {
         return true;
      } else if(p_canEnchant_1_ instanceof ItemArmor) {
         if(this == ARMOR) {
            return true;
         } else {
            ItemArmor itemarmor = (ItemArmor)p_canEnchant_1_;
            return itemarmor.b == 0?this == ARMOR_HEAD:(itemarmor.b == 2?this == ARMOR_LEGS:(itemarmor.b == 1?this == ARMOR_TORSO:(itemarmor.b == 3?this == ARMOR_FEET:false)));
         }
      } else {
         return p_canEnchant_1_ instanceof ItemSword?this == WEAPON:(p_canEnchant_1_ instanceof ItemTool?this == DIGGER:(p_canEnchant_1_ instanceof ItemBow?this == BOW:(p_canEnchant_1_ instanceof ItemFishingRod?this == FISHING_ROD:false)));
      }
   }
}
