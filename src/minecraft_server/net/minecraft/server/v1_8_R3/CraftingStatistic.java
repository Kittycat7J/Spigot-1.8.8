package net.minecraft.server.v1_8_R3;

public class CraftingStatistic extends Statistic
{
    private final Item a;

    public CraftingStatistic(String p_i1102_1_, String p_i1102_2_, IChatBaseComponent p_i1102_3_, Item p_i1102_4_)
    {
        super(p_i1102_1_ + p_i1102_2_, p_i1102_3_);
        this.a = p_i1102_4_;
        int i = Item.getId(p_i1102_4_);

        if (i != 0)
        {
            IScoreboardCriteria.criteria.put(p_i1102_1_ + i, this.k());
        }
    }
}
