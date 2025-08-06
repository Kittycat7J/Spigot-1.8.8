package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.PlayerAbilities;

public class PacketPlayOutAbilities implements Packet<PacketListenerPlayOut> {
   private boolean a;
   private boolean b;
   private boolean c;
   private boolean d;
   private float e;
   private float f;

   public PacketPlayOutAbilities() {
   }

   public PacketPlayOutAbilities(PlayerAbilities p_i990_1_) {
      this.a(p_i990_1_.isInvulnerable);
      this.b(p_i990_1_.isFlying);
      this.c(p_i990_1_.canFly);
      this.d(p_i990_1_.canInstantlyBuild);
      this.a(p_i990_1_.a());
      this.b(p_i990_1_.b());
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      byte b0 = p_a_1_.readByte();
      this.a((b0 & 1) > 0);
      this.b((b0 & 2) > 0);
      this.c((b0 & 4) > 0);
      this.d((b0 & 8) > 0);
      this.a(p_a_1_.readFloat());
      this.b(p_a_1_.readFloat());
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      byte b0 = 0;
      if(this.a()) {
         b0 = (byte)(b0 | 1);
      }

      if(this.b()) {
         b0 = (byte)(b0 | 2);
      }

      if(this.c()) {
         b0 = (byte)(b0 | 4);
      }

      if(this.d()) {
         b0 = (byte)(b0 | 8);
      }

      p_b_1_.writeByte(b0);
      p_b_1_.writeFloat(this.e);
      p_b_1_.writeFloat(this.f);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public boolean a() {
      return this.a;
   }

   public void a(boolean p_a_1_) {
      this.a = p_a_1_;
   }

   public boolean b() {
      return this.b;
   }

   public void b(boolean p_b_1_) {
      this.b = p_b_1_;
   }

   public boolean c() {
      return this.c;
   }

   public void c(boolean p_c_1_) {
      this.c = p_c_1_;
   }

   public boolean d() {
      return this.d;
   }

   public void d(boolean p_d_1_) {
      this.d = p_d_1_;
   }

   public void a(float p_a_1_) {
      this.e = p_a_1_;
   }

   public void b(float p_b_1_) {
      this.f = p_b_1_;
   }
}
