package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketPrepender extends MessageToByteEncoder<ByteBuf>
{
    protected void a(ChannelHandlerContext p_a_1_, ByteBuf p_a_2_, ByteBuf p_a_3_) throws Exception
    {
        int i = p_a_2_.readableBytes();
        int j = PacketDataSerializer.a(i);

        if (j > 3)
        {
            throw new IllegalArgumentException("unable to fit " + i + " into " + 3);
        }
        else
        {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(p_a_3_);
            packetdataserializer.ensureWritable(j + i);
            packetdataserializer.b(i);
            packetdataserializer.writeBytes(p_a_2_, p_a_2_.readerIndex(), i);
        }
    }
}
