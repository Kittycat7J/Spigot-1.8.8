package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.IDataManager;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagShort;
import net.minecraft.server.v1_8_R3.PersistentBase;

public class PersistentCollection {
   private IDataManager b;
   protected Map<String, PersistentBase> a = Maps.<String, PersistentBase>newHashMap();
   public List<PersistentBase> c = Lists.<PersistentBase>newArrayList();
   private Map<String, Short> d = Maps.<String, Short>newHashMap();

   public PersistentCollection(IDataManager p_i258_1_) {
      this.b = p_i258_1_;
      this.b();
   }

   public PersistentBase get(Class<? extends PersistentBase> p_get_1_, String p_get_2_) {
      PersistentBase persistentbase = (PersistentBase)this.a.get(p_get_2_);
      if(persistentbase != null) {
         return persistentbase;
      } else {
         if(this.b != null) {
            try {
               File file1 = this.b.getDataFile(p_get_2_);
               if(file1 != null && file1.exists()) {
                  try {
                     persistentbase = (PersistentBase)p_get_1_.getConstructor(new Class[]{String.class}).newInstance(new Object[]{p_get_2_});
                  } catch (Exception exception1) {
                     throw new RuntimeException("Failed to instantiate " + p_get_1_.toString(), exception1);
                  }

                  FileInputStream fileinputstream = new FileInputStream(file1);
                  NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a((InputStream)fileinputstream);
                  fileinputstream.close();
                  persistentbase.a(nbttagcompound.getCompound("data"));
               }
            } catch (Exception exception1) {
               exception1.printStackTrace();
            }
         }

         if(persistentbase != null) {
            this.a.put(p_get_2_, persistentbase);
            this.c.add(persistentbase);
         }

         return persistentbase;
      }
   }

   public void a(String p_a_1_, PersistentBase p_a_2_) {
      if(this.a.containsKey(p_a_1_)) {
         this.c.remove(this.a.remove(p_a_1_));
      }

      this.a.put(p_a_1_, p_a_2_);
      this.c.add(p_a_2_);
   }

   public void a() {
      for(int i = 0; i < this.c.size(); ++i) {
         PersistentBase persistentbase = (PersistentBase)this.c.get(i);
         if(persistentbase.d()) {
            this.a(persistentbase);
            persistentbase.a(false);
         }
      }

   }

   private void a(PersistentBase p_a_1_) {
      if(this.b != null) {
         try {
            File file1 = this.b.getDataFile(p_a_1_.id);
            if(file1 != null) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();
               p_a_1_.b(nbttagcompound);
               NBTTagCompound nbttagcompound1 = new NBTTagCompound();
               nbttagcompound1.set("data", nbttagcompound);
               FileOutputStream fileoutputstream = new FileOutputStream(file1);
               NBTCompressedStreamTools.a((NBTTagCompound)nbttagcompound1, (OutputStream)fileoutputstream);
               fileoutputstream.close();
            }
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }

   }

   private void b() {
      try {
         this.d.clear();
         if(this.b == null) {
            return;
         }

         File file1 = this.b.getDataFile("idcounts");
         if(file1 != null && file1.exists()) {
            DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
            NBTTagCompound nbttagcompound = NBTCompressedStreamTools.a(datainputstream);
            datainputstream.close();

            for(String s : nbttagcompound.c()) {
               NBTBase nbtbase = nbttagcompound.get(s);
               if(nbtbase instanceof NBTTagShort) {
                  NBTTagShort nbttagshort = (NBTTagShort)nbtbase;
                  short short1 = nbttagshort.e();
                  this.d.put(s, Short.valueOf(short1));
               }
            }
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }

   }

   public int a(String p_a_1_) {
      Short oshort = (Short)this.d.get(p_a_1_);
      if(oshort == null) {
         oshort = Short.valueOf((short)0);
      } else {
         oshort = Short.valueOf((short)(oshort.shortValue() + 1));
      }

      this.d.put(p_a_1_, oshort);
      if(this.b == null) {
         return oshort.shortValue();
      } else {
         try {
            File file1 = this.b.getDataFile("idcounts");
            if(file1 != null) {
               NBTTagCompound nbttagcompound = new NBTTagCompound();

               for(String s : this.d.keySet()) {
                  short short1 = ((Short)this.d.get(s)).shortValue();
                  nbttagcompound.setShort(s, short1);
               }

               DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
               NBTCompressedStreamTools.a((NBTTagCompound)nbttagcompound, (DataOutput)dataoutputstream);
               dataoutputstream.close();
            }
         } catch (Exception exception) {
            exception.printStackTrace();
         }

         return oshort.shortValue();
      }
   }
}
