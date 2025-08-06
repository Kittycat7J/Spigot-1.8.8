package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketHandshakingInSetProtocol implements Packet<PacketHandshakingInListener>
{
    private int a;
    public String hostname;
    public int port;
    private EnumProtocol d;

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.e();
        this.hostname = p_a_1_.c(32767);
        this.port = p_a_1_.readUnsignedShort();
        this.d = EnumProtocol.a(p_a_1_.e());
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.b(this.a);
        p_b_1_.a(this.hostname);
        p_b_1_.writeShort(this.port);
        p_b_1_.b(this.d.a());
    }

    public void a(PacketHandshakingInListener p_a_1_)
    {
        p_a_1_.a(this);
    }

    public EnumProtocol a()
    {
        return this.d;
    }

    public int b()
    {
        return this.a;
    }
}
