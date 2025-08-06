package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_8_R3.WorldServer;

public class PacketPlayInSpectate implements Packet<PacketListenerPlayIn> {
   private UUID a;

   public PacketPlayInSpectate() {
   }

   public PacketPlayInSpectate(UUID p_i1045_1_) {
      this.a = p_i1045_1_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.g();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
   }

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public Entity a(WorldServer p_a_1_) {
      return p_a_1_.getEntity(this.a);
   }
}
