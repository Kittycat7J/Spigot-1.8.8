package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IContainer;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public abstract class BlockContainer extends Block implements IContainer {
   protected BlockContainer(Material p_i592_1_) {
      this(p_i592_1_, p_i592_1_.r());
   }

   protected BlockContainer(Material p_i593_1_, MaterialMapColor p_i593_2_) {
      super(p_i593_1_, p_i593_2_);
      this.isTileEntity = true;
   }

   protected boolean a(World p_a_1_, BlockPosition p_a_2_, EnumDirection p_a_3_) {
      return p_a_1_.getType(p_a_2_.shift(p_a_3_)).getBlock().getMaterial() == Material.CACTUS;
   }

   protected boolean e(World p_e_1_, BlockPosition p_e_2_) {
      return this.a(p_e_1_, p_e_2_, EnumDirection.NORTH) || this.a(p_e_1_, p_e_2_, EnumDirection.SOUTH) || this.a(p_e_1_, p_e_2_, EnumDirection.WEST) || this.a(p_e_1_, p_e_2_, EnumDirection.EAST);
   }

   public int b() {
      return -1;
   }

   public void remove(World p_remove_1_, BlockPosition p_remove_2_, IBlockData p_remove_3_) {
      super.remove(p_remove_1_, p_remove_2_, p_remove_3_);
      p_remove_1_.t(p_remove_2_);
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, int p_a_4_, int p_a_5_) {
      super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);
      TileEntity tileentity = p_a_1_.getTileEntity(p_a_2_);
      return tileentity == null?false:tileentity.c(p_a_4_, p_a_5_);
   }
}
