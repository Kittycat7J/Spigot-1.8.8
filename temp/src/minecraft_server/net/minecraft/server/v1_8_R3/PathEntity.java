package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.PathPoint;
import net.minecraft.server.v1_8_R3.Vec3D;

public class PathEntity {
   private final PathPoint[] a;
   private int b;
   private int c;

   public PathEntity(PathPoint[] p_i833_1_) {
      this.a = p_i833_1_;
      this.c = p_i833_1_.length;
   }

   public void a() {
      ++this.b;
   }

   public boolean b() {
      return this.b >= this.c;
   }

   public PathPoint c() {
      return this.c > 0?this.a[this.c - 1]:null;
   }

   public PathPoint a(int p_a_1_) {
      return this.a[p_a_1_];
   }

   public int d() {
      return this.c;
   }

   public void b(int p_b_1_) {
      this.c = p_b_1_;
   }

   public int e() {
      return this.b;
   }

   public void c(int p_c_1_) {
      this.b = p_c_1_;
   }

   public Vec3D a(Entity p_a_1_, int p_a_2_) {
      double d0 = (double)this.a[p_a_2_].a + (double)((int)(p_a_1_.width + 1.0F)) * 0.5D;
      double d1 = (double)this.a[p_a_2_].b;
      double d2 = (double)this.a[p_a_2_].c + (double)((int)(p_a_1_.width + 1.0F)) * 0.5D;
      return new Vec3D(d0, d1, d2);
   }

   public Vec3D a(Entity p_a_1_) {
      return this.a(p_a_1_, this.b);
   }

   public boolean a(PathEntity p_a_1_) {
      if(p_a_1_ == null) {
         return false;
      } else if(p_a_1_.a.length != this.a.length) {
         return false;
      } else {
         for(int i = 0; i < this.a.length; ++i) {
            if(this.a[i].a != p_a_1_.a[i].a || this.a[i].b != p_a_1_.a[i].b || this.a[i].c != p_a_1_.a[i].c) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean b(Vec3D p_b_1_) {
      PathPoint pathpoint = this.c();
      return pathpoint == null?false:pathpoint.a == (int)p_b_1_.a && pathpoint.c == (int)p_b_1_.c;
   }
}
