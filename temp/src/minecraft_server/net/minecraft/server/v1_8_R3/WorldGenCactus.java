package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenCactus extends WorldGenerator {
   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      for(int i = 0; i < 10; ++i) {
         BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8), p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8));
         if(p_generate_1_.isEmpty(blockposition)) {
            int j = 1 + p_generate_2_.nextInt(p_generate_2_.nextInt(3) + 1);

            for(int k = 0; k < j; ++k) {
               if(Blocks.CACTUS.e(p_generate_1_, blockposition)) {
                  p_generate_1_.setTypeAndData(blockposition.up(k), Blocks.CACTUS.getBlockData(), 2);
               }
            }
         }
      }

      return true;
   }
}
