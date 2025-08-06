package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.MathHelper;

public class PathPoint {
   public final int a;
   public final int b;
   public final int c;
   private final int j;
   int d = -1;
   float e;
   float f;
   float g;
   PathPoint h;
   public boolean i;

   public PathPoint(int p_i832_1_, int p_i832_2_, int p_i832_3_) {
      this.a = p_i832_1_;
      this.b = p_i832_2_;
      this.c = p_i832_3_;
      this.j = a(p_i832_1_, p_i832_2_, p_i832_3_);
   }

   public static int a(int p_a_0_, int p_a_1_, int p_a_2_) {
      return p_a_1_ & 255 | (p_a_0_ & 32767) << 8 | (p_a_2_ & 32767) << 24 | (p_a_0_ < 0?Integer.MIN_VALUE:0) | (p_a_2_ < 0?'\u8000':0);
   }

   public float a(PathPoint p_a_1_) {
      float fx = (float)(p_a_1_.a - this.a);
      float f1 = (float)(p_a_1_.b - this.b);
      float f2 = (float)(p_a_1_.c - this.c);
      return MathHelper.c(fx * fx + f1 * f1 + f2 * f2);
   }

   public float b(PathPoint p_b_1_) {
      float fx = (float)(p_b_1_.a - this.a);
      float f1 = (float)(p_b_1_.b - this.b);
      float f2 = (float)(p_b_1_.c - this.c);
      return fx * fx + f1 * f1 + f2 * f2;
   }

   public boolean equals(Object p_equals_1_) {
      if(!(p_equals_1_ instanceof PathPoint)) {
         return false;
      } else {
         PathPoint pathpoint = (PathPoint)p_equals_1_;
         return this.j == pathpoint.j && this.a == pathpoint.a && this.b == pathpoint.b && this.c == pathpoint.c;
      }
   }

   public int hashCode() {
      return this.j;
   }

   public boolean a() {
      return this.d >= 0;
   }

   public String toString() {
      return this.a + ", " + this.b + ", " + this.c;
   }
}
