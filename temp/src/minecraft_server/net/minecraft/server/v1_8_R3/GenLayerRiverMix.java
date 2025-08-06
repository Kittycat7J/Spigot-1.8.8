package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.GenLayer;
import net.minecraft.server.v1_8_R3.IntCache;

public class GenLayerRiverMix extends GenLayer {
   private GenLayer c;
   private GenLayer d;

   public GenLayerRiverMix(long p_i827_1_, GenLayer p_i827_3_, GenLayer p_i827_4_) {
      super(p_i827_1_);
      this.c = p_i827_3_;
      this.d = p_i827_4_;
   }

   public void a(long p_a_1_) {
      this.c.a(p_a_1_);
      this.d.a(p_a_1_);
      super.a(p_a_1_);
   }

   public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      int[] aint = this.c.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
      int[] aint1 = this.d.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
      int[] aint2 = IntCache.a(p_a_3_ * p_a_4_);

      for(int i = 0; i < p_a_3_ * p_a_4_; ++i) {
         if(aint[i] != BiomeBase.OCEAN.id && aint[i] != BiomeBase.DEEP_OCEAN.id) {
            if(aint1[i] == BiomeBase.RIVER.id) {
               if(aint[i] == BiomeBase.ICE_PLAINS.id) {
                  aint2[i] = BiomeBase.FROZEN_RIVER.id;
               } else if(aint[i] != BiomeBase.MUSHROOM_ISLAND.id && aint[i] != BiomeBase.MUSHROOM_SHORE.id) {
                  aint2[i] = aint1[i] & 255;
               } else {
                  aint2[i] = BiomeBase.MUSHROOM_SHORE.id;
               }
            } else {
               aint2[i] = aint[i];
            }
         } else {
            aint2[i] = aint[i];
         }
      }

      return aint2;
   }
}
