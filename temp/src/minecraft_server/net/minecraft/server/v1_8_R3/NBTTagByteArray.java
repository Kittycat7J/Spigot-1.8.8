package net.minecraft.server.v1_8_R3;

import com.google.common.base.Preconditions;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;

public class NBTTagByteArray extends NBTBase {
   private byte[] data;

   NBTTagByteArray() {
   }

   public NBTTagByteArray(byte[] p_i330_1_) {
      this.data = p_i330_1_;
   }

   void write(DataOutput p_write_1_) throws IOException {
      p_write_1_.writeInt(this.data.length);
      p_write_1_.write(this.data);
   }

   void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException {
      p_load_3_.a(192L);
      int i = p_load_1_.readInt();
      Preconditions.checkArgument(i < 16777216);
      p_load_3_.a((long)(8 * i));
      this.data = new byte[i];
      p_load_1_.readFully(this.data);
   }

   public byte getTypeId() {
      return (byte)7;
   }

   public String toString() {
      return "[" + this.data.length + " bytes]";
   }

   public NBTBase clone() {
      byte[] abyte = new byte[this.data.length];
      System.arraycopy(this.data, 0, abyte, 0, this.data.length);
      return new NBTTagByteArray(abyte);
   }

   public boolean equals(Object p_equals_1_) {
      return super.equals(p_equals_1_)?Arrays.equals(this.data, ((NBTTagByteArray)p_equals_1_).data):false;
   }

   public int hashCode() {
      return super.hashCode() ^ Arrays.hashCode(this.data);
   }

   public byte[] c() {
      return this.data;
   }
}
