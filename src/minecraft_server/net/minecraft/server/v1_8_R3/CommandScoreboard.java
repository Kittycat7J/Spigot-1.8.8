package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandScoreboard extends CommandAbstract
{
    public String getCommand()
    {
        return "scoreboard";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.scoreboard.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (!this.b(p_execute_1_, p_execute_2_))
        {
            if (p_execute_2_.length < 1)
            {
                throw new ExceptionUsage("commands.scoreboard.usage", new Object[0]);
            }
            else
            {
                if (p_execute_2_[0].equalsIgnoreCase("objectives"))
                {
                    if (p_execute_2_.length == 1)
                    {
                        throw new ExceptionUsage("commands.scoreboard.objectives.usage", new Object[0]);
                    }

                    if (p_execute_2_[1].equalsIgnoreCase("list"))
                    {
                        this.d(p_execute_1_);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("add"))
                    {
                        if (p_execute_2_.length < 4)
                        {
                            throw new ExceptionUsage("commands.scoreboard.objectives.add.usage", new Object[0]);
                        }

                        this.b(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("remove"))
                    {
                        if (p_execute_2_.length != 3)
                        {
                            throw new ExceptionUsage("commands.scoreboard.objectives.remove.usage", new Object[0]);
                        }

                        this.h(p_execute_1_, p_execute_2_[2]);
                    }
                    else
                    {
                        if (!p_execute_2_[1].equalsIgnoreCase("setdisplay"))
                        {
                            throw new ExceptionUsage("commands.scoreboard.objectives.usage", new Object[0]);
                        }

                        if (p_execute_2_.length != 3 && p_execute_2_.length != 4)
                        {
                            throw new ExceptionUsage("commands.scoreboard.objectives.setdisplay.usage", new Object[0]);
                        }

                        this.j(p_execute_1_, p_execute_2_, 2);
                    }
                }
                else if (p_execute_2_[0].equalsIgnoreCase("players"))
                {
                    if (p_execute_2_.length == 1)
                    {
                        throw new ExceptionUsage("commands.scoreboard.players.usage", new Object[0]);
                    }

                    if (p_execute_2_[1].equalsIgnoreCase("list"))
                    {
                        if (p_execute_2_.length > 3)
                        {
                            throw new ExceptionUsage("commands.scoreboard.players.list.usage", new Object[0]);
                        }

                        this.k(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("add"))
                    {
                        if (p_execute_2_.length < 5)
                        {
                            throw new ExceptionUsage("commands.scoreboard.players.add.usage", new Object[0]);
                        }

                        this.l(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("remove"))
                    {
                        if (p_execute_2_.length < 5)
                        {
                            throw new ExceptionUsage("commands.scoreboard.players.remove.usage", new Object[0]);
                        }

                        this.l(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("set"))
                    {
                        if (p_execute_2_.length < 5)
                        {
                            throw new ExceptionUsage("commands.scoreboard.players.set.usage", new Object[0]);
                        }

                        this.l(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("reset"))
                    {
                        if (p_execute_2_.length != 3 && p_execute_2_.length != 4)
                        {
                            throw new ExceptionUsage("commands.scoreboard.players.reset.usage", new Object[0]);
                        }

                        this.m(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("enable"))
                    {
                        if (p_execute_2_.length != 4)
                        {
                            throw new ExceptionUsage("commands.scoreboard.players.enable.usage", new Object[0]);
                        }

                        this.n(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("test"))
                    {
                        if (p_execute_2_.length != 5 && p_execute_2_.length != 6)
                        {
                            throw new ExceptionUsage("commands.scoreboard.players.test.usage", new Object[0]);
                        }

                        this.o(p_execute_1_, p_execute_2_, 2);
                    }
                    else
                    {
                        if (!p_execute_2_[1].equalsIgnoreCase("operation"))
                        {
                            throw new ExceptionUsage("commands.scoreboard.players.usage", new Object[0]);
                        }

                        if (p_execute_2_.length != 7)
                        {
                            throw new ExceptionUsage("commands.scoreboard.players.operation.usage", new Object[0]);
                        }

                        this.p(p_execute_1_, p_execute_2_, 2);
                    }
                }
                else
                {
                    if (!p_execute_2_[0].equalsIgnoreCase("teams"))
                    {
                        throw new ExceptionUsage("commands.scoreboard.usage", new Object[0]);
                    }

                    if (p_execute_2_.length == 1)
                    {
                        throw new ExceptionUsage("commands.scoreboard.teams.usage", new Object[0]);
                    }

                    if (p_execute_2_[1].equalsIgnoreCase("list"))
                    {
                        if (p_execute_2_.length > 3)
                        {
                            throw new ExceptionUsage("commands.scoreboard.teams.list.usage", new Object[0]);
                        }

                        this.f(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("add"))
                    {
                        if (p_execute_2_.length < 3)
                        {
                            throw new ExceptionUsage("commands.scoreboard.teams.add.usage", new Object[0]);
                        }

                        this.c(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("remove"))
                    {
                        if (p_execute_2_.length != 3)
                        {
                            throw new ExceptionUsage("commands.scoreboard.teams.remove.usage", new Object[0]);
                        }

                        this.e(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("empty"))
                    {
                        if (p_execute_2_.length != 3)
                        {
                            throw new ExceptionUsage("commands.scoreboard.teams.empty.usage", new Object[0]);
                        }

                        this.i(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("join"))
                    {
                        if (p_execute_2_.length < 4 && (p_execute_2_.length != 3 || !(p_execute_1_ instanceof EntityHuman)))
                        {
                            throw new ExceptionUsage("commands.scoreboard.teams.join.usage", new Object[0]);
                        }

                        this.g(p_execute_1_, p_execute_2_, 2);
                    }
                    else if (p_execute_2_[1].equalsIgnoreCase("leave"))
                    {
                        if (p_execute_2_.length < 3 && !(p_execute_1_ instanceof EntityHuman))
                        {
                            throw new ExceptionUsage("commands.scoreboard.teams.leave.usage", new Object[0]);
                        }

                        this.h(p_execute_1_, p_execute_2_, 2);
                    }
                    else
                    {
                        if (!p_execute_2_[1].equalsIgnoreCase("option"))
                        {
                            throw new ExceptionUsage("commands.scoreboard.teams.usage", new Object[0]);
                        }

                        if (p_execute_2_.length != 4 && p_execute_2_.length != 5)
                        {
                            throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
                        }

                        this.d(p_execute_1_, p_execute_2_, 2);
                    }
                }
            }
        }
    }

    private boolean b(ICommandListener p_b_1_, String[] p_b_2_) throws CommandException
    {
        int i = -1;

        for (int j = 0; j < p_b_2_.length; ++j)
        {
            if (this.isListStart(p_b_2_, j) && "*".equals(p_b_2_[j]))
            {
                if (i >= 0)
                {
                    throw new CommandException("commands.scoreboard.noMultiWildcard", new Object[0]);
                }

                i = j;
            }
        }

        if (i < 0)
        {
            return false;
        }
        else
        {
            ArrayList arraylist1 = Lists.newArrayList(this.d().getPlayers());
            String s = p_b_2_[i];
            ArrayList arraylist = Lists.newArrayList();

            for (String s1 : arraylist1)
            {
                p_b_2_[i] = s1;

                try
                {
                    this.execute(p_b_1_, p_b_2_);
                    arraylist.add(s1);
                }
                catch (CommandException commandexception)
                {
                    ChatMessage chatmessage = new ChatMessage(commandexception.getMessage(), commandexception.getArgs());
                    chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
                    p_b_1_.sendMessage(chatmessage);
                }
            }

            p_b_2_[i] = s;
            p_b_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, arraylist.size());

            if (arraylist.size() == 0)
            {
                throw new ExceptionUsage("commands.scoreboard.allMatchesFailed", new Object[0]);
            }
            else
            {
                return true;
            }
        }
    }

    protected Scoreboard d()
    {
        return MinecraftServer.getServer().getWorldServer(0).getScoreboard();
    }

    protected ScoreboardObjective a(String p_a_1_, boolean p_a_2_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        ScoreboardObjective scoreboardobjective = scoreboard.getObjective(p_a_1_);

        if (scoreboardobjective == null)
        {
            throw new CommandException("commands.scoreboard.objectiveNotFound", new Object[] {p_a_1_});
        }
        else if (p_a_2_ && scoreboardobjective.getCriteria().isReadOnly())
        {
            throw new CommandException("commands.scoreboard.objectiveReadOnly", new Object[] {p_a_1_});
        }
        else
        {
            return scoreboardobjective;
        }
    }

    protected ScoreboardTeam e(String p_e_1_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        ScoreboardTeam scoreboardteam = scoreboard.getTeam(p_e_1_);

        if (scoreboardteam == null)
        {
            throw new CommandException("commands.scoreboard.teamNotFound", new Object[] {p_e_1_});
        }
        else
        {
            return scoreboardteam;
        }
    }

    protected void b(ICommandListener p_b_1_, String[] p_b_2_, int p_b_3_) throws CommandException
    {
        String s = p_b_2_[p_b_3_++];
        String s1 = p_b_2_[p_b_3_++];
        Scoreboard scoreboard = this.d();
        IScoreboardCriteria iscoreboardcriteria = (IScoreboardCriteria)IScoreboardCriteria.criteria.get(s1);

        if (iscoreboardcriteria == null)
        {
            throw new ExceptionUsage("commands.scoreboard.objectives.add.wrongType", new Object[] {s1});
        }
        else if (scoreboard.getObjective(s) != null)
        {
            throw new CommandException("commands.scoreboard.objectives.add.alreadyExists", new Object[] {s});
        }
        else if (s.length() > 16)
        {
            throw new ExceptionInvalidSyntax("commands.scoreboard.objectives.add.tooLong", new Object[] {s, Integer.valueOf(16)});
        }
        else if (s.length() == 0)
        {
            throw new ExceptionUsage("commands.scoreboard.objectives.add.usage", new Object[0]);
        }
        else
        {
            if (p_b_2_.length > p_b_3_)
            {
                String s2 = a(p_b_1_, p_b_2_, p_b_3_).c();

                if (s2.length() > 32)
                {
                    throw new ExceptionInvalidSyntax("commands.scoreboard.objectives.add.displayTooLong", new Object[] {s2, Integer.valueOf(32)});
                }

                if (s2.length() > 0)
                {
                    scoreboard.registerObjective(s, iscoreboardcriteria).setDisplayName(s2);
                }
                else
                {
                    scoreboard.registerObjective(s, iscoreboardcriteria);
                }
            }
            else
            {
                scoreboard.registerObjective(s, iscoreboardcriteria);
            }

            a(p_b_1_, this, "commands.scoreboard.objectives.add.success", new Object[] {s});
        }
    }

    protected void c(ICommandListener p_c_1_, String[] p_c_2_, int p_c_3_) throws CommandException
    {
        String s = p_c_2_[p_c_3_++];
        Scoreboard scoreboard = this.d();

        if (scoreboard.getTeam(s) != null)
        {
            throw new CommandException("commands.scoreboard.teams.add.alreadyExists", new Object[] {s});
        }
        else if (s.length() > 16)
        {
            throw new ExceptionInvalidSyntax("commands.scoreboard.teams.add.tooLong", new Object[] {s, Integer.valueOf(16)});
        }
        else if (s.length() == 0)
        {
            throw new ExceptionUsage("commands.scoreboard.teams.add.usage", new Object[0]);
        }
        else
        {
            if (p_c_2_.length > p_c_3_)
            {
                String s1 = a(p_c_1_, p_c_2_, p_c_3_).c();

                if (s1.length() > 32)
                {
                    throw new ExceptionInvalidSyntax("commands.scoreboard.teams.add.displayTooLong", new Object[] {s1, Integer.valueOf(32)});
                }

                if (s1.length() > 0)
                {
                    scoreboard.createTeam(s).setDisplayName(s1);
                }
                else
                {
                    scoreboard.createTeam(s);
                }
            }
            else
            {
                scoreboard.createTeam(s);
            }

            a(p_c_1_, this, "commands.scoreboard.teams.add.success", new Object[] {s});
        }
    }

    protected void d(ICommandListener p_d_1_, String[] p_d_2_, int p_d_3_) throws CommandException
    {
        ScoreboardTeam scoreboardteam = this.e(p_d_2_[p_d_3_++]);

        if (scoreboardteam != null)
        {
            String s = p_d_2_[p_d_3_++].toLowerCase();

            if (!s.equalsIgnoreCase("color") && !s.equalsIgnoreCase("friendlyfire") && !s.equalsIgnoreCase("seeFriendlyInvisibles") && !s.equalsIgnoreCase("nametagVisibility") && !s.equalsIgnoreCase("deathMessageVisibility"))
            {
                throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
            }
            else if (p_d_2_.length == 4)
            {
                if (s.equalsIgnoreCase("color"))
                {
                    throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] {s, a(EnumChatFormat.a(true, false))});
                }
                else if (!s.equalsIgnoreCase("friendlyfire") && !s.equalsIgnoreCase("seeFriendlyInvisibles"))
                {
                    if (!s.equalsIgnoreCase("nametagVisibility") && !s.equalsIgnoreCase("deathMessageVisibility"))
                    {
                        throw new ExceptionUsage("commands.scoreboard.teams.option.usage", new Object[0]);
                    }
                    else
                    {
                        throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] {s, a(ScoreboardTeamBase.EnumNameTagVisibility.a())});
                    }
                }
                else
                {
                    throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] {s, a(Arrays.asList(new String[]{"true", "false"}))});
                }
            }
            else
            {
                String s1 = p_d_2_[p_d_3_];

                if (s.equalsIgnoreCase("color"))
                {
                    EnumChatFormat enumchatformat = EnumChatFormat.b(s1);

                    if (enumchatformat == null || enumchatformat.isFormat())
                    {
                        throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] {s, a(EnumChatFormat.a(true, false))});
                    }

                    scoreboardteam.a(enumchatformat);
                    scoreboardteam.setPrefix(enumchatformat.toString());
                    scoreboardteam.setSuffix(EnumChatFormat.RESET.toString());
                }
                else if (s.equalsIgnoreCase("friendlyfire"))
                {
                    if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false"))
                    {
                        throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] {s, a(Arrays.asList(new String[]{"true", "false"}))});
                    }

                    scoreboardteam.setAllowFriendlyFire(s1.equalsIgnoreCase("true"));
                }
                else if (s.equalsIgnoreCase("seeFriendlyInvisibles"))
                {
                    if (!s1.equalsIgnoreCase("true") && !s1.equalsIgnoreCase("false"))
                    {
                        throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] {s, a(Arrays.asList(new String[]{"true", "false"}))});
                    }

                    scoreboardteam.setCanSeeFriendlyInvisibles(s1.equalsIgnoreCase("true"));
                }
                else if (s.equalsIgnoreCase("nametagVisibility"))
                {
                    ScoreboardTeamBase.EnumNameTagVisibility scoreboardteambase$enumnametagvisibility = ScoreboardTeamBase.EnumNameTagVisibility.a(s1);

                    if (scoreboardteambase$enumnametagvisibility == null)
                    {
                        throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] {s, a(ScoreboardTeamBase.EnumNameTagVisibility.a())});
                    }

                    scoreboardteam.setNameTagVisibility(scoreboardteambase$enumnametagvisibility);
                }
                else if (s.equalsIgnoreCase("deathMessageVisibility"))
                {
                    ScoreboardTeamBase.EnumNameTagVisibility scoreboardteambase$enumnametagvisibility1 = ScoreboardTeamBase.EnumNameTagVisibility.a(s1);

                    if (scoreboardteambase$enumnametagvisibility1 == null)
                    {
                        throw new ExceptionUsage("commands.scoreboard.teams.option.noValue", new Object[] {s, a(ScoreboardTeamBase.EnumNameTagVisibility.a())});
                    }

                    scoreboardteam.b(scoreboardteambase$enumnametagvisibility1);
                }

                a(p_d_1_, this, "commands.scoreboard.teams.option.success", new Object[] {s, scoreboardteam.getName(), s1});
            }
        }
    }

    protected void e(ICommandListener p_e_1_, String[] p_e_2_, int p_e_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        ScoreboardTeam scoreboardteam = this.e(p_e_2_[p_e_3_]);

        if (scoreboardteam != null)
        {
            scoreboard.removeTeam(scoreboardteam);
            a(p_e_1_, this, "commands.scoreboard.teams.remove.success", new Object[] {scoreboardteam.getName()});
        }
    }

    protected void f(ICommandListener p_f_1_, String[] p_f_2_, int p_f_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();

        if (p_f_2_.length > p_f_3_)
        {
            ScoreboardTeam scoreboardteam = this.e(p_f_2_[p_f_3_]);

            if (scoreboardteam == null)
            {
                return;
            }

            Collection collection = scoreboardteam.getPlayerNameSet();
            p_f_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, collection.size());

            if (collection.size() <= 0)
            {
                throw new CommandException("commands.scoreboard.teams.list.player.empty", new Object[] {scoreboardteam.getName()});
            }

            ChatMessage chatmessage = new ChatMessage("commands.scoreboard.teams.list.player.count", new Object[] {Integer.valueOf(collection.size()), scoreboardteam.getName()});
            chatmessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
            p_f_1_.sendMessage(chatmessage);
            p_f_1_.sendMessage(new ChatComponentText(a(collection.toArray())));
        }
        else
        {
            Collection collection1 = scoreboard.getTeams();
            p_f_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, collection1.size());

            if (collection1.size() <= 0)
            {
                throw new CommandException("commands.scoreboard.teams.list.empty", new Object[0]);
            }

            ChatMessage chatmessage1 = new ChatMessage("commands.scoreboard.teams.list.count", new Object[] {Integer.valueOf(collection1.size())});
            chatmessage1.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
            p_f_1_.sendMessage(chatmessage1);

            for (ScoreboardTeam scoreboardteam1 : collection1)
            {
                p_f_1_.sendMessage(new ChatMessage("commands.scoreboard.teams.list.entry", new Object[] {scoreboardteam1.getName(), scoreboardteam1.getDisplayName(), Integer.valueOf(scoreboardteam1.getPlayerNameSet().size())}));
            }
        }
    }

    protected void g(ICommandListener p_g_1_, String[] p_g_2_, int p_g_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        String s = p_g_2_[p_g_3_++];
        HashSet hashset = Sets.newHashSet();
        HashSet hashset1 = Sets.newHashSet();

        if (p_g_1_ instanceof EntityHuman && p_g_3_ == p_g_2_.length)
        {
            String s4 = b(p_g_1_).getName();

            if (scoreboard.addPlayerToTeam(s4, s))
            {
                hashset.add(s4);
            }
            else
            {
                hashset1.add(s4);
            }
        }
        else
        {
            while (p_g_3_ < p_g_2_.length)
            {
                String s1 = p_g_2_[p_g_3_++];

                if (s1.startsWith("@"))
                {
                    for (Entity entity : c(p_g_1_, s1))
                    {
                        String s3 = e(p_g_1_, entity.getUniqueID().toString());

                        if (scoreboard.addPlayerToTeam(s3, s))
                        {
                            hashset.add(s3);
                        }
                        else
                        {
                            hashset1.add(s3);
                        }
                    }
                }
                else
                {
                    String s2 = e(p_g_1_, s1);

                    if (scoreboard.addPlayerToTeam(s2, s))
                    {
                        hashset.add(s2);
                    }
                    else
                    {
                        hashset1.add(s2);
                    }
                }
            }
        }

        if (!hashset.isEmpty())
        {
            p_g_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, hashset.size());
            a(p_g_1_, this, "commands.scoreboard.teams.join.success", new Object[] {Integer.valueOf(hashset.size()), s, a(hashset.toArray(new String[hashset.size()]))});
        }

        if (!hashset1.isEmpty())
        {
            throw new CommandException("commands.scoreboard.teams.join.failure", new Object[] {Integer.valueOf(hashset1.size()), s, a(hashset1.toArray(new String[hashset1.size()]))});
        }
    }

    protected void h(ICommandListener p_h_1_, String[] p_h_2_, int p_h_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        HashSet hashset = Sets.newHashSet();
        HashSet hashset1 = Sets.newHashSet();

        if (p_h_1_ instanceof EntityHuman && p_h_3_ == p_h_2_.length)
        {
            String s3 = b(p_h_1_).getName();

            if (scoreboard.removePlayerFromTeam(s3))
            {
                hashset.add(s3);
            }
            else
            {
                hashset1.add(s3);
            }
        }
        else
        {
            while (p_h_3_ < p_h_2_.length)
            {
                String s = p_h_2_[p_h_3_++];

                if (s.startsWith("@"))
                {
                    for (Entity entity : c(p_h_1_, s))
                    {
                        String s2 = e(p_h_1_, entity.getUniqueID().toString());

                        if (scoreboard.removePlayerFromTeam(s2))
                        {
                            hashset.add(s2);
                        }
                        else
                        {
                            hashset1.add(s2);
                        }
                    }
                }
                else
                {
                    String s1 = e(p_h_1_, s);

                    if (scoreboard.removePlayerFromTeam(s1))
                    {
                        hashset.add(s1);
                    }
                    else
                    {
                        hashset1.add(s1);
                    }
                }
            }
        }

        if (!hashset.isEmpty())
        {
            p_h_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, hashset.size());
            a(p_h_1_, this, "commands.scoreboard.teams.leave.success", new Object[] {Integer.valueOf(hashset.size()), a(hashset.toArray(new String[hashset.size()]))});
        }

        if (!hashset1.isEmpty())
        {
            throw new CommandException("commands.scoreboard.teams.leave.failure", new Object[] {Integer.valueOf(hashset1.size()), a(hashset1.toArray(new String[hashset1.size()]))});
        }
    }

    protected void i(ICommandListener p_i_1_, String[] p_i_2_, int p_i_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        ScoreboardTeam scoreboardteam = this.e(p_i_2_[p_i_3_]);

        if (scoreboardteam != null)
        {
            ArrayList arraylist = Lists.newArrayList(scoreboardteam.getPlayerNameSet());
            p_i_1_.a(CommandObjectiveExecutor.EnumCommandResult.AFFECTED_ENTITIES, arraylist.size());

            if (arraylist.isEmpty())
            {
                throw new CommandException("commands.scoreboard.teams.empty.alreadyEmpty", new Object[] {scoreboardteam.getName()});
            }
            else
            {
                for (String s : arraylist)
                {
                    scoreboard.removePlayerFromTeam(s, scoreboardteam);
                }

                a(p_i_1_, this, "commands.scoreboard.teams.empty.success", new Object[] {Integer.valueOf(arraylist.size()), scoreboardteam.getName()});
            }
        }
    }

    protected void h(ICommandListener p_h_1_, String p_h_2_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        ScoreboardObjective scoreboardobjective = this.a(p_h_2_, false);
        scoreboard.unregisterObjective(scoreboardobjective);
        a(p_h_1_, this, "commands.scoreboard.objectives.remove.success", new Object[] {p_h_2_});
    }

    protected void d(ICommandListener p_d_1_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        Collection collection = scoreboard.getObjectives();

        if (collection.size() <= 0)
        {
            throw new CommandException("commands.scoreboard.objectives.list.empty", new Object[0]);
        }
        else
        {
            ChatMessage chatmessage = new ChatMessage("commands.scoreboard.objectives.list.count", new Object[] {Integer.valueOf(collection.size())});
            chatmessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
            p_d_1_.sendMessage(chatmessage);

            for (ScoreboardObjective scoreboardobjective : collection)
            {
                p_d_1_.sendMessage(new ChatMessage("commands.scoreboard.objectives.list.entry", new Object[] {scoreboardobjective.getName(), scoreboardobjective.getDisplayName(), scoreboardobjective.getCriteria().getName()}));
            }
        }
    }

    protected void j(ICommandListener p_j_1_, String[] p_j_2_, int p_j_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        String s = p_j_2_[p_j_3_++];
        int i = Scoreboard.getSlotForName(s);
        ScoreboardObjective scoreboardobjective = null;

        if (p_j_2_.length == 4)
        {
            scoreboardobjective = this.a(p_j_2_[p_j_3_], false);
        }

        if (i < 0)
        {
            throw new CommandException("commands.scoreboard.objectives.setdisplay.invalidSlot", new Object[] {s});
        }
        else
        {
            scoreboard.setDisplaySlot(i, scoreboardobjective);

            if (scoreboardobjective != null)
            {
                a(p_j_1_, this, "commands.scoreboard.objectives.setdisplay.successSet", new Object[] {Scoreboard.getSlotName(i), scoreboardobjective.getName()});
            }
            else
            {
                a(p_j_1_, this, "commands.scoreboard.objectives.setdisplay.successCleared", new Object[] {Scoreboard.getSlotName(i)});
            }
        }
    }

    protected void k(ICommandListener p_k_1_, String[] p_k_2_, int p_k_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();

        if (p_k_2_.length > p_k_3_)
        {
            String s = e(p_k_1_, p_k_2_[p_k_3_]);
            Map map = scoreboard.getPlayerObjectives(s);
            p_k_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, map.size());

            if (map.size() <= 0)
            {
                throw new CommandException("commands.scoreboard.players.list.player.empty", new Object[] {s});
            }

            ChatMessage chatmessage = new ChatMessage("commands.scoreboard.players.list.player.count", new Object[] {Integer.valueOf(map.size()), s});
            chatmessage.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
            p_k_1_.sendMessage(chatmessage);

            for (ScoreboardScore scoreboardscore : map.values())
            {
                p_k_1_.sendMessage(new ChatMessage("commands.scoreboard.players.list.player.entry", new Object[] {Integer.valueOf(scoreboardscore.getScore()), scoreboardscore.getObjective().getDisplayName(), scoreboardscore.getObjective().getName()}));
            }
        }
        else
        {
            Collection collection = scoreboard.getPlayers();
            p_k_1_.a(CommandObjectiveExecutor.EnumCommandResult.QUERY_RESULT, collection.size());

            if (collection.size() <= 0)
            {
                throw new CommandException("commands.scoreboard.players.list.empty", new Object[0]);
            }

            ChatMessage chatmessage1 = new ChatMessage("commands.scoreboard.players.list.count", new Object[] {Integer.valueOf(collection.size())});
            chatmessage1.getChatModifier().setColor(EnumChatFormat.DARK_GREEN);
            p_k_1_.sendMessage(chatmessage1);
            p_k_1_.sendMessage(new ChatComponentText(a(collection.toArray())));
        }
    }

    protected void l(ICommandListener p_l_1_, String[] p_l_2_, int p_l_3_) throws CommandException
    {
        String s = p_l_2_[p_l_3_ - 1];
        int i = p_l_3_;
        String s1 = e(p_l_1_, p_l_2_[p_l_3_++]);

        if (s1.length() > 40)
        {
            throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] {s1, Integer.valueOf(40)});
        }
        else
        {
            ScoreboardObjective scoreboardobjective = this.a(p_l_2_[p_l_3_++], true);
            int j = s.equalsIgnoreCase("set") ? a(p_l_2_[p_l_3_++]) : a(p_l_2_[p_l_3_++], 0);

            if (p_l_2_.length > p_l_3_)
            {
                Entity entity = b(p_l_1_, p_l_2_[i]);

                try
                {
                    NBTTagCompound nbttagcompound = MojangsonParser.parse(a(p_l_2_, p_l_3_));
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    entity.e(nbttagcompound1);

                    if (!GameProfileSerializer.a(nbttagcompound, nbttagcompound1, true))
                    {
                        throw new CommandException("commands.scoreboard.players.set.tagMismatch", new Object[] {s1});
                    }
                }
                catch (MojangsonParseException mojangsonparseexception)
                {
                    throw new CommandException("commands.scoreboard.players.set.tagError", new Object[] {mojangsonparseexception.getMessage()});
                }
            }

            Scoreboard scoreboard = this.d();
            ScoreboardScore scoreboardscore = scoreboard.getPlayerScoreForObjective(s1, scoreboardobjective);

            if (s.equalsIgnoreCase("set"))
            {
                scoreboardscore.setScore(j);
            }
            else if (s.equalsIgnoreCase("add"))
            {
                scoreboardscore.addScore(j);
            }
            else
            {
                scoreboardscore.removeScore(j);
            }

            a(p_l_1_, this, "commands.scoreboard.players.set.success", new Object[] {scoreboardobjective.getName(), s1, Integer.valueOf(scoreboardscore.getScore())});
        }
    }

    protected void m(ICommandListener p_m_1_, String[] p_m_2_, int p_m_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        String s = e(p_m_1_, p_m_2_[p_m_3_++]);

        if (p_m_2_.length > p_m_3_)
        {
            ScoreboardObjective scoreboardobjective = this.a(p_m_2_[p_m_3_++], false);
            scoreboard.resetPlayerScores(s, scoreboardobjective);
            a(p_m_1_, this, "commands.scoreboard.players.resetscore.success", new Object[] {scoreboardobjective.getName(), s});
        }
        else
        {
            scoreboard.resetPlayerScores(s, (ScoreboardObjective)null);
            a(p_m_1_, this, "commands.scoreboard.players.reset.success", new Object[] {s});
        }
    }

    protected void n(ICommandListener p_n_1_, String[] p_n_2_, int p_n_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        String s = d(p_n_1_, p_n_2_[p_n_3_++]);

        if (s.length() > 40)
        {
            throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] {s, Integer.valueOf(40)});
        }
        else
        {
            ScoreboardObjective scoreboardobjective = this.a(p_n_2_[p_n_3_], false);

            if (scoreboardobjective.getCriteria() != IScoreboardCriteria.c)
            {
                throw new CommandException("commands.scoreboard.players.enable.noTrigger", new Object[] {scoreboardobjective.getName()});
            }
            else
            {
                ScoreboardScore scoreboardscore = scoreboard.getPlayerScoreForObjective(s, scoreboardobjective);
                scoreboardscore.a(false);
                a(p_n_1_, this, "commands.scoreboard.players.enable.success", new Object[] {scoreboardobjective.getName(), s});
            }
        }
    }

    protected void o(ICommandListener p_o_1_, String[] p_o_2_, int p_o_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        String s = e(p_o_1_, p_o_2_[p_o_3_++]);

        if (s.length() > 40)
        {
            throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] {s, Integer.valueOf(40)});
        }
        else
        {
            ScoreboardObjective scoreboardobjective = this.a(p_o_2_[p_o_3_++], false);

            if (!scoreboard.b(s, scoreboardobjective))
            {
                throw new CommandException("commands.scoreboard.players.test.notFound", new Object[] {scoreboardobjective.getName(), s});
            }
            else
            {
                int i = p_o_2_[p_o_3_].equals("*") ? Integer.MIN_VALUE : a(p_o_2_[p_o_3_]);
                ++p_o_3_;
                int j = p_o_3_ < p_o_2_.length && !p_o_2_[p_o_3_].equals("*") ? a(p_o_2_[p_o_3_], i) : Integer.MAX_VALUE;
                ScoreboardScore scoreboardscore = scoreboard.getPlayerScoreForObjective(s, scoreboardobjective);

                if (scoreboardscore.getScore() >= i && scoreboardscore.getScore() <= j)
                {
                    a(p_o_1_, this, "commands.scoreboard.players.test.success", new Object[] {Integer.valueOf(scoreboardscore.getScore()), Integer.valueOf(i), Integer.valueOf(j)});
                }
                else
                {
                    throw new CommandException("commands.scoreboard.players.test.failed", new Object[] {Integer.valueOf(scoreboardscore.getScore()), Integer.valueOf(i), Integer.valueOf(j)});
                }
            }
        }
    }

    protected void p(ICommandListener p_p_1_, String[] p_p_2_, int p_p_3_) throws CommandException
    {
        Scoreboard scoreboard = this.d();
        String s = e(p_p_1_, p_p_2_[p_p_3_++]);
        ScoreboardObjective scoreboardobjective = this.a(p_p_2_[p_p_3_++], true);
        String s1 = p_p_2_[p_p_3_++];
        String s2 = e(p_p_1_, p_p_2_[p_p_3_++]);
        ScoreboardObjective scoreboardobjective1 = this.a(p_p_2_[p_p_3_], false);

        if (s.length() > 40)
        {
            throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] {s, Integer.valueOf(40)});
        }
        else if (s2.length() > 40)
        {
            throw new ExceptionInvalidSyntax("commands.scoreboard.players.name.tooLong", new Object[] {s2, Integer.valueOf(40)});
        }
        else
        {
            ScoreboardScore scoreboardscore = scoreboard.getPlayerScoreForObjective(s, scoreboardobjective);

            if (!scoreboard.b(s2, scoreboardobjective1))
            {
                throw new CommandException("commands.scoreboard.players.operation.notFound", new Object[] {scoreboardobjective1.getName(), s2});
            }
            else
            {
                ScoreboardScore scoreboardscore1 = scoreboard.getPlayerScoreForObjective(s2, scoreboardobjective1);

                if (s1.equals("+="))
                {
                    scoreboardscore.setScore(scoreboardscore.getScore() + scoreboardscore1.getScore());
                }
                else if (s1.equals("-="))
                {
                    scoreboardscore.setScore(scoreboardscore.getScore() - scoreboardscore1.getScore());
                }
                else if (s1.equals("*="))
                {
                    scoreboardscore.setScore(scoreboardscore.getScore() * scoreboardscore1.getScore());
                }
                else if (s1.equals("/="))
                {
                    if (scoreboardscore1.getScore() != 0)
                    {
                        scoreboardscore.setScore(scoreboardscore.getScore() / scoreboardscore1.getScore());
                    }
                }
                else if (s1.equals("%="))
                {
                    if (scoreboardscore1.getScore() != 0)
                    {
                        scoreboardscore.setScore(scoreboardscore.getScore() % scoreboardscore1.getScore());
                    }
                }
                else if (s1.equals("="))
                {
                    scoreboardscore.setScore(scoreboardscore1.getScore());
                }
                else if (s1.equals("<"))
                {
                    scoreboardscore.setScore(Math.min(scoreboardscore.getScore(), scoreboardscore1.getScore()));
                }
                else if (s1.equals(">"))
                {
                    scoreboardscore.setScore(Math.max(scoreboardscore.getScore(), scoreboardscore1.getScore()));
                }
                else
                {
                    if (!s1.equals("><"))
                    {
                        throw new CommandException("commands.scoreboard.players.operation.invalidOperation", new Object[] {s1});
                    }

                    int i = scoreboardscore.getScore();
                    scoreboardscore.setScore(scoreboardscore1.getScore());
                    scoreboardscore1.setScore(i);
                }

                a(p_p_1_, this, "commands.scoreboard.players.operation.success", new Object[0]);
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        if (p_tabComplete_2_.length == 1)
        {
            return a(p_tabComplete_2_, new String[] {"objectives", "players", "teams"});
        }
        else
        {
            if (p_tabComplete_2_[0].equalsIgnoreCase("objectives"))
            {
                if (p_tabComplete_2_.length == 2)
                {
                    return a(p_tabComplete_2_, new String[] {"list", "add", "remove", "setdisplay"});
                }

                if (p_tabComplete_2_[1].equalsIgnoreCase("add"))
                {
                    if (p_tabComplete_2_.length == 4)
                    {
                        Set set = IScoreboardCriteria.criteria.keySet();
                        return a(p_tabComplete_2_, set);
                    }
                }
                else if (p_tabComplete_2_[1].equalsIgnoreCase("remove"))
                {
                    if (p_tabComplete_2_.length == 3)
                    {
                        return a(p_tabComplete_2_, this.a(false));
                    }
                }
                else if (p_tabComplete_2_[1].equalsIgnoreCase("setdisplay"))
                {
                    if (p_tabComplete_2_.length == 3)
                    {
                        return a(p_tabComplete_2_, Scoreboard.h());
                    }

                    if (p_tabComplete_2_.length == 4)
                    {
                        return a(p_tabComplete_2_, this.a(false));
                    }
                }
            }
            else if (p_tabComplete_2_[0].equalsIgnoreCase("players"))
            {
                if (p_tabComplete_2_.length == 2)
                {
                    return a(p_tabComplete_2_, new String[] {"set", "add", "remove", "reset", "list", "enable", "test", "operation"});
                }

                if (!p_tabComplete_2_[1].equalsIgnoreCase("set") && !p_tabComplete_2_[1].equalsIgnoreCase("add") && !p_tabComplete_2_[1].equalsIgnoreCase("remove") && !p_tabComplete_2_[1].equalsIgnoreCase("reset"))
                {
                    if (p_tabComplete_2_[1].equalsIgnoreCase("enable"))
                    {
                        if (p_tabComplete_2_.length == 3)
                        {
                            return a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers());
                        }

                        if (p_tabComplete_2_.length == 4)
                        {
                            return a(p_tabComplete_2_, this.e());
                        }
                    }
                    else if (!p_tabComplete_2_[1].equalsIgnoreCase("list") && !p_tabComplete_2_[1].equalsIgnoreCase("test"))
                    {
                        if (p_tabComplete_2_[1].equalsIgnoreCase("operation"))
                        {
                            if (p_tabComplete_2_.length == 3)
                            {
                                return a(p_tabComplete_2_, this.d().getPlayers());
                            }

                            if (p_tabComplete_2_.length == 4)
                            {
                                return a(p_tabComplete_2_, this.a(true));
                            }

                            if (p_tabComplete_2_.length == 5)
                            {
                                return a(p_tabComplete_2_, new String[] {"+=", "-=", "*=", "/=", "%=", "=", "<", ">", "><"});
                            }

                            if (p_tabComplete_2_.length == 6)
                            {
                                return a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers());
                            }

                            if (p_tabComplete_2_.length == 7)
                            {
                                return a(p_tabComplete_2_, this.a(false));
                            }
                        }
                    }
                    else
                    {
                        if (p_tabComplete_2_.length == 3)
                        {
                            return a(p_tabComplete_2_, this.d().getPlayers());
                        }

                        if (p_tabComplete_2_.length == 4 && p_tabComplete_2_[1].equalsIgnoreCase("test"))
                        {
                            return a(p_tabComplete_2_, this.a(false));
                        }
                    }
                }
                else
                {
                    if (p_tabComplete_2_.length == 3)
                    {
                        return a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers());
                    }

                    if (p_tabComplete_2_.length == 4)
                    {
                        return a(p_tabComplete_2_, this.a(true));
                    }
                }
            }
            else if (p_tabComplete_2_[0].equalsIgnoreCase("teams"))
            {
                if (p_tabComplete_2_.length == 2)
                {
                    return a(p_tabComplete_2_, new String[] {"add", "remove", "join", "leave", "empty", "list", "option"});
                }

                if (p_tabComplete_2_[1].equalsIgnoreCase("join"))
                {
                    if (p_tabComplete_2_.length == 3)
                    {
                        return a(p_tabComplete_2_, this.d().getTeamNames());
                    }

                    if (p_tabComplete_2_.length >= 4)
                    {
                        return a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers());
                    }
                }
                else
                {
                    if (p_tabComplete_2_[1].equalsIgnoreCase("leave"))
                    {
                        return a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers());
                    }

                    if (!p_tabComplete_2_[1].equalsIgnoreCase("empty") && !p_tabComplete_2_[1].equalsIgnoreCase("list") && !p_tabComplete_2_[1].equalsIgnoreCase("remove"))
                    {
                        if (p_tabComplete_2_[1].equalsIgnoreCase("option"))
                        {
                            if (p_tabComplete_2_.length == 3)
                            {
                                return a(p_tabComplete_2_, this.d().getTeamNames());
                            }

                            if (p_tabComplete_2_.length == 4)
                            {
                                return a(p_tabComplete_2_, new String[] {"color", "friendlyfire", "seeFriendlyInvisibles", "nametagVisibility", "deathMessageVisibility"});
                            }

                            if (p_tabComplete_2_.length == 5)
                            {
                                if (p_tabComplete_2_[3].equalsIgnoreCase("color"))
                                {
                                    return a(p_tabComplete_2_, EnumChatFormat.a(true, false));
                                }

                                if (p_tabComplete_2_[3].equalsIgnoreCase("nametagVisibility") || p_tabComplete_2_[3].equalsIgnoreCase("deathMessageVisibility"))
                                {
                                    return a(p_tabComplete_2_, ScoreboardTeamBase.EnumNameTagVisibility.a());
                                }

                                if (p_tabComplete_2_[3].equalsIgnoreCase("friendlyfire") || p_tabComplete_2_[3].equalsIgnoreCase("seeFriendlyInvisibles"))
                                {
                                    return a(p_tabComplete_2_, new String[] {"true", "false"});
                                }
                            }
                        }
                    }
                    else if (p_tabComplete_2_.length == 3)
                    {
                        return a(p_tabComplete_2_, this.d().getTeamNames());
                    }
                }
            }

            return null;
        }
    }

    protected List<String> a(boolean p_a_1_)
    {
        Collection collection = this.d().getObjectives();
        ArrayList arraylist = Lists.newArrayList();

        for (ScoreboardObjective scoreboardobjective : collection)
        {
            if (!p_a_1_ || !scoreboardobjective.getCriteria().isReadOnly())
            {
                arraylist.add(scoreboardobjective.getName());
            }
        }

        return arraylist;
    }

    protected List<String> e()
    {
        Collection collection = this.d().getObjectives();
        ArrayList arraylist = Lists.newArrayList();

        for (ScoreboardObjective scoreboardobjective : collection)
        {
            if (scoreboardobjective.getCriteria() == IScoreboardCriteria.c)
            {
                arraylist.add(scoreboardobjective.getName());
            }
        }

        return arraylist;
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return !p_isListStart_1_[0].equalsIgnoreCase("players") ? (p_isListStart_1_[0].equalsIgnoreCase("teams") ? p_isListStart_2_ == 2 : false) : (p_isListStart_1_.length > 1 && p_isListStart_1_[1].equalsIgnoreCase("operation") ? p_isListStart_2_ == 2 || p_isListStart_2_ == 5 : p_isListStart_2_ == 2);
    }
}
