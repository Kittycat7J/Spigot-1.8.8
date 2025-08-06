package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.Scoreboard;

public class ScoreboardObjective {
   private final Scoreboard a;
   private final String b;
   private final IScoreboardCriteria c;
   private IScoreboardCriteria.EnumScoreboardHealthDisplay d;
   private String e;

   public ScoreboardObjective(Scoreboard p_i853_1_, String p_i853_2_, IScoreboardCriteria p_i853_3_) {
      this.a = p_i853_1_;
      this.b = p_i853_2_;
      this.c = p_i853_3_;
      this.e = p_i853_2_;
      this.d = p_i853_3_.c();
   }

   public String getName() {
      return this.b;
   }

   public IScoreboardCriteria getCriteria() {
      return this.c;
   }

   public String getDisplayName() {
      return this.e;
   }

   public void setDisplayName(String p_setDisplayName_1_) {
      this.e = p_setDisplayName_1_;
      this.a.handleObjectiveChanged(this);
   }

   public IScoreboardCriteria.EnumScoreboardHealthDisplay e() {
      return this.d;
   }

   public void a(IScoreboardCriteria.EnumScoreboardHealthDisplay p_a_1_) {
      this.d = p_a_1_;
      this.a.handleObjectiveChanged(this);
   }
}
