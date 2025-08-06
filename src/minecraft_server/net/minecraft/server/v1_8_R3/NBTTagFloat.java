package net.minecraft.server.v1_8_R3;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase.NBTNumber
{
    private float data;

    NBTTagFloat()
    {
    }

    public NBTTagFloat(float p_i910_1_)
    {
        this.data = p_i910_1_;
    }

    void write(DataOutput p_write_1_) throws IOException
    {
        p_write_1_.writeFloat(this.data);
    }

    void load(DataInput p_load_1_, int p_load_2_, NBTReadLimiter p_load_3_) throws IOException
    {
        p_load_3_.a(96L);
        this.data = p_load_1_.readFloat();
    }

    public byte getTypeId()
    {
        return (byte)5;
    }

    public String toString()
    {
        return "" + this.data + "f";
    }

    public NBTBase clone()
    {
        return new NBTTagFloat(this.data);
    }

    public boolean equals(Object p_equals_1_)
    {
        if (super.equals(p_equals_1_))
        {
            NBTTagFloat nbttagfloat = (NBTTagFloat)p_equals_1_;
            return this.data == nbttagfloat.data;
        }
        else
        {
            return false;
        }
    }

    public int hashCode()
    {
        return super.hashCode() ^ Float.floatToIntBits(this.data);
    }

    public long c()
    {
        return (long)this.data;
    }

    public int d()
    {
        return MathHelper.d(this.data);
    }

    public short e()
    {
        return (short)(MathHelper.d(this.data) & 65535);
    }

    public byte f()
    {
        return (byte)(MathHelper.d(this.data) & 255);
    }

    public double g()
    {
        return (double)this.data;
    }

    public float h()
    {
        return this.data;
    }
}
