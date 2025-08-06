package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketLoginOutListener;

public class PacketLoginOutSetCompression implements Packet<PacketLoginOutListener> {
   private int a;

   public PacketLoginOutSetCompression() {
   }

   public PacketLoginOutSetCompression(int p_i1048_1_) {
      this.a = p_i1048_1_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
   }

   public void a(PacketLoginOutListener p_a_1_) {
      p_a_1_.a(this);
   }
}
