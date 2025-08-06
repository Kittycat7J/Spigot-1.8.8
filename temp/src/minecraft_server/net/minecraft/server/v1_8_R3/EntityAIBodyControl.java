package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.MathHelper;

public class EntityAIBodyControl {
   private EntityLiving a;
   private int b;
   private float c;

   public EntityAIBodyControl(EntityLiving p_i1154_1_) {
      this.a = p_i1154_1_;
   }

   public void a() {
      double d0 = this.a.locX - this.a.lastX;
      double d1 = this.a.locZ - this.a.lastZ;
      if(d0 * d0 + d1 * d1 > 2.500000277905201E-7D) {
         this.a.aI = this.a.yaw;
         this.a.aK = this.a(this.a.aI, this.a.aK, 75.0F);
         this.c = this.a.aK;
         this.b = 0;
      } else {
         float f = 75.0F;
         if(Math.abs(this.a.aK - this.c) > 15.0F) {
            this.b = 0;
            this.c = this.a.aK;
         } else {
            ++this.b;
            boolean flag = true;
            if(this.b > 10) {
               f = Math.max(1.0F - (float)(this.b - 10) / 10.0F, 0.0F) * 75.0F;
            }
         }

         this.a.aI = this.a(this.a.aK, this.a.aI, f);
      }
   }

   private float a(float p_a_1_, float p_a_2_, float p_a_3_) {
      float f = MathHelper.g(p_a_1_ - p_a_2_);
      if(f < -p_a_3_) {
         f = -p_a_3_;
      }

      if(f >= p_a_3_) {
         f = p_a_3_;
      }

      return p_a_1_ - f;
   }
}
