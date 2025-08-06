package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;

public class ChunkCoordIntPair {
   public final int x;
   public final int z;

   public ChunkCoordIntPair(int p_i551_1_, int p_i551_2_) {
      this.x = p_i551_1_;
      this.z = p_i551_2_;
   }

   public static long a(int p_a_0_, int p_a_1_) {
      return (long)p_a_0_ & 4294967295L | ((long)p_a_1_ & 4294967295L) << 32;
   }

   public int hashCode() {
      int i = 1664525 * this.x + 1013904223;
      int j = 1664525 * (this.z ^ -559038737) + 1013904223;
      return i ^ j;
   }

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(!(p_equals_1_ instanceof ChunkCoordIntPair)) {
         return false;
      } else {
         ChunkCoordIntPair chunkcoordintpair = (ChunkCoordIntPair)p_equals_1_;
         return this.x == chunkcoordintpair.x && this.z == chunkcoordintpair.z;
      }
   }

   public int a() {
      return (this.x << 4) + 8;
   }

   public int b() {
      return (this.z << 4) + 8;
   }

   public int c() {
      return this.x << 4;
   }

   public int d() {
      return this.z << 4;
   }

   public int e() {
      return (this.x << 4) + 15;
   }

   public int f() {
      return (this.z << 4) + 15;
   }

   public BlockPosition a(int p_a_1_, int p_a_2_, int p_a_3_) {
      return new BlockPosition((this.x << 4) + p_a_1_, p_a_2_, (this.z << 4) + p_a_3_);
   }

   public BlockPosition a(int p_a_1_) {
      return new BlockPosition(this.a(), p_a_1_, this.b());
   }

   public String toString() {
      return "[" + this.x + ", " + this.z + "]";
   }
}
