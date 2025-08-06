package net.minecraft.server.v1_8_R3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MobEffect
{
    private static final Logger a = LogManager.getLogger();
    private int effectId;
    private int duration;
    private int amplification;
    private boolean splash;
    private boolean ambient;
    private boolean particles;

    public MobEffect(int p_i1140_1_, int p_i1140_2_)
    {
        this(p_i1140_1_, p_i1140_2_, 0);
    }

    public MobEffect(int p_i1141_1_, int p_i1141_2_, int p_i1141_3_)
    {
        this(p_i1141_1_, p_i1141_2_, p_i1141_3_, false, true);
    }

    public MobEffect(int p_i1142_1_, int p_i1142_2_, int p_i1142_3_, boolean p_i1142_4_, boolean p_i1142_5_)
    {
        this.effectId = p_i1142_1_;
        this.duration = p_i1142_2_;
        this.amplification = p_i1142_3_;
        this.ambient = p_i1142_4_;
        this.particles = p_i1142_5_;
    }

    public MobEffect(MobEffect p_i1143_1_)
    {
        this.effectId = p_i1143_1_.effectId;
        this.duration = p_i1143_1_.duration;
        this.amplification = p_i1143_1_.amplification;
        this.ambient = p_i1143_1_.ambient;
        this.particles = p_i1143_1_.particles;
    }

    public void a(MobEffect p_a_1_)
    {
        if (this.effectId != p_a_1_.effectId)
        {
            a.warn("This method should only be called for matching effects!");
        }

        if (p_a_1_.amplification > this.amplification)
        {
            this.amplification = p_a_1_.amplification;
            this.duration = p_a_1_.duration;
        }
        else if (p_a_1_.amplification == this.amplification && this.duration < p_a_1_.duration)
        {
            this.duration = p_a_1_.duration;
        }
        else if (!p_a_1_.ambient && this.ambient)
        {
            this.ambient = p_a_1_.ambient;
        }

        this.particles = p_a_1_.particles;
    }

    public int getEffectId()
    {
        return this.effectId;
    }

    public int getDuration()
    {
        return this.duration;
    }

    public int getAmplifier()
    {
        return this.amplification;
    }

    public void setSplash(boolean p_setSplash_1_)
    {
        this.splash = p_setSplash_1_;
    }

    public boolean isAmbient()
    {
        return this.ambient;
    }

    public boolean isShowParticles()
    {
        return this.particles;
    }

    public boolean tick(EntityLiving p_tick_1_)
    {
        if (this.duration > 0)
        {
            if (MobEffectList.byId[this.effectId].a(this.duration, this.amplification))
            {
                this.b(p_tick_1_);
            }

            this.i();
        }

        return this.duration > 0;
    }

    private int i()
    {
        return --this.duration;
    }

    public void b(EntityLiving p_b_1_)
    {
        if (this.duration > 0)
        {
            MobEffectList.byId[this.effectId].tick(p_b_1_, this.amplification);
        }
    }

    public String g()
    {
        return MobEffectList.byId[this.effectId].a();
    }

    public int hashCode()
    {
        return this.effectId;
    }

    public String toString()
    {
        String s = "";

        if (this.getAmplifier() > 0)
        {
            s = this.g() + " x " + (this.getAmplifier() + 1) + ", Duration: " + this.getDuration();
        }
        else
        {
            s = this.g() + ", Duration: " + this.getDuration();
        }

        if (this.splash)
        {
            s = s + ", Splash: true";
        }

        if (!this.particles)
        {
            s = s + ", Particles: false";
        }

        return MobEffectList.byId[this.effectId].j() ? "(" + s + ")" : s;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (!(p_equals_1_ instanceof MobEffect))
        {
            return false;
        }
        else
        {
            MobEffect mobeffect = (MobEffect)p_equals_1_;
            return this.effectId == mobeffect.effectId && this.amplification == mobeffect.amplification && this.duration == mobeffect.duration && this.splash == mobeffect.splash && this.ambient == mobeffect.ambient;
        }
    }

    public NBTTagCompound a(NBTTagCompound p_a_1_)
    {
        p_a_1_.setByte("Id", (byte)this.getEffectId());
        p_a_1_.setByte("Amplifier", (byte)this.getAmplifier());
        p_a_1_.setInt("Duration", this.getDuration());
        p_a_1_.setBoolean("Ambient", this.isAmbient());
        p_a_1_.setBoolean("ShowParticles", this.isShowParticles());
        return p_a_1_;
    }

    public static MobEffect b(NBTTagCompound p_b_0_)
    {
        byte b0 = p_b_0_.getByte("Id");

        if (b0 >= 0 && b0 < MobEffectList.byId.length && MobEffectList.byId[b0] != null)
        {
            byte b1 = p_b_0_.getByte("Amplifier");
            int i = p_b_0_.getInt("Duration");
            boolean flag = p_b_0_.getBoolean("Ambient");
            boolean flag1 = true;

            if (p_b_0_.hasKeyOfType("ShowParticles", 1))
            {
                flag1 = p_b_0_.getBoolean("ShowParticles");
            }

            return new MobEffect(b0, i, b1, flag, flag1);
        }
        else
        {
            return null;
        }
    }
}
