package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;

public class ScoreboardBaseCriteria implements IScoreboardCriteria {
   private final String j;

   public ScoreboardBaseCriteria(String p_i859_1_) {
      this.j = p_i859_1_;
      IScoreboardCriteria.criteria.put(p_i859_1_, this);
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
