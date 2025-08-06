package net.minecraft.server.v1_8_R3;

class WorldGenRegistration$1
{
    static
    {
        try
        {
            a[EnumDirection.NORTH.ordinal()] = 1;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            a[EnumDirection.SOUTH.ordinal()] = 2;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
