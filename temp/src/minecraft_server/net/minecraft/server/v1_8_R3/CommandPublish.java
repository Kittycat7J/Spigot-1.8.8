package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldSettings;

public class CommandPublish extends CommandAbstract {
   public String getCommand() {
      return "publish";
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.publish.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      String s = MinecraftServer.getServer().a(WorldSettings.EnumGamemode.SURVIVAL, false);
      if(s != null) {
         a(p_execute_1_, this, "commands.publish.started", new Object[]{s});
      } else {
         a(p_execute_1_, this, "commands.publish.failed", new Object[0]);
      }

   }
}
