package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityAgeable;
import net.minecraft.server.v1_8_R3.EntityAnimal;
import net.minecraft.server.v1_8_R3.EntityArrow;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import net.minecraft.server.v1_8_R3.EntityGhast;
import net.minecraft.server.v1_8_R3.EntityHorse;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityRabbit;
import net.minecraft.server.v1_8_R3.EntitySheep;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.EntityTameableAnimal;
import net.minecraft.server.v1_8_R3.EnumColor;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemFood;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Navigation;
import net.minecraft.server.v1_8_R3.PathfinderGoalBeg;
import net.minecraft.server.v1_8_R3.PathfinderGoalBreed;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalFollowOwner;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalOwnerHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalOwnerHurtTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomTargetNonTamed;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityWolf extends EntityTameableAnimal {
   private float bo;
   private float bp;
   private boolean bq;
   private boolean br;
   private float bs;
   private float bt;

   public EntityWolf(World p_i296_1_) {
      super(p_i296_1_);
      this.setSize(0.6F, 0.8F);
      ((Navigation)this.getNavigation()).a(true);
      this.goalSelector.a(1, new PathfinderGoalFloat(this));
      this.goalSelector.a(2, this.bm);
      this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
      this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
      this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 1.0D, 10.0F, 2.0F));
      this.goalSelector.a(6, new PathfinderGoalBreed(this, 1.0D));
      this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
      this.goalSelector.a(8, new PathfinderGoalBeg(this, 8.0F));
      this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
      this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
      this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
      this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
      this.targetSelector.a(4, new PathfinderGoalRandomTargetNonTamed(this, EntityAnimal.class, false, new Predicate() {
         public boolean a(Entity p_a_1_) {
            return p_a_1_ instanceof EntitySheep || p_a_1_ instanceof EntityRabbit;
         }

         public boolean apply(Object p_apply_1_) {
            return this.a((Entity)p_apply_1_);
         }
      }));
      this.targetSelector.a(5, new PathfinderGoalNearestAttackableTarget(this, EntitySkeleton.class, false));
      this.setTamed(false);
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
      if(this.isTamed()) {
         this.getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
      } else {
         this.getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
      }

      this.getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE);
      this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(2.0D);
   }

   public void setGoalTarget(EntityLiving p_setGoalTarget_1_) {
      super.setGoalTarget(p_setGoalTarget_1_);
      if(p_setGoalTarget_1_ == null) {
         this.setAngry(false);
      } else if(!this.isTamed()) {
         this.setAngry(true);
      }

   }

   public void setGoalTarget(EntityLiving p_setGoalTarget_1_, TargetReason p_setGoalTarget_2_, boolean p_setGoalTarget_3_) {
      super.setGoalTarget(p_setGoalTarget_1_, p_setGoalTarget_2_, p_setGoalTarget_3_);
      if(p_setGoalTarget_1_ == null) {
         this.setAngry(false);
      } else if(!this.isTamed()) {
         this.setAngry(true);
      }

   }

   protected void E() {
      this.datawatcher.watch(18, Float.valueOf(this.getHealth()));
   }

   protected void h() {
      super.h();
      this.datawatcher.a(18, new Float(this.getHealth()));
      this.datawatcher.a(19, new Byte((byte)0));
      this.datawatcher.a(20, new Byte((byte)EnumColor.RED.getColorIndex()));
   }

   protected void a(BlockPosition p_a_1_, Block p_a_2_) {
      this.makeSound("mob.wolf.step", 0.15F, 1.0F);
   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setBoolean("Angry", this.isAngry());
      p_b_1_.setByte("CollarColor", (byte)this.getCollarColor().getInvColorIndex());
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.setAngry(p_a_1_.getBoolean("Angry"));
      if(p_a_1_.hasKeyOfType("CollarColor", 99)) {
         this.setCollarColor(EnumColor.fromInvColorIndex(p_a_1_.getByte("CollarColor")));
      }

   }

   protected String z() {
      return this.isAngry()?"mob.wolf.growl":(this.random.nextInt(3) == 0?(this.isTamed() && this.datawatcher.getFloat(18) < this.getMaxHealth() / 2.0F?"mob.wolf.whine":"mob.wolf.panting"):"mob.wolf.bark");
   }

   protected String bo() {
      return "mob.wolf.hurt";
   }

   protected String bp() {
      return "mob.wolf.death";
   }

   protected float bB() {
      return 0.4F;
   }

   protected Item getLoot() {
      return Item.getById(-1);
   }

   public void m() {
      super.m();
      if(!this.world.isClientSide && this.bq && !this.br && !this.cf() && this.onGround) {
         this.br = true;
         this.bs = 0.0F;
         this.bt = 0.0F;
         this.world.broadcastEntityEffect(this, (byte)8);
      }

      if(!this.world.isClientSide && this.getGoalTarget() == null && this.isAngry()) {
         this.setAngry(false);
      }

   }

   public void t_() {
      super.t_();
      this.bp = this.bo;
      if(this.cx()) {
         this.bo += (1.0F - this.bo) * 0.4F;
      } else {
         this.bo += (0.0F - this.bo) * 0.4F;
      }

      if(this.U()) {
         this.bq = true;
         this.br = false;
         this.bs = 0.0F;
         this.bt = 0.0F;
      } else if((this.bq || this.br) && this.br) {
         if(this.bs == 0.0F) {
            this.makeSound("mob.wolf.shake", this.bB(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
         }

         this.bt = this.bs;
         this.bs += 0.05F;
         if(this.bt >= 2.0F) {
            this.bq = false;
            this.br = false;
            this.bt = 0.0F;
            this.bs = 0.0F;
         }

         if(this.bs > 0.4F) {
            float f = (float)this.getBoundingBox().b;
            int i = (int)(MathHelper.sin((this.bs - 0.4F) * 3.1415927F) * 7.0F);

            for(int j = 0; j < i; ++j) {
               float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
               float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
               this.world.addParticle(EnumParticle.WATER_SPLASH, this.locX + (double)f1, (double)(f + 0.8F), this.locZ + (double)f2, this.motX, this.motY, this.motZ, new int[0]);
            }
         }
      }

   }

   public float getHeadHeight() {
      return this.length * 0.8F;
   }

   public int bQ() {
      return this.isSitting()?20:super.bQ();
   }

   public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_) {
      if(this.isInvulnerable(p_damageEntity_1_)) {
         return false;
      } else {
         Entity entity = p_damageEntity_1_.getEntity();
         if(entity != null && !(entity instanceof EntityHuman) && !(entity instanceof EntityArrow)) {
            p_damageEntity_2_ = (p_damageEntity_2_ + 1.0F) / 2.0F;
         }

         return super.damageEntity(p_damageEntity_1_, p_damageEntity_2_);
      }
   }

   public boolean r(Entity p_r_1_) {
      boolean flag = p_r_1_.damageEntity(DamageSource.mobAttack(this), (float)((int)this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue()));
      if(flag) {
         this.a((EntityLiving)this, (Entity)p_r_1_);
      }

      return flag;
   }

   public void setTamed(boolean p_setTamed_1_) {
      super.setTamed(p_setTamed_1_);
      if(p_setTamed_1_) {
         this.getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
      } else {
         this.getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
      }

      this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(4.0D);
   }

   public boolean a(EntityHuman p_a_1_) {
      ItemStack itemstack = p_a_1_.inventory.getItemInHand();
      if(this.isTamed()) {
         if(itemstack != null) {
            if(itemstack.getItem() instanceof ItemFood) {
               ItemFood itemfood = (ItemFood)itemstack.getItem();
               if(itemfood.g() && this.datawatcher.getFloat(18) < 20.0F) {
                  if(!p_a_1_.abilities.canInstantlyBuild) {
                     --itemstack.count;
                  }

                  this.heal((float)itemfood.getNutrition(itemstack), RegainReason.EATING);
                  if(itemstack.count <= 0) {
                     p_a_1_.inventory.setItem(p_a_1_.inventory.itemInHandIndex, (ItemStack)null);
                  }

                  return true;
               }
            } else if(itemstack.getItem() == Items.DYE) {
               EnumColor enumcolor = EnumColor.fromInvColorIndex(itemstack.getData());
               if(enumcolor != this.getCollarColor()) {
                  this.setCollarColor(enumcolor);
                  if(!p_a_1_.abilities.canInstantlyBuild && --itemstack.count <= 0) {
                     p_a_1_.inventory.setItem(p_a_1_.inventory.itemInHandIndex, (ItemStack)null);
                  }

                  return true;
               }
            }
         }

         if(this.e(p_a_1_) && !this.world.isClientSide && !this.d(itemstack)) {
            this.bm.setSitting(!this.isSitting());
            this.aY = false;
            this.navigation.n();
            this.setGoalTarget((EntityLiving)null, TargetReason.FORGOT_TARGET, true);
         }
      } else if(itemstack != null && itemstack.getItem() == Items.BONE && !this.isAngry()) {
         if(!p_a_1_.abilities.canInstantlyBuild) {
            --itemstack.count;
         }

         if(itemstack.count <= 0) {
            p_a_1_.inventory.setItem(p_a_1_.inventory.itemInHandIndex, (ItemStack)null);
         }

         if(!this.world.isClientSide) {
            if(this.random.nextInt(3) == 0 && !CraftEventFactory.callEntityTameEvent(this, p_a_1_).isCancelled()) {
               this.setTamed(true);
               this.navigation.n();
               this.setGoalTarget((EntityLiving)null, TargetReason.FORGOT_TARGET, true);
               this.bm.setSitting(true);
               this.setHealth(this.getMaxHealth());
               this.setOwnerUUID(p_a_1_.getUniqueID().toString());
               this.l(true);
               this.world.broadcastEntityEffect(this, (byte)7);
            } else {
               this.l(false);
               this.world.broadcastEntityEffect(this, (byte)6);
            }
         }

         return true;
      }

      return super.a(p_a_1_);
   }

   public boolean d(ItemStack p_d_1_) {
      return p_d_1_ == null?false:(!(p_d_1_.getItem() instanceof ItemFood)?false:((ItemFood)p_d_1_.getItem()).g());
   }

   public int bV() {
      return 8;
   }

   public boolean isAngry() {
      return (this.datawatcher.getByte(16) & 2) != 0;
   }

   public void setAngry(boolean p_setAngry_1_) {
      byte b0 = this.datawatcher.getByte(16);
      if(p_setAngry_1_) {
         this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 2)));
      } else {
         this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & -3)));
      }

   }

   public EnumColor getCollarColor() {
      return EnumColor.fromInvColorIndex(this.datawatcher.getByte(20) & 15);
   }

   public void setCollarColor(EnumColor p_setCollarColor_1_) {
      this.datawatcher.watch(20, Byte.valueOf((byte)(p_setCollarColor_1_.getInvColorIndex() & 15)));
   }

   public EntityWolf b(EntityAgeable p_b_1_) {
      EntityWolf entitywolf = new EntityWolf(this.world);
      String s = this.getOwnerUUID();
      if(s != null && s.trim().length() > 0) {
         entitywolf.setOwnerUUID(s);
         entitywolf.setTamed(true);
      }

      return entitywolf;
   }

   public void p(boolean p_p_1_) {
      if(p_p_1_) {
         this.datawatcher.watch(19, Byte.valueOf((byte)1));
      } else {
         this.datawatcher.watch(19, Byte.valueOf((byte)0));
      }

   }

   public boolean mate(EntityAnimal p_mate_1_) {
      if(p_mate_1_ == this) {
         return false;
      } else if(!this.isTamed()) {
         return false;
      } else if(!(p_mate_1_ instanceof EntityWolf)) {
         return false;
      } else {
         EntityWolf entitywolf = (EntityWolf)p_mate_1_;
         return !entitywolf.isTamed()?false:(entitywolf.isSitting()?false:this.isInLove() && entitywolf.isInLove());
      }
   }

   public boolean cx() {
      return this.datawatcher.getByte(19) == 1;
   }

   protected boolean isTypeNotPersistent() {
      return !this.isTamed();
   }

   public boolean a(EntityLiving p_a_1_, EntityLiving p_a_2_) {
      if(!(p_a_1_ instanceof EntityCreeper) && !(p_a_1_ instanceof EntityGhast)) {
         if(p_a_1_ instanceof EntityWolf) {
            EntityWolf entitywolf = (EntityWolf)p_a_1_;
            if(entitywolf.isTamed() && entitywolf.getOwner() == p_a_2_) {
               return false;
            }
         }

         return p_a_1_ instanceof EntityHuman && p_a_2_ instanceof EntityHuman && !((EntityHuman)p_a_2_).a((EntityHuman)p_a_1_)?false:!(p_a_1_ instanceof EntityHorse) || !((EntityHorse)p_a_1_).isTame();
      } else {
         return false;
      }
   }

   public boolean cb() {
      return !this.isAngry() && super.cb();
   }

   public EntityAgeable createChild(EntityAgeable p_createChild_1_) {
      return this.b(p_createChild_1_);
   }
}
