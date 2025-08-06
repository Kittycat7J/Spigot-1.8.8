package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutOpenSignEditor implements Packet<PacketListenerPlayOut> {
   private BlockPosition a;

   public PacketPlayOutOpenSignEditor() {
   }

   public PacketPlayOutOpenSignEditor(BlockPosition p_i989_1_) {
      this.a = p_i989_1_;
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.c();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
   }
}
