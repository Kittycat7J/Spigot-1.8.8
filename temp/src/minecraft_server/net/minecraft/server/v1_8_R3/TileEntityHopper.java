package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockChest;
import net.minecraft.server.v1_8_R3.BlockHopper;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerHopper;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityMinecartHopper;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.IHopper;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.IUpdatePlayerListBox;
import net.minecraft.server.v1_8_R3.IWorldInventory;
import net.minecraft.server.v1_8_R3.InventoryLargeChest;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.TileEntityContainer;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.inventory.Inventory;

public class TileEntityHopper extends TileEntityContainer implements IHopper, IUpdatePlayerListBox {
   private ItemStack[] items = new ItemStack[5];
   private String f;
   private int g = -1;
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

   public void a(NBTTagCompound p_a_1_) {
      super.a(p_a_1_);
      NBTTagList nbttaglist = p_a_1_.getList("Items", 10);
      this.items = new ItemStack[this.getSize()];
      if(p_a_1_.hasKeyOfType("CustomName", 8)) {
         this.f = p_a_1_.getString("CustomName");
      }

      this.g = p_a_1_.getInt("TransferCooldown");

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.get(i);
         byte b0 = nbttagcompound.getByte("Slot");
         if(b0 >= 0 && b0 < this.items.length) {
            this.items[b0] = ItemStack.createStack(nbttagcompound);
         }
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
      p_b_1_.setInt("TransferCooldown", this.g);
      if(this.hasCustomName()) {
         p_b_1_.setString("CustomName", this.f);
      }

   }

   public void update() {
      super.update();
   }

   public int getSize() {
      return this.items.length;
   }

   public ItemStack getItem(int p_getItem_1_) {
      return this.items[p_getItem_1_];
   }

   public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_) {
      if(this.items[p_splitStack_1_] != null) {
         if(this.items[p_splitStack_1_].count <= p_splitStack_2_) {
            ItemStack itemstack1 = this.items[p_splitStack_1_];
            this.items[p_splitStack_1_] = null;
            return itemstack1;
         } else {
            ItemStack itemstack = this.items[p_splitStack_1_].cloneAndSubtract(p_splitStack_2_);
            if(this.items[p_splitStack_1_].count == 0) {
               this.items[p_splitStack_1_] = null;
            }

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

   public void setItem(int p_setItem_1_, ItemStack p_setItem_2_) {
      this.items[p_setItem_1_] = p_setItem_2_;
      if(p_setItem_2_ != null && p_setItem_2_.count > this.getMaxStackSize()) {
         p_setItem_2_.count = this.getMaxStackSize();
      }

   }

   public String getName() {
      return this.hasCustomName()?this.f:"container.hopper";
   }

   public boolean hasCustomName() {
      return this.f != null && this.f.length() > 0;
   }

   public void a(String p_a_1_) {
      this.f = p_a_1_;
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

   public void c() {
      if(this.world != null && !this.world.isClientSide) {
         --this.g;
         if(!this.n()) {
            this.d(0);
            this.m();
         }
      }

   }

   public boolean m() {
      if(this.world != null && !this.world.isClientSide) {
         if(!this.n() && BlockHopper.f(this.u())) {
            boolean flag = false;
            if(!this.p()) {
               flag = this.r();
            }

            if(!this.q()) {
               flag = a((IHopper)this) || flag;
            }

            if(flag) {
               this.d(this.world.spigotConfig.hopperTransfer);
               this.update();
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private boolean p() {
      for(ItemStack itemstack : this.items) {
         if(itemstack != null) {
            return false;
         }
      }

      return true;
   }

   private boolean q() {
      for(ItemStack itemstack : this.items) {
         if(itemstack == null || itemstack.count != itemstack.getMaxStackSize()) {
            return false;
         }
      }

      return true;
   }

   private boolean r() {
      IInventory iinventory = this.H();
      if(iinventory == null) {
         return false;
      } else {
         EnumDirection enumdirection = BlockHopper.b(this.u()).opposite();
         if(this.a(iinventory, enumdirection)) {
            return false;
         } else {
            for(int i = 0; i < this.getSize(); ++i) {
               if(this.getItem(i) != null) {
                  ItemStack itemstack = this.getItem(i).cloneItemStack();
                  CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(this.splitStack(i, this.world.spigotConfig.hopperAmount));
                  Inventory inventory;
                  if(iinventory instanceof InventoryLargeChest) {
                     inventory = new CraftInventoryDoubleChest((InventoryLargeChest)iinventory);
                  } else {
                     inventory = iinventory.getOwner().getInventory();
                  }

                  InventoryMoveItemEvent inventorymoveitemevent = new InventoryMoveItemEvent(this.getOwner().getInventory(), craftitemstack.clone(), inventory, true);
                  this.getWorld().getServer().getPluginManager().callEvent(inventorymoveitemevent);
                  if(inventorymoveitemevent.isCancelled()) {
                     this.setItem(i, itemstack);
                     this.d(this.world.spigotConfig.hopperTransfer);
                     return false;
                  }

                  int j = inventorymoveitemevent.getItem().getAmount();
                  ItemStack itemstack1 = addItem(iinventory, CraftItemStack.asNMSCopy(inventorymoveitemevent.getItem()), enumdirection);
                  if(itemstack1 == null || itemstack1.count == 0) {
                     if(inventorymoveitemevent.getItem().equals(craftitemstack)) {
                        iinventory.update();
                     } else {
                        this.setItem(i, itemstack);
                     }

                     return true;
                  }

                  itemstack.count -= j - itemstack1.count;
                  this.setItem(i, itemstack);
               }
            }

            return false;
         }
      }
   }

   private boolean a(IInventory p_a_1_, EnumDirection p_a_2_) {
      if(p_a_1_ instanceof IWorldInventory) {
         IWorldInventory iworldinventory = (IWorldInventory)p_a_1_;
         int[] aint = iworldinventory.getSlotsForFace(p_a_2_);

         for(int k = 0; k < aint.length; ++k) {
            ItemStack itemstack1 = iworldinventory.getItem(aint[k]);
            if(itemstack1 == null || itemstack1.count != itemstack1.getMaxStackSize()) {
               return false;
            }
         }
      } else {
         int i = p_a_1_.getSize();

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack = p_a_1_.getItem(j);
            if(itemstack == null || itemstack.count != itemstack.getMaxStackSize()) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean b(IInventory p_b_0_, EnumDirection p_b_1_) {
      if(p_b_0_ instanceof IWorldInventory) {
         IWorldInventory iworldinventory = (IWorldInventory)p_b_0_;
         int[] aint = iworldinventory.getSlotsForFace(p_b_1_);

         for(int i = 0; i < aint.length; ++i) {
            if(iworldinventory.getItem(aint[i]) != null) {
               return false;
            }
         }
      } else {
         int j = p_b_0_.getSize();

         for(int k = 0; k < j; ++k) {
            if(p_b_0_.getItem(k) != null) {
               return false;
            }
         }
      }

      return true;
   }

   public static boolean a(IHopper p_a_0_) {
      IInventory iinventory = b(p_a_0_);
      if(iinventory != null) {
         EnumDirection enumdirection = EnumDirection.DOWN;
         if(b(iinventory, enumdirection)) {
            return false;
         }

         if(iinventory instanceof IWorldInventory) {
            IWorldInventory iworldinventory = (IWorldInventory)iinventory;
            int[] aint = iworldinventory.getSlotsForFace(enumdirection);

            for(int i = 0; i < aint.length; ++i) {
               if(a(p_a_0_, iinventory, aint[i], enumdirection)) {
                  return true;
               }
            }
         } else {
            int j = iinventory.getSize();

            for(int k = 0; k < j; ++k) {
               if(a(p_a_0_, iinventory, k, enumdirection)) {
                  return true;
               }
            }
         }
      } else {
         for(EntityItem entityitem : a(p_a_0_.getWorld(), p_a_0_.A(), p_a_0_.B() + 1.0D, p_a_0_.C())) {
            if(a((IInventory)p_a_0_, (EntityItem)entityitem)) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean a(IHopper p_a_0_, IInventory p_a_1_, int p_a_2_, EnumDirection p_a_3_) {
      ItemStack itemstack = p_a_1_.getItem(p_a_2_);
      if(itemstack != null && b(p_a_1_, itemstack, p_a_2_, p_a_3_)) {
         ItemStack itemstack1 = itemstack.cloneItemStack();
         CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(p_a_1_.splitStack(p_a_2_, p_a_0_.getWorld().spigotConfig.hopperAmount));
         Inventory inventory;
         if(p_a_1_ instanceof InventoryLargeChest) {
            inventory = new CraftInventoryDoubleChest((InventoryLargeChest)p_a_1_);
         } else {
            inventory = p_a_1_.getOwner().getInventory();
         }

         InventoryMoveItemEvent inventorymoveitemevent = new InventoryMoveItemEvent(inventory, craftitemstack.clone(), p_a_0_.getOwner().getInventory(), false);
         p_a_0_.getWorld().getServer().getPluginManager().callEvent(inventorymoveitemevent);
         if(inventorymoveitemevent.isCancelled()) {
            p_a_1_.setItem(p_a_2_, itemstack1);
            if(p_a_0_ instanceof TileEntityHopper) {
               ((TileEntityHopper)p_a_0_).d(p_a_0_.getWorld().spigotConfig.hopperTransfer);
            } else if(p_a_0_ instanceof EntityMinecartHopper) {
               ((EntityMinecartHopper)p_a_0_).m(p_a_0_.getWorld().spigotConfig.hopperTransfer / 2);
            }

            return false;
         }

         int i = inventorymoveitemevent.getItem().getAmount();
         ItemStack itemstack2 = addItem(p_a_0_, CraftItemStack.asNMSCopy(inventorymoveitemevent.getItem()), (EnumDirection)null);
         if(itemstack2 == null || itemstack2.count == 0) {
            if(inventorymoveitemevent.getItem().equals(craftitemstack)) {
               p_a_1_.update();
            } else {
               p_a_1_.setItem(p_a_2_, itemstack1);
            }

            return true;
         }

         itemstack1.count -= i - itemstack2.count;
         p_a_1_.setItem(p_a_2_, itemstack1);
      }

      return false;
   }

   public static boolean a(IInventory p_a_0_, EntityItem p_a_1_) {
      boolean flag = false;
      if(p_a_1_ == null) {
         return false;
      } else {
         InventoryPickupItemEvent inventorypickupitemevent = new InventoryPickupItemEvent(p_a_0_.getOwner().getInventory(), (org.bukkit.entity.Item)p_a_1_.getBukkitEntity());
         p_a_1_.world.getServer().getPluginManager().callEvent(inventorypickupitemevent);
         if(inventorypickupitemevent.isCancelled()) {
            return false;
         } else {
            ItemStack itemstack = p_a_1_.getItemStack().cloneItemStack();
            ItemStack itemstack1 = addItem(p_a_0_, itemstack, (EnumDirection)null);
            if(itemstack1 != null && itemstack1.count != 0) {
               p_a_1_.setItemStack(itemstack1);
            } else {
               flag = true;
               p_a_1_.die();
            }

            return flag;
         }
      }
   }

   public static ItemStack addItem(IInventory p_addItem_0_, ItemStack p_addItem_1_, EnumDirection p_addItem_2_) {
      if(p_addItem_0_ instanceof IWorldInventory && p_addItem_2_ != null) {
         IWorldInventory iworldinventory = (IWorldInventory)p_addItem_0_;
         int[] aint = iworldinventory.getSlotsForFace(p_addItem_2_);

         for(int k = 0; k < aint.length && p_addItem_1_ != null && p_addItem_1_.count > 0; ++k) {
            p_addItem_1_ = c(p_addItem_0_, p_addItem_1_, aint[k], p_addItem_2_);
         }
      } else {
         int i = p_addItem_0_.getSize();

         for(int j = 0; j < i && p_addItem_1_ != null && p_addItem_1_.count > 0; ++j) {
            p_addItem_1_ = c(p_addItem_0_, p_addItem_1_, j, p_addItem_2_);
         }
      }

      if(p_addItem_1_ != null && p_addItem_1_.count == 0) {
         p_addItem_1_ = null;
      }

      return p_addItem_1_;
   }

   private static boolean a(IInventory p_a_0_, ItemStack p_a_1_, int p_a_2_, EnumDirection p_a_3_) {
      return !p_a_0_.b(p_a_2_, p_a_1_)?false:!(p_a_0_ instanceof IWorldInventory) || ((IWorldInventory)p_a_0_).canPlaceItemThroughFace(p_a_2_, p_a_1_, p_a_3_);
   }

   private static boolean b(IInventory p_b_0_, ItemStack p_b_1_, int p_b_2_, EnumDirection p_b_3_) {
      return !(p_b_0_ instanceof IWorldInventory) || ((IWorldInventory)p_b_0_).canTakeItemThroughFace(p_b_2_, p_b_1_, p_b_3_);
   }

   private static ItemStack c(IInventory p_c_0_, ItemStack p_c_1_, int p_c_2_, EnumDirection p_c_3_) {
      ItemStack itemstack = p_c_0_.getItem(p_c_2_);
      if(a(p_c_0_, p_c_1_, p_c_2_, p_c_3_)) {
         boolean flag = false;
         if(itemstack == null) {
            p_c_0_.setItem(p_c_2_, p_c_1_);
            p_c_1_ = null;
            flag = true;
         } else if(a(itemstack, p_c_1_)) {
            int i = p_c_1_.getMaxStackSize() - itemstack.count;
            int j = Math.min(p_c_1_.count, i);
            p_c_1_.count -= j;
            itemstack.count += j;
            flag = j > 0;
         }

         if(flag) {
            if(p_c_0_ instanceof TileEntityHopper) {
               TileEntityHopper tileentityhopper = (TileEntityHopper)p_c_0_;
               if(tileentityhopper.o()) {
                  tileentityhopper.d(tileentityhopper.world.spigotConfig.hopperTransfer);
               }

               p_c_0_.update();
            }

            p_c_0_.update();
         }
      }

      return p_c_1_;
   }

   private IInventory H() {
      EnumDirection enumdirection = BlockHopper.b(this.u());
      return b(this.getWorld(), (double)(this.position.getX() + enumdirection.getAdjacentX()), (double)(this.position.getY() + enumdirection.getAdjacentY()), (double)(this.position.getZ() + enumdirection.getAdjacentZ()));
   }

   public static IInventory b(IHopper p_b_0_) {
      return b(p_b_0_.getWorld(), p_b_0_.A(), p_b_0_.B() + 1.0D, p_b_0_.C());
   }

   public static List<EntityItem> a(World p_a_0_, double p_a_1_, double p_a_3_, double p_a_5_) {
      return p_a_0_.a(EntityItem.class, new AxisAlignedBB(p_a_1_ - 0.5D, p_a_3_ - 0.5D, p_a_5_ - 0.5D, p_a_1_ + 0.5D, p_a_3_ + 0.5D, p_a_5_ + 0.5D), IEntitySelector.a);
   }

   public static IInventory b(World p_b_0_, double p_b_1_, double p_b_3_, double p_b_5_) {
      Object object = null;
      int i = MathHelper.floor(p_b_1_);
      int j = MathHelper.floor(p_b_3_);
      int k = MathHelper.floor(p_b_5_);
      BlockPosition blockposition = new BlockPosition(i, j, k);
      if(!p_b_0_.isLoaded(blockposition)) {
         return null;
      } else {
         Block block = p_b_0_.getType(blockposition).getBlock();
         if(block.isTileEntity()) {
            TileEntity tileentity = p_b_0_.getTileEntity(blockposition);
            if(tileentity instanceof IInventory) {
               object = (IInventory)tileentity;
               if(object instanceof TileEntityChest && block instanceof BlockChest) {
                  object = ((BlockChest)block).f(p_b_0_, blockposition);
               }
            }
         }

         if(object == null) {
            List list = p_b_0_.a((Entity)null, (AxisAlignedBB)(new AxisAlignedBB(p_b_1_ - 0.5D, p_b_3_ - 0.5D, p_b_5_ - 0.5D, p_b_1_ + 0.5D, p_b_3_ + 0.5D, p_b_5_ + 0.5D)), (Predicate)IEntitySelector.c);
            if(list.size() > 0) {
               object = (IInventory)list.get(p_b_0_.random.nextInt(list.size()));
            }
         }

         return (IInventory)object;
      }
   }

   private static boolean a(ItemStack p_a_0_, ItemStack p_a_1_) {
      return p_a_0_.getItem() != p_a_1_.getItem()?false:(p_a_0_.getData() != p_a_1_.getData()?false:(p_a_0_.count > p_a_0_.getMaxStackSize()?false:ItemStack.equals(p_a_0_, p_a_1_)));
   }

   public double A() {
      return (double)this.position.getX() + 0.5D;
   }

   public double B() {
      return (double)this.position.getY() + 0.5D;
   }

   public double C() {
      return (double)this.position.getZ() + 0.5D;
   }

   public void d(int p_d_1_) {
      this.g = p_d_1_;
   }

   public boolean n() {
      return this.g > 0;
   }

   public boolean o() {
      return this.g <= 1;
   }

   public String getContainerName() {
      return "minecraft:hopper";
   }

   public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_) {
      return new ContainerHopper(p_createContainer_1_, this, p_createContainer_2_);
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
