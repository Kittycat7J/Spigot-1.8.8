package net.minecraft.server.v1_8_R3;

public class StatisticWrapper
{
    private int a;
    private IJsonStatistic b;

    public int a()
    {
        return this.a;
    }

    public void a(int p_a_1_)
    {
        this.a = p_a_1_;
    }

    public <T extends IJsonStatistic> T b()
    {
        return (T)this.b;
    }

    public void a(IJsonStatistic p_a_1_)
    {
        this.b = p_a_1_;
    }
}
