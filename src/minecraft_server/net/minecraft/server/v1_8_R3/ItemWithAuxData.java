package net.minecraft.server.v1_8_R3;

public class ItemWithAuxData extends ItemBlock
{
    private final Block b;
    private String[] c;

    public ItemWithAuxData(Block p_i519_1_, boolean p_i519_2_)
    {
        super(p_i519_1_);
        this.b = p_i519_1_;

        if (p_i519_2_)
        {
            this.setMaxDurability(0);
            this.a(true);
        }
    }

    public int filterData(int p_filterData_1_)
    {
        return p_filterData_1_;
    }

    public ItemWithAuxData a(String[] p_a_1_)
    {
        this.c = p_a_1_;
        return this;
    }

    public String e_(ItemStack p_e__1_)
    {
        if (this.c == null)
        {
            return super.e_(p_e__1_);
        }
        else
        {
            int i = p_e__1_.getData();
            return i >= 0 && i < this.c.length ? super.e_(p_e__1_) + "." + this.c[i] : super.e_(p_e__1_);
        }
    }
}
