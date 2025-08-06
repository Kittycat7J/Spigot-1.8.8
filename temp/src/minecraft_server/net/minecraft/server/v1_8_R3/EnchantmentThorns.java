package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MinecraftKey;

public class EnchantmentThorns extends Enchantment {
   public EnchantmentThorns(int p_i379_1_, MinecraftKey p_i379_2_, int p_i379_3_) {
      super(p_i379_1_, p_i379_2_, p_i379_3_, EnchantmentSlotType.ARMOR_TORSO);
      this.c("thorns");
   }

   public int a(int p_a_1_) {
      return 10 + 20 * (p_a_1_ - 1);
   }

   public int b(int p_b_1_) {
      return super.a(p_b_1_) + 50;
   }

   public int getMaxLevel() {
      return 3;
   }

   public boolean canEnchant(ItemStack p_canEnchant_1_) {
      return p_canEnchant_1_.getItem() instanceof ItemArmor?true:super.canEnchant(p_canEnchant_1_);
   }

   public void b(EntityLiving p_b_1_, Entity p_b_2_, int p_b_3_) {
      Random random = p_b_1_.bc();
      ItemStack itemstack = EnchantmentManager.a(Enchantment.THORNS, p_b_1_);
      if(p_b_2_ != null && a(p_b_3_, random)) {
         if(p_b_2_ != null) {
            p_b_2_.damageEntity(DamageSource.a(p_b_1_), (float)b(p_b_3_, random));
            p_b_2_.makeSound("damage.thorns", 0.5F, 1.0F);
         }

         if(itemstack != null) {
            itemstack.damage(3, p_b_1_);
         }
      } else if(itemstack != null) {
         itemstack.damage(1, p_b_1_);
      }

   }

   public static boolean a(int p_a_0_, Random p_a_1_) {
      return p_a_0_ <= 0?false:p_a_1_.nextFloat() < 0.15F * (float)p_a_0_;
   }

   public static int b(int p_b_0_, Random p_b_1_) {
      return p_b_0_ > 10?p_b_0_ - 10:1 + p_b_1_.nextInt(4);
   }
}
