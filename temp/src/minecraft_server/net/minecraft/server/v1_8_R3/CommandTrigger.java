package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.IScoreboardCriteria;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.Scoreboard;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.ScoreboardScore;

public class CommandTrigger extends CommandAbstract {
   public String getCommand() {
      return "trigger";
   }

   public int a() {
      return 0;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.trigger.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 3) {
         throw new ExceptionUsage("commands.trigger.usage", new Object[0]);
      } else {
         EntityPlayer entityplayer;
         if(p_execute_1_ instanceof EntityPlayer) {
            entityplayer = (EntityPlayer)p_execute_1_;
         } else {
            Entity entity = p_execute_1_.f();
            if(!(entity instanceof EntityPlayer)) {
               throw new CommandException("commands.trigger.invalidPlayer", new Object[0]);
            }

            entityplayer = (EntityPlayer)entity;
         }

         Scoreboard scoreboard = MinecraftServer.getServer().getWorldServer(0).getScoreboard();
         ScoreboardObjective scoreboardobjective = scoreboard.getObjective(p_execute_2_[0]);
         if(scoreboardobjective != null && scoreboardobjective.getCriteria() == IScoreboardCriteria.c) {
            int i = a(p_execute_2_[2]);
            if(!scoreboard.b(entityplayer.getName(), scoreboardobjective)) {
               throw new CommandException("commands.trigger.invalidObjective", new Object[]{p_execute_2_[0]});
            } else {
               ScoreboardScore scoreboardscore = scoreboard.getPlayerScoreForObjective(entityplayer.getName(), scoreboardobjective);
               if(scoreboardscore.g()) {
                  throw new CommandException("commands.trigger.disabled", new Object[]{p_execute_2_[0]});
               } else {
                  if("set".equals(p_execute_2_[1])) {
                     scoreboardscore.setScore(i);
                  } else {
                     if(!"add".equals(p_execute_2_[1])) {
                        throw new CommandException("commands.trigger.invalidMode", new Object[]{p_execute_2_[1]});
                     }

                     scoreboardscore.addScore(i);
                  }

                  scoreboardscore.a(true);
                  if(entityplayer.playerInteractManager.isCreative()) {
                     a(p_execute_1_, this, "commands.trigger.success", new Object[]{p_execute_2_[0], p_execute_2_[1], p_execute_2_[2]});
                  }

               }
            }
         } else {
            throw new CommandException("commands.trigger.invalidObjective", new Object[]{p_execute_2_[0]});
         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      if(p_tabComplete_2_.length == 1) {
         Scoreboard scoreboard = MinecraftServer.getServer().getWorldServer(0).getScoreboard();
         ArrayList arraylist = Lists.newArrayList();

         for(ScoreboardObjective scoreboardobjective : scoreboard.getObjectives()) {
            if(scoreboardobjective.getCriteria() == IScoreboardCriteria.c) {
               arraylist.add(scoreboardobjective.getName());
            }
         }

         return a(p_tabComplete_2_, (String[])arraylist.toArray(new String[arraylist.size()]));
      } else {
         return p_tabComplete_2_.length == 2?a(p_tabComplete_2_, new String[]{"add", "set"}):null;
      }
   }
}
