package net.minecraft.server.v1_8_R3;

import org.apache.commons.lang3.StringUtils;

public abstract class PathfinderGoalTarget extends PathfinderGoal
{
    protected final EntityCreature e;
    protected boolean f;
    private boolean a;
    private int b;
    private int c;
    private int d;

    public PathfinderGoalTarget(EntityCreature p_i1198_1_, boolean p_i1198_2_)
    {
        this(p_i1198_1_, p_i1198_2_, false);
    }

    public PathfinderGoalTarget(EntityCreature p_i1199_1_, boolean p_i1199_2_, boolean p_i1199_3_)
    {
        this.e = p_i1199_1_;
        this.f = p_i1199_2_;
        this.a = p_i1199_3_;
    }

    public boolean b()
    {
        EntityLiving entityliving = this.e.getGoalTarget();

        if (entityliving == null)
        {
            return false;
        }
        else if (!entityliving.isAlive())
        {
            return false;
        }
        else
        {
            ScoreboardTeamBase scoreboardteambase = this.e.getScoreboardTeam();
            ScoreboardTeamBase scoreboardteambase1 = entityliving.getScoreboardTeam();

            if (scoreboardteambase != null && scoreboardteambase1 == scoreboardteambase)
            {
                return false;
            }
            else
            {
                double d0 = this.f();

                if (this.e.h(entityliving) > d0 * d0)
                {
                    return false;
                }
                else
                {
                    if (this.f)
                    {
                        if (this.e.getEntitySenses().a(entityliving))
                        {
                            this.d = 0;
                        }
                        else if (++this.d > 60)
                        {
                            return false;
                        }
                    }

                    return !(entityliving instanceof EntityHuman) || !((EntityHuman)entityliving).abilities.isInvulnerable;
                }
            }
        }
    }

    protected double f()
    {
        AttributeInstance attributeinstance = this.e.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);
        return attributeinstance == null ? 16.0D : attributeinstance.getValue();
    }

    public void c()
    {
        this.b = 0;
        this.c = 0;
        this.d = 0;
    }

    public void d()
    {
        this.e.setGoalTarget((EntityLiving)null);
    }

    public static boolean a(EntityInsentient p_a_0_, EntityLiving p_a_1_, boolean p_a_2_, boolean p_a_3_)
    {
        if (p_a_1_ == null)
        {
            return false;
        }
        else if (p_a_1_ == p_a_0_)
        {
            return false;
        }
        else if (!p_a_1_.isAlive())
        {
            return false;
        }
        else if (!p_a_0_.a(p_a_1_.getClass()))
        {
            return false;
        }
        else
        {
            ScoreboardTeamBase scoreboardteambase = p_a_0_.getScoreboardTeam();
            ScoreboardTeamBase scoreboardteambase1 = p_a_1_.getScoreboardTeam();

            if (scoreboardteambase != null && scoreboardteambase1 == scoreboardteambase)
            {
                return false;
            }
            else
            {
                if (p_a_0_ instanceof EntityOwnable && StringUtils.isNotEmpty(((EntityOwnable)p_a_0_).getOwnerUUID()))
                {
                    if (p_a_1_ instanceof EntityOwnable && ((EntityOwnable)p_a_0_).getOwnerUUID().equals(((EntityOwnable)p_a_1_).getOwnerUUID()))
                    {
                        return false;
                    }

                    if (p_a_1_ == ((EntityOwnable)p_a_0_).getOwner())
                    {
                        return false;
                    }
                }
                else if (p_a_1_ instanceof EntityHuman && !p_a_2_ && ((EntityHuman)p_a_1_).abilities.isInvulnerable)
                {
                    return false;
                }

                return !p_a_3_ || p_a_0_.getEntitySenses().a(p_a_1_);
            }
        }
    }

    protected boolean a(EntityLiving p_a_1_, boolean p_a_2_)
    {
        if (!a(this.e, p_a_1_, p_a_2_, this.f))
        {
            return false;
        }
        else if (!this.e.e(new BlockPosition(p_a_1_)))
        {
            return false;
        }
        else
        {
            if (this.a)
            {
                if (--this.c <= 0)
                {
                    this.b = 0;
                }

                if (this.b == 0)
                {
                    this.b = this.a(p_a_1_) ? 1 : 2;
                }

                if (this.b == 2)
                {
                    return false;
                }
            }

            return true;
        }
    }

    private boolean a(EntityLiving p_a_1_)
    {
        this.c = 10 + this.e.bc().nextInt(5);
        PathEntity pathentity = this.e.getNavigation().a((Entity)p_a_1_);

        if (pathentity == null)
        {
            return false;
        }
        else
        {
            PathPoint pathpoint = pathentity.c();

            if (pathpoint == null)
            {
                return false;
            }
            else
            {
                int i = pathpoint.a - MathHelper.floor(p_a_1_.locX);
                int j = pathpoint.c - MathHelper.floor(p_a_1_.locZ);
                return (double)(i * i + j * j) <= 2.25D;
            }
        }
    }
}
