package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutWindowData implements Packet<PacketListenerPlayOut> {
   private int a;
   private int b;
   private int c;

   public PacketPlayOutWindowData() {
   }

   public PacketPlayOutWindowData(int p_i971_1_, int p_i971_2_, int p_i971_3_) {
      this.a = p_i971_1_;
      this.b = p_i971_2_;
      this.c = p_i971_3_;
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.readUnsignedByte();
      this.b = p_a_1_.readShort();
      this.c = p_a_1_.readShort();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeByte(this.a);
      p_b_1_.writeShort(this.b);
      p_b_1_.writeShort(this.c);
   }
}
