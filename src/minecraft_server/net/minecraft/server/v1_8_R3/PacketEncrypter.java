package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import javax.crypto.Cipher;

public class PacketEncrypter extends MessageToByteEncoder<ByteBuf>
{
    private final PacketEncryptionHandler a;

    public PacketEncrypter(Cipher p_i924_1_)
    {
        this.a = new PacketEncryptionHandler(p_i924_1_);
    }

    protected void a(ChannelHandlerContext p_a_1_, ByteBuf p_a_2_, ByteBuf p_a_3_) throws Exception
    {
        this.a.a(p_a_2_, p_a_3_);
    }
}
