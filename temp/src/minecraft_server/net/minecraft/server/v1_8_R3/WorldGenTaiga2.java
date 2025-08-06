package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockLeaves;
import net.minecraft.server.v1_8_R3.BlockLeaves1;
import net.minecraft.server.v1_8_R3.BlockLog1;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;

public class WorldGenTaiga2 extends WorldGenTreeAbstract {
   private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.SPRUCE);
   private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.SPRUCE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

   public WorldGenTaiga2(boolean p_i710_1_) {
      super(p_i710_1_);
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      int i = p_generate_2_.nextInt(4) + 6;
      int j = 1 + p_generate_2_.nextInt(2);
      int k = i - j;
      int l = 2 + p_generate_2_.nextInt(2);
      boolean flag = true;
      if(p_generate_3_.getY() >= 1 && p_generate_3_.getY() + i + 1 <= 256) {
         for(int i1 = p_generate_3_.getY(); i1 <= p_generate_3_.getY() + 1 + i && flag; ++i1) {
            int j1 = 1;
            if(i1 - p_generate_3_.getY() < j) {
               j1 = 0;
            } else {
               j1 = l;
            }

            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

            for(int k1 = p_generate_3_.getX() - j1; k1 <= p_generate_3_.getX() + j1 && flag; ++k1) {
               for(int l1 = p_generate_3_.getZ() - j1; l1 <= p_generate_3_.getZ() + j1 && flag; ++l1) {
                  if(i1 >= 0 && i1 < 256) {
                     Block block = p_generate_1_.getType(blockposition$mutableblockposition.c(k1, i1, l1)).getBlock();
                     if(block.getMaterial() != Material.AIR && block.getMaterial() != Material.LEAVES) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         if(!flag) {
            return false;
         } else {
            Block block1 = p_generate_1_.getType(p_generate_3_.down()).getBlock();
            if((block1 == Blocks.GRASS || block1 == Blocks.DIRT || block1 == Blocks.FARMLAND) && p_generate_3_.getY() < 256 - i - 1) {
               this.a(p_generate_1_, p_generate_3_.down());
               int i3 = p_generate_2_.nextInt(2);
               int j3 = 1;
               byte b0 = 0;

               for(int k3 = 0; k3 <= k; ++k3) {
                  int i4 = p_generate_3_.getY() + i - k3;

                  for(int i2 = p_generate_3_.getX() - i3; i2 <= p_generate_3_.getX() + i3; ++i2) {
                     int j2 = i2 - p_generate_3_.getX();

                     for(int k2 = p_generate_3_.getZ() - i3; k2 <= p_generate_3_.getZ() + i3; ++k2) {
                        int l2 = k2 - p_generate_3_.getZ();
                        if(Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0) {
                           BlockPosition blockposition = new BlockPosition(i2, i4, k2);
                           if(!p_generate_1_.getType(blockposition).getBlock().o()) {
                              this.a(p_generate_1_, blockposition, b);
                           }
                        }
                     }
                  }

                  if(i3 >= j3) {
                     i3 = b0;
                     b0 = 1;
                     ++j3;
                     if(j3 > l) {
                        j3 = l;
                     }
                  } else {
                     ++i3;
                  }
               }

               int l3 = p_generate_2_.nextInt(3);

               for(int j4 = 0; j4 < i - l3; ++j4) {
                  Block block2 = p_generate_1_.getType(p_generate_3_.up(j4)).getBlock();
                  if(block2.getMaterial() == Material.AIR || block2.getMaterial() == Material.LEAVES) {
                     this.a(p_generate_1_, p_generate_3_.up(j4), a);
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }
}
