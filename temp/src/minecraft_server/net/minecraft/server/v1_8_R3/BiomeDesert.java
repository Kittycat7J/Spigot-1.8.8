package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenDesertWell;

public class BiomeDesert extends BiomeBase {
   public BiomeDesert(int p_i571_1_) {
      super(p_i571_1_);
      this.au.clear();
      this.ak = Blocks.SAND.getBlockData();
      this.al = Blocks.SAND.getBlockData();
      this.as.A = -999;
      this.as.D = 2;
      this.as.F = 50;
      this.as.G = 10;
      this.au.clear();
   }

   public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_) {
      super.a(p_a_1_, p_a_2_, p_a_3_);
      if(p_a_2_.nextInt(1000) == 0) {
         int i = p_a_2_.nextInt(16) + 8;
         int j = p_a_2_.nextInt(16) + 8;
         BlockPosition blockposition = p_a_1_.getHighestBlockYAt(p_a_3_.a(i, 0, j)).up();
         (new WorldGenDesertWell()).generate(p_a_1_, p_a_2_, blockposition);
      }

   }
}
