package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class BlockHardenedClay extends Block {
   public BlockHardenedClay() {
      super(Material.STONE);
      this.a(CreativeModeTab.b);
   }

   public MaterialMapColor g(IBlockData p_g_1_) {
      return MaterialMapColor.q;
   }
}
