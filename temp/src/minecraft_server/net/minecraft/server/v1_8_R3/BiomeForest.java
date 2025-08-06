package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BiomeBaseSub;
import net.minecraft.server.v1_8_R3.BlockFlowers;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockTallPlant;
import net.minecraft.server.v1_8_R3.EntityWolf;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenForest;
import net.minecraft.server.v1_8_R3.WorldGenForestTree;
import net.minecraft.server.v1_8_R3.WorldGenHugeMushroom;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;

public class BiomeForest extends BiomeBase {
   private int aG;
   protected static final WorldGenForest aD = new WorldGenForest(false, true);
   protected static final WorldGenForest aE = new WorldGenForest(false, false);
   protected static final WorldGenForestTree aF = new WorldGenForestTree(false);

   public BiomeForest(int p_i576_1_, int p_i576_2_) {
      super(p_i576_1_);
      this.aG = p_i576_2_;
      this.as.A = 10;
      this.as.C = 2;
      if(this.aG == 1) {
         this.as.A = 6;
         this.as.B = 100;
         this.as.C = 1;
      }

      this.a(5159473);
      this.a(0.7F, 0.8F);
      if(this.aG == 2) {
         this.aj = 353825;
         this.ai = 3175492;
         this.a(0.6F, 0.6F);
      }

      if(this.aG == 0) {
         this.au.add(new BiomeBase.BiomeMeta(EntityWolf.class, 5, 4, 4));
      }

      if(this.aG == 3) {
         this.as.A = -999;
      }

   }

   protected BiomeBase a(int p_a_1_, boolean p_a_2_) {
      if(this.aG == 2) {
         this.aj = 353825;
         this.ai = p_a_1_;
         if(p_a_2_) {
            this.aj = (this.aj & 16711422) >> 1;
         }

         return this;
      } else {
         return super.a(p_a_1_, p_a_2_);
      }
   }

   public WorldGenTreeAbstract a(Random p_a_1_) {
      return (WorldGenTreeAbstract)(this.aG == 3 && p_a_1_.nextInt(3) > 0?aF:(this.aG != 2 && p_a_1_.nextInt(5) != 0?this.aA:aE));
   }

   public BlockFlowers.EnumFlowerVarient a(Random p_a_1_, BlockPosition p_a_2_) {
      if(this.aG == 1) {
         double d0 = MathHelper.a((1.0D + af.a((double)p_a_2_.getX() / 48.0D, (double)p_a_2_.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
         BlockFlowers.EnumFlowerVarient blockflowers$enumflowervarient = BlockFlowers.EnumFlowerVarient.values()[(int)(d0 * (double)BlockFlowers.EnumFlowerVarient.values().length)];
         return blockflowers$enumflowervarient == BlockFlowers.EnumFlowerVarient.BLUE_ORCHID?BlockFlowers.EnumFlowerVarient.POPPY:blockflowers$enumflowervarient;
      } else {
         return super.a(p_a_1_, p_a_2_);
      }
   }

   public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_) {
      if(this.aG == 3) {
         for(int i = 0; i < 4; ++i) {
            for(int j = 0; j < 4; ++j) {
               int k = i * 4 + 1 + 8 + p_a_2_.nextInt(3);
               int l = j * 4 + 1 + 8 + p_a_2_.nextInt(3);
               BlockPosition blockposition = p_a_1_.getHighestBlockYAt(p_a_3_.a(k, 0, l));
               if(p_a_2_.nextInt(20) == 0) {
                  WorldGenHugeMushroom worldgenhugemushroom = new WorldGenHugeMushroom();
                  worldgenhugemushroom.generate(p_a_1_, p_a_2_, blockposition);
               } else {
                  WorldGenTreeAbstract worldgentreeabstract = this.a(p_a_2_);
                  worldgentreeabstract.e();
                  if(worldgentreeabstract.generate(p_a_1_, p_a_2_, blockposition)) {
                     worldgentreeabstract.a(p_a_1_, p_a_2_, blockposition);
                  }
               }
            }
         }
      }

      int j1 = p_a_2_.nextInt(5) - 3;
      if(this.aG == 1) {
         j1 += 2;
      }

      for(int k1 = 0; k1 < j1; ++k1) {
         int l1 = p_a_2_.nextInt(3);
         if(l1 == 0) {
            ag.a(BlockTallPlant.EnumTallFlowerVariants.SYRINGA);
         } else if(l1 == 1) {
            ag.a(BlockTallPlant.EnumTallFlowerVariants.ROSE);
         } else if(l1 == 2) {
            ag.a(BlockTallPlant.EnumTallFlowerVariants.PAEONIA);
         }

         for(int i2 = 0; i2 < 5; ++i2) {
            int j2 = p_a_2_.nextInt(16) + 8;
            int k2 = p_a_2_.nextInt(16) + 8;
            int i1 = p_a_2_.nextInt(p_a_1_.getHighestBlockYAt(p_a_3_.a(j2, 0, k2)).getY() + 32);
            if(ag.generate(p_a_1_, p_a_2_, new BlockPosition(p_a_3_.getX() + j2, i1, p_a_3_.getZ() + k2))) {
               break;
            }
         }
      }

      super.a(p_a_1_, p_a_2_, p_a_3_);
   }

   protected BiomeBase d(final int p_d_1_) {
      if(this.id == BiomeBase.FOREST.id) {
         BiomeForest biomeforest = new BiomeForest(p_d_1_, 1);
         biomeforest.a(new BiomeBase.BiomeTemperature(this.an, this.ao + 0.2F));
         biomeforest.a("Flower Forest");
         biomeforest.a(6976549, true);
         biomeforest.a(8233509);
         return biomeforest;
      } else {
         return this.id != BiomeBase.BIRCH_FOREST.id && this.id != BiomeBase.BIRCH_FOREST_HILLS.id?new BiomeBaseSub(p_d_1_, this) {
            public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_) {
               this.aE.a(p_a_1_, p_a_2_, p_a_3_);
            }
         }:new BiomeBaseSub(p_d_1_, this) {
            public WorldGenTreeAbstract a(Random p_a_1_) {
               return p_a_1_.nextBoolean()?BiomeForest.aD:BiomeForest.aE;
            }
         };
      }
   }
}
