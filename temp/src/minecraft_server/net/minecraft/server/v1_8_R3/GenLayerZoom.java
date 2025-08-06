package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.GenLayer;
import net.minecraft.server.v1_8_R3.IntCache;

public class GenLayerZoom extends GenLayer {
   public GenLayerZoom(long p_i831_1_, GenLayer p_i831_3_) {
      super(p_i831_1_);
      super.a = p_i831_3_;
   }

   public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      int i = p_a_1_ >> 1;
      int j = p_a_2_ >> 1;
      int k = (p_a_3_ >> 1) + 2;
      int l = (p_a_4_ >> 1) + 2;
      int[] aint = this.a.a(i, j, k, l);
      int i1 = k - 1 << 1;
      int j1 = l - 1 << 1;
      int[] aint1 = IntCache.a(i1 * j1);

      for(int k1 = 0; k1 < l - 1; ++k1) {
         int l1 = (k1 << 1) * i1;
         int i2 = 0;
         int j2 = aint[i2 + 0 + (k1 + 0) * k];

         for(int k2 = aint[i2 + 0 + (k1 + 1) * k]; i2 < k - 1; ++i2) {
            this.a((long)(i2 + i << 1), (long)(k1 + j << 1));
            int l2 = aint[i2 + 1 + (k1 + 0) * k];
            int i3 = aint[i2 + 1 + (k1 + 1) * k];
            aint1[l1] = j2;
            aint1[l1++ + i1] = this.a(new int[]{j2, k2});
            aint1[l1] = this.a(new int[]{j2, l2});
            aint1[l1++ + i1] = this.b(j2, l2, k2, i3);
            j2 = l2;
            k2 = i3;
         }
      }

      int[] aint2 = IntCache.a(p_a_3_ * p_a_4_);

      for(int j3 = 0; j3 < p_a_4_; ++j3) {
         System.arraycopy(aint1, (j3 + (p_a_2_ & 1)) * i1 + (p_a_1_ & 1), aint2, j3 * p_a_3_, p_a_3_);
      }

      return aint2;
   }

   public static GenLayer b(long p_b_0_, GenLayer p_b_2_, int p_b_3_) {
      Object object = p_b_2_;

      for(int i = 0; i < p_b_3_; ++i) {
         object = new GenLayerZoom(p_b_0_ + (long)i, (GenLayer)object);
      }

      return (GenLayer)object;
   }
}
