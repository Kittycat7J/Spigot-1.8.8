package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import net.minecraft.server.v1_8_R3.PersistentBase;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import net.minecraft.server.v1_8_R3.ScoreboardTeam;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PersistentScoreboard extends PersistentBase {
   private static final Logger b = LogManager.getLogger();
   private Scoreboard c;
   private NBTTagCompound d;

   public PersistentScoreboard() {
      this("scoreboard");
   }

   public PersistentScoreboard(String p_i856_1_) {
      super(p_i856_1_);
   }

   public void a(Scoreboard p_a_1_) {
      this.c = p_a_1_;
      if(this.d != null) {
         this.a(this.d);
      }

   }

   public void a(NBTTagCompound p_a_1_) {
      if(this.c == null) {
         this.d = p_a_1_;
      } else {
         this.b(p_a_1_.getList("Objectives", 10));
         this.c(p_a_1_.getList("PlayerScores", 10));
         if(p_a_1_.hasKeyOfType("DisplaySlots", 10)) {
            this.c(p_a_1_.getCompound("DisplaySlots"));
         }

         if(p_a_1_.hasKeyOfType("Teams", 9)) {
            this.a(p_a_1_.getList("Teams", 10));
         }

      }
   }

   protected void a(NBTTagList p_a_1_) {
      for(int i = 0; i < p_a_1_.size(); ++i) {
         NBTTagCompound nbttagcompound = p_a_1_.get(i);
         String s = nbttagcompound.getString("Name");
         if(s.length() > 16) {
            s = s.substring(0, 16);
         }

         ScoreboardTeam scoreboardteam = this.c.createTeam(s);
         String s1 = nbttagcompound.getString("DisplayName");
         if(s1.length() > 32) {
            s1 = s1.substring(0, 32);
         }

         scoreboardteam.setDisplayName(s1);
         if(nbttagcompound.hasKeyOfType("TeamColor", 8)) {
            scoreboardteam.a(EnumChatFormat.b(nbttagcompound.getString("TeamColor")));
         }

         scoreboardteam.setPrefix(nbttagcompound.getString("Prefix"));
         scoreboardteam.setSuffix(nbttagcompound.getString("Suffix"));
         if(nbttagcompound.hasKeyOfType("AllowFriendlyFire", 99)) {
            scoreboardteam.setAllowFriendlyFire(nbttagcompound.getBoolean("AllowFriendlyFire"));
         }

         if(nbttagcompound.hasKeyOfType("SeeFriendlyInvisibles", 99)) {
            scoreboardteam.setCanSeeFriendlyInvisibles(nbttagcompound.getBoolean("SeeFriendlyInvisibles"));
         }

         if(nbttagcompound.hasKeyOfType("NameTagVisibility", 8)) {
            ScoreboardTeamBase.EnumNameTagVisibility scoreboardteambase$enumnametagvisibility = ScoreboardTeamBase.EnumNameTagVisibility.a(nbttagcompound.getString("NameTagVisibility"));
            if(scoreboardteambase$enumnametagvisibility != null) {
               scoreboardteam.setNameTagVisibility(scoreboardteambase$enumnametagvisibility);
            }
         }

         if(nbttagcompound.hasKeyOfType("DeathMessageVisibility", 8)) {
            ScoreboardTeamBase.EnumNameTagVisibility scoreboardteambase$enumnametagvisibility1 = ScoreboardTeamBase.EnumNameTagVisibility.a(nbttagcompound.getString("DeathMessageVisibility"));
            if(scoreboardteambase$enumnametagvisibility1 != null) {
               scoreboardteam.b(scoreboardteambase$enumnametagvisibility1);
            }
         }

         this.a(scoreboardteam, nbttagcompound.getList("Players", 8));
      }

   }

   protected void a(ScoreboardTeam p_a_1_, NBTTagList p_a_2_) {
      for(int i = 0; i < p_a_2_.size(); ++i) {
         this.c.addPlayerToTeam(p_a_2_.getString(i), p_a_1_.getName());
      }

   }

   protected void c(NBTTagCompound p_c_1_) {
      for(int i = 0; i < 19; ++i) {
         if(p_c_1_.hasKeyOfType("slot_" + i, 8)) {
            String s = p_c_1_.getString("slot_" + i);
            ScoreboardObjective scoreboardobjective = this.c.getObjective(s);
            this.c.setDisplaySlot(i, scoreboardobjective);
         }
      }

   }

   protected void b(NBTTagList p_b_1_) {
      for(int i = 0; i < p_b_1_.size(); ++i) {
         NBTTagCompound nbttagcompound = p_b_1_.get(i);
         IScoreboardCriteria iscoreboardcriteria = (IScoreboardCriteria)IScoreboardCriteria.criteria.get(nbttagcompound.getString("CriteriaName"));
         if(iscoreboardcriteria != null) {
            String s = nbttagcompound.getString("Name");
            if(s.length() > 16) {
               s = s.substring(0, 16);
            }

            ScoreboardObjective scoreboardobjective = this.c.registerObjective(s, iscoreboardcriteria);
            scoreboardobjective.setDisplayName(nbttagcompound.getString("DisplayName"));
            scoreboardobjective.a(IScoreboardCriteria.EnumScoreboardHealthDisplay.a(nbttagcompound.getString("RenderType")));
         }
      }

   }

   protected void c(NBTTagList p_c_1_) {
      for(int i = 0; i < p_c_1_.size(); ++i) {
         NBTTagCompound nbttagcompound = p_c_1_.get(i);
         ScoreboardObjective scoreboardobjective = this.c.getObjective(nbttagcompound.getString("Objective"));
         String s = nbttagcompound.getString("Name");
         if(s.length() > 40) {
            s = s.substring(0, 40);
         }

         ScoreboardScore scoreboardscore = this.c.getPlayerScoreForObjective(s, scoreboardobjective);
         scoreboardscore.setScore(nbttagcompound.getInt("Score"));
         if(nbttagcompound.hasKey("Locked")) {
            scoreboardscore.a(nbttagcompound.getBoolean("Locked"));
         }
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      if(this.c == null) {
         b.warn("Tried to save scoreboard without having a scoreboard...");
      } else {
         p_b_1_.set("Objectives", this.b());
         p_b_1_.set("PlayerScores", this.e());
         p_b_1_.set("Teams", this.a());
         this.d(p_b_1_);
      }
   }

   protected NBTTagList a() {
      NBTTagList nbttaglist = new NBTTagList();

      for(ScoreboardTeam scoreboardteam : this.c.getTeams()) {
         NBTTagCompound nbttagcompound = new NBTTagCompound();
         nbttagcompound.setString("Name", scoreboardteam.getName());
         nbttagcompound.setString("DisplayName", scoreboardteam.getDisplayName());
         if(scoreboardteam.l().b() >= 0) {
            nbttagcompound.setString("TeamColor", scoreboardteam.l().e());
         }

         nbttagcompound.setString("Prefix", scoreboardteam.getPrefix());
         nbttagcompound.setString("Suffix", scoreboardteam.getSuffix());
         nbttagcompound.setBoolean("AllowFriendlyFire", scoreboardteam.allowFriendlyFire());
         nbttagcompound.setBoolean("SeeFriendlyInvisibles", scoreboardteam.canSeeFriendlyInvisibles());
         nbttagcompound.setString("NameTagVisibility", scoreboardteam.getNameTagVisibility().e);
         nbttagcompound.setString("DeathMessageVisibility", scoreboardteam.j().e);
         NBTTagList nbttaglist1 = new NBTTagList();

         for(String s : scoreboardteam.getPlayerNameSet()) {
            nbttaglist1.add(new NBTTagString(s));
         }

         nbttagcompound.set("Players", nbttaglist1);
         nbttaglist.add(nbttagcompound);
      }

      return nbttaglist;
   }

   protected void d(NBTTagCompound p_d_1_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      boolean flag = false;

      for(int i = 0; i < 19; ++i) {
         ScoreboardObjective scoreboardobjective = this.c.getObjectiveForSlot(i);
         if(scoreboardobjective != null) {
            nbttagcompound.setString("slot_" + i, scoreboardobjective.getName());
            flag = true;
         }
      }

      if(flag) {
         p_d_1_.set("DisplaySlots", nbttagcompound);
      }

   }

   protected NBTTagList b() {
      NBTTagList nbttaglist = new NBTTagList();

      for(ScoreboardObjective scoreboardobjective : this.c.getObjectives()) {
         if(scoreboardobjective.getCriteria() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Name", scoreboardobjective.getName());
            nbttagcompound.setString("CriteriaName", scoreboardobjective.getCriteria().getName());
            nbttagcompound.setString("DisplayName", scoreboardobjective.getDisplayName());
            nbttagcompound.setString("RenderType", scoreboardobjective.e().a());
            nbttaglist.add(nbttagcompound);
         }
      }

      return nbttaglist;
   }

   protected NBTTagList e() {
      NBTTagList nbttaglist = new NBTTagList();

      for(ScoreboardScore scoreboardscore : this.c.getScores()) {
         if(scoreboardscore.getObjective() != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setString("Name", scoreboardscore.getPlayerName());
            nbttagcompound.setString("Objective", scoreboardscore.getObjective().getName());
            nbttagcompound.setInt("Score", scoreboardscore.getScore());
            nbttagcompound.setBoolean("Locked", scoreboardscore.g());
            nbttaglist.add(nbttagcompound);
         }
      }

      return nbttaglist;
   }
}
