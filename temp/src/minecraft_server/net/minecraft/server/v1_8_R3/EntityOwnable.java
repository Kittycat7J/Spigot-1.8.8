package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Entity;

public interface EntityOwnable {
   String getOwnerUUID();

   Entity getOwner();
}
