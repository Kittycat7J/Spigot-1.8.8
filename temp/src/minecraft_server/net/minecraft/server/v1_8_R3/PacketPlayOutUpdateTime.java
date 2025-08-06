package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutUpdateTime implements Packet<PacketListenerPlayOut> {
   private long a;
   private long b;

   public PacketPlayOutUpdateTime() {
   }

   public PacketPlayOutUpdateTime(long p_i1023_1_, long p_i1023_3_, boolean p_i1023_5_) {
      this.a = p_i1023_1_;
      this.b = p_i1023_3_;
      if(!p_i1023_5_) {
         this.b = -this.b;
         if(this.b == 0L) {
            this.b = -1L;
         }
      }

   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.readLong();
      this.b = p_a_1_.readLong();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeLong(this.a);
      p_b_1_.writeLong(this.b);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
