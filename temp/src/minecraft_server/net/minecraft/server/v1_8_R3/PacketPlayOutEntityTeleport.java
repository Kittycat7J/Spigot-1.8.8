package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutEntityTeleport implements Packet<PacketListenerPlayOut> {
   private int a;
   private int b;
   private int c;
   private int d;
   private byte e;
   private byte f;
   private boolean g;

   public PacketPlayOutEntityTeleport() {
   }

   public PacketPlayOutEntityTeleport(Entity p_i1031_1_) {
      this.a = p_i1031_1_.getId();
      this.b = MathHelper.floor(p_i1031_1_.locX * 32.0D);
      this.c = MathHelper.floor(p_i1031_1_.locY * 32.0D);
      this.d = MathHelper.floor(p_i1031_1_.locZ * 32.0D);
      this.e = (byte)((int)(p_i1031_1_.yaw * 256.0F / 360.0F));
      this.f = (byte)((int)(p_i1031_1_.pitch * 256.0F / 360.0F));
      this.g = p_i1031_1_.onGround;
   }

   public PacketPlayOutEntityTeleport(int p_i1032_1_, int p_i1032_2_, int p_i1032_3_, int p_i1032_4_, byte p_i1032_5_, byte p_i1032_6_, boolean p_i1032_7_) {
      this.a = p_i1032_1_;
      this.b = p_i1032_2_;
      this.c = p_i1032_3_;
      this.d = p_i1032_4_;
      this.e = p_i1032_5_;
      this.f = p_i1032_6_;
      this.g = p_i1032_7_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.b = p_a_1_.readInt();
      this.c = p_a_1_.readInt();
      this.d = p_a_1_.readInt();
      this.e = p_a_1_.readByte();
      this.f = p_a_1_.readByte();
      this.g = p_a_1_.readBoolean();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.writeInt(this.b);
      p_b_1_.writeInt(this.c);
      p_b_1_.writeInt(this.d);
      p_b_1_.writeByte(this.e);
      p_b_1_.writeByte(this.f);
      p_b_1_.writeBoolean(this.g);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
