package net.minecraft.server.v1_8_R3;

public class AxisAlignedBB
{
    public final double a;
    public final double b;
    public final double c;
    public final double d;
    public final double e;
    public final double f;

    public AxisAlignedBB(double p_i843_1_, double p_i843_3_, double p_i843_5_, double p_i843_7_, double p_i843_9_, double p_i843_11_)
    {
        this.a = Math.min(p_i843_1_, p_i843_7_);
        this.b = Math.min(p_i843_3_, p_i843_9_);
        this.c = Math.min(p_i843_5_, p_i843_11_);
        this.d = Math.max(p_i843_1_, p_i843_7_);
        this.e = Math.max(p_i843_3_, p_i843_9_);
        this.f = Math.max(p_i843_5_, p_i843_11_);
    }

    public AxisAlignedBB(BlockPosition p_i844_1_, BlockPosition p_i844_2_)
    {
        this.a = (double)p_i844_1_.getX();
        this.b = (double)p_i844_1_.getY();
        this.c = (double)p_i844_1_.getZ();
        this.d = (double)p_i844_2_.getX();
        this.e = (double)p_i844_2_.getY();
        this.f = (double)p_i844_2_.getZ();
    }

    public AxisAlignedBB a(double p_a_1_, double p_a_3_, double p_a_5_)
    {
        double d0 = this.a;
        double d1 = this.b;
        double d2 = this.c;
        double d3 = this.d;
        double d4 = this.e;
        double d5 = this.f;

        if (p_a_1_ < 0.0D)
        {
            d0 += p_a_1_;
        }
        else if (p_a_1_ > 0.0D)
        {
            d3 += p_a_1_;
        }

        if (p_a_3_ < 0.0D)
        {
            d1 += p_a_3_;
        }
        else if (p_a_3_ > 0.0D)
        {
            d4 += p_a_3_;
        }

        if (p_a_5_ < 0.0D)
        {
            d2 += p_a_5_;
        }
        else if (p_a_5_ > 0.0D)
        {
            d5 += p_a_5_;
        }

        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public AxisAlignedBB grow(double p_grow_1_, double p_grow_3_, double p_grow_5_)
    {
        double d0 = this.a - p_grow_1_;
        double d1 = this.b - p_grow_3_;
        double d2 = this.c - p_grow_5_;
        double d3 = this.d + p_grow_1_;
        double d4 = this.e + p_grow_3_;
        double d5 = this.f + p_grow_5_;
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public AxisAlignedBB a(AxisAlignedBB p_a_1_)
    {
        double d0 = Math.min(this.a, p_a_1_.a);
        double d1 = Math.min(this.b, p_a_1_.b);
        double d2 = Math.min(this.c, p_a_1_.c);
        double d3 = Math.max(this.d, p_a_1_.d);
        double d4 = Math.max(this.e, p_a_1_.e);
        double d5 = Math.max(this.f, p_a_1_.f);
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public static AxisAlignedBB a(double p_a_0_, double p_a_2_, double p_a_4_, double p_a_6_, double p_a_8_, double p_a_10_)
    {
        double d0 = Math.min(p_a_0_, p_a_6_);
        double d1 = Math.min(p_a_2_, p_a_8_);
        double d2 = Math.min(p_a_4_, p_a_10_);
        double d3 = Math.max(p_a_0_, p_a_6_);
        double d4 = Math.max(p_a_2_, p_a_8_);
        double d5 = Math.max(p_a_4_, p_a_10_);
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public AxisAlignedBB c(double p_c_1_, double p_c_3_, double p_c_5_)
    {
        return new AxisAlignedBB(this.a + p_c_1_, this.b + p_c_3_, this.c + p_c_5_, this.d + p_c_1_, this.e + p_c_3_, this.f + p_c_5_);
    }

    public double a(AxisAlignedBB p_a_1_, double p_a_2_)
    {
        if (p_a_1_.e > this.b && p_a_1_.b < this.e && p_a_1_.f > this.c && p_a_1_.c < this.f)
        {
            if (p_a_2_ > 0.0D && p_a_1_.d <= this.a)
            {
                double d1 = this.a - p_a_1_.d;

                if (d1 < p_a_2_)
                {
                    p_a_2_ = d1;
                }
            }
            else if (p_a_2_ < 0.0D && p_a_1_.a >= this.d)
            {
                double d0 = this.d - p_a_1_.a;

                if (d0 > p_a_2_)
                {
                    p_a_2_ = d0;
                }
            }

            return p_a_2_;
        }
        else
        {
            return p_a_2_;
        }
    }

    public double b(AxisAlignedBB p_b_1_, double p_b_2_)
    {
        if (p_b_1_.d > this.a && p_b_1_.a < this.d && p_b_1_.f > this.c && p_b_1_.c < this.f)
        {
            if (p_b_2_ > 0.0D && p_b_1_.e <= this.b)
            {
                double d1 = this.b - p_b_1_.e;

                if (d1 < p_b_2_)
                {
                    p_b_2_ = d1;
                }
            }
            else if (p_b_2_ < 0.0D && p_b_1_.b >= this.e)
            {
                double d0 = this.e - p_b_1_.b;

                if (d0 > p_b_2_)
                {
                    p_b_2_ = d0;
                }
            }

            return p_b_2_;
        }
        else
        {
            return p_b_2_;
        }
    }

    public double c(AxisAlignedBB p_c_1_, double p_c_2_)
    {
        if (p_c_1_.d > this.a && p_c_1_.a < this.d && p_c_1_.e > this.b && p_c_1_.b < this.e)
        {
            if (p_c_2_ > 0.0D && p_c_1_.f <= this.c)
            {
                double d1 = this.c - p_c_1_.f;

                if (d1 < p_c_2_)
                {
                    p_c_2_ = d1;
                }
            }
            else if (p_c_2_ < 0.0D && p_c_1_.c >= this.f)
            {
                double d0 = this.f - p_c_1_.c;

                if (d0 > p_c_2_)
                {
                    p_c_2_ = d0;
                }
            }

            return p_c_2_;
        }
        else
        {
            return p_c_2_;
        }
    }

    public boolean b(AxisAlignedBB p_b_1_)
    {
        return p_b_1_.d > this.a && p_b_1_.a < this.d ? (p_b_1_.e > this.b && p_b_1_.b < this.e ? p_b_1_.f > this.c && p_b_1_.c < this.f : false) : false;
    }

    public boolean a(Vec3D p_a_1_)
    {
        return p_a_1_.a > this.a && p_a_1_.a < this.d ? (p_a_1_.b > this.b && p_a_1_.b < this.e ? p_a_1_.c > this.c && p_a_1_.c < this.f : false) : false;
    }

    public double a()
    {
        double d0 = this.d - this.a;
        double d1 = this.e - this.b;
        double d2 = this.f - this.c;
        return (d0 + d1 + d2) / 3.0D;
    }

    public AxisAlignedBB shrink(double p_shrink_1_, double p_shrink_3_, double p_shrink_5_)
    {
        double d0 = this.a + p_shrink_1_;
        double d1 = this.b + p_shrink_3_;
        double d2 = this.c + p_shrink_5_;
        double d3 = this.d - p_shrink_1_;
        double d4 = this.e - p_shrink_3_;
        double d5 = this.f - p_shrink_5_;
        return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
    }

    public MovingObjectPosition a(Vec3D p_a_1_, Vec3D p_a_2_)
    {
        Vec3D vec3d = p_a_1_.a(p_a_2_, this.a);
        Vec3D vec3d1 = p_a_1_.a(p_a_2_, this.d);
        Vec3D vec3d2 = p_a_1_.b(p_a_2_, this.b);
        Vec3D vec3d3 = p_a_1_.b(p_a_2_, this.e);
        Vec3D vec3d4 = p_a_1_.c(p_a_2_, this.c);
        Vec3D vec3d5 = p_a_1_.c(p_a_2_, this.f);

        if (!this.b(vec3d))
        {
            vec3d = null;
        }

        if (!this.b(vec3d1))
        {
            vec3d1 = null;
        }

        if (!this.c(vec3d2))
        {
            vec3d2 = null;
        }

        if (!this.c(vec3d3))
        {
            vec3d3 = null;
        }

        if (!this.d(vec3d4))
        {
            vec3d4 = null;
        }

        if (!this.d(vec3d5))
        {
            vec3d5 = null;
        }

        Vec3D vec3d6 = null;

        if (vec3d != null)
        {
            vec3d6 = vec3d;
        }

        if (vec3d1 != null && (vec3d6 == null || p_a_1_.distanceSquared(vec3d1) < p_a_1_.distanceSquared(vec3d6)))
        {
            vec3d6 = vec3d1;
        }

        if (vec3d2 != null && (vec3d6 == null || p_a_1_.distanceSquared(vec3d2) < p_a_1_.distanceSquared(vec3d6)))
        {
            vec3d6 = vec3d2;
        }

        if (vec3d3 != null && (vec3d6 == null || p_a_1_.distanceSquared(vec3d3) < p_a_1_.distanceSquared(vec3d6)))
        {
            vec3d6 = vec3d3;
        }

        if (vec3d4 != null && (vec3d6 == null || p_a_1_.distanceSquared(vec3d4) < p_a_1_.distanceSquared(vec3d6)))
        {
            vec3d6 = vec3d4;
        }

        if (vec3d5 != null && (vec3d6 == null || p_a_1_.distanceSquared(vec3d5) < p_a_1_.distanceSquared(vec3d6)))
        {
            vec3d6 = vec3d5;
        }

        if (vec3d6 == null)
        {
            return null;
        }
        else
        {
            EnumDirection enumdirection = null;

            if (vec3d6 == vec3d)
            {
                enumdirection = EnumDirection.WEST;
            }
            else if (vec3d6 == vec3d1)
            {
                enumdirection = EnumDirection.EAST;
            }
            else if (vec3d6 == vec3d2)
            {
                enumdirection = EnumDirection.DOWN;
            }
            else if (vec3d6 == vec3d3)
            {
                enumdirection = EnumDirection.UP;
            }
            else if (vec3d6 == vec3d4)
            {
                enumdirection = EnumDirection.NORTH;
            }
            else
            {
                enumdirection = EnumDirection.SOUTH;
            }

            return new MovingObjectPosition(vec3d6, enumdirection);
        }
    }

    private boolean b(Vec3D p_b_1_)
    {
        return p_b_1_ == null ? false : p_b_1_.b >= this.b && p_b_1_.b <= this.e && p_b_1_.c >= this.c && p_b_1_.c <= this.f;
    }

    private boolean c(Vec3D p_c_1_)
    {
        return p_c_1_ == null ? false : p_c_1_.a >= this.a && p_c_1_.a <= this.d && p_c_1_.c >= this.c && p_c_1_.c <= this.f;
    }

    private boolean d(Vec3D p_d_1_)
    {
        return p_d_1_ == null ? false : p_d_1_.a >= this.a && p_d_1_.a <= this.d && p_d_1_.b >= this.b && p_d_1_.b <= this.e;
    }

    public String toString()
    {
        return "box[" + this.a + ", " + this.b + ", " + this.c + " -> " + this.d + ", " + this.e + ", " + this.f + "]";
    }
}
