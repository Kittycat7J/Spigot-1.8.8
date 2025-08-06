package net.minecraft.server.v1_8_R3;

public class PathfinderGoalArrowAttack extends PathfinderGoal
{
    private final EntityInsentient a;
    private final IRangedEntity b;
    private EntityLiving c;
    private int d;
    private double e;
    private int f;
    private int g;
    private int h;
    private float i;
    private float j;

    public PathfinderGoalArrowAttack(IRangedEntity p_i1189_1_, double p_i1189_2_, int p_i1189_4_, float p_i1189_5_)
    {
        this(p_i1189_1_, p_i1189_2_, p_i1189_4_, p_i1189_4_, p_i1189_5_);
    }

    public PathfinderGoalArrowAttack(IRangedEntity p_i1190_1_, double p_i1190_2_, int p_i1190_4_, int p_i1190_5_, float p_i1190_6_)
    {
        this.d = -1;

        if (!(p_i1190_1_ instanceof EntityLiving))
        {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else
        {
            this.b = p_i1190_1_;
            this.a = (EntityInsentient)p_i1190_1_;
            this.e = p_i1190_2_;
            this.g = p_i1190_4_;
            this.h = p_i1190_5_;
            this.i = p_i1190_6_;
            this.j = p_i1190_6_ * p_i1190_6_;
            this.a(3);
        }
    }

    public boolean a()
    {
        EntityLiving entityliving = this.a.getGoalTarget();

        if (entityliving == null)
        {
            return false;
        }
        else
        {
            this.c = entityliving;
            return true;
        }
    }

    public boolean b()
    {
        return this.a() || !this.a.getNavigation().m();
    }

    public void d()
    {
        this.c = null;
        this.f = 0;
        this.d = -1;
    }

    public void e()
    {
        double d0 = this.a.e(this.c.locX, this.c.getBoundingBox().b, this.c.locZ);
        boolean flag = this.a.getEntitySenses().a(this.c);

        if (flag)
        {
            ++this.f;
        }
        else
        {
            this.f = 0;
        }

        if (d0 <= (double)this.j && this.f >= 20)
        {
            this.a.getNavigation().n();
        }
        else
        {
            this.a.getNavigation().a((Entity)this.c, this.e);
        }

        this.a.getControllerLook().a(this.c, 30.0F, 30.0F);

        if (--this.d == 0)
        {
            if (d0 > (double)this.j || !flag)
            {
                return;
            }

            float fx = MathHelper.sqrt(d0) / this.i;
            float f1 = MathHelper.a(fx, 0.1F, 1.0F);
            this.b.a(this.c, f1);
            this.d = MathHelper.d(fx * (float)(this.h - this.g) + (float)this.g);
        }
        else if (this.d < 0)
        {
            float f2 = MathHelper.sqrt(d0) / this.i;
            this.d = MathHelper.d(f2 * (float)(this.h - this.g) + (float)this.g);
        }
    }
}
