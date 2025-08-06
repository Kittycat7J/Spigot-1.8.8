package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandBanList extends CommandAbstract
{
    public String getCommand()
    {
        return "banlist";
    }

    public int a()
    {
        return 3;
    }

    public boolean canUse(ICommandListener p_canUse_1_)
    {
        return (MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() || MinecraftServer.getServer().getPlayerList().getProfileBans().isEnabled()) && super.canUse(p_canUse_1_);
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.banlist.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length >= 1 && p_execute_2_[0].equalsIgnoreCase("ips"))
        {
            p_execute_1_.sendMessage(new ChatMessage("commands.banlist.ips", new Object[] {Integer.valueOf(MinecraftServer.getServer().getPlayerList().getIPBans().getEntries().length)}));
            p_execute_1_.sendMessage(new ChatComponentText(a(MinecraftServer.getServer().getPlayerList().getIPBans().getEntries())));
        }
        else
        {
            p_execute_1_.sendMessage(new ChatMessage("commands.banlist.players", new Object[] {Integer.valueOf(MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries().length)}));
            p_execute_1_.sendMessage(new ChatComponentText(a(MinecraftServer.getServer().getPlayerList().getProfileBans().getEntries())));
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, new String[] {"players", "ips"}): null;
    }
}
