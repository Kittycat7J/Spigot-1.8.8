package net.minecraft.server.v1_8_R3;

public class ControllerMove
{
    protected EntityInsentient a;
    protected double b;
    protected double c;
    protected double d;
    protected double e;
    protected boolean f;

    public ControllerMove(EntityInsentient p_i1157_1_)
    {
        this.a = p_i1157_1_;
        this.b = p_i1157_1_.locX;
        this.c = p_i1157_1_.locY;
        this.d = p_i1157_1_.locZ;
    }

    public boolean a()
    {
        return this.f;
    }

    public double b()
    {
        return this.e;
    }

    public void a(double p_a_1_, double p_a_3_, double p_a_5_, double p_a_7_)
    {
        this.b = p_a_1_;
        this.c = p_a_3_;
        this.d = p_a_5_;
        this.e = p_a_7_;
        this.f = true;
    }

    public void c()
    {
        this.a.n(0.0F);

        if (this.f)
        {
            this.f = false;
            int i = MathHelper.floor(this.a.getBoundingBox().b + 0.5D);
            double d0 = this.b - this.a.locX;
            double d1 = this.d - this.a.locZ;
            double d2 = this.c - (double)i;
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

            if (d3 >= 2.500000277905201E-7D)
            {
                float fx = (float)(MathHelper.b(d1, d0) * 180.0D / Math.PI) - 90.0F;
                this.a.yaw = this.a(this.a.yaw, fx, 30.0F);
                this.a.k((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));

                if (d2 > 0.0D && d0 * d0 + d1 * d1 < 1.0D)
                {
                    this.a.getControllerJump().a();
                }
            }
        }
    }

    protected float a(float p_a_1_, float p_a_2_, float p_a_3_)
    {
        float fx = MathHelper.g(p_a_2_ - p_a_1_);

        if (fx > p_a_3_)
        {
            fx = p_a_3_;
        }

        if (fx < -p_a_3_)
        {
            fx = -p_a_3_;
        }

        float f1 = p_a_1_ + fx;

        if (f1 < 0.0F)
        {
            f1 += 360.0F;
        }
        else if (f1 > 360.0F)
        {
            f1 -= 360.0F;
        }

        return f1;
    }

    public double d()
    {
        return this.b;
    }

    public double e()
    {
        return this.c;
    }

    public double f()
    {
        return this.d;
    }
}
