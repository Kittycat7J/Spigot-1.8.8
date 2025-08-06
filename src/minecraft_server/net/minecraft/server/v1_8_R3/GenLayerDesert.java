package net.minecraft.server.v1_8_R3;

public class GenLayerDesert extends GenLayer
{
    public GenLayerDesert(long p_i815_1_, GenLayer p_i815_3_)
    {
        super(p_i815_1_);
        this.a = p_i815_3_;
    }

    public int[] a(int p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_)
    {
        int[] aint = this.a.a(p_a_1_ - 1, p_a_2_ - 1, p_a_3_ + 2, p_a_4_ + 2);
        int[] aint1 = IntCache.a(p_a_3_ * p_a_4_);

        for (int i = 0; i < p_a_4_; ++i)
        {
            for (int j = 0; j < p_a_3_; ++j)
            {
                this.a((long)(j + p_a_1_), (long)(i + p_a_2_));
                int k = aint[j + 1 + (i + 1) * (p_a_3_ + 2)];

                if (!this.a(aint, aint1, j, i, p_a_3_, k, BiomeBase.EXTREME_HILLS.id, BiomeBase.SMALL_MOUNTAINS.id) && !this.b(aint, aint1, j, i, p_a_3_, k, BiomeBase.MESA_PLATEAU_F.id, BiomeBase.MESA.id) && !this.b(aint, aint1, j, i, p_a_3_, k, BiomeBase.MESA_PLATEAU.id, BiomeBase.MESA.id) && !this.b(aint, aint1, j, i, p_a_3_, k, BiomeBase.MEGA_TAIGA.id, BiomeBase.TAIGA.id))
                {
                    if (k == BiomeBase.DESERT.id)
                    {
                        int l1 = aint[j + 1 + (i + 1 - 1) * (p_a_3_ + 2)];
                        int i2 = aint[j + 1 + 1 + (i + 1) * (p_a_3_ + 2)];
                        int j2 = aint[j + 1 - 1 + (i + 1) * (p_a_3_ + 2)];
                        int k2 = aint[j + 1 + (i + 1 + 1) * (p_a_3_ + 2)];

                        if (l1 != BiomeBase.ICE_PLAINS.id && i2 != BiomeBase.ICE_PLAINS.id && j2 != BiomeBase.ICE_PLAINS.id && k2 != BiomeBase.ICE_PLAINS.id)
                        {
                            aint1[j + i * p_a_3_] = k;
                        }
                        else
                        {
                            aint1[j + i * p_a_3_] = BiomeBase.EXTREME_HILLS_PLUS.id;
                        }
                    }
                    else if (k == BiomeBase.SWAMPLAND.id)
                    {
                        int l = aint[j + 1 + (i + 1 - 1) * (p_a_3_ + 2)];
                        int i1 = aint[j + 1 + 1 + (i + 1) * (p_a_3_ + 2)];
                        int j1 = aint[j + 1 - 1 + (i + 1) * (p_a_3_ + 2)];
                        int k1 = aint[j + 1 + (i + 1 + 1) * (p_a_3_ + 2)];

                        if (l != BiomeBase.DESERT.id && i1 != BiomeBase.DESERT.id && j1 != BiomeBase.DESERT.id && k1 != BiomeBase.DESERT.id && l != BiomeBase.COLD_TAIGA.id && i1 != BiomeBase.COLD_TAIGA.id && j1 != BiomeBase.COLD_TAIGA.id && k1 != BiomeBase.COLD_TAIGA.id && l != BiomeBase.ICE_PLAINS.id && i1 != BiomeBase.ICE_PLAINS.id && j1 != BiomeBase.ICE_PLAINS.id && k1 != BiomeBase.ICE_PLAINS.id)
                        {
                            if (l != BiomeBase.JUNGLE.id && k1 != BiomeBase.JUNGLE.id && i1 != BiomeBase.JUNGLE.id && j1 != BiomeBase.JUNGLE.id)
                            {
                                aint1[j + i * p_a_3_] = k;
                            }
                            else
                            {
                                aint1[j + i * p_a_3_] = BiomeBase.JUNGLE_EDGE.id;
                            }
                        }
                        else
                        {
                            aint1[j + i * p_a_3_] = BiomeBase.PLAINS.id;
                        }
                    }
                    else
                    {
                        aint1[j + i * p_a_3_] = k;
                    }
                }
            }
        }

        return aint1;
    }

    private boolean a(int[] p_a_1_, int[] p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_, int p_a_8_)
    {
        if (!a(p_a_6_, p_a_7_))
        {
            return false;
        }
        else
        {
            int i = p_a_1_[p_a_3_ + 1 + (p_a_4_ + 1 - 1) * (p_a_5_ + 2)];
            int j = p_a_1_[p_a_3_ + 1 + 1 + (p_a_4_ + 1) * (p_a_5_ + 2)];
            int k = p_a_1_[p_a_3_ + 1 - 1 + (p_a_4_ + 1) * (p_a_5_ + 2)];
            int l = p_a_1_[p_a_3_ + 1 + (p_a_4_ + 1 + 1) * (p_a_5_ + 2)];

            if (this.b(i, p_a_7_) && this.b(j, p_a_7_) && this.b(k, p_a_7_) && this.b(l, p_a_7_))
            {
                p_a_2_[p_a_3_ + p_a_4_ * p_a_5_] = p_a_6_;
            }
            else
            {
                p_a_2_[p_a_3_ + p_a_4_ * p_a_5_] = p_a_8_;
            }

            return true;
        }
    }

    private boolean b(int[] p_b_1_, int[] p_b_2_, int p_b_3_, int p_b_4_, int p_b_5_, int p_b_6_, int p_b_7_, int p_b_8_)
    {
        if (p_b_6_ != p_b_7_)
        {
            return false;
        }
        else
        {
            int i = p_b_1_[p_b_3_ + 1 + (p_b_4_ + 1 - 1) * (p_b_5_ + 2)];
            int j = p_b_1_[p_b_3_ + 1 + 1 + (p_b_4_ + 1) * (p_b_5_ + 2)];
            int k = p_b_1_[p_b_3_ + 1 - 1 + (p_b_4_ + 1) * (p_b_5_ + 2)];
            int l = p_b_1_[p_b_3_ + 1 + (p_b_4_ + 1 + 1) * (p_b_5_ + 2)];

            if (a(i, p_b_7_) && a(j, p_b_7_) && a(k, p_b_7_) && a(l, p_b_7_))
            {
                p_b_2_[p_b_3_ + p_b_4_ * p_b_5_] = p_b_6_;
            }
            else
            {
                p_b_2_[p_b_3_ + p_b_4_ * p_b_5_] = p_b_8_;
            }

            return true;
        }
    }

    private boolean b(int p_b_1_, int p_b_2_)
    {
        if (a(p_b_1_, p_b_2_))
        {
            return true;
        }
        else
        {
            BiomeBase biomebase = BiomeBase.getBiome(p_b_1_);
            BiomeBase biomebase1 = BiomeBase.getBiome(p_b_2_);

            if (biomebase != null && biomebase1 != null)
            {
                BiomeBase.EnumTemperature biomebase$enumtemperature = biomebase.m();
                BiomeBase.EnumTemperature biomebase$enumtemperature1 = biomebase1.m();
                return biomebase$enumtemperature == biomebase$enumtemperature1 || biomebase$enumtemperature == BiomeBase.EnumTemperature.MEDIUM || biomebase$enumtemperature1 == BiomeBase.EnumTemperature.MEDIUM;
            }
            else
            {
                return false;
            }
        }
    }
}
