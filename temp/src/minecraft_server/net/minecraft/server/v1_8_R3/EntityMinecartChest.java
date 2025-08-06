package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockChest;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerChest;
import net.minecraft.server.v1_8_R3.DamageSource;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R3.EntityMinecartContainer;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.World;

public class EntityMinecartChest extends EntityMinecartContainer {
   public EntityMinecartChest(World p_i1217_1_) {
      super(p_i1217_1_);
   }

   public EntityMinecartChest(World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_) {
      super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
   }

   public void a(DamageSource p_a_1_) {
      super.a(p_a_1_);
      if(this.world.getGameRules().getBoolean("doEntityDrops")) {
         this.a(Item.getItemOf(Blocks.CHEST), 1, 0.0F);
      }

   }

   public int getSize() {
      return 27;
   }

   public EntityMinecartAbstract.EnumMinecartType s() {
      return EntityMinecartAbstract.EnumMinecartType.CHEST;
   }

   public IBlockData u() {
      return Blocks.CHEST.getBlockData().set(BlockChest.FACING, EnumDirection.NORTH);
   }

   public int w() {
      return 8;
   }

   public String getContainerName() {
      return "minecraft:chest";
   }

   public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_) {
      return new ContainerChest(p_createContainer_1_, this, p_createContainer_2_);
   }
}
