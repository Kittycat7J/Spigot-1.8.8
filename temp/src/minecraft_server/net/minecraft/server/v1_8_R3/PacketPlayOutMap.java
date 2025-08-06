package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.util.Collection;
import net.minecraft.server.v1_8_R3.MapIcon;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutMap implements Packet<PacketListenerPlayOut> {
   private int a;
   private byte b;
   private MapIcon[] c;
   private int d;
   private int e;
   private int f;
   private int g;
   private byte[] h;

   public PacketPlayOutMap() {
   }

   public PacketPlayOutMap(int p_i984_1_, byte p_i984_2_, Collection<MapIcon> p_i984_3_, byte[] p_i984_4_, int p_i984_5_, int p_i984_6_, int p_i984_7_, int p_i984_8_) {
      this.a = p_i984_1_;
      this.b = p_i984_2_;
      this.c = (MapIcon[])p_i984_3_.toArray(new MapIcon[p_i984_3_.size()]);
      this.d = p_i984_5_;
      this.e = p_i984_6_;
      this.f = p_i984_7_;
      this.g = p_i984_8_;
      this.h = new byte[p_i984_7_ * p_i984_8_];

      for(int i = 0; i < p_i984_7_; ++i) {
         for(int j = 0; j < p_i984_8_; ++j) {
            this.h[i + j * p_i984_7_] = p_i984_4_[p_i984_5_ + i + (p_i984_6_ + j) * 128];
         }
      }

   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.b = p_a_1_.readByte();
      this.c = new MapIcon[p_a_1_.e()];

      for(int i = 0; i < this.c.length; ++i) {
         short short1 = (short)p_a_1_.readByte();
         this.c[i] = new MapIcon((byte)(short1 >> 4 & 15), p_a_1_.readByte(), p_a_1_.readByte(), (byte)(short1 & 15));
      }

      this.f = p_a_1_.readUnsignedByte();
      if(this.f > 0) {
         this.g = p_a_1_.readUnsignedByte();
         this.d = p_a_1_.readUnsignedByte();
         this.e = p_a_1_.readUnsignedByte();
         this.h = p_a_1_.a();
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.writeByte(this.b);
      p_b_1_.b(this.c.length);

      for(MapIcon mapicon : this.c) {
         p_b_1_.writeByte((mapicon.getType() & 15) << 4 | mapicon.getRotation() & 15);
         p_b_1_.writeByte(mapicon.getX());
         p_b_1_.writeByte(mapicon.getY());
      }

      p_b_1_.writeByte(this.f);
      if(this.f > 0) {
         p_b_1_.writeByte(this.g);
         p_b_1_.writeByte(this.d);
         p_b_1_.writeByte(this.e);
         p_b_1_.a(this.h);
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
