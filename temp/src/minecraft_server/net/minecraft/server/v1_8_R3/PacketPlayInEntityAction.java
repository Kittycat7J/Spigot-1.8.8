package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;

public class PacketPlayInEntityAction implements Packet<PacketListenerPlayIn> {
   private int a;
   private PacketPlayInEntityAction.EnumPlayerAction animation;
   private int c;

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.animation = (PacketPlayInEntityAction.EnumPlayerAction)p_a_1_.a(PacketPlayInEntityAction.EnumPlayerAction.class);
      this.c = p_a_1_.e();
   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.a((Enum)this.animation);
      p_b_1_.b(this.c);
   }

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public PacketPlayInEntityAction.EnumPlayerAction b() {
      return this.animation;
   }

   public int c() {
      return this.c;
   }

   public static enum EnumPlayerAction {
      START_SNEAKING,
      STOP_SNEAKING,
      STOP_SLEEPING,
      START_SPRINTING,
      STOP_SPRINTING,
      RIDING_JUMP,
      OPEN_INVENTORY;
   }
}
