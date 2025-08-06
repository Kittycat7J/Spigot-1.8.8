package net.minecraft.server.v1_8_R3;

public class CounterStatistic extends Statistic
{
    public CounterStatistic(String p_i1100_1_, IChatBaseComponent p_i1100_2_, Counter p_i1100_3_)
    {
        super(p_i1100_1_, p_i1100_2_, p_i1100_3_);
    }

    public CounterStatistic(String p_i1101_1_, IChatBaseComponent p_i1101_2_)
    {
        super(p_i1101_1_, p_i1101_2_);
    }

    public Statistic h()
    {
        super.h();
        StatisticList.c.add(this);
        return this;
    }
}
