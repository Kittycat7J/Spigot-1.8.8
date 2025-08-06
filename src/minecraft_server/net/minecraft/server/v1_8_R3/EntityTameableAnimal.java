package net.minecraft.server.v1_8_R3;

import java.util.UUID;

public abstract class EntityTameableAnimal extends EntityAnimal implements EntityOwnable
{
    protected PathfinderGoalSit bm = new PathfinderGoalSit(this);

    public EntityTameableAnimal(World p_i1149_1_)
    {
        super(p_i1149_1_);
        this.cm();
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(16, Byte.valueOf((byte)0));
        this.datawatcher.a(17, "");
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);

        if (this.getOwnerUUID() == null)
        {
            p_b_1_.setString("OwnerUUID", "");
        }
        else
        {
            p_b_1_.setString("OwnerUUID", this.getOwnerUUID());
        }

        p_b_1_.setBoolean("Sitting", this.isSitting());
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        String s = "";

        if (p_a_1_.hasKeyOfType("OwnerUUID", 8))
        {
            s = p_a_1_.getString("OwnerUUID");
        }
        else
        {
            String s1 = p_a_1_.getString("Owner");
            s = NameReferencingFileConverter.a(s1);
        }

        if (s.length() > 0)
        {
            this.setOwnerUUID(s);
            this.setTamed(true);
        }

        this.bm.setSitting(p_a_1_.getBoolean("Sitting"));
        this.setSitting(p_a_1_.getBoolean("Sitting"));
    }

    protected void l(boolean p_l_1_)
    {
        EnumParticle enumparticle = EnumParticle.HEART;

        if (!p_l_1_)
        {
            enumparticle = EnumParticle.SMOKE_NORMAL;
        }

        for (int i = 0; i < 7; ++i)
        {
            double d0 = this.random.nextGaussian() * 0.02D;
            double d1 = this.random.nextGaussian() * 0.02D;
            double d2 = this.random.nextGaussian() * 0.02D;
            this.world.addParticle(enumparticle, this.locX + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, this.locY + 0.5D + (double)(this.random.nextFloat() * this.length), this.locZ + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, d0, d1, d2, new int[0]);
        }
    }

    public boolean isTamed()
    {
        return (this.datawatcher.getByte(16) & 4) != 0;
    }

    public void setTamed(boolean p_setTamed_1_)
    {
        byte b0 = this.datawatcher.getByte(16);

        if (p_setTamed_1_)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 4)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & -5)));
        }

        this.cm();
    }

    protected void cm()
    {
    }

    public boolean isSitting()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void setSitting(boolean p_setSitting_1_)
    {
        byte b0 = this.datawatcher.getByte(16);

        if (p_setSitting_1_)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & -2)));
        }
    }

    public String getOwnerUUID()
    {
        return this.datawatcher.getString(17);
    }

    public void setOwnerUUID(String p_setOwnerUUID_1_)
    {
        this.datawatcher.watch(17, p_setOwnerUUID_1_);
    }

    public EntityLiving getOwner()
    {
        try
        {
            UUID uuid = UUID.fromString(this.getOwnerUUID());
            return uuid == null ? null : this.world.b(uuid);
        }
        catch (IllegalArgumentException var2)
        {
            return null;
        }
    }

    public boolean e(EntityLiving p_e_1_)
    {
        return p_e_1_ == this.getOwner();
    }

    public PathfinderGoalSit getGoalSit()
    {
        return this.bm;
    }

    public boolean a(EntityLiving p_a_1_, EntityLiving p_a_2_)
    {
        return true;
    }

    public ScoreboardTeamBase getScoreboardTeam()
    {
        if (this.isTamed())
        {
            EntityLiving entityliving = this.getOwner();

            if (entityliving != null)
            {
                return entityliving.getScoreboardTeam();
            }
        }

        return super.getScoreboardTeam();
    }

    public boolean c(EntityLiving p_c_1_)
    {
        if (this.isTamed())
        {
            EntityLiving entityliving = this.getOwner();

            if (p_c_1_ == entityliving)
            {
                return true;
            }

            if (entityliving != null)
            {
                return entityliving.c(p_c_1_);
            }
        }

        return super.c(p_c_1_);
    }

    public void die(DamageSource p_die_1_)
    {
        if (!this.world.isClientSide && this.world.getGameRules().getBoolean("showDeathMessages") && this.hasCustomName() && this.getOwner() instanceof EntityPlayer)
        {
            ((EntityPlayer)this.getOwner()).sendMessage(this.bs().b());
        }

        super.die(p_die_1_);
    }
}
