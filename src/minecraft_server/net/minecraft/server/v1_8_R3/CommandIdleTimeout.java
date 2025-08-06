package net.minecraft.server.v1_8_R3;

public class CommandIdleTimeout extends CommandAbstract
{
    public String getCommand()
    {
        return "setidletimeout";
    }

    public int a()
    {
        return 3;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.setidletimeout.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length != 1)
        {
            throw new ExceptionUsage("commands.setidletimeout.usage", new Object[0]);
        }
        else
        {
            int i = a(p_execute_2_[0], 0);
            MinecraftServer.getServer().setIdleTimeout(i);
            a(p_execute_1_, this, "commands.setidletimeout.success", new Object[] {Integer.valueOf(i)});
        }
    }
}
