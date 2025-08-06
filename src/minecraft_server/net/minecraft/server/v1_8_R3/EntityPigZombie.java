package net.minecraft.server.v1_8_R3;

import java.util.UUID;

public class EntityPigZombie extends EntityZombie
{
    private static final UUID b = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
    private static final AttributeModifier c = (new AttributeModifier(b, "Attacking speed boost", 0.05D, 0)).a(false);
    public int angerLevel;
    private int soundDelay;
    private UUID hurtBy;

    public EntityPigZombie(World p_i1243_1_)
    {
        super(p_i1243_1_);
        this.fireProof = true;
    }

    public void b(EntityLiving p_b_1_)
    {
        super.b(p_b_1_);

        if (p_b_1_ != null)
        {
            this.hurtBy = p_b_1_.getUniqueID();
        }
    }

    protected void n()
    {
        this.targetSelector.a(1, new EntityPigZombie.PathfinderGoalAngerOther(this));
        this.targetSelector.a(2, new EntityPigZombie.PathfinderGoalAnger(this));
    }

    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(a).setValue(0.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.23000000417232513D);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(5.0D);
    }

    public void t_()
    {
        super.t_();
    }

    protected void E()
    {
        AttributeInstance attributeinstance = this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED);

        if (this.cm())
        {
            if (!this.isBaby() && !attributeinstance.a(c))
            {
                attributeinstance.b(c);
            }

            --this.angerLevel;
        }
        else if (attributeinstance.a(c))
        {
            attributeinstance.c(c);
        }

        if (this.soundDelay > 0 && --this.soundDelay == 0)
        {
            this.makeSound("mob.zombiepig.zpigangry", this.bB() * 2.0F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
        }

        if (this.angerLevel > 0 && this.hurtBy != null && this.getLastDamager() == null)
        {
            EntityHuman entityhuman = this.world.b(this.hurtBy);
            this.b((EntityLiving)entityhuman);
            this.killer = entityhuman;
            this.lastDamageByPlayerTime = this.be();
        }

        super.E();
    }

    public boolean bR()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    public boolean canSpawn()
    {
        return this.world.a((AxisAlignedBB)this.getBoundingBox(), (Entity)this) && this.world.getCubes(this, this.getBoundingBox()).isEmpty() && !this.world.containsLiquid(this.getBoundingBox());
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setShort("Anger", (short)this.angerLevel);

        if (this.hurtBy != null)
        {
            p_b_1_.setString("HurtBy", this.hurtBy.toString());
        }
        else
        {
            p_b_1_.setString("HurtBy", "");
        }
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.angerLevel = p_a_1_.getShort("Anger");
        String s = p_a_1_.getString("HurtBy");

        if (s.length() > 0)
        {
            this.hurtBy = UUID.fromString(s);
            EntityHuman entityhuman = this.world.b(this.hurtBy);
            this.b((EntityLiving)entityhuman);

            if (entityhuman != null)
            {
                this.killer = entityhuman;
                this.lastDamageByPlayerTime = this.be();
            }
        }
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        if (this.isInvulnerable(p_damageEntity_1_))
        {
            return false;
        }
        else
        {
            Entity entity = p_damageEntity_1_.getEntity();

            if (entity instanceof EntityHuman)
            {
                this.b(entity);
            }

            return super.damageEntity(p_damageEntity_1_, p_damageEntity_2_);
        }
    }

    private void b(Entity p_b_1_)
    {
        this.angerLevel = 400 + this.random.nextInt(400);
        this.soundDelay = this.random.nextInt(40);

        if (p_b_1_ instanceof EntityLiving)
        {
            this.b((EntityLiving)p_b_1_);
        }
    }

    public boolean cm()
    {
        return this.angerLevel > 0;
    }

    protected String z()
    {
        return "mob.zombiepig.zpig";
    }

    protected String bo()
    {
        return "mob.zombiepig.zpighurt";
    }

    protected String bp()
    {
        return "mob.zombiepig.zpigdeath";
    }

    protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_)
    {
        int i = this.random.nextInt(2 + p_dropDeathLoot_2_);

        for (int j = 0; j < i; ++j)
        {
            this.a(Items.ROTTEN_FLESH, 1);
        }

        i = this.random.nextInt(2 + p_dropDeathLoot_2_);

        for (int k = 0; k < i; ++k)
        {
            this.a(Items.GOLD_NUGGET, 1);
        }
    }

    public boolean a(EntityHuman p_a_1_)
    {
        return false;
    }

    protected void getRareDrop()
    {
        this.a(Items.GOLD_INGOT, 1);
    }

    protected void a(DifficultyDamageScaler p_a_1_)
    {
        this.setEquipment(0, new ItemStack(Items.GOLDEN_SWORD));
    }

    public GroupDataEntity prepare(DifficultyDamageScaler p_prepare_1_, GroupDataEntity p_prepare_2_)
    {
        super.prepare(p_prepare_1_, p_prepare_2_);
        this.setVillager(false);
        return p_prepare_2_;
    }

    static class PathfinderGoalAnger extends PathfinderGoalNearestAttackableTarget<EntityHuman>
    {
        public PathfinderGoalAnger(EntityPigZombie p_i1241_1_)
        {
            super(p_i1241_1_, EntityHuman.class, true);
        }

        public boolean a()
        {
            return ((EntityPigZombie)this.e).cm() && super.a();
        }
    }

    static class PathfinderGoalAngerOther extends PathfinderGoalHurtByTarget
    {
        public PathfinderGoalAngerOther(EntityPigZombie p_i1242_1_)
        {
            super(p_i1242_1_, true, new Class[0]);
        }

        protected void a(EntityCreature p_a_1_, EntityLiving p_a_2_)
        {
            super.a(p_a_1_, p_a_2_);

            if (p_a_1_ instanceof EntityPigZombie)
            {
                ((EntityPigZombie)p_a_1_).b((Entity)p_a_2_);
            }
        }
    }
}
