package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandSay extends CommandAbstract
{
    public String getCommand()
    {
        return "say";
    }

    public int a()
    {
        return 1;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.say.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length > 0 && p_execute_2_[0].length() > 0)
        {
            IChatBaseComponent ichatbasecomponent = b(p_execute_1_, p_execute_2_, 0, true);
            MinecraftServer.getServer().getPlayerList().sendMessage((IChatBaseComponent)(new ChatMessage("chat.type.announcement", new Object[] {p_execute_1_.getScoreboardDisplayName(), ichatbasecomponent})));
        }
        else
        {
            throw new ExceptionUsage("commands.say.usage", new Object[0]);
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length >= 1 ? a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()) : null;
    }
}
