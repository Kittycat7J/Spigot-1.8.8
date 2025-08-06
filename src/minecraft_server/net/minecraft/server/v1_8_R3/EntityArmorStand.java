package net.minecraft.server.v1_8_R3;

import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.CraftEquipmentSlot;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EntityArmorStand extends EntityLiving
{
    private static final Vector3f a = new Vector3f(0.0F, 0.0F, 0.0F);
    private static final Vector3f b = new Vector3f(0.0F, 0.0F, 0.0F);
    private static final Vector3f c = new Vector3f(-10.0F, 0.0F, -10.0F);
    private static final Vector3f d = new Vector3f(-15.0F, 0.0F, 10.0F);
    private static final Vector3f e = new Vector3f(-1.0F, 0.0F, -1.0F);
    private static final Vector3f f = new Vector3f(1.0F, 0.0F, 1.0F);
    private final ItemStack[] items;
    private boolean h;
    private long i;
    private int bi;
    private boolean bj;
    public Vector3f headPose;
    public Vector3f bodyPose;
    public Vector3f leftArmPose;
    public Vector3f rightArmPose;
    public Vector3f leftLegPose;
    public Vector3f rightLegPose;

    public EntityArmorStand(World p_i501_1_)
    {
        super(p_i501_1_);
        this.items = new ItemStack[5];
        this.headPose = a;
        this.bodyPose = b;
        this.leftArmPose = c;
        this.rightArmPose = d;
        this.leftLegPose = e;
        this.rightLegPose = f;
        this.b(true);
        this.noclip = this.hasGravity();
        this.setSize(0.5F, 1.975F);
    }

    public EntityArmorStand(World p_i502_1_, double p_i502_2_, double p_i502_4_, double p_i502_6_)
    {
        this(p_i502_1_);
        this.setPosition(p_i502_2_, p_i502_4_, p_i502_6_);
    }

    public boolean bM()
    {
        return super.bM() && !this.hasGravity();
    }

    protected void h()
    {
        super.h();
        this.datawatcher.a(10, Byte.valueOf((byte)0));
        this.datawatcher.a(11, a);
        this.datawatcher.a(12, b);
        this.datawatcher.a(13, c);
        this.datawatcher.a(14, d);
        this.datawatcher.a(15, e);
        this.datawatcher.a(16, f);
    }

    public ItemStack bA()
    {
        return this.items[0];
    }

    public ItemStack getEquipment(int p_getEquipment_1_)
    {
        return this.items[p_getEquipment_1_];
    }

    public void setEquipment(int p_setEquipment_1_, ItemStack p_setEquipment_2_)
    {
        this.items[p_setEquipment_1_] = p_setEquipment_2_;
    }

    public ItemStack[] getEquipment()
    {
        return this.items;
    }

    public boolean d(int p_d_1_, ItemStack p_d_2_)
    {
        int i;

        if (p_d_1_ == 99)
        {
            i = 0;
        }
        else
        {
            i = p_d_1_ - 100 + 1;

            if (i < 0 || i >= this.items.length)
            {
                return false;
            }
        }

        if (p_d_2_ == null || EntityInsentient.c(p_d_2_) == i || i == 4 && p_d_2_.getItem() instanceof ItemBlock)
        {
            this.setEquipment(i, p_d_2_);
            return true;
        }
        else
        {
            return false;
        }
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.items.length; ++i)
        {
            NBTTagCompound nbttagcompound = new NBTTagCompound();

            if (this.items[i] != null)
            {
                this.items[i].save(nbttagcompound);
            }

            nbttaglist.add(nbttagcompound);
        }

        p_b_1_.set("Equipment", nbttaglist);

        if (this.getCustomNameVisible() && (this.getCustomName() == null || this.getCustomName().length() == 0))
        {
            p_b_1_.setBoolean("CustomNameVisible", this.getCustomNameVisible());
        }

        p_b_1_.setBoolean("Invisible", this.isInvisible());
        p_b_1_.setBoolean("Small", this.isSmall());
        p_b_1_.setBoolean("ShowArms", this.hasArms());
        p_b_1_.setInt("DisabledSlots", this.bi);
        p_b_1_.setBoolean("NoGravity", this.hasGravity());
        p_b_1_.setBoolean("NoBasePlate", this.hasBasePlate());

        if (this.s())
        {
            p_b_1_.setBoolean("Marker", this.s());
        }

        p_b_1_.set("Pose", this.z());
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);

        if (p_a_1_.hasKeyOfType("Equipment", 9))
        {
            NBTTagList nbttaglist = p_a_1_.getList("Equipment", 10);

            for (int i = 0; i < this.items.length; ++i)
            {
                this.items[i] = ItemStack.createStack(nbttaglist.get(i));
            }
        }

        this.setInvisible(p_a_1_.getBoolean("Invisible"));
        this.setSmall(p_a_1_.getBoolean("Small"));
        this.setArms(p_a_1_.getBoolean("ShowArms"));
        this.bi = p_a_1_.getInt("DisabledSlots");
        this.setGravity(p_a_1_.getBoolean("NoGravity"));
        this.setBasePlate(p_a_1_.getBoolean("NoBasePlate"));
        this.n(p_a_1_.getBoolean("Marker"));
        this.bj = !this.s();
        this.noclip = this.hasGravity();
        NBTTagCompound nbttagcompound = p_a_1_.getCompound("Pose");
        this.h(nbttagcompound);
    }

    private void h(NBTTagCompound p_h_1_)
    {
        NBTTagList nbttaglist = p_h_1_.getList("Head", 5);

        if (nbttaglist.size() > 0)
        {
            this.setHeadPose(new Vector3f(nbttaglist));
        }
        else
        {
            this.setHeadPose(a);
        }

        NBTTagList nbttaglist1 = p_h_1_.getList("Body", 5);

        if (nbttaglist1.size() > 0)
        {
            this.setBodyPose(new Vector3f(nbttaglist1));
        }
        else
        {
            this.setBodyPose(b);
        }

        NBTTagList nbttaglist2 = p_h_1_.getList("LeftArm", 5);

        if (nbttaglist2.size() > 0)
        {
            this.setLeftArmPose(new Vector3f(nbttaglist2));
        }
        else
        {
            this.setLeftArmPose(c);
        }

        NBTTagList nbttaglist3 = p_h_1_.getList("RightArm", 5);

        if (nbttaglist3.size() > 0)
        {
            this.setRightArmPose(new Vector3f(nbttaglist3));
        }
        else
        {
            this.setRightArmPose(d);
        }

        NBTTagList nbttaglist4 = p_h_1_.getList("LeftLeg", 5);

        if (nbttaglist4.size() > 0)
        {
            this.setLeftLegPose(new Vector3f(nbttaglist4));
        }
        else
        {
            this.setLeftLegPose(e);
        }

        NBTTagList nbttaglist5 = p_h_1_.getList("RightLeg", 5);

        if (nbttaglist5.size() > 0)
        {
            this.setRightLegPose(new Vector3f(nbttaglist5));
        }
        else
        {
            this.setRightLegPose(f);
        }
    }

    private NBTTagCompound z()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();

        if (!a.equals(this.headPose))
        {
            nbttagcompound.set("Head", this.headPose.a());
        }

        if (!b.equals(this.bodyPose))
        {
            nbttagcompound.set("Body", this.bodyPose.a());
        }

        if (!c.equals(this.leftArmPose))
        {
            nbttagcompound.set("LeftArm", this.leftArmPose.a());
        }

        if (!d.equals(this.rightArmPose))
        {
            nbttagcompound.set("RightArm", this.rightArmPose.a());
        }

        if (!e.equals(this.leftLegPose))
        {
            nbttagcompound.set("LeftLeg", this.leftLegPose.a());
        }

        if (!f.equals(this.rightLegPose))
        {
            nbttagcompound.set("RightLeg", this.rightLegPose.a());
        }

        return nbttagcompound;
    }

    public boolean ae()
    {
        return false;
    }

    protected void s(Entity p_s_1_)
    {
    }

    protected void bL()
    {
        List list = this.world.getEntities(this, this.getBoundingBox());

        if (list != null && !list.isEmpty())
        {
            for (int i = 0; i < list.size(); ++i)
            {
                Entity entity = (Entity)list.get(i);

                if (entity instanceof EntityMinecartAbstract && ((EntityMinecartAbstract)entity).s() == EntityMinecartAbstract.EnumMinecartType.RIDEABLE && this.h(entity) <= 0.2D)
                {
                    entity.collide(this);
                }
            }
        }
    }

    public boolean a(EntityHuman p_a_1_, Vec3D p_a_2_)
    {
        if (this.s())
        {
            return false;
        }
        else if (!this.world.isClientSide && !p_a_1_.isSpectator())
        {
            byte b0 = 0;
            ItemStack itemstack = p_a_1_.bZ();
            boolean flag = itemstack != null;

            if (flag && itemstack.getItem() instanceof ItemArmor)
            {
                ItemArmor itemarmor = (ItemArmor)itemstack.getItem();

                if (itemarmor.b == 3)
                {
                    b0 = 1;
                }
                else if (itemarmor.b == 2)
                {
                    b0 = 2;
                }
                else if (itemarmor.b == 1)
                {
                    b0 = 3;
                }
                else if (itemarmor.b == 0)
                {
                    b0 = 4;
                }
            }

            if (flag && (itemstack.getItem() == Items.SKULL || itemstack.getItem() == Item.getItemOf(Blocks.PUMPKIN)))
            {
                b0 = 4;
            }

            byte b1 = 0;
            boolean flag1 = this.isSmall();
            double d0 = flag1 ? p_a_2_.b * 2.0D : p_a_2_.b;

            if (d0 >= 0.1D && d0 < 0.1D + (flag1 ? 0.8D : 0.45D) && this.items[1] != null)
            {
                b1 = 1;
            }
            else if (d0 >= 0.9D + (flag1 ? 0.3D : 0.0D) && d0 < 0.9D + (flag1 ? 1.0D : 0.7D) && this.items[3] != null)
            {
                b1 = 3;
            }
            else if (d0 >= 0.4D && d0 < 0.4D + (flag1 ? 1.0D : 0.8D) && this.items[2] != null)
            {
                b1 = 2;
            }
            else if (d0 >= 1.6D && this.items[4] != null)
            {
                b1 = 4;
            }

            boolean flag2 = this.items[b1] != null;

            if ((this.bi & 1 << b1) != 0 || (this.bi & 1 << b0) != 0)
            {
                b1 = b0;

                if ((this.bi & 1 << b0) != 0)
                {
                    if ((this.bi & 1) != 0)
                    {
                        return true;
                    }

                    b1 = 0;
                }
            }

            if (flag && b0 == 0 && !this.hasArms())
            {
                return true;
            }
            else
            {
                if (flag)
                {
                    this.a(p_a_1_, b0);
                }
                else if (flag2)
                {
                    this.a(p_a_1_, b1);
                }

                return true;
            }
        }
        else
        {
            return true;
        }
    }

    private void a(EntityHuman p_a_1_, int p_a_2_)
    {
        ItemStack itemstack = this.items[p_a_2_];

        if ((itemstack == null || (this.bi & 1 << p_a_2_ + 8) == 0) && (itemstack != null || (this.bi & 1 << p_a_2_ + 16) == 0))
        {
            int i = p_a_1_.inventory.itemInHandIndex;
            ItemStack itemstack1 = p_a_1_.inventory.getItem(i);
            org.bukkit.inventory.ItemStack itemstack2 = CraftItemStack.asCraftMirror(itemstack);
            org.bukkit.inventory.ItemStack itemstack3 = CraftItemStack.asCraftMirror(itemstack1);
            Player player = (Player)p_a_1_.getBukkitEntity();
            ArmorStand armorstand = (ArmorStand)this.getBukkitEntity();
            EquipmentSlot equipmentslot = CraftEquipmentSlot.getSlot(p_a_2_);
            PlayerArmorStandManipulateEvent playerarmorstandmanipulateevent = new PlayerArmorStandManipulateEvent(player, armorstand, itemstack3, itemstack2, equipmentslot);
            this.world.getServer().getPluginManager().callEvent(playerarmorstandmanipulateevent);

            if (playerarmorstandmanipulateevent.isCancelled())
            {
                return;
            }

            if (p_a_1_.abilities.canInstantlyBuild && (itemstack == null || itemstack.getItem() == Item.getItemOf(Blocks.AIR)) && itemstack1 != null)
            {
                ItemStack itemstack5 = itemstack1.cloneItemStack();
                itemstack5.count = 1;
                this.setEquipment(p_a_2_, itemstack5);
            }
            else if (itemstack1 != null && itemstack1.count > 1)
            {
                if (itemstack == null)
                {
                    ItemStack itemstack4 = itemstack1.cloneItemStack();
                    itemstack4.count = 1;
                    this.setEquipment(p_a_2_, itemstack4);
                    --itemstack1.count;
                }
            }
            else
            {
                this.setEquipment(p_a_2_, itemstack1);
                p_a_1_.inventory.setItem(i, itemstack);
            }
        }
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, p_damageEntity_1_, (double)p_damageEntity_2_))
        {
            return false;
        }
        else if (this.world.isClientSide)
        {
            return false;
        }
        else if (DamageSource.OUT_OF_WORLD.equals(p_damageEntity_1_))
        {
            this.die();
            return false;
        }
        else if (!this.isInvulnerable(p_damageEntity_1_) && !this.h && !this.s())
        {
            if (p_damageEntity_1_.isExplosion())
            {
                this.D();
                this.die();
                return false;
            }
            else if (DamageSource.FIRE.equals(p_damageEntity_1_))
            {
                if (!this.isBurning())
                {
                    this.setOnFire(5);
                }
                else
                {
                    this.a(0.15F);
                }

                return false;
            }
            else if (DamageSource.BURN.equals(p_damageEntity_1_) && this.getHealth() > 0.5F)
            {
                this.a(4.0F);
                return false;
            }
            else
            {
                boolean flag = "arrow".equals(p_damageEntity_1_.p());
                boolean flag1 = "player".equals(p_damageEntity_1_.p());

                if (!flag1 && !flag)
                {
                    return false;
                }
                else
                {
                    if (p_damageEntity_1_.i() instanceof EntityArrow)
                    {
                        p_damageEntity_1_.i().die();
                    }

                    if (p_damageEntity_1_.getEntity() instanceof EntityHuman && !((EntityHuman)p_damageEntity_1_.getEntity()).abilities.mayBuild)
                    {
                        return false;
                    }
                    else if (p_damageEntity_1_.u())
                    {
                        this.A();
                        this.die();
                        return false;
                    }
                    else
                    {
                        long i = this.world.getTime();

                        if (i - this.i > 5L && !flag)
                        {
                            this.i = i;
                        }
                        else
                        {
                            this.C();
                            this.A();
                            this.die();
                        }

                        return false;
                    }
                }
            }
        }
        else
        {
            return false;
        }
    }

    private void A()
    {
        if (this.world instanceof WorldServer)
        {
            ((WorldServer)this.world).a(EnumParticle.BLOCK_DUST, this.locX, this.locY + (double)this.length / 1.5D, this.locZ, 10, (double)(this.width / 4.0F), (double)(this.length / 4.0F), (double)(this.width / 4.0F), 0.05D, new int[] {Block.getCombinedId(Blocks.PLANKS.getBlockData())});
        }
    }

    private void a(float p_a_1_)
    {
        float f = this.getHealth();
        f = f - p_a_1_;

        if (f <= 0.5F)
        {
            this.D();
            this.die();
        }
        else
        {
            this.setHealth(f);
        }
    }

    private void C()
    {
        Block.a(this.world, new BlockPosition(this), new ItemStack(Items.ARMOR_STAND));
        this.D();
    }

    private void D()
    {
        for (int i = 0; i < this.items.length; ++i)
        {
            if (this.items[i] != null && this.items[i].count > 0)
            {
                if (this.items[i] != null)
                {
                    Block.a(this.world, (new BlockPosition(this)).up(), this.items[i]);
                }

                this.items[i] = null;
            }
        }
    }

    protected float h(float p_h_1_, float p_h_2_)
    {
        this.aJ = this.lastYaw;
        this.aI = this.yaw;
        return 0.0F;
    }

    public float getHeadHeight()
    {
        return this.isBaby() ? this.length * 0.5F : this.length * 0.9F;
    }

    public void g(float p_g_1_, float p_g_2_)
    {
        if (!this.hasGravity())
        {
            super.g(p_g_1_, p_g_2_);
        }
    }

    public void t_()
    {
        super.t_();
        Vector3f vector3f = this.datawatcher.h(11);

        if (!this.headPose.equals(vector3f))
        {
            this.setHeadPose(vector3f);
        }

        Vector3f vector3f1 = this.datawatcher.h(12);

        if (!this.bodyPose.equals(vector3f1))
        {
            this.setBodyPose(vector3f1);
        }

        Vector3f vector3f2 = this.datawatcher.h(13);

        if (!this.leftArmPose.equals(vector3f2))
        {
            this.setLeftArmPose(vector3f2);
        }

        Vector3f vector3f3 = this.datawatcher.h(14);

        if (!this.rightArmPose.equals(vector3f3))
        {
            this.setRightArmPose(vector3f3);
        }

        Vector3f vector3f4 = this.datawatcher.h(15);

        if (!this.leftLegPose.equals(vector3f4))
        {
            this.setLeftLegPose(vector3f4);
        }

        Vector3f vector3f5 = this.datawatcher.h(16);

        if (!this.rightLegPose.equals(vector3f5))
        {
            this.setRightLegPose(vector3f5);
        }

        boolean flag = this.s();

        if (!this.bj && flag)
        {
            this.a(false);
        }
        else
        {
            if (!this.bj || flag)
            {
                return;
            }

            this.a(true);
        }

        this.bj = flag;
    }

    private void a(boolean p_a_1_)
    {
        double d0 = this.locX;
        double d1 = this.locY;
        double d2 = this.locZ;

        if (p_a_1_)
        {
            this.setSize(0.5F, 1.975F);
        }
        else
        {
            this.setSize(0.0F, 0.0F);
        }

        this.setPosition(d0, d1, d2);
    }

    protected void B()
    {
        this.setInvisible(this.h);
    }

    public void setInvisible(boolean p_setInvisible_1_)
    {
        this.h = p_setInvisible_1_;
        super.setInvisible(p_setInvisible_1_);
    }

    public boolean isBaby()
    {
        return this.isSmall();
    }

    public void G()
    {
        this.die();
    }

    public boolean aW()
    {
        return this.isInvisible();
    }

    public void setSmall(boolean p_setSmall_1_)
    {
        byte b0 = this.datawatcher.getByte(10);

        if (p_setSmall_1_)
        {
            b0 = (byte)(b0 | 1);
        }
        else
        {
            b0 = (byte)(b0 & -2);
        }

        this.datawatcher.watch(10, Byte.valueOf(b0));
    }

    public boolean isSmall()
    {
        return (this.datawatcher.getByte(10) & 1) != 0;
    }

    public void setGravity(boolean p_setGravity_1_)
    {
        byte b0 = this.datawatcher.getByte(10);

        if (p_setGravity_1_)
        {
            b0 = (byte)(b0 | 2);
        }
        else
        {
            b0 = (byte)(b0 & -3);
        }

        this.datawatcher.watch(10, Byte.valueOf(b0));
    }

    public boolean hasGravity()
    {
        return (this.datawatcher.getByte(10) & 2) != 0;
    }

    public void setArms(boolean p_setArms_1_)
    {
        byte b0 = this.datawatcher.getByte(10);

        if (p_setArms_1_)
        {
            b0 = (byte)(b0 | 4);
        }
        else
        {
            b0 = (byte)(b0 & -5);
        }

        this.datawatcher.watch(10, Byte.valueOf(b0));
    }

    public boolean hasArms()
    {
        return (this.datawatcher.getByte(10) & 4) != 0;
    }

    public void setBasePlate(boolean p_setBasePlate_1_)
    {
        byte b0 = this.datawatcher.getByte(10);

        if (p_setBasePlate_1_)
        {
            b0 = (byte)(b0 | 8);
        }
        else
        {
            b0 = (byte)(b0 & -9);
        }

        this.datawatcher.watch(10, Byte.valueOf(b0));
    }

    public boolean hasBasePlate()
    {
        return (this.datawatcher.getByte(10) & 8) != 0;
    }

    public void n(boolean p_n_1_)
    {
        byte b0 = this.datawatcher.getByte(10);

        if (p_n_1_)
        {
            b0 = (byte)(b0 | 16);
        }
        else
        {
            b0 = (byte)(b0 & -17);
        }

        this.datawatcher.watch(10, Byte.valueOf(b0));
    }

    public boolean s()
    {
        return (this.datawatcher.getByte(10) & 16) != 0;
    }

    public void setHeadPose(Vector3f p_setHeadPose_1_)
    {
        this.headPose = p_setHeadPose_1_;
        this.datawatcher.watch(11, p_setHeadPose_1_);
    }

    public void setBodyPose(Vector3f p_setBodyPose_1_)
    {
        this.bodyPose = p_setBodyPose_1_;
        this.datawatcher.watch(12, p_setBodyPose_1_);
    }

    public void setLeftArmPose(Vector3f p_setLeftArmPose_1_)
    {
        this.leftArmPose = p_setLeftArmPose_1_;
        this.datawatcher.watch(13, p_setLeftArmPose_1_);
    }

    public void setRightArmPose(Vector3f p_setRightArmPose_1_)
    {
        this.rightArmPose = p_setRightArmPose_1_;
        this.datawatcher.watch(14, p_setRightArmPose_1_);
    }

    public void setLeftLegPose(Vector3f p_setLeftLegPose_1_)
    {
        this.leftLegPose = p_setLeftLegPose_1_;
        this.datawatcher.watch(15, p_setLeftLegPose_1_);
    }

    public void setRightLegPose(Vector3f p_setRightLegPose_1_)
    {
        this.rightLegPose = p_setRightLegPose_1_;
        this.datawatcher.watch(16, p_setRightLegPose_1_);
    }

    public Vector3f t()
    {
        return this.headPose;
    }

    public Vector3f u()
    {
        return this.bodyPose;
    }

    public boolean ad()
    {
        return super.ad() && !this.s();
    }
}
