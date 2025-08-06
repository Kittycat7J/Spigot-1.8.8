package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class ItemMinecart extends Item
{
    private static final IDispenseBehavior a = new DispenseBehaviorItem()
    {
        private final DispenseBehaviorItem b = new DispenseBehaviorItem();
        public ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_)
        {
            EnumDirection enumdirection = BlockDispenser.b(p_b_1_.f());
            World world = p_b_1_.getWorld();
            double d0 = p_b_1_.getX() + (double)enumdirection.getAdjacentX() * 1.125D;
            double d1 = Math.floor(p_b_1_.getY()) + (double)enumdirection.getAdjacentY();
            double d2 = p_b_1_.getZ() + (double)enumdirection.getAdjacentZ() * 1.125D;
            BlockPosition blockposition = p_b_1_.getBlockPosition().shift(enumdirection);
            IBlockData iblockdata = world.getType(blockposition);
            BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = iblockdata.getBlock() instanceof BlockMinecartTrackAbstract ? (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(((BlockMinecartTrackAbstract)iblockdata.getBlock()).n()) : BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
            double d3;

            if (BlockMinecartTrackAbstract.d(iblockdata))
            {
                if (blockminecarttrackabstract$enumtrackposition.c())
                {
                    d3 = 0.6D;
                }
                else
                {
                    d3 = 0.1D;
                }
            }
            else
            {
                if (iblockdata.getBlock().getMaterial() != Material.AIR || !BlockMinecartTrackAbstract.d(world.getType(blockposition.down())))
                {
                    return this.b.a(p_b_1_, p_b_2_);
                }

                IBlockData iblockdata1 = world.getType(blockposition.down());
                BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition1 = iblockdata1.getBlock() instanceof BlockMinecartTrackAbstract ? (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata1.get(((BlockMinecartTrackAbstract)iblockdata1.getBlock()).n()) : BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;

                if (enumdirection != EnumDirection.DOWN && blockminecarttrackabstract$enumtrackposition1.c())
                {
                    d3 = -0.4D;
                }
                else
                {
                    d3 = -0.9D;
                }
            }

            ItemStack itemstack1 = p_b_2_.cloneAndSubtract(1);
            org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack1);
            BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(d0, d1 + d3, d2));

            if (!BlockDispenser.eventFired)
            {
                world.getServer().getPluginManager().callEvent(blockdispenseevent);
            }

            if (blockdispenseevent.isCancelled())
            {
                ++p_b_2_.count;
                return p_b_2_;
            }
            else
            {
                if (!blockdispenseevent.getItem().equals(craftitemstack))
                {
                    ++p_b_2_.count;
                    ItemStack itemstack = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                    IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack.getItem());

                    if (idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this)
                    {
                        idispensebehavior.a(p_b_1_, itemstack);
                        return p_b_2_;
                    }
                }

                itemstack1 = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(world, blockdispenseevent.getVelocity().getX(), blockdispenseevent.getVelocity().getY(), blockdispenseevent.getVelocity().getZ(), ((ItemMinecart)itemstack1.getItem()).b);

                if (p_b_2_.hasName())
                {
                    entityminecartabstract.setCustomName(p_b_2_.getName());
                }

                world.addEntity(entityminecartabstract);
                return p_b_2_;
            }
        }
        protected void a(ISourceBlock p_a_1_)
        {
            p_a_1_.getWorld().triggerEffect(1000, p_a_1_.getBlockPosition(), 0);
        }
    };
    private final EntityMinecartAbstract.EnumMinecartType b;

    public ItemMinecart(EntityMinecartAbstract.EnumMinecartType p_i255_1_)
    {
        this.maxStackSize = 1;
        this.b = p_i255_1_;
        this.a(CreativeModeTab.e);
        BlockDispenser.REGISTRY.a(this, a);
    }

    public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_)
    {
        IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);

        if (BlockMinecartTrackAbstract.d(iblockdata))
        {
            if (!p_interactWith_3_.isClientSide)
            {
                BlockMinecartTrackAbstract.EnumTrackPosition blockminecarttrackabstract$enumtrackposition = iblockdata.getBlock() instanceof BlockMinecartTrackAbstract ? (BlockMinecartTrackAbstract.EnumTrackPosition)iblockdata.get(((BlockMinecartTrackAbstract)iblockdata.getBlock()).n()) : BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH;
                double d0 = 0.0D;

                if (blockminecarttrackabstract$enumtrackposition.c())
                {
                    d0 = 0.5D;
                }

                PlayerInteractEvent playerinteractevent = CraftEventFactory.callPlayerInteractEvent(p_interactWith_2_, Action.RIGHT_CLICK_BLOCK, p_interactWith_4_, p_interactWith_5_, p_interactWith_1_);

                if (playerinteractevent.isCancelled())
                {
                    return false;
                }

                EntityMinecartAbstract entityminecartabstract = EntityMinecartAbstract.a(p_interactWith_3_, (double)p_interactWith_4_.getX() + 0.5D, (double)p_interactWith_4_.getY() + 0.0625D + d0, (double)p_interactWith_4_.getZ() + 0.5D, this.b);

                if (p_interactWith_1_.hasName())
                {
                    entityminecartabstract.setCustomName(p_interactWith_1_.getName());
                }

                p_interactWith_3_.addEntity(entityminecartabstract);
            }

            --p_interactWith_1_.count;
            return true;
        }
        else
        {
            return false;
        }
    }
}
