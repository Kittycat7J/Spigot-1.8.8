package net.minecraft.server.v1_8_R3;

public class GenLayerMushroomShore extends GenLayer
{
    public GenLayerMushroomShore(long p_i828_1_, GenLayer p_i828_3_)
    {
        super(p_i828_1_);
        this.a = p_i828_3_;
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
                BiomeBase biomebase = BiomeBase.getBiome(k);

                if (k == BiomeBase.MUSHROOM_ISLAND.id)
                {
                    int j2 = aint[j + 1 + (i + 1 - 1) * (p_a_3_ + 2)];
                    int i3 = aint[j + 1 + 1 + (i + 1) * (p_a_3_ + 2)];
                    int l3 = aint[j + 1 - 1 + (i + 1) * (p_a_3_ + 2)];
                    int k4 = aint[j + 1 + (i + 1 + 1) * (p_a_3_ + 2)];

                    if (j2 != BiomeBase.OCEAN.id && i3 != BiomeBase.OCEAN.id && l3 != BiomeBase.OCEAN.id && k4 != BiomeBase.OCEAN.id)
                    {
                        aint1[j + i * p_a_3_] = k;
                    }
                    else
                    {
                        aint1[j + i * p_a_3_] = BiomeBase.MUSHROOM_SHORE.id;
                    }
                }
                else if (biomebase != null && biomebase.l() == BiomeJungle.class)
                {
                    int i2 = aint[j + 1 + (i + 1 - 1) * (p_a_3_ + 2)];
                    int l2 = aint[j + 1 + 1 + (i + 1) * (p_a_3_ + 2)];
                    int k3 = aint[j + 1 - 1 + (i + 1) * (p_a_3_ + 2)];
                    int j4 = aint[j + 1 + (i + 1 + 1) * (p_a_3_ + 2)];

                    if (this.c(i2) && this.c(l2) && this.c(k3) && this.c(j4))
                    {
                        if (!b(i2) && !b(l2) && !b(k3) && !b(j4))
                        {
                            aint1[j + i * p_a_3_] = k;
                        }
                        else
                        {
                            aint1[j + i * p_a_3_] = BiomeBase.BEACH.id;
                        }
                    }
                    else
                    {
                        aint1[j + i * p_a_3_] = BiomeBase.JUNGLE_EDGE.id;
                    }
                }
                else if (k != BiomeBase.EXTREME_HILLS.id && k != BiomeBase.EXTREME_HILLS_PLUS.id && k != BiomeBase.SMALL_MOUNTAINS.id)
                {
                    if (biomebase != null && biomebase.j())
                    {
                        this.a(aint, aint1, j, i, p_a_3_, k, BiomeBase.COLD_BEACH.id);
                    }
                    else if (k != BiomeBase.MESA.id && k != BiomeBase.MESA_PLATEAU_F.id)
                    {
                        if (k != BiomeBase.OCEAN.id && k != BiomeBase.DEEP_OCEAN.id && k != BiomeBase.RIVER.id && k != BiomeBase.SWAMPLAND.id)
                        {
                            int l1 = aint[j + 1 + (i + 1 - 1) * (p_a_3_ + 2)];
                            int k2 = aint[j + 1 + 1 + (i + 1) * (p_a_3_ + 2)];
                            int j3 = aint[j + 1 - 1 + (i + 1) * (p_a_3_ + 2)];
                            int i4 = aint[j + 1 + (i + 1 + 1) * (p_a_3_ + 2)];

                            if (!b(l1) && !b(k2) && !b(j3) && !b(i4))
                            {
                                aint1[j + i * p_a_3_] = k;
                            }
                            else
                            {
                                aint1[j + i * p_a_3_] = BiomeBase.BEACH.id;
                            }
                        }
                        else
                        {
                            aint1[j + i * p_a_3_] = k;
                        }
                    }
                    else
                    {
                        int l = aint[j + 1 + (i + 1 - 1) * (p_a_3_ + 2)];
                        int i1 = aint[j + 1 + 1 + (i + 1) * (p_a_3_ + 2)];
                        int j1 = aint[j + 1 - 1 + (i + 1) * (p_a_3_ + 2)];
                        int k1 = aint[j + 1 + (i + 1 + 1) * (p_a_3_ + 2)];

                        if (!b(l) && !b(i1) && !b(j1) && !b(k1))
                        {
                            if (this.d(l) && this.d(i1) && this.d(j1) && this.d(k1))
                            {
                                aint1[j + i * p_a_3_] = k;
                            }
                            else
                            {
                                aint1[j + i * p_a_3_] = BiomeBase.DESERT.id;
                            }
                        }
                        else
                        {
                            aint1[j + i * p_a_3_] = k;
                        }
                    }
                }
                else
                {
                    this.a(aint, aint1, j, i, p_a_3_, k, BiomeBase.STONE_BEACH.id);
                }
            }
        }

        return aint1;
    }

    private void a(int[] p_a_1_, int[] p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, int p_a_6_, int p_a_7_)
    {
        if (b(p_a_6_))
        {
            p_a_2_[p_a_3_ + p_a_4_ * p_a_5_] = p_a_6_;
        }
        else
        {
            int i = p_a_1_[p_a_3_ + 1 + (p_a_4_ + 1 - 1) * (p_a_5_ + 2)];
            int j = p_a_1_[p_a_3_ + 1 + 1 + (p_a_4_ + 1) * (p_a_5_ + 2)];
            int k = p_a_1_[p_a_3_ + 1 - 1 + (p_a_4_ + 1) * (p_a_5_ + 2)];
            int l = p_a_1_[p_a_3_ + 1 + (p_a_4_ + 1 + 1) * (p_a_5_ + 2)];

            if (!b(i) && !b(j) && !b(k) && !b(l))
            {
                p_a_2_[p_a_3_ + p_a_4_ * p_a_5_] = p_a_6_;
            }
            else
            {
                p_a_2_[p_a_3_ + p_a_4_ * p_a_5_] = p_a_7_;
            }
        }
    }

    private boolean c(int p_c_1_)
    {
        return BiomeBase.getBiome(p_c_1_) != null && BiomeBase.getBiome(p_c_1_).l() == BiomeJungle.class ? true : p_c_1_ == BiomeBase.JUNGLE_EDGE.id || p_c_1_ == BiomeBase.JUNGLE.id || p_c_1_ == BiomeBase.JUNGLE_HILLS.id || p_c_1_ == BiomeBase.FOREST.id || p_c_1_ == BiomeBase.TAIGA.id || b(p_c_1_);
    }

    private boolean d(int p_d_1_)
    {
        return BiomeBase.getBiome(p_d_1_) instanceof BiomeMesa;
    }
}
