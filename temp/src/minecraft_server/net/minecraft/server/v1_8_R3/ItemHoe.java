package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDirt;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class ItemHoe extends Item {
   protected Item.EnumToolMaterial a;

   public ItemHoe(Item.EnumToolMaterial p_i1282_1_) {
      this.a = p_i1282_1_;
      this.maxStackSize = 1;
      this.setMaxDurability(p_i1282_1_.a());
      this.a(CreativeModeTab.i);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      if(!p_interactWith_2_.a(p_interactWith_4_.shift(p_interactWith_5_), p_interactWith_5_, p_interactWith_1_)) {
         return false;
      } else {
         IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
         Block block = iblockdata.getBlock();
         if(p_interactWith_5_ != EnumDirection.DOWN && p_interactWith_3_.getType(p_interactWith_4_.up()).getBlock().getMaterial() == Material.AIR) {
            if(block == Blocks.GRASS) {
               return this.a(p_interactWith_1_, p_interactWith_2_, p_interactWith_3_, p_interactWith_4_, Blocks.FARMLAND.getBlockData());
            }

            if(block == Blocks.DIRT) {
               switch((BlockDirt.EnumDirtVariant)iblockdata.get(BlockDirt.VARIANT)) {
               case DIRT:
                  return this.a(p_interactWith_1_, p_interactWith_2_, p_interactWith_3_, p_interactWith_4_, Blocks.FARMLAND.getBlockData());
               case COARSE_DIRT:
                  return this.a(p_interactWith_1_, p_interactWith_2_, p_interactWith_3_, p_interactWith_4_, Blocks.DIRT.getBlockData().set(BlockDirt.VARIANT, BlockDirt.EnumDirtVariant.DIRT));
               }
            }
         }

         return false;
      }
   }

   protected boolean a(ItemStack p_a_1_, EntityHuman p_a_2_, World p_a_3_, BlockPosition p_a_4_, IBlockData p_a_5_) {
      p_a_3_.makeSound((double)((float)p_a_4_.getX() + 0.5F), (double)((float)p_a_4_.getY() + 0.5F), (double)((float)p_a_4_.getZ() + 0.5F), p_a_5_.getBlock().stepSound.getStepSound(), (p_a_5_.getBlock().stepSound.getVolume1() + 1.0F) / 2.0F, p_a_5_.getBlock().stepSound.getVolume2() * 0.8F);
      if(p_a_3_.isClientSide) {
         return true;
      } else {
         p_a_3_.setTypeUpdate(p_a_4_, p_a_5_);
         p_a_1_.damage(1, p_a_2_);
         return true;
      }
   }

   public String g() {
      return this.a.toString();
   }
}
