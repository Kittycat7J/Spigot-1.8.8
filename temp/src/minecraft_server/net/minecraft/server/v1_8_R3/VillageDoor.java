package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;

public class VillageDoor {
   private final BlockPosition a;
   private final BlockPosition b;
   private final EnumDirection c;
   private int d;
   private boolean e;
   private int f;

   public VillageDoor(BlockPosition p_i1205_1_, int p_i1205_2_, int p_i1205_3_, int p_i1205_4_) {
      this(p_i1205_1_, a(p_i1205_2_, p_i1205_3_), p_i1205_4_);
   }

   private static EnumDirection a(int p_a_0_, int p_a_1_) {
      return p_a_0_ < 0?EnumDirection.WEST:(p_a_0_ > 0?EnumDirection.EAST:(p_a_1_ < 0?EnumDirection.NORTH:EnumDirection.SOUTH));
   }

   public VillageDoor(BlockPosition p_i1206_1_, EnumDirection p_i1206_2_, int p_i1206_3_) {
      this.a = p_i1206_1_;
      this.c = p_i1206_2_;
      this.b = p_i1206_1_.shift(p_i1206_2_, 2);
      this.d = p_i1206_3_;
   }

   public int b(int p_b_1_, int p_b_2_, int p_b_3_) {
      return (int)this.a.c((double)p_b_1_, (double)p_b_2_, (double)p_b_3_);
   }

   public int a(BlockPosition p_a_1_) {
      return (int)p_a_1_.i(this.d());
   }

   public int b(BlockPosition p_b_1_) {
      return (int)this.b.i(p_b_1_);
   }

   public boolean c(BlockPosition p_c_1_) {
      int i = p_c_1_.getX() - this.a.getX();
      int j = p_c_1_.getZ() - this.a.getY();
      return i * this.c.getAdjacentX() + j * this.c.getAdjacentZ() >= 0;
   }

   public void a() {
      this.f = 0;
   }

   public void b() {
      ++this.f;
   }

   public int c() {
      return this.f;
   }

   public BlockPosition d() {
      return this.a;
   }

   public BlockPosition e() {
      return this.b;
   }

   public int f() {
      return this.c.getAdjacentX() * 2;
   }

   public int g() {
      return this.c.getAdjacentZ() * 2;
   }

   public int h() {
      return this.d;
   }

   public void a(int p_a_1_) {
      this.d = p_a_1_;
   }

   public boolean i() {
      return this.e;
   }

   public void a(boolean p_a_1_) {
      this.e = p_a_1_;
   }
}
