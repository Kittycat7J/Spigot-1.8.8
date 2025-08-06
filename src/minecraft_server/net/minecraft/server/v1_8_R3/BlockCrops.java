package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockCrops extends BlockPlant implements IBlockFragilePlantElement
{
    public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 7);

    protected BlockCrops()
    {
        this.j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)));
        this.a(true);
        float f = 0.5F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.a((CreativeModeTab)null);
        this.c(0.0F);
        this.a(h);
        this.K();
    }

    protected boolean c(Block p_c_1_)
    {
        return p_c_1_ == Blocks.FARMLAND;
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        super.b(p_b_1_, p_b_2_, p_b_3_, p_b_4_);

        if (p_b_1_.getLightLevel(p_b_2_.up()) >= 9)
        {
            int i = ((Integer)p_b_3_.get(AGE)).intValue();

            if (i < 7)
            {
                float f = a(this, p_b_1_, p_b_2_);

                if (p_b_4_.nextInt((int)(p_b_1_.growthOdds / (float)p_b_1_.spigotConfig.wheatModifier * (25.0F / f)) + 1) == 0)
                {
                    IBlockData iblockdata = p_b_3_.set(AGE, Integer.valueOf(i + 1));
                    CraftEventFactory.handleBlockGrowEvent(p_b_1_, p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ(), this, this.toLegacyData(iblockdata));
                }
            }
        }
    }

    public void g(World p_g_1_, BlockPosition p_g_2_, IBlockData p_g_3_)
    {
        int i = ((Integer)p_g_3_.get(AGE)).intValue() + MathHelper.nextInt(p_g_1_.random, 2, 5);

        if (i > 7)
        {
            i = 7;
        }

        IBlockData iblockdata = p_g_3_.set(AGE, Integer.valueOf(i));
        CraftEventFactory.handleBlockGrowEvent(p_g_1_, p_g_2_.getX(), p_g_2_.getY(), p_g_2_.getZ(), this, this.toLegacyData(iblockdata));
    }

    protected static float a(Block p_a_0_, World p_a_1_, BlockPosition p_a_2_)
    {
        float f = 1.0F;
        BlockPosition blockposition = p_a_2_.down();

        for (int i = -1; i <= 1; ++i)
        {
            for (int j = -1; j <= 1; ++j)
            {
                float f1 = 0.0F;
                IBlockData iblockdata = p_a_1_.getType(blockposition.a(i, 0, j));

                if (iblockdata.getBlock() == Blocks.FARMLAND)
                {
                    f1 = 1.0F;

                    if (((Integer)iblockdata.get(BlockSoil.MOISTURE)).intValue() > 0)
                    {
                        f1 = 3.0F;
                    }
                }

                if (i != 0 || j != 0)
                {
                    f1 /= 4.0F;
                }

                f += f1;
            }
        }

        BlockPosition blockposition1 = p_a_2_.north();
        BlockPosition blockposition2 = p_a_2_.south();
        BlockPosition blockposition3 = p_a_2_.west();
        BlockPosition blockposition4 = p_a_2_.east();
        boolean flag = p_a_0_ == p_a_1_.getType(blockposition3).getBlock() || p_a_0_ == p_a_1_.getType(blockposition4).getBlock();
        boolean flag1 = p_a_0_ == p_a_1_.getType(blockposition1).getBlock() || p_a_0_ == p_a_1_.getType(blockposition2).getBlock();

        if (flag && flag1)
        {
            f /= 2.0F;
        }
        else
        {
            boolean flag2 = p_a_0_ == p_a_1_.getType(blockposition3.north()).getBlock() || p_a_0_ == p_a_1_.getType(blockposition4.north()).getBlock() || p_a_0_ == p_a_1_.getType(blockposition4.south()).getBlock() || p_a_0_ == p_a_1_.getType(blockposition3.south()).getBlock();

            if (flag2)
            {
                f /= 2.0F;
            }
        }

        return f;
    }

    public boolean f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_)
    {
        return (p_f_1_.k(p_f_2_) >= 8 || p_f_1_.i(p_f_2_)) && this.c(p_f_1_.getType(p_f_2_.down()).getBlock());
    }

    protected Item l()
    {
        return Items.WHEAT_SEEDS;
    }

    protected Item n()
    {
        return Items.WHEAT;
    }

    public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_)
    {
        super.dropNaturally(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, p_dropNaturally_4_, 0);

        if (!p_dropNaturally_1_.isClientSide)
        {
            int i = ((Integer)p_dropNaturally_3_.get(AGE)).intValue();

            if (i >= 7)
            {
                int j = 3 + p_dropNaturally_5_;

                for (int k = 0; k < j; ++k)
                {
                    if (p_dropNaturally_1_.random.nextInt(15) <= i)
                    {
                        a(p_dropNaturally_1_, p_dropNaturally_2_, new ItemStack(this.l(), 1, 0));
                    }
                }
            }
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return ((Integer)p_getDropType_1_.get(AGE)).intValue() == 7 ? this.n() : this.l();
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_)
    {
        return ((Integer)p_a_3_.get(AGE)).intValue() < 7;
    }

    public boolean a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_)
    {
        return true;
    }

    public void b(World p_b_1_, Random p_b_2_, BlockPosition p_b_3_, IBlockData p_b_4_)
    {
        this.g(p_b_1_, p_b_3_, p_b_4_);
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(AGE, Integer.valueOf(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((Integer)p_toLegacyData_1_.get(AGE)).intValue();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {AGE});
    }
}
