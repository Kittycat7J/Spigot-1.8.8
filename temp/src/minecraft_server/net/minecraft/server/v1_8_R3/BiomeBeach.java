package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Blocks;

public class BiomeBeach extends BiomeBase {
   public BiomeBeach(int p_i562_1_) {
      super(p_i562_1_);
      this.au.clear();
      this.ak = Blocks.SAND.getBlockData();
      this.al = Blocks.SAND.getBlockData();
      this.as.A = -999;
      this.as.D = 0;
      this.as.F = 0;
      this.as.G = 0;
   }
}
