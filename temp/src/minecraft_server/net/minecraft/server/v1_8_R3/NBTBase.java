package net.minecraft.server.v1_8_R3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.server.v1_8_R3.NBTReadLimiter;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagByteArray;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import net.minecraft.server.v1_8_R3.NBTTagEnd;
import net.minecraft.server.v1_8_R3.NBTTagFloat;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagIntArray;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagLong;
import net.minecraft.server.v1_8_R3.NBTTagShort;
import net.minecraft.server.v1_8_R3.NBTTagString;

public abstract class NBTBase {
   public static final String[] a = new String[]{"END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]"};

   abstract void write(DataOutput var1) throws IOException;

   abstract void load(DataInput var1, int var2, NBTReadLimiter var3) throws IOException;

   public abstract String toString();

   public abstract byte getTypeId();

   protected static NBTBase createTag(byte p_createTag_0_) {
      switch(p_createTag_0_) {
      case 0:
         return new NBTTagEnd();
      case 1:
         return new NBTTagByte();
      case 2:
         return new NBTTagShort();
      case 3:
         return new NBTTagInt();
      case 4:
         return new NBTTagLong();
      case 5:
         return new NBTTagFloat();
      case 6:
         return new NBTTagDouble();
      case 7:
         return new NBTTagByteArray();
      case 8:
         return new NBTTagString();
      case 9:
         return new NBTTagList();
      case 10:
         return new NBTTagCompound();
      case 11:
         return new NBTTagIntArray();
      default:
         return null;
      }
   }

   public abstract NBTBase clone();

   public boolean isEmpty() {
      return false;
   }

   public boolean equals(Object p_equals_1_) {
      if(!(p_equals_1_ instanceof NBTBase)) {
         return false;
      } else {
         NBTBase nbtbase = (NBTBase)p_equals_1_;
         return this.getTypeId() == nbtbase.getTypeId();
      }
   }

   public int hashCode() {
      return this.getTypeId();
   }

   protected String a_() {
      return this.toString();
   }

   public abstract static class NBTNumber extends NBTBase {
      public abstract long c();

      public abstract int d();

      public abstract short e();

      public abstract byte f();

      public abstract double g();

      public abstract float h();
   }
}
