package net.minecraft.server.v1_8_R3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase.NBTNumber
{
    private byte data;

    NBTTagByte()
    {
    }

    public NBTTagByte(byte p_i906_1_)
    {
        this.data = p_i906_1_;
    }

    void write(DataOutput p_write_1_) throws IOException
    {
        p_write_1_.writeByte(this.data);
    }

    void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException
    {
        p_load_3_.a(72L);
        this.data = p_load_1_.readByte();
    }

    public byte getTypeId()
    {
        return (byte)1;
    }

    public String toString()
    {
        return "" + this.data + "b";
    }

    public NBTBase clone()
    {
        return new NBTTagByte(this.data);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (super.equals(p_equals_1_))
        {
            NBTTagByte nbttagbyte = (NBTTagByte)p_equals_1_;
            return this.data == nbttagbyte.data;
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
        return (short)this.data;
    }

    public byte f()
    {
        return this.data;
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
