package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class BlockHalfTransparent extends Block {
   private boolean a;

   protected BlockHalfTransparent(Material p_i624_1_, boolean p_i624_2_) {
      this(p_i624_1_, p_i624_2_, p_i624_1_.r());
   }

   protected BlockHalfTransparent(Material p_i625_1_, boolean p_i625_2_, MaterialMapColor p_i625_3_) {
      super(p_i625_1_, p_i625_3_);
      this.a = p_i625_2_;
   }

   public boolean c() {
      return false;
   }
}
