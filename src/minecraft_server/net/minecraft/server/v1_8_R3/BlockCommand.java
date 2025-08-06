package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockCommand extends BlockContainer
{
    public static final BlockStateBoolean TRIGGERED = BlockStateBoolean.of("triggered");

    public BlockCommand()
    {
        super(Material.ORE, MaterialMapColor.q);
        this.j(this.blockStateList.getBlockData().set(TRIGGERED, Boolean.valueOf(false)));
    }

    public TileEntity a(World p_a_1_, int p_a_2_)
    {
        return new TileEntityCommand();
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (!p_doPhysics_1_.isClientSide)
        {
            boolean flag = p_doPhysics_1_.isBlockIndirectlyPowered(p_doPhysics_2_);
            boolean flag1 = ((Boolean)p_doPhysics_3_.get(TRIGGERED)).booleanValue();
            org.bukkit.block.Block block = p_doPhysics_1_.getWorld().getBlockAt(p_doPhysics_2_.getX(), p_doPhysics_2_.getY(), p_doPhysics_2_.getZ());
            int i = flag1 ? 15 : 0;
            int j = flag ? 15 : 0;
            BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, i, j);
            p_doPhysics_1_.getServer().getPluginManager().callEvent(blockredstoneevent);

            if (blockredstoneevent.getNewCurrent() > 0 && blockredstoneevent.getOldCurrent() <= 0)
            {
                p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, p_doPhysics_3_.set(TRIGGERED, Boolean.valueOf(true)), 4);
                p_doPhysics_1_.a((BlockPosition)p_doPhysics_2_, (Block)this, this.a(p_doPhysics_1_));
            }
            else if (blockredstoneevent.getNewCurrent() <= 0 && blockredstoneevent.getOldCurrent() > 0)
            {
                p_doPhysics_1_.setTypeAndData(p_doPhysics_2_, p_doPhysics_3_.set(TRIGGERED, Boolean.valueOf(false)), 4);
            }
        }
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        TileEntity tileentity = p_b_1_.getTileEntity(p_b_2_);

        if (tileentity instanceof TileEntityCommand)
        {
            ((TileEntityCommand)tileentity).getCommandBlock().a(p_b_1_);
            p_b_1_.updateAdjacentComparators(p_b_2_, this);
        }
    }

    public int a(World p_a_1_)
    {
        return 1;
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        TileEntity tileentity = p_interact_1_.getTileEntity(p_interact_2_);
        return tileentity instanceof TileEntityCommand ? ((TileEntityCommand)tileentity).getCommandBlock().a(p_interact_4_) : false;
    }

    public boolean isComplexRedstone()
    {
        return true;
    }

    public int l(World p_l_1_, BlockPosition p_l_2_)
    {
        TileEntity tileentity = p_l_1_.getTileEntity(p_l_2_);
        return tileentity instanceof TileEntityCommand ? ((TileEntityCommand)tileentity).getCommandBlock().j() : 0;
    }

    public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_)
    {
        TileEntity tileentity = p_postPlace_1_.getTileEntity(p_postPlace_2_);

        if (tileentity instanceof TileEntityCommand)
        {
            CommandBlockListenerAbstract commandblocklistenerabstract = ((TileEntityCommand)tileentity).getCommandBlock();

            if (p_postPlace_5_.hasName())
            {
                commandblocklistenerabstract.setName(p_postPlace_5_.getName());
            }

            if (!p_postPlace_1_.isClientSide)
            {
                commandblocklistenerabstract.a(p_postPlace_1_.getGameRules().getBoolean("sendCommandFeedback"));
            }
        }
    }

    public int a(Random p_a_1_)
    {
        return 0;
    }

    public int b()
    {
        return 3;
    }

    public IBlockData fromLegacyData(int p_fromLegacyData_1_)
    {
        return this.getBlockData().set(TRIGGERED, Boolean.valueOf((p_fromLegacyData_1_ & 1) > 0));
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        int i = 0;

        if (((Boolean)p_toLegacyData_1_.get(TRIGGERED)).booleanValue())
        {
            i |= 1;
        }

        return i;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {TRIGGERED});
    }

    public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_)
    {
        return this.getBlockData().set(TRIGGERED, Boolean.valueOf(false));
    }
}
