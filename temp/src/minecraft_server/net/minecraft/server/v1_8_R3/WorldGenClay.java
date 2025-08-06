package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenClay extends WorldGenerator {
   private Block a = Blocks.CLAY;
   private int b;

   public WorldGenClay(int p_i695_1_) {
      this.b = p_i695_1_;
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      if(p_generate_1_.getType(p_generate_3_).getBlock().getMaterial() != Material.WATER) {
         return false;
      } else {
         int i = p_generate_2_.nextInt(this.b - 2) + 2;
         byte b0 = 1;

         for(int j = p_generate_3_.getX() - i; j <= p_generate_3_.getX() + i; ++j) {
            for(int k = p_generate_3_.getZ() - i; k <= p_generate_3_.getZ() + i; ++k) {
               int l = j - p_generate_3_.getX();
               int i1 = k - p_generate_3_.getZ();
               if(l * l + i1 * i1 <= i * i) {
                  for(int j1 = p_generate_3_.getY() - b0; j1 <= p_generate_3_.getY() + b0; ++j1) {
                     BlockPosition blockposition = new BlockPosition(j, j1, k);
                     Block block = p_generate_1_.getType(blockposition).getBlock();
                     if(block == Blocks.DIRT || block == Blocks.CLAY) {
                        p_generate_1_.setTypeAndData(blockposition, this.a.getBlockData(), 2);
                     }
                  }
               }
            }
         }

         return true;
      }
   }
}
