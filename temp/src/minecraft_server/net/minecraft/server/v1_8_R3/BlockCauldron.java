package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityItem;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemArmor;
import net.minecraft.server.v1_8_R3.ItemBanner;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MaterialMapColor;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntityBanner;
import net.minecraft.server.v1_8_R3.World;

public class BlockCauldron extends Block {
   public static final BlockStateInteger LEVEL = BlockStateInteger.of("level", 0, 3);

   public BlockCauldron() {
      super(Material.ORE, MaterialMapColor.m);
      this.j(this.blockStateList.getBlockData().set(LEVEL, Integer.valueOf(0)));
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, AxisAlignedBB p_a_4_, List<AxisAlignedBB> p_a_5_, Entity p_a_6_) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
      super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      float f = 0.125F;
      this.a(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
      super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
      super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      this.a(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      this.a(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
      super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_, p_a_6_);
      this.j();
   }

   public void j() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean c() {
      return false;
   }

   public boolean d() {
      return false;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_) {
      int i = ((Integer)p_a_3_.get(LEVEL)).intValue();
      float f = (float)p_a_2_.getY() + (6.0F + (float)(3 * i)) / 16.0F;
      if(!p_a_1_.isClientSide && p_a_4_.isBurning() && i > 0 && p_a_4_.getBoundingBox().b <= (double)f) {
         p_a_4_.extinguish();
         this.a(p_a_1_, p_a_2_, p_a_3_, i - 1);
      }

   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(p_interact_1_.isClientSide) {
         return true;
      } else {
         ItemStack itemstack = p_interact_4_.inventory.getItemInHand();
         if(itemstack == null) {
            return true;
         } else {
            int i = ((Integer)p_interact_3_.get(LEVEL)).intValue();
            Item item = itemstack.getItem();
            if(item == Items.WATER_BUCKET) {
               if(i < 3) {
                  if(!p_interact_4_.abilities.canInstantlyBuild) {
                     p_interact_4_.inventory.setItem(p_interact_4_.inventory.itemInHandIndex, new ItemStack(Items.BUCKET));
                  }

                  p_interact_4_.b(StatisticList.I);
                  this.a(p_interact_1_, p_interact_2_, p_interact_3_, 3);
               }

               return true;
            } else if(item == Items.GLASS_BOTTLE) {
               if(i > 0) {
                  if(!p_interact_4_.abilities.canInstantlyBuild) {
                     ItemStack itemstack2 = new ItemStack(Items.POTION, 1, 0);
                     if(!p_interact_4_.inventory.pickup(itemstack2)) {
                        p_interact_1_.addEntity(new EntityItem(p_interact_1_, (double)p_interact_2_.getX() + 0.5D, (double)p_interact_2_.getY() + 1.5D, (double)p_interact_2_.getZ() + 0.5D, itemstack2));
                     } else if(p_interact_4_ instanceof EntityPlayer) {
                        ((EntityPlayer)p_interact_4_).updateInventory(p_interact_4_.defaultContainer);
                     }

                     p_interact_4_.b(StatisticList.J);
                     --itemstack.count;
                     if(itemstack.count <= 0) {
                        p_interact_4_.inventory.setItem(p_interact_4_.inventory.itemInHandIndex, (ItemStack)null);
                     }
                  }

                  this.a(p_interact_1_, p_interact_2_, p_interact_3_, i - 1);
               }

               return true;
            } else {
               if(i > 0 && item instanceof ItemArmor) {
                  ItemArmor itemarmor = (ItemArmor)item;
                  if(itemarmor.x_() == ItemArmor.EnumArmorMaterial.LEATHER && itemarmor.d_(itemstack)) {
                     itemarmor.c(itemstack);
                     this.a(p_interact_1_, p_interact_2_, p_interact_3_, i - 1);
                     p_interact_4_.b(StatisticList.K);
                     return true;
                  }
               }

               if(i > 0 && item instanceof ItemBanner && TileEntityBanner.c(itemstack) > 0) {
                  ItemStack itemstack1 = itemstack.cloneItemStack();
                  itemstack1.count = 1;
                  TileEntityBanner.e(itemstack1);
                  if(itemstack.count <= 1 && !p_interact_4_.abilities.canInstantlyBuild) {
                     p_interact_4_.inventory.setItem(p_interact_4_.inventory.itemInHandIndex, itemstack1);
                  } else {
                     if(!p_interact_4_.inventory.pickup(itemstack1)) {
                        p_interact_1_.addEntity(new EntityItem(p_interact_1_, (double)p_interact_2_.getX() + 0.5D, (double)p_interact_2_.getY() + 1.5D, (double)p_interact_2_.getZ() + 0.5D, itemstack1));
                     } else if(p_interact_4_ instanceof EntityPlayer) {
                        ((EntityPlayer)p_interact_4_).updateInventory(p_interact_4_.defaultContainer);
                     }

                     p_interact_4_.b(StatisticList.L);
                     if(!p_interact_4_.abilities.canInstantlyBuild) {
                        --itemstack.count;
                     }
                  }

                  if(!p_interact_4_.abilities.canInstantlyBuild) {
                     this.a(p_interact_1_, p_interact_2_, p_interact_3_, i - 1);
                  }

                  return true;
               } else {
                  return false;
               }
            }
         }
      }
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, int p_a_4_) {
      p_a_1_.setTypeAndData(p_a_2_, p_a_3_.set(LEVEL, Integer.valueOf(MathHelper.clamp(p_a_4_, 0, 3))), 2);
      p_a_1_.updateAdjacentComparators(p_a_2_, this);
   }

   public void k(World p_k_1_, BlockPosition p_k_2_) {
      if(p_k_1_.random.nextInt(20) == 1) {
         IBlockData iblockdata = p_k_1_.getType(p_k_2_);
         if(((Integer)iblockdata.get(LEVEL)).intValue() < 3) {
            p_k_1_.setTypeAndData(p_k_2_, iblockdata.a(LEVEL), 2);
         }

      }
   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return Items.CAULDRON;
   }

   public boolean isComplexRedstone() {
      return true;
   }

   public int l(World p_l_1_, BlockPosition p_l_2_) {
      return ((Integer)p_l_1_.getType(p_l_2_).get(LEVEL)).intValue();
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(LEVEL, Integer.valueOf(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((Integer)p_toLegacyData_1_.get(LEVEL)).intValue();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{LEVEL});
   }
}
