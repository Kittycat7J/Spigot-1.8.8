package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import java.util.List;
import java.util.zip.Inflater;

public class PacketDecompressor extends ByteToMessageDecoder
{
    private final Inflater a;
    private int b;

    public PacketDecompressor(int p_i925_1_)
    {
        this.b = p_i925_1_;
        this.a = new Inflater();
    }

    protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws Exception
    {
        if (p_decode_2_.readableBytes() != 0)
        {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(p_decode_2_);
            int i = packetdataserializer.e();

            if (i == 0)
            {
                p_decode_3_.add(packetdataserializer.readBytes(packetdataserializer.readableBytes()));
            }
            else
            {
                if (i < this.b)
                {
                    throw new DecoderException("Badly compressed packet - size of " + i + " is below server threshold of " + this.b);
                }

                if (i > 2097152)
                {
                    throw new DecoderException("Badly compressed packet - size of " + i + " is larger than protocol maximum of " + 2097152);
                }

                byte[] abyte = new byte[packetdataserializer.readableBytes()];
                packetdataserializer.readBytes(abyte);
                this.a.setInput(abyte);
                byte[] abyte1 = new byte[i];
                this.a.inflate(abyte1);
                p_decode_3_.add(Unpooled.wrappedBuffer(abyte1));
                this.a.reset();
            }
        }
    }

    public void a(int p_a_1_)
    {
        this.b = p_a_1_;
    }
}
