package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockMinecartTrackAbstract;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.World;

public class BlockMinecartTrack extends BlockMinecartTrackAbstract {
   public static final BlockStateEnum<BlockMinecartTrackAbstract.EnumTrackPosition> SHAPE = BlockStateEnum.<BlockMinecartTrackAbstract.EnumTrackPosition>of("shape", BlockMinecartTrackAbstract.EnumTrackPosition.class);

   protected BlockMinecartTrack() {
      super(false);
      this.j(this.blockStateList.getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.NORTH_SOUTH));
   }

   protected void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Block p_b_4_) {
      if(p_b_4_.isPowerSource() && (new BlockMinecartTrackAbstract.MinecartTrackLogic(p_b_1_, p_b_2_, p_b_3_)).a() == 3) {
         this.a(p_b_1_, p_b_2_, p_b_3_, false);
      }

   }

   public IBlockState<BlockMinecartTrackAbstract.EnumTrackPosition> n() {
      return SHAPE;
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(SHAPE, BlockMinecartTrackAbstract.EnumTrackPosition.a(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockMinecartTrackAbstract.EnumTrackPosition)p_toLegacyData_1_.get(SHAPE)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{SHAPE});
   }
}
