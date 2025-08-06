package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagEnd;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagIntArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NBTTagList extends NBTBase {
   private static final Logger b = LogManager.getLogger();
   private List<NBTBase> list = Lists.<NBTBase>newArrayList();
   private byte type = 0;

   void write(DataOutput p_write_1_) throws IOException {
      if(!this.list.isEmpty()) {
         this.type = ((NBTBase)this.list.get(0)).getTypeId();
      } else {
         this.type = 0;
      }

      p_write_1_.writeByte(this.type);
      p_write_1_.writeInt(this.list.size());

      for(int i = 0; i < this.list.size(); ++i) {
         ((NBTBase)this.list.get(i)).write(p_write_1_);
      }

   }

   void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException {
      p_load_3_.a(296L);
      if(p_load_2_ > 512) {
         throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
      } else {
         this.type = p_load_1_.readByte();
         int i = p_load_1_.readInt();
         p_load_3_.a((long)(i * 8));
         if(this.type == 0 && i > 0) {
            throw new RuntimeException("Missing type on ListTag");
         } else {
            p_load_3_.a(32L * (long)i);
            this.list = Lists.<NBTBase>newArrayListWithCapacity(i);

            for(int j = 0; j < i; ++j) {
               NBTBase nbtbase = NBTBase.createTag(this.type);
               nbtbase.load(p_load_1_, p_load_2_ + 1, p_load_3_);
               this.list.add(nbtbase);
            }

         }
      }
   }

   public byte getTypeId() {
      return (byte)9;
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder("[");

      for(int i = 0; i < this.list.size(); ++i) {
         if(i != 0) {
            stringbuilder.append(',');
         }

         stringbuilder.append(i).append(':').append(this.list.get(i));
      }

      return stringbuilder.append(']').toString();
   }

   public void add(NBTBase p_add_1_) {
      if(p_add_1_.getTypeId() == 0) {
         b.warn("Invalid TagEnd added to ListTag");
      } else {
         if(this.type == 0) {
            this.type = p_add_1_.getTypeId();
         } else if(this.type != p_add_1_.getTypeId()) {
            b.warn("Adding mismatching tag types to tag list");
            return;
         }

         this.list.add(p_add_1_);
      }

   }

   public void a(int p_a_1_, NBTBase p_a_2_) {
      if(p_a_2_.getTypeId() == 0) {
         b.warn("Invalid TagEnd added to ListTag");
      } else if(p_a_1_ >= 0 && p_a_1_ < this.list.size()) {
         if(this.type == 0) {
            this.type = p_a_2_.getTypeId();
         } else if(this.type != p_a_2_.getTypeId()) {
            b.warn("Adding mismatching tag types to tag list");
            return;
         }

         this.list.set(p_a_1_, p_a_2_);
      } else {
         b.warn("index out of bounds to set tag in tag list");
      }

   }

   public NBTBase a(int p_a_1_) {
      return (NBTBase)this.list.remove(p_a_1_);
   }

   public boolean isEmpty() {
      return this.list.isEmpty();
   }

   public NBTTagCompound get(int p_get_1_) {
      if(p_get_1_ >= 0 && p_get_1_ < this.list.size()) {
         NBTBase nbtbase = (NBTBase)this.list.get(p_get_1_);
         return nbtbase.getTypeId() == 10?(NBTTagCompound)nbtbase:new NBTTagCompound();
      } else {
         return new NBTTagCompound();
      }
   }

   public int[] c(int p_c_1_) {
      if(p_c_1_ >= 0 && p_c_1_ < this.list.size()) {
         NBTBase nbtbase = (NBTBase)this.list.get(p_c_1_);
         return nbtbase.getTypeId() == 11?((NBTTagIntArray)nbtbase).c():new int[0];
      } else {
         return new int[0];
      }
   }

   public double d(int p_d_1_) {
      if(p_d_1_ >= 0 && p_d_1_ < this.list.size()) {
         NBTBase nbtbase = (NBTBase)this.list.get(p_d_1_);
         return nbtbase.getTypeId() == 6?((NBTTagDouble)nbtbase).g():0.0D;
      } else {
         return 0.0D;
      }
   }

   public float e(int p_e_1_) {
      if(p_e_1_ >= 0 && p_e_1_ < this.list.size()) {
         NBTBase nbtbase = (NBTBase)this.list.get(p_e_1_);
         return nbtbase.getTypeId() == 5?((NBTTagFloat)nbtbase).h():0.0F;
      } else {
         return 0.0F;
      }
   }

   public String getString(int p_getString_1_) {
      if(p_getString_1_ >= 0 && p_getString_1_ < this.list.size()) {
         NBTBase nbtbase = (NBTBase)this.list.get(p_getString_1_);
         return nbtbase.getTypeId() == 8?nbtbase.a_():nbtbase.toString();
      } else {
         return "";
      }
   }

   public NBTBase g(int p_g_1_) {
      return (NBTBase)(p_g_1_ >= 0 && p_g_1_ < this.list.size()?(NBTBase)this.list.get(p_g_1_):new NBTTagEnd());
   }

   public int size() {
      return this.list.size();
   }

   public NBTBase clone() {
      NBTTagList nbttaglist = new NBTTagList();
      nbttaglist.type = this.type;

      for(NBTBase nbtbase : this.list) {
         NBTBase nbtbase1 = nbtbase.clone();
         nbttaglist.list.add(nbtbase1);
      }

      return nbttaglist;
   }

   public boolean equals(Object p_equals_1_) {
      if(super.equals(p_equals_1_)) {
         NBTTagList nbttaglist = (NBTTagList)p_equals_1_;
         if(this.type == nbttaglist.type) {
            return this.list.equals(nbttaglist.list);
         }
      }

      return false;
   }

   public int hashCode() {
      return super.hashCode() ^ this.list.hashCode();
   }

   public int f() {
      return this.type;
   }
}
