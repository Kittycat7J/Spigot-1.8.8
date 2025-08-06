package net.minecraft.server.v1_8_R3;

public class Achievement extends Statistic
{
    public final int a;
    public final int b;
    public final Achievement c;
    private final String k;
    public final ItemStack d;
    private boolean m;

    public Achievement(String p_i1097_1_, String p_i1097_2_, int p_i1097_3_, int p_i1097_4_, Item p_i1097_5_, Achievement p_i1097_6_)
    {
        this(p_i1097_1_, p_i1097_2_, p_i1097_3_, p_i1097_4_, new ItemStack(p_i1097_5_), p_i1097_6_);
    }

    public Achievement(String p_i1098_1_, String p_i1098_2_, int p_i1098_3_, int p_i1098_4_, Block p_i1098_5_, Achievement p_i1098_6_)
    {
        this(p_i1098_1_, p_i1098_2_, p_i1098_3_, p_i1098_4_, new ItemStack(p_i1098_5_), p_i1098_6_);
    }

    public Achievement(String p_i1099_1_, String p_i1099_2_, int p_i1099_3_, int p_i1099_4_, ItemStack p_i1099_5_, Achievement p_i1099_6_)
    {
        super(p_i1099_1_, new ChatMessage("achievement." + p_i1099_2_, new Object[0]));
        this.d = p_i1099_5_;
        this.k = "achievement." + p_i1099_2_ + ".desc";
        this.a = p_i1099_3_;
        this.b = p_i1099_4_;

        if (p_i1099_3_ < AchievementList.a)
        {
            a = p_i1099_3_;
        }

        if (p_i1099_4_ < AchievementList.b)
        {
            b = p_i1099_4_;
        }

        if (p_i1099_3_ > AchievementList.c)
        {
            AchievementList.c = p_i1099_3_;
        }

        if (p_i1099_4_ > AchievementList.d)
        {
            AchievementList.d = p_i1099_4_;
        }

        this.c = p_i1099_6_;
    }

    public Achievement a()
    {
        this.f = true;
        return this;
    }

    public Achievement b()
    {
        this.m = true;
        return this;
    }

    public Achievement c()
    {
        super.h();
        AchievementList.e.add(this);
        return this;
    }

    public boolean d()
    {
        return true;
    }

    public IChatBaseComponent e()
    {
        IChatBaseComponent ichatbasecomponent = super.e();
        ichatbasecomponent.getChatModifier().setColor(this.g() ? EnumChatFormat.DARK_PURPLE : EnumChatFormat.GREEN);
        return ichatbasecomponent;
    }

    public Achievement a(Class <? extends IJsonStatistic > p_a_1_)
    {
        return (Achievement)super.b(p_a_1_);
    }

    public boolean g()
    {
        return this.m;
    }
}
