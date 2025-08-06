package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutExperience implements Packet<PacketListenerPlayOut> {
   private float a;
   private int b;
   private int c;

   public PacketPlayOutExperience() {
   }

   public PacketPlayOutExperience(float p_i1013_1_, int p_i1013_2_, int p_i1013_3_) {
      this.a = p_i1013_1_;
      this.b = p_i1013_2_;
      this.c = p_i1013_3_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.readFloat();
      this.c = p_a_1_.e();
      this.b = p_a_1_.e();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeFloat(this.a);
      p_b_1_.b(this.c);
      p_b_1_.b(this.b);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
