package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class EntityFireworks extends Entity
{
    private int ticksFlown;
    public int expectedLifespan;

    public void inactiveTick()
    {
        ++this.ticksFlown;
        super.inactiveTick();
    }

    public EntityFireworks(World p_i345_1_)
    {
        super(p_i345_1_);
        this.setSize(0.25F, 0.25F);
    }

    protected void h()
    {
        this.datawatcher.add(8, 5);
    }

    public EntityFireworks(World p_i346_1_, double p_i346_2_, double p_i346_4_, double p_i346_6_, ItemStack p_i346_8_)
    {
        super(p_i346_1_);
        this.ticksFlown = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(p_i346_2_, p_i346_4_, p_i346_6_);
        int i = 1;

        if (p_i346_8_ != null && p_i346_8_.hasTag())
        {
            this.datawatcher.watch(8, p_i346_8_);
            NBTTagCompound nbttagcompound = p_i346_8_.getTag();
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("Fireworks");

            if (nbttagcompound1 != null)
            {
                i += nbttagcompound1.getByte("Flight");
            }
        }

        this.motX = this.random.nextGaussian() * 0.001D;
        this.motZ = this.random.nextGaussian() * 0.001D;
        this.motY = 0.05D;
        this.expectedLifespan = 10 * i + this.random.nextInt(6) + this.random.nextInt(7);
    }

    public void t_()
    {
        this.P = this.locX;
        this.Q = this.locY;
        this.R = this.locZ;
        super.t_();
        this.motX *= 1.15D;
        this.motZ *= 1.15D;
        this.motY += 0.04D;
        this.move(this.motX, this.motY, this.motZ);
        float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        this.yaw = (float)(MathHelper.b(this.motX, this.motZ) * 180.0D / Math.PI);

        for (this.pitch = (float)(MathHelper.b(this.motY, (double)f) * 180.0D / Math.PI); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F)
        {
            ;
        }

        while (this.pitch - this.lastPitch >= 180.0F)
        {
            this.lastPitch += 360.0F;
        }

        while (this.yaw - this.lastYaw < -180.0F)
        {
            this.lastYaw -= 360.0F;
        }

        while (this.yaw - this.lastYaw >= 180.0F)
        {
            this.lastYaw += 360.0F;
        }

        this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
        this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;

        if (this.ticksFlown == 0 && !this.R())
        {
            this.world.makeSound(this, "fireworks.launch", 3.0F, 1.0F);
        }

        ++this.ticksFlown;

        if (this.world.isClientSide && this.ticksFlown % 2 < 2)
        {
            this.world.addParticle(EnumParticle.FIREWORKS_SPARK, this.locX, this.locY - 0.3D, this.locZ, this.random.nextGaussian() * 0.05D, -this.motY * 0.5D, this.random.nextGaussian() * 0.05D, new int[0]);
        }

        if (!this.world.isClientSide && this.ticksFlown > this.expectedLifespan)
        {
            if (!CraftEventFactory.callFireworkExplodeEvent(this).isCancelled())
            {
                this.world.broadcastEntityEffect(this, (byte)17);
            }

            this.die();
        }
    }

    public void b(NBTTagCompound p_b_1_)
    {
        p_b_1_.setInt("Life", this.ticksFlown);
        p_b_1_.setInt("LifeTime", this.expectedLifespan);
        ItemStack itemstack = this.datawatcher.getItemStack(8);

        if (itemstack != null)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            itemstack.save(nbttagcompound);
            p_b_1_.set("FireworksItem", nbttagcompound);
        }
    }

    public void a(NBTTagCompound p_a_1_)
    {
        this.ticksFlown = p_a_1_.getInt("Life");
        this.expectedLifespan = p_a_1_.getInt("LifeTime");
        NBTTagCompound nbttagcompound = p_a_1_.getCompound("FireworksItem");

        if (nbttagcompound != null)
        {
            ItemStack itemstack = ItemStack.createStack(nbttagcompound);

            if (itemstack != null)
            {
                this.datawatcher.watch(8, itemstack);
            }
        }
    }

    public float c(float p_c_1_)
    {
        return super.c(p_c_1_);
    }

    public boolean aD()
    {
        return false;
    }
}
