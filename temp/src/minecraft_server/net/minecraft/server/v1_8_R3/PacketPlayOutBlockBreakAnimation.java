package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutBlockBreakAnimation implements Packet<PacketListenerPlayOut> {
   private int a;
   private BlockPosition b;
   private int c;

   public PacketPlayOutBlockBreakAnimation() {
   }

   public PacketPlayOutBlockBreakAnimation(int p_i959_1_, BlockPosition p_i959_2_, int p_i959_3_) {
      this.a = p_i959_1_;
      this.b = p_i959_2_;
      this.c = p_i959_3_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.b = p_a_1_.c();
      this.c = p_a_1_.readUnsignedByte();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.a(this.b);
      p_b_1_.writeByte(this.c);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
