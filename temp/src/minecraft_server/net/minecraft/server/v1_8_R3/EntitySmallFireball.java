package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityFireball;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityCombustByEntityEvent;

public class EntitySmallFireball extends EntityFireball {
   public EntitySmallFireball(World p_i498_1_) {
      super(p_i498_1_);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World p_i499_1_, EntityLiving p_i499_2_, double p_i499_3_, double p_i499_5_, double p_i499_7_) {
      super(p_i499_1_, p_i499_2_, p_i499_3_, p_i499_5_, p_i499_7_);
      this.setSize(0.3125F, 0.3125F);
   }

   public EntitySmallFireball(World p_i500_1_, double p_i500_2_, double p_i500_4_, double p_i500_6_, double p_i500_8_, double p_i500_10_, double p_i500_12_) {
      super(p_i500_1_, p_i500_2_, p_i500_4_, p_i500_6_, p_i500_8_, p_i500_10_, p_i500_12_);
      this.setSize(0.3125F, 0.3125F);
   }

   protected void a(MovingObjectPosition p_a_1_) {
      if(!this.world.isClientSide) {
         if(p_a_1_.entity != null) {
            boolean flag = p_a_1_.entity.damageEntity(DamageSource.fireball(this, this.shooter), 5.0F);
            if(flag) {
               this.a(this.shooter, p_a_1_.entity);
               if(!p_a_1_.entity.isFireProof()) {
                  EntityCombustByEntityEvent entitycombustbyentityevent = new EntityCombustByEntityEvent((Projectile)this.getBukkitEntity(), p_a_1_.entity.getBukkitEntity(), 5);
                  p_a_1_.entity.world.getServer().getPluginManager().callEvent(entitycombustbyentityevent);
                  if(!entitycombustbyentityevent.isCancelled()) {
                     p_a_1_.entity.setOnFire(entitycombustbyentityevent.getDuration());
                  }
               }
            }
         } else {
            boolean flag1 = true;
            if(this.shooter != null && this.shooter instanceof EntityInsentient) {
               flag1 = this.world.getGameRules().getBoolean("mobGriefing");
            }

            if(flag1) {
               BlockPosition blockposition = p_a_1_.a().shift(p_a_1_.direction);
               if(this.world.isEmpty(blockposition) && this.isIncendiary && !CraftEventFactory.callBlockIgniteEvent(this.world, blockposition.getX(), blockposition.getY(), blockposition.getZ(), (Entity)this).isCancelled()) {
                  this.world.setTypeUpdate(blockposition, Blocks.FIRE.getBlockData());
               }
            }
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
}
