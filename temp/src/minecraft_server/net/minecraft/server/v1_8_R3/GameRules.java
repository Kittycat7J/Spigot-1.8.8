package net.minecraft.server.v1_8_R3;

import java.util.Set;
import java.util.TreeMap;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class GameRules {
   private TreeMap<String, GameRules.GameRuleValue> a = new TreeMap();

   public GameRules() {
      this.a("doFireTick", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("mobGriefing", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("keepInventory", "false", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("doMobSpawning", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("doMobLoot", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("doTileDrops", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("doEntityDrops", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("commandBlockOutput", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("naturalRegeneration", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("doDaylightCycle", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("logAdminCommands", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("showDeathMessages", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("randomTickSpeed", "3", GameRules.EnumGameRuleType.NUMERICAL_VALUE);
      this.a("sendCommandFeedback", "true", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
      this.a("reducedDebugInfo", "false", GameRules.EnumGameRuleType.BOOLEAN_VALUE);
   }

   public void a(String p_a_1_, String p_a_2_, GameRules.EnumGameRuleType p_a_3_) {
      this.a.put(p_a_1_, new GameRules.GameRuleValue(p_a_2_, p_a_3_));
   }

   public void set(String p_set_1_, String p_set_2_) {
      GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.a.get(p_set_1_);
      if(gamerules$gamerulevalue != null) {
         gamerules$gamerulevalue.a(p_set_2_);
      } else {
         this.a(p_set_1_, p_set_2_, GameRules.EnumGameRuleType.ANY_VALUE);
      }

   }

   public String get(String p_get_1_) {
      GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.a.get(p_get_1_);
      return gamerules$gamerulevalue != null?gamerules$gamerulevalue.a():"";
   }

   public boolean getBoolean(String p_getBoolean_1_) {
      GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.a.get(p_getBoolean_1_);
      return gamerules$gamerulevalue != null?gamerules$gamerulevalue.b():false;
   }

   public int c(String p_c_1_) {
      GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.a.get(p_c_1_);
      return gamerules$gamerulevalue != null?gamerules$gamerulevalue.c():0;
   }

   public NBTTagCompound a() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();

      for(String s : this.a.keySet()) {
         GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.a.get(s);
         nbttagcompound.setString(s, gamerules$gamerulevalue.a());
      }

      return nbttagcompound;
   }

   public void a(NBTTagCompound p_a_1_) {
      for(String s : p_a_1_.c()) {
         String s1 = p_a_1_.getString(s);
         this.set(s, s1);
      }

   }

   public String[] getGameRules() {
      Set set = this.a.keySet();
      return (String[])set.toArray(new String[set.size()]);
   }

   public boolean contains(String p_contains_1_) {
      return this.a.containsKey(p_contains_1_);
   }

   public boolean a(String p_a_1_, GameRules.EnumGameRuleType p_a_2_) {
      GameRules.GameRuleValue gamerules$gamerulevalue = (GameRules.GameRuleValue)this.a.get(p_a_1_);
      return gamerules$gamerulevalue != null && (gamerules$gamerulevalue.e() == p_a_2_ || p_a_2_ == GameRules.EnumGameRuleType.ANY_VALUE);
   }

   public static enum EnumGameRuleType {
      ANY_VALUE,
      BOOLEAN_VALUE,
      NUMERICAL_VALUE;
   }

   static class GameRuleValue {
      private String a;
      private boolean b;
      private int c;
      private double d;
      private final GameRules.EnumGameRuleType e;

      public GameRuleValue(String p_i552_1_, GameRules.EnumGameRuleType p_i552_2_) {
         this.e = p_i552_2_;
         this.a(p_i552_1_);
      }

      public void a(String p_a_1_) {
         this.a = p_a_1_;
         this.b = Boolean.parseBoolean(p_a_1_);
         this.c = this.b?1:0;

         try {
            this.c = Integer.parseInt(p_a_1_);
         } catch (NumberFormatException var4) {
            ;
         }

         try {
            this.d = Double.parseDouble(p_a_1_);
         } catch (NumberFormatException var3) {
            ;
         }

      }

      public String a() {
         return this.a;
      }

      public boolean b() {
         return this.b;
      }

      public int c() {
         return this.c;
      }

      public GameRules.EnumGameRuleType e() {
         return this.e;
      }
   }
}
