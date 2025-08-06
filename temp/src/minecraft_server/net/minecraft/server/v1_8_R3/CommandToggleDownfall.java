package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.WorldData;

public class CommandToggleDownfall extends CommandAbstract {
   public String getCommand() {
      return "toggledownfall";
   }

   public int a() {
      return 2;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.downfall.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      this.d();
      a(p_execute_1_, this, "commands.downfall.success", new Object[0]);
   }

   protected void d() {
      WorldData worlddata = MinecraftServer.getServer().worldServer[0].getWorldData();
      worlddata.setStorm(!worlddata.hasStorm());
   }
}
