package net.minecraft.server.v1_8_R3;

public class BlockEnchantmentTable extends BlockContainer
{
    protected BlockEnchantmentTable()
    {
        super(Material.STONE, MaterialMapColor.D);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        this.e(0);
        this.a(CreativeModeTab.c);
    }

    public boolean d()
    {
        return false;
    }

    public boolean c()
    {
        return false;
    }

    public int b()
    {
        return 3;
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityEnchantTable();
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

            if (tileentity instanceof TileEntityEnchantTable)
            {
                p_interact_4_.openTileEntity((TileEntityEnchantTable)tileentity);
            }

            return true;
        }
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        super.postPlace(p_postPlace_1_, p_postPlace_2_, p_postPlace_3_, p_postPlace_4_, p_postPlace_5_);

        if (p_postPlace_5_.hasName())
        {
            TileEntity tileentity = p_postPlace_1_.getTileEntity(p_postPlace_2_);

            if (tileentity instanceof TileEntityEnchantTable)
            {
                ((TileEntityEnchantTable)tileentity).a(p_postPlace_5_.getName());
            }
        }
    }
}
