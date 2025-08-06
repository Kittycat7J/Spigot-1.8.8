package net.minecraft.server.v1_8_R3;

import java.util.Random;

public class BlockStone extends Block
{
    public static final BlockStateEnum<BlockStone.EnumStoneVariant> VARIANT = BlockStateEnum.<BlockStone.EnumStoneVariant>of("variant", BlockStone.EnumStoneVariant.class);

    public BlockStone()
    {
        super(Material.STONE);
        this.j(this.blockStateList.getBlockData().set(VARIANT, BlockStone.EnumStoneVariant.STONE));
        this.a(CreativeModeTab.b);
    }

    public String getName()
    {
        return LocaleI18n.get(this.a() + "." + BlockStone.EnumStoneVariant.STONE.d() + ".name");
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return ((BlockStone.EnumStoneVariant)p_g_1_.get(VARIANT)).c();
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return p_getDropType_1_.get(VARIANT) == BlockStone.EnumStoneVariant.STONE ? Item.getItemOf(Blocks.COBBLESTONE) : Item.getItemOf(Blocks.STONE);
    }

    public int getDropData(IBlockData p_getDropData_1_)
    {
        return ((BlockStone.EnumStoneVariant)p_getDropData_1_.get(VARIANT)).a();
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(VARIANT, BlockStone.EnumStoneVariant.a(p_fromLegacyData_1_));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((BlockStone.EnumStoneVariant)p_toLegacyData_1_.get(VARIANT)).a();
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {VARIANT});
    }

    public static enum EnumStoneVariant implements INamable
    {
        STONE(0, MaterialMapColor.m, "stone"),
        GRANITE(1, MaterialMapColor.l, "granite"),
        GRANITE_SMOOTH(2, MaterialMapColor.l, "smooth_granite", "graniteSmooth"),
        DIORITE(3, MaterialMapColor.p, "diorite"),
        DIORITE_SMOOTH(4, MaterialMapColor.p, "smooth_diorite", "dioriteSmooth"),
        ANDESITE(5, MaterialMapColor.m, "andesite"),
        ANDESITE_SMOOTH(6, MaterialMapColor.m, "smooth_andesite", "andesiteSmooth");

        private static final BlockStone.EnumStoneVariant[] h = new BlockStone.EnumStoneVariant[values().length];
        private final int i;
        private final String j;
        private final String k;
        private final MaterialMapColor l;

        private EnumStoneVariant(int p_i647_3_, MaterialMapColor p_i647_4_, String p_i647_5_)
        {
            this(p_i647_3_, p_i647_4_, p_i647_5_, p_i647_5_);
        }

        private EnumStoneVariant(int p_i648_3_, MaterialMapColor p_i648_4_, String p_i648_5_, String p_i648_6_)
        {
            this.i = p_i648_3_;
            this.j = p_i648_5_;
            this.k = p_i648_6_;
            this.l = p_i648_4_;
        }

        public int a()
        {
            return this.i;
        }

        public MaterialMapColor c()
        {
            return this.l;
        }

        public String toString()
        {
            return this.j;
        }

        public static BlockStone.EnumStoneVariant a(int p_a_0_)
        {
            if (p_a_0_ < 0 || p_a_0_ >= h.length)
            {
                p_a_0_ = 0;
            }

            return h[p_a_0_];
        }

        public String getName()
        {
            return this.j;
        }

        public String d()
        {
            return this.k;
        }

        static {
            for (BlockStone.EnumStoneVariant blockstone$enumstonevariant : values())
            {
                h[blockstone$enumstonevariant.a()] = blockstone$enumstonevariant;
            }
        }
    }
}
