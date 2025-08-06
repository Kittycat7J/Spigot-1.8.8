package net.minecraft.server.v1_8_R3;

import java.util.Random;
import net.minecraft.server.v1_8_R3.Block;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockSmoothBrick;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.BlockStone;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.CreativeModeTab;
import net.minecraft.server.v1_8_R3.EntitySilverfish;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Material;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class BlockMonsterEggs extends Block {
   public static final BlockStateEnum<BlockMonsterEggs.EnumMonsterEggVarient> VARIANT = BlockStateEnum.<BlockMonsterEggs.EnumMonsterEggVarient>of("variant", BlockMonsterEggs.EnumMonsterEggVarient.class);

   public BlockMonsterEggs() {
      super(Material.CLAY);
      this.j(this.blockStateList.getBlockData().set(VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.STONE));
      this.c(0.0F);
      this.a(CreativeModeTab.c);
   }

   public int a(Random p_a_1_) {
      return 0;
   }

   public static boolean d(IBlockData p_d_0_) {
      Block block = p_d_0_.getBlock();
      return p_d_0_ == Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.STONE) || block == Blocks.COBBLESTONE || block == Blocks.STONEBRICK;
   }

   protected ItemStack i(IBlockData p_i_1_) {
      switch(BlockMonsterEggs.SyntheticClass_1.a[((BlockMonsterEggs.EnumMonsterEggVarient)p_i_1_.get(VARIANT)).ordinal()]) {
      case 1:
         return new ItemStack(Blocks.COBBLESTONE);
      case 2:
         return new ItemStack(Blocks.STONEBRICK);
      case 3:
         return new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.EnumStonebrickType.MOSSY.a());
      case 4:
         return new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.EnumStonebrickType.CRACKED.a());
      case 5:
         return new ItemStack(Blocks.STONEBRICK, 1, BlockSmoothBrick.EnumStonebrickType.CHISELED.a());
      default:
         return new ItemStack(Blocks.STONE);
      }
   }

   public void dropNaturally(World p_dropNaturally_1_, BlockPosition p_dropNaturally_2_, IBlockData p_dropNaturally_3_, float p_dropNaturally_4_, int p_dropNaturally_5_) {
      if(!p_dropNaturally_1_.isClientSide && p_dropNaturally_1_.getGameRules().getBoolean("doTileDrops")) {
         EntitySilverfish entitysilverfish = new EntitySilverfish(p_dropNaturally_1_);
         entitysilverfish.setPositionRotation((double)p_dropNaturally_2_.getX() + 0.5D, (double)p_dropNaturally_2_.getY(), (double)p_dropNaturally_2_.getZ() + 0.5D, 0.0F, 0.0F);
         p_dropNaturally_1_.addEntity(entitysilverfish, SpawnReason.SILVERFISH_BLOCK);
         entitysilverfish.y();
      }

   }

   public int getDropData(World p_getDropData_1_, BlockPosition p_getDropData_2_) {
      IBlockData iblockdata = p_getDropData_1_.getType(p_getDropData_2_);
      return iblockdata.getBlock().toLegacyData(iblockdata);
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(VARIANT, BlockMonsterEggs.EnumMonsterEggVarient.a(p_fromLegacyData_1_));
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockMonsterEggs.EnumMonsterEggVarient)p_toLegacyData_1_.get(VARIANT)).a();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{VARIANT});
   }

   public static enum EnumMonsterEggVarient implements INamable {
      STONE(0, "stone") {
         public IBlockData d() {
            return Blocks.STONE.getBlockData().set(BlockStone.VARIANT, BlockStone.EnumStoneVariant.STONE);
         }
      },
      COBBLESTONE(1, "cobblestone", "cobble") {
         public IBlockData d() {
            return Blocks.COBBLESTONE.getBlockData();
         }
      },
      STONEBRICK(2, "stone_brick", "brick") {
         public IBlockData d() {
            return Blocks.STONEBRICK.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.DEFAULT);
         }
      },
      MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick") {
         public IBlockData d() {
            return Blocks.STONEBRICK.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.MOSSY);
         }
      },
      CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick") {
         public IBlockData d() {
            return Blocks.STONEBRICK.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.CRACKED);
         }
      },
      CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick") {
         public IBlockData d() {
            return Blocks.STONEBRICK.getBlockData().set(BlockSmoothBrick.VARIANT, BlockSmoothBrick.EnumStonebrickType.CHISELED);
         }
      };

      private static final BlockMonsterEggs.EnumMonsterEggVarient[] g = new BlockMonsterEggs.EnumMonsterEggVarient[values().length];
      private final int h;
      private final String i;
      private final String j;

      static {
         for(BlockMonsterEggs.EnumMonsterEggVarient blockmonstereggs$enummonstereggvarient : values()) {
            g[blockmonstereggs$enummonstereggvarient.a()] = blockmonstereggs$enummonstereggvarient;
         }

      }

      private EnumMonsterEggVarient(int p_i217_3_, String p_i217_4_) {
         this(p_i217_3_, p_i217_4_, p_i217_4_);
      }

      private EnumMonsterEggVarient(int p_i218_3_, String p_i218_4_, String p_i218_5_) {
         this.h = p_i218_3_;
         this.i = p_i218_4_;
         this.j = p_i218_5_;
      }

      public int a() {
         return this.h;
      }

      public String toString() {
         return this.i;
      }

      public static BlockMonsterEggs.EnumMonsterEggVarient a(int p_a_0_) {
         if(p_a_0_ < 0 || p_a_0_ >= g.length) {
            p_a_0_ = 0;
         }

         return g[p_a_0_];
      }

      public String getName() {
         return this.i;
      }

      public String c() {
         return this.j;
      }

      public abstract IBlockData d();

      public static BlockMonsterEggs.EnumMonsterEggVarient a(IBlockData p_a_0_) {
         for(BlockMonsterEggs.EnumMonsterEggVarient blockmonstereggs$enummonstereggvarient : values()) {
            if(p_a_0_ == blockmonstereggs$enummonstereggvarient.d()) {
               return blockmonstereggs$enummonstereggvarient;
            }
         }

         return STONE;
      }

      private EnumMonsterEggVarient(int p_i219_3_, String p_i219_4_, BlockMonsterEggs.SyntheticClass_1 p_i219_5_) {
         this(p_i219_3_, p_i219_4_);
      }

      private EnumMonsterEggVarient(int p_i220_3_, String p_i220_4_, String p_i220_5_, BlockMonsterEggs.SyntheticClass_1 p_i220_6_) {
         this(p_i220_3_, p_i220_4_, p_i220_5_);
      }
   }

   static class SyntheticClass_1 {
      static final int[] a = new int[BlockMonsterEggs.EnumMonsterEggVarient.values().length];

      static {
         try {
            a[BlockMonsterEggs.EnumMonsterEggVarient.COBBLESTONE.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
            ;
         }

         try {
            a[BlockMonsterEggs.EnumMonsterEggVarient.STONEBRICK.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            a[BlockMonsterEggs.EnumMonsterEggVarient.MOSSY_STONEBRICK.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            a[BlockMonsterEggs.EnumMonsterEggVarient.CRACKED_STONEBRICK.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
            ;
         }

         try {
            a[BlockMonsterEggs.EnumMonsterEggVarient.CHISELED_STONEBRICK.ordinal()] = 5;
         } catch (NoSuchFieldError var0) {
            ;
         }

      }
   }
}
