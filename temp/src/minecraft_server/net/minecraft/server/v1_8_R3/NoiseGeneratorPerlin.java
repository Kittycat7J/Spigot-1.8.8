package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.NoiseGenerator;

public class NoiseGeneratorPerlin extends NoiseGenerator {
   private int[] d;
   public double a;
   public double b;
   public double c;
   private static final double[] e = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
   private static final double[] f = new double[]{1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D};
   private static final double[] g = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};
   private static final double[] h = new double[]{1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D};
   private static final double[] i = new double[]{0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D};

   public NoiseGeneratorPerlin() {
      this(new Random());
   }

   public NoiseGeneratorPerlin(Random p_i798_1_) {
      this.d = new int[512];
      this.a = p_i798_1_.nextDouble() * 256.0D;
      this.b = p_i798_1_.nextDouble() * 256.0D;
      this.c = p_i798_1_.nextDouble() * 256.0D;

      for(int ix = 0; ix < 256; this.d[ix] = ix++) {
         ;
      }

      for(int l = 0; l < 256; ++l) {
         int j = p_i798_1_.nextInt(256 - l) + l;
         int k = this.d[l];
         this.d[l] = this.d[j];
         this.d[j] = k;
         this.d[l + 256] = this.d[l];
      }

   }

   public final double b(double p_b_1_, double p_b_3_, double p_b_5_) {
      return p_b_3_ + p_b_1_ * (p_b_5_ - p_b_3_);
   }

   public final double a(int p_a_1_, double p_a_2_, double p_a_4_) {
      int ix = p_a_1_ & 15;
      return h[ix] * p_a_2_ + i[ix] * p_a_4_;
   }

   public final double a(int p_a_1_, double p_a_2_, double p_a_4_, double p_a_6_) {
      int ix = p_a_1_ & 15;
      return e[ix] * p_a_2_ + f[ix] * p_a_4_ + g[ix] * p_a_6_;
   }

   public void a(double[] p_a_1_, double p_a_2_, double p_a_4_, double p_a_6_, int p_a_8_, int p_a_9_, int p_a_10_, double p_a_11_, double p_a_13_, double p_a_15_, double p_a_17_) {
      if(p_a_9_ == 1) {
         int k5 = 0;
         int j = 0;
         int k = 0;
         int l5 = 0;
         double d0 = 0.0D;
         double d1 = 0.0D;
         int i6 = 0;
         double d2 = 1.0D / p_a_17_;

         for(int j1 = 0; j1 < p_a_8_; ++j1) {
            double d18 = p_a_2_ + (double)j1 * p_a_11_ + this.a;
            int k1 = (int)d18;
            if(d18 < (double)k1) {
               --k1;
            }

            int l1 = k1 & 255;
            d18 = d18 - (double)k1;
            double d19 = d18 * d18 * d18 * (d18 * (d18 * 6.0D - 15.0D) + 10.0D);

            for(int j6 = 0; j6 < p_a_10_; ++j6) {
               double d20 = p_a_6_ + (double)j6 * p_a_15_ + this.c;
               int k6 = (int)d20;
               if(d20 < (double)k6) {
                  --k6;
               }

               int l6 = k6 & 255;
               d20 = d20 - (double)k6;
               double d21 = d20 * d20 * d20 * (d20 * (d20 * 6.0D - 15.0D) + 10.0D);
               k5 = this.d[l1] + 0;
               j = this.d[k5] + l6;
               k = this.d[l1 + 1] + 0;
               l5 = this.d[k] + l6;
               d0 = this.b(d19, this.a(this.d[j], d18, d20), this.a(this.d[l5], d18 - 1.0D, 0.0D, d20));
               d1 = this.b(d19, this.a(this.d[j + 1], d18, 0.0D, d20 - 1.0D), this.a(this.d[l5 + 1], d18 - 1.0D, 0.0D, d20 - 1.0D));
               double d7 = this.b(d21, d0, d1);
               int i7 = i6++;
               p_a_1_[i7] += d7 * d2;
            }
         }

      } else {
         int ix = 0;
         double d8 = 1.0D / p_a_17_;
         int l = -1;
         int l2 = 0;
         int i3 = 0;
         int j3 = 0;
         int k3 = 0;
         int i1 = 0;
         int l3 = 0;
         double d9 = 0.0D;
         double d3 = 0.0D;
         double d10 = 0.0D;
         double d4 = 0.0D;

         for(int i2 = 0; i2 < p_a_8_; ++i2) {
            double d5 = p_a_2_ + (double)i2 * p_a_11_ + this.a;
            int j2 = (int)d5;
            if(d5 < (double)j2) {
               --j2;
            }

            int k2 = j2 & 255;
            d5 = d5 - (double)j2;
            double d6 = d5 * d5 * d5 * (d5 * (d5 * 6.0D - 15.0D) + 10.0D);

            for(int i4 = 0; i4 < p_a_10_; ++i4) {
               double d11 = p_a_6_ + (double)i4 * p_a_15_ + this.c;
               int j4 = (int)d11;
               if(d11 < (double)j4) {
                  --j4;
               }

               int k4 = j4 & 255;
               d11 = d11 - (double)j4;
               double d12 = d11 * d11 * d11 * (d11 * (d11 * 6.0D - 15.0D) + 10.0D);

               for(int l4 = 0; l4 < p_a_9_; ++l4) {
                  double d13 = p_a_4_ + (double)l4 * p_a_13_ + this.b;
                  int i5 = (int)d13;
                  if(d13 < (double)i5) {
                     --i5;
                  }

                  int j5 = i5 & 255;
                  d13 = d13 - (double)i5;
                  double d14 = d13 * d13 * d13 * (d13 * (d13 * 6.0D - 15.0D) + 10.0D);
                  if(l4 == 0 || j5 != l) {
                     l = j5;
                     l2 = this.d[k2] + j5;
                     i3 = this.d[l2] + k4;
                     j3 = this.d[l2 + 1] + k4;
                     k3 = this.d[k2 + 1] + j5;
                     i1 = this.d[k3] + k4;
                     l3 = this.d[k3 + 1] + k4;
                     d9 = this.b(d6, this.a(this.d[i3], d5, d13, d11), this.a(this.d[i1], d5 - 1.0D, d13, d11));
                     d3 = this.b(d6, this.a(this.d[j3], d5, d13 - 1.0D, d11), this.a(this.d[l3], d5 - 1.0D, d13 - 1.0D, d11));
                     d10 = this.b(d6, this.a(this.d[i3 + 1], d5, d13, d11 - 1.0D), this.a(this.d[i1 + 1], d5 - 1.0D, d13, d11 - 1.0D));
                     d4 = this.b(d6, this.a(this.d[j3 + 1], d5, d13 - 1.0D, d11 - 1.0D), this.a(this.d[l3 + 1], d5 - 1.0D, d13 - 1.0D, d11 - 1.0D));
                  }

                  double d15 = this.b(d14, d9, d3);
                  double d16 = this.b(d14, d10, d4);
                  double d17 = this.b(d12, d15, d16);
                  int j7 = ix++;
                  p_a_1_[j7] += d17 * d8;
               }
            }
         }

      }
   }
}
