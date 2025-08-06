package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicates;
import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDoubleStepAbstract;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockSand;
import net.minecraft.server.v1_8_R3.BlockStatePredicate;
import net.minecraft.server.v1_8_R3.BlockStepAbstract;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenDesertWell extends WorldGenerator {
   private static final BlockStatePredicate a = BlockStatePredicate.a((Block)Blocks.SAND).a(BlockSand.VARIANT, Predicates.<V>equalTo(BlockSand.EnumSandVariant.SAND));
   private final IBlockData b = Blocks.STONE_SLAB.getBlockData().set(BlockDoubleStepAbstract.VARIANT, BlockDoubleStepAbstract.EnumStoneSlabVariant.SAND).set(BlockStepAbstract.HALF, BlockStepAbstract.EnumSlabHalf.BOTTOM);
   private final IBlockData c = Blocks.SANDSTONE.getBlockData();
   private final IBlockData d = Blocks.FLOWING_WATER.getBlockData();

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      while(p_generate_1_.isEmpty(p_generate_3_) && p_generate_3_.getY() > 2) {
         p_generate_3_ = p_generate_3_.down();
      }

      if(!a.a(p_generate_1_.getType(p_generate_3_))) {
         return false;
      } else {
         for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
               if(p_generate_1_.isEmpty(p_generate_3_.a(i, -1, j)) && p_generate_1_.isEmpty(p_generate_3_.a(i, -2, j))) {
                  return false;
               }
            }
         }

         for(int l = -1; l <= 0; ++l) {
            for(int l1 = -2; l1 <= 2; ++l1) {
               for(int k = -2; k <= 2; ++k) {
                  p_generate_1_.setTypeAndData(p_generate_3_.a(l1, l, k), this.c, 2);
               }
            }
         }

         p_generate_1_.setTypeAndData(p_generate_3_, this.d, 2);

         for(EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL) {
            p_generate_1_.setTypeAndData(p_generate_3_.shift(enumdirection), this.d, 2);
         }

         for(int i1 = -2; i1 <= 2; ++i1) {
            for(int i2 = -2; i2 <= 2; ++i2) {
               if(i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2) {
                  p_generate_1_.setTypeAndData(p_generate_3_.a(i1, 1, i2), this.c, 2);
               }
            }
         }

         p_generate_1_.setTypeAndData(p_generate_3_.a(2, 1, 0), this.b, 2);
         p_generate_1_.setTypeAndData(p_generate_3_.a(-2, 1, 0), this.b, 2);
         p_generate_1_.setTypeAndData(p_generate_3_.a(0, 1, 2), this.b, 2);
         p_generate_1_.setTypeAndData(p_generate_3_.a(0, 1, -2), this.b, 2);

         for(int j1 = -1; j1 <= 1; ++j1) {
            for(int j2 = -1; j2 <= 1; ++j2) {
               if(j1 == 0 && j2 == 0) {
                  p_generate_1_.setTypeAndData(p_generate_3_.a(j1, 4, j2), this.c, 2);
               } else {
                  p_generate_1_.setTypeAndData(p_generate_3_.a(j1, 4, j2), this.b, 2);
               }
            }
         }

         for(int k1 = 1; k1 <= 3; ++k1) {
            p_generate_1_.setTypeAndData(p_generate_3_.a(-1, k1, -1), this.c, 2);
            p_generate_1_.setTypeAndData(p_generate_3_.a(-1, k1, 1), this.c, 2);
            p_generate_1_.setTypeAndData(p_generate_3_.a(1, k1, -1), this.c, 2);
            p_generate_1_.setTypeAndData(p_generate_3_.a(1, k1, 1), this.c, 2);
         }

         return true;
      }
   }
}
