package net.minecraft.server.v1_8_R3;

import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.util.UUID;

public class PacketLoginInStart implements Packet<PacketLoginInListener>
{
    private GameProfile a;

    public PacketLoginInStart()
    {
    }

    public PacketLoginInStart(GameProfile p_i1050_1_)
    {
        this.a = p_i1050_1_;
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.a = new GameProfile((UUID)null, p_a_1_.c(16));
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.a(this.a.getName());
    }

    public void a(PacketLoginInListener p_a_1_)
    {
        p_a_1_.a(this);
    }

    public GameProfile a()
    {
        return this.a;
    }
}
