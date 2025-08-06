package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockContainer;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateDirection;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.InventoryEnderChest;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityEnderChest;
import net.minecraft.server.v1_8_R3.World;

public class BlockEnderChest extends BlockContainer {
   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);

   protected BlockEnderChest() {
      super(Material.STONE);
      this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH));
      this.a(CreativeModeTab.c);
      this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public int b() {
      return 2;
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Item.getItemOf(Blocks.OBSIDIAN);
   }

   public int a(Random p_a_1_) {
      return 8;
   }

   protected boolean I() {
      return true;
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      return this.getBlockData().set(FACING, p_getPlacedState_8_.getDirection().opposite());
   }

   public void postPlace(World p_postPlace_1_, BlockPosition p_postPlace_2_, IBlockData p_postPlace_3_, EntityLiving p_postPlace_4_, ItemStack p_postPlace_5_) {
      p_postPlace_1_.setTypeAndData(p_postPlace_2_, p_postPlace_3_.set(FACING, p_postPlace_4_.getDirection().opposite()), 2);
   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      InventoryEnderChest inventoryenderchest = p_interact_4_.getEnderChest();
      TileEntity tileentity = p_interact_1_.getTileEntity(p_interact_2_);
      if(inventoryenderchest != null && tileentity instanceof TileEntityEnderChest) {
         if(p_interact_1_.getType(p_interact_2_.up()).getBlock().isOccluding()) {
            return true;
         } else if(p_interact_1_.isClientSide) {
            return true;
         } else {
            inventoryenderchest.a((TileEntityEnderChest)tileentity);
            p_interact_4_.openContainer(inventoryenderchest);
            p_interact_4_.b(StatisticList.V);
            return true;
         }
      } else {
         return true;
      }
   }

   public TileEntity a(World p_a_1_, int p_a_2_) {
      return new TileEntityEnderChest();
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      EnumDirection enumdirection = EnumDirection.fromType1(p_fromLegacyData_1_);
      if(enumdirection.k() == EnumDirection.EnumAxis.Y) {
         enumdirection = EnumDirection.NORTH;
      }

      return this.getBlockData().set(FACING, enumdirection);
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((EnumDirection)p_toLegacyData_1_.get(FACING)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{FACING});
   }
}
