package net.minecraft.server.v1_8_R3;

public class EntityMinecartFurnace extends EntityMinecartAbstract
{
    private int c;
    public double a;
    public double b;

    public EntityMinecartFurnace(World p_i1219_1_)
    {
        super(p_i1219_1_);
    }

    public EntityMinecartFurnace(World p_i1220_1_, double p_i1220_2_, double p_i1220_4_, double p_i1220_6_)
    {
        super(p_i1220_1_, p_i1220_2_, p_i1220_4_, p_i1220_6_);
    }

    public EntityMinecartAbstract.EnumMinecartType s()
    {
        return EntityMinecartAbstract.EnumMinecartType.FURNACE;
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(16, new Byte((byte)0));
    }

    public void t_()
    {
        super.t_();

        if (this.c > 0)
        {
            --this.c;
        }

        if (this.c <= 0)
        {
            this.a = this.b = 0.0D;
        }

        this.i(this.c > 0);

        if (this.j() && this.random.nextInt(4) == 0)
        {
            this.world.addParticle(EnumParticle.SMOKE_LARGE, this.locX, this.locY + 0.8D, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }

    protected double m()
    {
        return 0.2D;
    }

    public void a(DamageSource p_a_1_)
    {
        super.a(p_a_1_);

        if (!p_a_1_.isExplosion() && this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            this.a(new ItemStack(Blocks.FURNACE, 1), 0.0F);
        }
    }

    protected void a(BlockPosition p_a_1_, IBlockData p_a_2_)
    {
        super.a(p_a_1_, p_a_2_);
        double d0 = this.a * this.a + this.b * this.b;

        if (d0 > 1.0E-4D && this.motX * this.motX + this.motZ * this.motZ > 0.001D)
        {
            d0 = (double)MathHelper.sqrt(d0);
            this.a /= d0;
            this.b /= d0;

            if (this.a * this.motX + this.b * this.motZ < 0.0D)
            {
                this.a = 0.0D;
                this.b = 0.0D;
            }
            else
            {
                double d1 = d0 / this.m();
                this.a *= d1;
                this.b *= d1;
            }
        }
    }

    protected void o()
    {
        double d0 = this.a * this.a + this.b * this.b;

        if (d0 > 1.0E-4D)
        {
            d0 = (double)MathHelper.sqrt(d0);
            this.a /= d0;
            this.b /= d0;
            double d1 = 1.0D;
            this.motX *= 0.800000011920929D;
            this.motY *= 0.0D;
            this.motZ *= 0.800000011920929D;
            this.motX += this.a * d1;
            this.motZ += this.b * d1;
        }
        else
        {
            this.motX *= 0.9800000190734863D;
            this.motY *= 0.0D;
            this.motZ *= 0.9800000190734863D;
        }

        super.o();
    }

    public boolean e(EntityHuman p_e_1_)
    {
        ItemStack itemstack = p_e_1_.inventory.getItemInHand();

        if (itemstack != null && itemstack.getItem() == Items.COAL)
        {
            if (!p_e_1_.abilities.canInstantlyBuild && --itemstack.count == 0)
            {
                p_e_1_.inventory.setItem(p_e_1_.inventory.itemInHandIndex, (ItemStack)null);
            }

            this.c += 3600;
        }

        this.a = this.locX - p_e_1_.locX;
        this.b = this.locZ - p_e_1_.locZ;
        return true;
    }

    protected void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setDouble("PushX", this.a);
        p_b_1_.setDouble("PushZ", this.b);
        p_b_1_.setShort("Fuel", (short)this.c);
    }

    protected void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.a = p_a_1_.getDouble("PushX");
        this.b = p_a_1_.getDouble("PushZ");
        this.c = p_a_1_.getShort("Fuel");
    }

    protected boolean j()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    protected void i(boolean p_i_1_)
    {
        if (p_i_1_)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(this.datawatcher.getByte(16) | 1)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(this.datawatcher.getByte(16) & -2)));
        }
    }

    public IBlockData u()
    {
        return (this.j() ? Blocks.LIT_FURNACE : Blocks.FURNACE).getBlockData().set(BlockFurnace.FACING, EnumDirection.NORTH);
    }
}
