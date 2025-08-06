package net.minecraft.server.v1_8_R3;

import java.util.List;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Vehicle;
import org.bukkit.event.vehicle.VehicleCreateEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleDestroyEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.event.vehicle.VehicleUpdateEvent;

public class EntityBoat extends Entity
{
    private boolean a;
    private double b;
    private int c;
    private double d;
    private double e;
    private double f;
    private double g;
    private double h;
    public double maxSpeed;
    public double occupiedDeceleration;
    public double unoccupiedDeceleration;
    public boolean landBoats;

    public void collide(Entity p_collide_1_)
    {
        org.bukkit.entity.Entity entity = p_collide_1_ == null ? null : p_collide_1_.getBukkitEntity();
        VehicleEntityCollisionEvent vehicleentitycollisionevent = new VehicleEntityCollisionEvent((Vehicle)this.getBukkitEntity(), entity);
        this.world.getServer().getPluginManager().callEvent(vehicleentitycollisionevent);

        if (!vehicleentitycollisionevent.isCancelled())
        {
            super.collide(p_collide_1_);
        }
    }

    public EntityBoat(World p_i307_1_)
    {
        super(p_i307_1_);
        this.maxSpeed = 0.4D;
        this.occupiedDeceleration = 0.2D;
        this.unoccupiedDeceleration = -1.0D;
        this.landBoats = false;
        this.a = true;
        this.b = 0.07D;
        this.k = true;
        this.setSize(1.5F, 0.6F);
    }

    protected boolean s_()
    {
        return false;
    }

    protected void h()
    {
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(1));
        this.datawatcher.a(19, new Float(0.0F));
    }

    public AxisAlignedBB j(Entity p_j_1_)
    {
        return p_j_1_.getBoundingBox();
    }

    public AxisAlignedBB S()
    {
        return this.getBoundingBox();
    }

    public boolean ae()
    {
        return true;
    }

    public EntityBoat(World p_i308_1_, double p_i308_2_, double p_i308_4_, double p_i308_6_)
    {
        this(p_i308_1_);
        this.setPosition(p_i308_2_, p_i308_4_, p_i308_6_);
        this.motX = 0.0D;
        this.motY = 0.0D;
        this.motZ = 0.0D;
        this.lastX = p_i308_2_;
        this.lastY = p_i308_4_;
        this.lastZ = p_i308_6_;
        this.world.getServer().getPluginManager().callEvent(new VehicleCreateEvent((Vehicle)this.getBukkitEntity()));
    }

    public double an()
    {
        return -0.3D;
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        if (this.isInvulnerable(p_damageEntity_1_))
        {
            return false;
        }
        else if (!this.world.isClientSide && !this.dead)
        {
            if (this.passenger != null && this.passenger == p_damageEntity_1_.getEntity() && p_damageEntity_1_ instanceof EntityDamageSourceIndirect)
            {
                return false;
            }
            else
            {
                Vehicle vehicle = (Vehicle)this.getBukkitEntity();
                org.bukkit.entity.Entity entity = p_damageEntity_1_.getEntity() == null ? null : p_damageEntity_1_.getEntity().getBukkitEntity();
                VehicleDamageEvent vehicledamageevent = new VehicleDamageEvent(vehicle, entity, (double)p_damageEntity_2_);
                this.world.getServer().getPluginManager().callEvent(vehicledamageevent);

                if (vehicledamageevent.isCancelled())
                {
                    return true;
                }
                else
                {
                    this.b(-this.m());
                    this.a(10);
                    this.setDamage(this.j() + p_damageEntity_2_ * 10.0F);
                    this.ac();
                    boolean flag = p_damageEntity_1_.getEntity() instanceof EntityHuman && ((EntityHuman)p_damageEntity_1_.getEntity()).abilities.canInstantlyBuild;

                    if (flag || this.j() > 40.0F)
                    {
                        VehicleDestroyEvent vehicledestroyevent = new VehicleDestroyEvent(vehicle, entity);
                        this.world.getServer().getPluginManager().callEvent(vehicledestroyevent);

                        if (vehicledestroyevent.isCancelled())
                        {
                            this.setDamage(40.0F);
                            return true;
                        }

                        if (this.passenger != null)
                        {
                            this.passenger.mount(this);
                        }

                        if (!flag && this.world.getGameRules().getBoolean("doEntityDrops"))
                        {
                            this.a(Items.BOAT, 1, 0.0F);
                        }

                        this.die();
                    }

                    return true;
                }
            }
        }
        else
        {
            return true;
        }
    }

    public boolean ad()
    {
        return !this.dead;
    }

    public void t_()
    {
        double d0 = this.locX;
        double d1 = this.locY;
        double d2 = this.locZ;
        float f = this.yaw;
        float f1 = this.pitch;
        super.t_();

        if (this.l() > 0)
        {
            this.a(this.l() - 1);
        }

        if (this.j() > 0.0F)
        {
            this.setDamage(this.j() - 1.0F);
        }

        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        byte b0 = 5;
        double d3 = 0.0D;

        for (int i = 0; i < b0; ++i)
        {
            double d4 = this.getBoundingBox().b + (this.getBoundingBox().e - this.getBoundingBox().b) * (double)(i + 0) / (double)b0 - 0.125D;
            double d5 = this.getBoundingBox().b + (this.getBoundingBox().e - this.getBoundingBox().b) * (double)(i + 1) / (double)b0 - 0.125D;
            AxisAlignedBB axisalignedbb = new AxisAlignedBB(this.getBoundingBox().a, d4, this.getBoundingBox().c, this.getBoundingBox().d, d5, this.getBoundingBox().f);

            if (this.world.b(axisalignedbb, Material.WATER))
            {
                d3 += 1.0D / (double)b0;
            }
        }

        double d6 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

        if (d6 > 0.2975D)
        {
            double d7 = Math.cos((double)this.yaw * Math.PI / 180.0D);
            double d8 = Math.sin((double)this.yaw * Math.PI / 180.0D);

            for (int j = 0; (double)j < 1.0D + d6 * 60.0D; ++j)
            {
                double d9 = (double)(this.random.nextFloat() * 2.0F - 1.0F);
                double d10 = (double)(this.random.nextInt(2) * 2 - 1) * 0.7D;

                if (this.random.nextBoolean())
                {
                    double d11 = this.locX - d7 * d9 * 0.8D + d8 * d10;
                    double d12 = this.locZ - d8 * d9 * 0.8D - d7 * d10;
                    this.world.addParticle(EnumParticle.WATER_SPLASH, d11, this.locY - 0.125D, d12, this.motX, this.motY, this.motZ, new int[0]);
                }
                else
                {
                    double d27 = this.locX + d7 + d8 * d9 * 0.7D;
                    double d28 = this.locZ + d8 - d7 * d9 * 0.7D;
                    this.world.addParticle(EnumParticle.WATER_SPLASH, d27, this.locY - 0.125D, d28, this.motX, this.motY, this.motZ, new int[0]);
                }
            }
        }

        if (this.world.isClientSide && this.a)
        {
            if (this.c > 0)
            {
                double d16 = this.locX + (this.d - this.locX) / (double)this.c;
                double d20 = this.locY + (this.e - this.locY) / (double)this.c;
                double d23 = this.locZ + (this.f - this.locZ) / (double)this.c;
                double d26 = MathHelper.g(this.g - (double)this.yaw);
                this.yaw = (float)((double)this.yaw + d26 / (double)this.c);
                this.pitch = (float)((double)this.pitch + (this.h - (double)this.pitch) / (double)this.c);
                --this.c;
                this.setPosition(d16, d20, d23);
                this.setYawPitch(this.yaw, this.pitch);
            }
            else
            {
                double d17 = this.locX + this.motX;
                double d21 = this.locY + this.motY;
                double d24 = this.locZ + this.motZ;
                this.setPosition(d17, d21, d24);

                if (this.onGround)
                {
                    this.motX *= 0.5D;
                    this.motY *= 0.5D;
                    this.motZ *= 0.5D;
                }

                this.motX *= 0.9900000095367432D;
                this.motY *= 0.949999988079071D;
                this.motZ *= 0.9900000095367432D;
            }
        }
        else
        {
            if (d3 < 1.0D)
            {
                double d14 = d3 * 2.0D - 1.0D;
                this.motY += 0.03999999910593033D * d14;
            }
            else
            {
                if (this.motY < 0.0D)
                {
                    this.motY /= 2.0D;
                }

                this.motY += 0.007000000216066837D;
            }

            if (this.passenger instanceof EntityLiving)
            {
                EntityLiving entityliving = (EntityLiving)this.passenger;
                float f2 = this.passenger.yaw + -entityliving.aZ * 90.0F;
                this.motX += -Math.sin((double)(f2 * (float)Math.PI / 180.0F)) * this.b * (double)entityliving.ba * 0.05000000074505806D;
                this.motZ += Math.cos((double)(f2 * (float)Math.PI / 180.0F)) * this.b * (double)entityliving.ba * 0.05000000074505806D;
            }
            else if (this.unoccupiedDeceleration >= 0.0D)
            {
                this.motX *= this.unoccupiedDeceleration;
                this.motZ *= this.unoccupiedDeceleration;

                if (this.motX <= 1.0E-5D)
                {
                    this.motX = 0.0D;
                }

                if (this.motZ <= 1.0E-5D)
                {
                    this.motZ = 0.0D;
                }
            }

            double d15 = Math.sqrt(this.motX * this.motX + this.motZ * this.motZ);

            if (d15 > 0.35D)
            {
                double d18 = 0.35D / d15;
                this.motX *= d18;
                this.motZ *= d18;
                d15 = 0.35D;
            }

            if (d15 > d6 && this.b < 0.35D)
            {
                this.b += (0.35D - this.b) / 35.0D;

                if (this.b > 0.35D)
                {
                    this.b = 0.35D;
                }
            }
            else
            {
                this.b -= (this.b - 0.07D) / 35.0D;

                if (this.b < 0.07D)
                {
                    this.b = 0.07D;
                }
            }

            for (int k1 = 0; k1 < 4; ++k1)
            {
                int j2 = MathHelper.floor(this.locX + ((double)(k1 % 2) - 0.5D) * 0.8D);
                int j1 = MathHelper.floor(this.locZ + ((double)(k1 / 2) - 0.5D) * 0.8D);

                for (int k = 0; k < 2; ++k)
                {
                    int l = MathHelper.floor(this.locY) + k;
                    BlockPosition blockposition = new BlockPosition(j2, l, j1);
                    Block block = this.world.getType(blockposition).getBlock();

                    if (block == Blocks.SNOW_LAYER)
                    {
                        if (!CraftEventFactory.callEntityChangeBlockEvent(this, j2, l, j1, Blocks.AIR, 0).isCancelled())
                        {
                            this.world.setAir(blockposition);
                            this.positionChanged = false;
                        }
                    }
                    else if (block == Blocks.WATERLILY && !CraftEventFactory.callEntityChangeBlockEvent(this, j2, l, j1, Blocks.AIR, 0).isCancelled())
                    {
                        this.world.setAir(blockposition, true);
                        this.positionChanged = false;
                    }
                }
            }

            if (this.onGround && !this.landBoats)
            {
                this.motX *= 0.5D;
                this.motY *= 0.5D;
                this.motZ *= 0.5D;
            }

            this.move(this.motX, this.motY, this.motZ);

            if (this.positionChanged && d6 > 0.2975D)
            {
                if (!this.world.isClientSide && !this.dead)
                {
                    Vehicle vehicle1 = (Vehicle)this.getBukkitEntity();
                    VehicleDestroyEvent vehicledestroyevent = new VehicleDestroyEvent(vehicle1, (org.bukkit.entity.Entity)null);
                    this.world.getServer().getPluginManager().callEvent(vehicledestroyevent);

                    if (!vehicledestroyevent.isCancelled())
                    {
                        this.die();

                        if (this.world.getGameRules().getBoolean("doEntityDrops"))
                        {
                            for (int l1 = 0; l1 < 3; ++l1)
                            {
                                this.a(Item.getItemOf(Blocks.PLANKS), 1, 0.0F);
                            }

                            for (int i2 = 0; i2 < 2; ++i2)
                            {
                                this.a(Items.STICK, 1, 0.0F);
                            }
                        }
                    }
                }
            }
            else
            {
                this.motX *= 0.9900000095367432D;
                this.motY *= 0.949999988079071D;
                this.motZ *= 0.9900000095367432D;
            }

            this.pitch = 0.0F;
            double d19 = (double)this.yaw;
            double d22 = this.lastX - this.locX;
            double d25 = this.lastZ - this.locZ;

            if (d22 * d22 + d25 * d25 > 0.001D)
            {
                d19 = (double)((float)(MathHelper.b(d25, d22) * 180.0D / Math.PI));
            }

            double d13 = MathHelper.g(d19 - (double)this.yaw);

            if (d13 > 20.0D)
            {
                d13 = 20.0D;
            }

            if (d13 < -20.0D)
            {
                d13 = -20.0D;
            }

            this.yaw = (float)((double)this.yaw + d13);
            this.setYawPitch(this.yaw, this.pitch);
            Server server = this.world.getServer();
            org.bukkit.World world = this.world.getWorld();
            Location location1 = new Location(world, d0, d1, d2, f, f1);
            Location location = new Location(world, this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            Vehicle vehicle = (Vehicle)this.getBukkitEntity();
            server.getPluginManager().callEvent(new VehicleUpdateEvent(vehicle));

            if (!location1.equals(location))
            {
                VehicleMoveEvent vehiclemoveevent = new VehicleMoveEvent(vehicle, location1, location);
                server.getPluginManager().callEvent(vehiclemoveevent);
            }

            if (!this.world.isClientSide)
            {
                List list = this.world.getEntities(this, this.getBoundingBox().grow(0.20000000298023224D, 0.0D, 0.20000000298023224D));

                if (list != null && !list.isEmpty())
                {
                    for (int i1 = 0; i1 < list.size(); ++i1)
                    {
                        Entity entity = (Entity)list.get(i1);

                        if (entity != this.passenger && entity.ae() && entity instanceof EntityBoat)
                        {
                            entity.collide(this);
                        }
                    }
                }

                if (this.passenger != null && this.passenger.dead)
                {
                    this.passenger.vehicle = null;
                    this.passenger = null;
                }
            }
        }
    }

    public void al()
    {
        if (this.passenger != null)
        {
            double d0 = Math.cos((double)this.yaw * Math.PI / 180.0D) * 0.4D;
            double d1 = Math.sin((double)this.yaw * Math.PI / 180.0D) * 0.4D;
            this.passenger.setPosition(this.locX + d0, this.locY + this.an() + this.passenger.am(), this.locZ + d1);
        }
    }

    protected void b(NBTTagCompound p_b_1_)
    {
    }

    protected void a(NBTTagCompound p_a_1_)
    {
    }

    public boolean e(EntityHuman p_e_1_)
    {
        if (this.passenger != null && this.passenger instanceof EntityHuman && this.passenger != p_e_1_)
        {
            return true;
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

    protected void a(double p_a_1_, boolean p_a_3_, Block p_a_4_, BlockPosition p_a_5_)
    {
        if (p_a_3_)
        {
            if (this.fallDistance > 3.0F)
            {
                this.e(this.fallDistance, 1.0F);

                if (!this.world.isClientSide && !this.dead)
                {
                    Vehicle vehicle = (Vehicle)this.getBukkitEntity();
                    VehicleDestroyEvent vehicledestroyevent = new VehicleDestroyEvent(vehicle, (org.bukkit.entity.Entity)null);
                    this.world.getServer().getPluginManager().callEvent(vehicledestroyevent);

                    if (!vehicledestroyevent.isCancelled())
                    {
                        this.die();

                        if (this.world.getGameRules().getBoolean("doEntityDrops"))
                        {
                            for (int i = 0; i < 3; ++i)
                            {
                                this.a(Item.getItemOf(Blocks.PLANKS), 1, 0.0F);
                            }

                            for (int j = 0; j < 2; ++j)
                            {
                                this.a(Items.STICK, 1, 0.0F);
                            }
                        }
                    }
                }

                this.fallDistance = 0.0F;
            }
        }
        else if (this.world.getType((new BlockPosition(this)).down()).getBlock().getMaterial() != Material.WATER && p_a_1_ < 0.0D)
        {
            this.fallDistance = (float)((double)this.fallDistance - p_a_1_);
        }
    }

    public void setDamage(float p_setDamage_1_)
    {
        this.datawatcher.watch(19, Float.valueOf(p_setDamage_1_));
    }

    public float j()
    {
        return this.datawatcher.getFloat(19);
    }

    public void a(int p_a_1_)
    {
        this.datawatcher.watch(17, Integer.valueOf(p_a_1_));
    }

    public int l()
    {
        return this.datawatcher.getInt(17);
    }

    public void b(int p_b_1_)
    {
        this.datawatcher.watch(18, Integer.valueOf(p_b_1_));
    }

    public int m()
    {
        return this.datawatcher.getInt(18);
    }
}
