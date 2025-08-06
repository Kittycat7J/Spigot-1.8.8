package net.minecraft.server.v1_8_R3;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.Collection;
import net.minecraft.server.v1_8_R3.BlockPlant;
import net.minecraft.server.v1_8_R3.BlockStateEnum;
import net.minecraft.server.v1_8_R3.BlockStateList;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.IBlockData;
import net.minecraft.server.v1_8_R3.IBlockState;
import net.minecraft.server.v1_8_R3.INamable;

public abstract class BlockFlowers extends BlockPlant {
   protected BlockStateEnum<BlockFlowers.EnumFlowerVarient> TYPE;

   protected BlockFlowers() {
      this.j(this.blockStateList.getBlockData().set(this.n(), this.l() == BlockFlowers.EnumFlowerType.RED?BlockFlowers.EnumFlowerVarient.POPPY:BlockFlowers.EnumFlowerVarient.DANDELION));
   }

   public int getDropData(IBlockData p_getDropData_1_) {
      return ((BlockFlowers.EnumFlowerVarient)p_getDropData_1_.get(this.n())).b();
   }

   public IBlockData fromLegacyData(int p_fromLegacyData_1_) {
      return this.getBlockData().set(this.n(), BlockFlowers.EnumFlowerVarient.a(this.l(), p_fromLegacyData_1_));
   }

   public abstract BlockFlowers.EnumFlowerType l();

   public IBlockState<BlockFlowers.EnumFlowerVarient> n() {
      if(this.TYPE == null) {
         this.TYPE = BlockStateEnum.a("type", BlockFlowers.EnumFlowerVarient.class, new Predicate<BlockFlowers.EnumFlowerVarient>() {
            public boolean a(BlockFlowers.EnumFlowerVarient p_a_1_) {
               return p_a_1_.a() == BlockFlowers.this.l();
            }
         });
      }

      return this.TYPE;
   }

   public int toLegacyData(IBlockData p_toLegacyData_1_) {
      return ((BlockFlowers.EnumFlowerVarient)p_toLegacyData_1_.get(this.n())).b();
   }

   protected BlockStateList getStateList() {
      return new BlockStateList(this, new IBlockState[]{this.n()});
   }

   public static enum EnumFlowerType {
      YELLOW,
      RED;

      public BlockFlowers a() {
         return this == YELLOW?Blocks.YELLOW_FLOWER:Blocks.RED_FLOWER;
      }
   }

   public static enum EnumFlowerVarient implements INamable {
      DANDELION(BlockFlowers.EnumFlowerType.YELLOW, 0, "dandelion"),
      POPPY(BlockFlowers.EnumFlowerType.RED, 0, "poppy"),
      BLUE_ORCHID(BlockFlowers.EnumFlowerType.RED, 1, "blue_orchid", "blueOrchid"),
      ALLIUM(BlockFlowers.EnumFlowerType.RED, 2, "allium"),
      HOUSTONIA(BlockFlowers.EnumFlowerType.RED, 3, "houstonia"),
      RED_TULIP(BlockFlowers.EnumFlowerType.RED, 4, "red_tulip", "tulipRed"),
      ORANGE_TULIP(BlockFlowers.EnumFlowerType.RED, 5, "orange_tulip", "tulipOrange"),
      WHITE_TULIP(BlockFlowers.EnumFlowerType.RED, 6, "white_tulip", "tulipWhite"),
      PINK_TULIP(BlockFlowers.EnumFlowerType.RED, 7, "pink_tulip", "tulipPink"),
      OXEYE_DAISY(BlockFlowers.EnumFlowerType.RED, 8, "oxeye_daisy", "oxeyeDaisy");

      private static final BlockFlowers.EnumFlowerVarient[][] k = new BlockFlowers.EnumFlowerVarient[BlockFlowers.EnumFlowerType.values().length][];
      private final BlockFlowers.EnumFlowerType l;
      private final int m;
      private final String n;
      private final String o;

      private EnumFlowerVarient(BlockFlowers.EnumFlowerType p_i616_3_, int p_i616_4_, String p_i616_5_) {
         this(p_i616_3_, p_i616_4_, p_i616_5_, p_i616_5_);
      }

      private EnumFlowerVarient(BlockFlowers.EnumFlowerType p_i617_3_, int p_i617_4_, String p_i617_5_, String p_i617_6_) {
         this.l = p_i617_3_;
         this.m = p_i617_4_;
         this.n = p_i617_5_;
         this.o = p_i617_6_;
      }

      public BlockFlowers.EnumFlowerType a() {
         return this.l;
      }

      public int b() {
         return this.m;
      }

      public static BlockFlowers.EnumFlowerVarient a(BlockFlowers.EnumFlowerType p_a_0_, int p_a_1_) {
         BlockFlowers.EnumFlowerVarient[] ablockflowers$enumflowervarient = k[p_a_0_.ordinal()];
         if(p_a_1_ < 0 || p_a_1_ >= ablockflowers$enumflowervarient.length) {
            p_a_1_ = 0;
         }

         return ablockflowers$enumflowervarient[p_a_1_];
      }

      public String toString() {
         return this.n;
      }

      public String getName() {
         return this.n;
      }

      public String d() {
         return this.o;
      }

      static {
         for(final BlockFlowers.EnumFlowerType blockflowers$enumflowertype : BlockFlowers.EnumFlowerType.values()) {
            Collection collection = Collections2.filter(Lists.newArrayList(values()), new Predicate<BlockFlowers.EnumFlowerVarient>() {
               public boolean a(BlockFlowers.EnumFlowerVarient p_a_1_) {
                  return p_a_1_.a() == blockflowers$enumflowertype;
               }
            });
            k[blockflowers$enumflowertype.ordinal()] = (BlockFlowers.EnumFlowerVarient[])collection.toArray(new BlockFlowers.EnumFlowerVarient[collection.size()]);
         }

      }
   }
}
