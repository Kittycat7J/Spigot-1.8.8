package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class PacketDecoder extends ByteToMessageDecoder
{
    private static final Logger a = LogManager.getLogger();
    private static final Marker b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.b);
    private final EnumProtocolDirection c;

    public PacketDecoder(EnumProtocolDirection p_i933_1_)
    {
        this.c = p_i933_1_;
    }

    protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws Exception
    {
        if (p_decode_2_.readableBytes() != 0)
        {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(p_decode_2_);
            int i = packetdataserializer.e();
            Packet packet = ((EnumProtocol)p_decode_1_.channel().attr(NetworkManager.c).get()).a(this.c, i);

            if (packet == null)
            {
                throw new IOException("Bad packet id " + i);
            }
            else
            {
                packet.a(packetdataserializer);

                if (packetdataserializer.readableBytes() > 0)
                {
                    throw new IOException("Packet " + ((EnumProtocol)p_decode_1_.channel().attr(NetworkManager.c).get()).a() + "/" + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetdataserializer.readableBytes() + " bytes extra whilst reading packet " + i);
                }
                else
                {
                    p_decode_3_.add(packet);

                    if (a.isDebugEnabled())
                    {
                        a.debug(b, " IN: [{}:{}] {}", new Object[] {p_decode_1_.channel().attr(NetworkManager.c).get(), Integer.valueOf(i), packet.getClass().getName()});
                    }
                }
            }
        }
    }
}
