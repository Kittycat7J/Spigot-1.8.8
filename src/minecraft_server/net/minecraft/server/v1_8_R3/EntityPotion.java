package net.minecraft.server.v1_8_R3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.PotionSplashEvent;

public class EntityPotion extends EntityProjectile
{
    public ItemStack item;

    public EntityPotion(World p_i62_1_)
    {
        super(p_i62_1_);
    }

    public EntityPotion(World p_i63_1_, EntityLiving p_i63_2_, int p_i63_3_)
    {
        this(p_i63_1_, p_i63_2_, new ItemStack(Items.POTION, 1, p_i63_3_));
    }

    public EntityPotion(World p_i64_1_, EntityLiving p_i64_2_, ItemStack p_i64_3_)
    {
        super(p_i64_1_, p_i64_2_);
        this.item = p_i64_3_;
    }

    public EntityPotion(World p_i65_1_, double p_i65_2_, double p_i65_4_, double p_i65_6_, ItemStack p_i65_8_)
    {
        super(p_i65_1_, p_i65_2_, p_i65_4_, p_i65_6_);
        this.item = p_i65_8_;
    }

    protected float m()
    {
        return 0.05F;
    }

    protected float j()
    {
        return 0.5F;
    }

    protected float l()
    {
        return -20.0F;
    }

    public void setPotionValue(int p_setPotionValue_1_)
    {
        if (this.item == null)
        {
            this.item = new ItemStack(Items.POTION, 1, 0);
        }

        this.item.setData(p_setPotionValue_1_);
    }

    public int getPotionValue()
    {
        if (this.item == null)
        {
            this.item = new ItemStack(Items.POTION, 1, 0);
        }

        return this.item.getData();
    }

    protected void a(MovingObjectPosition p_a_1_)
    {
        if (!this.world.isClientSide)
        {
            List list = Items.POTION.h(this.item);
            AxisAlignedBB axisalignedbb = this.getBoundingBox().grow(4.0D, 2.0D, 4.0D);
            List list1 = this.world.a(EntityLiving.class, axisalignedbb);
            Iterator iterator = list1.iterator();
            HashMap<LivingEntity, Double> hashmap = new HashMap();

            while (iterator.hasNext())
            {
                EntityLiving entityliving = (EntityLiving)iterator.next();
                double d0 = this.h(entityliving);

                if (d0 < 16.0D)
                {
                    double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                    if (entityliving == p_a_1_.entity)
                    {
                        d1 = 1.0D;
                    }

                    hashmap.put((LivingEntity)entityliving.getBukkitEntity(), Double.valueOf(d1));
                }
            }

            PotionSplashEvent potionsplashevent = CraftEventFactory.callPotionSplashEvent(this, hashmap);

            if (!potionsplashevent.isCancelled() && list != null && !list.isEmpty())
            {
                for (LivingEntity livingentity : potionsplashevent.getAffectedEntities())
                {
                    if (livingentity instanceof CraftLivingEntity)
                    {
                        EntityLiving entityliving1 = ((CraftLivingEntity)livingentity).getHandle();
                        double d2 = potionsplashevent.getIntensity(livingentity);

                        for (MobEffect mobeffect : list)
                        {
                            int i = mobeffect.getEffectId();

                            if (this.world.pvpMode || !(this.getShooter() instanceof EntityPlayer) || !(entityliving1 instanceof EntityPlayer) || entityliving1 == this.getShooter() || i != 2 && i != 4 && i != 7 && i != 15 && i != 17 && i != 18 && i != 19)
                            {
                                if (MobEffectList.byId[i].isInstant())
                                {
                                    MobEffectList.byId[i].applyInstantEffect(this, this.getShooter(), entityliving1, mobeffect.getAmplifier(), d2);
                                }
                                else
                                {
                                    int j = (int)(d2 * (double)mobeffect.getDuration() + 0.5D);

                                    if (j > 20)
                                    {
                                        entityliving1.addEffect(new MobEffect(i, j, mobeffect.getAmplifier()));
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.world.triggerEffect(2002, new BlockPosition(this), this.getPotionValue());
            this.die();
        }
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);

        if (p_a_1_.hasKeyOfType("Potion", 10))
        {
            this.item = ItemStack.createStack(p_a_1_.getCompound("Potion"));
        }
        else
        {
            this.setPotionValue(p_a_1_.getInt("potionValue"));
        }

        if (this.item == null)
        {
            this.die();
        }
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);

        if (this.item != null)
        {
            p_b_1_.set("Potion", this.item.save(new NBTTagCompound()));
        }
    }
}
