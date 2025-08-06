package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeMapBase;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IAttribute;
import net.minecraft.server.v1_8_R3.InstantMobEffect;
import net.minecraft.server.v1_8_R3.MinecraftKey;
import net.minecraft.server.v1_8_R3.MobEffectAbsorption;
import net.minecraft.server.v1_8_R3.MobEffectAttackDamage;
import net.minecraft.server.v1_8_R3.MobEffectHealthBoost;
import net.minecraft.server.v1_8_R3.PacketPlayOutUpdateHealth;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.potion.CraftPotionEffectType;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.potion.PotionEffectType;

public class MobEffectList {
   public static final MobEffectList[] byId = new MobEffectList[32];
   private static final Map<MinecraftKey, MobEffectList> I = Maps.<MinecraftKey, MobEffectList>newHashMap();
   public static final MobEffectList b = null;
   public static final MobEffectList FASTER_MOVEMENT = (new MobEffectList(1, new MinecraftKey("speed"), false, 8171462)).c("potion.moveSpeed").b(0, 0).a(GenericAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", 0.20000000298023224D, 2);
   public static final MobEffectList SLOWER_MOVEMENT = (new MobEffectList(2, new MinecraftKey("slowness"), true, 5926017)).c("potion.moveSlowdown").b(1, 0).a(GenericAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448D, 2);
   public static final MobEffectList FASTER_DIG = (new MobEffectList(3, new MinecraftKey("haste"), false, 14270531)).c("potion.digSpeed").b(2, 0).a(1.5D);
   public static final MobEffectList SLOWER_DIG = (new MobEffectList(4, new MinecraftKey("mining_fatigue"), true, 4866583)).c("potion.digSlowDown").b(3, 0);
   public static final MobEffectList INCREASE_DAMAGE = (new MobEffectAttackDamage(5, new MinecraftKey("strength"), false, 9643043)).c("potion.damageBoost").b(4, 0).a(GenericAttributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 2.5D, 2);
   public static final MobEffectList HEAL = (new InstantMobEffect(6, new MinecraftKey("instant_health"), false, 16262179)).c("potion.heal");
   public static final MobEffectList HARM = (new InstantMobEffect(7, new MinecraftKey("instant_damage"), true, 4393481)).c("potion.harm");
   public static final MobEffectList JUMP = (new MobEffectList(8, new MinecraftKey("jump_boost"), false, 2293580)).c("potion.jump").b(2, 1);
   public static final MobEffectList CONFUSION = (new MobEffectList(9, new MinecraftKey("nausea"), true, 5578058)).c("potion.confusion").b(3, 1).a(0.25D);
   public static final MobEffectList REGENERATION = (new MobEffectList(10, new MinecraftKey("regeneration"), false, 13458603)).c("potion.regeneration").b(7, 0).a(0.25D);
   public static final MobEffectList RESISTANCE = (new MobEffectList(11, new MinecraftKey("resistance"), false, 10044730)).c("potion.resistance").b(6, 1);
   public static final MobEffectList FIRE_RESISTANCE = (new MobEffectList(12, new MinecraftKey("fire_resistance"), false, 14981690)).c("potion.fireResistance").b(7, 1);
   public static final MobEffectList WATER_BREATHING = (new MobEffectList(13, new MinecraftKey("water_breathing"), false, 3035801)).c("potion.waterBreathing").b(0, 2);
   public static final MobEffectList INVISIBILITY = (new MobEffectList(14, new MinecraftKey("invisibility"), false, 8356754)).c("potion.invisibility").b(0, 1);
   public static final MobEffectList BLINDNESS = (new MobEffectList(15, new MinecraftKey("blindness"), true, 2039587)).c("potion.blindness").b(5, 1).a(0.25D);
   public static final MobEffectList NIGHT_VISION = (new MobEffectList(16, new MinecraftKey("night_vision"), false, 2039713)).c("potion.nightVision").b(4, 1);
   public static final MobEffectList HUNGER = (new MobEffectList(17, new MinecraftKey("hunger"), true, 5797459)).c("potion.hunger").b(1, 1);
   public static final MobEffectList WEAKNESS = (new MobEffectAttackDamage(18, new MinecraftKey("weakness"), true, 4738376)).c("potion.weakness").b(5, 0).a(GenericAttributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 2.0D, 0);
   public static final MobEffectList POISON = (new MobEffectList(19, new MinecraftKey("poison"), true, 5149489)).c("potion.poison").b(6, 0).a(0.25D);
   public static final MobEffectList WITHER = (new MobEffectList(20, new MinecraftKey("wither"), true, 3484199)).c("potion.wither").b(1, 2).a(0.25D);
   public static final MobEffectList HEALTH_BOOST = (new MobEffectHealthBoost(21, new MinecraftKey("health_boost"), false, 16284963)).c("potion.healthBoost").b(2, 2).a(GenericAttributes.maxHealth, "5D6F0BA2-1186-46AC-B896-C61C5CEE99CC", 4.0D, 0);
   public static final MobEffectList ABSORBTION = (new MobEffectAbsorption(22, new MinecraftKey("absorption"), false, 2445989)).c("potion.absorption").b(2, 2);
   public static final MobEffectList SATURATION = (new InstantMobEffect(23, new MinecraftKey("saturation"), false, 16262179)).c("potion.saturation");
   public static final MobEffectList z = null;
   public static final MobEffectList A = null;
   public static final MobEffectList B = null;
   public static final MobEffectList C = null;
   public static final MobEffectList D = null;
   public static final MobEffectList E = null;
   public static final MobEffectList F = null;
   public static final MobEffectList G = null;
   public final int id;
   private final Map<IAttribute, AttributeModifier> J = Maps.<IAttribute, AttributeModifier>newHashMap();
   private final boolean K;
   private final int L;
   private String M = "";
   private int N = -1;
   private double O;
   private boolean P;

   protected MobEffectList(int p_i452_1_, MinecraftKey p_i452_2_, boolean p_i452_3_, int p_i452_4_) {
      this.id = p_i452_1_;
      byId[p_i452_1_] = this;
      I.put(p_i452_2_, this);
      this.K = p_i452_3_;
      if(p_i452_3_) {
         this.O = 0.5D;
      } else {
         this.O = 1.0D;
      }

      this.L = p_i452_4_;
      PotionEffectType.registerPotionEffectType(new CraftPotionEffectType(this));
   }

   public static MobEffectList b(String p_b_0_) {
      return (MobEffectList)I.get(new MinecraftKey(p_b_0_));
   }

   public static Set<MinecraftKey> c() {
      return I.keySet();
   }

   protected MobEffectList b(int p_b_1_, int p_b_2_) {
      this.N = p_b_1_ + p_b_2_ * 8;
      return this;
   }

   public int getId() {
      return this.id;
   }

   public void tick(EntityLiving p_tick_1_, int p_tick_2_) {
      if(this.id == REGENERATION.id) {
         if(p_tick_1_.getHealth() < p_tick_1_.getMaxHealth()) {
            p_tick_1_.heal(1.0F, RegainReason.MAGIC_REGEN);
         }
      } else if(this.id == POISON.id) {
         if(p_tick_1_.getHealth() > 1.0F) {
            p_tick_1_.damageEntity(CraftEventFactory.POISON, 1.0F);
         }
      } else if(this.id == WITHER.id) {
         p_tick_1_.damageEntity(DamageSource.WITHER, 1.0F);
      } else if(this.id == HUNGER.id && p_tick_1_ instanceof EntityHuman) {
         ((EntityHuman)p_tick_1_).applyExhaustion(0.025F * (float)(p_tick_2_ + 1));
      } else if(this.id == SATURATION.id && p_tick_1_ instanceof EntityHuman) {
         if(!p_tick_1_.world.isClientSide) {
            EntityHuman entityhuman = (EntityHuman)p_tick_1_;
            int i = entityhuman.getFoodData().foodLevel;
            FoodLevelChangeEvent foodlevelchangeevent = CraftEventFactory.callFoodLevelChangeEvent(entityhuman, p_tick_2_ + 1 + i);
            if(!foodlevelchangeevent.isCancelled()) {
               entityhuman.getFoodData().eat(foodlevelchangeevent.getFoodLevel() - i, 1.0F);
            }

            ((EntityPlayer)entityhuman).playerConnection.sendPacket(new PacketPlayOutUpdateHealth(((EntityPlayer)entityhuman).getBukkitEntity().getScaledHealth(), entityhuman.getFoodData().foodLevel, entityhuman.getFoodData().saturationLevel));
         }
      } else if((this.id != HEAL.id || p_tick_1_.bm()) && (this.id != HARM.id || !p_tick_1_.bm())) {
         if(this.id == HARM.id && !p_tick_1_.bm() || this.id == HEAL.id && p_tick_1_.bm()) {
            p_tick_1_.damageEntity(DamageSource.MAGIC, (float)(6 << p_tick_2_));
         }
      } else {
         p_tick_1_.heal((float)Math.max(4 << p_tick_2_, 0), RegainReason.MAGIC);
      }

   }

   public void applyInstantEffect(Entity p_applyInstantEffect_1_, Entity p_applyInstantEffect_2_, EntityLiving p_applyInstantEffect_3_, int p_applyInstantEffect_4_, double p_applyInstantEffect_5_) {
      if((this.id != HEAL.id || p_applyInstantEffect_3_.bm()) && (this.id != HARM.id || !p_applyInstantEffect_3_.bm())) {
         if(this.id == HARM.id && !p_applyInstantEffect_3_.bm() || this.id == HEAL.id && p_applyInstantEffect_3_.bm()) {
            int j = (int)(p_applyInstantEffect_5_ * (double)(6 << p_applyInstantEffect_4_) + 0.5D);
            if(p_applyInstantEffect_1_ == null) {
               p_applyInstantEffect_3_.damageEntity(DamageSource.MAGIC, (float)j);
            } else {
               p_applyInstantEffect_3_.damageEntity(DamageSource.b(p_applyInstantEffect_1_, p_applyInstantEffect_2_), (float)j);
            }
         }
      } else {
         int i = (int)(p_applyInstantEffect_5_ * (double)(4 << p_applyInstantEffect_4_) + 0.5D);
         p_applyInstantEffect_3_.heal((float)i, RegainReason.MAGIC);
      }

   }

   public boolean isInstant() {
      return false;
   }

   public boolean a(int p_a_1_, int p_a_2_) {
      if(this.id == REGENERATION.id) {
         int k = 50 >> p_a_2_;
         return k > 0?p_a_1_ % k == 0:true;
      } else if(this.id == POISON.id) {
         int j = 25 >> p_a_2_;
         return j > 0?p_a_1_ % j == 0:true;
      } else if(this.id == WITHER.id) {
         int i = 40 >> p_a_2_;
         return i > 0?p_a_1_ % i == 0:true;
      } else {
         return this.id == HUNGER.id;
      }
   }

   public MobEffectList c(String p_c_1_) {
      this.M = p_c_1_;
      return this;
   }

   public String a() {
      return this.M;
   }

   protected MobEffectList a(double p_a_1_) {
      this.O = p_a_1_;
      return this;
   }

   public double getDurationModifier() {
      return this.O;
   }

   public boolean j() {
      return this.P;
   }

   public int k() {
      return this.L;
   }

   public MobEffectList a(IAttribute p_a_1_, String p_a_2_, double p_a_3_, int p_a_5_) {
      AttributeModifier attributemodifier = new AttributeModifier(UUID.fromString(p_a_2_), this.a(), p_a_3_, p_a_5_);
      this.J.put(p_a_1_, attributemodifier);
      return this;
   }

   public void a(EntityLiving p_a_1_, AttributeMapBase p_a_2_, int p_a_3_) {
      for(Entry entry : this.J.entrySet()) {
         AttributeInstance attributeinstance = p_a_2_.a((IAttribute)entry.getKey());
         if(attributeinstance != null) {
            attributeinstance.c((AttributeModifier)entry.getValue());
         }
      }

   }

   public void b(EntityLiving p_b_1_, AttributeMapBase p_b_2_, int p_b_3_) {
      for(Entry entry : this.J.entrySet()) {
         AttributeInstance attributeinstance = p_b_2_.a((IAttribute)entry.getKey());
         if(attributeinstance != null) {
            AttributeModifier attributemodifier = (AttributeModifier)entry.getValue();
            attributeinstance.c(attributemodifier);
            attributeinstance.b(new AttributeModifier(attributemodifier.a(), this.a() + " " + p_b_3_, this.a(p_b_3_, attributemodifier), attributemodifier.c()));
         }
      }

   }

   public double a(int p_a_1_, AttributeModifier p_a_2_) {
      return p_a_2_.d() * (double)(p_a_1_ + 1);
   }
}
