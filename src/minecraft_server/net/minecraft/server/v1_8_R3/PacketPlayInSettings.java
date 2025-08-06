package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayInSettings implements Packet<PacketListenerPlayIn>
{
    private String a;
    private int b;
    private EntityHuman.EnumChatVisibility c;
    private boolean d;
    private int e;

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.c(7);
        this.b = p_a_1_.readByte();
        this.c = EntityHuman.EnumChatVisibility.a(p_a_1_.readByte());
        this.d = p_a_1_.readBoolean();
        this.e = p_a_1_.readUnsignedByte();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a);
        p_b_1_.writeByte(this.b);
        p_b_1_.writeByte(this.c.a());
        p_b_1_.writeBoolean(this.d);
        p_b_1_.writeByte(this.e);
    }

    public void a(PacketListenerPlayIn p_a_1_)
    {
        p_a_1_.a(this);
    }

    public String a()
    {
        return this.a;
    }

    public EntityHuman.EnumChatVisibility c()
    {
        return this.c;
    }

    public boolean d()
    {
        return this.d;
    }

    public int e()
    {
        return this.e;
    }
}
