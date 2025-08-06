package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockFurnace extends BlockContainer
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
    private final boolean b;
    private static boolean N;

    protected BlockFurnace(boolean p_i619_1_)
    {
        super(Material.STONE);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
        this.b = p_i619_1_;
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Item.getItemOf(Blocks.FURNACE);
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
    }

    private void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (!p_e_1_.isClientSide)
        {
            Block block = p_e_1_.getType(p_e_2_.north()).getBlock();
            Block block1 = p_e_1_.getType(p_e_2_.south()).getBlock();
            Block block2 = p_e_1_.getType(p_e_2_.west()).getBlock();
            Block block3 = p_e_1_.getType(p_e_2_.east()).getBlock();
            EnumDirection enumdirection = (EnumDirection)p_e_3_.get(FACING);

            if (enumdirection == EnumDirection.NORTH && block.o() && !block1.o())
            {
                enumdirection = EnumDirection.SOUTH;
            }
            else if (enumdirection == EnumDirection.SOUTH && block1.o() && !block.o())
            {
                enumdirection = EnumDirection.NORTH;
            }
            else if (enumdirection == EnumDirection.WEST && block2.o() && !block3.o())
            {
                enumdirection = EnumDirection.EAST;
            }
            else if (enumdirection == EnumDirection.EAST && block3.o() && !block2.o())
            {
                enumdirection = EnumDirection.WEST;
            }

            p_e_1_.setTypeAndData(p_e_2_, p_e_3_.set(FACING, enumdirection), 2);
        }
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

            if (tileentity instanceof TileEntityFurnace)
            {
                p_interact_4_.openContainer((TileEntityFurnace)tileentity);
                p_interact_4_.b(StatisticList.Y);
            }

            return true;
        }
    }

    public static void a(boolean p_a_0_, World p_a_1_, BlockPosition p_a_2_)
    {
        IBlockData iblockdata = p_a_1_.getType(p_a_2_);
        TileEntity tileentity = p_a_1_.getTileEntity(p_a_2_);
        N = true;

        if (p_a_0_)
        {
            p_a_1_.setTypeAndData(p_a_2_, Blocks.LIT_FURNACE.getBlockData().set(FACING, iblockdata.get(FACING)), 3);
            p_a_1_.setTypeAndData(p_a_2_, Blocks.LIT_FURNACE.getBlockData().set(FACING, iblockdata.get(FACING)), 3);
        }
        else
        {
            p_a_1_.setTypeAndData(p_a_2_, Blocks.FURNACE.getBlockData().set(FACING, iblockdata.get(FACING)), 3);
            p_a_1_.setTypeAndData(p_a_2_, Blocks.FURNACE.getBlockData().set(FACING, iblockdata.get(FACING)), 3);
        }

        N = false;

        if (tileentity != null)
        {
            tileentity.D();
            p_a_1_.setTileEntity(p_a_2_, tileentity);
        }
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityFurnace();
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        return this.getBlockData().set(FACING, p_getPlacedState_8_.getDirection().opposite());
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        p_postPlace_1_.setTypeAndData(p_postPlace_2_, p_postPlace_3_.set(FACING, p_postPlace_4_.getDirection().opposite()), 2);

        if (p_postPlace_5_.hasName())
        {
            TileEntity tileentity = p_postPlace_1_.getTileEntity(p_postPlace_2_);

            if (tileentity instanceof TileEntityFurnace)
            {
                ((TileEntityFurnace)tileentity).a(p_postPlace_5_.getName());
            }
        }
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        if (!N)
        {
            TileEntity tileentity = p_remove_1_.getTileEntity(p_remove_2_);

            if (tileentity instanceof TileEntityFurnace)
            {
                InventoryUtils.dropInventory(p_remove_1_, p_remove_2_, (TileEntityFurnace)tileentity);
                p_remove_1_.updateAdjacentComparators(p_remove_2_, this);
            }
        }

        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    public boolean isComplexRedstone()
    {
        return true;
    }

    public int l(World p_l_1_, BlockPosition p_l_2_)
    {
        return Container.a(p_l_1_.getTileEntity(p_l_2_));
    }

    public int b()
    {
        return 3;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        EnumDirection enumdirection = EnumDirection.fromType1(p_fromLegacyData_1_);

        if (enumdirection.k() == EnumDirection.EnumAxis.Y)
        {
            enumdirection = EnumDirection.NORTH;
        }

        return this.getBlockData().set(FACING, enumdirection);
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING});
    }
}
