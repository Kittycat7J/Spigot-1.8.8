package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerSelector
{
    private static final Pattern a = Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
    private static final Pattern b = Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
    private static final Pattern c = Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
    private static final Set<String> d = Sets.newHashSet(new String[] {"x", "y", "z", "dx", "dy", "dz", "rm", "r"});

    public static EntityPlayer getPlayer(ICommandListener p_getPlayer_0_, String p_getPlayer_1_)
    {
        return (EntityPlayer)getEntity(p_getPlayer_0_, p_getPlayer_1_, EntityPlayer.class);
    }

    public static <T extends Entity> T getEntity(ICommandListener p_getEntity_0_, String p_getEntity_1_, Class <? extends T > p_getEntity_2_)
    {
        List list = getPlayers(p_getEntity_0_, p_getEntity_1_, p_getEntity_2_);
        return (T)(list.size() == 1 ? (Entity)list.get(0) : null);
    }

    public static IChatBaseComponent getPlayerNames(ICommandListener p_getPlayerNames_0_, String p_getPlayerNames_1_)
    {
        List list = getPlayers(p_getPlayerNames_0_, p_getPlayerNames_1_, Entity.class);

        if (list.isEmpty())
        {
            return null;
        }
        else
        {
            ArrayList arraylist = Lists.newArrayList();

            for (Entity entity : list)
            {
                arraylist.add(entity.getScoreboardDisplayName());
            }

            return CommandAbstract.a((List)arraylist);
        }
    }

    public static <T extends Entity> List<T> getPlayers(ICommandListener p_getPlayers_0_, String p_getPlayers_1_, Class <? extends T > p_getPlayers_2_)
    {
        Matcher matcher = a.matcher(p_getPlayers_1_);

        if (matcher.matches() && p_getPlayers_0_.a(1, "@"))
        {
            Map map = c(matcher.group(2));

            if (!b(p_getPlayers_0_, map))
            {
                return Collections.emptyList();
            }
            else
            {
                String s = matcher.group(1);
                BlockPosition blockposition = b(map, p_getPlayers_0_.getChunkCoordinates());
                List list = a(p_getPlayers_0_, map);
                ArrayList arraylist = Lists.newArrayList();

                for (World world : list)
                {
                    if (world != null)
                    {
                        ArrayList arraylist1 = Lists.newArrayList();
                        arraylist1.addAll(a(map, s));
                        arraylist1.addAll(b(map));
                        arraylist1.addAll(c(map));
                        arraylist1.addAll(d(map));
                        arraylist1.addAll(e(map));
                        arraylist1.addAll(f(map));
                        arraylist1.addAll(a(map, blockposition));
                        arraylist1.addAll(g(map));
                        arraylist.addAll(a(map, p_getPlayers_2_, arraylist1, s, (World)world, blockposition));
                    }
                }

                return a(arraylist, map, p_getPlayers_0_, (Class)p_getPlayers_2_, (String)s, blockposition);
            }
        }
        else
        {
            return Collections.emptyList();
        }
    }

    private static List<World> a(ICommandListener p_a_0_, Map<String, String> p_a_1_)
    {
        ArrayList arraylist = Lists.newArrayList();

        if (h(p_a_1_))
        {
            arraylist.add(p_a_0_.getWorld());
        }
        else
        {
            Collections.addAll(arraylist, MinecraftServer.getServer().worldServer);
        }

        return arraylist;
    }

    private static <T extends Entity> boolean b(ICommandListener p_b_0_, Map<String, String> p_b_1_)
    {
        String s = b(p_b_1_, "type");
        s = s != null && s.startsWith("!") ? s.substring(1) : s;

        if (s != null && !EntityTypes.b(s))
        {
            ChatMessage chatmessage = new ChatMessage("commands.generic.entity.invalidType", new Object[] {s});
            chatmessage.getChatModifier().setColor(EnumChatFormat.RED);
            p_b_0_.sendMessage(chatmessage);
            return false;
        }
        else
        {
            return true;
        }
    }

    private static List<Predicate<Entity>> a(Map<String, String> p_a_0_, String p_a_1_)
    {
        ArrayList arraylist = Lists.newArrayList();
        final String s = b(p_a_0_, "type");
        final boolean flag = s != null && s.startsWith("!");

        if (flag)
        {
            s = s.substring(1);
        }

        boolean flag1 = !p_a_1_.equals("e");
        boolean flag2 = p_a_1_.equals("r") && s != null;

        if ((s == null || !p_a_1_.equals("e")) && !flag2)
        {
            if (flag1)
            {
                arraylist.add(new Predicate<Entity>()
                {
                    public boolean a(Entity p_a_1_)
                    {
                        return p_a_1_ instanceof EntityHuman;
                    }
                });
            }
        }
        else
        {
            arraylist.add(new Predicate<Entity>()
            {
                public boolean a(Entity p_a_1_)
                {
                    return EntityTypes.a(p_a_1_, s) != flag;
                }
            });
        }

        return arraylist;
    }

    private static List<Predicate<Entity>> b(Map<String, String> p_b_0_)
    {
        ArrayList arraylist = Lists.newArrayList();
        final int i = a(p_b_0_, "lm", -1);
        final int j = a(p_b_0_, "l", -1);

        if (i > -1 || j > -1)
        {
            arraylist.add(new Predicate<Entity>()
            {
                public boolean a(Entity p_a_1_)
                {
                    if (!(p_a_1_ instanceof EntityPlayer))
                    {
                        return false;
                    }
                    else
                    {
                        EntityPlayer entityplayer = (EntityPlayer)p_a_1_;
                        return (i <= -1 || entityplayer.expLevel >= i) && (j <= -1 || entityplayer.expLevel <= j);
                    }
                }
            });
        }

        return arraylist;
    }

    private static List<Predicate<Entity>> c(Map<String, String> p_c_0_)
    {
        ArrayList arraylist = Lists.newArrayList();
        final int i = a(p_c_0_, "m", WorldSettings.EnumGamemode.NOT_SET.getId());

        if (i != WorldSettings.EnumGamemode.NOT_SET.getId())
        {
            arraylist.add(new Predicate<Entity>()
            {
                public boolean a(Entity p_a_1_)
                {
                    if (!(p_a_1_ instanceof EntityPlayer))
                    {
                        return false;
                    }
                    else
                    {
                        EntityPlayer entityplayer = (EntityPlayer)p_a_1_;
                        return entityplayer.playerInteractManager.getGameMode().getId() == i;
                    }
                }
            });
        }

        return arraylist;
    }

    private static List<Predicate<Entity>> d(Map<String, String> p_d_0_)
    {
        ArrayList arraylist = Lists.newArrayList();
        final String s = b(p_d_0_, "team");
        final boolean flag = s != null && s.startsWith("!");

        if (flag)
        {
            s = s.substring(1);
        }

        if (s != null)
        {
            arraylist.add(new Predicate<Entity>()
            {
                public boolean a(Entity p_a_1_)
                {
                    if (!(p_a_1_ instanceof EntityLiving))
                    {
                        return false;
                    }
                    else
                    {
                        EntityLiving entityliving = (EntityLiving)p_a_1_;
                        ScoreboardTeamBase scoreboardteambase = entityliving.getScoreboardTeam();
                        String s1 = scoreboardteambase == null ? "" : scoreboardteambase.getName();
                        return s1.equals(s) != flag;
                    }
                }
            });
        }

        return arraylist;
    }

    private static List<Predicate<Entity>> e(Map<String, String> p_e_0_)
    {
        ArrayList arraylist = Lists.newArrayList();
        final Map map = a(p_e_0_);

        if (map != null && map.size() > 0)
        {
            arraylist.add(new Predicate<Entity>()
            {
                public boolean a(Entity p_a_1_)
                {
                    Scoreboard scoreboard = MinecraftServer.getServer().getWorldServer(0).getScoreboard();

                    for (Entry entry : map.entrySet())
                    {
                        String s = (String)entry.getKey();
                        boolean flag = false;

                        if (s.endsWith("_min") && s.length() > 4)
                        {
                            flag = true;
                            s = s.substring(0, s.length() - 4);
                        }

                        ScoreboardObjective scoreboardobjective = scoreboard.getObjective(s);

                        if (scoreboardobjective == null)
                        {
                            return false;
                        }

                        String s1 = p_a_1_ instanceof EntityPlayer ? p_a_1_.getName() : p_a_1_.getUniqueID().toString();

                        if (!scoreboard.b(s1, scoreboardobjective))
                        {
                            return false;
                        }

                        ScoreboardScore scoreboardscore = scoreboard.getPlayerScoreForObjective(s1, scoreboardobjective);
                        int i = scoreboardscore.getScore();

                        if (i < ((Integer)entry.getValue()).intValue() && flag)
                        {
                            return false;
                        }

                        if (i > ((Integer)entry.getValue()).intValue() && !flag)
                        {
                            return false;
                        }
                    }

                    return true;
                }
            });
        }

        return arraylist;
    }

    private static List<Predicate<Entity>> f(Map<String, String> p_f_0_)
    {
        ArrayList arraylist = Lists.newArrayList();
        final String s = b(p_f_0_, "name");
        final boolean flag = s != null && s.startsWith("!");

        if (flag)
        {
            s = s.substring(1);
        }

        if (s != null)
        {
            arraylist.add(new Predicate<Entity>()
            {
                public boolean a(Entity p_a_1_)
                {
                    return p_a_1_.getName().equals(s) != flag;
                }
            });
        }

        return arraylist;
    }

    private static List<Predicate<Entity>> a(Map<String, String> p_a_0_, final BlockPosition p_a_1_)
    {
        ArrayList arraylist = Lists.newArrayList();
        final int i = a(p_a_0_, "rm", -1);
        final int j = a(p_a_0_, "r", -1);

        if (p_a_1_ != null && (i >= 0 || j >= 0))
        {
            final int k = i * i;
            final int l = j * j;
            arraylist.add(new Predicate<Entity>()
            {
                public boolean a(Entity p_a_1_x)
                {
                    int i1 = (int)p_a_1_x.c(p_a_1_);
                    return (i < 0 || i1 >= k) && (j < 0 || i1 <= l);
                }
            });
        }

        return arraylist;
    }

    private static List<Predicate<Entity>> g(Map<String, String> p_g_0_)
    {
        ArrayList arraylist = Lists.newArrayList();

        if (p_g_0_.containsKey("rym") || p_g_0_.containsKey("ry"))
        {
            final int i = a(a(p_g_0_, "rym", 0));
            final int j = a(a(p_g_0_, "ry", 359));
            arraylist.add(new Predicate<Entity>()
            {
                public boolean a(Entity p_a_1_)
                {
                    int i1 = PlayerSelector.a((int)Math.floor((double)p_a_1_.yaw));
                    return i > j ? i1 >= i || i1 <= j : i1 >= i && i1 <= j;
                }
            });
        }

        if (p_g_0_.containsKey("rxm") || p_g_0_.containsKey("rx"))
        {
            final int k = a(a(p_g_0_, "rxm", 0));
            final int l = a(a(p_g_0_, "rx", 359));
            arraylist.add(new Predicate<Entity>()
            {
                public boolean a(Entity p_a_1_)
                {
                    int i1 = PlayerSelector.a((int)Math.floor((double)p_a_1_.pitch));
                    return k > l ? i1 >= k || i1 <= l : i1 >= k && i1 <= l;
                }
            });
        }

        return arraylist;
    }

    private static <T extends Entity> List<T> a(Map<String, String> p_a_0_, Class <? extends T > p_a_1_, List<Predicate<Entity>> p_a_2_, String p_a_3_, World p_a_4_, BlockPosition p_a_5_)
    {
        ArrayList arraylist = Lists.newArrayList();
        String s = b(p_a_0_, "type");
        s = s != null && s.startsWith("!") ? s.substring(1) : s;
        boolean flag = !p_a_3_.equals("e");
        boolean flag1 = p_a_3_.equals("r") && s != null;
        int i = a(p_a_0_, "dx", 0);
        int j = a(p_a_0_, "dy", 0);
        int k = a(p_a_0_, "dz", 0);
        int l = a(p_a_0_, "r", -1);
        Predicate predicate = Predicates.and(p_a_2_);
        Predicate predicate1 = Predicates.and(IEntitySelector.a, predicate);

        if (p_a_5_ != null)
        {
            int i1 = p_a_4_.players.size();
            int j1 = p_a_4_.entityList.size();
            boolean flag2 = i1 < j1 / 16;

            if (!p_a_0_.containsKey("dx") && !p_a_0_.containsKey("dy") && !p_a_0_.containsKey("dz"))
            {
                if (l >= 0)
                {
                    AxisAlignedBB axisalignedbb1 = new AxisAlignedBB((double)(p_a_5_.getX() - l), (double)(p_a_5_.getY() - l), (double)(p_a_5_.getZ() - l), (double)(p_a_5_.getX() + l + 1), (double)(p_a_5_.getY() + l + 1), (double)(p_a_5_.getZ() + l + 1));

                    if (flag && flag2 && !flag1)
                    {
                        arraylist.addAll(p_a_4_.b(p_a_1_, predicate1));
                    }
                    else
                    {
                        arraylist.addAll(p_a_4_.a(p_a_1_, axisalignedbb1, predicate1));
                    }
                }
                else if (p_a_3_.equals("a"))
                {
                    arraylist.addAll(p_a_4_.b(p_a_1_, predicate));
                }
                else if (!p_a_3_.equals("p") && (!p_a_3_.equals("r") || flag1))
                {
                    arraylist.addAll(p_a_4_.a(p_a_1_, predicate1));
                }
                else
                {
                    arraylist.addAll(p_a_4_.b(p_a_1_, predicate1));
                }
            }
            else
            {
                final AxisAlignedBB axisalignedbb = a(p_a_5_, i, j, k);

                if (flag && flag2 && !flag1)
                {
                    Predicate predicate2 = new Predicate<Entity>()
                    {
                        public boolean a(Entity p_a_1_)
                        {
                            return p_a_1_.locX >= axisalignedbb.a && p_a_1_.locY >= axisalignedbb.b && p_a_1_.locZ >= axisalignedbb.c ? p_a_1_.locX < axisalignedbb.d && p_a_1_.locY < axisalignedbb.e && p_a_1_.locZ < axisalignedbb.f : false;
                        }
                    };
                    arraylist.addAll(p_a_4_.b(p_a_1_, Predicates.and(predicate1, predicate2)));
                }
                else
                {
                    arraylist.addAll(p_a_4_.a(p_a_1_, axisalignedbb, predicate1));
                }
            }
        }
        else if (p_a_3_.equals("a"))
        {
            arraylist.addAll(p_a_4_.b(p_a_1_, predicate));
        }
        else if (!p_a_3_.equals("p") && (!p_a_3_.equals("r") || flag1))
        {
            arraylist.addAll(p_a_4_.a(p_a_1_, predicate1));
        }
        else
        {
            arraylist.addAll(p_a_4_.b(p_a_1_, predicate1));
        }

        return arraylist;
    }

    private static <T extends Entity> List<T> a(List<T> p_a_0_, Map<String, String> p_a_1_, ICommandListener p_a_2_, Class <? extends T > p_a_3_, String p_a_4_, final BlockPosition p_a_5_)
    {
        int i = a(p_a_1_, "c", !p_a_4_.equals("a") && !p_a_4_.equals("e") ? 1 : 0);

        if (!p_a_4_.equals("p") && !p_a_4_.equals("a") && !p_a_4_.equals("e"))
        {
            if (p_a_4_.equals("r"))
            {
                Collections.shuffle((List)p_a_0_);
            }
        }
        else if (p_a_5_ != null)
        {
            Collections.sort((List)p_a_0_, new Comparator<Entity>()
            {
                public int a(Entity p_a_1_, Entity p_a_2_)
                {
                    return ComparisonChain.start().compare(p_a_1_.b(p_a_5_), p_a_2_.b(p_a_5_)).result();
                }
            });
        }

        Entity entity = p_a_2_.f();

        if (entity != null && p_a_3_.isAssignableFrom(entity.getClass()) && i == 1 && ((List)p_a_0_).contains(entity) && !"r".equals(p_a_4_))
        {
            p_a_0_ = Lists.newArrayList(new Entity[] {entity});
        }

        if (i != 0)
        {
            if (i < 0)
            {
                Collections.reverse((List)p_a_0_);
            }

            p_a_0_ = ((List)p_a_0_).subList(0, Math.min(Math.abs(i), ((List)p_a_0_).size()));
        }

        return (List)p_a_0_;
    }

    private static AxisAlignedBB a(BlockPosition p_a_0_, int p_a_1_, int p_a_2_, int p_a_3_)
    {
        boolean flag = p_a_1_ < 0;
        boolean flag1 = p_a_2_ < 0;
        boolean flag2 = p_a_3_ < 0;
        int i = p_a_0_.getX() + (flag ? p_a_1_ : 0);
        int j = p_a_0_.getY() + (flag1 ? p_a_2_ : 0);
        int k = p_a_0_.getZ() + (flag2 ? p_a_3_ : 0);
        int l = p_a_0_.getX() + (flag ? 0 : p_a_1_) + 1;
        int i1 = p_a_0_.getY() + (flag1 ? 0 : p_a_2_) + 1;
        int j1 = p_a_0_.getZ() + (flag2 ? 0 : p_a_3_) + 1;
        return new AxisAlignedBB((double)i, (double)j, (double)k, (double)l, (double)i1, (double)j1);
    }

    public static int a(int p_a_0_)
    {
        p_a_0_ = p_a_0_ % 360;

        if (p_a_0_ >= 160)
        {
            p_a_0_ -= 360;
        }

        if (p_a_0_ < 0)
        {
            p_a_0_ += 360;
        }

        return p_a_0_;
    }

    private static BlockPosition b(Map<String, String> p_b_0_, BlockPosition p_b_1_)
    {
        return new BlockPosition(a(p_b_0_, "x", p_b_1_.getX()), a(p_b_0_, "y", p_b_1_.getY()), a(p_b_0_, "z", p_b_1_.getZ()));
    }

    private static boolean h(Map<String, String> p_h_0_)
    {
        for (String s : d)
        {
            if (p_h_0_.containsKey(s))
            {
                return true;
            }
        }

        return false;
    }

    private static int a(Map<String, String> p_a_0_, String p_a_1_, int p_a_2_)
    {
        return p_a_0_.containsKey(p_a_1_) ? MathHelper.a((String)p_a_0_.get(p_a_1_), p_a_2_) : p_a_2_;
    }

    private static String b(Map<String, String> p_b_0_, String p_b_1_)
    {
        return (String)p_b_0_.get(p_b_1_);
    }

    public static Map<String, Integer> a(Map<String, String> p_a_0_)
    {
        HashMap hashmap = Maps.newHashMap();

        for (String s : p_a_0_.keySet())
        {
            if (s.startsWith("score_") && s.length() > "score_".length())
            {
                hashmap.put(s.substring("score_".length()), Integer.valueOf(MathHelper.a((String)p_a_0_.get(s), 1)));
            }
        }

        return hashmap;
    }

    public static boolean isList(String p_isList_0_)
    {
        Matcher matcher = a.matcher(p_isList_0_);

        if (!matcher.matches())
        {
            return false;
        }
        else
        {
            Map map = c(matcher.group(2));
            String s = matcher.group(1);
            int i = !"a".equals(s) && !"e".equals(s) ? 1 : 0;
            return a(map, "c", i) != 1;
        }
    }

    public static boolean isPattern(String p_isPattern_0_)
    {
        return a.matcher(p_isPattern_0_).matches();
    }

    private static Map<String, String> c(String p_c_0_)
    {
        HashMap hashmap = Maps.newHashMap();

        if (p_c_0_ == null)
        {
            return hashmap;
        }
        else
        {
            int i = 0;
            int j = -1;

            for (Matcher matcher = b.matcher(p_c_0_); matcher.find(); j = matcher.end())
            {
                String s = null;

                switch (i++)
                {
                    case 0:
                        s = "x";
                        break;

                    case 1:
                        s = "y";
                        break;

                    case 2:
                        s = "z";
                        break;

                    case 3:
                        s = "r";
                }

                if (s != null && matcher.group(1).length() > 0)
                {
                    hashmap.put(s, matcher.group(1));
                }
            }

            if (j < p_c_0_.length())
            {
                Matcher matcher1 = c.matcher(j == -1 ? p_c_0_ : p_c_0_.substring(j));

                while (matcher1.find())
                {
                    hashmap.put(matcher1.group(1), matcher1.group(2));
                }
            }

            return hashmap;
        }
    }
}
