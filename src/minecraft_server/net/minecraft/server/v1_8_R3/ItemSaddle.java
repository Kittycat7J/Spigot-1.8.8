package net.minecraft.server.v1_8_R3;

public class ItemSaddle extends Item
{
    public ItemSaddle()
    {
        this.maxStackSize = 1;
        this.a(CreativeModeTab.e);
    }

    public boolean a(ItemStack p_a_1_, EntityHuman p_a_2_, EntityLiving p_a_3_)
    {
        if (p_a_3_ instanceof EntityPig)
        {
            EntityPig entitypig = (EntityPig)p_a_3_;

            if (!entitypig.hasSaddle() && !entitypig.isBaby())
            {
                entitypig.setSaddle(true);
                entitypig.world.makeSound(entitypig, "mob.horse.leather", 0.5F, 1.0F);
                --p_a_1_.count;
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean a(ItemStack p_a_1_, EntityLiving p_a_2_, EntityLiving p_a_3_)
    {
        this.a(p_a_1_, (EntityHuman)null, p_a_2_);
        return true;
    }
}
