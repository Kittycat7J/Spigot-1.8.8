package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockPredicate;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class WorldGenMinable extends WorldGenerator {
   private final IBlockData a;
   private final int b;
   private final Predicate<IBlockData> c;

   public WorldGenMinable(IBlockData p_i704_1_, int p_i704_2_) {
      this(p_i704_1_, p_i704_2_, BlockPredicate.a(Blocks.STONE));
   }

   public WorldGenMinable(IBlockData p_i705_1_, int p_i705_2_, Predicate<IBlockData> p_i705_3_) {
      this.a = p_i705_1_;
      this.b = p_i705_2_;
      this.c = p_i705_3_;
   }

   public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_) {
      float f = p_generate_2_.nextFloat() * 3.1415927F;
      double d0 = (double)((float)(p_generate_3_.getX() + 8) + MathHelper.sin(f) * (float)this.b / 8.0F);
      double d1 = (double)((float)(p_generate_3_.getX() + 8) - MathHelper.sin(f) * (float)this.b / 8.0F);
      double d2 = (double)((float)(p_generate_3_.getZ() + 8) + MathHelper.cos(f) * (float)this.b / 8.0F);
      double d3 = (double)((float)(p_generate_3_.getZ() + 8) - MathHelper.cos(f) * (float)this.b / 8.0F);
      double d4 = (double)(p_generate_3_.getY() + p_generate_2_.nextInt(3) - 2);
      double d5 = (double)(p_generate_3_.getY() + p_generate_2_.nextInt(3) - 2);

      for(int i = 0; i < this.b; ++i) {
         float f1 = (float)i / (float)this.b;
         double d6 = d0 + (d1 - d0) * (double)f1;
         double d7 = d4 + (d5 - d4) * (double)f1;
         double d8 = d2 + (d3 - d2) * (double)f1;
         double d9 = p_generate_2_.nextDouble() * (double)this.b / 16.0D;
         double d10 = (double)(MathHelper.sin(3.1415927F * f1) + 1.0F) * d9 + 1.0D;
         double d11 = (double)(MathHelper.sin(3.1415927F * f1) + 1.0F) * d9 + 1.0D;
         int j = MathHelper.floor(d6 - d10 / 2.0D);
         int k = MathHelper.floor(d7 - d11 / 2.0D);
         int l = MathHelper.floor(d8 - d10 / 2.0D);
         int i1 = MathHelper.floor(d6 + d10 / 2.0D);
         int j1 = MathHelper.floor(d7 + d11 / 2.0D);
         int k1 = MathHelper.floor(d8 + d10 / 2.0D);

         for(int l1 = j; l1 <= i1; ++l1) {
            double d12 = ((double)l1 + 0.5D - d6) / (d10 / 2.0D);
            if(d12 * d12 < 1.0D) {
               for(int i2 = k; i2 <= j1; ++i2) {
                  double d13 = ((double)i2 + 0.5D - d7) / (d11 / 2.0D);
                  if(d12 * d12 + d13 * d13 < 1.0D) {
                     for(int j2 = l; j2 <= k1; ++j2) {
                        double d14 = ((double)j2 + 0.5D - d8) / (d10 / 2.0D);
                        if(d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
                           BlockPosition blockposition = new BlockPosition(l1, i2, j2);
                           if(this.c.apply(p_generate_1_.getType(blockposition))) {
                              p_generate_1_.setTypeAndData(blockposition, this.a, 2);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

      return true;
   }
}
