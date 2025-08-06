package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketStatusOutListener;

public class PacketStatusOutPong implements Packet<PacketStatusOutListener> {
   private long a;

   public PacketStatusOutPong() {
   }

   public PacketStatusOutPong(long p_i1051_1_) {
      this.a = p_i1051_1_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.readLong();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeLong(this.a);
   }

   public void a(PacketStatusOutListener p_a_1_) {
      p_a_1_.a(this);
   }
}
