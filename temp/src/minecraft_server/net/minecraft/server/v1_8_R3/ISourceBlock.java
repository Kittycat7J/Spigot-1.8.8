package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ILocationSource;
import net.minecraft.server.v1_8_R3.TileEntity;

public interface ISourceBlock extends ILocationSource {
   double getX();

   double getY();

   double getZ();

   BlockPosition getBlockPosition();

   int f();

   <T extends TileEntity> T getTileEntity();
}
