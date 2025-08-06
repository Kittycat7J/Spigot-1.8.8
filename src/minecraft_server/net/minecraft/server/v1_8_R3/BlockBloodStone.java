package net.minecraft.server.v1_8_R3;

import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockBloodStone extends Block
{
    public BlockBloodStone()
    {
        super(Material.STONE);
        this.a(CreativeModeTab.b);
    }

    public MaterialMapColor g(IBlockData p_g_1_)
    {
        return MaterialMapColor.K;
    }

    public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_)
    {
        if (p_doPhysics_4_ != null && p_doPhysics_4_.isPowerSource())
        {
            org.bukkit.block.Block block = p_doPhysics_1_.getWorld().getBlockAt(p_doPhysics_2_.getX(), p_doPhysics_2_.getY(), p_doPhysics_2_.getZ());
            int i = block.getBlockPower();
            BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, i, i);
            p_doPhysics_1_.getServer().getPluginManager().callEvent(blockredstoneevent);
        }
    }
}
