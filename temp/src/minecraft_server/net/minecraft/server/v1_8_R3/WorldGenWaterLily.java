package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenWaterLily extends WorldGenerator {
   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      for(int i = 0; i < 10; ++i) {
         int j = p_generate_3_.getX() + p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8);
         int k = p_generate_3_.getY() + p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4);
         int l = p_generate_3_.getZ() + p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8);
         if(p_generate_1_.isEmpty(new BlockPosition(j, k, l)) && Blocks.WATERLILY.canPlace(p_generate_1_, new BlockPosition(j, k, l))) {
            p_generate_1_.setTypeAndData(new BlockPosition(j, k, l), Blocks.WATERLILY.getBlockData(), 2);
         }
      }

      return true;
   }
}
