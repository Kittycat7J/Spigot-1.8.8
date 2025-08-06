package net.minecraft.server.v1_8_R3;

public abstract class BlockRotatable extends Block
{
    public static final BlockStateEnum<EnumDirection.EnumAxis> AXIS = BlockStateEnum.<EnumDirection.EnumAxis>of("axis", EnumDirection.EnumAxis.class);

    protected BlockRotatable(Material p_i638_1_)
    {
        super(p_i638_1_, p_i638_1_.r());
    }

    protected BlockRotatable(Material p_i639_1_, MaterialMapColor p_i639_2_)
    {
        super(p_i639_1_, p_i639_2_);
    }
}
