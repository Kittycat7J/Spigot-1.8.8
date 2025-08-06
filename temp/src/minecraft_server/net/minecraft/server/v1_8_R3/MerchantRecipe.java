package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class MerchantRecipe {
   private ItemStack buyingItem1;
   private ItemStack buyingItem2;
   private ItemStack sellingItem;
   private int uses;
   private int maxUses;
   private boolean rewardExp;

   public MerchantRecipe(NBTTagCompound p_i540_1_) {
      this.a(p_i540_1_);
   }

   public MerchantRecipe(ItemStack p_i541_1_, ItemStack p_i541_2_, ItemStack p_i541_3_) {
      this(p_i541_1_, p_i541_2_, p_i541_3_, 0, 7);
   }

   public MerchantRecipe(ItemStack p_i542_1_, ItemStack p_i542_2_, ItemStack p_i542_3_, int p_i542_4_, int p_i542_5_) {
      this.buyingItem1 = p_i542_1_;
      this.buyingItem2 = p_i542_2_;
      this.sellingItem = p_i542_3_;
      this.uses = p_i542_4_;
      this.maxUses = p_i542_5_;
      this.rewardExp = true;
   }

   public MerchantRecipe(ItemStack p_i543_1_, ItemStack p_i543_2_) {
      this(p_i543_1_, (ItemStack)null, p_i543_2_);
   }

   public MerchantRecipe(ItemStack p_i544_1_, Item p_i544_2_) {
      this(p_i544_1_, new ItemStack(p_i544_2_));
   }

   public ItemStack getBuyItem1() {
      return this.buyingItem1;
   }

   public ItemStack getBuyItem2() {
      return this.buyingItem2;
   }

   public boolean hasSecondItem() {
      return this.buyingItem2 != null;
   }

   public ItemStack getBuyItem3() {
      return this.sellingItem;
   }

   public int e() {
      return this.uses;
   }

   public int f() {
      return this.maxUses;
   }

   public void g() {
      ++this.uses;
   }

   public void a(int p_a_1_) {
      this.maxUses += p_a_1_;
   }

   public boolean h() {
      return this.uses >= this.maxUses;
   }

   public boolean j() {
      return this.rewardExp;
   }

   public void a(NBTTagCompound p_a_1_) {
      NBTTagCompound nbttagcompound = p_a_1_.getCompound("buy");
      this.buyingItem1 = ItemStack.createStack(nbttagcompound);
      NBTTagCompound nbttagcompound1 = p_a_1_.getCompound("sell");
      this.sellingItem = ItemStack.createStack(nbttagcompound1);
      if(p_a_1_.hasKeyOfType("buyB", 10)) {
         this.buyingItem2 = ItemStack.createStack(p_a_1_.getCompound("buyB"));
      }

      if(p_a_1_.hasKeyOfType("uses", 99)) {
         this.uses = p_a_1_.getInt("uses");
      }

      if(p_a_1_.hasKeyOfType("maxUses", 99)) {
         this.maxUses = p_a_1_.getInt("maxUses");
      } else {
         this.maxUses = 7;
      }

      if(p_a_1_.hasKeyOfType("rewardExp", 1)) {
         this.rewardExp = p_a_1_.getBoolean("rewardExp");
      } else {
         this.rewardExp = true;
      }

   }

   public NBTTagCompound k() {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      nbttagcompound.set("buy", this.buyingItem1.save(new NBTTagCompound()));
      nbttagcompound.set("sell", this.sellingItem.save(new NBTTagCompound()));
      if(this.buyingItem2 != null) {
         nbttagcompound.set("buyB", this.buyingItem2.save(new NBTTagCompound()));
      }

      nbttagcompound.setInt("uses", this.uses);
      nbttagcompound.setInt("maxUses", this.maxUses);
      nbttagcompound.setBoolean("rewardExp", this.rewardExp);
      return nbttagcompound;
   }
}
