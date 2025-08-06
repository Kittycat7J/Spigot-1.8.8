package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BlockMonsterEggs;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChunkSnapshot;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenMinable;
import net.minecraft.server.v1_8_R3.WorldGenTaiga2;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;
import net.minecraft.server.v1_8_R3.WorldGenerator;

public class BiomeBigHills extends BiomeBase {
   private WorldGenerator aD = new WorldGenMinable(Blocks.MONSTER_EGG.getBlockData().set(BlockMonsterEggs.VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.STONE), 9);
   private WorldGenTaiga2 aE = new WorldGenTaiga2(false);
   private int aF = 0;
   private int aG = 1;
   private int aH = 2;
   private int aI;

   protected BiomeBigHills(int p_i572_1_, boolean p_i572_2_) {
      super(p_i572_1_);
      this.aI = this.aF;
      if(p_i572_2_) {
         this.as.A = 3;
         this.aI = this.aG;
      }

   }

   public WorldGenTreeAbstract a(Random p_a_1_) {
      return (WorldGenTreeAbstract)(p_a_1_.nextInt(3) > 0?this.aE:super.a(p_a_1_));
   }

   public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_) {
      super.a(p_a_1_, p_a_2_, p_a_3_);
      int i = 3 + p_a_2_.nextInt(6);

      for(int j = 0; j < i; ++j) {
         int k = p_a_2_.nextInt(16);
         int l = p_a_2_.nextInt(28) + 4;
         int i1 = p_a_2_.nextInt(16);
         BlockPosition blockposition = p_a_3_.a(k, l, i1);
         if(p_a_1_.getType(blockposition).getBlock() == Blocks.STONE) {
            p_a_1_.setTypeAndData(blockposition, Blocks.EMERALD_ORE.getBlockData(), 2);
         }
      }

      for(i = 0; i < 7; ++i) {
         int j1 = p_a_2_.nextInt(16);
         int k1 = p_a_2_.nextInt(64);
         int l1 = p_a_2_.nextInt(16);
         this.aD.generate(p_a_1_, p_a_2_, p_a_3_.a(j1, k1, l1));
      }

   }

   public void a(World p_a_1_, Random p_a_2_, ChunkSnapshot p_a_3_, int p_a_4_, int p_a_5_, double p_a_6_) {
      this.ak = Blocks.GRASS.getBlockData();
      this.al = Blocks.DIRT.getBlockData();
      if((p_a_6_ < -1.0D || p_a_6_ > 2.0D) && this.aI == this.aH) {
         this.ak = Blocks.GRAVEL.getBlockData();
         this.al = Blocks.GRAVEL.getBlockData();
      } else if(p_a_6_ > 1.0D && this.aI != this.aG) {
         this.ak = Blocks.STONE.getBlockData();
         this.al = Blocks.STONE.getBlockData();
      }

      this.b(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
   }

   private BiomeBigHills b(BiomeBase p_b_1_) {
      this.aI = this.aH;
      this.a(p_b_1_.ai, true);
      this.a(p_b_1_.ah + " M");
      this.a(new BiomeBase.BiomeTemperature(p_b_1_.an, p_b_1_.ao));
      this.a(p_b_1_.temperature, p_b_1_.humidity);
      return this;
   }

   protected BiomeBase d(int p_d_1_) {
      return (new BiomeBigHills(p_d_1_, false)).b(this);
   }
}
