package net.minecraft.server.v1_8_R3;

import java.util.Calendar;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntitySkeleton extends EntityMonster implements IRangedEntity
{
    private PathfinderGoalArrowAttack a = new PathfinderGoalArrowAttack(this, 1.0D, 20, 60, 15.0F);
    private PathfinderGoalMeleeAttack b = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2D, false);

    public EntitySkeleton(World p_i226_1_)
    {
        super(p_i226_1_);
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
        this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalAvoidTarget(this, EntityWolf.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
        this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));

        if (p_i226_1_ != null && !p_i226_1_.isClientSide)
        {
            this.n();
        }
    }

    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(13, new Byte((byte)0));
    }

    protected String z()
    {
        return "mob.skeleton.say";
    }

    protected String bo()
    {
        return "mob.skeleton.hurt";
    }

    protected String bp()
    {
        return "mob.skeleton.death";
    }

    protected void a(BlockPosition p_a_1_, Block p_a_2_)
    {
        this.makeSound("mob.skeleton.step", 0.15F, 1.0F);
    }

    public boolean r(Entity p_r_1_)
    {
        if (super.r(p_r_1_))
        {
            if (this.getSkeletonType() == 1 && p_r_1_ instanceof EntityLiving)
            {
                ((EntityLiving)p_r_1_).addEffect(new MobEffect(MobEffectList.WITHER.id, 200));
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    public EnumMonsterType getMonsterType()
    {
        return EnumMonsterType.UNDEAD;
    }

    public void m()
    {
        if (this.world.w() && !this.world.isClientSide)
        {
            float f = this.c(1.0F);
            BlockPosition blockposition = new BlockPosition(this.locX, (double)Math.round(this.locY), this.locZ);

            if (f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.i(blockposition))
            {
                boolean flag = true;
                ItemStack itemstack = this.getEquipment(4);

                if (itemstack != null)
                {
                    if (itemstack.e())
                    {
                        itemstack.setData(itemstack.h() + this.random.nextInt(2));

                        if (itemstack.h() >= itemstack.j())
                        {
                            this.b(itemstack);
                            this.setEquipment(4, (ItemStack)null);
                        }
                    }

                    flag = false;
                }

                if (flag)
                {
                    EntityCombustEvent entitycombustevent = new EntityCombustEvent(this.getBukkitEntity(), 8);
                    this.world.getServer().getPluginManager().callEvent(entitycombustevent);

                    if (!entitycombustevent.isCancelled())
                    {
                        this.setOnFire(entitycombustevent.getDuration());
                    }
                }
            }
        }

        if (this.world.isClientSide && this.getSkeletonType() == 1)
        {
            this.setSize(0.72F, 2.535F);
        }

        super.m();
    }

    public void ak()
    {
        super.ak();

        if (this.vehicle instanceof EntityCreature)
        {
            EntityCreature entitycreature = (EntityCreature)this.vehicle;
            this.aI = entitycreature.aI;
        }
    }

    public void die(DamageSource p_die_1_)
    {
        if (p_die_1_.i() instanceof EntityArrow && p_die_1_.getEntity() instanceof EntityHuman)
        {
            EntityHuman entityhuman = (EntityHuman)p_die_1_.getEntity();
            double d0 = entityhuman.locX - this.locX;
            double d1 = entityhuman.locZ - this.locZ;

            if (d0 * d0 + d1 * d1 >= 2500.0D)
            {
                entityhuman.b((Statistic)AchievementList.v);
            }
        }
        else if (p_die_1_.getEntity() instanceof EntityCreeper && ((EntityCreeper)p_die_1_.getEntity()).isPowered() && ((EntityCreeper)p_die_1_.getEntity()).cp())
        {
            ((EntityCreeper)p_die_1_.getEntity()).cq();
            this.headDrop = new ItemStack(Items.SKULL, 1, this.getSkeletonType() == 1 ? 1 : 0);
        }

        super.die(p_die_1_);
    }

    protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_)
    {
        super.dropDeathLoot(p_dropDeathLoot_1_, p_dropDeathLoot_2_);

        if (this.getSkeletonType() == 1)
        {
            int i = this.random.nextInt(3 + p_dropDeathLoot_2_) - 1;

            for (int j = 0; j < i; ++j)
            {
                this.a(Items.COAL, 1);
            }
        }
        else
        {
            int k = this.random.nextInt(3 + p_dropDeathLoot_2_);

            for (int i1 = 0; i1 < k; ++i1)
            {
                this.a(Items.ARROW, 1);
            }
        }

        int l = this.random.nextInt(3 + p_dropDeathLoot_2_);

        for (int j1 = 0; j1 < l; ++j1)
        {
            this.a(Items.BONE, 1);
        }
    }

    protected void getRareDrop()
    {
        if (this.getSkeletonType() == 1)
        {
            this.a(new ItemStack(Items.SKULL, 1, 1), 0.0F);
        }
    }

    protected void a(DifficultyDamageScaler p_a_1_)
    {
        super.a(p_a_1_);
        this.setEquipment(0, new ItemStack(Items.BOW));
    }

    public GroupDataEntity prepare(DifficultyDamageScaler p_prepare_1_, GroupDataEntity p_prepare_2_)
    {
        p_prepare_2_ = super.prepare(p_prepare_1_, p_prepare_2_);

        if (this.world.worldProvider instanceof WorldProviderHell && this.bc().nextInt(5) > 0)
        {
            this.goalSelector.a(4, this.b);
            this.setSkeletonType(1);
            this.setEquipment(0, new ItemStack(Items.STONE_SWORD));
            this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(4.0D);
        }
        else
        {
            this.goalSelector.a(4, this.a);
            this.a(p_prepare_1_);
            this.b(p_prepare_1_);
        }

        this.j(this.random.nextFloat() < 0.55F * p_prepare_1_.c());

        if (this.getEquipment(4) == null)
        {
            Calendar calendar = this.world.Y();

            if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.random.nextFloat() < 0.25F)
            {
                this.setEquipment(4, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
                this.dropChances[4] = 0.0F;
            }
        }

        return p_prepare_2_;
    }

    public void n()
    {
        this.goalSelector.a((PathfinderGoal)this.b);
        this.goalSelector.a((PathfinderGoal)this.a);
        ItemStack itemstack = this.bA();

        if (itemstack != null && itemstack.getItem() == Items.BOW)
        {
            this.goalSelector.a(4, this.a);
        }
        else
        {
            this.goalSelector.a(4, this.b);
        }
    }

    public void a(EntityLiving p_a_1_, float p_a_2_)
    {
        EntityArrow entityarrow = new EntityArrow(this.world, this, p_a_1_, 1.6F, (float)(14 - this.world.getDifficulty().a() * 4));
        int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, this.bA());
        int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, this.bA());
        entityarrow.b((double)(p_a_2_ * 2.0F) + this.random.nextGaussian() * 0.25D + (double)((float)this.world.getDifficulty().a() * 0.11F));

        if (i > 0)
        {
            entityarrow.b(entityarrow.j() + (double)i * 0.5D + 0.5D);
        }

        if (j > 0)
        {
            entityarrow.setKnockbackStrength(j);
        }

        if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, this.bA()) > 0 || this.getSkeletonType() == 1)
        {
            EntityCombustEvent entitycombustevent = new EntityCombustEvent(entityarrow.getBukkitEntity(), 100);
            this.world.getServer().getPluginManager().callEvent(entitycombustevent);

            if (!entitycombustevent.isCancelled())
            {
                entityarrow.setOnFire(entitycombustevent.getDuration());
            }
        }

        EntityShootBowEvent entityshootbowevent = CraftEventFactory.callEntityShootBowEvent(this, this.bA(), entityarrow, 0.8F);

        if (entityshootbowevent.isCancelled())
        {
            entityshootbowevent.getProjectile().remove();
        }
        else
        {
            if (entityshootbowevent.getProjectile() == entityarrow.getBukkitEntity())
            {
                this.world.addEntity(entityarrow);
            }

            this.makeSound("random.bow", 1.0F, 1.0F / (this.bc().nextFloat() * 0.4F + 0.8F));
        }
    }

    public int getSkeletonType()
    {
        return this.datawatcher.getByte(13);
    }

    public void setSkeletonType(int p_setSkeletonType_1_)
    {
        this.datawatcher.watch(13, Byte.valueOf((byte)p_setSkeletonType_1_));
        this.fireProof = p_setSkeletonType_1_ == 1;

        if (p_setSkeletonType_1_ == 1)
        {
            this.setSize(0.72F, 2.535F);
        }
        else
        {
            this.setSize(0.6F, 1.95F);
        }
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);

        if (p_a_1_.hasKeyOfType("SkeletonType", 99))
        {
            byte b0 = p_a_1_.getByte("SkeletonType");
            this.setSkeletonType(b0);
        }

        this.n();
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setByte("SkeletonType", (byte)this.getSkeletonType());
    }

    public void setEquipment(int p_setEquipment_1_, ItemStack p_setEquipment_2_)
    {
        super.setEquipment(p_setEquipment_1_, p_setEquipment_2_);

        if (!this.world.isClientSide && p_setEquipment_1_ == 0)
        {
            this.n();
        }
    }

    public float getHeadHeight()
    {
        return this.getSkeletonType() == 1 ? super.getHeadHeight() : 1.74F;
    }

    public double am()
    {
        return this.isBaby() ? 0.0D : -0.35D;
    }
}
