package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import net.minecraft.server.v1_8_R3.RegionFile;

public class RegionFileCache {
   public static final Map<File, RegionFile> a = Maps.<File, RegionFile>newHashMap();

   public static synchronized RegionFile a(File p_a_0_, int p_a_1_, int p_a_2_) {
      File file1 = new File(p_a_0_, "region");
      File file2 = new File(file1, "r." + (p_a_1_ >> 5) + "." + (p_a_2_ >> 5) + ".mca");
      RegionFile regionfile = (RegionFile)a.get(file2);
      if(regionfile != null) {
         return regionfile;
      } else {
         if(!file1.exists()) {
            file1.mkdirs();
         }

         if(a.size() >= 256) {
            a();
         }

         RegionFile regionfile1 = new RegionFile(file2);
         a.put(file2, regionfile1);
         return regionfile1;
      }
   }

   public static synchronized void a() {
      for(RegionFile regionfile : a.values()) {
         try {
            if(regionfile != null) {
               regionfile.c();
            }
         } catch (IOException ioexception) {
            ioexception.printStackTrace();
         }
      }

      a.clear();
   }

   public static DataInputStream c(File p_c_0_, int p_c_1_, int p_c_2_) {
      RegionFile regionfile = a(p_c_0_, p_c_1_, p_c_2_);
      return regionfile.a(p_c_1_ & 31, p_c_2_ & 31);
   }

   public static DataOutputStream d(File p_d_0_, int p_d_1_, int p_d_2_) {
      RegionFile regionfile = a(p_d_0_, p_d_1_, p_d_2_);
      return regionfile.b(p_d_1_ & 31, p_d_2_ & 31);
   }
}
