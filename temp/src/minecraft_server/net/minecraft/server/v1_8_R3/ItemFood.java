package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumAnimation;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;

public class ItemFood extends Item {
   public final int a;
   private final int b;
   private final float c;
   private final boolean d;
   private boolean k;
   private int l;
   private int m;
   private int n;
   private float o;

   public ItemFood(int p_i1279_1_, float p_i1279_2_, boolean p_i1279_3_) {
      this.a = 32;
      this.b = p_i1279_1_;
      this.d = p_i1279_3_;
      this.c = p_i1279_2_;
      this.a(CreativeModeTab.h);
   }

   public ItemFood(int p_i1280_1_, boolean p_i1280_2_) {
      this(p_i1280_1_, 0.6F, p_i1280_2_);
   }

   public ItemStack b(ItemStack p_b_1_, World p_b_2_, EntityHuman p_b_3_) {
      --p_b_1_.count;
      p_b_3_.getFoodData().a(this, p_b_1_);
      p_b_2_.makeSound(p_b_3_, "random.burp", 0.5F, p_b_2_.random.nextFloat() * 0.1F + 0.9F);
      this.c(p_b_1_, p_b_2_, p_b_3_);
      p_b_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
      return p_b_1_;
   }

   protected void c(ItemStack p_c_1_, World p_c_2_, EntityHuman p_c_3_) {
      if(!p_c_2_.isClientSide && this.l > 0 && p_c_2_.random.nextFloat() < this.o) {
         p_c_3_.addEffect(new MobEffect(this.l, this.m * 20, this.n));
      }

   }

   public int d(ItemStack p_d_1_) {
      return 32;
   }

   public EnumAnimation e(ItemStack p_e_1_) {
      return EnumAnimation.EAT;
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      if(p_a_3_.j(this.k)) {
         p_a_3_.a(p_a_1_, this.d(p_a_1_));
      }

      return p_a_1_;
   }

   public int getNutrition(ItemStack p_getNutrition_1_) {
      return this.b;
   }

   public float getSaturationModifier(ItemStack p_getSaturationModifier_1_) {
      return this.c;
   }

   public boolean g() {
      return this.d;
   }

   public ItemFood a(int p_a_1_, int p_a_2_, int p_a_3_, float p_a_4_) {
      this.l = p_a_1_;
      this.m = p_a_2_;
      this.n = p_a_3_;
      this.o = p_a_4_;
      return this;
   }

   public ItemFood h() {
      this.k = true;
      return this;
   }
}
