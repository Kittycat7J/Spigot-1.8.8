package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockHugeMushroom extends Block
{
    public static final BlockStateEnum<BlockHugeMushroom.EnumHugeMushroomVariant> VARIANT = BlockStateEnum.<BlockHugeMushroom.EnumHugeMushroomVariant>of("variant", BlockHugeMushroom.EnumHugeMushroomVariant.class);
    private final Block b;

    public BlockHugeMushroom(Material p_i627_1_, MaterialMapColor p_i627_2_, Block p_i627_3_)
    {
        super(p_i627_1_, p_i627_2_);
        this.j(this.blockStateList.getBlockData().set(VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.ALL_OUTSIDE));
        this.b = p_i627_3_;
    }

    public int a(Random p_a_1_)
    {
        return Math.max(0, p_a_1_.nextInt(10) - 7);
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        switch ((BlockHugeMushroom.EnumHugeMushroomVariant)p_g_1_.get(VARIANT))
        {
            case ALL_STEM:
                return MaterialMapColor.e;

            case ALL_INSIDE:
                return MaterialMapColor.d;

            case STEM:
                return MaterialMapColor.d;

            default:
                return super.g(p_g_1_);
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Item.getItemOf(this.b);
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        return this.getBlockData();
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(VARIANT, BlockHugeMushroom.EnumHugeMushroomVariant.a(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((BlockHugeMushroom.EnumHugeMushroomVariant)p_toLegacyData_1_.get(VARIANT)).a();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {VARIANT});
    }

    public static enum EnumHugeMushroomVariant implements INamable
    {
        NORTH_WEST(1, "north_west"),
        NORTH(2, "north"),
        NORTH_EAST(3, "north_east"),
        WEST(4, "west"),
        CENTER(5, "center"),
        EAST(6, "east"),
        SOUTH_WEST(7, "south_west"),
        SOUTH(8, "south"),
        SOUTH_EAST(9, "south_east"),
        STEM(10, "stem"),
        ALL_INSIDE(0, "all_inside"),
        ALL_OUTSIDE(14, "all_outside"),
        ALL_STEM(15, "all_stem");

        private static final BlockHugeMushroom.EnumHugeMushroomVariant[] n = new BlockHugeMushroom.EnumHugeMushroomVariant[16];
        private final int o;
        private final String p;

        private EnumHugeMushroomVariant(int p_i626_3_, String p_i626_4_)
        {
            this.o = p_i626_3_;
            this.p = p_i626_4_;
        }

        public int a()
        {
            return this.o;
        }

        public String toString()
        {
            return this.p;
        }

        public static BlockHugeMushroom.EnumHugeMushroomVariant a(int p_a_0_)
        {
            if (p_a_0_ < 0 || p_a_0_ >= n.length)
            {
                p_a_0_ = 0;
            }

            BlockHugeMushroom.EnumHugeMushroomVariant blockhugemushroom$enumhugemushroomvariant = n[p_a_0_];
            return blockhugemushroom$enumhugemushroomvariant == null ? n[0] : blockhugemushroom$enumhugemushroomvariant;
        }

        public String getName()
        {
            return this.p;
        }

        static {
            for (BlockHugeMushroom.EnumHugeMushroomVariant blockhugemushroom$enumhugemushroomvariant : values())
            {
                n[blockhugemushroom$enumhugemushroomvariant.a()] = blockhugemushroom$enumhugemushroomvariant;
            }
        }
    }
}
