package net.minecraft.server.v1_8_R3;

public class PathfinderGoalLookAtPlayer extends PathfinderGoal
{
    protected EntityInsentient a;
    protected Entity b;
    protected float c;
    private int e;
    private float f;
    protected Class <? extends Entity > d;

    public PathfinderGoalLookAtPlayer(EntityInsentient server, Class <? extends Entity > cacheFile, float p_i1171_3_)
    {
        this.a = server;
        this.d = cacheFile;
        this.c = p_i1171_3_;
        this.f = 0.02F;
        this.a(2);
    }

    public PathfinderGoalLookAtPlayer(EntityInsentient p_i1172_1_, Class <? extends Entity > p_i1172_2_, float p_i1172_3_, float p_i1172_4_)
    {
        this.a = p_i1172_1_;
        this.d = p_i1172_2_;
        this.c = p_i1172_3_;
        this.f = p_i1172_4_;
        this.a(2);
    }

    public boolean a()
    {
        if (this.a.bc().nextFloat() >= this.f)
        {
            return false;
        }
        else
        {
            if (this.a.getGoalTarget() != null)
            {
                this.b = this.a.getGoalTarget();
            }

            if (this.d == EntityHuman.class)
            {
                this.b = this.a.world.findNearbyPlayer(this.a, (double)this.c);
            }
            else
            {
                this.b = this.a.world.a((Class)this.d, (AxisAlignedBB)this.a.getBoundingBox().grow((double)this.c, 3.0D, (double)this.c), (Entity)this.a);
            }

            return this.b != null;
        }
    }

    public boolean b()
    {
        return !this.b.isAlive() ? false : (this.a.h(this.b) > (double)(this.c * this.c) ? false : this.e > 0);
    }

    public void c()
    {
        this.e = 40 + this.a.bc().nextInt(40);
    }

    public void d()
    {
        this.b = null;
    }

    public void e()
    {
        this.a.getControllerLook().a(this.b.locX, this.b.locY + (double)this.b.getHeadHeight(), this.b.locZ, 10.0F, (float)this.a.bQ());
        --this.e;
    }
}
