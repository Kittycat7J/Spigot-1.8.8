package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;
import net.minecraft.server.v1_8_R3.PacketListenerPlayOut;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;

public class PacketPlayOutScoreboardScore implements Packet<PacketListenerPlayOut> {
   private String a = "";
   private String b = "";
   private int c;
   private PacketPlayOutScoreboardScore.EnumScoreboardAction d;

   public PacketPlayOutScoreboardScore() {
   }

   public PacketPlayOutScoreboardScore(ScoreboardScore p_i1019_1_) {
      this.a = p_i1019_1_.getPlayerName();
      this.b = p_i1019_1_.getObjective().getName();
      this.c = p_i1019_1_.getScore();
      this.d = PacketPlayOutScoreboardScore.EnumScoreboardAction.CHANGE;
   }

   public PacketPlayOutScoreboardScore(String p_i1020_1_) {
      this.a = p_i1020_1_;
      this.b = "";
      this.c = 0;
      this.d = PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE;
   }

   public PacketPlayOutScoreboardScore(String p_i1021_1_, ScoreboardObjective p_i1021_2_) {
      this.a = p_i1021_1_;
      this.b = p_i1021_2_.getName();
      this.c = 0;
      this.d = PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE;
   }

   public void a(PacketDataSerializer p_a_1_) throws IOException {
      this.a = p_a_1_.c(40);
      this.d = (PacketPlayOutScoreboardScore.EnumScoreboardAction)p_a_1_.a(PacketPlayOutScoreboardScore.EnumScoreboardAction.class);
      this.b = p_a_1_.c(16);
      if(this.d != PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE) {
         this.c = p_a_1_.e();
      }

   }

   public void b(PacketDataSerializer p_b_1_) throws IOException {
      p_b_1_.a(this.a);
      p_b_1_.a((Enum)this.d);
      p_b_1_.a(this.b);
      if(this.d != PacketPlayOutScoreboardScore.EnumScoreboardAction.REMOVE) {
         p_b_1_.b(this.c);
      }

   }

   public void a(PacketListenerPlayOut p_a_1_) {
      p_a_1_.a(this);
   }

   public static enum EnumScoreboardAction {
      CHANGE,
      REMOVE;
   }
}
