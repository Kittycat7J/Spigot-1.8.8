package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.WeightedRandom;
import net.minecraft.server.v1_8_R3.WeightedRandomEnchant;

public class EnchantmentManager {
   private static final Random a = new Random();
   private static final EnchantmentManager.EnchantmentModifierProtection b = new EnchantmentManager.EnchantmentModifierProtection((EnchantmentManager.SyntheticClass_1)null);
   private static final EnchantmentManager.EnchantmentModifierDamage c = new EnchantmentManager.EnchantmentModifierDamage((EnchantmentManager.SyntheticClass_1)null);
   private static final EnchantmentManager.EnchantmentModifierThorns d = new EnchantmentManager.EnchantmentModifierThorns((EnchantmentManager.SyntheticClass_1)null);
   private static final EnchantmentManager.EnchantmentModifierArthropods e = new EnchantmentManager.EnchantmentModifierArthropods((EnchantmentManager.SyntheticClass_1)null);

   public static int getEnchantmentLevel(int p_getEnchantmentLevel_0_, ItemStack p_getEnchantmentLevel_1_) {
      if(p_getEnchantmentLevel_1_ == null) {
         return 0;
      } else {
         NBTTagList nbttaglist = p_getEnchantmentLevel_1_.getEnchantments();
         if(nbttaglist == null) {
            return 0;
         } else {
            for(int i = 0; i < nbttaglist.size(); ++i) {
               short short1 = nbttaglist.get(i).getShort("id");
               short short2 = nbttaglist.get(i).getShort("lvl");
               if(short1 == p_getEnchantmentLevel_0_) {
                  return short2;
               }
            }

            return 0;
         }
      }
   }

   public static Map<Integer, Integer> a(ItemStack p_a_0_) {
      LinkedHashMap linkedhashmap = Maps.newLinkedHashMap();
      NBTTagList nbttaglist = p_a_0_.getItem() == Items.ENCHANTED_BOOK?Items.ENCHANTED_BOOK.h(p_a_0_):p_a_0_.getEnchantments();
      if(nbttaglist != null) {
         for(int i = 0; i < nbttaglist.size(); ++i) {
            short short1 = nbttaglist.get(i).getShort("id");
            short short2 = nbttaglist.get(i).getShort("lvl");
            linkedhashmap.put(Integer.valueOf(short1), Integer.valueOf(short2));
         }
      }

      return linkedhashmap;
   }

   public static void a(Map<Integer, Integer> p_a_0_, ItemStack p_a_1_) {
      NBTTagList nbttaglist = new NBTTagList();
      Iterator iterator = p_a_0_.keySet().iterator();

      while(iterator.hasNext()) {
         int i = ((Integer)iterator.next()).intValue();
         Enchantment enchantment = Enchantment.getById(i);
         if(enchantment != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setShort("id", (short)i);
            nbttagcompound.setShort("lvl", (short)((Integer)p_a_0_.get(Integer.valueOf(i))).intValue());
            nbttaglist.add(nbttagcompound);
            if(p_a_1_.getItem() == Items.ENCHANTED_BOOK) {
               Items.ENCHANTED_BOOK.a(p_a_1_, new WeightedRandomEnchant(enchantment, ((Integer)p_a_0_.get(Integer.valueOf(i))).intValue()));
            }
         }
      }

      if(nbttaglist.size() > 0) {
         if(p_a_1_.getItem() != Items.ENCHANTED_BOOK) {
            p_a_1_.a((String)"ench", (NBTBase)nbttaglist);
         }
      } else if(p_a_1_.hasTag()) {
         p_a_1_.getTag().remove("ench");
      }

   }

   public static int a(int p_a_0_, ItemStack[] p_a_1_) {
      if(p_a_1_ == null) {
         return 0;
      } else {
         int i = 0;

         for(ItemStack itemstack : p_a_1_) {
            int j = getEnchantmentLevel(p_a_0_, itemstack);
            if(j > i) {
               i = j;
            }
         }

         return i;
      }
   }

   private static void a(EnchantmentManager.EnchantmentModifier p_a_0_, ItemStack p_a_1_) {
      if(p_a_1_ != null) {
         NBTTagList nbttaglist = p_a_1_.getEnchantments();
         if(nbttaglist != null) {
            for(int i = 0; i < nbttaglist.size(); ++i) {
               short short1 = nbttaglist.get(i).getShort("id");
               short short2 = nbttaglist.get(i).getShort("lvl");
               if(Enchantment.getById(short1) != null) {
                  p_a_0_.a(Enchantment.getById(short1), short2);
               }
            }
         }
      }

   }

   private static void a(EnchantmentManager.EnchantmentModifier p_a_0_, ItemStack[] p_a_1_) {
      for(ItemStack itemstack : p_a_1_) {
         a(p_a_0_, itemstack);
      }

   }

   public static int a(ItemStack[] p_a_0_, DamageSource p_a_1_) {
      b.a = 0;
      b.b = p_a_1_;
      a(b, (ItemStack[])p_a_0_);
      if(b.a > 25) {
         b.a = 25;
      } else if(b.a < 0) {
         b.a = 0;
      }

      return (b.a + 1 >> 1) + a.nextInt((b.a >> 1) + 1);
   }

   public static float a(ItemStack p_a_0_, EnumMonsterType p_a_1_) {
      c.a = 0.0F;
      c.b = p_a_1_;
      a(c, (ItemStack)p_a_0_);
      return c.a;
   }

   public static void a(EntityLiving p_a_0_, Entity p_a_1_) {
      d.b = p_a_1_;
      d.a = p_a_0_;
      if(p_a_0_ != null) {
         a(d, (ItemStack[])p_a_0_.getEquipment());
      }

      if(p_a_1_ instanceof EntityHuman) {
         a(d, (ItemStack)p_a_0_.bA());
      }

   }

   public static void b(EntityLiving p_b_0_, Entity p_b_1_) {
      e.a = p_b_0_;
      e.b = p_b_1_;
      if(p_b_0_ != null) {
         a(e, (ItemStack[])p_b_0_.getEquipment());
      }

      if(p_b_0_ instanceof EntityHuman) {
         a(e, (ItemStack)p_b_0_.bA());
      }

   }

   public static int a(EntityLiving p_a_0_) {
      return getEnchantmentLevel(Enchantment.KNOCKBACK.id, p_a_0_.bA());
   }

   public static int getFireAspectEnchantmentLevel(EntityLiving p_getFireAspectEnchantmentLevel_0_) {
      return getEnchantmentLevel(Enchantment.FIRE_ASPECT.id, p_getFireAspectEnchantmentLevel_0_.bA());
   }

   public static int getOxygenEnchantmentLevel(Entity p_getOxygenEnchantmentLevel_0_) {
      return a(Enchantment.OXYGEN.id, p_getOxygenEnchantmentLevel_0_.getEquipment());
   }

   public static int b(Entity p_b_0_) {
      return a(Enchantment.DEPTH_STRIDER.id, p_b_0_.getEquipment());
   }

   public static int getDigSpeedEnchantmentLevel(EntityLiving p_getDigSpeedEnchantmentLevel_0_) {
      return getEnchantmentLevel(Enchantment.DIG_SPEED.id, p_getDigSpeedEnchantmentLevel_0_.bA());
   }

   public static boolean hasSilkTouchEnchantment(EntityLiving p_hasSilkTouchEnchantment_0_) {
      return getEnchantmentLevel(Enchantment.SILK_TOUCH.id, p_hasSilkTouchEnchantment_0_.bA()) > 0;
   }

   public static int getBonusBlockLootEnchantmentLevel(EntityLiving p_getBonusBlockLootEnchantmentLevel_0_) {
      return getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS.id, p_getBonusBlockLootEnchantmentLevel_0_.bA());
   }

   public static int g(EntityLiving p_g_0_) {
      return getEnchantmentLevel(Enchantment.LUCK.id, p_g_0_.bA());
   }

   public static int h(EntityLiving p_h_0_) {
      return getEnchantmentLevel(Enchantment.LURE.id, p_h_0_.bA());
   }

   public static int getBonusMonsterLootEnchantmentLevel(EntityLiving p_getBonusMonsterLootEnchantmentLevel_0_) {
      return getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS.id, p_getBonusMonsterLootEnchantmentLevel_0_.bA());
   }

   public static boolean j(EntityLiving p_j_0_) {
      return a(Enchantment.WATER_WORKER.id, p_j_0_.getEquipment()) > 0;
   }

   public static ItemStack a(Enchantment p_a_0_, EntityLiving p_a_1_) {
      for(ItemStack itemstack : p_a_1_.getEquipment()) {
         if(itemstack != null && getEnchantmentLevel(p_a_0_.id, itemstack) > 0) {
            return itemstack;
         }
      }

      return null;
   }

   public static int a(Random p_a_0_, int p_a_1_, int p_a_2_, ItemStack p_a_3_) {
      Item item = p_a_3_.getItem();
      int i = item.b();
      if(i <= 0) {
         return 0;
      } else {
         if(p_a_2_ > 15) {
            p_a_2_ = 15;
         }

         int j = p_a_0_.nextInt(8) + 1 + (p_a_2_ >> 1) + p_a_0_.nextInt(p_a_2_ + 1);
         return p_a_1_ == 0?Math.max(j / 3, 1):(p_a_1_ == 1?j * 2 / 3 + 1:Math.max(j, p_a_2_ * 2));
      }
   }

   public static ItemStack a(Random p_a_0_, ItemStack p_a_1_, int p_a_2_) {
      List list = b(p_a_0_, p_a_1_, p_a_2_);
      boolean flag = p_a_1_.getItem() == Items.BOOK;
      if(flag) {
         p_a_1_.setItem(Items.ENCHANTED_BOOK);
      }

      if(list != null) {
         for(WeightedRandomEnchant weightedrandomenchant : list) {
            if(flag) {
               Items.ENCHANTED_BOOK.a(p_a_1_, weightedrandomenchant);
            } else {
               p_a_1_.addEnchantment(weightedrandomenchant.enchantment, weightedrandomenchant.level);
            }
         }
      }

      return p_a_1_;
   }

   public static List<WeightedRandomEnchant> b(Random p_b_0_, ItemStack p_b_1_, int p_b_2_) {
      Item item = p_b_1_.getItem();
      int i = item.b();
      if(i <= 0) {
         return null;
      } else {
         i = i / 2;
         i = 1 + p_b_0_.nextInt((i >> 1) + 1) + p_b_0_.nextInt((i >> 1) + 1);
         int j = i + p_b_2_;
         float f = (p_b_0_.nextFloat() + p_b_0_.nextFloat() - 1.0F) * 0.15F;
         int k = (int)((float)j * (1.0F + f) + 0.5F);
         if(k < 1) {
            k = 1;
         }

         ArrayList arraylist = null;
         Map map = b(k, p_b_1_);
         if(map != null && !map.isEmpty()) {
            WeightedRandomEnchant weightedrandomenchant = (WeightedRandomEnchant)WeightedRandom.a(p_b_0_, map.values());
            if(weightedrandomenchant != null) {
               arraylist = Lists.newArrayList();
               arraylist.add(weightedrandomenchant);

               for(int l = k; p_b_0_.nextInt(50) <= l; l >>= 1) {
                  Iterator iterator = map.keySet().iterator();

                  while(iterator.hasNext()) {
                     Integer integer = (Integer)iterator.next();
                     boolean flag = true;

                     for(WeightedRandomEnchant weightedrandomenchant1 : arraylist) {
                        if(!weightedrandomenchant1.enchantment.a(Enchantment.getById(integer.intValue()))) {
                           flag = false;
                           break;
                        }
                     }

                     if(!flag) {
                        iterator.remove();
                     }
                  }

                  if(!map.isEmpty()) {
                     WeightedRandomEnchant weightedrandomenchant2 = (WeightedRandomEnchant)WeightedRandom.a(p_b_0_, map.values());
                     arraylist.add(weightedrandomenchant2);
                  }
               }
            }
         }

         return arraylist;
      }
   }

   public static Map<Integer, WeightedRandomEnchant> b(int p_b_0_, ItemStack p_b_1_) {
      Item item = p_b_1_.getItem();
      HashMap hashmap = null;
      boolean flag = p_b_1_.getItem() == Items.BOOK;

      for(Enchantment enchantment : Enchantment.b) {
         if(enchantment != null && (enchantment.slot.canEnchant(item) || flag)) {
            for(int i = enchantment.getStartLevel(); i <= enchantment.getMaxLevel(); ++i) {
               if(p_b_0_ >= enchantment.a(i) && p_b_0_ <= enchantment.b(i)) {
                  if(hashmap == null) {
                     hashmap = Maps.newHashMap();
                  }

                  hashmap.put(Integer.valueOf(enchantment.id), new WeightedRandomEnchant(enchantment, i));
               }
            }
         }
      }

      return hashmap;
   }

   interface EnchantmentModifier {
      void a(Enchantment var1, int var2);
   }

   static final class EnchantmentModifierArthropods implements EnchantmentManager.EnchantmentModifier {
      public EntityLiving a;
      public Entity b;

      private EnchantmentModifierArthropods() {
      }

      public void a(Enchantment p_a_1_, int p_a_2_) {
         p_a_1_.a(this.a, this.b, p_a_2_);
      }

      EnchantmentModifierArthropods(EnchantmentManager.SyntheticClass_1 p_i0_1_) {
         this();
      }
   }

   static final class EnchantmentModifierDamage implements EnchantmentManager.EnchantmentModifier {
      public float a;
      public EnumMonsterType b;

      private EnchantmentModifierDamage() {
      }

      public void a(Enchantment p_a_1_, int p_a_2_) {
         this.a += p_a_1_.a(p_a_2_, this.b);
      }

      EnchantmentModifierDamage(EnchantmentManager.SyntheticClass_1 p_i75_1_) {
         this();
      }
   }

   static final class EnchantmentModifierProtection implements EnchantmentManager.EnchantmentModifier {
      public int a;
      public DamageSource b;

      private EnchantmentModifierProtection() {
      }

      public void a(Enchantment p_a_1_, int p_a_2_) {
         this.a += p_a_1_.a(p_a_2_, this.b);
      }

      EnchantmentModifierProtection(EnchantmentManager.SyntheticClass_1 p_i475_1_) {
         this();
      }
   }

   static final class EnchantmentModifierThorns implements EnchantmentManager.EnchantmentModifier {
      public EntityLiving a;
      public Entity b;

      private EnchantmentModifierThorns() {
      }

      public void a(Enchantment p_a_1_, int p_a_2_) {
         p_a_1_.b(this.a, this.b, p_a_2_);
      }

      EnchantmentModifierThorns(EnchantmentManager.SyntheticClass_1 p_i468_1_) {
         this();
      }
   }

   static class SyntheticClass_1 {
   }
}
