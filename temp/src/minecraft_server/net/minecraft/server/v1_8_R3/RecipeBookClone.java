package net.minecraft.server.v1_8_R3;

import java.util.Arrays;
import net.minecraft.server.v1_8_R3.IRecipe;
import net.minecraft.server.v1_8_R3.InventoryCrafting;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemWrittenBook;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.ShapelessRecipes;
import net.minecraft.server.v1_8_R3.World;

public class RecipeBookClone extends ShapelessRecipes implements IRecipe {
   public RecipeBookClone() {
      super(new ItemStack(Items.WRITTEN_BOOK, 0, -1), Arrays.asList(new ItemStack[]{new ItemStack(Items.WRITABLE_BOOK, 0, 0)}));
   }

   public boolean a(InventoryCrafting p_a_1_, World p_a_2_) {
      int i = 0;
      ItemStack itemstack = null;

      for(int j = 0; j < p_a_1_.getSize(); ++j) {
         ItemStack itemstack1 = p_a_1_.getItem(j);
         if(itemstack1 != null) {
            if(itemstack1.getItem() == Items.WRITTEN_BOOK) {
               if(itemstack != null) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if(itemstack1.getItem() != Items.WRITABLE_BOOK) {
                  return false;
               }

               ++i;
            }
         }
      }

      if(itemstack != null && i > 0) {
         return true;
      } else {
         return false;
      }
   }

   public ItemStack craftItem(InventoryCrafting p_craftItem_1_) {
      int i = 0;
      ItemStack itemstack = null;

      for(int j = 0; j < p_craftItem_1_.getSize(); ++j) {
         ItemStack itemstack1 = p_craftItem_1_.getItem(j);
         if(itemstack1 != null) {
            if(itemstack1.getItem() == Items.WRITTEN_BOOK) {
               if(itemstack != null) {
                  return null;
               }

               itemstack = itemstack1;
            } else {
               if(itemstack1.getItem() != Items.WRITABLE_BOOK) {
                  return null;
               }

               ++i;
            }
         }
      }

      if(itemstack != null && i >= 1 && ItemWrittenBook.h(itemstack) < 2) {
         ItemStack itemstack2 = new ItemStack(Items.WRITTEN_BOOK, i);
         itemstack2.setTag((NBTTagCompound)itemstack.getTag().clone());
         itemstack2.getTag().setInt("generation", ItemWrittenBook.h(itemstack) + 1);
         if(itemstack.hasName()) {
            itemstack2.c(itemstack.getName());
         }

         return itemstack2;
      } else {
         return null;
      }
   }

   public int a() {
      return 9;
   }

   public ItemStack b() {
      return null;
   }

   public ItemStack[] b(InventoryCrafting p_b_1_) {
      ItemStack[] aitemstack = new ItemStack[p_b_1_.getSize()];

      for(int i = 0; i < aitemstack.length; ++i) {
         ItemStack itemstack = p_b_1_.getItem(i);
         if(itemstack != null && itemstack.getItem() instanceof ItemWrittenBook) {
            aitemstack[i] = itemstack;
            break;
         }
      }

      return aitemstack;
   }
}
