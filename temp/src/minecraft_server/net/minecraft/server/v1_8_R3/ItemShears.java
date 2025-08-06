package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class ItemShears extends Item {
   public ItemShears() {
      this.c(1);
      this.setMaxDurability(238);
      this.a(CreativeModeTab.i);
   }

   public boolean a(ItemStack p_a_1_, World p_a_2_, Block p_a_3_, BlockPosition p_a_4_, EntityLiving p_a_5_) {
      if(p_a_3_.getMaterial() != Material.LEAVES && p_a_3_ != Blocks.WEB && p_a_3_ != Blocks.TALLGRASS && p_a_3_ != Blocks.VINE && p_a_3_ != Blocks.TRIPWIRE && p_a_3_ != Blocks.WOOL) {
         return super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);
      } else {
         p_a_1_.damage(1, p_a_5_);
         return true;
      }
   }

   public boolean canDestroySpecialBlock(Block p_canDestroySpecialBlock_1_) {
      return p_canDestroySpecialBlock_1_ == Blocks.WEB || p_canDestroySpecialBlock_1_ == Blocks.REDSTONE_WIRE || p_canDestroySpecialBlock_1_ == Blocks.TRIPWIRE;
   }

   public float getDestroySpeed(ItemStack p_getDestroySpeed_1_, Block p_getDestroySpeed_2_) {
      return p_getDestroySpeed_2_ != Blocks.WEB && p_getDestroySpeed_2_.getMaterial() != Material.LEAVES?(p_getDestroySpeed_2_ == Blocks.WOOL?5.0F:super.getDestroySpeed(p_getDestroySpeed_1_, p_getDestroySpeed_2_)):15.0F;
   }
}
