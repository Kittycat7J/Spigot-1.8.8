package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;

public abstract class WorldGenMegaTreeAbstract extends WorldGenTreeAbstract {
   protected final int a;
   protected final IBlockData b;
   protected final IBlockData c;
   protected int d;

   public WorldGenMegaTreeAbstract(boolean p_i399_1_, int p_i399_2_, int p_i399_3_, IBlockData p_i399_4_, IBlockData p_i399_5_) {
      super(p_i399_1_);
      this.a = p_i399_2_;
      this.d = p_i399_3_;
      this.b = p_i399_4_;
      this.c = p_i399_5_;
   }

   protected int a(Random p_a_1_) {
      int i = p_a_1_.nextInt(3) + this.a;
      if(this.d > 1) {
         i += p_a_1_.nextInt(this.d);
      }

      return i;
   }

   private boolean c(World p_c_1_, BlockPosition p_c_2_, int p_c_3_) {
      boolean flag = true;
      if(p_c_2_.getY() >= 1 && p_c_2_.getY() + p_c_3_ + 1 <= 256) {
         for(int i = 0; i <= 1 + p_c_3_; ++i) {
            byte b0 = 2;
            if(i == 0) {
               b0 = 1;
            } else if(i >= 1 + p_c_3_ - 2) {
               b0 = 2;
            }

            for(int j = -b0; j <= b0 && flag; ++j) {
               for(int k = -b0; k <= b0 && flag; ++k) {
                  if(p_c_2_.getY() + i < 0 || p_c_2_.getY() + i >= 256 || !this.a(p_c_1_.getType(p_c_2_.a(j, i, k)).getBlock()) && p_c_1_.getType(p_c_2_.a(j, i, k)).getBlock() != Blocks.SAPLING) {
                     flag = false;
                  }
               }
            }
         }

         return flag;
      } else {
         return false;
      }
   }

   private boolean a(BlockPosition p_a_1_, World p_a_2_) {
      BlockPosition blockposition = p_a_1_.down();
      Block block = p_a_2_.getType(blockposition).getBlock();
      if((block == Blocks.GRASS || block == Blocks.DIRT) && p_a_1_.getY() >= 2) {
         this.a(p_a_2_, blockposition);
         this.a(p_a_2_, blockposition.east());
         this.a(p_a_2_, blockposition.south());
         this.a(p_a_2_, blockposition.south().east());
         return true;
      } else {
         return false;
      }
   }

   protected boolean a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_, int p_a_4_) {
      return this.c(p_a_1_, p_a_3_, p_a_4_) && this.a(p_a_3_, p_a_1_);
   }

   protected void a(World p_a_1_, BlockPosition p_a_2_, int p_a_3_) {
      int i = p_a_3_ * p_a_3_;

      for(int j = -p_a_3_; j <= p_a_3_ + 1; ++j) {
         for(int k = -p_a_3_; k <= p_a_3_ + 1; ++k) {
            int l = j - 1;
            int i1 = k - 1;
            if(j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i) {
               BlockPosition blockposition = p_a_2_.a(j, 0, k);
               Material material = p_a_1_.getType(blockposition).getBlock().getMaterial();
               if(material == Material.AIR || material == Material.LEAVES) {
                  this.a(p_a_1_, blockposition, this.c);
               }
            }
         }
      }

   }

   protected void b(World p_b_1_, BlockPosition p_b_2_, int p_b_3_) {
      int i = p_b_3_ * p_b_3_;

      for(int j = -p_b_3_; j <= p_b_3_; ++j) {
         for(int k = -p_b_3_; k <= p_b_3_; ++k) {
            if(j * j + k * k <= i) {
               BlockPosition blockposition = p_b_2_.a(j, 0, k);
               Material material = p_b_1_.getType(blockposition).getBlock().getMaterial();
               if(material == Material.AIR || material == Material.LEAVES) {
                  this.a(p_b_1_, blockposition, this.c);
               }
            }
         }
      }

   }
}
