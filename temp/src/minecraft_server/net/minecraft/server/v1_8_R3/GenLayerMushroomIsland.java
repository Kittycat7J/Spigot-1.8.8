package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.GenLayer;
import net.minecraft.server.v1_8_R3.IntCache;

public class GenLayerMushroomIsland extends GenLayer {
   public GenLayerMushroomIsland(long p_i813_1_, GenLayer p_i813_3_) {
      super(p_i813_1_);
      this.a = p_i813_3_;
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
            int k1 = aint[j1 + 0 + (i1 + 0) * k];
            int l1 = aint[j1 + 2 + (i1 + 0) * k];
            int i2 = aint[j1 + 0 + (i1 + 2) * k];
            int j2 = aint[j1 + 2 + (i1 + 2) * k];
            int k2 = aint[j1 + 1 + (i1 + 1) * k];
            this.a((long)(j1 + p_a_1_), (long)(i1 + p_a_2_));
            if(k2 == 0 && k1 == 0 && l1 == 0 && i2 == 0 && j2 == 0 && this.a(100) == 0) {
               aint1[j1 + i1 * p_a_3_] = BiomeBase.MUSHROOM_ISLAND.id;
            } else {
               aint1[j1 + i1 * p_a_3_] = k2;
            }
         }
      }

      return aint1;
   }
}
