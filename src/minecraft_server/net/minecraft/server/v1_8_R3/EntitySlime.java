package net.minecraft.server.v1_8_R3;

import org.bukkit.entity.Slime;
import org.bukkit.event.entity.SlimeSplitEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntitySlime extends EntityInsentient implements IMonster
{
    public float a;
    public float b;
    public float c;
    private boolean bk;

    public EntitySlime(World p_i81_1_)
    {
        super(p_i81_1_);
        this.moveController = new EntitySlime.ControllerMoveSlime(this);
        this.goalSelector.a(1, new EntitySlime.PathfinderGoalSlimeRandomJump(this));
        this.goalSelector.a(2, new EntitySlime.PathfinderGoalSlimeNearestPlayer(this));
        this.goalSelector.a(3, new EntitySlime.PathfinderGoalSlimeRandomDirection(this));
        this.goalSelector.a(5, new EntitySlime.PathfinderGoalSlimeIdle(this));
        this.targetSelector.a(1, new PathfinderGoalTargetNearestPlayer(this));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTargetInsentient(this, EntityIronGolem.class));
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(16, Byte.valueOf((byte)1));
    }

    public void setSize(int p_setSize_1_)
    {
        this.datawatcher.watch(16, Byte.valueOf((byte)p_setSize_1_));
        this.setSize(0.51000005F * (float)p_setSize_1_, 0.51000005F * (float)p_setSize_1_);
        this.setPosition(this.locX, this.locY, this.locZ);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue((double)(p_setSize_1_ * p_setSize_1_));
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue((double)(0.2F + 0.1F * (float)p_setSize_1_));
        this.setHealth(this.getMaxHealth());
        this.b_ = p_setSize_1_;
    }

    public int getSize()
    {
        return this.datawatcher.getByte(16);
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setInt("Size", this.getSize() - 1);
        p_b_1_.setBoolean("wasOnGround", this.bk);
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        int i = p_a_1_.getInt("Size");

        if (i < 0)
        {
            i = 0;
        }

        this.setSize(i + 1);
        this.bk = p_a_1_.getBoolean("wasOnGround");
    }

    protected EnumParticle n()
    {
        return EnumParticle.SLIME;
    }

    protected String ck()
    {
        return "mob.slime." + (this.getSize() > 1 ? "big" : "small");
    }

    public void t_()
    {
        if (!this.world.isClientSide && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.getSize() > 0)
        {
            this.dead = true;
        }

        this.b += (this.a - this.b) * 0.5F;
        this.c = this.b;
        super.t_();

        if (this.onGround && !this.bk)
        {
            int i = this.getSize();

            for (int j = 0; j < i * 8; ++j)
            {
                float f = this.random.nextFloat() * (float)Math.PI * 2.0F;
                float f1 = this.random.nextFloat() * 0.5F + 0.5F;
                float f2 = MathHelper.sin(f) * (float)i * 0.5F * f1;
                float f3 = MathHelper.cos(f) * (float)i * 0.5F * f1;
                World world = this.world;
                EnumParticle enumparticle = this.n();
                double d0 = this.locX + (double)f2;
                double d1 = this.locZ + (double)f3;
                world.addParticle(enumparticle, d0, this.getBoundingBox().b, d1, 0.0D, 0.0D, 0.0D, new int[0]);
            }

            if (this.cl())
            {
                this.makeSound(this.ck(), this.bB(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
            }

            this.a = -0.5F;
        }
        else if (!this.onGround && this.bk)
        {
            this.a = 1.0F;
        }

        this.bk = this.onGround;
        this.ch();
    }

    protected void ch()
    {
        this.a *= 0.6F;
    }

    protected int cg()
    {
        return this.random.nextInt(20) + 10;
    }

    protected EntitySlime cf()
    {
        return new EntitySlime(this.world);
    }

    public void i(int p_i_1_)
    {
        if (p_i_1_ == 16)
        {
            int i = this.getSize();
            this.setSize(0.51000005F * (float)i, 0.51000005F * (float)i);
            this.yaw = this.aK;
            this.aI = this.aK;

            if (this.V() && this.random.nextInt(20) == 0)
            {
                this.X();
            }
        }

        super.i(p_i_1_);
    }

    public void die()
    {
        int i = this.getSize();

        if (!this.world.isClientSide && i > 1 && this.getHealth() <= 0.0F)
        {
            int j = 2 + this.random.nextInt(3);
            SlimeSplitEvent slimesplitevent = new SlimeSplitEvent((Slime)this.getBukkitEntity(), j);
            this.world.getServer().getPluginManager().callEvent(slimesplitevent);

            if (slimesplitevent.isCancelled() || slimesplitevent.getCount() <= 0)
            {
                super.die();
                return;
            }

            j = slimesplitevent.getCount();

            for (int k = 0; k < j; ++k)
            {
                float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
                float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
                EntitySlime entityslime = this.cf();

                if (this.hasCustomName())
                {
                    entityslime.setCustomName(this.getCustomName());
                }

                if (this.isPersistent())
                {
                    entityslime.bX();
                }

                entityslime.setSize(i / 2);
                entityslime.setPositionRotation(this.locX + (double)f, this.locY + 0.5D, this.locZ + (double)f1, this.random.nextFloat() * 360.0F, 0.0F);
                this.world.addEntity(entityslime, SpawnReason.SLIME_SPLIT);
            }
        }

        super.die();
    }

    public void collide(Entity p_collide_1_)
    {
        super.collide(p_collide_1_);

        if (p_collide_1_ instanceof EntityIronGolem && this.ci())
        {
            this.e((EntityLiving)p_collide_1_);
        }
    }

    public void d(EntityHuman p_d_1_)
    {
        if (this.ci())
        {
            this.e(p_d_1_);
        }
    }

    protected void e(EntityLiving p_e_1_)
    {
        int i = this.getSize();

        if (this.hasLineOfSight(p_e_1_) && this.h(p_e_1_) < 0.6D * (double)i * 0.6D * (double)i && p_e_1_.damageEntity(DamageSource.mobAttack(this), (float)this.cj()))
        {
            this.makeSound("mob.attack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.a(this, p_e_1_);
        }
    }

    public float getHeadHeight()
    {
        return 0.625F * this.length;
    }

    protected boolean ci()
    {
        return this.getSize() > 1;
    }

    protected int cj()
    {
        return this.getSize();
    }

    protected String bo()
    {
        return "mob.slime." + (this.getSize() > 1 ? "big" : "small");
    }

    protected String bp()
    {
        return "mob.slime." + (this.getSize() > 1 ? "big" : "small");
    }

    protected Item getLoot()
    {
        return this.getSize() == 1 ? Items.SLIME : null;
    }

    public boolean bR()
    {
        BlockPosition blockposition = new BlockPosition(MathHelper.floor(this.locX), 0, MathHelper.floor(this.locZ));
        Chunk chunk = this.world.getChunkAtWorldCoords(blockposition);

        if (this.world.getWorldData().getType() == WorldType.FLAT && this.random.nextInt(4) != 1)
        {
            return false;
        }
        else
        {
            if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL)
            {
                BiomeBase biomebase = this.world.getBiome(blockposition);

                if (biomebase == BiomeBase.SWAMPLAND && this.locY > 50.0D && this.locY < 70.0D && this.random.nextFloat() < 0.5F && this.random.nextFloat() < this.world.y() && this.world.getLightLevel(new BlockPosition(this)) <= this.random.nextInt(8))
                {
                    return super.bR();
                }

                if (this.random.nextInt(10) == 0 && chunk.a(987234911L).nextInt(10) == 0 && this.locY < 40.0D)
                {
                    return super.bR();
                }
            }

            return false;
        }
    }

    protected float bB()
    {
        return 0.4F * (float)this.getSize();
    }

    public int bQ()
    {
        return 0;
    }

    protected boolean cn()
    {
        return this.getSize() > 0;
    }

    protected boolean cl()
    {
        return this.getSize() > 2;
    }

    protected void bF()
    {
        this.motY = 0.41999998688697815D;
        this.ai = true;
    }

    public GroupDataEntity prepare(DifficultyDamageScaler p_prepare_1_, GroupDataEntity p_prepare_2_)
    {
        int i = this.random.nextInt(3);

        if (i < 2 && this.random.nextFloat() < 0.5F * p_prepare_1_.c())
        {
            ++i;
        }

        int j = 1 << i;
        this.setSize(j);
        return super.prepare(p_prepare_1_, p_prepare_2_);
    }

    static class ControllerMoveSlime extends ControllerMove
    {
        private float g;
        private int h;
        private EntitySlime i;
        private boolean j;

        public ControllerMoveSlime(EntitySlime p_i251_1_)
        {
            super(p_i251_1_);
            this.i = p_i251_1_;
        }

        public void a(float p_a_1_, boolean p_a_2_)
        {
            this.g = p_a_1_;
            this.j = p_a_2_;
        }

        public void a(double p_a_1_)
        {
            this.e = p_a_1_;
            this.f = true;
        }

        public void c()
        {
            this.a.yaw = this.a(this.a.yaw, this.g, 30.0F);
            this.a.aK = this.a.yaw;
            this.a.aI = this.a.yaw;

            if (!this.f)
            {
                this.a.n(0.0F);
            }
            else
            {
                this.f = false;

                if (this.a.onGround)
                {
                    this.a.k((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));

                    if (this.h-- <= 0)
                    {
                        this.h = this.i.cg();

                        if (this.j)
                        {
                            this.h /= 3;
                        }

                        this.i.getControllerJump().a();

                        if (this.i.cn())
                        {
                            this.i.makeSound(this.i.ck(), this.i.bB(), ((this.i.bc().nextFloat() - this.i.bc().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                        }
                    }
                    else
                    {
                        this.i.aZ = this.i.ba = 0.0F;
                        this.a.k(0.0F);
                    }
                }
                else
                {
                    this.a.k((float)(this.e * this.a.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).getValue()));
                }
            }
        }
    }

    static class PathfinderGoalSlimeIdle extends PathfinderGoal
    {
        private EntitySlime a;

        public PathfinderGoalSlimeIdle(EntitySlime p_i338_1_)
        {
            this.a = p_i338_1_;
            this.a(5);
        }

        public boolean a()
        {
            return true;
        }

        public void e()
        {
            ((EntitySlime.ControllerMoveSlime)this.a.getControllerMove()).a(1.0D);
        }
    }

    static class PathfinderGoalSlimeNearestPlayer extends PathfinderGoal
    {
        private EntitySlime a;
        private int b;

        public PathfinderGoalSlimeNearestPlayer(EntitySlime p_i177_1_)
        {
            this.a = p_i177_1_;
            this.a(2);
        }

        public boolean a()
        {
            EntityLiving entityliving = this.a.getGoalTarget();
            return entityliving == null ? false : (!entityliving.isAlive() ? false : !(entityliving instanceof EntityHuman) || !((EntityHuman)entityliving).abilities.isInvulnerable);
        }

        public void c()
        {
            this.b = 300;
            super.c();
        }

        public boolean b()
        {
            EntityLiving entityliving = this.a.getGoalTarget();
            return entityliving == null ? false : (!entityliving.isAlive() ? false : (entityliving instanceof EntityHuman && ((EntityHuman)entityliving).abilities.isInvulnerable ? false : --this.b > 0));
        }

        public void e()
        {
            this.a.a(this.a.getGoalTarget(), 10.0F, 10.0F);
            ((EntitySlime.ControllerMoveSlime)this.a.getControllerMove()).a(this.a.yaw, this.a.ci());
        }
    }

    static class PathfinderGoalSlimeRandomDirection extends PathfinderGoal
    {
        private EntitySlime a;
        private float b;
        private int c;

        public PathfinderGoalSlimeRandomDirection(EntitySlime p_i407_1_)
        {
            this.a = p_i407_1_;
            this.a(2);
        }

        public boolean a()
        {
            return this.a.getGoalTarget() == null && (this.a.onGround || this.a.V() || this.a.ab());
        }

        public void e()
        {
            if (--this.c <= 0)
            {
                this.c = 40 + this.a.bc().nextInt(60);
                this.b = (float)this.a.bc().nextInt(360);
            }

            ((EntitySlime.ControllerMoveSlime)this.a.getControllerMove()).a(this.b, false);
        }
    }

    static class PathfinderGoalSlimeRandomJump extends PathfinderGoal
    {
        private EntitySlime a;

        public PathfinderGoalSlimeRandomJump(EntitySlime p_i323_1_)
        {
            this.a = p_i323_1_;
            this.a(5);
            ((Navigation)p_i323_1_.getNavigation()).d(true);
        }

        public boolean a()
        {
            return this.a.V() || this.a.ab();
        }

        public void e()
        {
            if (this.a.bc().nextFloat() < 0.8F)
            {
                this.a.getControllerJump().a();
            }

            ((EntitySlime.ControllerMoveSlime)this.a.getControllerMove()).a(1.2D);
        }
    }
}
