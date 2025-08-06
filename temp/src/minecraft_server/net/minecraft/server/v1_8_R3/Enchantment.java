package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EnchantmentArrowDamage;
import net.minecraft.server.v1_8_R3.EnchantmentArrowKnockback;
import net.minecraft.server.v1_8_R3.EnchantmentDepthStrider;
import net.minecraft.server.v1_8_R3.EnchantmentDigging;
import net.minecraft.server.v1_8_R3.EnchantmentDurability;
import net.minecraft.server.v1_8_R3.EnchantmentFire;
import net.minecraft.server.v1_8_R3.EnchantmentFlameArrows;
import net.minecraft.server.v1_8_R3.EnchantmentInfiniteArrows;
import net.minecraft.server.v1_8_R3.EnchantmentKnockback;
import net.minecraft.server.v1_8_R3.EnchantmentLootBonus;
import net.minecraft.server.v1_8_R3.EnchantmentLure;
import net.minecraft.server.v1_8_R3.EnchantmentOxygen;
import net.minecraft.server.v1_8_R3.EnchantmentProtection;
import net.minecraft.server.v1_8_R3.EnchantmentSilkTouch;
import net.minecraft.server.v1_8_R3.EnchantmentSlotType;
import net.minecraft.server.v1_8_R3.EnchantmentThorns;
import net.minecraft.server.v1_8_R3.EnchantmentWaterWorker;
import net.minecraft.server.v1_8_R3.EnchantmentWeaponDamage;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import org.bukkit.craftbukkit.v1_8_R3.enchantments.CraftEnchantment;

public abstract class Enchantment {
   private static final Enchantment[] byId = new Enchantment[256];
   public static final Enchantment[] b;
   private static final Map<MinecraftKey, Enchantment> E = Maps.<MinecraftKey, Enchantment>newHashMap();
   public static final Enchantment PROTECTION_ENVIRONMENTAL = new EnchantmentProtection(0, new MinecraftKey("protection"), 10, 0);
   public static final Enchantment PROTECTION_FIRE = new EnchantmentProtection(1, new MinecraftKey("fire_protection"), 5, 1);
   public static final Enchantment PROTECTION_FALL = new EnchantmentProtection(2, new MinecraftKey("feather_falling"), 5, 2);
   public static final Enchantment PROTECTION_EXPLOSIONS = new EnchantmentProtection(3, new MinecraftKey("blast_protection"), 2, 3);
   public static final Enchantment PROTECTION_PROJECTILE = new EnchantmentProtection(4, new MinecraftKey("projectile_protection"), 5, 4);
   public static final Enchantment OXYGEN = new EnchantmentOxygen(5, new MinecraftKey("respiration"), 2);
   public static final Enchantment WATER_WORKER = new EnchantmentWaterWorker(6, new MinecraftKey("aqua_affinity"), 2);
   public static final Enchantment THORNS = new EnchantmentThorns(7, new MinecraftKey("thorns"), 1);
   public static final Enchantment DEPTH_STRIDER = new EnchantmentDepthStrider(8, new MinecraftKey("depth_strider"), 2);
   public static final Enchantment DAMAGE_ALL = new EnchantmentWeaponDamage(16, new MinecraftKey("sharpness"), 10, 0);
   public static final Enchantment DAMAGE_UNDEAD = new EnchantmentWeaponDamage(17, new MinecraftKey("smite"), 5, 1);
   public static final Enchantment DAMAGE_ARTHROPODS = new EnchantmentWeaponDamage(18, new MinecraftKey("bane_of_arthropods"), 5, 2);
   public static final Enchantment KNOCKBACK = new EnchantmentKnockback(19, new MinecraftKey("knockback"), 5);
   public static final Enchantment FIRE_ASPECT = new EnchantmentFire(20, new MinecraftKey("fire_aspect"), 2);
   public static final Enchantment LOOT_BONUS_MOBS = new EnchantmentLootBonus(21, new MinecraftKey("looting"), 2, EnchantmentSlotType.WEAPON);
   public static final Enchantment DIG_SPEED = new EnchantmentDigging(32, new MinecraftKey("efficiency"), 10);
   public static final Enchantment SILK_TOUCH = new EnchantmentSilkTouch(33, new MinecraftKey("silk_touch"), 1);
   public static final Enchantment DURABILITY = new EnchantmentDurability(34, new MinecraftKey("unbreaking"), 5);
   public static final Enchantment LOOT_BONUS_BLOCKS = new EnchantmentLootBonus(35, new MinecraftKey("fortune"), 2, EnchantmentSlotType.DIGGER);
   public static final Enchantment ARROW_DAMAGE = new EnchantmentArrowDamage(48, new MinecraftKey("power"), 10);
   public static final Enchantment ARROW_KNOCKBACK = new EnchantmentArrowKnockback(49, new MinecraftKey("punch"), 2);
   public static final Enchantment ARROW_FIRE = new EnchantmentFlameArrows(50, new MinecraftKey("flame"), 2);
   public static final Enchantment ARROW_INFINITE = new EnchantmentInfiniteArrows(51, new MinecraftKey("infinity"), 1);
   public static final Enchantment LUCK = new EnchantmentLootBonus(61, new MinecraftKey("luck_of_the_sea"), 2, EnchantmentSlotType.FISHING_ROD);
   public static final Enchantment LURE = new EnchantmentLure(62, new MinecraftKey("lure"), 2, EnchantmentSlotType.FISHING_ROD);
   public final int id;
   private final int weight;
   public EnchantmentSlotType slot;
   protected String name;

   static {
      ArrayList arraylist = Lists.newArrayList();

      for(Enchantment enchantment : byId) {
         if(enchantment != null) {
            arraylist.add(enchantment);
         }
      }

      b = (Enchantment[])arraylist.toArray(new Enchantment[arraylist.size()]);
   }

   public static Enchantment getById(int p_getById_0_) {
      return p_getById_0_ >= 0 && p_getById_0_ < byId.length?byId[p_getById_0_]:null;
   }

   protected Enchantment(int p_i46_1_, MinecraftKey p_i46_2_, int p_i46_3_, EnchantmentSlotType p_i46_4_) {
      this.id = p_i46_1_;
      this.weight = p_i46_3_;
      this.slot = p_i46_4_;
      if(byId[p_i46_1_] != null) {
         throw new IllegalArgumentException("Duplicate enchantment id!");
      } else {
         byId[p_i46_1_] = this;
         E.put(p_i46_2_, this);
         org.bukkit.enchantments.Enchantment.registerEnchantment(new CraftEnchantment(this));
      }
   }

   public static Enchantment getByName(String p_getByName_0_) {
      return (Enchantment)E.get(new MinecraftKey(p_getByName_0_));
   }

   public static Set<MinecraftKey> getEffects() {
      return E.keySet();
   }

   public int getRandomWeight() {
      return this.weight;
   }

   public int getStartLevel() {
      return 1;
   }

   public int getMaxLevel() {
      return 1;
   }

   public int a(int p_a_1_) {
      return 1 + p_a_1_ * 10;
   }

   public int b(int p_b_1_) {
      return this.a(p_b_1_) + 5;
   }

   public int a(int p_a_1_, DamageSource p_a_2_) {
      return 0;
   }

   public float a(int p_a_1_, EnumMonsterType p_a_2_) {
      return 0.0F;
   }

   public boolean a(Enchantment p_a_1_) {
      return this != p_a_1_;
   }

   public Enchantment c(String p_c_1_) {
      this.name = p_c_1_;
      return this;
   }

   public String a() {
      return "enchantment." + this.name;
   }

   public String d(int p_d_1_) {
      String s = LocaleI18n.get(this.a());
      return s + " " + LocaleI18n.get("enchantment.level." + p_d_1_);
   }

   public boolean canEnchant(ItemStack p_canEnchant_1_) {
      return this.slot.canEnchant(p_canEnchant_1_.getItem());
   }

   public void a(EntityLiving p_a_1_, Entity p_a_2_, int p_a_3_) {
   }

   public void b(EntityLiving p_b_1_, Entity p_b_2_, int p_b_3_) {
   }
}
