package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutServerDifficulty implements Packet<PacketListenerPlayOut> {
   private EnumDifficulty a;
   private boolean b;

   public PacketPlayOutServerDifficulty() {
   }

   public PacketPlayOutServerDifficulty(EnumDifficulty p_i963_1_, boolean p_i963_2_) {
      this.a = p_i963_1_;
      this.b = p_i963_2_;
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = EnumDifficulty.getById(p_a_1_.readUnsignedByte());
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeByte(this.a.a());
   }
}
