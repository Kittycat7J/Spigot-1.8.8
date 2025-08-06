package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMinecartCommand;

public class EntityMinecartCommandBlock extends EntityMinecartAbstract
{
    private final CommandBlockListenerAbstract a = new CommandBlockListenerAbstract()
    {
        {
            this.sender = (CraftMinecartCommand)EntityMinecartCommandBlock.this.getBukkitEntity();
        }
        public void h()
        {
            EntityMinecartCommandBlock.this.getDataWatcher().watch(23, this.getCommand());
            EntityMinecartCommandBlock.this.getDataWatcher().watch(24, IChatBaseComponent.ChatSerializer.a(this.k()));
        }
        public BlockPosition getChunkCoordinates()
        {
            return new BlockPosition(EntityMinecartCommandBlock.this.locX, EntityMinecartCommandBlock.this.locY + 0.5D, EntityMinecartCommandBlock.this.locZ);
        }
        public Vec3D d()
        {
            return new Vec3D(EntityMinecartCommandBlock.this.locX, EntityMinecartCommandBlock.this.locY, EntityMinecartCommandBlock.this.locZ);
        }
        public World getWorld()
        {
            return EntityMinecartCommandBlock.this.world;
        }
        public Entity f()
        {
            return EntityMinecartCommandBlock.this;
        }
    };
    private int b = 0;

    public EntityMinecartCommandBlock(World p_i102_1_)
    {
        super(p_i102_1_);
    }

    public EntityMinecartCommandBlock(World p_i103_1_, double p_i103_2_, double p_i103_4_, double p_i103_6_)
    {
        super(p_i103_1_, p_i103_2_, p_i103_4_, p_i103_6_);
    }

    protected void h()
    {
        super.h();
        this.getDataWatcher().a(23, "");
        this.getDataWatcher().a(24, "");
    }

    protected void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.a.b(p_a_1_);
        this.getDataWatcher().watch(23, this.getCommandBlock().getCommand());
        this.getDataWatcher().watch(24, IChatBaseComponent.ChatSerializer.a(this.getCommandBlock().k()));
    }

    protected void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        this.a.a(p_b_1_);
    }

    public EntityMinecartAbstract.EnumMinecartType s()
    {
        return EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK;
    }

    public IBlockData u()
    {
        return Blocks.COMMAND_BLOCK.getBlockData();
    }

    public CommandBlockListenerAbstract getCommandBlock()
    {
        return this.a;
    }

    public void a(int p_a_1_, int p_a_2_, int p_a_3_, boolean p_a_4_)
    {
        if (p_a_4_ && this.ticksLived - this.b >= 4)
        {
            this.getCommandBlock().a(this.world);
            this.b = this.ticksLived;
        }
    }

    public boolean e(EntityHuman p_e_1_)
    {
        this.a.a(p_e_1_);
        return false;
    }

    public void i(int p_i_1_)
    {
        super.i(p_i_1_);

        if (p_i_1_ == 24)
        {
            try
            {
                this.a.b(IChatBaseComponent.ChatSerializer.a(this.getDataWatcher().getString(24)));
            }
            catch (Throwable var2)
            {
                ;
            }
        }
        else if (p_i_1_ == 23)
        {
            this.a.setCommand(this.getDataWatcher().getString(23));
        }
    }
}
