package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ICrafting;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.Slot;
import net.minecraft.server.v1_8_R3.TileEntityBeacon;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryBeacon;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerBeacon extends Container {
   private IInventory beacon;
   private final ContainerBeacon.SlotBeacon f;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public ContainerBeacon(IInventory p_i194_1_, IInventory p_i194_2_) {
      this.player = (PlayerInventory)p_i194_1_;
      this.beacon = p_i194_2_;
      this.a(this.f = new ContainerBeacon.SlotBeacon(p_i194_2_, 0, 136, 110));
      byte b0 = 36;
      short short1 = 137;

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.a(new Slot(p_i194_1_, j + i * 9 + 9, b0 + j * 18, short1 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.a(new Slot(p_i194_1_, k, b0 + k * 18, 58 + short1));
      }

   }

   public void addSlotListener(ICrafting p_addSlotListener_1_) {
      super.addSlotListener(p_addSlotListener_1_);
      p_addSlotListener_1_.setContainerData(this, this.beacon);
   }

   public IInventory e() {
      return this.beacon;
   }

   public void b(EntityHuman p_b_1_) {
      super.b(p_b_1_);
      if(p_b_1_ != null && !p_b_1_.world.isClientSide) {
         ItemStack itemstack = this.f.a(this.f.getMaxStackSize());
         if(itemstack != null) {
            p_b_1_.drop(itemstack, false);
         }
      }

   }

   public boolean a(EntityHuman p_a_1_) {
      return !this.checkReachable?true:this.beacon.a(p_a_1_);
   }

   public ItemStack b(EntityHuman p_b_1_, int p_b_2_) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.c.get(p_b_2_);
      if(slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.cloneItemStack();
         if(p_b_2_ == 0) {
            if(!this.a(itemstack1, 1, 37, true)) {
               return null;
            }

            slot.a(itemstack1, itemstack);
         } else if(!this.f.hasItem() && this.f.isAllowed(itemstack1) && itemstack1.count == 1) {
            if(!this.a(itemstack1, 0, 1, false)) {
               return null;
            }
         } else if(p_b_2_ >= 1 && p_b_2_ < 28) {
            if(!this.a(itemstack1, 28, 37, false)) {
               return null;
            }
         } else if(p_b_2_ >= 28 && p_b_2_ < 37) {
            if(!this.a(itemstack1, 1, 28, false)) {
               return null;
            }
         } else if(!this.a(itemstack1, 1, 37, false)) {
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

   public CraftInventoryView getBukkitView() {
      if(this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory craftinventory = new CraftInventoryBeacon((TileEntityBeacon)this.beacon);
         this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventory, this);
         return this.bukkitEntity;
      }
   }

   class SlotBeacon extends Slot {
      public SlotBeacon(IInventory p_i145_2_, int p_i145_3_, int p_i145_4_, int p_i145_5_) {
         super(p_i145_2_, p_i145_3_, p_i145_4_, p_i145_5_);
      }

      public boolean isAllowed(ItemStack p_isAllowed_1_) {
         return p_isAllowed_1_ == null?false:p_isAllowed_1_.getItem() == Items.EMERALD || p_isAllowed_1_.getItem() == Items.DIAMOND || p_isAllowed_1_.getItem() == Items.GOLD_INGOT || p_isAllowed_1_.getItem() == Items.IRON_INGOT;
      }

      public int getMaxStackSize() {
         return 1;
      }
   }
}
