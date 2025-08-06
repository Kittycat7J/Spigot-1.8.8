package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityDamageSource;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.LocaleI18n;

public class EntityDamageSourceIndirect extends EntityDamageSource {
   private Entity owner;

   public EntityDamageSourceIndirect(String p_i181_1_, Entity p_i181_2_, Entity p_i181_3_) {
      super(p_i181_1_, p_i181_2_);
      this.owner = p_i181_3_;
   }

   public Entity i() {
      return this.q;
   }

   public Entity getEntity() {
      return this.owner;
   }

   public IChatBaseComponent getLocalizedDeathMessage(EntityLiving p_getLocalizedDeathMessage_1_) {
      IChatBaseComponent ichatbasecomponent = this.owner == null?this.q.getScoreboardDisplayName():this.owner.getScoreboardDisplayName();
      ItemStack itemstack = this.owner instanceof EntityLiving?((EntityLiving)this.owner).bA():null;
      String s = "death.attack." + this.translationIndex;
      String s1 = s + ".item";
      return itemstack != null && itemstack.hasName() && LocaleI18n.c(s1)?new ChatMessage(s1, new Object[]{p_getLocalizedDeathMessage_1_.getScoreboardDisplayName(), ichatbasecomponent, itemstack.C()}):new ChatMessage(s, new Object[]{p_getLocalizedDeathMessage_1_.getScoreboardDisplayName(), ichatbasecomponent});
   }

   public Entity getProximateDamageSource() {
      return super.getEntity();
   }
}
