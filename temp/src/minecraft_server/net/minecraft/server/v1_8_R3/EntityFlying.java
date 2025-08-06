package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;

public abstract class EntityFlying extends EntityInsentient {
   public EntityFlying(World p_i1146_1_) {
      super(p_i1146_1_);
   }

   public void e(float p_e_1_, float p_e_2_) {
   }

   protected void a(double p_a_1_, boolean p_a_3_, Block p_a_4_, BlockPosition p_a_5_) {
   }

   public void g(float p_g_1_, float p_g_2_) {
      if(this.V()) {
         this.a(p_g_1_, p_g_2_, 0.02F);
         this.move(this.motX, this.motY, this.motZ);
         this.motX *= 0.800000011920929D;
         this.motY *= 0.800000011920929D;
         this.motZ *= 0.800000011920929D;
      } else if(this.ab()) {
         this.a(p_g_1_, p_g_2_, 0.02F);
         this.move(this.motX, this.motY, this.motZ);
         this.motX *= 0.5D;
         this.motY *= 0.5D;
         this.motZ *= 0.5D;
      } else {
         float f = 0.91F;
         if(this.onGround) {
            f = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
         }

         float f1 = 0.16277136F / (f * f * f);
         this.a(p_g_1_, p_g_2_, this.onGround?0.1F * f1:0.02F);
         f = 0.91F;
         if(this.onGround) {
            f = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.91F;
         }

         this.move(this.motX, this.motY, this.motZ);
         this.motX *= (double)f;
         this.motY *= (double)f;
         this.motZ *= (double)f;
      }

      this.aA = this.aB;
      double d0 = this.locX - this.lastX;
      double d1 = this.locZ - this.lastZ;
      float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
      if(f2 > 1.0F) {
         f2 = 1.0F;
      }

      this.aB += (f2 - this.aB) * 0.4F;
      this.aC += this.aB;
   }

   public boolean k_() {
      return false;
   }
}
