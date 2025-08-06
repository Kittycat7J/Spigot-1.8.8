package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Explosive;
import org.bukkit.event.entity.ExplosionPrimeEvent;

public class EntityLargeFireball extends EntityFireball
{
    public int yield = 1;

    public EntityLargeFireball(World p_i367_1_)
    {
        super(p_i367_1_);
    }

    public EntityLargeFireball(World p_i368_1_, EntityLiving p_i368_2_, double p_i368_3_, double p_i368_5_, double p_i368_7_)
    {
        super(p_i368_1_, p_i368_2_, p_i368_3_, p_i368_5_, p_i368_7_);
    }

    protected void a(MovingObjectPosition p_a_1_)
    {
        if (!this.world.isClientSide)
        {
            if (p_a_1_.entity != null)
            {
                p_a_1_.entity.damageEntity(DamageSource.fireball(this, this.shooter), 6.0F);
                this.a(this.shooter, p_a_1_.entity);
            }

            boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
            ExplosionPrimeEvent explosionprimeevent = new ExplosionPrimeEvent((Explosive)CraftEntity.getEntity(this.world.getServer(), this));
            this.world.getServer().getPluginManager().callEvent(explosionprimeevent);

            if (!explosionprimeevent.isCancelled())
            {
                this.world.createExplosion(this, this.locX, this.locY, this.locZ, explosionprimeevent.getRadius(), explosionprimeevent.getFire(), flag);
            }

            this.die();
        }
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setInt("ExplosionPower", this.yield);
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);

        if (p_a_1_.hasKeyOfType("ExplosionPower", 99))
        {
            this.bukkitYield = (float)(this.yield = p_a_1_.getInt("ExplosionPower"));
        }
    }
}
