package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockFluids;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class ItemBucket extends Item {
   private Block a;

   public ItemBucket(Block p_i87_1_) {
      this.maxStackSize = 1;
      this.a = p_i87_1_;
      this.a(CreativeModeTab.f);
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      boolean flag = this.a == Blocks.AIR;
      MovingObjectPosition movingobjectposition = this.a(p_a_2_, p_a_3_, flag);
      if(movingobjectposition == null) {
         return p_a_1_;
      } else {
         if(movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
            BlockPosition blockposition = movingobjectposition.a();
            if(!p_a_2_.a(p_a_3_, blockposition)) {
               return p_a_1_;
            }

            if(flag) {
               if(!p_a_3_.a(blockposition.shift(movingobjectposition.direction), movingobjectposition.direction, p_a_1_)) {
                  return p_a_1_;
               }

               IBlockData iblockdata = p_a_2_.getType(blockposition);
               Material material = iblockdata.getBlock().getMaterial();
               if(material == Material.WATER && ((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0) {
                  PlayerBucketFillEvent playerbucketfillevent1 = CraftEventFactory.callPlayerBucketFillEvent(p_a_3_, blockposition.getX(), blockposition.getY(), blockposition.getZ(), (EnumDirection)null, p_a_1_, Items.WATER_BUCKET);
                  if(playerbucketfillevent1.isCancelled()) {
                     return p_a_1_;
                  }

                  p_a_2_.setAir(blockposition);
                  p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
                  return this.a(p_a_1_, p_a_3_, Items.WATER_BUCKET, playerbucketfillevent1.getItemStack());
               }

               if(material == Material.LAVA && ((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0) {
                  PlayerBucketFillEvent playerbucketfillevent = CraftEventFactory.callPlayerBucketFillEvent(p_a_3_, blockposition.getX(), blockposition.getY(), blockposition.getZ(), (EnumDirection)null, p_a_1_, Items.LAVA_BUCKET);
                  if(playerbucketfillevent.isCancelled()) {
                     return p_a_1_;
                  }

                  p_a_2_.setAir(blockposition);
                  p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
                  return this.a(p_a_1_, p_a_3_, Items.LAVA_BUCKET, playerbucketfillevent.getItemStack());
               }
            } else {
               if(this.a == Blocks.AIR) {
                  PlayerBucketEmptyEvent playerbucketemptyevent = CraftEventFactory.callPlayerBucketEmptyEvent(p_a_3_, blockposition.getX(), blockposition.getY(), blockposition.getZ(), movingobjectposition.direction, p_a_1_);
                  if(playerbucketemptyevent.isCancelled()) {
                     return p_a_1_;
                  }

                  return CraftItemStack.asNMSCopy(playerbucketemptyevent.getItemStack());
               }

               BlockPosition blockposition1 = blockposition.shift(movingobjectposition.direction);
               if(!p_a_3_.a(blockposition1, movingobjectposition.direction, p_a_1_)) {
                  return p_a_1_;
               }

               PlayerBucketEmptyEvent playerbucketemptyevent1 = CraftEventFactory.callPlayerBucketEmptyEvent(p_a_3_, blockposition.getX(), blockposition.getY(), blockposition.getZ(), movingobjectposition.direction, p_a_1_);
               if(playerbucketemptyevent1.isCancelled()) {
                  return p_a_1_;
               }

               if(this.a(p_a_2_, blockposition1) && !p_a_3_.abilities.canInstantlyBuild) {
                  p_a_3_.b(StatisticList.USE_ITEM_COUNT[Item.getId(this)]);
                  return CraftItemStack.asNMSCopy(playerbucketemptyevent1.getItemStack());
               }
            }
         }

         return p_a_1_;
      }
   }

   private ItemStack a(ItemStack p_a_1_, EntityHuman p_a_2_, Item p_a_3_, org.bukkit.inventory.ItemStack p_a_4_) {
      if(p_a_2_.abilities.canInstantlyBuild) {
         return p_a_1_;
      } else if(--p_a_1_.count <= 0) {
         return CraftItemStack.asNMSCopy(p_a_4_);
      } else {
         if(!p_a_2_.inventory.pickup(CraftItemStack.asNMSCopy(p_a_4_))) {
            p_a_2_.drop(CraftItemStack.asNMSCopy(p_a_4_), false);
         }

         return p_a_1_;
      }
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_) {
      if(this.a == Blocks.AIR) {
         return false;
      } else {
         Material material = p_a_1_.getType(p_a_2_).getBlock().getMaterial();
         boolean flag = !material.isBuildable();
         if(!p_a_1_.isEmpty(p_a_2_) && !flag) {
            return false;
         } else {
            if(p_a_1_.worldProvider.n() && this.a == Blocks.FLOWING_WATER) {
               int i = p_a_2_.getX();
               int j = p_a_2_.getY();
               int k = p_a_2_.getZ();
               p_a_1_.makeSound((double)((float)i + 0.5F), (double)((float)j + 0.5F), (double)((float)k + 0.5F), "random.fizz", 0.5F, 2.6F + (p_a_1_.random.nextFloat() - p_a_1_.random.nextFloat()) * 0.8F);

               for(int l = 0; l < 8; ++l) {
                  p_a_1_.addParticle(EnumParticle.SMOKE_LARGE, (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
               }
            } else {
               if(!p_a_1_.isClientSide && flag && !material.isLiquid()) {
                  p_a_1_.setAir(p_a_2_, true);
               }

               p_a_1_.setTypeAndData(p_a_2_, this.a.getBlockData(), 3);
            }

            return true;
         }
      }
   }
}
