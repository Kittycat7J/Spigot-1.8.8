package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.CombatTracker;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;

public class PacketPlayOutCombatEvent implements Packet<PacketListenerPlayOut> {
   public PacketPlayOutCombatEvent.EnumCombatEventType a;
   public int b;
   public int c;
   public int d;
   public String e;

   public PacketPlayOutCombatEvent() {
   }

   public PacketPlayOutCombatEvent(CombatTracker p_i992_1_, PacketPlayOutCombatEvent.EnumCombatEventType p_i992_2_) {
      this.a = p_i992_2_;
      EntityLiving entityliving = p_i992_1_.c();
      switch(p_i992_2_) {
      case END_COMBAT:
         this.d = p_i992_1_.f();
         this.c = entityliving == null?-1:entityliving.getId();
         break;
      case ENTITY_DIED:
         this.b = p_i992_1_.h().getId();
         this.c = entityliving == null?-1:entityliving.getId();
         this.e = p_i992_1_.b().c();
      }

   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = (PacketPlayOutCombatEvent.EnumCombatEventType)p_a_1_.a(PacketPlayOutCombatEvent.EnumCombatEventType.class);
      if(this.a == PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT) {
         this.d = p_a_1_.e();
         this.c = p_a_1_.readInt();
      } else if(this.a == PacketPlayOutCombatEvent.EnumCombatEventType.ENTITY_DIED) {
         this.b = p_a_1_.e();
         this.c = p_a_1_.readInt();
         this.e = p_a_1_.c(32767);
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a((Enum)this.a);
      if(this.a == PacketPlayOutCombatEvent.EnumCombatEventType.END_COMBAT) {
         p_b_1_.b(this.d);
         p_b_1_.writeInt(this.c);
      } else if(this.a == PacketPlayOutCombatEvent.EnumCombatEventType.ENTITY_DIED) {
         p_b_1_.b(this.b);
         p_b_1_.writeInt(this.c);
         p_b_1_.a(this.e);
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public static enum EnumCombatEventType {
      ENTER_COMBAT,
      END_COMBAT,
      ENTITY_DIED;
   }
}
