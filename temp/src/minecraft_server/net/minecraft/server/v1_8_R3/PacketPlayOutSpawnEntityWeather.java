package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutSpawnEntityWeather implements Packet<PacketListenerPlayOut> {
   private int a;
   private int b;
   private int c;
   private int d;
   private int e;

   public PacketPlayOutSpawnEntityWeather() {
   }

   public PacketPlayOutSpawnEntityWeather(Entity p_i953_1_) {
      this.a = p_i953_1_.getId();
      this.b = MathHelper.floor(p_i953_1_.locX * 32.0D);
      this.c = MathHelper.floor(p_i953_1_.locY * 32.0D);
      this.d = MathHelper.floor(p_i953_1_.locZ * 32.0D);
      if(p_i953_1_ instanceof EntityLightning) {
         this.e = 1;
      }

   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.e = p_a_1_.readByte();
      this.b = p_a_1_.readInt();
      this.c = p_a_1_.readInt();
      this.d = p_a_1_.readInt();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.writeByte(this.e);
      p_b_1_.writeInt(this.b);
      p_b_1_.writeInt(this.c);
      p_b_1_.writeInt(this.d);
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
