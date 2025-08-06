package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.IPosition;
import net.minecraft.server.v1_8_R3.World;

public interface ISource extends IPosition {
   World getWorld();
}
