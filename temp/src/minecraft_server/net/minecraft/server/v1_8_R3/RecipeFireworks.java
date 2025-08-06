package net.minecraft.server.v1_8_R3;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.server.v1_8_R3.IRecipe;
import net.minecraft.server.v1_8_R3.InventoryCrafting;
import net.minecraft.server.v1_8_R3.ItemDye;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.ShapelessRecipes;
import net.minecraft.server.v1_8_R3.World;

public class RecipeFireworks extends ShapelessRecipes implements IRecipe {
   private ItemStack a;

   public RecipeFireworks() {
      super(new ItemStack(Items.FIREWORKS, 0, 0), Arrays.asList(new ItemStack[]{new ItemStack(Items.GUNPOWDER, 0, 5)}));
   }

   public boolean a(InventoryCrafting p_a_1_, World p_a_2_) {
      this.a = null;
      int i = 0;
      int j = 0;
      int k = 0;
      int l = 0;
      int i1 = 0;
      int j1 = 0;

      for(int k1 = 0; k1 < p_a_1_.getSize(); ++k1) {
         ItemStack itemstack = p_a_1_.getItem(k1);
         if(itemstack != null) {
            if(itemstack.getItem() == Items.GUNPOWDER) {
               ++j;
            } else if(itemstack.getItem() == Items.FIREWORK_CHARGE) {
               ++l;
            } else if(itemstack.getItem() == Items.DYE) {
               ++k;
            } else if(itemstack.getItem() == Items.PAPER) {
               ++i;
            } else if(itemstack.getItem() == Items.GLOWSTONE_DUST) {
               ++i1;
            } else if(itemstack.getItem() == Items.DIAMOND) {
               ++i1;
            } else if(itemstack.getItem() == Items.FIRE_CHARGE) {
               ++j1;
            } else if(itemstack.getItem() == Items.FEATHER) {
               ++j1;
            } else if(itemstack.getItem() == Items.GOLD_NUGGET) {
               ++j1;
            } else {
               if(itemstack.getItem() != Items.SKULL) {
                  return false;
               }

               ++j1;
            }
         }
      }

      i1 = i1 + k + j1;
      if(j <= 3 && i <= 1) {
         if(j >= 1 && i == 1 && i1 == 0) {
            this.a = new ItemStack(Items.FIREWORKS);
            if(l > 0) {
               NBTTagCompound nbttagcompound1 = new NBTTagCompound();
               NBTTagCompound nbttagcompound3 = new NBTTagCompound();
               NBTTagList nbttaglist = new NBTTagList();

               for(int i2 = 0; i2 < p_a_1_.getSize(); ++i2) {
                  ItemStack itemstack3 = p_a_1_.getItem(i2);
                  if(itemstack3 != null && itemstack3.getItem() == Items.FIREWORK_CHARGE && itemstack3.hasTag() && itemstack3.getTag().hasKeyOfType("Explosion", 10)) {
                     nbttaglist.add(itemstack3.getTag().getCompound("Explosion"));
                  }
               }

               nbttagcompound3.set("Explosions", nbttaglist);
               nbttagcompound3.setByte("Flight", (byte)j);
               nbttagcompound1.set("Fireworks", nbttagcompound3);
               this.a.setTag(nbttagcompound1);
            }

            return true;
         } else if(j == 1 && i == 0 && l == 0 && k > 0 && j1 <= 1) {
            this.a = new ItemStack(Items.FIREWORK_CHARGE);
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            NBTTagCompound nbttagcompound2 = new NBTTagCompound();
            byte b0 = 0;
            ArrayList arraylist1 = Lists.newArrayList();

            for(int k2 = 0; k2 < p_a_1_.getSize(); ++k2) {
               ItemStack itemstack2 = p_a_1_.getItem(k2);
               if(itemstack2 != null) {
                  if(itemstack2.getItem() == Items.DYE) {
                     arraylist1.add(Integer.valueOf(ItemDye.a[itemstack2.getData() & 15]));
                  } else if(itemstack2.getItem() == Items.GLOWSTONE_DUST) {
                     nbttagcompound2.setBoolean("Flicker", true);
                  } else if(itemstack2.getItem() == Items.DIAMOND) {
                     nbttagcompound2.setBoolean("Trail", true);
                  } else if(itemstack2.getItem() == Items.FIRE_CHARGE) {
                     b0 = 1;
                  } else if(itemstack2.getItem() == Items.FEATHER) {
                     b0 = 4;
                  } else if(itemstack2.getItem() == Items.GOLD_NUGGET) {
                     b0 = 2;
                  } else if(itemstack2.getItem() == Items.SKULL) {
                     b0 = 3;
                  }
               }
            }

            int[] aint1 = new int[arraylist1.size()];

            for(int l2 = 0; l2 < aint1.length; ++l2) {
               aint1[l2] = ((Integer)arraylist1.get(l2)).intValue();
            }

            nbttagcompound2.setIntArray("Colors", aint1);
            nbttagcompound2.setByte("Type", b0);
            nbttagcompound.set("Explosion", nbttagcompound2);
            this.a.setTag(nbttagcompound);
            return true;
         } else if(j == 0 && i == 0 && l == 1 && k > 0 && k == i1) {
            ArrayList arraylist = Lists.newArrayList();

            for(int l1 = 0; l1 < p_a_1_.getSize(); ++l1) {
               ItemStack itemstack1 = p_a_1_.getItem(l1);
               if(itemstack1 != null) {
                  if(itemstack1.getItem() == Items.DYE) {
                     arraylist.add(Integer.valueOf(ItemDye.a[itemstack1.getData() & 15]));
                  } else if(itemstack1.getItem() == Items.FIREWORK_CHARGE) {
                     this.a = itemstack1.cloneItemStack();
                     this.a.count = 1;
                  }
               }
            }

            int[] aint = new int[arraylist.size()];

            for(int j2 = 0; j2 < aint.length; ++j2) {
               aint[j2] = ((Integer)arraylist.get(j2)).intValue();
            }

            if(this.a != null && this.a.hasTag()) {
               NBTTagCompound nbttagcompound4 = this.a.getTag().getCompound("Explosion");
               if(nbttagcompound4 == null) {
                  return false;
               } else {
                  nbttagcompound4.setIntArray("FadeColors", aint);
                  return true;
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public ItemStack craftItem(InventoryCrafting p_craftItem_1_) {
      return this.a.cloneItemStack();
   }

   public int a() {
      return 10;
   }

   public ItemStack b() {
      return this.a;
   }

   public ItemStack[] b(InventoryCrafting p_b_1_) {
      ItemStack[] aitemstack = new ItemStack[p_b_1_.getSize()];

      for(int i = 0; i < aitemstack.length; ++i) {
         ItemStack itemstack = p_b_1_.getItem(i);
         if(itemstack != null && itemstack.getItem().r()) {
            aitemstack[i] = new ItemStack(itemstack.getItem().q());
         }
      }

      return aitemstack;
   }
}
