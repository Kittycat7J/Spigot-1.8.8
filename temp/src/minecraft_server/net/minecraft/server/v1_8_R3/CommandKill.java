package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class CommandKill extends CommandAbstract {
   public String getCommand() {
      return "kill";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.kill.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length == 0) {
         EntityPlayer entityplayer = b(p_execute_1_);
         entityplayer.G();
         a(p_execute_1_, this, "commands.kill.successful", new Object[]{entityplayer.getScoreboardDisplayName()});
      } else {
         Entity entity = b(p_execute_1_, p_execute_2_[0]);
         entity.G();
         a(p_execute_1_, this, "commands.kill.successful", new Object[]{entity.getScoreboardDisplayName()});
      }
   }

   public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_) {
      return p_isListStart_2_ == 0;
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 1?a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()):null;
   }
}
