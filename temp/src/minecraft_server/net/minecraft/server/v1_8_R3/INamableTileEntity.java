package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;

public interface INamableTileEntity {
   String getName();

   boolean hasCustomName();

   IChatBaseComponent getScoreboardDisplayName();
}
