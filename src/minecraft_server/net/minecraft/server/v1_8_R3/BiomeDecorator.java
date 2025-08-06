package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BiomeDecorator
{
    protected World a;
    protected Random b;
    protected BlockPosition c;
    protected CustomWorldSettingsFinal d;
    protected WorldGenerator e = new WorldGenClay(4);
    protected WorldGenerator f = new WorldGenSand(Blocks.SAND, 7);
    protected WorldGenerator g = new WorldGenSand(Blocks.GRAVEL, 6);
    protected WorldGenerator h;
    protected WorldGenerator i;
    protected WorldGenerator j;
    protected WorldGenerator k;
    protected WorldGenerator l;
    protected WorldGenerator m;
    protected WorldGenerator n;
    protected WorldGenerator o;
    protected WorldGenerator p;
    protected WorldGenerator q;
    protected WorldGenerator r;
    protected WorldGenFlowers s = new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlowers.EnumFlowerVarient.DANDELION);
    protected WorldGenerator t = new WorldGenMushrooms(Blocks.BROWN_MUSHROOM);
    protected WorldGenerator u = new WorldGenMushrooms(Blocks.RED_MUSHROOM);
    protected WorldGenerator v = new WorldGenHugeMushroom();
    protected WorldGenerator w = new WorldGenReed();
    protected WorldGenerator x = new WorldGenCactus();
    protected WorldGenerator y = new WorldGenWaterLily();
    protected int z;
    protected int A;
    protected int B = 2;
    protected int C = 1;
    protected int D;
    protected int E;
    protected int F;
    protected int G;
    protected int H = 1;
    protected int I = 3;
    protected int J = 1;
    protected int K;
    public boolean L = true;

    public void a(World p_a_1_, Random p_a_2_, BiomeBase p_a_3_, BlockPosition p_a_4_)
    {
        if (this.a != null)
        {
            throw new RuntimeException("Already decorating");
        }
        else
        {
            this.a = p_a_1_;
            String sx = p_a_1_.getWorldData().getGeneratorOptions();

            if (sx != null)
            {
                this.d = CustomWorldSettingsFinal.CustomWorldSettings.a(sx).b();
            }
            else
            {
                this.d = CustomWorldSettingsFinal.CustomWorldSettings.a("").b();
            }

            this.b = p_a_2_;
            this.c = p_a_4_;
            this.h = new WorldGenMinable(Blocks.DIRT.getBlockData(), this.d.I);
            this.i = new WorldGenMinable(Blocks.GRAVEL.getBlockData(), this.d.M);
            this.j = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.GRANITE), this.d.Q);
            this.k = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.DIORITE), this.d.U);
            this.l = new WorldGenMinable(Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.ANDESITE), this.d.Y);
            this.m = new WorldGenMinable(Blocks.COAL_ORE.getBlockData(), this.d.ac);
            this.n = new WorldGenMinable(Blocks.IRON_ORE.getBlockData(), this.d.ag);
            this.o = new WorldGenMinable(Blocks.GOLD_ORE.getBlockData(), this.d.ak);
            this.p = new WorldGenMinable(Blocks.REDSTONE_ORE.getBlockData(), this.d.ao);
            this.q = new WorldGenMinable(Blocks.DIAMOND_ORE.getBlockData(), this.d.as);
            this.r = new WorldGenMinable(Blocks.LAPIS_ORE.getBlockData(), this.d.aw);
            this.a(p_a_3_);
            this.a = null;
            this.b = null;
        }
    }

    protected void a(BiomeBase p_a_1_)
    {
        this.a();

        for (int ix = 0; ix < this.I; ++ix)
        {
            int jx = this.b.nextInt(16) + 8;
            int kx = this.b.nextInt(16) + 8;
            this.f.generate(this.a, this.b, this.a.r(this.c.a(jx, 0, kx)));
        }

        for (int i1 = 0; i1 < this.J; ++i1)
        {
            int l1 = this.b.nextInt(16) + 8;
            int i6 = this.b.nextInt(16) + 8;
            this.e.generate(this.a, this.b, this.a.r(this.c.a(l1, 0, i6)));
        }

        for (int j1 = 0; j1 < this.H; ++j1)
        {
            int i2 = this.b.nextInt(16) + 8;
            int j6 = this.b.nextInt(16) + 8;
            this.g.generate(this.a, this.b, this.a.r(this.c.a(i2, 0, j6)));
        }

        int k1 = this.A;

        if (this.b.nextInt(10) == 0)
        {
            ++k1;
        }

        for (int j2 = 0; j2 < k1; ++j2)
        {
            int k6 = this.b.nextInt(16) + 8;
            int lx = this.b.nextInt(16) + 8;
            WorldGenTreeAbstract worldgentreeabstract = p_a_1_.a(this.b);
            worldgentreeabstract.e();
            BlockPosition blockposition = this.a.getHighestBlockYAt(this.c.a(k6, 0, lx));

            if (worldgentreeabstract.generate(this.a, this.b, blockposition))
            {
                worldgentreeabstract.a(this.a, this.b, blockposition);
            }
        }

        for (int k2 = 0; k2 < this.K; ++k2)
        {
            int l6 = this.b.nextInt(16) + 8;
            int k10 = this.b.nextInt(16) + 8;
            this.v.generate(this.a, this.b, this.a.getHighestBlockYAt(this.c.a(l6, 0, k10)));
        }

        for (int l2 = 0; l2 < this.B; ++l2)
        {
            int i7 = this.b.nextInt(16) + 8;
            int l10 = this.b.nextInt(16) + 8;
            int j14 = this.a.getHighestBlockYAt(this.c.a(i7, 0, l10)).getY() + 32;

            if (j14 > 0)
            {
                int k17 = this.b.nextInt(j14);
                BlockPosition blockposition1 = this.c.a(i7, k17, l10);
                BlockFlowers.EnumFlowerVarient blockflowers$enumflowervarient = p_a_1_.a(this.b, blockposition1);
                BlockFlowers blockflowers = blockflowers$enumflowervarient.a().a();

                if (blockflowers.getMaterial() != Material.AIR)
                {
                    this.s.a(blockflowers, blockflowers$enumflowervarient);
                    this.s.generate(this.a, this.b, blockposition1);
                }
            }
        }

        for (int i3 = 0; i3 < this.C; ++i3)
        {
            int j7 = this.b.nextInt(16) + 8;
            int i11 = this.b.nextInt(16) + 8;
            int k14 = this.a.getHighestBlockYAt(this.c.a(j7, 0, i11)).getY() * 2;

            if (k14 > 0)
            {
                int l17 = this.b.nextInt(k14);
                p_a_1_.b(this.b).generate(this.a, this.b, this.c.a(j7, l17, i11));
            }
        }

        for (int j3 = 0; j3 < this.D; ++j3)
        {
            int k7 = this.b.nextInt(16) + 8;
            int j11 = this.b.nextInt(16) + 8;
            int l14 = this.a.getHighestBlockYAt(this.c.a(k7, 0, j11)).getY() * 2;

            if (l14 > 0)
            {
                int i18 = this.b.nextInt(l14);
                (new WorldGenDeadBush()).generate(this.a, this.b, this.c.a(k7, i18, j11));
            }
        }

        for (int k3 = 0; k3 < this.z; ++k3)
        {
            int l7 = this.b.nextInt(16) + 8;
            int k11 = this.b.nextInt(16) + 8;
            int i15 = this.a.getHighestBlockYAt(this.c.a(l7, 0, k11)).getY() * 2;

            if (i15 > 0)
            {
                int j18 = this.b.nextInt(i15);
                BlockPosition blockposition4;
                BlockPosition blockposition7;

                for (blockposition4 = this.c.a(l7, j18, k11); blockposition4.getY() > 0; blockposition4 = blockposition7)
                {
                    blockposition7 = blockposition4.down();

                    if (!this.a.isEmpty(blockposition7))
                    {
                        break;
                    }
                }

                this.y.generate(this.a, this.b, blockposition4);
            }
        }

        for (int l3 = 0; l3 < this.E; ++l3)
        {
            if (this.b.nextInt(4) == 0)
            {
                int i8 = this.b.nextInt(16) + 8;
                int l11 = this.b.nextInt(16) + 8;
                BlockPosition blockposition2 = this.a.getHighestBlockYAt(this.c.a(i8, 0, l11));
                this.t.generate(this.a, this.b, blockposition2);
            }

            if (this.b.nextInt(8) == 0)
            {
                int j8 = this.b.nextInt(16) + 8;
                int i12 = this.b.nextInt(16) + 8;
                int j15 = this.a.getHighestBlockYAt(this.c.a(j8, 0, i12)).getY() * 2;

                if (j15 > 0)
                {
                    int k18 = this.b.nextInt(j15);
                    BlockPosition blockposition5 = this.c.a(j8, k18, i12);
                    this.u.generate(this.a, this.b, blockposition5);
                }
            }
        }

        if (this.b.nextInt(4) == 0)
        {
            int i4 = this.b.nextInt(16) + 8;
            int k8 = this.b.nextInt(16) + 8;
            int j12 = this.a.getHighestBlockYAt(this.c.a(i4, 0, k8)).getY() * 2;

            if (j12 > 0)
            {
                int k15 = this.b.nextInt(j12);
                this.t.generate(this.a, this.b, this.c.a(i4, k15, k8));
            }
        }

        if (this.b.nextInt(8) == 0)
        {
            int j4 = this.b.nextInt(16) + 8;
            int l8 = this.b.nextInt(16) + 8;
            int k12 = this.a.getHighestBlockYAt(this.c.a(j4, 0, l8)).getY() * 2;

            if (k12 > 0)
            {
                int l15 = this.b.nextInt(k12);
                this.u.generate(this.a, this.b, this.c.a(j4, l15, l8));
            }
        }

        for (int k4 = 0; k4 < this.F; ++k4)
        {
            int i9 = this.b.nextInt(16) + 8;
            int l12 = this.b.nextInt(16) + 8;
            int i16 = this.a.getHighestBlockYAt(this.c.a(i9, 0, l12)).getY() * 2;

            if (i16 > 0)
            {
                int l18 = this.b.nextInt(i16);
                this.w.generate(this.a, this.b, this.c.a(i9, l18, l12));
            }
        }

        for (int l4 = 0; l4 < 10; ++l4)
        {
            int j9 = this.b.nextInt(16) + 8;
            int i13 = this.b.nextInt(16) + 8;
            int j16 = this.a.getHighestBlockYAt(this.c.a(j9, 0, i13)).getY() * 2;

            if (j16 > 0)
            {
                int i19 = this.b.nextInt(j16);
                this.w.generate(this.a, this.b, this.c.a(j9, i19, i13));
            }
        }

        if (this.b.nextInt(32) == 0)
        {
            int i5 = this.b.nextInt(16) + 8;
            int k9 = this.b.nextInt(16) + 8;
            int j13 = this.a.getHighestBlockYAt(this.c.a(i5, 0, k9)).getY() * 2;

            if (j13 > 0)
            {
                int k16 = this.b.nextInt(j13);
                (new WorldGenPumpkin()).generate(this.a, this.b, this.c.a(i5, k16, k9));
            }
        }

        for (int j5 = 0; j5 < this.G; ++j5)
        {
            int l9 = this.b.nextInt(16) + 8;
            int k13 = this.b.nextInt(16) + 8;
            int l16 = this.a.getHighestBlockYAt(this.c.a(l9, 0, k13)).getY() * 2;

            if (l16 > 0)
            {
                int j19 = this.b.nextInt(l16);
                this.x.generate(this.a, this.b, this.c.a(l9, j19, k13));
            }
        }

        if (this.L)
        {
            for (int k5 = 0; k5 < 50; ++k5)
            {
                int i10 = this.b.nextInt(16) + 8;
                int l13 = this.b.nextInt(16) + 8;
                int i17 = this.b.nextInt(248) + 8;

                if (i17 > 0)
                {
                    int k19 = this.b.nextInt(i17);
                    BlockPosition blockposition6 = this.c.a(i10, k19, l13);
                    (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(this.a, this.b, blockposition6);
                }
            }

            for (int l5 = 0; l5 < 20; ++l5)
            {
                int j10 = this.b.nextInt(16) + 8;
                int i14 = this.b.nextInt(16) + 8;
                int j17 = this.b.nextInt(this.b.nextInt(this.b.nextInt(240) + 8) + 8);
                BlockPosition blockposition3 = this.c.a(j10, j17, i14);
                (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(this.a, this.b, blockposition3);
            }
        }
    }

    protected void a(int p_a_1_, WorldGenerator p_a_2_, int p_a_3_, int p_a_4_)
    {
        if (p_a_4_ < p_a_3_)
        {
            int ix = p_a_3_;
            p_a_3_ = p_a_4_;
            p_a_4_ = ix;
        }
        else if (p_a_4_ == p_a_3_)
        {
            if (p_a_3_ < 255)
            {
                ++p_a_4_;
            }
            else
            {
                --p_a_3_;
            }
        }

        for (int jx = 0; jx < p_a_1_; ++jx)
        {
            BlockPosition blockposition = this.c.a(this.b.nextInt(16), this.b.nextInt(p_a_4_ - p_a_3_) + p_a_3_, this.b.nextInt(16));
            p_a_2_.generate(this.a, this.b, blockposition);
        }
    }

    protected void b(int p_b_1_, WorldGenerator p_b_2_, int p_b_3_, int p_b_4_)
    {
        for (int ix = 0; ix < p_b_1_; ++ix)
        {
            BlockPosition blockposition = this.c.a(this.b.nextInt(16), this.b.nextInt(p_b_4_) + this.b.nextInt(p_b_4_) + p_b_3_ - p_b_4_, this.b.nextInt(16));
            p_b_2_.generate(this.a, this.b, blockposition);
        }
    }

    protected void a()
    {
        this.a(this.d.J, this.h, this.d.K, this.d.L);
        this.a(this.d.N, this.i, this.d.O, this.d.P);
        this.a(this.d.V, this.k, this.d.W, this.d.X);
        this.a(this.d.R, this.j, this.d.S, this.d.T);
        this.a(this.d.Z, this.l, this.d.aa, this.d.ab);
        this.a(this.d.ad, this.m, this.d.ae, this.d.af);
        this.a(this.d.ah, this.n, this.d.ai, this.d.aj);
        this.a(this.d.al, this.o, this.d.am, this.d.an);
        this.a(this.d.ap, this.p, this.d.aq, this.d.ar);
        this.a(this.d.at, this.q, this.d.au, this.d.av);
        this.b(this.d.ax, this.r, this.d.ay, this.d.az);
    }
}
