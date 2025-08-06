package net.minecraft.server.v1_8_R3;

import java.util.Map;
import java.util.Map.Entry;

public class WorldGenMineshaft extends StructureGenerator
{
    private double d = 0.004D;

    public WorldGenMineshaft()
    {
    }

    public String a()
    {
        return "Mineshaft";
    }

    public WorldGenMineshaft(Map<String, String> p_i722_1_)
    {
        for (Entry entry : p_i722_1_.entrySet())
        {
            if (((String)entry.getKey()).equals("chance"))
            {
                this.d = MathHelper.a((String)entry.getValue(), this.d);
            }
        }
    }

    protected boolean a(int p_a_1_, int p_a_2_)
    {
        return this.b.nextDouble() < this.d && this.b.nextInt(80) < Math.max(Math.abs(p_a_1_), Math.abs(p_a_2_));
    }

    protected StructureStart b(int p_b_1_, int p_b_2_)
    {
        return new WorldGenMineshaftStart(this.c, this.b, p_b_1_, p_b_2_);
    }
}
