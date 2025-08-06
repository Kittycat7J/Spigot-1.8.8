package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.AchievementList;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.ICrafting;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.Slot;
import net.minecraft.server.v1_8_R3.Statistic;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryBrewer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryView;

public class ContainerBrewingStand extends Container {
   private IInventory brewingStand;
   private final Slot f;
   private int g;
   private CraftInventoryView bukkitEntity = null;
   private PlayerInventory player;

   public ContainerBrewingStand(PlayerInventory p_i121_1_, IInventory p_i121_2_) {
      this.player = p_i121_1_;
      this.brewingStand = p_i121_2_;
      this.a(new ContainerBrewingStand.SlotPotionBottle(p_i121_1_.player, p_i121_2_, 0, 56, 46));
      this.a(new ContainerBrewingStand.SlotPotionBottle(p_i121_1_.player, p_i121_2_, 1, 79, 53));
      this.a(new ContainerBrewingStand.SlotPotionBottle(p_i121_1_.player, p_i121_2_, 2, 102, 46));
      this.f = this.a(new ContainerBrewingStand.SlotBrewing(p_i121_2_, 3, 79, 17));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.a(new Slot(p_i121_1_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.a(new Slot(p_i121_1_, k, 8 + k * 18, 142));
      }

   }

   public void addSlotListener(ICrafting p_addSlotListener_1_) {
      super.addSlotListener(p_addSlotListener_1_);
      p_addSlotListener_1_.setContainerData(this, this.brewingStand);
   }

   public void b() {
      super.b();

      for(int i = 0; i < this.listeners.size(); ++i) {
         ICrafting icrafting = (ICrafting)this.listeners.get(i);
         if(this.g != this.brewingStand.getProperty(0)) {
            icrafting.setContainerData(this, 0, this.brewingStand.getProperty(0));
         }
      }

      this.g = this.brewingStand.getProperty(0);
   }

   public boolean a(EntityHuman p_a_1_) {
      return !this.checkReachable?true:this.brewingStand.a(p_a_1_);
   }

   public ItemStack b(EntityHuman p_b_1_, int p_b_2_) {
      ItemStack itemstack = null;
      Slot slot = (Slot)this.c.get(p_b_2_);
      if(slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.cloneItemStack();
         if((p_b_2_ < 0 || p_b_2_ > 2) && p_b_2_ != 3) {
            if(!this.f.hasItem() && this.f.isAllowed(itemstack1)) {
               if(!this.a(itemstack1, 3, 4, false)) {
                  return null;
               }
            } else if(ContainerBrewingStand.SlotPotionBottle.b_(itemstack)) {
               if(!this.a(itemstack1, 0, 3, false)) {
                  return null;
               }
            } else if(p_b_2_ >= 4 && p_b_2_ < 31) {
               if(!this.a(itemstack1, 31, 40, false)) {
                  return null;
               }
            } else if(p_b_2_ >= 31 && p_b_2_ < 40) {
               if(!this.a(itemstack1, 4, 31, false)) {
                  return null;
               }
            } else if(!this.a(itemstack1, 4, 40, false)) {
               return null;
            }
         } else {
            if(!this.a(itemstack1, 4, 40, true)) {
               return null;
            }

            slot.a(itemstack1, itemstack);
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
         CraftInventoryBrewer craftinventorybrewer = new CraftInventoryBrewer(this.brewingStand);
         this.bukkitEntity = new CraftInventoryView(this.player.player.getBukkitEntity(), craftinventorybrewer, this);
         return this.bukkitEntity;
      }
   }

   class SlotBrewing extends Slot {
      public SlotBrewing(IInventory p_i447_2_, int p_i447_3_, int p_i447_4_, int p_i447_5_) {
         super(p_i447_2_, p_i447_3_, p_i447_4_, p_i447_5_);
      }

      public boolean isAllowed(ItemStack p_isAllowed_1_) {
         return p_isAllowed_1_ != null?p_isAllowed_1_.getItem().l(p_isAllowed_1_):false;
      }

      public int getMaxStackSize() {
         return 64;
      }
   }

   static class SlotPotionBottle extends Slot {
      private EntityHuman a;

      public SlotPotionBottle(EntityHuman p_i166_1_, IInventory p_i166_2_, int p_i166_3_, int p_i166_4_, int p_i166_5_) {
         super(p_i166_2_, p_i166_3_, p_i166_4_, p_i166_5_);
         this.a = p_i166_1_;
      }

      public boolean isAllowed(ItemStack p_isAllowed_1_) {
         return b_(p_isAllowed_1_);
      }

      public int getMaxStackSize() {
         return 1;
      }

      public void a(EntityHuman p_a_1_, ItemStack p_a_2_) {
         if(p_a_2_.getItem() == Items.POTION && p_a_2_.getData() > 0) {
            this.a.b((Statistic)AchievementList.B);
         }

         super.a(p_a_1_, p_a_2_);
      }

      public static boolean b_(ItemStack p_b__0_) {
         return p_b__0_ != null && (p_b__0_.getItem() == Items.POTION || p_b__0_.getItem() == Items.GLASS_BOTTLE);
      }
   }
}
