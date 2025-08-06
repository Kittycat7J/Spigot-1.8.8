package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.GenLayer;
import net.minecraft.server.v1_8_R3.GenLayerZoom;

public class GenLayerZoomFuzzy extends GenLayerZoom {
   public GenLayerZoomFuzzy(long p_i817_1_, GenLayer p_i817_3_) {
      super(p_i817_1_, p_i817_3_);
   }

   protected int b(int p_b_1_, int p_b_2_, int p_b_3_, int p_b_4_) {
      return this.a(new int[]{p_b_1_, p_b_2_, p_b_3_, p_b_4_});
   }
}
