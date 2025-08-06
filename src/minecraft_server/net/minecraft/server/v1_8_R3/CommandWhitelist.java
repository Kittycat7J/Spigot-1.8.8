package net.minecraft.server.v1_8_R3;

import com.mojang.authlib.GameProfile;
import java.util.List;

public class CommandWhitelist extends CommandAbstract
{
    public String getCommand()
    {
        return "whitelist";
    }

    public int a()
    {
        return 3;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.whitelist.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 1)
        {
            throw new ExceptionUsage("commands.whitelist.usage", new Object[0]);
        }
        else
        {
            MinecraftServer minecraftserver = MinecraftServer.getServer();

            if (p_execute_2_[0].equals("on"))
            {
                minecraftserver.getPlayerList().setHasWhitelist(true);
                a(p_execute_1_, this, "commands.whitelist.enabled", new Object[0]);
            }
            else if (p_execute_2_[0].equals("off"))
            {
                minecraftserver.getPlayerList().setHasWhitelist(false);
                a(p_execute_1_, this, "commands.whitelist.disabled", new Object[0]);
            }
            else if (p_execute_2_[0].equals("list"))
            {
                p_execute_1_.sendMessage(new ChatMessage("commands.whitelist.list", new Object[] {Integer.valueOf(minecraftserver.getPlayerList().getWhitelisted().length), Integer.valueOf(minecraftserver.getPlayerList().getSeenPlayers().length)}));
                String[] astring = minecraftserver.getPlayerList().getWhitelisted();
                p_execute_1_.sendMessage(new ChatComponentText(a(astring)));
            }
            else if (p_execute_2_[0].equals("add"))
            {
                if (p_execute_2_.length < 2)
                {
                    throw new ExceptionUsage("commands.whitelist.add.usage", new Object[0]);
                }

                GameProfile gameprofile = minecraftserver.getUserCache().getProfile(p_execute_2_[1]);

                if (gameprofile == null)
                {
                    throw new CommandException("commands.whitelist.add.failed", new Object[] {p_execute_2_[1]});
                }

                minecraftserver.getPlayerList().addWhitelist(gameprofile);
                a(p_execute_1_, this, "commands.whitelist.add.success", new Object[] {p_execute_2_[1]});
            }
            else if (p_execute_2_[0].equals("remove"))
            {
                if (p_execute_2_.length < 2)
                {
                    throw new ExceptionUsage("commands.whitelist.remove.usage", new Object[0]);
                }

                GameProfile gameprofile1 = minecraftserver.getPlayerList().getWhitelist().a(p_execute_2_[1]);

                if (gameprofile1 == null)
                {
                    throw new CommandException("commands.whitelist.remove.failed", new Object[] {p_execute_2_[1]});
                }

                minecraftserver.getPlayerList().removeWhitelist(gameprofile1);
                a(p_execute_1_, this, "commands.whitelist.remove.success", new Object[] {p_execute_2_[1]});
            }
            else if (p_execute_2_[0].equals("reload"))
            {
                minecraftserver.getPlayerList().reloadWhitelist();
                a(p_execute_1_, this, "commands.whitelist.reloaded", new Object[0]);
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        if (p_tabComplete_2_.length == 1)
        {
            return a(p_tabComplete_2_, new String[] {"on", "off", "list", "add", "remove", "reload"});
        }
        else
        {
            if (p_tabComplete_2_.length == 2)
            {
                if (p_tabComplete_2_[0].equals("remove"))
                {
                    return a(p_tabComplete_2_, MinecraftServer.getServer().getPlayerList().getWhitelisted());
                }

                if (p_tabComplete_2_[0].equals("add"))
                {
                    return a(p_tabComplete_2_, MinecraftServer.getServer().getUserCache().a());
                }
            }

            return null;
        }
    }
}
