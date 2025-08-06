package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.World;

public class InventoryUtils {
   private static final Random a = new Random();

   public static void dropInventory(World p_dropInventory_0_, BlockPosition p_dropInventory_1_, IInventory p_dropInventory_2_) {
      dropInventory(p_dropInventory_0_, (double)p_dropInventory_1_.getX(), (double)p_dropInventory_1_.getY(), (double)p_dropInventory_1_.getZ(), p_dropInventory_2_);
   }

   public static void dropEntity(World p_dropEntity_0_, Entity p_dropEntity_1_, IInventory p_dropEntity_2_) {
      dropInventory(p_dropEntity_0_, p_dropEntity_1_.locX, p_dropEntity_1_.locY, p_dropEntity_1_.locZ, p_dropEntity_2_);
   }

   private static void dropInventory(World p_dropInventory_0_, double p_dropInventory_1_, double p_dropInventory_3_, double p_dropInventory_5_, IInventory p_dropInventory_7_) {
      for(int i = 0; i < p_dropInventory_7_.getSize(); ++i) {
         ItemStack itemstack = p_dropInventory_7_.getItem(i);
         if(itemstack != null) {
            dropItem(p_dropInventory_0_, p_dropInventory_1_, p_dropInventory_3_, p_dropInventory_5_, itemstack);
         }
      }

   }

   private static void dropItem(World p_dropItem_0_, double p_dropItem_1_, double p_dropItem_3_, double p_dropItem_5_, ItemStack p_dropItem_7_) {
      float f = a.nextFloat() * 0.8F + 0.1F;
      float f1 = a.nextFloat() * 0.8F + 0.1F;
      float f2 = a.nextFloat() * 0.8F + 0.1F;

      while(p_dropItem_7_.count > 0) {
         int i = a.nextInt(21) + 10;
         if(i > p_dropItem_7_.count) {
            i = p_dropItem_7_.count;
         }

         p_dropItem_7_.count -= i;
         EntityItem entityitem = new EntityItem(p_dropItem_0_, p_dropItem_1_ + (double)f, p_dropItem_3_ + (double)f1, p_dropItem_5_ + (double)f2, new ItemStack(p_dropItem_7_.getItem(), i, p_dropItem_7_.getData()));
         if(p_dropItem_7_.hasTag()) {
            entityitem.getItemStack().setTag((NBTTagCompound)p_dropItem_7_.getTag().clone());
         }

         float f3 = 0.05F;
         entityitem.motX = a.nextGaussian() * (double)f3;
         entityitem.motY = a.nextGaussian() * (double)f3 + 0.20000000298023224D;
         entityitem.motZ = a.nextGaussian() * (double)f3;
         p_dropItem_0_.addEntity(entityitem);
      }

   }
}
