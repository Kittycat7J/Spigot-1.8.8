package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayInClientCommand implements Packet<PacketListenerPlayIn>
{
    private PacketPlayInClientCommand.EnumClientCommand a;

    public PacketPlayInClientCommand()
    {
    }

    public PacketPlayInClientCommand(PacketPlayInClientCommand.EnumClientCommand p_i1040_1_)
    {
        this.a = p_i1040_1_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = (PacketPlayInClientCommand.EnumClientCommand)p_a_1_.a(PacketPlayInClientCommand.EnumClientCommand.class);
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a((Enum)this.a);
    }

    public void a(PacketListenerPlayIn p_a_1_)
    {
        p_a_1_.a(this);
    }

    public PacketPlayInClientCommand.EnumClientCommand a()
    {
        return this.a;
    }

    public static enum EnumClientCommand
    {
        PERFORM_RESPAWN,
        REQUEST_STATS,
        OPEN_INVENTORY_ACHIEVEMENT;
    }
}
