package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.security.PrivateKey;
import javax.crypto.SecretKey;
import net.minecraft.server.v1_8_R3.MinecraftEncryption;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketLoginInListener;

public class PacketLoginInEncryptionBegin implements Packet<PacketLoginInListener> {
   private byte[] a = new byte[0];
   private byte[] b = new byte[0];

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.a();
      this.b = p_a_1_.a();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
      p_b_1_.a(this.b);
   }

   public void a(PacketLoginInListener p_a_1_) {
      p_a_1_.a(this);
   }

   public SecretKey a(PrivateKey p_a_1_) {
      return MinecraftEncryption.a(p_a_1_, this.a);
   }

   public byte[] b(PrivateKey p_b_1_) {
      return p_b_1_ == null?this.b:MinecraftEncryption.b(p_b_1_, this.b);
   }
}
