package net.minecraft.server.v1_8_R3;

import java.util.Random;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockSpreadEvent;

public class BlockMycel extends Block
{
    public static final BlockStateBoolean SNOWY = BlockStateBoolean.of("snowy");

    protected BlockMycel()
    {
        super(Material.GRASS, MaterialMapColor.z);
        this.j(this.blockStateList.getBlockData().set(SNOWY, Boolean.valueOf(false)));
        this.a(true);
        this.a(CreativeModeTab.b);
    }

    public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_)
    {
        Block block = p_updateState_2_.getType(p_updateState_3_.up()).getBlock();
        return p_updateState_1_.set(SNOWY, Boolean.valueOf(block == Blocks.SNOW || block == Blocks.SNOW_LAYER));
    }

    public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_)
    {
        if (!p_b_1_.isClientSide)
        {
            if (p_b_1_.getLightLevel(p_b_2_.up()) < 4 && p_b_1_.getType(p_b_2_.up()).getBlock().p() > 2)
            {
                org.bukkit.World world1 = p_b_1_.getWorld();
                org.bukkit.block.BlockState blockstate1 = world1.getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()).getState();
                blockstate1.setType(CraftMagicNumbers.getMaterial(Blocks.DIRT));
                BlockFadeEvent blockfadeevent = new BlockFadeEvent(blockstate1.getBlock(), blockstate1);
                p_b_1_.getServer().getPluginManager().callEvent(blockfadeevent);

                if (!blockfadeevent.isCancelled())
                {
                    blockstate1.update(true);
                }
            }
            else if (p_b_1_.getLightLevel(p_b_2_.up()) >= 9)
            {
                for (int i = 0; i < Math.min(4, Math.max(20, (int)(400.0F / p_b_1_.growthOdds))); ++i)
                {
                    BlockPosition blockposition = p_b_2_.a(p_b_4_.nextInt(3) - 1, p_b_4_.nextInt(5) - 3, p_b_4_.nextInt(3) - 1);
                    IBlockData iblockdata = p_b_1_.getType(blockposition);
                    Block block = p_b_1_.getType(blockposition.up()).getBlock();

                    if (iblockdata.getBlock() == Blocks.DIRT && iblockdata.get(BlockDirt.VARIANT) == BlockDirt.EnumDirtVariant.DIRT && p_b_1_.getLightLevel(blockposition.up()) >= 4 && block.p() <= 2)
                    {
                        org.bukkit.World world = p_b_1_.getWorld();
                        org.bukkit.block.BlockState blockstate = world.getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()).getState();
                        blockstate.setType(CraftMagicNumbers.getMaterial((Block)this));
                        BlockSpreadEvent blockspreadevent = new BlockSpreadEvent(blockstate.getBlock(), world.getBlockAt(p_b_2_.getX(), p_b_2_.getY(), p_b_2_.getZ()), blockstate);
                        p_b_1_.getServer().getPluginManager().callEvent(blockspreadevent);

                        if (!blockspreadevent.isCancelled())
                        {
                            blockstate.update(true);
                        }
                    }
                }
            }
        }
    }

    public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_)
    {
        return Blocks.DIRT.getDropType(Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT), p_getDropType_2_, p_getDropType_3_);
    }

    public int toLegacyData(IBlockData p_toLegacyData_1_)
    {
        return 0;
    }

    protected BlockStateList getStateList()
    {
        return new BlockStateList(this, new IBlockState[] {SNOWY});
    }
}
