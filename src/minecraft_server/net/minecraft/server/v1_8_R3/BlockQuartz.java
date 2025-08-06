package net.minecraft.server.v1_8_R3;

public class BlockQuartz extends Block
{
    public static final BlockStateEnum<BlockQuartz.EnumQuartzVariant> VARIANT = BlockStateEnum.<BlockQuartz.EnumQuartzVariant>of("variant", BlockQuartz.EnumQuartzVariant.class);

    public BlockQuartz()
    {
        super(Material.STONE);
        this.j(this.blockStateList.getBlockData().set(VARIANT, BlockQuartz.EnumQuartzVariant.DEFAULT));
        this.a(CreativeModeTab.b);
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        if (p_getPlacedState_7_ == BlockQuartz.EnumQuartzVariant.LINES_Y.a())
        {
            switch (p_getPlacedState_3_.k())
            {
                case Z:
                    return this.getBlockData().set(VARIANT, BlockQuartz.EnumQuartzVariant.LINES_Z);

                case X:
                    return this.getBlockData().set(VARIANT, BlockQuartz.EnumQuartzVariant.LINES_X);

                case Y:
                default:
                    return this.getBlockData().set(VARIANT, BlockQuartz.EnumQuartzVariant.LINES_Y);
            }
        }
        else
        {
            return p_getPlacedState_7_ == BlockQuartz.EnumQuartzVariant.CHISELED.a() ? this.getBlockData().set(VARIANT, BlockQuartz.EnumQuartzVariant.CHISELED) : this.getBlockData().set(VARIANT, BlockQuartz.EnumQuartzVariant.DEFAULT);
        }
    }

    public int getDropData(IBlockData p_getDropData_1_)
    {
        BlockQuartz.EnumQuartzVariant blockquartz$enumquartzvariant = (BlockQuartz.EnumQuartzVariant)p_getDropData_1_.get(VARIANT);
        return blockquartz$enumquartzvariant != BlockQuartz.EnumQuartzVariant.LINES_X && blockquartz$enumquartzvariant != BlockQuartz.EnumQuartzVariant.LINES_Z ? blockquartz$enumquartzvariant.a() : BlockQuartz.EnumQuartzVariant.LINES_Y.a();
    }

    protected ItemStack i(IBlockData p_i_1_)
    {
        BlockQuartz.EnumQuartzVariant blockquartz$enumquartzvariant = (BlockQuartz.EnumQuartzVariant)p_i_1_.get(VARIANT);
        return blockquartz$enumquartzvariant != BlockQuartz.EnumQuartzVariant.LINES_X && blockquartz$enumquartzvariant != BlockQuartz.EnumQuartzVariant.LINES_Z ? super.i(p_i_1_) : new ItemStack(Item.getItemOf(this), 1, BlockQuartz.EnumQuartzVariant.LINES_Y.a());
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return MaterialMapColor.p;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(VARIANT, BlockQuartz.EnumQuartzVariant.a(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((BlockQuartz.EnumQuartzVariant)p_toLegacyData_1_.get(VARIANT)).a();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {VARIANT});
    }

    public static enum EnumQuartzVariant implements INamable
    {
        DEFAULT(0, "default", "default"),
        CHISELED(1, "chiseled", "chiseled"),
        LINES_Y(2, "lines_y", "lines"),
        LINES_X(3, "lines_x", "lines"),
        LINES_Z(4, "lines_z", "lines");

        private static final BlockQuartz.EnumQuartzVariant[] f = new BlockQuartz.EnumQuartzVariant[values().length];
        private final int g;
        private final String h;
        private final String i;

        private EnumQuartzVariant(int p_i635_3_, String p_i635_4_, String p_i635_5_)
        {
            this.g = p_i635_3_;
            this.h = p_i635_4_;
            this.i = p_i635_5_;
        }

        public int a()
        {
            return this.g;
        }

        public String toString()
        {
            return this.i;
        }

        public static BlockQuartz.EnumQuartzVariant a(int p_a_0_)
        {
            if (p_a_0_ < 0 || p_a_0_ >= f.length)
            {
                p_a_0_ = 0;
            }

            return f[p_a_0_];
        }

        public String getName()
        {
            return this.h;
        }

        static {
            for (BlockQuartz.EnumQuartzVariant blockquartz$enumquartzvariant : values())
            {
                f[blockquartz$enumquartzvariant.a()] = blockquartz$enumquartzvariant;
            }
        }
    }
}
