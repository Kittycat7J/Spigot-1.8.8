package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockDispenser;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.DispenseBehaviorItem;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IDispenseBehavior;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.InventoryLargeChest;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.SourceBlock;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityDispenser;
import net.minecraft.server.v1_8_R3.TileEntityDropper;
import net.minecraft.server.v1_8_R3.TileEntityHopper;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

public class BlockDropper extends BlockDispenser {
   private final IDispenseBehavior P = new DispenseBehaviorItem();

   protected IDispenseBehavior a(ItemStack p_a_1_) {
      return this.P;
   }

   public TileEntity a(World p_a_1_, int p_a_2_) {
      return new TileEntityDropper();
   }

   public void dispense(World p_dispense_1_, BlockPosition p_dispense_2_) {
      SourceBlock sourceblock = new SourceBlock(p_dispense_1_, p_dispense_2_);
      TileEntityDispenser tileentitydispenser = (TileEntityDispenser)sourceblock.getTileEntity();
      if(tileentitydispenser != null) {
         int i = tileentitydispenser.m();
         if(i < 0) {
            p_dispense_1_.triggerEffect(1001, p_dispense_2_, 0);
         } else {
            ItemStack itemstack = tileentitydispenser.getItem(i);
            if(itemstack != null) {
               EnumDirection enumdirection = (EnumDirection)p_dispense_1_.getType(p_dispense_2_).get(FACING);
               BlockPosition blockposition = p_dispense_2_.shift(enumdirection);
               IInventory iinventory = TileEntityHopper.b(p_dispense_1_, (double)blockposition.getX(), (double)blockposition.getY(), (double)blockposition.getZ());
               ItemStack itemstack1;
               if(iinventory == null) {
                  itemstack1 = this.P.a(sourceblock, itemstack);
                  if(itemstack1 != null && itemstack1.count <= 0) {
                     itemstack1 = null;
                  }
               } else {
                  CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack.cloneItemStack().cloneAndSubtract(1));
                  Inventory inventory;
                  if(iinventory instanceof InventoryLargeChest) {
                     inventory = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
                  } else {
                     inventory = iinventory.getOwner().getInventory();
                  }

                  InventoryMoveItemEvent inventorymoveitemevent = new InventoryMoveItemEvent(tileentitydispenser.getOwner().getInventory(), craftitemstack.clone(), inventory, true);
                  p_dispense_1_.getServer().getPluginManager().callEvent(inventorymoveitemevent);
                  if(inventorymoveitemevent.isCancelled()) {
                     return;
                  }

                  itemstack1 = TileEntityHopper.addItem(iinventory, CraftItemStack.asNMSCopy(inventorymoveitemevent.getItem()), enumdirection.opposite());
                  if(inventorymoveitemevent.getItem().equals(craftitemstack) && itemstack1 == null) {
                     itemstack1 = itemstack.cloneItemStack();
                     if(--itemstack1.count <= 0) {
                        itemstack1 = null;
                     }
                  } else {
                     itemstack1 = itemstack.cloneItemStack();
                  }
               }

               tileentitydispenser.setItem(i, itemstack1);
            }
         }
      }

   }
}
