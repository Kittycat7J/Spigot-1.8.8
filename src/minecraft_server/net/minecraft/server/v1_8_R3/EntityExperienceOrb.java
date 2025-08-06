package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityExperienceOrb extends Entity
{
    public int a;
    public int b;
    public int c;
    private int d = 5;
    public int value;
    private EntityHuman targetPlayer;
    private int targetTime;

    public EntityExperienceOrb(World p_i4_1_, double p_i4_2_, double p_i4_4_, double p_i4_6_, int p_i4_8_)
    {
        super(p_i4_1_);
        this.setSize(0.5F, 0.5F);
        this.setPosition(p_i4_2_, p_i4_4_, p_i4_6_);
        this.yaw = (float)(Math.random() * 360.0D);
        this.motX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.motY = (double)((float)(Math.random() * 0.2D) * 2.0F);
        this.motZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D) * 2.0F);
        this.value = p_i4_8_;
    }

    protected boolean s_()
    {
        return false;
    }

    public EntityExperienceOrb(World p_i5_1_)
    {
        super(p_i5_1_);
        this.setSize(0.25F, 0.25F);
    }

    protected void h()
    {
    }

    public void t_()
    {
        super.t_();
        EntityHuman entityhuman = this.targetPlayer;

        if (this.c > 0)
        {
            --this.c;
        }

        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.motY -= 0.029999999329447746D;

        if (this.world.getType(new BlockPosition(this)).getBlock().getMaterial() == Material.LAVA)
        {
            this.motY = 0.20000000298023224D;
            this.motX = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            this.motZ = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
            this.makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
        }

        this.j(this.locX, (this.getBoundingBox().b + this.getBoundingBox().e) / 2.0D, this.locZ);
        double d0 = 8.0D;

        if (this.targetTime < this.a - 20 + this.getId() % 100)
        {
            if (this.targetPlayer == null || this.targetPlayer.h(this) > d0 * d0)
            {
                this.targetPlayer = this.world.findNearbyPlayer(this, d0);
            }

            this.targetTime = this.a;
        }

        if (this.targetPlayer != null && this.targetPlayer.isSpectator())
        {
            this.targetPlayer = null;
        }

        if (this.targetPlayer != null)
        {
            boolean flag = false;

            if (this.targetPlayer != entityhuman)
            {
                EntityTargetLivingEntityEvent entitytargetlivingentityevent = CraftEventFactory.callEntityTargetLivingEvent(this, this.targetPlayer, TargetReason.CLOSEST_PLAYER);
                EntityLiving entityliving = entitytargetlivingentityevent.getTarget() == null ? null : ((CraftLivingEntity)entitytargetlivingentityevent.getTarget()).getHandle();
                this.targetPlayer = entityliving instanceof EntityHuman ? (EntityHuman)entityliving : null;
                flag = entitytargetlivingentityevent.isCancelled();
            }

            if (!flag && this.targetPlayer != null)
            {
                double d1 = (this.targetPlayer.locX - this.locX) / d0;
                double d2 = (this.targetPlayer.locY + (double)this.targetPlayer.getHeadHeight() - this.locY) / d0;
                double d3 = (this.targetPlayer.locZ - this.locZ) / d0;
                double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
                double d5 = 1.0D - d4;

                if (d5 > 0.0D)
                {
                    d5 = d5 * d5;
                    this.motX += d1 / d4 * d5 * 0.1D;
                    this.motY += d2 / d4 * d5 * 0.1D;
                    this.motZ += d3 / d4 * d5 * 0.1D;
                }
            }
        }

        this.move(this.motX, this.motY, this.motZ);
        float f = 0.98F;

        if (this.onGround)
        {
            f = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.98F;
        }

        this.motX *= (double)f;
        this.motY *= 0.9800000190734863D;
        this.motZ *= (double)f;

        if (this.onGround)
        {
            this.motY *= -0.8999999761581421D;
        }

        ++this.a;
        ++this.b;

        if (this.b >= 6000)
        {
            this.die();
        }
    }

    public boolean W()
    {
        return this.world.a((AxisAlignedBB)this.getBoundingBox(), (Material)Material.WATER, (Entity)this);
    }

    protected void burn(int p_burn_1_)
    {
        this.damageEntity(DamageSource.FIRE, (float)p_burn_1_);
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        if (this.isInvulnerable(p_damageEntity_1_))
        {
            return false;
        }
        else
        {
            this.ac();
            this.d = (int)((float)this.d - p_damageEntity_2_);

            if (this.d <= 0)
            {
                this.die();
            }

            return false;
        }
    }

    public void b(NBTTagCompound p_b_1_)
    {
        p_b_1_.setShort("Health", (short)((byte)this.d));
        p_b_1_.setShort("Age", (short)this.b);
        p_b_1_.setShort("Value", (short)this.value);
    }

    public void a(NBTTagCompound p_a_1_)
    {
        this.d = p_a_1_.getShort("Health") & 255;
        this.b = p_a_1_.getShort("Age");
        this.value = p_a_1_.getShort("Value");
    }

    public void d(EntityHuman p_d_1_)
    {
        if (!this.world.isClientSide && this.c == 0 && p_d_1_.bp == 0)
        {
            p_d_1_.bp = 2;
            this.world.makeSound(p_d_1_, "random.orb", 0.1F, 0.5F * ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.8F));
            p_d_1_.receive(this, 1);
            p_d_1_.giveExp(CraftEventFactory.callPlayerExpChangeEvent(p_d_1_, this.value).getAmount());
            this.die();
        }
    }

    public int j()
    {
        return this.value;
    }

    public static int getOrbValue(int p_getOrbValue_0_)
    {
        return p_getOrbValue_0_ > 162670129 ? p_getOrbValue_0_ - 100000 : (p_getOrbValue_0_ > 81335063 ? 81335063 : (p_getOrbValue_0_ > 40667527 ? 40667527 : (p_getOrbValue_0_ > 20333759 ? 20333759 : (p_getOrbValue_0_ > 10166857 ? 10166857 : (p_getOrbValue_0_ > 5083423 ? 5083423 : (p_getOrbValue_0_ > 2541701 ? 2541701 : (p_getOrbValue_0_ > 1270849 ? 1270849 : (p_getOrbValue_0_ > 635413 ? 635413 : (p_getOrbValue_0_ > 317701 ? 317701 : (p_getOrbValue_0_ > 158849 ? 158849 : (p_getOrbValue_0_ > 79423 ? 79423 : (p_getOrbValue_0_ > 39709 ? 39709 : (p_getOrbValue_0_ > 19853 ? 19853 : (p_getOrbValue_0_ > 9923 ? 9923 : (p_getOrbValue_0_ > 4957 ? 4957 : (p_getOrbValue_0_ >= 2477 ? 2477 : (p_getOrbValue_0_ >= 1237 ? 1237 : (p_getOrbValue_0_ >= 617 ? 617 : (p_getOrbValue_0_ >= 307 ? 307 : (p_getOrbValue_0_ >= 149 ? 149 : (p_getOrbValue_0_ >= 73 ? 73 : (p_getOrbValue_0_ >= 37 ? 37 : (p_getOrbValue_0_ >= 17 ? 17 : (p_getOrbValue_0_ >= 7 ? 7 : (p_getOrbValue_0_ >= 3 ? 3 : 1)))))))))))))))))))))))));
    }

    public boolean aD()
    {
        return false;
    }
}
