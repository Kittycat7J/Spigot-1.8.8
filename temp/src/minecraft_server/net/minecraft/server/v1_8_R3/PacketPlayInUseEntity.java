package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayIn;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

public class PacketPlayInUseEntity implements Packet<PacketListenerPlayIn> {
   private int a;
   private PacketPlayInUseEntity.EnumEntityUseAction action;
   private Vec3D c;

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.e();
      this.action = (PacketPlayInUseEntity.EnumEntityUseAction)p_a_1_.a(PacketPlayInUseEntity.EnumEntityUseAction.class);
      if(this.action == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
         this.c = new Vec3D((double)p_a_1_.readFloat(), (double)p_a_1_.readFloat(), (double)p_a_1_.readFloat());
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.b(this.a);
      p_b_1_.a((Enum)this.action);
      if(this.action == PacketPlayInUseEntity.EnumEntityUseAction.INTERACT_AT) {
         p_b_1_.writeFloat((float)this.c.a);
         p_b_1_.writeFloat((float)this.c.b);
         p_b_1_.writeFloat((float)this.c.c);
      }

   }

   public void a(PacketListenerPlayIn p_a_1_) {
      p_a_1_.a(this);
   }

   public Entity a(World p_a_1_) {
      return p_a_1_.a(this.a);
   }

   public PacketPlayInUseEntity.EnumEntityUseAction a() {
      return this.action;
   }

   public Vec3D b() {
      return this.c;
   }

   public static enum EnumEntityUseAction {
      INTERACT,
      ATTACK,
      INTERACT_AT;
   }
}
