package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;

public class BlockBrewingStand extends BlockContainer
{
    public static final BlockStateBoolean[] HAS_BOTTLE = new BlockStateBoolean[] {BlockStateBoolean.of("has_bottle_0"), BlockStateBoolean.of("has_bottle_1"), BlockStateBoolean.of("has_bottle_2")};

    public BlockBrewingStand()
    {
        super(Material.ORE);
        this.j(this.blockStateList.getBlockData().set(HAS_BOTTLE[0], Boolean.valueOf(false)).set(HAS_BOTTLE[1], Boolean.valueOf(false)).set(HAS_BOTTLE[2], Boolean.valueOf(false)));
    }

    public String getName()
    {
        return LocaleI18n.get("item.brewingStand.name");
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
        return new TileEntityBrewingStand();
    }

    public boolean d()
    {
        return false;
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_)
    {
        this.a(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        this.j();
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
    }

    public void j()
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
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

            if (tileentity instanceof TileEntityBrewingStand)
            {
                p_interact_4_.openContainer((TileEntityBrewingStand)tileentity);
                p_interact_4_.b(StatisticList.M);
            }

            return true;
        }
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        if (p_postPlace_5_.hasName())
        {
            TileEntity tileentity = p_postPlace_1_.getTileEntity(p_postPlace_2_);

            if (tileentity instanceof TileEntityBrewingStand)
            {
                ((TileEntityBrewingStand)tileentity).a(p_postPlace_5_.getName());
            }
        }
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        TileEntity tileentity = p_remove_1_.getTileEntity(p_remove_2_);

        if (tileentity instanceof TileEntityBrewingStand)
        {
            InventoryUtils.dropInventory(p_remove_1_, p_remove_2_, (TileEntityBrewingStand)tileentity);
        }

        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.BREWING_STAND;
    }

    public boolean isComplexRedstone()
    {
        return true;
    }

    public int l(World p_l_1_, BlockPosition p_l_2_)
    {
        return Container.a(p_l_1_.getTileEntity(p_l_2_));
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        IBlockData iblockdata = this.getBlockData();

        for (int i = 0; i < 3; ++i)
        {
            iblockdata = iblockdata.set(HAS_BOTTLE[i], Boolean.valueOf((p_fromLegacyData_1_ & 1 << i) > 0));
        }

        return iblockdata;
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;

        for (int j = 0; j < 3; ++j)
        {
            if (((Boolean)p_toLegacyData_1_.get(HAS_BOTTLE[j])).booleanValue())
            {
                i |= 1 << j;
            }
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {HAS_BOTTLE[0], HAS_BOTTLE[1], HAS_BOTTLE[2]});
    }
}
