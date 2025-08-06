package net.minecraft.server.v1_8_R3;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketLoginOutListener;

public class PacketLoginOutSuccess implements Packet<PacketLoginOutListener> {
   private GameProfile a;

   public PacketLoginOutSuccess() {
   }

   public PacketLoginOutSuccess(GameProfile p_i1046_1_) {
      this.a = p_i1046_1_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      String s = p_a_1_.c(36);
      String s1 = p_a_1_.c(16);
      UUID uuid = UUID.fromString(s);
      this.a = new GameProfile(uuid, s1);
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      UUID uuid = this.a.getId();
      p_b_1_.a(uuid == null?"":uuid.toString());
      p_b_1_.a(this.a.getName());
   }

   public void a(PacketLoginOutListener p_a_1_) {
      p_a_1_.a(this);
   }
}
