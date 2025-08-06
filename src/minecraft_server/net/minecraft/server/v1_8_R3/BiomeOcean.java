package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BiomeOcean extends BiomeBase
{
    public BiomeOcean(int p_i583_1_)
    {
        super(p_i583_1_);
        this.au.clear();
    }

    public BiomeBase.EnumTemperature m()
    {
        return BiomeBase.EnumTemperature.OCEAN;
    }

    public void a(World p_a_1_, Random p_a_2_, ChunkSnapshot p_a_3_, int p_a_4_, int p_a_5_, double p_a_6_)
    {
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
    }
}
