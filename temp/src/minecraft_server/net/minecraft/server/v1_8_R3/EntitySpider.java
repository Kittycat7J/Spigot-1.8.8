package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.EnumMonsterType;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.GroupDataEntity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.NavigationAbstract;
import net.minecraft.server.v1_8_R3.NavigationSpider;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntitySpider extends EntityMonster {
   public EntitySpider(World p_i477_1_) {
      super(p_i477_1_);
      this.setSize(1.4F, 0.9F);
      this.goalSelector.a(1, new PathfinderGoalFloat(this));
      this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
      this.goalSelector.a(4, new EntitySpider.PathfinderGoalSpiderMeleeAttack(this, EntityHuman.class));
      this.goalSelector.a(4, new EntitySpider.PathfinderGoalSpiderMeleeAttack(this, EntityIronGolem.class));
      this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
      this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
      this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
      this.targetSelector.a(2, new EntitySpider.PathfinderGoalSpiderNearestAttackableTarget(this, EntityHuman.class));
      this.targetSelector.a(3, new EntitySpider.PathfinderGoalSpiderNearestAttackableTarget(this, EntityIronGolem.class));
   }

   public double an() {
      return (double)(this.length * 0.5F);
   }

   protected NavigationAbstract b(World p_b_1_) {
      return new NavigationSpider(this, p_b_1_);
   }

   protected void h() {
      super.h();
      this.datawatcher.a(16, new Byte((byte)0));
   }

   public void t_() {
      super.t_();
      if(!this.world.isClientSide) {
         this.a(this.positionChanged);
      }

   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeInstance(GenericAttributes.maxHealth).setValue(16.0D);
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
   }

   protected String z() {
      return "mob.spider.say";
   }

   protected String bo() {
      return "mob.spider.say";
   }

   protected String bp() {
      return "mob.spider.death";
   }

   protected void a(BlockPosition p_a_1_, Block p_a_2_) {
      this.makeSound("mob.spider.step", 0.15F, 1.0F);
   }

   protected Item getLoot() {
      return Items.STRING;
   }

   protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_) {
      super.dropDeathLoot(p_dropDeathLoot_1_, p_dropDeathLoot_2_);
      if(p_dropDeathLoot_1_ && (this.random.nextInt(3) == 0 || this.random.nextInt(1 + p_dropDeathLoot_2_) > 0)) {
         this.a(Items.SPIDER_EYE, 1);
      }

   }

   public boolean k_() {
      return this.n();
   }

   public void aA() {
   }

   public EnumMonsterType getMonsterType() {
      return EnumMonsterType.ARTHROPOD;
   }

   public boolean d(MobEffect p_d_1_) {
      return p_d_1_.getEffectId() == MobEffectList.POISON.id?false:super.d(p_d_1_);
   }

   public boolean n() {
      return (this.datawatcher.getByte(16) & 1) != 0;
   }

   public void a(boolean p_a_1_) {
      byte b0 = this.datawatcher.getByte(16);
      if(p_a_1_) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 = (byte)(b0 & -2);
      }

      this.datawatcher.watch(16, Byte.valueOf(b0));
   }

   public GroupDataEntity prepare(DifficultyDamageScaler p_prepare_1_, GroupDataEntity p_prepare_2_) {
      Object object = super.prepare(p_prepare_1_, p_prepare_2_);
      if(this.world.random.nextInt(100) == 0) {
         EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
         entityskeleton.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
         entityskeleton.prepare(p_prepare_1_, (GroupDataEntity)null);
         this.world.addEntity(entityskeleton, SpawnReason.JOCKEY);
         entityskeleton.mount(this);
      }

      if(object == null) {
         object = new EntitySpider.GroupDataSpider();
         if(this.world.getDifficulty() == EnumDifficulty.HARD && this.world.random.nextFloat() < 0.1F * p_prepare_1_.c()) {
            ((EntitySpider.GroupDataSpider)object).a(this.world.random);
         }
      }

      if(object instanceof EntitySpider.GroupDataSpider) {
         int i = ((EntitySpider.GroupDataSpider)object).a;
         if(i > 0 && MobEffectList.byId[i] != null) {
            this.addEffect(new MobEffect(i, Integer.MAX_VALUE));
         }
      }

      return (GroupDataEntity)object;
   }

   public float getHeadHeight() {
      return 0.65F;
   }

   public static class GroupDataSpider implements GroupDataEntity {
      public int a;

      public void a(Random p_a_1_) {
         int i = p_a_1_.nextInt(5);
         if(i <= 1) {
            this.a = MobEffectList.FASTER_MOVEMENT.id;
         } else if(i <= 2) {
            this.a = MobEffectList.INCREASE_DAMAGE.id;
         } else if(i <= 3) {
            this.a = MobEffectList.REGENERATION.id;
         } else if(i <= 4) {
            this.a = MobEffectList.INVISIBILITY.id;
         }

      }
   }

   static class PathfinderGoalSpiderMeleeAttack extends PathfinderGoalMeleeAttack {
      public PathfinderGoalSpiderMeleeAttack(EntitySpider p_i86_1_, Class<? extends Entity> p_i86_2_) {
         super(p_i86_1_, p_i86_2_, 1.0D, true);
      }

      public boolean b() {
         float f = this.b.c(1.0F);
         if(f >= 0.5F && this.b.bc().nextInt(100) == 0) {
            this.b.setGoalTarget((EntityLiving)null);
            return false;
         } else {
            return super.b();
         }
      }

      protected double a(EntityLiving p_a_1_) {
         return (double)(4.0F + p_a_1_.width);
      }
   }

   static class PathfinderGoalSpiderNearestAttackableTarget<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget {
      public PathfinderGoalSpiderNearestAttackableTarget(EntitySpider p_i187_1_, Class<T> p_i187_2_) {
         super(p_i187_1_, p_i187_2_, true);
      }

      public boolean a() {
         float f = this.e.c(1.0F);
         return f >= 0.5F?false:super.a();
      }
   }
}
