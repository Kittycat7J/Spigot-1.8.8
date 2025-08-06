package net.minecraft.server.v1_8_R3;

public abstract class TileEntityContainer extends TileEntity implements ITileEntityContainer, ITileInventory
{
    private ChestLock a = ChestLock.a;

    public void a(NBTTagCompound p_a_1_)
    {
        super.a(p_a_1_);
        this.a = ChestLock.b(p_a_1_);
    }

    public void b(NBTTagCompound p_b_1_)
    {
        super.b(p_b_1_);

        if (this.a != null)
        {
            this.a.a(p_b_1_);
        }
    }

    public boolean r_()
    {
        return this.a != null && !this.a.a();
    }

    public ChestLock i()
    {
        return this.a;
    }

    public void a(ChestLock p_a_1_)
    {
        this.a = p_a_1_;
    }

    public IChatBaseComponent getScoreboardDisplayName()
    {
        return (IChatBaseComponent)(this.hasCustomName() ? new ChatComponentText(this.getName()) : new ChatMessage(this.getName(), new Object[0]));
    }
}
