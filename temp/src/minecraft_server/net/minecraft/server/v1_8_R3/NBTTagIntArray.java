package net.minecraft.server.v1_8_R3;

import com.google.common.base.Preconditions;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;

public class NBTTagIntArray extends NBTBase {
   private int[] data;

   NBTTagIntArray() {
   }

   public NBTTagIntArray(int[] p_i412_1_) {
      this.data = p_i412_1_;
   }

   void write(DataOutput p_write_1_) throws IOException {
      p_write_1_.writeInt(this.data.length);

      for(int i = 0; i < this.data.length; ++i) {
         p_write_1_.writeInt(this.data[i]);
      }

   }

   void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException {
      p_load_3_.a(192L);
      int i = p_load_1_.readInt();
      Preconditions.checkArgument(i < 16777216);
      p_load_3_.a((long)(32 * i));
      this.data = new int[i];

      for(int j = 0; j < i; ++j) {
         this.data[j] = p_load_1_.readInt();
      }

   }

   public byte getTypeId() {
      return (byte)11;
   }

   public String toString() {
      String s = "[";

      for(int i : this.data) {
         s = s + i + ",";
      }

      return s + "]";
   }

   public NBTBase clone() {
      int[] aint = new int[this.data.length];
      System.arraycopy(this.data, 0, aint, 0, this.data.length);
      return new NBTTagIntArray(aint);
   }

   public boolean equals(Object p_equals_1_) {
      return super.equals(p_equals_1_)?Arrays.equals(this.data, ((NBTTagIntArray)p_equals_1_).data):false;
   }

   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.data);
   }

   public int[] c() {
      return this.data;
   }
}
