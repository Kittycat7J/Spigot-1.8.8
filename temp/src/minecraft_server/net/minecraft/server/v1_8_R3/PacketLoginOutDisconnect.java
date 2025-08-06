package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketLoginOutListener;

public class PacketLoginOutDisconnect implements Packet<PacketLoginOutListener> {
   private IChatBaseComponent a;

   public PacketLoginOutDisconnect() {
   }

   public PacketLoginOutDisconnect(IChatBaseComponent p_i1049_1_) {
      this.a = p_i1049_1_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.d();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
   }

   public void a(PacketLoginOutListener p_a_1_) {
      p_a_1_.a(this);
   }
}
