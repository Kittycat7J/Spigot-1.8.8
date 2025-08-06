package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryCraftResult implements IInventory {
   private ItemStack[] items = new ItemStack[1];
   private int maxStack = 64;

   public ItemStack[] getContents() {
      return this.items;
   }

   public InventoryHolder getOwner() {
      return null;
   }

   public void onOpen(CraftHumanEntity p_onOpen_1_) {
   }

   public void onClose(CraftHumanEntity p_onClose_1_) {
   }

   public List<HumanEntity> getViewers() {
      return new ArrayList();
   }

   public void setMaxStackSize(int p_setMaxStackSize_1_) {
      this.maxStack = p_setMaxStackSize_1_;
   }

   public int getSize() {
      return 1;
   }

   public ItemStack getItem(int p_getItem_1_) {
      return this.items[0];
   }

   public String getName() {
      return "Result";
   }

   public boolean hasCustomName() {
      return false;
   }

   public IChatBaseComponent getScoreboardDisplayName() {
      return (IChatBaseComponent)(this.hasCustomName()?new ChatComponentText(this.getName()):new ChatMessage(this.getName(), new Object[0]));
   }

   public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_) {
      if(this.items[0] != null) {
         ItemStack itemstack = this.items[0];
         this.items[0] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_) {
      if(this.items[0] != null) {
         ItemStack itemstack = this.items[0];
         this.items[0] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void setItem(int p_setItem_1_, ItemStack p_setItem_2_) {
      this.items[0] = p_setItem_2_;
   }

   public int getMaxStackSize() {
      return this.maxStack;
   }

   public void update() {
   }

   public boolean a(EntityHuman p_a_1_) {
      return true;
   }

   public void startOpen(EntityHuman p_startOpen_1_) {
   }

   public void closeContainer(EntityHuman p_closeContainer_1_) {
   }

   public boolean b(int p_b_1_, ItemStack p_b_2_) {
      return true;
   }

   public int getProperty(int p_getProperty_1_) {
      return 0;
   }

   public void b(int p_b_1_, int p_b_2_) {
   }

   public int g() {
      return 0;
   }

   public void l() {
      for(int i = 0; i < this.items.length; ++i) {
         this.items[i] = null;
      }

   }
}
