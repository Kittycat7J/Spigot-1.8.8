package net.minecraft.server.v1_8_R3;

public class CommandStop extends CommandAbstract
{
    public String getCommand()
    {
        return "stop";
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.stop.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (MinecraftServer.getServer().worldServer != null)
        {
            a(p_execute_1_, this, "commands.stop.start", new Object[0]);
        }

        MinecraftServer.getServer().safeShutdown();
    }
}
