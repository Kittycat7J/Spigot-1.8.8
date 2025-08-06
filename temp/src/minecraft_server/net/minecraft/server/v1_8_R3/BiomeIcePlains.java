package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenPackedIce1;
import net.minecraft.server.v1_8_R3.WorldGenPackedIce2;
import net.minecraft.server.v1_8_R3.WorldGenTaiga2;
import net.minecraft.server.v1_8_R3.WorldGenTreeAbstract;

public class BiomeIcePlains extends BiomeBase {
   private boolean aD;
   private WorldGenPackedIce2 aE = new WorldGenPackedIce2();
   private WorldGenPackedIce1 aF = new WorldGenPackedIce1(4);

   public BiomeIcePlains(int p_i578_1_, boolean p_i578_2_) {
      super(p_i578_1_);
      this.aD = p_i578_2_;
      if(p_i578_2_) {
         this.ak = Blocks.SNOW.getBlockData();
      }

      this.au.clear();
   }

   public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_) {
      if(this.aD) {
         for(int i = 0; i < 3; ++i) {
            int j = p_a_2_.nextInt(16) + 8;
            int k = p_a_2_.nextInt(16) + 8;
            this.aE.generate(p_a_1_, p_a_2_, p_a_1_.getHighestBlockYAt(p_a_3_.a(j, 0, k)));
         }

         for(int l = 0; l < 2; ++l) {
            int i1 = p_a_2_.nextInt(16) + 8;
            int j1 = p_a_2_.nextInt(16) + 8;
            this.aF.generate(p_a_1_, p_a_2_, p_a_1_.getHighestBlockYAt(p_a_3_.a(i1, 0, j1)));
         }
      }

      super.a(p_a_1_, p_a_2_, p_a_3_);
   }

   public WorldGenTreeAbstract a(Random p_a_1_) {
      return new WorldGenTaiga2(false);
   }

   protected BiomeBase d(int p_d_1_) {
      BiomeBase biomebase = (new BiomeIcePlains(p_d_1_, true)).a(13828095, true).a(this.ah + " Spikes").c().a(0.0F, 0.5F).a(new BiomeBase.BiomeTemperature(this.an + 0.1F, this.ao + 0.1F));
      biomebase.an = this.an + 0.3F;
      biomebase.ao = this.ao + 0.4F;
      return biomebase;
   }
}
