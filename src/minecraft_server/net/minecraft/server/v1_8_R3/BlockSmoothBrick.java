package net.minecraft.server.v1_8_R3;

public class BlockSmoothBrick extends Block
{
    public static final BlockStateEnum<BlockSmoothBrick.EnumStonebrickType> VARIANT = BlockStateEnum.<BlockSmoothBrick.EnumStonebrickType>of("variant", BlockSmoothBrick.EnumStonebrickType.class);
    public static final int b = BlockSmoothBrick.EnumStonebrickType.DEFAULT.a();
    public static final int N = BlockSmoothBrick.EnumStonebrickType.MOSSY.a();
    public static final int O = BlockSmoothBrick.EnumStonebrickType.CRACKED.a();
    public static final int P = BlockSmoothBrick.EnumStonebrickType.CHISELED.a();

    public BlockSmoothBrick()
    {
        super(Material.STONE);
        this.j(this.blockStateList.getBlockData().set(VARIANT, BlockSmoothBrick.EnumStonebrickType.DEFAULT));
        this.a(CreativeModeTab.b);
    }

    public int getDropData(IBlockData p_getDropData_1_)
    {
        return ((BlockSmoothBrick.EnumStonebrickType)p_getDropData_1_.get(VARIANT)).a();
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(VARIANT, BlockSmoothBrick.EnumStonebrickType.a(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((BlockSmoothBrick.EnumStonebrickType)p_toLegacyData_1_.get(VARIANT)).a();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {VARIANT});
    }

    public static enum EnumStonebrickType implements INamable
    {
        DEFAULT(0, "stonebrick", "default"),
        MOSSY(1, "mossy_stonebrick", "mossy"),
        CRACKED(2, "cracked_stonebrick", "cracked"),
        CHISELED(3, "chiseled_stonebrick", "chiseled");

        private static final BlockSmoothBrick.EnumStonebrickType[] e = new BlockSmoothBrick.EnumStonebrickType[values().length];
        private final int f;
        private final String g;
        private final String h;

        private EnumStonebrickType(int p_i649_3_, String p_i649_4_, String p_i649_5_)
        {
            this.f = p_i649_3_;
            this.g = p_i649_4_;
            this.h = p_i649_5_;
        }

        public int a()
        {
            return this.f;
        }

        public String toString()
        {
            return this.g;
        }

        public static BlockSmoothBrick.EnumStonebrickType a(int p_a_0_)
        {
            if (p_a_0_ < 0 || p_a_0_ >= e.length)
            {
                p_a_0_ = 0;
            }

            return e[p_a_0_];
        }

        public String getName()
        {
            return this.g;
        }

        public String c()
        {
            return this.h;
        }

        static {
            for (BlockSmoothBrick.EnumStonebrickType blocksmoothbrick$enumstonebricktype : values())
            {
                e[blocksmoothbrick$enumstonebricktype.a()] = blocksmoothbrick$enumstonebricktype;
            }
        }
    }
}
