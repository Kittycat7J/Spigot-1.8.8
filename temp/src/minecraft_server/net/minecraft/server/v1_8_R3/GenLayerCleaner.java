package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.GenLayer;
import net.minecraft.server.v1_8_R3.IntCache;

public class GenLayerCleaner extends GenLayer {
   public GenLayerCleaner(long p_i825_1_, GenLayer p_i825_3_) {
      super(p_i825_1_);
      this.a = p_i825_3_;
   }

   public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      int[] aint = this.a.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
      int[] aint1 = IntCache.a(p_a_3_ * p_a_4_);

      for(int i = 0; i < p_a_4_; ++i) {
         for(int j = 0; j < p_a_3_; ++j) {
            this.a((long)(j + p_a_1_), (long)(i + p_a_2_));
            aint1[j + i * p_a_3_] = aint[j + i * p_a_3_] > 0?this.a(299999) + 2:0;
         }
      }

      return aint1;
   }
}
