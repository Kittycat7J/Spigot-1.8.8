package net.minecraft.server.v1_8_R3;

class HandshakeListener$1
{
    static
    {
        try
        {
            a[EnumProtocol.LOGIN.ordinal()] = 1;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            a[EnumProtocol.STATUS.ordinal()] = 2;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
