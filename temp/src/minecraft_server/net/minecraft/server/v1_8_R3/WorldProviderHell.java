package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.ChunkProviderHell;
import net.minecraft.server.v1_8_R3.IChunkProvider;
import net.minecraft.server.v1_8_R3.WorldBorder;
import net.minecraft.server.v1_8_R3.WorldChunkManagerHell;
import net.minecraft.server.v1_8_R3.WorldProvider;

public class WorldProviderHell extends WorldProvider {
   public void b() {
      this.c = new WorldChunkManagerHell(BiomeBase.HELL, 0.0F);
      this.d = true;
      this.e = true;
      this.dimension = -1;
   }

   protected void a() {
      float f = 0.1F;

      for(int i = 0; i <= 15; ++i) {
         float f1 = 1.0F - (float)i / 15.0F;
         this.f[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
      }

   }

   public IChunkProvider getChunkProvider() {
      return new ChunkProviderHell(this.b, this.b.getWorldData().shouldGenerateMapFeatures(), this.b.getSeed());
   }

   public boolean d() {
      return false;
   }

   public boolean canSpawn(int p_canSpawn_1_, int p_canSpawn_2_) {
      return false;
   }

   public float a(long p_a_1_, float p_a_3_) {
      return 0.5F;
   }

   public boolean e() {
      return false;
   }

   public String getName() {
      return "Nether";
   }

   public String getSuffix() {
      return "_nether";
   }

   public WorldBorder getWorldBorder() {
      return new WorldBorder() {
         public double getCenterX() {
            return super.getCenterX() / 8.0D;
         }

         public double getCenterZ() {
            return super.getCenterZ() / 8.0D;
         }
      };
   }
}
