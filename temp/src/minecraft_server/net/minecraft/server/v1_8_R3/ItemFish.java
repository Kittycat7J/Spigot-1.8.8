package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ItemFood;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.PotionBrewer;
import net.minecraft.server.v1_8_R3.World;

public class ItemFish extends ItemFood {
   private final boolean b;

   public ItemFish(boolean p_i1278_1_) {
      super(0, 0.0F, false);
      this.b = p_i1278_1_;
   }

   public int getNutrition(ItemStack p_getNutrition_1_) {
      ItemFish.EnumFish itemfish$enumfish = ItemFish.EnumFish.a(p_getNutrition_1_);
      return this.b && itemfish$enumfish.g()?itemfish$enumfish.e():itemfish$enumfish.c();
   }

   public float getSaturationModifier(ItemStack p_getSaturationModifier_1_) {
      ItemFish.EnumFish itemfish$enumfish = ItemFish.EnumFish.a(p_getSaturationModifier_1_);
      return this.b && itemfish$enumfish.g()?itemfish$enumfish.f():itemfish$enumfish.d();
   }

   public String j(ItemStack p_j_1_) {
      return ItemFish.EnumFish.a(p_j_1_) == ItemFish.EnumFish.PUFFERFISH?PotionBrewer.m:null;
   }

   protected void c(ItemStack p_c_1_, World p_c_2_, EntityHuman p_c_3_) {
      ItemFish.EnumFish itemfish$enumfish = ItemFish.EnumFish.a(p_c_1_);
      if(itemfish$enumfish == ItemFish.EnumFish.PUFFERFISH) {
         p_c_3_.addEffect(new MobEffect(MobEffectList.POISON.id, 1200, 3));
         p_c_3_.addEffect(new MobEffect(MobEffectList.HUNGER.id, 300, 2));
         p_c_3_.addEffect(new MobEffect(MobEffectList.CONFUSION.id, 300, 1));
      }

      super.c(p_c_1_, p_c_2_, p_c_3_);
   }

   public String e_(ItemStack p_e__1_) {
      ItemFish.EnumFish itemfish$enumfish = ItemFish.EnumFish.a(p_e__1_);
      return this.getName() + "." + itemfish$enumfish.b() + "." + (this.b && itemfish$enumfish.g()?"cooked":"raw");
   }

   public static enum EnumFish {
      COD(0, "cod", 2, 0.1F, 5, 0.6F),
      SALMON(1, "salmon", 2, 0.1F, 6, 0.8F),
      CLOWNFISH(2, "clownfish", 1, 0.1F),
      PUFFERFISH(3, "pufferfish", 1, 0.1F);

      private static final Map<Integer, ItemFish.EnumFish> e = Maps.<Integer, ItemFish.EnumFish>newHashMap();
      private final int f;
      private final String g;
      private final int h;
      private final float i;
      private final int j;
      private final float k;
      private boolean l = false;

      private EnumFish(int p_i1276_3_, String p_i1276_4_, int p_i1276_5_, float p_i1276_6_, int p_i1276_7_, float p_i1276_8_) {
         this.f = p_i1276_3_;
         this.g = p_i1276_4_;
         this.h = p_i1276_5_;
         this.i = p_i1276_6_;
         this.j = p_i1276_7_;
         this.k = p_i1276_8_;
         this.l = true;
      }

      private EnumFish(int p_i1277_3_, String p_i1277_4_, int p_i1277_5_, float p_i1277_6_) {
         this.f = p_i1277_3_;
         this.g = p_i1277_4_;
         this.h = p_i1277_5_;
         this.i = p_i1277_6_;
         this.j = 0;
         this.k = 0.0F;
         this.l = false;
      }

      public int a() {
         return this.f;
      }

      public String b() {
         return this.g;
      }

      public int c() {
         return this.h;
      }

      public float d() {
         return this.i;
      }

      public int e() {
         return this.j;
      }

      public float f() {
         return this.k;
      }

      public boolean g() {
         return this.l;
      }

      public static ItemFish.EnumFish a(int p_a_0_) {
         ItemFish.EnumFish itemfish$enumfish = (ItemFish.EnumFish)e.get(Integer.valueOf(p_a_0_));
         return itemfish$enumfish == null?COD:itemfish$enumfish;
      }

      public static ItemFish.EnumFish a(ItemStack p_a_0_) {
         return p_a_0_.getItem() instanceof ItemFish?a(p_a_0_.getData()):COD;
      }

      static {
         for(ItemFish.EnumFish itemfish$enumfish : values()) {
            e.put(Integer.valueOf(itemfish$enumfish.a()), itemfish$enumfish);
         }

      }
   }
}
