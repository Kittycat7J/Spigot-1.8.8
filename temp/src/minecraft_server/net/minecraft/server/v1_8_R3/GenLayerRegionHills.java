package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.GenLayer;
import net.minecraft.server.v1_8_R3.IntCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenLayerRegionHills extends GenLayer {
   private static final Logger c = LogManager.getLogger();
   private GenLayer d;

   public GenLayerRegionHills(long p_i823_1_, GenLayer p_i823_3_, GenLayer p_i823_4_) {
      super(p_i823_1_);
      this.a = p_i823_3_;
      this.d = p_i823_4_;
   }

   public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      int[] aint = this.a.a(p_a_1_ - 1, p_a_2_ - 1, p_a_3_ + 2, p_a_4_ + 2);
      int[] aint1 = this.d.a(p_a_1_ - 1, p_a_2_ - 1, p_a_3_ + 2, p_a_4_ + 2);
      int[] aint2 = IntCache.a(p_a_3_ * p_a_4_);

      for(int i = 0; i < p_a_4_; ++i) {
         for(int j = 0; j < p_a_3_; ++j) {
            this.a((long)(j + p_a_1_), (long)(i + p_a_2_));
            int k = aint[j + 1 + (i + 1) * (p_a_3_ + 2)];
            int l = aint1[j + 1 + (i + 1) * (p_a_3_ + 2)];
            boolean flag = (l - 2) % 29 == 0;
            if(k > 255) {
               c.debug("old! " + k);
            }

            if(k != 0 && l >= 2 && (l - 2) % 29 == 1 && k < 128) {
               if(BiomeBase.getBiome(k + 128) != null) {
                  aint2[j + i * p_a_3_] = k + 128;
               } else {
                  aint2[j + i * p_a_3_] = k;
               }
            } else if(this.a(3) != 0 && !flag) {
               aint2[j + i * p_a_3_] = k;
            } else {
               int i1 = k;
               if(k == BiomeBase.DESERT.id) {
                  i1 = BiomeBase.DESERT_HILLS.id;
               } else if(k == BiomeBase.FOREST.id) {
                  i1 = BiomeBase.FOREST_HILLS.id;
               } else if(k == BiomeBase.BIRCH_FOREST.id) {
                  i1 = BiomeBase.BIRCH_FOREST_HILLS.id;
               } else if(k == BiomeBase.ROOFED_FOREST.id) {
                  i1 = BiomeBase.PLAINS.id;
               } else if(k == BiomeBase.TAIGA.id) {
                  i1 = BiomeBase.TAIGA_HILLS.id;
               } else if(k == BiomeBase.MEGA_TAIGA.id) {
                  i1 = BiomeBase.MEGA_TAIGA_HILLS.id;
               } else if(k == BiomeBase.COLD_TAIGA.id) {
                  i1 = BiomeBase.COLD_TAIGA_HILLS.id;
               } else if(k == BiomeBase.PLAINS.id) {
                  if(this.a(3) == 0) {
                     i1 = BiomeBase.FOREST_HILLS.id;
                  } else {
                     i1 = BiomeBase.FOREST.id;
                  }
               } else if(k == BiomeBase.ICE_PLAINS.id) {
                  i1 = BiomeBase.ICE_MOUNTAINS.id;
               } else if(k == BiomeBase.JUNGLE.id) {
                  i1 = BiomeBase.JUNGLE_HILLS.id;
               } else if(k == BiomeBase.OCEAN.id) {
                  i1 = BiomeBase.DEEP_OCEAN.id;
               } else if(k == BiomeBase.EXTREME_HILLS.id) {
                  i1 = BiomeBase.EXTREME_HILLS_PLUS.id;
               } else if(k == BiomeBase.SAVANNA.id) {
                  i1 = BiomeBase.SAVANNA_PLATEAU.id;
               } else if(a(k, BiomeBase.MESA_PLATEAU_F.id)) {
                  i1 = BiomeBase.MESA.id;
               } else if(k == BiomeBase.DEEP_OCEAN.id && this.a(3) == 0) {
                  int j1 = this.a(2);
                  if(j1 == 0) {
                     i1 = BiomeBase.PLAINS.id;
                  } else {
                     i1 = BiomeBase.FOREST.id;
                  }
               }

               if(flag && i1 != k) {
                  if(BiomeBase.getBiome(i1 + 128) != null) {
                     i1 += 128;
                  } else {
                     i1 = k;
                  }
               }

               if(i1 == k) {
                  aint2[j + i * p_a_3_] = k;
               } else {
                  int k2 = aint[j + 1 + (i + 1 - 1) * (p_a_3_ + 2)];
                  int k1 = aint[j + 1 + 1 + (i + 1) * (p_a_3_ + 2)];
                  int l1 = aint[j + 1 - 1 + (i + 1) * (p_a_3_ + 2)];
                  int i2 = aint[j + 1 + (i + 1 + 1) * (p_a_3_ + 2)];
                  int j2 = 0;
                  if(a(k2, k)) {
                     ++j2;
                  }

                  if(a(k1, k)) {
                     ++j2;
                  }

                  if(a(l1, k)) {
                     ++j2;
                  }

                  if(a(i2, k)) {
                     ++j2;
                  }

                  if(j2 >= 3) {
                     aint2[j + i * p_a_3_] = i1;
                  } else {
                     aint2[j + i * p_a_3_] = k;
                  }
               }
            }
         }
      }

      return aint2;
   }
}
