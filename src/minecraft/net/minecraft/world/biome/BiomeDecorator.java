package net.minecraft.world.biome;

import net.custom.CustomRandom;
import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkProviderSettings;
import net.minecraft.world.gen.GeneratorBushFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeDecorator {
   protected World currentWorld;
    private static final Logger logger = LogManager.getLogger();
   protected Random randomGenerator;
   protected BlockPos field_180294_c;
   protected ChunkProviderSettings chunkProviderSettings;
   protected WorldGenerator clayGen = new WorldGenClay(4);
   protected WorldGenerator sandGen = new WorldGenSand(Blocks.sand, 7);
   protected WorldGenerator gravelAsSandGen = new WorldGenSand(Blocks.gravel, 6);
   protected WorldGenerator dirtGen;
   protected WorldGenerator gravelGen;
   protected WorldGenerator graniteGen;
   protected WorldGenerator dioriteGen;
   protected WorldGenerator andesiteGen;
   protected WorldGenerator coalGen;
   protected WorldGenerator ironGen;
   protected WorldGenerator goldGen;
   protected WorldGenerator redstoneGen;
   protected WorldGenerator diamondGen;
   protected WorldGenerator lapisGen;
   protected WorldGenFlowers yellowFlowerGen = new WorldGenFlowers(Blocks.yellow_flower, BlockFlower.EnumFlowerType.DANDELION);
   protected WorldGenerator mushroomBrownGen = new GeneratorBushFeature(Blocks.brown_mushroom);
   protected WorldGenerator mushroomRedGen = new GeneratorBushFeature(Blocks.red_mushroom);
   protected WorldGenerator bigMushroomGen = new WorldGenBigMushroom();
   protected WorldGenerator reedGen = new WorldGenReed();
   protected WorldGenerator cactusGen = new WorldGenCactus();
   protected WorldGenerator waterlilyGen = new WorldGenWaterlily();
   protected int waterlilyPerChunk;
   protected int treesPerChunk;
   protected int flowersPerChunk = 2;
   protected int grassPerChunk = 1;
   protected int deadBushPerChunk;
   protected int mushroomsPerChunk;
   protected int reedsPerChunk;
   protected int cactiPerChunk;
   protected int sandPerChunk = 1;
   protected int sandPerChunk2 = 3;
   protected int clayPerChunk = 1;
   protected int bigMushroomsPerChunk;
   public boolean generateLakes = true;

   public void decorate(World worldIn, Random random, BiomeGenBase p_180292_3_, BlockPos p_180292_4_) {
      if(this.currentWorld != null) {
         throw new RuntimeException("Already decorating");
      } else {
         this.currentWorld = worldIn;
         String s = worldIn.getWorldInfo().getGeneratorOptions();
         if(s != null) {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory(s).func_177864_b();
         } else {
            this.chunkProviderSettings = ChunkProviderSettings.Factory.jsonToFactory("").func_177864_b();
         }

         this.randomGenerator = random;
         logger.info("custom logger BiomeDecorator: this.randomGenerator = random;");
         this.field_180294_c = p_180292_4_;
         this.dirtGen = new WorldGenMinable(Blocks.dirt.getDefaultState(), this.chunkProviderSettings.dirtSize);
         this.gravelGen = new WorldGenMinable(Blocks.gravel.getDefaultState(), this.chunkProviderSettings.gravelSize);
         this.graniteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
         this.dioriteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
         this.andesiteGen = new WorldGenMinable(Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
         this.coalGen = new WorldGenMinable(Blocks.coal_ore.getDefaultState(), this.chunkProviderSettings.coalSize);
         this.ironGen = new WorldGenMinable(Blocks.iron_ore.getDefaultState(), this.chunkProviderSettings.ironSize);
         this.goldGen = new WorldGenMinable(Blocks.gold_ore.getDefaultState(), this.chunkProviderSettings.goldSize);
         this.redstoneGen = new WorldGenMinable(Blocks.redstone_ore.getDefaultState(), this.chunkProviderSettings.redstoneSize);
         this.diamondGen = new WorldGenMinable(Blocks.diamond_ore.getDefaultState(), this.chunkProviderSettings.diamondSize);
         this.lapisGen = new WorldGenMinable(Blocks.lapis_ore.getDefaultState(), this.chunkProviderSettings.lapisSize);
         this.genDecorations(p_180292_3_);
         this.currentWorld = null;
         this.randomGenerator = null;
          logger.info("custom logger BiomeDecorator: this.randomGenerator = null;");
      }
   }

   protected void genDecorations(BiomeGenBase biomeGenBaseIn) {
      this.generateOres();

      for(int i = 0; i < this.sandPerChunk2; ++i) {
         logger.info("custom logger BiomeDecorator decore: this.randomGenerator.nextInt(16) two times");
         int j = this.randomGenerator.nextInt(16) + 8;
         int k = this.randomGenerator.nextInt(16) + 8;
         this.sandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(j, 0, k)));
      }

      for(int i1 = 0; i1 < this.clayPerChunk; ++i1) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int l1 = this.randomGenerator.nextInt(16) + 8;
         int i6 = this.randomGenerator.nextInt(16) + 8;
         this.clayGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(l1, 0, i6)));
      }

      for(int j1 = 0; j1 < this.sandPerChunk; ++j1) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int i2 = this.randomGenerator.nextInt(16) + 8;
         int j6 = this.randomGenerator.nextInt(16) + 8;
         this.gravelAsSandGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getTopSolidOrLiquidBlock(this.field_180294_c.add(i2, 0, j6)));
      }

      int k1 = this.treesPerChunk;
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(10)");
      if(this.randomGenerator.nextInt(10) == 0) {
         ++k1;
      }

      for(int j2 = 0; j2 < k1; ++j2) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int k6 = this.randomGenerator.nextInt(16) + 8;
         int l = this.randomGenerator.nextInt(16) + 8;
         WorldGenAbstractTree worldgenabstracttree = biomeGenBaseIn.genBigTreeChance(this.randomGenerator);
         worldgenabstracttree.func_175904_e();
         BlockPos blockpos = this.currentWorld.getHeight(this.field_180294_c.add(k6, 0, l));
         if(worldgenabstracttree.generate(this.currentWorld, this.randomGenerator, blockpos)) {
            worldgenabstracttree.func_180711_a(this.currentWorld, this.randomGenerator, blockpos);
         }
      }

      for(int k2 = 0; k2 < this.bigMushroomsPerChunk; ++k2) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int l6 = this.randomGenerator.nextInt(16) + 8;
         int k10 = this.randomGenerator.nextInt(16) + 8;
         this.bigMushroomGen.generate(this.currentWorld, this.randomGenerator, this.currentWorld.getHeight(this.field_180294_c.add(l6, 0, k10)));
      }

      for(int l2 = 0; l2 < this.flowersPerChunk; ++l2) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int i7 = this.randomGenerator.nextInt(16) + 8;
         int l10 = this.randomGenerator.nextInt(16) + 8;
         int j14 = this.currentWorld.getHeight(this.field_180294_c.add(i7, 0, l10)).getY() + 32;
         if(j14 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",j14);
            int k17 = this.randomGenerator.nextInt(j14);
            BlockPos blockpos1 = this.field_180294_c.add(i7, k17, l10);
            BlockFlower.EnumFlowerType blockflower$enumflowertype = biomeGenBaseIn.pickRandomFlower(this.randomGenerator, blockpos1);
            BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
            if(blockflower.getMaterial() != Material.air) {
               this.yellowFlowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
               this.yellowFlowerGen.generate(this.currentWorld, this.randomGenerator, blockpos1);
            }
         }
      }

      for(int i3 = 0; i3 < this.grassPerChunk; ++i3) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int j7 = this.randomGenerator.nextInt(16) + 8;
         int i11 = this.randomGenerator.nextInt(16) + 8;
         int k14 = this.currentWorld.getHeight(this.field_180294_c.add(j7, 0, i11)).getY() * 2;
         if(k14 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",k14);
            int l17 = this.randomGenerator.nextInt(k14);
            biomeGenBaseIn.getRandomWorldGenForGrass(this.randomGenerator).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j7, l17, i11));
         }
      }

      for(int j3 = 0; j3 < this.deadBushPerChunk; ++j3) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int k7 = this.randomGenerator.nextInt(16) + 8;
         int j11 = this.randomGenerator.nextInt(16) + 8;
         int l14 = this.currentWorld.getHeight(this.field_180294_c.add(k7, 0, j11)).getY() * 2;
         if(l14 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",l14);
            int i18 = this.randomGenerator.nextInt(l14);
            (new WorldGenDeadBush()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(k7, i18, j11));
         }
      }

      for(int k3 = 0; k3 < this.waterlilyPerChunk; ++k3) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int l7 = this.randomGenerator.nextInt(16) + 8;
         int k11 = this.randomGenerator.nextInt(16) + 8;
         int i15 = this.currentWorld.getHeight(this.field_180294_c.add(l7, 0, k11)).getY() * 2;
         if(i15 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",i15);
            int j18 = this.randomGenerator.nextInt(i15);

            BlockPos blockpos4;
            BlockPos blockpos7;
            for(blockpos4 = this.field_180294_c.add(l7, j18, k11); blockpos4.getY() > 0; blockpos4 = blockpos7) {
               blockpos7 = blockpos4.down();
               if(!this.currentWorld.isAirBlock(blockpos7)) {
                  break;
               }
            }

            this.waterlilyGen.generate(this.currentWorld, this.randomGenerator, blockpos4);
         }
      }

      for(int l3 = 0; l3 < this.mushroomsPerChunk; ++l3) {

         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(4)");
         if(this.randomGenerator.nextInt(4) == 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
            int i8 = this.randomGenerator.nextInt(16) + 8;
            int l11 = this.randomGenerator.nextInt(16) + 8;
            BlockPos blockpos2 = this.currentWorld.getHeight(this.field_180294_c.add(i8, 0, l11));
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, blockpos2);
         }

         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(8)");
         if(this.randomGenerator.nextInt(8) == 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
            int j8 = this.randomGenerator.nextInt(16) + 8;
            int i12 = this.randomGenerator.nextInt(16) + 8;
            int j15 = this.currentWorld.getHeight(this.field_180294_c.add(j8, 0, i12)).getY() * 2;
            if(j15 > 0) {

               logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",j15);
               int k18 = this.randomGenerator.nextInt(j15);
               BlockPos blockpos5 = this.field_180294_c.add(j8, k18, i12);
               this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, blockpos5);
            }
         }
      }

      logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(4)");
      if(this.randomGenerator.nextInt(4) == 0) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int i4 = this.randomGenerator.nextInt(16) + 8;
         int k8 = this.randomGenerator.nextInt(16) + 8;
         int j12 = this.currentWorld.getHeight(this.field_180294_c.add(i4, 0, k8)).getY() * 2;
         if(j12 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",j12);
            int k15 = this.randomGenerator.nextInt(j12);
            this.mushroomBrownGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i4, k15, k8));
         }
      }

      logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(8)");
      if(this.randomGenerator.nextInt(8) == 0) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int j4 = this.randomGenerator.nextInt(16) + 8;
         int l8 = this.randomGenerator.nextInt(16) + 8;
         int k12 = this.currentWorld.getHeight(this.field_180294_c.add(j4, 0, l8)).getY() * 2;
         if(k12 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",k12);
            int l15 = this.randomGenerator.nextInt(k12);
            this.mushroomRedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j4, l15, l8));
         }
      }

      for(int k4 = 0; k4 < this.reedsPerChunk; ++k4) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int i9 = this.randomGenerator.nextInt(16) + 8;
         int l12 = this.randomGenerator.nextInt(16) + 8;
         int i16 = this.currentWorld.getHeight(this.field_180294_c.add(i9, 0, l12)).getY() * 2;
         if(i16 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",i16);
            int l18 = this.randomGenerator.nextInt(i16);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i9, l18, l12));
         }
      }

      for(int l4 = 0; l4 < 10; ++l4) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int j9 = this.randomGenerator.nextInt(16) + 8;
         int i13 = this.randomGenerator.nextInt(16) + 8;
         int j16 = this.currentWorld.getHeight(this.field_180294_c.add(j9, 0, i13)).getY() * 2;
         if(j16 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",j16);
            int i19 = this.randomGenerator.nextInt(j16);
            this.reedGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(j9, i19, i13));
         }
      }

      logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(32)");
      if(this.randomGenerator.nextInt(32) == 0) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int i5 = this.randomGenerator.nextInt(16) + 8;
         int k9 = this.randomGenerator.nextInt(16) + 8;
         int j13 = this.currentWorld.getHeight(this.field_180294_c.add(i5, 0, k9)).getY() * 2;
         if(j13 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",j13);
            int k16 = this.randomGenerator.nextInt(j13);
            (new WorldGenPumpkin()).generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(i5, k16, k9));
         }
      }

      for(int j5 = 0; j5 < this.cactiPerChunk; ++j5) {
         logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
         int l9 = this.randomGenerator.nextInt(16) + 8;
         int k13 = this.randomGenerator.nextInt(16) + 8;
         int l16 = this.currentWorld.getHeight(this.field_180294_c.add(l9, 0, k13)).getY() * 2;
         if(l16 > 0) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",l16);
            int j19 = this.randomGenerator.nextInt(l16);
            this.cactusGen.generate(this.currentWorld, this.randomGenerator, this.field_180294_c.add(l9, j19, k13));
         }
      }

      if(this.generateLakes) {
         for(int k5 = 0; k5 < 50; ++k5) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(248)");
            int i10 = this.randomGenerator.nextInt(16) + 8;
            int l13 = this.randomGenerator.nextInt(16) + 8;
            int i17 = this.randomGenerator.nextInt(248) + 8;
            if(i17 > 0) {
               logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt({})",i17);
               int k19 = this.randomGenerator.nextInt(i17);
               BlockPos blockpos6 = this.field_180294_c.add(i10, k19, l13);
               (new WorldGenLiquids(Blocks.flowing_water)).generate(this.currentWorld, this.randomGenerator, blockpos6);
            }
         }

         for(int l5 = 0; l5 < 20; ++l5) {
            logger.info("custom logger BiomeDecorator: this.randomGenerator.nextInt(16) two times");
            logger.info("custom logger BiomeDecorator decor: this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);");
            int j10 = this.randomGenerator.nextInt(16) + 8;
            int i14 = this.randomGenerator.nextInt(16) + 8;
            int j17 = this.randomGenerator.nextInt(this.randomGenerator.nextInt(this.randomGenerator.nextInt(240) + 8) + 8);
            BlockPos blockpos3 = this.field_180294_c.add(j10, j17, i14);
            (new WorldGenLiquids(Blocks.flowing_lava)).generate(this.currentWorld, this.randomGenerator, blockpos3);
         }
      }
   }

   protected void genStandardOre1(int blockCount, WorldGenerator generator, int minHeight, int maxHeight) {
      if(maxHeight < minHeight) {
         int i = minHeight;
         minHeight = maxHeight;
         maxHeight = i;
      } else if(maxHeight == minHeight) {
         if(minHeight < 255) {
            ++maxHeight;
         } else {
            --minHeight;
         }
      }

      for(int j = 0; j < blockCount; ++j) {
//         logger.info("custom logger BiomeDecorator ore1:this.field_180294_c.add(this.randomGenerator.nextInt(16)\nthis.randomGenerator.nextInt({});\nthis.randomGenerator.nextInt(16));",maxHeight - minHeight);
         BlockPos blockpos = this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(maxHeight - minHeight) + minHeight, this.randomGenerator.nextInt(16));
         generator.generate(this.currentWorld, this.randomGenerator, blockpos);
      }
   }

   protected void genStandardOre2(int blockCount, WorldGenerator generator, int centerHeight, int spread) {
//       this.randomGenerator.setSeed(2873990483555L);

       for(int i = 0; i < blockCount; ++i) {
//         logger.info("custom logger BiomeDecorator ore2:this.randomGenerator.nextInt(16)\nthis.randomGenerator.nextInt({})\nthis.randomGenerator.nextInt({})\nthis.randomGenerator.nextInt(16));",spread,spread);
         BlockPos blockpos = this.field_180294_c.add(this.randomGenerator.nextInt(16), this.randomGenerator.nextInt(spread) + this.randomGenerator.nextInt(spread) + centerHeight - spread, this.randomGenerator.nextInt(16));
         generator.generate(this.currentWorld, this.randomGenerator, blockpos);

      }
//       int chance = this.randomGenerator.nextInt(5);
//       logger.info("\ncustom logger BiomeDecorator: this.randomGenerator.nextInt(16) = {}",chance);
//       int x = this.randomGenerator.nextInt(16) + 8;
//       int z = this.randomGenerator.nextInt(16) + 8;
//       logger.info("custom logger BiomeDecorator: x:{}, z:{}",x,z);
   }

   protected void generateOres() {
      this.genStandardOre1(this.chunkProviderSettings.dirtCount,/*10*/ this.dirtGen, this.chunkProviderSettings.dirtMinHeight,/*0*/ this.chunkProviderSettings.dirtMaxHeight/*256*/);
      this.genStandardOre1(this.chunkProviderSettings.gravelCount,/*10*/ this.gravelGen, this.chunkProviderSettings.gravelMinHeight/*0*/, this.chunkProviderSettings.gravelMaxHeight/*256*/);
      this.genStandardOre1(this.chunkProviderSettings.graniteCount,/*10*/ this.graniteGen, this.chunkProviderSettings.graniteMinHeight/*0*/, this.chunkProviderSettings.graniteMaxHeight/*80*/);
      this.genStandardOre1(this.chunkProviderSettings.dioriteCount,/*10*/ this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight/*0*/, this.chunkProviderSettings.dioriteMaxHeight/*80*/);
      this.genStandardOre1(this.chunkProviderSettings.andesiteCount,/*10*/ this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight/*0*/, this.chunkProviderSettings.andesiteMaxHeight/*80*/);
      this.genStandardOre1(this.chunkProviderSettings.coalCount,/*20*/ this.coalGen, this.chunkProviderSettings.coalMinHeight/*0*/, this.chunkProviderSettings.coalMaxHeight/*128*/);
      this.genStandardOre1(this.chunkProviderSettings.ironCount,/*20*/ this.ironGen, this.chunkProviderSettings.ironMinHeight/*0*/, this.chunkProviderSettings.ironMaxHeight/*64*/);
      this.genStandardOre1(this.chunkProviderSettings.goldCount,/*2*/ this.goldGen, this.chunkProviderSettings.goldMinHeight/*0*/, this.chunkProviderSettings.goldMaxHeight/*32*/);
      this.genStandardOre1(this.chunkProviderSettings.redstoneCount,/*8*/ this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight/*0*/, this.chunkProviderSettings.redstoneMaxHeight/*16*/);
      this.genStandardOre1(this.chunkProviderSettings.diamondCount,/*1*/ this.diamondGen, this.chunkProviderSettings.diamondMinHeight/*0*/, this.chunkProviderSettings.diamondMaxHeight/*16*/);
      this.genStandardOre2(this.chunkProviderSettings.lapisCount,/*1*/ this.lapisGen, this.chunkProviderSettings.lapisCenterHeight/*16*/, this.chunkProviderSettings.lapisSpread/*16*/);

   }
}
