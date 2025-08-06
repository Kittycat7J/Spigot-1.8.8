package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BlockFlowers;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChunkSnapshot;
import net.minecraft.server.v1_8_R3.EntitySlime;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;

public class BiomeSwamp extends BiomeBase {
   protected BiomeSwamp(int p_i589_1_) {
      super(p_i589_1_);
      this.as.A = 2;
      this.as.B = 1;
      this.as.D = 1;
      this.as.E = 8;
      this.as.F = 10;
      this.as.J = 1;
      this.as.z = 4;
      this.as.I = 0;
      this.as.H = 0;
      this.as.C = 5;
      this.ar = 14745518;
      this.at.add(new BiomeBase.BiomeMeta(EntitySlime.class, 1, 1, 1));
   }

   public WorldGenTreeAbstract a(Random p_a_1_) {
      return this.aC;
   }

   public BlockFlowers.EnumFlowerVarient a(Random p_a_1_, BlockPosition p_a_2_) {
      return BlockFlowers.EnumFlowerVarient.BLUE_ORCHID;
   }

   public void a(World p_a_1_, Random p_a_2_, ChunkSnapshot p_a_3_, int p_a_4_, int p_a_5_, double p_a_6_) {
      double d0 = af.a((double)p_a_4_ * 0.25D, (double)p_a_5_ * 0.25D);
      if(d0 > 0.0D) {
         int i = p_a_4_ & 15;
         int j = p_a_5_ & 15;

         for(int k = 255; k >= 0; --k) {
            if(p_a_3_.a(j, k, i).getBlock().getMaterial() != Material.AIR) {
               if(k == 62 && p_a_3_.a(j, k, i).getBlock() != Blocks.WATER) {
                  p_a_3_.a(j, k, i, Blocks.WATER.getBlockData());
                  if(d0 < 0.12D) {
                     p_a_3_.a(j, k + 1, i, Blocks.WATERLILY.getBlockData());
                  }
               }
               break;
            }
         }
      }

      this.b(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
   }
}
