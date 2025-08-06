package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenLightStone2 extends WorldGenerator {
   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      if(!p_generate_1_.isEmpty(p_generate_3_)) {
         return false;
      } else if(p_generate_1_.getType(p_generate_3_.up()).getBlock() != Blocks.NETHERRACK) {
         return false;
      } else {
         p_generate_1_.setTypeAndData(p_generate_3_, Blocks.GLOWSTONE.getBlockData(), 2);

         for(int i = 0; i < 1500; ++i) {
            BlockPosition blockposition = p_generate_3_.a(p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8), -p_generate_2_.nextInt(12), p_generate_2_.nextInt(8) - p_generate_2_.nextInt(8));
            if(p_generate_1_.getType(blockposition).getBlock().getMaterial() == Material.AIR) {
               int j = 0;

               for(EnumDirection enumdirection : EnumDirection.values()) {
                  if(p_generate_1_.getType(blockposition.shift(enumdirection)).getBlock() == Blocks.GLOWSTONE) {
                     ++j;
                  }

                  if(j > 1) {
                     break;
                  }
               }

               if(j == 1) {
                  p_generate_1_.setTypeAndData(blockposition, Blocks.GLOWSTONE.getBlockData(), 2);
               }
            }
         }

         return true;
      }
   }
}
