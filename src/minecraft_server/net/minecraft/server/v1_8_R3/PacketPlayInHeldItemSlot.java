package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayInHeldItemSlot implements Packet<PacketListenerPlayIn>
{
    private int itemInHandIndex;

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.itemInHandIndex = p_a_1_.readShort();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeShort(this.itemInHandIndex);
    }

    public void a(PacketListenerPlayIn p_a_1_)
    {
        p_a_1_.a(this);
    }

    public int a()
    {
        return this.itemInHandIndex;
    }
}
