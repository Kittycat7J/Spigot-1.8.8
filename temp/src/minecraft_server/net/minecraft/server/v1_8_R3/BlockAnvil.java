package net.minecraft.server.v1_8_R3;

import net.minecraft.server.v1_8_R3.BlockFalling;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateDirection;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.ContainerAnvil;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntityFallingBlock;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.ITileEntityContainer;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.PlayerInventory;
import net.minecraft.server.v1_8_R3.World;

public class BlockAnvil extends BlockFalling {
   public static final BlockStateDirection FACING = BlockStateDirection.of("facing", EnumDirection.EnumDirectionLimit.HORIZONTAL);
   public static final BlockStateInteger DAMAGE = BlockStateInteger.of("damage", 0, 2);

   protected BlockAnvil() {
      super(Material.HEAVY);
      this.j(this.blockStateList.getBlockData().set(FACING, EnumDirection.NORTH).set(DAMAGE, Integer.valueOf(0)));
      this.e(0);
      this.a(CreativeModeTab.c);
   }

   public boolean d() {
      return false;
   }

   public boolean c() {
      return false;
   }

   public IBlockData getPlacedState(World p_getPlacedState_1_, BlockPosition p_getPlacedState_2_, EnumDirection p_getPlacedState_3_, float p_getPlacedState_4_, float p_getPlacedState_5_, float p_getPlacedState_6_, int p_getPlacedState_7_, EntityLiving p_getPlacedState_8_) {
      EnumDirection enumdirection = p_getPlacedState_8_.getDirection().e();
      return super.getPlacedState(p_getPlacedState_1_, p_getPlacedState_2_, p_getPlacedState_3_, p_getPlacedState_4_, p_getPlacedState_5_, p_getPlacedState_6_, p_getPlacedState_7_, p_getPlacedState_8_).set(FACING, enumdirection).set(DAMAGE, Integer.valueOf(p_getPlacedState_7_ >> 2));
   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(!p_interact_1_.isClientSide) {
         p_interact_4_.openTileEntity(new BlockAnvil.TileEntityContainerAnvil(p_interact_1_, p_interact_2_));
      }

      return true;
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((Integer)p_getDropData_1_.get(DAMAGE)).intValue();
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      EnumDirection enumdirection = (EnumDirection)p_updateShape_1_.getType(p_updateShape_2_).get(FACING);
      if(enumdirection.k() == EnumDirection.EnumAxis.X) {
         this.a(0.0F, 0.0F, 0.125F, 1.0F, 1.0F, 0.875F);
      } else {
         this.a(0.125F, 0.0F, 0.0F, 0.875F, 1.0F, 1.0F);
      }

   }

   protected void a(EntityFallingBlock p_a_1_) {
      p_a_1_.a(true);
   }

   public void a_(World p_a__1_, BlockPosition p_a__2_) {
      p_a__1_.triggerEffect(1022, p_a__2_, 0);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(FACING, EnumDirection.fromType2(p_fromLegacyData_1_ & 3)).set(DAMAGE, Integer.valueOf((p_fromLegacyData_1_ & 15) >> 2));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      byte b0 = 0;
      int i = b0 | ((EnumDirection)p_toLegacyData_1_.get(FACING)).b();
      i = i | ((Integer)p_toLegacyData_1_.get(DAMAGE)).intValue() << 2;
      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{FACING, DAMAGE});
   }

   public static class TileEntityContainerAnvil implements ITileEntityContainer {
      private final World a;
      private final BlockPosition b;

      public TileEntityContainerAnvil(World p_i456_1_, BlockPosition p_i456_2_) {
         this.a = p_i456_1_;
         this.b = p_i456_2_;
      }

      public String getName() {
         return "anvil";
      }

      public boolean hasCustomName() {
         return false;
      }

      public IChatBaseComponent getScoreboardDisplayName() {
         return new ChatMessage(Blocks.ANVIL.a() + ".name", new Object[0]);
      }

      public Container createContainer(PlayerInventory p_createContainer_1_, EntityHuman p_createContainer_2_) {
         return new ContainerAnvil(p_createContainer_1_, this.a, this.b, p_createContainer_2_);
      }

      public String getContainerName() {
         return "minecraft:anvil";
      }
   }
}
