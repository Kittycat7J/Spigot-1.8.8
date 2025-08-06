package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutEntity implements Packet<PacketListenerPlayOut> {
   protected int a;
   protected byte b;
   protected byte c;
   protected byte d;
   protected byte e;
   protected byte f;
   protected boolean g;
   protected boolean h;

   public PacketPlayOutEntity() {
   }

   public PacketPlayOutEntity(int p_i988_1_) {
      this.a = p_i988_1_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public String toString() {
      return "Entity_" + super.toString();
   }

   public static class PacketPlayOutEntityLook extends PacketPlayOutEntity {
      public PacketPlayOutEntityLook() {
         this.h = true;
      }

      public PacketPlayOutEntityLook(int p_i987_1_, byte p_i987_2_, byte p_i987_3_, boolean p_i987_4_) {
         super(p_i987_1_);
         this.e = p_i987_2_;
         this.f = p_i987_3_;
         this.h = true;
         this.g = p_i987_4_;
      }

      public void a(PacketDataSerializer p_a_1_) throws IOException {
         super.a(p_a_1_);
         this.e = p_a_1_.readByte();
         this.f = p_a_1_.readByte();
         this.g = p_a_1_.readBoolean();
      }

      public void b(PacketDataSerializer p_b_1_) throws IOException {
         super.b(p_b_1_);
         p_b_1_.writeByte(this.e);
         p_b_1_.writeByte(this.f);
         p_b_1_.writeBoolean(this.g);
      }
   }

   public static class PacketPlayOutRelEntityMove extends PacketPlayOutEntity {
      public PacketPlayOutRelEntityMove() {
      }

      public PacketPlayOutRelEntityMove(int p_i985_1_, byte p_i985_2_, byte p_i985_3_, byte p_i985_4_, boolean p_i985_5_) {
         super(p_i985_1_);
         this.b = p_i985_2_;
         this.c = p_i985_3_;
         this.d = p_i985_4_;
         this.g = p_i985_5_;
      }

      public void a(PacketDataSerializer p_a_1_) throws IOException {
         super.a(p_a_1_);
         this.b = p_a_1_.readByte();
         this.c = p_a_1_.readByte();
         this.d = p_a_1_.readByte();
         this.g = p_a_1_.readBoolean();
      }

      public void b(PacketDataSerializer p_b_1_) throws IOException {
         super.b(p_b_1_);
         p_b_1_.writeByte(this.b);
         p_b_1_.writeByte(this.c);
         p_b_1_.writeByte(this.d);
         p_b_1_.writeBoolean(this.g);
      }
   }

   public static class PacketPlayOutRelEntityMoveLook extends PacketPlayOutEntity {
      public PacketPlayOutRelEntityMoveLook() {
         this.h = true;
      }

      public PacketPlayOutRelEntityMoveLook(int p_i986_1_, byte p_i986_2_, byte p_i986_3_, byte p_i986_4_, byte p_i986_5_, byte p_i986_6_, boolean p_i986_7_) {
         super(p_i986_1_);
         this.b = p_i986_2_;
         this.c = p_i986_3_;
         this.d = p_i986_4_;
         this.e = p_i986_5_;
         this.f = p_i986_6_;
         this.g = p_i986_7_;
         this.h = true;
      }

      public void a(PacketDataSerializer p_a_1_) throws IOException {
         super.a(p_a_1_);
         this.b = p_a_1_.readByte();
         this.c = p_a_1_.readByte();
         this.d = p_a_1_.readByte();
         this.e = p_a_1_.readByte();
         this.f = p_a_1_.readByte();
         this.g = p_a_1_.readBoolean();
      }

      public void b(PacketDataSerializer p_b_1_) throws IOException {
         super.b(p_b_1_);
         p_b_1_.writeByte(this.b);
         p_b_1_.writeByte(this.c);
         p_b_1_.writeByte(this.d);
         p_b_1_.writeByte(this.e);
         p_b_1_.writeByte(this.f);
         p_b_1_.writeBoolean(this.g);
      }
   }
}
