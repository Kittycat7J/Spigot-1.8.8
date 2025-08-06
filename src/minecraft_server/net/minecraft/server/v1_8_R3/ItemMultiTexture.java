package net.minecraft.server.v1_8_R3;

import com.google.common.base.Function;

public class ItemMultiTexture extends ItemBlock
{
    protected final Block b;
    protected final Function<ItemStack, String> c;

    public ItemMultiTexture(Block p_i508_1_, Block p_i508_2_, Function<ItemStack, String> p_i508_3_)
    {
        super(p_i508_1_);
        this.b = p_i508_2_;
        this.c = p_i508_3_;
        this.setMaxDurability(0);
        this.a(true);
    }

    public ItemMultiTexture(Block p_i509_1_, Block p_i509_2_, final String[] p_i509_3_)
    {
        this(p_i509_1_, p_i509_2_, new Function<ItemStack, String>()
        {
            public String a(ItemStack p_a_1_)
            {
                int i = p_a_1_.getData();

                if (i < 0 || i >= p_i509_3_.length)
                {
                    i = 0;
                }

                return p_i509_3_[i];
            }
        });
    }

    public int filterData(int p_filterData_1_)
    {
        return p_filterData_1_;
    }

    public String e_(ItemStack p_e__1_)
    {
        return super.getName() + "." + (String)this.c.apply(p_e__1_);
    }
}
