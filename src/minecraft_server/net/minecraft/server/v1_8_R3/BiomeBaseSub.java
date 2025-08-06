package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.Random;

public class BiomeBaseSub extends BiomeBase
{
    protected BiomeBase aE;

    public BiomeBaseSub(int p_i582_1_, BiomeBase p_i582_2_)
    {
        super(p_i582_1_);
        this.aE = p_i582_2_;
        this.a(p_i582_2_.ai, true);
        this.ah = p_i582_2_.ah + " M";
        this.ak = p_i582_2_.ak;
        this.al = p_i582_2_.al;
        this.am = p_i582_2_.am;
        this.an = p_i582_2_.an;
        this.ao = p_i582_2_.ao;
        this.temperature = p_i582_2_.temperature;
        this.humidity = p_i582_2_.humidity;
        this.ar = p_i582_2_.ar;
        this.ax = p_i582_2_.ax;
        this.ay = p_i582_2_.ay;
        this.au = Lists.newArrayList(p_i582_2_.au);
        this.at = Lists.newArrayList(p_i582_2_.at);
        this.aw = Lists.newArrayList(p_i582_2_.aw);
        this.av = Lists.newArrayList(p_i582_2_.av);
        this.temperature = p_i582_2_.temperature;
        this.humidity = p_i582_2_.humidity;
        this.an = p_i582_2_.an + 0.1F;
        this.ao = p_i582_2_.ao + 0.2F;
    }

    public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_)
    {
        this.aE.as.a(p_a_1_, p_a_2_, this, p_a_3_);
    }

    public void a(World p_a_1_, Random p_a_2_, ChunkSnapshot p_a_3_, int p_a_4_, int p_a_5_, double p_a_6_)
    {
        this.aE.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
    }

    public float g()
    {
        return this.aE.g();
    }

    public WorldGenTreeAbstract a(Random p_a_1_)
    {
        return this.aE.a(p_a_1_);
    }

    public Class <? extends BiomeBase > l()
    {
        return this.aE.l();
    }

    public boolean a(BiomeBase p_a_1_)
    {
        return this.aE.a(p_a_1_);
    }

    public BiomeBase.EnumTemperature m()
    {
        return this.aE.m();
    }
}
