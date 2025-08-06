package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;

public class PacketPlayOutScoreboardObjective implements Packet<PacketListenerPlayOut> {
   private String a;
   private String b;
   private IScoreboardCriteria.EnumScoreboardHealthDisplay c;
   private int d;

   public PacketPlayOutScoreboardObjective() {
   }

   public PacketPlayOutScoreboardObjective(ScoreboardObjective p_i1015_1_, int p_i1015_2_) {
      this.a = p_i1015_1_.getName();
      this.b = p_i1015_1_.getDisplayName();
      this.c = p_i1015_1_.getCriteria().c();
      this.d = p_i1015_2_;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.c(16);
      this.d = p_a_1_.readByte();
      if(this.d == 0 || this.d == 2) {
         this.b = p_a_1_.c(32);
         this.c = IScoreboardCriteria.EnumScoreboardHealthDisplay.a(p_a_1_.c(16));
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
      p_b_1_.writeByte(this.d);
      if(this.d == 0 || this.d == 2) {
         p_b_1_.a(this.b);
         p_b_1_.a(this.c.a());
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }
}
