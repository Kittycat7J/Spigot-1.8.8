package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockDaylightDetector;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.TileEntity;

public class TileEntityLightDetector extends TileEntity implements IUpdatePlayerListBox {
   public void c() {
      if(this.world != null && !this.world.isClientSide && this.world.getTime() % 20L == 0L) {
         this.e = this.w();
         if(this.e instanceof BlockDaylightDetector) {
            ((BlockDaylightDetector)this.e).f(this.world, this.position);
         }
      }

   }
}
