package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.EnumDifficulty;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.WorldSettings;
import net.minecraft.server.v1_8_R3.WorldType;

public class PacketPlayOutRespawn implements Packet<PacketListenerPlayOut> {
   private int a;
   private EnumDifficulty b;
   private WorldSettings.EnumGamemode c;
   private WorldType d;

   public PacketPlayOutRespawn() {
   }

   public PacketPlayOutRespawn(int p_i1001_1_, EnumDifficulty p_i1001_2_, WorldType p_i1001_3_, WorldSettings.EnumGamemode p_i1001_4_) {
      this.a = p_i1001_1_;
      this.b = p_i1001_2_;
      this.c = p_i1001_4_;
      this.d = p_i1001_3_;
   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.readInt();
      this.b = EnumDifficulty.getById(p_a_1_.readUnsignedByte());
      this.c = WorldSettings.EnumGamemode.getById(p_a_1_.readUnsignedByte());
      this.d = WorldType.getType(p_a_1_.c(16));
      if(this.d == null) {
         this.d = WorldType.NORMAL;
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.writeInt(this.a);
      p_b_1_.writeByte(this.b.a());
      p_b_1_.writeByte(this.c.getId());
      p_b_1_.a(this.d.name());
   }
}
