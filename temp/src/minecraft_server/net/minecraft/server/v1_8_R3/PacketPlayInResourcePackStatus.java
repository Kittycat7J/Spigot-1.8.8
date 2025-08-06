package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class PacketPlayInResourcePackStatus implements Packet<PacketListenerPlayIn> {
   private String a;
   public PacketPlayInResourcePackStatus.EnumResourcePackStatus b;

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.c(40);
      this.b = (PacketPlayInResourcePackStatus.EnumResourcePackStatus)p_a_1_.a(PacketPlayInResourcePackStatus.EnumResourcePackStatus.class);
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
      p_b_1_.a((Enum)this.b);
   }

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public static enum EnumResourcePackStatus {
      SUCCESSFULLY_LOADED,
      DECLINED,
      FAILED_DOWNLOAD,
      ACCEPTED;
   }
}
