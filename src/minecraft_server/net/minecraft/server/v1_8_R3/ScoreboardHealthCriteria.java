package net.minecraft.server.v1_8_R3;

import java.util.List;

public class ScoreboardHealthCriteria extends ScoreboardBaseCriteria
{
    public ScoreboardHealthCriteria(String p_i860_1_)
    {
        super(p_i860_1_);
    }

    public int getScoreModifier(List<EntityHuman> p_getScoreModifier_1_)
    {
        float f = 0.0F;

        for (EntityHuman entityhuman : p_getScoreModifier_1_)
        {
            f += entityhuman.getHealth() + entityhuman.getAbsorptionHearts();
        }

        if (p_getScoreModifier_1_.size() > 0)
        {
            f /= (float)p_getScoreModifier_1_.size();
        }

        return MathHelper.f(f);
    }

    public boolean isReadOnly()
    {
        return true;
    }

    public IScoreboardCriteria.EnumScoreboardHealthDisplay c()
    {
        return IScoreboardCriteria.EnumScoreboardHealthDisplay.HEARTS;
    }
}
