package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayOutHeldItemSlot implements Packet<PacketListenerPlayOut>
{
    private int a;

    public PacketPlayOutHeldItemSlot()
    {
    }

    public PacketPlayOutHeldItemSlot(int p_i1006_1_)
    {
        this.a = p_i1006_1_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readByte();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeByte(this.a);
    }

    public void a(PacketListenerPlayOut p_a_1_)
    {
        p_a_1_.a(this);
    }
}
