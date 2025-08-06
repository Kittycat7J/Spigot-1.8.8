package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.ChestLock;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerChest;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ITileInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class InventoryLargeChest implements ITileInventory {
   private String a;
   public ITileInventory left;
   public ITileInventory right;
   public List<HumanEntity> transaction = new ArrayList();

   public ItemStack[] getContents() {
      ItemStack[] aitemstack = new ItemStack[this.getSize()];

      for(int i = 0; i < aitemstack.length; ++i) {
         aitemstack[i] = this.getItem(i);
      }

      return aitemstack;
   }

   public void onOpen(CraftHumanEntity p_onOpen_1_) {
      this.left.onOpen(p_onOpen_1_);
      this.right.onOpen(p_onOpen_1_);
      this.transaction.add(p_onOpen_1_);
   }

   public void onClose(CraftHumanEntity p_onClose_1_) {
      this.left.onClose(p_onClose_1_);
      this.right.onClose(p_onClose_1_);
      this.transaction.remove(p_onClose_1_);
   }

   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   public InventoryHolder getOwner() {
      return null;
   }

   public void setMaxStackSize(int p_setMaxStackSize_1_) {
      this.left.setMaxStackSize(p_setMaxStackSize_1_);
      this.right.setMaxStackSize(p_setMaxStackSize_1_);
   }

   public InventoryLargeChest(String p_i254_1_, ITileInventory p_i254_2_, ITileInventory p_i254_3_) {
      this.a = p_i254_1_;
      if(p_i254_2_ == null) {
         p_i254_2_ = p_i254_3_;
      }

      if(p_i254_3_ == null) {
         p_i254_3_ = p_i254_2_;
      }

      this.left = p_i254_2_;
      this.right = p_i254_3_;
      if(p_i254_2_.r_()) {
         p_i254_3_.a(p_i254_2_.i());
      } else if(p_i254_3_.r_()) {
         p_i254_2_.a(p_i254_3_.i());
      }

   }

   public int getSize() {
      return this.left.getSize() + this.right.getSize();
   }

   public boolean a(IInventory p_a_1_) {
      return this.left == p_a_1_ || this.right == p_a_1_;
   }

   public String getName() {
      return this.left.hasCustomName()?this.left.getName():(this.right.hasCustomName()?this.right.getName():this.a);
   }

   public boolean hasCustomName() {
      return this.left.hasCustomName() || this.right.hasCustomName();
   }

   public IChatBaseComponent getScoreboardDisplayName() {
      return (IChatBaseComponent)(this.hasCustomName()?new ChatComponentText(this.getName()):new ChatMessage(this.getName(), new Object[0]));
   }

   public ItemStack getItem(int p_getItem_1_) {
      return p_getItem_1_ >= this.left.getSize()?this.right.getItem(p_getItem_1_ - this.left.getSize()):this.left.getItem(p_getItem_1_);
   }

   public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_) {
      return p_splitStack_1_ >= this.left.getSize()?this.right.splitStack(p_splitStack_1_ - this.left.getSize(), p_splitStack_2_):this.left.splitStack(p_splitStack_1_, p_splitStack_2_);
   }

   public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_) {
      return p_splitWithoutUpdate_1_ >= this.left.getSize()?this.right.splitWithoutUpdate(p_splitWithoutUpdate_1_ - this.left.getSize()):this.left.splitWithoutUpdate(p_splitWithoutUpdate_1_);
   }

   public void setItem(int p_setItem_1_, ItemStack p_setItem_2_) {
      if(p_setItem_1_ >= this.left.getSize()) {
         this.right.setItem(p_setItem_1_ - this.left.getSize(), p_setItem_2_);
      } else {
         this.left.setItem(p_setItem_1_, p_setItem_2_);
      }

   }

   public int getMaxStackSize() {
      return Math.min(this.left.getMaxStackSize(), this.right.getMaxStackSize());
   }

   public void update() {
      this.left.update();
      this.right.update();
   }

   public boolean a(EntityHuman p_a_1_) {
      return this.left.a(p_a_1_) && this.right.a(p_a_1_);
   }

   public void startOpen(EntityHuman p_startOpen_1_) {
      this.left.startOpen(p_startOpen_1_);
      this.right.startOpen(p_startOpen_1_);
   }

   public void closeContainer(EntityHuman p_closeContainer_1_) {
      this.left.closeContainer(p_closeContainer_1_);
      this.right.closeContainer(p_closeContainer_1_);
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

   public boolean r_() {
      return this.left.r_() || this.right.r_();
   }

   public void a(ChestLock p_a_1_) {
      this.left.a(p_a_1_);
      this.right.a(p_a_1_);
   }

   public ChestLock i() {
      return this.left.i();
   }

   public String getContainerName() {
      return this.left.getContainerName();
   }

   public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_) {
      return new ContainerChest(p_createContainer_1_, this, p_createContainer_2_);
   }

   public void l() {
      this.left.l();
      this.right.l();
   }
}
