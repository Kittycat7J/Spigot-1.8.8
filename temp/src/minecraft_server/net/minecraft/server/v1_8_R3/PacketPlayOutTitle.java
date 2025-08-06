package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutTitle implements Packet<PacketListenerPlayOut> {
   private PacketPlayOutTitle.EnumTitleAction a;
   private IChatBaseComponent b;
   private int c;
   private int d;
   private int e;

   public PacketPlayOutTitle() {
   }

   public PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction p_i1025_1_, IChatBaseComponent p_i1025_2_) {
      this(p_i1025_1_, p_i1025_2_, -1, -1, -1);
   }

   public PacketPlayOutTitle(int p_i1026_1_, int p_i1026_2_, int p_i1026_3_) {
      this(PacketPlayOutTitle.EnumTitleAction.TIMES, (IChatBaseComponent)null, p_i1026_1_, p_i1026_2_, p_i1026_3_);
   }

   public PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction p_i1027_1_, IChatBaseComponent p_i1027_2_, int p_i1027_3_, int p_i1027_4_, int p_i1027_5_) {
      this.a = p_i1027_1_;
      this.b = p_i1027_2_;
      this.c = p_i1027_3_;
      this.d = p_i1027_4_;
      this.e = p_i1027_5_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = (PacketPlayOutTitle.EnumTitleAction)p_a_1_.a(PacketPlayOutTitle.EnumTitleAction.class);
      if(this.a == PacketPlayOutTitle.EnumTitleAction.TITLE || this.a == PacketPlayOutTitle.EnumTitleAction.SUBTITLE) {
         this.b = p_a_1_.d();
      }

      if(this.a == PacketPlayOutTitle.EnumTitleAction.TIMES) {
         this.c = p_a_1_.readInt();
         this.d = p_a_1_.readInt();
         this.e = p_a_1_.readInt();
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a((Enum)this.a);
      if(this.a == PacketPlayOutTitle.EnumTitleAction.TITLE || this.a == PacketPlayOutTitle.EnumTitleAction.SUBTITLE) {
         p_b_1_.a(this.b);
      }

      if(this.a == PacketPlayOutTitle.EnumTitleAction.TIMES) {
         p_b_1_.writeInt(this.c);
         p_b_1_.writeInt(this.d);
         p_b_1_.writeInt(this.e);
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public static enum EnumTitleAction {
      TITLE,
      SUBTITLE,
      TIMES,
      CLEAR,
      RESET;

      public static PacketPlayOutTitle.EnumTitleAction a(String p_a_0_) {
         for(PacketPlayOutTitle.EnumTitleAction packetplayouttitle$enumtitleaction : values()) {
            if(packetplayouttitle$enumtitleaction.name().equalsIgnoreCase(p_a_0_)) {
               return packetplayouttitle$enumtitleaction;
            }
         }

         return TITLE;
      }

      public static String[] a() {
         String[] astring = new String[values().length];
         int i = 0;

         for(PacketPlayOutTitle.EnumTitleAction packetplayouttitle$enumtitleaction : values()) {
            astring[i++] = packetplayouttitle$enumtitleaction.name().toLowerCase();
         }

         return astring;
      }
   }
}
