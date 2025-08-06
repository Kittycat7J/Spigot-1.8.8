package net.minecraft.server.v1_8_R3;

import java.io.IOException;
import java.security.PublicKey;

public class PacketLoginOutEncryptionBegin implements Packet<PacketLoginOutListener>
{
    private String a;
    private PublicKey b;
    private byte[] c;

    public PacketLoginOutEncryptionBegin()
    {
    }

    public PacketLoginOutEncryptionBegin(String p_i1047_1_, PublicKey p_i1047_2_, byte[] p_i1047_3_)
    {
        this.a = p_i1047_1_;
        this.b = p_i1047_2_;
        this.c = p_i1047_3_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = p_a_1_.c(20);
        this.b = MinecraftEncryption.a(p_a_1_.a());
        this.c = p_a_1_.a();
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a);
        p_b_1_.a(this.b.getEncoded());
        p_b_1_.a(this.c);
    }

    public void a(PacketLoginOutListener p_a_1_)
    {
        p_a_1_.a(this);
    }
}
