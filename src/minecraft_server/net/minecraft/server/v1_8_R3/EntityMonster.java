package net.minecraft.server.v1_8_R3;

import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityCombustByEntityEvent;

public abstract class EntityMonster extends EntityCreature implements IMonster
{
    public EntityMonster(World p_i248_1_)
    {
        super(p_i248_1_);
        this.b_ = 5;
    }

    public void m()
    {
        this.bx();
        float f = this.c(1.0F);

        if (f > 0.5F)
        {
            this.ticksFarFromPlayer += 2;
        }

        super.m();
    }

    public void t_()
    {
        super.t_();

        if (!this.world.isClientSide && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.die();
        }
    }

    protected String P()
    {
        return "game.hostile.swim";
    }

    protected String aa()
    {
        return "game.hostile.swim.splash";
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        if (this.isInvulnerable(p_damageEntity_1_))
        {
            return false;
        }
        else if (!super.damageEntity(p_damageEntity_1_, p_damageEntity_2_))
        {
            return false;
        }
        else
        {
            Entity entity = p_damageEntity_1_.getEntity();
            return this.passenger != entity && this.vehicle != entity ? true : true;
        }
    }

    protected String bo()
    {
        return "game.hostile.hurt";
    }

    protected String bp()
    {
        return "game.hostile.die";
    }

    protected String n(int p_n_1_)
    {
        return p_n_1_ > 4 ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
    }

    public boolean r(Entity p_r_1_)
    {
        float f = (float)this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
        int i = 0;

        if (p_r_1_ instanceof EntityLiving)
        {
            f += EnchantmentManager.a(this.bA(), ((EntityLiving)p_r_1_).getMonsterType());
            i += EnchantmentManager.a((EntityLiving)this);
        }

        boolean flag = p_r_1_.damageEntity(DamageSource.mobAttack(this), f);

        if (flag)
        {
            if (i > 0)
            {
                p_r_1_.g((double)(-MathHelper.sin(this.yaw * (float)Math.PI / 180.0F) * (float)i * 0.5F), 0.1D, (double)(MathHelper.cos(this.yaw * (float)Math.PI / 180.0F) * (float)i * 0.5F));
                this.motX *= 0.6D;
                this.motZ *= 0.6D;
            }

            int j = EnchantmentManager.getFireAspectEnchantmentLevel(this);

            if (j > 0)
            {
                EntityCombustByEntityEvent entitycombustbyentityevent = new EntityCombustByEntityEvent(this.getBukkitEntity(), p_r_1_.getBukkitEntity(), j * 4);
                Bukkit.getPluginManager().callEvent(entitycombustbyentityevent);

                if (!entitycombustbyentityevent.isCancelled())
                {
                    p_r_1_.setOnFire(entitycombustbyentityevent.getDuration());
                }
            }

            this.a(this, p_r_1_);
        }

        return flag;
    }

    public float a(BlockPosition p_a_1_)
    {
        return 0.5F - this.world.o(p_a_1_);
    }

    protected boolean n_()
    {
        BlockPosition blockposition = new BlockPosition(this.locX, this.getBoundingBox().b, this.locZ);

        if (this.world.b(EnumSkyBlock.SKY, blockposition) > this.random.nextInt(32))
        {
            return false;
        }
        else
        {
            int i = this.world.getLightLevel(blockposition);

            if (this.world.R())
            {
                int j = this.world.ab();
                this.world.c(10);
                i = this.world.getLightLevel(blockposition);
                this.world.c(j);
            }

            return i <= this.random.nextInt(8);
        }
    }

    public boolean bR()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.n_() && super.bR();
    }

    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeMap().b(GenericAttributes.ATTACK_DAMAGE);
    }

    protected boolean ba()
    {
        return true;
    }
}
