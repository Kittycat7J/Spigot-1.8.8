package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

public class EntityWither extends EntityMonster implements IRangedEntity
{
    private float[] a = new float[2];
    private float[] b = new float[2];
    private float[] c = new float[2];
    private float[] bm = new float[2];
    private int[] bn = new int[2];
    private int[] bo = new int[2];
    private int bp;
    private static final Predicate<Entity> bq = new Predicate()
    {
        public boolean a(Entity p_a_1_)
        {
            return p_a_1_ instanceof EntityLiving && ((EntityLiving)p_a_1_).getMonsterType() != EnumMonsterType.UNDEAD;
        }
        public boolean apply(Object p_apply_1_)
        {
            return this.a((Entity)p_apply_1_);
        }
    };

    public EntityWither(World p_i467_1_)
    {
        super(p_i467_1_);
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9F, 3.5F);
        this.fireProof = true;
        ((Navigation)this.getNavigation()).d(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 40, 20.0F));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityInsentient.class, 0, false, false, bq));
        this.b_ = 50;
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(17, new Integer(0));
        this.datawatcher.a(18, new Integer(0));
        this.datawatcher.a(19, new Integer(0));
        this.datawatcher.a(20, new Integer(0));
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setInt("Invul", this.cl());
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.r(p_a_1_.getInt("Invul"));
    }

    protected String z()
    {
        return "mob.wither.idle";
    }

    protected String bo()
    {
        return "mob.wither.hurt";
    }

    protected String bp()
    {
        return "mob.wither.death";
    }

    public void m()
    {
        this.motY *= 0.6000000238418579D;

        if (!this.world.isClientSide && this.s(0) > 0)
        {
            Entity entity = this.world.a(this.s(0));

            if (entity != null)
            {
                if (this.locY < entity.locY || !this.cm() && this.locY < entity.locY + 5.0D)
                {
                    if (this.motY < 0.0D)
                    {
                        this.motY = 0.0D;
                    }

                    this.motY += (0.5D - this.motY) * 0.6000000238418579D;
                }

                double d0 = entity.locX - this.locX;
                double d1 = entity.locZ - this.locZ;
                double d2 = d0 * d0 + d1 * d1;

                if (d2 > 9.0D)
                {
                    double d3 = (double)MathHelper.sqrt(d2);
                    this.motX += (d0 / d3 * 0.5D - this.motX) * 0.6000000238418579D;
                    this.motZ += (d1 / d3 * 0.5D - this.motZ) * 0.6000000238418579D;
                }
            }
        }

        if (this.motX * this.motX + this.motZ * this.motZ > 0.05000000074505806D)
        {
            this.yaw = (float)MathHelper.b(this.motZ, this.motX) * (180F / (float)Math.PI) - 90.0F;
        }

        super.m();

        for (int j = 0; j < 2; ++j)
        {
            this.bm[j] = this.b[j];
            this.c[j] = this.a[j];
        }

        for (int k = 0; k < 2; ++k)
        {
            int i = this.s(k + 1);
            Entity entity1 = null;

            if (i > 0)
            {
                entity1 = this.world.a(i);
            }

            if (entity1 != null)
            {
                double d8 = this.t(k + 1);
                double d9 = this.u(k + 1);
                double d10 = this.v(k + 1);
                double d4 = entity1.locX - d8;
                double d5 = entity1.locY + (double)entity1.getHeadHeight() - d9;
                double d6 = entity1.locZ - d10;
                double d7 = (double)MathHelper.sqrt(d4 * d4 + d6 * d6);
                float f = (float)(MathHelper.b(d6, d4) * 180.0D / Math.PI) - 90.0F;
                float f1 = (float)(-(MathHelper.b(d5, d7) * 180.0D / Math.PI));
                this.a[k] = this.b(this.a[k], f1, 40.0F);
                this.b[k] = this.b(this.b[k], f, 10.0F);
            }
            else
            {
                this.b[k] = this.b(this.b[k], this.aI, 10.0F);
            }
        }

        boolean flag = this.cm();

        for (int l = 0; l < 3; ++l)
        {
            double d11 = this.t(l);
            double d12 = this.u(l);
            double d13 = this.v(l);
            this.world.addParticle(EnumParticle.SMOKE_NORMAL, d11 + this.random.nextGaussian() * 0.30000001192092896D, d12 + this.random.nextGaussian() * 0.30000001192092896D, d13 + this.random.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);

            if (flag && this.world.random.nextInt(4) == 0)
            {
                this.world.addParticle(EnumParticle.SPELL_MOB, d11 + this.random.nextGaussian() * 0.30000001192092896D, d12 + this.random.nextGaussian() * 0.30000001192092896D, d13 + this.random.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
            }
        }

        if (this.cl() > 0)
        {
            for (int i1 = 0; i1 < 3; ++i1)
            {
                this.world.addParticle(EnumParticle.SPELL_MOB, this.locX + this.random.nextGaussian() * 1.0D, this.locY + (double)(this.random.nextFloat() * 3.3F), this.locZ + this.random.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
            }
        }
    }

    protected void E()
    {
        if (this.cl() > 0)
        {
            int i = this.cl() - 1;

            if (i <= 0)
            {
                ExplosionPrimeEvent explosionprimeevent = new ExplosionPrimeEvent(this.getBukkitEntity(), 7.0F, false);
                this.world.getServer().getPluginManager().callEvent(explosionprimeevent);

                if (!explosionprimeevent.isCancelled())
                {
                    this.world.createExplosion(this, this.locX, this.locY + (double)this.getHeadHeight(), this.locZ, explosionprimeevent.getRadius(), explosionprimeevent.getFire(), this.world.getGameRules().getBoolean("mobGriefing"));
                }

                int j = ((WorldServer)this.world).getServer().getViewDistance() * 16;

                for (EntityPlayer entityplayer : MinecraftServer.getServer().getPlayerList().players)
                {
                    double d0 = this.locX - entityplayer.locX;
                    double d1 = this.locZ - entityplayer.locZ;
                    double d2 = d0 * d0 + d1 * d1;

                    if (this.world.spigotConfig.witherSpawnSoundRadius <= 0 || d2 <= (double)(this.world.spigotConfig.witherSpawnSoundRadius * this.world.spigotConfig.witherSpawnSoundRadius))
                    {
                        if (d2 > (double)(j * j))
                        {
                            double d3 = Math.sqrt(d2);
                            double d4 = entityplayer.locX + d0 / d3 * (double)j;
                            double d5 = entityplayer.locZ + d1 / d3 * (double)j;
                            entityplayer.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1013, new BlockPosition((int)d4, (int)this.locY, (int)d5), 0, true));
                        }
                        else
                        {
                            entityplayer.playerConnection.sendPacket(new PacketPlayOutWorldEvent(1013, new BlockPosition((int)this.locX, (int)this.locY, (int)this.locZ), 0, true));
                        }
                    }
                }
            }

            this.r(i);

            if (this.ticksLived % 10 == 0)
            {
                this.heal(10.0F, RegainReason.WITHER_SPAWN);
            }
        }
        else
        {
            super.E();

            for (int k1 = 1; k1 < 3; ++k1)
            {
                if (this.ticksLived >= this.bn[k1 - 1])
                {
                    this.bn[k1 - 1] = this.ticksLived + 10 + this.random.nextInt(10);

                    if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD)
                    {
                        int k2 = k1 - 1;
                        int j3 = this.bo[k1 - 1];
                        this.bo[k2] = this.bo[k1 - 1] + 1;

                        if (j3 > 15)
                        {
                            float f1 = 10.0F;
                            float f = 5.0F;
                            double d6 = MathHelper.a(this.random, this.locX - (double)f1, this.locX + (double)f1);
                            double d7 = MathHelper.a(this.random, this.locY - (double)f, this.locY + (double)f);
                            double d8 = MathHelper.a(this.random, this.locZ - (double)f1, this.locZ + (double)f1);
                            this.a(k1 + 1, d6, d7, d8, true);
                            this.bo[k1 - 1] = 0;
                        }
                    }

                    int i2 = this.s(k1);

                    if (i2 > 0)
                    {
                        Entity entity = this.world.a(i2);

                        if (entity != null && entity.isAlive() && this.h(entity) <= 900.0D && this.hasLineOfSight(entity))
                        {
                            if (entity instanceof EntityHuman && ((EntityHuman)entity).abilities.isInvulnerable)
                            {
                                this.b(k1, 0);
                            }
                            else
                            {
                                this.a(k1 + 1, (EntityLiving)entity);
                                this.bn[k1 - 1] = this.ticksLived + 40 + this.random.nextInt(20);
                                this.bo[k1 - 1] = 0;
                            }
                        }
                        else
                        {
                            this.b(k1, 0);
                        }
                    }
                    else
                    {
                        List list = this.world.a(EntityLiving.class, this.getBoundingBox().grow(20.0D, 8.0D, 20.0D), Predicates.and(bq, IEntitySelector.d));

                        for (int k3 = 0; k3 < 10 && !list.isEmpty(); ++k3)
                        {
                            EntityLiving entityliving = (EntityLiving)list.get(this.random.nextInt(list.size()));

                            if (entityliving != this && entityliving.isAlive() && this.hasLineOfSight(entityliving))
                            {
                                if (entityliving instanceof EntityHuman)
                                {
                                    if (!((EntityHuman)entityliving).abilities.isInvulnerable)
                                    {
                                        this.b(k1, entityliving.getId());
                                    }
                                }
                                else
                                {
                                    this.b(k1, entityliving.getId());
                                }

                                break;
                            }

                            list.remove(entityliving);
                        }
                    }
                }
            }

            if (this.getGoalTarget() != null)
            {
                this.b(0, this.getGoalTarget().getId());
            }
            else
            {
                this.b(0, 0);
            }

            if (this.bp > 0)
            {
                --this.bp;

                if (this.bp == 0 && this.world.getGameRules().getBoolean("mobGriefing"))
                {
                    int l1 = MathHelper.floor(this.locY);
                    int j2 = MathHelper.floor(this.locX);
                    int l2 = MathHelper.floor(this.locZ);
                    boolean flag = false;

                    for (int i3 = -1; i3 <= 1; ++i3)
                    {
                        for (int l3 = -1; l3 <= 1; ++l3)
                        {
                            for (int k = 0; k <= 3; ++k)
                            {
                                int l = j2 + i3;
                                int i1 = l1 + k;
                                int j1 = l2 + l3;
                                BlockPosition blockposition = new BlockPosition(l, i1, j1);
                                Block block = this.world.getType(blockposition).getBlock();

                                if (block.getMaterial() != Material.AIR && a(block) && !CraftEventFactory.callEntityChangeBlockEvent(this, l, i1, j1, Blocks.AIR, 0).isCancelled())
                                {
                                    flag = this.world.setAir(blockposition, true) || flag;
                                }
                            }
                        }
                    }

                    if (flag)
                    {
                        this.world.a((EntityHuman)null, 1012, new BlockPosition(this), 0);
                    }
                }
            }

            if (this.ticksLived % 20 == 0)
            {
                this.heal(1.0F, RegainReason.REGEN);
            }
        }
    }

    public static boolean a(Block p_a_0_)
    {
        return p_a_0_ != Blocks.BEDROCK && p_a_0_ != Blocks.END_PORTAL && p_a_0_ != Blocks.END_PORTAL_FRAME && p_a_0_ != Blocks.COMMAND_BLOCK && p_a_0_ != Blocks.BARRIER;
    }

    public void n()
    {
        this.r(220);
        this.setHealth(this.getMaxHealth() / 3.0F);
    }

    public void aA()
    {
    }

    public int br()
    {
        return 4;
    }

    private double t(int p_t_1_)
    {
        if (p_t_1_ <= 0)
        {
            return this.locX;
        }
        else
        {
            float f = (this.aI + (float)(180 * (p_t_1_ - 1))) / 180.0F * (float)Math.PI;
            float f1 = MathHelper.cos(f);
            return this.locX + (double)f1 * 1.3D;
        }
    }

    private double u(int p_u_1_)
    {
        return p_u_1_ <= 0 ? this.locY + 3.0D : this.locY + 2.2D;
    }

    private double v(int p_v_1_)
    {
        if (p_v_1_ <= 0)
        {
            return this.locZ;
        }
        else
        {
            float f = (this.aI + (float)(180 * (p_v_1_ - 1))) / 180.0F * (float)Math.PI;
            float f1 = MathHelper.sin(f);
            return this.locZ + (double)f1 * 1.3D;
        }
    }

    private float b(float p_b_1_, float p_b_2_, float p_b_3_)
    {
        float f = MathHelper.g(p_b_2_ - p_b_1_);

        if (f > p_b_3_)
        {
            f = p_b_3_;
        }

        if (f < -p_b_3_)
        {
            f = -p_b_3_;
        }

        return p_b_1_ + f;
    }

    private void a(int p_a_1_, EntityLiving p_a_2_)
    {
        this.a(p_a_1_, p_a_2_.locX, p_a_2_.locY + (double)p_a_2_.getHeadHeight() * 0.5D, p_a_2_.locZ, p_a_1_ == 0 && this.random.nextFloat() < 0.001F);
    }

    private void a(int p_a_1_, double p_a_2_, double p_a_4_, double p_a_6_, boolean p_a_8_)
    {
        this.world.a((EntityHuman)null, 1014, new BlockPosition(this), 0);
        double d0 = this.t(p_a_1_);
        double d1 = this.u(p_a_1_);
        double d2 = this.v(p_a_1_);
        double d3 = p_a_2_ - d0;
        double d4 = p_a_4_ - d1;
        double d5 = p_a_6_ - d2;
        EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, this, d3, d4, d5);

        if (p_a_8_)
        {
            entitywitherskull.setCharged(true);
        }

        entitywitherskull.locY = d1;
        entitywitherskull.locX = d0;
        entitywitherskull.locZ = d2;
        this.world.addEntity(entitywitherskull);
    }

    public void a(EntityLiving p_a_1_, float p_a_2_)
    {
        this.a(0, p_a_1_);
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        if (this.isInvulnerable(p_damageEntity_1_))
        {
            return false;
        }
        else if (p_damageEntity_1_ != DamageSource.DROWN && !(p_damageEntity_1_.getEntity() instanceof EntityWither))
        {
            if (this.cl() > 0 && p_damageEntity_1_ != DamageSource.OUT_OF_WORLD)
            {
                return false;
            }
            else
            {
                if (this.cm())
                {
                    Entity entity = p_damageEntity_1_.i();

                    if (entity instanceof EntityArrow)
                    {
                        return false;
                    }
                }

                Entity entity1 = p_damageEntity_1_.getEntity();

                if (entity1 != null && !(entity1 instanceof EntityHuman) && entity1 instanceof EntityLiving && ((EntityLiving)entity1).getMonsterType() == this.getMonsterType())
                {
                    return false;
                }
                else
                {
                    if (this.bp <= 0)
                    {
                        this.bp = 20;
                    }

                    for (int i = 0; i < this.bo.length; ++i)
                    {
                        this.bo[i] += 3;
                    }

                    return super.damageEntity(p_damageEntity_1_, p_damageEntity_2_);
                }
            }
        }
        else
        {
            return false;
        }
    }

    protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_)
    {
        EntityItem entityitem = this.a(Items.NETHER_STAR, 1);

        if (entityitem != null)
        {
            entityitem.u();
        }

        if (!this.world.isClientSide)
        {
            for (EntityHuman entityhuman : this.world.a(EntityHuman.class, this.getBoundingBox().grow(50.0D, 100.0D, 50.0D)))
            {
                entityhuman.b((Statistic)AchievementList.J);
            }
        }
    }

    protected void D()
    {
        this.ticksFarFromPlayer = 0;
    }

    public void e(float p_e_1_, float p_e_2_)
    {
    }

    public void addEffect(MobEffect p_addEffect_1_)
    {
    }

    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(300.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.6000000238418579D);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(40.0D);
    }

    public int cl()
    {
        return this.datawatcher.getInt(20);
    }

    public void r(int p_r_1_)
    {
        this.datawatcher.watch(20, Integer.valueOf(p_r_1_));
    }

    public int s(int p_s_1_)
    {
        return this.datawatcher.getInt(17 + p_s_1_);
    }

    public void b(int p_b_1_, int p_b_2_)
    {
        this.datawatcher.watch(17 + p_b_1_, Integer.valueOf(p_b_2_));
    }

    public boolean cm()
    {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    public EnumMonsterType getMonsterType()
    {
        return EnumMonsterType.UNDEAD;
    }

    public void mount(Entity p_mount_1_)
    {
        this.vehicle = null;
    }
}
