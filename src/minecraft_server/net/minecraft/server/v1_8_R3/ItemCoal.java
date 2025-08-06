package net.minecraft.server.v1_8_R3;

public class ItemCoal extends Item
{
    public ItemCoal()
    {
        this.a(true);
        this.setMaxDurability(0);
        this.a(CreativeModeTab.l);
    }

    public String e_(ItemStack p_e__1_)
    {
        return p_e__1_.getData() == 1 ? "item.charcoal" : "item.coal";
    }
}
