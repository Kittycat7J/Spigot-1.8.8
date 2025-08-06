package net.minecraft.server.v1_8_R3;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Statistic
{
    public final String name;
    private final IChatBaseComponent a;
    public boolean f;
    private final Counter b;
    private final IScoreboardCriteria c;
    private Class <? extends IJsonStatistic > d;
    private static NumberFormat k = NumberFormat.getIntegerInstance(Locale.US);
    public static Counter g = new Counter()
    {
    };
    private static DecimalFormat l = new DecimalFormat("########0.00");
    public static Counter h = new Counter()
    {
    };
    public static Counter i = new Counter()
    {
    };
    public static Counter j = new Counter()
    {
    };

    public Statistic(String p_i1103_1_, IChatBaseComponent p_i1103_2_, Counter p_i1103_3_)
    {
        this.name = p_i1103_1_;
        this.a = p_i1103_2_;
        this.b = p_i1103_3_;
        this.c = new ScoreboardStatisticCriteria(this);
        IScoreboardCriteria.criteria.put(this.c.getName(), this.c);
    }

    public Statistic(String p_i1104_1_, IChatBaseComponent p_i1104_2_)
    {
        this(p_i1104_1_, p_i1104_2_, g);
    }

    public Statistic i()
    {
        this.f = true;
        return this;
    }

    public Statistic h()
    {
        if (StatisticList.a.containsKey(this.name))
        {
            throw new RuntimeException("Duplicate stat id: \"" + ((Statistic)StatisticList.a.get(this.name)).a + "\" and \"" + this.a + "\" at id " + this.name);
        }
        else
        {
            StatisticList.stats.add(this);
            StatisticList.a.put(this.name, this);
            return this;
        }
    }

    public boolean d()
    {
        return false;
    }

    public IChatBaseComponent e()
    {
        IChatBaseComponent ichatbasecomponent = this.a.f();
        ichatbasecomponent.getChatModifier().setColor(EnumChatFormat.GRAY);
        ichatbasecomponent.getChatModifier().setChatHoverable(new ChatHoverable(ChatHoverable.EnumHoverAction.SHOW_ACHIEVEMENT, new ChatComponentText(this.name)));
        return ichatbasecomponent;
    }

    public IChatBaseComponent j()
    {
        IChatBaseComponent ichatbasecomponent = this.e();
        IChatBaseComponent ichatbasecomponent1 = (new ChatComponentText("[")).addSibling(ichatbasecomponent).a("]");
        ichatbasecomponent1.setChatModifier(ichatbasecomponent.getChatModifier());
        return ichatbasecomponent1;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass())
        {
            Statistic statistic = (Statistic)p_equals_1_;
            return this.name.equals(statistic.name);
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return this.name.hashCode();
    }

    public String toString()
    {
        return "Stat{id=" + this.name + ", nameId=" + this.a + ", awardLocallyOnly=" + this.f + ", formatter=" + this.b + ", objectiveCriteria=" + this.c + '}';
    }

    public IScoreboardCriteria k()
    {
        return this.c;
    }

    public Class <? extends IJsonStatistic > l()
    {
        return this.d;
    }

    public Statistic b(Class <? extends IJsonStatistic > p_b_1_)
    {
        this.d = p_b_1_;
        return this;
    }
}
