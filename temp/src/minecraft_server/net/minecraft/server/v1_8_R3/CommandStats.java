package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.ScoreboardObjective;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityCommand;
import net.minecraft.server.v1_8_R3.TileEntitySign;
import net.minecraft.server.v1_8_R3.World;

public class CommandStats extends CommandAbstract {
   public String getCommand() {
      return "stats";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.stats.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length < 1) {
         throw new ExceptionUsage("commands.stats.usage", new Object[0]);
      } else {
         boolean flag;
         if(p_execute_2_[0].equals("entity")) {
            flag = false;
         } else {
            if(!p_execute_2_[0].equals("block")) {
               throw new ExceptionUsage("commands.stats.usage", new Object[0]);
            }

            flag = true;
         }

         int i;
         if(flag) {
            if(p_execute_2_.length < 5) {
               throw new ExceptionUsage("commands.stats.block.usage", new Object[0]);
            }

            i = 4;
         } else {
            if(p_execute_2_.length < 3) {
               throw new ExceptionUsage("commands.stats.entity.usage", new Object[0]);
            }

            i = 2;
         }

         String s = p_execute_2_[i++];
         if("set".equals(s)) {
            if(p_execute_2_.length < i + 3) {
               if(i == 5) {
                  throw new ExceptionUsage("commands.stats.block.set.usage", new Object[0]);
               }

               throw new ExceptionUsage("commands.stats.entity.set.usage", new Object[0]);
            }
         } else {
            if(!"clear".equals(s)) {
               throw new ExceptionUsage("commands.stats.usage", new Object[0]);
            }

            if(p_execute_2_.length < i + 1) {
               if(i == 5) {
                  throw new ExceptionUsage("commands.stats.block.clear.usage", new Object[0]);
               }

               throw new ExceptionUsage("commands.stats.entity.clear.usage", new Object[0]);
            }
         }

         CommandObjectiveExecutor.EnumCommandResult commandobjectiveexecutor$enumcommandresult = CommandObjectiveExecutor.EnumCommandResult.a(p_execute_2_[i++]);
         if(commandobjectiveexecutor$enumcommandresult == null) {
            throw new CommandException("commands.stats.failed", new Object[0]);
         } else {
            World world = p_execute_1_.getWorld();
            CommandObjectiveExecutor commandobjectiveexecutor;
            if(flag) {
               BlockPosition blockposition = a(p_execute_1_, p_execute_2_, 1, false);
               TileEntity tileentity = world.getTileEntity(blockposition);
               if(tileentity == null) {
                  throw new CommandException("commands.stats.noCompatibleBlock", new Object[]{Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ())});
               }

               if(tileentity instanceof TileEntityCommand) {
                  commandobjectiveexecutor = ((TileEntityCommand)tileentity).c();
               } else {
                  if(!(tileentity instanceof TileEntitySign)) {
                     throw new CommandException("commands.stats.noCompatibleBlock", new Object[]{Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ())});
                  }

                  commandobjectiveexecutor = ((TileEntitySign)tileentity).d();
               }
            } else {
               Entity entity = b(p_execute_1_, p_execute_2_[1]);
               commandobjectiveexecutor = entity.aU();
            }

            if("set".equals(s)) {
               String s1 = p_execute_2_[i++];
               String s2 = p_execute_2_[i];
               if(s1.length() == 0 || s2.length() == 0) {
                  throw new CommandException("commands.stats.failed", new Object[0]);
               }

               CommandObjectiveExecutor.a(commandobjectiveexecutor, commandobjectiveexecutor$enumcommandresult, s1, s2);
               a(p_execute_1_, this, "commands.stats.success", new Object[]{commandobjectiveexecutor$enumcommandresult.b(), s2, s1});
            } else if("clear".equals(s)) {
               CommandObjectiveExecutor.a(commandobjectiveexecutor, commandobjectiveexecutor$enumcommandresult, (String)null, (String)null);
               a(p_execute_1_, this, "commands.stats.cleared", new Object[]{commandobjectiveexecutor$enumcommandresult.b()});
            }

            if(flag) {
               BlockPosition blockposition1 = a(p_execute_1_, p_execute_2_, 1, false);
               TileEntity tileentity1 = world.getTileEntity(blockposition1);
               tileentity1.update();
            }

         }
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 1?a(p_tabComplete_2_, new String[]{"entity", "block"}):(p_tabComplete_2_.length == 2 && p_tabComplete_2_[0].equals("entity")?a(p_tabComplete_2_, this.d()):(p_tabComplete_2_.length >= 2 && p_tabComplete_2_.length <= 4 && p_tabComplete_2_[0].equals("block")?a(p_tabComplete_2_, 1, p_tabComplete_3_):((p_tabComplete_2_.length != 3 || !p_tabComplete_2_[0].equals("entity")) && (p_tabComplete_2_.length != 5 || !p_tabComplete_2_[0].equals("block"))?((p_tabComplete_2_.length != 4 || !p_tabComplete_2_[0].equals("entity")) && (p_tabComplete_2_.length != 6 || !p_tabComplete_2_[0].equals("block"))?((p_tabComplete_2_.length != 6 || !p_tabComplete_2_[0].equals("entity")) && (p_tabComplete_2_.length != 8 || !p_tabComplete_2_[0].equals("block"))?null:a(p_tabComplete_2_, this.e())):a(p_tabComplete_2_, CommandObjectiveExecutor.EnumCommandResult.c())):a(p_tabComplete_2_, new String[]{"set", "clear"}))));
   }

   protected String[] d() {
      return MinecraftServer.getServer().getPlayers();
   }

   protected List<String> e() {
      Collection collection = MinecraftServer.getServer().getWorldServer(0).getScoreboard().getObjectives();
      ArrayList arraylist = Lists.newArrayList();

      for(ScoreboardObjective scoreboardobjective : collection) {
         if(!scoreboardobjective.getCriteria().isReadOnly()) {
            arraylist.add(scoreboardobjective.getName());
         }
      }

      return arraylist;
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return p_isListStart_1_.length > 0 && p_isListStart_1_[0].equals("entity") && p_isListStart_2_ == 1;
   }
}
