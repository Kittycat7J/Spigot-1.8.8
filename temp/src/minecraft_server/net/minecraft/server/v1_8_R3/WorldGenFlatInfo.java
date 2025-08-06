package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.WorldGenFlatLayerInfo;

public class WorldGenFlatInfo {
   private final List<WorldGenFlatLayerInfo> layers = Lists.<WorldGenFlatLayerInfo>newArrayList();
   private final Map<String, Map<String, String>> structures = Maps.<String, Map<String, String>>newHashMap();
   private int c;

   public int a() {
      return this.c;
   }

   public void a(int p_a_1_) {
      this.c = p_a_1_;
   }

   public Map<String, Map<String, String>> b() {
      return this.structures;
   }

   public List<WorldGenFlatLayerInfo> c() {
      return this.layers;
   }

   public void d() {
      int i = 0;

      for(WorldGenFlatLayerInfo worldgenflatlayerinfo : this.layers) {
         worldgenflatlayerinfo.b(i);
         i += worldgenflatlayerinfo.b();
      }

   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append(3);
      stringbuilder.append(";");

      for(int i = 0; i < this.layers.size(); ++i) {
         if(i > 0) {
            stringbuilder.append(",");
         }

         stringbuilder.append(((WorldGenFlatLayerInfo)this.layers.get(i)).toString());
      }

      stringbuilder.append(";");
      stringbuilder.append(this.c);
      if(!this.structures.isEmpty()) {
         stringbuilder.append(";");
         int k = 0;

         for(Entry entry : this.structures.entrySet()) {
            if(k++ > 0) {
               stringbuilder.append(",");
            }

            stringbuilder.append(((String)entry.getKey()).toLowerCase());
            Map map = (Map)entry.getValue();
            if(!map.isEmpty()) {
               stringbuilder.append("(");
               int j = 0;

               for(Entry entry1 : map.entrySet()) {
                  if(j++ > 0) {
                     stringbuilder.append(" ");
                  }

                  stringbuilder.append((String)entry1.getKey());
                  stringbuilder.append("=");
                  stringbuilder.append((String)entry1.getValue());
               }

               stringbuilder.append(")");
            }
         }
      } else {
         stringbuilder.append(";");
      }

      return stringbuilder.toString();
   }

   private static WorldGenFlatLayerInfo a(int p_a_0_, String p_a_1_, int p_a_2_) {
      String[] astring = p_a_0_ >= 3?p_a_1_.split("\\*", 2):p_a_1_.split("x", 2);
      int i = 1;
      int j = 0;
      if(astring.length == 2) {
         try {
            i = Integer.parseInt(astring[0]);
            if(p_a_2_ + i >= 256) {
               i = 256 - p_a_2_;
            }

            if(i < 0) {
               i = 0;
            }
         } catch (Throwable var8) {
            return null;
         }
      }

      Block block = null;

      try {
         String s = astring[astring.length - 1];
         if(p_a_0_ < 3) {
            astring = s.split(":", 2);
            if(astring.length > 1) {
               j = Integer.parseInt(astring[1]);
            }

            block = Block.getById(Integer.parseInt(astring[0]));
         } else {
            astring = s.split(":", 3);
            block = astring.length > 1?Block.getByName(astring[0] + ":" + astring[1]):null;
            if(block != null) {
               j = astring.length > 2?Integer.parseInt(astring[2]):0;
            } else {
               block = Block.getByName(astring[0]);
               if(block != null) {
                  j = astring.length > 1?Integer.parseInt(astring[1]):0;
               }
            }

            if(block == null) {
               return null;
            }
         }

         if(block == Blocks.AIR) {
            j = 0;
         }

         if(j < 0 || j > 15) {
            j = 0;
         }
      } catch (Throwable var9) {
         return null;
      }

      WorldGenFlatLayerInfo worldgenflatlayerinfo = new WorldGenFlatLayerInfo(p_a_0_, i, block, j);
      worldgenflatlayerinfo.b(p_a_2_);
      return worldgenflatlayerinfo;
   }

   private static List<WorldGenFlatLayerInfo> a(int p_a_0_, String p_a_1_) {
      if(p_a_1_ != null && p_a_1_.length() >= 1) {
         ArrayList arraylist = Lists.newArrayList();
         String[] astring = p_a_1_.split(",");
         int i = 0;

         for(String s : astring) {
            WorldGenFlatLayerInfo worldgenflatlayerinfo = a(p_a_0_, s, i);
            if(worldgenflatlayerinfo == null) {
               return null;
            }

            arraylist.add(worldgenflatlayerinfo);
            i += worldgenflatlayerinfo.b();
         }

         return arraylist;
      } else {
         return null;
      }
   }

   public static WorldGenFlatInfo a(String p_a_0_) {
      if(p_a_0_ == null) {
         return e();
      } else {
         String[] astring = p_a_0_.split(";", -1);
         int i = astring.length == 1?0:MathHelper.a(astring[0], 0);
         if(i >= 0 && i <= 3) {
            WorldGenFlatInfo worldgenflatinfo = new WorldGenFlatInfo();
            int j = astring.length == 1?0:1;
            List list = a(i, astring[j++]);
            if(list != null && !list.isEmpty()) {
               worldgenflatinfo.c().addAll(list);
               worldgenflatinfo.d();
               int k = BiomeBase.PLAINS.id;
               if(i > 0 && astring.length > j) {
                  k = MathHelper.a(astring[j++], k);
               }

               worldgenflatinfo.a(k);
               if(i > 0 && astring.length > j) {
                  String[] astring1 = astring[j++].toLowerCase().split(",");

                  for(String s : astring1) {
                     String[] astring2 = s.split("\\(", 2);
                     HashMap hashmap = Maps.newHashMap();
                     if(astring2[0].length() > 0) {
                        worldgenflatinfo.b().put(astring2[0], hashmap);
                        if(astring2.length > 1 && astring2[1].endsWith(")") && astring2[1].length() > 1) {
                           String[] astring3 = astring2[1].substring(0, astring2[1].length() - 1).split(" ");

                           for(int l = 0; l < astring3.length; ++l) {
                              String[] astring4 = astring3[l].split("=", 2);
                              if(astring4.length == 2) {
                                 hashmap.put(astring4[0], astring4[1]);
                              }
                           }
                        }
                     }
                  }
               } else {
                  worldgenflatinfo.b().put("village", Maps.newHashMap());
               }

               return worldgenflatinfo;
            } else {
               return e();
            }
         } else {
            return e();
         }
      }
   }

   public static WorldGenFlatInfo e() {
      WorldGenFlatInfo worldgenflatinfo = new WorldGenFlatInfo();
      worldgenflatinfo.a(BiomeBase.PLAINS.id);
      worldgenflatinfo.c().add(new WorldGenFlatLayerInfo(1, Blocks.BEDROCK));
      worldgenflatinfo.c().add(new WorldGenFlatLayerInfo(2, Blocks.DIRT));
      worldgenflatinfo.c().add(new WorldGenFlatLayerInfo(1, Blocks.GRASS));
      worldgenflatinfo.d();
      worldgenflatinfo.b().put("village", Maps.newHashMap());
      return worldgenflatinfo;
   }
}
