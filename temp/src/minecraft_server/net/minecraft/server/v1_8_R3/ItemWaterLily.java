package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockFluids;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.ItemWithAuxData;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.MovingObjectPosition;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory;
import org.bukkit.event.block.BlockPlaceEvent;

public class ItemWaterLily extends ItemWithAuxData {
   public ItemWaterLily(Block p_i49_1_) {
      super(p_i49_1_, false);
   }

   public ItemStack a(ItemStack p_a_1_, World p_a_2_, EntityHuman p_a_3_) {
      MovingObjectPosition movingobjectposition = this.a(p_a_2_, p_a_3_, true);
      if(movingobjectposition == null) {
         return p_a_1_;
      } else {
         if(movingobjectposition.type == MovingObjectPosition.EnumMovingObjectType.BLOCK) {
            BlockPosition blockposition = movingobjectposition.a();
            if(!p_a_2_.a(p_a_3_, blockposition)) {
               return p_a_1_;
            }

            if(!p_a_3_.a(blockposition.shift(movingobjectposition.direction), movingobjectposition.direction, p_a_1_)) {
               return p_a_1_;
            }

            BlockPosition blockposition1 = blockposition.up();
            IBlockData iblockdata = p_a_2_.getType(blockposition);
            if(iblockdata.getBlock().getMaterial() == Material.WATER && ((Integer)iblockdata.get(BlockFluids.LEVEL)).intValue() == 0 && p_a_2_.isEmpty(blockposition1)) {
               org.bukkit.block.BlockState blockstate = CraftBlockState.getBlockState(p_a_2_, blockposition1.getX(), blockposition1.getY(), blockposition1.getZ());
               p_a_2_.setTypeUpdate(blockposition1, Blocks.WATERLILY.getBlockData());
               BlockPlaceEvent blockplaceevent = CraftEventFactory.callBlockPlaceEvent(p_a_2_, p_a_3_, blockstate, blockposition.getX(), blockposition.getY(), blockposition.getZ());
               if(blockplaceevent != null && (blockplaceevent.isCancelled() || !blockplaceevent.canBuild())) {
                  blockstate.update(true, false);
                  return p_a_1_;
               }

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
