package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spigotmc.SpigotConfig;

public class ServerStatisticManager extends StatisticManager
{
    private static final Logger b = LogManager.getLogger();
    private final MinecraftServer c;
    private final File d;
    private final Set<Statistic> e = Sets.<Statistic>newHashSet();
    private int f = -300;
    private boolean g = false;

    public ServerStatisticManager(MinecraftServer p_i329_1_, File p_i329_2_)
    {
        this.c = p_i329_1_;
        this.d = p_i329_2_;

        for (String s : SpigotConfig.forcedStats.keySet())
        {
            StatisticWrapper statisticwrapper = new StatisticWrapper();
            statisticwrapper.a(SpigotConfig.forcedStats.get(s));
            this.a.put(StatisticList.getStatistic(s), statisticwrapper);
        }
    }

    public void a()
    {
        if (this.d.isFile())
        {
            try
            {
                this.a.clear();
                this.a.putAll(this.a(FileUtils.readFileToString(this.d)));
            }
            catch (IOException ioexception)
            {
                b.error((String)("Couldn\'t read statistics file " + this.d), (Throwable)ioexception);
            }
            catch (JsonParseException jsonparseexception)
            {
                b.error((String)("Couldn\'t parse statistics file " + this.d), (Throwable)jsonparseexception);
            }
        }
    }

    public void b()
    {
        if (!SpigotConfig.disableStatSaving)
        {
            try
            {
                FileUtils.writeStringToFile(this.d, a(this.a));
            }
            catch (IOException ioexception)
            {
                b.error((String)"Couldn\'t save stats", (Throwable)ioexception);
            }
        }
    }

    public void setStatistic(EntityHuman p_setStatistic_1_, Statistic p_setStatistic_2_, int p_setStatistic_3_)
    {
        if (!SpigotConfig.disableStatSaving)
        {
            int i = p_setStatistic_2_.d() ? this.getStatisticValue(p_setStatistic_2_) : 0;
            super.setStatistic(p_setStatistic_1_, p_setStatistic_2_, p_setStatistic_3_);
            this.e.add(p_setStatistic_2_);

            if (p_setStatistic_2_.d() && i == 0 && p_setStatistic_3_ > 0)
            {
                this.g = true;

                if (this.c.aB())
                {
                    this.c.getPlayerList().sendMessage((IChatBaseComponent)(new ChatMessage("chat.type.achievement", new Object[] {p_setStatistic_1_.getScoreboardDisplayName(), p_setStatistic_2_.j()})));
                }
            }

            if (p_setStatistic_2_.d() && i > 0 && p_setStatistic_3_ == 0)
            {
                this.g = true;

                if (this.c.aB())
                {
                    this.c.getPlayerList().sendMessage((IChatBaseComponent)(new ChatMessage("chat.type.achievement.taken", new Object[] {p_setStatistic_1_.getScoreboardDisplayName(), p_setStatistic_2_.j()})));
                }
            }
        }
    }

    public Set<Statistic> c()
    {
        HashSet hashset = Sets.newHashSet(this.e);
        this.e.clear();
        this.g = false;
        return hashset;
    }

    public Map<Statistic, StatisticWrapper> a(String p_a_1_)
    {
        JsonElement jsonelement = (new JsonParser()).parse(p_a_1_);

        if (!jsonelement.isJsonObject())
        {
            return Maps.<Statistic, StatisticWrapper>newHashMap();
        }
        else
        {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            HashMap hashmap = Maps.newHashMap();

            for (Entry entry : jsonobject.entrySet())
            {
                Statistic statistic = StatisticList.getStatistic((String)entry.getKey());

                if (statistic != null)
                {
                    StatisticWrapper statisticwrapper = new StatisticWrapper();

                    if (((JsonElement)entry.getValue()).isJsonPrimitive() && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber())
                    {
                        statisticwrapper.a(((JsonElement)entry.getValue()).getAsInt());
                    }
                    else if (((JsonElement)entry.getValue()).isJsonObject())
                    {
                        JsonObject jsonobject1 = ((JsonElement)entry.getValue()).getAsJsonObject();

                        if (jsonobject1.has("value") && jsonobject1.get("value").isJsonPrimitive() && jsonobject1.get("value").getAsJsonPrimitive().isNumber())
                        {
                            statisticwrapper.a(jsonobject1.getAsJsonPrimitive("value").getAsInt());
                        }

                        if (jsonobject1.has("progress") && statistic.l() != null)
                        {
                            try
                            {
                                Constructor constructor = statistic.l().getConstructor(new Class[0]);
                                IJsonStatistic ijsonstatistic = (IJsonStatistic)constructor.newInstance(new Object[0]);
                                ijsonstatistic.a(jsonobject1.get("progress"));
                                statisticwrapper.a(ijsonstatistic);
                            }
                            catch (Throwable throwable)
                            {
                                b.warn("Invalid statistic progress in " + this.d, throwable);
                            }
                        }
                    }

                    hashmap.put(statistic, statisticwrapper);
                }
                else
                {
                    b.warn("Invalid statistic in " + this.d + ": Don\'t know what " + (String)entry.getKey() + " is");
                }
            }

            return hashmap;
        }
    }

    public static String a(Map<Statistic, StatisticWrapper> p_a_0_)
    {
        JsonObject jsonobject = new JsonObject();

        for (Entry entry : p_a_0_.entrySet())
        {
            if (((StatisticWrapper)entry.getValue()).b() != null)
            {
                JsonObject jsonobject1 = new JsonObject();
                jsonobject1.addProperty("value", (Number)Integer.valueOf(((StatisticWrapper)entry.getValue()).a()));

                try
                {
                    jsonobject1.add("progress", ((StatisticWrapper)entry.getValue()).b().a());
                }
                catch (Throwable throwable)
                {
                    b.warn("Couldn\'t save statistic " + ((Statistic)entry.getKey()).e() + ": error serializing progress", throwable);
                }

                jsonobject.add(((Statistic)entry.getKey()).name, jsonobject1);
            }
            else
            {
                jsonobject.addProperty(((Statistic)entry.getKey()).name, (Number)Integer.valueOf(((StatisticWrapper)entry.getValue()).a()));
            }
        }

        return jsonobject.toString();
    }

    public void d()
    {
        for (Statistic statistic : this.a.keySet())
        {
            this.e.add(statistic);
        }
    }

    public void a(EntityPlayer p_a_1_)
    {
        int i = this.c.at();
        HashMap hashmap = Maps.newHashMap();

        if (this.g || i - this.f > 300)
        {
            this.f = i;

            for (Statistic statistic : this.c())
            {
                hashmap.put(statistic, Integer.valueOf(this.getStatisticValue(statistic)));
            }
        }

        p_a_1_.playerConnection.sendPacket(new PacketPlayOutStatistic(hashmap));
    }

    public void updateStatistics(EntityPlayer p_updateStatistics_1_)
    {
        HashMap hashmap = Maps.newHashMap();

        for (Achievement achievement : AchievementList.e)
        {
            if (this.hasAchievement(achievement))
            {
                hashmap.put(achievement, Integer.valueOf(this.getStatisticValue(achievement)));
                this.e.remove(achievement);
            }
        }

        p_updateStatistics_1_.playerConnection.sendPacket(new PacketPlayOutStatistic(hashmap));
    }

    public boolean e()
    {
        return this.g;
    }
}
