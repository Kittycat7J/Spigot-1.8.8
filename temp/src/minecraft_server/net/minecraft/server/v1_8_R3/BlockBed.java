package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockDirectional;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateBoolean;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.EnumDirection;
import net.minecraft.server.v1_8_R3.IBlockAccess;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.Item;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;

public class BlockBed extends BlockDirectional {
   public static final BlockStateEnum<BlockBed.EnumBedPart> PART = BlockStateEnum.<BlockBed.EnumBedPart>of("part", BlockBed.EnumBedPart.class);
   public static final BlockStateBoolean OCCUPIED = BlockStateBoolean.of("occupied");

   public BlockBed() {
      super(Material.CLOTH);
      this.j(this.blockStateList.getBlockData().set(PART, BlockBed.EnumBedPart.FOOT).set(OCCUPIED, Boolean.valueOf(false)));
      this.l();
   }

   public boolean interact(World p_interact_1_, BlockPosition p_interact_2_, IBlockData p_interact_3_, EntityHuman p_interact_4_, EnumDirection p_interact_5_, float p_interact_6_, float p_interact_7_, float p_interact_8_) {
      if(p_interact_1_.isClientSide) {
         return true;
      } else {
         if(p_interact_3_.get(PART) != BlockBed.EnumBedPart.HEAD) {
            p_interact_2_ = p_interact_2_.shift((EnumDirection)p_interact_3_.get(FACING));
            p_interact_3_ = p_interact_1_.getType(p_interact_2_);
            if(p_interact_3_.getBlock() != this) {
               return true;
            }
         }

         if(p_interact_1_.worldProvider.e() && p_interact_1_.getBiome(p_interact_2_) != BiomeBase.HELL) {
            if(((Boolean)p_interact_3_.get(OCCUPIED)).booleanValue()) {
               EntityHuman entityhuman = this.f(p_interact_1_, p_interact_2_);
               if(entityhuman != null) {
                  p_interact_4_.b((IChatBaseComponent)(new ChatMessage("tile.bed.occupied", new Object[0])));
                  return true;
               }

               p_interact_3_ = p_interact_3_.set(OCCUPIED, Boolean.valueOf(false));
               p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_, 4);
            }

            EntityHuman.EnumBedResult entityhuman$enumbedresult = p_interact_4_.a(p_interact_2_);
            if(entityhuman$enumbedresult == EntityHuman.EnumBedResult.OK) {
               p_interact_3_ = p_interact_3_.set(OCCUPIED, Boolean.valueOf(true));
               p_interact_1_.setTypeAndData(p_interact_2_, p_interact_3_, 4);
               return true;
            } else {
               if(entityhuman$enumbedresult == EntityHuman.EnumBedResult.NOT_POSSIBLE_NOW) {
                  p_interact_4_.b((IChatBaseComponent)(new ChatMessage("tile.bed.noSleep", new Object[0])));
               } else if(entityhuman$enumbedresult == EntityHuman.EnumBedResult.NOT_SAFE) {
                  p_interact_4_.b((IChatBaseComponent)(new ChatMessage("tile.bed.notSafe", new Object[0])));
               }

               return true;
            }
         } else {
            p_interact_1_.setAir(p_interact_2_);
            BlockPosition blockposition = p_interact_2_.shift(((EnumDirection)p_interact_3_.get(FACING)).opposite());
            if(p_interact_1_.getType(blockposition).getBlock() == this) {
               p_interact_1_.setAir(blockposition);
            }

            p_interact_1_.createExplosion((Entity)null, (double)p_interact_2_.getX() + 0.5D, (double)p_interact_2_.getY() + 0.5D, (double)p_interact_2_.getZ() + 0.5D, 5.0F, true, true);
            return true;
         }
      }
   }

   private EntityHuman f(World p_f_1_, BlockPosition p_f_2_) {
      for(EntityHuman entityhuman : p_f_1_.players) {
         if(entityhuman.isSleeping() && entityhuman.bx.equals(p_f_2_)) {
            return entityhuman;
         }
      }

      return null;
   }

   public boolean d() {
      return false;
   }

   public boolean c() {
      return false;
   }

   public void updateShape(IBlockAccess p_updateShape_1_, BlockPosition p_updateShape_2_) {
      this.l();
   }

   public void doPhysics(World p_doPhysics_1_, BlockPosition p_doPhysics_2_, IBlockData p_doPhysics_3_, Block p_doPhysics_4_) {
      EnumDirection enumdirection = (EnumDirection)p_doPhysics_3_.get(FACING);
      if(p_doPhysics_3_.get(PART) == BlockBed.EnumBedPart.HEAD) {
         if(p_doPhysics_1_.getType(p_doPhysics_2_.shift(enumdirection.opposite())).getBlock() != this) {
            p_doPhysics_1_.setAir(p_doPhysics_2_);
         }
      } else if(p_doPhysics_1_.getType(p_doPhysics_2_.shift(enumdirection)).getBlock() != this) {
         p_doPhysics_1_.setAir(p_doPhysics_2_);
         if(!p_doPhysics_1_.isClientSide) {
            this.b(p_doPhysics_1_, p_doPhysics_2_, p_doPhysics_3_, 0);
         }
      }

   }

   public Item getDropType(IBlockData p_getDropType_1_, Random p_getDropType_2_, int p_getDropType_3_) {
      return p_getDropType_1_.get(PART) == BlockBed.EnumBedPart.HEAD?null:Items.BED;
   }

   private void l() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
   }

   public static BlockPosition a(World p_a_0_, BlockPosition p_a_1_, int p_a_2_) {
      EnumDirection enumdirection = (EnumDirection)p_a_0_.getType(p_a_1_).get(FACING);
      int i = p_a_1_.getX();
      int j = p_a_1_.getY();
      int k = p_a_1_.getZ();

      for(int l = 0; l <= 1; ++l) {
         int i1 = i - enumdirection.getAdjacentX() * l - 1;
         int j1 = k - enumdirection.getAdjacentZ() * l - 1;
         int k1 = i1 + 2;
         int l1 = j1 + 2;

         for(int i2 = i1; i2 <= k1; ++i2) {
            for(int j2 = j1; j2 <= l1; ++j2) {
               BlockPosition blockposition = new BlockPosition(i2, j, j2);
               if(e(p_a_0_, blockposition)) {
                  if(p_a_2_ <= 0) {
                     return blockposition;
                  }

                  --p_a_2_;
               }
            }
         }
      }

      return null;
   }

   protected static boolean e(World p_e_0_, BlockPosition p_e_1_) {
      return World.a((IBlockAccess)p_e_0_, (BlockPosition)p_e_1_.down()) && !p_e_0_.getType(p_e_1_).getBlock().getMaterial().isBuildable() && !p_e_0_.getType(p_e_1_.up()).getBlock().getMaterial().isBuildable();
   }

   public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_) {
      if(p_dropNaturally_3_.get(PART) == BlockBed.EnumBedPart.FOOT) {
         super.dropNaturally(p_dropNaturally_1_, p_dropNaturally_2_, p_dropNaturally_3_, p_dropNaturally_4_, 0);
      }

   }

   public int k() {
      return 1;
   }

   public void a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, EntityHuman p_a_4_) {
      if(p_a_4_.abilities.canInstantlyBuild && p_a_3_.get(PART) == BlockBed.EnumBedPart.HEAD) {
         BlockPosition blockposition = p_a_2_.shift(((EnumDirection)p_a_3_.get(FACING)).opposite());
         if(p_a_1_.getType(blockposition).getBlock() == this) {
            p_a_1_.setAir(blockposition);
         }
      }

   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      EnumDirection enumdirection = EnumDirection.fromType2(p_fromLegacyData_1_);
      return (p_fromLegacyData_1_ & 8) > 0?this.getBlockData().set(PART, BlockBed.EnumBedPart.HEAD).set(FACING, enumdirection).set(OCCUPIED, Boolean.valueOf((p_fromLegacyData_1_ & 4) > 0)):this.getBlockData().set(PART, BlockBed.EnumBedPart.FOOT).set(FACING, enumdirection);
   }

   public IBlockData updateState(IBlockData p_updateState_1_, IBlockAccess p_updateState_2_, BlockPosition p_updateState_3_) {
      if(p_updateState_1_.get(PART) == BlockBed.EnumBedPart.FOOT) {
         IBlockData iblockdata = p_updateState_2_.getType(p_updateState_3_.shift((EnumDirection)p_updateState_1_.get(FACING)));
         if(iblockdata.getBlock() == this) {
            p_updateState_1_ = p_updateState_1_.set(OCCUPIED, iblockdata.get(OCCUPIED));
         }
      }

      return p_updateState_1_;
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      int i = 0;
      i = i | ((EnumDirection)p_toLegacyData_1_.get(FACING)).b();
      if(p_toLegacyData_1_.get(PART) == BlockBed.EnumBedPart.HEAD) {
         i |= 8;
         if(((Boolean)p_toLegacyData_1_.get(OCCUPIED)).booleanValue()) {
            i |= 4;
         }
      }

      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{FACING, PART, OCCUPIED});
   }

   public static enum EnumBedPart implements INamable {
      HEAD("head"),
      FOOT("foot");

      private final String c;

      private EnumBedPart(String p_i596_3_) {
         this.c = p_i596_3_;
      }

      public String toString() {
         return this.c;
      }

      public String getName() {
         return this.c;
      }
   }
}
