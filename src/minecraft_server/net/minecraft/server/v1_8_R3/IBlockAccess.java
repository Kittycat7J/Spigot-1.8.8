package net.minecraft.server.v1_8_R3;

public interface IBlockAccess
{
    TileEntity getTileEntity(BlockPosition var1);

    IBlockData getType(BlockPosition var1);

    boolean isEmpty(BlockPosition var1);

    int getBlockPower(BlockPosition var1, EnumDirection var2);
}
