package net.minecraft.server.v1_8_R3;

import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public abstract class EntityAgeable extends EntityCreature
{
    protected int a;
    protected int b;
    protected int c;
    private float bm = -1.0F;
    private float bn;
    public boolean ageLocked = false;

    public void inactiveTick()
    {
        super.inactiveTick();

        if (!this.world.isClientSide && !this.ageLocked)
        {
            int i = this.getAge();

            if (i < 0)
            {
                ++i;
                this.setAgeRaw(i);
            }
            else if (i > 0)
            {
                --i;
                this.setAgeRaw(i);
            }
        }
        else
        {
            this.a(this.isBaby());
        }
    }

    public EntityAgeable(World p_i321_1_)
    {
        super(p_i321_1_);
    }

    public abstract EntityAgeable createChild(EntityAgeable var1);

    public boolean a(EntityHuman p_a_1_)
    {
        ItemStack itemstack = p_a_1_.inventory.getItemInHand();

        if (itemstack != null && itemstack.getItem() == Items.SPAWN_EGG)
        {
            if (!this.world.isClientSide)
            {
                Class oclass = EntityTypes.a(itemstack.getData());

                if (oclass != null && this.getClass() == oclass)
                {
                    EntityAgeable entityageable = this.createChild(this);

                    if (entityageable != null)
                    {
                        entityageable.setAgeRaw(-24000);
                        entityageable.setPositionRotation(this.locX, this.locY, this.locZ, 0.0F, 0.0F);
                        this.world.addEntity(entityageable, SpawnReason.SPAWNER_EGG);

                        if (itemstack.hasName())
                        {
                            entityageable.setCustomName(itemstack.getName());
                        }

                        if (!p_a_1_.abilities.canInstantlyBuild)
                        {
                            --itemstack.count;

                            if (itemstack.count == 0)
                            {
                                p_a_1_.inventory.setItem(p_a_1_.inventory.itemInHandIndex, (ItemStack)null);
                            }
                        }
                    }
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(12, Byte.valueOf((byte)0));
    }

    public int getAge()
    {
        return this.world.isClientSide ? this.datawatcher.getByte(12) : this.a;
    }

    public void setAge(int p_setAge_1_, boolean p_setAge_2_)
    {
        int i = this.getAge();
        int j = i;
        i = i + p_setAge_1_ * 20;

        if (i > 0)
        {
            i = 0;

            if (j < 0)
            {
                this.n();
            }
        }

        int k = i - j;
        this.setAgeRaw(i);

        if (p_setAge_2_)
        {
            this.b += k;

            if (this.c == 0)
            {
                this.c = 40;
            }
        }

        if (this.getAge() == 0)
        {
            this.setAgeRaw(this.b);
        }
    }

    public void setAge(int p_setAge_1_)
    {
        this.setAge(p_setAge_1_, false);
    }

    public void setAgeRaw(int p_setAgeRaw_1_)
    {
        this.datawatcher.watch(12, Byte.valueOf((byte)MathHelper.clamp(p_setAgeRaw_1_, -1, 1)));
        this.a = p_setAgeRaw_1_;
        this.a(this.isBaby());
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setInt("Age", this.getAge());
        p_b_1_.setInt("ForcedAge", this.b);
        p_b_1_.setBoolean("AgeLocked", this.ageLocked);
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.setAgeRaw(p_a_1_.getInt("Age"));
        this.b = p_a_1_.getInt("ForcedAge");
        this.ageLocked = p_a_1_.getBoolean("AgeLocked");
    }

    public void m()
    {
        super.m();

        if (!this.world.isClientSide && !this.ageLocked)
        {
            int i = this.getAge();

            if (i < 0)
            {
                ++i;
                this.setAgeRaw(i);

                if (i == 0)
                {
                    this.n();
                }
            }
            else if (i > 0)
            {
                --i;
                this.setAgeRaw(i);
            }
        }
        else
        {
            if (this.c > 0)
            {
                if (this.c % 4 == 0)
                {
                    this.world.addParticle(EnumParticle.VILLAGER_HAPPY, this.locX + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, this.locY + 0.5D + (double)(this.random.nextFloat() * this.length), this.locZ + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D, new int[0]);
                }

                --this.c;
            }

            this.a(this.isBaby());
        }
    }

    protected void n()
    {
    }

    public boolean isBaby()
    {
        return this.getAge() < 0;
    }

    public void a(boolean p_a_1_)
    {
        this.a(p_a_1_ ? 0.5F : 1.0F);
    }

    public final void setSize(float p_setSize_1_, float p_setSize_2_)
    {
        boolean flag = this.bm > 0.0F;
        this.bm = p_setSize_1_;
        this.bn = p_setSize_2_;

        if (!flag)
        {
            this.a(1.0F);
        }
    }

    protected final void a(float p_a_1_)
    {
        super.setSize(this.bm * p_a_1_, this.bn * p_a_1_);
    }
}
