package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenFire extends WorldGenerator {
   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      for(int i = 0; i < 64; ++i) {
         BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8), p_generate_2_.nextInt(4) - p_generate_2_.nextInt(4), p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8));
         if(p_generate_1_.isEmpty(blockposition) && p_generate_1_.getType(blockposition.down()).getBlock() == Blocks.NETHERRACK) {
            p_generate_1_.setTypeAndData(blockposition, Blocks.FIRE.getBlockData(), 2);
         }
      }

      return true;
   }
}
