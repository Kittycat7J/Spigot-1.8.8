package net.minecraft.server.v1_8_R3;

public class CommandSeed extends CommandAbstract
{
    public boolean canUse(ICommandListener p_canUse_1_)
    {
        return MinecraftServer.getServer().T() || super.canUse(p_canUse_1_);
    }

    public String getCommand()
    {
        return "seed";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.seed.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        Object object = p_execute_1_ instanceof EntityHuman ? ((EntityHuman)p_execute_1_).world : MinecraftServer.getServer().getWorldServer(0);
        p_execute_1_.sendMessage(new ChatMessage("commands.seed.success", new Object[] {Long.valueOf(((World)object).getSeed())}));
    }
}
