package net.minecraft.server.v1_8_R3;

import java.util.Iterator;
import java.util.List;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.plugin.PluginManager;

public class BlockPressurePlateBinary extends BlockPressurePlateAbstract
{
    public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
    private final BlockPressurePlateBinary.EnumMobType b;

    protected BlockPressurePlateBinary(Material p_i372_1_, BlockPressurePlateBinary.EnumMobType p_i372_2_)
    {
        super(p_i372_1_);
        this.j(this.blockStateList.getBlockData().set(POWERED, Boolean.valueOf(false)));
        this.b = p_i372_2_;
    }

    protected int e(IBlockData p_e_1_)
    {
        return ((Boolean)p_e_1_.get(POWERED)).booleanValue() ? 15 : 0;
    }

    protected IBlockData a(IBlockData p_a_1_, int p_a_2_)
    {
        return p_a_1_.set(POWERED, Boolean.valueOf(p_a_2_ > 0));
    }

    protected int f(World p_f_1_, BlockPosition p_f_2_)
    {
        AxisAlignedBB axisalignedbb = this.getBoundingBox(p_f_2_);
        List list;

        switch (BlockPressurePlateBinary.SyntheticClass_1.a[this.b.ordinal()])
        {
            case 1:
                list = p_f_1_.getEntities((Entity)null, axisalignedbb);
                break;

            case 2:
                list = p_f_1_.a(EntityLiving.class, axisalignedbb);
                break;

            default:
                return 0;
        }

        if (!list.isEmpty())
        {
            Iterator iterator = list.iterator();

            while (true)
            {
                Entity entity;

                while (true)
                {
                    if (!iterator.hasNext())
                    {
                        return 0;
                    }

                    entity = (Entity)iterator.next();

                    if (this.e(p_f_1_.getType(p_f_2_)) != 0)
                    {
                        break;
                    }

                    org.bukkit.World world = p_f_1_.getWorld();
                    PluginManager pluginmanager = p_f_1_.getServer().getPluginManager();
                    Cancellable cancellable;

                    if (entity instanceof EntityHuman)
                    {
                        cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)entity, Action.PHYSICAL, p_f_2_, (EnumDirection)null, (ItemStack)null);
                    }
                    else
                    {
                        cancellable = new EntityInteractEvent(entity.getBukkitEntity(), world.getBlockAt(p_f_2_.getX(), p_f_2_.getY(), p_f_2_.getZ()));
                        pluginmanager.callEvent((EntityInteractEvent)cancellable);
                    }

                    if (!cancellable.isCancelled())
                    {
                        break;
                    }
                }

                if (!entity.aI())
                {
                    break;
                }
            }

            return 15;
        }
        else
        {
            return 0;
        }
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(POWERED, Boolean.valueOf(p_fromLegacyData_1_ == 1));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return ((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue() ? 1 : 0;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {POWERED});
    }

    public static enum EnumMobType
    {
        EVERYTHING,
        MOBS;
    }

    static class SyntheticClass_1
    {
        static final int[] a = new int[BlockPressurePlateBinary.EnumMobType.values().length];

        static
        {
            try
            {
                a[BlockPressurePlateBinary.EnumMobType.EVERYTHING.ordinal()] = 1;
            }
            catch (NoSuchFieldError var1)
            {
                ;
            }

            try
            {
                a[BlockPressurePlateBinary.EnumMobType.MOBS.ordinal()] = 2;
            }
            catch (NoSuchFieldError var0)
            {
                ;
            }
        }
    }
}
