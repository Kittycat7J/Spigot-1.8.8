package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockTallPlant extends BlockPlant implements IBlockFragilePlantElement
{
    public static final BlockStateEnum<BlockTallPlant.EnumTallFlowerVariants> VARIANT = BlockStateEnum.<BlockTallPlant.EnumTallFlowerVariants>of("variant", BlockTallPlant.EnumTallFlowerVariants.class);
    public static final BlockStateEnum<BlockTallPlant.EnumTallPlantHalf> HALF = BlockStateEnum.<BlockTallPlant.EnumTallPlantHalf>of("half", BlockTallPlant.EnumTallPlantHalf.class);
    public static final BlockStateEnum<EnumDirection> N = BlockDirectional.FACING;

    public BlockTallPlant()
    {
        super(Material.REPLACEABLE_PLANT);
        this.j(this.blockStateList.getBlockData().set(VARIANT, BlockTallPlant.EnumTallFlowerVariants.SUNFLOWER).set(HALF, BlockTallPlant.EnumTallPlantHalf.LOWER).set(N, EnumDirection.NORTH));
        this.c(0.0F);
        this.a(h);
        this.c("doublePlant");
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public BlockTallPlant.EnumTallFlowerVariants e(IBlockAccess p_e_1_, BlockPosition p_e_2_)
    {
        IBlockData iblockdata = p_e_1_.getType(p_e_2_);

        if (iblockdata.getBlock() == this)
        {
            iblockdata = this.updateState(iblockdata, p_e_1_, p_e_2_);
            return (BlockTallPlant.EnumTallFlowerVariants)iblockdata.get(VARIANT);
        }
        else
        {
            return BlockTallPlant.EnumTallFlowerVariants.FERN;
        }
    }

    public boolean canPlace(World p_canPlace_1_, BlockPosition p_canPlace_2_)
    {
        return super.canPlace(p_canPlace_1_, p_canPlace_2_) && p_canPlace_1_.isEmpty(p_canPlace_2_.up());
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_)
    {
        IBlockData iblockdata = p_a_1_.getType(p_a_2_);

        if (iblockdata.getBlock() != this)
        {
            return true;
        }
        else
        {
            BlockTallPlant.EnumTallFlowerVariants blocktallplant$enumtallflowervariants = (BlockTallPlant.EnumTallFlowerVariants)this.updateState(iblockdata, p_a_1_, p_a_2_).get(VARIANT);
            return blocktallplant$enumtallflowervariants == BlockTallPlant.EnumTallFlowerVariants.FERN || blocktallplant$enumtallflowervariants == BlockTallPlant.EnumTallFlowerVariants.GRASS;
        }
    }

    protected void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        if (!this.f(p_e_1_, p_e_2_, p_e_3_))
        {
            boolean flag = p_e_3_.get(HALF) == BlockTallPlant.EnumTallPlantHalf.UPPER;
            BlockPosition blockposition = flag ? p_e_2_ : p_e_2_.up();
            BlockPosition blockposition1 = flag ? p_e_2_.down() : p_e_2_;
            Object object = flag ? this : p_e_1_.getType(blockposition).getBlock();
            Object object1 = flag ? p_e_1_.getType(blockposition1).getBlock() : this;

            if (object == this)
            {
                p_e_1_.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
            }

            if (object1 == this)
            {
                p_e_1_.setTypeAndData(blockposition1, Blocks.AIR.getBlockData(), 3);

                if (!flag)
                {
                    this.b(p_e_1_, blockposition1, p_e_3_, 0);
                }
            }
        }
    }

    public boolean f(World p_f_1_, BlockPosition p_f_2_, IBlockData p_f_3_)
    {
        if (p_f_3_.get(HALF) == BlockTallPlant.EnumTallPlantHalf.UPPER)
        {
            return p_f_1_.getType(p_f_2_.down()).getBlock() == this;
        }
        else
        {
            IBlockData iblockdata = p_f_1_.getType(p_f_2_.up());
            return iblockdata.getBlock() == this && super.f(p_f_1_, p_f_2_, iblockdata);
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        if (p_getDropType_1_.get(HALF) == BlockTallPlant.EnumTallPlantHalf.UPPER)
        {
            return null;
        }
        else
        {
            BlockTallPlant.EnumTallFlowerVariants blocktallplant$enumtallflowervariants = (BlockTallPlant.EnumTallFlowerVariants)p_getDropType_1_.get(VARIANT);
            return blocktallplant$enumtallflowervariants == BlockTallPlant.EnumTallFlowerVariants.FERN ? null : (blocktallplant$enumtallflowervariants == BlockTallPlant.EnumTallFlowerVariants.GRASS ? (p_getDropType_2_.nextInt(8) == 0 ? Items.WHEAT_SEEDS : null) : Item.getItemOf(this));
        }
    }

    public int getDropData(IBlockData p_getDropData_1_)
    {
        return p_getDropData_1_.get(HALF) != BlockTallPlant.EnumTallPlantHalf.UPPER && p_getDropData_1_.get(VARIANT) != BlockTallPlant.EnumTallFlowerVariants.GRASS ? ((BlockTallPlant.EnumTallFlowerVariants)p_getDropData_1_.get(VARIANT)).a() : 0;
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, BlockTallPlant.EnumTallFlowerVariants p_a_3_, int p_a_4_)
    {
        p_a_1_.setTypeAndData(p_a_2_, this.getBlockData().set(HALF, BlockTallPlant.EnumTallPlantHalf.LOWER).set(VARIANT, p_a_3_), p_a_4_);
        p_a_1_.setTypeAndData(p_a_2_.up(), this.getBlockData().set(HALF, BlockTallPlant.EnumTallPlantHalf.UPPER), p_a_4_);
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        p_postPlace_1_.setTypeAndData(p_postPlace_2_.up(), this.getBlockData().set(HALF, BlockTallPlant.EnumTallPlantHalf.UPPER), 2);
    }

    public void a(World p_a_1_, EntityHuman p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, TileEntity p_a_5_)
    {
        if (p_a_1_.isClientSide || p_a_2_.bZ() == null || p_a_2_.bZ().getItem() != Items.SHEARS || p_a_4_.get(HALF) != BlockTallPlant.EnumTallPlantHalf.LOWER || !this.b(p_a_1_, p_a_3_, p_a_4_, p_a_2_))
        {
            super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);
        }
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EntityHuman p_a_4_)
    {
        if (p_a_3_.get(HALF) == BlockTallPlant.EnumTallPlantHalf.UPPER)
        {
            if (p_a_1_.getType(p_a_2_.down()).getBlock() == this)
            {
                if (!p_a_4_.abilities.canInstantlyBuild)
                {
                    IBlockData iblockdata = p_a_1_.getType(p_a_2_.down());
                    BlockTallPlant.EnumTallFlowerVariants blocktallplant$enumtallflowervariants = (BlockTallPlant.EnumTallFlowerVariants)iblockdata.get(VARIANT);

                    if (blocktallplant$enumtallflowervariants != BlockTallPlant.EnumTallFlowerVariants.FERN && blocktallplant$enumtallflowervariants != BlockTallPlant.EnumTallFlowerVariants.GRASS)
                    {
                        p_a_1_.setAir(p_a_2_.down(), true);
                    }
                    else if (!p_a_1_.isClientSide)
                    {
                        if (p_a_4_.bZ() != null && p_a_4_.bZ().getItem() == Items.SHEARS)
                        {
                            this.b(p_a_1_, p_a_2_, iblockdata, p_a_4_);
                            p_a_1_.setAir(p_a_2_.down());
                        }
                        else
                        {
                            p_a_1_.setAir(p_a_2_.down(), true);
                        }
                    }
                    else
                    {
                        p_a_1_.setAir(p_a_2_.down());
                    }
                }
                else
                {
                    p_a_1_.setAir(p_a_2_.down());
                }
            }
        }
        else if (p_a_4_.abilities.canInstantlyBuild && p_a_1_.getType(p_a_2_.up()).getBlock() == this)
        {
            p_a_1_.setTypeAndData(p_a_2_.up(), Blocks.AIR.getBlockData(), 2);
        }

        super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_);
    }

    private boolean b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EntityHuman p_b_4_)
    {
        BlockTallPlant.EnumTallFlowerVariants blocktallplant$enumtallflowervariants = (BlockTallPlant.EnumTallFlowerVariants)p_b_3_.get(VARIANT);

        if (blocktallplant$enumtallflowervariants != BlockTallPlant.EnumTallFlowerVariants.FERN && blocktallplant$enumtallflowervariants != BlockTallPlant.EnumTallFlowerVariants.GRASS)
        {
            return false;
        }
        else
        {
            p_b_4_.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
            int i = (blocktallplant$enumtallflowervariants == BlockTallPlant.EnumTallFlowerVariants.GRASS ? BlockLongGrass.EnumTallGrassType.GRASS : BlockLongGrass.EnumTallGrassType.FERN).a();
            a(p_b_1_, p_b_2_, new ItemStack(Blocks.TALLGRASS, 2, i));
            return true;
        }
    }

    public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_)
    {
        return this.e(p_getDropData_1_, p_getDropData_2_).a();
    }

    public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_)
    {
        BlockTallPlant.EnumTallFlowerVariants blocktallplant$enumtallflowervariants = this.e(p_a_1_, p_a_2_);
        return blocktallplant$enumtallflowervariants != BlockTallPlant.EnumTallFlowerVariants.GRASS && blocktallplant$enumtallflowervariants != BlockTallPlant.EnumTallFlowerVariants.FERN;
    }

    public boolean a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_)
    {
        return true;
    }

    public void b(World p_b_1_, Random p_b_2_, BlockPosition p_b_3_, IBlockData p_b_4_)
    {
        a(p_b_1_, p_b_3_, new ItemStack(this, 1, this.e(p_b_1_, p_b_3_).a()));
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return (p_fromLegacyData_1_ & 8) > 0 ? this.getBlockData().set(HALF, BlockTallPlant.EnumTallPlantHalf.UPPER) : this.getBlockData().set(HALF, BlockTallPlant.EnumTallPlantHalf.LOWER).set(VARIANT, BlockTallPlant.EnumTallFlowerVariants.a(p_fromLegacyData_1_ & 7));
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        if (p_updateState_1_.get(HALF) == BlockTallPlant.EnumTallPlantHalf.UPPER)
        {
            IBlockData iblockdata = p_updateState_2_.getType(p_updateState_3_.down());

            if (iblockdata.getBlock() == this)
            {
                p_updateState_1_ = p_updateState_1_.set(VARIANT, iblockdata.get(VARIANT));
            }
        }

        return p_updateState_1_;
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return p_toLegacyData_1_.get(HALF) == BlockTallPlant.EnumTallPlantHalf.UPPER ? 8 | ((EnumDirection)p_toLegacyData_1_.get(N)).b() : ((BlockTallPlant.EnumTallFlowerVariants)p_toLegacyData_1_.get(VARIANT)).a();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {HALF, VARIANT, N});
    }

    public static enum EnumTallFlowerVariants implements INamable
    {
        SUNFLOWER(0, "sunflower"),
        SYRINGA(1, "syringa"),
        GRASS(2, "double_grass", "grass"),
        FERN(3, "double_fern", "fern"),
        ROSE(4, "double_rose", "rose"),
        PAEONIA(5, "paeonia");

        private static final BlockTallPlant.EnumTallFlowerVariants[] g = new BlockTallPlant.EnumTallFlowerVariants[values().length];
        private final int h;
        private final String i;
        private final String j;

        private EnumTallFlowerVariants(int p_i608_3_, String p_i608_4_)
        {
            this(p_i608_3_, p_i608_4_, p_i608_4_);
        }

        private EnumTallFlowerVariants(int p_i609_3_, String p_i609_4_, String p_i609_5_)
        {
            this.h = p_i609_3_;
            this.i = p_i609_4_;
            this.j = p_i609_5_;
        }

        public int a()
        {
            return this.h;
        }

        public String toString()
        {
            return this.i;
        }

        public static BlockTallPlant.EnumTallFlowerVariants a(int p_a_0_)
        {
            if (p_a_0_ < 0 || p_a_0_ >= g.length)
            {
                p_a_0_ = 0;
            }

            return g[p_a_0_];
        }

        public String getName()
        {
            return this.i;
        }

        public String c()
        {
            return this.j;
        }

        static {
            for (BlockTallPlant.EnumTallFlowerVariants blocktallplant$enumtallflowervariants : values())
            {
                g[blocktallplant$enumtallflowervariants.a()] = blocktallplant$enumtallflowervariants;
            }
        }
    }

    public static enum EnumTallPlantHalf implements INamable
    {
        UPPER,
        LOWER;

        public String toString()
        {
            return this.getName();
        }

        public String getName()
        {
            return this == UPPER ? "upper" : "lower";
        }
    }
}
