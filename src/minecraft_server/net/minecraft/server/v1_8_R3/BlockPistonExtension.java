package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;

public class BlockPistonExtension extends Block
{
    public static final BlockStateDirection FACING = BlockStateDirection.of("facing");
    public static final BlockStateEnum<BlockPistonExtension.EnumPistonType> TYPE = BlockStateEnum.<BlockPistonExtension.EnumPistonType>of("type", BlockPistonExtension.EnumPistonType.class);
    public static final BlockStateBoolean SHORT = BlockStateBoolean.of("short");

    public BlockPistonExtension()
    {
        super(Material.PISTON);
        this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(TYPE, BlockPistonExtension.EnumPistonType.DEFAULT).set(SHORT, Boolean.valueOf(false)));
        this.a(i);
        this.c(0.5F);
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EntityHuman p_a_4_)
    {
        if (p_a_4_.abilities.canInstantlyBuild)
        {
            EnumDirection enumdirection = (EnumDirection)p_a_3_.get(FACING);

            if (enumdirection != null)
            {
                BlockPosition blockposition = p_a_2_.shift(enumdirection.opposite());
                Block block = p_a_1_.getType(blockposition).getBlock();

                if (block == Blocks.PISTON || block == Blocks.STICKY_PISTON)
                {
                    p_a_1_.setAir(blockposition);
                }
            }
        }

        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
        EnumDirection enumdirection = ((EnumDirection)p_remove_3_.get(FACING)).opposite();
        p_remove_2_ = p_remove_2_.shift(enumdirection);
        IBlockData iblockdata = p_remove_1_.getType(p_remove_2_);

        if ((iblockdata.getBlock() == Blocks.PISTON || iblockdata.getBlock() == Blocks.STICKY_PISTON) && ((Boolean)iblockdata.get(BlockPiston.EXTENDED)).booleanValue())
        {
            iblockdata.getBlock().b(p_remove_1_, p_remove_2_, iblockdata, 0);
            p_remove_1_.setAir(p_remove_2_);
        }
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return false;
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_, EnumDirection p_canPlace_3_)
    {
        return false;
    }

    public int a(Random p_a_1_)
    {
        return 0;
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_)
    {
        this.d(p_a_3_);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        this.e(p_a_3_);
        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    private void e(IBlockData p_e_1_)
    {
        float f = 0.25F;
        float f1 = 0.375F;
        float f2 = 0.625F;
        float f3 = 0.25F;
        float f4 = 0.75F;

        switch ((EnumDirection)p_e_1_.get(FACING))
        {
            case DOWN:
                this.a(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
                break;

            case UP:
                this.a(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
                break;

            case NORTH:
                this.a(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
                break;

            case SOUTH:
                this.a(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
                break;

            case WEST:
                this.a(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
                break;

            case EAST:
                this.a(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
        }
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        this.d(p_updateShape_1_.getType(p_updateShape_2_));
    }

    public void d(IBlockData p_d_1_)
    {
        float f = 0.25F;
        EnumDirection enumdirection = (EnumDirection)p_d_1_.get(FACING);

        if (enumdirection != null)
        {
            switch (enumdirection)
            {
                case DOWN:
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
                    break;

                case UP:
                    this.a(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
                    break;

                case NORTH:
                    this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
                    break;

                case SOUTH:
                    this.a(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
                    break;

                case WEST:
                    this.a(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
                    break;

                case EAST:
                    this.a(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        EnumDirection enumdirection = (EnumDirection)p_doPhysics_3_.get(FACING);
        BlockPosition blockposition = p_doPhysics_2_.shift(enumdirection.opposite());
        IBlockData iblockdata = p_doPhysics_1_.getType(blockposition);

        if (iblockdata.getBlock() != Blocks.PISTON && iblockdata.getBlock() != Blocks.STICKY_PISTON)
        {
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }
        else
        {
            iblockdata.getBlock().doPhysics(p_doPhysics_1_, blockposition, iblockdata, p_doPhysics_4_);
        }
    }

    public static EnumDirection b(int p_b_0_)
    {
        int i = p_b_0_ & 7;
        return i > 5 ? null : EnumDirection.fromType1(i);
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(FACING, b(p_fromLegacyData_1_)).set(TYPE, (p_fromLegacyData_1_ & 8) > 0 ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;
        i = i | ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();

        if (p_toLegacyData_1_.get(TYPE) == BlockPistonExtension.EnumPistonType.STICKY)
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {FACING, TYPE, SHORT});
    }

    public static enum EnumPistonType implements INamable
    {
        DEFAULT("normal"),
        STICKY("sticky");

        private final String c;

        private EnumPistonType(String p_i659_3_)
        {
            this.c = p_i659_3_;
        }

        public String toString()
        {
            return this.c;
        }

        public String getName()
        {
            return this.c;
        }
    }
}
