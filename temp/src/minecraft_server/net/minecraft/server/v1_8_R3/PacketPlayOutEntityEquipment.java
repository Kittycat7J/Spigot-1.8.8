package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutEntityEquipment implements Packet<PacketListenerPlayOut> {
   private int a;
   private int b;
   private ItemStack c;

   public PacketPlayOutEntityEquipment() {
   }

   public PacketPlayOutEntityEquipment(int p_i1012_1_, int p_i1012_2_, ItemStack p_i1012_3_) {
      this.a = p_i1012_1_;
      this.b = p_i1012_2_;
      this.c = p_i1012_3_ == null?null:p_i1012_3_.cloneItemStack();
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.b = p_a_1_.readShort();
      this.c = p_a_1_.i();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.writeShort(this.b);
      p_b_1_.a(this.c);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
