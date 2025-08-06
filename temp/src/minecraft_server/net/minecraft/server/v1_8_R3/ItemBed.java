package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockBed;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.World;

public class ItemBed extends Item {
   public ItemBed() {
      this.a(CreativeModeTab.c);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      if(p_interactWith_3_.isClientSide) {
         return true;
      } else if(p_interactWith_5_ != EnumDirection.UP) {
         return false;
      } else {
         IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
         Block block = iblockdata.getBlock();
         boolean flag = block.a(p_interactWith_3_, p_interactWith_4_);
         if(!flag) {
            p_interactWith_4_ = p_interactWith_4_.up();
         }

         int i = MathHelper.floor((double)(p_interactWith_2_.yaw * 4.0F / 360.0F) + 0.5D) & 3;
         EnumDirection enumdirection = EnumDirection.fromType2(i);
         BlockPosition blockposition = p_interactWith_4_.shift(enumdirection);
         if(p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_) && p_interactWith_2_.a(blockposition, p_interactWith_5_, p_interactWith_1_)) {
            boolean flag1 = p_interactWith_3_.getType(blockposition).getBlock().a(p_interactWith_3_, blockposition);
            boolean flag2 = flag || p_interactWith_3_.isEmpty(p_interactWith_4_);
            boolean flag3 = flag1 || p_interactWith_3_.isEmpty(blockposition);
            if(flag2 && flag3 && World.a((IBlockAccess)p_interactWith_3_, (BlockPosition)p_interactWith_4_.down()) && World.a((IBlockAccess)p_interactWith_3_, (BlockPosition)blockposition.down())) {
               IBlockData iblockdata1 = Blocks.BED.getBlockData().set(BlockBed.OCCUPIED, Boolean.valueOf(false)).set(BlockBed.FACING, enumdirection).set(BlockBed.PART, BlockBed.EnumBedPart.FOOT);
               if(p_interactWith_3_.setTypeAndData(p_interactWith_4_, iblockdata1, 3)) {
                  IBlockData iblockdata2 = iblockdata1.set(BlockBed.PART, BlockBed.EnumBedPart.HEAD);
                  p_interactWith_3_.setTypeAndData(blockposition, iblockdata2, 3);
               }

               --p_interactWith_1_.count;
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }
}
