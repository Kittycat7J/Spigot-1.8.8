package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Blocks;

public class BiomeStoneBeach extends BiomeBase {
   public BiomeStoneBeach(int p_i588_1_) {
      super(p_i588_1_);
      this.au.clear();
      this.ak = Blocks.STONE.getBlockData();
      this.al = Blocks.STONE.getBlockData();
      this.as.A = -999;
      this.as.D = 0;
      this.as.F = 0;
      this.as.G = 0;
   }
}
