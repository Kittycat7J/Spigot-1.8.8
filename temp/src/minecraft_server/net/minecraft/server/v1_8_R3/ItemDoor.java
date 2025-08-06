package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDoor;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.World;

public class ItemDoor extends Item {
   private Block a;

   public ItemDoor(Block p_i112_1_) {
      this.a = p_i112_1_;
      this.a(CreativeModeTab.d);
   }

   public boolean interactWith(ItemStack p_interactWith_1_, EntityHuman p_interactWith_2_, World p_interactWith_3_, BlockPosition p_interactWith_4_, EnumDirection p_interactWith_5_, float p_interactWith_6_, float p_interactWith_7_, float p_interactWith_8_) {
      if(p_interactWith_5_ != EnumDirection.UP) {
         return false;
      } else {
         IBlockData iblockdata = p_interactWith_3_.getType(p_interactWith_4_);
         Block block = iblockdata.getBlock();
         if(!block.a(p_interactWith_3_, p_interactWith_4_)) {
            p_interactWith_4_ = p_interactWith_4_.shift(p_interactWith_5_);
         }

         if(!p_interactWith_2_.a(p_interactWith_4_, p_interactWith_5_, p_interactWith_1_)) {
            return false;
         } else if(!this.a.canPlace(p_interactWith_3_, p_interactWith_4_)) {
            return false;
         } else {
            a(p_interactWith_3_, p_interactWith_4_, EnumDirection.fromAngle((double)p_interactWith_2_.yaw), this.a);
            --p_interactWith_1_.count;
            return true;
         }
      }
   }

   public static void a(World p_a_0_, BlockPosition p_a_1_, EnumDirection p_a_2_, Block p_a_3_) {
      BlockPosition blockposition = p_a_1_.shift(p_a_2_.e());
      BlockPosition blockposition1 = p_a_1_.shift(p_a_2_.f());
      int i = (p_a_0_.getType(blockposition1).getBlock().isOccluding()?1:0) + (p_a_0_.getType(blockposition1.up()).getBlock().isOccluding()?1:0);
      int j = (p_a_0_.getType(blockposition).getBlock().isOccluding()?1:0) + (p_a_0_.getType(blockposition.up()).getBlock().isOccluding()?1:0);
      boolean flag = p_a_0_.getType(blockposition1).getBlock() == p_a_3_ || p_a_0_.getType(blockposition1.up()).getBlock() == p_a_3_;
      boolean flag1 = p_a_0_.getType(blockposition).getBlock() == p_a_3_ || p_a_0_.getType(blockposition.up()).getBlock() == p_a_3_;
      boolean flag2 = false;
      if(flag && !flag1 || j > i) {
         flag2 = true;
      }

      BlockPosition blockposition2 = p_a_1_.up();
      IBlockData iblockdata = p_a_3_.getBlockData().set(BlockDoor.FACING, p_a_2_).set(BlockDoor.HINGE, flag2?BlockDoor.EnumDoorHinge.RIGHT:BlockDoor.EnumDoorHinge.LEFT);
      p_a_0_.setTypeAndData(p_a_1_, iblockdata.set(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), 3);
      p_a_0_.setTypeAndData(blockposition2, iblockdata.set(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), 3);
   }
}
