package net.minecraft.server.v1_8_R3;

public class PathfinderGoalLookAtTradingPlayer extends PathfinderGoalLookAtPlayer
{
    private final EntityVillager e;

    public PathfinderGoalLookAtTradingPlayer(EntityVillager p_i1173_1_)
    {
        super(p_i1173_1_, EntityHuman.class, 8.0F);
        this.e = p_i1173_1_;
    }

    public boolean a()
    {
        if (this.e.co())
        {
            this.b = this.e.v_();
            return true;
        }
        else
        {
            return false;
        }
    }
}
