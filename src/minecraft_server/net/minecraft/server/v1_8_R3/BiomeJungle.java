package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BiomeJungle extends BiomeBase
{
    private boolean aD;
    private static final IBlockData aE = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.JUNGLE);
    private static final IBlockData aF = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.JUNGLE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private static final IBlockData aG = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.OAK).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    public BiomeJungle(int p_i579_1_, boolean p_i579_2_)
    {
        super(p_i579_1_);
        this.aD = p_i579_2_;

        if (p_i579_2_)
        {
            this.as.A = 2;
        }
        else
        {
            this.as.A = 50;
        }

        this.as.C = 25;
        this.as.B = 4;

        if (!p_i579_2_)
        {
            this.at.add(new BiomeBase.BiomeMeta(EntityOcelot.class, 2, 1, 1));
        }

        this.au.add(new BiomeBase.BiomeMeta(EntityChicken.class, 10, 4, 4));
    }

    public WorldGenTreeAbstract a(Random p_a_1_)
    {
        return (WorldGenTreeAbstract)(p_a_1_.nextInt(10) == 0 ? this.aB : (p_a_1_.nextInt(2) == 0 ? new WorldGenGroundBush(aE, aG) : (!this.aD && p_a_1_.nextInt(3) == 0 ? new WorldGenJungleTree(false, 10, 20, aE, aF) : new WorldGenTrees(false, 4 + p_a_1_.nextInt(7), aE, aF, true))));
    }

    public WorldGenerator b(Random p_b_1_)
    {
        return p_b_1_.nextInt(4) == 0 ? new WorldGenGrass(BlockLongGrass.EnumTallGrassType.FERN) : new WorldGenGrass(BlockLongGrass.EnumTallGrassType.GRASS);
    }

    public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_)
    {
        super.a(p_a_1_, p_a_2_, p_a_3_);
        int i = p_a_2_.nextInt(16) + 8;
        int j = p_a_2_.nextInt(16) + 8;
        int k = p_a_2_.nextInt(p_a_1_.getHighestBlockYAt(p_a_3_.a(i, 0, j)).getY() * 2);
        (new WorldGenMelon()).generate(p_a_1_, p_a_2_, p_a_3_.a(i, k, j));
        WorldGenVines worldgenvines = new WorldGenVines();

        for (j = 0; j < 50; ++j)
        {
            k = p_a_2_.nextInt(16) + 8;
            boolean flag = true;
            int l = p_a_2_.nextInt(16) + 8;
            worldgenvines.generate(p_a_1_, p_a_2_, p_a_3_.a(k, 128, l));
        }
    }
}
