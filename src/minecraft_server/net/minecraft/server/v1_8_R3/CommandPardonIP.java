package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.regex.Matcher;

public class CommandPardonIP extends CommandAbstract
{
    public String getCommand()
    {
        return "pardon-ip";
    }

    public int a()
    {
        return 3;
    }

    public boolean canUse(ICommandListener p_canUse_1_)
    {
        return MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() && super.canUse(p_canUse_1_);
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.unbanip.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length == 1 && p_execute_2_[0].length() > 1)
        {
            Matcher matcher = CommandBanIp.a.matcher(p_execute_2_[0]);

            if (matcher.matches())
            {
                MinecraftServer.getServer().getPlayerList().getIPBans().remove(p_execute_2_[0]);
                a(p_execute_1_, this, "commands.unbanip.success", new Object[] {p_execute_2_[0]});
            }
            else
            {
                throw new ExceptionInvalidSyntax("commands.unbanip.invalid", new Object[0]);
            }
        }
        else
        {
            throw new ExceptionUsage("commands.unbanip.usage", new Object[0]);
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, MinecraftServer.getServer().getPlayerList().getIPBans().getEntries()) : null;
    }
}
