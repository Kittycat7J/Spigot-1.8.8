package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockLeaves;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.StatisticList;
import net.minecraft.server.v1_8_R3.TileEntity;
import net.minecraft.server.v1_8_R3.World;

public class BlockLeaves2 extends BlockLeaves {
   public static final BlockStateEnum<BlockWood.EnumLogVariant> VARIANT = BlockStateEnum.a("variant", BlockWood.EnumLogVariant.class, new Predicate<BlockWood.EnumLogVariant>() {
      public boolean a(BlockWood.EnumLogVariant p_a_1_) {
         return p_a_1_.a() >= 4;
      }
   });

   public BlockLeaves2() {
      this.j(this.blockStateList.getBlockData().set(VARIANT, BlockWood.EnumLogVariant.ACACIA).set(CHECK_DECAY, Boolean.valueOf(true)).set(DECAYABLE, Boolean.valueOf(true)));
   }

   protected void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, int p_a_4_) {
      if(p_a_3_.get(VARIANT) == BlockWood.EnumLogVariant.DARK_OAK && p_a_1_.random.nextInt(p_a_4_) == 0) {
         a(p_a_1_, p_a_2_, new ItemStack(Items.APPLE, 1, 0));
      }

   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockWood.EnumLogVariant)p_getDropData_1_.get(VARIANT)).a();
   }

   public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_) {
      IBlockData iblockdata = p_getDropData_1_.getType(p_getDropData_2_);
      return iblockdata.getBlock().toLegacyData(iblockdata) & 3;
   }

   protected ItemStack i(IBlockData p_i_1_) {
      return new ItemStack(Item.getItemOf(this), 1, ((BlockWood.EnumLogVariant)p_i_1_.get(VARIANT)).a() - 4);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(VARIANT, this.b(p_fromLegacyData_1_)).set(DECAYABLE, Boolean.valueOf((p_fromLegacyData_1_ & 4) == 0)).set(CHECK_DECAY, Boolean.valueOf((p_fromLegacyData_1_ & 8) > 0));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i = 0;
      i = i | ((BlockWood.EnumLogVariant)p_toLegacyData_1_.get(VARIANT)).a() - 4;
      if(!((Boolean)p_toLegacyData_1_.get(DECAYABLE)).booleanValue()) {
         i |= 4;
      }

      if(((Boolean)p_toLegacyData_1_.get(CHECK_DECAY)).booleanValue()) {
         i |= 8;
      }

      return i;
   }

   public BlockWood.EnumLogVariant b(int p_b_1_) {
      return BlockWood.EnumLogVariant.a((p_b_1_ & 3) + 4);
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{VARIANT, CHECK_DECAY, DECAYABLE});
   }

   public void a(World p_a_1_, EntityHuman p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_, TileEntity p_a_5_) {
      if(!p_a_1_.isClientSide && p_a_2_.bZ() != null && p_a_2_.bZ().getItem() == Items.SHEARS) {
         p_a_2_.b(StatisticList.MINE_BLOCK_COUNT[Block.getId(this)]);
         a(p_a_1_, p_a_3_, new ItemStack(Item.getItemOf(this), 1, ((BlockWood.EnumLogVariant)p_a_4_.get(VARIANT)).a() - 4));
      } else {
         super.a(p_a_1_, p_a_2_, p_a_3_, p_a_4_, p_a_5_);
      }
   }
}
