package net.minecraft.server.v1_8_R3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase.NBTNumber
{
    private short data;

    public NBTTagShort()
    {
    }

    public NBTTagShort(short p_i915_1_)
    {
        this.data = p_i915_1_;
    }

    void write(DataOutput p_write_1_) throws IOException
    {
        p_write_1_.writeShort(this.data);
    }

    void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException
    {
        p_load_3_.a(80L);
        this.data = p_load_1_.readShort();
    }

    public byte getTypeId()
    {
        return (byte)2;
    }

    public String toString()
    {
        return "" + this.data + "s";
    }

    public NBTBase clone()
    {
        return new NBTTagShort(this.data);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (super.equals(p_equals_1_))
        {
            NBTTagShort nbttagshort = (NBTTagShort)p_equals_1_;
            return this.data == nbttagshort.data;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ this.data;
    }

    public long c()
    {
        return (long)this.data;
    }

    public int d()
    {
        return this.data;
    }

    public short e()
    {
        return this.data;
    }

    public byte f()
    {
        return (byte)(this.data & 255);
    }

    public double g()
    {
        return (double)this.data;
    }

    public float h()
    {
        return (float)this.data;
    }
}
