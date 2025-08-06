package net.minecraft.server.v1_8_R3;

public class GenLayerBiome extends GenLayer
{
    private BiomeBase[] c = new BiomeBase[] {BiomeBase.DESERT, BiomeBase.DESERT, BiomeBase.DESERT, BiomeBase.SAVANNA, BiomeBase.SAVANNA, BiomeBase.PLAINS};
    private BiomeBase[] d = new BiomeBase[] {BiomeBase.FOREST, BiomeBase.ROOFED_FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.PLAINS, BiomeBase.BIRCH_FOREST, BiomeBase.SWAMPLAND};
    private BiomeBase[] e = new BiomeBase[] {BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.TAIGA, BiomeBase.PLAINS};
    private BiomeBase[] f = new BiomeBase[] {BiomeBase.ICE_PLAINS, BiomeBase.ICE_PLAINS, BiomeBase.ICE_PLAINS, BiomeBase.COLD_TAIGA};
    private final CustomWorldSettingsFinal g;

    public GenLayerBiome(long p_i816_1_, GenLayer p_i816_3_, WorldType p_i816_4_, String p_i816_5_)
    {
        super(p_i816_1_);
        this.a = p_i816_3_;

        if (p_i816_4_ == WorldType.NORMAL_1_1)
        {
            this.c = new BiomeBase[] {BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA};
            this.g = null;
        }
        else if (p_i816_4_ == WorldType.CUSTOMIZED)
        {
            this.g = CustomWorldSettingsFinal.CustomWorldSettings.a(p_i816_5_).b();
        }
        else
        {
            this.g = null;
        }
    }

    public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        int[] aint = this.a.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
        int[] aint1 = IntCache.a(p_a_3_ * p_a_4_);

        for (int i = 0; i < p_a_4_; ++i)
        {
            for (int j = 0; j < p_a_3_; ++j)
            {
                this.a((long)(j + p_a_1_), (long)(i + p_a_2_));
                int k = aint[j + i * p_a_3_];
                int l = (k & 3840) >> 8;
                k = k & -3841;

                if (this.g != null && this.g.F >= 0)
                {
                    aint1[j + i * p_a_3_] = this.g.F;
                }
                else if (b(k))
                {
                    aint1[j + i * p_a_3_] = k;
                }
                else if (k == BiomeBase.MUSHROOM_ISLAND.id)
                {
                    aint1[j + i * p_a_3_] = k;
                }
                else if (k == 1)
                {
                    if (l > 0)
                    {
                        if (this.a(3) == 0)
                        {
                            aint1[j + i * p_a_3_] = BiomeBase.MESA_PLATEAU.id;
                        }
                        else
                        {
                            aint1[j + i * p_a_3_] = BiomeBase.MESA_PLATEAU_F.id;
                        }
                    }
                    else
                    {
                        aint1[j + i * p_a_3_] = this.c[this.a(this.c.length)].id;
                    }
                }
                else if (k == 2)
                {
                    if (l > 0)
                    {
                        aint1[j + i * p_a_3_] = BiomeBase.JUNGLE.id;
                    }
                    else
                    {
                        aint1[j + i * p_a_3_] = this.d[this.a(this.d.length)].id;
                    }
                }
                else if (k == 3)
                {
                    if (l > 0)
                    {
                        aint1[j + i * p_a_3_] = BiomeBase.MEGA_TAIGA.id;
                    }
                    else
                    {
                        aint1[j + i * p_a_3_] = this.e[this.a(this.e.length)].id;
                    }
                }
                else if (k == 4)
                {
                    aint1[j + i * p_a_3_] = this.f[this.a(this.f.length)].id;
                }
                else
                {
                    aint1[j + i * p_a_3_] = BiomeBase.MUSHROOM_ISLAND.id;
                }
            }
        }

        return aint1;
    }
}
