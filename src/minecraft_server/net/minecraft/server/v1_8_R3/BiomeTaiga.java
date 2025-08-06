package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BiomeTaiga extends BiomeBase
{
    private static final WorldGenTaiga1 aD = new WorldGenTaiga1();
    private static final WorldGenTaiga2 aE = new WorldGenTaiga2(false);
    private static final WorldGenMegaTree aF = new WorldGenMegaTree(false, false);
    private static final WorldGenMegaTree aG = new WorldGenMegaTree(false, true);
    private static final WorldGenTaigaStructure aH = new WorldGenTaigaStructure(Blocks.MOSSY_COBBLESTONE, 0);
    private int aI;

    public BiomeTaiga(int p_i590_1_, int p_i590_2_)
    {
        super(p_i590_1_);
        this.aI = p_i590_2_;
        this.au.add(new BiomeBase.BiomeMeta(EntityWolf.class, 8, 4, 4));
        this.as.A = 10;

        if (p_i590_2_ != 1 && p_i590_2_ != 2)
        {
            this.as.C = 1;
            this.as.E = 1;
        }
        else
        {
            this.as.C = 7;
            this.as.D = 1;
            this.as.E = 3;
        }
    }

    public WorldGenTreeAbstract a(Random p_a_1_)
    {
        return (WorldGenTreeAbstract)((this.aI == 1 || this.aI == 2) && p_a_1_.nextInt(3) == 0 ? (this.aI != 2 && p_a_1_.nextInt(13) != 0 ? aF : aG) : (p_a_1_.nextInt(3) == 0 ? aD : aE));
    }

    public WorldGenerator b(Random p_b_1_)
    {
        return p_b_1_.nextInt(5) > 0 ? new WorldGenGrass(BlockLongGrass.EnumTallGrassType.FERN) : new WorldGenGrass(BlockLongGrass.EnumTallGrassType.GRASS);
    }

    public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_)
    {
        if (this.aI == 1 || this.aI == 2)
        {
            int i = p_a_2_.nextInt(3);

            for (int j = 0; j < i; ++j)
            {
                int k = p_a_2_.nextInt(16) + 8;
                int l = p_a_2_.nextInt(16) + 8;
                BlockPosition blockposition = p_a_1_.getHighestBlockYAt(p_a_3_.a(k, 0, l));
                aH.generate(p_a_1_, p_a_2_, blockposition);
            }
        }

        ag.a(BlockTallPlant.EnumTallFlowerVariants.FERN);

        for (int i1 = 0; i1 < 7; ++i1)
        {
            int j1 = p_a_2_.nextInt(16) + 8;
            int k1 = p_a_2_.nextInt(16) + 8;
            int l1 = p_a_2_.nextInt(p_a_1_.getHighestBlockYAt(p_a_3_.a(j1, 0, k1)).getY() + 32);
            ag.generate(p_a_1_, p_a_2_, p_a_3_.a(j1, l1, k1));
        }

        super.a(p_a_1_, p_a_2_, p_a_3_);
    }

    public void a(World p_a_1_, Random p_a_2_, ChunkSnapshot p_a_3_, int p_a_4_, int p_a_5_, double p_a_6_)
    {
        if (this.aI == 1 || this.aI == 2)
        {
            this.ak = Blocks.GRASS.getBlockData();
            this.al = Blocks.DIRT.getBlockData();

            if (p_a_6_ > 1.75D)
            {
                this.ak = Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.COARSE_DIRT);
            }
            else if (p_a_6_ > -0.95D)
            {
                this.ak = Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.PODZOL);
            }
        }

        this.b(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
    }

    protected BiomeBase d(int p_d_1_)
    {
        return this.id == BiomeBase.MEGA_TAIGA.id ? (new BiomeTaiga(p_d_1_, 2)).a(5858897, true).a("Mega Spruce Taiga").a(5159473).a(0.25F, 0.8F).a(new BiomeBase.BiomeTemperature(this.an, this.ao)) : super.d(p_d_1_);
    }
}
