package net.minecraft.server.v1_8_R3;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.WorldChunkManager;

public class WorldChunkManagerHell extends WorldChunkManager {
   private BiomeBase b;
   private float c;

   public WorldChunkManagerHell(BiomeBase p_i573_1_, float p_i573_2_) {
      this.b = p_i573_1_;
      this.c = p_i573_2_;
   }

   public BiomeBase getBiome(BlockPosition p_getBiome_1_) {
      return this.b;
   }

   public BiomeBase[] getBiomes(BiomeBase[] p_getBiomes_1_, int p_getBiomes_2_, int p_getBiomes_3_, int p_getBiomes_4_, int p_getBiomes_5_) {
      if(p_getBiomes_1_ == null || p_getBiomes_1_.length < p_getBiomes_4_ * p_getBiomes_5_) {
         p_getBiomes_1_ = new BiomeBase[p_getBiomes_4_ * p_getBiomes_5_];
      }

      Arrays.fill(p_getBiomes_1_, 0, p_getBiomes_4_ * p_getBiomes_5_, this.b);
      return p_getBiomes_1_;
   }

   public float[] getWetness(float[] p_getWetness_1_, int p_getWetness_2_, int p_getWetness_3_, int p_getWetness_4_, int p_getWetness_5_) {
      if(p_getWetness_1_ == null || p_getWetness_1_.length < p_getWetness_4_ * p_getWetness_5_) {
         p_getWetness_1_ = new float[p_getWetness_4_ * p_getWetness_5_];
      }

      Arrays.fill(p_getWetness_1_, 0, p_getWetness_4_ * p_getWetness_5_, this.c);
      return p_getWetness_1_;
   }

   public BiomeBase[] getBiomeBlock(BiomeBase[] p_getBiomeBlock_1_, int p_getBiomeBlock_2_, int p_getBiomeBlock_3_, int p_getBiomeBlock_4_, int p_getBiomeBlock_5_) {
      if(p_getBiomeBlock_1_ == null || p_getBiomeBlock_1_.length < p_getBiomeBlock_4_ * p_getBiomeBlock_5_) {
         p_getBiomeBlock_1_ = new BiomeBase[p_getBiomeBlock_4_ * p_getBiomeBlock_5_];
      }

      Arrays.fill(p_getBiomeBlock_1_, 0, p_getBiomeBlock_4_ * p_getBiomeBlock_5_, this.b);
      return p_getBiomeBlock_1_;
   }

   public BiomeBase[] a(BiomeBase[] p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, boolean p_a_6_) {
      return this.getBiomeBlock(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);
   }

   public BlockPosition a(int p_a_1_, int p_a_2_, int p_a_3_, List<BiomeBase> p_a_4_, Random p_a_5_) {
      return p_a_4_.contains(this.b)?new BlockPosition(p_a_1_ - p_a_3_ + p_a_5_.nextInt(p_a_3_ * 2 + 1), 0, p_a_2_ - p_a_3_ + p_a_5_.nextInt(p_a_3_ * 2 + 1)):null;
   }

   public boolean a(int p_a_1_, int p_a_2_, int p_a_3_, List<BiomeBase> p_a_4_) {
      return p_a_4_.contains(this.b);
   }
}
