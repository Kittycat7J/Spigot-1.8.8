package net.minecraft.server.v1_8_R3;

public class MapIcon
{
    private byte type;
    private byte x;
    private byte y;
    private byte rotation;

    public MapIcon(byte p_i836_1_, byte p_i836_2_, byte p_i836_3_, byte p_i836_4_)
    {
        this.type = p_i836_1_;
        this.x = p_i836_2_;
        this.y = p_i836_3_;
        this.rotation = p_i836_4_;
    }

    public MapIcon(MapIcon p_i837_1_)
    {
        this.type = p_i837_1_.type;
        this.x = p_i837_1_.x;
        this.y = p_i837_1_.y;
        this.rotation = p_i837_1_.rotation;
    }

    public byte getType()
    {
        return this.type;
    }

    public byte getX()
    {
        return this.x;
    }

    public byte getY()
    {
        return this.y;
    }

    public byte getRotation()
    {
        return this.rotation;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof MapIcon))
        {
            return false;
        }
        else
        {
            MapIcon mapicon = (MapIcon)p_equals_1_;
            return this.type != mapicon.type ? false : (this.rotation != mapicon.rotation ? false : (this.x != mapicon.x ? false : this.y == mapicon.y));
        }
    }

    public int hashCode()
    {
        int i = this.type;
        i = 31 * i + this.x;
        i = 31 * i + this.y;
        i = 31 * i + this.rotation;
        return i;
    }
}
