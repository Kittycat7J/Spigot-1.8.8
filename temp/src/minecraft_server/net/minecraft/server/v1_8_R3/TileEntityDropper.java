package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.TileEntityDispenser;

public class TileEntityDropper extends TileEntityDispenser {
   public String getName() {
      return this.hasCustomName()?this.a:"container.dropper";
   }

   public String getContainerName() {
      return "minecraft:dropper";
   }
}
