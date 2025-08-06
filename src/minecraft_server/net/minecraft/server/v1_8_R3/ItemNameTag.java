package net.minecraft.server.v1_8_R3;

public class ItemNameTag extends Item
{
    public ItemNameTag()
    {
        this.a(CreativeModeTab.i);
    }

    public boolean a(ItemStack p_a_1_, EntityHuman p_a_2_, EntityLiving p_a_3_)
    {
        if (!p_a_1_.hasName())
        {
            return false;
        }
        else if (p_a_3_ instanceof EntityInsentient)
        {
            EntityInsentient entityinsentient = (EntityInsentient)p_a_3_;
            entityinsentient.setCustomName(p_a_1_.getName());
            entityinsentient.bX();
            --p_a_1_.count;
            return true;
        }
        else
        {
            return super.a(p_a_1_, p_a_2_, p_a_3_);
        }
    }
}
