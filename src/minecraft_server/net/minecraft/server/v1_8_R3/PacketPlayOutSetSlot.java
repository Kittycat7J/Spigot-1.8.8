package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutSetSlot implements Packet<PacketListenerPlayOut>
{
    private int a;
    private int b;
    private ItemStack c;

    public PacketPlayOutSetSlot()
    {
    }

    public PacketPlayOutSetSlot(int p_i972_1_, int p_i972_2_, ItemStack p_i972_3_)
    {
        this.a = p_i972_1_;
        this.b = p_i972_2_;
        this.c = p_i972_3_ == null ? null : p_i972_3_.cloneItemStack();
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readByte();
        this.b = p_a_1_.readShort();
        this.c = p_a_1_.i();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeByte(this.a);
        p_b_1_.writeShort(this.b);
        p_b_1_.a(this.c);
    }
}
