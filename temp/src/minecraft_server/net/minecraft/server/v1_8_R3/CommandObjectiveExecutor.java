package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.ExceptionEntityNotFound;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;
import net.minecraft.server.v1_8_R3.Vec3D;
import net.minecraft.server.v1_8_R3.World;

public class CommandObjectiveExecutor {
   private static final int a = CommandObjectiveExecutor.EnumCommandResult.values().length;
   private static final String[] b = new String[a];
   private String[] c;
   private String[] d;

   public CommandObjectiveExecutor() {
      this.c = b;
      this.d = b;
   }

   public void a(final ICommandListener p_a_1_, CommandObjectiveExecutor.EnumCommandResult p_a_2_, int p_a_3_) {
      String s = this.c[p_a_2_.a()];
      if(s != null) {
         ICommandListener icommandlistener = new ICommandListener() {
            public String getName() {
               return p_a_1_.getName();
            }

            public IChatBaseComponent getScoreboardDisplayName() {
               return p_a_1_.getScoreboardDisplayName();
            }

            public void sendMessage(IChatBaseComponent p_sendMessage_1_) {
               p_a_1_.sendMessage(p_sendMessage_1_);
            }

            public boolean a(int p_a_1_x, String p_a_2_) {
               return true;
            }

            public BlockPosition getChunkCoordinates() {
               return p_a_1_.getChunkCoordinates();
            }

            public Vec3D d() {
               return p_a_1_.d();
            }

            public World getWorld() {
               return p_a_1_.getWorld();
            }

            public Entity f() {
               return p_a_1_.f();
            }

            public boolean getSendCommandFeedback() {
               return p_a_1_.getSendCommandFeedback();
            }

            public void a(CommandObjectiveExecutor.EnumCommandResult p_a_1_x, int p_a_2_) {
               p_a_1_.a(p_a_1_x, p_a_2_);
            }
         };

         String s1;
         try {
            s1 = CommandAbstract.e(icommandlistener, s);
         } catch (ExceptionEntityNotFound var11) {
            return;
         }

         String s2 = this.d[p_a_2_.a()];
         if(s2 != null) {
            Scoreboard scoreboard = p_a_1_.getWorld().getScoreboard();
            ScoreboardObjective scoreboardobjective = scoreboard.getObjective(s2);
            if(scoreboardobjective != null) {
               if(scoreboard.b(s1, scoreboardobjective)) {
                  ScoreboardScore scoreboardscore = scoreboard.getPlayerScoreForObjective(s1, scoreboardobjective);
                  scoreboardscore.setScore(p_a_3_);
               }
            }
         }
      }
   }

   public void a(NBTTagCompound p_a_1_) {
      if(p_a_1_.hasKeyOfType("CommandStats", 10)) {
         NBTTagCompound nbttagcompound = p_a_1_.getCompound("CommandStats");

         for(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor$enumcommandresult : CommandObjectiveExecutor.EnumCommandResult.values()) {
            String s = commandobjectiveexecutor$enumcommandresult.b() + "Name";
            String s1 = commandobjectiveexecutor$enumcommandresult.b() + "Objective";
            if(nbttagcompound.hasKeyOfType(s, 8) && nbttagcompound.hasKeyOfType(s1, 8)) {
               String s2 = nbttagcompound.getString(s);
               String s3 = nbttagcompound.getString(s1);
               a(this, commandobjectiveexecutor$enumcommandresult, s2, s3);
            }
         }

      }
   }

   public void b(NBTTagCompound p_b_1_) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();

      for(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor$enumcommandresult : CommandObjectiveExecutor.EnumCommandResult.values()) {
         String s = this.c[commandobjectiveexecutor$enumcommandresult.a()];
         String s1 = this.d[commandobjectiveexecutor$enumcommandresult.a()];
         if(s != null && s1 != null) {
            nbttagcompound.setString(commandobjectiveexecutor$enumcommandresult.b() + "Name", s);
            nbttagcompound.setString(commandobjectiveexecutor$enumcommandresult.b() + "Objective", s1);
         }
      }

      if(!nbttagcompound.isEmpty()) {
         p_b_1_.set("CommandStats", nbttagcompound);
      }

   }

   public static void a(CommandObjectiveExecutor p_a_0_, CommandObjectiveExecutor.EnumCommandResult p_a_1_, String p_a_2_, String p_a_3_) {
      if(p_a_2_ != null && p_a_2_.length() != 0 && p_a_3_ != null && p_a_3_.length() != 0) {
         if(p_a_0_.c == b || p_a_0_.d == b) {
            p_a_0_.c = new String[a];
            p_a_0_.d = new String[a];
         }

         p_a_0_.c[p_a_1_.a()] = p_a_2_;
         p_a_0_.d[p_a_1_.a()] = p_a_3_;
      } else {
         a(p_a_0_, p_a_1_);
      }
   }

   private static void a(CommandObjectiveExecutor p_a_0_, CommandObjectiveExecutor.EnumCommandResult p_a_1_) {
      if(p_a_0_.c != b && p_a_0_.d != b) {
         p_a_0_.c[p_a_1_.a()] = null;
         p_a_0_.d[p_a_1_.a()] = null;
         boolean flag = true;

         for(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor$enumcommandresult : CommandObjectiveExecutor.EnumCommandResult.values()) {
            if(p_a_0_.c[commandobjectiveexecutor$enumcommandresult.a()] != null && p_a_0_.d[commandobjectiveexecutor$enumcommandresult.a()] != null) {
               flag = false;
               break;
            }
         }

         if(flag) {
            p_a_0_.c = b;
            p_a_0_.d = b;
         }

      }
   }

   public void a(CommandObjectiveExecutor p_a_1_) {
      for(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor$enumcommandresult : CommandObjectiveExecutor.EnumCommandResult.values()) {
         a(this, commandobjectiveexecutor$enumcommandresult, p_a_1_.c[commandobjectiveexecutor$enumcommandresult.a()], p_a_1_.d[commandobjectiveexecutor$enumcommandresult.a()]);
      }

   }

   public static enum EnumCommandResult {
      SUCCESS_COUNT(0, "SuccessCount"),
      AFFECTED_BLOCKS(1, "AffectedBlocks"),
      AFFECTED_ENTITIES(2, "AffectedEntities"),
      AFFECTED_ITEMS(3, "AffectedItems"),
      QUERY_RESULT(4, "QueryResult");

      final int f;
      final String g;

      private EnumCommandResult(int p_i1106_3_, String p_i1106_4_) {
         this.f = p_i1106_3_;
         this.g = p_i1106_4_;
      }

      public int a() {
         return this.f;
      }

      public String b() {
         return this.g;
      }

      public static String[] c() {
         String[] astring = new String[values().length];
         int i = 0;

         for(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor$enumcommandresult : values()) {
            astring[i++] = commandobjectiveexecutor$enumcommandresult.b();
         }

         return astring;
      }

      public static CommandObjectiveExecutor.EnumCommandResult a(String p_a_0_) {
         for(CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor$enumcommandresult : values()) {
            if(commandobjectiveexecutor$enumcommandresult.b().equals(p_a_0_)) {
               return commandobjectiveexecutor$enumcommandresult;
            }
         }

         return null;
      }
   }
}
