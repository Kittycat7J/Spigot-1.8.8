package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.EntityMonster;
import net.minecraft.server.v1_8_R3.EntityOcelot;
import net.minecraft.server.v1_8_R3.EntitySkeleton;
import net.minecraft.server.v1_8_R3.GenericAttributes;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PathfinderGoalAvoidTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R3.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R3.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R3.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R3.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R3.PathfinderGoalSwell;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.CreeperPowerEvent.PowerCause;

public class EntityCreeper extends EntityMonster {
   private int a;
   private int fuseTicks;
   private int maxFuseTicks = 30;
   private int explosionRadius = 3;
   private int bn = 0;
   private int record = -1;

   public EntityCreeper(World p_i7_1_) {
      super(p_i7_1_);
      this.goalSelector.a(1, new PathfinderGoalFloat(this));
      this.goalSelector.a(2, new PathfinderGoalSwell(this));
      this.goalSelector.a(3, new PathfinderGoalAvoidTarget(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
      this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, false));
      this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
      this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
      this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
      this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
      this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
   }

   protected void initAttributes() {
      super.initAttributes();
      this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
   }

   public int aE() {
      return this.getGoalTarget() == null?3:3 + (int)(this.getHealth() - 1.0F);
   }

   public void e(float p_e_1_, float p_e_2_) {
      super.e(p_e_1_, p_e_2_);
      this.fuseTicks = (int)((float)this.fuseTicks + p_e_1_ * 1.5F);
      if(this.fuseTicks > this.maxFuseTicks - 5) {
         this.fuseTicks = this.maxFuseTicks - 5;
      }

   }

   protected void h() {
      super.h();
      this.datawatcher.a(16, Byte.valueOf((byte)-1));
      this.datawatcher.a(17, Byte.valueOf((byte)0));
      this.datawatcher.a(18, Byte.valueOf((byte)0));
   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      if(this.datawatcher.getByte(17) == 1) {
         p_b_1_.setBoolean("powered", true);
      }

      p_b_1_.setShort("Fuse", (short)this.maxFuseTicks);
      p_b_1_.setByte("ExplosionRadius", (byte)this.explosionRadius);
      p_b_1_.setBoolean("ignited", this.cn());
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      this.datawatcher.watch(17, Byte.valueOf((byte)(p_a_1_.getBoolean("powered")?1:0)));
      if(p_a_1_.hasKeyOfType("Fuse", 99)) {
         this.maxFuseTicks = p_a_1_.getShort("Fuse");
      }

      if(p_a_1_.hasKeyOfType("ExplosionRadius", 99)) {
         this.explosionRadius = p_a_1_.getByte("ExplosionRadius");
      }

      if(p_a_1_.getBoolean("ignited")) {
         this.co();
      }

   }

   public void t_() {
      if(this.isAlive()) {
         this.a = this.fuseTicks;
         if(this.cn()) {
            this.a(1);
         }

         int i = this.cm();
         if(i > 0 && this.fuseTicks == 0) {
            this.makeSound("creeper.primed", 1.0F, 0.5F);
         }

         this.fuseTicks += i;
         if(this.fuseTicks < 0) {
            this.fuseTicks = 0;
         }

         if(this.fuseTicks >= this.maxFuseTicks) {
            this.fuseTicks = this.maxFuseTicks;
            this.cr();
         }
      }

      super.t_();
   }

   protected String bo() {
      return "mob.creeper.say";
   }

   protected String bp() {
      return "mob.creeper.death";
   }

   public void die(DamageSource p_die_1_) {
      if(p_die_1_.getEntity() instanceof EntitySkeleton) {
         int i = Item.getId(Items.RECORD_13);
         int j = Item.getId(Items.RECORD_WAIT);
         int k = i + this.random.nextInt(j - i + 1);
         this.record = k;
      } else if(p_die_1_.getEntity() instanceof EntityCreeper && p_die_1_.getEntity() != this && ((EntityCreeper)p_die_1_.getEntity()).isPowered() && ((EntityCreeper)p_die_1_.getEntity()).cp()) {
         ((EntityCreeper)p_die_1_.getEntity()).cq();
         this.headDrop = new ItemStack(Items.SKULL, 1, 4);
      }

      super.die(p_die_1_);
   }

   protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_) {
      super.dropDeathLoot(p_dropDeathLoot_1_, p_dropDeathLoot_2_);
      if(this.record != -1) {
         this.a(Item.getById(this.record), 1);
         this.record = -1;
      }

   }

   public boolean r(Entity p_r_1_) {
      return true;
   }

   public boolean isPowered() {
      return this.datawatcher.getByte(17) == 1;
   }

   protected Item getLoot() {
      return Items.GUNPOWDER;
   }

   public int cm() {
      return this.datawatcher.getByte(16);
   }

   public void a(int p_a_1_) {
      this.datawatcher.watch(16, Byte.valueOf((byte)p_a_1_));
   }

   public void onLightningStrike(EntityLightning p_onLightningStrike_1_) {
      super.onLightningStrike(p_onLightningStrike_1_);
      if(!CraftEventFactory.callCreeperPowerEvent(this, p_onLightningStrike_1_, PowerCause.LIGHTNING).isCancelled()) {
         this.setPowered(true);
      }
   }

   public void setPowered(boolean p_setPowered_1_) {
      if(!p_setPowered_1_) {
         this.datawatcher.watch(17, Byte.valueOf((byte)0));
      } else {
         this.datawatcher.watch(17, Byte.valueOf((byte)1));
      }

   }

   protected boolean a(EntityHuman p_a_1_) {
      ItemStack itemstack = p_a_1_.inventory.getItemInHand();
      if(itemstack != null && itemstack.getItem() == Items.FLINT_AND_STEEL) {
         this.world.makeSound(this.locX + 0.5D, this.locY + 0.5D, this.locZ + 0.5D, "fire.ignite", 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
         p_a_1_.bw();
         if(!this.world.isClientSide) {
            this.co();
            itemstack.damage(1, p_a_1_);
            return true;
         }
      }

      return super.a(p_a_1_);
   }

   private void cr() {
      if(!this.world.isClientSide) {
         boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
         float f = this.isPowered()?2.0F:1.0F;
         ExplosionPrimeEvent explosionprimeevent = new ExplosionPrimeEvent(this.getBukkitEntity(), (float)this.explosionRadius * f, false);
         this.world.getServer().getPluginManager().callEvent(explosionprimeevent);
         if(!explosionprimeevent.isCancelled()) {
            this.world.createExplosion(this, this.locX, this.locY, this.locZ, explosionprimeevent.getRadius(), explosionprimeevent.getFire(), flag);
            this.die();
         } else {
            this.fuseTicks = 0;
         }
      }

   }

   public boolean cn() {
      return this.datawatcher.getByte(18) != 0;
   }

   public void co() {
      this.datawatcher.watch(18, Byte.valueOf((byte)1));
   }

   public boolean cp() {
      return this.bn < 1 && this.world.getGameRules().getBoolean("doMobLoot");
   }

   public void cq() {
      ++this.bn;
   }
}
