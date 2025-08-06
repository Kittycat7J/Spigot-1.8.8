package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutPosition implements Packet<PacketListenerPlayOut> {
   private double a;
   private double b;
   private double c;
   private float d;
   private float e;
   private Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> f;

   public PacketPlayOutPosition() {
   }

   public PacketPlayOutPosition(double p_i949_1_, double p_i949_3_, double p_i949_5_, float p_i949_7_, float p_i949_8_, Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> p_i949_9_) {
      this.a = p_i949_1_;
      this.b = p_i949_3_;
      this.c = p_i949_5_;
      this.d = p_i949_7_;
      this.e = p_i949_8_;
      this.f = p_i949_9_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.readDouble();
      this.b = p_a_1_.readDouble();
      this.c = p_a_1_.readDouble();
      this.d = p_a_1_.readFloat();
      this.e = p_a_1_.readFloat();
      this.f = PacketPlayOutPosition.EnumPlayerTeleportFlags.a(p_a_1_.readUnsignedByte());
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeDouble(this.a);
      p_b_1_.writeDouble(this.b);
      p_b_1_.writeDouble(this.c);
      p_b_1_.writeFloat(this.d);
      p_b_1_.writeFloat(this.e);
      p_b_1_.writeByte(PacketPlayOutPosition.EnumPlayerTeleportFlags.a(this.f));
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public static enum EnumPlayerTeleportFlags {
      X(0),
      Y(1),
      Z(2),
      Y_ROT(3),
      X_ROT(4);

      private int f;

      private EnumPlayerTeleportFlags(int p_i948_3_) {
         this.f = p_i948_3_;
      }

      private int a() {
         return 1 << this.f;
      }

      private boolean b(int p_b_1_) {
         return (p_b_1_ & this.a()) == this.a();
      }

      public static Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> a(int p_a_0_) {
         EnumSet enumset = EnumSet.noneOf(PacketPlayOutPosition.EnumPlayerTeleportFlags.class);

         for(PacketPlayOutPosition.EnumPlayerTeleportFlags packetplayoutposition$enumplayerteleportflags : values()) {
            if(packetplayoutposition$enumplayerteleportflags.b(p_a_0_)) {
               enumset.add(packetplayoutposition$enumplayerteleportflags);
            }
         }

         return enumset;
      }

      public static int a(Set<PacketPlayOutPosition.EnumPlayerTeleportFlags> p_a_0_) {
         int i = 0;

         for(PacketPlayOutPosition.EnumPlayerTeleportFlags packetplayoutposition$enumplayerteleportflags : p_a_0_) {
            i |= packetplayoutposition$enumplayerteleportflags.a();
         }

         return i;
      }
   }
}
