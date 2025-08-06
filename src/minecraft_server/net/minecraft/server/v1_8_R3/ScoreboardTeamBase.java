package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;

public abstract class ScoreboardTeamBase
{
    public boolean isAlly(ScoreboardTeamBase p_isAlly_1_)
    {
        return p_isAlly_1_ == null ? false : this == p_isAlly_1_;
    }

    public abstract String getName();

    public abstract String getFormattedName(String var1);

    public abstract boolean allowFriendlyFire();

    public abstract Collection<String> getPlayerNameSet();

    public abstract ScoreboardTeamBase.EnumNameTagVisibility j();

    public static enum EnumNameTagVisibility
    {
        ALWAYS("always", 0),
        NEVER("never", 1),
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
        HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

        private static Map<String, ScoreboardTeamBase.EnumNameTagVisibility> g = Maps.<String, ScoreboardTeamBase.EnumNameTagVisibility>newHashMap();
        public final String e;
        public final int f;

        public static String[] a()
        {
            return (String[])g.keySet().toArray(new String[g.size()]);
        }

        public static ScoreboardTeamBase.EnumNameTagVisibility a(String p_a_0_)
        {
            return (ScoreboardTeamBase.EnumNameTagVisibility)g.get(p_a_0_);
        }

        private EnumNameTagVisibility(String p_i857_3_, int p_i857_4_)
        {
            this.e = p_i857_3_;
            this.f = p_i857_4_;
        }

        static {
            for (ScoreboardTeamBase.EnumNameTagVisibility scoreboardteambase$enumnametagvisibility : values())
            {
                g.put(scoreboardteambase$enumnametagvisibility.e, scoreboardteambase$enumnametagvisibility);
            }
        }
    }
}
