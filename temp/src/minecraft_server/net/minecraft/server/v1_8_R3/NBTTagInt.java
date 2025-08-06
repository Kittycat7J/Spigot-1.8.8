package net.minecraft.server.v1_8_R3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;

public class NBTTagInt extends NBTBase.NBTNumber {
   private int data;

   NBTTagInt() {
   }

   public NBTTagInt(int p_i911_1_) {
      this.data = p_i911_1_;
   }

   void write(DataOutput p_write_1_) throws IOException {
      p_write_1_.writeInt(this.data);
   }

   void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException {
      p_load_3_.a(96L);
      this.data = p_load_1_.readInt();
   }

   public byte getTypeId() {
      return (byte)3;
   }

   public String toString() {
      return "" + this.data;
   }

   public NBTBase clone() {
      return new NBTTagInt(this.data);
   }

   public boolean equals(Object p_equals_1_) {
      if(super.equals(p_equals_1_)) {
         NBTTagInt nbttagint = (NBTTagInt)p_equals_1_;
         return this.data == nbttagint.data;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.data;
   }

   public long c() {
      return (long)this.data;
   }

   public int d() {
      return this.data;
   }

   public short e() {
      return (short)(this.data & '\uffff');
   }

   public byte f() {
      return (byte)(this.data & 255);
   }

   public double g() {
      return (double)this.data;
   }

   public float h() {
      return (float)this.data;
   }
}
