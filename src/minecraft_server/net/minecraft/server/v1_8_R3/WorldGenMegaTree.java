package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class WorldGenMegaTree extends WorldGenMegaTreeAbstract
{
    private static final IBlockData e = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.SPRUCE);
    private static final IBlockData f = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.SPRUCE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private static final IBlockData g = Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.PODZOL);
    private boolean h;

    public WorldGenMegaTree(boolean p_i703_1_, boolean p_i703_2_)
    {
        super(p_i703_1_, 13, 15, e, f);
        this.h = p_i703_2_;
    }

    public boolean generate(World p_generate_1_, Random p_generate_2_, BlockPosition p_generate_3_)
    {
        int i = this.a(p_generate_2_);

        if (!this.a(p_generate_1_, p_generate_2_, p_generate_3_, i))
        {
            return false;
        }
        else
        {
            this.a(p_generate_1_, p_generate_3_.getX(), p_generate_3_.getZ(), p_generate_3_.getY() + i, 0, p_generate_2_);

            for (int j = 0; j < i; ++j)
            {
                Block block = p_generate_1_.getType(p_generate_3_.up(j)).getBlock();

                if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES)
                {
                    this.a(p_generate_1_, p_generate_3_.up(j), this.b);
                }

                if (j < i - 1)
                {
                    block = p_generate_1_.getType(p_generate_3_.a(1, j, 0)).getBlock();

                    if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES)
                    {
                        this.a(p_generate_1_, p_generate_3_.a(1, j, 0), this.b);
                    }

                    block = p_generate_1_.getType(p_generate_3_.a(1, j, 1)).getBlock();

                    if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES)
                    {
                        this.a(p_generate_1_, p_generate_3_.a(1, j, 1), this.b);
                    }

                    block = p_generate_1_.getType(p_generate_3_.a(0, j, 1)).getBlock();

                    if (block.getMaterial() == Material.AIR || block.getMaterial() == Material.LEAVES)
                    {
                        this.a(p_generate_1_, p_generate_3_.a(0, j, 1), this.b);
                    }
                }
            }

            return true;
        }
    }

    private void a(World p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_, int p_a_5_, Random p_a_6_)
    {
        int i = p_a_6_.nextInt(5) + (this.h ? this.a : 3);
        int j = 0;

        for (int k = p_a_4_ - i; k <= p_a_4_; ++k)
        {
            int l = p_a_4_ - k;
            int i1 = p_a_5_ + MathHelper.d((float)l / (float)i * 3.5F);
            this.a(p_a_1_, new BlockPosition(p_a_2_, k, p_a_3_), i1 + (l > 0 && i1 == j && (k & 1) == 0 ? 1 : 0));
            j = i1;
        }
    }

    public void a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_)
    {
        this.b(p_a_1_, p_a_3_.west().north());
        this.b(p_a_1_, p_a_3_.east(2).north());
        this.b(p_a_1_, p_a_3_.west().south(2));
        this.b(p_a_1_, p_a_3_.east(2).south(2));

        for (int i = 0; i < 5; ++i)
        {
            int j = p_a_2_.nextInt(64);
            int k = j % 8;
            int l = j / 8;

            if (k == 0 || k == 7 || l == 0 || l == 7)
            {
                this.b(p_a_1_, p_a_3_.a(-3 + k, 0, -3 + l));
            }
        }
    }

    private void b(World p_b_1_, BlockPosition p_b_2_)
    {
        for (int i = -2; i <= 2; ++i)
        {
            for (int j = -2; j <= 2; ++j)
            {
                if (Math.abs(i) != 2 || Math.abs(j) != 2)
                {
                    this.c(p_b_1_, p_b_2_.a(i, 0, j));
                }
            }
        }
    }

    private void c(World p_c_1_, BlockPosition p_c_2_)
    {
        for (int i = 2; i >= -3; --i)
        {
            BlockPosition blockposition = p_c_2_.up(i);
            Block block = p_c_1_.getType(blockposition).getBlock();

            if (block == Blocks.GRASS || block == Blocks.DIRT)
            {
                this.a(p_c_1_, blockposition, g);
                break;
            }

            if (block.getMaterial() != Material.AIR && i < 0)
            {
                break;
            }
        }
    }
}
