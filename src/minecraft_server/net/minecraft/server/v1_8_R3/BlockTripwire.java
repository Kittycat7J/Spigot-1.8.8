package net.minecraft.server.v1_8_R3;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.plugin.PluginManager;

public class BlockTripwire extends Block
{
    public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");
    public static final BlockStateBoolean SUSPENDED = BlockStateBoolean.of("suspended");
    public static final BlockStateBoolean ATTACHED = BlockStateBoolean.of("attached");
    public static final BlockStateBoolean DISARMED = BlockStateBoolean.of("disarmed");
    public static final BlockStateBoolean NORTH = BlockStateBoolean.of("north");
    public static final BlockStateBoolean EAST = BlockStateBoolean.of("east");
    public static final BlockStateBoolean SOUTH = BlockStateBoolean.of("south");
    public static final BlockStateBoolean WEST = BlockStateBoolean.of("west");

    public BlockTripwire()
    {
        super(Material.ORIENTABLE);
        this.j(this.blockStateList.getBlockData().set(POWERED, Boolean.valueOf(false)).set(SUSPENDED, Boolean.valueOf(false)).set(ATTACHED, Boolean.valueOf(false)).set(DISARMED, Boolean.valueOf(false)).set(NORTH, Boolean.valueOf(false)).set(EAST, Boolean.valueOf(false)).set(SOUTH, Boolean.valueOf(false)).set(WEST, Boolean.valueOf(false)));
        this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
        this.a(true);
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        return p_updateState_1_.set(NORTH, Boolean.valueOf(c(p_updateState_2_, p_updateState_3_, p_updateState_1_, EnumDirection.NORTH))).set(EAST, Boolean.valueOf(c(p_updateState_2_, p_updateState_3_, p_updateState_1_, EnumDirection.EAST))).set(SOUTH, Boolean.valueOf(c(p_updateState_2_, p_updateState_3_, p_updateState_1_, EnumDirection.SOUTH))).set(WEST, Boolean.valueOf(c(p_updateState_2_, p_updateState_3_, p_updateState_1_, EnumDirection.WEST)));
    }

    public AxisAlignedBB a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_)
    {
        return null;
    }

    public boolean c()
    {
        return false;
    }

    public boolean d()
    {
        return false;
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Items.STRING;
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        boolean flag = ((Boolean)p_doPhysics_3_.get(SUSPENDED)).booleanValue();
        boolean flag1 = !World.a((IBlockAccess)p_doPhysics_1_, (BlockPosition)p_doPhysics_2_.down());

        if (flag != flag1)
        {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
            p_doPhysics_1_.setAir(p_doPhysics_2_);
        }
    }

    public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_)
    {
        IBlockData iblockdata = p_updateShape_1_.getType(p_updateShape_2_);
        boolean flag = ((Boolean)iblockdata.get(ATTACHED)).booleanValue();
        boolean flag1 = ((Boolean)iblockdata.get(SUSPENDED)).booleanValue();

        if (!flag1)
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
        }
        else if (!flag)
        {
            this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
        }
        else
        {
            this.a(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
        }
    }

    public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_)
    {
        p_onPlace_3_ = p_onPlace_3_.set(SUSPENDED, Boolean.valueOf(!World.a((IBlockAccess)p_onPlace_1_, (BlockPosition)p_onPlace_2_.down())));
        p_onPlace_1_.setTypeAndData(p_onPlace_2_, p_onPlace_3_, 3);
        this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
    }

    public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_)
    {
        this.e(p_remove_1_, p_remove_2_, p_remove_3_.set(POWERED, Boolean.valueOf(true)));
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EntityHuman p_a_4_)
    {
        if (!p_a_1_.isClientSide && p_a_4_.bZ() != null && p_a_4_.bZ().getItem() == Items.SHEARS)
        {
            p_a_1_.setTypeAndData(p_a_2_, p_a_3_.set(DISARMED, Boolean.valueOf(true)), 4);
        }
    }

    private void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_)
    {
        for (EnumDirection enumdirection : new EnumDirection[] {EnumDirection.SOUTH, EnumDirection.WEST})
        {
            for (int i = 1; i < 42; ++i)
            {
                BlockPosition blockposition = p_e_2_.shift(enumdirection, i);
                IBlockData iblockdata = p_e_1_.getType(blockposition);

                if (iblockdata.getBlock() == Blocks.TRIPWIRE_HOOK)
                {
                    if (iblockdata.get(BlockTripwireHook.FACING) == enumdirection.opposite())
                    {
                        Blocks.TRIPWIRE_HOOK.a(p_e_1_, blockposition, iblockdata, false, true, i, p_e_3_);
                    }

                    break;
                }

                if (iblockdata.getBlock() != Blocks.TRIPWIRE)
                {
                    break;
                }
            }
        }
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_)
    {
        if (!p_a_1_.isClientSide && !((Boolean)p_a_3_.get(POWERED)).booleanValue())
        {
            this.e(p_a_1_, p_a_2_);
        }
    }

    public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Random p_a_4_)
    {
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (!p_b_1_.isClientSide && ((Boolean)p_b_1_.getType(p_b_2_).get(POWERED)).booleanValue())
        {
            this.e(p_b_1_, p_b_2_);
        }
    }

    private void e(World p_e_1_, BlockPosition p_e_2_)
    {
        IBlockData iblockdata = p_e_1_.getType(p_e_2_);
        boolean flag = ((Boolean)iblockdata.get(POWERED)).booleanValue();
        boolean flag1 = false;
        List list = p_e_1_.getEntities((Entity)null, new AxisAlignedBB((double)p_e_2_.getX() + this.minX, (double)p_e_2_.getY() + this.minY, (double)p_e_2_.getZ() + this.minZ, (double)p_e_2_.getX() + this.maxX, (double)p_e_2_.getY() + this.maxY, (double)p_e_2_.getZ() + this.maxZ));

        if (!list.isEmpty())
        {
            for (Entity entity : list)
            {
                if (!entity.aI())
                {
                    flag1 = true;
                    break;
                }
            }
        }

        if (flag != flag1 && flag1 && ((Boolean)iblockdata.get(ATTACHED)).booleanValue())
        {
            org.bukkit.World world = p_e_1_.getWorld();
            PluginManager pluginmanager = p_e_1_.getServer().getPluginManager();
            org.bukkit.block.Block block = world.getBlockAt(p_e_2_.getX(), p_e_2_.getY(), p_e_2_.getZ());
            boolean flag2 = false;
            Iterator iterator = list.iterator();
            label335:

            while (true)
            {
                Cancellable cancellable;

                while (true)
                {
                    if (!iterator.hasNext())
                    {
                        break label335;
                    }

                    Object object = iterator.next();

                    if (object != null)
                    {
                        if (object instanceof EntityHuman)
                        {
                            cancellable = CraftEventFactory.callPlayerInteractEvent((EntityHuman)object, Action.PHYSICAL, p_e_2_, (EnumDirection)null, (ItemStack)null);
                            break;
                        }

                        if (object instanceof Entity)
                        {
                            cancellable = new EntityInteractEvent(((Entity)object).getBukkitEntity(), block);
                            pluginmanager.callEvent((EntityInteractEvent)cancellable);
                            break;
                        }
                    }
                }

                if (!cancellable.isCancelled())
                {
                    flag2 = true;
                    break;
                }
            }

            if (!flag2)
            {
                return;
            }
        }

        if (flag1 != flag)
        {
            iblockdata = iblockdata.set(POWERED, Boolean.valueOf(flag1));
            p_e_1_.setTypeAndData(p_e_2_, iblockdata, 3);
            this.e(p_e_1_, p_e_2_, iblockdata);
        }

        if (flag1)
        {
            p_e_1_.a((BlockPosition)p_e_2_, (Block)this, this.a(p_e_1_));
        }
    }

    public static boolean c(IBlockAccess p_c_0_, BlockPosition p_c_1_, IBlockData p_c_2_, EnumDirection p_c_3_)
    {
        BlockPosition blockposition = p_c_1_.shift(p_c_3_);
        IBlockData iblockdata = p_c_0_.getType(blockposition);
        Block block = iblockdata.getBlock();

        if (block == Blocks.TRIPWIRE_HOOK)
        {
            EnumDirection enumdirection = p_c_3_.opposite();
            return iblockdata.get(BlockTripwireHook.FACING) == enumdirection;
        }
        else if (block == Blocks.TRIPWIRE)
        {
            boolean flag = ((Boolean)p_c_2_.get(SUSPENDED)).booleanValue();
            boolean flag1 = ((Boolean)iblockdata.get(SUSPENDED)).booleanValue();
            return flag == flag1;
        }
        else
        {
            return false;
        }
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(POWERED, Boolean.valueOf((p_fromLegacyData_1_ & 1) > 0)).set(SUSPENDED, Boolean.valueOf((p_fromLegacyData_1_ & 2) > 0)).set(ATTACHED, Boolean.valueOf((p_fromLegacyData_1_ & 4) > 0)).set(DISARMED, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;

        if (((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue())
        {
            i |= 1;
        }

        if (((Boolean)p_toLegacyData_1_.get(SUSPENDED)).booleanValue())
        {
            i |= 2;
        }

        if (((Boolean)p_toLegacyData_1_.get(ATTACHED)).booleanValue())
        {
            i |= 4;
        }

        if (((Boolean)p_toLegacyData_1_.get(DISARMED)).booleanValue())
        {
            i |= 8;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {POWERED, SUSPENDED, ATTACHED, DISARMED, NORTH, EAST, WEST, SOUTH});
    }
}
