package net.minecraft.server.v1_8_R3;

public class EntityMinecartTNT extends EntityMinecartAbstract
{
    private int a = -1;

    public EntityMinecartTNT(World p_i1228_1_)
    {
        super(p_i1228_1_);
    }

    public EntityMinecartTNT(World p_i1229_1_, double p_i1229_2_, double p_i1229_4_, double p_i1229_6_)
    {
        super(p_i1229_1_, p_i1229_2_, p_i1229_4_, p_i1229_6_);
    }

    public EntityMinecartAbstract.EnumMinecartType s()
    {
        return EntityMinecartAbstract.EnumMinecartType.TNT;
    }

    public IBlockData u()
    {
        return Blocks.TNT.getBlockData();
    }

    public void t_()
    {
        super.t_();

        if (this.a > 0)
        {
            --this.a;
            this.world.addParticle(EnumParticle.SMOKE_NORMAL, this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
        else if (this.a == 0)
        {
            this.b(this.motX * this.motX + this.motZ * this.motZ);
        }

        if (this.positionChanged)
        {
            double d0 = this.motX * this.motX + this.motZ * this.motZ;

            if (d0 >= 0.009999999776482582D)
            {
                this.b(d0);
            }
        }
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        Entity entity = p_damageEntity_1_.i();

        if (entity instanceof EntityArrow)
        {
            EntityArrow entityarrow = (EntityArrow)entity;

            if (entityarrow.isBurning())
            {
                this.b(entityarrow.motX * entityarrow.motX + entityarrow.motY * entityarrow.motY + entityarrow.motZ * entityarrow.motZ);
            }
        }

        return super.damageEntity(p_damageEntity_1_, p_damageEntity_2_);
    }

    public void a(DamageSource p_a_1_)
    {
        super.a(p_a_1_);
        double d0 = this.motX * this.motX + this.motZ * this.motZ;

        if (!p_a_1_.isExplosion() && this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            this.a(new ItemStack(Blocks.TNT, 1), 0.0F);
        }

        if (p_a_1_.o() || p_a_1_.isExplosion() || d0 >= 0.009999999776482582D)
        {
            this.b(d0);
        }
    }

    protected void b(double p_b_1_)
    {
        if (!this.world.isClientSide)
        {
            double d0 = Math.sqrt(p_b_1_);

            if (d0 > 5.0D)
            {
                d0 = 5.0D;
            }

            this.world.explode(this, this.locX, this.locY, this.locZ, (float)(4.0D + this.random.nextDouble() * 1.5D * d0), true);
            this.die();
        }
    }

    public void e(float p_e_1_, float p_e_2_)
    {
        if (p_e_1_ >= 3.0F)
        {
            float f = p_e_1_ / 10.0F;
            this.b((double)(f * f));
        }

        super.e(p_e_1_, p_e_2_);
    }

    public void a(int p_a_1_, int p_a_2_, int p_a_3_, boolean p_a_4_)
    {
        if (p_a_4_ && this.a < 0)
        {
            this.j();
        }
    }

    public void j()
    {
        this.a = 80;

        if (!this.world.isClientSide)
        {
            this.world.broadcastEntityEffect(this, (byte)10);

            if (!this.R())
            {
                this.world.makeSound(this, "game.tnt.primed", 1.0F, 1.0F);
            }
        }
    }

    public boolean y()
    {
        return this.a > -1;
    }

    public float a(Explosion p_a_1_, World p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_)
    {
        return !this.y() || !BlockMinecartTrackAbstract.d(p_a_4_) && !BlockMinecartTrackAbstract.e(p_a_2_, p_a_3_.up()) ? super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_) : 0.0F;
    }

    public boolean a(Explosion p_a_1_, World p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, float p_a_5_)
    {
        return !this.y() || !BlockMinecartTrackAbstract.d(p_a_4_) && !BlockMinecartTrackAbstract.e(p_a_2_, p_a_3_.up()) ? super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_) : false;
    }

    protected void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);

        if (p_a_1_.hasKeyOfType("TNTFuse", 99))
        {
            this.a = p_a_1_.getInt("TNTFuse");
        }
    }

    protected void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setInt("TNTFuse", this.a);
    }
}
