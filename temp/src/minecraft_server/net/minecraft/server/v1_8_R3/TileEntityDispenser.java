package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerDispenser;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.TileEntityContainer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;

public class TileEntityDispenser extends TileEntityContainer implements IInventory {
   private static final Random f = new Random();
   private ItemStack[] items = new ItemStack[9];
   protected String a;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

   public ItemStack[] getContents() {
      return this.items;
   }

   public void onOpen(CraftHumanEntity p_onOpen_1_) {
      this.transaction.add(p_onOpen_1_);
   }

   public void onClose(CraftHumanEntity p_onClose_1_) {
      this.transaction.remove(p_onClose_1_);
   }

   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   public void setMaxStackSize(int p_setMaxStackSize_1_) {
      this.maxStack = p_setMaxStackSize_1_;
   }

   public int getSize() {
      return 9;
   }

   public ItemStack getItem(int p_getItem_1_) {
      return this.items[p_getItem_1_];
   }

   public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_) {
      if(this.items[p_splitStack_1_] != null) {
         if(this.items[p_splitStack_1_].count <= p_splitStack_2_) {
            ItemStack itemstack1 = this.items[p_splitStack_1_];
            this.items[p_splitStack_1_] = null;
            this.update();
            return itemstack1;
         } else {
            ItemStack itemstack = this.items[p_splitStack_1_].cloneAndSubtract(p_splitStack_2_);
            if(this.items[p_splitStack_1_].count == 0) {
               this.items[p_splitStack_1_] = null;
            }

            this.update();
            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_) {
      if(this.items[p_splitWithoutUpdate_1_] != null) {
         ItemStack itemstack = this.items[p_splitWithoutUpdate_1_];
         this.items[p_splitWithoutUpdate_1_] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public int m() {
      int i = -1;
      int j = 1;

      for(int k = 0; k < this.items.length; ++k) {
         if(this.items[k] != null && f.nextInt(j++) == 0 && this.items[k].count != 0) {
            i = k;
         }
      }

      return i;
   }

   public void setItem(int p_setItem_1_, ItemStack p_setItem_2_) {
      this.items[p_setItem_1_] = p_setItem_2_;
      if(p_setItem_2_ != null && p_setItem_2_.count > this.getMaxStackSize()) {
         p_setItem_2_.count = this.getMaxStackSize();
      }

      this.update();
   }

   public int addItem(ItemStack p_addItem_1_) {
      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] == null || this.items[i].getItem() == null) {
            this.setItem(i, p_addItem_1_);
            return i;
         }
      }

      return -1;
   }

   public String getName() {
      return this.hasCustomName()?this.a:"container.dispenser";
   }

   public void a(String p_a_1_) {
      this.a = p_a_1_;
   }

   public boolean hasCustomName() {
      return this.a != null;
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      NBTTagList nbttaglist = p_a_1_.getList("Items", 10);
      this.items = new ItemStack[this.getSize()];

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.get(i);
         int j = nbttagcompound.getByte("Slot") & 255;
         if(j >= 0 && j < this.items.length) {
            this.items[j] = ItemStack.createStack(nbttagcompound);
         }
      }

      if(p_a_1_.hasKeyOfType("CustomName", 8)) {
         this.a = p_a_1_.getString("CustomName");
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Slot", (byte)i);
            this.items[i].save(nbttagcompound);
            nbttaglist.add(nbttagcompound);
         }
      }

      p_b_1_.set("Items", nbttaglist);
      if(this.hasCustomName()) {
         p_b_1_.setString("CustomName", this.a);
      }

   }

   public int getMaxStackSize() {
      return this.maxStack;
   }

   public boolean a(EntityHuman p_a_1_) {
      return this.world.getTileEntity(this.position) != this?false:p_a_1_.e((double)this.position.getX() + 0.5D, (double)this.position.getY() + 0.5D, (double)this.position.getZ() + 0.5D) <= 64.0D;
   }

   public void startOpen(EntityHuman p_startOpen_1_) {
   }

   public void closeContainer(EntityHuman p_closeContainer_1_) {
   }

   public boolean b(int p_b_1_, ItemStack p_b_2_) {
      return true;
   }

   public String getContainerName() {
      return "minecraft:dispenser";
   }

   public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_) {
      return new ContainerDispenser(p_createContainer_1_, this);
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
