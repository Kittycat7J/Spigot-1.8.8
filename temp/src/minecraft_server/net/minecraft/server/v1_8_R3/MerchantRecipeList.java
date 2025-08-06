package net.minecraft.server.v1_8_R3;

import java.util.ArrayList;
import net.minecraft.server.v1_8_R3.GameProfileSerializer;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MerchantRecipe;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;

public class MerchantRecipeList extends ArrayList<MerchantRecipe> {
   public MerchantRecipeList() {
   }

   public MerchantRecipeList(NBTTagCompound p_i546_1_) {
      this.a(p_i546_1_);
   }

   public MerchantRecipe a(ItemStack p_a_1_, ItemStack p_a_2_, int p_a_3_) {
      if(p_a_3_ > 0 && p_a_3_ < this.size()) {
         MerchantRecipe merchantrecipe1 = (MerchantRecipe)this.get(p_a_3_);
         return !this.a(p_a_1_, merchantrecipe1.getBuyItem1()) || (p_a_2_ != null || merchantrecipe1.hasSecondItem()) && (!merchantrecipe1.hasSecondItem() || !this.a(p_a_2_, merchantrecipe1.getBuyItem2())) || p_a_1_.count < merchantrecipe1.getBuyItem1().count || merchantrecipe1.hasSecondItem() && p_a_2_.count < merchantrecipe1.getBuyItem2().count?null:merchantrecipe1;
      } else {
         for(int i = 0; i < this.size(); ++i) {
            MerchantRecipe merchantrecipe = (MerchantRecipe)this.get(i);
            if(this.a(p_a_1_, merchantrecipe.getBuyItem1()) && p_a_1_.count >= merchantrecipe.getBuyItem1().count && (!merchantrecipe.hasSecondItem() && p_a_2_ == null || merchantrecipe.hasSecondItem() && this.a(p_a_2_, merchantrecipe.getBuyItem2()) && p_a_2_.count >= merchantrecipe.getBuyItem2().count)) {
               return merchantrecipe;
            }
         }

         return null;
      }
   }

   private boolean a(ItemStack p_a_1_, ItemStack p_a_2_) {
      return ItemStack.c(p_a_1_, p_a_2_) && (!p_a_2_.hasTag() || p_a_1_.hasTag() && GameProfileSerializer.a(p_a_2_.getTag(), p_a_1_.getTag(), false));
   }

   public void a(PacketDataSerializer p_a_1_) {
      p_a_1_.writeByte((byte)(this.size() & 255));

      for(int i = 0; i < this.size(); ++i) {
         MerchantRecipe merchantrecipe = (MerchantRecipe)this.get(i);
         p_a_1_.a(merchantrecipe.getBuyItem1());
         p_a_1_.a(merchantrecipe.getBuyItem3());
         ItemStack itemstack = merchantrecipe.getBuyItem2();
         p_a_1_.writeBoolean(itemstack != null);
         if(itemstack != null) {
            p_a_1_.a(itemstack);
         }

         p_a_1_.writeBoolean(merchantrecipe.h());
         p_a_1_.writeInt(merchantrecipe.e());
         p_a_1_.writeInt(merchantrecipe.f());
      }

   }

   public void a(NBTTagCompound p_a_1_) {
      NBTTagList nbttaglist = p_a_1_.getList("Recipes", 10);

      for(int i = 0; i < nbttaglist.size(); ++i) {
         NBTTagCompound nbttagcompound = nbttaglist.get(i);
         this.add(new MerchantRecipe(nbttagcompound));
      }

   }

   public NBTTagCompound a() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      NBTTagList nbttaglist = new NBTTagList();

      for(int i = 0; i < this.size(); ++i) {
         MerchantRecipe merchantrecipe = (MerchantRecipe)this.get(i);
         nbttaglist.add(merchantrecipe.k());
      }

      nbttagcompound.set("Recipes", nbttaglist);
      return nbttagcompound;
   }
}
