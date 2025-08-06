package net.minecraft.server.v1_8_R3;

public class ItemLeaves extends ItemBlock
{
    private final BlockLeaves b;

    public ItemLeaves(BlockLeaves p_i506_1_)
    {
        super(p_i506_1_);
        this.b = p_i506_1_;
        this.setMaxDurability(0);
        this.a(true);
    }

    public int filterData(int p_filterData_1_)
    {
        return p_filterData_1_ | 4;
    }

    public String e_(ItemStack p_e__1_)
    {
        return super.getName() + "." + this.b.b(p_e__1_.getData()).d();
    }
}
