package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenEnder extends WorldGenerator {
   private Block a;

   public WorldGenEnder(Block p_i708_1_) {
      this.a = p_i708_1_;
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      if(p_generate_1_.isEmpty(p_generate_3_) && p_generate_1_.getType(p_generate_3_.down()).getBlock() == this.a) {
         int i = p_generate_2_.nextInt(32) + 6;
         int j = p_generate_2_.nextInt(4) + 1;
         BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

         for(int k = p_generate_3_.getX() - j; k <= p_generate_3_.getX() + j; ++k) {
            for(int l = p_generate_3_.getZ() - j; l <= p_generate_3_.getZ() + j; ++l) {
               int i1 = k - p_generate_3_.getX();
               int j1 = l - p_generate_3_.getZ();
               if(i1 * i1 + j1 * j1 <= j * j + 1 && p_generate_1_.getType(blockposition$mutableblockposition.c(k, p_generate_3_.getY() - 1, l)).getBlock() != this.a) {
                  return false;
               }
            }
         }

         for(int l1 = p_generate_3_.getY(); l1 < p_generate_3_.getY() + i && l1 < 256; ++l1) {
            for(int i2 = p_generate_3_.getX() - j; i2 <= p_generate_3_.getX() + j; ++i2) {
               for(int j2 = p_generate_3_.getZ() - j; j2 <= p_generate_3_.getZ() + j; ++j2) {
                  int k2 = i2 - p_generate_3_.getX();
                  int k1 = j2 - p_generate_3_.getZ();
                  if(k2 * k2 + k1 * k1 <= j * j + 1) {
                     p_generate_1_.setTypeAndData(new BlockPosition(i2, l1, j2), Blocks.OBSIDIAN.getBlockData(), 2);
                  }
               }
            }
         }

         EntityEnderCrystal entityendercrystal = new EntityEnderCrystal(p_generate_1_);
         entityendercrystal.setPositionRotation((double)((float)p_generate_3_.getX() + 0.5F), (double)(p_generate_3_.getY() + i), (double)((float)p_generate_3_.getZ() + 0.5F), p_generate_2_.nextFloat() * 360.0F, 0.0F);
         p_generate_1_.addEntity(entityendercrystal);
         p_generate_1_.setTypeAndData(p_generate_3_.up(i), Blocks.BEDROCK.getBlockData(), 2);
         return true;
      } else {
         return false;
      }
   }
}
