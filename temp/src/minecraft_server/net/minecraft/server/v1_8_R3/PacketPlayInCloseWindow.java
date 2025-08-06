package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class PacketPlayInCloseWindow implements Packet<PacketListenerPlayIn> {
   private int id;

   public PacketPlayInCloseWindow() {
   }

   public PacketPlayInCloseWindow(int p_i326_1_) {
      this.id = p_i326_1_;
   }

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.id = p_a_1_.readByte();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeByte(this.id);
   }
}
