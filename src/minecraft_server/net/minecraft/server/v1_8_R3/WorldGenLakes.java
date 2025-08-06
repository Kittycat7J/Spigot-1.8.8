package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenLakes extends WorldGenerator
{
    private Block a;

    public WorldGenLakes(Block p_i701_1_)
    {
        this.a = p_i701_1_;
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        for (p_generate_3_ = p_generate_3_.a(-8, 0, -8); p_generate_3_.getY() > 5 && p_generate_1_.isEmpty(p_generate_3_); p_generate_3_ = p_generate_3_.down())
        {
            ;
        }

        if (p_generate_3_.getY() <= 4)
        {
            return false;
        }
        else
        {
            p_generate_3_ = p_generate_3_.down(4);
            boolean[] aboolean = new boolean[2048];
            int i = p_generate_2_.nextInt(4) + 4;

            for (int j = 0; j < i; ++j)
            {
                double d0 = p_generate_2_.nextDouble() * 6.0D + 3.0D;
                double d1 = p_generate_2_.nextDouble() * 4.0D + 2.0D;
                double d2 = p_generate_2_.nextDouble() * 6.0D + 3.0D;
                double d3 = p_generate_2_.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = p_generate_2_.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = p_generate_2_.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for (int k = 1; k < 15; ++k)
                {
                    for (int l = 1; l < 15; ++l)
                    {
                        for (int i1 = 1; i1 < 7; ++i1)
                        {
                            double d6 = ((double)k - d3) / (d0 / 2.0D);
                            double d7 = ((double)i1 - d4) / (d1 / 2.0D);
                            double d8 = ((double)l - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if (d9 < 1.0D)
                            {
                                aboolean[(k * 16 + l) * 8 + i1] = true;
                            }
                        }
                    }
                }
            }

            for (int l1 = 0; l1 < 16; ++l1)
            {
                for (int j1 = 0; j1 < 16; ++j1)
                {
                    for (int k1 = 0; k1 < 8; ++k1)
                    {
                        boolean flag = !aboolean[(l1 * 16 + j1) * 8 + k1] && (l1 < 15 && aboolean[((l1 + 1) * 16 + j1) * 8 + k1] || l1 > 0 && aboolean[((l1 - 1) * 16 + j1) * 8 + k1] || j1 < 15 && aboolean[(l1 * 16 + j1 + 1) * 8 + k1] || j1 > 0 && aboolean[(l1 * 16 + (j1 - 1)) * 8 + k1] || k1 < 7 && aboolean[(l1 * 16 + j1) * 8 + k1 + 1] || k1 > 0 && aboolean[(l1 * 16 + j1) * 8 + (k1 - 1)]);

                        if (flag)
                        {
                            Material material = p_generate_1_.getType(p_generate_3_.a(l1, k1, j1)).getBlock().getMaterial();

                            if (k1 >= 4 && material.isLiquid())
                            {
                                return false;
                            }

                            if (k1 < 4 && !material.isBuildable() && p_generate_1_.getType(p_generate_3_.a(l1, k1, j1)).getBlock() != this.a)
                            {
                                return false;
                            }
                        }
                    }
                }
            }

            for (int i2 = 0; i2 < 16; ++i2)
            {
                for (int i3 = 0; i3 < 16; ++i3)
                {
                    for (int i4 = 0; i4 < 8; ++i4)
                    {
                        if (aboolean[(i2 * 16 + i3) * 8 + i4])
                        {
                            p_generate_1_.setTypeAndData(p_generate_3_.a(i2, i4, i3), i4 >= 4 ? Blocks.AIR.getBlockData() : this.a.getBlockData(), 2);
                        }
                    }
                }
            }

            for (int j2 = 0; j2 < 16; ++j2)
            {
                for (int j3 = 0; j3 < 16; ++j3)
                {
                    for (int j4 = 4; j4 < 8; ++j4)
                    {
                        if (aboolean[(j2 * 16 + j3) * 8 + j4])
                        {
                            BlockPosition blockposition = p_generate_3_.a(j2, j4 - 1, j3);

                            if (p_generate_1_.getType(blockposition).getBlock() == Blocks.DIRT && p_generate_1_.b(EnumSkyBlock.SKY, p_generate_3_.a(j2, j4, j3)) > 0)
                            {
                                BiomeBase biomebase = p_generate_1_.getBiome(blockposition);

                                if (biomebase.ak.getBlock() == Blocks.MYCELIUM)
                                {
                                    p_generate_1_.setTypeAndData(blockposition, Blocks.MYCELIUM.getBlockData(), 2);
                                }
                                else
                                {
                                    p_generate_1_.setTypeAndData(blockposition, Blocks.GRASS.getBlockData(), 2);
                                }
                            }
                        }
                    }
                }
            }

            if (this.a.getMaterial() == Material.LAVA)
            {
                for (int k2 = 0; k2 < 16; ++k2)
                {
                    for (int k3 = 0; k3 < 16; ++k3)
                    {
                        for (int k4 = 0; k4 < 8; ++k4)
                        {
                            boolean flag1 = !aboolean[(k2 * 16 + k3) * 8 + k4] && (k2 < 15 && aboolean[((k2 + 1) * 16 + k3) * 8 + k4] || k2 > 0 && aboolean[((k2 - 1) * 16 + k3) * 8 + k4] || k3 < 15 && aboolean[(k2 * 16 + k3 + 1) * 8 + k4] || k3 > 0 && aboolean[(k2 * 16 + (k3 - 1)) * 8 + k4] || k4 < 7 && aboolean[(k2 * 16 + k3) * 8 + k4 + 1] || k4 > 0 && aboolean[(k2 * 16 + k3) * 8 + (k4 - 1)]);

                            if (flag1 && (k4 < 4 || p_generate_2_.nextInt(2) != 0) && p_generate_1_.getType(p_generate_3_.a(k2, k4, k3)).getBlock().getMaterial().isBuildable())
                            {
                                p_generate_1_.setTypeAndData(p_generate_3_.a(k2, k4, k3), Blocks.STONE.getBlockData(), 2);
                            }
                        }
                    }
                }
            }

            if (this.a.getMaterial() == Material.WATER)
            {
                for (int l2 = 0; l2 < 16; ++l2)
                {
                    for (int l3 = 0; l3 < 16; ++l3)
                    {
                        byte b0 = 4;

                        if (p_generate_1_.v(p_generate_3_.a(l2, b0, l3)))
                        {
                            p_generate_1_.setTypeAndData(p_generate_3_.a(l2, b0, l3), Blocks.ICE.getBlockData(), 2);
                        }
                    }
                }
            }

            return true;
        }
    }
}
