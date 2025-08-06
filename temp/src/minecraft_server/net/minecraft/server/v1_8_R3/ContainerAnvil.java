package net.minecraft.server.v1_8_R3;

import java.util.Iterator;
import java.util.Map;
import net.minecraft.server.v1_8_R3.BlockAnvil;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.Enchantment;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.ICrafting;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.InventoryCraftResult;
import net.minecraft.server.v1_8_R3.InventorySubcontainer;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.Slot;
import net.minecraft.server.v1_8_R3.World;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryAnvil;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerAnvil extends Container {
   private static final Logger f = LogManager.getLogger();
   private IInventory g = new InventoryCraftResult();
   private IInventory h = new InventorySubcontainer("Repair", true, 2) {
      public void update() {
         super.update();
         ContainerAnvil.this.a((IInventory)this);
      }
   };
   private World i;
   private BlockPosition j;
   public int a;
   private int k;
   private String l;
   private final EntityHuman m;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public ContainerAnvil(PlayerInventory p_i318_1_, final World p_i318_2_, final BlockPosition p_i318_3_, EntityHuman p_i318_4_) {
      this.player = p_i318_1_;
      this.j = p_i318_3_;
      this.i = p_i318_2_;
      this.m = p_i318_4_;
      this.a((Slot)(new Slot(this.h, 0, 27, 47)));
      this.a((Slot)(new Slot(this.h, 1, 76, 47)));
      this.a((Slot)(new Slot(this.g, 2, 134, 47) {
         public boolean isAllowed(ItemStack p_isAllowed_1_) {
            return false;
         }

         public boolean isAllowed(EntityHuman p_isAllowed_1_) {
            return (p_isAllowed_1_.abilities.canInstantlyBuild || p_isAllowed_1_.expLevel >= ContainerAnvil.this.a) && ContainerAnvil.this.a > 0 && this.hasItem();
         }

         public void a(EntityHuman p_a_1_, ItemStack p_a_2_) {
            if(!p_a_1_.abilities.canInstantlyBuild) {
               p_a_1_.levelDown(-ContainerAnvil.this.a);
            }

            ContainerAnvil.this.h.setItem(0, (ItemStack)null);
            if(ContainerAnvil.this.k > 0) {
               ItemStack itemstack = ContainerAnvil.this.h.getItem(1);
               if(itemstack != null && itemstack.count > ContainerAnvil.this.k) {
                  itemstack.count -= ContainerAnvil.this.k;
                  ContainerAnvil.this.h.setItem(1, itemstack);
               } else {
                  ContainerAnvil.this.h.setItem(1, (ItemStack)null);
               }
            } else {
               ContainerAnvil.this.h.setItem(1, (ItemStack)null);
            }

            ContainerAnvil.this.a = 0;
            IBlockData iblockdata = p_i318_2_.getType(p_i318_3_);
            if(!p_a_1_.abilities.canInstantlyBuild && !p_i318_2_.isClientSide && iblockdata.getBlock() == Blocks.ANVIL && p_a_1_.bc().nextFloat() < 0.12F) {
               int l = ((Integer)iblockdata.get(BlockAnvil.DAMAGE)).intValue();
               ++l;
               if(l > 2) {
                  p_i318_2_.setAir(p_i318_3_);
                  p_i318_2_.triggerEffect(1020, p_i318_3_, 0);
               } else {
                  p_i318_2_.setTypeAndData(p_i318_3_, iblockdata.set(BlockAnvil.DAMAGE, Integer.valueOf(l)), 2);
                  p_i318_2_.triggerEffect(1021, p_i318_3_, 0);
               }
            } else if(!p_i318_2_.isClientSide) {
               p_i318_2_.triggerEffect(1021, p_i318_3_, 0);
            }

         }
      }));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.a((Slot)(new Slot(p_i318_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18)));
         }
      }

      for(int kx = 0; kx < 9; ++kx) {
         this.a((Slot)(new Slot(p_i318_1_, kx, 8 + kx * 18, 142)));
      }

   }

   public void a(IInventory p_a_1_) {
      super.a(p_a_1_);
      if(p_a_1_ == this.h) {
         this.e();
      }

   }

   public void e() {
      ItemStack itemstack = this.h.getItem(0);
      this.a = 1;
      int i = 0;
      byte b0 = 0;
      byte b1 = 0;
      if(itemstack == null) {
         this.g.setItem(0, (ItemStack)null);
         this.a = 0;
      } else {
         ItemStack itemstack1 = itemstack.cloneItemStack();
         ItemStack itemstack2 = this.h.getItem(1);
         Map map = EnchantmentManager.a(itemstack1);
         boolean flag = false;
         int j = b0 + itemstack.getRepairCost() + (itemstack2 == null?0:itemstack2.getRepairCost());
         this.k = 0;
         if(itemstack2 != null) {
            flag = itemstack2.getItem() == Items.ENCHANTED_BOOK && Items.ENCHANTED_BOOK.h(itemstack2).size() > 0;
            if(itemstack1.e() && itemstack1.getItem().a(itemstack, itemstack2)) {
               int k2 = Math.min(itemstack1.h(), itemstack1.j() / 4);
               if(k2 <= 0) {
                  this.g.setItem(0, (ItemStack)null);
                  this.a = 0;
                  return;
               }

               int i3;
               for(i3 = 0; k2 > 0 && i3 < itemstack2.count; ++i3) {
                  int k3 = itemstack1.h() - k2;
                  itemstack1.setData(k3);
                  ++i;
                  k2 = Math.min(itemstack1.h(), itemstack1.j() / 4);
               }

               this.k = i3;
            } else {
               if(!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.e())) {
                  this.g.setItem(0, (ItemStack)null);
                  this.a = 0;
                  return;
               }

               if(itemstack1.e() && !flag) {
                  int k = itemstack.j() - itemstack.h();
                  int l = itemstack2.j() - itemstack2.h();
                  int i1 = l + itemstack1.j() * 12 / 100;
                  int j1 = k + i1;
                  int k1 = itemstack1.j() - j1;
                  if(k1 < 0) {
                     k1 = 0;
                  }

                  if(k1 < itemstack1.getData()) {
                     itemstack1.setData(k1);
                     i += 2;
                  }
               }

               Map map1 = EnchantmentManager.a(itemstack2);
               Iterator iterator = map1.keySet().iterator();

               while(iterator.hasNext()) {
                  int j3 = ((Integer)iterator.next()).intValue();
                  Enchantment enchantment = Enchantment.getById(j3);
                  if(enchantment != null) {
                     int l3 = map.containsKey(Integer.valueOf(j3))?((Integer)map.get(Integer.valueOf(j3))).intValue():0;
                     int l1 = ((Integer)map1.get(Integer.valueOf(j3))).intValue();
                     int i2;
                     if(l3 == l1) {
                        ++l1;
                        i2 = l1;
                     } else {
                        i2 = Math.max(l1, l3);
                     }

                     l1 = i2;
                     boolean flag1 = enchantment.canEnchant(itemstack);
                     if(this.m.abilities.canInstantlyBuild || itemstack.getItem() == Items.ENCHANTED_BOOK) {
                        flag1 = true;
                     }

                     Iterator iterator1 = map.keySet().iterator();

                     while(iterator1.hasNext()) {
                        int j2 = ((Integer)iterator1.next()).intValue();
                        if(j2 != j3 && !enchantment.a(Enchantment.getById(j2))) {
                           flag1 = false;
                           ++i;
                        }
                     }

                     if(flag1) {
                        if(i2 > enchantment.getMaxLevel()) {
                           l1 = enchantment.getMaxLevel();
                        }

                        map.put(Integer.valueOf(j3), Integer.valueOf(l1));
                        int i4 = 0;
                        switch(enchantment.getRandomWeight()) {
                        case 1:
                           i4 = 8;
                           break;
                        case 2:
                           i4 = 4;
                        case 3:
                        case 4:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        default:
                           break;
                        case 5:
                           i4 = 2;
                           break;
                        case 10:
                           i4 = 1;
                        }

                        if(flag) {
                           i4 = Math.max(1, i4 / 2);
                        }

                        i += i4 * l1;
                     }
                  }
               }
            }
         }

         if(StringUtils.isBlank(this.l)) {
            if(itemstack.hasName()) {
               b1 = 1;
               i += b1;
               itemstack1.r();
            }
         } else if(!this.l.equals(itemstack.getName())) {
            b1 = 1;
            i += b1;
            itemstack1.c(this.l);
         }

         this.a = j + i;
         if(i <= 0) {
            itemstack1 = null;
         }

         if(b1 == i && b1 > 0 && this.a >= 40) {
            this.a = 39;
         }

         if(this.a >= 40 && !this.m.abilities.canInstantlyBuild) {
            itemstack1 = null;
         }

         if(itemstack1 != null) {
            int l2 = itemstack1.getRepairCost();
            if(itemstack2 != null && l2 < itemstack2.getRepairCost()) {
               l2 = itemstack2.getRepairCost();
            }

            l2 = l2 * 2 + 1;
            itemstack1.setRepairCost(l2);
            EnchantmentManager.a(map, itemstack1);
         }

         this.g.setItem(0, itemstack1);
         this.b();
      }

   }

   public void addSlotListener(ICrafting p_addSlotListener_1_) {
      super.addSlotListener(p_addSlotListener_1_);
      p_addSlotListener_1_.setContainerData(this, 0, this.a);
   }

   public void b(EntityHuman p_b_1_) {
      super.b(p_b_1_);
      if(!this.i.isClientSide) {
         for(int i = 0; i < this.h.getSize(); ++i) {
            ItemStack itemstack = this.h.splitWithoutUpdate(i);
            if(itemstack != null) {
               p_b_1_.drop(itemstack, false);
            }
         }
      }

   }

   public boolean a(EntityHuman p_a_1_) {
      return !this.checkReachable?true:(this.i.getType(this.j).getBlock() != Blocks.ANVIL?false:p_a_1_.e((double)this.j.getX() + 0.5D, (double)this.j.getY() + 0.5D, (double)this.j.getZ() + 0.5D) <= 64.0D);
   }

   public ItemStack b(EntityHuman p_b_1_, int p_b_2_) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.c.get(p_b_2_);
      if(slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.cloneItemStack();
         if(p_b_2_ == 2) {
            if(!this.a(itemstack1, 3, 39, true)) {
               return null;
            }

            slot.a(itemstack1, itemstack);
         } else if(p_b_2_ != 0 && p_b_2_ != 1) {
            if(p_b_2_ >= 3 && p_b_2_ < 39 && !this.a(itemstack1, 0, 2, false)) {
               return null;
            }
         } else if(!this.a(itemstack1, 3, 39, false)) {
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

   public void a(String p_a_1_) {
      this.l = p_a_1_;
      if(this.getSlot(2).hasItem()) {
         ItemStack itemstack = this.getSlot(2).getItem();
         if(StringUtils.isBlank(p_a_1_)) {
            itemstack.r();
         } else {
            itemstack.c(this.l);
         }
      }

      this.e();
   }

   public CraftInventoryView getBukkitView() {
      if(this.bukkitEntity != null) {
         return this.bukkitEntity;
      } else {
         CraftInventory craftinventory = new CraftInventoryAnvil(this.h, this.g);
         this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventory, this);
         return this.bukkitEntity;
      }
   }
}
