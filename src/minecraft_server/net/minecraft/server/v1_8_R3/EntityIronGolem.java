package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

public class EntityIronGolem extends EntityGolem
{
    private int b;
    Village a;
    private int c;
    private int bm;

    public EntityIronGolem(World p_i136_1_)
    {
        super(p_i136_1_);
        this.setSize(1.4F, 2.9F);
        ((Navigation)this.getNavigation()).a(true);
        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9D, 32.0F));
        this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.6D, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
        this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
        this.targetSelector.a(3, new EntityIronGolem.PathfinderGoalNearestGolemTarget(this, EntityInsentient.class, 10, false, true, IMonster.e));
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(16, Byte.valueOf((byte)0));
    }

    protected void E()
    {
        if (--this.b <= 0)
        {
            this.b = 70 + this.random.nextInt(50);
            this.a = this.world.ae().getClosestVillage(new BlockPosition(this), 32);

            if (this.a == null)
            {
                this.cj();
            }
            else
            {
                BlockPosition blockposition = this.a.a();
                this.a(blockposition, (int)((float)this.a.b() * 0.6F));
            }
        }

        super.E();
    }

    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
    }

    protected int j(int p_j_1_)
    {
        return p_j_1_;
    }

    protected void s(Entity p_s_1_)
    {
        if (p_s_1_ instanceof IMonster && !(p_s_1_ instanceof EntityCreeper) && this.bc().nextInt(20) == 0)
        {
            this.setGoalTarget((EntityLiving)p_s_1_, TargetReason.COLLISION, true);
        }

        super.s(p_s_1_);
    }

    public void m()
    {
        super.m();

        if (this.c > 0)
        {
            --this.c;
        }

        if (this.bm > 0)
        {
            --this.bm;
        }

        if (this.motX * this.motX + this.motZ * this.motZ > 2.500000277905201E-7D && this.random.nextInt(5) == 0)
        {
            int i = MathHelper.floor(this.locX);
            int j = MathHelper.floor(this.locY - 0.20000000298023224D);
            int k = MathHelper.floor(this.locZ);
            IBlockData iblockdata = this.world.getType(new BlockPosition(i, j, k));
            Block block = iblockdata.getBlock();

            if (block.getMaterial() != Material.AIR)
            {
                this.world.addParticle(EnumParticle.BLOCK_CRACK, this.locX + ((double)this.random.nextFloat() - 0.5D) * (double)this.width, this.getBoundingBox().b + 0.1D, this.locZ + ((double)this.random.nextFloat() - 0.5D) * (double)this.width, 4.0D * ((double)this.random.nextFloat() - 0.5D), 0.5D, ((double)this.random.nextFloat() - 0.5D) * 4.0D, new int[] {Block.getCombinedId(iblockdata)});
            }
        }
    }

    public boolean a(Class <? extends EntityLiving > p_a_1_)
    {
        return this.isPlayerCreated() && EntityHuman.class.isAssignableFrom(p_a_1_) ? false : (p_a_1_ == EntityCreeper.class ? false : super.a(p_a_1_));
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setBoolean("PlayerCreated", this.isPlayerCreated());
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.setPlayerCreated(p_a_1_.getBoolean("PlayerCreated"));
    }

    public boolean r(Entity p_r_1_)
    {
        this.c = 10;
        this.world.broadcastEntityEffect(this, (byte)4);
        boolean flag = p_r_1_.damageEntity(DamageSource.mobAttack(this), (float)(7 + this.random.nextInt(15)));

        if (flag)
        {
            p_r_1_.motY += 0.4000000059604645D;
            this.a(this, p_r_1_);
        }

        this.makeSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }

    public Village n()
    {
        return this.a;
    }

    public void a(boolean p_a_1_)
    {
        this.bm = p_a_1_ ? 400 : 0;
        this.world.broadcastEntityEffect(this, (byte)11);
    }

    protected String bo()
    {
        return "mob.irongolem.hit";
    }

    protected String bp()
    {
        return "mob.irongolem.death";
    }

    protected void a(BlockPosition p_a_1_, Block p_a_2_)
    {
        this.makeSound("mob.irongolem.walk", 1.0F, 1.0F);
    }

    protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_)
    {
        int i = this.random.nextInt(3);

        for (int j = 0; j < i; ++j)
        {
            this.a(Item.getItemOf(Blocks.RED_FLOWER), 1, (float)BlockFlowers.EnumFlowerVarient.POPPY.b());
        }

        int l1 = 3 + this.random.nextInt(3);

        for (int k = 0; k < l1; ++k)
        {
            this.a(Items.IRON_INGOT, 1);
        }
    }

    public int cm()
    {
        return this.bm;
    }

    public boolean isPlayerCreated()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void setPlayerCreated(boolean p_setPlayerCreated_1_)
    {
        byte b0 = this.datawatcher.getByte(16);

        if (p_setPlayerCreated_1_)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & -2)));
        }
    }

    public void die(DamageSource p_die_1_)
    {
        if (!this.isPlayerCreated() && this.killer != null && this.a != null)
        {
            this.a.a(this.killer.getName(), -5);
        }

        super.die(p_die_1_);
    }

    static class PathfinderGoalNearestGolemTarget<T extends EntityLiving> extends PathfinderGoalNearestAttackableTarget<T>
    {
        public PathfinderGoalNearestGolemTarget(final EntityCreature p_i497_1_, Class<T> p_i497_2_, int p_i497_3_, boolean p_i497_4_, boolean p_i497_5_, final Predicate <? super T > p_i497_6_)
        {
            super(p_i497_1_, p_i497_2_, p_i497_3_, p_i497_4_, p_i497_5_, p_i497_6_);
            this.c = new Predicate()
            {
                public boolean a(T p_a_1_)
                {
                    if (p_i497_6_ != null && !p_i497_6_.apply(p_a_1_))
                    {
                        return false;
                    }
                    else if (p_a_1_ instanceof EntityCreeper)
                    {
                        return false;
                    }
                    else
                    {
                        if (p_a_1_ instanceof EntityHuman)
                        {
                            double d0 = PathfinderGoalNearestGolemTarget.this.f();

                            if (p_a_1_.isSneaking())
                            {
                                d0 *= 0.800000011920929D;
                            }

                            if (p_a_1_.isInvisible())
                            {
                                float f = ((EntityHuman)p_a_1_).bY();

                                if (f < 0.1F)
                                {
                                    f = 0.1F;
                                }

                                d0 *= (double)(0.7F * f);
                            }

                            if ((double)p_a_1_.g(p_i497_1_) > d0)
                            {
                                return false;
                            }
                        }

                        return PathfinderGoalNearestGolemTarget.this.a(p_a_1_, false);
                    }
                }
                public boolean apply(Object p_apply_1_)
                {
                    return this.a((EntityLiving)p_apply_1_);
                }
            };
        }
    }
}
