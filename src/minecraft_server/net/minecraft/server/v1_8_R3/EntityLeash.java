package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class EntityLeash extends EntityHanging
{
    public EntityLeash(World p_i347_1_)
    {
        super(p_i347_1_);
    }

    public EntityLeash(World p_i348_1_, BlockPosition p_i348_2_)
    {
        super(p_i348_1_, p_i348_2_);
        this.setPosition((double)p_i348_2_.getX() + 0.5D, (double)p_i348_2_.getY() + 0.5D, (double)p_i348_2_.getZ() + 0.5D);
        this.a(new AxisAlignedBB(this.locX - 0.1875D, this.locY - 0.25D + 0.125D, this.locZ - 0.1875D, this.locX + 0.1875D, this.locY + 0.25D + 0.125D, this.locZ + 0.1875D));
    }

    protected void h()
    {
        super.h();
    }

    public void setDirection(EnumDirection p_setDirection_1_)
    {
    }

    public int l()
    {
        return 9;
    }

    public int m()
    {
        return 9;
    }

    public float getHeadHeight()
    {
        return -0.0625F;
    }

    public void b(Entity p_b_1_)
    {
    }

    public boolean d(NBTTagCompound p_d_1_)
    {
        return false;
    }

    public void b(NBTTagCompound p_b_1_)
    {
    }

    public void a(NBTTagCompound p_a_1_)
    {
    }

    public boolean e(EntityHuman p_e_1_)
    {
        ItemStack itemstack = p_e_1_.bA();
        boolean flag = false;

        if (itemstack != null && itemstack.getItem() == Items.LEAD && !this.world.isClientSide)
        {
            double d0 = 7.0D;

            for (EntityInsentient entityinsentient : this.world.a(EntityInsentient.class, new AxisAlignedBB(this.locX - d0, this.locY - d0, this.locZ - d0, this.locX + d0, this.locY + d0, this.locZ + d0)))
            {
                if (entityinsentient.cc() && entityinsentient.getLeashHolder() == p_e_1_)
                {
                    if (CraftEventFactory.callPlayerLeashEntityEvent(entityinsentient, this, p_e_1_).isCancelled())
                    {
                        ((EntityPlayer)p_e_1_).playerConnection.sendPacket(new PacketPlayOutAttachEntity(1, entityinsentient, entityinsentient.getLeashHolder()));
                    }
                    else
                    {
                        entityinsentient.setLeashHolder(this, true);
                        flag = true;
                    }
                }
            }
        }

        if (!this.world.isClientSide && !flag)
        {
            boolean flag1 = true;
            double d1 = 7.0D;

            for (EntityInsentient entityinsentient1 : this.world.a(EntityInsentient.class, new AxisAlignedBB(this.locX - d1, this.locY - d1, this.locZ - d1, this.locX + d1, this.locY + d1, this.locZ + d1)))
            {
                if (entityinsentient1.cc() && entityinsentient1.getLeashHolder() == this)
                {
                    if (CraftEventFactory.callPlayerUnleashEntityEvent(entityinsentient1, p_e_1_).isCancelled())
                    {
                        flag1 = false;
                    }
                    else
                    {
                        entityinsentient1.unleash(true, !p_e_1_.abilities.canInstantlyBuild);
                    }
                }
            }

            if (flag1)
            {
                this.die();
            }
        }

        return true;
    }

    public boolean survives()
    {
        return this.world.getType(this.blockPosition).getBlock() instanceof BlockFence;
    }

    public static EntityLeash a(World p_a_0_, BlockPosition p_a_1_)
    {
        EntityLeash entityleash = new EntityLeash(p_a_0_, p_a_1_);
        entityleash.attachedToPlayer = true;
        p_a_0_.addEntity(entityleash);
        return entityleash;
    }

    public static EntityLeash b(World p_b_0_, BlockPosition p_b_1_)
    {
        int i = p_b_1_.getX();
        int j = p_b_1_.getY();
        int k = p_b_1_.getZ();

        for (EntityLeash entityleash : p_b_0_.a(EntityLeash.class, new AxisAlignedBB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D)))
        {
            if (entityleash.getBlockPosition().equals(p_b_1_))
            {
                return entityleash;
            }
        }

        return null;
    }
}
