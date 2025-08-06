package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketStatusInListener;

public class PacketStatusInStart implements Packet<PacketStatusInListener> {
   public void a(PacketDataSerializer p_a_1_) throws IOException {
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
   }

   public void a(PacketStatusInListener p_a_1_) {
      p_a_1_.a(this);
   }
}
