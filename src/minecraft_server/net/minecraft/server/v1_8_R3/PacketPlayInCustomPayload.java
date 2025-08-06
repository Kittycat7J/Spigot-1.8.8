package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import java.io.IOException;

public class PacketPlayInCustomPayload implements Packet<PacketListenerPlayIn>
{
    private String a;
    private PacketDataSerializer b;

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.c(20);
        int i = p_a_1_.readableBytes();

        if (i >= 0 && i <= 32767)
        {
            this.b = new PacketDataSerializer(p_a_1_.readBytes(i));
        }
        else
        {
            throw new IOException("Payload may not be larger than 32767 bytes");
        }
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a);
        p_b_1_.writeBytes((ByteBuf)this.b);
    }

    public void a(PacketListenerPlayIn p_a_1_)
    {
        p_a_1_.a(this);
    }

    public String a()
    {
        return this.a;
    }

    public PacketDataSerializer b()
    {
        return this.b;
    }
}
