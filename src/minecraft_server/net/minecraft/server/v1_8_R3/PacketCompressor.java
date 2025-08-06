package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Deflater;

public class PacketCompressor extends MessageToByteEncoder<ByteBuf>
{
    private final byte[] a = new byte[8192];
    private final Deflater b;
    private int c;

    public PacketCompressor(int p_i926_1_)
    {
        this.c = p_i926_1_;
        this.b = new Deflater();
    }

    protected void a(ChannelHandlerContext p_a_1_, ByteBuf p_a_2_, ByteBuf p_a_3_) throws Exception
    {
        int i = p_a_2_.readableBytes();
        PacketDataSerializer packetdataserializer = new PacketDataSerializer(p_a_3_);

        if (i < this.c)
        {
            packetdataserializer.b(0);
            packetdataserializer.writeBytes(p_a_2_);
        }
        else
        {
            byte[] abyte = new byte[i];
            p_a_2_.readBytes(abyte);
            packetdataserializer.b(abyte.length);
            this.b.setInput(abyte, 0, i);
            this.b.finish();

            while (!this.b.finished())
            {
                int j = this.b.deflate(this.a);
                packetdataserializer.writeBytes((byte[])this.a, 0, j);
            }

            this.b.reset();
        }
    }

    public void a(int p_a_1_)
    {
        this.c = p_a_1_;
    }
}
