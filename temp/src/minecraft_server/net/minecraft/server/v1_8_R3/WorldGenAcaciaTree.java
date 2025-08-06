package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockLeaves;
import net.minecraft.server.v1_8_R3.BlockLeaves2;
import net.minecraft.server.v1_8_R3.BlockLog2;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;

public class WorldGenAcaciaTree extends WorldGenTreeAbstract {
   private static final IBlockData a = Blocks.LOG2.getBlockData().set(BlockLog2.VARIANT, BlockWood.EnumLogVariant.ACACIA);
   private static final IBlockData b = Blocks.LEAVES2.getBlockData().set(BlockLeaves2.VARIANT, BlockWood.EnumLogVariant.ACACIA).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

   public WorldGenAcaciaTree(boolean p_i707_1_) {
      super(p_i707_1_);
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      int i = p_generate_2_.nextInt(3) + p_generate_2_.nextInt(3) + 5;
      boolean flag = true;
      if(p_generate_3_.getY() >= 1 && p_generate_3_.getY() + i + 1 <= 256) {
         for(int j = p_generate_3_.getY(); j <= p_generate_3_.getY() + 1 + i; ++j) {
            byte b0 = 1;
            if(j == p_generate_3_.getY()) {
               b0 = 0;
            }

            if(j >= p_generate_3_.getY() + 1 + i - 2) {
               b0 = 2;
            }

            BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

            for(int k = p_generate_3_.getX() - b0; k <= p_generate_3_.getX() + b0 && flag; ++k) {
               for(int l = p_generate_3_.getZ() - b0; l <= p_generate_3_.getZ() + b0 && flag; ++l) {
                  if(j >= 0 && j < 256) {
                     if(!this.a(p_generate_1_.getType(blockposition$mutableblockposition.c(k, j, l)).getBlock())) {
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
            Block block = p_generate_1_.getType(p_generate_3_.down()).getBlock();
            if((block == Blocks.GRASS || block == Blocks.DIRT) && p_generate_3_.getY() < 256 - i - 1) {
               this.a(p_generate_1_, p_generate_3_.down());
               EnumDirection enumdirection = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_generate_2_);
               int j2 = i - p_generate_2_.nextInt(4) - 1;
               int k2 = 3 - p_generate_2_.nextInt(3);
               int l2 = p_generate_3_.getX();
               int i1 = p_generate_3_.getZ();
               int j1 = 0;

               for(int k1 = 0; k1 < i; ++k1) {
                  int l1 = p_generate_3_.getY() + k1;
                  if(k1 >= j2 && k2 > 0) {
                     l2 += enumdirection.getAdjacentX();
                     i1 += enumdirection.getAdjacentZ();
                     --k2;
                  }

                  BlockPosition blockposition = new BlockPosition(l2, l1, i1);
                  Material material = p_generate_1_.getType(blockposition).getBlock().getMaterial();
                  if(material == Material.AIR || material == Material.LEAVES) {
                     this.b(p_generate_1_, blockposition);
                     j1 = l1;
                  }
               }

               BlockPosition blockposition2 = new BlockPosition(l2, j1, i1);

               for(int i3 = -3; i3 <= 3; ++i3) {
                  for(int l3 = -3; l3 <= 3; ++l3) {
                     if(Math.abs(i3) != 3 || Math.abs(l3) != 3) {
                        this.c(p_generate_1_, blockposition2.a(i3, 0, l3));
                     }
                  }
               }

               blockposition2 = blockposition2.up();

               for(int j3 = -1; j3 <= 1; ++j3) {
                  for(int i4 = -1; i4 <= 1; ++i4) {
                     this.c(p_generate_1_, blockposition2.a(j3, 0, i4));
                  }
               }

               this.c(p_generate_1_, blockposition2.east(2));
               this.c(p_generate_1_, blockposition2.west(2));
               this.c(p_generate_1_, blockposition2.south(2));
               this.c(p_generate_1_, blockposition2.north(2));
               l2 = p_generate_3_.getX();
               i1 = p_generate_3_.getZ();
               EnumDirection enumdirection1 = EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_generate_2_);
               if(enumdirection1 != enumdirection) {
                  int k3 = j2 - p_generate_2_.nextInt(2) - 1;
                  int j4 = 1 + p_generate_2_.nextInt(3);
                  j1 = 0;

                  for(int k4 = k3; k4 < i && j4 > 0; --j4) {
                     if(k4 >= 1) {
                        int i2 = p_generate_3_.getY() + k4;
                        l2 += enumdirection1.getAdjacentX();
                        i1 += enumdirection1.getAdjacentZ();
                        BlockPosition blockposition1 = new BlockPosition(l2, i2, i1);
                        Material material1 = p_generate_1_.getType(blockposition1).getBlock().getMaterial();
                        if(material1 == Material.AIR || material1 == Material.LEAVES) {
                           this.b(p_generate_1_, blockposition1);
                           j1 = i2;
                        }
                     }

                     ++k4;
                  }

                  if(j1 > 0) {
                     BlockPosition blockposition3 = new BlockPosition(l2, j1, i1);

                     for(int l4 = -2; l4 <= 2; ++l4) {
                        for(int j5 = -2; j5 <= 2; ++j5) {
                           if(Math.abs(l4) != 2 || Math.abs(j5) != 2) {
                              this.c(p_generate_1_, blockposition3.a(l4, 0, j5));
                           }
                        }
                     }

                     blockposition3 = blockposition3.up();

                     for(int i5 = -1; i5 <= 1; ++i5) {
                        for(int k5 = -1; k5 <= 1; ++k5) {
                           this.c(p_generate_1_, blockposition3.a(i5, 0, k5));
                        }
                     }
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

   private void b(World p_b_1_, BlockPosition p_b_2_) {
      this.a(p_b_1_, p_b_2_, a);
   }

   private void c(World p_c_1_, BlockPosition p_c_2_) {
      Material material = p_c_1_.getType(p_c_2_).getBlock().getMaterial();
      if(material == Material.AIR || material == Material.LEAVES) {
         this.a(p_c_1_, p_c_2_, b);
      }

   }
}
