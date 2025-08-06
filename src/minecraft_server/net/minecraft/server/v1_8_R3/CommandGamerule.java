package net.minecraft.server.v1_8_R3;

import java.util.List;

public class CommandGamerule extends CommandAbstract
{
    public String getCommand()
    {
        return "gamerule";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.gamerule.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        GameRules gamerules = p_execute_1_.getWorld().getGameRules();
        String s = p_execute_2_.length > 0 ? p_execute_2_[0] : "";
        String s1 = p_execute_2_.length > 1 ? a(p_execute_2_, 1) : "";

        switch (p_execute_2_.length)
        {
            case 0:
                p_execute_1_.sendMessage(new ChatComponentText(a(gamerules.getGameRules())));
                break;

            case 1:
                if (!gamerules.contains(s))
                {
                    throw new CommandException("commands.gamerule.norule", new Object[] {s});
                }

                String s2 = gamerules.get(s);
                p_execute_1_.sendMessage((new ChatComponentText(s)).a(" = ").a(s2));
                p_execute_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, gamerules.c(s));
                break;

            default:
                if (gamerules.a(s, GameRules.EnumGameRuleType.BOOLEAN_VALUE) && !"true".equals(s1) && !"false".equals(s1))
                {
                    throw new CommandException("commands.generic.boolean.invalid", new Object[] {s1});
                }

                gamerules.set(s, s1);
                a(gamerules, s);
                a(p_execute_1_, this, "commands.gamerule.success", new Object[0]);
        }
    }

    public static void a(GameRules p_a_0_, String p_a_1_)
    {
        if ("reducedDebugInfo".equals(p_a_1_))
        {
            int i = p_a_0_.getBoolean(p_a_1_) ? 22 : 23;

            for (EntityPlayer entityplayer : MinecraftServer.getServer().getPlayerList().v())
            {
                entityplayer.playerConnection.sendPacket(new PacketPlayOutEntityStatus(entityplayer, (byte)i));
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        if (p_tabComplete_2_.length == 1)
        {
            return a(p_tabComplete_2_, this.d().getGameRules());
        }
        else
        {
            if (p_tabComplete_2_.length == 2)
            {
                GameRules gamerules = this.d();

                if (gamerules.a(p_tabComplete_2_[0], GameRules.EnumGameRuleType.BOOLEAN_VALUE))
                {
                    return a(p_tabComplete_2_, new String[] {"true", "false"});
                }
            }

            return null;
        }
    }

    private GameRules d()
    {
        return MinecraftServer.getServer().getWorldServer(0).getGameRules();
    }

    public int compareTo(ICommand p_compareTo_1_)
    {
        return this.a(p_compareTo_1_);
    }
}
