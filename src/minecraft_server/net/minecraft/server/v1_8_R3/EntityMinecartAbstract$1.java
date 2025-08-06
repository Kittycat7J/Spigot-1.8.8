package net.minecraft.server.v1_8_R3;

class EntityMinecartAbstract$1
{
    static
    {
        try
        {
            b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_EAST.ordinal()] = 1;
        }
        catch (NoSuchFieldError var10)
        {
            ;
        }

        try
        {
            b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_WEST.ordinal()] = 2;
        }
        catch (NoSuchFieldError var9)
        {
            ;
        }

        try
        {
            b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_NORTH.ordinal()] = 3;
        }
        catch (NoSuchFieldError var8)
        {
            ;
        }

        try
        {
            b[BlockMinecartTrackAbstract.EnumTrackPosition.ASCENDING_SOUTH.ordinal()] = 4;
        }
        catch (NoSuchFieldError var7)
        {
            ;
        }

        a = new int[EntityMinecartAbstract.EnumMinecartType.values().length];

        try
        {
            a[EntityMinecartAbstract.EnumMinecartType.CHEST.ordinal()] = 1;
        }
        catch (NoSuchFieldError var6)
        {
            ;
        }

        try
        {
            a[EntityMinecartAbstract.EnumMinecartType.FURNACE.ordinal()] = 2;
        }
        catch (NoSuchFieldError var5)
        {
            ;
        }

        try
        {
            a[EntityMinecartAbstract.EnumMinecartType.TNT.ordinal()] = 3;
        }
        catch (NoSuchFieldError var4)
        {
            ;
        }

        try
        {
            a[EntityMinecartAbstract.EnumMinecartType.SPAWNER.ordinal()] = 4;
        }
        catch (NoSuchFieldError var3)
        {
            ;
        }

        try
        {
            a[EntityMinecartAbstract.EnumMinecartType.HOPPER.ordinal()] = 5;
        }
        catch (NoSuchFieldError var2)
        {
            ;
        }

        try
        {
            a[EntityMinecartAbstract.EnumMinecartType.COMMAND_BLOCK.ordinal()] = 6;
        }
        catch (NoSuchFieldError var1)
        {
            ;
        }
    }
}
