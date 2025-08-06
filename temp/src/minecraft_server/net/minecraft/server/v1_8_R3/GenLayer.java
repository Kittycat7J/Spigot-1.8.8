package net.minecraft.server.v1_8_R3;

import java.util.concurrent.Callable;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.CustomWorldSettingsFinal;
import net.minecraft.server.v1_8_R3.GenLayerBiome;
import net.minecraft.server.v1_8_R3.GenLayerCleaner;
import net.minecraft.server.v1_8_R3.GenLayerDeepOcean;
import net.minecraft.server.v1_8_R3.GenLayerDesert;
import net.minecraft.server.v1_8_R3.GenLayerIcePlains;
import net.minecraft.server.v1_8_R3.GenLayerIsland;
import net.minecraft.server.v1_8_R3.GenLayerMushroomIsland;
import net.minecraft.server.v1_8_R3.GenLayerMushroomShore;
import net.minecraft.server.v1_8_R3.GenLayerPlains;
import net.minecraft.server.v1_8_R3.GenLayerRegionHills;
import net.minecraft.server.v1_8_R3.GenLayerRiver;
import net.minecraft.server.v1_8_R3.GenLayerRiverMix;
import net.minecraft.server.v1_8_R3.GenLayerSmooth;
import net.minecraft.server.v1_8_R3.GenLayerSpecial;
import net.minecraft.server.v1_8_R3.GenLayerTopSoil;
import net.minecraft.server.v1_8_R3.GenLayerZoom;
import net.minecraft.server.v1_8_R3.GenLayerZoomFuzzy;
import net.minecraft.server.v1_8_R3.GenLayerZoomVoronoi;
import net.minecraft.server.v1_8_R3.LayerIsland;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.WorldType;

public abstract class GenLayer {
   private long c;
   protected GenLayer a;
   private long d;
   protected long b;

   public static GenLayer[] a(long p_a_0_, WorldType p_a_2_, String p_a_3_) {
      LayerIsland layerisland = new LayerIsland(1L);
      GenLayerZoomFuzzy genlayerzoomfuzzy = new GenLayerZoomFuzzy(2000L, layerisland);
      GenLayerIsland genlayerisland = new GenLayerIsland(1L, genlayerzoomfuzzy);
      GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayerisland);
      GenLayerIsland genlayerisland1 = new GenLayerIsland(2L, genlayerzoom);
      genlayerisland1 = new GenLayerIsland(50L, genlayerisland1);
      genlayerisland1 = new GenLayerIsland(70L, genlayerisland1);
      GenLayerIcePlains genlayericeplains = new GenLayerIcePlains(2L, genlayerisland1);
      GenLayerTopSoil genlayertopsoil = new GenLayerTopSoil(2L, genlayericeplains);
      GenLayerIsland genlayerisland2 = new GenLayerIsland(3L, genlayertopsoil);
      GenLayerSpecial genlayerspecial = new GenLayerSpecial(2L, genlayerisland2, GenLayerSpecial.EnumGenLayerSpecial.COOL_WARM);
      genlayerspecial = new GenLayerSpecial(2L, genlayerspecial, GenLayerSpecial.EnumGenLayerSpecial.HEAT_ICE);
      genlayerspecial = new GenLayerSpecial(3L, genlayerspecial, GenLayerSpecial.EnumGenLayerSpecial.SPECIAL);
      GenLayerZoom genlayerzoom1 = new GenLayerZoom(2002L, genlayerspecial);
      genlayerzoom1 = new GenLayerZoom(2003L, genlayerzoom1);
      GenLayerIsland genlayerisland3 = new GenLayerIsland(4L, genlayerzoom1);
      GenLayerMushroomIsland genlayermushroomisland = new GenLayerMushroomIsland(5L, genlayerisland3);
      GenLayerDeepOcean genlayerdeepocean = new GenLayerDeepOcean(4L, genlayermushroomisland);
      GenLayer genlayer2 = GenLayerZoom.b(1000L, genlayerdeepocean, 0);
      CustomWorldSettingsFinal customworldsettingsfinal = null;
      int i = 4;
      int j = i;
      if(p_a_2_ == WorldType.CUSTOMIZED && p_a_3_.length() > 0) {
         customworldsettingsfinal = CustomWorldSettingsFinal.CustomWorldSettings.a(p_a_3_).b();
         i = customworldsettingsfinal.G;
         j = customworldsettingsfinal.H;
      }

      if(p_a_2_ == WorldType.LARGE_BIOMES) {
         i = 6;
      }

      GenLayer genlayer = GenLayerZoom.b(1000L, genlayer2, 0);
      GenLayerCleaner genlayercleaner = new GenLayerCleaner(100L, genlayer);
      GenLayerBiome genlayerbiome = new GenLayerBiome(200L, genlayer2, p_a_2_, p_a_3_);
      GenLayer genlayer4 = GenLayerZoom.b(1000L, genlayerbiome, 2);
      GenLayerDesert genlayerdesert = new GenLayerDesert(1000L, genlayer4);
      GenLayer genlayer1 = GenLayerZoom.b(1000L, genlayercleaner, 2);
      GenLayerRegionHills genlayerregionhills = new GenLayerRegionHills(1000L, genlayerdesert, genlayer1);
      GenLayer genlayer3 = GenLayerZoom.b(1000L, genlayercleaner, 2);
      genlayer3 = GenLayerZoom.b(1000L, genlayer3, j);
      GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer3);
      GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
      genlayerregionhills = new GenLayerPlains(1001L, genlayerregionhills);

      for(int k = 0; k < i; ++k) {
         genlayerregionhills = new GenLayerZoom((long)(1000 + k), genlayerregionhills);
         if(k == 0) {
            genlayerregionhills = new GenLayerIsland(3L, genlayerregionhills);
         }

         if(k == 1 || i == 1) {
            genlayerregionhills = new GenLayerMushroomShore(1000L, genlayerregionhills);
         }
      }

      GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, genlayerregionhills);
      GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
      GenLayerZoomVoronoi genlayerzoomvoronoi = new GenLayerZoomVoronoi(10L, genlayerrivermix);
      genlayerrivermix.a(p_a_0_);
      genlayerzoomvoronoi.a(p_a_0_);
      return new GenLayer[]{genlayerrivermix, genlayerzoomvoronoi, genlayerrivermix};
   }

   public GenLayer(long p_i821_1_) {
      this.b = p_i821_1_;
      this.b *= this.b * 6364136223846793005L + 1442695040888963407L;
      this.b += p_i821_1_;
      this.b *= this.b * 6364136223846793005L + 1442695040888963407L;
      this.b += p_i821_1_;
      this.b *= this.b * 6364136223846793005L + 1442695040888963407L;
      this.b += p_i821_1_;
   }

   public void a(long p_a_1_) {
      this.c = p_a_1_;
      if(this.a != null) {
         this.a.a(p_a_1_);
      }

      this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
      this.c += this.b;
      this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
      this.c += this.b;
      this.c *= this.c * 6364136223846793005L + 1442695040888963407L;
      this.c += this.b;
   }

   public void a(long p_a_1_, long p_a_3_) {
      this.d = this.c;
      this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
      this.d += p_a_1_;
      this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
      this.d += p_a_3_;
      this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
      this.d += p_a_1_;
      this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
      this.d += p_a_3_;
   }

   protected int a(int p_a_1_) {
      int i = (int)((this.d >> 24) % (long)p_a_1_);
      if(i < 0) {
         i += p_a_1_;
      }

      this.d *= this.d * 6364136223846793005L + 1442695040888963407L;
      this.d += this.c;
      return i;
   }

   public abstract int[] a(int var1, int var2, int var3, int var4);

   protected static boolean a(int p_a_0_, int p_a_1_) {
      if(p_a_0_ == p_a_1_) {
         return true;
      } else if(p_a_0_ != BiomeBase.MESA_PLATEAU_F.id && p_a_0_ != BiomeBase.MESA_PLATEAU.id) {
         final BiomeBase biomebase = BiomeBase.getBiome(p_a_0_);
         final BiomeBase biomebase1 = BiomeBase.getBiome(p_a_1_);

         try {
            return biomebase != null && biomebase1 != null?biomebase.a(biomebase1):false;
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Comparing biomes");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Biomes being compared");
            crashreportsystemdetails.a((String)"Biome A ID", Integer.valueOf(p_a_0_));
            crashreportsystemdetails.a((String)"Biome B ID", Integer.valueOf(p_a_1_));
            crashreportsystemdetails.a("Biome A", new Callable<String>() {
               public String a() throws Exception {
                  return String.valueOf(biomebase);
               }
            });
            crashreportsystemdetails.a("Biome B", new Callable<String>() {
               public String a() throws Exception {
                  return String.valueOf(biomebase1);
               }
            });
            throw new ReportedException(crashreport);
         }
      } else {
         return p_a_1_ == BiomeBase.MESA_PLATEAU_F.id || p_a_1_ == BiomeBase.MESA_PLATEAU.id;
      }
   }

   protected static boolean b(int p_b_0_) {
      return p_b_0_ == BiomeBase.OCEAN.id || p_b_0_ == BiomeBase.DEEP_OCEAN.id || p_b_0_ == BiomeBase.FROZEN_OCEAN.id;
   }

   protected int a(int... p_a_1_) {
      return p_a_1_[this.a(p_a_1_.length)];
   }

   protected int b(int p_b_1_, int p_b_2_, int p_b_3_, int p_b_4_) {
      return p_b_2_ == p_b_3_ && p_b_3_ == p_b_4_?p_b_2_:(p_b_1_ == p_b_2_ && p_b_1_ == p_b_3_?p_b_1_:(p_b_1_ == p_b_2_ && p_b_1_ == p_b_4_?p_b_1_:(p_b_1_ == p_b_3_ && p_b_1_ == p_b_4_?p_b_1_:(p_b_1_ == p_b_2_ && p_b_3_ != p_b_4_?p_b_1_:(p_b_1_ == p_b_3_ && p_b_2_ != p_b_4_?p_b_1_:(p_b_1_ == p_b_4_ && p_b_2_ != p_b_3_?p_b_1_:(p_b_2_ == p_b_3_ && p_b_1_ != p_b_4_?p_b_2_:(p_b_2_ == p_b_4_ && p_b_1_ != p_b_3_?p_b_2_:(p_b_3_ == p_b_4_ && p_b_1_ != p_b_2_?p_b_3_:this.a(new int[]{p_b_1_, p_b_2_, p_b_3_, p_b_4_}))))))))));
   }
}
