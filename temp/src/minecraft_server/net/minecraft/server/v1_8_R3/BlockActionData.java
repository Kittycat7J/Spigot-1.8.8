package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;

public class BlockActionData {
   private BlockPosition a;
   private Block b;
   private int c;
   private int d;

   public BlockActionData(BlockPosition p_i550_1_, Block p_i550_2_, int p_i550_3_, int p_i550_4_) {
      this.a = p_i550_1_;
      this.c = p_i550_3_;
      this.d = p_i550_4_;
      this.b = p_i550_2_;
   }

   public BlockPosition a() {
      return this.a;
   }

   public int b() {
      return this.c;
   }

   public int c() {
      return this.d;
   }

   public Block d() {
      return this.b;
   }

   public boolean equals(Object p_equals_1_) {
      if(!(p_equals_1_ instanceof BlockActionData)) {
         return false;
      } else {
         BlockActionData blockactiondata = (BlockActionData)p_equals_1_;
         return this.a.equals(blockactiondata.a) && this.c == blockactiondata.c && this.d == blockactiondata.d && this.b == blockactiondata.b;
      }
   }

   public String toString() {
      return "TE(" + this.a + ")," + this.c + "," + this.d + "," + this.b;
   }
}
