package net.minecraft.server.v1_8_R3;

import java.io.IOException;

public class PacketPlayInFlying implements Packet<PacketListenerPlayIn>
{
    protected double x;
    protected double y;
    protected double z;
    protected float yaw;
    protected float pitch;
    protected boolean f;
    protected boolean hasPos;
    protected boolean hasLook;

    public void a(PacketListenerPlayIn p_a_1_)
    {
        p_a_1_.a(this);
    }

    public void a(PacketDataSerializer p_a_1_) throws IOException
    {
        this.f = p_a_1_.readUnsignedByte() != 0;
    }

    public void b(PacketDataSerializer p_b_1_) throws IOException
    {
        p_b_1_.writeByte(this.f ? 1 : 0);
    }

    public double a()
    {
        return this.x;
    }

    public double b()
    {
        return this.y;
    }

    public double c()
    {
        return this.z;
    }

    public float d()
    {
        return this.yaw;
    }

    public float e()
    {
        return this.pitch;
    }

    public boolean f()
    {
        return this.f;
    }

    public boolean g()
    {
        return this.hasPos;
    }

    public boolean h()
    {
        return this.hasLook;
    }

    public void a(boolean p_a_1_)
    {
        this.hasPos = p_a_1_;
    }

    public static class PacketPlayInLook extends PacketPlayInFlying
    {
        public PacketPlayInLook()
        {
            this.hasLook = true;
        }

        public void a(PacketDataSerializer p_a_1_) throws IOException
        {
            this.yaw = p_a_1_.readFloat();
            this.pitch = p_a_1_.readFloat();
            super.a(p_a_1_);
        }

        public void b(PacketDataSerializer p_b_1_) throws IOException
        {
            p_b_1_.writeFloat(this.yaw);
            p_b_1_.writeFloat(this.pitch);
            super.b(p_b_1_);
        }
    }

    public static class PacketPlayInPosition extends PacketPlayInFlying
    {
        public PacketPlayInPosition()
        {
            this.hasPos = true;
        }

        public void a(PacketDataSerializer p_a_1_) throws IOException
        {
            this.x = p_a_1_.readDouble();
            this.y = p_a_1_.readDouble();
            this.z = p_a_1_.readDouble();
            super.a(p_a_1_);
        }

        public void b(PacketDataSerializer p_b_1_) throws IOException
        {
            p_b_1_.writeDouble(this.x);
            p_b_1_.writeDouble(this.y);
            p_b_1_.writeDouble(this.z);
            super.b(p_b_1_);
        }
    }

    public static class PacketPlayInPositionLook extends PacketPlayInFlying
    {
        public PacketPlayInPositionLook()
        {
            this.hasPos = true;
            this.hasLook = true;
        }

        public void a(PacketDataSerializer p_a_1_) throws IOException
        {
            this.x = p_a_1_.readDouble();
            this.y = p_a_1_.readDouble();
            this.z = p_a_1_.readDouble();
            this.yaw = p_a_1_.readFloat();
            this.pitch = p_a_1_.readFloat();
            super.a(p_a_1_);
        }

        public void b(PacketDataSerializer p_b_1_) throws IOException
        {
            p_b_1_.writeDouble(this.x);
            p_b_1_.writeDouble(this.y);
            p_b_1_.writeDouble(this.z);
            p_b_1_.writeFloat(this.yaw);
            p_b_1_.writeFloat(this.pitch);
            super.b(p_b_1_);
        }
    }
}
