package net.minecraft.server.v1_8_R3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase.NBTNumber
{
    private double data;

    NBTTagDouble()
    {
    }

    public NBTTagDouble(double p_i909_1_)
    {
        this.data = p_i909_1_;
    }

    void write(DataOutput p_write_1_) throws IOException
    {
        p_write_1_.writeDouble(this.data);
    }

    void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException
    {
        p_load_3_.a(128L);
        this.data = p_load_1_.readDouble();
    }

    public byte getTypeId()
    {
        return (byte)6;
    }

    public String toString()
    {
        return "" + this.data + "d";
    }

    public NBTBase clone()
    {
        return new NBTTagDouble(this.data);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (super.equals(p_equals_1_))
        {
            NBTTagDouble nbttagdouble = (NBTTagDouble)p_equals_1_;
            return this.data == nbttagdouble.data;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        long i = Double.doubleToLongBits(this.data);
        return super.hashCode() ^ (int)(i ^ i >>> 32);
    }

    public long c()
    {
        return (long)Math.floor(this.data);
    }

    public int d()
    {
        return MathHelper.floor(this.data);
    }

    public short e()
    {
        return (short)(MathHelper.floor(this.data) & 65535);
    }

    public byte f()
    {
        return (byte)(MathHelper.floor(this.data) & 255);
    }

    public double g()
    {
        return this.data;
    }

    public float h()
    {
        return (float)this.data;
    }
}
