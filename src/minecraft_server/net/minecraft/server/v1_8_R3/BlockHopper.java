package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import java.util.List;

public class BlockHopper extends BlockContainer
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing", new Predicate()
    {
        public boolean a(EnumDirection p_a_1_)
        {
            return p_a_1_ != EnumDirection.UP;
        }
        public boolean apply(Object p_apply_1_)
        {
            return this.a((EnumDirection)p_apply_1_);
        }
    });
    public static final BlockStateBoolean ENABLED = BlockStateBoolean.of("enabled");

    public BlockHopper()
    {
        super(Material.ORE, MaterialMapColor.m);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.DOWN).set(ENABLED, Boolean.valueOf(true)));
        this.a(CreativeModeTab.d);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_)
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        float f = 0.125F;
        this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        EnumDirection enumdirection = p_getPlacedState_3_.opposite();

        if (enumdirection == EnumDirection.UP)
        {
            enumdirection = EnumDirection.DOWN;
        }

        return this.getBlockData().set(FACING, enumdirection).set(ENABLED, Boolean.valueOf(true));
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityHopper();
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        super.postPlace(p_postPlace_1_, p_postPlace_2_, p_postPlace_3_, p_postPlace_4_, p_postPlace_5_);

        if (p_postPlace_5_.hasName())
        {
            TileEntity tileentity = p_postPlace_1_.getTileEntity(p_postPlace_2_);

            if (tileentity instanceof TileEntityHopper)
            {
                ((TileEntityHopper)tileentity).a(p_postPlace_5_.getName());
            }
        }
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
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

            if (tileentity instanceof TileEntityHopper)
            {
                p_interact_4_.openContainer((TileEntityHopper)tileentity);
                p_interact_4_.b(StatisticList.P);
            }

            return true;
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        this.e(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_);
    }

    private void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        boolean flag = !p_e_1_.isBlockIndirectlyPowered(p_e_2_);

        if (flag != ((Boolean)p_e_3_.get(ENABLED)).booleanValue())
        {
            p_e_1_.setTypeAndData(p_e_2_, p_e_3_.set(ENABLED, Boolean.valueOf(flag)), 4);
        }
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        TileEntity tileentity = p_remove_1_.getTileEntity(p_remove_2_);

        if (tileentity instanceof TileEntityHopper)
        {
            InventoryUtils.dropInventory(p_remove_1_, p_remove_2_, (TileEntityHopper)tileentity);
            p_remove_1_.updateAdjacentComparators(p_remove_2_, this);
        }

        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
    }

    public int b()
    {
        return 3;
    }

    public boolean d()
    {
        return false;
    }

    public boolean c()
    {
        return false;
    }

    public static EnumDirection b(int p_b_0_)
    {
        return EnumDirection.fromType1(p_b_0_ & 7);
    }

    public static boolean f(int p_f_0_)
    {
        return (p_f_0_ & 8) != 8;
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
        return this.getBlockData().set(FACING, b(p_fromLegacyData_1_)).set(ENABLED, Boolean.valueOf(f(p_fromLegacyData_1_)));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        byte b0 = 0;
        int i = b0 | ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();

        if (!((Boolean)p_toLegacyData_1_.get(ENABLED)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, ENABLED});
    }
}
