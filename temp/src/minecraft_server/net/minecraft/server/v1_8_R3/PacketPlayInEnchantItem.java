package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class PacketPlayInEnchantItem implements Packet<PacketListenerPlayIn> {
   private int a;
   private int b;

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.readByte();
      this.b = p_a_1_.readByte();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeByte(this.a);
      p_b_1_.writeByte(this.b);
   }

   public int a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }
}
