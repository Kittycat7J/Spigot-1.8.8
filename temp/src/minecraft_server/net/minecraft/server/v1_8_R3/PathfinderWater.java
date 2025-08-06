package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PathPoint;
import net.minecraft.server.v1_8_R3.PathfinderAbstract;

public class PathfinderWater extends PathfinderAbstract {
   public void a(IBlockAccess p_a_1_, Entity p_a_2_) {
      super.a(p_a_1_, p_a_2_);
   }

   public void a() {
      super.a();
   }

   public PathPoint a(Entity p_a_1_) {
      return this.a(MathHelper.floor(p_a_1_.getBoundingBox().a), MathHelper.floor(p_a_1_.getBoundingBox().b + 0.5D), MathHelper.floor(p_a_1_.getBoundingBox().c));
   }

   public PathPoint a(Entity p_a_1_, double p_a_2_, double p_a_4_, double p_a_6_) {
      return this.a(MathHelper.floor(p_a_2_ - (double)(p_a_1_.width / 2.0F)), MathHelper.floor(p_a_4_ + 0.5D), MathHelper.floor(p_a_6_ - (double)(p_a_1_.width / 2.0F)));
   }

   public int a(PathPoint[] p_a_1_, Entity p_a_2_, PathPoint p_a_3_, PathPoint p_a_4_, float p_a_5_) {
      int i = 0;

      for(EnumDirection enumdirection : EnumDirection.values()) {
         PathPoint pathpoint = this.a(p_a_2_, p_a_3_.a + enumdirection.getAdjacentX(), p_a_3_.b + enumdirection.getAdjacentY(), p_a_3_.c + enumdirection.getAdjacentZ());
         if(pathpoint != null && !pathpoint.i && pathpoint.a(p_a_4_) < p_a_5_) {
            p_a_1_[i++] = pathpoint;
         }
      }

      return i;
   }

   private PathPoint a(Entity p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      int i = this.b(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
      return i == -1?this.a(p_a_2_, p_a_3_, p_a_4_):null;
   }

   private int b(Entity p_b_1_, int p_b_2_, int p_b_3_, int p_b_4_) {
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int i = p_b_2_; i < p_b_2_ + this.c; ++i) {
         for(int j = p_b_3_; j < p_b_3_ + this.d; ++j) {
            for(int k = p_b_4_; k < p_b_4_ + this.e; ++k) {
               Block block = this.a.getType(blockposition$mutableblockposition.c(i, j, k)).getBlock();
               if(block.getMaterial() != Material.WATER) {
                  return 0;
               }
            }
         }
      }

      return -1;
   }
}
