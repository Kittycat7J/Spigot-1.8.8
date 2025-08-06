package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandSpawnpoint extends CommandAbstract
{
    public String getCommand()
    {
        return "spawnpoint";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.spawnpoint.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length > 1 && p_execute_2_.length < 4)
        {
            throw new ExceptionUsage("commands.spawnpoint.usage", new Object[0]);
        }
        else
        {
            EntityPlayer entityplayer = p_execute_2_.length > 0 ? a(p_execute_1_, p_execute_2_[0]) : b(p_execute_1_);
            BlockPosition blockposition = p_execute_2_.length > 3 ? a(p_execute_1_, p_execute_2_, 1, true) : entityplayer.getChunkCoordinates();

            if (entityplayer.world != null)
            {
                entityplayer.setRespawnPosition(blockposition, true);
                a(p_execute_1_, this, "commands.spawnpoint.success", new Object[] {entityplayer.getName(), Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ())});
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()) : (p_tabComplete_2_.length > 1 && p_tabComplete_2_.length <= 4 ? a(p_tabComplete_2_, 1, p_tabComplete_3_) : null);
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return p_isListStart_2_ == 0;
    }
}
