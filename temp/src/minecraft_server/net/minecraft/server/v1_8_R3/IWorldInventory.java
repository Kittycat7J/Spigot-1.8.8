package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;

public interface IWorldInventory extends IInventory {
   int[] getSlotsForFace(EnumDirection var1);

   boolean canPlaceItemThroughFace(int var1, ItemStack var2, EnumDirection var3);

   boolean canTakeItemThroughFace(int var1, ItemStack var2, EnumDirection var3);
}
