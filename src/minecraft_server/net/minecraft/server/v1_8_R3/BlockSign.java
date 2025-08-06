package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockSign extends BlockContainer
{
    protected BlockSign()
    {
        super(Material.WOOD);
        float f = 0.25F;
        float f1 = 1.0F;
        this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public boolean d()
    {
        return false;
    }

    public boolean b(IBlockAccess p_b_1_, BlockPosition p_b_2_)
    {
        return true;
    }

    public boolean c()
    {
        return false;
    }

    public boolean g()
    {
        return true;
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntitySign();
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.SIGN;
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        if (p_interact_1_.isClientSide)
        {
            return true;
        }
        else
        {
            TileEntity tileentity = p_interact_1_.getTileEntity(p_interact_2_);
            return tileentity instanceof TileEntitySign ? ((TileEntitySign)tileentity).b(p_interact_4_) : false;
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return !this.e(p_canPlace_1_, p_canPlace_2_) && super.canPlace(p_canPlace_1_, p_canPlace_2_);
    }
}
