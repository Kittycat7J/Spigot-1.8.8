package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.CraftingManager;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.InventoryCraftResult;
import net.minecraft.server.v1_8_R3.InventoryCrafting;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.Slot;
import net.minecraft.server.v1_8_R3.SlotResult;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCrafting;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerPlayer extends Container {
   public InventoryCrafting craftInventory = new InventoryCrafting(this, 2, 2);
   public IInventory resultInventory = new InventoryCraftResult();
   public boolean g;
   private final EntityHuman h;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public ContainerPlayer(final PlayerInventory p_i114_1_, boolean p_i114_2_, EntityHuman p_i114_3_) {
      this.g = p_i114_2_;
      this.h = p_i114_3_;
      this.resultInventory = new InventoryCraftResult();
      this.craftInventory = new InventoryCrafting(this, 2, 2, p_i114_1_.player);
      this.craftInventory.resultInventory = this.resultInventory;
      this.player = p_i114_1_;
      this.a((Slot)(new SlotResult(p_i114_1_.player, this.craftInventory, this.resultInventory, 0, 144, 36)));

      for(int i = 0; i < 2; ++i) {
         for(int j = 0; j < 2; ++j) {
            this.a((Slot)(new Slot(this.craftInventory, j + i * 2, 88 + j * 18, 26 + i * 18)));
         }
      }

      for(final int k = 0; k < 4; ++k) {
         this.a((Slot)(new Slot(p_i114_1_, p_i114_1_.getSize() - 1 - k, 8, 8 + k * 18) {
            public int getMaxStackSize() {
               return 1;
            }

            public boolean isAllowed(ItemStack p_isAllowed_1_) {
               return p_isAllowed_1_ == null?false:(p_isAllowed_1_.getItem() instanceof ItemArmor?((ItemArmor)p_isAllowed_1_.getItem()).b == k:(p_isAllowed_1_.getItem() != Item.getItemOf(Blocks.PUMPKIN) && p_isAllowed_1_.getItem() != Items.SKULL?false:k == 0));
            }
         }));
      }

      for(int l = 0; l < 3; ++l) {
         for(int j1 = 0; j1 < 9; ++j1) {
            this.a((Slot)(new Slot(p_i114_1_, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18)));
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         this.a((Slot)(new Slot(p_i114_1_, i1, 8 + i1 * 18, 142)));
      }

   }

   public void a(IInventory p_a_1_) {
      CraftingManager.getInstance().lastCraftView = this.getBukkitView();
      ItemStack itemstack = CraftingManager.getInstance().craft(this.craftInventory, this.h.world);
      this.resultInventory.setItem(0, itemstack);
      if(super.listeners.size() >= 1) {
         EntityPlayer entityplayer = (EntityPlayer)super.listeners.get(0);
         entityplayer.playerConnection.sendPacket(new PacketPlayOutSetSlot(entityplayer.activeContainer.windowId, 0, itemstack));
      }
   }

   public void b(EntityHuman p_b_1_) {
      super.b(p_b_1_);

      for(int i = 0; i < 4; ++i) {
         ItemStack itemstack = this.craftInventory.splitWithoutUpdate(i);
         if(itemstack != null) {
            p_b_1_.drop(itemstack, false);
         }
      }

      this.resultInventory.setItem(0, (ItemStack)null);
   }

   public boolean a(EntityHuman p_a_1_) {
      return true;
   }

   public ItemStack b(EntityHuman p_b_1_, int p_b_2_) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.c.get(p_b_2_);
      if(slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.cloneItemStack();
         if(p_b_2_ == 0) {
            if(!this.a(itemstack1, 9, 45, true)) {
               return null;
            }

            slot.a(itemstack1, itemstack);
         } else if(p_b_2_ >= 1 && p_b_2_ < 5) {
            if(!this.a(itemstack1, 9, 45, false)) {
               return null;
            }
         } else if(p_b_2_ >= 5 && p_b_2_ < 9) {
            if(!this.a(itemstack1, 9, 45, false)) {
               return null;
            }
         } else if(itemstack.getItem() instanceof ItemArmor && !((Slot)this.c.get(5 + ((ItemArmor)itemstack.getItem()).b)).hasItem()) {
            int i = 5 + ((ItemArmor)itemstack.getItem()).b;
            if(!this.a(itemstack1, i, i + 1, false)) {
               return null;
            }
         } else if(p_b_2_ >= 9 && p_b_2_ < 36) {
            if(!this.a(itemstack1, 36, 45, false)) {
               return null;
            }
         } else if(p_b_2_ >= 36 && p_b_2_ < 45) {
            if(!this.a(itemstack1, 9, 36, false)) {
               return null;
            }
         } else if(!this.a(itemstack1, 9, 45, false)) {
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

   public boolean a(ItemStack p_a_1_, Slot p_a_2_) {
      return p_a_2_.inventory != this.resultInventory && super.a(p_a_1_, p_a_2_);
   }

   public CraftInventoryView getBukkitView() {
      if(this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventoryCrafting craftinventorycrafting = new CraftInventoryCrafting(this.craftInventory, this.resultInventory);
         this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventorycrafting, this);
         return this.bukkitEntity;
      }
   }
}
