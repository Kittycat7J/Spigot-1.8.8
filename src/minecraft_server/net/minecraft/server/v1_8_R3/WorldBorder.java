package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.util.LongHash;

public class WorldBorder
{
    private final List<IWorldBorderListener> a = Lists.<IWorldBorderListener>newArrayList();
    private double b = 0.0D;
    private double c = 0.0D;
    private double d = 6.0E7D;
    private double e;
    private long f;
    private long g;
    private int h;
    private double i;
    private double j;
    private int k;
    private int l;
    public WorldServer world;

    public WorldBorder()
    {
        this.e = this.d;
        this.h = 29999984;
        this.i = 0.2D;
        this.j = 5.0D;
        this.k = 15;
        this.l = 5;
    }

    public boolean a(BlockPosition p_a_1_)
    {
        return (double)(p_a_1_.getX() + 1) > this.b() && (double)p_a_1_.getX() < this.d() && (double)(p_a_1_.getZ() + 1) > this.c() && (double)p_a_1_.getZ() < this.e();
    }

    public boolean isInBounds(ChunkCoordIntPair p_isInBounds_1_)
    {
        return this.isInBounds(p_isInBounds_1_.x, p_isInBounds_1_.z);
    }

    public boolean isInBounds(long p_isInBounds_1_)
    {
        return this.isInBounds(LongHash.msw(p_isInBounds_1_), LongHash.lsw(p_isInBounds_1_));
    }

    public boolean isInBounds(int p_isInBounds_1_, int p_isInBounds_2_)
    {
        return (double)((p_isInBounds_1_ << 4) + 15) > this.b() && (double)(p_isInBounds_1_ << 4) < this.d() && (double)((p_isInBounds_2_ << 4) + 15) > this.c() && (double)(p_isInBounds_1_ << 4) < this.e();
    }

    public boolean a(AxisAlignedBB p_a_1_)
    {
        return p_a_1_.d > this.b() && p_a_1_.a < this.d() && p_a_1_.f > this.c() && p_a_1_.c < this.e();
    }

    public double a(Entity p_a_1_)
    {
        return this.b(p_a_1_.locX, p_a_1_.locZ);
    }

    public double b(double p_b_1_, double p_b_3_)
    {
        double d0 = p_b_3_ - this.c();
        double d1 = this.e() - p_b_3_;
        double d2 = p_b_1_ - this.b();
        double d3 = this.d() - p_b_1_;
        double d4 = Math.min(d2, d3);
        d4 = Math.min(d4, d0);
        return Math.min(d4, d1);
    }

    public EnumWorldBorderState getState()
    {
        return this.e < this.d ? EnumWorldBorderState.SHRINKING : (this.e > this.d ? EnumWorldBorderState.GROWING : EnumWorldBorderState.STATIONARY);
    }

    public double b()
    {
        double d0 = this.getCenterX() - this.getSize() / 2.0D;

        if (d0 < (double)(-this.h))
        {
            d0 = (double)(-this.h);
        }

        return d0;
    }

    public double c()
    {
        double d0 = this.getCenterZ() - this.getSize() / 2.0D;

        if (d0 < (double)(-this.h))
        {
            d0 = (double)(-this.h);
        }

        return d0;
    }

    public double d()
    {
        double d0 = this.getCenterX() + this.getSize() / 2.0D;

        if (d0 > (double)this.h)
        {
            d0 = (double)this.h;
        }

        return d0;
    }

    public double e()
    {
        double d0 = this.getCenterZ() + this.getSize() / 2.0D;

        if (d0 > (double)this.h)
        {
            d0 = (double)this.h;
        }

        return d0;
    }

    public double getCenterX()
    {
        return this.b;
    }

    public double getCenterZ()
    {
        return this.c;
    }

    public void setCenter(double p_setCenter_1_, double p_setCenter_3_)
    {
        this.b = p_setCenter_1_;
        this.c = p_setCenter_3_;

        for (IWorldBorderListener iworldborderlistener : this.k())
        {
            iworldborderlistener.a(this, p_setCenter_1_, p_setCenter_3_);
        }
    }

    public double getSize()
    {
        if (this.getState() != EnumWorldBorderState.STATIONARY)
        {
            double d0 = (double)((float)(System.currentTimeMillis() - this.g) / (float)(this.f - this.g));

            if (d0 < 1.0D)
            {
                return this.d + (this.e - this.d) * d0;
            }

            this.setSize(this.e);
        }

        return this.d;
    }

    public long i()
    {
        return this.getState() != EnumWorldBorderState.STATIONARY ? this.f - System.currentTimeMillis() : 0L;
    }

    public double j()
    {
        return this.e;
    }

    public void setSize(double p_setSize_1_)
    {
        this.d = p_setSize_1_;
        this.e = p_setSize_1_;
        this.f = System.currentTimeMillis();
        this.g = this.f;

        for (IWorldBorderListener iworldborderlistener : this.k())
        {
            iworldborderlistener.a(this, p_setSize_1_);
        }
    }

    public void transitionSizeBetween(double p_transitionSizeBetween_1_, double p_transitionSizeBetween_3_, long p_transitionSizeBetween_5_)
    {
        this.d = p_transitionSizeBetween_1_;
        this.e = p_transitionSizeBetween_3_;
        this.g = System.currentTimeMillis();
        this.f = this.g + p_transitionSizeBetween_5_;

        for (IWorldBorderListener iworldborderlistener : this.k())
        {
            iworldborderlistener.a(this, p_transitionSizeBetween_1_, p_transitionSizeBetween_3_, p_transitionSizeBetween_5_);
        }
    }

    protected List<IWorldBorderListener> k()
    {
        return Lists.newArrayList(this.a);
    }

    public void a(IWorldBorderListener p_a_1_)
    {
        if (!this.a.contains(p_a_1_))
        {
            this.a.add(p_a_1_);
        }
    }

    public void a(int p_a_1_)
    {
        this.h = p_a_1_;
    }

    public int l()
    {
        return this.h;
    }

    public double getDamageBuffer()
    {
        return this.j;
    }

    public void setDamageBuffer(double p_setDamageBuffer_1_)
    {
        this.j = p_setDamageBuffer_1_;

        for (IWorldBorderListener iworldborderlistener : this.k())
        {
            iworldborderlistener.c(this, p_setDamageBuffer_1_);
        }
    }

    public double getDamageAmount()
    {
        return this.i;
    }

    public void setDamageAmount(double p_setDamageAmount_1_)
    {
        this.i = p_setDamageAmount_1_;

        for (IWorldBorderListener iworldborderlistener : this.k())
        {
            iworldborderlistener.b(this, p_setDamageAmount_1_);
        }
    }

    public int getWarningTime()
    {
        return this.k;
    }

    public void setWarningTime(int p_setWarningTime_1_)
    {
        this.k = p_setWarningTime_1_;

        for (IWorldBorderListener iworldborderlistener : this.k())
        {
            iworldborderlistener.a(this, p_setWarningTime_1_);
        }
    }

    public int getWarningDistance()
    {
        return this.l;
    }

    public void setWarningDistance(int p_setWarningDistance_1_)
    {
        this.l = p_setWarningDistance_1_;

        for (IWorldBorderListener iworldborderlistener : this.k())
        {
            iworldborderlistener.b(this, p_setWarningDistance_1_);
        }
    }
}
