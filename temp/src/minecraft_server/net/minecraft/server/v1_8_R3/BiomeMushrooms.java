package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityMushroomCow;

public class BiomeMushrooms extends BiomeBase {
   public BiomeMushrooms(int p_i581_1_) {
      super(p_i581_1_);
      this.as.A = -100;
      this.as.B = -100;
      this.as.C = -100;
      this.as.E = 1;
      this.as.K = 1;
      this.ak = Blocks.MYCELIUM.getBlockData();
      this.at.clear();
      this.au.clear();
      this.av.clear();
      this.au.add(new BiomeBase.BiomeMeta(EntityMushroomCow.class, 8, 4, 8));
   }
}
