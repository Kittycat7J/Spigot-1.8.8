package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class EntityPig extends EntityAnimal
{
    private final PathfinderGoalPassengerCarrotStick bm;

    public EntityPig(World p_i147_1_)
    {
        super(p_i147_1_);
        this.setSize(0.9F, 0.9F);
        ((Navigation)this.getNavigation()).a(true);
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
        this.goalSelector.a(2, this.bm = new PathfinderGoalPassengerCarrotStick(this, 0.3F));
        this.goalSelector.a(3, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT_ON_A_STICK, false));
        this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT, false));
        this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 1.1D));
        this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

    protected void initAttributes()
    {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.25D);
    }

    public boolean bW()
    {
        ItemStack itemstack = ((EntityHuman)this.passenger).bA();
        return itemstack != null && itemstack.getItem() == Items.CARROT_ON_A_STICK;
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(16, Byte.valueOf((byte)0));
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        p_b_1_.setBoolean("Saddle", this.hasSaddle());
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.setSaddle(p_a_1_.getBoolean("Saddle"));
    }

    protected String z()
    {
        return "mob.pig.say";
    }

    protected String bo()
    {
        return "mob.pig.say";
    }

    protected String bp()
    {
        return "mob.pig.death";
    }

    protected void a(BlockPosition p_a_1_, Block p_a_2_)
    {
        this.makeSound("mob.pig.step", 0.15F, 1.0F);
    }

    public boolean a(EntityHuman p_a_1_)
    {
        if (super.a(p_a_1_))
        {
            return true;
        }
        else if (!this.hasSaddle() || this.world.isClientSide || this.passenger != null && this.passenger != p_a_1_)
        {
            return false;
        }
        else
        {
            p_a_1_.mount(this);
            return true;
        }
    }

    protected Item getLoot()
    {
        return this.isBurning() ? Items.COOKED_PORKCHOP : Items.PORKCHOP;
    }

    protected void dropDeathLoot(boolean p_dropDeathLoot_1_, int p_dropDeathLoot_2_)
    {
        int i = this.random.nextInt(3) + 1 + this.random.nextInt(1 + p_dropDeathLoot_2_);

        for (int j = 0; j < i; ++j)
        {
            if (this.isBurning())
            {
                this.a(Items.COOKED_PORKCHOP, 1);
            }
            else
            {
                this.a(Items.PORKCHOP, 1);
            }
        }

        if (this.hasSaddle())
        {
            this.a(Items.SADDLE, 1);
        }
    }

    public boolean hasSaddle()
    {
        return (this.datawatcher.getByte(16) & 1) != 0;
    }

    public void setSaddle(boolean p_setSaddle_1_)
    {
        if (p_setSaddle_1_)
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)1));
        }
        else
        {
            this.datawatcher.watch(16, Byte.valueOf((byte)0));
        }
    }

    public void onLightningStrike(EntityLightning p_onLightningStrike_1_)
    {
        if (!this.world.isClientSide && !this.dead)
        {
            EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);

            if (CraftEventFactory.callPigZapEvent(this, p_onLightningStrike_1_, entitypigzombie).isCancelled())
            {
                return;
            }

            entitypigzombie.setEquipment(0, new ItemStack(Items.GOLDEN_SWORD));
            entitypigzombie.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
            entitypigzombie.k(this.ce());

            if (this.hasCustomName())
            {
                entitypigzombie.setCustomName(this.getCustomName());
                entitypigzombie.setCustomNameVisible(this.getCustomNameVisible());
            }

            this.world.addEntity(entitypigzombie, SpawnReason.LIGHTNING);
            this.die();
        }
    }

    public void e(float p_e_1_, float p_e_2_)
    {
        super.e(p_e_1_, p_e_2_);

        if (p_e_1_ > 5.0F && this.passenger instanceof EntityHuman)
        {
            ((EntityHuman)this.passenger).b((Statistic)AchievementList.u);
        }
    }

    public EntityPig b(EntityAgeable p_b_1_)
    {
        return new EntityPig(this.world);
    }

    public boolean d(ItemStack p_d_1_)
    {
        return p_d_1_ != null && p_d_1_.getItem() == Items.CARROT;
    }

    public PathfinderGoalPassengerCarrotStick cm()
    {
        return this.bm;
    }

    public EntityAgeable createChild(EntityAgeable p_createChild_1_)
    {
        return this.b(p_createChild_1_);
    }
}
