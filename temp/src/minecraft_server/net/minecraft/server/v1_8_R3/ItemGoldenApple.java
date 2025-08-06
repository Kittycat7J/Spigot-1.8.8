package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumItemRarity;
import net.minecraft.server.v1_8_R3.ItemFood;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.World;

public class ItemGoldenApple extends ItemFood {
   public ItemGoldenApple(int p_i1281_1_, float p_i1281_2_, boolean p_i1281_3_) {
      super(p_i1281_1_, p_i1281_2_, p_i1281_3_);
      this.a(true);
   }

   public EnumItemRarity g(ItemStack p_g_1_) {
      return p_g_1_.getData() == 0?EnumItemRarity.RARE:EnumItemRarity.EPIC;
   }

   protected void c(ItemStack p_c_1_, World p_c_2_, EntityHuman p_c_3_) {
      if(!p_c_2_.isClientSide) {
         p_c_3_.addEffect(new MobEffect(MobEffectList.ABSORBTION.id, 2400, 0));
      }

      if(p_c_1_.getData() > 0) {
         if(!p_c_2_.isClientSide) {
            p_c_3_.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 600, 4));
            p_c_3_.addEffect(new MobEffect(MobEffectList.RESISTANCE.id, 6000, 0));
            p_c_3_.addEffect(new MobEffect(MobEffectList.FIRE_RESISTANCE.id, 6000, 0));
         }
      } else {
         super.c(p_c_1_, p_c_2_, p_c_3_);
      }

   }
}
