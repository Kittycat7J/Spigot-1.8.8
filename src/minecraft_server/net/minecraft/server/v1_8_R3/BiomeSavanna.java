package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BiomeSavanna extends BiomeBase
{
    private static final WorldGenAcaciaTree aD = new WorldGenAcaciaTree(false);

    protected BiomeSavanna(int p_i587_1_)
    {
        super(p_i587_1_);
        this.au.add(new BiomeBase.BiomeMeta(EntityHorse.class, 1, 2, 6));
        this.as.A = 1;
        this.as.B = 4;
        this.as.C = 20;
    }

    public WorldGenTreeAbstract a(Random p_a_1_)
    {
        return (WorldGenTreeAbstract)(p_a_1_.nextInt(5) > 0 ? aD : this.aA);
    }

    protected BiomeBase d(int p_d_1_)
    {
        BiomeSavanna.BiomeSavannaSub biomesavanna$biomesavannasub = new BiomeSavanna.BiomeSavannaSub(p_d_1_, this);
        biomesavanna$biomesavannasub.temperature = (this.temperature + 1.0F) * 0.5F;
        biomesavanna$biomesavannasub.an = this.an * 0.5F + 0.3F;
        biomesavanna$biomesavannasub.ao = this.ao * 0.5F + 1.2F;
        return biomesavanna$biomesavannasub;
    }

    public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_)
    {
        ag.a(BlockTallPlant.EnumTallFlowerVariants.GRASS);

        for (int i = 0; i < 7; ++i)
        {
            int j = p_a_2_.nextInt(16) + 8;
            int k = p_a_2_.nextInt(16) + 8;
            int l = p_a_2_.nextInt(p_a_1_.getHighestBlockYAt(p_a_3_.a(j, 0, k)).getY() + 32);
            ag.generate(p_a_1_, p_a_2_, p_a_3_.a(j, l, k));
        }

        super.a(p_a_1_, p_a_2_, p_a_3_);
    }

    public static class BiomeSavannaSub extends BiomeBaseSub
    {
        public BiomeSavannaSub(int p_i586_1_, BiomeBase p_i586_2_)
        {
            super(p_i586_1_, p_i586_2_);
            this.as.A = 2;
            this.as.B = 2;
            this.as.C = 5;
        }

        public void a(World p_a_1_, Random p_a_2_, ChunkSnapshot p_a_3_, int p_a_4_, int p_a_5_, double p_a_6_)
        {
            this.ak = Blocks.GRASS.getBlockData();
            this.al = Blocks.DIRT.getBlockData();

            if (p_a_6_ > 1.75D)
            {
                this.ak = Blocks.STONE.getBlockData();
                this.al = Blocks.STONE.getBlockData();
            }
            else if (p_a_6_ > -0.5D)
            {
                this.ak = Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.COARSE_DIRT);
            }

            this.b(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        }

        public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_)
        {
            this.as.a(p_a_1_, p_a_2_, this, p_a_3_);
        }
    }
}
