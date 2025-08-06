package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityBlaze;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityProjectile;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.World;

public class EntitySnowball extends EntityProjectile {
   public EntitySnowball(World p_i1247_1_) {
      super(p_i1247_1_);
   }

   public EntitySnowball(World p_i1248_1_, EntityLiving p_i1248_2_) {
      super(p_i1248_1_, p_i1248_2_);
   }

   public EntitySnowball(World p_i1249_1_, double p_i1249_2_, double p_i1249_4_, double p_i1249_6_) {
      super(p_i1249_1_, p_i1249_2_, p_i1249_4_, p_i1249_6_);
   }

   protected void a(MovingObjectPosition p_a_1_) {
      if(p_a_1_.entity != null) {
         byte b0 = 0;
         if(p_a_1_.entity instanceof EntityBlaze) {
            b0 = 3;
         }

         p_a_1_.entity.damageEntity(DamageSource.projectile(this, this.getShooter()), (float)b0);
      }

      for(int i = 0; i < 8; ++i) {
         this.world.addParticle(EnumParticle.SNOWBALL, this.locX, this.locY, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
      }

      if(!this.world.isClientSide) {
         this.die();
      }

   }
}
