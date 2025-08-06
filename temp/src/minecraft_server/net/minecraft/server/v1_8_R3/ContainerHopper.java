package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.Slot;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerHopper extends Container {
   private final IInventory hopper;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public CraftInventoryView getBukkitView() {
      if(this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory craftinventory = new CraftInventory(this.hopper);
         this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventory, this);
         return this.bukkitEntity;
      }
   }

   public ContainerHopper(PlayerInventory p_i459_1_, IInventory p_i459_2_, EntityHuman p_i459_3_) {
      this.hopper = p_i459_2_;
      this.player = p_i459_1_;
      p_i459_2_.startOpen(p_i459_3_);
      byte b0 = 51;

      for(int i = 0; i < p_i459_2_.getSize(); ++i) {
         this.a(new Slot(p_i459_2_, i, 44 + i * 18, 20));
      }

      for(int k = 0; k < 3; ++k) {
         for(int j = 0; j < 9; ++j) {
            this.a(new Slot(p_i459_1_, j + k * 9 + 9, 8 + j * 18, k * 18 + b0));
         }
      }

      for(int l = 0; l < 9; ++l) {
         this.a(new Slot(p_i459_1_, l, 8 + l * 18, 58 + b0));
      }

   }

   public boolean a(EntityHuman p_a_1_) {
      return !this.checkReachable?true:this.hopper.a(p_a_1_);
   }

   public ItemStack b(EntityHuman p_b_1_, int p_b_2_) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.c.get(p_b_2_);
      if(slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.cloneItemStack();
         if(p_b_2_ < this.hopper.getSize()) {
            if(!this.a(itemstack1, this.hopper.getSize(), this.c.size(), true)) {
               return null;
            }
         } else if(!this.a(itemstack1, 0, this.hopper.getSize(), false)) {
            return null;
         }

         if(itemstack1.count == 0) {
            slot.set((ItemStack)null);
         } else {
            slot.f();
         }
      }

      return itemstack;
   }

   public void b(EntityHuman p_b_1_) {
      super.b(p_b_1_);
      this.hopper.closeContainer(p_b_1_);
   }
}
