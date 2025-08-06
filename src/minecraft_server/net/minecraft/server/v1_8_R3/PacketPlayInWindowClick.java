package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayInWindowClick implements Packet<PacketListenerPlayIn>
{
    private int a;
    private int slot;
    private int button;
    private short d;
    private ItemStack item;
    private int shift;

    public void a(PacketListenerPlayIn p_a_1_)
    {
        p_a_1_.a(this);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.readByte();
        this.slot = p_a_1_.readShort();
        this.button = p_a_1_.readByte();
        this.d = p_a_1_.readShort();
        this.shift = p_a_1_.readByte();
        this.item = p_a_1_.i();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeByte(this.a);
        p_b_1_.writeShort(this.slot);
        p_b_1_.writeByte(this.button);
        p_b_1_.writeShort(this.d);
        p_b_1_.writeByte(this.shift);
        p_b_1_.a(this.item);
    }

    public int a()
    {
        return this.a;
    }

    public int b()
    {
        return this.slot;
    }

    public int c()
    {
        return this.button;
    }

    public short d()
    {
        return this.d;
    }

    public ItemStack e()
    {
        return this.item;
    }

    public int f()
    {
        return this.shift;
    }
}
