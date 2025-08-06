package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.INamableTileEntity;
import net.minecraft.server.v1_8_R3.PlayerInventory;

public interface ITileEntityContainer extends INamableTileEntity {
   Container createContainer(PlayerInventory var1, EntityHuman var2);

   String getContainerName();
}
