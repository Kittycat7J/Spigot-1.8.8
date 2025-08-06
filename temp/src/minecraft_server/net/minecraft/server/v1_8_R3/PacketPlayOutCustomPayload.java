package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutCustomPayload implements Packet<PacketListenerPlayOut> {
   private String a;
   private PacketDataSerializer b;

   public PacketPlayOutCustomPayload() {
   }

   public PacketPlayOutCustomPayload(String p_i973_1_, PacketDataSerializer p_i973_2_) {
      this.a = p_i973_1_;
      this.b = p_i973_2_;
      if(p_i973_2_.writerIndex() > 1048576) {
         throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
      }
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.c(20);
      int i = p_a_1_.readableBytes();
      if(i >= 0 && i <= 1048576) {
         this.b = new PacketDataSerializer(p_a_1_.readBytes(i));
      } else {
         throw new IOException("Payload may not be larger than 1048576 bytes");
      }
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
      p_b_1_.writeBytes((ByteBuf)this.b);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
