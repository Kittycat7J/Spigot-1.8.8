package net.minecraft.server.v1_8_R3;

import java.util.UUID;
import org.apache.commons.codec.Charsets;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;

public class EntityItemFrame extends EntityHanging
{
    private float c = 1.0F;

    public EntityItemFrame(World p_i206_1_)
    {
        super(p_i206_1_);
    }

    public EntityItemFrame(World p_i207_1_, BlockPosition p_i207_2_, EnumDirection p_i207_3_)
    {
        super(p_i207_1_, p_i207_2_);
        this.setDirection(p_i207_3_);
    }

    protected void h()
    {
        this.getDataWatcher().add(8, 5);
        this.getDataWatcher().a(9, Byte.valueOf((byte)0));
    }

    public float ao()
    {
        return 0.0F;
    }

    public boolean damageEntity(DamageSource p_damageEntity_1_, float p_damageEntity_2_)
    {
        if (this.isInvulnerable(p_damageEntity_1_))
        {
            return false;
        }
        else if (!p_damageEntity_1_.isExplosion() && this.getItem() != null)
        {
            if (!this.world.isClientSide)
            {
                if (CraftEventFactory.handleNonLivingEntityDamageEvent(this, p_damageEntity_1_, (double)p_damageEntity_2_, false) || this.dead)
                {
                    return true;
                }

                this.a(p_damageEntity_1_.getEntity(), false);
                this.setItem((ItemStack)null);
            }

            return true;
        }
        else
        {
            return super.damageEntity(p_damageEntity_1_, p_damageEntity_2_);
        }
    }

    public int l()
    {
        return 12;
    }

    public int m()
    {
        return 12;
    }

    public void b(Entity p_b_1_)
    {
        this.a(p_b_1_, true);
    }

    public void a(Entity p_a_1_, boolean p_a_2_)
    {
        if (this.world.getGameRules().getBoolean("doEntityDrops"))
        {
            ItemStack itemstack = this.getItem();

            if (p_a_1_ instanceof EntityHuman)
            {
                EntityHuman entityhuman = (EntityHuman)p_a_1_;

                if (entityhuman.abilities.canInstantlyBuild)
                {
                    this.b(itemstack);
                    return;
                }
            }

            if (p_a_2_)
            {
                this.a(new ItemStack(Items.ITEM_FRAME), 0.0F);
            }

            if (itemstack != null && this.random.nextFloat() < this.c)
            {
                itemstack = itemstack.cloneItemStack();
                this.b(itemstack);
                this.a(itemstack, 0.0F);
            }
        }
    }

    private void b(ItemStack p_b_1_)
    {
        if (p_b_1_ != null)
        {
            if (p_b_1_.getItem() == Items.FILLED_MAP)
            {
                WorldMap worldmap = ((ItemWorldMap)p_b_1_.getItem()).getSavedMap(p_b_1_, this.world);
                worldmap.decorations.remove(UUID.nameUUIDFromBytes(("frame-" + this.getId()).getBytes(Charsets.US_ASCII)));
            }

            p_b_1_.a((EntityItemFrame)null);
        }
    }

    public ItemStack getItem()
    {
        return this.getDataWatcher().getItemStack(8);
    }

    public void setItem(ItemStack p_setItem_1_)
    {
        this.setItem(p_setItem_1_, true);
    }

    private void setItem(ItemStack p_setItem_1_, boolean p_setItem_2_)
    {
        if (p_setItem_1_ != null)
        {
            p_setItem_1_ = p_setItem_1_.cloneItemStack();
            p_setItem_1_.count = 1;
            p_setItem_1_.a(this);
        }

        this.getDataWatcher().watch(8, p_setItem_1_);
        this.getDataWatcher().update(8);

        if (p_setItem_2_ && this.blockPosition != null)
        {
            this.world.updateAdjacentComparators(this.blockPosition, Blocks.AIR);
        }
    }

    public int getRotation()
    {
        return this.getDataWatcher().getByte(9);
    }

    public void setRotation(int p_setRotation_1_)
    {
        this.setRotation(p_setRotation_1_, true);
    }

    private void setRotation(int p_setRotation_1_, boolean p_setRotation_2_)
    {
        this.getDataWatcher().watch(9, Byte.valueOf((byte)(p_setRotation_1_ % 8)));

        if (p_setRotation_2_ && this.blockPosition != null)
        {
            this.world.updateAdjacentComparators(this.blockPosition, Blocks.AIR);
        }
    }

    public void b(NBTTagCompound p_b_1_)
    {
        if (this.getItem() != null)
        {
            p_b_1_.set("Item", this.getItem().save(new NBTTagCompound()));
            p_b_1_.setByte("ItemRotation", (byte)this.getRotation());
            p_b_1_.setFloat("ItemDropChance", this.c);
        }

        super.b(p_b_1_);
    }

    public void a(NBTTagCompound p_a_1_)
    {
        NBTTagCompound nbttagcompound = p_a_1_.getCompound("Item");

        if (nbttagcompound != null && !nbttagcompound.isEmpty())
        {
            this.setItem(ItemStack.createStack(nbttagcompound), false);
            this.setRotation(p_a_1_.getByte("ItemRotation"), false);

            if (p_a_1_.hasKeyOfType("ItemDropChance", 99))
            {
                this.c = p_a_1_.getFloat("ItemDropChance");
            }

            if (p_a_1_.hasKey("Direction"))
            {
                this.setRotation(this.getRotation() * 2, false);
            }
        }

        super.a(p_a_1_);
    }

    public boolean e(EntityHuman p_e_1_)
    {
        if (this.getItem() == null)
        {
            ItemStack itemstack = p_e_1_.bA();

            if (itemstack != null && !this.world.isClientSide)
            {
                this.setItem(itemstack);

                if (!p_e_1_.abilities.canInstantlyBuild && --itemstack.count <= 0)
                {
                    p_e_1_.inventory.setItem(p_e_1_.inventory.itemInHandIndex, (ItemStack)null);
                }
            }
        }
        else if (!this.world.isClientSide)
        {
            this.setRotation(this.getRotation() + 1);
        }

        return true;
    }

    public int q()
    {
        return this.getItem() == null ? 0 : this.getRotation() % 8 + 1;
    }
}
