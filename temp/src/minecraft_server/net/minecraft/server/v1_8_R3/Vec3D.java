package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BaseBlockPosition;
import net.minecraft.server.v1_8_R3.MathHelper;

public class Vec3D {
   public final double a;
   public final double b;
   public final double c;

   public Vec3D(double p_i851_1_, double p_i851_3_, double p_i851_5_) {
      if(p_i851_1_ == -0.0D) {
         p_i851_1_ = 0.0D;
      }

      if(p_i851_3_ == -0.0D) {
         p_i851_3_ = 0.0D;
      }

      if(p_i851_5_ == -0.0D) {
         p_i851_5_ = 0.0D;
      }

      this.a = p_i851_1_;
      this.b = p_i851_3_;
      this.c = p_i851_5_;
   }

   public Vec3D(BaseBlockPosition p_i852_1_) {
      this((double)p_i852_1_.getX(), (double)p_i852_1_.getY(), (double)p_i852_1_.getZ());
   }

   public Vec3D a() {
      double d0 = (double)MathHelper.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
      return d0 < 1.0E-4D?new Vec3D(0.0D, 0.0D, 0.0D):new Vec3D(this.a / d0, this.b / d0, this.c / d0);
   }

   public double b(Vec3D p_b_1_) {
      return this.a * p_b_1_.a + this.b * p_b_1_.b + this.c * p_b_1_.c;
   }

   public Vec3D d(Vec3D p_d_1_) {
      return this.a(p_d_1_.a, p_d_1_.b, p_d_1_.c);
   }

   public Vec3D a(double p_a_1_, double p_a_3_, double p_a_5_) {
      return this.add(-p_a_1_, -p_a_3_, -p_a_5_);
   }

   public Vec3D e(Vec3D p_e_1_) {
      return this.add(p_e_1_.a, p_e_1_.b, p_e_1_.c);
   }

   public Vec3D add(double p_add_1_, double p_add_3_, double p_add_5_) {
      return new Vec3D(this.a + p_add_1_, this.b + p_add_3_, this.c + p_add_5_);
   }

   public double distanceSquared(Vec3D p_distanceSquared_1_) {
      double d0 = p_distanceSquared_1_.a - this.a;
      double d1 = p_distanceSquared_1_.b - this.b;
      double d2 = p_distanceSquared_1_.c - this.c;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public double b() {
      return (double)MathHelper.sqrt(this.a * this.a + this.b * this.b + this.c * this.c);
   }

   public Vec3D a(Vec3D p_a_1_, double p_a_2_) {
      double d0 = p_a_1_.a - this.a;
      double d1 = p_a_1_.b - this.b;
      double d2 = p_a_1_.c - this.c;
      if(d0 * d0 < 1.0000000116860974E-7D) {
         return null;
      } else {
         double d3 = (p_a_2_ - this.a) / d0;
         return d3 >= 0.0D && d3 <= 1.0D?new Vec3D(this.a + d0 * d3, this.b + d1 * d3, this.c + d2 * d3):null;
      }
   }

   public Vec3D b(Vec3D p_b_1_, double p_b_2_) {
      double d0 = p_b_1_.a - this.a;
      double d1 = p_b_1_.b - this.b;
      double d2 = p_b_1_.c - this.c;
      if(d1 * d1 < 1.0000000116860974E-7D) {
         return null;
      } else {
         double d3 = (p_b_2_ - this.b) / d1;
         return d3 >= 0.0D && d3 <= 1.0D?new Vec3D(this.a + d0 * d3, this.b + d1 * d3, this.c + d2 * d3):null;
      }
   }

   public Vec3D c(Vec3D p_c_1_, double p_c_2_) {
      double d0 = p_c_1_.a - this.a;
      double d1 = p_c_1_.b - this.b;
      double d2 = p_c_1_.c - this.c;
      if(d2 * d2 < 1.0000000116860974E-7D) {
         return null;
      } else {
         double d3 = (p_c_2_ - this.c) / d2;
         return d3 >= 0.0D && d3 <= 1.0D?new Vec3D(this.a + d0 * d3, this.b + d1 * d3, this.c + d2 * d3):null;
      }
   }

   public String toString() {
      return "(" + this.a + ", " + this.b + ", " + this.c + ")";
   }

   public Vec3D a(float p_a_1_) {
      float f = MathHelper.cos(p_a_1_);
      float f1 = MathHelper.sin(p_a_1_);
      double d0 = this.a;
      double d1 = this.b * (double)f + this.c * (double)f1;
      double d2 = this.c * (double)f - this.b * (double)f1;
      return new Vec3D(d0, d1, d2);
   }

   public Vec3D b(float p_b_1_) {
      float f = MathHelper.cos(p_b_1_);
      float f1 = MathHelper.sin(p_b_1_);
      double d0 = this.a * (double)f + this.c * (double)f1;
      double d1 = this.b;
      double d2 = this.c * (double)f - this.a * (double)f1;
      return new Vec3D(d0, d1, d2);
   }
}
