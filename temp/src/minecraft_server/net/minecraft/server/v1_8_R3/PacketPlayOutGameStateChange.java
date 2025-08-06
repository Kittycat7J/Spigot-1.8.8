package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutGameStateChange implements Packet<PacketListenerPlayOut> {
   public static final String[] a = new String[]{"tile.bed.notValid"};
   private int b;
   private float c;

   public PacketPlayOutGameStateChange() {
   }

   public PacketPlayOutGameStateChange(int p_i978_1_, float p_i978_2_) {
      this.b = p_i978_1_;
      this.c = p_i978_2_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.b = p_a_1_.readUnsignedByte();
      this.c = p_a_1_.readFloat();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeByte(this.b);
      p_b_1_.writeFloat(this.c);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
