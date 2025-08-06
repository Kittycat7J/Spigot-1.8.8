package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.util.List;

public class PacketPlayOutWindowItems implements Packet<PacketListenerPlayOut>
{
    private int a;
    private ItemStack[] b;

    public PacketPlayOutWindowItems()
    {
    }

    public PacketPlayOutWindowItems(int p_i970_1_, List<ItemStack> p_i970_2_)
    {
        this.a = p_i970_1_;
        this.b = new ItemStack[p_i970_2_.size()];

        for (int i = 0; i < this.b.length; ++i)
        {
            ItemStack itemstack = (ItemStack)p_i970_2_.get(i);
            this.b[i] = itemstack == null ? null : itemstack.cloneItemStack();
        }
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readUnsignedByte();
        short short1 = p_a_1_.readShort();
        this.b = new ItemStack[short1];

        for (int i = 0; i < short1; ++i)
        {
            this.b[i] = p_a_1_.i();
        }
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeByte(this.a);
        p_b_1_.writeShort(this.b.length);

        for (ItemStack itemstack : this.b)
        {
            p_b_1_.a(itemstack);
        }
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
