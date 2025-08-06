package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockDispenser extends BlockContainer
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
    public static final BlockStateBoolean TRIGGERED = BlockStateBoolean.of("triggered");
    public static final RegistryDefault<Item, IDispenseBehavior> REGISTRY = new RegistryDefault(new DispenseBehaviorItem());
    protected Random O = new Random();
    public static boolean eventFired = false;

    protected BlockDispenser()
    {
        super(Material.STONE);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(TRIGGERED, Boolean.valueOf(false)));
        this.a((CreativeModeTab)CreativeModeTab.d);
    }

    public int a(World p_a_1_)
    {
        return 4;
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        super.onPlace(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
        this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
    }

    private void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (!p_e_1_.isClientSide)
        {
            EnumDirection enumdirection = (EnumDirection)p_e_3_.get(FACING);
            boolean flag = p_e_1_.getType(p_e_2_.north()).getBlock().o();
            boolean flag1 = p_e_1_.getType(p_e_2_.south()).getBlock().o();

            if (enumdirection == EnumDirection.NORTH && flag && !flag1)
            {
                enumdirection = EnumDirection.SOUTH;
            }
            else if (enumdirection == EnumDirection.SOUTH && flag1 && !flag)
            {
                enumdirection = EnumDirection.NORTH;
            }
            else
            {
                boolean flag2 = p_e_1_.getType(p_e_2_.west()).getBlock().o();
                boolean flag3 = p_e_1_.getType(p_e_2_.east()).getBlock().o();

                if (enumdirection == EnumDirection.WEST && flag2 && !flag3)
                {
                    enumdirection = EnumDirection.EAST;
                }
                else if (enumdirection == EnumDirection.EAST && flag3 && !flag2)
                {
                    enumdirection = EnumDirection.WEST;
                }
            }

            p_e_1_.setTypeAndData(p_e_2_, p_e_3_.set(FACING, enumdirection).set(TRIGGERED, Boolean.valueOf(false)), 2);
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

            if (tileentity instanceof TileEntityDispenser)
            {
                p_interact_4_.openContainer((TileEntityDispenser)tileentity);

                if (tileentity instanceof TileEntityDropper)
                {
                    p_interact_4_.b(StatisticList.O);
                }
                else
                {
                    p_interact_4_.b(StatisticList.Q);
                }
            }

            return true;
        }
    }

    public void dispense(World p_dispense_1_, BlockPosition p_dispense_2_)
    {
        SourceBlock sourceblock = new SourceBlock(p_dispense_1_, p_dispense_2_);
        TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();

        if (tileentitydispenser != null)
        {
            int i = tileentitydispenser.m();

            if (i < 0)
            {
                p_dispense_1_.triggerEffect(1001, p_dispense_2_, 0);
            }
            else
            {
                ItemStack itemstack = tileentitydispenser.getItem(i);
                IDispenseBehavior idispensebehavior = this.a(itemstack);

                if (idispensebehavior != IDispenseBehavior.NONE)
                {
                    ItemStack itemstack1 = idispensebehavior.a(sourceblock, itemstack);
                    eventFired = false;
                    tileentitydispenser.setItem(i, itemstack1.count <= 0 ? null : itemstack1);
                }
            }
        }
    }

    protected IDispenseBehavior a(ItemStack p_a_1_)
    {
        return (IDispenseBehavior)REGISTRY.get(p_a_1_ == null ? null : p_a_1_.getItem());
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        boolean flag = p_doPhysics_1_.isBlockIndirectlyPowered(p_doPhysics_2_) || p_doPhysics_1_.isBlockIndirectlyPowered(p_doPhysics_2_.up());
        boolean flag1 = ((Boolean)p_doPhysics_3_.get(TRIGGERED)).booleanValue();

        if (flag && !flag1)
        {
            p_doPhysics_1_.a((BlockPosition)p_doPhysics_2_, (Block)this, this.a(p_doPhysics_1_));
            p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, p_doPhysics_3_.set(TRIGGERED, Boolean.valueOf(true)), 4);
        }
        else if (!flag && flag1)
        {
            p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, p_doPhysics_3_.set(TRIGGERED, Boolean.valueOf(false)), 4);
        }
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (!p_b_1_.isClientSide)
        {
            this.dispense(p_b_1_, p_b_2_);
        }
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityDispenser();
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        return this.getBlockData().set(FACING, BlockPiston.a(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_8_)).set(TRIGGERED, Boolean.valueOf(false));
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        p_postPlace_1_.setTypeAndData(p_postPlace_2_, p_postPlace_3_.set(FACING, BlockPiston.a(p_postPlace_1_, p_postPlace_2_, p_postPlace_4_)), 2);

        if (p_postPlace_5_.hasName())
        {
            TileEntity tileentity = p_postPlace_1_.getTileEntity(p_postPlace_2_);

            if (tileentity instanceof TileEntityDispenser)
            {
                ((TileEntityDispenser)tileentity).a(p_postPlace_5_.getName());
            }
        }
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        TileEntity tileentity = p_remove_1_.getTileEntity(p_remove_2_);

        if (tileentity instanceof TileEntityDispenser)
        {
            InventoryUtils.dropInventory(p_remove_1_, p_remove_2_, (TileEntityDispenser)tileentity);
            p_remove_1_.updateAdjacentComparators(p_remove_2_, this);
        }

        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    public static IPosition a(ISourceBlock p_a_0_)
    {
        EnumDirection enumdirection = b(p_a_0_.f());
        double d0 = p_a_0_.getX() + 0.7D * (double)enumdirection.getAdjacentX();
        double d1 = p_a_0_.getY() + 0.7D * (double)enumdirection.getAdjacentY();
        double d2 = p_a_0_.getZ() + 0.7D * (double)enumdirection.getAdjacentZ();
        return new Position(d0, d1, d2);
    }

    public static EnumDirection b(int p_b_0_)
    {
        return EnumDirection.fromType1(p_b_0_ & 7);
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
        return this.getBlockData().set(FACING, b(p_fromLegacyData_1_)).set(TRIGGERED, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        byte b0 = 0;
        int i = b0 | ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();

        if (((Boolean)p_toLegacyData_1_.get(TRIGGERED)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, TRIGGERED});
    }
}
