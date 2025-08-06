package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public class MaterialDecoration extends Material {
   public MaterialDecoration(MaterialMapColor p_i802_1_) {
      super(p_i802_1_);
      this.p();
   }

   public boolean isBuildable() {
      return false;
   }

   public boolean blocksLight() {
      return false;
   }

   public boolean isSolid() {
      return false;
   }
}
