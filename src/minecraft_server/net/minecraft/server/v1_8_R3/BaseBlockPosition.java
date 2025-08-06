package net.minecraft.server.v1_8_R3;

import com.google.common.base.Objects;

public class BaseBlockPosition implements Comparable<BaseBlockPosition>
{
    public static final BaseBlockPosition ZERO = new BaseBlockPosition(0, 0, 0);
    private final int a;
    private final int c;
    private final int d;

    public BaseBlockPosition(int p_i904_1_, int p_i904_2_, int p_i904_3_)
    {
        this.a = p_i904_1_;
        this.c = p_i904_2_;
        this.d = p_i904_3_;
    }

    public BaseBlockPosition(double p_i905_1_, double p_i905_3_, double p_i905_5_)
    {
        this(MathHelper.floor(p_i905_1_), MathHelper.floor(p_i905_3_), MathHelper.floor(p_i905_5_));
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof BaseBlockPosition))
        {
            return false;
        }
        else
        {
            BaseBlockPosition baseblockposition = (BaseBlockPosition)p_equals_1_;
            return this.getX() != baseblockposition.getX() ? false : (this.getY() != baseblockposition.getY() ? false : this.getZ() == baseblockposition.getZ());
        }
    }

    public int hashCode()
    {
        return (this.getY() + this.getZ() * 31) * 31 + this.getX();
    }

    public int g(BaseBlockPosition p_g_1_)
    {
        return this.getY() == p_g_1_.getY() ? (this.getZ() == p_g_1_.getZ() ? this.getX() - p_g_1_.getX() : this.getZ() - p_g_1_.getZ()) : this.getY() - p_g_1_.getY();
    }

    public int getX()
    {
        return this.a;
    }

    public int getY()
    {
        return this.c;
    }

    public int getZ()
    {
        return this.d;
    }

    public BaseBlockPosition d(BaseBlockPosition p_d_1_)
    {
        return new BaseBlockPosition(this.getY() * p_d_1_.getZ() - this.getZ() * p_d_1_.getY(), this.getZ() * p_d_1_.getX() - this.getX() * p_d_1_.getZ(), this.getX() * p_d_1_.getY() - this.getY() * p_d_1_.getX());
    }

    public double c(double p_c_1_, double p_c_3_, double p_c_5_)
    {
        double d0 = (double)this.getX() - p_c_1_;
        double d1 = (double)this.getY() - p_c_3_;
        double d2 = (double)this.getZ() - p_c_5_;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double d(double p_d_1_, double p_d_3_, double p_d_5_)
    {
        double d0 = (double)this.getX() + 0.5D - p_d_1_;
        double d1 = (double)this.getY() + 0.5D - p_d_3_;
        double d2 = (double)this.getZ() + 0.5D - p_d_5_;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double i(BaseBlockPosition p_i_1_)
    {
        return this.c((double)p_i_1_.getX(), (double)p_i_1_.getY(), (double)p_i_1_.getZ());
    }

    public String toString()
    {
        return Objects.toStringHelper(this).add("x", this.getX()).add("y", this.getY()).add("z", this.getZ()).toString();
    }
}
