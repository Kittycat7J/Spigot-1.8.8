package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayInBlockDig implements Packet<PacketListenerPlayIn>
{
    private BlockPosition a;
    private EnumDirection b;
    private PacketPlayInBlockDig.EnumPlayerDigType c;

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.c = (PacketPlayInBlockDig.EnumPlayerDigType)p_a_1_.a(PacketPlayInBlockDig.EnumPlayerDigType.class);
        this.a = p_a_1_.c();
        this.b = EnumDirection.fromType1(p_a_1_.readUnsignedByte());
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a((Enum)this.c);
        p_b_1_.a(this.a);
        p_b_1_.writeByte(this.b.a());
    }

    public void a(PacketListenerPlayIn p_a_1_)
    {
        p_a_1_.a(this);
    }

    public BlockPosition a()
    {
        return this.a;
    }

    public EnumDirection b()
    {
        return this.b;
    }

    public PacketPlayInBlockDig.EnumPlayerDigType c()
    {
        return this.c;
    }

    public static enum EnumPlayerDigType
    {
        START_DESTROY_BLOCK,
        ABORT_DESTROY_BLOCK,
        STOP_DESTROY_BLOCK,
        DROP_ALL_ITEMS,
        DROP_ITEM,
        RELEASE_USE_ITEM;
    }
}
