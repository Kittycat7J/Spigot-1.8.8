package net.minecraft.server.v1_8_R3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;

public class NBTTagEnd extends NBTBase {
   void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException {
      p_load_3_.a(64L);
   }

   void write(DataOutput p_write_1_) throws IOException {
   }

   public byte getTypeId() {
      return (byte)0;
   }

   public String toString() {
      return "END";
   }

   public NBTBase clone() {
      return new NBTTagEnd();
   }
}
