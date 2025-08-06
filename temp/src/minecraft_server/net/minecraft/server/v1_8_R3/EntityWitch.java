package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.AttributeInstance;
import net.minecraft.server.v1_8_R3.AttributeModifier;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntityPotion;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.IRangedEntity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.World;

public class EntityWitch extends EntityMonster implements IRangedEntity {
   private static final UUID a = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
   private static final AttributeModifier b = (new AttributeModifier(a, "Drinking speed penalty", -0.25D, 0)).a(false);
   private static final Item[] c = new Item[]{Items.GLOWSTONE_DUST, Items.SUGAR, Items.REDSTONE, Items.SPIDER_EYE, Items.GLASS_BOTTLE, Items.GUNPOWDER, Items.STICK, Items.STICK};
   private int bm;

   public EntityWitch(World p_i1244_1_) {
      super(p_i1244_1_);
      this.setSize(0.6F, 1.95F);
      this.goalSelector.a(1, new PathfinderGoalFloat(this));
      this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 60, 10.0F));
      this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
      this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
      this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
      this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
   }

   protected void h() {
      super.h();
      this.getDataWatcher().a(21, Byte.valueOf((byte)0));
   }

   protected String z() {
      return null;
   }

   protected String bo() {
      return null;
   }

   protected String bp() {
      return null;
   }

   public void a(boolean p_a_1_) {
      this.getDataWatcher().watch(21, Byte.valueOf((byte)(p_a_1_?1:0)));
   }

   public boolean n() {
      return this.getDataWatcher().getByte(21) == 1;
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeInstance(GenericAttributes.maxHealth).setValue(26.0D);
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
   }

   public void m() {
      if(!this.world.isClientSide) {
         if(this.n()) {
            if(this.bm-- <= 0) {
               this.a(false);
               ItemStack itemstack = this.bA();
               this.setEquipment(0, (ItemStack)null);
               if(itemstack != null && itemstack.getItem() == Items.POTION) {
                  List list = Items.POTION.h(itemstack);
                  if(list != null) {
                     for(MobEffect mobeffect : list) {
                        this.addEffect(new MobEffect(mobeffect));
                     }
                  }
               }

               this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).c(b);
            }
         } else {
            short short1 = -1;
            if(this.random.nextFloat() < 0.15F && this.a(Material.WATER) && !this.hasEffect(MobEffectList.WATER_BREATHING)) {
               short1 = 8237;
            } else if(this.random.nextFloat() < 0.15F && this.isBurning() && !this.hasEffect(MobEffectList.FIRE_RESISTANCE)) {
               short1 = 16307;
            } else if(this.random.nextFloat() < 0.05F && this.getHealth() < this.getMaxHealth()) {
               short1 = 16341;
            } else if(this.random.nextFloat() < 0.25F && this.getGoalTarget() != null && !this.hasEffect(MobEffectList.FASTER_MOVEMENT) && this.getGoalTarget().h(this) > 121.0D) {
               short1 = 16274;
            } else if(this.random.nextFloat() < 0.25F && this.getGoalTarget() != null && !this.hasEffect(MobEffectList.FASTER_MOVEMENT) && this.getGoalTarget().h(this) > 121.0D) {
               short1 = 16274;
            }

            if(short1 > -1) {
               this.setEquipment(0, new ItemStack(Items.POTION, 1, short1));
               this.bm = this.bA().l();
               this.a(true);
               AttributeInstance attributeinstance = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);
               attributeinstance.c(b);
               attributeinstance.b(b);
            }
         }

         if(this.random.nextFloat() < 7.5E-4F) {
            this.world.broadcastEntityEffect(this, (byte)15);
         }
      }

      super.m();
   }

   protected float applyMagicModifier(DamageSource p_applyMagicModifier_1_, float p_applyMagicModifier_2_) {
      p_applyMagicModifier_2_ = super.applyMagicModifier(p_applyMagicModifier_1_, p_applyMagicModifier_2_);
      if(p_applyMagicModifier_1_.getEntity() == this) {
         p_applyMagicModifier_2_ = 0.0F;
      }

      if(p_applyMagicModifier_1_.isMagic()) {
         p_applyMagicModifier_2_ = (float)((double)p_applyMagicModifier_2_ * 0.15D);
      }

      return p_applyMagicModifier_2_;
   }

   protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_) {
      int i = this.random.nextInt(3) + 1;

      for(int j = 0; j < i; ++j) {
         int k = this.random.nextInt(3);
         Item item = c[this.random.nextInt(c.length)];
         if(p_dropDeathLoot_2_ > 0) {
            k += this.random.nextInt(p_dropDeathLoot_2_ + 1);
         }

         for(int l = 0; l < k; ++l) {
            this.a(item, 1);
         }
      }

   }

   public void a(EntityLiving p_a_1_, float p_a_2_) {
      if(!this.n()) {
         EntityPotion entitypotion = new EntityPotion(this.world, this, 32732);
         double d0 = p_a_1_.locY + (double)p_a_1_.getHeadHeight() - 1.100000023841858D;
         entitypotion.pitch -= -20.0F;
         double d1 = p_a_1_.locX + p_a_1_.motX - this.locX;
         double d2 = d0 - this.locY;
         double d3 = p_a_1_.locZ + p_a_1_.motZ - this.locZ;
         float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
         if(f >= 8.0F && !p_a_1_.hasEffect(MobEffectList.SLOWER_MOVEMENT)) {
            entitypotion.setPotionValue(32698);
         } else if(p_a_1_.getHealth() >= 8.0F && !p_a_1_.hasEffect(MobEffectList.POISON)) {
            entitypotion.setPotionValue(32660);
         } else if(f <= 3.0F && !p_a_1_.hasEffect(MobEffectList.WEAKNESS) && this.random.nextFloat() < 0.25F) {
            entitypotion.setPotionValue(32696);
         }

         entitypotion.shoot(d1, d2 + (double)(f * 0.2F), d3, 0.75F, 8.0F);
         this.world.addEntity(entitypotion);
      }
   }

   public float getHeadHeight() {
      return 1.62F;
   }
}
