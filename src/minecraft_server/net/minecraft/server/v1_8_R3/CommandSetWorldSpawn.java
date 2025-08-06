package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandSetWorldSpawn extends CommandAbstract
{
    public String getCommand()
    {
        return "setworldspawn";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.setworldspawn.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        BlockPosition blockposition;

        if (p_execute_2_.length == 0)
        {
            blockposition = b(p_execute_1_).getChunkCoordinates();
        }
        else
        {
            if (p_execute_2_.length != 3 || p_execute_1_.getWorld() == null)
            {
                throw new ExceptionUsage("commands.setworldspawn.usage", new Object[0]);
            }

            blockposition = a(p_execute_1_, p_execute_2_, 0, true);
        }

        p_execute_1_.getWorld().B(blockposition);
        MinecraftServer.getServer().getPlayerList().sendAll(new PacketPlayOutSpawnPosition(blockposition));
        a(p_execute_1_, this, "commands.setworldspawn.success", new Object[] {Integer.valueOf(blockposition.getX()), Integer.valueOf(blockposition.getY()), Integer.valueOf(blockposition.getZ())});
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length > 0 && p_tabComplete_2_.length <= 3 ? a(p_tabComplete_2_, 0, p_tabComplete_3_) : null;
    }
}
