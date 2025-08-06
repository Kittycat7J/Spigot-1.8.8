package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;

public class Scoreboard {
   private final Map<String, ScoreboardObjective> objectivesByName = Maps.<String, ScoreboardObjective>newHashMap();
   private final Map<IScoreboardCriteria, List<ScoreboardObjective>> objectivesByCriteria = Maps.<IScoreboardCriteria, List<ScoreboardObjective>>newHashMap();
   private final Map<String, Map<ScoreboardObjective, ScoreboardScore>> playerScores = Maps.<String, Map<ScoreboardObjective, ScoreboardScore>>newHashMap();
   private final ScoreboardObjective[] displaySlots = new ScoreboardObjective[19];
   private final Map<String, ScoreboardTeam> teamsByName = Maps.<String, ScoreboardTeam>newHashMap();
   private final Map<String, ScoreboardTeam> teamsByPlayer = Maps.<String, ScoreboardTeam>newHashMap();
   private static String[] g = null;

   public ScoreboardObjective getObjective(String p_getObjective_1_) {
      return (ScoreboardObjective)this.objectivesByName.get(p_getObjective_1_);
   }

   public ScoreboardObjective registerObjective(String p_registerObjective_1_, IScoreboardCriteria p_registerObjective_2_) {
      if(p_registerObjective_1_.length() > 16) {
         throw new IllegalArgumentException("The objective name \'" + p_registerObjective_1_ + "\' is too long!");
      } else {
         ScoreboardObjective scoreboardobjective = this.getObjective(p_registerObjective_1_);
         if(scoreboardobjective != null) {
            throw new IllegalArgumentException("An objective with the name \'" + p_registerObjective_1_ + "\' already exists!");
         } else {
            scoreboardobjective = new ScoreboardObjective(this, p_registerObjective_1_, p_registerObjective_2_);
            Object object = (List)this.objectivesByCriteria.get(p_registerObjective_2_);
            if(object == null) {
               object = Lists.newArrayList();
               this.objectivesByCriteria.put(p_registerObjective_2_, object);
            }

            ((List)object).add(scoreboardobjective);
            this.objectivesByName.put(p_registerObjective_1_, scoreboardobjective);
            this.handleObjectiveAdded(scoreboardobjective);
            return scoreboardobjective;
         }
      }
   }

   public Collection<ScoreboardObjective> getObjectivesForCriteria(IScoreboardCriteria p_getObjectivesForCriteria_1_) {
      Collection collection = (Collection)this.objectivesByCriteria.get(p_getObjectivesForCriteria_1_);
      return collection == null?Lists.newArrayList():Lists.newArrayList(collection);
   }

   public boolean b(String p_b_1_, ScoreboardObjective p_b_2_) {
      Map map = (Map)this.playerScores.get(p_b_1_);
      if(map == null) {
         return false;
      } else {
         ScoreboardScore scoreboardscore = (ScoreboardScore)map.get(p_b_2_);
         return scoreboardscore != null;
      }
   }

   public ScoreboardScore getPlayerScoreForObjective(String p_getPlayerScoreForObjective_1_, ScoreboardObjective p_getPlayerScoreForObjective_2_) {
      if(p_getPlayerScoreForObjective_1_.length() > 40) {
         throw new IllegalArgumentException("The player name \'" + p_getPlayerScoreForObjective_1_ + "\' is too long!");
      } else {
         Object object = (Map)this.playerScores.get(p_getPlayerScoreForObjective_1_);
         if(object == null) {
            object = Maps.newHashMap();
            this.playerScores.put(p_getPlayerScoreForObjective_1_, object);
         }

         ScoreboardScore scoreboardscore = (ScoreboardScore)((Map)object).get(p_getPlayerScoreForObjective_2_);
         if(scoreboardscore == null) {
            scoreboardscore = new ScoreboardScore(this, p_getPlayerScoreForObjective_2_, p_getPlayerScoreForObjective_1_);
            ((Map)object).put(p_getPlayerScoreForObjective_2_, scoreboardscore);
         }

         return scoreboardscore;
      }
   }

   public Collection<ScoreboardScore> getScoresForObjective(ScoreboardObjective p_getScoresForObjective_1_) {
      ArrayList arraylist = Lists.newArrayList();

      for(Map map : this.playerScores.values()) {
         ScoreboardScore scoreboardscore = (ScoreboardScore)map.get(p_getScoresForObjective_1_);
         if(scoreboardscore != null) {
            arraylist.add(scoreboardscore);
         }
      }

      Collections.sort(arraylist, ScoreboardScore.a);
      return arraylist;
   }

   public Collection<ScoreboardObjective> getObjectives() {
      return this.objectivesByName.values();
   }

   public Collection<String> getPlayers() {
      return this.playerScores.keySet();
   }

   public void resetPlayerScores(String p_resetPlayerScores_1_, ScoreboardObjective p_resetPlayerScores_2_) {
      if(p_resetPlayerScores_2_ == null) {
         Map map = (Map)this.playerScores.remove(p_resetPlayerScores_1_);
         if(map != null) {
            this.handlePlayerRemoved(p_resetPlayerScores_1_);
         }
      } else {
         Map map2 = (Map)this.playerScores.get(p_resetPlayerScores_1_);
         if(map2 != null) {
            ScoreboardScore scoreboardscore = (ScoreboardScore)map2.remove(p_resetPlayerScores_2_);
            if(map2.size() < 1) {
               Map map1 = (Map)this.playerScores.remove(p_resetPlayerScores_1_);
               if(map1 != null) {
                  this.handlePlayerRemoved(p_resetPlayerScores_1_);
               }
            } else if(scoreboardscore != null) {
               this.a(p_resetPlayerScores_1_, p_resetPlayerScores_2_);
            }
         }
      }

   }

   public Collection<ScoreboardScore> getScores() {
      Collection collection = this.playerScores.values();
      ArrayList arraylist = Lists.newArrayList();

      for(Map map : collection) {
         arraylist.addAll(map.values());
      }

      return arraylist;
   }

   public Map<ScoreboardObjective, ScoreboardScore> getPlayerObjectives(String p_getPlayerObjectives_1_) {
      Object object = (Map)this.playerScores.get(p_getPlayerObjectives_1_);
      if(object == null) {
         object = Maps.newHashMap();
      }

      return (Map<ScoreboardObjective, ScoreboardScore>)object;
   }

   public void unregisterObjective(ScoreboardObjective p_unregisterObjective_1_) {
      this.objectivesByName.remove(p_unregisterObjective_1_.getName());

      for(int i = 0; i < 19; ++i) {
         if(this.getObjectiveForSlot(i) == p_unregisterObjective_1_) {
            this.setDisplaySlot(i, (ScoreboardObjective)null);
         }
      }

      List list = (List)this.objectivesByCriteria.get(p_unregisterObjective_1_.getCriteria());
      if(list != null) {
         list.remove(p_unregisterObjective_1_);
      }

      for(Map map : this.playerScores.values()) {
         map.remove(p_unregisterObjective_1_);
      }

      this.handleObjectiveRemoved(p_unregisterObjective_1_);
   }

   public void setDisplaySlot(int p_setDisplaySlot_1_, ScoreboardObjective p_setDisplaySlot_2_) {
      this.displaySlots[p_setDisplaySlot_1_] = p_setDisplaySlot_2_;
   }

   public ScoreboardObjective getObjectiveForSlot(int p_getObjectiveForSlot_1_) {
      return this.displaySlots[p_getObjectiveForSlot_1_];
   }

   public ScoreboardTeam getTeam(String p_getTeam_1_) {
      return (ScoreboardTeam)this.teamsByName.get(p_getTeam_1_);
   }

   public ScoreboardTeam createTeam(String p_createTeam_1_) {
      if(p_createTeam_1_.length() > 16) {
         throw new IllegalArgumentException("The team name \'" + p_createTeam_1_ + "\' is too long!");
      } else {
         ScoreboardTeam scoreboardteam = this.getTeam(p_createTeam_1_);
         if(scoreboardteam != null) {
            throw new IllegalArgumentException("A team with the name \'" + p_createTeam_1_ + "\' already exists!");
         } else {
            scoreboardteam = new ScoreboardTeam(this, p_createTeam_1_);
            this.teamsByName.put(p_createTeam_1_, scoreboardteam);
            this.handleTeamAdded(scoreboardteam);
            return scoreboardteam;
         }
      }
   }

   public void removeTeam(ScoreboardTeam p_removeTeam_1_) {
      this.teamsByName.remove(p_removeTeam_1_.getName());

      for(String s : p_removeTeam_1_.getPlayerNameSet()) {
         this.teamsByPlayer.remove(s);
      }

      this.handleTeamRemoved(p_removeTeam_1_);
   }

   public boolean addPlayerToTeam(String p_addPlayerToTeam_1_, String p_addPlayerToTeam_2_) {
      if(p_addPlayerToTeam_1_.length() > 40) {
         throw new IllegalArgumentException("The player name \'" + p_addPlayerToTeam_1_ + "\' is too long!");
      } else if(!this.teamsByName.containsKey(p_addPlayerToTeam_2_)) {
         return false;
      } else {
         ScoreboardTeam scoreboardteam = this.getTeam(p_addPlayerToTeam_2_);
         if(this.getPlayerTeam(p_addPlayerToTeam_1_) != null) {
            this.removePlayerFromTeam(p_addPlayerToTeam_1_);
         }

         this.teamsByPlayer.put(p_addPlayerToTeam_1_, scoreboardteam);
         scoreboardteam.getPlayerNameSet().add(p_addPlayerToTeam_1_);
         return true;
      }
   }

   public boolean removePlayerFromTeam(String p_removePlayerFromTeam_1_) {
      ScoreboardTeam scoreboardteam = this.getPlayerTeam(p_removePlayerFromTeam_1_);
      if(scoreboardteam != null) {
         this.removePlayerFromTeam(p_removePlayerFromTeam_1_, scoreboardteam);
         return true;
      } else {
         return false;
      }
   }

   public void removePlayerFromTeam(String p_removePlayerFromTeam_1_, ScoreboardTeam p_removePlayerFromTeam_2_) {
      if(this.getPlayerTeam(p_removePlayerFromTeam_1_) != p_removePlayerFromTeam_2_) {
         throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team \'" + p_removePlayerFromTeam_2_.getName() + "\'.");
      } else {
         this.teamsByPlayer.remove(p_removePlayerFromTeam_1_);
         p_removePlayerFromTeam_2_.getPlayerNameSet().remove(p_removePlayerFromTeam_1_);
      }
   }

   public Collection<String> getTeamNames() {
      return this.teamsByName.keySet();
   }

   public Collection<ScoreboardTeam> getTeams() {
      return this.teamsByName.values();
   }

   public ScoreboardTeam getPlayerTeam(String p_getPlayerTeam_1_) {
      return (ScoreboardTeam)this.teamsByPlayer.get(p_getPlayerTeam_1_);
   }

   public void handleObjectiveAdded(ScoreboardObjective p_handleObjectiveAdded_1_) {
   }

   public void handleObjectiveChanged(ScoreboardObjective p_handleObjectiveChanged_1_) {
   }

   public void handleObjectiveRemoved(ScoreboardObjective p_handleObjectiveRemoved_1_) {
   }

   public void handleScoreChanged(ScoreboardScore p_handleScoreChanged_1_) {
   }

   public void handlePlayerRemoved(String p_handlePlayerRemoved_1_) {
   }

   public void a(String p_a_1_, ScoreboardObjective p_a_2_) {
   }

   public void handleTeamAdded(ScoreboardTeam p_handleTeamAdded_1_) {
   }

   public void handleTeamChanged(ScoreboardTeam p_handleTeamChanged_1_) {
   }

   public void handleTeamRemoved(ScoreboardTeam p_handleTeamRemoved_1_) {
   }

   public static String getSlotName(int p_getSlotName_0_) {
      switch(p_getSlotName_0_) {
      case 0:
         return "list";
      case 1:
         return "sidebar";
      case 2:
         return "belowName";
      default:
         if(p_getSlotName_0_ >= 3 && p_getSlotName_0_ <= 18) {
            EnumChatFormat enumchatformat = EnumChatFormat.a(p_getSlotName_0_ - 3);
            if(enumchatformat != null && enumchatformat != EnumChatFormat.RESET) {
               return "sidebar.team." + enumchatformat.e();
            }
         }

         return null;
      }
   }

   public static int getSlotForName(String p_getSlotForName_0_) {
      if(p_getSlotForName_0_.equalsIgnoreCase("list")) {
         return 0;
      } else if(p_getSlotForName_0_.equalsIgnoreCase("sidebar")) {
         return 1;
      } else if(p_getSlotForName_0_.equalsIgnoreCase("belowName")) {
         return 2;
      } else {
         if(p_getSlotForName_0_.startsWith("sidebar.team.")) {
            String s = p_getSlotForName_0_.substring("sidebar.team.".length());
            EnumChatFormat enumchatformat = EnumChatFormat.b(s);
            if(enumchatformat != null && enumchatformat.b() >= 0) {
               return enumchatformat.b() + 3;
            }
         }

         return -1;
      }
   }

   public static String[] h() {
      if(g == null) {
         g = new String[19];

         for(int i = 0; i < 19; ++i) {
            g[i] = getSlotName(i);
         }
      }

      return g;
   }

   public void a(Entity p_a_1_) {
      if(p_a_1_ != null && !(p_a_1_ instanceof EntityHuman) && !p_a_1_.isAlive()) {
         String s = p_a_1_.getUniqueID().toString();
         this.resetPlayerScores(s, (ScoreboardObjective)null);
         this.removePlayerFromTeam(s);
      }
   }
}
