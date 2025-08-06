package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class BlockReed extends Block
{
    public static final BlockStateInteger AGE = BlockStateInteger.of("age", 0, 15);

    protected BlockReed()
    {
        super(Material.PLANT);
        this.j(this.blockStateList.getBlockData().set(AGE, Integer.valueOf(0)));
        float f = 0.375F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 1.0F, 0.5F + f);
        this.a(true);
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if ((p_b_1_.getType(p_b_2_.down()).getBlock() == Blocks.REEDS || this.e(p_b_1_, p_b_2_, p_b_3_)) && p_b_1_.isEmpty(p_b_2_.up()))
        {
            int i;

            for (i = 1; p_b_1_.getType(p_b_2_.down(i)).getBlock() == this; ++i)
            {
                ;
            }

            if (i < 3)
            {
                int j = ((Integer)p_b_3_.get(AGE)).intValue();

                if (j >= (byte)((int)range(3.0F, p_b_1_.growthOdds / (float)p_b_1_.spigotConfig.caneModifier * 15.0F + 0.5F, 15.0F)))
                {
                    BlockPosition blockposition = p_b_2_.up();
                    CraftEventFactory.handleBlockGrowEvent(p_b_1_, blockposition.getX(), blockposition.getY(), blockposition.getZ(), this, 0);
                    p_b_1_.setTypeAndData(p_b_2_, p_b_3_.set(AGE, Integer.valueOf(0)), 4);
                }
                else
                {
                    p_b_1_.setTypeAndData(p_b_2_, p_b_3_.set(AGE, Integer.valueOf(j + 1)), 4);
                }
            }
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        Block block = p_canPlace_1_.getType(p_canPlace_2_.down()).getBlock();

        if (block == this)
        {
            return true;
        }
        else if (block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.SAND)
        {
            return false;
        }
        else
        {
            for (EnumDirection enumdirection : EnumDirection.EnumDirectionLimit.HORIZONTAL)
            {
                if (p_canPlace_1_.getType(p_canPlace_2_.shift(enumdirection).down()).getBlock().getMaterial() == Material.WATER)
                {
                    return true;
                }
            }

            return false;
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
    }

    protected final boolean e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (this.e(p_e_1_, p_e_2_))
        {
            return true;
        }
        else
        {
            this.b(p_e_1_, p_e_2_, p_e_3_, 0);
            p_e_1_.setAir(p_e_2_);
            return false;
        }
    }

    public boolean e(World p_e_1_, BlockPosition p_e_2_)
    {
        return this.canPlace(p_e_1_, p_e_2_);
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.REEDS;
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
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
