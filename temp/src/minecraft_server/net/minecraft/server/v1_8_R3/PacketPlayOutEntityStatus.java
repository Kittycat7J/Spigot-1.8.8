package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutEntityStatus implements Packet<PacketListenerPlayOut> {
   private int a;
   private byte b;

   public PacketPlayOutEntityStatus() {
   }

   public PacketPlayOutEntityStatus(Entity p_i975_1_, byte p_i975_2_) {
      this.a = p_i975_1_.getId();
      this.b = p_i975_2_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.readInt();
      this.b = p_a_1_.readByte();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeInt(this.a);
      p_b_1_.writeByte(this.b);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
