package net.minecraft.server.v1_8_R3;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class MinecraftKey
{
    protected final String a;
    protected final String b;

    protected MinecraftKey(int p_i1055_1_, String... p_i1055_2_)
    {
        this.a = StringUtils.isEmpty(p_i1055_2_[0]) ? "minecraft" : p_i1055_2_[0].toLowerCase();
        this.b = p_i1055_2_[1];
        Validate.notNull(this.b);
    }

    public MinecraftKey(String p_i1056_1_)
    {
        this(0, a(p_i1056_1_));
    }

    protected static String[] a(String p_a_0_)
    {
        String[] astring = new String[] {null, p_a_0_};
        int i = p_a_0_.indexOf(58);

        if (i >= 0)
        {
            astring[1] = p_a_0_.substring(i + 1, p_a_0_.length());

            if (i > 1)
            {
                astring[0] = p_a_0_.substring(0, i);
            }
        }

        return astring;
    }

    public String a()
    {
        return this.b;
    }

    public String toString()
    {
        return this.a + ':' + this.b;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof MinecraftKey))
        {
            return false;
        }
        else
        {
            MinecraftKey minecraftkey = (MinecraftKey)p_equals_1_;
            return this.a.equals(minecraftkey.a) && this.b.equals(minecraftkey.b);
        }
    }

    public int hashCode()
    {
        return 31 * this.a.hashCode() + this.b.hashCode();
    }
}
