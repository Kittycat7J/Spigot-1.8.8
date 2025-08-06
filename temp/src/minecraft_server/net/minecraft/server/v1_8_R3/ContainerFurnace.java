package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ICrafting;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.RecipesFurnace;
import net.minecraft.server.v1_8_R3.Slot;
import net.minecraft.server.v1_8_R3.SlotFurnaceFuel;
import net.minecraft.server.v1_8_R3.SlotFurnaceResult;
import net.minecraft.server.v1_8_R3.TileEntityFurnace;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryFurnace;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerFurnace extends Container {
   private final IInventory furnace;
   private int f;
   private int g;
   private int h;
   private int i;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public CraftInventoryView getBukkitView() {
      if(this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryFurnace craftinventoryfurnace = new CraftInventoryFurnace((TileEntityFurnace)this.furnace);
         this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventoryfurnace, this);
         return this.bukkitEntity;
      }
   }

   public ContainerFurnace(PlayerInventory p_i160_1_, IInventory p_i160_2_) {
      this.furnace = p_i160_2_;
      this.a(new Slot(p_i160_2_, 0, 56, 17));
      this.a(new SlotFurnaceFuel(p_i160_2_, 1, 56, 53));
      this.a(new SlotFurnaceResult(p_i160_1_.player, p_i160_2_, 2, 116, 35));
      this.player = p_i160_1_;

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.a(new Slot(p_i160_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.a(new Slot(p_i160_1_, k, 8 + k * 18, 142));
      }

   }

   public void addSlotListener(ICrafting p_addSlotListener_1_) {
      super.addSlotListener(p_addSlotListener_1_);
      p_addSlotListener_1_.setContainerData(this, this.furnace);
   }

   public void b() {
      super.b();

      for(int i = 0; i < this.listeners.size(); ++i) {
         ICrafting icrafting = (ICrafting)this.listeners.get(i);
         if(this.f != this.furnace.getProperty(2)) {
            icrafting.setContainerData(this, 2, this.furnace.getProperty(2));
         }

         if(this.h != this.furnace.getProperty(0)) {
            icrafting.setContainerData(this, 0, this.furnace.getProperty(0));
         }

         if(this.i != this.furnace.getProperty(1)) {
            icrafting.setContainerData(this, 1, this.furnace.getProperty(1));
         }

         if(this.g != this.furnace.getProperty(3)) {
            icrafting.setContainerData(this, 3, this.furnace.getProperty(3));
         }
      }

      this.f = this.furnace.getProperty(2);
      this.h = this.furnace.getProperty(0);
      this.i = this.furnace.getProperty(1);
      this.g = this.furnace.getProperty(3);
   }

   public boolean a(EntityHuman p_a_1_) {
      return !this.checkReachable?true:this.furnace.a(p_a_1_);
   }

   public ItemStack b(EntityHuman p_b_1_, int p_b_2_) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.c.get(p_b_2_);
      if(slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.cloneItemStack();
         if(p_b_2_ == 2) {
            if(!this.a(itemstack1, 3, 39, true)) {
               return null;
            }

            slot.a(itemstack1, itemstack);
         } else if(p_b_2_ != 1 && p_b_2_ != 0) {
            if(RecipesFurnace.getInstance().getResult(itemstack1) != null) {
               if(!this.a(itemstack1, 0, 1, false)) {
                  return null;
               }
            } else if(TileEntityFurnace.isFuel(itemstack1)) {
               if(!this.a(itemstack1, 1, 2, false)) {
                  return null;
               }
            } else if(p_b_2_ >= 3 && p_b_2_ < 30) {
               if(!this.a(itemstack1, 30, 39, false)) {
                  return null;
               }
            } else if(p_b_2_ >= 30 && p_b_2_ < 39 && !this.a(itemstack1, 3, 30, false)) {
               return null;
            }
         } else if(!this.a(itemstack1, 3, 39, false)) {
            return null;
         }

         if(itemstack1.count == 0) {
            slot.set((ItemStack)null);
         } else {
            slot.f();
         }

         if(itemstack1.count == itemstack.count) {
            return null;
         }

         slot.a(p_b_1_, itemstack1);
      }

      return itemstack;
   }
}
