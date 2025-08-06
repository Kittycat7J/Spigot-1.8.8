package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.ExpBottleEvent;

public class EntityThrownExpBottle extends EntityProjectile
{
    public EntityThrownExpBottle(World p_i19_1_)
    {
        super(p_i19_1_);
    }

    public EntityThrownExpBottle(World p_i20_1_, EntityLiving p_i20_2_)
    {
        super(p_i20_1_, p_i20_2_);
    }

    public EntityThrownExpBottle(World p_i21_1_, double p_i21_2_, double p_i21_4_, double p_i21_6_)
    {
        super(p_i21_1_, p_i21_2_, p_i21_4_, p_i21_6_);
    }

    protected float m()
    {
        return 0.07F;
    }

    protected float j()
    {
        return 0.7F;
    }

    protected float l()
    {
        return -20.0F;
    }

    protected void a(MovingObjectPosition p_a_1_)
    {
        if (!this.world.isClientSide)
        {
            int i = 3 + this.world.random.nextInt(5) + this.world.random.nextInt(5);
            ExpBottleEvent expbottleevent = CraftEventFactory.callExpBottleEvent(this, i);
            i = expbottleevent.getExperience();

            if (expbottleevent.getShowEffect())
            {
                this.world.triggerEffect(2002, new BlockPosition(this), 0);
            }

            while (i > 0)
            {
                int j = EntityExperienceOrb.getOrbValue(i);
                i -= j;
                this.world.addEntity(new EntityExperienceOrb(this.world, this.locX, this.locY, this.locZ, j));
            }

            this.die();
        }
    }
}
