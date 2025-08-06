package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import net.minecraft.server.v1_8_R3.UtilColor;

public class ChatComponentScore extends ChatBaseComponent {
   private final String b;
   private final String c;
   private String d = "";

   public ChatComponentScore(String p_i939_1_, String p_i939_2_) {
      this.b = p_i939_1_;
      this.c = p_i939_2_;
   }

   public String g() {
      return this.b;
   }

   public String h() {
      return this.c;
   }

   public void b(String p_b_1_) {
      this.d = p_b_1_;
   }

   public String getText() {
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      if(minecraftserver != null && minecraftserver.O() && UtilColor.b(this.d)) {
         Scoreboard scoreboard = minecraftserver.getWorldServer(0).getScoreboard();
         ScoreboardObjective scoreboardobjective = scoreboard.getObjective(this.c);
         if(scoreboard.b(this.b, scoreboardobjective)) {
            ScoreboardScore scoreboardscore = scoreboard.getPlayerScoreForObjective(this.b, scoreboardobjective);
            this.b(String.format("%d", new Object[]{Integer.valueOf(scoreboardscore.getScore())}));
         } else {
            this.d = "";
         }
      }

      return this.d;
   }

   public ChatComponentScore i() {
      ChatComponentScore chatcomponentscore = new ChatComponentScore(this.b, this.c);
      chatcomponentscore.b(this.d);
      chatcomponentscore.setChatModifier(this.getChatModifier().clone());

      for(IChatBaseComponent ichatbasecomponent : this.a()) {
         chatcomponentscore.addSibling(ichatbasecomponent.f());
      }

      return chatcomponentscore;
   }

   public boolean equals(Object p_equals_1_) {
      if(this == p_equals_1_) {
         return true;
      } else if(!(p_equals_1_ instanceof ChatComponentScore)) {
         return false;
      } else {
         ChatComponentScore chatcomponentscore = (ChatComponentScore)p_equals_1_;
         return this.b.equals(chatcomponentscore.b) && this.c.equals(chatcomponentscore.c) && super.equals(p_equals_1_);
      }
   }

   public String toString() {
      return "ScoreComponent{name=\'" + this.b + '\'' + "objective=\'" + this.c + '\'' + ", siblings=" + this.a + ", style=" + this.getChatModifier() + '}';
   }
}
