package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class PacketPlayInUpdateSign implements Packet<PacketListenerPlayIn> {
   private BlockPosition a;
   private IChatBaseComponent[] b;

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.c();
      this.b = new IChatBaseComponent[4];

      for(int i = 0; i < 4; ++i) {
         String s = p_a_1_.c(384);
         IChatBaseComponent ichatbasecomponent = IChatBaseComponent.ChatSerializer.a(s);
         this.b[i] = ichatbasecomponent;
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);

      for(int i = 0; i < 4; ++i) {
         IChatBaseComponent ichatbasecomponent = this.b[i];
         String s = IChatBaseComponent.ChatSerializer.a(ichatbasecomponent);
         p_b_1_.a(s);
      }

   }

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public BlockPosition a() {
      return this.a;
   }

   public IChatBaseComponent[] b() {
      return this.b;
   }
}
