package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicates;
import java.util.List;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.BlockDispenser;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.DispenseBehaviorItem;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.IDispenseBehavior;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.ISourceBlock;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.util.Vector;

public class ItemArmor extends Item {
   private static final int[] k = new int[]{11, 16, 15, 13};
   public static final String[] a = new String[]{"minecraft:items/empty_armor_slot_helmet", "minecraft:items/empty_armor_slot_chestplate", "minecraft:items/empty_armor_slot_leggings", "minecraft:items/empty_armor_slot_boots"};
   private static final IDispenseBehavior l = new DispenseBehaviorItem() {
      protected ItemStack b(ISourceBlock p_b_1_, ItemStack p_b_2_) {
         BlockPosition blockposition = p_b_1_.getBlockPosition().shift(BlockDispenser.b(p_b_1_.f()));
         int i = blockposition.getX();
         int j = blockposition.getY();
         int k = blockposition.getZ();
         AxisAlignedBB axisalignedbb = new AxisAlignedBB((double)i, (double)j, (double)k, (double)(i + 1), (double)(j + 1), (double)(k + 1));
         List list = p_b_1_.getWorld().a(EntityLiving.class, axisalignedbb, Predicates.and(IEntitySelector.d, new IEntitySelector.EntitySelectorEquipable(p_b_2_)));
         if(list.size() > 0) {
            EntityLiving entityliving = (EntityLiving)list.get(0);
            int l = entityliving instanceof EntityHuman?1:0;
            int i1 = EntityInsentient.c(p_b_2_);
            ItemStack itemstack = p_b_2_.cloneAndSubtract(1);
            World world = p_b_1_.getWorld();
            org.bukkit.block.Block block = world.getWorld().getBlockAt(p_b_1_.getBlockPosition().getX(), p_b_1_.getBlockPosition().getY(), p_b_1_.getBlockPosition().getZ());
            CraftItemStack craftitemstack = CraftItemStack.asCraftMirror(itemstack);
            BlockDispenseEvent blockdispenseevent = new BlockDispenseEvent(block, craftitemstack.clone(), new Vector(0, 0, 0));
            if(!BlockDispenser.eventFired) {
               world.getServer().getPluginManager().callEvent(blockdispenseevent);
            }

            if(blockdispenseevent.isCancelled()) {
               ++p_b_2_.count;
               return p_b_2_;
            } else {
               if(!blockdispenseevent.getItem().equals(craftitemstack)) {
                  ++p_b_2_.count;
                  ItemStack itemstack1 = CraftItemStack.asNMSCopy(blockdispenseevent.getItem());
                  IDispenseBehavior idispensebehavior = (IDispenseBehavior)BlockDispenser.REGISTRY.get(itemstack1.getItem());
                  if(idispensebehavior != IDispenseBehavior.NONE && idispensebehavior != this) {
                     idispensebehavior.a(p_b_1_, itemstack1);
                     return p_b_2_;
                  }
               }

               itemstack.count = 1;
               entityliving.setEquipment(i1 - l, itemstack);
               if(entityliving instanceof EntityInsentient) {
                  ((EntityInsentient)entityliving).a(i1, 2.0F);
               }

               return p_b_2_;
            }
         } else {
            return super.b(p_b_1_, p_b_2_);
         }
      }
   };
   public final int b;
   public final int c;
   public final int d;
   private final ItemArmor.EnumArmorMaterial m;

   public ItemArmor(ItemArmor.EnumArmorMaterial p_i378_1_, int p_i378_2_, int p_i378_3_) {
      this.m = p_i378_1_;
      this.b = p_i378_3_;
      this.d = p_i378_2_;
      this.c = p_i378_1_.b(p_i378_3_);
      this.setMaxDurability(p_i378_1_.a(p_i378_3_));
      this.maxStackSize = 1;
      this.a(CreativeModeTab.j);
      BlockDispenser.REGISTRY.a(this, l);
   }

   public int b() {
      return this.m.a();
   }

   public ItemArmor.EnumArmorMaterial x_() {
      return this.m;
   }

   public boolean d_(ItemStack p_d__1_) {
      return this.m != ItemArmor.EnumArmorMaterial.LEATHER?false:(!p_d__1_.hasTag()?false:(!p_d__1_.getTag().hasKeyOfType("display", 10)?false:p_d__1_.getTag().getCompound("display").hasKeyOfType("color", 3)));
   }

   public int b(ItemStack p_b_1_) {
      if(this.m != ItemArmor.EnumArmorMaterial.LEATHER) {
         return -1;
      } else {
         NBTTagCompound nbttagcompound = p_b_1_.getTag();
         if(nbttagcompound != null) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("display");
            if(nbttagcompound1 != null && nbttagcompound1.hasKeyOfType("color", 3)) {
               return nbttagcompound1.getInt("color");
            }
         }

         return 10511680;
      }
   }

   public void c(ItemStack p_c_1_) {
      if(this.m == ItemArmor.EnumArmorMaterial.LEATHER) {
         NBTTagCompound nbttagcompound = p_c_1_.getTag();
         if(nbttagcompound != null) {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("display");
            if(nbttagcompound1.hasKey("color")) {
               nbttagcompound1.remove("color");
            }
         }
      }

   }

   public void b(ItemStack p_b_1_, int p_b_2_) {
      if(this.m != ItemArmor.EnumArmorMaterial.LEATHER) {
         throw new UnsupportedOperationException("Can\'t dye non-leather!");
      } else {
         NBTTagCompound nbttagcompound = p_b_1_.getTag();
         if(nbttagcompound == null) {
            nbttagcompound = new NBTTagCompound();
            p_b_1_.setTag(nbttagcompound);
         }

         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompound("display");
         if(!nbttagcompound.hasKeyOfType("display", 10)) {
            nbttagcompound.set("display", nbttagcompound1);
         }

         nbttagcompound1.setInt("color", p_b_2_);
      }
   }

   public boolean a(ItemStack p_a_1_, ItemStack p_a_2_) {
      return this.m.b() == p_a_2_.getItem()?true:super.a(p_a_1_, p_a_2_);
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      int i = EntityInsentient.c(p_a_1_) - 1;
      ItemStack itemstack = p_a_3_.q(i);
      if(itemstack == null) {
         p_a_3_.setEquipment(i, p_a_1_.cloneItemStack());
         p_a_1_.count = 0;
      }

      return p_a_1_;
   }

   public static enum EnumArmorMaterial {
      LEATHER("leather", 5, new int[]{1, 3, 2, 1}, 15),
      CHAIN("chainmail", 15, new int[]{2, 5, 4, 1}, 12),
      IRON("iron", 15, new int[]{2, 6, 5, 2}, 9),
      GOLD("gold", 7, new int[]{2, 5, 3, 1}, 25),
      DIAMOND("diamond", 33, new int[]{3, 8, 6, 3}, 10);

      private final String f;
      private final int g;
      private final int[] h;
      private final int i;

      private EnumArmorMaterial(String p_i263_3_, int p_i263_4_, int[] p_i263_5_, int p_i263_6_) {
         this.f = p_i263_3_;
         this.g = p_i263_4_;
         this.h = p_i263_5_;
         this.i = p_i263_6_;
      }

      public int a(int p_a_1_) {
         return ItemArmor.k[p_a_1_] * this.g;
      }

      public int b(int p_b_1_) {
         return this.h[p_b_1_];
      }

      public int a() {
         return this.i;
      }

      public Item b() {
         return this == LEATHER?Items.LEATHER:(this == CHAIN?Items.IRON_INGOT:(this == GOLD?Items.GOLD_INGOT:(this == IRON?Items.IRON_INGOT:(this == DIAMOND?Items.DIAMOND:null))));
      }
   }
}
