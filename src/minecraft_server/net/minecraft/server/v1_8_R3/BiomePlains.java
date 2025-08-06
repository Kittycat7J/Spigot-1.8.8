package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BiomePlains extends BiomeBase
{
    protected boolean aD;

    protected BiomePlains(int p_i584_1_)
    {
        super(p_i584_1_);
        this.a(0.8F, 0.4F);
        this.a(e);
        this.au.add(new BiomeBase.BiomeMeta(EntityHorse.class, 5, 2, 6));
        this.as.A = -999;
        this.as.B = 4;
        this.as.C = 10;
    }

    public BlockFlowers.EnumFlowerVarient a(Random p_a_1_, BlockPosition p_a_2_)
    {
        double d0 = af.a((double)p_a_2_.getX() / 200.0D, (double)p_a_2_.getZ() / 200.0D);

        if (d0 < -0.8D)
        {
            int j = p_a_1_.nextInt(4);

            switch (j)
            {
                case 0:
                    return BlockFlowers.EnumFlowerVarient.ORANGE_TULIP;

                case 1:
                    return BlockFlowers.EnumFlowerVarient.RED_TULIP;

                case 2:
                    return BlockFlowers.EnumFlowerVarient.PINK_TULIP;

                case 3:
                default:
                    return BlockFlowers.EnumFlowerVarient.WHITE_TULIP;
            }
        }
        else if (p_a_1_.nextInt(3) > 0)
        {
            int i = p_a_1_.nextInt(3);
            return i == 0 ? BlockFlowers.EnumFlowerVarient.POPPY : (i == 1 ? BlockFlowers.EnumFlowerVarient.HOUSTONIA : BlockFlowers.EnumFlowerVarient.OXEYE_DAISY);
        }
        else
        {
            return BlockFlowers.EnumFlowerVarient.DANDELION;
        }
    }

    public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_)
    {
        double d0 = af.a((double)(p_a_3_.getX() + 8) / 200.0D, (double)(p_a_3_.getZ() + 8) / 200.0D);

        if (d0 < -0.8D)
        {
            this.as.B = 15;
            this.as.C = 5;
        }
        else
        {
            this.as.B = 4;
            this.as.C = 10;
            ag.a(BlockTallPlant.EnumTallFlowerVariants.GRASS);

            for (int i = 0; i < 7; ++i)
            {
                int j = p_a_2_.nextInt(16) + 8;
                int k = p_a_2_.nextInt(16) + 8;
                int l = p_a_2_.nextInt(p_a_1_.getHighestBlockYAt(p_a_3_.a(j, 0, k)).getY() + 32);
                ag.generate(p_a_1_, p_a_2_, p_a_3_.a(j, l, k));
            }
        }

        if (this.aD)
        {
            ag.a(BlockTallPlant.EnumTallFlowerVariants.SUNFLOWER);

            for (int i1 = 0; i1 < 10; ++i1)
            {
                int j1 = p_a_2_.nextInt(16) + 8;
                int k1 = p_a_2_.nextInt(16) + 8;
                int l1 = p_a_2_.nextInt(p_a_1_.getHighestBlockYAt(p_a_3_.a(j1, 0, k1)).getY() + 32);
                ag.generate(p_a_1_, p_a_2_, p_a_3_.a(j1, l1, k1));
            }
        }

        super.a(p_a_1_, p_a_2_, p_a_3_);
    }

    protected BiomeBase d(int p_d_1_)
    {
        BiomePlains biomeplains = new BiomePlains(p_d_1_);
        biomeplains.a("Sunflower Plains");
        biomeplains.aD = true;
        biomeplains.b(9286496);
        biomeplains.aj = 14273354;
        return biomeplains;
    }
}
