package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandXp extends CommandAbstract
{
    public String getCommand()
    {
        return "xp";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.xp.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length <= 0)
        {
            throw new ExceptionUsage("commands.xp.usage", new Object[0]);
        }
        else
        {
            String s = p_execute_2_[0];
            boolean flag = s.endsWith("l") || s.endsWith("L");

            if (flag && s.length() > 1)
            {
                s = s.substring(0, s.length() - 1);
            }

            int i = a(s);
            boolean flag1 = i < 0;

            if (flag1)
            {
                i *= -1;
            }

            EntityPlayer entityplayer = p_execute_2_.length > 1 ? a(p_execute_1_, p_execute_2_[1]) : b(p_execute_1_);

            if (flag)
            {
                p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, entityplayer.expLevel);

                if (flag1)
                {
                    entityplayer.levelDown(-i);
                    a(p_execute_1_, this, "commands.xp.success.negative.levels", new Object[] {Integer.valueOf(i), entityplayer.getName()});
                }
                else
                {
                    entityplayer.levelDown(i);
                    a(p_execute_1_, this, "commands.xp.success.levels", new Object[] {Integer.valueOf(i), entityplayer.getName()});
                }
            }
            else
            {
                p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, entityplayer.expTotal);

                if (flag1)
                {
                    throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
                }

                entityplayer.giveExp(i);
                a(p_execute_1_, this, "commands.xp.success", new Object[] {Integer.valueOf(i), entityplayer.getName()});
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        return p_tabComplete_2_.length == 2 ? a(p_tabComplete_2_, this.d()) : null;
    }

    protected String[] d()
    {
        return MinecraftServer.getServer().getPlayers();
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return p_isListStart_2_ == 1;
    }
}
