package net.minecraft.server.v1_8_R3;

public class EntityRabbit extends EntityAnimal
{
    private EntityRabbit.PathfinderGoalRabbitAvoidTarget<EntityWolf> bm;
    private int bo = 0;
    private int bp = 0;
    private boolean bq = false;
    private boolean br = false;
    private int bs = 0;
    private EntityRabbit.EnumRabbitState bt = EntityRabbit.EnumRabbitState.HOP;
    private int bu = 0;
    private EntityHuman bv = null;

    public EntityRabbit(World p_i48_1_)
    {
        super(p_i48_1_);
        this.setSize(0.6F, 0.7F);
        this.g = new EntityRabbit.ControllerJumpRabbit(this);
        this.moveController = new EntityRabbit.ControllerMoveRabbit(this);
        ((Navigation)this.getNavigation()).a(true);
        this.initializePathFinderGoals();
        this.b(0.0D);
    }

    public void initializePathFinderGoals()
    {
        this.navigation.a(2.5F);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new EntityRabbit.PathfinderGoalRabbitPanic(this, 1.33D));
        this.goalSelector.a(2, new PathfinderGoalTempt(this, 1.0D, Items.CARROT, false));
        this.goalSelector.a(2, new PathfinderGoalTempt(this, 1.0D, Items.GOLDEN_CARROT, false));
        this.goalSelector.a(2, new PathfinderGoalTempt(this, 1.0D, Item.getItemOf(Blocks.YELLOW_FLOWER), false));
        this.goalSelector.a(3, new PathfinderGoalBreed(this, 0.8D));
        this.goalSelector.a(5, new EntityRabbit.PathfinderGoalEatCarrots(this));
        this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
        this.bm = new EntityRabbit.PathfinderGoalRabbitAvoidTarget(this, EntityWolf.class, 16.0F, 1.33D, 1.33D);
        this.goalSelector.a(4, this.bm);
    }

    protected float bE()
    {
        return this.moveController.a() && this.moveController.e() > this.locY + 0.5D ? 0.5F : this.bt.b();
    }

    public void a(EntityRabbit.EnumRabbitState p_a_1_)
    {
        this.bt = p_a_1_;
    }

    public void b(double p_b_1_)
    {
        this.getNavigation().a(p_b_1_);
        this.moveController.a(this.moveController.d(), this.moveController.e(), this.moveController.f(), p_b_1_);
    }

    public void a(boolean p_a_1_, EntityRabbit.EnumRabbitState p_a_2_)
    {
        super.i(p_a_1_);

        if (!p_a_1_)
        {
            if (this.bt == EntityRabbit.EnumRabbitState.ATTACK)
            {
                this.bt = EntityRabbit.EnumRabbitState.HOP;
            }
        }
        else
        {
            this.b(1.5D * (double)p_a_2_.a());
            this.makeSound(this.cm(), this.bB(), ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 0.8F);
        }

        this.bq = p_a_1_;
    }

    public void b(EntityRabbit.EnumRabbitState p_b_1_)
    {
        this.a(true, p_b_1_);
        this.bp = p_b_1_.d();
        this.bo = 0;
    }

    public boolean cl()
    {
        return this.bq;
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(18, Byte.valueOf((byte)0));
    }

    public void E()
    {
        if (this.moveController.b() > 0.8D)
        {
            this.a(EntityRabbit.EnumRabbitState.SPRINT);
        }
        else if (this.bt != EntityRabbit.EnumRabbitState.ATTACK)
        {
            this.a(EntityRabbit.EnumRabbitState.HOP);
        }

        if (this.bs > 0)
        {
            --this.bs;
        }

        if (this.bu > 0)
        {
            this.bu -= this.random.nextInt(3);

            if (this.bu < 0)
            {
                this.bu = 0;
            }
        }

        if (this.onGround)
        {
            if (!this.br)
            {
                this.a(false, EntityRabbit.EnumRabbitState.NONE);
                this.cw();
            }

            if (this.getRabbitType() == 99 && this.bs == 0)
            {
                EntityLiving entityliving = this.getGoalTarget();

                if (entityliving != null && this.h(entityliving) < 16.0D)
                {
                    this.a(entityliving.locX, entityliving.locZ);
                    this.moveController.a(entityliving.locX, entityliving.locY, entityliving.locZ, this.moveController.b());
                    this.b(EntityRabbit.EnumRabbitState.ATTACK);
                    this.br = true;
                }
            }

            EntityRabbit.ControllerJumpRabbit entityrabbit$controllerjumprabbit = (EntityRabbit.ControllerJumpRabbit)this.g;

            if (!entityrabbit$controllerjumprabbit.c())
            {
                if (this.moveController.a() && this.bs == 0)
                {
                    PathEntity pathentity = this.navigation.j();
                    Vec3D vec3d = new Vec3D(this.moveController.d(), this.moveController.e(), this.moveController.f());

                    if (pathentity != null && pathentity.e() < pathentity.d())
                    {
                        vec3d = pathentity.a((Entity)this);
                    }

                    this.a(vec3d.a, vec3d.c);
                    this.b(this.bt);
                }
            }
            else if (!entityrabbit$controllerjumprabbit.d())
            {
                this.ct();
            }
        }

        this.br = this.onGround;
    }

    public void Y()
    {
    }

    private void a(double p_a_1_, double p_a_3_)
    {
        this.yaw = (float)(MathHelper.b(p_a_3_ - this.locZ, p_a_1_ - this.locX) * 180.0D / Math.PI) - 90.0F;
    }

    private void ct()
    {
        ((EntityRabbit.ControllerJumpRabbit)this.g).a(true);
    }

    private void cu()
    {
        ((EntityRabbit.ControllerJumpRabbit)this.g).a(false);
    }

    private void cv()
    {
        this.bs = this.co();
    }

    private void cw()
    {
        this.cv();
        this.cu();
    }

    public void m()
    {
        super.m();

        if (this.bo != this.bp)
        {
            if (this.bo == 0 && !this.world.isClientSide)
            {
                this.world.broadcastEntityEffect(this, (byte)1);
            }

            ++this.bo;
        }
        else if (this.bp != 0)
        {
            this.bo = 0;
            this.bp = 0;
        }
    }

    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.30000001192092896D);
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setInt("RabbitType", this.getRabbitType());
        p_b_1_.setInt("MoreCarrotTicks", this.bu);
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.setRabbitType(p_a_1_.getInt("RabbitType"));
        this.bu = p_a_1_.getInt("MoreCarrotTicks");
    }

    protected String cm()
    {
        return "mob.rabbit.hop";
    }

    protected String z()
    {
        return "mob.rabbit.idle";
    }

    protected String bo()
    {
        return "mob.rabbit.hurt";
    }

    protected String bp()
    {
        return "mob.rabbit.death";
    }

    public boolean r(Entity p_r_1_)
    {
        if (this.getRabbitType() == 99)
        {
            this.makeSound("mob.attack", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            return p_r_1_.damageEntity(DamageSource.mobAttack(this), 8.0F);
        }
        else
        {
            return p_r_1_.damageEntity(DamageSource.mobAttack(this), 3.0F);
        }
    }

    public int br()
    {
        return this.getRabbitType() == 99 ? 8 : super.br();
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        return this.isInvulnerable(p_damageEntity_1_) ? false : super.damageEntity(p_damageEntity_1_, p_damageEntity_2_);
    }

    protected void getRareDrop()
    {
        this.a(new ItemStack(Items.RABBIT_FOOT, 1), 0.0F);
    }

    protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_)
    {
        int i = this.random.nextInt(2) + this.random.nextInt(1 + p_dropDeathLoot_2_);

        for (int j = 0; j < i; ++j)
        {
            this.a(Items.RABBIT_HIDE, 1);
        }

        i = this.random.nextInt(2);

        for (int k1 = 0; k1 < i; ++k1)
        {
            if (this.isBurning())
            {
                this.a(Items.COOKED_RABBIT, 1);
            }
            else
            {
                this.a(Items.RABBIT, 1);
            }
        }
    }

    private boolean a(Item p_a_1_)
    {
        return p_a_1_ == Items.CARROT || p_a_1_ == Items.GOLDEN_CARROT || p_a_1_ == Item.getItemOf(Blocks.YELLOW_FLOWER);
    }

    public EntityRabbit b(EntityAgeable p_b_1_)
    {
        EntityRabbit entityrabbit = new EntityRabbit(this.world);

        if (p_b_1_ instanceof EntityRabbit)
        {
            entityrabbit.setRabbitType(this.random.nextBoolean() ? this.getRabbitType() : ((EntityRabbit)p_b_1_).getRabbitType());
        }

        return entityrabbit;
    }

    public boolean d(ItemStack p_d_1_)
    {
        return p_d_1_ != null && this.a(p_d_1_.getItem());
    }

    public int getRabbitType()
    {
        return this.datawatcher.getByte(18);
    }

    public void setRabbitType(int p_setRabbitType_1_)
    {
        if (p_setRabbitType_1_ == 99)
        {
            this.goalSelector.a((PathfinderGoal)this.bm);
            this.goalSelector.a(4, new EntityRabbit.PathfinderGoalKillerRabbitMeleeAttack(this));
            this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
            this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
            this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityWolf.class, true));

            if (!this.hasCustomName())
            {
                this.setCustomName(LocaleI18n.get("entity.KillerBunny.name"));
            }
        }

        this.datawatcher.watch(18, Byte.valueOf((byte)p_setRabbitType_1_));
    }

    public GroupDataEntity prepare(DifficultyDamageScaler p_prepare_1_, GroupDataEntity p_prepare_2_)
    {
        Object object = super.prepare(p_prepare_1_, p_prepare_2_);
        int i = this.random.nextInt(6);
        boolean flag = false;

        if (object instanceof EntityRabbit.GroupDataRabbit)
        {
            i = ((EntityRabbit.GroupDataRabbit)object).a;
            flag = true;
        }
        else
        {
            object = new EntityRabbit.GroupDataRabbit(i);
        }

        this.setRabbitType(i);

        if (flag)
        {
            this.setAgeRaw(-24000);
        }

        return (GroupDataEntity)object;
    }

    private boolean cx()
    {
        return this.bu == 0;
    }

    protected int co()
    {
        return this.bt.c();
    }

    protected void cp()
    {
        this.world.addParticle(EnumParticle.BLOCK_DUST, this.locX + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, this.locY + 0.5D + (double)(this.random.nextFloat() * this.length), this.locZ + (double)(this.random.nextFloat() * this.width * 2.0F) - (double)this.width, 0.0D, 0.0D, 0.0D, new int[] {Block.getCombinedId(Blocks.CARROTS.fromLegacyData(7))});
        this.bu = 100;
    }

    public EntityAgeable createChild(EntityAgeable p_createChild_1_)
    {
        return this.b(p_createChild_1_);
    }

    public class ControllerJumpRabbit extends ControllerJump
    {
        private EntityRabbit c;
        private boolean d = false;

        public ControllerJumpRabbit(EntityRabbit p_i264_2_)
        {
            super(p_i264_2_);
            this.c = p_i264_2_;
        }

        public boolean c()
        {
            return this.a;
        }

        public boolean d()
        {
            return this.d;
        }

        public void a(boolean p_a_1_)
        {
            this.d = p_a_1_;
        }

        public void b()
        {
            if (this.a)
            {
                this.c.b(EntityRabbit.EnumRabbitState.STEP);
                this.a = false;
            }
        }
    }

    static class ControllerMoveRabbit extends ControllerMove
    {
        private EntityRabbit g;

        public ControllerMoveRabbit(EntityRabbit p_i472_1_)
        {
            super(p_i472_1_);
            this.g = p_i472_1_;
        }

        public void c()
        {
            if (this.g.onGround && !this.g.cl())
            {
                this.g.b(0.0D);
            }

            super.c();
        }
    }

    static enum EnumRabbitState
    {
        NONE(0.0F, 0.0F, 30, 1),
        HOP(0.8F, 0.2F, 20, 10),
        STEP(1.0F, 0.45F, 14, 14),
        SPRINT(1.75F, 0.4F, 1, 8),
        ATTACK(2.0F, 0.7F, 7, 8);

        private final float f;
        private final float g;
        private final int h;
        private final int i;

        private EnumRabbitState(float p_i95_3_, float p_i95_4_, int p_i95_5_, int p_i95_6_)
        {
            this.f = p_i95_3_;
            this.g = p_i95_4_;
            this.h = p_i95_5_;
            this.i = p_i95_6_;
        }

        public float a()
        {
            return this.f;
        }

        public float b()
        {
            return this.g;
        }

        public int c()
        {
            return this.h;
        }

        public int d()
        {
            return this.i;
        }
    }

    public static class GroupDataRabbit implements GroupDataEntity
    {
        public int a;

        public GroupDataRabbit(int p_i159_1_)
        {
            this.a = p_i159_1_;
        }
    }

    static class PathfinderGoalEatCarrots extends PathfinderGoalGotoTarget
    {
        private final EntityRabbit c;
        private boolean d;
        private boolean e = false;

        public PathfinderGoalEatCarrots(EntityRabbit p_i229_1_)
        {
            super(p_i229_1_, 0.699999988079071D, 16);
            this.c = p_i229_1_;
        }

        public boolean a()
        {
            if (this.a <= 0)
            {
                if (!this.c.world.getGameRules().getBoolean("mobGriefing"))
                {
                    return false;
                }

                this.e = false;
                this.d = this.c.cx();
            }

            return super.a();
        }

        public boolean b()
        {
            return this.e && super.b();
        }

        public void c()
        {
            super.c();
        }

        public void d()
        {
            super.d();
        }

        public void e()
        {
            super.e();
            this.c.getControllerLook().a((double)this.b.getX() + 0.5D, (double)(this.b.getY() + 1), (double)this.b.getZ() + 0.5D, 10.0F, (float)this.c.bQ());

            if (this.f())
            {
                World world = this.c.world;
                BlockPosition blockposition = this.b.up();
                IBlockData iblockdata = world.getType(blockposition);
                Block block = iblockdata.getBlock();

                if (this.e && block instanceof BlockCarrots && ((Integer)iblockdata.get(BlockCarrots.AGE)).intValue() == 7)
                {
                    world.setTypeAndData(blockposition, Blocks.AIR.getBlockData(), 2);
                    world.setAir(blockposition, true);
                    this.c.cp();
                }

                this.e = false;
                this.a = 10;
            }
        }

        protected boolean a(World p_a_1_, BlockPosition p_a_2_)
        {
            Block block = p_a_1_.getType(p_a_2_).getBlock();

            if (block == Blocks.FARMLAND)
            {
                p_a_2_ = p_a_2_.up();
                IBlockData iblockdata = p_a_1_.getType(p_a_2_);
                block = iblockdata.getBlock();

                if (block instanceof BlockCarrots && ((Integer)iblockdata.get(BlockCarrots.AGE)).intValue() == 7 && this.d && !this.e)
                {
                    this.e = true;
                    return true;
                }
            }

            return false;
        }
    }

    static class PathfinderGoalKillerRabbitMeleeAttack extends PathfinderGoalMeleeAttack
    {
        public PathfinderGoalKillerRabbitMeleeAttack(EntityRabbit p_i11_1_)
        {
            super(p_i11_1_, EntityLiving.class, 1.4D, true);
        }

        protected double a(EntityLiving p_a_1_)
        {
            return (double)(4.0F + p_a_1_.width);
        }
    }

    static class PathfinderGoalRabbitAvoidTarget<T extends Entity> extends PathfinderGoalAvoidTarget<T>
    {
        private EntityRabbit c;

        public PathfinderGoalRabbitAvoidTarget(EntityRabbit p_i200_1_, Class<T> p_i200_2_, float p_i200_3_, double p_i200_4_, double p_i200_6_)
        {
            super(p_i200_1_, p_i200_2_, p_i200_3_, p_i200_4_, p_i200_6_);
            this.c = p_i200_1_;
        }

        public void e()
        {
            super.e();
        }
    }

    static class PathfinderGoalRabbitPanic extends PathfinderGoalPanic
    {
        private EntityRabbit b;

        public PathfinderGoalRabbitPanic(EntityRabbit p_i453_1_, double p_i453_2_)
        {
            super(p_i453_1_, p_i453_2_);
            this.b = p_i453_1_;
        }

        public void e()
        {
            super.e();
            this.b.b(this.a);
        }
    }
}
