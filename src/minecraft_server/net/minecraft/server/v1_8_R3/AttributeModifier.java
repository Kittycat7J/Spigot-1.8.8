package net.minecraft.server.v1_8_R3;

import io.netty.util.internal.ThreadLocalRandom;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.Validate;

public class AttributeModifier
{
    private final double a;
    private final int b;
    private final String c;
    private final UUID d;
    private boolean e;

    public AttributeModifier(String p_i1150_1_, double p_i1150_2_, int p_i1150_4_)
    {
        this(MathHelper.a((Random)ThreadLocalRandom.current()), p_i1150_1_, p_i1150_2_, p_i1150_4_);
    }

    public AttributeModifier(UUID p_i1151_1_, String p_i1151_2_, double p_i1151_3_, int p_i1151_5_)
    {
        this.e = true;
        this.d = p_i1151_1_;
        this.c = p_i1151_2_;
        this.a = p_i1151_3_;
        this.b = p_i1151_5_;
        Validate.notEmpty(p_i1151_2_, "Modifier name cannot be empty", new Object[0]);
        Validate.inclusiveBetween(0L, 2L, (long)p_i1151_5_, "Invalid operation");
    }

    public UUID a()
    {
        return this.d;
    }

    public String b()
    {
        return this.c;
    }

    public int c()
    {
        return this.b;
    }

    public double d()
    {
        return this.a;
    }

    public boolean e()
    {
        return this.e;
    }

    public AttributeModifier a(boolean p_a_1_)
    {
        this.e = p_a_1_;
        return this;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (p_equals_1_ != null && this.getClass() == p_equals_1_.getClass())
        {
            AttributeModifier attributemodifier = (AttributeModifier)p_equals_1_;

            if (this.d != null)
            {
                if (!this.d.equals(attributemodifier.d))
                {
                    return false;
                }
            }
            else if (attributemodifier.d != null)
            {
                return false;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return this.d != null ? this.d.hashCode() : 0;
    }

    public String toString()
    {
        return "AttributeModifier{amount=" + this.a + ", operation=" + this.b + ", name=\'" + this.c + '\'' + ", id=" + this.d + ", serialize=" + this.e + '}';
    }
}
