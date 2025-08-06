package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldServer;

public class CommandSaveOn extends CommandAbstract {
   public String getCommand() {
      return "save-on";
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.save-on.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      MinecraftServer minecraftserver = MinecraftServer.getServer();
      boolean flag = false;

      for(int i = 0; i < minecraftserver.worldServer.length; ++i) {
         if(minecraftserver.worldServer[i] != null) {
            WorldServer worldserver = minecraftserver.worldServer[i];
            if(worldserver.savingDisabled) {
               worldserver.savingDisabled = false;
               flag = true;
            }
         }
      }

      if(flag) {
         a(p_execute_1_, this, "commands.save.enabled", new Object[0]);
      } else {
         throw new CommandException("commands.save-on.alreadyOn", new Object[0]);
      }
   }
}
