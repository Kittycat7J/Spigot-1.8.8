package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockStem extends BlockPlant implements IBlockFragilePlantElement
{
    public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 7);
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", new Predicate()
    {
        public boolean a(EnumDirection p_a_1_)
        {
            return p_a_1_ != EnumDirection.DOWN;
        }
        public boolean apply(Object p_apply_1_)
        {
            return this.a((EnumDirection)p_apply_1_);
        }
    });
    private final Block blockFruit;

    protected BlockStem(Block p_i185_1_)
    {
        this.j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)).set(FACING, EnumDirection.UP));
        this.blockFruit = p_i185_1_;
        this.a(true);
        float f = 0.125F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
        this.a((CreativeModeTab)null);
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        p_updateState_1_ = p_updateState_1_.set(FACING, EnumDirection.UP);

        for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
        {
            if (p_updateState_2_.getType(p_updateState_3_.shift(enumdirection)).getBlock() == this.blockFruit)
            {
                p_updateState_1_ = p_updateState_1_.set(FACING, enumdirection);
                break;
            }
        }

        return p_updateState_1_;
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
            float f = BlockCrops.a(this, p_b_1_, p_b_2_);

            if (p_b_4_.nextInt((int)(p_b_1_.growthOdds / (float)(this == Blocks.PUMPKIN_STEM ? p_b_1_.spigotConfig.pumpkinModifier : p_b_1_.spigotConfig.melonModifier) * (25.0F / f)) + 1) == 0)
            {
                int i = ((Integer)p_b_3_.get(AGE)).intValue();

                if (i < 7)
                {
                    p_b_3_ = p_b_3_.set(AGE, Integer.valueOf(i + 1));
                    CraftEventFactory.handleBlockGrowEvent(p_b_1_, p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ(), this, this.toLegacyData(p_b_3_));
                }
                else
                {
                    for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
                    {
                        if (p_b_1_.getType(p_b_2_.shift(enumdirection)).getBlock() == this.blockFruit)
                        {
                            return;
                        }
                    }

                    p_b_2_ = p_b_2_.shift(EnumDirection.EnumDirectionLimit.HORIZONTAL.a(p_b_4_));
                    Block block = p_b_1_.getType(p_b_2_.down()).getBlock();

                    if (p_b_1_.getType(p_b_2_).getBlock().material == Material.AIR && (block == Blocks.FARMLAND || block == Blocks.DIRT || block == Blocks.GRASS))
                    {
                        CraftEventFactory.handleBlockGrowEvent(p_b_1_, p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ(), this.blockFruit, 0);
                    }
                }
            }
        }
    }

    public void g(World p_g_1_, BlockPosition p_g_2_, IBlockData p_g_3_)
    {
        int i = ((Integer)p_g_3_.get(AGE)).intValue() + MathHelper.nextInt(p_g_1_.random, 2, 5);
        CraftEventFactory.handleBlockGrowEvent(p_g_1_, p_g_2_.getX(), p_g_2_.getY(), p_g_2_.getZ(), this, Math.min(7, i));
    }

    public void j()
    {
        float f = 0.125F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.25F, 0.5F + f);
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        this.maxY = (double)((float)(((Integer)p_updateShape_1_.getType(p_updateShape_2_).get(AGE)).intValue() * 2 + 2) / 16.0F);
        float f = 0.125F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, (float)this.maxY, 0.5F + f);
    }

    public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_)
    {
        super.dropNaturally(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, p_dropNaturally_4_, p_dropNaturally_5_);

        if (!p_dropNaturally_1_.isClientSide)
        {
            Item item = this.l();

            if (item != null)
            {
                int i = ((Integer)p_dropNaturally_3_.get(AGE)).intValue();

                for (int j = 0; j < 3; ++j)
                {
                    if (p_dropNaturally_1_.random.nextInt(15) <= i)
                    {
                        a(p_dropNaturally_1_, p_dropNaturally_2_, new ItemStack(item));
                    }
                }
            }
        }
    }

    protected Item l()
    {
        return this.blockFruit == Blocks.PUMPKIN ? Items.PUMPKIN_SEEDS : (this.blockFruit == Blocks.MELON_BLOCK ? Items.MELON_SEEDS : null);
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return null;
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_)
    {
        return ((Integer)p_a_3_.get(AGE)).intValue() != 7;
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
        return new BlockStateList(this, new IBlockState[] {AGE, FACING});
    }
}
