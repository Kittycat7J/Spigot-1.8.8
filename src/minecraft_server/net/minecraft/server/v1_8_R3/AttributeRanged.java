package net.minecraft.server.v1_8_R3;

public class AttributeRanged extends AttributeBase
{
    private final double a;
    public double b;
    private String c;

    public AttributeRanged(IAttribute p_i273_1_, String p_i273_2_, double p_i273_3_, double p_i273_5_, double p_i273_7_)
    {
        super(p_i273_1_, p_i273_2_, p_i273_3_);
        this.a = p_i273_5_;
        this.b = p_i273_7_;

        if (p_i273_5_ > p_i273_7_)
        {
            throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
        }
        else if (p_i273_3_ < p_i273_5_)
        {
            throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
        }
        else if (p_i273_3_ > p_i273_7_)
        {
            throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
        }
    }

    public AttributeRanged a(String p_a_1_)
    {
        this.c = p_a_1_;
        return this;
    }

    public String g()
    {
        return this.c;
    }

    public double a(double p_a_1_)
    {
        p_a_1_ = MathHelper.a(p_a_1_, this.a, this.b);
        return p_a_1_;
    }
}
