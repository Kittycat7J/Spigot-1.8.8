package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutSpawnEntity implements Packet<PacketListenerPlayOut> {
   private int a;
   private int b;
   private int c;
   private int d;
   private int e;
   private int f;
   private int g;
   private int h;
   private int i;
   private int j;
   private int k;

   public PacketPlayOutSpawnEntity() {
   }

   public PacketPlayOutSpawnEntity(Entity p_i950_1_, int p_i950_2_) {
      this(p_i950_1_, p_i950_2_, 0);
   }

   public PacketPlayOutSpawnEntity(Entity p_i951_1_, int p_i951_2_, int p_i951_3_) {
      this.a = p_i951_1_.getId();
      this.b = MathHelper.floor(p_i951_1_.locX * 32.0D);
      this.c = MathHelper.floor(p_i951_1_.locY * 32.0D);
      this.d = MathHelper.floor(p_i951_1_.locZ * 32.0D);
      this.h = MathHelper.d(p_i951_1_.pitch * 256.0F / 360.0F);
      this.i = MathHelper.d(p_i951_1_.yaw * 256.0F / 360.0F);
      this.j = p_i951_2_;
      this.k = p_i951_3_;
      if(p_i951_3_ > 0) {
         double d0 = p_i951_1_.motX;
         double d1 = p_i951_1_.motY;
         double d2 = p_i951_1_.motZ;
         double d3 = 3.9D;
         if(d0 < -d3) {
            d0 = -d3;
         }

         if(d1 < -d3) {
            d1 = -d3;
         }

         if(d2 < -d3) {
            d2 = -d3;
         }

         if(d0 > d3) {
            d0 = d3;
         }

         if(d1 > d3) {
            d1 = d3;
         }

         if(d2 > d3) {
            d2 = d3;
         }

         this.e = (int)(d0 * 8000.0D);
         this.f = (int)(d1 * 8000.0D);
         this.g = (int)(d2 * 8000.0D);
      }

   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.j = p_a_1_.readByte();
      this.b = p_a_1_.readInt();
      this.c = p_a_1_.readInt();
      this.d = p_a_1_.readInt();
      this.h = p_a_1_.readByte();
      this.i = p_a_1_.readByte();
      this.k = p_a_1_.readInt();
      if(this.k > 0) {
         this.e = p_a_1_.readShort();
         this.f = p_a_1_.readShort();
         this.g = p_a_1_.readShort();
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.writeByte(this.j);
      p_b_1_.writeInt(this.b);
      p_b_1_.writeInt(this.c);
      p_b_1_.writeInt(this.d);
      p_b_1_.writeByte(this.h);
      p_b_1_.writeByte(this.i);
      p_b_1_.writeInt(this.k);
      if(this.k > 0) {
         p_b_1_.writeShort(this.e);
         p_b_1_.writeShort(this.f);
         p_b_1_.writeShort(this.g);
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(int p_a_1_) {
      this.b = p_a_1_;
   }

   public void b(int p_b_1_) {
      this.c = p_b_1_;
   }

   public void c(int p_c_1_) {
      this.d = p_c_1_;
   }

   public void d(int p_d_1_) {
      this.e = p_d_1_;
   }

   public void e(int p_e_1_) {
      this.f = p_e_1_;
   }

   public void f(int p_f_1_) {
      this.g = p_f_1_;
   }
}
