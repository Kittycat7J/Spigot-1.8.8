package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutResourcePackSend implements Packet<PacketListenerPlayOut> {
   private String a;
   private String b;

   public PacketPlayOutResourcePackSend() {
   }

   public PacketPlayOutResourcePackSend(String p_i1000_1_, String p_i1000_2_) {
      this.a = p_i1000_1_;
      this.b = p_i1000_2_;
      if(p_i1000_2_.length() > 40) {
         throw new IllegalArgumentException("Hash is too long (max 40, was " + p_i1000_2_.length() + ")");
      }
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.c(32767);
      this.b = p_a_1_.c(40);
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
      p_b_1_.a(this.b);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
