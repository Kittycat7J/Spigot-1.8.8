package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.World;

public interface IHopper extends IInventory {
   World getWorld();

   double A();

   double B();

   double C();
}
