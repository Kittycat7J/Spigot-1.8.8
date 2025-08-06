package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class PacketPlayInTransaction implements Packet<PacketListenerPlayIn> {
   private int a;
   private short b;
   private boolean c;

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.readByte();
      this.b = p_a_1_.readShort();
      this.c = p_a_1_.readByte() != 0;
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeByte(this.a);
      p_b_1_.writeShort(this.b);
      p_b_1_.writeByte(this.c?1:0);
   }

   public int a() {
      return this.a;
   }

   public short b() {
      return this.b;
   }
}
