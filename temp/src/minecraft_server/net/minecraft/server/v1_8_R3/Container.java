package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.ICrafting;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutSetSlot;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.Slot;
import net.minecraft.server.v1_8_R3.TileEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryView;

public abstract class Container {
   public List<ItemStack> b = Lists.<ItemStack>newArrayList();
   public List<Slot> c = Lists.<Slot>newArrayList();
   public int windowId;
   private int dragType = -1;
   private int g;
   private final Set<Slot> h = Sets.<Slot>newHashSet();
   protected List<ICrafting> listeners = Lists.<ICrafting>newArrayList();
   private Set<EntityHuman> i = Sets.<EntityHuman>newHashSet();
   private int tickCount;
   public boolean checkReachable = true;

   public abstract InventoryView getBukkitView();

   public void transferTo(Container p_transferTo_1_, CraftHumanEntity p_transferTo_2_) {
      InventoryView inventoryview = this.getBukkitView();
      InventoryView inventoryview1 = p_transferTo_1_.getBukkitView();
      ((CraftInventory)inventoryview.getTopInventory()).getInventory().onClose(p_transferTo_2_);
      ((CraftInventory)inventoryview.getBottomInventory()).getInventory().onClose(p_transferTo_2_);
      ((CraftInventory)inventoryview1.getTopInventory()).getInventory().onOpen(p_transferTo_2_);
      ((CraftInventory)inventoryview1.getBottomInventory()).getInventory().onOpen(p_transferTo_2_);
   }

   protected Slot a(Slot p_a_1_) {
      p_a_1_.rawSlotIndex = this.c.size();
      this.c.add(p_a_1_);
      this.b.add((Object)null);
      return p_a_1_;
   }

   public void addSlotListener(ICrafting p_addSlotListener_1_) {
      if(this.listeners.contains(p_addSlotListener_1_)) {
         throw new IllegalArgumentException("Listener already listening");
      } else {
         this.listeners.add(p_addSlotListener_1_);
         p_addSlotListener_1_.a(this, this.a());
         this.b();
      }
   }

   public List<ItemStack> a() {
      ArrayList arraylist = Lists.newArrayList();

      for(int i = 0; i < this.c.size(); ++i) {
         arraylist.add(((Slot)this.c.get(i)).getItem());
      }

      return arraylist;
   }

   public void b() {
      for(int i = 0; i < this.c.size(); ++i) {
         ItemStack itemstack = ((Slot)this.c.get(i)).getItem();
         ItemStack itemstack1 = (ItemStack)this.b.get(i);
         if(!ItemStack.fastMatches(itemstack1, itemstack) || this.tickCount % 20 == 0 && !ItemStack.matches(itemstack1, itemstack)) {
            itemstack1 = itemstack == null?null:itemstack.cloneItemStack();
            this.b.set(i, itemstack1);

            for(int j = 0; j < this.listeners.size(); ++j) {
               ((ICrafting)this.listeners.get(j)).a(this, i, itemstack1);
            }
         }
      }

      ++this.tickCount;
   }

   public boolean a(EntityHuman p_a_1_, int p_a_2_) {
      return false;
   }

   public Slot getSlot(IInventory p_getSlot_1_, int p_getSlot_2_) {
      for(int i = 0; i < this.c.size(); ++i) {
         Slot slot = (Slot)this.c.get(i);
         if(slot.a(p_getSlot_1_, p_getSlot_2_)) {
            return slot;
         }
      }

      return null;
   }

   public Slot getSlot(int p_getSlot_1_) {
      return (Slot)this.c.get(p_getSlot_1_);
   }

   public ItemStack b(EntityHuman p_b_1_, int p_b_2_) {
      Slot slot = (Slot)this.c.get(p_b_2_);
      return slot != null?slot.getItem():null;
   }

   public ItemStack clickItem(int p_clickItem_1_, int p_clickItem_2_, int p_clickItem_3_, EntityHuman p_clickItem_4_) {
      ItemStack itemstack = null;
      PlayerInventory playerinventory = p_clickItem_4_.inventory;
      if(p_clickItem_3_ == 5) {
         int i = this.g;
         this.g = c(p_clickItem_2_);
         if((i != 1 || this.g != 2) && i != this.g) {
            this.d();
         } else if(playerinventory.getCarried() == null) {
            this.d();
         } else if(this.g == 0) {
            this.dragType = b(p_clickItem_2_);
            if(a(this.dragType, p_clickItem_4_)) {
               this.g = 1;
               this.h.clear();
            } else {
               this.d();
            }
         } else if(this.g == 1) {
            Slot slot = (Slot)this.c.get(p_clickItem_1_);
            if(slot != null && a(slot, playerinventory.getCarried(), true) && slot.isAllowed(playerinventory.getCarried()) && playerinventory.getCarried().count > this.h.size() && this.b(slot)) {
               this.h.add(slot);
            }
         } else if(this.g == 2) {
            if(!this.h.isEmpty()) {
               ItemStack itemstack1 = playerinventory.getCarried().cloneItemStack();
               int j = playerinventory.getCarried().count;
               Iterator iterator = this.h.iterator();
               Map<Integer, ItemStack> map = new HashMap();

               while(iterator.hasNext()) {
                  Slot slot1 = (Slot)iterator.next();
                  if(slot1 != null && a(slot1, playerinventory.getCarried(), true) && slot1.isAllowed(playerinventory.getCarried()) && playerinventory.getCarried().count >= this.h.size() && this.b(slot1)) {
                     ItemStack itemstack2 = itemstack1.cloneItemStack();
                     int k = slot1.hasItem()?slot1.getItem().count:0;
                     a(this.h, this.dragType, itemstack2, k);
                     if(itemstack2.count > itemstack2.getMaxStackSize()) {
                        itemstack2.count = itemstack2.getMaxStackSize();
                     }

                     if(itemstack2.count > slot1.getMaxStackSize(itemstack2)) {
                        itemstack2.count = slot1.getMaxStackSize(itemstack2);
                     }

                     j -= itemstack2.count - k;
                     map.put(Integer.valueOf(slot1.rawSlotIndex), itemstack2);
                  }
               }

               InventoryView inventoryview = this.getBukkitView();
               org.bukkit.inventory.ItemStack itemstack13 = CraftItemStack.asCraftMirror(itemstack1);
               itemstack13.setAmount(j);
               Map<Integer, org.bukkit.inventory.ItemStack> map1 = new HashMap();

               for(Entry<Integer, ItemStack> entry : map.entrySet()) {
                  map1.put((Integer)entry.getKey(), CraftItemStack.asBukkitCopy((ItemStack)entry.getValue()));
               }

               ItemStack itemstack15 = playerinventory.getCarried();
               playerinventory.setCarried(CraftItemStack.asNMSCopy(itemstack13));
               InventoryDragEvent inventorydragevent = new InventoryDragEvent(inventoryview, itemstack13.getType() != org.bukkit.Material.AIR?itemstack13:null, CraftItemStack.asBukkitCopy(itemstack15), this.dragType == 1, map1);
               p_clickItem_4_.world.getServer().getPluginManager().callEvent(inventorydragevent);
               boolean flag = inventorydragevent.getResult() != Result.DEFAULT;
               if(inventorydragevent.getResult() != Result.DENY) {
                  for(Entry<Integer, ItemStack> entry1 : map.entrySet()) {
                     inventoryview.setItem(((Integer)entry1.getKey()).intValue(), CraftItemStack.asBukkitCopy((ItemStack)entry1.getValue()));
                  }

                  if(playerinventory.getCarried() != null) {
                     playerinventory.setCarried(CraftItemStack.asNMSCopy(inventorydragevent.getCursor()));
                     flag = true;
                  }
               } else {
                  playerinventory.setCarried(itemstack15);
               }

               if(flag && p_clickItem_4_ instanceof EntityPlayer) {
                  ((EntityPlayer)p_clickItem_4_).updateInventory(this);
               }
            }

            this.d();
         } else {
            this.d();
         }
      } else if(this.g != 0) {
         this.d();
      } else if((p_clickItem_3_ == 0 || p_clickItem_3_ == 1) && (p_clickItem_2_ == 0 || p_clickItem_2_ == 1)) {
         if(p_clickItem_1_ == -999) {
            if(playerinventory.getCarried() != null) {
               if(p_clickItem_2_ == 0) {
                  p_clickItem_4_.drop(playerinventory.getCarried(), true);
                  playerinventory.setCarried((ItemStack)null);
               }

               if(p_clickItem_2_ == 1) {
                  ItemStack itemstack11 = playerinventory.getCarried();
                  if(itemstack11.count > 0) {
                     p_clickItem_4_.drop(itemstack11.cloneAndSubtract(1), true);
                  }

                  if(itemstack11.count == 0) {
                     playerinventory.setCarried((ItemStack)null);
                  }
               }
            }
         } else if(p_clickItem_3_ == 1) {
            if(p_clickItem_1_ < 0) {
               return null;
            }

            Slot slot6 = (Slot)this.c.get(p_clickItem_1_);
            if(slot6 != null && slot6.isAllowed(p_clickItem_4_)) {
               ItemStack itemstack7 = this.b(p_clickItem_4_, p_clickItem_1_);
               if(itemstack7 != null) {
                  Item item = itemstack7.getItem();
                  itemstack = itemstack7.cloneItemStack();
                  if(slot6.getItem() != null && slot6.getItem().getItem() == item) {
                     this.a(p_clickItem_1_, p_clickItem_2_, true, p_clickItem_4_);
                  }
               }
            }
         } else {
            if(p_clickItem_1_ < 0) {
               return null;
            }

            Slot slot7 = (Slot)this.c.get(p_clickItem_1_);
            if(slot7 != null) {
               ItemStack itemstack8 = slot7.getItem();
               ItemStack itemstack12 = playerinventory.getCarried();
               if(itemstack8 != null) {
                  itemstack = itemstack8.cloneItemStack();
               }

               if(itemstack8 == null) {
                  if(itemstack12 != null && slot7.isAllowed(itemstack12)) {
                     int i2 = p_clickItem_2_ == 0?itemstack12.count:1;
                     if(i2 > slot7.getMaxStackSize(itemstack12)) {
                        i2 = slot7.getMaxStackSize(itemstack12);
                     }

                     if(itemstack12.count >= i2) {
                        slot7.set(itemstack12.cloneAndSubtract(i2));
                     }

                     if(itemstack12.count == 0) {
                        playerinventory.setCarried((ItemStack)null);
                     } else if(p_clickItem_4_ instanceof EntityPlayer) {
                        ((EntityPlayer)p_clickItem_4_).playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, p_clickItem_4_.inventory.getCarried()));
                     }
                  }
               } else if(slot7.isAllowed(p_clickItem_4_)) {
                  if(itemstack12 == null) {
                     int l1 = p_clickItem_2_ == 0?itemstack8.count:(itemstack8.count + 1) / 2;
                     ItemStack itemstack10 = slot7.a(l1);
                     playerinventory.setCarried(itemstack10);
                     if(itemstack8.count == 0) {
                        slot7.set((ItemStack)null);
                     }

                     slot7.a(p_clickItem_4_, playerinventory.getCarried());
                  } else if(slot7.isAllowed(itemstack12)) {
                     if(itemstack8.getItem() == itemstack12.getItem() && itemstack8.getData() == itemstack12.getData() && ItemStack.equals(itemstack8, itemstack12)) {
                        int k1 = p_clickItem_2_ == 0?itemstack12.count:1;
                        if(k1 > slot7.getMaxStackSize(itemstack12) - itemstack8.count) {
                           k1 = slot7.getMaxStackSize(itemstack12) - itemstack8.count;
                        }

                        if(k1 > itemstack12.getMaxStackSize() - itemstack8.count) {
                           k1 = itemstack12.getMaxStackSize() - itemstack8.count;
                        }

                        itemstack12.cloneAndSubtract(k1);
                        if(itemstack12.count == 0) {
                           playerinventory.setCarried((ItemStack)null);
                        } else if(p_clickItem_4_ instanceof EntityPlayer) {
                           ((EntityPlayer)p_clickItem_4_).playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, p_clickItem_4_.inventory.getCarried()));
                        }

                        itemstack8.count += k1;
                     } else if(itemstack12.count <= slot7.getMaxStackSize(itemstack12)) {
                        slot7.set(itemstack12);
                        playerinventory.setCarried(itemstack8);
                     }
                  } else if(itemstack8.getItem() == itemstack12.getItem() && itemstack12.getMaxStackSize() > 1 && (!itemstack8.usesData() || itemstack8.getData() == itemstack12.getData()) && ItemStack.equals(itemstack8, itemstack12)) {
                     int j1 = itemstack8.count;
                     int i3 = Math.min(itemstack12.getMaxStackSize(), slot7.getMaxStackSize());
                     if(j1 > 0 && j1 + itemstack12.count <= i3) {
                        itemstack12.count += j1;
                        itemstack8 = slot7.a(j1);
                        if(itemstack8.count == 0) {
                           slot7.set((ItemStack)null);
                        }

                        slot7.a(p_clickItem_4_, playerinventory.getCarried());
                     } else if(p_clickItem_4_ instanceof EntityPlayer) {
                        ((EntityPlayer)p_clickItem_4_).playerConnection.sendPacket(new PacketPlayOutSetSlot(-1, -1, p_clickItem_4_.inventory.getCarried()));
                     }
                  }
               }

               slot7.f();
               if(p_clickItem_4_ instanceof EntityPlayer && slot7.getMaxStackSize() != 64) {
                  ((EntityPlayer)p_clickItem_4_).playerConnection.sendPacket(new PacketPlayOutSetSlot(this.windowId, slot7.rawSlotIndex, slot7.getItem()));
                  if(this.getBukkitView().getType() == InventoryType.WORKBENCH || this.getBukkitView().getType() == InventoryType.CRAFTING) {
                     ((EntityPlayer)p_clickItem_4_).playerConnection.sendPacket(new PacketPlayOutSetSlot(this.windowId, 0, this.getSlot(0).getItem()));
                  }
               }
            }
         }
      } else if(p_clickItem_3_ == 2 && p_clickItem_2_ >= 0 && p_clickItem_2_ < 9) {
         Slot slot5 = (Slot)this.c.get(p_clickItem_1_);
         if(slot5.isAllowed(p_clickItem_4_)) {
            ItemStack itemstack6 = playerinventory.getItem(p_clickItem_2_);
            boolean flag1 = itemstack6 == null || slot5.inventory == playerinventory && slot5.isAllowed(itemstack6);
            int i1 = -1;
            if(!flag1) {
               i1 = playerinventory.getFirstEmptySlotIndex();
               flag1 |= i1 > -1;
            }

            if(slot5.hasItem() && flag1) {
               ItemStack itemstack9 = slot5.getItem();
               playerinventory.setItem(p_clickItem_2_, itemstack9.cloneItemStack());
               if((slot5.inventory != playerinventory || !slot5.isAllowed(itemstack6)) && itemstack6 != null) {
                  if(i1 > -1) {
                     playerinventory.pickup(itemstack6);
                     slot5.a(itemstack9.count);
                     slot5.set((ItemStack)null);
                     slot5.a(p_clickItem_4_, itemstack9);
                  }
               } else {
                  slot5.a(itemstack9.count);
                  slot5.set(itemstack6);
                  slot5.a(p_clickItem_4_, itemstack9);
               }
            } else if(!slot5.hasItem() && itemstack6 != null && slot5.isAllowed(itemstack6)) {
               playerinventory.setItem(p_clickItem_2_, (ItemStack)null);
               slot5.set(itemstack6);
            }
         }
      } else if(p_clickItem_3_ == 3 && p_clickItem_4_.abilities.canInstantlyBuild && playerinventory.getCarried() == null && p_clickItem_1_ >= 0) {
         Slot slot4 = (Slot)this.c.get(p_clickItem_1_);
         if(slot4 != null && slot4.hasItem()) {
            ItemStack itemstack5 = slot4.getItem().cloneItemStack();
            itemstack5.count = itemstack5.getMaxStackSize();
            playerinventory.setCarried(itemstack5);
         }
      } else if(p_clickItem_3_ == 4 && playerinventory.getCarried() == null && p_clickItem_1_ >= 0) {
         Slot slot3 = (Slot)this.c.get(p_clickItem_1_);
         if(slot3 != null && slot3.hasItem() && slot3.isAllowed(p_clickItem_4_)) {
            ItemStack itemstack4 = slot3.a(p_clickItem_2_ == 0?1:slot3.getItem().count);
            slot3.a(p_clickItem_4_, itemstack4);
            p_clickItem_4_.drop(itemstack4, true);
         }
      } else if(p_clickItem_3_ == 6 && p_clickItem_1_ >= 0) {
         Slot slot2 = (Slot)this.c.get(p_clickItem_1_);
         ItemStack itemstack3 = playerinventory.getCarried();
         if(itemstack3 != null && (slot2 == null || !slot2.hasItem() || !slot2.isAllowed(p_clickItem_4_))) {
            int j2 = p_clickItem_2_ == 0?0:this.c.size() - 1;
            int l = p_clickItem_2_ == 0?1:-1;

            for(int k2 = 0; k2 < 2; ++k2) {
               for(int l2 = j2; l2 >= 0 && l2 < this.c.size() && itemstack3.count < itemstack3.getMaxStackSize(); l2 += l) {
                  Slot slot8 = (Slot)this.c.get(l2);
                  if(slot8.hasItem() && a(slot8, itemstack3, true) && slot8.isAllowed(p_clickItem_4_) && this.a(itemstack3, slot8) && (k2 != 0 || slot8.getItem().count != slot8.getItem().getMaxStackSize())) {
                     int j3 = Math.min(itemstack3.getMaxStackSize() - itemstack3.count, slot8.getItem().count);
                     ItemStack itemstack14 = slot8.a(j3);
                     itemstack3.count += j3;
                     if(itemstack14.count <= 0) {
                        slot8.set((ItemStack)null);
                     }

                     slot8.a(p_clickItem_4_, itemstack14);
                  }
               }
            }
         }

         this.b();
      }

      return itemstack;
   }

   public boolean a(ItemStack p_a_1_, Slot p_a_2_) {
      return true;
   }

   protected void a(int p_a_1_, int p_a_2_, boolean p_a_3_, EntityHuman p_a_4_) {
      this.clickItem(p_a_1_, p_a_2_, 1, p_a_4_);
   }

   public void b(EntityHuman p_b_1_) {
      PlayerInventory playerinventory = p_b_1_.inventory;
      if(playerinventory.getCarried() != null) {
         p_b_1_.drop(playerinventory.getCarried(), false);
         playerinventory.setCarried((ItemStack)null);
      }

   }

   public void a(IInventory p_a_1_) {
      this.b();
   }

   public void setItem(int p_setItem_1_, ItemStack p_setItem_2_) {
      this.getSlot(p_setItem_1_).set(p_setItem_2_);
   }

   public boolean c(EntityHuman p_c_1_) {
      return !this.i.contains(p_c_1_);
   }

   public void a(EntityHuman p_a_1_, boolean p_a_2_) {
      if(p_a_2_) {
         this.i.remove(p_a_1_);
      } else {
         this.i.add(p_a_1_);
      }

   }

   public abstract boolean a(EntityHuman var1);

   protected boolean a(ItemStack p_a_1_, int p_a_2_, int p_a_3_, boolean p_a_4_) {
      boolean flag = false;
      int i = p_a_2_;
      if(p_a_4_) {
         i = p_a_3_ - 1;
      }

      if(p_a_1_.isStackable()) {
         while(p_a_1_.count > 0 && (!p_a_4_ && i < p_a_3_ || p_a_4_ && i >= p_a_2_)) {
            Slot slot = (Slot)this.c.get(i);
            ItemStack itemstack = slot.getItem();
            if(itemstack != null && itemstack.getItem() == p_a_1_.getItem() && (!p_a_1_.usesData() || p_a_1_.getData() == itemstack.getData()) && ItemStack.equals(p_a_1_, itemstack)) {
               int j = itemstack.count + p_a_1_.count;
               int k = Math.min(p_a_1_.getMaxStackSize(), slot.getMaxStackSize());
               if(j <= k) {
                  p_a_1_.count = 0;
                  itemstack.count = j;
                  slot.f();
                  flag = true;
               } else if(itemstack.count < k) {
                  p_a_1_.count -= k - itemstack.count;
                  itemstack.count = k;
                  slot.f();
                  flag = true;
               }
            }

            if(p_a_4_) {
               --i;
            } else {
               ++i;
            }
         }
      }

      if(p_a_1_.count > 0) {
         if(p_a_4_) {
            i = p_a_3_ - 1;
         } else {
            i = p_a_2_;
         }

         while(!p_a_4_ && i < p_a_3_ || p_a_4_ && i >= p_a_2_) {
            Slot slot1 = (Slot)this.c.get(i);
            ItemStack itemstack1 = slot1.getItem();
            if(itemstack1 == null) {
               slot1.set(p_a_1_.cloneItemStack());
               slot1.f();
               p_a_1_.count = 0;
               flag = true;
               break;
            }

            if(p_a_4_) {
               --i;
            } else {
               ++i;
            }
         }
      }

      return flag;
   }

   public static int b(int p_b_0_) {
      return p_b_0_ >> 2 & 3;
   }

   public static int c(int p_c_0_) {
      return p_c_0_ & 3;
   }

   public static boolean a(int p_a_0_, EntityHuman p_a_1_) {
      return p_a_0_ == 0?true:(p_a_0_ == 1?true:p_a_0_ == 2 && p_a_1_.abilities.canInstantlyBuild);
   }

   protected void d() {
      this.g = 0;
      this.h.clear();
   }

   public static boolean a(Slot p_a_0_, ItemStack p_a_1_, boolean p_a_2_) {
      boolean flag = p_a_0_ == null || !p_a_0_.hasItem();
      if(p_a_0_ != null && p_a_0_.hasItem() && p_a_1_ != null && p_a_1_.doMaterialsMatch(p_a_0_.getItem()) && ItemStack.equals(p_a_0_.getItem(), p_a_1_)) {
         flag |= p_a_0_.getItem().count + (p_a_2_?0:p_a_1_.count) <= p_a_1_.getMaxStackSize();
      }

      return flag;
   }

   public static void a(Set<Slot> p_a_0_, int p_a_1_, ItemStack p_a_2_, int p_a_3_) {
      switch(p_a_1_) {
      case 0:
         p_a_2_.count = MathHelper.d((float)p_a_2_.count / (float)p_a_0_.size());
         break;
      case 1:
         p_a_2_.count = 1;
         break;
      case 2:
         p_a_2_.count = p_a_2_.getItem().getMaxStackSize();
      }

      p_a_2_.count += p_a_3_;
   }

   public boolean b(Slot p_b_1_) {
      return true;
   }

   public static int a(TileEntity p_a_0_) {
      return p_a_0_ instanceof IInventory?b((IInventory)p_a_0_):0;
   }

   public static int b(IInventory p_b_0_) {
      if(p_b_0_ == null) {
         return 0;
      } else {
         int i = 0;
         float f = 0.0F;

         for(int j = 0; j < p_b_0_.getSize(); ++j) {
            ItemStack itemstack = p_b_0_.getItem(j);
            if(itemstack != null) {
               f += (float)itemstack.count / (float)Math.min(p_b_0_.getMaxStackSize(), itemstack.getMaxStackSize());
               ++i;
            }
         }

         f = f / (float)p_b_0_.getSize();
         return MathHelper.d(f * 14.0F) + (i > 0?1:0);
      }
   }
}
