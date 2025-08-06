package net.minecraft.server.v1_8_R3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;

public class NBTTagString extends NBTBase {
   private String data;

   public NBTTagString() {
      this.data = "";
   }

   public NBTTagString(String p_i917_1_) {
      this.data = p_i917_1_;
      if(p_i917_1_ == null) {
         throw new IllegalArgumentException("Empty string not allowed");
      }
   }

   void write(DataOutput p_write_1_) throws IOException {
      p_write_1_.writeUTF(this.data);
   }

   void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException {
      p_load_3_.a(288L);
      this.data = p_load_1_.readUTF();
      p_load_3_.a((long)(16 * this.data.length()));
   }

   public byte getTypeId() {
      return (byte)8;
   }

   public String toString() {
      return "\"" + this.data.replace("\"", "\\\"") + "\"";
   }

   public NBTBase clone() {
      return new NBTTagString(this.data);
   }

   public boolean isEmpty() {
      return this.data.isEmpty();
   }

   public boolean equals(Object p_equals_1_) {
      if(!super.equals(p_equals_1_)) {
         return false;
      } else {
         NBTTagString nbttagstring = (NBTTagString)p_equals_1_;
         return this.data == null && nbttagstring.data == null || this.data != null && this.data.equals(nbttagstring.data);
      }
   }

   public int hashCode() {
      return super.hashCode() ^ this.data.hashCode();
   }

   public String a_() {
      return this.data;
   }
}
