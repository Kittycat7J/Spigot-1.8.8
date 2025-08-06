package net.minecraft.server.v1_8_R3;

public class EntityMinecartRideable extends EntityMinecartAbstract
{
    public EntityMinecartRideable(World p_i1223_1_)
    {
        super(p_i1223_1_);
    }

    public EntityMinecartRideable(World p_i1224_1_, double p_i1224_2_, double p_i1224_4_, double p_i1224_6_)
    {
        super(p_i1224_1_, p_i1224_2_, p_i1224_4_, p_i1224_6_);
    }

    public boolean e(EntityHuman p_e_1_)
    {
        if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != p_e_1_)
        {
            return true;
        }
        else if (this.passenger != null && this.passenger != p_e_1_)
        {
            return false;
        }
        else
        {
            if (!this.world.isClientSide)
            {
                p_e_1_.mount(this);
            }

            return true;
        }
    }

    public void a(int p_a_1_, int p_a_2_, int p_a_3_, boolean p_a_4_)
    {
        if (p_a_4_)
        {
            if (this.passenger != null)
            {
                this.passenger.mount((Entity)null);
            }

            if (this.getType() == 0)
            {
                this.k(-this.r());
                this.j(10);
                this.setDamage(50.0F);
                this.ac();
            }
        }
    }

    public EntityMinecartAbstract.EnumMinecartType s()
    {
        return EntityMinecartAbstract.EnumMinecartType.RIDEABLE;
    }
}
