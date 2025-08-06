package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BiomeCache;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.GenLayer;
import net.minecraft.server.v1_8_R3.IntCache;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldType;

public class WorldChunkManager {
   private GenLayer b;
   private GenLayer c;
   private BiomeCache d;
   private List<BiomeBase> e;
   private String f;

   protected WorldChunkManager() {
      this.d = new BiomeCache(this);
      this.f = "";
      this.e = Lists.<BiomeBase>newArrayList();
      this.e.add(BiomeBase.FOREST);
      this.e.add(BiomeBase.PLAINS);
      this.e.add(BiomeBase.TAIGA);
      this.e.add(BiomeBase.TAIGA_HILLS);
      this.e.add(BiomeBase.FOREST_HILLS);
      this.e.add(BiomeBase.JUNGLE);
      this.e.add(BiomeBase.JUNGLE_HILLS);
   }

   public WorldChunkManager(long p_i569_1_, WorldType p_i569_3_, String p_i569_4_) {
      this();
      this.f = p_i569_4_;
      GenLayer[] agenlayer = GenLayer.a(p_i569_1_, p_i569_3_, p_i569_4_);
      this.b = agenlayer[0];
      this.c = agenlayer[1];
   }

   public WorldChunkManager(World p_i570_1_) {
      this(p_i570_1_.getSeed(), p_i570_1_.getWorldData().getType(), p_i570_1_.getWorldData().getGeneratorOptions());
   }

   public List<BiomeBase> a() {
      return this.e;
   }

   public BiomeBase getBiome(BlockPosition p_getBiome_1_) {
      return this.getBiome(p_getBiome_1_, (BiomeBase)null);
   }

   public BiomeBase getBiome(BlockPosition p_getBiome_1_, BiomeBase p_getBiome_2_) {
      return this.d.a(p_getBiome_1_.getX(), p_getBiome_1_.getZ(), p_getBiome_2_);
   }

   public float[] getWetness(float[] p_getWetness_1_, int p_getWetness_2_, int p_getWetness_3_, int p_getWetness_4_, int p_getWetness_5_) {
      IntCache.a();
      if(p_getWetness_1_ == null || p_getWetness_1_.length < p_getWetness_4_ * p_getWetness_5_) {
         p_getWetness_1_ = new float[p_getWetness_4_ * p_getWetness_5_];
      }

      int[] aint = this.c.a(p_getWetness_2_, p_getWetness_3_, p_getWetness_4_, p_getWetness_5_);

      for(int i = 0; i < p_getWetness_4_ * p_getWetness_5_; ++i) {
         try {
            float fx = (float)BiomeBase.getBiome(aint[i], BiomeBase.ad).h() / 65536.0F;
            if(fx > 1.0F) {
               fx = 1.0F;
            }

            p_getWetness_1_[i] = fx;
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Invalid Biome id");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("DownfallBlock");
            crashreportsystemdetails.a((String)"biome id", Integer.valueOf(i));
            crashreportsystemdetails.a((String)"downfalls[] size", Integer.valueOf(p_getWetness_1_.length));
            crashreportsystemdetails.a((String)"x", Integer.valueOf(p_getWetness_2_));
            crashreportsystemdetails.a((String)"z", Integer.valueOf(p_getWetness_3_));
            crashreportsystemdetails.a((String)"w", Integer.valueOf(p_getWetness_4_));
            crashreportsystemdetails.a((String)"h", Integer.valueOf(p_getWetness_5_));
            throw new ReportedException(crashreport);
         }
      }

      return p_getWetness_1_;
   }

   public BiomeBase[] getBiomes(BiomeBase[] p_getBiomes_1_, int p_getBiomes_2_, int p_getBiomes_3_, int p_getBiomes_4_, int p_getBiomes_5_) {
      IntCache.a();
      if(p_getBiomes_1_ == null || p_getBiomes_1_.length < p_getBiomes_4_ * p_getBiomes_5_) {
         p_getBiomes_1_ = new BiomeBase[p_getBiomes_4_ * p_getBiomes_5_];
      }

      int[] aint = this.b.a(p_getBiomes_2_, p_getBiomes_3_, p_getBiomes_4_, p_getBiomes_5_);

      try {
         for(int i = 0; i < p_getBiomes_4_ * p_getBiomes_5_; ++i) {
            p_getBiomes_1_[i] = BiomeBase.getBiome(aint[i], BiomeBase.ad);
         }

         return p_getBiomes_1_;
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.a(throwable, "Invalid Biome id");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("RawBiomeBlock");
         crashreportsystemdetails.a((String)"biomes[] size", Integer.valueOf(p_getBiomes_1_.length));
         crashreportsystemdetails.a((String)"x", Integer.valueOf(p_getBiomes_2_));
         crashreportsystemdetails.a((String)"z", Integer.valueOf(p_getBiomes_3_));
         crashreportsystemdetails.a((String)"w", Integer.valueOf(p_getBiomes_4_));
         crashreportsystemdetails.a((String)"h", Integer.valueOf(p_getBiomes_5_));
         throw new ReportedException(crashreport);
      }
   }

   public BiomeBase[] getBiomeBlock(BiomeBase[] p_getBiomeBlock_1_, int p_getBiomeBlock_2_, int p_getBiomeBlock_3_, int p_getBiomeBlock_4_, int p_getBiomeBlock_5_) {
      return this.a(p_getBiomeBlock_1_, p_getBiomeBlock_2_, p_getBiomeBlock_3_, p_getBiomeBlock_4_, p_getBiomeBlock_5_, true);
   }

   public BiomeBase[] a(BiomeBase[] p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, boolean p_a_6_) {
      IntCache.a();
      if(p_a_1_ == null || p_a_1_.length < p_a_4_ * p_a_5_) {
         p_a_1_ = new BiomeBase[p_a_4_ * p_a_5_];
      }

      if(p_a_6_ && p_a_4_ == 16 && p_a_5_ == 16 && (p_a_2_ & 15) == 0 && (p_a_3_ & 15) == 0) {
         BiomeBase[] abiomebase = this.d.c(p_a_2_, p_a_3_);
         System.arraycopy(abiomebase, 0, p_a_1_, 0, p_a_4_ * p_a_5_);
         return p_a_1_;
      } else {
         int[] aint = this.c.a(p_a_2_, p_a_3_, p_a_4_, p_a_5_);

         for(int i = 0; i < p_a_4_ * p_a_5_; ++i) {
            p_a_1_[i] = BiomeBase.getBiome(aint[i], BiomeBase.ad);
         }

         return p_a_1_;
      }
   }

   public boolean a(int p_a_1_, int p_a_2_, int p_a_3_, List<BiomeBase> p_a_4_) {
      IntCache.a();
      int i = p_a_1_ - p_a_3_ >> 2;
      int j = p_a_2_ - p_a_3_ >> 2;
      int k = p_a_1_ + p_a_3_ >> 2;
      int l = p_a_2_ + p_a_3_ >> 2;
      int i1 = k - i + 1;
      int j1 = l - j + 1;
      int[] aint = this.b.a(i, j, i1, j1);

      try {
         for(int k1 = 0; k1 < i1 * j1; ++k1) {
            BiomeBase biomebase = BiomeBase.getBiome(aint[k1]);
            if(!p_a_4_.contains(biomebase)) {
               return false;
            }
         }

         return true;
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.a(throwable, "Invalid Biome id");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Layer");
         crashreportsystemdetails.a((String)"Layer", this.b.toString());
         crashreportsystemdetails.a((String)"x", Integer.valueOf(p_a_1_));
         crashreportsystemdetails.a((String)"z", Integer.valueOf(p_a_2_));
         crashreportsystemdetails.a((String)"radius", Integer.valueOf(p_a_3_));
         crashreportsystemdetails.a((String)"allowed", p_a_4_);
         throw new ReportedException(crashreport);
      }
   }

   public BlockPosition a(int p_a_1_, int p_a_2_, int p_a_3_, List<BiomeBase> p_a_4_, Random p_a_5_) {
      IntCache.a();
      int i = p_a_1_ - p_a_3_ >> 2;
      int j = p_a_2_ - p_a_3_ >> 2;
      int k = p_a_1_ + p_a_3_ >> 2;
      int l = p_a_2_ + p_a_3_ >> 2;
      int i1 = k - i + 1;
      int j1 = l - j + 1;
      int[] aint = this.b.a(i, j, i1, j1);
      BlockPosition blockposition = null;
      int k1 = 0;

      for(int l1 = 0; l1 < i1 * j1; ++l1) {
         int i2 = i + l1 % i1 << 2;
         int j2 = j + l1 / i1 << 2;
         BiomeBase biomebase = BiomeBase.getBiome(aint[l1]);
         if(p_a_4_.contains(biomebase) && (blockposition == null || p_a_5_.nextInt(k1 + 1) == 0)) {
            blockposition = new BlockPosition(i2, 0, j2);
            ++k1;
         }
      }

      return blockposition;
   }

   public void b() {
      this.d.a();
   }
}
