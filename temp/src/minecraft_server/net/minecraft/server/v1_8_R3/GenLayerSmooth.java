package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.GenLayer;
import net.minecraft.server.v1_8_R3.IntCache;

public class GenLayerSmooth extends GenLayer {
   public GenLayerSmooth(long p_i829_1_, GenLayer p_i829_3_) {
      super(p_i829_1_);
      super.a = p_i829_3_;
   }

   public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      int i = p_a_1_ - 1;
      int j = p_a_2_ - 1;
      int k = p_a_3_ + 2;
      int l = p_a_4_ + 2;
      int[] aint = this.a.a(i, j, k, l);
      int[] aint1 = IntCache.a(p_a_3_ * p_a_4_);

      for(int i1 = 0; i1 < p_a_4_; ++i1) {
         for(int j1 = 0; j1 < p_a_3_; ++j1) {
            int k1 = aint[j1 + 0 + (i1 + 1) * k];
            int l1 = aint[j1 + 2 + (i1 + 1) * k];
            int i2 = aint[j1 + 1 + (i1 + 0) * k];
            int j2 = aint[j1 + 1 + (i1 + 2) * k];
            int k2 = aint[j1 + 1 + (i1 + 1) * k];
            if(k1 == l1 && i2 == j2) {
               this.a((long)(j1 + p_a_1_), (long)(i1 + p_a_2_));
               if(this.a(2) == 0) {
                  k2 = k1;
               } else {
                  k2 = i2;
               }
            } else {
               if(k1 == l1) {
                  k2 = k1;
               }

               if(i2 == j2) {
                  k2 = i2;
               }
            }

            aint1[j1 + i1 * p_a_3_] = k2;
         }
      }

      return aint1;
   }
}
