package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MerchantRecipe;
import net.minecraft.server.v1_8_R3.MerchantRecipeList;

public interface IMerchant {
   void a_(EntityHuman var1);

   EntityHuman v_();

   MerchantRecipeList getOffers(EntityHuman var1);

   void a(MerchantRecipe var1);

   void a_(ItemStack var1);

   IChatBaseComponent getScoreboardDisplayName();
}
