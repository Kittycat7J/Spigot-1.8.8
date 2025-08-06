package net.minecraft.server.v1_8_R3;

import java.util.Arrays;
import java.util.Random;

public class BiomeMesa extends BiomeBase
{
    private IBlockData[] aD;
    private long aE;
    private NoiseGenerator3 aF;
    private NoiseGenerator3 aG;
    private NoiseGenerator3 aH;
    private boolean aI;
    private boolean aJ;

    public BiomeMesa(int p_i580_1_, boolean p_i580_2_, boolean p_i580_3_)
    {
        super(p_i580_1_);
        this.aI = p_i580_2_;
        this.aJ = p_i580_3_;
        this.b();
        this.a(2.0F, 0.0F);
        this.au.clear();
        this.ak = Blocks.SAND.getBlockData().set(BlockSand.VARIANT, BlockSand.EnumSandVariant.RED_SAND);
        this.al = Blocks.STAINED_HARDENED_CLAY.getBlockData();
        this.as.A = -999;
        this.as.D = 20;
        this.as.F = 3;
        this.as.G = 5;
        this.as.B = 0;
        this.au.clear();

        if (p_i580_3_)
        {
            this.as.A = 5;
        }
    }

    public WorldGenTreeAbstract a(Random p_a_1_)
    {
        return this.aA;
    }

    public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_)
    {
        super.a(p_a_1_, p_a_2_, p_a_3_);
    }

    public void a(World p_a_1_, Random p_a_2_, ChunkSnapshot p_a_3_, int p_a_4_, int p_a_5_, double p_a_6_)
    {
        if (this.aD == null || this.aE != p_a_1_.getSeed())
        {
            this.a(p_a_1_.getSeed());
        }

        if (this.aF == null || this.aG == null || this.aE != p_a_1_.getSeed())
        {
            Random random = new Random(this.aE);
            this.aF = new NoiseGenerator3(random, 4);
            this.aG = new NoiseGenerator3(random, 1);
        }

        this.aE = p_a_1_.getSeed();
        double d0 = 0.0D;

        if (this.aI)
        {
            int i = (p_a_4_ & -16) + (p_a_5_ & 15);
            int j = (p_a_5_ & -16) + (p_a_4_ & 15);
            double d1 = Math.min(Math.abs(p_a_6_), this.aF.a((double)i * 0.25D, (double)j * 0.25D));

            if (d1 > 0.0D)
            {
                double d2 = 0.001953125D;
                double d3 = Math.abs(this.aG.a((double)i * d2, (double)j * d2));
                d0 = d1 * d1 * 2.5D;
                double d4 = Math.ceil(d3 * 50.0D) + 14.0D;

                if (d0 > d4)
                {
                    d0 = d4;
                }

                d0 = d0 + 64.0D;
            }
        }

        int k1 = p_a_4_ & 15;
        int l1 = p_a_5_ & 15;
        int k = p_a_1_.F();
        IBlockData iblockdata = Blocks.STAINED_HARDENED_CLAY.getBlockData();
        IBlockData iblockdata1 = this.al;
        int l = (int)(p_a_6_ / 3.0D + 3.0D + p_a_2_.nextDouble() * 0.25D);
        boolean flag = Math.cos(p_a_6_ / 3.0D * Math.PI) > 0.0D;
        int i1 = -1;
        boolean flag1 = false;

        for (int j1 = 255; j1 >= 0; --j1)
        {
            if (p_a_3_.a(l1, j1, k1).getBlock().getMaterial() == Material.AIR && j1 < (int)d0)
            {
                p_a_3_.a(l1, j1, k1, Blocks.STONE.getBlockData());
            }

            if (j1 <= p_a_2_.nextInt(5))
            {
                p_a_3_.a(l1, j1, k1, Blocks.BEDROCK.getBlockData());
            }
            else
            {
                IBlockData iblockdata2 = p_a_3_.a(l1, j1, k1);

                if (iblockdata2.getBlock().getMaterial() == Material.AIR)
                {
                    i1 = -1;
                }
                else if (iblockdata2.getBlock() == Blocks.STONE)
                {
                    if (i1 == -1)
                    {
                        flag1 = false;

                        if (l <= 0)
                        {
                            iblockdata = null;
                            iblockdata1 = Blocks.STONE.getBlockData();
                        }
                        else if (j1 >= k - 4 && j1 <= k + 1)
                        {
                            iblockdata = Blocks.STAINED_HARDENED_CLAY.getBlockData();
                            iblockdata1 = this.al;
                        }

                        if (j1 < k && (iblockdata == null || iblockdata.getBlock().getMaterial() == Material.AIR))
                        {
                            iblockdata = Blocks.WATER.getBlockData();
                        }

                        i1 = l + Math.max(0, j1 - k);

                        if (j1 < k - 1)
                        {
                            p_a_3_.a(l1, j1, k1, iblockdata1);

                            if (iblockdata1.getBlock() == Blocks.STAINED_HARDENED_CLAY)
                            {
                                p_a_3_.a(l1, j1, k1, iblockdata1.getBlock().getBlockData().set(BlockCloth.COLOR, EnumColor.ORANGE));
                            }
                        }
                        else if (this.aJ && j1 > 86 + l * 2)
                        {
                            if (flag)
                            {
                                p_a_3_.a(l1, j1, k1, Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.COARSE_DIRT));
                            }
                            else
                            {
                                p_a_3_.a(l1, j1, k1, Blocks.GRASS.getBlockData());
                            }
                        }
                        else if (j1 <= k + 3 + l)
                        {
                            p_a_3_.a(l1, j1, k1, this.ak);
                            flag1 = true;
                        }
                        else
                        {
                            IBlockData iblockdata4;

                            if (j1 >= 64 && j1 <= 127)
                            {
                                if (flag)
                                {
                                    iblockdata4 = Blocks.HARDENED_CLAY.getBlockData();
                                }
                                else
                                {
                                    iblockdata4 = this.a(p_a_4_, j1, p_a_5_);
                                }
                            }
                            else
                            {
                                iblockdata4 = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.ORANGE);
                            }

                            p_a_3_.a(l1, j1, k1, iblockdata4);
                        }
                    }
                    else if (i1 > 0)
                    {
                        --i1;

                        if (flag1)
                        {
                            p_a_3_.a(l1, j1, k1, Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.ORANGE));
                        }
                        else
                        {
                            IBlockData iblockdata3 = this.a(p_a_4_, j1, p_a_5_);
                            p_a_3_.a(l1, j1, k1, iblockdata3);
                        }
                    }
                }
            }
        }
    }

    private void a(long p_a_1_)
    {
        this.aD = new IBlockData[64];
        Arrays.fill(this.aD, Blocks.HARDENED_CLAY.getBlockData());
        Random random = new Random(p_a_1_);
        this.aH = new NoiseGenerator3(random, 1);

        for (int l1 = 0; l1 < 64; ++l1)
        {
            l1 += random.nextInt(5) + 1;

            if (l1 < 64)
            {
                this.aD[l1] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.ORANGE);
            }
        }

        int i2 = random.nextInt(4) + 2;

        for (int i = 0; i < i2; ++i)
        {
            int j = random.nextInt(3) + 1;
            int k = random.nextInt(64);

            for (int l = 0; k + l < 64 && l < j; ++l)
            {
                this.aD[k + l] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.YELLOW);
            }
        }

        int j2 = random.nextInt(4) + 2;

        for (int k2 = 0; k2 < j2; ++k2)
        {
            int i3 = random.nextInt(3) + 2;
            int l3 = random.nextInt(64);

            for (int i1 = 0; l3 + i1 < 64 && i1 < i3; ++i1)
            {
                this.aD[l3 + i1] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.BROWN);
            }
        }

        int l2 = random.nextInt(4) + 2;

        for (int j3 = 0; j3 < l2; ++j3)
        {
            int i4 = random.nextInt(3) + 1;
            int k4 = random.nextInt(64);

            for (int j1 = 0; k4 + j1 < 64 && j1 < i4; ++j1)
            {
                this.aD[k4 + j1] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.RED);
            }
        }

        int k3 = random.nextInt(3) + 3;
        int j4 = 0;

        for (int l4 = 0; l4 < k3; ++l4)
        {
            byte b0 = 1;
            j4 += random.nextInt(16) + 4;

            for (int k1 = 0; j4 + k1 < 64 && k1 < b0; ++k1)
            {
                this.aD[j4 + k1] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.WHITE);

                if (j4 + k1 > 1 && random.nextBoolean())
                {
                    this.aD[j4 + k1 - 1] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.SILVER);
                }

                if (j4 + k1 < 63 && random.nextBoolean())
                {
                    this.aD[j4 + k1 + 1] = Blocks.STAINED_HARDENED_CLAY.getBlockData().set(BlockCloth.COLOR, EnumColor.SILVER);
                }
            }
        }
    }

    private IBlockData a(int p_a_1_, int p_a_2_, int p_a_3_)
    {
        int i = (int)Math.round(this.aH.a((double)p_a_1_ * 1.0D / 512.0D, (double)p_a_1_ * 1.0D / 512.0D) * 2.0D);
        return this.aD[(p_a_2_ + i + 64) % 64];
    }

    protected BiomeBase d(int p_d_1_)
    {
        boolean flag = this.id == BiomeBase.MESA.id;
        BiomeMesa biomemesa = new BiomeMesa(p_d_1_, flag, this.aJ);

        if (!flag)
        {
            biomemesa.a(g);
            biomemesa.a(this.ah + " M");
        }
        else
        {
            biomemesa.a(this.ah + " (Bryce)");
        }

        biomemesa.a(this.ai, true);
        return biomemesa;
    }
}
