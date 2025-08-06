package net.minecraft.server.v1_8_R3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

public class PacketEncryptionHandler
{
    private final Cipher a;
    private byte[] b = new byte[0];
    private byte[] c = new byte[0];

    protected PacketEncryptionHandler(Cipher p_i922_1_)
    {
        this.a = p_i922_1_;
    }

    private byte[] a(ByteBuf p_a_1_)
    {
        int i = p_a_1_.readableBytes();

        if (this.b.length < i)
        {
            this.b = new byte[i];
        }

        p_a_1_.readBytes((byte[])this.b, 0, i);
        return this.b;
    }

    protected ByteBuf a(ChannelHandlerContext p_a_1_, ByteBuf p_a_2_) throws ShortBufferException
    {
        int i = p_a_2_.readableBytes();
        byte[] abyte = this.a(p_a_2_);
        ByteBuf bytebuf = p_a_1_.alloc().heapBuffer(this.a.getOutputSize(i));
        bytebuf.writerIndex(this.a.update(abyte, 0, i, bytebuf.array(), bytebuf.arrayOffset()));
        return bytebuf;
    }

    protected void a(ByteBuf p_a_1_, ByteBuf p_a_2_) throws ShortBufferException
    {
        int i = p_a_1_.readableBytes();
        byte[] abyte = this.a(p_a_1_);
        int j = this.a.getOutputSize(i);

        if (this.c.length < j)
        {
            this.c = new byte[j];
        }

        p_a_2_.writeBytes((byte[])this.c, 0, this.a.update(abyte, 0, i, this.c));
    }
}
