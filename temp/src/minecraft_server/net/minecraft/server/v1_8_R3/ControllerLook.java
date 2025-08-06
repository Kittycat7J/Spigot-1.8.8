package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.MathHelper;

public class ControllerLook {
   private EntityInsentient a;
   private float b;
   private float c;
   private boolean d;
   private double e;
   private double f;
   private double g;

   public ControllerLook(EntityInsentient p_i1156_1_) {
      this.a = p_i1156_1_;
   }

   public void a(Entity p_a_1_, float p_a_2_, float p_a_3_) {
      this.e = p_a_1_.locX;
      if(p_a_1_ instanceof EntityLiving) {
         this.f = p_a_1_.locY + (double)p_a_1_.getHeadHeight();
      } else {
         this.f = (p_a_1_.getBoundingBox().b + p_a_1_.getBoundingBox().e) / 2.0D;
      }

      this.g = p_a_1_.locZ;
      this.b = p_a_2_;
      this.c = p_a_3_;
      this.d = true;
   }

   public void a(double p_a_1_, double p_a_3_, double p_a_5_, float p_a_7_, float p_a_8_) {
      this.e = p_a_1_;
      this.f = p_a_3_;
      this.g = p_a_5_;
      this.b = p_a_7_;
      this.c = p_a_8_;
      this.d = true;
   }

   public void a() {
      this.a.pitch = 0.0F;
      if(this.d) {
         this.d = false;
         double d0 = this.e - this.a.locX;
         double d1 = this.f - (this.a.locY + (double)this.a.getHeadHeight());
         double d2 = this.g - this.a.locZ;
         double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
         float fx = (float)(MathHelper.b(d2, d0) * 180.0D / 3.1415927410125732D) - 90.0F;
         float f1 = (float)(-(MathHelper.b(d1, d3) * 180.0D / 3.1415927410125732D));
         this.a.pitch = this.a(this.a.pitch, f1, this.c);
         this.a.aK = this.a(this.a.aK, fx, this.b);
      } else {
         this.a.aK = this.a(this.a.aK, this.a.aI, 10.0F);
      }

      float f2 = MathHelper.g(this.a.aK - this.a.aI);
      if(!this.a.getNavigation().m()) {
         if(f2 < -75.0F) {
            this.a.aK = this.a.aI - 75.0F;
         }

         if(f2 > 75.0F) {
            this.a.aK = this.a.aI + 75.0F;
         }
      }

   }

   private float a(float p_a_1_, float p_a_2_, float p_a_3_) {
      float fx = MathHelper.g(p_a_2_ - p_a_1_);
      if(fx > p_a_3_) {
         fx = p_a_3_;
      }

      if(fx < -p_a_3_) {
         fx = -p_a_3_;
      }

      return p_a_1_ + fx;
   }

   public boolean b() {
      return this.d;
   }

   public double e() {
      return this.e;
   }

   public double f() {
      return this.f;
   }

   public double g() {
      return this.g;
   }
}
