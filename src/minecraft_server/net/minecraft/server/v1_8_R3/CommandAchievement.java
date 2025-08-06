package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

public class CommandAchievement extends CommandAbstract
{
    public String getCommand()
    {
        return "achievement";
    }

    public int a()
    {
        return 2;
    }

    public String getUsage(ICommandListener p_getUsage_1_)
    {
        return "commands.achievement.usage";
    }

    public void execute(ICommandListener p_execute_1_, String[] p_execute_2_) throws CommandException
    {
        if (p_execute_2_.length < 2)
        {
            throw new ExceptionUsage("commands.achievement.usage", new Object[0]);
        }
        else
        {
            final Statistic statistic = StatisticList.getStatistic(p_execute_2_[1]);

            if (statistic == null && !p_execute_2_[1].equals("*"))
            {
                throw new CommandException("commands.achievement.unknownAchievement", new Object[] {p_execute_2_[1]});
            }
            else
            {
                final EntityPlayer entityplayer = p_execute_2_.length >= 3 ? a(p_execute_1_, p_execute_2_[2]) : b(p_execute_1_);
                boolean flag = p_execute_2_[0].equalsIgnoreCase("give");
                boolean flag1 = p_execute_2_[0].equalsIgnoreCase("take");

                if (flag || flag1)
                {
                    if (statistic == null)
                    {
                        if (flag)
                        {
                            for (Achievement achievement4 : AchievementList.e)
                            {
                                entityplayer.b((Statistic)achievement4);
                            }

                            a(p_execute_1_, this, "commands.achievement.give.success.all", new Object[] {entityplayer.getName()});
                        }
                        else if (flag1)
                        {
                            for (Achievement achievement5 : Lists.reverse(AchievementList.e))
                            {
                                entityplayer.a((Statistic)achievement5);
                            }

                            a(p_execute_1_, this, "commands.achievement.take.success.all", new Object[] {entityplayer.getName()});
                        }
                    }
                    else
                    {
                        if (statistic instanceof Achievement)
                        {
                            Achievement achievement = (Achievement)statistic;

                            if (flag)
                            {
                                if (entityplayer.getStatisticManager().hasAchievement(achievement))
                                {
                                    throw new CommandException("commands.achievement.alreadyHave", new Object[] {entityplayer.getName(), statistic.j()});
                                }

                                ArrayList arraylist;

                                for (arraylist = Lists.newArrayList(); achievement.c != null && !entityplayer.getStatisticManager().hasAchievement(achievement.c); achievement = achievement.c)
                                {
                                    arraylist.add(achievement.c);
                                }

                                for (Achievement achievement1 : Lists.reverse(arraylist))
                                {
                                    entityplayer.b((Statistic)achievement1);
                                }
                            }
                            else if (flag1)
                            {
                                if (!entityplayer.getStatisticManager().hasAchievement(achievement))
                                {
                                    throw new CommandException("commands.achievement.dontHave", new Object[] {entityplayer.getName(), statistic.j()});
                                }

                                ArrayList arraylist1 = Lists.newArrayList(Iterators.filter(AchievementList.e.iterator(), new Predicate<Achievement>()
                                {
                                    public boolean a(Achievement p_a_1_)
                                    {
                                        return entityplayer.getStatisticManager().hasAchievement(p_a_1_) && p_a_1_ != statistic;
                                    }
                                }));
                                ArrayList arraylist2 = Lists.newArrayList(arraylist1);

                                for (Achievement achievement2 : arraylist1)
                                {
                                    Achievement achievement3 = achievement2;
                                    boolean flag2;

                                    for (flag2 = false; achievement3 != null; achievement3 = achievement3.c)
                                    {
                                        if (achievement3 == statistic)
                                        {
                                            flag2 = true;
                                        }
                                    }

                                    if (!flag2)
                                    {
                                        for (achievement3 = achievement2; achievement3 != null; achievement3 = achievement3.c)
                                        {
                                            arraylist2.remove(achievement2);
                                        }
                                    }
                                }

                                for (Achievement achievement6 : arraylist2)
                                {
                                    entityplayer.a((Statistic)achievement6);
                                }
                            }
                        }

                        if (flag)
                        {
                            entityplayer.b((Statistic)statistic);
                            a(p_execute_1_, this, "commands.achievement.give.success.one", new Object[] {entityplayer.getName(), statistic.j()});
                        }
                        else if (flag1)
                        {
                            entityplayer.a(statistic);
                            a(p_execute_1_, this, "commands.achievement.take.success.one", new Object[] {statistic.j(), entityplayer.getName()});
                        }
                    }
                }
            }
        }
    }

    public List<String> tabComplete(ICommandListener p_tabComplete_1_, String[] p_tabComplete_2_, BlockPosition p_tabComplete_3_)
    {
        if (p_tabComplete_2_.length == 1)
        {
            return a(p_tabComplete_2_, new String[] {"give", "take"});
        }
        else if (p_tabComplete_2_.length != 2)
        {
            return p_tabComplete_2_.length == 3 ? a(p_tabComplete_2_, MinecraftServer.getServer().getPlayers()) : null;
        }
        else
        {
            ArrayList arraylist = Lists.newArrayList();

            for (Statistic statistic : StatisticList.stats)
            {
                arraylist.add(statistic.name);
            }

            return a(p_tabComplete_2_, arraylist);
        }
    }

    public boolean isListStart(String[] p_isListStart_1_, int p_isListStart_2_)
    {
        return p_isListStart_2_ == 2;
    }
}
