package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockEnderPortalFrame;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityEnderSignal;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;

public class ItemEnderEye extends Item {
   public ItemEnderEye() {
      this.a(CreativeModeTab.f);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
      if(p_interactWith_2_.a(p_interactWith_4_.shift(p_interactWith_5_), p_interactWith_5_, p_interactWith_1_) && iblockdata.getBlock() == Blocks.END_PORTAL_FRAME && !((Boolean)iblockdata.get(BlockEnderPortalFrame.EYE)).booleanValue()) {
         if(p_interactWith_3_.isClientSide) {
            return true;
         } else {
            p_interactWith_3_.setTypeAndData(p_interactWith_4_, iblockdata.set(BlockEnderPortalFrame.EYE, Boolean.valueOf(true)), 2);
            p_interactWith_3_.updateAdjacentComparators(p_interactWith_4_, Blocks.END_PORTAL_FRAME);
            --p_interactWith_1_.count;

            for(int i = 0; i < 16; ++i) {
               double d0 = (double)((float)p_interactWith_4_.getX() + (5.0F + g.nextFloat() * 6.0F) / 16.0F);
               double d1 = (double)((float)p_interactWith_4_.getY() + 0.8125F);
               double d2 = (double)((float)p_interactWith_4_.getZ() + (5.0F + g.nextFloat() * 6.0F) / 16.0F);
               double d3 = 0.0D;
               double d4 = 0.0D;
               double d5 = 0.0D;
               p_interactWith_3_.addParticle(EnumParticle.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
            }

            EnumDirection enumdirection1 = (EnumDirection)iblockdata.get(BlockEnderPortalFrame.FACING);
            int j = 0;
            int k = 0;
            boolean flag = false;
            boolean flag1 = true;
            EnumDirection enumdirection = enumdirection1.e();

            for(int l = -2; l <= 2; ++l) {
               BlockPosition blockposition = p_interactWith_4_.shift(enumdirection, l);
               IBlockData iblockdata1 = p_interactWith_3_.getType(blockposition);
               if(iblockdata1.getBlock() == Blocks.END_PORTAL_FRAME) {
                  if(!((Boolean)iblockdata1.get(BlockEnderPortalFrame.EYE)).booleanValue()) {
                     flag1 = false;
                     break;
                  }

                  k = l;
                  if(!flag) {
                     j = l;
                     flag = true;
                  }
               }
            }

            if(flag1 && k == j + 2) {
               BlockPosition blockposition1 = p_interactWith_4_.shift(enumdirection1, 4);

               for(int i1 = j; i1 <= k; ++i1) {
                  BlockPosition blockposition2 = blockposition1.shift(enumdirection, i1);
                  IBlockData iblockdata2 = p_interactWith_3_.getType(blockposition2);
                  if(iblockdata2.getBlock() != Blocks.END_PORTAL_FRAME || !((Boolean)iblockdata2.get(BlockEnderPortalFrame.EYE)).booleanValue()) {
                     flag1 = false;
                     break;
                  }
               }

               for(int j1 = j - 1; j1 <= k + 1; j1 += 4) {
                  blockposition1 = p_interactWith_4_.shift(enumdirection, j1);

                  for(int l1 = 1; l1 <= 3; ++l1) {
                     BlockPosition blockposition3 = blockposition1.shift(enumdirection1, l1);
                     IBlockData iblockdata3 = p_interactWith_3_.getType(blockposition3);
                     if(iblockdata3.getBlock() != Blocks.END_PORTAL_FRAME || !((Boolean)iblockdata3.get(BlockEnderPortalFrame.EYE)).booleanValue()) {
                        flag1 = false;
                        break;
                     }
                  }
               }

               if(flag1) {
                  for(int k1 = j; k1 <= k; ++k1) {
                     blockposition1 = p_interactWith_4_.shift(enumdirection, k1);

                     for(int i2 = 1; i2 <= 3; ++i2) {
                        BlockPosition blockposition4 = blockposition1.shift(enumdirection1, i2);
                        p_interactWith_3_.setTypeAndData(blockposition4, Blocks.END_PORTAL.getBlockData(), 2);
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      MovingObjectPosition movingobjectposition = this.a(p_a_2_, p_a_3_, false);
      if(movingobjectposition != null && movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK && p_a_2_.getType(movingobjectposition.a()).getBlock() == Blocks.END_PORTAL_FRAME) {
         return p_a_1_;
      } else {
         if(!p_a_2_.isClientSide) {
            BlockPosition blockposition = p_a_2_.a("Stronghold", new BlockPosition(p_a_3_));
            if(blockposition != null) {
               EntityEnderSignal entityendersignal = new EntityEnderSignal(p_a_2_, p_a_3_.locX, p_a_3_.locY, p_a_3_.locZ);
               entityendersignal.a(blockposition);
               p_a_2_.addEntity(entityendersignal);
               p_a_2_.makeSound(p_a_3_, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
               p_a_2_.a((EntityHuman)null, 1002, new BlockPosition(p_a_3_), 0);
               if(!p_a_3_.abilities.canInstantlyBuild) {
                  --p_a_1_.count;
               }

               p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
            }
         }

         return p_a_1_;
      }
   }
}
