package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ScoreboardBaseCriteria;
import net.minecraft.server.v1_8_R3.Statistic;

public class ScoreboardStatisticCriteria extends ScoreboardBaseCriteria {
   private final Statistic j;

   public ScoreboardStatisticCriteria(Statistic p_i862_1_) {
      super(p_i862_1_.name);
      this.j = p_i862_1_;
   }
}
