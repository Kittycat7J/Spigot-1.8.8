package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.MobEffect;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutRemoveEntityEffect implements Packet<PacketListenerPlayOut> {
   private int a;
   private int b;

   public PacketPlayOutRemoveEntityEffect() {
   }

   public PacketPlayOutRemoveEntityEffect(int p_i999_1_, MobEffect p_i999_2_) {
      this.a = p_i999_1_;
      this.b = p_i999_2_.getEffectId();
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.b = p_a_1_.readUnsignedByte();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.writeByte(this.b);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
