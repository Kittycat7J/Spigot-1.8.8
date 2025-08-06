package net.minecraft.server.v1_8_R3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;

public class NBTTagLong extends NBTBase.NBTNumber {
   private long data;

   NBTTagLong() {
   }

   public NBTTagLong(long p_i912_1_) {
      this.data = p_i912_1_;
   }

   void write(DataOutput p_write_1_) throws IOException {
      p_write_1_.writeLong(this.data);
   }

   void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException {
      p_load_3_.a(128L);
      this.data = p_load_1_.readLong();
   }

   public byte getTypeId() {
      return (byte)4;
   }

   public String toString() {
      return "" + this.data + "L";
   }

   public NBTBase clone() {
      return new NBTTagLong(this.data);
   }

   public boolean equals(Object p_equals_1_) {
      if(super.equals(p_equals_1_)) {
         NBTTagLong nbttaglong = (NBTTagLong)p_equals_1_;
         return this.data == nbttaglong.data;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
   }

   public long c() {
      return this.data;
   }

   public int d() {
      return (int)(this.data & -1L);
   }

   public short e() {
      return (short)((int)(this.data & 65535L));
   }

   public byte f() {
      return (byte)((int)(this.data & 255L));
   }

   public double g() {
      return (double)this.data;
   }

   public float h() {
      return (float)this.data;
   }
}
