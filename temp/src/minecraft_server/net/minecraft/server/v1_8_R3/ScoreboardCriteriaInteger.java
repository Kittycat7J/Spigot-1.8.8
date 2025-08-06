package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;

public class ScoreboardCriteriaInteger implements IScoreboardCriteria {
   private final String j;

   public ScoreboardCriteriaInteger(String p_i858_1_, EnumChatFormat p_i858_2_) {
      this.j = p_i858_1_ + p_i858_2_.e();
      IScoreboardCriteria.criteria.put(this.j, this);
   }

   public String getName() {
      return this.j;
   }

   public int getScoreModifier(List<EntityHuman> p_getScoreModifier_1_) {
      return 0;
   }

   public boolean isReadOnly() {
      return false;
   }

   public IScoreboardCriteria.EnumScoreboardHealthDisplay c() {
      return IScoreboardCriteria.EnumScoreboardHealthDisplay.INTEGER;
   }
}
