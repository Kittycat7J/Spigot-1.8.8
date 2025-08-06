package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.ItemAxe;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;

public class EnchantmentWeaponDamage extends Enchantment {
   private static final String[] E = new String[]{"all", "undead", "arthropods"};
   private static final int[] F = new int[]{1, 5, 5};
   private static final int[] G = new int[]{11, 8, 8};
   private static final int[] H = new int[]{20, 20, 20};
   public final int a;

   public EnchantmentWeaponDamage(int p_i526_1_, MinecraftKey p_i526_2_, int p_i526_3_, int p_i526_4_) {
      super(p_i526_1_, p_i526_2_, p_i526_3_, EnchantmentSlotType.WEAPON);
      this.a = p_i526_4_;
   }

   public int a(int p_a_1_) {
      return F[this.a] + (p_a_1_ - 1) * G[this.a];
   }

   public int b(int p_b_1_) {
      return this.a(p_b_1_) + H[this.a];
   }

   public int getMaxLevel() {
      return 5;
   }

   public float a(int p_a_1_, EnumMonsterType p_a_2_) {
      return this.a == 0?(float)p_a_1_ * 1.25F:(this.a == 1 && p_a_2_ == EnumMonsterType.UNDEAD?(float)p_a_1_ * 2.5F:(this.a == 2 && p_a_2_ == EnumMonsterType.ARTHROPOD?(float)p_a_1_ * 2.5F:0.0F));
   }

   public String a() {
      return "enchantment.damage." + E[this.a];
   }

   public boolean a(Enchantment p_a_1_) {
      return !(p_a_1_ instanceof EnchantmentWeaponDamage);
   }

   public boolean canEnchant(ItemStack p_canEnchant_1_) {
      return p_canEnchant_1_.getItem() instanceof ItemAxe?true:super.canEnchant(p_canEnchant_1_);
   }

   public void a(EntityLiving p_a_1_, Entity p_a_2_, int p_a_3_) {
      if(p_a_2_ instanceof EntityLiving) {
         EntityLiving entityliving = (EntityLiving)p_a_2_;
         if(this.a == 2 && entityliving.getMonsterType() == EnumMonsterType.ARTHROPOD) {
            int i = 20 + p_a_1_.bc().nextInt(10 * p_a_3_);
            entityliving.addEffect(new MobEffect(MobEffectList.SLOWER_MOVEMENT.id, i, 3));
         }
      }

   }
}
