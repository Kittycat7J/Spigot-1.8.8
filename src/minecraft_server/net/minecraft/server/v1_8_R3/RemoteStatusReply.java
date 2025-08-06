package net.minecraft.server.v1_8_R3;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RemoteStatusReply
{
    private ByteArrayOutputStream buffer;
    private DataOutputStream stream;

    public RemoteStatusReply(int p_i1091_1_)
    {
        this.buffer = new ByteArrayOutputStream(p_i1091_1_);
        this.stream = new DataOutputStream(this.buffer);
    }

    public void a(byte[] p_a_1_) throws IOException
    {
        this.stream.write(p_a_1_, 0, p_a_1_.length);
    }

    public void a(String p_a_1_) throws IOException
    {
        this.stream.writeBytes(p_a_1_);
        this.stream.write(0);
    }

    public void a(int p_a_1_) throws IOException
    {
        this.stream.write(p_a_1_);
    }

    public void a(short p_a_1_) throws IOException
    {
        this.stream.writeShort(Short.reverseBytes(p_a_1_));
    }

    public byte[] a()
    {
        return this.buffer.toByteArray();
    }

    public void b()
    {
        this.buffer.reset();
    }
}
