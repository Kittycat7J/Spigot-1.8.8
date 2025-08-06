package net.minecraft.server.v1_8_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.projectiles.ProjectileSource;

public abstract class EntityFireball extends Entity
{
    private int e = -1;
    private int f = -1;
    private int g = -1;
    private Block h;
    private boolean i;
    public EntityLiving shooter;
    private int ar;
    private int as;
    public double dirX;
    public double dirY;
    public double dirZ;
    public float bukkitYield = 1.0F;
    public boolean isIncendiary = true;

    public EntityFireball(World p_i127_1_)
    {
        super(p_i127_1_);
        this.setSize(1.0F, 1.0F);
    }

    protected void h()
    {
    }

    public EntityFireball(World p_i128_1_, double p_i128_2_, double p_i128_4_, double p_i128_6_, double p_i128_8_, double p_i128_10_, double p_i128_12_)
    {
        super(p_i128_1_);
        this.setSize(1.0F, 1.0F);
        this.setPositionRotation(p_i128_2_, p_i128_4_, p_i128_6_, this.yaw, this.pitch);
        this.setPosition(p_i128_2_, p_i128_4_, p_i128_6_);
        double d0 = (double)MathHelper.sqrt(p_i128_8_ * p_i128_8_ + p_i128_10_ * p_i128_10_ + p_i128_12_ * p_i128_12_);
        this.dirX = p_i128_8_ / d0 * 0.1D;
        this.dirY = p_i128_10_ / d0 * 0.1D;
        this.dirZ = p_i128_12_ / d0 * 0.1D;
    }

    public EntityFireball(World p_i129_1_, EntityLiving p_i129_2_, double p_i129_3_, double p_i129_5_, double p_i129_7_)
    {
        super(p_i129_1_);
        this.shooter = p_i129_2_;
        this.projectileSource = (LivingEntity)p_i129_2_.getBukkitEntity();
        this.setSize(1.0F, 1.0F);
        this.setPositionRotation(p_i129_2_.locX, p_i129_2_.locY, p_i129_2_.locZ, p_i129_2_.yaw, p_i129_2_.pitch);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.motX = this.motY = this.motZ = 0.0D;
        this.setDirection(p_i129_3_, p_i129_5_, p_i129_7_);
    }

    public void setDirection(double p_setDirection_1_, double p_setDirection_3_, double p_setDirection_5_)
    {
        p_setDirection_1_ = p_setDirection_1_ + this.random.nextGaussian() * 0.4D;
        p_setDirection_3_ = p_setDirection_3_ + this.random.nextGaussian() * 0.4D;
        p_setDirection_5_ = p_setDirection_5_ + this.random.nextGaussian() * 0.4D;
        double d0 = (double)MathHelper.sqrt(p_setDirection_1_ * p_setDirection_1_ + p_setDirection_3_ * p_setDirection_3_ + p_setDirection_5_ * p_setDirection_5_);
        this.dirX = p_setDirection_1_ / d0 * 0.1D;
        this.dirY = p_setDirection_3_ / d0 * 0.1D;
        this.dirZ = p_setDirection_5_ / d0 * 0.1D;
    }

    public void t_()
    {
        if (this.world.isClientSide || (this.shooter == null || !this.shooter.dead) && this.world.isLoaded(new BlockPosition(this)))
        {
            super.t_();
            this.setOnFire(1);

            if (this.i)
            {
                if (this.world.getType(new BlockPosition(this.e, this.f, this.g)).getBlock() == this.h)
                {
                    ++this.ar;

                    if (this.ar == 600)
                    {
                        this.die();
                    }

                    return;
                }

                this.i = false;
                this.motX *= (double)(this.random.nextFloat() * 0.2F);
                this.motY *= (double)(this.random.nextFloat() * 0.2F);
                this.motZ *= (double)(this.random.nextFloat() * 0.2F);
                this.ar = 0;
                this.as = 0;
            }
            else
            {
                ++this.as;
            }

            Vec3D vec3d = new Vec3D(this.locX, this.locY, this.locZ);
            Vec3D vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);
            MovingObjectPosition movingobjectposition = this.world.rayTrace(vec3d, vec3d1);
            vec3d = new Vec3D(this.locX, this.locY, this.locZ);
            vec3d1 = new Vec3D(this.locX + this.motX, this.locY + this.motY, this.locZ + this.motZ);

            if (movingobjectposition != null)
            {
                vec3d1 = new Vec3D(movingobjectposition.pos.a, movingobjectposition.pos.b, movingobjectposition.pos.c);
            }

            Entity entity = null;
            List list = this.world.getEntities(this, this.getBoundingBox().a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if (entity1.ad() && (!entity1.k(this.shooter) || this.as >= 25))
                {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.getBoundingBox().grow((double)f, (double)f, (double)f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.a(vec3d, vec3d1);

                    if (movingobjectposition1 != null)
                    {
                        double d1 = vec3d.distanceSquared(movingobjectposition1.pos);

                        if (d1 < d0 || d0 == 0.0D)
                        {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null)
            {
                movingobjectposition = new MovingObjectPosition(entity);
            }

            if (movingobjectposition != null)
            {
                this.a(movingobjectposition);

                if (this.dead)
                {
                    CraftEventFactory.callProjectileHitEvent(this);
                }
            }

            this.locX += this.motX;
            this.locY += this.motY;
            this.locZ += this.motZ;
            float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
            this.yaw = (float)(MathHelper.b(this.motZ, this.motX) * 180.0D / Math.PI) + 90.0F;

            for (this.pitch = (float)(MathHelper.b((double)f1, this.motY) * 180.0D / Math.PI) - 90.0F; this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F)
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
            float f2 = this.j();

            if (this.V())
            {
                for (int j = 0; j < 4; ++j)
                {
                    float f3 = 0.25F;
                    this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - this.motX * (double)f3, this.locY - this.motY * (double)f3, this.locZ - this.motZ * (double)f3, this.motX, this.motY, this.motZ, new int[0]);
                }

                f2 = 0.8F;
            }

            this.motX += this.dirX;
            this.motY += this.dirY;
            this.motZ += this.dirZ;
            this.motX *= (double)f2;
            this.motY *= (double)f2;
            this.motZ *= (double)f2;
            this.world.addParticle(EnumParticle.SMOKE_NORMAL, this.locX, this.locY + 0.5D, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);
            this.setPosition(this.locX, this.locY, this.locZ);
        }
        else
        {
            this.die();
        }
    }

    protected float j()
    {
        return 0.95F;
    }

    protected abstract void a(MovingObjectPosition var1);

    public void b(NBTTagCompound p_b_1_)
    {
        p_b_1_.setShort("xTile", (short)this.e);
        p_b_1_.setShort("yTile", (short)this.f);
        p_b_1_.setShort("zTile", (short)this.g);
        MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(this.h);
        p_b_1_.setString("inTile", minecraftkey == null ? "" : minecraftkey.toString());
        p_b_1_.setByte("inGround", (byte)(this.i ? 1 : 0));
        p_b_1_.set("power", this.a((double[])(new double[] {this.dirX, this.dirY, this.dirZ})));
        p_b_1_.set("direction", this.a((double[])(new double[] {this.motX, this.motY, this.motZ})));
    }

    public void a(NBTTagCompound p_a_1_)
    {
        this.e = p_a_1_.getShort("xTile");
        this.f = p_a_1_.getShort("yTile");
        this.g = p_a_1_.getShort("zTile");

        if (p_a_1_.hasKeyOfType("inTile", 8))
        {
            this.h = Block.getByName(p_a_1_.getString("inTile"));
        }
        else
        {
            this.h = Block.getById(p_a_1_.getByte("inTile") & 255);
        }

        this.i = p_a_1_.getByte("inGround") == 1;

        if (p_a_1_.hasKeyOfType("power", 9))
        {
            NBTTagList nbttaglist = p_a_1_.getList("power", 6);
            this.dirX = nbttaglist.d(0);
            this.dirY = nbttaglist.d(1);
            this.dirZ = nbttaglist.d(2);
        }
        else if (p_a_1_.hasKeyOfType("direction", 9))
        {
            NBTTagList nbttaglist1 = p_a_1_.getList("direction", 6);
            this.motX = nbttaglist1.d(0);
            this.motY = nbttaglist1.d(1);
            this.motZ = nbttaglist1.d(2);
        }
        else
        {
            this.die();
        }
    }

    public boolean ad()
    {
        return true;
    }

    public float ao()
    {
        return 1.0F;
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

            if (p_damageEntity_1_.getEntity() != null)
            {
                if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, p_damageEntity_1_, (double)p_damageEntity_2_))
                {
                    return false;
                }
                else
                {
                    Vec3D vec3d = p_damageEntity_1_.getEntity().ap();

                    if (vec3d != null)
                    {
                        this.motX = vec3d.a;
                        this.motY = vec3d.b;
                        this.motZ = vec3d.c;
                        this.dirX = this.motX * 0.1D;
                        this.dirY = this.motY * 0.1D;
                        this.dirZ = this.motZ * 0.1D;
                    }

                    if (p_damageEntity_1_.getEntity() instanceof EntityLiving)
                    {
                        this.shooter = (EntityLiving)p_damageEntity_1_.getEntity();
                        this.projectileSource = (ProjectileSource)this.shooter.getBukkitEntity();
                    }

                    return true;
                }
            }
            else
            {
                return false;
            }
        }
    }

    public float c(float p_c_1_)
    {
        return 1.0F;
    }
}
