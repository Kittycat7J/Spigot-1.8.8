package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NibbleArray;
import net.minecraft.server.v1_8_R3.OldNibbleArray;
import net.minecraft.server.v1_8_R3.WorldChunkManager;

public class OldChunkLoader {
   public static OldChunkLoader.OldChunk a(NBTTagCompound p_a_0_) {
      int i = p_a_0_.getInt("xPos");
      int j = p_a_0_.getInt("zPos");
      OldChunkLoader.OldChunk oldchunkloader$oldchunk = new OldChunkLoader.OldChunk(i, j);
      oldchunkloader$oldchunk.g = p_a_0_.getByteArray("Blocks");
      oldchunkloader$oldchunk.f = new OldNibbleArray(p_a_0_.getByteArray("Data"), 7);
      oldchunkloader$oldchunk.e = new OldNibbleArray(p_a_0_.getByteArray("SkyLight"), 7);
      oldchunkloader$oldchunk.d = new OldNibbleArray(p_a_0_.getByteArray("BlockLight"), 7);
      oldchunkloader$oldchunk.c = p_a_0_.getByteArray("HeightMap");
      oldchunkloader$oldchunk.b = p_a_0_.getBoolean("TerrainPopulated");
      oldchunkloader$oldchunk.h = p_a_0_.getList("Entities", 10);
      oldchunkloader$oldchunk.i = p_a_0_.getList("TileEntities", 10);
      oldchunkloader$oldchunk.j = p_a_0_.getList("TileTicks", 10);

      try {
         oldchunkloader$oldchunk.a = p_a_0_.getLong("LastUpdate");
      } catch (ClassCastException var4) {
         oldchunkloader$oldchunk.a = (long)p_a_0_.getInt("LastUpdate");
      }

      return oldchunkloader$oldchunk;
   }

   public static void a(OldChunkLoader.OldChunk p_a_0_, NBTTagCompound p_a_1_, WorldChunkManager p_a_2_) {
      p_a_1_.setInt("xPos", p_a_0_.k);
      p_a_1_.setInt("zPos", p_a_0_.l);
      p_a_1_.setLong("LastUpdate", p_a_0_.a);
      int[] aint = new int[p_a_0_.c.length];

      for(int i = 0; i < p_a_0_.c.length; ++i) {
         aint[i] = p_a_0_.c[i];
      }

      p_a_1_.setIntArray("HeightMap", aint);
      p_a_1_.setBoolean("TerrainPopulated", p_a_0_.b);
      NBTTagList nbttaglist = new NBTTagList();

      for(int j = 0; j < 8; ++j) {
         boolean flag = true;

         for(int k = 0; k < 16 && flag; ++k) {
            for(int l = 0; l < 16 && flag; ++l) {
               for(int i1 = 0; i1 < 16; ++i1) {
                  int j1 = k << 11 | i1 << 7 | l + (j << 4);
                  byte b0 = p_a_0_.g[j1];
                  if(b0 != 0) {
                     flag = false;
                     break;
                  }
               }
            }
         }

         if(!flag) {
            byte[] abyte1 = new byte[4096];
            NibbleArray nibblearray1 = new NibbleArray();
            NibbleArray nibblearray2 = new NibbleArray();
            NibbleArray nibblearray = new NibbleArray();

            for(int k1 = 0; k1 < 16; ++k1) {
               for(int l1 = 0; l1 < 16; ++l1) {
                  for(int i2 = 0; i2 < 16; ++i2) {
                     int j2 = k1 << 11 | i2 << 7 | l1 + (j << 4);
                     byte b1 = p_a_0_.g[j2];
                     abyte1[l1 << 8 | i2 << 4 | k1] = (byte)(b1 & 255);
                     nibblearray1.a(k1, l1, i2, p_a_0_.f.a(k1, l1 + (j << 4), i2));
                     nibblearray2.a(k1, l1, i2, p_a_0_.e.a(k1, l1 + (j << 4), i2));
                     nibblearray.a(k1, l1, i2, p_a_0_.d.a(k1, l1 + (j << 4), i2));
                  }
               }
            }

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Y", (byte)(j & 255));
            nbttagcompound.setByteArray("Blocks", abyte1);
            nbttagcompound.setByteArray("Data", nibblearray1.a());
            nbttagcompound.setByteArray("SkyLight", nibblearray2.a());
            nbttagcompound.setByteArray("BlockLight", nibblearray.a());
            nbttaglist.add(nbttagcompound);
         }
      }

      p_a_1_.set("Sections", nbttaglist);
      byte[] abyte = new byte[256];
      BlockPosition.MutableBlockPosition blockposition$mutableblockposition = new BlockPosition.MutableBlockPosition();

      for(int k2 = 0; k2 < 16; ++k2) {
         for(int l2 = 0; l2 < 16; ++l2) {
            blockposition$mutableblockposition.c(p_a_0_.k << 4 | k2, 0, p_a_0_.l << 4 | l2);
            abyte[l2 << 4 | k2] = (byte)(p_a_2_.getBiome(blockposition$mutableblockposition, BiomeBase.ad).id & 255);
         }
      }

      p_a_1_.setByteArray("Biomes", abyte);
      p_a_1_.set("Entities", p_a_0_.h);
      p_a_1_.set("TileEntities", p_a_0_.i);
      if(p_a_0_.j != null) {
         p_a_1_.set("TileTicks", p_a_0_.j);
      }

   }

   public static class OldChunk {
      public long a;
      public boolean b;
      public byte[] c;
      public OldNibbleArray d;
      public OldNibbleArray e;
      public OldNibbleArray f;
      public byte[] g;
      public NBTTagList h;
      public NBTTagList i;
      public NBTTagList j;
      public final int k;
      public final int l;

      public OldChunk(int p_i142_1_, int p_i142_2_) {
         this.k = p_i142_1_;
         this.l = p_i142_2_;
      }
   }
}
