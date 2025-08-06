package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.TileEntity;

public interface IBlockAccess {
   TileEntity getTileEntity(BlockPosition var1);

   IBlockData getType(BlockPosition var1);

   boolean isEmpty(BlockPosition var1);

   int getBlockPower(BlockPosition var1, EnumDirection var2);
}
