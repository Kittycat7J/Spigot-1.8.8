package net.minecraft.server.v1_8_R3;

import java.util.List;
import java.util.Random;
import net.minecraft.server.v1_8_R3.BlockLeaves;
import net.minecraft.server.v1_8_R3.BlockLeaves1;
import net.minecraft.server.v1_8_R3.BlockLog1;
import net.minecraft.server.v1_8_R3.BlockPlant;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateInteger;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockWood;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockFragilePlantElement;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.LocaleI18n;
import net.minecraft.server.v1_8_R3.World;
import net.minecraft.server.v1_8_R3.WorldGenAcaciaTree;
import net.minecraft.server.v1_8_R3.WorldGenBigTree;
import net.minecraft.server.v1_8_R3.WorldGenForest;
import net.minecraft.server.v1_8_R3.WorldGenForestTree;
import net.minecraft.server.v1_8_R3.WorldGenJungleTree;
import net.minecraft.server.v1_8_R3.WorldGenMegaTree;
import net.minecraft.server.v1_8_R3.WorldGenTaiga2;
import net.minecraft.server.v1_8_R3.WorldGenTrees;
import net.minecraft.server.v1_8_R3.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.entity.Player;
import org.bukkit.event.world.StructureGrowEvent;

public class BlockSapling extends BlockPlant implements IBlockFragilePlantElement {
   public static final BlockStateEnum<BlockWood.EnumLogVariant> TYPE = BlockStateEnum.<BlockWood.EnumLogVariant>of("type", BlockWood.EnumLogVariant.class);
   public static final BlockStateInteger STAGE = BlockStateInteger.of("stage", 0, 1);
   public static TreeType treeType;

   protected BlockSapling() {
      this.j(this.blockStateList.getBlockData().set(TYPE, BlockWood.EnumLogVariant.OAK).set(STAGE, Integer.valueOf(0)));
      float f = 0.4F;
      this.a(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
      this.a(CreativeModeTab.c);
   }

   public String getName() {
      return LocaleI18n.get(this.a() + "." + BlockWood.EnumLogVariant.OAK.d() + ".name");
   }

   public void b(World p_b_1_, BlockPosition p_b_2_, IBlockData p_b_3_, Random p_b_4_) {
      if(!p_b_1_.isClientSide) {
         super.b(p_b_1_, p_b_2_, p_b_3_, p_b_4_);
         if(p_b_1_.getLightLevel(p_b_2_.up()) >= 9 && p_b_4_.nextInt(Math.max(2, (int)(p_b_1_.growthOdds / (float)p_b_1_.spigotConfig.saplingModifier * 7.0F + 0.5F))) == 0) {
            p_b_1_.captureTreeGeneration = true;
            this.grow(p_b_1_, p_b_2_, p_b_3_, p_b_4_);
            p_b_1_.captureTreeGeneration = false;
            if(p_b_1_.capturedBlockStates.size() > 0) {
               TreeType treetype = treeType;
               treeType = null;
               Location location = new Location(p_b_1_.getWorld(), (double)p_b_2_.getX(), (double)p_b_2_.getY(), (double)p_b_2_.getZ());
               List<org.bukkit.block.BlockState> list = (List)p_b_1_.capturedBlockStates.clone();
               p_b_1_.capturedBlockStates.clear();
               StructureGrowEvent structuregrowevent = null;
               if(treetype != null) {
                  structuregrowevent = new StructureGrowEvent(location, treetype, false, (Player)null, list);
                  Bukkit.getPluginManager().callEvent(structuregrowevent);
               }

               if(structuregrowevent == null || !structuregrowevent.isCancelled()) {
                  for(org.bukkit.block.BlockState blockstate : list) {
                     blockstate.update(true);
                  }
               }
            }
         }
      }

   }

   public void grow(World p_grow_1_, BlockPosition p_grow_2_, IBlockData p_grow_3_, Random p_grow_4_) {
      if(((Integer)p_grow_3_.get(STAGE)).intValue() == 0) {
         p_grow_1_.setTypeAndData(p_grow_2_, p_grow_3_.a(STAGE), 4);
      } else {
         this.e(p_grow_1_, p_grow_2_, p_grow_3_, p_grow_4_);
      }

   }

   public void e(World p_e_1_, BlockPosition p_e_2_, IBlockData p_e_3_, Random p_e_4_) {
      Object object;
      if(p_e_4_.nextInt(10) == 0) {
         treeType = TreeType.BIG_TREE;
         object = new WorldGenBigTree(true);
      } else {
         treeType = TreeType.TREE;
         object = new WorldGenTrees(true);
      }

      int i = 0;
      int j = 0;
      boolean flag = false;
      switch(BlockSapling.SyntheticClass_1.a[((BlockWood.EnumLogVariant)p_e_3_.get(TYPE)).ordinal()]) {
      case 1:
         label124:
         for(i = 0; i >= -1; --i) {
            for(j = 0; j >= -1; --j) {
               if(this.a(p_e_1_, p_e_2_, i, j, BlockWood.EnumLogVariant.SPRUCE)) {
                  treeType = TreeType.MEGA_REDWOOD;
                  object = new WorldGenMegaTree(false, p_e_4_.nextBoolean());
                  flag = true;
                  break label124;
               }
            }
         }

         if(!flag) {
            j = 0;
            i = 0;
            treeType = TreeType.REDWOOD;
            object = new WorldGenTaiga2(true);
         }
         break;
      case 2:
         treeType = TreeType.BIRCH;
         object = new WorldGenForest(true, false);
         break;
      case 3:
         IBlockData iblockdata = Blocks.LOG.getBlockData().set(BlockLog1.VARIANT, BlockWood.EnumLogVariant.JUNGLE);
         IBlockData iblockdata1 = Blocks.LEAVES.getBlockData().set(BlockLeaves1.VARIANT, BlockWood.EnumLogVariant.JUNGLE).set(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

         label297:
         for(i = 0; i >= -1; --i) {
            for(j = 0; j >= -1; --j) {
               if(this.a(p_e_1_, p_e_2_, i, j, BlockWood.EnumLogVariant.JUNGLE)) {
                  treeType = TreeType.JUNGLE;
                  object = new WorldGenJungleTree(true, 10, 20, iblockdata, iblockdata1);
                  flag = true;
                  break label297;
               }
            }
         }

         if(!flag) {
            j = 0;
            i = 0;
            treeType = TreeType.SMALL_JUNGLE;
            object = new WorldGenTrees(true, 4 + p_e_4_.nextInt(7), iblockdata, iblockdata1, false);
         }
         break;
      case 4:
         treeType = TreeType.ACACIA;
         object = new WorldGenAcaciaTree(true);
         break;
      case 5:
         label436:
         for(i = 0; i >= -1; --i) {
            for(j = 0; j >= -1; --j) {
               if(this.a(p_e_1_, p_e_2_, i, j, BlockWood.EnumLogVariant.DARK_OAK)) {
                  treeType = TreeType.DARK_OAK;
                  object = new WorldGenForestTree(true);
                  flag = true;
                  break label436;
               }
            }
         }

         if(!flag) {
            return;
         }
      case 6:
      }

      IBlockData iblockdata2 = Blocks.AIR.getBlockData();
      if(flag) {
         p_e_1_.setTypeAndData(p_e_2_.a(i, 0, j), iblockdata2, 4);
         p_e_1_.setTypeAndData(p_e_2_.a(i + 1, 0, j), iblockdata2, 4);
         p_e_1_.setTypeAndData(p_e_2_.a(i, 0, j + 1), iblockdata2, 4);
         p_e_1_.setTypeAndData(p_e_2_.a(i + 1, 0, j + 1), iblockdata2, 4);
      } else {
         p_e_1_.setTypeAndData(p_e_2_, iblockdata2, 4);
      }

      if(!((WorldGenerator)object).generate(p_e_1_, p_e_4_, p_e_2_.a(i, 0, j))) {
         if(flag) {
            p_e_1_.setTypeAndData(p_e_2_.a(i, 0, j), p_e_3_, 4);
            p_e_1_.setTypeAndData(p_e_2_.a(i + 1, 0, j), p_e_3_, 4);
            p_e_1_.setTypeAndData(p_e_2_.a(i, 0, j + 1), p_e_3_, 4);
            p_e_1_.setTypeAndData(p_e_2_.a(i + 1, 0, j + 1), p_e_3_, 4);
         } else {
            p_e_1_.setTypeAndData(p_e_2_, p_e_3_, 4);
         }
      }

   }

   private boolean a(World p_a_1_, BlockPosition p_a_2_, int p_a_3_, int p_a_4_, BlockWood.EnumLogVariant p_a_5_) {
      return this.a(p_a_1_, p_a_2_.a(p_a_3_, 0, p_a_4_), p_a_5_) && this.a(p_a_1_, p_a_2_.a(p_a_3_ + 1, 0, p_a_4_), p_a_5_) && this.a(p_a_1_, p_a_2_.a(p_a_3_, 0, p_a_4_ + 1), p_a_5_) && this.a(p_a_1_, p_a_2_.a(p_a_3_ + 1, 0, p_a_4_ + 1), p_a_5_);
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_, BlockWood.EnumLogVariant p_a_3_) {
      IBlockData iblockdata = p_a_1_.getType(p_a_2_);
      return iblockdata.getBlock() == this && iblockdata.get(TYPE) == p_a_3_;
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockWood.EnumLogVariant)p_getDropData_1_.get(TYPE)).a();
   }

   public boolean a(World p_a_1_, BlockPosition p_a_2_, IBlockData p_a_3_, boolean p_a_4_) {
      return true;
   }

   public boolean a(World p_a_1_, Random p_a_2_, BlockPosition p_a_3_, IBlockData p_a_4_) {
      return (double)p_a_1_.random.nextFloat() < 0.45D;
   }

   public void b(World p_b_1_, Random p_b_2_, BlockPosition p_b_3_, IBlockData p_b_4_) {
      this.grow(p_b_1_, p_b_3_, p_b_4_, p_b_2_);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(TYPE, BlockWood.EnumLogVariant.a(p_fromLegacyData_1_ & 7)).set(STAGE, Integer.valueOf((p_fromLegacyData_1_ & 8) >> 3));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      byte b0 = 0;
      int i = b0 | ((BlockWood.EnumLogVariant)p_toLegacyData_1_.get(TYPE)).a();
      i = i | ((Integer)p_toLegacyData_1_.get(STAGE)).intValue() << 3;
      return i;
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{TYPE, STAGE});
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[BlockWood.EnumLogVariant.values().length];

      static {
         try {
            a[BlockWood.EnumLogVariant.SPRUCE.ordinal()] = 1;
         } catch (NoSuchFieldError var5) {
            ;
         }

         try {
            a[BlockWood.EnumLogVariant.BIRCH.ordinal()] = 2;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            a[BlockWood.EnumLogVariant.JUNGLE.ordinal()] = 3;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[BlockWood.EnumLogVariant.ACACIA.ordinal()] = 4;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[BlockWood.EnumLogVariant.DARK_OAK.ordinal()] = 5;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[BlockWood.EnumLogVariant.OAK.ordinal()] = 6;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}
