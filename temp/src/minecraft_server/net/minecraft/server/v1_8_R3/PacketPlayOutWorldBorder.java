package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.WorldBorder;

public class PacketPlayOutWorldBorder implements Packet<PacketListenerPlayOut> {
   private PacketPlayOutWorldBorder.EnumWorldBorderAction a;
   private int b;
   private double c;
   private double d;
   private double e;
   private double f;
   private long g;
   private int h;
   private int i;

   public PacketPlayOutWorldBorder() {
   }

   public PacketPlayOutWorldBorder(WorldBorder p_i1004_1_, PacketPlayOutWorldBorder.EnumWorldBorderAction p_i1004_2_) {
      this.a = p_i1004_2_;
      this.c = p_i1004_1_.getCenterX();
      this.d = p_i1004_1_.getCenterZ();
      this.f = p_i1004_1_.getSize();
      this.e = p_i1004_1_.j();
      this.g = p_i1004_1_.i();
      this.b = p_i1004_1_.l();
      this.i = p_i1004_1_.getWarningDistance();
      this.h = p_i1004_1_.getWarningTime();
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = (PacketPlayOutWorldBorder.EnumWorldBorderAction)p_a_1_.a(PacketPlayOutWorldBorder.EnumWorldBorderAction.class);
      switch(this.a) {
      case SET_SIZE:
         this.e = p_a_1_.readDouble();
         break;
      case LERP_SIZE:
         this.f = p_a_1_.readDouble();
         this.e = p_a_1_.readDouble();
         this.g = p_a_1_.f();
         break;
      case SET_CENTER:
         this.c = p_a_1_.readDouble();
         this.d = p_a_1_.readDouble();
         break;
      case SET_WARNING_BLOCKS:
         this.i = p_a_1_.e();
         break;
      case SET_WARNING_TIME:
         this.h = p_a_1_.e();
         break;
      case INITIALIZE:
         this.c = p_a_1_.readDouble();
         this.d = p_a_1_.readDouble();
         this.f = p_a_1_.readDouble();
         this.e = p_a_1_.readDouble();
         this.g = p_a_1_.f();
         this.b = p_a_1_.e();
         this.i = p_a_1_.e();
         this.h = p_a_1_.e();
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a((Enum)this.a);
      switch(this.a) {
      case SET_SIZE:
         p_b_1_.writeDouble(this.e);
         break;
      case LERP_SIZE:
         p_b_1_.writeDouble(this.f);
         p_b_1_.writeDouble(this.e);
         p_b_1_.b(this.g);
         break;
      case SET_CENTER:
         p_b_1_.writeDouble(this.c);
         p_b_1_.writeDouble(this.d);
         break;
      case SET_WARNING_BLOCKS:
         p_b_1_.b(this.i);
         break;
      case SET_WARNING_TIME:
         p_b_1_.b(this.h);
         break;
      case INITIALIZE:
         p_b_1_.writeDouble(this.c);
         p_b_1_.writeDouble(this.d);
         p_b_1_.writeDouble(this.f);
         p_b_1_.writeDouble(this.e);
         p_b_1_.b(this.g);
         p_b_1_.b(this.b);
         p_b_1_.b(this.i);
         p_b_1_.b(this.h);
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public static enum EnumWorldBorderAction {
      SET_SIZE,
      LERP_SIZE,
      SET_CENTER,
      INITIALIZE,
      SET_WARNING_TIME,
      SET_WARNING_BLOCKS;
   }
}
