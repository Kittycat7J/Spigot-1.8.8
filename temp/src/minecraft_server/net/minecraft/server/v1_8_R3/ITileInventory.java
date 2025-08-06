package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.ChestLock;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ITileEntityContainer;

public interface ITileInventory extends IInventory, ITileEntityContainer {
   boolean r_();

   void a(ChestLock var1);

   ChestLock i();
}
