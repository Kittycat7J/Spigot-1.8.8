package net.minecraft.server.v1_8_R3;

import java.util.Comparator;
import java.util.List;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;

public class ScoreboardScore {
   public static final Comparator<ScoreboardScore> a = new Comparator<ScoreboardScore>() {
      public int a(ScoreboardScore p_a_1_, ScoreboardScore p_a_2_) {
         return p_a_1_.getScore() > p_a_2_.getScore()?1:(p_a_1_.getScore() < p_a_2_.getScore()?-1:p_a_2_.getPlayerName().compareToIgnoreCase(p_a_1_.getPlayerName()));
      }
   };
   private final Scoreboard b;
   private final ScoreboardObjective c;
   private final String playerName;
   private int score;
   private boolean f;
   private boolean g;

   public ScoreboardScore(Scoreboard p_i855_1_, ScoreboardObjective p_i855_2_, String p_i855_3_) {
      this.b = p_i855_1_;
      this.c = p_i855_2_;
      this.playerName = p_i855_3_;
      this.g = true;
   }

   public void addScore(int p_addScore_1_) {
      if(this.c.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.setScore(this.getScore() + p_addScore_1_);
      }
   }

   public void removeScore(int p_removeScore_1_) {
      if(this.c.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.setScore(this.getScore() - p_removeScore_1_);
      }
   }

   public void incrementScore() {
      if(this.c.getCriteria().isReadOnly()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.addScore(1);
      }
   }

   public int getScore() {
      return this.score;
   }

   public void setScore(int p_setScore_1_) {
      int i = this.score;
      this.score = p_setScore_1_;
      if(i != p_setScore_1_ || this.g) {
         this.g = false;
         this.f().handleScoreChanged(this);
      }

   }

   public ScoreboardObjective getObjective() {
      return this.c;
   }

   public String getPlayerName() {
      return this.playerName;
   }

   public Scoreboard f() {
      return this.b;
   }

   public boolean g() {
      return this.f;
   }

   public void a(boolean p_a_1_) {
      this.f = p_a_1_;
   }

   public void updateForList(List<EntityHuman> p_updateForList_1_) {
      this.setScore(this.c.getCriteria().getScoreModifier(p_updateForList_1_));
   }
}
