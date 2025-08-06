package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import java.util.List;
import javax.crypto.Cipher;

public class PacketDecrypter extends MessageToMessageDecoder<ByteBuf>
{
    private final PacketEncryptionHandler a;

    public PacketDecrypter(Cipher p_i923_1_)
    {
        this.a = new PacketEncryptionHandler(p_i923_1_);
    }

    protected void a(ChannelHandlerContext p_a_1_, ByteBuf p_a_2_, List<Object> p_a_3_) throws Exception
    {
        p_a_3_.add(this.a.a(p_a_1_, p_a_2_));
    }
}
