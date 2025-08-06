package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class PacketPlayInKeepAlive implements Packet<PacketListenerPlayIn> {
   private int a;

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
   }

   public int a() {
      return this.a;
   }
}
