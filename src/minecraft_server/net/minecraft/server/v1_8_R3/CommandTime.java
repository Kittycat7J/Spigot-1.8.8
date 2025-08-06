package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandTime extends CommandAbstract
{
    public String getCommand()
    {
        return "time";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.time.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length > 1)
        {
            if (p_execute_2_[0].equals("set"))
            {
                int l;

                if (p_execute_2_[1].equals("day"))
                {
                    l = 1000;
                }
                else if (p_execute_2_[1].equals("night"))
                {
                    l = 13000;
                }
                else
                {
                    l = a(p_execute_2_[1], 0);
                }

                this.a(p_execute_1_, l);
                a(p_execute_1_, this, "commands.time.set", new Object[] {Integer.valueOf(l)});
                return;
            }

            if (p_execute_2_[0].equals("add"))
            {
                int k = a(p_execute_2_[1], 0);
                this.b(p_execute_1_, k);
                a(p_execute_1_, this, "commands.time.added", new Object[] {Integer.valueOf(k)});
                return;
            }

            if (p_execute_2_[0].equals("query"))
            {
                if (p_execute_2_[1].equals("daytime"))
                {
                    int j = (int)(p_execute_1_.getWorld().getDayTime() % 2147483647L);
                    p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, j);
                    a(p_execute_1_, this, "commands.time.query", new Object[] {Integer.valueOf(j)});
                    return;
                }

                if (p_execute_2_[1].equals("gametime"))
                {
                    int i = (int)(p_execute_1_.getWorld().getTime() % 2147483647L);
                    p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, i);
                    a(p_execute_1_, this, "commands.time.query", new Object[] {Integer.valueOf(i)});
                    return;
                }
            }
        }

        throw new ExceptionUsage("commands.time.usage", new Object[0]);
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 1 ? a(p_tabComplete_2_, new String[] {"set", "add", "query"}): (p_tabComplete_2_.length == 2 && p_tabComplete_2_[0].equals("set") ? a(p_tabComplete_2_, new String[] {"day", "night"}): (p_tabComplete_2_.length == 2 && p_tabComplete_2_[0].equals("query") ? a(p_tabComplete_2_, new String[] {"daytime", "gametime"}): null));
    }

    protected void a(ICommandListener p_a_1_, int p_a_2_)
    {
        for (int i = 0; i < MinecraftServer.getServer().worldServer.length; ++i)
        {
            MinecraftServer.getServer().worldServer[i].setDayTime((long)p_a_2_);
        }
    }

    protected void b(ICommandListener p_b_1_, int p_b_2_)
    {
        for (int i = 0; i < MinecraftServer.getServer().worldServer.length; ++i)
        {
            WorldServer worldserver = MinecraftServer.getServer().worldServer[i];
            worldserver.setDayTime(worldserver.getDayTime() + (long)p_b_2_);
        }
    }
}
