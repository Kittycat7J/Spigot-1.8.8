package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.UUID;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;

public abstract class EntityProjectile extends Entity implements IProjectile
{
    private int blockX = -1;
    private int blockY = -1;
    private int blockZ = -1;
    private Block inBlockId;
    protected boolean inGround;
    public int shake;
    public EntityLiving shooter;
    public String shooterName;
    private int i;
    private int ar;

    public EntityProjectile(World p_i24_1_)
    {
        super(p_i24_1_);
        this.setSize(0.25F, 0.25F);
    }

    protected void h()
    {
    }

    public EntityProjectile(World p_i25_1_, EntityLiving p_i25_2_)
    {
        super(p_i25_1_);
        this.shooter = p_i25_2_;
        this.projectileSource = (LivingEntity)p_i25_2_.getBukkitEntity();
        this.setSize(0.25F, 0.25F);
        this.setPositionRotation(p_i25_2_.locX, p_i25_2_.locY + (double)p_i25_2_.getHeadHeight(), p_i25_2_.locZ, p_i25_2_.yaw, p_i25_2_.pitch);
        this.locX -= (double)(MathHelper.cos(this.yaw / 180.0F * (float)Math.PI) * 0.16F);
        this.locY -= 0.10000000149011612D;
        this.locZ -= (double)(MathHelper.sin(this.yaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.locX, this.locY, this.locZ);
        float f = 0.4F;
        this.motX = (double)(-MathHelper.sin(this.yaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float)Math.PI) * f);
        this.motZ = (double)(MathHelper.cos(this.yaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.pitch / 180.0F * (float)Math.PI) * f);
        this.motY = (double)(-MathHelper.sin((this.pitch + this.l()) / 180.0F * (float)Math.PI) * f);
        this.shoot(this.motX, this.motY, this.motZ, this.j(), 1.0F);
    }

    public EntityProjectile(World p_i26_1_, double p_i26_2_, double p_i26_4_, double p_i26_6_)
    {
        super(p_i26_1_);
        this.i = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(p_i26_2_, p_i26_4_, p_i26_6_);
    }

    protected float j()
    {
        return 1.5F;
    }

    protected float l()
    {
        return 0.0F;
    }

    public void shoot(double p_shoot_1_, double p_shoot_3_, double p_shoot_5_, float p_shoot_7_, float p_shoot_8_)
    {
        float f = MathHelper.sqrt(p_shoot_1_ * p_shoot_1_ + p_shoot_3_ * p_shoot_3_ + p_shoot_5_ * p_shoot_5_);
        p_shoot_1_ = p_shoot_1_ / (double)f;
        p_shoot_3_ = p_shoot_3_ / (double)f;
        p_shoot_5_ = p_shoot_5_ / (double)f;
        p_shoot_1_ = p_shoot_1_ + this.random.nextGaussian() * 0.007499999832361937D * (double)p_shoot_8_;
        p_shoot_3_ = p_shoot_3_ + this.random.nextGaussian() * 0.007499999832361937D * (double)p_shoot_8_;
        p_shoot_5_ = p_shoot_5_ + this.random.nextGaussian() * 0.007499999832361937D * (double)p_shoot_8_;
        p_shoot_1_ = p_shoot_1_ * (double)p_shoot_7_;
        p_shoot_3_ = p_shoot_3_ * (double)p_shoot_7_;
        p_shoot_5_ = p_shoot_5_ * (double)p_shoot_7_;
        this.motX = p_shoot_1_;
        this.motY = p_shoot_3_;
        this.motZ = p_shoot_5_;
        float f1 = MathHelper.sqrt(p_shoot_1_ * p_shoot_1_ + p_shoot_5_ * p_shoot_5_);
        this.lastYaw = this.yaw = (float)(MathHelper.b(p_shoot_1_, p_shoot_5_) * 180.0D / Math.PI);
        this.lastPitch = this.pitch = (float)(MathHelper.b(p_shoot_3_, (double)f1) * 180.0D / Math.PI);
        this.i = 0;
    }

    public void t_()
    {
        this.P = this.locX;
        this.Q = this.locY;
        this.R = this.locZ;
        super.t_();

        if (this.shake > 0)
        {
            --this.shake;
        }

        if (this.inGround)
        {
            if (this.world.getType(new BlockPosition(this.blockX, this.blockY, this.blockZ)).getBlock() == this.inBlockId)
            {
                ++this.i;

                if (this.i == 1200)
                {
                    this.die();
                }

                return;
            }

            this.inGround = false;
            this.motX *= (double)(this.random.nextFloat() * 0.2F);
            this.motY *= (double)(this.random.nextFloat() * 0.2F);
            this.motZ *= (double)(this.random.nextFloat() * 0.2F);
            this.i = 0;
            this.ar = 0;
        }
        else
        {
            ++this.ar;
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

        if (!this.world.isClientSide)
        {
            Entity entity = null;
            List list = this.world.getEntities(this, this.getBoundingBox().a(this.motX, this.motY, this.motZ).grow(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            EntityLiving entityliving = this.getShooter();

            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity1 = (Entity)list.get(i);

                if (entity1.ad() && (entity1 != entityliving || this.ar >= 5))
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
        }

        if (movingobjectposition != null)
        {
            if (movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK && this.world.getType(movingobjectposition.a()).getBlock() == Blocks.PORTAL)
            {
                this.d(movingobjectposition.a());
            }
            else
            {
                this.a(movingobjectposition);

                if (this.dead)
                {
                    CraftEventFactory.callProjectileHitEvent(this);
                }
            }
        }

        this.locX += this.motX;
        this.locY += this.motY;
        this.locZ += this.motZ;
        float f2 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
        this.yaw = (float)(MathHelper.b(this.motX, this.motZ) * 180.0D / Math.PI);

        for (this.pitch = (float)(MathHelper.b(this.motY, (double)f2) * 180.0D / Math.PI); this.pitch - this.lastPitch < -180.0F; this.lastPitch -= 360.0F)
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
        float f3 = 0.99F;
        float f1 = this.m();

        if (this.V())
        {
            for (int j = 0; j < 4; ++j)
            {
                float f4 = 0.25F;
                this.world.addParticle(EnumParticle.WATER_BUBBLE, this.locX - this.motX * (double)f4, this.locY - this.motY * (double)f4, this.locZ - this.motZ * (double)f4, this.motX, this.motY, this.motZ, new int[0]);
            }

            f3 = 0.8F;
        }

        this.motX *= (double)f3;
        this.motY *= (double)f3;
        this.motZ *= (double)f3;
        this.motY -= (double)f1;
        this.setPosition(this.locX, this.locY, this.locZ);
    }

    protected float m()
    {
        return 0.03F;
    }

    protected abstract void a(MovingObjectPosition var1);

    public void b(NBTTagCompound p_b_1_)
    {
        p_b_1_.setShort("xTile", (short)this.blockX);
        p_b_1_.setShort("yTile", (short)this.blockY);
        p_b_1_.setShort("zTile", (short)this.blockZ);
        MinecraftKey minecraftkey = (MinecraftKey)Block.REGISTRY.c(this.inBlockId);
        p_b_1_.setString("inTile", minecraftkey == null ? "" : minecraftkey.toString());
        p_b_1_.setByte("shake", (byte)this.shake);
        p_b_1_.setByte("inGround", (byte)(this.inGround ? 1 : 0));

        if ((this.shooterName == null || this.shooterName.length() == 0) && this.shooter instanceof EntityHuman)
        {
            this.shooterName = this.shooter.getName();
        }

        p_b_1_.setString("ownerName", this.shooterName == null ? "" : this.shooterName);
    }

    public void a(NBTTagCompound p_a_1_)
    {
        this.blockX = p_a_1_.getShort("xTile");
        this.blockY = p_a_1_.getShort("yTile");
        this.blockZ = p_a_1_.getShort("zTile");

        if (p_a_1_.hasKeyOfType("inTile", 8))
        {
            this.inBlockId = Block.getByName(p_a_1_.getString("inTile"));
        }
        else
        {
            this.inBlockId = Block.getById(p_a_1_.getByte("inTile") & 255);
        }

        this.shake = p_a_1_.getByte("shake") & 255;
        this.inGround = p_a_1_.getByte("inGround") == 1;
        this.shooter = null;
        this.shooterName = p_a_1_.getString("ownerName");

        if (this.shooterName != null && this.shooterName.length() == 0)
        {
            this.shooterName = null;
        }

        this.shooter = this.getShooter();
    }

    public EntityLiving getShooter()
    {
        if (this.shooter == null && this.shooterName != null && this.shooterName.length() > 0)
        {
            this.shooter = this.world.a(this.shooterName);

            if (this.shooter == null && this.world instanceof WorldServer)
            {
                try
                {
                    Entity entity = ((WorldServer)this.world).getEntity(UUID.fromString(this.shooterName));

                    if (entity instanceof EntityLiving)
                    {
                        this.shooter = (EntityLiving)entity;
                    }
                }
                catch (Throwable var2)
                {
                    this.shooter = null;
                }
            }
        }

        return this.shooter;
    }
}
