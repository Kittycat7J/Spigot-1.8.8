package net.minecraft.server.v1_8_R3;

public class BlockWorkbench extends Block
{
    protected BlockWorkbench()
    {
        super(Material.WOOD);
        this.a(CreativeModeTab.c);
    }

    public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_)
    {
        if (p_interact_1_.isClientSide)
        {
            return true;
        }
        else
        {
            p_interact_4_.openTileEntity(new BlockWorkbench.TileEntityContainerWorkbench(p_interact_1_, p_interact_2_));
            p_interact_4_.b(StatisticList.Z);
            return true;
        }
    }

    public static class TileEntityContainerWorkbench implements ITileEntityContainer
    {
        private final World a;
        private final BlockPosition b;

        public TileEntityContainerWorkbench(World p_i602_1_, BlockPosition p_i602_2_)
        {
            this.a = p_i602_1_;
            this.b = p_i602_2_;
        }

        public String getName()
        {
            return null;
        }

        public boolean hasCustomName()
        {
            return false;
        }

        public IChatBaseComponent getScoreboardDisplayName()
        {
            return new ChatMessage(Blocks.CRAFTING_TABLE.a() + ".name", new Object[0]);
        }

        public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_)
        {
            return new ContainerWorkbench(p_createContainer_1_, this.a, this.b);
        }

        public String getContainerName()
        {
            return "minecraft:crafting_table";
        }
    }
}
