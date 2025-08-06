package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.EnchantmentManager;
import net.minecraft.server.v1_8_R3.EnumItemRarity;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.StructurePieceTreasure;
import net.minecraft.server.v1_8_R3.WeightedRandomEnchant;

public class ItemEnchantedBook extends Item {
   public boolean f_(ItemStack p_f__1_) {
      return false;
   }

   public EnumItemRarity g(ItemStack p_g_1_) {
      return this.h(p_g_1_).size() > 0?EnumItemRarity.UNCOMMON:super.g(p_g_1_);
   }

   public NBTTagList h(ItemStack p_h_1_) {
      NBTTagCompound nbttagcompound = p_h_1_.getTag();
      return nbttagcompound != null && nbttagcompound.hasKeyOfType("StoredEnchantments", 9)?(NBTTagList)nbttagcompound.get("StoredEnchantments"):new NBTTagList();
   }

   public void a(ItemStack p_a_1_, WeightedRandomEnchant p_a_2_) {
      NBTTagList nbttaglist = this.h(p_a_1_);
      boolean flag = true;

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.get(i);
         if(nbttagcompound.getShort("id") == p_a_2_.enchantment.id) {
            if(nbttagcompound.getShort("lvl") < p_a_2_.level) {
               nbttagcompound.setShort("lvl", (short)p_a_2_.level);
            }

            flag = false;
            break;
         }
      }

      if(flag) {
         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
         nbttagcompound1.setShort("id", (short)p_a_2_.enchantment.id);
         nbttagcompound1.setShort("lvl", (short)p_a_2_.level);
         nbttaglist.add(nbttagcompound1);
      }

      if(!p_a_1_.hasTag()) {
         p_a_1_.setTag(new NBTTagCompound());
      }

      p_a_1_.getTag().set("StoredEnchantments", nbttaglist);
   }

   public ItemStack a(WeightedRandomEnchant p_a_1_) {
      ItemStack itemstack = new ItemStack(this);
      this.a(itemstack, p_a_1_);
      return itemstack;
   }

   public StructurePieceTreasure b(Random p_b_1_) {
      return this.a(p_b_1_, 1, 1, 1);
   }

   public StructurePieceTreasure a(Random p_a_1_, int p_a_2_, int p_a_3_, int p_a_4_) {
      ItemStack itemstack = new ItemStack(Items.BOOK, 1, 0);
      EnchantmentManager.a(p_a_1_, itemstack, 30);
      return new StructurePieceTreasure(itemstack, p_a_2_, p_a_3_, p_a_4_);
   }
}
