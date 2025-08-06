package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockCocoa;
import net.minecraft.server.v1_8_R3.BlockLeaves;
import net.minecraft.server.v1_8_R3.BlockLeaves1;
import net.minecraft.server.v1_8_R3.BlockLog1;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockVine;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;

public class WorldGenTrees extends WorldGenTreeAbstract {
   private static final IBlockData a = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.OAK);
   private static final IBlockData b = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.OAK).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
   private final int c;
   private final boolean d;
   private final IBlockData e;
   private final IBlockData f;

   public WorldGenTrees(boolean p_i712_1_) {
      this(p_i712_1_, 4, a, b, false);
   }

   public WorldGenTrees(boolean p_i713_1_, int p_i713_2_, IBlockData p_i713_3_, IBlockData p_i713_4_, boolean p_i713_5_) {
      super(p_i713_1_);
      this.c = p_i713_2_;
      this.e = p_i713_3_;
      this.f = p_i713_4_;
      this.d = p_i713_5_;
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      int i = p_generate_2_.nextInt(3) + this.c;
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
            Block block1 = p_generate_1_.getType(p_generate_3_.down()).getBlock();
            if((block1 == Blocks.GRASS || block1 == Blocks.DIRT || block1 == Blocks.FARMLAND) && p_generate_3_.getY() < 256 - i - 1) {
               this.a(p_generate_1_, p_generate_3_.down());
               byte b1 = 3;
               byte b2 = 0;

               for(int j2 = p_generate_3_.getY() - b1 + i; j2 <= p_generate_3_.getY() + i; ++j2) {
                  int j3 = j2 - (p_generate_3_.getY() + i);
                  int i1 = b2 + 1 - j3 / 2;

                  for(int j1 = p_generate_3_.getX() - i1; j1 <= p_generate_3_.getX() + i1; ++j1) {
                     int k1 = j1 - p_generate_3_.getX();

                     for(int l1 = p_generate_3_.getZ() - i1; l1 <= p_generate_3_.getZ() + i1; ++l1) {
                        int i2 = l1 - p_generate_3_.getZ();
                        if(Math.abs(k1) != i1 || Math.abs(i2) != i1 || p_generate_2_.nextInt(2) != 0 && j3 != 0) {
                           BlockPosition blockposition = new BlockPosition(j1, j2, l1);
                           Block block = p_generate_1_.getType(blockposition).getBlock();
                           if(block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES || block.getMaterial() == Material.REPLACEABLE_PLANT) {
                              this.a(p_generate_1_, blockposition, this.f);
                           }
                        }
                     }
                  }
               }

               for(int k2 = 0; k2 < i; ++k2) {
                  Block block2 = p_generate_1_.getType(p_generate_3_.up(k2)).getBlock();
                  if(block2.getMaterial() == Material.AIR || block2.getMaterial() == Material.LEAVES || block2.getMaterial() == Material.REPLACEABLE_PLANT) {
                     this.a(p_generate_1_, p_generate_3_.up(k2), this.e);
                     if(this.d && k2 > 0) {
                        if(p_generate_2_.nextInt(3) > 0 && p_generate_1_.isEmpty(p_generate_3_.a(-1, k2, 0))) {
                           this.a(p_generate_1_, p_generate_3_.a(-1, k2, 0), BlockVine.EAST);
                        }

                        if(p_generate_2_.nextInt(3) > 0 && p_generate_1_.isEmpty(p_generate_3_.a(1, k2, 0))) {
                           this.a(p_generate_1_, p_generate_3_.a(1, k2, 0), BlockVine.WEST);
                        }

                        if(p_generate_2_.nextInt(3) > 0 && p_generate_1_.isEmpty(p_generate_3_.a(0, k2, -1))) {
                           this.a(p_generate_1_, p_generate_3_.a(0, k2, -1), BlockVine.SOUTH);
                        }

                        if(p_generate_2_.nextInt(3) > 0 && p_generate_1_.isEmpty(p_generate_3_.a(0, k2, 1))) {
                           this.a(p_generate_1_, p_generate_3_.a(0, k2, 1), BlockVine.NORTH);
                        }
                     }
                  }
               }

               if(this.d) {
                  for(int l2 = p_generate_3_.getY() - 3 + i; l2 <= p_generate_3_.getY() + i; ++l2) {
                     int k3 = l2 - (p_generate_3_.getY() + i);
                     int l3 = 2 - k3 / 2;
                     BlockPosition.MutableBlockPosition blockposition$mutableblockposition1 = new BlockPosition.MutableBlockPosition();

                     for(int i4 = p_generate_3_.getX() - l3; i4 <= p_generate_3_.getX() + l3; ++i4) {
                        for(int j4 = p_generate_3_.getZ() - l3; j4 <= p_generate_3_.getZ() + l3; ++j4) {
                           blockposition$mutableblockposition1.c(i4, l2, j4);
                           if(p_generate_1_.getType(blockposition$mutableblockposition1).getBlock().getMaterial() == Material.LEAVES) {
                              BlockPosition blockposition2 = blockposition$mutableblockposition1.west();
                              BlockPosition blockposition3 = blockposition$mutableblockposition1.east();
                              BlockPosition blockposition4 = blockposition$mutableblockposition1.north();
                              BlockPosition blockposition1 = blockposition$mutableblockposition1.south();
                              if(p_generate_2_.nextInt(4) == 0 && p_generate_1_.getType(blockposition2).getBlock().getMaterial() == Material.AIR) {
                                 this.b(p_generate_1_, blockposition2, BlockVine.EAST);
                              }

                              if(p_generate_2_.nextInt(4) == 0 && p_generate_1_.getType(blockposition3).getBlock().getMaterial() == Material.AIR) {
                                 this.b(p_generate_1_, blockposition3, BlockVine.WEST);
                              }

                              if(p_generate_2_.nextInt(4) == 0 && p_generate_1_.getType(blockposition4).getBlock().getMaterial() == Material.AIR) {
                                 this.b(p_generate_1_, blockposition4, BlockVine.SOUTH);
                              }

                              if(p_generate_2_.nextInt(4) == 0 && p_generate_1_.getType(blockposition1).getBlock().getMaterial() == Material.AIR) {
                                 this.b(p_generate_1_, blockposition1, BlockVine.NORTH);
                              }
                           }
                        }
                     }
                  }

                  if(p_generate_2_.nextInt(5) == 0 && i > 5) {
                     for(int i3 = 0; i3 < 2; ++i3) {
                        for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
                           if(p_generate_2_.nextInt(4 - i3) == 0) {
                              EnumDirection enumdirection1 = enumdirection.opposite();
                              this.a(p_generate_1_, p_generate_2_.nextInt(3), p_generate_3_.a(enumdirection1.getAdjacentX(), i - 5 + i3, enumdirection1.getAdjacentZ()), enumdirection);
                           }
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

   private void a(World p_a_1_, int p_a_2_, BlockPosition p_a_3_, EnumDirection p_a_4_) {
      this.a(p_a_1_, p_a_3_, Blocks.COCOA.getBlockData().set(BlockCocoa.AGE, Integer.valueOf(p_a_2_)).set(BlockCocoa.FACING, p_a_4_));
   }

   private void a(World p_a_1_, BlockPosition p_a_2_, BlockStateBoolean p_a_3_) {
      this.a(p_a_1_, p_a_2_, Blocks.VINE.getBlockData().set(p_a_3_, Boolean.valueOf(true)));
   }

   private void b(World p_b_1_, BlockPosition p_b_2_, BlockStateBoolean p_b_3_) {
      this.a(p_b_1_, p_b_2_, p_b_3_);
      int i = 4;

      for(p_b_2_ = p_b_2_.down(); p_b_1_.getType(p_b_2_).getBlock().getMaterial() == Material.AIR && i > 0; --i) {
         this.a(p_b_1_, p_b_2_, p_b_3_);
         p_b_2_ = p_b_2_.down();
      }

   }
}
