package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockStateDirection;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;

public abstract class BlockDirectional extends Block {
   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);

   protected BlockDirectional(Material p_i603_1_) {
      super(p_i603_1_);
   }

   protected BlockDirectional(Material p_i604_1_, MaterialMapColor p_i604_2_) {
      super(p_i604_1_, p_i604_2_);
   }
}
