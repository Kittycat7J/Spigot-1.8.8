package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class PacketEncoder extends MessageToByteEncoder<Packet>
{
    private static final Logger a = LogManager.getLogger();
    private static final Marker b = MarkerManager.getMarker("PACKET_SENT", NetworkManager.b);
    private final EnumProtocolDirection c;

    public PacketEncoder(EnumProtocolDirection p_i934_1_)
    {
        this.c = p_i934_1_;
    }

    protected void a(ChannelHandlerContext p_a_1_, Packet p_a_2_, ByteBuf p_a_3_) throws Exception
    {
        Integer integer = ((EnumProtocol)p_a_1_.channel().attr(NetworkManager.c).get()).a(this.c, p_a_2_);

        if (a.isDebugEnabled())
        {
            a.debug(b, "OUT: [{}:{}] {}", new Object[] {p_a_1_.channel().attr(NetworkManager.c).get(), integer, p_a_2_.getClass().getName()});
        }

        if (integer == null)
        {
            throw new IOException("Can\'t serialize unregistered packet");
        }
        else
        {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(p_a_3_);
            packetdataserializer.b(integer.intValue());

            try
            {
                if (p_a_2_ instanceof PacketPlayOutNamedEntitySpawn)
                {
                    p_a_2_ = p_a_2_;
                }

                p_a_2_.b(packetdataserializer);
            }
            catch (Throwable throwable)
            {
                a.error((Object)throwable);
            }
        }
    }
}
