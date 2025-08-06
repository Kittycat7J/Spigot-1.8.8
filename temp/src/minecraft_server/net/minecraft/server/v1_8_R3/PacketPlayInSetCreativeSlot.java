package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class PacketPlayInSetCreativeSlot implements Packet<PacketListenerPlayIn> {
   private int slot;
   private ItemStack b;

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.slot = p_a_1_.readShort();
      this.b = p_a_1_.i();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeShort(this.slot);
      p_b_1_.a(this.b);
   }

   public int a() {
      return this.slot;
   }

   public ItemStack getItemStack() {
      return this.b;
   }
}
