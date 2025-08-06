package net.minecraft.server.v1_8_R3;

public class Position implements IPosition
{
    protected final double a;
    protected final double b;
    protected final double c;

    public Position(double p_i901_1_, double p_i901_3_, double p_i901_5_)
    {
        this.a = p_i901_1_;
        this.b = p_i901_3_;
        this.c = p_i901_5_;
    }

    public double getX()
    {
        return this.a;
    }

    public double getY()
    {
        return this.b;
    }

    public double getZ()
    {
        return this.c;
    }
}
