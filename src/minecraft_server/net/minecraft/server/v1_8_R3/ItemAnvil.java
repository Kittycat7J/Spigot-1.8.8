package net.minecraft.server.v1_8_R3;

public class ItemAnvil extends ItemMultiTexture
{
    public ItemAnvil(Block p_i1254_1_)
    {
        super(p_i1254_1_, p_i1254_1_, new String[] {"intact", "slightlyDamaged", "veryDamaged"});
    }

    public int filterData(int p_filterData_1_)
    {
        return p_filterData_1_ << 2;
    }
}
