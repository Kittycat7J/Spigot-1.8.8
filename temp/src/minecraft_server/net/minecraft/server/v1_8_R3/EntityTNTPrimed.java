package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Explosive;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityTNTPrimed extends Entity {
   public int fuseTicks;
   private EntityLiving source;
   public float yield;
   public boolean isIncendiary;

   public EntityTNTPrimed(World p_i32_1_) {
      super(p_i32_1_);
      this.yield = 4.0F;
      this.isIncendiary = false;
      this.k = true;
      this.setSize(0.98F, 0.98F);
   }

   public EntityTNTPrimed(World p_i33_1_, double p_i33_2_, double p_i33_4_, double p_i33_6_, EntityLiving p_i33_8_) {
      this(p_i33_1_);
      this.setPosition(p_i33_2_, p_i33_4_, p_i33_6_);
      float f = (float)(Math.random() * 3.1415927410125732D * 2.0D);
      this.motX = (double)(-((float)Math.sin((double)f)) * 0.02F);
      this.motY = 0.20000000298023224D;
      this.motZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
      this.fuseTicks = 80;
      this.lastX = p_i33_2_;
      this.lastY = p_i33_4_;
      this.lastZ = p_i33_6_;
      this.source = p_i33_8_;
   }

   protected void h() {
   }

   protected boolean s_() {
      return false;
   }

   public boolean ad() {
      return !this.dead;
   }

   public void t_() {
      if(this.world.spigotConfig.currentPrimedTnt++ <= this.world.spigotConfig.maxTntTicksPerTick) {
         this.lastX = this.locX;
         this.lastY = this.locY;
         this.lastZ = this.locZ;
         this.motY -= 0.03999999910593033D;
         this.move(this.motX, this.motY, this.motZ);
         this.motX *= 0.9800000190734863D;
         this.motY *= 0.9800000190734863D;
         this.motZ *= 0.9800000190734863D;
         if(this.onGround) {
            this.motX *= 0.699999988079071D;
            this.motZ *= 0.699999988079071D;
            this.motY *= -0.5D;
         }

         if(this.fuseTicks-- <= 0) {
            if(!this.world.isClientSide) {
               this.explode();
            }

            this.die();
         } else {
            this.W();
            this.world.addParticle(EnumParticle.SMOKE_NORMAL, this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
         }

      }
   }

   private void explode() {
      CraftServer craftserver = this.world.getServer();
      ExplosionPrimeEvent explosionprimeevent = new ExplosionPrimeEvent((Explosive)CraftEntity.getEntity(craftserver, this));
      craftserver.getPluginManager().callEvent(explosionprimeevent);
      if(!explosionprimeevent.isCancelled()) {
         this.world.createExplosion(this, this.locX, this.locY + (double)(this.length / 2.0F), this.locZ, explosionprimeevent.getRadius(), explosionprimeevent.getFire(), true);
      }

   }

   protected void b(NBTTagCompound p_b_1_) {
      p_b_1_.setByte("Fuse", (byte)this.fuseTicks);
   }

   protected void a(NBTTagCompound p_a_1_) {
      this.fuseTicks = p_a_1_.getByte("Fuse");
   }

   public EntityLiving getSource() {
      return this.source;
   }

   public float getHeadHeight() {
      return 0.0F;
   }
}
