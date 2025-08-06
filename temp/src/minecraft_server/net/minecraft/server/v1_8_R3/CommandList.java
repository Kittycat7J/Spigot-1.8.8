package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.CommandObjectiveExecutor;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class CommandList extends CommandAbstract {
   public String getCommand() {
      return "list";
   }

   public int a() {
      return 0;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.players.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      int i = MinecraftServer.getServer().I();
      p_execute_1_.sendMessage(new ChatMessage("commands.players.list", new Object[]{Integer.valueOf(i), Integer.valueOf(MinecraftServer.getServer().J())}));
      p_execute_1_.sendMessage(new ChatComponentText(MinecraftServer.getServer().getPlayerList().b(p_execute_2_.length > 0 && "uuids".equalsIgnoreCase(p_execute_2_[0]))));
      p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, i);
   }
}
