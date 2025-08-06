package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.server.v1_8_R3.BlockBrewingStand;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerBrewingStand;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.IWorldInventory;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemPotion;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.PotionBrewer;
import net.minecraft.server.v1_8_R3.TileEntityContainer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.BrewerInventory;

public class TileEntityBrewingStand extends TileEntityContainer implements IUpdatePlayerListBox, IWorldInventory {
   private static final int[] a = new int[]{3};
   private static final int[] f = new int[]{0, 1, 2};
   private ItemStack[] items = new ItemStack[4];
   public int brewTime;
   private boolean[] i;
   private Item j;
   private String k;
   private int lastTick = MinecraftServer.currentTick;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

   public void onOpen(CraftHumanEntity p_onOpen_1_) {
      this.transaction.add(p_onOpen_1_);
   }

   public void onClose(CraftHumanEntity p_onClose_1_) {
      this.transaction.remove(p_onClose_1_);
   }

   public List<HumanEntity> getViewers() {
      return this.transaction;
   }

   public ItemStack[] getContents() {
      return this.items;
   }

   public void setMaxStackSize(int p_setMaxStackSize_1_) {
      this.maxStack = p_setMaxStackSize_1_;
   }

   public String getName() {
      return this.hasCustomName()?this.k:"container.brewing";
   }

   public boolean hasCustomName() {
      return this.k != null && this.k.length() > 0;
   }

   public void a(String p_a_1_) {
      this.k = p_a_1_;
   }

   public int getSize() {
      return this.items.length;
   }

   public void c() {
      int i = MinecraftServer.currentTick - this.lastTick;
      this.lastTick = MinecraftServer.currentTick;
      if(this.brewTime > 0) {
         this.brewTime -= i;
         if(this.brewTime <= 0) {
            this.o();
            this.update();
         } else if(!this.n()) {
            this.brewTime = 0;
            this.update();
         } else if(this.j != this.items[3].getItem()) {
            this.brewTime = 0;
            this.update();
         }
      } else if(this.n()) {
         this.brewTime = 400;
         this.j = this.items[3].getItem();
      }

      if(!this.world.isClientSide) {
         boolean[] aboolean = this.m();
         if(!Arrays.equals(aboolean, this.i)) {
            this.i = aboolean;
            IBlockData iblockdata = this.world.getType(this.getPosition());
            if(!(iblockdata.getBlock() instanceof BlockBrewingStand)) {
               return;
            }

            for(int j = 0; j < BlockBrewingStand.HAS_BOTTLE.length; ++j) {
               iblockdata = iblockdata.set(BlockBrewingStand.HAS_BOTTLE[j], Boolean.valueOf(aboolean[j]));
            }

            this.world.setTypeAndData(this.position, iblockdata, 2);
         }
      }

   }

   private boolean n() {
      if(this.items[3] != null && this.items[3].count > 0) {
         ItemStack itemstack = this.items[3];
         if(!itemstack.getItem().l(itemstack)) {
            return false;
         } else {
            boolean flag = false;

            for(int i = 0; i < 3; ++i) {
               if(this.items[i] != null && this.items[i].getItem() == Items.POTION) {
                  int j = this.items[i].getData();
                  int k = this.c(j, itemstack);
                  if(!ItemPotion.f(j) && ItemPotion.f(k)) {
                     flag = true;
                     break;
                  }

                  List list = Items.POTION.e(j);
                  List list1 = Items.POTION.e(k);
                  if((j <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null) && j != k) {
                     flag = true;
                     break;
                  }
               }
            }

            return flag;
         }
      } else {
         return false;
      }
   }

   private void o() {
      if(this.n()) {
         ItemStack itemstack = this.items[3];
         if(this.getOwner() != null) {
            BrewEvent brewevent = new BrewEvent(this.world.getWorld().getBlockAt(this.position.getX(), this.position.getY(), this.position.getZ()), (BrewerInventory)this.getOwner().getInventory());
            Bukkit.getPluginManager().callEvent(brewevent);
            if(brewevent.isCancelled()) {
               return;
            }
         }

         for(int k = 0; k < 3; ++k) {
            if(this.items[k] != null && this.items[k].getItem() == Items.POTION) {
               int i = this.items[k].getData();
               int j = this.c(i, itemstack);
               List list = Items.POTION.e(i);
               List list1 = Items.POTION.e(j);
               if((i <= 0 || list != list1) && (list == null || !list.equals(list1) && list1 != null)) {
                  if(i != j) {
                     this.items[k].setData(j);
                  }
               } else if(!ItemPotion.f(i) && ItemPotion.f(j)) {
                  this.items[k].setData(j);
               }
            }
         }

         if(itemstack.getItem().r()) {
            this.items[3] = new ItemStack(itemstack.getItem().q());
         } else {
            --this.items[3].count;
            if(this.items[3].count <= 0) {
               this.items[3] = null;
            }
         }
      }

   }

   private int c(int p_c_1_, ItemStack p_c_2_) {
      return p_c_2_ == null?p_c_1_:(p_c_2_.getItem().l(p_c_2_)?PotionBrewer.a(p_c_1_, p_c_2_.getItem().j(p_c_2_)):p_c_1_);
   }

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      NBTTagList nbttaglist = p_a_1_.getList("Items", 10);
      this.items = new ItemStack[this.getSize()];

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.get(i);
         byte b0 = nbttagcompound.getByte("Slot");
         if(b0 >= 0 && b0 < this.items.length) {
            this.items[b0] = ItemStack.createStack(nbttagcompound);
         }
      }

      this.brewTime = p_a_1_.getShort("BrewTime");
      if(p_a_1_.hasKeyOfType("CustomName", 8)) {
         this.k = p_a_1_.getString("CustomName");
      }

   }

   public void b(NBTTagCompound p_b_1_) {
      super.b(p_b_1_);
      p_b_1_.setShort("BrewTime", (short)this.brewTime);
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
         p_b_1_.setString("CustomName", this.k);
      }

   }

   public ItemStack getItem(int p_getItem_1_) {
      return p_getItem_1_ >= 0 && p_getItem_1_ < this.items.length?this.items[p_getItem_1_]:null;
   }

   public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_) {
      if(p_splitStack_1_ >= 0 && p_splitStack_1_ < this.items.length) {
         ItemStack itemstack = this.items[p_splitStack_1_];
         this.items[p_splitStack_1_] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_) {
      if(p_splitWithoutUpdate_1_ >= 0 && p_splitWithoutUpdate_1_ < this.items.length) {
         ItemStack itemstack = this.items[p_splitWithoutUpdate_1_];
         this.items[p_splitWithoutUpdate_1_] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void setItem(int p_setItem_1_, ItemStack p_setItem_2_) {
      if(p_setItem_1_ >= 0 && p_setItem_1_ < this.items.length) {
         this.items[p_setItem_1_] = p_setItem_2_;
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
      return p_b_1_ == 3?p_b_2_.getItem().l(p_b_2_):p_b_2_.getItem() == Items.POTION || p_b_2_.getItem() == Items.GLASS_BOTTLE;
   }

   public boolean[] m() {
      boolean[] aboolean = new boolean[3];

      for(int i = 0; i < 3; ++i) {
         if(this.items[i] != null) {
            aboolean[i] = true;
         }
      }

      return aboolean;
   }

   public int[] getSlotsForFace(EnumDirection p_getSlotsForFace_1_) {
      return p_getSlotsForFace_1_ == EnumDirection.UP?a:f;
   }

   public boolean canPlaceItemThroughFace(int p_canPlaceItemThroughFace_1_, ItemStack p_canPlaceItemThroughFace_2_, EnumDirection p_canPlaceItemThroughFace_3_) {
      return this.b(p_canPlaceItemThroughFace_1_, p_canPlaceItemThroughFace_2_);
   }

   public boolean canTakeItemThroughFace(int p_canTakeItemThroughFace_1_, ItemStack p_canTakeItemThroughFace_2_, EnumDirection p_canTakeItemThroughFace_3_) {
      return true;
   }

   public String getContainerName() {
      return "minecraft:brewing_stand";
   }

   public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_) {
      return new ContainerBrewingStand(p_createContainer_1_, this);
   }

   public int getProperty(int p_getProperty_1_) {
      switch(p_getProperty_1_) {
      case 0:
         return this.brewTime;
      default:
         return 0;
      }
   }

   public void b(int p_b_1_, int p_b_2_) {
      switch(p_b_1_) {
      case 0:
         this.brewTime = p_b_2_;
      default:
      }
   }

   public int g() {
      return 1;
   }

   public void l() {
      for(int i = 0; i < this.items.length; ++i) {
         this.items[i] = null;
      }

   }
}
