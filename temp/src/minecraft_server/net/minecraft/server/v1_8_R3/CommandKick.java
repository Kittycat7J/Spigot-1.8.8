package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ExceptionPlayerNotFound;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class CommandKick extends CommandAbstract {
   public String getCommand() {
      return "kick";
   }

   public int a() {
      return 3;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.kick.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length > 0 && p_execute_2_[0].length() > 1) {
         EntityPlayer entityplayer = MinecraftServer.getServer().getPlayerList().getPlayer(p_execute_2_[0]);
         String s = "Kicked by an operator.";
         boolean flag = false;
         if(entityplayer == null) {
            throw new ExceptionPlayerNotFound();
         } else {
            if(p_execute_2_.length >= 2) {
               s = a(p_execute_1_, p_execute_2_, 1).c();
               flag = true;
            }

            entityplayer.playerConnection.disconnect(s);
            if(flag) {
               a(p_execute_1_, this, "commands.kick.success.reason", new Object[]{entityplayer.getName(), s});
            } else {
               a(p_execute_1_, this, "commands.kick.success", new Object[]{entityplayer.getName()});
            }

         }
      } else {
         throw new ExceptionUsage("commands.kick.usage", new Object[0]);
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length >= 1?a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()):null;
   }
}
