package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutChat implements Packet<PacketListenerPlayOut> {
   private IChatBaseComponent a;
   public BaseComponent[] components;
   private byte b;

   public PacketPlayOutChat() {
   }

   public PacketPlayOutChat(IChatBaseComponent p_i470_1_) {
      this(p_i470_1_, (byte)1);
   }

   public PacketPlayOutChat(IChatBaseComponent p_i471_1_, byte p_i471_2_) {
      this.a = p_i471_1_;
      this.b = p_i471_2_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.d();
      this.b = p_a_1_.readByte();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      if(this.components != null) {
         p_b_1_.a(ComponentSerializer.toString(this.components));
      } else {
         p_b_1_.a(this.a);
      }

      p_b_1_.writeByte(this.b);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public boolean b() {
      return this.b == 1 || this.b == 2;
   }
}
