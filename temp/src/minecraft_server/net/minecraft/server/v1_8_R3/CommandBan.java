package net.minecraft.server.v1_8_R3;

import com.mojang.authlib.GameProfile;
import java.util.Date;
import java.util.List;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CommandAbstract;
import net.minecraft.server.v1_8_R3.CommandException;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ExceptionUsage;
import net.minecraft.server.v1_8_R3.GameProfileBanEntry;
import net.minecraft.server.v1_8_R3.ICommandListener;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class CommandBan extends CommandAbstract {
   public String getCommand() {
      return "ban";
   }

   public int a() {
      return 3;
   }

   public String getUsage(ICommandListener p_getUsage_1_) {
      return "commands.ban.usage";
   }

   public boolean canUse(ICommandListener p_canUse_1_) {
      return MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled() && super.canUse(p_canUse_1_);
   }

   public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException {
      if(p_execute_2_.length >= 1 && p_execute_2_[0].length() > 0) {
         MinecraftServer minecraftserver = MinecraftServer.getServer();
         GameProfile gameprofile = minecraftserver.getUserCache().getProfile(p_execute_2_[0]);
         if(gameprofile == null) {
            throw new CommandException("commands.ban.failed", new Object[]{p_execute_2_[0]});
         } else {
            String s = null;
            if(p_execute_2_.length >= 2) {
               s = a(p_execute_1_, p_execute_2_, 1).c();
            }

            GameProfileBanEntry gameprofilebanentry = new GameProfileBanEntry(gameprofile, (Date)null, p_execute_1_.getName(), (Date)null, s);
            minecraftserver.getPlayerList().getProfileBans().add(gameprofilebanentry);
            EntityPlayer entityplayer = minecraftserver.getPlayerList().getPlayer(p_execute_2_[0]);
            if(entityplayer != null) {
               entityplayer.playerConnection.disconnect("You are banned from this server.");
            }

            a(p_execute_1_, this, "commands.ban.success", new Object[]{p_execute_2_[0]});
         }
      } else {
         throw new ExceptionUsage("commands.ban.usage", new Object[0]);
      }
   }

   public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_) {
      return p_tabComplete_2_.length >= 1?a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()):null;
   }
}
