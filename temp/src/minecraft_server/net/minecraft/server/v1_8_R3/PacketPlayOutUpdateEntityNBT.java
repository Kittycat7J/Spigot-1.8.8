package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutUpdateEntityNBT implements Packet<PacketListenerPlayOut> {
   private int a;
   private NBTTagCompound b;

   public PacketPlayOutUpdateEntityNBT() {
   }

   public PacketPlayOutUpdateEntityNBT(int p_i976_1_, NBTTagCompound p_i976_2_) {
      this.a = p_i976_1_;
      this.b = p_i976_2_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.b = p_a_1_.h();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.a(this.b);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
