package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.AxisAlignedBB;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockMinecartTrackAbstract;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityMinecartAbstract;
import net.minecraft.server.v1_8_R3.EntityMinecartCommandBlock;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.IEntitySelector;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockMinecartDetector extends BlockMinecartTrackAbstract {
   public static final BlockStateEnum<BlockMinecartTrackAbstract.EnumTrackPosition> SHAPE = BlockStateEnum.a("shape", BlockMinecartTrackAbstract.EnumTrackPosition.class, new Predicate() {
      public boolean a(BlockMinecartTrackAbstract.EnumTrackPosition p_a_1_) {
         return p_a_1_ != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_EAST && p_a_1_ != BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_WEST && p_a_1_ != BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_EAST && p_a_1_ != BlockMinecartTrackAbstract.EnumTrackPosition.SOUTH_WEST;
      }

      public boolean apply(Object p_apply_1_) {
         return this.a((BlockMinecartTrackAbstract.EnumTrackPosition)p_apply_1_);
      }
   });
   public static final BlockStateBoolean POWERED = BlockStateBoolean.of("powered");

   public BlockMinecartDetector() {
      super(true);
      this.j(this.blockStateList.getBlockData().set(POWERED, Boolean.valueOf(false)).set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH));
      this.a(true);
   }

   public int a(World p_a_1_) {
      return 20;
   }

   public boolean isPowerSource() {
      return true;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Entity p_a_4_) {
      if(!p_a_1_.isClientSide && !((Boolean)p_a_3_.get(POWERED)).booleanValue()) {
         this.e(p_a_1_, p_a_2_, p_a_3_);
      }

   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, Random p_a_4_) {
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(!p_b_1_.isClientSide && ((Boolean)p_b_3_.get(POWERED)).booleanValue()) {
         this.e(p_b_1_, p_b_2_, p_b_3_);
      }

   }

   public int a(IBlockAccess p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EnumDirection p_a_4_) {
      return ((Boolean)p_a_3_.get(POWERED)).booleanValue()?15:0;
   }

   public int b(IBlockAccess p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, EnumDirection p_b_4_) {
      return !((Boolean)p_b_3_.get(POWERED)).booleanValue()?0:(p_b_4_ == EnumDirection.UP?15:0);
   }

   private void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_) {
      boolean flag = ((Boolean)p_e_3_.get(POWERED)).booleanValue();
      boolean flag1 = false;
      List list = this.a(p_e_1_, p_e_2_, EntityMinecartAbstract.class, new Predicate[0]);
      if(!list.isEmpty()) {
         flag1 = true;
      }

      if(flag != flag1) {
         org.bukkit.block.Block block = p_e_1_.getWorld().getBlockAt(p_e_2_.getX(), p_e_2_.getY(), p_e_2_.getZ());
         BlockRedstoneEvent blockredstoneevent = new BlockRedstoneEvent(block, flag?15:0, flag1?15:0);
         p_e_1_.getServer().getPluginManager().callEvent(blockredstoneevent);
         flag1 = blockredstoneevent.getNewCurrent() > 0;
      }

      if(flag1 && !flag) {
         p_e_1_.setTypeAndData(p_e_2_, p_e_3_.set(POWERED, Boolean.valueOf(true)), 3);
         p_e_1_.applyPhysics(p_e_2_, this);
         p_e_1_.applyPhysics(p_e_2_.down(), this);
         p_e_1_.b(p_e_2_, p_e_2_);
      }

      if(!flag1 && flag) {
         p_e_1_.setTypeAndData(p_e_2_, p_e_3_.set(POWERED, Boolean.valueOf(false)), 3);
         p_e_1_.applyPhysics(p_e_2_, this);
         p_e_1_.applyPhysics(p_e_2_.down(), this);
         p_e_1_.b(p_e_2_, p_e_2_);
      }

      if(flag1) {
         p_e_1_.a((BlockPosition)p_e_2_, (Block)this, this.a(p_e_1_));
      }

      p_e_1_.updateAdjacentComparators(p_e_2_, this);
   }

   public void onPlace(World p_onPlace_1_, BlockPosition p_onPlace_2_, IBlockData p_onPlace_3_) {
      super.onPlace(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
      this.e(p_onPlace_1_, p_onPlace_2_, p_onPlace_3_);
   }

   public IBlockState<BlockMinecartTrackAbstract.EnumTrackPosition> n() {
      return SHAPE;
   }

   public boolean isComplexRedstone() {
      return true;
   }

   public int l(World p_l_1_, BlockPosition p_l_2_) {
      if(((Boolean)p_l_1_.getType(p_l_2_).get(POWERED)).booleanValue()) {
         List list = this.a(p_l_1_, p_l_2_, EntityMinecartCommandBlock.class, new Predicate[0]);
         if(!list.isEmpty()) {
            return ((EntityMinecartCommandBlock)list.get(0)).getCommandBlock().j();
         }

         List list1 = this.a(p_l_1_, p_l_2_, EntityMinecartAbstract.class, new Predicate[]{IEntitySelector.c});
         if(!list1.isEmpty()) {
            return Container.b((IInventory)list1.get(0));
         }
      }

      return 0;
   }

   protected <T extends EntityMinecartAbstract> List<T> a(World p_a_1_, BlockPosition p_a_2_, Class<T> p_a_3_, Predicate<Entity>... p_a_4_) {
      AxisAlignedBB axisalignedbb = this.a(p_a_2_);
      return p_a_4_.length != 1?p_a_1_.a(p_a_3_, axisalignedbb):p_a_1_.a(p_a_3_, axisalignedbb, p_a_4_[0]);
   }

   private AxisAlignedBB a(BlockPosition p_a_1_) {
      return new AxisAlignedBB((double)((float)p_a_1_.getX() + 0.2F), (double)p_a_1_.getY(), (double)((float)p_a_1_.getZ() + 0.2F), (double)((float)(p_a_1_.getX() + 1) - 0.2F), (double)((float)(p_a_1_.getY() + 1) - 0.2F), (double)((float)(p_a_1_.getZ() + 1) - 0.2F));
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.a(p_fromLegacyData_1_ & 7)).set(POWERED, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      byte b0 = 0;
      int i = b0 | ((BlockMinecartTrackAbstract.EnumTrackPosition)p_toLegacyData_1_.get(SHAPE)).a();
      if(((Boolean)p_toLegacyData_1_.get(POWERED)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{SHAPE, POWERED});
   }
}
