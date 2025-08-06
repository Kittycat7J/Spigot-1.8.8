package net.minecraft.server.v1_8_R3;

class TileEntityChest$1
{
    static
    {
        try
        {
            a[EnumDirection.NORTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError var4)
        {
            ;
        }

        try
        {
            a[EnumDirection.SOUTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError var3)
        {
            ;
        }

        try
        {
            a[EnumDirection.EAST.ordinal()] = 3;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            a[EnumDirection.WEST.ordinal()] = 4;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
