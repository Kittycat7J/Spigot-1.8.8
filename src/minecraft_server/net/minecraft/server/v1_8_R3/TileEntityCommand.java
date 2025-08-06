package net.minecraft.server.v1_8_R3;

import org.bukkit.craftbukkit.v1_8_R3.command.CraftBlockCommandSender;

public class TileEntityCommand extends TileEntity
{
    private final CommandBlockListenerAbstract a = new CommandBlockListenerAbstract()
    {
        {
            this.sender = new CraftBlockCommandSender(this);
        }
        public BlockPosition getChunkCoordinates()
        {
            return TileEntityCommand.this.position;
        }
        public Vec3D d()
        {
            return new Vec3D((double)TileEntityCommand.this.position.getX() + 0.5D, (double)TileEntityCommand.this.position.getY() + 0.5D, (double)TileEntityCommand.this.position.getZ() + 0.5D);
        }
        public World getWorld()
        {
            return TileEntityCommand.this.getWorld();
        }
        public void setCommand(String p_setCommand_1_)
        {
            super.setCommand(p_setCommand_1_);
            TileEntityCommand.this.update();
        }
        public void h()
        {
            TileEntityCommand.this.getWorld().notify(TileEntityCommand.this.position);
        }
        public Entity f()
        {
            return null;
        }
    };

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);
        this.a.a(p_b_1_);
    }

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.a.b(p_a_1_);
    }

    public Packet getUpdatePacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.b(nbttagcompound);
        return new PacketPlayOutTileEntityData(this.position, 2, nbttagcompound);
    }

    public boolean F()
    {
        return true;
    }

    public CommandBlockListenerAbstract getCommandBlock()
    {
        return this.a;
    }

    public CommandObjectiveExecutor c()
    {
        return this.a.n();
    }
}
