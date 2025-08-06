package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import gnu.trove.TDecorators;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.ReportedException;
import net.minecraft.server.v1_8_R3.Vector3f;
import org.apache.commons.lang3.ObjectUtils;

public class DataWatcher {
   private final Entity a;
   private boolean b = true;
   private static final TObjectIntMap classToId = new TObjectIntHashMap(10, 0.5F, -1);
   private final TIntObjectMap dataValues = new TIntObjectHashMap(10, 0.5F, -1);
   private static final Map<Class<?>, Integer> c = TDecorators.wrap(classToId);
   private final Map<Integer, DataWatcher.WatchableObject> d;
   private boolean e;
   private ReadWriteLock f;

   static {
      classToId.put(Byte.class, 0);
      classToId.put(Short.class, 1);
      classToId.put(Integer.class, 2);
      classToId.put(Float.class, 3);
      classToId.put(String.class, 4);
      classToId.put(ItemStack.class, 5);
      classToId.put(BlockPosition.class, 6);
      classToId.put(Vector3f.class, 7);
   }

   public DataWatcher(Entity p_i52_1_) {
      this.d = TDecorators.wrap(this.dataValues);
      this.f = new ReentrantReadWriteLock();
      this.a = p_i52_1_;
   }

   public <T> void a(int p_a_1_, T p_a_2_) {
      int i = classToId.get(p_a_2_.getClass());
      if(i == -1) {
         throw new IllegalArgumentException("Unknown data type: " + p_a_2_.getClass());
      } else if(p_a_1_ > 31) {
         throw new IllegalArgumentException("Data value id is too big with " + p_a_1_ + "! (Max is " + 31 + ")");
      } else if(this.dataValues.containsKey(p_a_1_)) {
         throw new IllegalArgumentException("Duplicate id value for " + p_a_1_ + "!");
      } else {
         DataWatcher.WatchableObject datawatcher$watchableobject = new DataWatcher.WatchableObject(i, p_a_1_, p_a_2_);
         this.f.writeLock().lock();
         this.dataValues.put(p_a_1_, datawatcher$watchableobject);
         this.f.writeLock().unlock();
         this.b = false;
      }
   }

   public void add(int p_add_1_, int p_add_2_) {
      DataWatcher.WatchableObject datawatcher$watchableobject = new DataWatcher.WatchableObject(p_add_2_, p_add_1_, (Object)null);
      this.f.writeLock().lock();
      this.dataValues.put(p_add_1_, datawatcher$watchableobject);
      this.f.writeLock().unlock();
      this.b = false;
   }

   public byte getByte(int p_getByte_1_) {
      return ((Byte)this.j(p_getByte_1_).b()).byteValue();
   }

   public short getShort(int p_getShort_1_) {
      return ((Short)this.j(p_getShort_1_).b()).shortValue();
   }

   public int getInt(int p_getInt_1_) {
      return ((Integer)this.j(p_getInt_1_).b()).intValue();
   }

   public float getFloat(int p_getFloat_1_) {
      return ((Float)this.j(p_getFloat_1_).b()).floatValue();
   }

   public String getString(int p_getString_1_) {
      return (String)this.j(p_getString_1_).b();
   }

   public ItemStack getItemStack(int p_getItemStack_1_) {
      return (ItemStack)this.j(p_getItemStack_1_).b();
   }

   private DataWatcher.WatchableObject j(int p_j_1_) {
      this.f.readLock().lock();

      DataWatcher.WatchableObject datawatcher$watchableobject;
      try {
         datawatcher$watchableobject = (DataWatcher.WatchableObject)this.dataValues.get(p_j_1_);
      } catch (Throwable throwable) {
         CrashReport crashreport = CrashReport.a(throwable, "Getting synched entity data");
         CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Synched entity data");
         crashreportsystemdetails.a((String)"Data ID", Integer.valueOf(p_j_1_));
         throw new ReportedException(crashreport);
      }

      this.f.readLock().unlock();
      return datawatcher$watchableobject;
   }

   public Vector3f h(int p_h_1_) {
      return (Vector3f)this.j(p_h_1_).b();
   }

   public <T> void watch(int p_watch_1_, T p_watch_2_) {
      DataWatcher.WatchableObject datawatcher$watchableobject = this.j(p_watch_1_);
      if(ObjectUtils.notEqual(p_watch_2_, datawatcher$watchableobject.b())) {
         datawatcher$watchableobject.a(p_watch_2_);
         this.a.i(p_watch_1_);
         datawatcher$watchableobject.a(true);
         this.e = true;
      }

   }

   public void update(int p_update_1_) {
      this.j(p_update_1_).d = true;
      this.e = true;
   }

   public boolean a() {
      return this.e;
   }

   public static void a(List<DataWatcher.WatchableObject> p_a_0_, PacketDataSerializer p_a_1_) throws IOException {
      if(p_a_0_ != null) {
         for(DataWatcher.WatchableObject datawatcher$watchableobject : p_a_0_) {
            a(p_a_1_, datawatcher$watchableobject);
         }
      }

      p_a_1_.writeByte(127);
   }

   public List<DataWatcher.WatchableObject> b() {
      ArrayList arraylist = null;
      if(this.e) {
         this.f.readLock().lock();

         for(DataWatcher.WatchableObject datawatcher$watchableobject : this.dataValues.valueCollection()) {
            if(datawatcher$watchableobject.d()) {
               datawatcher$watchableobject.a(false);
               if(arraylist == null) {
                  arraylist = Lists.newArrayList();
               }

               if(datawatcher$watchableobject.b() instanceof ItemStack) {
                  datawatcher$watchableobject = new DataWatcher.WatchableObject(datawatcher$watchableobject.c(), datawatcher$watchableobject.a(), ((ItemStack)datawatcher$watchableobject.b()).cloneItemStack());
               }

               arraylist.add(datawatcher$watchableobject);
            }
         }

         this.f.readLock().unlock();
      }

      this.e = false;
      return arraylist;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.f.readLock().lock();

      for(DataWatcher.WatchableObject datawatcher$watchableobject : this.dataValues.valueCollection()) {
         a(p_a_1_, datawatcher$watchableobject);
      }

      this.f.readLock().unlock();
      p_a_1_.writeByte(127);
   }

   public List<DataWatcher.WatchableObject> c() {
      ArrayList arraylist = Lists.newArrayList();
      this.f.readLock().lock();
      arraylist.addAll(this.dataValues.valueCollection());

      for(int i = 0; i < arraylist.size(); ++i) {
         DataWatcher.WatchableObject datawatcher$watchableobject = (DataWatcher.WatchableObject)arraylist.get(i);
         if(datawatcher$watchableobject.b() instanceof ItemStack) {
            datawatcher$watchableobject = new DataWatcher.WatchableObject(datawatcher$watchableobject.c(), datawatcher$watchableobject.a(), ((ItemStack)datawatcher$watchableobject.b()).cloneItemStack());
            arraylist.set(i, datawatcher$watchableobject);
         }
      }

      this.f.readLock().unlock();
      return arraylist;
   }

   private static void a(PacketDataSerializer p_a_0_, DataWatcher.WatchableObject p_a_1_) throws IOException {
      int i = (p_a_1_.c() << 5 | p_a_1_.a() & 31) & 255;
      p_a_0_.writeByte(i);
      switch(p_a_1_.c()) {
      case 0:
         p_a_0_.writeByte(((Byte)p_a_1_.b()).byteValue());
         break;
      case 1:
         p_a_0_.writeShort(((Short)p_a_1_.b()).shortValue());
         break;
      case 2:
         p_a_0_.writeInt(((Integer)p_a_1_.b()).intValue());
         break;
      case 3:
         p_a_0_.writeFloat(((Float)p_a_1_.b()).floatValue());
         break;
      case 4:
         p_a_0_.a((String)p_a_1_.b());
         break;
      case 5:
         ItemStack itemstack = (ItemStack)p_a_1_.b();
         p_a_0_.a(itemstack);
         break;
      case 6:
         BlockPosition blockposition = (BlockPosition)p_a_1_.b();
         p_a_0_.writeInt(blockposition.getX());
         p_a_0_.writeInt(blockposition.getY());
         p_a_0_.writeInt(blockposition.getZ());
         break;
      case 7:
         Vector3f vector3f = (Vector3f)p_a_1_.b();
         p_a_0_.writeFloat(vector3f.getX());
         p_a_0_.writeFloat(vector3f.getY());
         p_a_0_.writeFloat(vector3f.getZ());
      }

   }

   public static List<DataWatcher.WatchableObject> b(PacketDataSerializer p_b_0_) throws IOException {
      ArrayList arraylist = null;

      for(byte b0 = p_b_0_.readByte(); b0 != 127; b0 = p_b_0_.readByte()) {
         if(arraylist == null) {
            arraylist = Lists.newArrayList();
         }

         int i = (b0 & 224) >> 5;
         int j = b0 & 31;
         DataWatcher.WatchableObject datawatcher$watchableobject = null;
         switch(i) {
         case 0:
            datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, Byte.valueOf(p_b_0_.readByte()));
            break;
         case 1:
            datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, Short.valueOf(p_b_0_.readShort()));
            break;
         case 2:
            datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, Integer.valueOf(p_b_0_.readInt()));
            break;
         case 3:
            datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, Float.valueOf(p_b_0_.readFloat()));
            break;
         case 4:
            datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, p_b_0_.c(32767));
            break;
         case 5:
            datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, p_b_0_.i());
            break;
         case 6:
            int k = p_b_0_.readInt();
            int l = p_b_0_.readInt();
            int i1 = p_b_0_.readInt();
            datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, new BlockPosition(k, l, i1));
            break;
         case 7:
            float f = p_b_0_.readFloat();
            float f1 = p_b_0_.readFloat();
            float f2 = p_b_0_.readFloat();
            datawatcher$watchableobject = new DataWatcher.WatchableObject(i, j, new Vector3f(f, f1, f2));
         }

         arraylist.add(datawatcher$watchableobject);
      }

      return arraylist;
   }

   public boolean d() {
      return this.b;
   }

   public void e() {
      this.e = false;
   }

   public static class WatchableObject {
      private final int a;
      private final int b;
      private Object c;
      private boolean d;

      public WatchableObject(int p_i279_1_, int p_i279_2_, Object p_i279_3_) {
         this.b = p_i279_2_;
         this.c = p_i279_3_;
         this.a = p_i279_1_;
         this.d = true;
      }

      public int a() {
         return this.b;
      }

      public void a(Object p_a_1_) {
         this.c = p_a_1_;
      }

      public Object b() {
         return this.c;
      }

      public int c() {
         return this.a;
      }

      public boolean d() {
         return this.d;
      }

      public void a(boolean p_a_1_) {
         this.d = p_a_1_;
      }
   }
}
