package net.minecraft.server.v1_8_R3;

public class CommandGamemodeDefault extends CommandGamemode
{
    public String getCommand()
    {
        return "defaultgamemode";
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.defaultgamemode.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length <= 0)
        {
            throw new ExceptionUsage("commands.defaultgamemode.usage", new Object[0]);
        }
        else
        {
            WorldSettings.EnumGamemode worldsettings$enumgamemode = this.h(p_execute_1_, p_execute_2_[0]);
            this.a(worldsettings$enumgamemode);
            a(p_execute_1_, this, "commands.defaultgamemode.success", new Object[] {new ChatMessage("gameMode." + worldsettings$enumgamemode.b(), new Object[0])});
        }
    }

    protected void a(WorldSettings.EnumGamemode p_a_1_)
    {
        MinecraftServer minecraftserver = MinecraftServer.getServer();
        minecraftserver.setGamemode(p_a_1_);

        if (minecraftserver.getForceGamemode())
        {
            for (EntityPlayer entityplayer : MinecraftServer.getServer().getPlayerList().v())
            {
                entityplayer.a(p_a_1_);
                entityplayer.fallDistance = 0.0F;
            }
        }
    }
}
