package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityFireball;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityWither;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.Explosion;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.MobEffectList;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class EntityWitherSkull extends EntityFireball {
   public EntityWitherSkull(World p_i418_1_) {
      super(p_i418_1_);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntityWitherSkull(World p_i419_1_, EntityLiving p_i419_2_, double p_i419_3_, double p_i419_5_, double p_i419_7_) {
      super(p_i419_1_, p_i419_2_, p_i419_3_, p_i419_5_, p_i419_7_);
      this.setSize(0.3125F, 0.3125F);
   }

   protected float j() {
      return this.isCharged()?0.73F:super.j();
   }

   public boolean isBurning() {
      return false;
   }

   public float a(Explosion p_a_1_, World p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_) {
      float f = super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
      Block block = p_a_4_.getBlock();
      if(this.isCharged() && EntityWither.a(block)) {
         f = Math.min(0.8F, f);
      }

      return f;
   }

   protected void a(MovingObjectPosition p_a_1_) {
      if(!this.world.isClientSide) {
         if(p_a_1_.entity != null) {
            boolean flag = false;
            if(this.shooter != null) {
               flag = p_a_1_.entity.damageEntity(DamageSource.projectile(this, this.shooter), 8.0F);
               if(flag) {
                  if(!p_a_1_.entity.isAlive()) {
                     this.shooter.heal(5.0F, RegainReason.WITHER);
                  } else {
                     this.a(this.shooter, p_a_1_.entity);
                  }
               }
            } else {
               flag = p_a_1_.entity.damageEntity(DamageSource.MAGIC, 5.0F);
            }

            if(flag && p_a_1_.entity instanceof EntityLiving) {
               byte b0 = 0;
               if(this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                  b0 = 10;
               } else if(this.world.getDifficulty() == EnumDifficulty.HARD) {
                  b0 = 40;
               }

               if(b0 > 0) {
                  ((EntityLiving)p_a_1_.entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 20 * b0, 1));
               }
            }
         }

         ExplosionPrimeEvent explosionprimeevent = new ExplosionPrimeEvent(this.getBukkitEntity(), 1.0F, false);
         this.world.getServer().getPluginManager().callEvent(explosionprimeevent);
         if(!explosionprimeevent.isCancelled()) {
            this.world.createExplosion(this, this.locX, this.locY, this.locZ, explosionprimeevent.getRadius(), explosionprimeevent.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
         }

         this.die();
      }

   }

   public boolean ad() {
      return false;
   }

   public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_) {
      return false;
   }

   protected void h() {
      this.datawatcher.a(10, Byte.valueOf((byte)0));
   }

   public boolean isCharged() {
      return this.datawatcher.getByte(10) == 1;
   }

   public void setCharged(boolean p_setCharged_1_) {
      this.datawatcher.watch(10, Byte.valueOf((byte)(p_setCharged_1_?1:0)));
   }
}
