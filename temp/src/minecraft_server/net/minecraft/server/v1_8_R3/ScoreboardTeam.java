package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.server.v1_8_R3.EnumChatFormat;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardTeamBase;

public class ScoreboardTeam extends ScoreboardTeamBase {
   private final Scoreboard a;
   private final String b;
   private final Set<String> c = Sets.<String>newHashSet();
   private String d;
   private String e = "";
   private String f = "";
   private boolean g = true;
   private boolean h = true;
   private ScoreboardTeamBase.EnumNameTagVisibility i = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS;
   private ScoreboardTeamBase.EnumNameTagVisibility j = ScoreboardTeamBase.EnumNameTagVisibility.ALWAYS;
   private EnumChatFormat k = EnumChatFormat.RESET;

   public ScoreboardTeam(Scoreboard p_i854_1_, String p_i854_2_) {
      this.a = p_i854_1_;
      this.b = p_i854_2_;
      this.d = p_i854_2_;
   }

   public String getName() {
      return this.b;
   }

   public String getDisplayName() {
      return this.d;
   }

   public void setDisplayName(String p_setDisplayName_1_) {
      if(p_setDisplayName_1_ == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.d = p_setDisplayName_1_;
         this.a.handleTeamChanged(this);
      }
   }

   public Collection<String> getPlayerNameSet() {
      return this.c;
   }

   public String getPrefix() {
      return this.e;
   }

   public void setPrefix(String p_setPrefix_1_) {
      if(p_setPrefix_1_ == null) {
         throw new IllegalArgumentException("Prefix cannot be null");
      } else {
         this.e = p_setPrefix_1_;
         this.a.handleTeamChanged(this);
      }
   }

   public String getSuffix() {
      return this.f;
   }

   public void setSuffix(String p_setSuffix_1_) {
      this.f = p_setSuffix_1_;
      this.a.handleTeamChanged(this);
   }

   public String getFormattedName(String p_getFormattedName_1_) {
      return this.getPrefix() + p_getFormattedName_1_ + this.getSuffix();
   }

   public static String getPlayerDisplayName(ScoreboardTeamBase p_getPlayerDisplayName_0_, String p_getPlayerDisplayName_1_) {
      return p_getPlayerDisplayName_0_ == null?p_getPlayerDisplayName_1_:p_getPlayerDisplayName_0_.getFormattedName(p_getPlayerDisplayName_1_);
   }

   public boolean allowFriendlyFire() {
      return this.g;
   }

   public void setAllowFriendlyFire(boolean p_setAllowFriendlyFire_1_) {
      this.g = p_setAllowFriendlyFire_1_;
      this.a.handleTeamChanged(this);
   }

   public boolean canSeeFriendlyInvisibles() {
      return this.h;
   }

   public void setCanSeeFriendlyInvisibles(boolean p_setCanSeeFriendlyInvisibles_1_) {
      this.h = p_setCanSeeFriendlyInvisibles_1_;
      this.a.handleTeamChanged(this);
   }

   public ScoreboardTeamBase.EnumNameTagVisibility getNameTagVisibility() {
      return this.i;
   }

   public ScoreboardTeamBase.EnumNameTagVisibility j() {
      return this.j;
   }

   public void setNameTagVisibility(ScoreboardTeamBase.EnumNameTagVisibility p_setNameTagVisibility_1_) {
      this.i = p_setNameTagVisibility_1_;
      this.a.handleTeamChanged(this);
   }

   public void b(ScoreboardTeamBase.EnumNameTagVisibility p_b_1_) {
      this.j = p_b_1_;
      this.a.handleTeamChanged(this);
   }

   public int packOptionData() {
      int ix = 0;
      if(this.allowFriendlyFire()) {
         ix |= 1;
      }

      if(this.canSeeFriendlyInvisibles()) {
         ix |= 2;
      }

      return ix;
   }

   public void a(EnumChatFormat p_a_1_) {
      this.k = p_a_1_;
   }

   public EnumChatFormat l() {
      return this.k;
   }
}
