package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.CrashReport;
import net.minecraft.server.v1_8_R3.CrashReportSystemDetails;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.GameProfileSerializer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.ReportedException;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryHolder;

public class PlayerInventory implements IInventory {
   public ItemStack[] items = new ItemStack[36];
   public ItemStack[] armor = new ItemStack[4];
   public int itemInHandIndex;
   public EntityHuman player;
   private ItemStack f;
   public boolean e;
   public List<HumanEntity> transaction = new ArrayList();
   private int maxStack = 64;

   public ItemStack[] getContents() {
      return this.items;
   }

   public ItemStack[] getArmorContents() {
      return this.armor;
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

   public InventoryHolder getOwner() {
      return this.player.getBukkitEntity();
   }

   public void setMaxStackSize(int p_setMaxStackSize_1_) {
      this.maxStack = p_setMaxStackSize_1_;
   }

   public PlayerInventory(EntityHuman p_i333_1_) {
      this.player = p_i333_1_;
   }

   public ItemStack getItemInHand() {
      return this.itemInHandIndex < 9 && this.itemInHandIndex >= 0?this.items[this.itemInHandIndex]:null;
   }

   public static int getHotbarSize() {
      return 9;
   }

   private int c(Item p_c_1_) {
      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] != null && this.items[i].getItem() == p_c_1_) {
            return i;
         }
      }

      return -1;
   }

   private int firstPartial(ItemStack p_firstPartial_1_) {
      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] != null && this.items[i].getItem() == p_firstPartial_1_.getItem() && this.items[i].isStackable() && this.items[i].count < this.items[i].getMaxStackSize() && this.items[i].count < this.getMaxStackSize() && (!this.items[i].usesData() || this.items[i].getData() == p_firstPartial_1_.getData()) && ItemStack.equals(this.items[i], p_firstPartial_1_)) {
            return i;
         }
      }

      return -1;
   }

   public int canHold(ItemStack p_canHold_1_) {
      int i = p_canHold_1_.count;

      for(int j = 0; j < this.items.length; ++j) {
         if(this.items[j] == null) {
            return p_canHold_1_.count;
         }

         if(this.items[j] != null && this.items[j].getItem() == p_canHold_1_.getItem() && this.items[j].isStackable() && this.items[j].count < this.items[j].getMaxStackSize() && this.items[j].count < this.getMaxStackSize() && (!this.items[j].usesData() || this.items[j].getData() == p_canHold_1_.getData()) && ItemStack.equals(this.items[j], p_canHold_1_)) {
            i -= (this.items[j].getMaxStackSize() < this.getMaxStackSize()?this.items[j].getMaxStackSize():this.getMaxStackSize()) - this.items[j].count;
         }

         if(i <= 0) {
            return p_canHold_1_.count;
         }
      }

      return p_canHold_1_.count - i;
   }

   public int getFirstEmptySlotIndex() {
      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] == null) {
            return i;
         }
      }

      return -1;
   }

   public int a(Item p_a_1_, int p_a_2_, int p_a_3_, NBTTagCompound p_a_4_) {
      int i = 0;

      for(int j = 0; j < this.items.length; ++j) {
         ItemStack itemstack = this.items[j];
         if(itemstack != null && (p_a_1_ == null || itemstack.getItem() == p_a_1_) && (p_a_2_ <= -1 || itemstack.getData() == p_a_2_) && (p_a_4_ == null || GameProfileSerializer.a(p_a_4_, itemstack.getTag(), true))) {
            int k = p_a_3_ <= 0?itemstack.count:Math.min(p_a_3_ - i, itemstack.count);
            i += k;
            if(p_a_3_ != 0) {
               this.items[j].count -= k;
               if(this.items[j].count == 0) {
                  this.items[j] = null;
               }

               if(p_a_3_ > 0 && i >= p_a_3_) {
                  return i;
               }
            }
         }
      }

      for(int l1 = 0; l1 < this.armor.length; ++l1) {
         ItemStack itemstack1 = this.armor[l1];
         if(itemstack1 != null && (p_a_1_ == null || itemstack1.getItem() == p_a_1_) && (p_a_2_ <= -1 || itemstack1.getData() == p_a_2_) && (p_a_4_ == null || GameProfileSerializer.a(p_a_4_, itemstack1.getTag(), false))) {
            int j1 = p_a_3_ <= 0?itemstack1.count:Math.min(p_a_3_ - i, itemstack1.count);
            i += j1;
            if(p_a_3_ != 0) {
               this.armor[l1].count -= j1;
               if(this.armor[l1].count == 0) {
                  this.armor[l1] = null;
               }

               if(p_a_3_ > 0 && i >= p_a_3_) {
                  return i;
               }
            }
         }
      }

      if(this.f != null) {
         if(p_a_1_ != null && this.f.getItem() != p_a_1_) {
            return i;
         }

         if(p_a_2_ > -1 && this.f.getData() != p_a_2_) {
            return i;
         }

         if(p_a_4_ != null && !GameProfileSerializer.a(p_a_4_, this.f.getTag(), false)) {
            return i;
         }

         int i11 = p_a_3_ <= 0?this.f.count:Math.min(p_a_3_ - i, this.f.count);
         i += i11;
         if(p_a_3_ != 0) {
            this.f.count -= i11;
            if(this.f.count == 0) {
               this.f = null;
            }

            if(p_a_3_ > 0 && i >= p_a_3_) {
               return i;
            }
         }
      }

      return i;
   }

   private int e(ItemStack p_e_1_) {
      Item item = p_e_1_.getItem();
      int i = p_e_1_.count;
      int j = this.firstPartial(p_e_1_);
      if(j < 0) {
         j = this.getFirstEmptySlotIndex();
      }

      if(j < 0) {
         return i;
      } else {
         if(this.items[j] == null) {
            this.items[j] = new ItemStack(item, 0, p_e_1_.getData());
            if(p_e_1_.hasTag()) {
               this.items[j].setTag((NBTTagCompound)p_e_1_.getTag().clone());
            }
         }

         int k = i;
         if(i > this.items[j].getMaxStackSize() - this.items[j].count) {
            k = this.items[j].getMaxStackSize() - this.items[j].count;
         }

         if(k > this.getMaxStackSize() - this.items[j].count) {
            k = this.getMaxStackSize() - this.items[j].count;
         }

         if(k == 0) {
            return i;
         } else {
            i = i - k;
            this.items[j].count += k;
            this.items[j].c = 5;
            return i;
         }
      }
   }

   public void k() {
      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] != null) {
            this.items[i].a(this.player.world, this.player, i, this.itemInHandIndex == i);
         }
      }

   }

   public boolean a(Item p_a_1_) {
      int i = this.c(p_a_1_);
      if(i < 0) {
         return false;
      } else {
         if(--this.items[i].count <= 0) {
            this.items[i] = null;
         }

         return true;
      }
   }

   public boolean b(Item p_b_1_) {
      int i = this.c(p_b_1_);
      return i >= 0;
   }

   public boolean pickup(final ItemStack p_pickup_1_) {
      if(p_pickup_1_ != null && p_pickup_1_.count != 0 && p_pickup_1_.getItem() != null) {
         try {
            if(p_pickup_1_.g()) {
               int j = this.getFirstEmptySlotIndex();
               if(j >= 0) {
                  this.items[j] = ItemStack.b(p_pickup_1_);
                  this.items[j].c = 5;
                  p_pickup_1_.count = 0;
                  return true;
               } else if(this.player.abilities.canInstantlyBuild) {
                  p_pickup_1_.count = 0;
                  return true;
               } else {
                  return false;
               }
            } else {
               int i;
               while(true) {
                  i = p_pickup_1_.count;
                  p_pickup_1_.count = this.e(p_pickup_1_);
                  if(p_pickup_1_.count <= 0 || p_pickup_1_.count >= i) {
                     break;
                  }
               }

               if(p_pickup_1_.count == i && this.player.abilities.canInstantlyBuild) {
                  p_pickup_1_.count = 0;
                  return true;
               } else {
                  return p_pickup_1_.count < i;
               }
            }
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.a(throwable, "Adding item to inventory");
            CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Item being added");
            crashreportsystemdetails.a((String)"Item ID", Integer.valueOf(Item.getId(p_pickup_1_.getItem())));
            crashreportsystemdetails.a((String)"Item data", Integer.valueOf(p_pickup_1_.getData()));
            crashreportsystemdetails.a("Item name", new Callable() {
               public String a() throws Exception {
                  return p_pickup_1_.getName();
               }

               public Object call() throws Exception {
                  return this.a();
               }
            });
            throw new ReportedException(crashreport);
         }
      } else {
         return false;
      }
   }

   public ItemStack splitStack(int p_splitStack_1_, int p_splitStack_2_) {
      ItemStack[] aitemstack = this.items;
      if(p_splitStack_1_ >= this.items.length) {
         aitemstack = this.armor;
         p_splitStack_1_ -= this.items.length;
      }

      if(aitemstack[p_splitStack_1_] != null) {
         if(aitemstack[p_splitStack_1_].count <= p_splitStack_2_) {
            ItemStack itemstack1 = aitemstack[p_splitStack_1_];
            aitemstack[p_splitStack_1_] = null;
            return itemstack1;
         } else {
            ItemStack itemstack = aitemstack[p_splitStack_1_].cloneAndSubtract(p_splitStack_2_);
            if(aitemstack[p_splitStack_1_].count == 0) {
               aitemstack[p_splitStack_1_] = null;
            }

            return itemstack;
         }
      } else {
         return null;
      }
   }

   public ItemStack splitWithoutUpdate(int p_splitWithoutUpdate_1_) {
      ItemStack[] aitemstack = this.items;
      if(p_splitWithoutUpdate_1_ >= this.items.length) {
         aitemstack = this.armor;
         p_splitWithoutUpdate_1_ -= this.items.length;
      }

      if(aitemstack[p_splitWithoutUpdate_1_] != null) {
         ItemStack itemstack = aitemstack[p_splitWithoutUpdate_1_];
         aitemstack[p_splitWithoutUpdate_1_] = null;
         return itemstack;
      } else {
         return null;
      }
   }

   public void setItem(int p_setItem_1_, ItemStack p_setItem_2_) {
      ItemStack[] aitemstack = this.items;
      if(p_setItem_1_ >= aitemstack.length) {
         p_setItem_1_ -= aitemstack.length;
         aitemstack = this.armor;
      }

      aitemstack[p_setItem_1_] = p_setItem_2_;
   }

   public float a(Block p_a_1_) {
      float f = 1.0F;
      if(this.items[this.itemInHandIndex] != null) {
         f *= this.items[this.itemInHandIndex].a(p_a_1_);
      }

      return f;
   }

   public NBTTagList a(NBTTagList p_a_1_) {
      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] != null) {
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Slot", (byte)i);
            this.items[i].save(nbttagcompound);
            p_a_1_.add(nbttagcompound);
         }
      }

      for(int j = 0; j < this.armor.length; ++j) {
         if(this.armor[j] != null) {
            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setByte("Slot", (byte)(j + 100));
            this.armor[j].save(nbttagcompound1);
            p_a_1_.add(nbttagcompound1);
         }
      }

      return p_a_1_;
   }

   public void b(NBTTagList p_b_1_) {
      this.items = new ItemStack[36];
      this.armor = new ItemStack[4];

      for(int i = 0; i < p_b_1_.size(); ++i) {
         NBTTagCompound nbttagcompound = p_b_1_.get(i);
         int j = nbttagcompound.getByte("Slot") & 255;
         ItemStack itemstack = ItemStack.createStack(nbttagcompound);
         if(itemstack != null) {
            if(j >= 0 && j < this.items.length) {
               this.items[j] = itemstack;
            }

            if(j >= 100 && j < this.armor.length + 100) {
               this.armor[j - 100] = itemstack;
            }
         }
      }

   }

   public int getSize() {
      return this.items.length + 4;
   }

   public ItemStack getItem(int p_getItem_1_) {
      ItemStack[] aitemstack = this.items;
      if(p_getItem_1_ >= aitemstack.length) {
         p_getItem_1_ -= aitemstack.length;
         aitemstack = this.armor;
      }

      return aitemstack[p_getItem_1_];
   }

   public String getName() {
      return "container.inventory";
   }

   public boolean hasCustomName() {
      return false;
   }

   public IChatBaseComponent getScoreboardDisplayName() {
      return (IChatBaseComponent)(this.hasCustomName()?new ChatComponentText(this.getName()):new ChatMessage(this.getName(), new Object[0]));
   }

   public int getMaxStackSize() {
      return this.maxStack;
   }

   public boolean b(Block p_b_1_) {
      if(p_b_1_.getMaterial().isAlwaysDestroyable()) {
         return true;
      } else {
         ItemStack itemstack = this.getItem(this.itemInHandIndex);
         return itemstack != null?itemstack.b(p_b_1_):false;
      }
   }

   public ItemStack e(int p_e_1_) {
      return this.armor[p_e_1_];
   }

   public int m() {
      int i = 0;

      for(int j = 0; j < this.armor.length; ++j) {
         if(this.armor[j] != null && this.armor[j].getItem() instanceof ItemArmor) {
            int k = ((ItemArmor)this.armor[j].getItem()).c;
            i += k;
         }
      }

      return i;
   }

   public void a(float p_a_1_) {
      p_a_1_ = p_a_1_ / 4.0F;
      if(p_a_1_ < 1.0F) {
         p_a_1_ = 1.0F;
      }

      for(int i = 0; i < this.armor.length; ++i) {
         if(this.armor[i] != null && this.armor[i].getItem() instanceof ItemArmor) {
            this.armor[i].damage((int)p_a_1_, this.player);
            if(this.armor[i].count == 0) {
               this.armor[i] = null;
            }
         }
      }

   }

   public void n() {
      for(int i = 0; i < this.items.length; ++i) {
         if(this.items[i] != null) {
            this.player.a(this.items[i], true, false);
            this.items[i] = null;
         }
      }

      for(int j = 0; j < this.armor.length; ++j) {
         if(this.armor[j] != null) {
            this.player.a(this.armor[j], true, false);
            this.armor[j] = null;
         }
      }

   }

   public void update() {
      this.e = true;
   }

   public void setCarried(ItemStack p_setCarried_1_) {
      this.f = p_setCarried_1_;
   }

   public ItemStack getCarried() {
      if(this.f != null && this.f.count == 0) {
         this.setCarried((ItemStack)null);
      }

      return this.f;
   }

   public boolean a(EntityHuman p_a_1_) {
      return this.player.dead?false:p_a_1_.h(this.player) <= 64.0D;
   }

   public boolean c(ItemStack p_c_1_) {
      for(int i = 0; i < this.armor.length; ++i) {
         if(this.armor[i] != null && this.armor[i].doMaterialsMatch(p_c_1_)) {
            return true;
         }
      }

      for(int j = 0; j < this.items.length; ++j) {
         if(this.items[j] != null && this.items[j].doMaterialsMatch(p_c_1_)) {
            return true;
         }
      }

      return false;
   }

   public void startOpen(EntityHuman p_startOpen_1_) {
   }

   public void closeContainer(EntityHuman p_closeContainer_1_) {
   }

   public boolean b(int p_b_1_, ItemStack p_b_2_) {
      return true;
   }

   public void b(PlayerInventory p_b_1_) {
      for(int i = 0; i < this.items.length; ++i) {
         this.items[i] = ItemStack.b(p_b_1_.items[i]);
      }

      for(int j = 0; j < this.armor.length; ++j) {
         this.armor[j] = ItemStack.b(p_b_1_.armor[j]);
      }

      this.itemInHandIndex = p_b_1_.itemInHandIndex;
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

      for(int j = 0; j < this.armor.length; ++j) {
         this.armor[j] = null;
      }

   }
}
