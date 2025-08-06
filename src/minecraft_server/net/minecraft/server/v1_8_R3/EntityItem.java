package net.minecraft.server.v1_8_R3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class EntityItem extends Entity
{
    private static final Logger b = LogManager.getLogger();
    private int age;
    public int pickupDelay;
    private int e;
    private String f;
    private String g;
    public float a;
    private int lastTick;

    public EntityItem(World p_i202_1_, double p_i202_2_, double p_i202_4_, double p_i202_6_)
    {
        super(p_i202_1_);
        this.lastTick = MinecraftServer.currentTick;
        this.e = 5;
        this.a = (float)(Math.random() * Math.PI * 2.0D);
        this.setSize(0.25F, 0.25F);
        this.setPosition(p_i202_2_, p_i202_4_, p_i202_6_);
        this.yaw = (float)(Math.random() * 360.0D);
        this.motX = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
        this.motY = 0.20000000298023224D;
        this.motZ = (double)((float)(Math.random() * 0.20000000298023224D - 0.10000000149011612D));
    }

    public EntityItem(World p_i203_1_, double p_i203_2_, double p_i203_4_, double p_i203_6_, ItemStack p_i203_8_)
    {
        this(p_i203_1_, p_i203_2_, p_i203_4_, p_i203_6_);

        if (p_i203_8_ != null && p_i203_8_.getItem() != null)
        {
            this.setItemStack(p_i203_8_);
        }
    }

    protected boolean s_()
    {
        return false;
    }

    public EntityItem(World p_i204_1_)
    {
        super(p_i204_1_);
        this.lastTick = MinecraftServer.currentTick;
        this.e = 5;
        this.a = (float)(Math.random() * Math.PI * 2.0D);
        this.setSize(0.25F, 0.25F);
        this.setItemStack(new ItemStack(Blocks.AIR, 0));
    }

    protected void h()
    {
        this.getDataWatcher().add(10, 5);
    }

    public void t_()
    {
        if (this.getItemStack() == null)
        {
            this.die();
        }
        else
        {
            super.t_();
            int i = MinecraftServer.currentTick - this.lastTick;

            if (this.pickupDelay != 32767)
            {
                this.pickupDelay -= i;
            }

            if (this.age != -32768)
            {
                this.age += i;
            }

            this.lastTick = MinecraftServer.currentTick;
            this.lastX = this.locX;
            this.lastY = this.locY;
            this.lastZ = this.locZ;
            this.motY -= 0.03999999910593033D;
            this.noclip = this.j(this.locX, (this.getBoundingBox().b + this.getBoundingBox().e) / 2.0D, this.locZ);
            this.move(this.motX, this.motY, this.motZ);
            boolean flag = (int)this.lastX != (int)this.locX || (int)this.lastY != (int)this.locY || (int)this.lastZ != (int)this.locZ;

            if (flag || this.ticksLived % 25 == 0)
            {
                if (this.world.getType(new BlockPosition(this)).getBlock().getMaterial() == Material.LAVA)
                {
                    this.motY = 0.20000000298023224D;
                    this.motX = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    this.motZ = (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    this.makeSound("random.fizz", 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                }

                if (!this.world.isClientSide)
                {
                    this.w();
                }
            }

            float f = 0.98F;

            if (this.onGround)
            {
                f = this.world.getType(new BlockPosition(MathHelper.floor(this.locX), MathHelper.floor(this.getBoundingBox().b) - 1, MathHelper.floor(this.locZ))).getBlock().frictionFactor * 0.98F;
            }

            this.motX *= (double)f;
            this.motY *= 0.9800000190734863D;
            this.motZ *= (double)f;

            if (this.onGround)
            {
                this.motY *= -0.5D;
            }

            this.W();

            if (!this.world.isClientSide && this.age >= this.world.spigotConfig.itemDespawnRate)
            {
                if (CraftEventFactory.callItemDespawnEvent(this).isCancelled())
                {
                    this.age = 0;
                    return;
                }

                this.die();
            }
        }
    }

    public void inactiveTick()
    {
        int i = MinecraftServer.currentTick - this.lastTick;

        if (this.pickupDelay != 32767)
        {
            this.pickupDelay -= i;
        }

        if (this.age != -32768)
        {
            this.age += i;
        }

        this.lastTick = MinecraftServer.currentTick;

        if (!this.world.isClientSide && this.age >= this.world.spigotConfig.itemDespawnRate)
        {
            if (CraftEventFactory.callItemDespawnEvent(this).isCancelled())
            {
                this.age = 0;
                return;
            }

            this.die();
        }
    }

    private void w()
    {
        double d0 = this.world.spigotConfig.itemMerge;

        for (EntityItem entityitem : this.world.a(EntityItem.class, this.getBoundingBox().grow(d0, d0, d0)))
        {
            this.a(entityitem);
        }
    }

    private boolean a(EntityItem p_a_1_)
    {
        if (p_a_1_ == this)
        {
            return false;
        }
        else if (p_a_1_.isAlive() && this.isAlive())
        {
            ItemStack itemstack = this.getItemStack();
            ItemStack itemstack1 = p_a_1_.getItemStack();

            if (this.pickupDelay != 32767 && p_a_1_.pickupDelay != 32767)
            {
                if (this.age != -32768 && p_a_1_.age != -32768)
                {
                    if (itemstack1.getItem() != itemstack.getItem())
                    {
                        return false;
                    }
                    else if (itemstack1.hasTag() ^ itemstack.hasTag())
                    {
                        return false;
                    }
                    else if (itemstack1.hasTag() && !itemstack1.getTag().equals(itemstack.getTag()))
                    {
                        return false;
                    }
                    else if (itemstack1.getItem() == null)
                    {
                        return false;
                    }
                    else if (itemstack1.getItem().k() && itemstack1.getData() != itemstack.getData())
                    {
                        return false;
                    }
                    else if (itemstack1.count < itemstack.count)
                    {
                        return p_a_1_.a(this);
                    }
                    else if (itemstack1.count + itemstack.count > itemstack1.getMaxStackSize())
                    {
                        return false;
                    }
                    else if (CraftEventFactory.callItemMergeEvent(p_a_1_, this).isCancelled())
                    {
                        return false;
                    }
                    else
                    {
                        itemstack.count += itemstack1.count;
                        this.pickupDelay = Math.max(p_a_1_.pickupDelay, this.pickupDelay);
                        this.age = Math.min(p_a_1_.age, this.age);
                        this.setItemStack(itemstack);
                        p_a_1_.die();
                        return true;
                    }
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

    public void j()
    {
        this.age = 4800;
    }

    public boolean W()
    {
        if (this.world.a((AxisAlignedBB)this.getBoundingBox(), (Material)Material.WATER, (Entity)this))
        {
            if (!this.inWater && !this.justCreated)
            {
                this.X();
            }

            this.inWater = true;
        }
        else
        {
            this.inWater = false;
        }

        return this.inWater;
    }

    protected void burn(int p_burn_1_)
    {
        this.damageEntity(DamageSource.FIRE, (float)p_burn_1_);
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        if (this.isInvulnerable(p_damageEntity_1_))
        {
            return false;
        }
        else if (this.getItemStack() != null && this.getItemStack().getItem() == Items.NETHER_STAR && p_damageEntity_1_.isExplosion())
        {
            return false;
        }
        else if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, p_damageEntity_1_, (double)p_damageEntity_2_))
        {
            return false;
        }
        else
        {
            this.ac();
            this.e = (int)((float)this.e - p_damageEntity_2_);

            if (this.e <= 0)
            {
                this.die();
            }

            return false;
        }
    }

    public void b(NBTTagCompound p_b_1_)
    {
        p_b_1_.setShort("Health", (short)((byte)this.e));
        p_b_1_.setShort("Age", (short)this.age);
        p_b_1_.setShort("PickupDelay", (short)this.pickupDelay);

        if (this.n() != null)
        {
            p_b_1_.setString("Thrower", this.f);
        }

        if (this.m() != null)
        {
            p_b_1_.setString("Owner", this.g);
        }

        if (this.getItemStack() != null)
        {
            p_b_1_.set("Item", this.getItemStack().save(new NBTTagCompound()));
        }
    }

    public void a(NBTTagCompound p_a_1_)
    {
        this.e = p_a_1_.getShort("Health") & 255;
        this.age = p_a_1_.getShort("Age");

        if (p_a_1_.hasKey("PickupDelay"))
        {
            this.pickupDelay = p_a_1_.getShort("PickupDelay");
        }

        if (p_a_1_.hasKey("Owner"))
        {
            this.g = p_a_1_.getString("Owner");
        }

        if (p_a_1_.hasKey("Thrower"))
        {
            this.f = p_a_1_.getString("Thrower");
        }

        NBTTagCompound nbttagcompound = p_a_1_.getCompound("Item");

        if (nbttagcompound != null)
        {
            ItemStack itemstack = ItemStack.createStack(nbttagcompound);

            if (itemstack != null)
            {
                this.setItemStack(itemstack);
            }
            else
            {
                this.die();
            }
        }
        else
        {
            this.die();
        }

        if (this.getItemStack() == null)
        {
            this.die();
        }
    }

    public void d(EntityHuman p_d_1_)
    {
        if (!this.world.isClientSide)
        {
            ItemStack itemstack = this.getItemStack();
            int i = itemstack.count;
            int j = p_d_1_.inventory.canHold(itemstack);
            int k = itemstack.count - j;

            if (this.pickupDelay <= 0 && j > 0)
            {
                itemstack.count = j;
                PlayerPickupItemEvent playerpickupitemevent = new PlayerPickupItemEvent((Player)p_d_1_.getBukkitEntity(), (org.bukkit.entity.Item)this.getBukkitEntity(), k);
                this.world.getServer().getPluginManager().callEvent(playerpickupitemevent);
                itemstack.count = j + k;

                if (playerpickupitemevent.isCancelled())
                {
                    return;
                }

                this.pickupDelay = 0;
            }

            if (this.pickupDelay == 0 && (this.g == null || 6000 - this.age <= 200 || this.g.equals(p_d_1_.getName())) && p_d_1_.inventory.pickup(itemstack))
            {
                if (itemstack.getItem() == Item.getItemOf(Blocks.LOG))
                {
                    p_d_1_.b((Statistic)AchievementList.g);
                }

                if (itemstack.getItem() == Item.getItemOf(Blocks.LOG2))
                {
                    p_d_1_.b((Statistic)AchievementList.g);
                }

                if (itemstack.getItem() == Items.LEATHER)
                {
                    p_d_1_.b((Statistic)AchievementList.t);
                }

                if (itemstack.getItem() == Items.DIAMOND)
                {
                    p_d_1_.b((Statistic)AchievementList.w);
                }

                if (itemstack.getItem() == Items.BLAZE_ROD)
                {
                    p_d_1_.b((Statistic)AchievementList.A);
                }

                if (itemstack.getItem() == Items.DIAMOND && this.n() != null)
                {
                    EntityHuman entityhuman = this.world.a(this.n());

                    if (entityhuman != null && entityhuman != p_d_1_)
                    {
                        entityhuman.b((Statistic)AchievementList.x);
                    }
                }

                if (!this.R())
                {
                    this.world.makeSound(p_d_1_, "random.pop", 0.2F, ((this.random.nextFloat() - this.random.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                }

                p_d_1_.receive(this, i);

                if (itemstack.count <= 0)
                {
                    this.die();
                }
            }
        }
    }

    public String getName()
    {
        return this.hasCustomName() ? this.getCustomName() : LocaleI18n.get("item." + this.getItemStack().a());
    }

    public boolean aD()
    {
        return false;
    }

    public void c(int p_c_1_)
    {
        super.c(p_c_1_);

        if (!this.world.isClientSide)
        {
            this.w();
        }
    }

    public ItemStack getItemStack()
    {
        ItemStack itemstack = this.getDataWatcher().getItemStack(10);

        if (itemstack == null)
        {
            if (this.world != null)
            {
                b.error("Item entity " + this.getId() + " has no item?!");
            }

            return new ItemStack(Blocks.STONE);
        }
        else
        {
            return itemstack;
        }
    }

    public void setItemStack(ItemStack p_setItemStack_1_)
    {
        this.getDataWatcher().watch(10, p_setItemStack_1_);
        this.getDataWatcher().update(10);
    }

    public String m()
    {
        return this.g;
    }

    public void b(String p_b_1_)
    {
        this.g = p_b_1_;
    }

    public String n()
    {
        return this.f;
    }

    public void c(String p_c_1_)
    {
        this.f = p_c_1_;
    }

    public void p()
    {
        this.pickupDelay = 10;
    }

    public void q()
    {
        this.pickupDelay = 0;
    }

    public void r()
    {
        this.pickupDelay = 32767;
    }

    public void a(int p_a_1_)
    {
        this.pickupDelay = p_a_1_;
    }

    public boolean s()
    {
        return this.pickupDelay > 0;
    }

    public void u()
    {
        this.age = -6000;
    }

    public void v()
    {
        this.r();
        this.age = 5999;
    }
}
