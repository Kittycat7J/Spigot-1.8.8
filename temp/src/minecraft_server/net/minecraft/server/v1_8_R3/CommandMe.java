package net.minecraft.server.v1_8_R3;

import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class CommandMe extends CommandAbstract {
   public String getCommand() {
      return "me";
   }

   public int a() {
      return 0;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.me.usage";
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length <= 0) {
         throw new ExceptionUsage("commands.me.usage", new Object[0]);
      } else {
         IChatBaseComponent ichatbasecomponent = b(p_execute_1_, p_execute_2_, 0, !(p_execute_1_ instanceof EntityHuman));
         MinecraftServer.getServer().getPlayerList().sendMessage((IChatBaseComponent)(new ChatMessage("chat.type.emote", new Object[]{p_execute_1_.getScoreboardDisplayName(), ichatbasecomponent})));
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers());
   }
}
