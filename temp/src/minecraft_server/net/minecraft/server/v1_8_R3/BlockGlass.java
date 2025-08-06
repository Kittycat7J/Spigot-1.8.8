package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockHalfTransparent;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.Material;

public class BlockGlass extends BlockHalfTransparent {
   public BlockGlass(Material p_i620_1_, boolean p_i620_2_) {
      super(p_i620_1_, p_i620_2_);
      this.a(CreativeModeTab.b);
   }

   public int a(Random p_a_1_) {
      return 0;
   }

   public boolean d() {
      return false;
   }

   protected boolean I() {
      return true;
   }
}
