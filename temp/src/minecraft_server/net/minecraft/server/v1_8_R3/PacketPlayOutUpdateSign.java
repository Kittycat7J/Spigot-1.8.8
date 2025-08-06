package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.World;

public class PacketPlayOutUpdateSign implements Packet<PacketListenerPlayOut> {
   private World a;
   private BlockPosition b;
   private IChatBaseComponent[] c;

   public PacketPlayOutUpdateSign() {
   }

   public PacketPlayOutUpdateSign(World p_i1028_1_, BlockPosition p_i1028_2_, IChatBaseComponent[] p_i1028_3_) {
      this.a = p_i1028_1_;
      this.b = p_i1028_2_;
      this.c = new IChatBaseComponent[]{p_i1028_3_[0], p_i1028_3_[1], p_i1028_3_[2], p_i1028_3_[3]};
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.b = p_a_1_.c();
      this.c = new IChatBaseComponent[4];

      for(int i = 0; i < 4; ++i) {
         this.c[i] = p_a_1_.d();
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.b);

      for(int i = 0; i < 4; ++i) {
         p_b_1_.a(this.c[i]);
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
