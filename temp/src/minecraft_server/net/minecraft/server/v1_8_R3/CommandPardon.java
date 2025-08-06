package net.minecraft.server.v1_8_R3;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class CommandPardon extends CommandAbstract {
   public String getCommand() {
      return "pardon";
   }

   public int a() {
      return 3;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.unban.usage";
   }

   public boolean canUse(ICommandListener p_canUse_1_) {
      return MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled() && super.canUse(p_canUse_1_);
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length == 1 && p_execute_2_[0].length() > 0) {
         MinecraftServer minecraftserver = MinecraftServer.getServer();
         GameProfile gameprofile = minecraftserver.getPlayerList().getProfileBans().a(p_execute_2_[0]);
         if(gameprofile == null) {
            throw new CommandException("commands.unban.failed", new Object[]{p_execute_2_[0]});
         } else {
            minecraftserver.getPlayerList().getProfileBans().remove(gameprofile);
            a(p_execute_1_, this, "commands.unban.success", new Object[]{p_execute_2_[0]});
         }
      } else {
         throw new ExceptionUsage("commands.unban.usage", new Object[0]);
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length == 1?a(p_tabComplete_2_, MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries()):null;
   }
}
