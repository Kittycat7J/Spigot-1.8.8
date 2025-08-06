package net.minecraft.server.v1_8_R3;

public class ItemCloth extends ItemBlock
{
    public ItemCloth(Block p_i1275_1_)
    {
        super(p_i1275_1_);
        this.setMaxDurability(0);
        this.a(true);
    }

    public int filterData(int p_filterData_1_)
    {
        return p_filterData_1_;
    }

    public String e_(ItemStack p_e__1_)
    {
        return super.getName() + "." + EnumColor.fromColorIndex(p_e__1_.getData()).d();
    }
}
